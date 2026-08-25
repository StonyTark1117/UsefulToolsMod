from __future__ import annotations

import json
import re
import subprocess
import sys
import unittest
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
ALIASES = {
    "pointedDripstoneEnabled": "dripstoneEnabled",
    "pointedDripstoneEffects": "dripstoneEffects",
}

CONFIG_SOURCES = (
    "1.20.1/fabric/src/main/java/com/stonytark/usefultoolsmod/Config.java",
    "1.20.1/forge/src/main/java/com/stonytark/usefultoolsmod/Config.java",
    "1.20.1/neoforge/src/main/java/com/stonytark/usefultoolsmod/Config.java",
    "1.20.2/fabric/src/main/java/com/stonytark/usefultoolsmod/Config.java",
    "1.20.2/forge/src/main/java/com/stonytark/usefultoolsmod/Config.java",
    "1.20.2/neoforge/src/main/java/com/stonytark/usefultoolsmod/Config.java",
    "1.21.1/fabric/src/main/java/com/stonytark/usefultoolsmod/Config.java",
    "1.21.1/forge/src/main/java/com/stonytark/usefultoolsmod/Config.java",
    "1.21.1/neoforge/src/main/java/com/stonytark/usefultoolsmod/Config.java",
    "26.1.2/fabric/src/main/java/com/stonytark/usefultoolsmod/Config.java",
    "26.1.2/forge/src/main/java/com/stonytark/usefultoolsmod/Config.java",
    "26.1.2/neoforge/src/main/java/com/stonytark/usefultoolsmod/Config.java",
    "26.2/fabric/src/main/java/com/stonytark/usefultoolsmod/Config.java",
    "26.2/forge/src/main/java/com/stonytark/usefultoolsmod/Config.java",
    "26.2/neoforge/src/main/java/com/stonytark/usefultoolsmod/Config.java",
)

JSON_CONFIG_SOURCES = tuple(path for path in CONFIG_SOURCES if "/fabric/" in path)

SPEC_CONFIG_SOURCES = tuple(
    path for path in CONFIG_SOURCES
    if "/forge/" in path or "/neoforge/" in path
)

FABRIC_SCREEN_ROOTS = {
    "1.20.1/fabric": "7.2.2",
    "1.20.2/fabric": "8.0.1",
    "1.21.1/fabric": None,
    "26.1.2/fabric": "18.0.0-alpha.8",
    "26.2/fabric": "20.0.1",
}

MODERN_SOURCE_ROOTS = (
    "1.20.1/fabric", "1.20.1/forge", "1.20.1/neoforge",
    "1.20.2/fabric", "1.20.2/forge", "1.20.2/neoforge",
    "1.21.1/forge", "1.21.1/fabric", "1.21.1/neoforge",
    "26.1.2/fabric", "26.1.2/forge", "26.1.2/neoforge",
    "26.2/fabric", "26.2/forge", "26.2/neoforge",
)


def public_option_names(text: str) -> list[str]:
    names = re.findall(r"public static (?:boolean|double)\s+([A-Za-z0-9_]+)\s*(?:=|;)", text)
    return [ALIASES.get(name, name) for name in names]


