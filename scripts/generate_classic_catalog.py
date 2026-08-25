#!/usr/bin/env python3
"""Generate the Forge 1.7.10 canonical catalog adapter from the 2.3.0 manifest."""

from __future__ import annotations

import json
import re
import shutil
from pathlib import Path


ROOT = Path(__file__).resolve().parents[1]
CATALOG = ROOT / "catalog/useful_tools_catalog.json"
MODERN_JAVA = ROOT / "1.21.1/common/src/main/java/com/stonytark/usefultoolsmod/item"
MODERN_ASSETS = ROOT / "1.21.1/common/src/main/resources/assets/usefultoolsmod"
CLASSIC = ROOT / "1.7.10"
OUTPUT = CLASSIC / "src/main/java/com/stonytark/usefultoolsmod/classic/ClassicGeneratedCatalog.java"
RECIPE_OUTPUT = CLASSIC / "src/main/java/com/stonytark/usefultoolsmod/classic/ClassicGeneratedRecipes.java"
CONFIG_OUTPUT = CLASSIC / "src/main/java/com/stonytark/usefultoolsmod/classic/ClassicGeneratedConfig.java"
MIGRATION_OUTPUT = ROOT / "CLASSIC_MIGRATION.md"
LANG = CLASSIC / "src/main/resources/assets/usefultoolsmod/lang/en_US.lang"
SUBSTITUTIONS = ROOT / "catalog/classic_1_7_substitutions.json"

ITEM_DECLARATION = re.compile(
    r"public static final RegistrySupplier<Item>\s+(\w+)\s*=\s*ITEMS\.register\(\"([^\"]+)\",(.*?)"
    r"(?=\n\s*public static final RegistrySupplier<Item>|\n\s*public static void register\(|\Z)",
    re.S,
)

CLASSIC_EXPRESSIONS = {
    "minecraft:acacia_planks": "new ItemStack(Blocks.planks, 1, 4)",
    "minecraft:apple": "new ItemStack(Items.apple)",
    "minecraft:birch_planks": "new ItemStack(Blocks.planks, 1, 2)",
    "minecraft:blaze_rod": "new ItemStack(Items.blaze_rod)",
    "minecraft:bone": "new ItemStack(Items.bone)",
    "minecraft:bread": "new ItemStack(Items.bread)",
    "minecraft:brick": "new ItemStack(Items.brick)",
    "minecraft:cactus": "new ItemStack(Blocks.cactus)",
    "minecraft:cake": "new ItemStack(Items.cake)",
    "minecraft:charcoal": "new ItemStack(Items.coal, 1, 1)",
    "minecraft:clay_ball": "new ItemStack(Items.clay_ball)",
    "minecraft:coal": "new ItemStack(Items.coal)",
    "minecraft:cobblestone": "new ItemStack(Blocks.cobblestone)",
    "minecraft:dark_oak_planks": "new ItemStack(Blocks.planks, 1, 5)",
    "minecraft:diamond": "new ItemStack(Items.diamond)",
    "minecraft:diamond_block": "new ItemStack(Blocks.diamond_block)",
    "minecraft:emerald": "new ItemStack(Items.emerald)",
    "minecraft:end_stone": "new ItemStack(Blocks.end_stone)",
    "minecraft:ender_eye": "new ItemStack(Items.ender_eye)",
    "minecraft:ender_pearl": "new ItemStack(Items.ender_pearl)",
    "minecraft:feather": "new ItemStack(Items.feather)",
    "minecraft:fish": "new ItemStack(Items.fish)",
    "minecraft:flint": "new ItemStack(Items.flint)",
    "minecraft:ghast_tear": "new ItemStack(Items.ghast_tear)",
    "minecraft:glass_pane": "new ItemStack(Blocks.glass_pane)",
    "minecraft:glowstone_dust": "new ItemStack(Items.glowstone_dust)",
    "minecraft:gold_ingot": "new ItemStack(Items.gold_ingot)",
    "minecraft:golden_apple": "new ItemStack(Items.golden_apple)",
    "minecraft:gunpowder": "new ItemStack(Items.gunpowder)",
    "minecraft:hardened_clay": "new ItemStack(Blocks.hardened_clay)",
    "minecraft:ice": "new ItemStack(Blocks.ice)",
    "minecraft:iron_ingot": "new ItemStack(Items.iron_ingot)",
    "minecraft:jungle_planks": "new ItemStack(Blocks.planks, 1, 3)",
    "minecraft:lapis_lazuli": "new ItemStack(Items.dye, 1, 4)",
    "minecraft:leather": "new ItemStack(Items.leather)",
    "minecraft:magma_cream": "new ItemStack(Items.magma_cream)",
    "minecraft:melon": "new ItemStack(Items.melon)",
    "minecraft:nether_brick": "new ItemStack(Items.netherbrick)",
    "minecraft:nether_wart": "new ItemStack(Items.nether_wart)",
    "minecraft:netherrack": "new ItemStack(Blocks.netherrack)",
    "minecraft:oak_planks": "new ItemStack(Blocks.planks, 1, 0)",
    "minecraft:obsidian": "new ItemStack(Blocks.obsidian)",
    "minecraft:paper": "new ItemStack(Items.paper)",
    "minecraft:pufferfish_meta": "new ItemStack(Items.fish, 1, 3)",
    "minecraft:pumpkin_pie": "new ItemStack(Items.pumpkin_pie)",
    "minecraft:quartz": "new ItemStack(Items.quartz)",
    "minecraft:quartz_block": "new ItemStack(Blocks.quartz_block)",
    "minecraft:red_mushroom": "new ItemStack(Blocks.red_mushroom)",
    "minecraft:redstone": "new ItemStack(Items.redstone)",
    "minecraft:rotten_flesh": "new ItemStack(Items.rotten_flesh)",
    "minecraft:sandstone": "new ItemStack(Blocks.sandstone)",
    "minecraft:slime_ball": "new ItemStack(Items.slime_ball)",
    "minecraft:snowball": "new ItemStack(Items.snowball)",
    "minecraft:sponge": "new ItemStack(Blocks.sponge)",
    "minecraft:spruce_planks": "new ItemStack(Blocks.planks, 1, 1)",
    "minecraft:stick": "new ItemStack(Items.stick)",
    "minecraft:stone": "new ItemStack(Blocks.stone)",
    "minecraft:string": "new ItemStack(Items.string)",
    "minecraft:sugar": "new ItemStack(Items.sugar)",
    "usefultoolsmod:legacy_iron_nugget": "new ItemStack(MItems.INugget)",
}


