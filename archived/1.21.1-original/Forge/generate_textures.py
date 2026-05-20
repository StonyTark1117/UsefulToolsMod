#!/usr/bin/env python3
"""
Texture generator for UsefulToolsMod.
Extracts vanilla MC textures and mod base textures, then applies HSV color
remapping to produce vibrant, distinctive tool/armor/material textures.

Rough/rough tools use robsidian PNGs as the shape framework.
Polished/higher-tier tools use vanilla tool shapes.
Armor uses vanilla armor shapes.
Overpower and stone rock variants are NOT touched.
"""

import colorsys
import os
import zipfile
from pathlib import Path
from PIL import Image

# ---------------------------------------------------------------------------
# Paths
# ---------------------------------------------------------------------------
CLIENT_JAR = os.path.expanduser(
    "~/.gradle/caches/forge_gradle/minecraft_repo/versions/1.21.1/client-extra.jar"
)
TEX_ROOT = Path("src/main/resources/assets/usefultoolsmod/textures")
ITEM_DIR = TEX_ROOT / "item"
ARMOR_DIR = TEX_ROOT / "models" / "armor"

# ---------------------------------------------------------------------------
# Vanilla texture paths inside the jar
# ---------------------------------------------------------------------------
V_ITEM = "assets/minecraft/textures/item"
V_ARMOR = "assets/minecraft/textures/models/armor"

TOOL_TYPES = ["sword", "pickaxe", "axe", "shovel", "hoe"]
ARMOR_PIECES = ["helmet", "chestplate", "leggings", "boots"]


def extract_png(jar_path: str, internal_path: str) -> Image.Image:
    """Extract a PNG from a jar (zip) file and return as RGBA Image."""
    with zipfile.ZipFile(jar_path, "r") as zf:
        with zf.open(internal_path) as f:
            img = Image.open(f).convert("RGBA")
            img.load()
            return img


def load_local_png(path: str | Path) -> Image.Image:
    """Load a local PNG file as RGBA Image."""
    img = Image.open(path).convert("RGBA")
    img.load()
    return img


# ---------------------------------------------------------------------------
# Color remapping
# ---------------------------------------------------------------------------

def remap_hsv(img: Image.Image, *, target_hue: float | None = None,
              sat_mult: float = 1.0, sat_set: float | None = None,
              val_mult: float = 1.0, val_add: float = 0.0,
              hue_shift: float | None = None) -> Image.Image:
    """
    Per-pixel HSV remapping.  Preserves alpha.

    target_hue : if set, all pixels get this hue (0-360)
    hue_shift  : if set (and target_hue is None), shift existing hue by this amount
    sat_mult   : multiply saturation
    sat_set    : if set, force saturation to this value (0-1)
    val_mult   : multiply value
    val_add    : add to value after mult
    """
    out = img.copy()
    px = out.load()
    w, h = out.size

    for y in range(h):
        for x in range(w):
            r, g, b, a = px[x, y]
            if a == 0:
                continue

            rn, gn, bn = r / 255.0, g / 255.0, b / 255.0
            h_val, s_val, v_val = colorsys.rgb_to_hsv(rn, gn, bn)

            # Hue
            if target_hue is not None:
                h_val = target_hue / 360.0
            elif hue_shift is not None:
                h_val = (h_val + hue_shift / 360.0) % 1.0

            # Saturation
            if sat_set is not None:
                s_val = sat_set
            else:
                s_val = min(1.0, s_val * sat_mult)

            # Value
            v_val = min(1.0, max(0.0, v_val * val_mult + val_add))

            rn, gn, bn = colorsys.hsv_to_rgb(h_val, s_val, v_val)
            px[x, y] = (int(rn * 255), int(gn * 255), int(bn * 255), a)

    return out


def blend_tint(img: Image.Image, tint_rgb: tuple[int, int, int],
               strength: float = 0.4) -> Image.Image:
    """Blend each pixel toward a tint color.  Preserves luminance shape."""
    out = img.copy()
    px = out.load()
    w, h = out.size
    tr, tg, tb = tint_rgb

    for y in range(h):
        for x in range(w):
            r, g, b, a = px[x, y]
            if a == 0:
                continue
            nr = int(r * (1 - strength) + tr * strength)
            ng = int(g * (1 - strength) + tg * strength)
            nb = int(b * (1 - strength) + tb * strength)
            px[x, y] = (min(255, nr), min(255, ng), min(255, nb), a)

    return out


def composite_with_overlay(base: Image.Image, overlay: Image.Image) -> Image.Image:
    """Composite a base texture with its overlay to produce a fully opaque result.
    Used for leather armor which splits into tintable base + detail overlay."""
    result = base.copy()
    result.paste(overlay, (0, 0), overlay)
    return result


def pattern_overlay(armor_img: Image.Image, pattern_img: Image.Image,
                    strength: float = 0.15) -> Image.Image:
    """Tile a pattern texture across an armor texture and blend at given strength.
    Only affects pixels that are already opaque in the armor image."""
    out = armor_img.copy()
    px = out.load()
    aw, ah = armor_img.size
    pw, ph = pattern_img.size
    pat_px = pattern_img.load()

    for y in range(ah):
        for x in range(aw):
            r, g, b, a = px[x, y]
            if a == 0:
                continue
            pr, pg, pb, pa = pat_px[x % pw, y % ph]
            if pa == 0:
                continue
            nr = int(r * (1 - strength) + pr * strength)
            ng = int(g * (1 - strength) + pg * strength)
            nb = int(b * (1 - strength) + pb * strength)
            px[x, y] = (min(255, nr), min(255, ng), min(255, nb), a)

    return out


# ---------------------------------------------------------------------------
# Helper: generate a full tool set from a base
# ---------------------------------------------------------------------------

def gen_tools(bases: dict[str, Image.Image], prefix: str,
              remap_fn=None, **remap_kwargs):
    """Generate 5 tool textures from base images. remap_fn defaults to remap_hsv."""
    count = 0
    if remap_fn is None:
        remap_fn = remap_hsv
    for tool in TOOL_TYPES:
        out = remap_fn(bases[tool], **remap_kwargs)
        out.save(ITEM_DIR / f"{prefix}_{tool}.png")
        count += 1
    print(f"  {prefix}: {count} tools")
    return count


