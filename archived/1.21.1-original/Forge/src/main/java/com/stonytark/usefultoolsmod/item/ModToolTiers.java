package com.stonytark.usefultoolsmod.item;

import com.stonytark.usefultoolsmod.block.ModBlocks;
import com.stonytark.usefultoolsmod.util.ModTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.ForgeTier;

public class ModToolTiers {
    public static final Tier REMERALD = new ForgeTier(1361, 6, 4.5f, 22,
            ModTags.Blocks.NEEDS_JEM_TOOL, () -> Ingredient.of(Items.EMERALD),
            ModTags.Blocks.INCORRECT_JEM_TOOL);

    public static final Tier PEMERALD = new ForgeTier(1561, 7, 3.2f, 30,
            ModTags.Blocks.NEEDS_SEM_TOOL, () -> Ingredient.of(ModItems.SEM.get()),
            ModTags.Blocks.INCORRECT_SEM_TOOL);

    public static final Tier ROBSIDIAN = new ForgeTier(1650, 9, 6f, 15,
            ModTags.Blocks.NEEDS_JOB_TOOL, () -> Ingredient.of(ModItems.OBSHARD.get()),
            ModTags.Blocks.INCORRECT_JOB_TOOL);

    public static final Tier POBSIDIAN = new ForgeTier(2031, 10, 5f, 18,
            ModTags.Blocks.NEEDS_SOB_TOOL, () -> Ingredient.of(ModItems.OBINGOT.get()),
            ModTags.Blocks.INCORRECT_SOB_TOOL);

    public static final Tier OVERPOWER = new ForgeTier(9999, 25, 30f, 35,
            ModTags.Blocks.NEEDS_OP_TOOL, () -> Ingredient.of(ModBlocks.SOBLOCK.get()),
            ModTags.Blocks.INCORRECT_OP_TOOL);

    public static final Tier HREDSTONE = new ForgeTier(600, 8, 3f, 20,
            ModTags.Blocks.NEEDS_HRED_TOOL, () -> Ingredient.of(ModItems.HRED.get()),
            ModTags.Blocks.INCORRECT_HRED_TOOL);

    /**
     * Hardened Glowstone — iron mining level, magical/light-based complement to HREDSTONE.
     * Lower durability and attack than HRED, but significantly higher enchantability.
     * Sacrifices raw power for synergy with enchantments.
     */
    public static final Tier HGLOWSTONE = new ForgeTier(500, 7.0f, 2f, 28,
            ModTags.Blocks.NEEDS_HGLOW_TOOL, () -> Ingredient.of(ModItems.HGLOW.get()),
            ModTags.Blocks.INCORRECT_HGLOW_TOOL);

    public static final Tier RGOLD = new ForgeTier(1200, 8, 3.5f, 16,
            ModTags.Blocks.NEEDS_RGOLD_TOOL, () -> Ingredient.of(ModItems.RGOLD.get()),
            ModTags.Blocks.INCORRECT_RGOLD_TOOL);

    public static final Tier RLAPIS = new ForgeTier(1100, 9, 3.2f, 32,
            ModTags.Blocks.NEEDS_RLAPIS_TOOL, () -> Ingredient.of(ModItems.RGOLD.get()),
            ModTags.Blocks.INCORRECT_RLAPIS_TOOL);

    /**
     * Coal tool tier — sits between wood and stone.
     * Durability closer to stone, mining level equivalent to wood.
     */
    public static final Tier COAL_TOOL = new ForgeTier(120, 0, 3.0f, 5,
            BlockTags.INCORRECT_FOR_WOODEN_TOOL,
            () -> Ingredient.of(ModItems.HARDENED_COAL.get()),
            BlockTags.INCORRECT_FOR_WOODEN_TOOL);

    // ── Raw material rough tiers ────────────────────────────────────────────

    /** Rough Raw Gold — wood mining level, very fast but fragile and inaccurate. */
    public static final Tier RRAW_GOLD = new ForgeTier(80, 12.0f, 0.0f, 25,
            BlockTags.NEEDS_STONE_TOOL,
            () -> Ingredient.of(Items.RAW_GOLD),
            BlockTags.INCORRECT_FOR_WOODEN_TOOL);