def java_number(value: str) -> str:
    return value.rstrip("fFdD")


def harvest_level(definition: str) -> int:
    for token, level in (
        ("NETHERITE", 4),
        ("DIAMOND", 3),
        ("IRON", 2),
        ("STONE", 1),
        ("WOODEN", 0),
    ):
        if token in definition:
            return level
    numbers = re.findall(r"(?<![A-Za-z_])-?\d+(?:\.\d+)?[fFdD]?", definition)
    speed = float(java_number(numbers[1]))
    return 3 if speed >= 8 else 2 if speed >= 6 else 1 if speed >= 4 else 0


def parse_tool_materials() -> dict[str, tuple[int, int, float, float, int]]:
    text = (MODERN_JAVA / "ModToolTiers.java").read_text(encoding="utf-8")
    result: dict[str, tuple[int, int, float, float, int]] = {}
    for key, definition in re.findall(
        r"public static final Tier\s+(\w+)\s*=\s*new\s+\w+\((.*?)\);", text, re.S
    ):
        numbers = re.findall(r"(?<![A-Za-z_])-?\d+(?:\.\d+)?[fFdD]?", definition)
        if len(numbers) < 4:
            raise ValueError(f"cannot parse tool tier {key}: {definition}")
        result[key] = (
            harvest_level(definition),
            int(float(java_number(numbers[0]))),
            float(java_number(numbers[1])),
            float(java_number(numbers[2])),
            int(float(java_number(numbers[3]))),
        )
    return result


def parse_item_declarations() -> dict[str, str]:
    text = (MODERN_JAVA / "ModItems.java").read_text(encoding="utf-8")
    return {item_id: body for _, item_id, body in ITEM_DECLARATION.findall(text)}