def gen_armor_items(bases: dict[str, Image.Image], prefix: str,
                    remap_fn=None, **remap_kwargs):
    """Generate 4 armor item icons from base images."""
    count = 0
    if remap_fn is None:
        remap_fn = remap_hsv
    for piece in ARMOR_PIECES:
        out = remap_fn(bases[piece], **remap_kwargs)
        out.save(ITEM_DIR / f"{prefix}_{piece}.png")
        count += 1
    print(f"  {prefix}: {count} armor items")
    return count


def gen_armor_layers(base_l1: Image.Image, base_l2: Image.Image, prefix: str,
                     remap_fn=None, **remap_kwargs):
    """Generate layer_1 and layer_2 armor model textures."""
    if remap_fn is None:
        remap_fn = remap_hsv
    l1 = remap_fn(base_l1, **remap_kwargs)
    l2 = remap_fn(base_l2, **remap_kwargs)
    l1.save(ARMOR_DIR / f"{prefix}_layer_1.png")
    l2.save(ARMOR_DIR / f"{prefix}_layer_2.png")
    print(f"  {prefix}: 2 armor layers")
    return 2


# ---------------------------------------------------------------------------
# Composite remap functions for multi-step transforms
# ---------------------------------------------------------------------------

def remap_rgold_tool(img, **_):
    """Rgold tools: iron base blended with gold tint, vibrant."""
    out = blend_tint(img, (218, 175, 42), strength=0.55)
    return remap_hsv(out, hue_shift=5, sat_mult=1.4, val_mult=1.05)


def remap_rgold_armor(img, **_):
    """Rgold armor items: golden base with slight iron tint, vibrant."""
    out = blend_tint(img, (170, 170, 180), strength=0.3)
    return remap_hsv(out, hue_shift=5, sat_mult=1.3, val_mult=1.0)


def remap_rgold_layers(img, **_):
    """Rgold armor layers: iron base with strong gold tint."""
    out = blend_tint(img, (218, 165, 32), strength=0.55)
    return remap_hsv(out, hue_shift=5, sat_mult=1.3, val_mult=1.05)


def remap_hred_tool(img, **_):
    """Hardened redstone tools: vibrant deep red."""
    return remap_hsv(img, target_hue=0, sat_set=0.85, val_mult=0.8)


def remap_hred_armor(img, **_):
    """Hardened redstone armor: vibrant red."""
    return remap_hsv(img, target_hue=0, sat_set=0.8, val_mult=0.85)


def remap_coal_tool(img, **_):
    """Coal tools: very dark grey, low saturation."""
    return remap_hsv(img, target_hue=0, sat_set=0.05, val_mult=0.35)


def remap_coal_armor(img, **_):
    """Coal armor: dark charcoal grey."""
    return remap_hsv(img, target_hue=0, sat_set=0.05, val_mult=0.35)


def remap_fni_tool(img, **_):
    """FNI tools: iron-flint blend, warm grey with orange accent."""
    out = blend_tint(img, (180, 140, 100), strength=0.35)
    return remap_hsv(out, hue_shift=10, sat_mult=1.3, val_mult=0.85)


def remap_fni_armor(img, **_):
    """FNI armor: warm iron-flint blend."""
    out = blend_tint(img, (180, 140, 100), strength=0.3)
    return remap_hsv(out, hue_shift=10, sat_mult=1.2, val_mult=0.88)


def remap_obsidian_armor(img, **_):
    """Obsidian armor: deep purple-black, vibrant."""
    return remap_hsv(img, target_hue=275, sat_set=0.6, val_mult=0.45)


def remap_emerald_armor(img, **_):
    """Emerald armor: vivid green."""
    return remap_hsv(img, target_hue=140, sat_set=0.8, val_mult=0.85)


# ---------------------------------------------------------------------------
# Rough tool color configs (applied to robsidian base shapes)
# ---------------------------------------------------------------------------

ROUGH_TOOL_CONFIGS = {
    # name: remap_kwargs or callable
    "remerald": {"target_hue": 140, "sat_set": 0.85, "val_mult": 0.9},
    "ramethyst": {"target_hue": 280, "sat_set": 0.7, "val_mult": 0.8},
    "rquartz": {"target_hue": 35, "sat_set": 0.12, "val_mult": 1.2, "val_add": 0.08},
    "rprism": {"target_hue": 170, "sat_set": 0.7, "val_mult": 0.75},
    "rflint": {"target_hue": 30, "sat_set": 0.15, "val_mult": 0.5},
    "snow": {"target_hue": 200, "sat_set": 0.15, "val_mult": 1.3, "val_add": 0.15},
    "rraw_gold": {"target_hue": 45, "sat_set": 0.8, "val_mult": 0.9},
    "rraw_copper": {"target_hue": 25, "sat_set": 0.75, "val_mult": 0.8},
    "rraw_iron": {"target_hue": 25, "sat_set": 0.12, "val_mult": 0.7},
    "rraw_rgold": {"target_hue": 42, "sat_set": 0.6, "val_mult": 0.8},
    "rscrap": {"target_hue": 30, "sat_set": 0.25, "val_mult": 0.4},
    "recto": {"target_hue": 175, "sat_set": 0.4, "val_mult": 0.85},
}


# ---------------------------------------------------------------------------
# Polished tool color configs (applied to vanilla tool shapes)
# ---------------------------------------------------------------------------

