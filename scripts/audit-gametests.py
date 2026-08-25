#!/usr/bin/env python3
"""Run loader-native Useful Tools GameTests with exact process cleanup."""

from __future__ import annotations

import argparse
from dataclasses import dataclass
import json
import os
from pathlib import Path
import queue
import re
import signal
import subprocess
import sys
import threading
import time
import tempfile
import uuid


REPO_ROOT = Path(__file__).resolve().parent.parent
RUN_ID_ENV = "USEFUL_TOOLS_GAMETEST_RUN_ID"
FAIL_RE = re.compile(
    r"required tests failed|GAME TEST FAILED|Game test server crashed|No test functions were given|Missing test structure",
    re.IGNORECASE,
)
SUMMARY_RE = re.compile(
    r"tests are now running|Running test batch|GAME TESTS COMPLETE|required tests|Game test server crashed|"
    r"Missing test structure|No test functions|BUILD (?:SUCCESSFUL|FAILED)|Execution failed",
    re.IGNORECASE,
)
ANSI_RE = re.compile(r"\x1b\[[0-9;]*[A-Za-z]")


@dataclass(frozen=True)
class Target:
    directory: str
    java_home: str
    task: str
    required_tests: int = 6


TARGETS = {
    "1.20.1-forge": Target("1.20.1/forge", "/usr/lib/jvm/java-17-openjdk", "runGameTestServer"),
    "1.20.1-fabric": Target("1.20.1/fabric", "/usr/lib/jvm/java-21-openjdk", "runGameTestServer"),
    "1.20.1-neoforge": Target("1.20.1/neoforge", "/usr/lib/jvm/java-17-openjdk", "runGameTestServer"),
    "1.20.2-forge": Target("1.20.2/forge", "/usr/lib/jvm/java-17-openjdk", "runGameTestServer"),
    "1.20.2-fabric": Target("1.20.2/fabric", "/usr/lib/jvm/java-21-openjdk", "runGameTestServer"),
    "1.20.2-neoforge": Target("1.20.2/neoforge", "/usr/lib/jvm/java-17-openjdk", "runGameTestServer"),
    "1.21.1-forge": Target("1.21.1/forge", "/usr/lib/jvm/java-21-openjdk", "runGameTestServer"),
    "1.21.1-fabric": Target("1.21.1/fabric", "/usr/lib/jvm/java-21-openjdk", "runGameTestServer"),
    "1.21.1-neoforge": Target("1.21.1/neoforge", "/usr/lib/jvm/java-21-openjdk", "runGameTestServer"),
    "26.1.2-forge": Target("26.1.2/forge", "/usr/lib/jvm/java-26-openjdk", "runGameTestServer", 7),
    "26.1.2-fabric": Target("26.1.2/fabric", "/home/braydon/.gradle/jdks/eclipse_adoptium-25-amd64-linux.2", "runGameTestServer", 7),
    "26.1.2-neoforge": Target("26.1.2/neoforge", "/usr/lib/jvm/java-21-openjdk", "runGameTestServer", 7),
    "26.2-fabric": Target("26.2/fabric", "/home/braydon/.gradle/jdks/eclipse_adoptium-25-amd64-linux.2", "runGameTestServer", 7),
    "26.2-neoforge": Target("26.2/neoforge", "/usr/lib/jvm/java-21-openjdk", "runGameTestServer", 7),
    "26.2-forge": Target("26.2/forge", "/usr/lib/jvm/java-26-openjdk", "runGameTestServer", 7),
}


def parse_args() -> argparse.Namespace:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("target", choices=[*TARGETS, "all"])
    parser.add_argument("--timeout", type=int, default=180)
    parser.add_argument("--verbose", action="store_true")
    parser.add_argument(
        "--output-dir", type=Path, default=REPO_ROOT / "validation" / "gametests"
    )
    return parser.parse_args()


def read_proc(pid: int, name: str) -> bytes:
    try:
        return (Path("/proc") / str(pid) / name).read_bytes()
    except (FileNotFoundError, PermissionError, ProcessLookupError):
        return b""


def owned_processes(run_id: str) -> list[int]:
    marker = f"{RUN_ID_ENV}={run_id}".encode()
    owned = []
    for entry in Path("/proc").iterdir():
        if not entry.name.isdigit():
            continue
        pid = int(entry.name)
        if pid != os.getpid() and marker in read_proc(pid, "environ").split(b"\0"):
            owned.append(pid)
    return sorted(owned)


def cleanup(run_id: str) -> list[int]:
    for sig, wait_seconds in ((signal.SIGTERM, 5), (signal.SIGKILL, 5)):
        for pid in reversed(owned_processes(run_id)):
            try:
                os.kill(pid, sig)
            except ProcessLookupError:
                pass
        deadline = time.monotonic() + wait_seconds
        while time.monotonic() < deadline and owned_processes(run_id):
            time.sleep(0.2)
        if not owned_processes(run_id):
            break
    return owned_processes(run_id)


