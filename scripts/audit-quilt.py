#!/usr/bin/env python3
"""Run an assembled Fabric jar unchanged in an existing Quilt server install."""

from __future__ import annotations

import argparse
import hashlib
import json
import os
from pathlib import Path
import re
import signal
import socket
import subprocess
import sys
import time


ROOT = Path(__file__).resolve().parent.parent
JAVA_HOMES = {
    "1.20.1": "/usr/lib/jvm/java-17-openjdk",
    "1.20.2": "/usr/lib/jvm/java-17-openjdk",
    "1.21.1": "/usr/lib/jvm/java-21-openjdk",
    "26.1.2": "/home/braydon/.gradle/jdks/eclipse_adoptium-25-amd64-linux.2",
    "26.2": "/home/braydon/.gradle/jdks/eclipse_adoptium-25-amd64-linux.2",
}
FATAL = re.compile(r"Minecraft has crashed|Could not execute entrypoint|Mod resolution encountered|BUILD FAILED", re.I)
VERSIONS = tuple(JAVA_HOMES)


def sha256(path: Path) -> str:
    digest = hashlib.sha256()
    with path.open("rb") as stream:
        for chunk in iter(lambda: stream.read(1024 * 1024), b""):
            digest.update(chunk)
    return digest.hexdigest()


def write_summary(output_dir: Path) -> None:
    reports = []
    for version in VERSIONS:
        path = output_dir / f"{version}.json"
        if path.is_file():
            reports.append(json.loads(path.read_text(encoding="utf-8")))
    failed = [report["version"] for report in reports if not report.get("success")]
    summary = {
        "loader": "Quilt Loader 0.30.0",
        "expected": len(VERSIONS),
        "completed": len(reports),
        "passed": len(reports) - len(failed),
        "failed": failed,
        "success": len(reports) == len(VERSIONS) and not failed,
        "reports": [f"{report['version']}.json" for report in reports],
    }
    (output_dir / "matrix-summary.json").write_text(
        json.dumps(summary, indent=2) + "\n", encoding="utf-8"
    )


def port_free() -> bool:
    with socket.socket() as sock:
        sock.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 0)
        try:
            sock.bind(("127.0.0.1", 25565))
            return True
        except OSError:
            return False


def parse_args() -> argparse.Namespace:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("version", choices=JAVA_HOMES)
    parser.add_argument("server_root", type=Path)
    parser.add_argument("--timeout", type=int, default=180)
    parser.add_argument("--output-dir", type=Path, default=ROOT / "validation/quilt")
    return parser.parse_args()


def main() -> int:
    args = parse_args()
    args.output_dir = args.output_dir.resolve()
    server_root = args.server_root.resolve()
    launcher = server_root / "quilt-server-launch.jar"
    mods = server_root / "mods"
    source = ROOT / "release/2.3.0" / f"usefultoolsmod-2.3.0-{args.version}-fabric.jar"
    if not launcher.is_file() or not mods.is_dir() or not source.is_file():
        print("missing Quilt launcher, mods directory, or assembled Fabric jar", file=sys.stderr)
        return 4
    if not port_free():
        print("refusing to start: TCP 25565 is occupied", file=sys.stderr)
        return 4

    for old in mods.glob("usefultoolsmod*.jar"):
        old.unlink()
    destination = mods / source.name
    destination.write_bytes(source.read_bytes())
    source_sha256 = sha256(source)
    if sha256(destination) != source_sha256:
        print("assembled jar changed while copying into Quilt install", file=sys.stderr)
        return 4

    args.output_dir.mkdir(parents=True, exist_ok=True)
    log_path = args.output_dir / f"{args.version}.log"
    report_path = args.output_dir / f"{args.version}.json"
    java_home = JAVA_HOMES[args.version]
    env = {**os.environ, "JAVA_HOME": java_home, "PATH": f"{java_home}/bin:{os.environ.get('PATH', '')}"}
    process = subprocess.Popen(
        [str(Path(java_home) / "bin/java"), "-jar", str(launcher), "nogui"],
        cwd=server_root, env=env, stdin=subprocess.PIPE, stdout=subprocess.PIPE,
        stderr=subprocess.STDOUT, text=True, start_new_session=True,
    )
    lines: list[str] = []
    ready = False
    stop_sent = False
    deadline = time.monotonic() + args.timeout
    try:
        assert process.stdout is not None and process.stdin is not None
        while time.monotonic() < deadline:
            line = process.stdout.readline()
            if line:
                lines.append(line)
                if "Done (" in line and not stop_sent:
                    ready = True
                    process.stdin.write("stop\n")
                    process.stdin.flush()
                    stop_sent = True
            elif process.poll() is not None:
                break
        if process.poll() is None:
            os.killpg(process.pid, signal.SIGTERM)
            try:
                process.wait(timeout=10)
            except subprocess.TimeoutExpired:
                os.killpg(process.pid, signal.SIGKILL)
                process.wait(timeout=10)
        lines.extend(process.stdout.readlines())
    finally:
        if process.poll() is None:
            os.killpg(process.pid, signal.SIGKILL)
            process.wait()

    output = "".join(lines)
    log_path.write_text(output, encoding="utf-8")
    saves = len(re.findall(r"Saving chunks for level", output))
    success = (
        ready and stop_sent and process.returncode == 0 and saves >= 3
        and "quilt_loader" in output and "0.30.0" in output
        and "Useful Tools Mod" in output
        and not FATAL.search(output) and port_free()
    )
    report = {
        "version": args.version,
        "assembled_jar": str(source.relative_to(ROOT)),
        "sha256": source_sha256,
        "server_root": str(server_root),
        "ready": ready,
        "stop_sent": stop_sent,
        "saved_dimension_lines": saves,
        "exit_code": process.returncode,
        "port_free_after": port_free(),
        "log": str(log_path.relative_to(ROOT)),
        "success": success,
    }
    report_path.write_text(json.dumps(report, indent=2) + "\n", encoding="utf-8")
    write_summary(args.output_dir)
    print(f"Quilt audit: {'OK' if success else 'FAILED'} ({args.version})")
    return 0 if success else 1


if __name__ == "__main__":
    raise SystemExit(main())