POLISHED_TOOL_CONFIGS = {
    # name: (vanilla_base_prefix, remap_kwargs_or_callable)
    # Diamond-based polished tools
    "pemerald": ("diamond", {"target_hue": 140, "sat_set": 0.8, "val_mult": 0.9}),
    "rlapis": ("diamond", {"target_hue": 230, "sat_set": 0.8, "val_mult": 0.75}),
    "camethyst": ("diamond", {"target_hue": 280, "sat_set": 0.65, "val_mult": 0.85}),
    "pquartz": ("diamond", {"target_hue": 35, "sat_set": 0.1, "val_mult": 1.2, "val_add": 0.08}),
    "pprism": ("diamond", {"target_hue": 170, "sat_set": 0.65, "val_mult": 0.8}),
    "ecto": ("diamond", {"target_hue": 175, "sat_set": 0.5, "val_mult": 0.8}),
    "ice": ("diamond", {"target_hue": 195, "sat_set": 0.65, "val_mult": 0.95}),
    # Iron-based polished tools
    "hredstone": ("iron", "remap_hred_tool"),
    "hglowstone": ("iron", {"target_hue": 41, "sat_set": 0.75, "val_mult": 1.1}),
    "coal": ("iron", "remap_coal_tool"),
    "fni": ("iron", "remap_fni_tool"),
    "rgold": ("iron", "remap_rgold_tool"),
    # Wooden-based tools
    "leather": ("wooden", {"target_hue": 30, "sat_set": 0.45, "val_mult": 0.60}),
    "cake": ("wooden", {"target_hue": 30, "sat_set": 0.45, "val_mult": 0.75}),
    "bread": ("wooden", {"target_hue": 42, "sat_set": 0.50, "val_mult": 0.85}),
    "dried_kelp": ("wooden", {"target_hue": 110, "sat_set": 0.35, "val_mult": 0.35}),
    "rotten_flesh": ("wooden", {"target_hue": 85, "sat_set": 0.45, "val_mult": 0.40}),
    "sweet_berry": ("wooden", {"target_hue": 350, "sat_set": 0.70, "val_mult": 0.55}),
    "pumpkin_pie": ("wooden", {"target_hue": 25, "sat_set": 0.70, "val_mult": 0.75}),
    "mushroom": ("wooden", {"target_hue": 0, "sat_set": 0.70, "val_mult": 0.60}),
    # Iron-based food tools
    "melon": ("iron", {"target_hue": 5, "sat_set": 0.70, "val_mult": 0.70}),
    "pufferfish": ("iron", {"target_hue": 55, "sat_set": 0.75, "val_mult": 0.85}),
    "honey": ("iron", {"target_hue": 40, "sat_set": 0.80, "val_mult": 0.90}),
    # Diamond-based food tools
    "chorus_fruit": ("diamond", {"target_hue": 290, "sat_set": 0.55, "val_mult": 0.70}),
    "golden_apple": ("diamond", {"target_hue": 50, "sat_set": 0.85, "val_mult": 0.90}),
    # Vanilla material tools
    "paper": ("wooden", {"target_hue": 45, "sat_set": 0.08, "val_mult": 1.3, "val_add": 0.1}),
    "feather": ("wooden", {"target_hue": 0, "sat_set": 0.03, "val_mult": 1.35, "val_add": 0.15}),
    "glass": ("wooden", {"target_hue": 200, "sat_set": 0.15, "val_mult": 1.4, "val_add": 0.1}),
    "cactus": ("wooden", {"target_hue": 120, "sat_set": 0.65, "val_mult": 0.55}),
    "sponge": ("wooden", {"target_hue": 50, "sat_set": 0.70, "val_mult": 0.85}),
    "bone": ("wooden", {"target_hue": 45, "sat_set": 0.10, "val_mult": 1.15, "val_add": 0.05}),
    "clay": ("wooden", {"target_hue": 20, "sat_set": 0.20, "val_mult": 0.70}),
    "nether_wart": ("wooden", {"target_hue": 350, "sat_set": 0.70, "val_mult": 0.40}),
    "brick": ("iron", {"target_hue": 15, "sat_set": 0.65, "val_mult": 0.65}),
    "nether_brick": ("iron", {"target_hue": 330, "sat_set": 0.40, "val_mult": 0.35}),
    "pointed_dripstone": ("iron", {"target_hue": 30, "sat_set": 0.35, "val_mult": 0.75}),
    "copper": ("iron", {"target_hue": 25, "sat_set": 0.70, "val_mult": 0.80}),
    "phantom": ("iron", {"target_hue": 140, "sat_set": 0.15, "val_mult": 1.05, "val_add": 0.05}),
    "magma_cream": ("iron", {"target_hue": 30, "sat_set": 0.80, "val_mult": 0.85}),
    "slime": ("iron", {"target_hue": 90, "sat_set": 0.70, "val_mult": 0.80}),
    "blaze": ("iron", {"target_hue": 35, "sat_set": 0.80, "val_mult": 0.95}),
    "nautilus": ("iron", {"target_hue": 20, "sat_set": 0.40, "val_mult": 0.75}),
    "purpur": ("iron", {"target_hue": 290, "sat_set": 0.50, "val_mult": 0.70}),
    "ghast_tear": ("diamond", {"target_hue": 200, "sat_set": 0.10, "val_mult": 1.3, "val_add": 0.1}),
    "eye_of_ender": ("diamond", {"target_hue": 160, "sat_set": 0.70, "val_mult": 0.70}),
    "shulker": ("diamond", {"target_hue": 310, "sat_set": 0.45, "val_mult": 0.70}),
    "echo_shard": ("diamond", {"target_hue": 180, "sat_set": 0.55, "val_mult": 0.45}),
    "dragon_breath": ("diamond", {"target_hue": 320, "sat_set": 0.70, "val_mult": 0.65}),
}


# ---------------------------------------------------------------------------
# Armor configs (item icons + model layers)
# ---------------------------------------------------------------------------