    /** Rough Raw Copper — stone mining level, decent all-rounder from raw ore. */
    public static final Tier RRAW_COPPER = new ForgeTier(170, 5.0f, 1.0f, 10,
            BlockTags.NEEDS_IRON_TOOL,
            () -> Ingredient.of(Items.RAW_COPPER),
            BlockTags.INCORRECT_FOR_STONE_TOOL);

    /** Rough Raw Iron — iron mining level, slightly weaker than refined iron. */
    public static final Tier RRAW_IRON = new ForgeTier(200, 5.5f, 1.5f, 10,
            BlockTags.NEEDS_DIAMOND_TOOL,
            () -> Ingredient.of(Items.RAW_IRON),
            BlockTags.INCORRECT_FOR_IRON_TOOL);

    /** Rough Raw Ferrous Gold — iron mining level, bridge between raw and refined rgold. */
    public static final Tier RRAW_RGOLD = new ForgeTier(600, 7.0f, 2.0f, 14,
            BlockTags.NEEDS_DIAMOND_TOOL,
            () -> Ingredient.of(ModItems.RAW_RGOLD.get()),
            BlockTags.INCORRECT_FOR_IRON_TOOL);

    /** Rough Netherite Scrap — diamond mining level, partially processed ancient metal. */
    public static final Tier RSCRAP = new ForgeTier(800, 7.5f, 2.5f, 12,
            BlockTags.NEEDS_DIAMOND_TOOL,
            () -> Ingredient.of(Items.NETHERITE_SCRAP),
            BlockTags.INCORRECT_FOR_DIAMOND_TOOL);

    // ── Crystal / element rough tiers (tools-only) ────────────────────────────

    /**
     * Rough Amethyst — stone mining level.
     * Geode shards are mid-game but unrefined; comparable to RRAW_COPPER but enchantable.
     */
    public static final Tier RAMETHYST = new ForgeTier(250, 5.0f, 1.5f, 12,
            BlockTags.NEEDS_IRON_TOOL,
            () -> Ingredient.of(Items.AMETHYST_SHARD),
            BlockTags.INCORRECT_FOR_STONE_TOOL);

    /**
     * Snow — wood mining level, barely functional.
     * Snowballs are free and abundant; tools shatter almost immediately.
     */
    public static final Tier SNOW_TOOL = new ForgeTier(45, 3.0f, 0.0f, 4,
            BlockTags.NEEDS_STONE_TOOL,
            () -> Ingredient.of(Items.SNOWBALL),
            BlockTags.INCORRECT_FOR_WOODEN_TOOL);

    /**
     * Rough Quartz — stone mining level, slightly faster than amethyst.
     * Nether quartz requires portal access, giving it a speed edge.
     */
    public static final Tier RQUARTZ = new ForgeTier(310, 5.5f, 1.5f, 10,
            BlockTags.NEEDS_IRON_TOOL,
            () -> Ingredient.of(Items.QUARTZ),
            BlockTags.INCORRECT_FOR_STONE_TOOL);

    /**
     * Rough Prismarine — stone mining level, slightly weaker than amethyst.
     * Prismarine shards require defeating guardians but are still raw crystal.
     */
    public static final Tier RPRISM = new ForgeTier(240, 4.5f, 1.5f, 8,
            BlockTags.NEEDS_IRON_TOOL,
            () -> Ingredient.of(Items.PRISMARINE_SHARD),
            BlockTags.INCORRECT_FOR_STONE_TOOL);

    // ── Stone rock variant tiers ─────────────────────────────────────────────────
    // All stone-level: NEEDS_IRON_TOOL correct, INCORRECT_FOR_STONE_TOOL drops.
    // Repair = the respective block item.  Stats reflect real-world hardness/weight.

    /** Andesite — baseline igneous rock.  Matches vanilla stone exactly. */
    public static final Tier STONE_ANDESITE = new ForgeTier(131, 4.0f, 1.5f, 5,
            BlockTags.NEEDS_IRON_TOOL, () -> Ingredient.of(Items.ANDESITE),
            BlockTags.INCORRECT_FOR_STONE_TOOL);

    /** Basalt — dense columnar volcanic rock.  Durable but heavy and slow. */
    public static final Tier STONE_BASALT = new ForgeTier(155, 3.8f, 2.0f, 4,
            BlockTags.NEEDS_IRON_TOOL, () -> Ingredient.of(Items.BASALT),
            BlockTags.INCORRECT_FOR_STONE_TOOL);

