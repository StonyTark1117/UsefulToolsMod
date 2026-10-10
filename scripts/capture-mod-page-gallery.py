#!/usr/bin/env python3
"""Capture genuine Useful Tools 2.3.1 gameplay for the project galleries.

This reuses the existing isolated client runner without changing its behavior.
Only the disposable game's screenshots and the selected gallery output are written.
"""

from __future__ import annotations

import argparse
import importlib.util
from pathlib import Path
import subprocess
import sys
import time


ROOT = Path(__file__).resolve().parent.parent
SOURCE = ROOT / "scripts" / "smoke-client.py"
spec = importlib.util.spec_from_file_location("useful_tools_smoke_client", SOURCE)
assert spec and spec.loader
smoke = importlib.util.module_from_spec(spec)
sys.modules[spec.name] = smoke
spec.loader.exec_module(smoke)

OUT = ROOT / "media" / "gallery-2.3.1"
OUT.mkdir(parents=True, exist_ok=True)
original_screenshot = smoke.screenshot


def command(display: str, value: str, delay: float = 0.28) -> None:
    window = smoke.window_id(display, 3)
    smoke.xdo(display, "key", "--window", window, "t")
    time.sleep(0.12)
    smoke.xdo(display, "type", "--window", window, "--delay", "1", f"/{value}")
    smoke.xdo(display, "key", "--window", window, "Return")
    time.sleep(delay)


def window_capture(display: str, name: str) -> None:
    window = smoke.window_id(display, 3)
    target = OUT / f"{name}.png"
    subprocess.run(
        ["magick", "import", "-window", window, str(target)],
        env={**smoke.os.environ, "DISPLAY": display},
        check=True,
    )
    if not smoke.image_is_substantial(target):
        raise RuntimeError(f"empty gallery capture: {target}")
    print(f"gallery capture: {target}", flush=True)


def blocks(display: str, name: str, item_ids: tuple[str, ...]) -> None:
    command(display, "fill -8 200 -2 8 205 3 minecraft:air")
    for index, item_id in enumerate(item_ids):
        x = -5 + index * 2
        command(display, f"setblock {x} 200 0 usefultoolsmod:{item_id}", 0.08)
    command(display, "tp @s 0 201 7 180 16", 0.8)
    window_capture(display, name)


def item_display(display: str, x: int, item_id: str) -> None:
    command(
        display,
        f'summon minecraft:item_display {x} 202 1 '
        '{item:{id:"usefultoolsmod:' + item_id + '",count:1},'
        'billboard:"center",transformation:{scale:[4f,4f,4f]}}',
        0.1,
    )


def items(display: str, name: str, item_ids: tuple[str, ...]) -> None:
    command(display, "kill @e[type=minecraft:item_display]")
    command(display, "fill -8 200 -2 8 205 3 minecraft:air")
    command(display, "fill -7 200 0 7 204 0 minecraft:deepslate_bricks")
    for index, item_id in enumerate(item_ids):
        item_display(display, -5 + index * 2, item_id)
    command(display, "tp @s 0 201 7 180 4", 0.8)
    window_capture(display, name)


def armor(display: str) -> None:
    command(display, "kill @e[type=minecraft:item_display]")
    command(display, "kill @e[type=minecraft:armor_stand]")
    command(display, "fill -8 200 -2 8 205 3 minecraft:air")
    sets = (
        ("rgold", "rgold_sword"),
        ("emerald", "remerald_sword"),
        ("obsidian", "robsidian_sword"),
        ("ecto", "ecto_sword"),
        ("overpower", "overpower_sword"),
    )
    for index, (prefix, sword) in enumerate(sets):
        x = -6 + index * 3
        tag = f"ut_gallery_{index}"
        command(
            display,
            f'summon minecraft:armor_stand {x} 200 0 '
            f'{{NoGravity:1b,ShowArms:1b,NoBasePlate:1b,Tags:["{tag}"]}}',
            0.35,
        )
        for piece, slot in (("boots", "feet"), ("leggings", "legs"),
                            ("chestplate", "chest"), ("helmet", "head")):
            command(display, f"item replace entity @e[tag={tag},limit=1] "
                    f"armor.{slot} with usefultoolsmod:{prefix}_{piece}", 0.35)
        command(display, f"item replace entity @e[tag={tag},limit=1] "
                f"weapon.mainhand with usefultoolsmod:{sword}", 0.35)
    command(display, "tp @s 0 201 8 180 6", 1.0)
    window_capture(display, "current-armor-families")