# (vanilla_item_base, vanilla_layer_base, remap_kwargs_or_callable)
ARMOR_CONFIGS = {
    # Diamond-based armor
    "rlapis": ("diamond", "diamond", {"target_hue": 230, "sat_set": 0.8, "val_mult": 0.75}),
    "camethyst": ("diamond", "diamond", {"target_hue": 280, "sat_set": 0.6, "val_mult": 0.85}),
    "pquartz": ("diamond", "diamond", {"target_hue": 35, "sat_set": 0.1, "val_mult": 1.2, "val_add": 0.08}),
    "ice": ("diamond", "diamond", {"target_hue": 195, "sat_set": 0.65, "val_mult": 1.05}),
    "pprism": ("diamond", "diamond", {"target_hue": 170, "sat_set": 0.65, "val_mult": 0.8}),
    "ecto": ("diamond", "diamond", {"target_hue": 175, "sat_set": 0.5, "val_mult": 0.8}),
    "obsidian": ("netherite", "netherite", "remap_obsidian_armor"),
    "emerald": ("diamond", "diamond", "remap_emerald_armor"),
    # Iron-based armor
    "hred": ("iron", "iron", "remap_hred_armor"),
    "hglow": ("iron", "iron", {"target_hue": 41, "sat_set": 0.7, "val_mult": 1.1}),
    "coal": ("iron", "iron", "remap_coal_armor"),
    "fni": ("iron", "iron", "remap_fni_armor"),
    # Special base armors
    "rgold": ("golden", "iron", "remap_rgold"),
    # Leather-based armor
    "cake": ("leather", "leather", {"target_hue": 30, "sat_set": 0.45, "val_mult": 0.75}),
    "bread": ("leather", "leather", {"target_hue": 42, "sat_set": 0.50, "val_mult": 0.85}),
    "dried_kelp": ("leather", "leather", {"target_hue": 110, "sat_set": 0.35, "val_mult": 0.35}),
    "rotten_flesh": ("leather", "leather", {"target_hue": 85, "sat_set": 0.45, "val_mult": 0.40}),
    "sweet_berry": ("leather", "leather", {"target_hue": 350, "sat_set": 0.70, "val_mult": 0.55}),
    "pumpkin_pie": ("leather", "leather", {"target_hue": 25, "sat_set": 0.70, "val_mult": 0.75}),
    "mushroom": ("iron", "iron", {"target_hue": 0, "sat_set": 0.70, "val_mult": 0.60}),
    "melon": ("iron", "iron", {"target_hue": 5, "sat_set": 0.70, "val_mult": 0.70}),
    "pufferfish": ("iron", "iron", {"target_hue": 55, "sat_set": 0.75, "val_mult": 0.85}),
    "honey": ("iron", "iron", {"target_hue": 40, "sat_set": 0.80, "val_mult": 0.90}),
    "chorus_fruit": ("diamond", "diamond", {"target_hue": 290, "sat_set": 0.55, "val_mult": 0.70}),
    "golden_apple": ("diamond", "diamond", {"target_hue": 50, "sat_set": 0.85, "val_mult": 0.90}),
    # Vanilla material armor
    "rabbit_hide": ("leather", "leather", {"target_hue": 30, "sat_set": 0.35, "val_mult": 0.60}),
    "cactus": ("leather", "leather", {"target_hue": 120, "sat_set": 0.65, "val_mult": 0.55}),
    "bone": ("iron", "iron", {"target_hue": 45, "sat_set": 0.10, "val_mult": 1.15, "val_add": 0.05}),
    "clay": ("iron", "iron", {"target_hue": 20, "sat_set": 0.20, "val_mult": 0.70}),
    "brick": ("iron", "iron", {"target_hue": 15, "sat_set": 0.65, "val_mult": 0.65}),
    "nether_brick": ("iron", "iron", {"target_hue": 330, "sat_set": 0.40, "val_mult": 0.35}),
    "copper": ("iron", "iron", {"target_hue": 25, "sat_set": 0.70, "val_mult": 0.80}),
    "phantom": ("iron", "iron", {"target_hue": 140, "sat_set": 0.15, "val_mult": 1.05, "val_add": 0.05}),
    "magma_cream": ("iron", "iron", {"target_hue": 30, "sat_set": 0.80, "val_mult": 0.85}),
    "slime": ("iron", "iron", {"target_hue": 90, "sat_set": 0.70, "val_mult": 0.80}),
    "blaze": ("iron", "iron", {"target_hue": 35, "sat_set": 0.80, "val_mult": 0.95}),
    "nautilus": ("iron", "iron", {"target_hue": 20, "sat_set": 0.40, "val_mult": 0.75}),
    "purpur": ("iron", "iron", {"target_hue": 290, "sat_set": 0.50, "val_mult": 0.70}),
    "ghast_tear": ("diamond", "diamond", {"target_hue": 200, "sat_set": 0.10, "val_mult": 1.3, "val_add": 0.1}),
    "eye_of_ender": ("diamond", "diamond", {"target_hue": 160, "sat_set": 0.70, "val_mult": 0.70}),
    "shulker": ("diamond", "diamond", {"target_hue": 310, "sat_set": 0.45, "val_mult": 0.70}),
    "turtle_scute": ("diamond", "diamond", {"target_hue": 100, "sat_set": 0.50, "val_mult": 0.70}),
    "echo_shard": ("diamond", "diamond", {"target_hue": 180, "sat_set": 0.55, "val_mult": 0.45}),
    "dragon_breath": ("diamond", "diamond", {"target_hue": 320, "sat_set": 0.70, "val_mult": 0.65}),
}

# Food armor names mapped to their vanilla food item texture filename
FOOD_PATTERNS = {
    "cake": "cake.png",
    "bread": "bread.png",
    "dried_kelp": "dried_kelp.png",
    "rotten_flesh": "rotten_flesh.png",
    "sweet_berry": "sweet_berries.png",
    "pumpkin_pie": "pumpkin_pie.png",
    "mushroom": "mushroom_stew.png",
    "melon": "melon_slice.png",
    "pufferfish": "pufferfish.png",
    "honey": "honey_bottle.png",
    "chorus_fruit": "chorus_fruit.png",
    "golden_apple": "golden_apple.png",
}


# ---------------------------------------------------------------------------
# Wood variant tool configs (applied to vanilla wooden_* tool shapes)
# ---------------------------------------------------------------------------

WOOD_TOOL_CONFIGS = {
    # name: remap_kwargs
    "oak": {"target_hue": 35, "sat_set": 0.45, "val_mult": 0.65},
    "spruce": {"target_hue": 28, "sat_set": 0.50, "val_mult": 0.40},
    "birch": {"target_hue": 45, "sat_set": 0.20, "val_mult": 1.0},
    "jungle": {"target_hue": 20, "sat_set": 0.55, "val_mult": 0.60},
    "acacia": {"target_hue": 20, "sat_set": 0.65, "val_mult": 0.70},
    "dark_oak": {"target_hue": 30, "sat_set": 0.45, "val_mult": 0.30},
    "mangrove": {"target_hue": 10, "sat_set": 0.60, "val_mult": 0.50},
    "cherry": {"target_hue": 340, "sat_set": 0.40, "val_mult": 0.85},
    "bamboo": {"target_hue": 55, "sat_set": 0.35, "val_mult": 0.80},
    "crimson": {"target_hue": 330, "sat_set": 0.70, "val_mult": 0.45},
    "warped": {"target_hue": 170, "sat_set": 0.65, "val_mult": 0.55},
}


# ---------------------------------------------------------------------------
# Resolve remap function
# ---------------------------------------------------------------------------

