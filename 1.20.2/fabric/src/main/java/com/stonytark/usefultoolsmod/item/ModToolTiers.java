package com.stonytark.usefultoolsmod.item;

import com.stonytark.usefultoolsmod.block.ModBlocks;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

import java.util.function.Supplier;

public class ModToolTiers {
    // Mining levels: 0=wood, 1=stone, 2=iron, 3=diamond, 4=netherite

    // == MAIN CUSTOM TIERS ==
    public static final ToolMaterial REMERALD = create(3, 1361, 4.5f, 6, 22, () -> Ingredient.ofItems(Items.EMERALD));
    public static final ToolMaterial PEMERALD = create(3, 1561, 3.2f, 7, 30, () -> Ingredient.ofItems(ModItems.SEM));
    public static final ToolMaterial ROBSIDIAN = create(4, 1650, 6f, 9, 15, () -> Ingredient.ofItems(ModItems.OBSHARD));
    public static final ToolMaterial POBSIDIAN = create(4, 2031, 5f, 10, 18, () -> Ingredient.ofItems(ModItems.OBINGOT));
    public static final ToolMaterial OVERPOWER = create(4, 9999, 30f, 25, 35, () -> Ingredient.ofItems(ModBlocks.SOBLOCK.asItem()));
    public static final ToolMaterial HREDSTONE = create(2, 600, 3f, 8, 20, () -> Ingredient.ofItems(ModItems.HRED));
    public static final ToolMaterial HGLOWSTONE = create(2, 500, 2f, 7, 28, () -> Ingredient.ofItems(ModItems.HGLOW));
    public static final ToolMaterial RGOLD = create(3, 1200, 3.5f, 8, 16, () -> Ingredient.ofItems(ModItems.RGOLD));
    public static final ToolMaterial RLAPIS = create(3, 1100, 3.2f, 9, 32, () -> Ingredient.ofItems(ModItems.RLAPIS));

    // == WOOD TIERS ==
    public static final ToolMaterial COAL_TOOL = create(0, 120, 3.0f, 0, 5, () -> Ingredient.ofItems(ModItems.HARDENED_COAL));
    public static final ToolMaterial RRAW_GOLD = create(0, 80, 0.0f, 12, 25, () -> Ingredient.ofItems(Items.RAW_GOLD));
    public static final ToolMaterial RRAW_COPPER = create(1, 170, 1.0f, 5, 10, () -> Ingredient.ofItems(Items.RAW_COPPER));
    public static final ToolMaterial RRAW_IRON = create(2, 200, 1.5f, 5.5f, 10, () -> Ingredient.ofItems(Items.RAW_IRON));
    public static final ToolMaterial RRAW_RGOLD = create(2, 600, 2.0f, 7, 14, () -> Ingredient.ofItems(ModItems.RAW_RGOLD));
    public static final ToolMaterial RSCRAP = create(3, 800, 2.5f, 7.5f, 12, () -> Ingredient.ofItems(Items.NETHERITE_SCRAP));

    // == CRYSTAL ROUGH TIERS ==
    public static final ToolMaterial RAMETHYST = create(1, 250, 1.5f, 5, 12, () -> Ingredient.ofItems(Items.AMETHYST_SHARD));
    public static final ToolMaterial SNOW_TOOL = create(0, 45, 0.0f, 3, 4, () -> Ingredient.ofItems(Items.SNOWBALL));
    public static final ToolMaterial RQUARTZ = create(1, 310, 1.5f, 5.5f, 10, () -> Ingredient.ofItems(Items.QUARTZ));
    public static final ToolMaterial RPRISM = create(1, 240, 1.5f, 4.5f, 8, () -> Ingredient.ofItems(Items.PRISMARINE_SHARD));