    /** Blackstone — hardened Nether volcanic rock.  Toughest stone variant. */
    public static final Tier STONE_BLACKSTONE = new ForgeTier(170, 3.7f, 2.0f, 5,
            BlockTags.NEEDS_IRON_TOOL, () -> Ingredient.of(Items.BLACKSTONE),
            BlockTags.INCORRECT_FOR_STONE_TOOL);

    /** Calcite — soft crystalline mineral (Mohs ~3).  Fragile but enchantable and light. */
    public static final Tier STONE_CALCITE = new ForgeTier(75, 4.5f, 0.5f, 8,
            BlockTags.NEEDS_IRON_TOOL, () -> Ingredient.of(Items.CALCITE),
            BlockTags.INCORRECT_FOR_STONE_TOOL);

    /** Cobbled Deepslate — compressed deep-layer rock.  Very durable, noticeably sluggish. */
    public static final Tier STONE_DEEPSLATE = new ForgeTier(178, 3.5f, 2.0f, 4,
            BlockTags.NEEDS_IRON_TOOL, () -> Ingredient.of(Items.COBBLED_DEEPSLATE),
            BlockTags.INCORRECT_FOR_STONE_TOOL);

    /** Diorite — coarser igneous rock.  Slightly better all-round than andesite. */
    public static final Tier STONE_DIORITE = new ForgeTier(140, 3.9f, 1.5f, 6,
            BlockTags.NEEDS_IRON_TOOL, () -> Ingredient.of(Items.DIORITE),
            BlockTags.INCORRECT_FOR_STONE_TOOL);

    /** End Stone — dense alien material.  Hard and reliable, high enchantability. */
    public static final Tier STONE_END_STONE = new ForgeTier(145, 4.1f, 1.5f, 7,
            BlockTags.NEEDS_IRON_TOOL, () -> Ingredient.of(Items.END_STONE),
            BlockTags.INCORRECT_FOR_STONE_TOOL);

    /** Granite — very hard igneous rock.  Strong and heavy. */
    public static final Tier STONE_GRANITE = new ForgeTier(158, 3.7f, 2.0f, 5,
            BlockTags.NEEDS_IRON_TOOL, () -> Ingredient.of(Items.GRANITE),
            BlockTags.INCORRECT_FOR_STONE_TOOL);

    /** Netherrack — crumbly Nether rock.  Barely functional; fastest but weakest. */
    public static final Tier STONE_NETHERRACK = new ForgeTier(80, 4.8f, 0.5f, 6,
            BlockTags.NEEDS_IRON_TOOL, () -> Ingredient.of(Items.NETHERRACK),
            BlockTags.INCORRECT_FOR_STONE_TOOL);

    /** Sandstone — soft sedimentary rock.  Light and nimble but brittle. */
    public static final Tier STONE_SANDSTONE = new ForgeTier(100, 4.2f, 0.5f, 5,
            BlockTags.NEEDS_IRON_TOOL, () -> Ingredient.of(Items.SANDSTONE),
            BlockTags.INCORRECT_FOR_STONE_TOOL);

    /** Smooth Basalt — polished volcanic rock.  Balanced; sits between basalt and andesite. */
    public static final Tier STONE_SMOOTH_BASALT = new ForgeTier(148, 3.9f, 1.8f, 5,
            BlockTags.NEEDS_IRON_TOOL, () -> Ingredient.of(Items.SMOOTH_BASALT),
            BlockTags.INCORRECT_FOR_STONE_TOOL);

    /** Terracotta — baked clay.  Moderate stats; more enchantable than plain stone. */
    public static final Tier STONE_TERRACOTTA = new ForgeTier(120, 4.0f, 1.0f, 7,
            BlockTags.NEEDS_IRON_TOOL, () -> Ingredient.of(Items.TERRACOTTA),
            BlockTags.INCORRECT_FOR_STONE_TOOL);

    /** Tuff — compressed volcanic ash.  Below-average damage and durability. */
    public static final Tier STONE_TUFF = new ForgeTier(110, 4.0f, 1.0f, 5,
            BlockTags.NEEDS_IRON_TOOL, () -> Ingredient.of(Items.TUFF),
            BlockTags.INCORRECT_FOR_STONE_TOOL);

    // ── Wood variant tiers ───────────────────────────────────────────────────────
    // All wood-level: INCORRECT_FOR_WOODEN_TOOL for both needs/incorrect tags.
    // Repair = the respective planks item.  Stats reflect real-world wood properties.