NAMED_REMAPS = {
    "remap_rgold_tool": remap_rgold_tool,
    "remap_rgold_armor": remap_rgold_armor,
    "remap_rgold_layers": remap_rgold_layers,
    "remap_hred_tool": remap_hred_tool,
    "remap_hred_armor": remap_hred_armor,
    "remap_coal_tool": remap_coal_tool,
    "remap_coal_armor": remap_coal_armor,
    "remap_fni_tool": remap_fni_tool,
    "remap_fni_armor": remap_fni_armor,
    "remap_obsidian_armor": remap_obsidian_armor,
    "remap_emerald_armor": remap_emerald_armor,
    "remap_rgold": remap_rgold_armor,
}


def resolve_remap(spec):
    """Given a dict of kwargs or a string function name, return (fn, kwargs)."""
    if isinstance(spec, str):
        return NAMED_REMAPS[spec], {}
    elif isinstance(spec, dict):
        return remap_hsv, spec
    else:
        raise ValueError(f"Unknown remap spec: {spec}")


# ---------------------------------------------------------------------------
# Generation functions
# ---------------------------------------------------------------------------

def generate_rough_tools():
    """Generate all rough/rough tool textures using robsidian as base shape."""
    print("=== Rough Tools (robsidian base) ===")

    # Load robsidian base shapes
    robsidian = {}
    for tool in TOOL_TYPES:
        robsidian[tool] = load_local_png(ITEM_DIR / f"robsidian_{tool}.png")

    total = 0
    for name, spec in ROUGH_TOOL_CONFIGS.items():
        fn, kwargs = resolve_remap(spec)
        total += gen_tools(robsidian, name, remap_fn=fn, **kwargs)

    return total


def generate_polished_tools():
    """Generate all polished/higher-tier tool textures using vanilla tool shapes."""
    print("\n=== Polished Tools (vanilla base) ===")

    # Cache vanilla tool bases
    vanilla_tools = {}
    for base_prefix in ("diamond", "iron", "golden", "netherite", "wooden"):
        vanilla_tools[base_prefix] = {}
        for tool in TOOL_TYPES:
            vanilla_tools[base_prefix][tool] = extract_png(
                CLIENT_JAR, f"{V_ITEM}/{base_prefix}_{tool}.png"
            )

    total = 0
    for name, (base_prefix, spec) in POLISHED_TOOL_CONFIGS.items():
        fn, kwargs = resolve_remap(spec)
        total += gen_tools(vanilla_tools[base_prefix], name, remap_fn=fn, **kwargs)

    return total


def generate_armor_items_all():
    """Generate all armor item icon textures."""
    print("\n=== Armor Item Icons ===")

    # Cache vanilla armor item bases
    vanilla_armor = {}
    for base_prefix in ("diamond", "iron", "golden", "netherite", "chainmail", "leather"):
        vanilla_armor[base_prefix] = {}
        for piece in ARMOR_PIECES:
            base = extract_png(CLIENT_JAR, f"{V_ITEM}/{base_prefix}_{piece}.png")
            # Composite leather overlays to eliminate transparency
            if base_prefix == "leather":
                overlay = extract_png(
                    CLIENT_JAR, f"{V_ITEM}/{base_prefix}_{piece}_overlay.png"
                )
                base = composite_with_overlay(base, overlay)
            vanilla_armor[base_prefix][piece] = base

    # Load food pattern textures
    food_pats = {}
    for name, food_tex in FOOD_PATTERNS.items():
        food_pats[name] = extract_png(CLIENT_JAR, f"{V_ITEM}/{food_tex}")

    total = 0
    for name, (item_base, _, spec) in ARMOR_CONFIGS.items():
        fn, kwargs = resolve_remap(spec)
        total += gen_armor_items(vanilla_armor[item_base], name, remap_fn=fn, **kwargs)

        # Apply food pattern overlay (recolored to match armor palette)
        if name in food_pats:
            recolored_pat = fn(food_pats[name], **kwargs)
            for piece in ARMOR_PIECES:
                path = ITEM_DIR / f"{name}_{piece}.png"
                armor = load_local_png(path)
                result = pattern_overlay(armor, recolored_pat, strength=0.2)
                result.save(path)

    return total


def generate_armor_layers_all():
    """Generate all armor model layer textures."""
    print("\n=== Armor Model Layers ===")

    # Cache vanilla armor layer bases
    vanilla_layers = {}
    for base_prefix in ("diamond", "iron", "gold", "netherite", "chainmail", "leather"):
        vanilla_layers[base_prefix] = (
            extract_png(CLIENT_JAR, f"{V_ARMOR}/{base_prefix}_layer_1.png"),
            extract_png(CLIENT_JAR, f"{V_ARMOR}/{base_prefix}_layer_2.png"),
        )

    # Composite leather layer overlays to eliminate transparency
    leather_l1_overlay = extract_png(CLIENT_JAR, f"{V_ARMOR}/leather_layer_1_overlay.png")
    leather_l2_overlay = extract_png(CLIENT_JAR, f"{V_ARMOR}/leather_layer_2_overlay.png")
    vanilla_layers["leather"] = (
        composite_with_overlay(vanilla_layers["leather"][0], leather_l1_overlay),
        composite_with_overlay(vanilla_layers["leather"][1], leather_l2_overlay),
    )

    # Load food pattern textures
    food_pats = {}
    for name, food_tex in FOOD_PATTERNS.items():
        food_pats[name] = extract_png(CLIENT_JAR, f"{V_ITEM}/{food_tex}")

    total = 0
    for name, (_, layer_base, spec) in ARMOR_CONFIGS.items():
        # Special handling for rgold layers (different transform than item icons)
        if name == "rgold":
            fn, kwargs = remap_rgold_layers, {}
        else:
            fn, kwargs = resolve_remap(spec)

        bl1, bl2 = vanilla_layers[layer_base]
        total += gen_armor_layers(bl1, bl2, name, remap_fn=fn, **kwargs)

        # Apply food pattern overlay (recolored to match armor palette)
        if name in food_pats:
            recolored_pat = fn(food_pats[name], **kwargs)
            for layer_n in ("1", "2"):
                path = ARMOR_DIR / f"{name}_layer_{layer_n}.png"
                armor = load_local_png(path)
                result = pattern_overlay(armor, recolored_pat, strength=0.12)
                result.save(path)

    return total


