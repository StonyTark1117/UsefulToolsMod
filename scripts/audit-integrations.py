#!/usr/bin/env python3
"""Prove optional integrations are absent-safe and present-safe on supported targets."""

from __future__ import annotations

import argparse
from dataclasses import dataclass
import json
from pathlib import Path
import subprocess
import sys


ROOT = Path(__file__).resolve().parent.parent


@dataclass(frozen=True)
class Profile:
    target: str
    integration: str
    gradle_property: str | None = None
    mod_urls: tuple[str, ...] = ()

    @property
    def name(self) -> str:
        return f"{self.target}-{self.integration}"


MODERN_TARGETS = tuple(
    f"{version}-{loader}"
    for version in ("1.20.1", "1.20.2", "1.21.1", "26.1.2", "26.2")
    for loader in ("forge", "fabric", "neoforge")
)

PRESENT_PROFILES = (
    Profile("1.20.1-fabric", "createoreexcavation", mod_urls=("https://cdn.modrinth.com/data/ResbpANg/versions/sHml8IJO/createoreexcavation-fabric-1.20-1.6.1.jar", "https://cdn.modrinth.com/data/Xbc0uyRg/versions/HAqwA6X1/create-fabric-6.0.8.1%2Bbuild.1744-mc1.20.1.jar")),
    Profile("1.21.1-neoforge", "createoreexcavation", mod_urls=("https://cdn.modrinth.com/data/ResbpANg/versions/tivxiPTo/createoreexcavation-1.21-1.6.8.jar", "https://cdn.modrinth.com/data/LNytGWDc/versions/UjX6dr61/create-1.21.1-6.0.10.jar")),
    Profile("1.20.1-forge", "jei", "usefulToolsJeiRuntime=true"),
    Profile("1.20.1-fabric", "jei", "usefulToolsJeiRuntime=true"),
    Profile("1.20.1-fabric", "wthit", "usefulToolsWthitRuntime=true"),
    Profile("1.20.1-neoforge", "jade", "usefulToolsJadeRuntime=true"),
    Profile("1.20.2-forge", "jei", "usefulToolsJeiRuntime=true"),
    Profile("1.20.2-fabric", "jei", "usefulToolsJeiRuntime=true"),
    Profile("1.20.2-fabric", "wthit", "usefulToolsWthitRuntime=true"),
    Profile("1.20.2-neoforge", "jade", "usefulToolsJadeRuntime=true"),
    Profile("1.20.2-neoforge", "rei", "usefulToolsReiRuntime=true"),
    Profile("1.21.1-forge", "jei", "usefulToolsJeiRuntime=true"),
    Profile("1.21.1-forge", "wthit", "usefulToolsWthitRuntime=true"),
    Profile("1.21.1-fabric", "jei", "usefulToolsJeiRuntime=true"),
    Profile("1.21.1-fabric", "wthit", "usefulToolsWthitRuntime=true"),
    Profile("1.21.1-neoforge", "jei", "usefulToolsJeiRuntime=true"),
    Profile("1.21.1-neoforge", "wthit", "usefulToolsWthitRuntime=true"),
    Profile("1.21.1-neoforge", "jer", "usefulToolsJerRuntime=true"),
    Profile("26.1.2-forge", "wthit", "usefulToolsWthitRuntime=true"),
    Profile("26.1.2-fabric", "jei", "usefulToolsJeiRuntime=true"),
    Profile("26.1.2-fabric", "wthit", "usefulToolsWthitRuntime=true"),
    Profile("26.1.2-neoforge", "jei", "usefulToolsJeiRuntime=true"),
    Profile("26.1.2-neoforge", "wthit", "usefulToolsWthitRuntime=true"),
    Profile("26.2-fabric", "jei", "usefulToolsJeiRuntime=true"),
    Profile("26.2-fabric", "wthit", "usefulToolsWthitRuntime=true"),
    Profile("26.2-neoforge", "jei", "usefulToolsJeiRuntime=true"),
    Profile("26.2-neoforge", "wthit", "usefulToolsWthitRuntime=true"),
)