    // == STONE VARIANTS ==
    public static final ToolMaterial STONE_ANDESITE = create(1, 131, 1.5f, 4, 5, () -> Ingredient.ofItems(Items.ANDESITE));
    public static final ToolMaterial STONE_BASALT = create(1, 155, 2.0f, 3.8f, 4, () -> Ingredient.ofItems(Items.BASALT));
    public static final ToolMaterial STONE_BLACKSTONE = create(1, 170, 2.0f, 3.7f, 5, () -> Ingredient.ofItems(Items.BLACKSTONE));
    public static final ToolMaterial STONE_CALCITE = create(1, 75, 0.5f, 4.5f, 8, () -> Ingredient.ofItems(Items.CALCITE));
    public static final ToolMaterial STONE_DEEPSLATE = create(1, 178, 2.0f, 3.5f, 4, () -> Ingredient.ofItems(Items.COBBLED_DEEPSLATE));
    public static final ToolMaterial STONE_DIORITE = create(1, 140, 1.5f, 3.9f, 6, () -> Ingredient.ofItems(Items.DIORITE));
    public static final ToolMaterial STONE_END_STONE = create(1, 145, 1.5f, 4.1f, 7, () -> Ingredient.ofItems(Items.END_STONE));
    public static final ToolMaterial STONE_GRANITE = create(1, 158, 2.0f, 3.7f, 5, () -> Ingredient.ofItems(Items.GRANITE));
    public static final ToolMaterial STONE_NETHERRACK = create(1, 80, 0.5f, 4.8f, 6, () -> Ingredient.ofItems(Items.NETHERRACK));
    public static final ToolMaterial STONE_SANDSTONE = create(1, 100, 0.5f, 4.2f, 5, () -> Ingredient.ofItems(Items.SANDSTONE));
    public static final ToolMaterial STONE_SMOOTH_BASALT = create(1, 148, 1.8f, 3.9f, 5, () -> Ingredient.ofItems(Items.SMOOTH_BASALT));
    public static final ToolMaterial STONE_TERRACOTTA = create(1, 120, 1.0f, 4.0f, 7, () -> Ingredient.ofItems(Items.TERRACOTTA));
    public static final ToolMaterial STONE_TUFF = create(1, 110, 1.0f, 4.0f, 5, () -> Ingredient.ofItems(Items.TUFF));

    // == WOOD VARIANTS ==
    public static final ToolMaterial WOOD_OAK = create(0, 59, 0.0f, 2.0f, 15, () -> Ingredient.ofItems(Items.OAK_PLANKS));
    public static final ToolMaterial WOOD_SPRUCE = create(0, 65, 0.0f, 2.0f, 14, () -> Ingredient.ofItems(Items.SPRUCE_PLANKS));
    public static final ToolMaterial WOOD_BIRCH = create(0, 48, 0.0f, 2.3f, 18, () -> Ingredient.ofItems(Items.BIRCH_PLANKS));
    public static final ToolMaterial WOOD_JUNGLE = create(0, 62, 0.5f, 2.1f, 14, () -> Ingredient.ofItems(Items.JUNGLE_PLANKS));
    public static final ToolMaterial WOOD_ACACIA = create(0, 68, 1.0f, 1.9f, 12, () -> Ingredient.ofItems(Items.ACACIA_PLANKS));
    public static final ToolMaterial WOOD_DARK_OAK = create(0, 75, 1.0f, 1.8f, 12, () -> Ingredient.ofItems(Items.DARK_OAK_PLANKS));
    public static final ToolMaterial WOOD_MANGROVE = create(0, 70, 0.5f, 1.9f, 13, () -> Ingredient.ofItems(Items.MANGROVE_PLANKS));
    public static final ToolMaterial WOOD_CHERRY = create(0, 42, 0.0f, 2.4f, 20, () -> Ingredient.ofItems(Items.CHERRY_PLANKS));
    public static final ToolMaterial WOOD_BAMBOO = create(0, 35, 0.0f, 2.5f, 16, () -> Ingredient.ofItems(Items.BAMBOO_PLANKS));
    public static final ToolMaterial WOOD_CRIMSON = create(0, 80, 1.5f, 1.8f, 10, () -> Ingredient.ofItems(Items.CRIMSON_PLANKS));
    public static final ToolMaterial WOOD_WARPED = create(0, 72, 0.5f, 2.1f, 17, () -> Ingredient.ofItems(Items.WARPED_PLANKS));

    // == FLINT & HYBRID TIERS ==
    public static final ToolMaterial RFLINT = create(1, 200, 0.5f, 4.5f, 5, () -> Ingredient.ofItems(Items.FLINT));
    public static final ToolMaterial FNI_TOOLS = create(2, 220, 2.0f, 5.5f, 9, () -> Ingredient.ofItems(Items.FLINT));