def generate_material_items():
    """Generate crystal/element material item textures."""
    print("\n=== Material Items ===")

    amethyst_shard = extract_png(CLIENT_JAR, f"{V_ITEM}/amethyst_shard.png")
    prismarine_shard = extract_png(CLIENT_JAR, f"{V_ITEM}/prismarine_shard.png")
    quartz = extract_png(CLIENT_JAR, f"{V_ITEM}/quartz.png")
    gold_ingot = extract_png(CLIENT_JAR, f"{V_ITEM}/gold_ingot.png")
    coal = extract_png(CLIENT_JAR, f"{V_ITEM}/coal.png")
    glowstone_dust = extract_png(CLIENT_JAR, f"{V_ITEM}/glowstone_dust.png")
    raw_gold = extract_png(CLIENT_JAR, f"{V_ITEM}/raw_gold.png")
    iron_ingot = extract_png(CLIENT_JAR, f"{V_ITEM}/iron_ingot.png")
    flint = extract_png(CLIENT_JAR, f"{V_ITEM}/flint.png")

    total = 0

    # Calcified Amethyst: amethyst shard shifted toward calcite beige/white
    calc = remap_hsv(amethyst_shard, target_hue=50, sat_mult=0.25, val_mult=1.25, val_add=0.12)
    calc = blend_tint(calc, (225, 215, 195), strength=0.5)
    calc.save(ITEM_DIR / "calcified_amethyst.png")
    print("  calcified_amethyst.png")
    total += 1

    # Glacial Shard: amethyst shard shifted to vibrant ice blue/cyan
    glacial = remap_hsv(amethyst_shard, target_hue=195, sat_set=0.7, val_mult=1.15)
    glacial.save(ITEM_DIR / "glacial_shard.png")
    print("  glacial_shard.png")
    total += 1

    # Polished Quartz: vanilla quartz with warm-white enhancement, more distinct
    pq = remap_hsv(quartz, hue_shift=5, sat_mult=0.5, val_mult=1.15, val_add=0.08)
    pq.save(ITEM_DIR / "polished_quartz.png")
    print("  polished_quartz.png")
    total += 1

    # Polished Prismarine: prismarine shard with vivid teal, boosted saturation
    pp = remap_hsv(prismarine_shard, target_hue=168, sat_set=0.75, val_mult=1.1)
    pp.save(ITEM_DIR / "polished_prismarine.png")
    print("  polished_prismarine.png")
    total += 1

    # Ferrous Gold Ingot: gold-iron alloy blend, vibrant
    rgold = blend_tint(gold_ingot, (160, 160, 170), strength=0.4)
    rgold = remap_hsv(rgold, hue_shift=-5, sat_mult=0.9, val_mult=0.95)
    rgold.save(ITEM_DIR / "rgold.png")
    print("  rgold.png")
    total += 1

    # Raw Ferrous Gold: raw_gold with iron grey blend
    raw_rg = blend_tint(raw_gold, (160, 160, 170), strength=0.4)
    raw_rg = remap_hsv(raw_rg, hue_shift=-5, sat_mult=0.85, val_mult=0.92)
    raw_rg.save(ITEM_DIR / "raw_rgold.png")
    print("  raw_rgold.png")
    total += 1

    # Coal Dust: coal ground into dust, darker
    cd = remap_hsv(coal, target_hue=0, sat_set=0.03, val_mult=0.4)
    cd.save(ITEM_DIR / "coal_dust.png")
    print("  coal_dust.png")
    total += 1

    # Hardened Coal: coal compressed/hardened, slightly brighter than dust
    hc = remap_hsv(coal, target_hue=0, sat_set=0.05, val_mult=0.5)
    hc.save(ITEM_DIR / "hardened_coal.png")
    print("  hardened_coal.png")
    total += 1

    # Hardened Glowstone (hglow): glowstone dust but richer amber
    hg = remap_hsv(glowstone_dust, target_hue=41, sat_set=0.8, val_mult=1.1)
    hg.save(ITEM_DIR / "hglow.png")
    print("  hglow.png")
    total += 1

    # Hardened Redstone (hred): iron ingot tinted deep red
    hr = blend_tint(iron_ingot, (200, 30, 30), strength=0.6)
    hr = remap_hsv(hr, target_hue=0, sat_set=0.8, val_mult=0.8)
    hr.save(ITEM_DIR / "hred.png")
    print("  hred.png")
    total += 1

    # Ectoplasm: slime ball base with spectral cyan-white ghost palette
    slime_ball = extract_png(CLIENT_JAR, f"{V_ITEM}/slime_ball.png")
    ecto = remap_hsv(slime_ball, target_hue=175, sat_set=0.4, val_mult=1.05)
    ecto = blend_tint(ecto, (200, 240, 235), strength=0.35)
    ecto.save(ITEM_DIR / "ectoplasm.png")
    print("  ectoplasm.png")
    total += 1

    # Refined Ectoplasm: slime ball base, more saturated/vivid than raw ectoplasm
    recto = remap_hsv(slime_ball, target_hue=175, sat_set=0.6, val_mult=0.95)
    recto = blend_tint(recto, (80, 220, 200), strength=0.3)
    recto.save(ITEM_DIR / "refined_ectoplasm.png")
    print("  refined_ectoplasm.png")
    total += 1

    # Obsidian Shard: amethyst shard shifted to dark obsidian purple
    obs = remap_hsv(amethyst_shard, target_hue=275, sat_set=0.6, val_mult=0.45)
    obs.save(ITEM_DIR / "obshard.png")
    print("  obshard.png")
    total += 1

    # Obsidian Ingot: netherite ingot shifted to polished obsidian purple
    netherite_ingot = extract_png(CLIENT_JAR, f"{V_ITEM}/netherite_ingot.png")
    obi = remap_hsv(netherite_ingot, target_hue=275, sat_set=0.5, val_mult=0.5)
    obi.save(ITEM_DIR / "obingot.png")
    print("  obingot.png")
    total += 1

    # Polished Emerald Material: ingot shape with vivid green
    se = remap_hsv(iron_ingot, target_hue=140, sat_set=0.75, val_mult=0.9)
    se.save(ITEM_DIR / "sem.png")
    print("  sem.png")
    total += 1

    # Reinforced Lapis: ingot shape with vivid blue
    rl = remap_hsv(iron_ingot, target_hue=230, sat_set=0.8, val_mult=0.8)
    rl.save(ITEM_DIR / "rlapis.png")
    print("  rlapis.png")
    total += 1

    return total