    /** Oak — reliable, versatile Overworld baseline.  Matches vanilla WOOD exactly. */
    public static final Tier WOOD_OAK = new ForgeTier(59, 2.0f, 0.0f, 15,
            BlockTags.INCORRECT_FOR_WOODEN_TOOL, () -> Ingredient.of(Items.OAK_PLANKS),
            BlockTags.INCORRECT_FOR_WOODEN_TOOL);

    /** Spruce — sturdy boreal conifer.  Slightly more durable than oak. */
    public static final Tier WOOD_SPRUCE = new ForgeTier(65, 2.0f, 0.0f, 14,
            BlockTags.INCORRECT_FOR_WOODEN_TOOL, () -> Ingredient.of(Items.SPRUCE_PLANKS),
            BlockTags.INCORRECT_FOR_WOODEN_TOOL);

    /** Birch — light, pale hardwood.  Fast and enchantable but fragile. */
    public static final Tier WOOD_BIRCH = new ForgeTier(48, 2.3f, 0.0f, 18,
            BlockTags.INCORRECT_FOR_WOODEN_TOOL, () -> Ingredient.of(Items.BIRCH_PLANKS),
            BlockTags.INCORRECT_FOR_WOODEN_TOOL);

    /** Jungle — dense tropical timber.  Slightly tougher and sharper than oak. */
    public static final Tier WOOD_JUNGLE = new ForgeTier(62, 2.1f, 0.5f, 14,
            BlockTags.INCORRECT_FOR_WOODEN_TOOL, () -> Ingredient.of(Items.JUNGLE_PLANKS),
            BlockTags.INCORRECT_FOR_WOODEN_TOOL);

    /** Acacia — hard African savanna wood.  Durable with a keen edge but slow. */
    public static final Tier WOOD_ACACIA = new ForgeTier(68, 1.9f, 1.0f, 12,
            BlockTags.INCORRECT_FOR_WOODEN_TOOL, () -> Ingredient.of(Items.ACACIA_PLANKS),
            BlockTags.INCORRECT_FOR_WOODEN_TOOL);

    /** Dark Oak — dense, heavy old-growth wood.  Most durable overworld wood, slowest. */
    public static final Tier WOOD_DARK_OAK = new ForgeTier(75, 1.8f, 1.0f, 12,
            BlockTags.INCORRECT_FOR_WOODEN_TOOL, () -> Ingredient.of(Items.DARK_OAK_PLANKS),
            BlockTags.INCORRECT_FOR_WOODEN_TOOL);

    /** Mangrove — tough, water-resistant tropical wood.  Good durability. */
    public static final Tier WOOD_MANGROVE = new ForgeTier(70, 1.9f, 0.5f, 13,
            BlockTags.INCORRECT_FOR_WOODEN_TOOL, () -> Ingredient.of(Items.MANGROVE_PLANKS),
            BlockTags.INCORRECT_FOR_WOODEN_TOOL);

    /** Cherry — ornamental flowering wood.  Beautiful but delicate; highly enchantable. */
    public static final Tier WOOD_CHERRY = new ForgeTier(42, 2.4f, 0.0f, 20,
            BlockTags.INCORRECT_FOR_WOODEN_TOOL, () -> Ingredient.of(Items.CHERRY_PLANKS),
            BlockTags.INCORRECT_FOR_WOODEN_TOOL);

    /** Bamboo — lightweight grass stem.  Fastest swing but shatters quickly. */
    public static final Tier WOOD_BAMBOO = new ForgeTier(35, 2.5f, 0.0f, 16,
            BlockTags.INCORRECT_FOR_WOODEN_TOOL, () -> Ingredient.of(Items.BAMBOO_PLANKS),
            BlockTags.INCORRECT_FOR_WOODEN_TOOL);

    /** Crimson — tough Nether fungus stem.  The strongest wood; heavy and slow. */
    public static final Tier WOOD_CRIMSON = new ForgeTier(80, 1.8f, 1.5f, 10,
            BlockTags.INCORRECT_FOR_WOODEN_TOOL, () -> Ingredient.of(Items.CRIMSON_PLANKS),
            BlockTags.INCORRECT_FOR_WOODEN_TOOL);

    /** Warped — resilient Nether fungus stem.  Balanced with high enchantability. */
    public static final Tier WOOD_WARPED = new ForgeTier(72, 2.1f, 0.5f, 17,
            BlockTags.INCORRECT_FOR_WOODEN_TOOL, () -> Ingredient.of(Items.WARPED_PLANKS),
            BlockTags.INCORRECT_FOR_WOODEN_TOOL);

