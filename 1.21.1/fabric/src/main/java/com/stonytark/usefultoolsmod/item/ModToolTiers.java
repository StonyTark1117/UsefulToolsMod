package com.stonytark.usefultoolsmod.item;

import com.stonytark.usefultoolsmod.block.ModBlocks;
import com.stonytark.usefultoolsmod.util.ModTags;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.block.Block;
import net.minecraft.registry.tag.BlockTags;

import java.util.function.Supplier;

public class ModToolTiers {

    // ── Main custom tiers ────────────────────────────────────────────
    public static final ToolMaterial REMERALD = create(ModTags.Blocks.INCORRECT_JEM_TOOL,
            1361, 4.5f, 6, 22, () -> Ingredient.ofItems(Items.EMERALD));

    public static final ToolMaterial PEMERALD = create(ModTags.Blocks.INCORRECT_SEM_TOOL,
            1561, 3.2f, 7, 30, () -> Ingredient.ofItems(ModItems.SEM));

    public static final ToolMaterial ROBSIDIAN = create(ModTags.Blocks.INCORRECT_JOB_TOOL,
            1650, 6f, 9, 15, () -> Ingredient.ofItems(ModItems.OBSHARD));

    public static final ToolMaterial POBSIDIAN = create(ModTags.Blocks.INCORRECT_SOB_TOOL,
            2031, 5f, 10, 18, () -> Ingredient.ofItems(ModItems.OBINGOT));

    public static final ToolMaterial OVERPOWER = create(ModTags.Blocks.INCORRECT_OP_TOOL,
            9999, 30f, 25, 35, () -> Ingredient.ofItems(ModBlocks.SOBLOCK));

    public static final ToolMaterial HREDSTONE = create(ModTags.Blocks.INCORRECT_HRED_TOOL,
            600, 3f, 8, 20, () -> Ingredient.ofItems(ModItems.HRED));

    public static final ToolMaterial HGLOWSTONE = create(ModTags.Blocks.INCORRECT_HGLOW_TOOL,
            500, 2f, 7.0f, 28, () -> Ingredient.ofItems(ModItems.HGLOW));

    public static final ToolMaterial RGOLD = create(ModTags.Blocks.INCORRECT_RGOLD_TOOL,
            1200, 3.5f, 8, 16, () -> Ingredient.ofItems(ModItems.RGOLD));

    public static final ToolMaterial RLAPIS = create(ModTags.Blocks.INCORRECT_RLAPIS_TOOL,
            1100, 3.2f, 9, 32, () -> Ingredient.ofItems(ModItems.RLAPIS));

    // ── Rough material tiers ────────────────────────────────────────────
    public static final ToolMaterial RECTO = create(ModTags.Blocks.INCORRECT_RECTO_TOOL,
            230, 3.5f, 5, 12, () -> Ingredient.ofItems(ModItems.ECTOPLASM));

    public static final ToolMaterial ECTO = create(ModTags.Blocks.INCORRECT_ECTO_TOOL,
            650, 4.0f, 7, 22, () -> Ingredient.ofItems(ModItems.REFINED_ECTOPLASM));

    public static final ToolMaterial COAL_TOOL = create(BlockTags.INCORRECT_FOR_WOODEN_TOOL,
            120, 3.0f, 0, 5, () -> Ingredient.ofItems(ModItems.HARDENED_COAL));

    public static final ToolMaterial RRAW_GOLD = create(BlockTags.INCORRECT_FOR_WOODEN_TOOL,
            80, 0.0f, 12.0f, 25, () -> Ingredient.ofItems(Items.RAW_GOLD));

    public static final ToolMaterial RRAW_COPPER = create(BlockTags.INCORRECT_FOR_STONE_TOOL,
            170, 1.0f, 5.0f, 10, () -> Ingredient.ofItems(Items.RAW_COPPER));

    public static final ToolMaterial RRAW_IRON = create(BlockTags.INCORRECT_FOR_IRON_TOOL,
            200, 1.5f, 5.5f, 10, () -> Ingredient.ofItems(Items.RAW_IRON));

    public static final ToolMaterial RRAW_RGOLD = create(BlockTags.INCORRECT_FOR_IRON_TOOL,
            600, 2.0f, 7.0f, 14, () -> Ingredient.ofItems(ModItems.RAW_RGOLD));

    public static final ToolMaterial RSCRAP = create(BlockTags.INCORRECT_FOR_DIAMOND_TOOL,
            800, 2.5f, 7.5f, 12, () -> Ingredient.ofItems(Items.NETHERITE_SCRAP));