def generate_wood_tools():
    """Generate all wood variant tool textures using vanilla wooden_* as base shape."""
    print("\n=== Wood Variant Tools (wooden base) ===")

    # Load vanilla wooden tool bases
    wooden = {}
    for tool in TOOL_TYPES:
        wooden[tool] = extract_png(CLIENT_JAR, f"{V_ITEM}/wooden_{tool}.png")

    total = 0
    for name, kwargs in WOOD_TOOL_CONFIGS.items():
        total += gen_tools(wooden, name, **kwargs)

    return total


def generate_hglow_emissives():
    """Generate emissive (_e) textures for hardened glowstone set.
    These are copies of the main textures with boosted brightness for shader glow.
    """
    print("\n=== HGLOW Emissive Textures ===")
    total = 0

    # Tool emissives
    for tool in TOOL_TYPES:
        src = load_local_png(ITEM_DIR / f"hglowstone_{tool}.png")
        em = remap_hsv(src, val_mult=1.3, val_add=0.15, sat_mult=1.2)
        em.save(ITEM_DIR / f"hglowstone_{tool}_e.png")
        total += 1

    # Armor item emissives
    for piece in ARMOR_PIECES:
        src = load_local_png(ITEM_DIR / f"hglow_{piece}.png")
        em = remap_hsv(src, val_mult=1.3, val_add=0.15, sat_mult=1.2)
        em.save(ITEM_DIR / f"hglow_{piece}_e.png")
        total += 1

    # Material emissive
    src = load_local_png(ITEM_DIR / "hglow.png")
    em = remap_hsv(src, val_mult=1.3, val_add=0.15, sat_mult=1.2)
    em.save(ITEM_DIR / "hglow_e.png")
    total += 1

    # Armor layer emissives
    for layer in ("1", "2"):
        src = load_local_png(ARMOR_DIR / f"hglow_layer_{layer}.png")
        em = remap_hsv(src, val_mult=1.3, val_add=0.15, sat_mult=1.2)
        em.save(ARMOR_DIR / f"hglow_layer_{layer}_e.png")
        total += 1

    print(f"  {total} emissive textures")
    return total


def generate_ecto_emissives():
    """Generate emissive (_e) textures for ectoplasm items.
    Regular ecto items get a soft green/blue glow; refined ecto items glow brighter.
    """
    print("\n=== Ecto Emissive Textures ===")
    total = 0

    BLOCK_DIR = TEX_ROOT / "block"

    # --- Regular ectoplasm (soft glow) ---
    soft = dict(val_mult=1.15, val_add=0.08, sat_mult=1.1)

    # Ectoplasm material
    src = load_local_png(ITEM_DIR / "ectoplasm.png")
    em = remap_hsv(src, **soft)
    em.save(ITEM_DIR / "ectoplasm_e.png")
    total += 1

    # Rough ecto tools
    for tool in TOOL_TYPES:
        src = load_local_png(ITEM_DIR / f"recto_{tool}.png")
        em = remap_hsv(src, **soft)
        em.save(ITEM_DIR / f"recto_{tool}_e.png")
        total += 1

    # Ectoplasm block
    src = load_local_png(BLOCK_DIR / "ectoplasm_block.png")
    em = remap_hsv(src, **soft)
    em.save(BLOCK_DIR / "ectoplasm_block_e.png")
    total += 1

    # --- Refined ectoplasm (brighter glow) ---
    bright = dict(val_mult=1.35, val_add=0.18, sat_mult=1.25)

    # Refined ectoplasm material
    src = load_local_png(ITEM_DIR / "refined_ectoplasm.png")
    em = remap_hsv(src, **bright)
    em.save(ITEM_DIR / "refined_ectoplasm_e.png")
    total += 1

    # Refined ecto tools
    for tool in TOOL_TYPES:
        src = load_local_png(ITEM_DIR / f"ecto_{tool}.png")
        em = remap_hsv(src, **bright)
        em.save(ITEM_DIR / f"ecto_{tool}_e.png")
        total += 1

    # Ecto armor item emissives
    for piece in ARMOR_PIECES:
        src = load_local_png(ITEM_DIR / f"ecto_{piece}.png")
        em = remap_hsv(src, **bright)
        em.save(ITEM_DIR / f"ecto_{piece}_e.png")
        total += 1

    # Ecto armor layer emissives
    for layer in ("1", "2"):
        src = load_local_png(ARMOR_DIR / f"ecto_layer_{layer}.png")
        em = remap_hsv(src, **bright)
        em.save(ARMOR_DIR / f"ecto_layer_{layer}_e.png")
        total += 1

    # Refined ectoplasm block
    src = load_local_png(BLOCK_DIR / "refined_ectoplasm_block.png")
    em = remap_hsv(src, **bright)
    em.save(BLOCK_DIR / "refined_ectoplasm_block_e.png")
    total += 1

    print(f"  {total} emissive textures")
    return total


