#!/usr/bin/env python3
"""Run one Useful Tools development server with bounded, exact cleanup.

Every descendant receives a unique environment marker.  That lets this gate
find launcher JVMs even when Gradle reparents them, without matching or killing
unrelated Minecraft/Gradle processes owned by the user.
"""

from __future__ import annotations

import argparse
import json
import os
from pathlib import Path
import queue
import re
import signal
import socket
import subprocess
import sys
import threading
import time
import tempfile
import uuid


REPO_ROOT = Path(__file__).resolve().parent.parent
RUN_ID_ENV = "USEFUL_TOOLS_SMOKE_RUN_ID"
READY_RE = re.compile(r"\bDone \([^\r\n]+\)!\s*(?:For help|$)")
STOPPING_RE = re.compile(r"\bStopping (?:the )?server\b", re.IGNORECASE)
CRASH_RE = re.compile(
    r"Encountered an unexpected exception|crash report has been saved|"
    r"ModLoadingException: Loading errors encountered|Mixin apply failed|"
    r"Failed to start the minecraft server|Encountered non-modloading exceptions",
    re.IGNORECASE,
)
ANSI_RE = re.compile(r"\x1b\[[0-?]*[ -/]*[@-~]")
SUMMARY_RE = re.compile(
    r"Useful Tools|usefultoolsmod|Loading Minecraft|Starting (?:minecraft|Minecraft) server|"
    r"Done \(|Stopping|Saving|BUILD (?:SUCCESSFUL|FAILED)|\bERROR\b|Exception|Caused by|"
    r"java version|Java is"
)

TARGETS = {
    "1.7.10-forge": ("1.7.10", "runServer", "/usr/lib/jvm/java-21-openjdk", "run"),
    "1.20.1-forge": ("1.20.1/forge", "runServer", "/usr/lib/jvm/java-17-openjdk", "run"),
    "1.20.1-fabric": ("1.20.1/fabric", "runServer", "/usr/lib/jvm/java-21-openjdk", "run"),
    "1.20.1-neoforge": ("1.20.1/neoforge", "runServer", "/usr/lib/jvm/java-17-openjdk", "runs/server"),
    "1.20.2-forge": ("1.20.2/forge", "runServer", "/usr/lib/jvm/java-17-openjdk", "run"),
    "1.20.2-fabric": ("1.20.2/fabric", "runServer", "/usr/lib/jvm/java-21-openjdk", "run"),
    "1.20.2-neoforge": ("1.20.2/neoforge", "runServer", "/usr/lib/jvm/java-17-openjdk", "runs/server"),
    "1.21.1-fabric": ("1.21.1", ":fabric:runServer", "/usr/lib/jvm/java-21-openjdk", "fabric/run"),
    "1.21.1-forge": ("1.21.1/forge-standalone", "runServer", "/usr/lib/jvm/java-21-openjdk", "run"),
    "1.21.1-neoforge": ("1.21.1", ":neoforge:runServer", "/usr/lib/jvm/java-21-openjdk", "neoforge/run"),
    "26.1.2-forge": ("26.1.2/forge", "runServer", "/usr/lib/jvm/java-21-openjdk", "run"),
    "26.1.2-fabric": ("26.1.2/fabric", "runServer", "/usr/lib/jvm/java-26-openjdk", "run"),
    "26.1.2-neoforge": ("26.1.2/neoforge", "runServer", "/usr/lib/jvm/java-21-openjdk", "runs/server"),
    "26.2-forge": ("26.2/forge", "runServer", "/usr/lib/jvm/java-21-openjdk", "run"),
    "26.2-fabric": ("26.2/fabric", "runServer", "/usr/lib/jvm/java-26-openjdk", "run"),
    "26.2-neoforge": ("26.2/neoforge", "runServer", "/usr/lib/jvm/java-21-openjdk", "runs/server"),
}