def parse_armor_materials() -> dict[str, tuple[str, int, tuple[int, int, int, int], int]]:
    text = (MODERN_JAVA / "ModArmorMaterials.java").read_text(encoding="utf-8")
    declarations = re.findall(
        r"public static final Holder<ArmorMaterial>\s+(\w+)\s*=\s*register\(\"([^\"]+)\",(.*?)"
        r"(?=\n\s*public static final Holder<ArmorMaterial>|\n\s*private static Holder<ArmorMaterial>|\Z)",
        text,
        re.S,
    )
    result = {}
    for constant, family, body in declarations:
        protection = {}
        for slot, value in re.findall(r"ArmorItem\.Type\.(BOOTS|LEGGINGS|CHESTPLATE|HELMET),\s*(\d+)", body):
            protection[slot] = int(value)
        enchant_match = re.search(r"\}\),\s*(\d+)\s*,", body)
        if len(protection) != 4 or enchant_match is None:
            raise ValueError(f"cannot parse armor material {constant}")
        result[constant] = (
            family,
            15,
            (
                protection["HELMET"],
                protection["CHESTPLATE"],
                protection["LEGGINGS"],
                protection["BOOTS"],
            ),
            int(enchant_match.group(1)),
        )
    return result


def title(item_id: str) -> str:
    replacements = {
        "ecto": "Ectoplasm",
        "fni": "Flint-Iron",
        "hglow": "Hardened Glowstone",
        "hred": "Hardened Redstone",
        "pemerald": "Polished Emerald",
        "pobsidian": "Polished Obsidian",
        "pprism": "Polished Prismarine",
        "pquartz": "Polished Quartz",
        "recto": "Rough Ectoplasm",
        "remerald": "Rough Emerald",
        "rgold": "Ferrous Gold",
        "rlapis": "Reinforced Lapis",
        "robsidian": "Rough Obsidian",
    }
    words = item_id.split("_")
    head = replacements.get(words[0], words[0].title())
    return " ".join([head, *[word.title() for word in words[1:]]])