    // == POLISHED CRYSTAL TIERS ==
    public static final ToolMaterial CAMETHYST = create(2, 580, 2.5f, 6.5f, 14, () -> Ingredient.ofItems(ModItems.CALCIFIED_AMETHYST));
    public static final ToolMaterial ICE_TOOL = create(1, 180, 1.0f, 4.0f, 6, () -> Ingredient.ofItems(ModItems.GLACIAL_SHARD));
    public static final ToolMaterial PQUARTZ = create(2, 640, 2.5f, 7.0f, 10, () -> Ingredient.ofItems(ModItems.POLISHED_QUARTZ));
    public static final ToolMaterial PPRISM = create(2, 560, 2.0f, 6.5f, 12, () -> Ingredient.ofItems(ModItems.POLISHED_PRISMARINE));

    // == LEATHER & NOVELTY ==
    public static final ToolMaterial LEATHER = create(0, 15, 0.0f, 1.5f, 12, () -> Ingredient.ofItems(Items.LEATHER));
    public static final ToolMaterial CAKE = create(0, 30, 0.0f, 1.5f, 1, () -> Ingredient.ofItems(Items.CAKE));
    public static final ToolMaterial BREAD = create(0, 25, 0.0f, 1.5f, 2, () -> Ingredient.ofItems(Items.BREAD));
    public static final ToolMaterial DRIED_KELP = create(0, 15, 0.5f, 1.0f, 1, () -> Ingredient.ofItems(Items.DRIED_KELP));
    public static final ToolMaterial ROTTEN_FLESH = create(0, 30, 0.0f, 0.5f, 3, () -> Ingredient.ofItems(Items.ROTTEN_FLESH));
    public static final ToolMaterial MELON = create(0, 50, 0.5f, 2.5f, 4, () -> Ingredient.ofItems(Items.MELON_SLICE));
    public static final ToolMaterial SWEET_BERRIES = create(0, 45, 1.0f, 2.0f, 5, () -> Ingredient.ofItems(Items.SWEET_BERRIES));
    public static final ToolMaterial PUMPKIN_PIE = create(0, 55, 0.5f, 2.0f, 7, () -> Ingredient.ofItems(Items.PUMPKIN_PIE));
    public static final ToolMaterial MUSHROOM = create(1, 100, 1.0f, 4.0f, 10, () -> Ingredient.ofItems(Items.RED_MUSHROOM));
    public static final ToolMaterial PUFFERFISH = create(1, 80, 1.5f, 3.5f, 8, () -> Ingredient.ofItems(Items.PUFFERFISH));
    public static final ToolMaterial HONEY = create(1, 120, 1.0f, 4.0f, 10, () -> Ingredient.ofItems(Items.HONEY_BOTTLE));
    public static final ToolMaterial CHORUS_FRUIT = create(2, 250, 2.0f, 6.0f, 15, () -> Ingredient.ofItems(Items.CHORUS_FRUIT));
    public static final ToolMaterial GOLDEN_APPLE = create(2, 300, 2.5f, 7.0f, 22, () -> Ingredient.ofItems(Items.GOLDEN_APPLE));

