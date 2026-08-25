import importlib.util
from pathlib import Path
import unittest


ROOT = Path(__file__).resolve().parents[1]
SMOKE_PATH = ROOT / "scripts/smoke-server.py"
SPEC = importlib.util.spec_from_file_location("useful_tools_smoke_server", SMOKE_PATH)
assert SPEC is not None and SPEC.loader is not None
SMOKE = importlib.util.module_from_spec(SPEC)
SPEC.loader.exec_module(SMOKE)

MATRIX_PATH = ROOT / "scripts/audit-server-matrix.py"
MATRIX_SPEC = importlib.util.spec_from_file_location("useful_tools_server_matrix", MATRIX_PATH)
assert MATRIX_SPEC is not None and MATRIX_SPEC.loader is not None
MATRIX = importlib.util.module_from_spec(MATRIX_SPEC)
MATRIX_SPEC.loader.exec_module(MATRIX)


MODERN_BUILD_FILES = (
    "1.20.1/forge/build.gradle",
    "1.20.1/fabric/build.gradle",
    "1.20.1/neoforge/build.gradle",
    "1.20.2/forge/build.gradle",
    "1.20.2/fabric/build.gradle",
    "1.20.2/neoforge/build.gradle",
    "1.21.1/forge-standalone/build.gradle",
    "1.21.1/fabric/build.gradle",
    "1.21.1/neoforge/build.gradle",
    "26.1.2/forge/build.gradle",
    "26.1.2/fabric/build.gradle",
    "26.1.2/neoforge/build.gradle",
    "26.2/forge/build.gradle",
    "26.2/fabric/build.gradle",
    "26.2/neoforge/build.gradle",
)


class ServerSmokeContractTests(unittest.TestCase):
    def test_runner_covers_the_complete_release_matrix(self) -> None:
        expected = {
            "1.7.10-forge",
            *(f"{version}-{loader}" for version in ("1.20.1", "1.20.2", "1.21.1", "26.1.2", "26.2")
              for loader in ("forge", "fabric", "neoforge")),
        }
        self.assertEqual(set(SMOKE.TARGETS), expected)
        self.assertEqual(set(MATRIX.TARGETS), expected)

    def test_ansi_colored_readiness_is_recognized(self) -> None:
        line = "\x1b[32m[Server thread/INFO] Done (3.141s)! For help, type help\x1b[0m"
        clean = SMOKE.ANSI_RE.sub("", line)
        self.assertIsNotNone(SMOKE.READY_RE.search(clean))

    def test_gradle_sigint_failure_is_not_a_minecraft_crash(self) -> None:
        self.assertIsNone(SMOKE.CRASH_RE.search("BUILD FAILED in 23s"))
        self.assertIsNotNone(
            SMOKE.CRASH_RE.search("Encountered an unexpected exception")
        )
        self.assertIsNotNone(
            SMOKE.CRASH_RE.search("Failed to start the minecraft server")
        )

    def test_every_modern_run_accepts_an_isolated_game_directory(self) -> None:
        missing = [
            relative
            for relative in MODERN_BUILD_FILES
            if "usefulToolsSmokeGameDir" not in (ROOT / relative).read_text(encoding="utf-8")
        ]
        self.assertEqual(missing, [])

    def test_init_script_only_mutates_run_server_tasks(self) -> None:
        script = (ROOT / "scripts/smoke-isolated.gradle").read_text(encoding="utf-8")
        self.assertIn("'runServer', 'runGameTestServer', 'runClient'", script)
        self.assertIn("usefulToolsSmokeGameDir", script)

    def test_runner_supports_explicit_upgrade_world_directories(self) -> None:
        source = SMOKE_PATH.read_text(encoding="utf-8")
        self.assertIn('"--game-dir"', source)
        self.assertIn("external_game_dir", source)

    def test_matrix_report_requires_game_layer_shutdown_evidence(self) -> None:
        report = {
            "ready": True,
            "clean_shutdown": True,
            "port_free_after": True,
            "failure": None,
            "crash_marker": None,
            "remaining_owned_pids": [],
            "saved_dimension_lines": ["overworld", "the_nether", "the_end"],
        }
        self.assertTrue(MATRIX.report_is_complete(report))
        report["saved_dimension_lines"] = ["overworld", "the_nether"]
        self.assertFalse(MATRIX.report_is_complete(report))

    def test_forge_26_2_does_not_load_the_26_1_wthit_runtime(self) -> None:
        build = (ROOT / "26.2/forge/build.gradle").read_text(encoding="utf-8")
        self.assertIn("exclude 'com/stonytark/usefultoolsmod/compat/wthit/**'", build)
        self.assertNotIn('runtimeOnly "mcp.mobius.waila:wthit:', build)
        self.assertFalse((ROOT / "26.2/forge/src/main/resources/waila_plugins.json").exists())


if __name__ == "__main__":
    unittest.main()