def parse_args() -> argparse.Namespace:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("target", choices=TARGETS)
    parser.add_argument("--startup-timeout", type=int, default=240)
    parser.add_argument("--shutdown-timeout", type=int, default=180)
    parser.add_argument("--port", type=int, default=25565)
    parser.add_argument(
        "--isolated-game-dir",
        action="store_true",
        help="run in a fresh tmpfs game directory to isolate world I/O",
    )
    parser.add_argument(
        "--keep-isolated-game-dir",
        action="store_true",
        help="retain the disposable directory after the run for diagnostics",
    )
    parser.add_argument(
        "--game-dir",
        type=Path,
        help="use an explicit existing game directory (for saved-world upgrade checks)",
    )
    parser.add_argument(
        "--gradle-property",
        action="append",
        default=[],
        metavar="NAME=VALUE",
        help="repeatable non-secret Gradle property used to select a runtime profile",
    )
    parser.add_argument("--verbose", action="store_true", help="also stream the complete server log")
    parser.add_argument(
        "--output-dir",
        type=Path,
        default=REPO_ROOT / "validation" / "server-smoke",
    )
    return parser.parse_args()


def port_is_free(port: int) -> bool:
    sockets: list[socket.socket] = []
    try:
        for family, address in (
            (socket.AF_INET, ("0.0.0.0", port)),
            (socket.AF_INET6, ("::", port)),
        ):
            sock = socket.socket(family, socket.SOCK_STREAM)
            sockets.append(sock)
            sock.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 0)
            if family == socket.AF_INET6:
                sock.setsockopt(socket.IPPROTO_IPV6, socket.IPV6_V6ONLY, 1)
            sock.bind(address)
        return True
    except OSError:
        return False
    finally:
        for sock in sockets:
            sock.close()


def read_proc_file(pid: int, name: str) -> bytes:
    try:
        return (Path("/proc") / str(pid) / name).read_bytes()
    except (FileNotFoundError, PermissionError, ProcessLookupError):
        return b""


def owned_processes(run_id: str) -> list[int]:
    marker = f"{RUN_ID_ENV}={run_id}".encode()
    owned: list[int] = []
    for entry in Path("/proc").iterdir():
        if entry.name.isdigit() and marker in read_proc_file(int(entry.name), "environ").split(b"\0"):
            pid = int(entry.name)
            if pid != os.getpid():
                owned.append(pid)
    return sorted(owned)


def cmdline(pid: int) -> str:
    return read_proc_file(pid, "cmdline").replace(b"\0", b" ").decode(errors="replace")


def process_cwd(pid: int) -> str | None:
    try:
        return str((Path("/proc") / str(pid) / "cwd").resolve(strict=True))
    except (FileNotFoundError, PermissionError, ProcessLookupError):
        return None


def game_processes(run_id: str) -> list[int]:
    candidates = []
    fallbacks = []
    for pid in owned_processes(run_id):
        command = cmdline(pid)
        lower_command = command.lower()
        if "java" not in lower_command:
            continue
        if not any(
            marker in lower_command
            for marker in ("gradledaemon", "gradle-wrapper", "gradleworkermain")
        ):
            fallbacks.append(pid)
        if any(
            marker in lower_command
            for marker in (
                "devlaunchinjector",
                "devlaunch",
                "knotserver",
                "bootstraplauncher",
                "launchwrapper",
                "userdev",
            )
        ):
            candidates.append(pid)
    return candidates or fallbacks


def signal_owned(run_id: str, sig: signal.Signals) -> None:
    for pid in reversed(owned_processes(run_id)):
        try:
            os.kill(pid, sig)
        except ProcessLookupError:
            pass


def wait_for_owned_exit(run_id: str, timeout: float) -> bool:
    deadline = time.monotonic() + timeout
    while time.monotonic() < deadline:
        if not owned_processes(run_id):
            return True
        time.sleep(0.2)
    return not owned_processes(run_id)