def render_java() -> tuple[str, list[str], list[str]]:
    catalog = json.loads(CATALOG.read_text(encoding="utf-8"))
    item_ids: list[str] = catalog["content"]["items"]
    block_ids: list[str] = catalog["content"]["blocks"]
    item_definitions = catalog["definitions"]["items"]
    tool_materials = {entry["key"]: entry for entry in catalog["definitions"]["tool_materials"]}
    armor_materials = {entry["key"]: entry for entry in catalog["definitions"]["armor_materials"]}
    tool_kinds = {"sword", "pickaxe", "shovel", "axe", "hoe"}
    armor_kinds = {"helmet", "chestplate", "leggings", "boots"}
    tools = [entry for entry in item_definitions if entry["kind"] in tool_kinds]
    armors = [entry for entry in item_definitions if entry["kind"] in armor_kinds]
    plain = [entry["id"] for entry in item_definitions if entry["kind"] == "item"]
    used_tool_materials = {entry["material"] for entry in tools}
    used_armor_materials = {entry["material"] for entry in armors}

    lines = [
        "// Generated by scripts/generate_classic_catalog.py. Do not edit manually.",
        "package com.stonytark.usefultoolsmod.classic;",
        "",
        "import com.krazykid1117.CreativeTabs.MCreativeTabs;",
        "import com.krazykid1117.item.Dynamite;",
        "import com.krazykid1117.item.Grenade;",
        "import com.stonytark.usefultoolsmod.classic.ClassicGhostSpawnEgg;",
        "import com.stonytark.usefultoolsmod.classic.ClassicSpectralInfuserBlock;",
        "import cpw.mods.fml.common.FMLLog;",
        "import cpw.mods.fml.common.registry.GameRegistry;",
        "import java.util.LinkedHashMap;",
        "import java.util.Map;",
        "import net.minecraft.block.Block;",
        "import net.minecraft.block.material.Material;",
        "import net.minecraft.entity.Entity;",
        "import net.minecraft.item.Item;",
        "import net.minecraft.item.ItemArmor;",
        "import net.minecraft.item.ItemAxe;",
        "import net.minecraft.item.ItemHoe;",
        "import net.minecraft.item.ItemPickaxe;",
        "import net.minecraft.item.ItemSpade;",
        "import net.minecraft.item.ItemStack;",
        "import net.minecraft.item.ItemSword;",
        "import net.minecraftforge.common.util.EnumHelper;",
        "",
        "public final class ClassicGeneratedCatalog {",
        "    public static final Map<String, Item> ITEMS = new LinkedHashMap<String, Item>();",
        "    public static final Map<String, Block> BLOCKS = new LinkedHashMap<String, Block>();",
        "    private static final Map<String, Item.ToolMaterial> TOOL_MATERIALS = new LinkedHashMap<String, Item.ToolMaterial>();",
        "    private static final Map<String, ItemArmor.ArmorMaterial> ARMOR_MATERIALS = new LinkedHashMap<String, ItemArmor.ArmorMaterial>();",
        "    private static boolean registered;",
        "",
        "    private ClassicGeneratedCatalog() {}",
        "",
        "    public static void register() {",
        "        if (registered) return;",
        "        registered = true;",
    ]

    for key in sorted(used_tool_materials):
        material = tool_materials[key]
        lines.append(
            f'        TOOL_MATERIALS.put("{key}", EnumHelper.addToolMaterial("UT230_{key}", {material["harvest_level"]}, {material["uses"]}, {material["speed"]}F, {material["attack_bonus"]}F, {material["enchantment_value"]}));'
        )
    for key in sorted(used_armor_materials):
        material = armor_materials[key]
        protection = material["protection"]
        protection_text = ", ".join(str(protection[slot]) for slot in ("helmet", "chestplate", "leggings", "boots"))
        lines.append(
            f'        ARMOR_MATERIALS.put("{key}", EnumHelper.addArmorMaterial("UT230_{key}", {material["durability"]}, new int[] {{{protection_text}}}, {material["enchantment_value"]}));'
        )
    for item_id in plain:
        constructor = "new Dynamite()" if item_id == "dynamite" else "new Grenade()" if item_id == "grenade" else "new ClassicGhostSpawnEgg()" if item_id == "ghost_spawn_egg" else "new Item()"
        lines.append(f'        registerItem("{item_id}", {constructor});')
    for entry in tools:
        item_id, material, kind = entry["id"], entry["material"], entry["kind"]
        class_name = {
            "sword": "ClassicSword",
            "pickaxe": "ClassicPickaxe",
            "shovel": "ClassicShovel",
            "axe": "ClassicAxe",
            "hoe": "ClassicHoe",
        }[kind]
        lines.append(f'        registerItem("{item_id}", new {class_name}(TOOL_MATERIALS.get("{material}")));')
    for entry in armors:
        item_id, material, slot = entry["id"], entry["material"], entry["kind"]
        armor_type = {"helmet": 0, "chestplate": 1, "leggings": 2, "boots": 3}[slot]
        family = armor_materials[material]["texture_family"]
        lines.append(
            f'        registerItem("{item_id}", new ClassicArmor(ARMOR_MATERIALS.get("{material}"), {armor_type}, "{family}"));'
        )
    for block_id in block_ids:
        lines.append(f'        registerBlock("{block_id}");')
    lines.append('        FMLLog.info("[Useful Tools] Canonical 2.3.0 classic catalog registered: %d items, %d blocks", ITEMS.size(), BLOCKS.size());')

    lines.extend(
        [
            "    }",
            "",
            "    private static void registerItem(String id, Item item) {",
            "        item.setUnlocalizedName(id).setTextureName(\"usefultoolsmod:\" + id).setCreativeTab(MCreativeTabs.tabToolsMod);",
            "        GameRegistry.registerItem(item, id);",
            "        ITEMS.put(id, item);",
            "    }",
            "",
            "    private static void registerBlock(String id) {",
            "        Block block = (id.equals(\"spectral_infuser\") ? new ClassicSpectralInfuserBlock() : new ClassicBlock()).setBlockName(id).setBlockTextureName(\"usefultoolsmod:\" + id).setCreativeTab(MCreativeTabs.tabToolsMod);",
            "        GameRegistry.registerBlock(block, id);",
            "        BLOCKS.put(id, block);",
            "    }",
            "",
            "    private static final class ClassicBlock extends Block {",
            "        private ClassicBlock() { super(Material.rock); setHardness(3.0F); setResistance(8.0F); }",
            "    }",
            "",
            "    private static final class ClassicSword extends ItemSword { private ClassicSword(ToolMaterial material) { super(material); } }",
            "    private static final class ClassicPickaxe extends ItemPickaxe { private ClassicPickaxe(ToolMaterial material) { super(material); } }",
            "    private static final class ClassicShovel extends ItemSpade { private ClassicShovel(ToolMaterial material) { super(material); } }",
            "    private static final class ClassicAxe extends ItemAxe { private ClassicAxe(ToolMaterial material) { super(material); } }",
            "    private static final class ClassicHoe extends ItemHoe { private ClassicHoe(ToolMaterial material) { super(material); } }",
            "",
            "    private static final class ClassicArmor extends ItemArmor {",
            "        private final String textureFamily;",
            "        private ClassicArmor(ArmorMaterial material, int armorType, String textureFamily) {",
            "            super(material, 0, armorType);",
            "            this.textureFamily = textureFamily;",
            "        }",
            "        @Override",
            "        public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {",
            "            int layer = armorType == 2 ? 2 : 1;",
            "            return \"usefultoolsmod:textures/models/armor/\" + textureFamily + \"_layer_\" + layer + \".png\";",
            "        }",
            "    }",
            "}",
            "",
        ]
    )
    return "\n".join(lines), item_ids, block_ids


