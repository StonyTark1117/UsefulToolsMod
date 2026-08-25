from __future__ import annotations

import json
import re
import unittest
from pathlib import Path


ROOT = Path(__file__).resolve().parents[1]


class ArchitecturyRemovalTests(unittest.TestCase):
    def test_1211_projects_are_independent_and_architectury_free(self) -> None:
        for loader in ("forge", "fabric", "neoforge"):
            root = ROOT / "1.21.1" / loader
            self.assertTrue((root / "gradlew").is_file(), loader)
            self.assertTrue((root / "settings.gradle").is_file(), loader)
            files = [root / "build.gradle", root / "gradle.properties", root / "settings.gradle"]
            files.extend((root / "src").rglob("*"))
            offenders = []
            for path in files:
                if path.is_file() and "architectury" in path.read_text(encoding="utf-8", errors="ignore").lower():
                    offenders.append(str(path.relative_to(ROOT)))
            self.assertEqual([], offenders, loader)
        self.assertFalse((ROOT / "1.21.1/common/build.gradle").exists())
        self.assertEqual([], list((ROOT / "1.21.1/common/src/main/java").rglob("*.java")))
        self.assertFalse((ROOT / "1.21.1/build.gradle").exists())

    def test_loader_metadata_has_no_architectury_requirement(self) -> None:
        fabric = json.loads((ROOT / "1.21.1/fabric/src/main/resources/fabric.mod.json").read_text())
        self.assertNotIn("architectury", fabric.get("depends", {}))
        for loader, metadata in (
            ("forge", "META-INF/mods.toml"),
            ("neoforge", "META-INF/neoforge.mods.toml"),
        ):
            text = (ROOT / "1.21.1" / loader / "src/main/resources" / metadata).read_text().lower()
            self.assertNotIn("architectury", text, loader)

    def test_120_architectury_is_compile_only(self) -> None:
        for version in ("1.20.1", "1.20.2"):
            for loader in ("forge", "neoforge"):
                build = (ROOT / version / loader / "build.gradle").read_text(encoding="utf-8")
                dependency_lines = [
                    line.strip() for line in build.splitlines()
                    if "dev.architectury:architectury-" in line and not line.lstrip().startswith("//")
                ]
                self.assertTrue(dependency_lines, f"{version}/{loader} REI compile contract")
                self.assertTrue(all(re.match(r"compileOnly\b", line) for line in dependency_lines),
                                (version, loader, dependency_lines))

    def test_ci_has_catalog_plus_16_independent_build_jobs(self) -> None:
        workflow = (ROOT / ".github/workflows/build-matrix.yml").read_text(encoding="utf-8")
        names = re.findall(r"- \{ name: ([^,]+), dir:", workflow)
        self.assertEqual(16, len(names))
        self.assertEqual(16, len(set(names)))
        for loader in ("forge", "fabric", "neoforge"):
            self.assertIn(f"1.21.1-{loader}", names)


if __name__ == "__main__":
    unittest.main()