def dump_java_threads(run_id: str, output: Path) -> list[int]:
    selected = game_processes(run_id)
    dumped: list[int] = []
    with output.open("w", encoding="utf-8") as handle:
        for pid in selected:
            handle.write(f"===== PID {pid}: {cmdline(pid)} =====\n")
            try:
                result = subprocess.run(
                    ["jcmd", str(pid), "Thread.print"],
                    stdout=subprocess.PIPE,
                    stderr=subprocess.STDOUT,
                    text=True,
                    timeout=15,
                    check=False,
                )
                handle.write(result.stdout)
                dumped.append(pid)
            except (OSError, subprocess.TimeoutExpired) as exc:
                handle.write(f"thread dump failed: {exc}\n")
    return dumped


def cleanup(run_id: str) -> list[int]:
    signal_owned(run_id, signal.SIGTERM)
    if not wait_for_owned_exit(run_id, 5):
        signal_owned(run_id, signal.SIGKILL)
        wait_for_owned_exit(run_id, 5)
    return owned_processes(run_id)


def main() -> int:
    args = parse_args()
    if args.game_dir is not None and (args.isolated_game_dir or args.keep_isolated_game_dir):
        print("--game-dir cannot be combined with isolated-game-dir options", file=sys.stderr)
        return 4
    property_re = re.compile(r"[A-Za-z][A-Za-z0-9_.-]*=[A-Za-z0-9_.-]+")
    invalid_properties = [value for value in args.gradle_property if not property_re.fullmatch(value)]
    if invalid_properties:
        print(f"invalid Gradle properties: {invalid_properties}", file=sys.stderr)
        return 4
    directory, task, java_home, run_dir = TARGETS[args.target]
    project_dir = REPO_ROOT / directory
    output_dir = args.output_dir.resolve()
    output_dir.mkdir(parents=True, exist_ok=True)
    log_path = output_dir / f"{args.target}.log"
    report_path = output_dir / f"{args.target}.json"
    dump_path = output_dir / f"{args.target}-threads.txt"

    if not port_is_free(args.port):
        print(f"refusing to start: TCP port {args.port} is already occupied", file=sys.stderr)
        return 4

    temporary_game_dir: tempfile.TemporaryDirectory[str] | None = None
    external_game_dir = args.game_dir.resolve() if args.game_dir is not None else None
    isolated_launch = args.isolated_game_dir or external_game_dir is not None
    if external_game_dir is not None:
        game_dir = external_game_dir
    elif args.isolated_game_dir:
        tmpfs_root = Path("/dev/shm")
        temporary_root = str(tmpfs_root) if tmpfs_root.is_dir() and os.access(tmpfs_root, os.W_OK) else None
        if args.keep_isolated_game_dir:
            game_dir = Path(tempfile.mkdtemp(prefix="usefultools-smoke-", dir=temporary_root))
        else:
            temporary_game_dir = tempfile.TemporaryDirectory(
                prefix="usefultools-smoke-", dir=temporary_root
            )
            game_dir = Path(temporary_game_dir.name)
    else:
        game_dir = project_dir / run_dir

    launch_directories = [game_dir]
    if isolated_launch:
        # Loader run tasks disagree about whether their configured directory is
        # the game root or a parent for a conventional run subdirectory. Seed
        # only directories inside this disposable root; the live child cwd is
        # captured below and must still remain underneath it.
        launch_directories.extend(
            game_dir / suffix
            for suffix in ("run", "runs/server", "fabric/run", "neoforge/run")
        )
    for launch_directory in launch_directories:
        launch_directory.mkdir(parents=True, exist_ok=True)
        (launch_directory / "eula.txt").write_text(
            "# Local Useful Tools release validation\neula=true\n", encoding="utf-8"
        )

    run_id = uuid.uuid4().hex
    env = os.environ.copy()
    env["JAVA_HOME"] = java_home
    env[RUN_ID_ENV] = run_id
    command = [
        "./gradlew",
        "--no-daemon",
        "--max-workers=1",
        *(
            (
                "--init-script",
                str(REPO_ROOT / "scripts" / "smoke-isolated.gradle"),
                f"-PusefulToolsSmokeGameDir={game_dir}",
            )
            if isolated_launch
            else ()
        ),
        *(f"-P{value}" for value in args.gradle_property),
        task,
    ]
    started_at = time.time()
    ready_at: float | None = None
    stop_sent_at: float | None = None
    stop_method: str | None = None
    term_fallback_pids: list[int] = []
    failure = ""
    crash_marker: str | None = None
    game_process_cwds: dict[int, str | None] = {}
    forced_launcher_cleanup = False
    line_queue: queue.Queue[str | None] = queue.Queue()

    print(f"==> {args.target}: {' '.join(command)}")
    with log_path.open("w", encoding="utf-8") as log:
        process = subprocess.Popen(
            command,
            cwd=project_dir,
            env=env,
            stdin=subprocess.PIPE,
            stdout=subprocess.PIPE,
            stderr=subprocess.STDOUT,
            text=True,
            bufsize=1,
            start_new_session=True,
        )

        def read_output() -> None:
            assert process.stdout is not None
            for line in process.stdout:
                line_queue.put(line)
            line_queue.put(None)

        threading.Thread(target=read_output, daemon=True).start()

        def record(line: str) -> str:
            clean = ANSI_RE.sub("", line)
            log.write(clean)
            log.flush()
            if args.verbose or SUMMARY_RE.search(clean):
                sys.stdout.write(clean)
                sys.stdout.flush()
            return clean

        startup_deadline = time.monotonic() + args.startup_timeout
        output_closed = False
        try:
            while time.monotonic() < startup_deadline and ready_at is None:
                try:
                    line = line_queue.get(timeout=0.2)
                except queue.Empty:
                    if process.poll() is not None:
                        failure = f"launcher exited before readiness with code {process.returncode}"
                        break
                    continue
                if line is None:
                    output_closed = True
                    failure = f"output closed before readiness with code {process.poll()}"
                    break
                clean = record(line)
                if CRASH_RE.search(clean):
                    crash_marker = clean.strip()
                    failure = "server crash marker before readiness"
                    break
                if READY_RE.search(clean):
                    ready_at = time.time()
        except KeyboardInterrupt:
            cleanup(run_id)
            raise

        if ready_at is None and not failure:
            failure = f"startup timeout after {args.startup_timeout}s"

        if ready_at is not None:
            game_process_cwds = {pid: process_cwd(pid) for pid in game_processes(run_id)}
            if isolated_launch:
                resolved_game_dir = game_dir.resolve()
                outside = {
                    pid: cwd
                    for pid, cwd in game_process_cwds.items()
                    if cwd is None or not Path(cwd).is_relative_to(resolved_game_dir)
                }
                if outside:
                    failure = f"isolated game directory was not honored: {outside}"
            assert process.stdin is not None
            process.stdin.write("stop\n")
            process.stdin.flush()
            stop_sent_at = time.time()
            stop_method = "stdin"
            shutdown_deadline = time.monotonic() + args.shutdown_timeout
            fallback_deadline = time.monotonic() + 3
            stopping_seen = False
            saved_dimensions_seen: set[str] = set()
            save_complete_grace: float | None = None
            try:
                while time.monotonic() < shutdown_deadline:
                    try:
                        line = line_queue.get(timeout=0.2)
                    except queue.Empty:
                        line = ""
                    if line is None:
                        output_closed = True
                    elif line:
                        clean = record(line)
                        stopping_seen = stopping_seen or bool(STOPPING_RE.search(clean))
                        if "Saving chunks for level" in clean:
                            saved_dimensions_seen.add(clean.strip())
                        if CRASH_RE.search(clean):
                            crash_marker = clean.strip()
                            failure = "server crash marker after readiness"
                            break
                    if not stopping_seen and not term_fallback_pids and time.monotonic() >= fallback_deadline:
                        term_fallback_pids = game_processes(run_id)
                        for pid in term_fallback_pids:
                            try:
                                # NeoGradle does not forward its JavaExec stdin. SIGINT is
                                # the process-level equivalent of the operator's Ctrl-C and
                                # gives the Minecraft JVM its normal shutdown-hook path.
                                os.kill(pid, signal.SIGINT)
                            except ProcessLookupError:
                                pass
                        if term_fallback_pids:
                            stop_method = "stdin+sigint"
                    if (
                        len(saved_dimensions_seen) >= 3
                        and port_is_free(args.port)
                        and save_complete_grace is None
                    ):
                        save_complete_grace = time.monotonic() + 10
                    if save_complete_grace is not None and time.monotonic() >= save_complete_grace:
                        # Architectury and some development launchers retain a
                        # transformer JVM after Minecraft has completed every
                        # dimension save and released its listener. Exact marker
                        # cleanup closes that launcher lifecycle without
                        # weakening the game-layer shutdown assertion.
                        forced_launcher_cleanup = bool(owned_processes(run_id))
                        break
                    if process.poll() is not None and not owned_processes(run_id):
                        break
                else:
                    failure = f"shutdown timeout after {args.shutdown_timeout}s"
            except KeyboardInterrupt:
                cleanup(run_id)
                raise

        if failure:
            print(f"smoke failure: {failure}", file=sys.stderr)
            dumped = dump_java_threads(run_id, dump_path)
        else:
            dumped = []

        remaining = cleanup(run_id)
        try:
            process.wait(timeout=2)
        except subprocess.TimeoutExpired:
            pass

        # Drain any output already captured by the reader after process exit.
        if not output_closed:
            while True:
                try:
                    line = line_queue.get_nowait()
                except queue.Empty:
                    break
                if line is None:
                    break
                record(line)

    saved_dimensions = sorted(
        {
            line.strip()
            for line in log_path.read_text(encoding="utf-8", errors="replace").splitlines()
            if "Saving chunks for level" in line
        }
    )
    if ready_at is not None and not failure and len(saved_dimensions) < 3:
        failure = f"shutdown saved only {len(saved_dimensions)} dimensions"

    port_free_after = port_is_free(args.port)
    report = {
        "target": args.target,
        "command": command,
        "java_home": java_home,
        "isolated_game_dir": args.isolated_game_dir,
        "external_game_dir": external_game_dir is not None,
        "kept_isolated_game_dir": args.keep_isolated_game_dir,
        "game_dir": str(game_dir),
        "game_process_cwds": game_process_cwds,
        "run_id": run_id,
        "started_at_epoch": started_at,
        "ready": ready_at is not None,
        "ready_seconds": None if ready_at is None else round(ready_at - started_at, 3),
        "stop_sent": stop_sent_at is not None,
        "stop_method": stop_method,
        "term_fallback_pids": term_fallback_pids,
        "forced_launcher_cleanup": forced_launcher_cleanup,
        "clean_shutdown": not failure and not remaining and port_free_after,
        "failure": failure or None,
        "crash_marker": crash_marker,
        "saved_dimension_lines": saved_dimensions,
        "thread_dump_pids": dumped,
        "remaining_owned_pids": remaining,
        "port_free_after": port_free_after,
        "launcher_exit_code": process.poll(),
        "log": str(log_path.relative_to(REPO_ROOT)),
    }
    report_path.write_text(json.dumps(report, indent=2) + "\n", encoding="utf-8")

    if remaining:
        print(f"owned processes survived cleanup: {remaining}", file=sys.stderr)
        return 5
    if not port_free_after:
        print(f"TCP port {args.port} remained occupied", file=sys.stderr)
        return 5
    if failure:
        return 3 if ready_at is not None else 2
    print(f"server smoke: OK ({args.target})")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