    // ── Flint / Flint-Iron tiers ─────────────────────────────────────────────────

    /**
     * Rough Flint — stone mining level, primitive but accessible.
     * Chipped shards from flint nodules; worse stats than RAMETHYST but found near surface.
     */
    public static final Tier RFLINT = new ForgeTier(200, 4.5f, 0.5f, 5,
            BlockTags.NEEDS_IRON_TOOL,
            () -> Ingredient.of(Items.FLINT),
            BlockTags.INCORRECT_FOR_STONE_TOOL);

    /**
     * Flint-Iron (FNI) — iron mining level, slightly below refined iron.
     * A hybrid of sharp flint edges and an iron frame; durable but not quite polished.
     */
    public static final Tier FNI_TOOLS = new ForgeTier(220, 5.5f, 2.0f, 9,
            BlockTags.NEEDS_DIAMOND_TOOL,
            () -> Ingredient.of(Items.FLINT),
            BlockTags.INCORRECT_FOR_IRON_TOOL);

    // ── Crystal / element polished tiers (tools + armor) ─────────────────────────

    /**
     * Calcified Amethyst — iron mining level.
     * Refined geode alloy; sits below HREDSTONE.
     */
    public static final Tier CAMETHYST = new ForgeTier(580, 6.5f, 2.5f, 14,
            BlockTags.NEEDS_DIAMOND_TOOL,
            () -> Ingredient.of(ModItems.CALCIFIED_AMETHYST.get()),
            BlockTags.INCORRECT_FOR_IRON_TOOL);

    /**
     * Glacial — stone mining level, worst of the stone crystal tier.
     * Ancient ice shards; brittle and temporary — melts over time.
     */
    public static final Tier ICE_TOOL = new ForgeTier(180, 4.0f, 1.0f, 6,
            BlockTags.NEEDS_IRON_TOOL,
            () -> Ingredient.of(ModItems.GLACIAL_SHARD.get()),
            BlockTags.INCORRECT_FOR_STONE_TOOL);

    /**
     * Polished Quartz — iron mining level, best durability of the polished set.
     * Polished nether quartz; durable and reliable.
     */
    public static final Tier PQUARTZ = new ForgeTier(640, 7.0f, 2.5f, 10,
            BlockTags.NEEDS_DIAMOND_TOOL,
            () -> Ingredient.of(ModItems.POLISHED_QUARTZ.get()),
            BlockTags.INCORRECT_FOR_IRON_TOOL);

    /**
     * Polished Prismarine — iron mining level.
     * Polished ocean crystal; balanced between camethyst and glacial.
     */
    public static final Tier PPRISM = new ForgeTier(560, 6.5f, 2.0f, 12,
            BlockTags.NEEDS_DIAMOND_TOOL,
            () -> Ingredient.of(ModItems.POLISHED_PRISMARINE.get()),
            BlockTags.INCORRECT_FOR_IRON_TOOL);

    // ── Leather tier ────────────────────────────────────────────────────────────

    /**
     * Leather — extremely weak, barely functional tools.
     * Floppy and dull; worse than wood in every way. Repaired with leather.
     */
    public static final Tier LEATHER = new ForgeTier(15, 1.5f, 0.0f, 12,
            BlockTags.INCORRECT_FOR_WOODEN_TOOL,
            () -> Ingredient.of(Items.LEATHER),
            BlockTags.INCORRECT_FOR_WOODEN_TOOL);

    // ── Cake tier ──────────────────────────────────────────────────────────────

    /**
     * Cake — below wood tier, practically useless.
     * Made from cake; fragile, slow, no attack bonus. Edible though!
     */
    public static final Tier CAKE = new ForgeTier(30, 1.5f, 0.0f, 1,
            BlockTags.INCORRECT_FOR_WOODEN_TOOL,
            () -> Ingredient.of(Items.CAKE),
            BlockTags.INCORRECT_FOR_WOODEN_TOOL);

    // ── Food tiers (edible novelty sets) ──────────────────────────────────────

    public static final Tier BREAD = new ForgeTier(25, 1.5f, 0.0f, 2,
            BlockTags.INCORRECT_FOR_WOODEN_TOOL,
            () -> Ingredient.of(Items.BREAD),
            BlockTags.INCORRECT_FOR_WOODEN_TOOL);