def generate_block_textures():
    """Generate block textures from vanilla block bases with HSV remapping."""
    print("\n=== Block Textures ===")

    V_BLOCK = "assets/minecraft/textures/block"
    BLOCK_DIR = TEX_ROOT / "block"
    os.makedirs(BLOCK_DIR, exist_ok=True)

    # Load vanilla block bases
    iron_block = extract_png(CLIENT_JAR, f"{V_BLOCK}/iron_block.png")
    gold_block = extract_png(CLIENT_JAR, f"{V_BLOCK}/gold_block.png")
    diamond_block = extract_png(CLIENT_JAR, f"{V_BLOCK}/diamond_block.png")
    emerald_block = extract_png(CLIENT_JAR, f"{V_BLOCK}/emerald_block.png")
    lapis_block = extract_png(CLIENT_JAR, f"{V_BLOCK}/lapis_block.png")
    coal_block = extract_png(CLIENT_JAR, f"{V_BLOCK}/coal_block.png")
    raw_gold_block = extract_png(CLIENT_JAR, f"{V_BLOCK}/raw_gold_block.png")
    amethyst_block = extract_png(CLIENT_JAR, f"{V_BLOCK}/amethyst_block.png")
    blue_ice = extract_png(CLIENT_JAR, f"{V_BLOCK}/blue_ice.png")
    quartz_block = extract_png(CLIENT_JAR, f"{V_BLOCK}/quartz_block_side.png")
    prismarine = extract_png(CLIENT_JAR, f"{V_BLOCK}/prismarine.png")
    slime_block = extract_png(CLIENT_JAR, f"{V_BLOCK}/slime_block.png")

    total = 0

    # --- Update 5 existing block textures ---

    # Ferrous Gold Block: iron block with gold-iron alloy blend
    rgb = remap_rgold_tool(iron_block)
    rgb.save(BLOCK_DIR / "rgoldblock.png")
    print("  rgoldblock.png (updated)")
    total += 1

    # Hardened Redstone Block: iron block shifted deep red
    hrb = remap_hsv(iron_block, target_hue=0, sat_set=0.8, val_mult=0.8)
    hrb.save(BLOCK_DIR / "hrblock.png")
    print("  hrblock.png (updated)")
    total += 1

    # Polished Emerald Block: emerald block vivid green
    seb = remap_hsv(emerald_block, target_hue=140, sat_set=0.75, val_mult=0.9)
    seb.save(BLOCK_DIR / "semblock.png")
    print("  semblock.png (updated)")
    total += 1

    # Polished Obsidian Block: diamond block shifted dark obsidian purple
    sob = remap_hsv(diamond_block, target_hue=275, sat_set=0.5, val_mult=0.45)
    sob.save(BLOCK_DIR / "soblock.png")
    print("  soblock.png (updated)")
    total += 1

    # Reinforced Lapis Block: lapis block vivid blue
    lb = remap_hsv(lapis_block, target_hue=230, sat_set=0.8, val_mult=0.85)
    lb.save(BLOCK_DIR / "lblock.png")
    print("  lblock.png (updated)")
    total += 1

    # --- 10 new storage block textures ---

    # Hardened Glowstone Block: gold block shifted amber
    hgb = remap_hsv(gold_block, target_hue=41, sat_set=0.75, val_mult=1.1)
    hgb.save(BLOCK_DIR / "hglow_block.png")
    print("  hglow_block.png")
    total += 1

    # Raw Ferrous Gold Block: raw gold block with ferrous blend
    rrb = remap_rgold_tool(raw_gold_block)
    rrb.save(BLOCK_DIR / "raw_rgold_block.png")
    print("  raw_rgold_block.png")
    total += 1

    # Ectoplasm Block: slime block shifted ecto green (softer)
    eb = remap_hsv(slime_block, target_hue=175, sat_set=0.4, val_mult=1.05)
    eb = blend_tint(eb, (200, 240, 235), strength=0.35)
    eb.save(BLOCK_DIR / "ectoplasm_block.png")
    print("  ectoplasm_block.png")
    total += 1

    # Refined Ectoplasm Block: slime block shifted ecto cyan (more saturated)
    reb = remap_hsv(slime_block, target_hue=175, sat_set=0.6, val_mult=0.95)
    reb.save(BLOCK_DIR / "refined_ectoplasm_block.png")
    print("  refined_ectoplasm_block.png")
    total += 1

    # Hardened Coal Block: coal block, charcoal grey
    hcb = remap_hsv(coal_block, target_hue=0, sat_set=0.05, val_mult=0.5)
    hcb.save(BLOCK_DIR / "hardened_coal_block.png")
    print("  hardened_coal_block.png")
    total += 1

    # Coal Dust Block: coal block, very dark
    cdb = remap_hsv(coal_block, target_hue=0, sat_set=0.03, val_mult=0.4)
    cdb.save(BLOCK_DIR / "coal_dust_block.png")
    print("  coal_dust_block.png")
    total += 1

    # Obsidian Shard Block: amethyst block, dark purple
    osb = remap_hsv(amethyst_block, target_hue=275, sat_set=0.6, val_mult=0.45)
    osb.save(BLOCK_DIR / "obshard_block.png")
    print("  obshard_block.png")
    total += 1

    # Calcified Amethyst Block: amethyst block shifted beige
    cab = remap_hsv(amethyst_block, target_hue=50, sat_mult=0.25, val_mult=1.25, val_add=0.12)
    cab = blend_tint(cab, (225, 215, 195), strength=0.5)
    cab.save(BLOCK_DIR / "calcified_amethyst_block.png")
    print("  calcified_amethyst_block.png")
    total += 1

    # Glacial Shard Block: blue ice shifted ice blue
    gsb = remap_hsv(blue_ice, target_hue=195, sat_set=0.65, val_mult=0.95)
    gsb.save(BLOCK_DIR / "glacial_shard_block.png")
    print("  glacial_shard_block.png")
    total += 1

    # Polished Quartz Block: quartz block warm white
    pqb = remap_hsv(quartz_block, target_hue=35, sat_set=0.1, val_mult=1.15)
    pqb.save(BLOCK_DIR / "polished_quartz_block.png")
    print("  polished_quartz_block.png")
    total += 1

    # Polished Prismarine Block: prismarine vivid teal
    ppb = remap_hsv(prismarine, target_hue=168, sat_set=0.75, val_mult=1.1)
    ppb.save(BLOCK_DIR / "polished_prismarine_block.png")
    print("  polished_prismarine_block.png")
    total += 1

    return total


# ---------------------------------------------------------------------------
# Main
# ---------------------------------------------------------------------------

if __name__ == "__main__":
    if not os.path.exists(CLIENT_JAR):
        print(f"ERROR: Vanilla client jar not found at:\n  {CLIENT_JAR}")
        print("Run './gradlew build' once to download Minecraft assets.")
        exit(1)

    os.makedirs(ITEM_DIR, exist_ok=True)
    os.makedirs(ARMOR_DIR, exist_ok=True)

    total = 0
    total += generate_rough_tools()        # 12 sets × 5 = 60 tools
    total += generate_polished_tools()        # 11 sets × 5 = 55 tools
    total += generate_armor_items_all()     # 13 sets × 4 = 52 armor items
    total += generate_armor_layers_all()    # 13 sets × 2 = 26 armor layers
    total += generate_material_items()      # 16 material items
    total += generate_block_textures()       # 15 block textures
    total += generate_wood_tools()           # 11 sets × 5 = 55 tools
    total += generate_hglow_emissives()     # 12 emissive textures
    total += generate_ecto_emissives()      # ~22 emissive textures

    print(f"\nDone! Generated {total} textures.")