    // ── Crystal rough tiers ────────────────────────────────────────────
    public static final ToolMaterial RAMETHYST = create(BlockTags.INCORRECT_FOR_STONE_TOOL,
            250, 1.5f, 5.0f, 12, () -> Ingredient.ofItems(Items.AMETHYST_SHARD));

    public static final ToolMaterial SNOW_TOOL = create(BlockTags.INCORRECT_FOR_WOODEN_TOOL,
            45, 0.0f, 3.0f, 4, () -> Ingredient.ofItems(Items.SNOWBALL));

    public static final ToolMaterial RQUARTZ = create(BlockTags.INCORRECT_FOR_STONE_TOOL,
            310, 1.5f, 5.5f, 10, () -> Ingredient.ofItems(Items.QUARTZ));

    public static final ToolMaterial RPRISM = create(BlockTags.INCORRECT_FOR_STONE_TOOL,
            240, 1.5f, 4.5f, 8, () -> Ingredient.ofItems(Items.PRISMARINE_SHARD));

    // ── Stone variants ────────────────────────────────────────────
    public static final ToolMaterial STONE_ANDESITE = create(BlockTags.INCORRECT_FOR_STONE_TOOL,
            131, 1.5f, 4.0f, 5, () -> Ingredient.ofItems(Items.ANDESITE));

    public static final ToolMaterial STONE_BASALT = create(BlockTags.INCORRECT_FOR_STONE_TOOL,
            155, 2.0f, 3.8f, 4, () -> Ingredient.ofItems(Items.BASALT));

    public static final ToolMaterial STONE_BLACKSTONE = create(BlockTags.INCORRECT_FOR_STONE_TOOL,
            170, 2.0f, 3.7f, 5, () -> Ingredient.ofItems(Items.BLACKSTONE));

    public static final ToolMaterial STONE_CALCITE = create(BlockTags.INCORRECT_FOR_STONE_TOOL,
            75, 0.5f, 4.5f, 8, () -> Ingredient.ofItems(Items.CALCITE));

    public static final ToolMaterial STONE_DEEPSLATE = create(BlockTags.INCORRECT_FOR_STONE_TOOL,
            178, 2.0f, 3.5f, 4, () -> Ingredient.ofItems(Items.COBBLED_DEEPSLATE));

    public static final ToolMaterial STONE_DIORITE = create(BlockTags.INCORRECT_FOR_STONE_TOOL,
            140, 1.5f, 3.9f, 6, () -> Ingredient.ofItems(Items.DIORITE));

    public static final ToolMaterial STONE_END_STONE = create(BlockTags.INCORRECT_FOR_STONE_TOOL,
            145, 1.5f, 4.1f, 7, () -> Ingredient.ofItems(Items.END_STONE));

    public static final ToolMaterial STONE_GRANITE = create(BlockTags.INCORRECT_FOR_STONE_TOOL,
            158, 2.0f, 3.7f, 5, () -> Ingredient.ofItems(Items.GRANITE));

    public static final ToolMaterial STONE_NETHERRACK = create(BlockTags.INCORRECT_FOR_STONE_TOOL,
            80, 0.5f, 4.8f, 6, () -> Ingredient.ofItems(Items.NETHERRACK));

    public static final ToolMaterial STONE_SANDSTONE = create(BlockTags.INCORRECT_FOR_STONE_TOOL,
            100, 0.5f, 4.2f, 5, () -> Ingredient.ofItems(Items.SANDSTONE));

    public static final ToolMaterial STONE_SMOOTH_BASALT = create(BlockTags.INCORRECT_FOR_STONE_TOOL,
            148, 1.8f, 3.9f, 5, () -> Ingredient.ofItems(Items.SMOOTH_BASALT));

    public static final ToolMaterial STONE_TERRACOTTA = create(BlockTags.INCORRECT_FOR_STONE_TOOL,
            120, 1.0f, 4.0f, 7, () -> Ingredient.ofItems(Items.TERRACOTTA));

    public static final ToolMaterial STONE_TUFF = create(BlockTags.INCORRECT_FOR_STONE_TOOL,
            110, 1.0f, 4.0f, 5, () -> Ingredient.ofItems(Items.TUFF));

    // ── Leather and wood variants ────────────────────────────────────────────
    public static final ToolMaterial LEATHER_TOOL = create(BlockTags.INCORRECT_FOR_WOODEN_TOOL,
            60, 2.0f, 1.0f, 15, () -> Ingredient.ofItems(Items.LEATHER));