def run_target(name: str, target: Target, args: argparse.Namespace) -> bool:
    run_id = uuid.uuid4().hex
    env = os.environ.copy()
    env["JAVA_HOME"] = target.java_home
    env["PATH"] = f"{target.java_home}/bin:{env.get('PATH', '')}"
    env[RUN_ID_ENV] = run_id
    tmpfs_root = Path("/dev/shm")
    temporary_root = str(tmpfs_root) if tmpfs_root.is_dir() and os.access(tmpfs_root, os.W_OK) else None
    temporary_game_dir = tempfile.TemporaryDirectory(
        prefix="usefultools-gametest-", dir=temporary_root
    )
    game_dir = Path(temporary_game_dir.name)
    command = [
        "./gradlew",
        "--no-daemon",
        "--max-workers=1",
        "--init-script",
        str(REPO_ROOT / "scripts/smoke-isolated.gradle"),
        f"-PusefulToolsSmokeGameDir={game_dir}",
        target.task,
    ]
    output_dir = args.output_dir.resolve()
    output_dir.mkdir(parents=True, exist_ok=True)
    log_path = output_dir / f"{name}.log"
    lines: queue.Queue[str | None] = queue.Queue()
    passed = False
    failed = False
    timed_out = False
    forced_cleanup = False
    pass_re = re.compile(
        rf"All {target.required_tests} required tests passed|"
        rf"testsuite[^>]+tests=\"{target.required_tests}\"",
        re.IGNORECASE,
    )

    print(f"==> {name}: {target.task}", flush=True)
    process: subprocess.Popen[str] | None = None
    try:
        with log_path.open("w", encoding="utf-8") as log:
            process = subprocess.Popen(
                command,
                cwd=REPO_ROOT / target.directory,
                env=env,
                stdout=subprocess.PIPE,
                stderr=subprocess.STDOUT,
                text=True,
                bufsize=1,
                start_new_session=True,
            )

            def reader() -> None:
                assert process is not None and process.stdout is not None
                for line in process.stdout:
                    lines.put(line)
                lines.put(None)

            threading.Thread(target=reader, daemon=True).start()
            deadline = time.monotonic() + args.timeout
            pass_grace: float | None = None
            while time.monotonic() < deadline:
                try:
                    line = lines.get(timeout=0.2)
                except queue.Empty:
                    line = ""
                if line is None:
                    break
                if line:
                    clean = ANSI_RE.sub("", line)
                    log.write(clean)
                    log.flush()
                    if args.verbose or SUMMARY_RE.search(clean):
                        sys.stdout.write(clean)
                        sys.stdout.flush()
                    if pass_re.search(clean):
                        passed = True
                        pass_grace = time.monotonic() + 10
                    if FAIL_RE.search(clean):
                        failed = True
                if process.poll() is not None:
                    break
                if pass_grace is not None and time.monotonic() >= pass_grace:
                    forced_cleanup = True
                    break
            else:
                timed_out = True
            if process.poll() is None:
                cleanup(run_id)
            try:
                process.wait(timeout=2)
            except subprocess.TimeoutExpired:
                pass
            remaining = cleanup(run_id)

        exit_code = process.poll()
        success = passed and not failed and not timed_out and not remaining and (
            exit_code in (0, 143, -signal.SIGTERM, -signal.SIGKILL) or forced_cleanup
        )
        report = {
            "target": name,
            "required_tests": target.required_tests,
            "command": command,
            "game_dir": str(game_dir),
            "passed": passed,
            "failed_marker": failed,
            "timed_out": timed_out,
            "forced_launcher_cleanup": forced_cleanup,
            "exit_code": exit_code,
            "remaining_owned_pids": remaining,
            "log": str(log_path.relative_to(REPO_ROOT)),
            "success": success,
        }
        (output_dir / f"{name}.json").write_text(
            json.dumps(report, indent=2) + "\n", encoding="utf-8"
        )
        print(f"gametest audit: {'OK' if success else 'FAILED'} ({name})")
        return success
    finally:
        cleanup(run_id)
        temporary_game_dir.cleanup()


def main() -> int:
    args = parse_args()
    names = list(TARGETS) if args.target == "all" else [args.target]
    results: dict[str, bool] = {}
    for name in names:
        results[name] = run_target(name, TARGETS[name], args)
    success = all(results.values())
    if args.target == "all":
        output_dir = args.output_dir.resolve()
        reports = {
            name: json.loads((output_dir / f"{name}.json").read_text(encoding="utf-8"))
            for name in names
        }
        summary = {
            "target_count": len(names),
            "required_test_invocations": sum(
                target.required_tests for target in TARGETS.values()
            ),
            "passed_targets": sum(results.values()),
            "failed_targets": [name for name, passed in results.items() if not passed],
            "success": success,
            "reports": reports,
        }
        (output_dir / "matrix-summary.json").write_text(
            json.dumps(summary, indent=2) + "\n", encoding="utf-8"
        )
        print(
            f"gametest matrix: {summary['passed_targets']}/{summary['target_count']} targets passed"
        )
    return 0 if success else 1


if __name__ == "__main__":
    raise SystemExit(main())
