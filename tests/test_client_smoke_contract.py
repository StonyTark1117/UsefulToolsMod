import importlib.util
from pathlib import Path
import sys
import unittest


ROOT = Path(__file__).resolve().parents[1]
RUNNER_PATH = ROOT / "scripts/smoke-client.py"
SPEC = importlib.util.spec_from_file_location("useful_tools_client_smoke", RUNNER_PATH)
assert SPEC is not None and SPEC.loader is not None
RUNNER = importlib.util.module_from_spec(SPEC)
sys.modules[SPEC.name] = RUNNER
SPEC.loader.exec_module(RUNNER)


class ClientSmokeContractTests(unittest.TestCase):
    def test_runner_covers_every_release_artifact(self) -> None:
        expected = {
            f"{version}-{loader}"
            for version in ("1.20.1", "1.20.2", "1.21.1", "26.1.2", "26.2")
            for loader in ("forge", "fabric", "neoforge")
        }
        expected.add("1.7.10-forge")
        self.assertEqual(set(RUNNER.TARGETS), expected)

    def test_client_gate_requires_config_world_and_clean_exit(self) -> None:
        source = RUNNER_PATH.read_text(encoding="utf-8")
        self.assertIn("config_opened and joined and clean_exit", source)
        self.assertIn("remaining_owned_pids", source)
        self.assertIn("USEFUL_TOOLS_CLIENT_RUN_ID", source)

    def test_fabric_config_smoke_opts_into_mod_menu(self) -> None:
        source = RUNNER_PATH.read_text(encoding="utf-8")
        self.assertIn("usefulToolsModMenuRuntime=true", source)

    def test_client_runner_supports_optional_visual_profiles(self) -> None:
        source = RUNNER_PATH.read_text(encoding="utf-8")
        self.assertIn('"--gradle-property"', source)
        self.assertIn('"--inventory-probe"', source)
        self.assertIn('"--inventory-search"', source)
        self.assertIn("inventory_probe_requested", source)
        self.assertIn('"--visual-showcase"', source)
        self.assertIn("visual_showcase_commands_succeeded", source)
        self.assertIn("mining_charge_unlit_screenshot", source)
        self.assertIn("mining_charge_lit_screenshot", source)
        self.assertIn("mining_charge_glass_screenshot", source)
        self.assertIn("ectoplasm_lantern_screenshots", source)
        self.assertIn("wraith_wthit_state_screenshots", source)
        self.assertIn("wraith_wthit_screenshot", source)

    def test_classic_client_uses_java_8_and_native_config_screen(self) -> None:
        target = RUNNER.TARGETS["1.7.10-forge"]
        self.assertEqual(target.java_home, "/usr/lib/jvm/java-17-openjdk")
        self.assertEqual(target.runtime_java_home, "/usr/lib/jvm/java-8-openjdk")
        self.assertEqual(target.loader, "classic")


if __name__ == "__main__":
    unittest.main()
