#!/usr/bin/env python3
"""Fail on project-source deprecation, unchecked, or missing-annotation warnings.

Gradle and game launchers may still report their own lifecycle/native-access
notices. This gate enables javac's actionable source diagnostics and rejects
lowercase ``warning:`` output from every active modern compilation unit.
"""

from __future__ import annotations

import argparse
from dataclasses import dataclass
import os
from pathlib import Path
import subprocess
import sys
import tempfile


ROOT = Path(__file__).resolve().parent.parent


@dataclass(frozen=True)
class Target:
    directory: str
    java_home: str
    tasks: tuple[str, ...] = ("compileJava",)


TARGETS = {
    "1.20.1-forge": Target("1.20.1/forge", "/usr/lib/jvm/java-17-openjdk"),
    "1.20.1-fabric": Target("1.20.1/fabric", "/usr/lib/jvm/java-21-openjdk"),
    "1.20.1-neoforge": Target("1.20.1/neoforge", "/usr/lib/jvm/java-17-openjdk"),
    "1.20.2-forge": Target("1.20.2/forge", "/usr/lib/jvm/java-17-openjdk"),
    "1.20.2-fabric": Target("1.20.2/fabric", "/usr/lib/jvm/java-21-openjdk"),
    "1.20.2-neoforge": Target("1.20.2/neoforge", "/usr/lib/jvm/java-17-openjdk"),
    "1.21.1-forge": Target("1.21.1/forge", "/usr/lib/jvm/java-21-openjdk"),
    "1.21.1-fabric": Target("1.21.1/fabric", "/usr/lib/jvm/java-21-openjdk"),
    "1.21.1-neoforge": Target("1.21.1/neoforge", "/usr/lib/jvm/java-21-openjdk"),
    "26.1.2-forge": Target("26.1.2/forge", "/usr/lib/jvm/java-21-openjdk"),
    "26.1.2-fabric": Target("26.1.2/fabric", "/usr/lib/jvm/java-26-openjdk"),
    "26.1.2-neoforge": Target("26.1.2/neoforge", "/usr/lib/jvm/java-21-openjdk"),
    "26.2-forge": Target("26.2/forge", "/usr/lib/jvm/java-21-openjdk"),
    "26.2-fabric": Target("26.2/fabric", "/usr/lib/jvm/java-26-openjdk"),
    "26.2-neoforge": Target("26.2/neoforge", "/usr/lib/jvm/java-21-openjdk"),
}


INIT_SCRIPT = """allprojects {
    tasks.withType(JavaCompile).configureEach {
        options.compilerArgs += ['-Xlint:deprecation,unchecked,-classfile']
    }
}
"""


def run_target(name: str, target: Target, init_script: Path) -> bool:
    env = os.environ.copy()
    env["JAVA_HOME"] = target.java_home
    env["PATH"] = f"{target.java_home}/bin:{env.get('PATH', '')}"
    command = [
        "./gradlew",
        "--no-daemon",
        "--max-workers=1",
        "-I",
        str(init_script),
        *target.tasks,
        "--rerun-tasks",
    ]
    result = subprocess.run(
        command,
        cwd=ROOT / target.directory,
        env=env,
        stdout=subprocess.PIPE,
        stderr=subprocess.STDOUT,
        text=True,
        check=False,
    )
    warnings = [line for line in result.stdout.splitlines() if "warning:" in line]
    if result.returncode or warnings:
        print(f"java warning audit: FAILED ({name})", file=sys.stderr)
        if warnings:
            print("\n".join(warnings), file=sys.stderr)
        if result.returncode:
            print(result.stdout, file=sys.stderr)
        return False
    print(f"java warning audit: OK ({name})", flush=True)
    return True


def parse_args() -> argparse.Namespace:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("target", nargs="?", choices=(*TARGETS, "all"), default="all")
    return parser.parse_args()


def main() -> int:
    args = parse_args()
    selected = TARGETS.items() if args.target == "all" else ((args.target, TARGETS[args.target]),)
    with tempfile.TemporaryDirectory(prefix="usefultools-lint-") as temporary:
        init_script = Path(temporary) / "warnings.gradle"
        init_script.write_text(INIT_SCRIPT, encoding="utf-8")
        return 0 if all(run_target(name, target, init_script) for name, target in selected) else 1


if __name__ == "__main__":
    raise SystemExit(main())