def render_recipes() -> tuple[str, int, int]:
    catalog = json.loads(CATALOG.read_text(encoding="utf-8"))
    substitutions = json.loads(SUBSTITUTIONS.read_text(encoding="utf-8"))
    item_ids = set(catalog["content"]["items"])
    block_ids = set(catalog["content"]["blocks"])

    def substitute(item_id: str) -> str:
        seen = set()
        while item_id in substitutions:
            if item_id in seen:
                raise ValueError(f"cyclic classic substitution for {item_id}")
            seen.add(item_id)
            item_id = substitutions[item_id]["classic"]
        return item_id

    def stack(item_id: str, count: int = 1) -> str:
        item_id = substitute(item_id)
        if item_id.startswith("usefultoolsmod:"):
            path = item_id.split(":", 1)[1]
            if path in item_ids:
                return f'new ItemStack(ClassicGeneratedCatalog.ITEMS.get("{path}"), {count})'
            if path in block_ids:
                return f'new ItemStack(ClassicGeneratedCatalog.BLOCKS.get("{path}"), {count})'
        expression = CLASSIC_EXPRESSIONS.get(item_id)
        if expression is None:
            raise ValueError(f"no Forge 1.7.10 recipe mapping for {item_id}")
        if count == 1:
            return expression
        return expression.replace("new ItemStack(", "withCount(new ItemStack(", 1) + f", {count})"

    lines = [
        "// Generated by scripts/generate_classic_catalog.py. Do not edit manually.",
        "package com.stonytark.usefultoolsmod.classic;",
        "",
        "import com.krazykid1117.item.MItems;",
        "import cpw.mods.fml.common.FMLLog;",
        "import cpw.mods.fml.common.registry.GameRegistry;",
        "import net.minecraft.init.Blocks;",
        "import net.minecraft.init.Items;",
        "import net.minecraft.item.ItemStack;",
        "",
        "public final class ClassicGeneratedRecipes {",
        "    private ClassicGeneratedRecipes() {}",
        "",
        "    public static void register() {",
    ]
    crafting_count = 0
    furnace_signatures = set()
    furnace_count = 0
    for recipe in catalog["definitions"]["recipes"]:
        payload = recipe["payload"]
        recipe_type = payload["type"]
        result = payload["result"]
        result_id = result["id"]
        result_stack = stack(result_id, int(result.get("count", 1)))
        if recipe_type == "minecraft:crafting_shaped":
            args = [f'"{row}"' for row in payload["pattern"]]
            for symbol, ingredient in payload["key"].items():
                args.extend((f"'{symbol}'", stack(ingredient["item"])))
            lines.append(f"        GameRegistry.addRecipe({result_stack}, new Object[] {{{', '.join(args)}}});")
            crafting_count += 1
        elif recipe_type == "minecraft:crafting_shapeless":
            ingredients = ", ".join(stack(entry["item"]) for entry in payload["ingredients"])
            lines.append(f"        GameRegistry.addShapelessRecipe({result_stack}, {ingredients});")
            crafting_count += 1
        elif recipe_type in {"minecraft:smelting", "minecraft:blasting"}:
            ingredient = payload["ingredient"]["item"]
            signature = (substitute(ingredient), result_id)
            if signature in furnace_signatures:
                continue
            furnace_signatures.add(signature)
            experience = float(payload.get("experience", 0.0))
            lines.append(f"        GameRegistry.addSmelting({stack(ingredient)}, {result_stack}, {experience}F);")
            furnace_count += 1
        else:
            raise ValueError(f"unsupported classic recipe type {recipe_type}")
    lines.extend([
        f'        FMLLog.info("[Useful Tools] Classic recipe adapter registered: {crafting_count} crafting, {furnace_count} furnace recipes");',
        "    }",
        "",
        "    private static ItemStack withCount(ItemStack stack, int count) {",
        "        stack.stackSize = count;",
        "        return stack;",
        "    }",
        "}",
        "",
    ])
    return "\n".join(lines), crafting_count, furnace_count


