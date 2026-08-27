#!/usr/bin/env python3
"""Install the 2.3.1 Ectoplasm Lantern and Mining Charge model contract."""

from __future__ import annotations

import json
from pathlib import Path


ROOT = Path(__file__).resolve().parent.parent
VERSIONS = ("1.20.1", "1.20.2", "1.21.1", "26.1.2", "26.2")
LOADERS = ("forge", "fabric", "neoforge")


def faces(texture: str) -> dict[str, dict[str, str]]:
    return {face: {"texture": texture} for face in ("down", "up", "north", "south", "west", "east")}


def cube(start: list[float], end: list[float], texture: str, *, shade: bool = True) -> dict[str, object]:
    result: dict[str, object] = {"from": start, "to": end, "faces": faces(texture)}
    if not shade:
        result["shade"] = False
    return result


SOUL_LANTERN_MODEL = {
    "credit": "Useful Tools 2.3.1 bounded block repair",
    "ambientocclusion": False,
    "textures": {
        "particle": "usefultoolsmod:block/soul_lantern",
        "frame": "minecraft:block/polished_blackstone",
        "core": "minecraft:block/cyan_concrete",
        "glass": "minecraft:block/cyan_stained_glass",
        "flame": "usefultoolsmod:block/soul_lantern",
    },
    "elements": [
        cube([4, 0, 4], [12, 2, 12], "#frame"),
        cube([4, 14, 4], [12, 16, 12], "#frame"),
        cube([4, 2, 4], [5, 14, 5], "#frame"),
        cube([11, 2, 4], [12, 14, 5], "#frame"),
        cube([4, 2, 11], [5, 14, 12], "#frame"),
        cube([11, 2, 11], [12, 14, 12], "#frame"),
        cube([5.25, 3, 5.25], [10.75, 13, 10.75], "#core", shade=False),
        cube([5, 2.75, 5], [11, 13.25, 11], "#glass", shade=False),
        {
            "from": [4.99, 3, 4.99],
            "to": [11.01, 13, 11.01],
            "shade": False,
            "faces": {
                "north": {"texture": "#flame"},
                "south": {"texture": "#flame"},
                "west": {"texture": "#flame"},
                "east": {"texture": "#flame"},
            },
        },
    ],
    "display": {
        "gui": {"rotation": [30, 225, 0], "translation": [0, 0, 0], "scale": [0.75, 0.75, 0.75]},
        "ground": {"translation": [0, 3, 0], "scale": [0.25, 0.25, 0.25]},
        "fixed": {"scale": [0.5, 0.5, 0.5]},
        "thirdperson_righthand": {"rotation": [75, 45, 0], "translation": [0, 2.5, 0], "scale": [0.375, 0.375, 0.375]},
        "firstperson_righthand": {"rotation": [0, 45, 0], "scale": [0.4, 0.4, 0.4]},
    },
}


def mining_charge_model(lit: bool) -> dict[str, object]:
    indicator = "minecraft:block/redstone_block" if lit else "minecraft:block/gray_concrete"
    return {
        "credit": "Useful Tools 2.3.1 bounded directional block repair",
        "ambientocclusion": False,
        "textures": {
            "particle": "usefultoolsmod:block/mining_charge",
            "casing": "minecraft:block/black_concrete",
            "metal": "minecraft:block/iron_block",
            "indicator": indicator,
            "detail": "usefultoolsmod:block/mining_charge",
        },
        "elements": [
            cube([3, 0, 3], [13, 2, 13], "#metal"),
            cube([4, 2, 4], [12, 5, 12], "#casing"),
            cube([4, 3, 3.75], [12, 4, 12.25], "#metal"),
            # Keep the armed-state indicator exposed around the smaller custom
            # detail plate. The previous nearly full-size plate hid the only
            # meaningful LIT difference from normal play distances.
            cube([5, 5, 5], [11, 5.75, 11], "#indicator", shade=False),
            {
                "from": [6.5, 5.76, 6.5],
                "to": [9.5, 5.77, 9.5],
                "shade": False,
                "faces": {"up": {"texture": "#detail"}},
            },
        ],
        "display": {
            "gui": {"rotation": [30, 225, 0], "translation": [0, 2, 0], "scale": [0.9, 0.9, 0.9]},
            "ground": {"translation": [0, 3, 0], "scale": [0.25, 0.25, 0.25]},
            "fixed": {"scale": [0.5, 0.5, 0.5]},
            "thirdperson_righthand": {"rotation": [75, 45, 0], "translation": [0, 2.5, 0], "scale": [0.375, 0.375, 0.375]},
            "firstperson_righthand": {"rotation": [0, 45, 0], "scale": [0.4, 0.4, 0.4]},
        },
    }


