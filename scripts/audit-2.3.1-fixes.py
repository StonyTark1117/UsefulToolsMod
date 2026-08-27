#!/usr/bin/env python3
"""Static release gate for the 2.3.1 block and Wraith repairs."""

from __future__ import annotations

import json
from pathlib import Path
import struct
import sys


ROOT = Path(__file__).resolve().parent.parent
VERSIONS = ("1.20.1", "1.20.2", "1.21.1", "26.1.2", "26.2")
LOADERS = ("forge", "fabric", "neoforge")
WTHIT_TARGETS = {
    "1.20.1-fabric", "1.20.2-fabric",
    "1.21.1-forge", "1.21.1-fabric", "1.21.1-neoforge",
    "26.1.2-forge", "26.1.2-fabric", "26.1.2-neoforge",
    "26.2-fabric", "26.2-neoforge",
}


def load(path: Path) -> object:
    if not path.is_file():
        raise AssertionError(f"missing {path.relative_to(ROOT)}")
    return json.loads(path.read_text(encoding="utf-8"))


def png_color_type(path: Path) -> int:
    data = path.read_bytes()
    if data[:8] != b"\x89PNG\r\n\x1a\n" or data[12:16] != b"IHDR":
        raise AssertionError(f"invalid PNG {path.relative_to(ROOT)}")
    _width, _height, _depth, color_type = struct.unpack(">IIBB", data[16:26])
    return color_type


def audit_modern(version: str, loader: str) -> None:
    target = f"{version}-{loader}"
    root = ROOT / version / loader
    assets = root / "src/main/resources/assets/usefultoolsmod"

    lantern = load(assets / "models/block/ectoplasm_lantern.json")
    assert lantern.get("parent") != "minecraft:block/cube_all", f"{target}: lantern still cube_all"
    assert len(lantern.get("elements", [])) >= 8, f"{target}: lantern geometry incomplete"
    assert lantern.get("textures", {}).get("frame", "").startswith("minecraft:block/"), f"{target}: lantern lacks solid frame"

    for name in ("mining_charge", "mining_charge_lit"):
        model = load(assets / f"models/block/{name}.json")
        assert model.get("parent") != "minecraft:block/cube_all", f"{target}: {name} still cube_all"
        assert len(model.get("elements", [])) >= 5, f"{target}: {name} geometry incomplete"
        assert model.get("textures", {}).get("casing", "").startswith("minecraft:block/"), f"{target}: {name} lacks backing"

    for model_name in ("ectoplasm_lantern", "mining_charge", "mining_charge_lit"):
        model = load(assets / f"models/block/{model_name}.json")
        serialized = json.dumps(model)
        assert "item/" not in serialized, f"{target}: placed model resolves through item assets"
        for element in model.get("elements", []):
            for point in (element.get("from", []), element.get("to", [])):
                assert len(point) == 3 and all(0 <= coordinate <= 16 for coordinate in point), (
                    f"{target}: {model_name} contains unbounded geometry"
                )

    charge_state = load(assets / "blockstates/mining_charge.json")
    variants = charge_state.get("variants", {})
    expected = {f"facing={face},lit={lit}" for face in ("down", "up", "north", "south", "west", "east") for lit in ("false", "true")}
    assert set(variants) == expected, f"{target}: Mining Charge does not map all 12 states"
    assert {value["model"] for value in variants.values()} == {
        "usefultoolsmod:block/mining_charge", "usefultoolsmod:block/mining_charge_lit"
    }, f"{target}: Mining Charge state models are incomplete"

    for name in ("ectoplasm_lantern", "mining_charge"):
        item = load(assets / f"models/item/{name}.json")
        assert item.get("parent") == "minecraft:item/generated", f"{target}: {name} item reuses world geometry"
        texture_name = "soul_lantern" if name == "ectoplasm_lantern" else name
        assert item.get("textures", {}).get("layer0") == f"usefultoolsmod:block/{texture_name}", f"{target}: {name} item sprite unresolved"
        assert (assets / f"textures/block/{texture_name}.png").is_file(), f"{target}: missing {name} sprite"

    lang = load(assets / "lang/en_us.json")
    assert lang.get("block.usefultoolsmod.ectoplasm_lantern") == "Ectoplasm Lantern", (
        f"{target}: player-facing Ectoplasm Lantern rename missing"
    )
    assert all(f"wthit.usefultoolsmod.wraith.{key}" in lang for key in (
        "identity", "warded", "hunting", "weapon_effective", "weapon_immune", "weapon_required"
    )), f"{target}: Wraith WTHIT translations incomplete"

    if target in WTHIT_TARGETS:
        source = root / "src/main/java/com/stonytark/usefultoolsmod/compat/wthit"
        provider = source / "WraithComponentProvider.java"
        assert provider.is_file(), f"{target}: Wraith WTHIT provider missing"
        registration_text = "\n".join(path.read_text(encoding="utf-8") for path in source.glob("UsefulToolsWthit*Plugin.java"))
        assert "new WraithComponentProvider()" in registration_text and "WraithEntity.class" in registration_text, (
            f"{target}: Wraith WTHIT provider is not registered"
        )


def audit_classic() -> None:
    root = ROOT / "1.7.10"
    textures = root / "src/main/resources/assets/usefultoolsmod/textures"
    for name in ("ectoplasm_lantern", "mining_charge"):
        block = textures / f"blocks/{name}.png"
        item = textures / f"items/{name}.png"
        assert block.is_file() and item.is_file(), f"1.7.10-forge: missing split {name} textures"
        assert png_color_type(block) not in (4, 6), f"1.7.10-forge: {name} block face still has alpha"
    soul_source = (root / "src/main/java/com/stonytark/usefultoolsmod/classic/ClassicSoulLantern.java").read_text(encoding="utf-8")
    charge_source = (root / "src/main/java/com/stonytark/usefultoolsmod/classic/ClassicMiningCharge.java").read_text(encoding="utf-8")
    assert "setBlockBounds" in soul_source and "isOpaqueCube() { return false; }" in soul_source
    assert "setBlockBounds" in charge_source and "isOpaqueCube() { return false; }" in charge_source
    assert (root / "src/main/java/com/stonytark/usefultoolsmod/classic/ClassicFeatureItemBlock.java").is_file()
    classic_lang = (root / "src/main/resources/assets/usefultoolsmod/lang/en_US.lang").read_text(encoding="utf-8")
    assert "tile.ectoplasm_lantern.name=Ectoplasm Lantern" in classic_lang, (
        "1.7.10-forge: player-facing Ectoplasm Lantern rename missing"
    )


def main() -> int:
    try:
        audit_classic()
        for version in VERSIONS:
            for loader in LOADERS:
                audit_modern(version, loader)
    except (AssertionError, KeyError, TypeError, ValueError) as error:
        print(f"2.3.1 fix audit FAILED: {error}", file=sys.stderr)
        return 1
    print("2.3.1 fix audit: 16/16 targets passed")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