    public static final ToolMaterial OAK_TOOL = create(BlockTags.INCORRECT_FOR_WOODEN_TOOL,
            59, 2.0f, 1.0f, 15, () -> Ingredient.ofItems(Items.OAK_PLANKS));

    public static final ToolMaterial SPRUCE_TOOL = create(BlockTags.INCORRECT_FOR_WOODEN_TOOL,
            59, 2.0f, 1.0f, 15, () -> Ingredient.ofItems(Items.SPRUCE_PLANKS));

    public static final ToolMaterial BIRCH_TOOL = create(BlockTags.INCORRECT_FOR_WOODEN_TOOL,
            59, 2.0f, 1.0f, 15, () -> Ingredient.ofItems(Items.BIRCH_PLANKS));

    public static final ToolMaterial JUNGLE_TOOL = create(BlockTags.INCORRECT_FOR_WOODEN_TOOL,
            59, 2.0f, 1.0f, 15, () -> Ingredient.ofItems(Items.JUNGLE_PLANKS));

    public static final ToolMaterial ACACIA_TOOL = create(BlockTags.INCORRECT_FOR_WOODEN_TOOL,
            59, 2.0f, 1.0f, 15, () -> Ingredient.ofItems(Items.ACACIA_PLANKS));

    public static final ToolMaterial DARK_OAK_TOOL = create(BlockTags.INCORRECT_FOR_WOODEN_TOOL,
            59, 2.0f, 1.0f, 15, () -> Ingredient.ofItems(Items.DARK_OAK_PLANKS));

    public static final ToolMaterial MANGROVE_TOOL = create(BlockTags.INCORRECT_FOR_WOODEN_TOOL,
            59, 2.0f, 1.0f, 15, () -> Ingredient.ofItems(Items.MANGROVE_PLANKS));

    public static final ToolMaterial CHERRY_TOOL = create(BlockTags.INCORRECT_FOR_WOODEN_TOOL,
            59, 2.0f, 1.0f, 15, () -> Ingredient.ofItems(Items.CHERRY_PLANKS));

    public static final ToolMaterial BAMBOO_TOOL = create(BlockTags.INCORRECT_FOR_WOODEN_TOOL,
            59, 2.0f, 1.0f, 15, () -> Ingredient.ofItems(Items.BAMBOO_PLANKS));

    public static final ToolMaterial CRIMSON_TOOL = create(BlockTags.INCORRECT_FOR_WOODEN_TOOL,
            59, 2.0f, 1.0f, 15, () -> Ingredient.ofItems(Items.CRIMSON_PLANKS));

    public static final ToolMaterial WARPED_TOOL = create(BlockTags.INCORRECT_FOR_WOODEN_TOOL,
            59, 2.0f, 1.0f, 15, () -> Ingredient.ofItems(Items.WARPED_PLANKS));

    // ── Flint ────────────────────────────────────────────
    public static final ToolMaterial RFLINT = create(BlockTags.INCORRECT_FOR_WOODEN_TOOL,
            100, 2.5f, 1.5f, 8, () -> Ingredient.ofItems(Items.FLINT));

    public static final ToolMaterial FNI = create(BlockTags.INCORRECT_FOR_STONE_TOOL,
            131, 1.5f, 2.5f, 9, () -> Ingredient.ofItems(Items.FLINT));

    // ── Food tool tiers (weak novelty items) ────────────────────────────────────────────
    public static final ToolMaterial CAKE_TOOL = create(BlockTags.INCORRECT_FOR_WOODEN_TOOL,
            20, 1.0f, -2.0f, 1, () -> Ingredient.ofItems(Items.CAKE));

    public static final ToolMaterial DRIED_KELP_TOOL = create(BlockTags.INCORRECT_FOR_WOODEN_TOOL,
            45, 1.5f, -1.0f, 1, () -> Ingredient.ofItems(Items.DRIED_KELP));

    public static final ToolMaterial ROTTEN_FLESH_TOOL = create(BlockTags.INCORRECT_FOR_WOODEN_TOOL,
            50, 1.0f, 0.0f, 3, () -> Ingredient.ofItems(Items.ROTTEN_FLESH));

    public static final ToolMaterial BREAD_TOOL = create(BlockTags.INCORRECT_FOR_WOODEN_TOOL,
            40, 1.5f, -0.5f, 2, () -> Ingredient.ofItems(Items.BREAD));

    public static final ToolMaterial SWEET_BERRY_TOOL = create(BlockTags.INCORRECT_FOR_WOODEN_TOOL,
            55, 2.0f, 0.5f, 5, () -> Ingredient.ofItems(Items.SWEET_BERRIES));

