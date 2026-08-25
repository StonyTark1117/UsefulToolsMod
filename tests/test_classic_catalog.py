from __future__ import annotations

import json
import sys
import unittest
from pathlib import Path


ROOT = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(ROOT / "scripts"))

import generate_classic_catalog  # noqa: E402


class ClassicCatalogGeneratorTests(unittest.TestCase):
    @classmethod
    def setUpClass(cls) -> None:
        cls.catalog = json.loads(
            (ROOT / "catalog/useful_tools_catalog.json").read_text(encoding="utf-8")
        )

    def test_manifest_contains_complete_version_neutral_definitions(self) -> None:
        definitions = self.catalog["definitions"]
        self.assertEqual(2, self.catalog["schema"])
        self.assertEqual(633, len(definitions["items"]))
        self.assertEqual(87, len(definitions["tool_materials"]))
        self.assertEqual(45, len(definitions["armor_materials"]))
        self.assertEqual(21, len(definitions["blocks"]))
        self.assertEqual(682, len(definitions["recipes"]))
        self.assertEqual(
            set(self.catalog["content"]["items"]),
            {entry["id"] for entry in definitions["items"]},
        )

    def test_generated_java_is_current_and_registers_the_exact_catalog(self) -> None:
        generated, items, blocks = generate_classic_catalog.render_java()
        checked_in = generate_classic_catalog.OUTPUT.read_text(encoding="utf-8")
        self.assertEqual(checked_in, generated)
        self.assertEqual(633, len(items))
        self.assertEqual(21, len(blocks))
        self.assertEqual(633, generated.count('        registerItem("'))
        self.assertEqual(21, generated.count('        registerBlock("'))

    def test_generated_recipe_adapter_is_current_and_complete(self) -> None:
        generated, crafting, furnace = generate_classic_catalog.render_recipes()
        checked_in = generate_classic_catalog.RECIPE_OUTPUT.read_text(encoding="utf-8")
        self.assertEqual(checked_in, generated)
        self.assertEqual(672, crafting)
        self.assertEqual(5, furnace)
        self.assertEqual(682, len(self.catalog["definitions"]["recipes"]))

    def test_generated_classic_config_is_current_and_exposes_every_descriptor(self) -> None:
        generated = generate_classic_catalog.render_config()
        checked_in = generate_classic_catalog.CONFIG_OUTPUT.read_text(encoding="utf-8")
        self.assertEqual(checked_in, generated)
        self.assertEqual(116, len(self.catalog["config"]))
        self.assertEqual(116, generated.count('        VALUES.put("'))
        for option in self.catalog["config"]:
            self.assertTrue(option["label"])
            self.assertTrue(option["tooltip"])
            self.assertIsInstance(option["reload_required"], bool)

    def test_classic_migration_table_is_generated_and_exhaustive(self) -> None:
        generated = generate_classic_catalog.render_migration_table()
        checked_in = generate_classic_catalog.MIGRATION_OUTPUT.read_text(encoding="utf-8")
        self.assertEqual(checked_in, generated)
        self.assertEqual(67, generated.count("| retained legacy object |"))

    def test_every_generated_item_and_armor_texture_exists(self) -> None:
        item_root = ROOT / "1.7.10/src/main/resources/assets/usefultoolsmod/textures/items"
        armor_root = ROOT / "1.7.10/src/main/resources/assets/usefultoolsmod/textures/models/armor"
        for definition in self.catalog["definitions"]["items"]:
            item_id = definition["id"]
            if item_id != "ghost_spawn_egg":
                self.assertTrue((item_root / f"{item_id}.png").is_file(), item_id)
        for material in self.catalog["definitions"]["armor_materials"]:
            family = material["texture_family"]
            self.assertTrue((armor_root / f"{family}_layer_1.png").is_file(), family)
            self.assertTrue((armor_root / f"{family}_layer_2.png").is_file(), family)

        animation = ROOT / (
            "1.7.10/src/main/resources/assets/usefultoolsmod/textures/blocks/"
            "polished_prismarine_block.png.mcmeta"
        )
        self.assertEqual({"animation": {"frametime": 8}}, json.loads(animation.read_text()))


if __name__ == "__main__":
    unittest.main()