    public static final Tier DRIED_KELP = new ForgeTier(15, 1.0f, 0.5f, 1,
            BlockTags.INCORRECT_FOR_WOODEN_TOOL,
            () -> Ingredient.of(Items.DRIED_KELP),
            BlockTags.INCORRECT_FOR_WOODEN_TOOL);

    public static final Tier ROTTEN_FLESH = new ForgeTier(30, 0.5f, 0.0f, 3,
            BlockTags.INCORRECT_FOR_WOODEN_TOOL,
            () -> Ingredient.of(Items.ROTTEN_FLESH),
            BlockTags.INCORRECT_FOR_WOODEN_TOOL);

    public static final Tier MELON = new ForgeTier(50, 2.5f, 0.5f, 4,
            BlockTags.INCORRECT_FOR_WOODEN_TOOL,
            () -> Ingredient.of(Items.MELON_SLICE),
            BlockTags.INCORRECT_FOR_WOODEN_TOOL);

    public static final Tier SWEET_BERRIES = new ForgeTier(45, 2.0f, 1.0f, 5,
            BlockTags.INCORRECT_FOR_WOODEN_TOOL,
            () -> Ingredient.of(Items.SWEET_BERRIES),
            BlockTags.INCORRECT_FOR_WOODEN_TOOL);

    public static final Tier PUMPKIN_PIE = new ForgeTier(55, 2.0f, 0.5f, 7,
            BlockTags.INCORRECT_FOR_WOODEN_TOOL,
            () -> Ingredient.of(Items.PUMPKIN_PIE),
            BlockTags.INCORRECT_FOR_WOODEN_TOOL);

    public static final Tier MUSHROOM = new ForgeTier(100, 4.0f, 1.0f, 10,
            BlockTags.INCORRECT_FOR_STONE_TOOL,
            () -> Ingredient.of(Items.RED_MUSHROOM),
            BlockTags.INCORRECT_FOR_STONE_TOOL);

    public static final Tier PUFFERFISH = new ForgeTier(80, 3.5f, 1.5f, 8,
            BlockTags.INCORRECT_FOR_STONE_TOOL,
            () -> Ingredient.of(Items.PUFFERFISH),
            BlockTags.INCORRECT_FOR_STONE_TOOL);

    public static final Tier HONEY = new ForgeTier(120, 4.0f, 1.0f, 10,
            BlockTags.INCORRECT_FOR_STONE_TOOL,
            () -> Ingredient.of(Items.HONEY_BOTTLE),
            BlockTags.INCORRECT_FOR_STONE_TOOL);

    public static final Tier CHORUS_FRUIT = new ForgeTier(250, 6.0f, 2.0f, 15,
            BlockTags.INCORRECT_FOR_IRON_TOOL,
            () -> Ingredient.of(Items.CHORUS_FRUIT),
            BlockTags.INCORRECT_FOR_IRON_TOOL);

    public static final Tier GOLDEN_APPLE = new ForgeTier(300, 7.0f, 2.5f, 22,
            BlockTags.INCORRECT_FOR_IRON_TOOL,
            () -> Ingredient.of(Items.GOLDEN_APPLE),
            BlockTags.INCORRECT_FOR_IRON_TOOL);

    // ── Vanilla material tiers ────────────────────────────────────────────────────

    /** Paper — ultra-weak novelty. Softer than tissue paper. */
    public static final Tier PAPER = new ForgeTier(8, 1.0f, 0.0f, 8,
            BlockTags.INCORRECT_FOR_WOODEN_TOOL,
            () -> Ingredient.of(Items.PAPER),
            BlockTags.INCORRECT_FOR_WOODEN_TOOL);

    /** Feather — ultra-weak novelty. Light and enchantable but useless in combat. */
    public static final Tier FEATHER = new ForgeTier(10, 1.5f, 0.0f, 15,
            BlockTags.INCORRECT_FOR_WOODEN_TOOL,
            () -> Ingredient.of(Items.FEATHER),
            BlockTags.INCORRECT_FOR_WOODEN_TOOL);

    /** Glass — extremely fragile but sharp and fast. Shatters spectacularly. */
    public static final Tier GLASS = new ForgeTier(5, 5.0f, 1.0f, 1,
            BlockTags.INCORRECT_FOR_WOODEN_TOOL,
            () -> Ingredient.of(Items.GLASS_PANE),
            BlockTags.INCORRECT_FOR_WOODEN_TOOL);

