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

    charge_entity = (
        root / "src/main/java/com/stonytark/usefultoolsmod/block/entity/MiningChargeBlockEntity.java"
    ).read_text(encoding="utf-8")
    safe_missing_fuse = (
        'getIntOr("Fuse",-1)' in charge_entity
        or 'getIntOr("Fuse", -1)' in charge_entity
        or ('contains("Fuse")' in charge_entity and ': -1' in charge_entity)
    )
    assert safe_missing_fuse, f"{target}: missing Fuse data arms the Mining Charge"
    for contract in ("primeOwner", "linkOwner", "detonateOwner"):
        assert contract in charge_entity, f"{target}: Mining Charge {contract} contract is not directly testable"
    for key in ('"Fuse"', '"Channel"', '"Owner"'):
        assert charge_entity.count(key) >= 2, f"{target}: Mining Charge does not persist {key}"

    wraith_entity = (
        root / "src/main/java/com/stonytark/usefultoolsmod/entity/custom/WraithEntity.java"
    ).read_text(encoding="utf-8")
    assert "Config.ghostEnabled && Config.wraithEnabled" not in wraith_entity, (
        f"{target}: Wraith natural spawning incorrectly depends on the Ghost toggle"
    )
    assert "Config.wraithEnabled" in wraith_entity and "Config.wraithSpawnChance" in wraith_entity, (
        f"{target}: Wraith natural spawning is missing its own enable/chance controls"
    )
    # Each tuple is one required clause whose entries are mapping-specific
    # alternatives (Mojmap vs Yarn).
    wraith_contracts = {
        "flight controller": (("FlyingMoveControl", "FlightMoveControl"),
                              ("FlyingPathNavigation", "BirdNavigation"), ("setNoGravity(true)",)),
        "hostile targeting": (("NearestAttackableTargetGoal", "ActiveTargetGoal"),
                              ("Player.class", "PlayerEntity.class"), ("GhostEntity.class",)),
        "lunge behavior": (("lungeCooldown",), ("setDeltaMovement(", "setVelocity(lunge)"),
                           ("hasLineOfSight(target)", "canSee(target)")),
        "lantern ward response": (("hasActiveLantern", "SoulLanternWard.activeWithin"),
                                  ("level.hasNeighborSignal(pos)", "world.isReceivingRedstonePower(pos)",
                                   "SoulLanternWard.nearest")),
        "fire clearing": (("clearFire()", "extinguish()"),),
        "damage rules": (("BYPASSES_INVULNERABILITY",), ("IS_EXPLOSION",),
                         ("EctoplasmInfusionHelper.isInfused",)),
        "loot drops": (("CONDENSED_ECTOPLASM",), ("ECTOPLASM",)),
        "sounds": (("WRAITH_AMBIENT", "wraithAmbient()"),
                   ("WRAITH_HURT", "wraithHurt()"), ("WRAITH_DEATH", "wraithDeath()")),
        "encounter advancement": (('award(player, "spectral/encounter_wraith")',),),
    }
    for contract, clauses in wraith_contracts.items():
        assert all(any(token in wraith_entity for token in alternatives) for alternatives in clauses), (
            f"{target}: Wraith {contract} contract is incomplete"
        )
    assert "dropCustomDeathLoot" in wraith_entity or "dropLoot" in wraith_entity, (
        f"{target}: Wraith custom loot hook missing"
    )
    # Mojmap Forge 1.20.x supplies the looting level as the override
    # parameter; Yarn and newer Mojmap versions calculate it explicitly.
    assert "int looting" in wraith_entity or "Enchantments.LOOTING" in wraith_entity, (
        f"{target}: Wraith looting behavior missing"
    )

    java_root = root / "src/main/java/com/stonytark/usefultoolsmod"
    entities_source = (java_root / "entity/ModEntities.java").read_text(encoding="utf-8")
    items_source = (java_root / "item/ModItems.java").read_text(encoding="utf-8")
    renderer_source = (java_root / "client/ModEntityRenderers.java").read_text(encoding="utf-8")
    renderer_source += (java_root / "UsefultoolsMod.java").read_text(encoding="utf-8")
    client_entrypoint = java_root / "UsefultoolsModClient.java"
    if client_entrypoint.is_file():
        renderer_source += client_entrypoint.read_text(encoding="utf-8")
    assert "WRAITH" in entities_source and "WraithEntity" in entities_source, (
        f"{target}: Wraith entity registration missing"
    )
    assert "WRAITH_SPAWN_EGG" in items_source, f"{target}: Wraith spawn egg registration missing"
    assert "WRAITH" in renderer_source and "WraithRenderer" in renderer_source, (
        f"{target}: Wraith renderer registration missing"
    )
    assert (java_root / "entity/client/WraithRenderer.java").is_file(), (
        f"{target}: Wraith renderer implementation missing"
    )
    wraith_egg_model = assets / "models/item/wraith_spawn_egg.json"
    if version.startswith("26."):
        candidates = (
            root / "src/generated/resources/client/assets/usefultoolsmod/models/item/wraith_spawn_egg.json",
            root / "src/main/generated/assets/usefultoolsmod/models/item/wraith_spawn_egg.json",
        )
        wraith_egg_model = next((path for path in candidates if path.is_file()), candidates[0])
    for asset in (
        assets / "textures/entity/ghost/wraith.png",
        assets / "sounds/entity/wraith_ambient.ogg",
        assets / "sounds/entity/wraith_hurt.ogg",
        assets / "sounds/entity/wraith_death.ogg",
        wraith_egg_model,
    ):
        assert asset.is_file(), f"{target}: missing Wraith asset {asset.relative_to(root)}"

    advancement_dir = "advancements" if version in ("1.20.1", "1.20.2") else "advancement"
    encounter = root / f"src/main/resources/data/usefultoolsmod/{advancement_dir}/spectral/encounter_wraith.json"
    assert encounter.is_file(), f"{target}: Wraith encounter advancement missing"
    encounter_data = load(encounter)
    assert encounter_data.get("criteria", {}).get("encountered", {}).get("trigger") == "minecraft:impossible", (
        f"{target}: Wraith event-driven advancement criterion changed"
    )
    advancement_source = (java_root / "util/ModAdvancements.java").read_text(encoding="utf-8")
    assert "getRemainingCriteria" in advancement_source or "getUnobtainedCriteria" in advancement_source, (
        f"{target}: event-driven advancement awards do not use the advancement's actual criteria"
    )
    assert 'award(advancement, "trigger")' not in advancement_source, (
        f"{target}: Mojmap advancement helper still hardcodes a nonexistent criterion"
    )
    assert 'grantCriterion(advancement, "trigger")' not in advancement_source, (
        f"{target}: Yarn advancement helper still hardcodes a nonexistent criterion"
    )

    gametest_source = (java_root / "gametest/UsefulToolsGameTests.java").read_text(encoding="utf-8")
    for runtime_contract in (
        "assertWraithRuntimeContracts",
        "Wraith must use flight rather than Ghost gravity",
        "Wraith must clear fire every tick",
        "Wraith must lunge toward a visible mid-range target",
        "Wraith encounter award path must complete its advancement",
    ):
        assert runtime_contract in gametest_source, (
            f"{target}: Wraith GameTest is missing {runtime_contract}"
        )
    for item in ("CONDENSED_ECTOPLASM", "ECTOPLASM"):
        assert (
            f"assertItemEntityPresent(ModItems.{item}" in gametest_source
            or f"expectItemAt(ModItems.{item}" in gametest_source
        ), f"{target}: Wraith GameTest does not assert the {item} loot drop"

    resonator_source = (java_root / "item/custom/SpectralResonatorItem.java").read_text(encoding="utf-8")
    assert "e instanceof GhostEntity || e instanceof WraithEntity" in resonator_source, (
        f"{target}: Spectral Resonator does not target both spirit types"
    )

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

    wraith_egg = (
        root / "src/main/java/com/stonytark/usefultoolsmod/classic/ClassicWraithSpawnEgg.java"
    ).read_text(encoding="utf-8")
    assert 'setTextureName("spawn_egg")' in wraith_egg, (
        "1.7.10-forge: Wraith egg must use vanilla's complete base/overlay texture pair"
    )
    assert "getColorFromItemStack" in wraith_egg, (
        "1.7.10-forge: Wraith egg is missing its two render-pass colors"
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