def parse_args() -> argparse.Namespace:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument(
        "profile",
        choices=("all", "absent", "present", "verify-existing", *(p.name for p in PRESENT_PROFILES)),
    )
    parser.add_argument("--startup-timeout", type=int, default=240)
    parser.add_argument("--shutdown-timeout", type=int, default=180)
    parser.add_argument(
        "--output-dir", type=Path, default=ROOT / "validation/integrations/matrix"
    )
    return parser.parse_args()


def profiles_for(selection: str) -> list[Profile]:
    absent = [Profile(target, "absent") for target in MODERN_TARGETS]
    if selection == "all":
        return [*absent, *PRESENT_PROFILES]
    if selection == "absent":
        return absent
    if selection == "present":
        return list(PRESENT_PROFILES)
    return [next(profile for profile in PRESENT_PROFILES if profile.name == selection)]


def report_passes(report: dict[str, object]) -> bool:
    return (
        report.get("ready") is True
        and report.get("clean_shutdown") is True
        and report.get("port_free_after") is True
        and report.get("failure") is None
        and report.get("crash_marker") is None
        and not report.get("remaining_owned_pids")
        and len(report.get("saved_dimension_lines", [])) >= 3
    )


def run(profile: Profile, args: argparse.Namespace) -> dict[str, object]:
    profile_dir = args.output_dir.resolve() / profile.name
    command = [
        sys.executable,
        str(ROOT / "scripts/smoke-server.py"),
        profile.target,
        "--isolated-game-dir",
        "--startup-timeout",
        str(args.startup_timeout),
        "--shutdown-timeout",
        str(args.shutdown_timeout),
        "--output-dir",
        str(profile_dir),
    ]
    if profile.gradle_property:
        command.extend(("--gradle-property", profile.gradle_property))
    for url in profile.mod_urls:
        command.extend(("--extra-mod-url", url))
    print(f"==> integration profile: {profile.name}", flush=True)
    completed = subprocess.run(command, cwd=ROOT, check=False)
    report_path = profile_dir / f"{profile.target}.json"
    report = json.loads(report_path.read_text(encoding="utf-8")) if report_path.exists() else {}
    success = completed.returncode == 0 and report_passes(report)
    return {
        "name": profile.name,
        "target": profile.target,
        "integration": profile.integration,
        "gradle_property": profile.gradle_property,
        "exit_code": completed.returncode,
        "report": str(report_path.relative_to(ROOT)) if report_path.exists() else None,
        "success": success,
    }


def main() -> int:
    args = parse_args()
    if args.profile == "verify-existing":
        selected = [
            *(Profile(target, "absent") for target in MODERN_TARGETS),
            *PRESENT_PROFILES,
        ]
        results = []
        for profile in selected:
            report_path = args.output_dir.resolve() / profile.name / f"{profile.target}.json"
            report = json.loads(report_path.read_text(encoding="utf-8")) if report_path.exists() else {}
            results.append(
                {
                    "name": profile.name,
                    "target": profile.target,
                    "integration": profile.integration,
                    "gradle_property": profile.gradle_property,
                    "exit_code": None,
                    "report": str(report_path.relative_to(ROOT)) if report_path.exists() else None,
                    "success": report_passes(report),
                }
            )
    else:
        selected = profiles_for(args.profile)
        results = [run(profile, args) for profile in selected]
    summary = {
        "profile_count": len(results),
        "passed_profiles": sum(result["success"] is True for result in results),
        "failed_profiles": [result["name"] for result in results if not result["success"]],
        "success": all(result["success"] for result in results),
        "results": results,
    }
    args.output_dir.mkdir(parents=True, exist_ok=True)
    (args.output_dir / f"{args.profile}-summary.json").write_text(
        json.dumps(summary, indent=2) + "\n", encoding="utf-8"
    )
    print(f"integration matrix: {summary['passed_profiles']}/{summary['profile_count']} passed")
    return 0 if summary["success"] else 1


if __name__ == "__main__":
    raise SystemExit(main())
