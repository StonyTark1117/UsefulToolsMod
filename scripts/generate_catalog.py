#!/usr/bin/env python3
"""Generate the version-neutral Useful Tools catalog from the canonical 1.21.1 tree."""

from __future__ import annotations

import json
import re
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
COMMON = ROOT / "1.21.1/common"
OUT = ROOT / "catalog/useful_tools_catalog.json"


def _generated_constants(path: Path) -> dict[str, str]:
    generated = path.parent.parent / "generated/GeneratedRegistrationIds.java"
    if not generated.exists():
        return {}
    return dict(re.findall(
        r'public static final String\s+(\w+)\s*=\s*"([a-z0-9_]+)";',
        generated.read_text(encoding="utf-8"),
    ))


def _resolve_id_token(token: str, constants: dict[str, str]) -> str:
    if token.startswith('"'):
        return token.strip('"')
    name = token.rsplit(".", 1)[-1]
    if name not in constants:
        raise ValueError(f"unknown generated registration constant {token}")
    return constants[name]


def registered_ids(path: Path, method: str = "register") -> list[str]:
    text = path.read_text(encoding="utf-8")
    token = r'("[a-z0-9_]+"|GeneratedRegistrationIds\.(?:Items|Blocks|Entities)\.\w+)'
    constants = _generated_constants(path)
    return [
        _resolve_id_token(match, constants)
        for match in re.findall(rf"\b{method}\(\s*{token}", text)
    ]


def resource_ids(kind: str, common: Path = COMMON) -> list[str]:
    data_root = common / "src/main/resources/data"
    result = []
    for namespace in ("usefultoolsmod", "minecraft"):
        base = data_root / namespace / kind
        if not base.exists():
            continue
        for path in base.rglob("*.json"):
            resource = path.relative_to(base).with_suffix("").as_posix()
            result.append(resource if namespace == "usefultoolsmod" else f"{namespace}:{resource}")
    return sorted(result)


def resource_definitions(kind: str, common: Path = COMMON) -> list[dict[str, object]]:
    data_root = common / "src/main/resources/data"
    result = []
    for namespace in ("usefultoolsmod", "minecraft"):
        base = data_root / namespace / kind
        if not base.exists():
            continue
        for path in base.rglob("*.json"):
            resource = path.relative_to(base).with_suffix("").as_posix()
            resource_id = resource if namespace == "usefultoolsmod" else f"{namespace}:{resource}"
            result.append({"id": resource_id, "payload": json.loads(path.read_text(encoding="utf-8"))})
    return sorted(result, key=lambda entry: entry["id"])


def parse_config_options(text: str) -> list[dict[str, object]]:
    fields = re.findall(r"public static (boolean|double) ([a-zA-Z0-9_]+) = ([^;]+);", text)
    result = []
    for value_type, key, default in fields:
        category = "Effects" if (
            key == "ghostSpawnChance" or key.endswith(("Effects", "Avoidance", "Phasing", "Drain", "Sticky", "Thorns", "Teleport"))
        ) else "Food Sets" if any(token in key for token in (
            "bread", "Kelp", "Flesh", "Melon", "Berry", "Pie", "mushroom", "pufferfish", "honey", "Fruit", "Apple", "cake"
        )) else "Content Sets"
        label = re.sub(r"(?<!^)(?=[A-Z])", " ", key)
        label = label.removesuffix(" Enabled").removesuffix(" Effects").strip().title()
        result.append({
            "key": key,
            "type": value_type,
            "default": default.strip(),
            "minimum": 0.0 if key == "ghostSpawnChance" else None,
            "maximum": 1.0 if key == "ghostSpawnChance" else None,
            "category": category,
            "label": label,
            "tooltip": f"Controls {label.lower()} behavior.",
            "reload_required": False,
        })
    return result


def config_options(common: Path = COMMON) -> list[dict[str, object]]:
    path = common / "src/main/java/com/stonytark/usefultoolsmod/Config.java"
    return parse_config_options(path.read_text(encoding="utf-8"))


def parse_tier_definitions(text: str) -> list[dict[str, str]]:
    pattern = re.compile(r"public static final Tier\s+(\w+)\s*=\s*new\s+\w+\((.*?)\);", re.S)
    return [{"key": key, "definition": " ".join(definition.split())} for key, definition in pattern.findall(text)]


def tier_definitions(common: Path = COMMON) -> list[dict[str, str]]:
    path = common / "src/main/java/com/stonytark/usefultoolsmod/item/ModToolTiers.java"
    return parse_tier_definitions(path.read_text(encoding="utf-8"))


def _java_numbers(definition: str) -> list[float]:
    return [float(value.rstrip("fFdD")) for value in re.findall(
        r"(?<![A-Za-z_])-?\d+(?:\.\d+)?[fFdD]?", definition
    )]


def _harvest_level(definition: str) -> int:
    for token, level in (("NETHERITE", 4), ("DIAMOND", 3), ("IRON", 2), ("STONE", 1), ("WOODEN", 0)):
        if token in definition:
            return level
    numbers = _java_numbers(definition)
    speed = numbers[1]
    return 3 if speed >= 8 else 2 if speed >= 6 else 1 if speed >= 4 else 0


def normalized_tool_materials(common: Path = COMMON) -> list[dict[str, object]]:
    result = []
    for tier in tier_definitions(common):
        numbers = _java_numbers(tier["definition"])
        if len(numbers) < 4:
            raise ValueError(f"cannot normalize tool tier {tier['key']}")
        result.append({
            "key": tier["key"],
            "harvest_level": _harvest_level(tier["definition"]),
            "uses": int(numbers[0]),
            "speed": numbers[1],
            "attack_bonus": numbers[2],
            "enchantment_value": int(numbers[3]),
        })
    return result


