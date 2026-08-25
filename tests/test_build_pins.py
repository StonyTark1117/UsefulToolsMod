import re
import unittest
from pathlib import Path


ROOT = Path(__file__).resolve().parents[1]
ACTIVE_ROOTS = ("1.7.10", "1.20.1", "1.20.2", "1.21.1", "26.1.2", "26.2")
BUILD_FILE_NAMES = {
    "build.gradle",
    "settings.gradle",
    "gradle.properties",
    "gradle-wrapper.properties",
}


def active_build_files():
    for root_name in ACTIVE_ROOTS:
        for path in (ROOT / root_name).rglob("*"):
            if not path.is_file() or path.name not in BUILD_FILE_NAMES:
                continue
            relative_parts = path.relative_to(ROOT).parts
            if any(part in {"build", "run", "runs", ".gradle"} for part in relative_parts):
                continue
            yield path


class BuildPinTests(unittest.TestCase):
    def test_active_builds_have_no_snapshots_or_placeholders(self):
        offenders = []
        for path in active_build_files():
            text = "\n".join(
                line for line in path.read_text(encoding="utf-8").splitlines()
                if not line.lstrip().startswith(("#", "//"))
            )
            if re.search(r"(?i)SNAPSHOT|PLACEHOLDER|latest\.(?:release|integration)", text):
                offenders.append(str(path.relative_to(ROOT)))
        self.assertEqual([], offenders)

    def test_dependency_notation_has_no_wildcard_version(self):
        # SemVer build metadata and upstream exact versions such as
        # 0.92.7+1.20.1 are valid. Only a terminal '+' selector is forbidden.
        wildcard = re.compile(r"['\"][^'\"\n]+:[^'\"\n]+:[^'\"\n]*\+['\"]")
        offenders = []
        for path in active_build_files():
            if path.suffix != ".gradle":
                continue
            for line_number, line in enumerate(path.read_text(encoding="utf-8").splitlines(), 1):
                if wildcard.search(line) and not line.lstrip().startswith("//"):
                    offenders.append(f"{path.relative_to(ROOT)}:{line_number}")
        self.assertEqual([], offenders)

    def test_wrappers_use_exact_distribution_versions(self):
        wildcard = re.compile(r"gradle-(?:latest|\d+(?:\.\d+)*-SNAPSHOT)-")
        offenders = []
        for path in active_build_files():
            if path.name != "gradle-wrapper.properties":
                continue
            text = path.read_text(encoding="utf-8")
            if wildcard.search(text):
                offenders.append(str(path.relative_to(ROOT)))
        self.assertEqual([], offenders)


if __name__ == "__main__":
    unittest.main()
