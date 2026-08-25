import importlib.util
from pathlib import Path
import re
import sys
import unittest


ROOT = Path(__file__).resolve().parents[1]
RUNNER_PATH = ROOT / "scripts/audit-integrations.py"
SPEC = importlib.util.spec_from_file_location("useful_tools_integrations", RUNNER_PATH)
assert SPEC is not None and SPEC.loader is not None
RUNNER = importlib.util.module_from_spec(SPEC)
sys.modules[SPEC.name] = RUNNER
SPEC.loader.exec_module(RUNNER)


class IntegrationProfileTests(unittest.TestCase):
    def test_absent_profiles_cover_every_modern_artifact(self) -> None:
        expected = {
            f"{version}-{loader}"
            for version in ("1.20.1", "1.20.2", "1.21.1", "26.1.2", "26.2")
            for loader in ("forge", "fabric", "neoforge")
        }
        self.assertEqual(set(RUNNER.MODERN_TARGETS), expected)

    def test_present_profiles_are_unique_and_opt_in(self) -> None:
        names = [profile.name for profile in RUNNER.PRESENT_PROFILES]
        self.assertEqual(len(names), len(set(names)))
        for profile in RUNNER.PRESENT_PROFILES:
            self.assertRegex(profile.gradle_property, r"^usefulTools[A-Za-z]+Runtime=true$")

    def test_optional_viewer_runtimes_are_not_unconditional(self) -> None:
        build_files = tuple(
            ROOT / directory / "build.gradle"
            for directory in (
                "1.20.1/forge", "1.20.1/fabric", "1.20.1/neoforge",
                "1.20.2/forge", "1.20.2/fabric", "1.20.2/neoforge",
                "1.21.1/forge-standalone", "1.21.1/fabric", "1.21.1/neoforge",
                "26.1.2/forge", "26.1.2/fabric", "26.1.2/neoforge",
                "26.2/forge", "26.2/fabric", "26.2/neoforge",
            )
        )
        unconditional = []
        for build_file in build_files:
            text = build_file.read_text(encoding="utf-8")
            for line in text.splitlines():
                if line.lstrip().startswith("//"):
                    continue
                if re.search(r"(?:modLocalRuntime|runtimeOnly).*(?:jei|wthit|jade|RoughlyEnoughItems|just-enough-resources)", line, re.I):
                    # Every runtime declaration must sit inside a nearby opt-in block.
                    offset = text.index(line)
                    if "usefulTools" not in text[max(0, offset - 400):offset]:
                        unconditional.append(f"{build_file.relative_to(ROOT)}: {line.strip()}")
        self.assertEqual(unconditional, [])


if __name__ == "__main__":
    unittest.main()
