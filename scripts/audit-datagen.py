#!/usr/bin/env python3
"""Run Useful Tools datagen and prove that checked-in resources are stable."""

from __future__ import annotations

import argparse
from dataclasses import dataclass
import hashlib
import json
import os
from pathlib import Path
import queue
import re
import shutil
import signal
import subprocess
import sys
import threading
import time
import uuid


REPO_ROOT = Path(__file__).resolve().parent.parent
RUN_ID_ENV = "USEFUL_TOOLS_DATAGEN_RUN_ID"
COMPLETE_RE = re.compile(r"All providers took:|Data generators? finished", re.IGNORECASE)
FATAL_RE = re.compile(r"Failed to create mod instance|ModLoadingException: Loading errors encountered")
SUMMARY_RE = re.compile(
    r"Starting provider:|finished after|All providers took:|Caching:|BUILD |\bERROR\b|Exception|Caused by"
)


@dataclass(frozen=True)
class Target:
    directory: str
    java_home: str
    tasks: tuple[str, ...]
    resource_roots: tuple[str, ...]
    generated_roots: tuple[str, ...] = ()
    expected_root: str | None = None

    @property
    def compares_external_output(self) -> bool:
        return bool(self.generated_roots)


TARGETS = {
    "1.20.1-forge": Target("1.20.1/forge", "/usr/lib/jvm/java-17-openjdk", ("runData",), ("src/generated/resources",)),
    "1.20.1-fabric": Target("1.20.1/fabric", "/usr/lib/jvm/java-21-openjdk", ("runDatagen",), ("src/main/generated",)),
    "1.20.1-neoforge": Target("1.20.1/neoforge", "/usr/lib/jvm/java-17-openjdk", ("runData",), ("src/generated/resources",)),
    "1.20.2-forge": Target("1.20.2/forge", "/usr/lib/jvm/java-17-openjdk", ("runData",), ("src/generated/resources",)),
    "1.20.2-fabric": Target("1.20.2/fabric", "/usr/lib/jvm/java-21-openjdk", ("runDatagen",), ("src/main/generated",)),
    "1.20.2-neoforge": Target("1.20.2/neoforge", "/usr/lib/jvm/java-17-openjdk", ("runData",), ("src/generated/resources",)),
    "1.21.1-fabric": Target(
        "1.21.1", "/usr/lib/jvm/java-21-openjdk", (":fabric:runDatagen",),
        ("common/src/main/resources",), ("fabric/build/datagen-output",), "common/src/main/resources",
    ),
    "1.21.1-forge": Target("1.21.1/forge-standalone", "/usr/lib/jvm/java-21-openjdk", ("runData",), ("src/generated/resources",)),
    "1.21.1-neoforge": Target(
        "1.21.1", "/usr/lib/jvm/java-21-openjdk", (":neoforge:runData",),
        ("common/src/main/resources",), ("neoforge/build/datagen-output",), "common/src/main/resources",
    ),
    "26.1.2-forge": Target(
        "26.1.2/forge", "/usr/lib/jvm/java-21-openjdk", ("runClientData", "runData"),
        ("src/generated/resources/client", "src/generated/resources/server"),
    ),
    "26.1.2-fabric": Target("26.1.2/fabric", "/usr/lib/jvm/java-26-openjdk", ("runDatagen",), ("src/main/generated",)),
    "26.1.2-neoforge": Target(
        "26.1.2/neoforge", "/usr/lib/jvm/java-21-openjdk", ("runClientData", "runServerData"),
        ("src/generated/resources",),
    ),
    "26.2-forge": Target(
        "26.2/forge", "/usr/lib/jvm/java-21-openjdk", ("runClientData", "runData"),
        ("src/generated/resources/client", "src/generated/resources/server"),
    ),
    "26.2-fabric": Target("26.2/fabric", "/usr/lib/jvm/java-26-openjdk", ("runDatagen",), ("src/main/generated",)),
    "26.2-neoforge": Target(
        "26.2/neoforge", "/usr/lib/jvm/java-21-openjdk", ("runClientData", "runServerData"),
        ("src/generated/resources",),
    ),
}