    /** Cactus — prickly wood-tier. Deals thorn damage on hit. */
    public static final Tier CACTUS = new ForgeTier(70, 2.5f, 1.5f, 5,
            BlockTags.INCORRECT_FOR_WOODEN_TOOL,
            () -> Ingredient.of(Items.CACTUS),
            BlockTags.INCORRECT_FOR_WOODEN_TOOL);

    /** Sponge — absorbent but terrible as a weapon. Can soak up water. */
    public static final Tier SPONGE = new ForgeTier(40, 1.5f, 0.0f, 3,
            BlockTags.INCORRECT_FOR_WOODEN_TOOL,
            () -> Ingredient.of(Items.SPONGE),
            BlockTags.INCORRECT_FOR_WOODEN_TOOL);

    /** Bone — low-mid tier, effective against undead. */
    public static final Tier BONE = new ForgeTier(150, 3.5f, 1.0f, 6,
            BlockTags.NEEDS_IRON_TOOL,
            () -> Ingredient.of(Items.BONE),
            BlockTags.INCORRECT_FOR_STONE_TOOL);

    /** Clay — low-mid tier, malleable and enchantable. */
    public static final Tier CLAY = new ForgeTier(90, 2.5f, 0.5f, 8,
            BlockTags.NEEDS_IRON_TOOL,
            () -> Ingredient.of(Items.CLAY_BALL),
            BlockTags.INCORRECT_FOR_STONE_TOOL);

    /** Nether Wart — low-mid tier, inflicts wither on hit. */
    public static final Tier NETHER_WART = new ForgeTier(100, 3.0f, 0.5f, 10,
            BlockTags.NEEDS_IRON_TOOL,
            () -> Ingredient.of(Items.NETHER_WART),
            BlockTags.INCORRECT_FOR_STONE_TOOL);

    /** Brick — mid stone-tier, durable and reliable. */
    public static final Tier BRICK = new ForgeTier(200, 4.0f, 1.5f, 5,
            BlockTags.NEEDS_IRON_TOOL,
            () -> Ingredient.of(Items.BRICK),
            BlockTags.INCORRECT_FOR_STONE_TOOL);

    /** Nether Brick — mid stone-tier, inflicts fire on hit. */
    public static final Tier NETHER_BRICK = new ForgeTier(220, 4.0f, 1.5f, 5,
            BlockTags.NEEDS_IRON_TOOL,
            () -> Ingredient.of(Items.NETHER_BRICK),
            BlockTags.INCORRECT_FOR_STONE_TOOL);

    /** Pointed Dripstone — mid stone-tier, pierces armor. */
    public static final Tier POINTED_DRIPSTONE = new ForgeTier(160, 4.5f, 2.0f, 4,
            BlockTags.NEEDS_IRON_TOOL,
            () -> Ingredient.of(Items.POINTED_DRIPSTONE),
            BlockTags.INCORRECT_FOR_STONE_TOOL);

    /** Copper — mid stone-tier, oxidizes over time. */
    public static final Tier COPPER = new ForgeTier(200, 5.0f, 1.5f, 8,
            BlockTags.NEEDS_IRON_TOOL,
            () -> Ingredient.of(Items.COPPER_INGOT),
            BlockTags.INCORRECT_FOR_STONE_TOOL);

    /** Phantom Membrane — upper-mid tier, grants slow falling. */
    public static final Tier PHANTOM_MEMBRANE = new ForgeTier(250, 5.0f, 1.5f, 12,
            BlockTags.NEEDS_DIAMOND_TOOL,
            () -> Ingredient.of(Items.PHANTOM_MEMBRANE),
            BlockTags.INCORRECT_FOR_IRON_TOOL);

    /** Magma Cream — upper-mid tier, sets targets on fire. */
    public static final Tier MAGMA_CREAM = new ForgeTier(200, 4.5f, 2.0f, 8,
            BlockTags.NEEDS_DIAMOND_TOOL,
            () -> Ingredient.of(Items.MAGMA_CREAM),
            BlockTags.INCORRECT_FOR_IRON_TOOL);

    /** Slime — upper-mid tier, bouncy knockback. */
    public static final Tier SLIME = new ForgeTier(180, 3.5f, 0.5f, 10,
            BlockTags.NEEDS_DIAMOND_TOOL,
            () -> Ingredient.of(Items.SLIME_BALL),
            BlockTags.INCORRECT_FOR_IRON_TOOL);