def render_config() -> str:
    catalog = json.loads(CATALOG.read_text(encoding="utf-8"))
    lines = [
        "// Generated by scripts/generate_classic_catalog.py. Do not edit manually.",
        "package com.stonytark.usefultoolsmod.classic;",
        "",
        "import java.io.File;",
        "import java.util.ArrayList;",
        "import java.util.LinkedHashMap;",
        "import java.util.List;",
        "import java.util.Map;",
        "import net.minecraftforge.common.config.ConfigElement;",
        "import net.minecraftforge.common.config.Configuration;",
        "import cpw.mods.fml.client.config.IConfigElement;",
        "",
        "public final class ClassicGeneratedConfig {",
        "    private static final Map<String, Object> VALUES = new LinkedHashMap<String, Object>();",
        "    private static Configuration configuration;",
        "    private ClassicGeneratedConfig() {}",
        "",
        "    public static void load(File file) {",
        "        configuration = new Configuration(file);",
        "        configuration.load();",
    ]
    for option in catalog["config"]:
        category = option["category"]
        key = option["key"]
        comment = option["tooltip"].replace('"', '\\"')
        if option["type"] == "boolean":
            default = str(option["default"]).lower()
            lines.append(
                f'        VALUES.put("{key}", configuration.get("{category}", "{key}", {default}, "{comment}").getBoolean({default}));'
            )
        else:
            default = option["default"]
            lines.append(
                f'        VALUES.put("{key}", configuration.getFloat("{key}", "{category}", {default}F, {option["minimum"]}F, {option["maximum"]}F, "{comment}"));'
            )
    lines.extend([
        "        if (configuration.hasChanged()) configuration.save();",
        "    }",
        "",
        "    public static boolean booleanValue(String key) {",
        "        Object value = VALUES.get(key);",
        "        return value instanceof Boolean && (Boolean) value;",
        "    }",
        "",
        "    public static double doubleValue(String key) {",
        "        Object value = VALUES.get(key);",
        "        return value instanceof Number ? ((Number) value).doubleValue() : 0.0D;",
        "    }",
        "",
        "    public static List<IConfigElement> elements() {",
        "        List<IConfigElement> elements = new ArrayList<IConfigElement>();",
        "        if (configuration == null) return elements;",
        "        for (String category : configuration.getCategoryNames())",
        "            elements.add(new ConfigElement(configuration.getCategory(category)));",
        "        return elements;",
        "    }",
        "}",
        "",
    ])
    return "\n".join(lines)


def render_migration_table() -> str:
    item_source = (CLASSIC / "src/main/java/com/krazykid1117/item/MItems.java").read_text(encoding="utf-8")
    block_source = (CLASSIC / "src/main/java/com/krazykid1117/blocks/MBlocks.java").read_text(encoding="utf-8")
    item_ids = list(dict.fromkeys(re.findall(r'setUnlocalizedName\("([^\"]+)"\)', item_source)))
    block_ids = list(dict.fromkeys(re.findall(r'setBlockName\("([^\"]+)"\)', block_source)))
    lines = [
        "# Minecraft 1.7.10 v1.6 registry migration",
        "",
        "The published v1.6 implementation passed each unlocalized name to Forge as its registry path. Useful Tools 2.3.0 therefore retains every exact mixed-case `item.` and `tile.` path below. It also handles prefix-free variants through `FMLMissingMappingsEvent`; these aliases remap to the retained object and never discard world data.",
        "",
        "| Kind | Published/retained registry path | Accepted prefix-free alias | 2.3.0 status |",
        "|---|---|---|---|",
    ]
    lines.extend(
        f"| Item | `usefultoolsmod:item.{item_id}` | `usefultoolsmod:{item_id}` | retained legacy object |"
        for item_id in item_ids
    )
    lines.extend(
        f"| Block | `usefultoolsmod:tile.{block_id}` | `usefultoolsmod:{block_id}` | retained legacy object |"
        for block_id in block_ids
    )
    lines.extend([
        "",
        "Lowercase modern IDs are additional registrations generated from the canonical catalog; they do not replace the entries above. Release acceptance still requires loading an independently captured published-v1.6 world, not merely a world produced by this checkout.",
        "",
    ])
    return "\n".join(lines)