def world_showcase(display: str) -> None:
    window = smoke.window_id(display, 3)
    size = smoke.xdo(display, "getwindowgeometry", "--shell", window).stdout
    original_width = next(int(line.split("=", 1)[1]) for line in size.splitlines() if line.startswith("WIDTH="))
    original_height = next(int(line.split("=", 1)[1]) for line in size.splitlines() if line.startswith("HEIGHT="))
    smoke.xdo(display, "windowsize", "--sync", window, "1280", "720")
    smoke.xdo(display, "windowmove", "--sync", window, "0", "0")
    try:
        for value in (
            "gamemode creative",
            "gamerule sendCommandFeedback false",
            "kill @e[type=!minecraft:player]",
            "fill -12 200 -12 12 210 12 minecraft:air",
            "fill -12 199 -12 12 199 12 minecraft:smooth_stone",
            "time set noon",
            "weather clear",
            "effect give @s minecraft:night_vision infinite 0 true",
        ):
            command(display, value)
        smoke.xdo(display, "key", "--window", window, "F1")
        blocks(display, "current-ferrous-gold-ores", (
            "rgoldore", "rgold_deepslate_ore", "rgold_nether_ore",
            "rgold_end_ore", "raw_rgold_block", "rgoldblock",
        ))
        blocks(display, "current-material-blocks", (
            "hrblock", "obshard_block", "calcified_amethyst_block",
            "glacial_shard_block", "polished_quartz_block", "polished_prismarine_block",
        ))
        blocks(display, "current-spectral-workshop", (
            "ectoplasm_block", "refined_ectoplasm_block", "spectral_infuser",
            "ectoplasm_lantern", "hglow_block", "mining_charge",
        ))
        items(display, "current-tool-materials", (
            "oak_pickaxe", "andesite_pickaxe", "rflint_pickaxe",
            "coal_pickaxe", "rcrystal_pickaxe", "rgold_pickaxe",
        ))
        items(display, "current-gem-and-endgame-tools", (
            "pprism_pickaxe", "remerald_pickaxe", "robsidian_pickaxe",
            "ecto_pickaxe", "overpower_pickaxe", "hredstone_pickaxe",
        ))
        items(display, "current-explosives-and-spectral-items", (
            "dynamite", "grenade", "sticky_dynamite",
            "remote_detonator", "spectral_resonator", "condensed_ectoplasm",
        ))
        items(display, "current-food-and-unusual-tools", (
            "cake_sword", "honey_sword", "pufferfish_sword",
            "chorus_fruit_sword", "slime_sword", "dragon_breath_sword",
        ))
        armor(display)
    finally:
        smoke.xdo(display, "key", "--window", window, "F1")
        smoke.xdo(display, "windowsize", "--sync", window, str(original_width), str(original_height))


def catalog_pages(display: str) -> None:
    window = smoke.window_id(display, 3)
    window_capture(display, "current-catalog-01")
    for page in range(2, 16):
        smoke.click(display, window, 822, 31)
        time.sleep(0.45)
        window_capture(display, f"current-catalog-{page:02d}")


def gallery_screenshot(display: str, path: Path) -> None:
    original_screenshot(display, path)
    if path.name.endswith("-ghost-wthit.png"):
        world_showcase(display)
    elif path.name.endswith("-inventory.png"):
        catalog_pages(display)


def main() -> int:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--offline", action="store_true")
    args = parser.parse_args()
    smoke.screenshot = gallery_screenshot
    run_args = argparse.Namespace(
        output_dir=OUT,
        client_mod=[],
        shader_pack=None,
        offline=args.offline,
        skip_asset_download=True,
        asset_cache_source=None,
        gradle_property=["usefulToolsJeiRuntime=true", "usefulToolsWthitRuntime=true"],
        timeout=420,
        verbose=False,
        visual_showcase=True,
        inventory_probe=True,
        inventory_search="@usefultoolsmod",
        spectral_render_check=False,
    )
    return 0 if smoke.run_target("1.21.1-neoforge", smoke.TARGETS["1.21.1-neoforge"], run_args) else 1


if __name__ == "__main__":
    raise SystemExit(main())