    public static final ToolMaterial PUMPKIN_PIE_TOOL = create(BlockTags.INCORRECT_FOR_WOODEN_TOOL,
            65, 1.5f, 1.0f, 7, () -> Ingredient.ofItems(Items.PUMPKIN_PIE));

    public static final ToolMaterial MELON_TOOL = create(BlockTags.INCORRECT_FOR_WOODEN_TOOL,
            70, 2.0f, 1.5f, 4, () -> Ingredient.ofItems(Items.MELON_SLICE));

    public static final ToolMaterial MUSHROOM_TOOL = create(BlockTags.INCORRECT_FOR_WOODEN_TOOL,
            80, 2.5f, 2.0f, 10, () -> Ingredient.ofItems(Items.RED_MUSHROOM));

    public static final ToolMaterial PUFFERFISH_TOOL = create(BlockTags.INCORRECT_FOR_WOODEN_TOOL,
            75, 2.2f, 1.5f, 8, () -> Ingredient.ofItems(Items.PUFFERFISH));

    public static final ToolMaterial HONEY_TOOL = create(BlockTags.INCORRECT_FOR_WOODEN_TOOL,
            85, 2.0f, 2.5f, 10, () -> Ingredient.ofItems(Items.HONEY_BOTTLE));

    public static final ToolMaterial CHORUS_FRUIT_TOOL = create(BlockTags.INCORRECT_FOR_STONE_TOOL,
            180, 2.0f, 4.0f, 15, () -> Ingredient.ofItems(Items.CHORUS_FRUIT));

    public static final ToolMaterial GOLDEN_APPLE_TOOL = create(BlockTags.INCORRECT_FOR_IRON_TOOL,
            250, 3.0f, 5.0f, 22, () -> Ingredient.ofItems(Items.GOLDEN_APPLE));

    // ── Polished crystal tiers ────────────────────────────────────────────
    public static final ToolMaterial CAMETHYST = create(BlockTags.INCORRECT_FOR_STONE_TOOL,
            410, 2.0f, 5.5f, 14, () -> Ingredient.ofItems(ModItems.CALCIFIED_AMETHYST));

    public static final ToolMaterial ICE_TOOL = create(BlockTags.INCORRECT_FOR_WOODEN_TOOL,
            45, 1.0f, 2.0f, 8, () -> Ingredient.ofItems(ModItems.GLACIAL_SHARD));

    public static final ToolMaterial PQUARTZ = create(BlockTags.INCORRECT_FOR_STONE_TOOL,
            450, 1.5f, 5.8f, 10, () -> Ingredient.ofItems(ModItems.POLISHED_QUARTZ));

    public static final ToolMaterial PPRISM = create(BlockTags.INCORRECT_FOR_STONE_TOOL,
            420, 1.0f, 5.0f, 12, () -> Ingredient.ofItems(ModItems.POLISHED_PRISMARINE));

    // ── Vanilla material tiers ────────────────────────────────────────────
    public static final ToolMaterial PAPER = create(BlockTags.INCORRECT_FOR_WOODEN_TOOL,
            8, 0.0f, 1.0f, 8, () -> Ingredient.ofItems(Items.PAPER));

    public static final ToolMaterial FEATHER = create(BlockTags.INCORRECT_FOR_WOODEN_TOOL,
            10, 0.0f, 1.5f, 15, () -> Ingredient.ofItems(Items.FEATHER));

    public static final ToolMaterial GLASS = create(BlockTags.INCORRECT_FOR_WOODEN_TOOL,
            5, 1.0f, 5.0f, 1, () -> Ingredient.ofItems(Items.GLASS_PANE));

    public static final ToolMaterial CACTUS = create(BlockTags.INCORRECT_FOR_WOODEN_TOOL,
            70, 1.5f, 2.5f, 5, () -> Ingredient.ofItems(Items.CACTUS));

    public static final ToolMaterial SPONGE = create(BlockTags.INCORRECT_FOR_WOODEN_TOOL,
            40, 0.0f, 1.5f, 3, () -> Ingredient.ofItems(Items.SPONGE));

    public static final ToolMaterial BONE = create(BlockTags.INCORRECT_FOR_STONE_TOOL,
            150, 1.0f, 3.5f, 6, () -> Ingredient.ofItems(Items.BONE));

    public static final ToolMaterial CLAY = create(BlockTags.INCORRECT_FOR_STONE_TOOL,
            90, 0.5f, 2.5f, 8, () -> Ingredient.ofItems(Items.CLAY_BALL));