def sync_assets(item_ids: list[str], block_ids: list[str]) -> None:
    item_target = CLASSIC / "src/main/resources/assets/usefultoolsmod/textures/items"
    block_target = CLASSIC / "src/main/resources/assets/usefultoolsmod/textures/blocks"
    armor_target = CLASSIC / "src/main/resources/assets/usefultoolsmod/textures/models/armor"
    entity_target = CLASSIC / "src/main/resources/assets/usefultoolsmod/textures/entity/ghost"
    item_target.mkdir(parents=True, exist_ok=True)
    block_target.mkdir(parents=True, exist_ok=True)
    armor_target.mkdir(parents=True, exist_ok=True)
    entity_target.mkdir(parents=True, exist_ok=True)
    for item_id in item_ids:
        source = MODERN_ASSETS / "textures/item" / f"{item_id}.png"
        if source.exists():
            shutil.copyfile(source, item_target / source.name)
    for block_id in block_ids:
        source = MODERN_ASSETS / "textures/block" / f"{block_id}.png"
        if not source.exists() and block_id == "spectral_infuser":
            source = MODERN_ASSETS / "textures/block/spectral_infuser_side.png"
        if not source.exists():
            raise FileNotFoundError(f"missing classic block texture source: {source}")
        shutil.copyfile(source, block_target / f"{block_id}.png")
    for source in (MODERN_ASSETS / "textures/models/armor").glob("*.png"):
        shutil.copyfile(source, armor_target / source.name)
    shutil.copyfile(
        MODERN_ASSETS / "textures/entity/ghost/ghost_white.png",
        entity_target / "ghost_white.png",
    )
    (block_target / "polished_prismarine_block.png.mcmeta").write_text(
        '{\n  "animation": {\n    "frametime": 8\n  }\n}\n', encoding="utf-8"
    )


def sync_language(item_ids: list[str], block_ids: list[str]) -> None:
    start = "# BEGIN GENERATED 2.3.0 CATALOG"
    end = "# END GENERATED 2.3.0 CATALOG"
    existing = LANG.read_text(encoding="utf-8") if LANG.exists() else ""
    existing = re.sub(rf"\n?{re.escape(start)}.*?{re.escape(end)}\n?", "\n", existing, flags=re.S).rstrip()
    generated = [start]
    generated.extend(f"item.{item_id}.name={title(item_id)}" for item_id in item_ids)
    generated.extend(f"tile.{block_id}.name={title(block_id)}" for block_id in block_ids)
    generated.append(end)
    LANG.write_text(existing + "\n\n" + "\n".join(generated) + "\n", encoding="utf-8")


def main() -> None:
    java, item_ids, block_ids = render_java()
    recipes, crafting_count, furnace_count = render_recipes()
    config = render_config()
    migration = render_migration_table()
    OUTPUT.parent.mkdir(parents=True, exist_ok=True)
    OUTPUT.write_text(java, encoding="utf-8")
    RECIPE_OUTPUT.write_text(recipes, encoding="utf-8")
    CONFIG_OUTPUT.write_text(config, encoding="utf-8")
    MIGRATION_OUTPUT.write_text(migration, encoding="utf-8")
    sync_assets(item_ids, block_ids)
    sync_language(item_ids, block_ids)
    print(
        f"classic catalog generated: {len(item_ids)} items, {len(block_ids)} blocks, "
        f"{crafting_count} crafting recipes, {furnace_count} furnace recipes"
    )


if __name__ == "__main__":
    main()