class ConfigContractTests(unittest.TestCase):
    def test_spec_screen_paths_resolve_to_canonical_descriptors(self) -> None:
        roots = (
            "1.20.1/forge",
            "1.20.1/neoforge",
            "1.20.2/forge",
            "1.20.2/neoforge",
            "1.21.1/forge",
            "1.21.1/neoforge",
            "26.1.2/forge",
            "26.1.2/neoforge",
            "26.2/forge",
            "26.2/neoforge",
        )
        for root_name in roots:
            root = ROOT / root_name / "src/main/java/com/stonytark/usefultoolsmod"
            config = (root / "Config.java").read_text(encoding="utf-8")
            descriptors = set(
                re.findall(
                    r'add\(values, "([^"]+)"',
                    (root / "client/ConfigDescriptors.java").read_text(encoding="utf-8"),
                )
            )
            section = ""
            current = ""
            spec_paths = []
            for line in config.splitlines():
                pushed = re.search(r'BUILDER\.push\("([^"]+)"', line)
                if pushed:
                    section = pushed.group(1)
                assigned = re.search(r'^\s*([A-Z][A-Z0-9_]+) = BUILDER', line)
                if assigned:
                    current = assigned.group(1)
                defined = re.search(r'\.define(?:InRange)?\("([^"]+)"', line)
                if defined and current:
                    leaf = defined.group(1)
                    spec_paths.append(section + leaf[:1].upper() + leaf[1:])

            def canonical(key: str) -> str:
                if key in descriptors:
                    return key
                for old, new in (
                    ("spectralInfuserInfused", "infused"),
                    ("pointedDripstone", "dripstone"),
                    ("foodSetsHunger", "foodHunger"),
                    ("sweetBerries", "sweetBerry"),
                    ("overpower", "op"),
                    ("ectoplasmSet", "ectoplasm"),
                ):
                    if key.startswith(old):
                        key = new + key[len(old):]
                        break
                if key in descriptors:
                    return key
                if key.endswith("Enabled") and key[:-7] in descriptors:
                    return key[:-7]
                return key

            unresolved = sorted(path for path in spec_paths if canonical(path) not in descriptors)
            self.assertEqual(unresolved, [], root_name)

    @classmethod
    def setUpClass(cls) -> None:
        catalog = json.loads((ROOT / "catalog/useful_tools_catalog.json").read_text(encoding="utf-8"))
        cls.expected = {option["key"] for option in catalog["config"]}

    def test_every_modern_config_exposes_the_canonical_116_options(self) -> None:
        for relative in CONFIG_SOURCES:
            with self.subTest(source=relative):
                names = public_option_names((ROOT / relative).read_text(encoding="utf-8"))
                self.assertEqual(116, len(names))
                self.assertEqual(116, len(set(names)), "duplicate runtime option")
                self.assertEqual(self.expected, set(names))

    def test_generated_runtime_descriptors_match_the_canonical_catalog(self) -> None:
        catalog = json.loads((ROOT / "catalog/useful_tools_catalog.json").read_text(encoding="utf-8"))
        expected_rows = [
            (
                option["key"], option["category"], option["label"], option["tooltip"],
                str(option["reload_required"]).lower(),
            )
            for option in catalog["config"]
        ]
        pattern = re.compile(
            r'add\(values, "([^"]+)", "([^"]+)", "([^"]+)", "([^"]+)", (true|false)\);'
        )
        descriptor_hashes = set()
        for root in MODERN_SOURCE_ROOTS:
            relative = (
                f"{root}/src/main/java/com/stonytark/usefultoolsmod/client/ConfigDescriptors.java"
            )
            with self.subTest(source=relative):
                text = (ROOT / relative).read_text(encoding="utf-8")
                self.assertEqual(expected_rows, pattern.findall(text))
                descriptor_hashes.add(text)
        self.assertEqual(1, len(descriptor_hashes), "all roots must consume one generated descriptor")

    def test_config_descriptor_generator_is_reproducible(self) -> None:
        before = {
            root: (ROOT / root / "src/main/java/com/stonytark/usefultoolsmod/client/ConfigDescriptors.java")
            .read_bytes()
            for root in MODERN_SOURCE_ROOTS
        }
        subprocess.run(
            [sys.executable, str(ROOT / "scripts/generate_config_descriptors.py")],
            cwd=ROOT,
            check=True,
            stdout=subprocess.DEVNULL,
        )
        after = {
            root: (ROOT / root / "src/main/java/com/stonytark/usefultoolsmod/client/ConfigDescriptors.java")
            .read_bytes()
            for root in MODERN_SOURCE_ROOTS
        }
        self.assertEqual(before, after)

    def test_every_modern_screen_consumes_canonical_labels_tooltips_and_reload_metadata(self) -> None:
        for root in MODERN_SOURCE_ROOTS:
            relative = (
                f"{root}/src/main/java/com/stonytark/usefultoolsmod/client/UsefulToolsConfigScreen.java"
            )
            with self.subTest(source=relative):
                text = (ROOT / relative).read_text(encoding="utf-8")
                self.assertIn("ConfigDescriptors.get", text)
                self.assertIn(".tooltip()", text)
                self.assertIn(".reloadRequired()", text)

    def test_standalone_forge_1211_registers_its_builtin_screen(self) -> None:
        root = ROOT / "1.21.1/forge/src/main/java/com/stonytark/usefultoolsmod"
        main = (root / "UsefultoolsMod.java").read_text(encoding="utf-8")
        registration = (root / "client/ClientConfigRegistration.java").read_text(encoding="utf-8")
        self.assertIn("ClientConfigRegistration.register(context.getContainer())", main)
        self.assertIn("ConfigScreenHandler.ConfigScreenFactory", registration)
        self.assertIn("UsefulToolsConfigScreen::build", registration)

    def test_json_configs_are_atomic_bounded_and_alias_aware(self) -> None:
        required_markers = (
            "AtomicMoveNotSupportedException",
            "StandardCopyOption.ATOMIC_MOVE",
            "ghostSpawnChance",
            "Math.max(0.0, Math.min(1.0, value))",
            "pointedDripstoneEnabled",
            "pointedDripstoneEffects",
            "dripstoneEnabled",
            "dripstoneEffects",
        )
        for relative in JSON_CONFIG_SOURCES:
            with self.subTest(source=relative):
                text = (ROOT / relative).read_text(encoding="utf-8")
                for marker in required_markers:
                    self.assertIn(marker, text)

    def test_spec_configs_bound_ghost_spawn_chance(self) -> None:
        pattern = re.compile(r'defineInRange\("spawnChance",\s*0\.15,\s*0\.0,\s*1\.0\)')
        for relative in SPEC_CONFIG_SOURCES:
            with self.subTest(source=relative):
                text = (ROOT / relative).read_text(encoding="utf-8")
                self.assertRegex(text, pattern)

    def test_spec_config_handlers_ignore_unload_events(self) -> None:
        for relative in SPEC_CONFIG_SOURCES:
            with self.subTest(source=relative):
                text = (ROOT / relative).read_text(encoding="utf-8")
                handler = text[text.index("static void onLoad(final ModConfigEvent event)"):]
                self.assertIn("event instanceof ModConfigEvent.Loading", handler)
                self.assertIn("event instanceof ModConfigEvent.Reloading", handler)
                self.assertIn("!SPEC.isLoaded()", handler)
                self.assertLess(handler.index("return;"), handler.index("EXPLOSIVES_ENABLED.get()"))

    def test_every_fabric_line_exposes_the_builtin_screen_through_mod_menu(self) -> None:
        for relative, pinned_version in FABRIC_SCREEN_ROOTS.items():
            with self.subTest(root=relative):
                root = ROOT / relative
                metadata = json.loads((root / "src/main/resources/fabric.mod.json").read_text(encoding="utf-8"))
                self.assertIn("modmenu", metadata["entrypoints"])
                plugin = metadata["entrypoints"]["modmenu"][0]
                plugin_path = root / "src/main/java" / Path(*plugin.split(".")).with_suffix(".java")
                self.assertTrue(plugin_path.is_file(), plugin_path)
                self.assertIn("UsefulToolsConfigScreen", plugin_path.read_text(encoding="utf-8"))
                screen = root / "src/main/java/com/stonytark/usefultoolsmod/client/UsefulToolsConfigScreen.java"
                self.assertTrue(screen.is_file(), screen)
                if pinned_version is not None:
                    properties = (root / "gradle.properties").read_text(encoding="utf-8")
                    self.assertIn(f"modmenu_version={pinned_version}", properties)
                if relative == "26.2/fabric":
                    self.assertEqual("${loader_version_range}", metadata["depends"]["fabricloader"])
                    self.assertIn("loader_version_range=>=0.19.2", properties)

    def test_explosives_kill_switch_guards_both_use_paths(self) -> None:
        for root in MODERN_SOURCE_ROOTS:
            for class_name in ("Dynamite", "Grenade"):
                relative = (
                    f"{root}/src/main/java/com/stonytark/usefultoolsmod/"
                    f"item/custom/{class_name}.java"
                )
                with self.subTest(source=relative):
                    text = (ROOT / relative).read_text(encoding="utf-8")
                    guard = text.index("if (!Config.explosivesEnabled)")
                    first_effect = text.index("playSound") if "playSound" in text else text.index("explode(")
                    consume = text.index("shrink(1)") if "shrink(1)" in text else text.index("decrement(1)")
                    self.assertLess(guard, first_effect, "disabled use must be side-effect free")
                    self.assertLess(guard, consume, "disabled use must not consume the item")

    def test_ghost_spawn_predicates_honor_enabled_and_chance(self) -> None:
        for root in MODERN_SOURCE_ROOTS:
            relative = (
                f"{root}/src/main/java/com/stonytark/usefultoolsmod/"
                "entity/custom/GhostEntity.java"
            )
            with self.subTest(source=relative):
                text = (ROOT / relative).read_text(encoding="utf-8")
                method = text[text.index("checkGhostSpawnRules"):]
                method = method[:method.index("return true;")]
                self.assertIn("Config.ghostEnabled", method)
                self.assertIn("Config.ghostSpawnChance", method)

    def test_spectral_infuser_processing_honors_kill_switch(self) -> None:
        for root in MODERN_SOURCE_ROOTS:
            relative = (
                f"{root}/src/main/java/com/stonytark/usefultoolsmod/"
                "block/entity/SpectralInfuserBlockEntity.java"
            )
            with self.subTest(source=relative):
                text = (ROOT / relative).read_text(encoding="utf-8")
                method = text[text.index("static void tick"):]
                self.assertIn("if (!Config.spectralInfuserEnabled)", method)
                self.assertLess(
                    method.index("if (!Config.spectralInfuserEnabled)"),
                    method.index("be.progress++"),
                )


if __name__ == "__main__":
    unittest.main()