    public static final ToolMaterial NETHER_WART = create(BlockTags.INCORRECT_FOR_STONE_TOOL,
            100, 0.5f, 3.0f, 10, () -> Ingredient.ofItems(Items.NETHER_WART));

    public static final ToolMaterial BRICK = create(BlockTags.INCORRECT_FOR_STONE_TOOL,
            200, 1.5f, 4.0f, 5, () -> Ingredient.ofItems(Items.BRICK));

    public static final ToolMaterial NETHER_BRICK = create(BlockTags.INCORRECT_FOR_STONE_TOOL,
            220, 1.5f, 4.0f, 5, () -> Ingredient.ofItems(Items.NETHER_BRICK));

    public static final ToolMaterial POINTED_DRIPSTONE = create(BlockTags.INCORRECT_FOR_STONE_TOOL,
            160, 2.0f, 4.5f, 4, () -> Ingredient.ofItems(Items.POINTED_DRIPSTONE));

    public static final ToolMaterial COPPER = create(BlockTags.INCORRECT_FOR_STONE_TOOL,
            200, 1.5f, 5.0f, 8, () -> Ingredient.ofItems(Items.COPPER_INGOT));

    public static final ToolMaterial PHANTOM_MEMBRANE = create(BlockTags.INCORRECT_FOR_IRON_TOOL,
            250, 1.5f, 5.0f, 12, () -> Ingredient.ofItems(Items.PHANTOM_MEMBRANE));

    public static final ToolMaterial MAGMA_CREAM = create(BlockTags.INCORRECT_FOR_IRON_TOOL,
            200, 2.0f, 4.5f, 8, () -> Ingredient.ofItems(Items.MAGMA_CREAM));

    public static final ToolMaterial SLIME = create(BlockTags.INCORRECT_FOR_IRON_TOOL,
            180, 0.5f, 3.5f, 10, () -> Ingredient.ofItems(Items.SLIME_BALL));

    public static final ToolMaterial BLAZE_ROD = create(BlockTags.INCORRECT_FOR_IRON_TOOL,
            300, 2.0f, 6.0f, 10, () -> Ingredient.ofItems(Items.BLAZE_ROD));

    public static final ToolMaterial NAUTILUS_SHELL = create(BlockTags.INCORRECT_FOR_IRON_TOOL,
            280, 2.0f, 5.5f, 14, () -> Ingredient.ofItems(Items.NAUTILUS_SHELL));

    public static final ToolMaterial PURPUR = create(BlockTags.INCORRECT_FOR_IRON_TOOL,
            320, 2.0f, 6.0f, 12, () -> Ingredient.ofItems(Items.POPPED_CHORUS_FRUIT));

    public static final ToolMaterial GHAST_TEAR = create(BlockTags.INCORRECT_FOR_IRON_TOOL,
            400, 2.5f, 6.5f, 18, () -> Ingredient.ofItems(Items.GHAST_TEAR));

    public static final ToolMaterial EYE_OF_ENDER = create(BlockTags.INCORRECT_FOR_IRON_TOOL,
            450, 2.5f, 7.0f, 20, () -> Ingredient.ofItems(Items.ENDER_EYE));

    public static final ToolMaterial SHULKER_SHELL = create(BlockTags.INCORRECT_FOR_IRON_TOOL,
            500, 2.0f, 6.0f, 16, () -> Ingredient.ofItems(Items.SHULKER_SHELL));

    public static final ToolMaterial ECHO_SHARD = create(BlockTags.INCORRECT_FOR_DIAMOND_TOOL,
            600, 3.0f, 7.5f, 18, () -> Ingredient.ofItems(Items.ECHO_SHARD));

    public static final ToolMaterial DRAGON_BREATH = create(BlockTags.INCORRECT_FOR_DIAMOND_TOOL,
            700, 3.5f, 8.0f, 20, () -> Ingredient.ofItems(Items.DRAGON_BREATH));

    private static ToolMaterial create(TagKey<Block> inverseTag, int durability, float attackDamage, float miningSpeed,
                                       int enchantability, Supplier<Ingredient> repairIngredient) {
        return new ToolMaterial() {
            @Override
            public int getDurability() { return durability; }
            @Override
            public float getMiningSpeedMultiplier() { return miningSpeed; }
            @Override
            public float getAttackDamage() { return attackDamage; }
            @Override
            public TagKey<Block> getInverseTag() { return inverseTag; }
            @Override
            public int getEnchantability() { return enchantability; }
            @Override
            public Ingredient getRepairIngredient() { return repairIngredient.get(); }
        };
    }
}