def parse_args() -> argparse.Namespace:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("target", choices=[*TARGETS, "all"])
    parser.add_argument("--timeout", type=int, default=600)
    parser.add_argument("--verbose", action="store_true")
    parser.add_argument(
        "--output-dir", type=Path, default=REPO_ROOT / "validation" / "datagen"
    )
    return parser.parse_args()


def manifest(roots: list[Path]) -> dict[str, str]:
    result: dict[str, str] = {}
    for root in roots:
        if not root.exists():
            continue
        for path in sorted(root.rglob("*")):
            if not path.is_file() or ".cache" in path.parts:
                continue
            key = f"{root.name}/{path.relative_to(root).as_posix()}"
            result[key] = hashlib.sha256(path.read_bytes()).hexdigest()
    return result


def read_proc(pid: int, name: str) -> bytes:
    try:
        return (Path("/proc") / str(pid) / name).read_bytes()
    except (FileNotFoundError, PermissionError, ProcessLookupError):
        return b""


def owned_processes(run_id: str) -> list[int]:
    marker = f"{RUN_ID_ENV}={run_id}".encode()
    owned = []
    for entry in Path("/proc").iterdir():
        if entry.name.isdigit() and marker in read_proc(int(entry.name), "environ").split(b"\0"):
            pid = int(entry.name)
            if pid != os.getpid():
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


def run_task(
    target_name: str,
    target: Target,
    task: str,
    timeout: int,
    verbose: bool,
    output_dir: Path,
) -> dict[str, object]:
    run_id = uuid.uuid4().hex
    env = os.environ.copy()
    env["JAVA_HOME"] = target.java_home
    env[RUN_ID_ENV] = run_id
    project_dir = REPO_ROOT / target.directory
    command = ["./gradlew", "--no-daemon", "--max-workers=1", task]
    task_slug = task.strip(":").replace(":", "-")
    log_path = output_dir / f"{target_name}-{task_slug}.log"
    lines: queue.Queue[str | None] = queue.Queue()
    completed = False
    forced_cleanup = False
    timed_out = False
    fatal_error = False

    print(f"==> {target_name}: {task}", flush=True)
    with log_path.open("w", encoding="utf-8") as log:
        process = subprocess.Popen(
            command,
            cwd=project_dir,
            env=env,
            stdout=subprocess.PIPE,
            stderr=subprocess.STDOUT,
            text=True,
            bufsize=1,
            start_new_session=True,
        )

        def reader() -> None:
            assert process.stdout is not None
            for line in process.stdout:
                lines.put(line)
            lines.put(None)

        threading.Thread(target=reader, daemon=True).start()
        deadline = time.monotonic() + timeout
        completion_grace: float | None = None
        fatal_grace: float | None = None
        try:
            while time.monotonic() < deadline:
                try:
                    line = lines.get(timeout=0.2)
                except queue.Empty:
                    line = ""
                if line is None:
                    break
                if line:
                    log.write(line)
                    log.flush()
                    if verbose or SUMMARY_RE.search(line):
                        sys.stdout.write(line)
                        sys.stdout.flush()
                    if COMPLETE_RE.search(line):
                        completed = True
                        completion_grace = time.monotonic() + 10
                    if FATAL_RE.search(line):
                        fatal_error = True
                        fatal_grace = time.monotonic() + 3
                if process.poll() is not None:
                    break
                if fatal_grace is not None and time.monotonic() >= fatal_grace:
                    break
                if completion_grace is not None and time.monotonic() >= completion_grace:
                    # Architectury's transformer can retain a launcher JVM after
                    # Minecraft's datagen main has completed. The unique marker
                    # keeps this cleanup exact and makes the gate non-leaking.
                    forced_cleanup = True
                    break
            else:
                timed_out = True
        except KeyboardInterrupt:
            cleanup(run_id)
            raise

        if process.poll() is None:
            cleanup(run_id)
        try:
            process.wait(timeout=2)
        except subprocess.TimeoutExpired:
            pass
        remaining = cleanup(run_id)

    exit_code = process.poll()
    success = completed and not timed_out and not remaining and (
        exit_code in (0, 143, -signal.SIGTERM, -signal.SIGKILL) or forced_cleanup
    )
    return {
        "task": task,
        "command": command,
        "completed": completed,
        "forced_launcher_cleanup": forced_cleanup,
        "timed_out": timed_out,
        "fatal_error": fatal_error,
        "exit_code": exit_code,
        "remaining_owned_pids": remaining,
        "log": str(log_path.relative_to(REPO_ROOT)),
        "success": success,
    }