ROTATIONS = {
    "down": {},
    "up": {"x": 180},
    "north": {"x": 90},
    "south": {"x": 270},
    "west": {"x": 90, "y": 90},
    "east": {"x": 90, "y": 270},
}


def mining_charge_blockstate() -> dict[str, object]:
    variants: dict[str, object] = {}
    for facing, rotation in ROTATIONS.items():
        for lit in (False, True):
            value: dict[str, object] = {
                "model": f"usefultoolsmod:block/mining_charge{'_lit' if lit else ''}",
                **rotation,
            }
            if rotation:
                value["uvlock"] = True
            variants[f"facing={facing},lit={str(lit).lower()}"] = value
    return {"variants": variants}


ITEM_MODELS = {
    "soul_lantern": {"parent": "minecraft:item/generated", "textures": {"layer0": "usefultoolsmod:block/soul_lantern"}},
    "mining_charge": {"parent": "minecraft:item/generated", "textures": {"layer0": "usefultoolsmod:block/mining_charge"}},
}

WRAITH_WTHIT_TRANSLATIONS = {
    "wthit.usefultoolsmod.wraith.identity": "Hostile spectral lunger",
    "wthit.usefultoolsmod.wraith.warded": "Repelled by an active Ectoplasm Lantern",
    "wthit.usefultoolsmod.wraith.hunting": "Hunting nearby targets",
    "wthit.usefultoolsmod.wraith.weapon_effective": "Your weapon can damage this Wraith",
    "wthit.usefultoolsmod.wraith.weapon_immune": "Your weapon cannot damage this Wraith",
    "wthit.usefultoolsmod.wraith.weapon_required": "Requires an ectoplasm-infused weapon",
}

PLAYER_FACING_RENAMES = {
    "block.usefultoolsmod.soul_lantern": "Ectoplasm Lantern",
    "advancement.usefultoolsmod.stabilize_ghost.description": "Stabilize a Ghost with an active Ectoplasm Lantern",
}


def write_json(path: Path, value: object) -> None:
    path.parent.mkdir(parents=True, exist_ok=True)
    path.write_text(json.dumps(value, indent=2) + "\n", encoding="utf-8")


def main() -> None:
    for version in VERSIONS:
        for loader in LOADERS:
            assets = ROOT / version / loader / "src/main/resources/assets/usefultoolsmod"
            # The Ectoplasm Lantern is canonical. Keep the old Soul Lantern
            # resource aliases in sync solely for existing-world migration.
            write_json(assets / "models/block/ectoplasm_lantern.json", SOUL_LANTERN_MODEL)
            write_json(assets / "models/block/soul_lantern.json", SOUL_LANTERN_MODEL)
            write_json(assets / "models/block/mining_charge.json", mining_charge_model(False))
            write_json(assets / "models/block/mining_charge_lit.json", mining_charge_model(True))
            write_json(assets / "models/item/ectoplasm_lantern.json", ITEM_MODELS["soul_lantern"])
            write_json(assets / "models/item/soul_lantern.json", ITEM_MODELS["soul_lantern"])
            write_json(assets / "models/item/mining_charge.json", ITEM_MODELS["mining_charge"])
            write_json(assets / "blockstates/ectoplasm_lantern.json", {"variants": {"": {"model": "usefultoolsmod:block/ectoplasm_lantern"}}})
            write_json(assets / "blockstates/soul_lantern.json", {"variants": {"": {"model": "usefultoolsmod:block/soul_lantern"}}})
            write_json(assets / "blockstates/mining_charge.json", mining_charge_blockstate())
            lang_path = assets / "lang/en_us.json"
            lang = json.loads(lang_path.read_text(encoding="utf-8"))
            lang.update(PLAYER_FACING_RENAMES)
            lang.update(WRAITH_WTHIT_TRANSLATIONS)
            write_json(lang_path, lang)


if __name__ == "__main__":
    main()