    // == VANILLA MATERIALS ==
    public static final ToolMaterial PAPER = create(0, 8, 0.0f, 1.0f, 8, () -> Ingredient.ofItems(Items.PAPER));
    public static final ToolMaterial FEATHER = create(0, 10, 0.0f, 1.5f, 15, () -> Ingredient.ofItems(Items.FEATHER));
    public static final ToolMaterial GLASS = create(0, 5, 1.0f, 5.0f, 1, () -> Ingredient.ofItems(Items.GLASS_PANE));
    public static final ToolMaterial CACTUS = create(0, 70, 1.5f, 2.5f, 5, () -> Ingredient.ofItems(Items.CACTUS));
    public static final ToolMaterial SPONGE = create(0, 40, 0.0f, 1.5f, 3, () -> Ingredient.ofItems(Items.SPONGE));
    public static final ToolMaterial BONE = create(1, 150, 1.0f, 3.5f, 6, () -> Ingredient.ofItems(Items.BONE));
    public static final ToolMaterial CLAY = create(1, 90, 0.5f, 2.5f, 8, () -> Ingredient.ofItems(Items.CLAY_BALL));
    public static final ToolMaterial NETHER_WART = create(1, 100, 0.5f, 3.0f, 10, () -> Ingredient.ofItems(Items.NETHER_WART));
    public static final ToolMaterial BRICK = create(1, 200, 1.5f, 4.0f, 5, () -> Ingredient.ofItems(Items.BRICK));
    public static final ToolMaterial NETHER_BRICK = create(1, 220, 1.5f, 4.0f, 5, () -> Ingredient.ofItems(Items.NETHER_BRICK));
    public static final ToolMaterial POINTED_DRIPSTONE = create(1, 160, 2.0f, 4.5f, 4, () -> Ingredient.ofItems(Items.POINTED_DRIPSTONE));
    public static final ToolMaterial COPPER = create(1, 200, 1.5f, 5.0f, 8, () -> Ingredient.ofItems(Items.COPPER_INGOT));
    public static final ToolMaterial PHANTOM_MEMBRANE = create(2, 250, 1.5f, 5.0f, 12, () -> Ingredient.ofItems(Items.PHANTOM_MEMBRANE));
    public static final ToolMaterial MAGMA_CREAM = create(2, 200, 2.0f, 4.5f, 8, () -> Ingredient.ofItems(Items.MAGMA_CREAM));
    public static final ToolMaterial SLIME = create(2, 180, 0.5f, 3.5f, 10, () -> Ingredient.ofItems(Items.SLIME_BALL));
    public static final ToolMaterial BLAZE_ROD = create(2, 300, 2.0f, 6.0f, 10, () -> Ingredient.ofItems(Items.BLAZE_ROD));
    public static final ToolMaterial NAUTILUS_SHELL = create(2, 280, 2.0f, 5.5f, 14, () -> Ingredient.ofItems(Items.NAUTILUS_SHELL));
    public static final ToolMaterial PURPUR = create(2, 320, 2.0f, 6.0f, 12, () -> Ingredient.ofItems(Items.POPPED_CHORUS_FRUIT));
    public static final ToolMaterial GHAST_TEAR = create(2, 400, 2.5f, 6.5f, 18, () -> Ingredient.ofItems(Items.GHAST_TEAR));
    public static final ToolMaterial EYE_OF_ENDER = create(2, 450, 2.5f, 7.0f, 20, () -> Ingredient.ofItems(Items.ENDER_EYE));
    public static final ToolMaterial SHULKER_SHELL = create(2, 500, 2.0f, 6.0f, 16, () -> Ingredient.ofItems(Items.SHULKER_SHELL));
    public static final ToolMaterial ECHO_SHARD = create(3, 600, 3.0f, 7.5f, 18, () -> Ingredient.ofItems(Items.ECHO_SHARD));
    public static final ToolMaterial DRAGON_BREATH = create(3, 700, 3.5f, 8.0f, 20, () -> Ingredient.ofItems(Items.DRAGON_BREATH));

    // == ECTOPLASM TIERS ==
    public static final ToolMaterial RECTO = create(1, 150, 1.5f, 4.5f, 10, () -> Ingredient.ofItems(ModItems.ECTOPLASM));
    public static final ToolMaterial ECTOPLASM = create(2, 300, 2.5f, 6.5f, 16, () -> Ingredient.ofItems(ModItems.REFINED_ECTOPLASM));

    private static ToolMaterial create(int miningLevel, int durability, float attackDamage,
                                       float miningSpeed, int enchantability, Supplier<Ingredient> repairIngredient) {
        return new ToolMaterial() {
            @Override
            public int getDurability() { return durability; }

            @Override
            public float getMiningSpeedMultiplier() { return miningSpeed; }

            @Override
            public float getAttackDamage() { return attackDamage; }

            @Override
            public int getMiningLevel() { return miningLevel; }

            @Override
            public int getEnchantability() { return enchantability; }

            @Override
            public Ingredient getRepairIngredient() { return repairIngredient.get(); }
        };
    }
}