def describe_drift(before: dict[str, str], after: dict[str, str]) -> dict[str, list[str]]:
    before_keys = set(before)
    after_keys = set(after)
    return {
        "added": sorted(after_keys - before_keys),
        "removed": sorted(before_keys - after_keys),
        "changed": sorted(key for key in before_keys & after_keys if before[key] != after[key]),
    }


def generated_file_matches(expected: Path, generated: Path, relative: str) -> bool:
    if not expected.is_file():
        return False
    expected_bytes = expected.read_bytes()
    generated_bytes = generated.read_bytes()
    if expected_bytes == generated_bytes:
        return True
    if not relative.endswith(".json") or "/recipe/" not in f"/{relative}":
        return False
    try:
        expected_json = json.loads(expected_bytes)
        generated_json = json.loads(generated_bytes)
    except (UnicodeDecodeError, json.JSONDecodeError):
        return False

    # Fabric's 1.21 codec omits the default cooking result count while
    # NeoForge's codec writes `count: 1`. Both decode to the same ItemStack.
    # Normalize that platform serialization difference; every other JSON
    # field remains part of the zero-drift comparison.
    for document in (expected_json, generated_json):
        result = document.get("result") if isinstance(document, dict) else None
        if isinstance(result, dict) and "id" in result and "count" not in result:
            result["count"] = 1
    return expected_json == generated_json


def audit_target(name: str, target: Target, args: argparse.Namespace) -> bool:
    project_dir = REPO_ROOT / target.directory
    resources = [project_dir / path for path in target.resource_roots]
    before = manifest(resources)
    output_dir = args.output_dir.resolve()
    output_dir.mkdir(parents=True, exist_ok=True)

    generated = [project_dir / path for path in target.generated_roots]
    for root in generated:
        if root.exists():
            shutil.rmtree(root)

    task_results = [
        run_task(name, target, task, args.timeout, args.verbose, output_dir)
        for task in target.tasks
    ]
    task_success = all(bool(result["success"]) for result in task_results)

    if target.compares_external_output:
        expected = project_dir / str(target.expected_root)
        generated_manifest = manifest(generated)
        mismatches = []
        for key, digest in generated_manifest.items():
            # There is one external root per current target. Strip its basename
            # prefix added by manifest() before looking in common resources.
            relative = key.split("/", 1)[1]
            expected_file = expected / relative
            generated_file = generated[0] / relative
            if not generated_file_matches(expected_file, generated_file, relative):
                mismatches.append(relative)
        drift = {"added": [], "removed": [], "changed": sorted(mismatches)}
        generated_count = len(generated_manifest)
        stable = generated_count > 0 and not mismatches
    else:
        after = manifest(resources)
        drift = describe_drift(before, after)
        generated_count = len(after)
        stable = not any(drift.values()) and generated_count > 0

    report = {
        "target": name,
        "tasks": task_results,
        "resource_roots": list(target.resource_roots),
        "generated_roots": list(target.generated_roots),
        "generated_file_count": generated_count,
        "drift": drift,
        "stable": stable,
        "success": task_success and stable,
    }
    report_path = output_dir / f"{name}.json"
    report_path.write_text(json.dumps(report, indent=2) + "\n", encoding="utf-8")

    if report["success"]:
        print(f"datagen audit: OK ({name}, {generated_count} files)")
        return True
    print(f"datagen audit: FAILED ({name})", file=sys.stderr)
    for category, paths in drift.items():
        if paths:
            print(f"  {category}: {len(paths)}", file=sys.stderr)
            for path in paths[:20]:
                print(f"    {path}", file=sys.stderr)
    return False


def main() -> int:
    args = parse_args()
    names = list(TARGETS) if args.target == "all" else [args.target]
    success = True
    for name in names:
        success = audit_target(name, TARGETS[name], args) and success
    return 0 if success else 1


if __name__ == "__main__":
    raise SystemExit(main())
