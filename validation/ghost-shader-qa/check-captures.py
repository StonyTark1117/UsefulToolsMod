#!/usr/bin/env python3
"""Check the saved Ghost/Wraith visual smoke evidence.

Requires ImageMagick, as does scripts/smoke-client.py. The fixed crop is the
face in the 1280x720 1.21.1 NeoForge spectral smoke scene. A human should also
review the screenshots for shape, eyes, mouth, and unwanted bloom.
"""

from __future__ import annotations

import json
from pathlib import Path
import subprocess


ROOT = Path(__file__).resolve().parent
TARGET = "1.21.1-neoforge"
SCENARIOS = ("vanilla-cutout", "vanilla-adaptive", "bsl-cutout", "bsl-adaptive")


def face_stats(path: Path) -> tuple[float, float]:
    result = subprocess.run(
        ["magick", str(path), "-crop", "35x30+620+250", "+repage",
         "-colorspace", "Gray", "-format", "%[mean] %[standard-deviation]", "info:"],
        check=True, text=True, capture_output=True,
    )
    return tuple(map(float, result.stdout.split()))  # type: ignore[return-value]


def main() -> None:
    metrics = {}
    for scenario in SCENARIOS:
        directory = ROOT / scenario
        report = json.loads((directory / f"{TARGET}.json").read_text())
        assert report["success"], f"{scenario}: smoke failed: {report['failure']}"
        assert report["spectral_render_commands_succeeded"], f"{scenario}: scene not staged"
        assert (report["shader_pack"] is not None) == scenario.startswith("bsl"), scenario
        assert report["shader_pack_loaded"] is (True if scenario.startswith("bsl") else None), (
            f"{scenario}: shader load state does not match the scenario"
        )
        for entity in ("ghost", "wraith"):
            for lighting in ("day", "night"):
                image = directory / f"{TARGET}-{entity}-{lighting}.png"
                assert image.is_file() and image.stat().st_size > 5_000, image
            metrics[scenario, entity] = face_stats(
                directory / f"{TARGET}-{entity}-night.png"
            )

    for entity in ("ghost", "wraith"):
        for family in ("vanilla", "bsl"):
            base_mean, base_detail = metrics[f"{family}-cutout", entity]
            fixed_mean, fixed_detail = metrics[f"{family}-adaptive", entity]
            assert fixed_mean > 1.5 * base_mean, (
                f"{family} {entity}: night face did not gain enough visibility"
            )
            assert fixed_detail > 1.3 * base_detail, (
                f"{family} {entity}: night face lost feature contrast"
            )
            print(
                f"{family:7} {entity:6} face mean {base_mean:.0f} -> {fixed_mean:.0f}; "
                f"detail {base_detail:.0f} -> {fixed_detail:.0f}"
            )
    print("spectral capture check: OK")


if __name__ == "__main__":
    main()
