from __future__ import annotations

import copy
import json
import sys
import tempfile
import unittest
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(ROOT / "scripts"))

import audit_catalog  # noqa: E402
import generate_catalog  # noqa: E402


class CatalogGeneratorTests(unittest.TestCase):
    def test_registered_ids_preserve_source_order_and_method(self) -> None:
        with tempfile.TemporaryDirectory() as directory:
            source = Path(directory) / "Registry.java"
            source.write_text(
                'register("first", factory);\nregisterBlock(\n    "stone_block", factory);\n'
                'register("second", factory);\n',
                encoding="utf-8",
            )
            self.assertEqual(["first", "second"], generate_catalog.registered_ids(source))
            self.assertEqual(["stone_block"], generate_catalog.registered_ids(source, "registerBlock"))

    def test_resource_ids_include_namespace_and_nested_paths(self) -> None:
        with tempfile.TemporaryDirectory() as directory:
            common = Path(directory)
            data = common / "src/main/resources/data"
            paths = (
                data / "usefultoolsmod/recipe/z.json",
                data / "usefultoolsmod/recipe/nested/a.json",
                data / "minecraft/recipe/ice.json",
            )
            for path in paths:
                path.parent.mkdir(parents=True, exist_ok=True)
                path.write_text("{}\n", encoding="utf-8")
            self.assertEqual(
                ["minecraft:ice", "nested/a", "z"],
                generate_catalog.resource_ids("recipe", common),
            )

    def test_config_parser_assigns_bounds_categories_and_defaults(self) -> None:
        parsed = generate_catalog.parse_config_options(
            "public static double ghostSpawnChance = 0.25;\n"
            "public static boolean fireEffects = true;\n"
            "public static boolean breadToolsEnabled = false;\n"
            "public static boolean steelToolsEnabled = true;\n"
        )
        self.assertEqual(
            ["Effects", "Effects", "Food Sets", "Content Sets"],
            [entry["category"] for entry in parsed],
        )
        self.assertEqual((0.0, 1.0), (parsed[0]["minimum"], parsed[0]["maximum"]))
        self.assertEqual("false", parsed[2]["default"])

    def test_tier_parser_normalizes_multiline_definitions(self) -> None:
        parsed = generate_catalog.parse_tier_definitions(
            "public static final Tier TEST = new ModTier(\n  3,  1200,\n  8.0F\n);"
        )
        self.assertEqual([{"key": "TEST", "definition": "3, 1200, 8.0F"}], parsed)

    def test_canonical_catalog_matches_checked_in_file(self) -> None:
        generated = generate_catalog.build_catalog()
        checked_in = json.loads(generate_catalog.OUT.read_text(encoding="utf-8"))
        self.assertEqual(checked_in, generated)
        self.assertEqual([], audit_catalog.validation_errors(generated))


class CatalogAuditTests(unittest.TestCase):
    @classmethod
    def setUpClass(cls) -> None:
        cls.catalog = generate_catalog.build_catalog()

    def test_detects_duplicate_and_count_drift(self) -> None:
        data = copy.deepcopy(self.catalog)
        data["content"]["items"].append(data["content"]["items"][0])
        errors = audit_catalog.validation_errors(data)
        self.assertIn("expected 638 items, found 639", errors)
        self.assertIn("duplicate items IDs", errors)

    def test_detects_block_item_mismatch_and_missing_ice_override(self) -> None:
        data = copy.deepcopy(self.catalog)
        data["content"]["block_items"] = data["content"]["block_items"][1:]
        data["content"]["recipes"].remove("minecraft:ice")
        errors = audit_catalog.validation_errors(data)
        self.assertIn("every block must have a matching block item", errors)
        self.assertIn("missing the intentional minecraft:ice snowball recipe", errors)


if __name__ == "__main__":
    unittest.main()