def _item_declarations(common: Path = COMMON) -> dict[str, str]:
    path = common / "src/main/java/com/stonytark/usefultoolsmod/item/ModItems.java"
    text = path.read_text(encoding="utf-8")
    pattern = re.compile(
        r"public static final RegistrySupplier<Item>\s+\w+\s*=\s*ITEMS\.register\("
        r'("[a-z0-9_]+"|GeneratedRegistrationIds\.Items\.\w+),(.*?)'
        r"(?=\n\s*public static final RegistrySupplier<Item>|\n\s*public static void register\(|\Z)",
        re.S,
    )
    constants = _generated_constants(path)
    return {
        _resolve_id_token(token, constants): body
        for token, body in pattern.findall(text)
    }


def normalized_item_definitions(common: Path = COMMON) -> list[dict[str, object]]:
    declarations = _item_declarations(common)
    definitions = []
    tool_suffixes = {"sword", "pickaxe", "shovel", "axe", "hoe"}
    armor_suffixes = {"helmet", "chestplate", "leggings", "boots"}
    for item_id in sorted(declarations):
        body = declarations[item_id]
        suffix = item_id.rsplit("_", 1)[-1]
        definition: dict[str, object] = {"id": item_id, "kind": "item"}
        if suffix in tool_suffixes:
            material = re.search(r"ModToolTiers\.(\w+)", body)
            if material is None:
                raise ValueError(f"missing tool material for {item_id}")
            definition.update(kind=suffix, material=material.group(1))
        elif suffix in armor_suffixes:
            material = re.search(r"ModArmorMaterials\.(\w+)", body)
            durability = re.search(r"getDurability\((\d+)\)", body)
            if material is None or durability is None:
                raise ValueError(f"missing armor definition for {item_id}")
            definition.update(kind=suffix, material=material.group(1), durability=int(durability.group(1)))
        definitions.append(definition)
    return definitions


def normalized_armor_materials(common: Path = COMMON) -> list[dict[str, object]]:
    path = common / "src/main/java/com/stonytark/usefultoolsmod/item/ModArmorMaterials.java"
    text = path.read_text(encoding="utf-8")
    declarations = re.findall(
        r"public static final Holder<ArmorMaterial>\s+(\w+)\s*=\s*register\(\"([^\"]+)\",(.*?)"
        r"(?=\n\s*public static final Holder<ArmorMaterial>|\n\s*private static Holder<ArmorMaterial>|\Z)",
        text,
        re.S,
    )
    item_definitions = normalized_item_definitions(common)
    result = []
    for key, texture_family, body in declarations:
        protection = dict((slot.lower(), int(value)) for slot, value in re.findall(
            r"ArmorItem\.Type\.(BOOTS|LEGGINGS|CHESTPLATE|HELMET),\s*(\d+)", body
        ))
        enchant = re.search(r"\}\),\s*(\d+)\s*,", body)
        durability = next((entry["durability"] for entry in item_definitions if entry.get("material") == key), None)
        if len(protection) != 4 or enchant is None or durability is None:
            raise ValueError(f"cannot normalize armor material {key}")
        result.append({
            "key": key,
            "texture_family": texture_family,
            "durability": durability,
            "protection": protection,
            "enchantment_value": int(enchant.group(1)),
        })
    return result


def build_catalog(common: Path = COMMON) -> dict[str, object]:
    item_source = common / "src/main/java/com/stonytark/usefultoolsmod/item/ModItems.java"
    block_source = common / "src/main/java/com/stonytark/usefultoolsmod/block/ModBlocks.java"
    entity_source = common / "src/main/java/com/stonytark/usefultoolsmod/entity/ModEntities.java"
    blocks = registered_ids(block_source, "registerBlock")
    direct_items = registered_ids(item_source)
    items = sorted(set(direct_items))

    return {
        "schema": 2,
        "mod_id": "usefultoolsmod",
        "logical_version": "2.3.0",
        "canonical_source": "1.21.1/common",
        "compatibility_aliases": {
            "pointedDripstoneEnabled": "dripstoneEnabled",
            "pointedDripstoneEffects": "dripstoneEffects",
        },
        "content": {
            "items": items,
            "block_items": sorted(blocks),
            "blocks": sorted(blocks),
            "entities": sorted(registered_ids(entity_source)),
            "tool_tiers": tier_definitions(common),
            "recipes": resource_ids("recipe", common),
            "advancements": resource_ids("advancement", common),
        },
        "definitions": {
            "items": normalized_item_definitions(common),
            "tool_materials": normalized_tool_materials(common),
            "armor_materials": normalized_armor_materials(common),
            "blocks": [{"id": block_id, "material": "rock"} for block_id in sorted(blocks)],
            "recipes": resource_definitions("recipe", common),
        },
        "config": config_options(common),
        "intent": {
            "spectral_infuser": "Transforms supported vanilla equipment with ectoplasm and creates ghost spawn eggs.",
            "ghost": "Night-spawning hostile mob with ectoplasm drops and armor interactions.",
            "explosives": ["dynamite", "grenade"],
            "worldgen": ["rgoldore", "rgold_deepslate_ore", "rgold_nether_ore", "rgold_end_ore"],
        },
    }


def main() -> None:
    catalog = build_catalog()
    OUT.parent.mkdir(parents=True, exist_ok=True)
    OUT.write_text(json.dumps(catalog, indent=2, sort_keys=True) + "\n", encoding="utf-8")


if __name__ == "__main__":
    main()