    /** Blaze Rod — iron-level, auto-smelts and ignites. */
    public static final Tier BLAZE_ROD = new ForgeTier(300, 6.0f, 2.0f, 10,
            BlockTags.NEEDS_DIAMOND_TOOL,
            () -> Ingredient.of(Items.BLAZE_ROD),
            BlockTags.INCORRECT_FOR_IRON_TOOL);

    /** Nautilus Shell — iron-level, conduit affinity. */
    public static final Tier NAUTILUS_SHELL = new ForgeTier(280, 5.5f, 2.0f, 14,
            BlockTags.NEEDS_DIAMOND_TOOL,
            () -> Ingredient.of(Items.NAUTILUS_SHELL),
            BlockTags.INCORRECT_FOR_IRON_TOOL);

    /** Purpur — iron-level, ender resilience. */
    public static final Tier PURPUR = new ForgeTier(320, 6.0f, 2.0f, 12,
            BlockTags.NEEDS_DIAMOND_TOOL,
            () -> Ingredient.of(Items.POPPED_CHORUS_FRUIT),
            BlockTags.INCORRECT_FOR_IRON_TOOL);

    /** Ghast Tear — above-iron, regeneration. */
    public static final Tier GHAST_TEAR = new ForgeTier(400, 6.5f, 2.5f, 18,
            BlockTags.NEEDS_DIAMOND_TOOL,
            () -> Ingredient.of(Items.GHAST_TEAR),
            BlockTags.INCORRECT_FOR_IRON_TOOL);

    /** Eye of Ender — above-iron, ender sight. */
    public static final Tier EYE_OF_ENDER = new ForgeTier(450, 7.0f, 2.5f, 20,
            BlockTags.NEEDS_DIAMOND_TOOL,
            () -> Ingredient.of(Items.ENDER_EYE),
            BlockTags.INCORRECT_FOR_IRON_TOOL);

    /** Shulker Shell — above-iron, levitation shield. */
    public static final Tier SHULKER_SHELL = new ForgeTier(500, 6.0f, 2.0f, 16,
            BlockTags.NEEDS_DIAMOND_TOOL,
            () -> Ingredient.of(Items.SHULKER_SHELL),
            BlockTags.INCORRECT_FOR_IRON_TOOL);

    /** Echo Shard — diamond-adjacent, sculk resonance. */
    public static final Tier ECHO_SHARD = new ForgeTier(600, 7.5f, 3.0f, 18,
            BlockTags.NEEDS_DIAMOND_TOOL,
            () -> Ingredient.of(Items.ECHO_SHARD),
            BlockTags.INCORRECT_FOR_DIAMOND_TOOL);

    /** Dragon's Breath — diamond-adjacent endgame, draconic aura. */
    public static final Tier DRAGON_BREATH = new ForgeTier(700, 8.0f, 3.5f, 20,
            BlockTags.NEEDS_DIAMOND_TOOL,
            () -> Ingredient.of(Items.DRAGON_BREATH),
            BlockTags.INCORRECT_FOR_DIAMOND_TOOL);

    // ── Ectoplasm tiers ─────────────────────────────────────────────────────────

    /**
     * Rough Ectoplasm — stone mining level, raw spectral shards.
     * Crude weapons carved from raw ectoplasm; come pre-infused
     * and can damage ghosts. Lower stats than refined ectoplasm tools.
     */
    public static final Tier RECTO = new ForgeTier(150, 4.5f, 1.5f, 10,
            ModTags.Blocks.NEEDS_ECTO_TOOL,
            () -> Ingredient.of(ModItems.ECTOPLASM.get()),
            ModTags.Blocks.INCORRECT_ECTO_TOOL);

    /**
     * Ectoplasm — iron mining level, slightly above vanilla iron.
     * Spectral weapons forged from refined ectoplasm; come pre-infused
     * and can damage ghosts without using the Spectral Infuser.
     */
    public static final Tier ECTOPLASM = new ForgeTier(300, 6.5f, 2.5f, 16,
            ModTags.Blocks.NEEDS_ECTO_TOOL,
            () -> Ingredient.of(ModItems.REFINED_ECTOPLASM.get()),
            ModTags.Blocks.INCORRECT_ECTO_TOOL);
}
