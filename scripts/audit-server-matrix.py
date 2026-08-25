#!/usr/bin/env python3
"""Run and aggregate the complete Useful Tools dedicated-server matrix."""

from __future__ import annotations

import argparse
import json
from pathlib import Path
import subprocess
import sys
import time


ROOT = Path(__file__).resolve().parent.parent
SMOKE_RUNNER = ROOT / "scripts/smoke-server.py"
TARGETS = (
    "1.7.10-forge",
    "1.20.1-forge",
    "1.20.1-fabric",
    "1.20.1-neoforge",
    "1.20.2-forge",
    "1.20.2-fabric",
    "1.20.2-neoforge",
    "1.21.1-forge",
    "1.21.1-fabric",
    "1.21.1-neoforge",
    "26.1.2-forge",
    "26.1.2-fabric",
    "26.1.2-neoforge",
    "26.2-forge",
    "26.2-fabric",
    "26.2-neoforge",
)


def parse_args() -> argparse.Namespace:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("targets", nargs="*", choices=TARGETS, default=list(TARGETS))
    parser.add_argument("--startup-timeout", type=int, default=300)
    parser.add_argument("--shutdown-timeout", type=int, default=120)
    parser.add_argument(
        "--output-dir",
        type=Path,
        default=ROOT / "validation/server-smoke/final-matrix",
    )
    parser.add_argument("--stop-on-failure", action="store_true")
    parser.add_argument(
        "--verify-existing",
        action="store_true",
        help="aggregate existing per-target JSON reports without launching servers",
    )
    return parser.parse_args()


def report_is_complete(report: dict[str, object]) -> bool:
    return bool(
        report.get("ready")
        and report.get("clean_shutdown")
        and report.get("port_free_after")
        and not report.get("failure")
        and not report.get("crash_marker")
        and not report.get("remaining_owned_pids")
        and len(report.get("saved_dimension_lines", [])) >= 3
    )


def main() -> int:
    args = parse_args()
    selected = args.targets or list(TARGETS)
    output_dir = args.output_dir.resolve()
    output_dir.mkdir(parents=True, exist_ok=True)
    results: list[dict[str, object]] = []
    started = time.time()

    for target in selected:
        report_path = output_dir / f"{target}.json"
        runner_exit_code = 0
        if not args.verify_existing:
            command = [
                sys.executable,
                str(SMOKE_RUNNER),
                target,
                "--startup-timeout",
                str(args.startup_timeout),
                "--shutdown-timeout",
                str(args.shutdown_timeout),
                "--output-dir",
                str(output_dir),
            ]
            if target != "1.7.10-forge":
                command.append("--isolated-game-dir")
            runner_exit_code = subprocess.run(command, cwd=ROOT, check=False).returncode
        report = (
            json.loads(report_path.read_text(encoding="utf-8"))
            if report_path.is_file()
            else {"target": target, "failure": "smoke runner did not create a report"}
        )
        report["runner_exit_code"] = runner_exit_code
        report["matrix_pass"] = runner_exit_code == 0 and report_is_complete(report)
        results.append(report)
        if args.stop_on_failure and not report["matrix_pass"]:
            break

    summary = {
        "started_at_epoch": started,
        "finished_at_epoch": time.time(),
        "expected_targets": list(selected),
        "passed_targets": [report["target"] for report in results if report["matrix_pass"]],
        "failed_targets": [report["target"] for report in results if not report["matrix_pass"]],
        "complete": len(results) == len(selected) and all(report["matrix_pass"] for report in results),
        "reports": [str((output_dir / f"{report['target']}.json").relative_to(ROOT)) for report in results],
    }
    summary_path = output_dir / "matrix-summary.json"
    summary_path.write_text(json.dumps(summary, indent=2) + "\n", encoding="utf-8")
    print(
        f"server matrix: {'OK' if summary['complete'] else 'FAILED'} "
        f"({len(summary['passed_targets'])}/{len(selected)} targets; {summary_path.relative_to(ROOT)})"
    )
    return 0 if summary["complete"] else 1


if __name__ == "__main__":
    raise SystemExit(main())
