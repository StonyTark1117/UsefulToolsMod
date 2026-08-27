import importlib.util
from pathlib import Path
import sys
import unittest


ROOT = Path(__file__).resolve().parents[1]
RUNNER_PATH = ROOT / "scripts/audit-gametests.py"
SPEC = importlib.util.spec_from_file_location("useful_tools_gametests", RUNNER_PATH)
assert SPEC is not None and SPEC.loader is not None
RUNNER = importlib.util.module_from_spec(SPEC)
sys.modules[SPEC.name] = RUNNER
SPEC.loader.exec_module(RUNNER)


class GameTestContractTests(unittest.TestCase):
    def test_runner_covers_every_modern_release_artifact(self) -> None:
        expected = {
            f"{version}-{loader}"
            for version in ("1.20.1", "1.20.2", "1.21.1", "26.1.2", "26.2")
            for loader in ("forge", "fabric", "neoforge")
        }
        self.assertEqual(set(RUNNER.TARGETS), expected)

    def test_modern_26_targets_include_platform_sentinel(self) -> None:
        for name, target in RUNNER.TARGETS.items():
            expected = 8 if name.startswith("26.") else 7
            self.assertEqual(target.required_tests, expected, name)

    def test_forge_26_registers_functions_and_instances(self) -> None:
        for version in ("26.1.2", "26.2"):
            source = (
                ROOT
                / version
                / "forge/src/main/java/com/stonytark/usefultoolsmod/UsefultoolsMod.java"
            ).read_text(encoding="utf-8")
            self.assertIn("ForgeGameTestHooks.gatherTests", source, version)
            self.assertIn("Registries.TEST_FUNCTION", source, version)
            instances = ROOT / version / "forge/src/main/resources/data/usefultoolsmod/test_instance"
            self.assertEqual(len(list(instances.glob("*.json"))), 7, version)

    def test_every_loader_has_spectral_block_and_charge_runtime_contract(self) -> None:
        for name, target in RUNNER.TARGETS.items():
            source = ROOT / target.directory / "src/main/java/com/stonytark/usefultoolsmod/gametest/UsefulToolsGameTests.java"
            text = source.read_text(encoding="utf-8")
            self.assertIn("spectralBlocksAndChargeContracts", text, name)
            self.assertIn("Ectoplasm Lantern light level", text, name)
            self.assertIn("matching owner/channel must arm remote detonation", text, name)

    def test_every_loader_has_wraith_runtime_and_advancement_contracts(self) -> None:
        for name, target in RUNNER.TARGETS.items():
            java_root = ROOT / target.directory / "src/main/java/com/stonytark/usefultoolsmod"
            gametest = (java_root / "gametest/UsefulToolsGameTests.java").read_text(encoding="utf-8")
            advancement = (java_root / "util/ModAdvancements.java").read_text(encoding="utf-8")
            for contract in (
                "assertWraithRuntimeContracts",
                "Wraith must use flight rather than Ghost gravity",
                "Wraith must clear fire every tick",
                "Wraith must lunge toward a visible mid-range target",
                "Wraith encounter award path must complete its advancement",
            ):
                self.assertIn(contract, gametest, name)
            for item in ("CONDENSED_ECTOPLASM", "ECTOPLASM"):
                self.assertTrue(
                    f"assertItemEntityPresent(ModItems.{item}" in gametest
                    or f"expectItemAt(ModItems.{item}" in gametest,
                    name,
                )
            self.assertTrue(
                "getRemainingCriteria" in advancement or "getUnobtainedCriteria" in advancement,
                name,
            )
            self.assertNotIn('award(advancement, "trigger")', advancement, name)
            self.assertNotIn('grantCriterion(advancement, "trigger")', advancement, name)

    def test_1_21_1_loader_tests_cover_tiers_worldgen_and_infused_armor(self) -> None:
        sources = {
            "forge": ROOT / "1.21.1/forge/src/main/java/com/stonytark/usefultoolsmod/gametest/UsefulToolsGameTests.java",
            "fabric": ROOT / "1.21.1/fabric/src/main/java/com/stonytark/usefultoolsmod/gametest/UsefulToolsGameTests.java",
            "neoforge": ROOT / "1.21.1/neoforge/src/main/java/com/stonytark/usefultoolsmod/gametest/UsefulToolsGameTests.java",
        }
        for loader, path in sources.items():
            source = path.read_text(encoding="utf-8")
            self.assertIn("ModItems.RGOLD_PICKAXE", source, loader)
            self.assertIn("PLACED_FEATURE", source, loader)
            self.assertIn("EctoplasmArmorHelper.isGhostInvisible(player)", source, loader)

    def test_all_mode_writes_an_aggregate_summary(self) -> None:
        source = RUNNER_PATH.read_text(encoding="utf-8")
        self.assertIn('output_dir / "matrix-summary.json"', source)
        self.assertIn('"failed_targets"', source)


if __name__ == "__main__":
    unittest.main()
