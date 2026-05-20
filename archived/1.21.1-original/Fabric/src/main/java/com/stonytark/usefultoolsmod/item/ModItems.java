package com.stonytark.usefultoolsmod.item;

import com.stonytark.usefultoolsmod.UsefultoolsMod;
import com.stonytark.usefultoolsmod.entity.ModEntities;
import com.stonytark.usefultoolsmod.item.custom.*;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    // Raw materials
    public static Item RGOLD;
    public static Item RAW_RGOLD;
    public static Item OBSHARD;
    public static Item SEM;
    public static Item OBINGOT;
    public static Item HRED;
    public static Item HGLOW;
    public static Item RLAPIS;
    public static Item COAL_DUST;
    public static Item HARDENED_COAL;
    public static Item ECTOPLASM;
    public static Item REFINED_ECTOPLASM;
    public static Item CALCIFIED_AMETHYST;
    public static Item GLACIAL_SHARD;
    public static Item POLISHED_QUARTZ;
    public static Item POLISHED_PRISMARINE;

    // Throwables
    public static Item GRENADE;
    public static Item DYNAMITE;

    // Remerald (emerald rough)
    public static Item REMERALD_SWORD;
    public static Item REMERALD_PICKAXE;
    public static Item REMERALD_SHOVEL;
    public static Item REMERALD_AXE;
    public static Item REMERALD_HOE;

    // Pemerald (emerald polished)
    public static Item PEMERALD_SWORD;
    public static Item PEMERALD_PICKAXE;
    public static Item PEMERALD_SHOVEL;
    public static Item PEMERALD_AXE;
    public static Item PEMERALD_HOE;

    // Robsidian (obsidian rough)
    public static Item ROBSIDIAN_SWORD;
    public static Item ROBSIDIAN_PICKAXE;
    public static Item ROBSIDIAN_SHOVEL;
    public static Item ROBSIDIAN_AXE;
    public static Item ROBSIDIAN_HOE;

    // Pobsidian (obsidian polished)
    public static Item POBSIDIAN_SWORD;
    public static Item POBSIDIAN_PICKAXE;
    public static Item POBSIDIAN_SHOVEL;
    public static Item POBSIDIAN_AXE;
    public static Item POBSIDIAN_HOE;

    // Overpower
    public static Item OVERPOWER_SWORD;
    public static Item OVERPOWER_PICKAXE;
    public static Item OVERPOWER_SHOVEL;
    public static Item OVERPOWER_AXE;

    // Hredstone
    public static Item HREDSTONE_SWORD;
    public static Item HREDSTONE_PICKAXE;
    public static Item HREDSTONE_SHOVEL;
    public static Item HREDSTONE_AXE;
    public static Item HREDSTONE_HOE;

    // Hglowstone
    public static Item HGLOWSTONE_SWORD;
    public static Item HGLOWSTONE_PICKAXE;
    public static Item HGLOWSTONE_SHOVEL;
    public static Item HGLOWSTONE_AXE;
    public static Item HGLOWSTONE_HOE;

    // Rgold tools
    public static Item RGOLD_SWORD;
    public static Item RGOLD_PICKAXE;
    public static Item RGOLD_SHOVEL;
    public static Item RGOLD_AXE;
    public static Item RGOLD_HOE;

    // Rlapis tools
    public static Item RLAPIS_SWORD;
    public static Item RLAPIS_PICKAXE;
    public static Item RLAPIS_SHOVEL;
    public static Item RLAPIS_AXE;
    public static Item RLAPIS_HOE;

    // Armor
    public static Item EMERALD_HELMET;
    public static Item EMERALD_CHESTPLATE;
    public static Item EMERALD_LEGGINGS;
    public static Item EMERALD_BOOTS;

    public static Item HRED_HELMET;
    public static Item HRED_CHESTPLATE;
    public static Item HRED_LEGGINGS;
    public static Item HRED_BOOTS;

    public static Item HGLOW_HELMET;
    public static Item HGLOW_CHESTPLATE;
    public static Item HGLOW_LEGGINGS;
    public static Item HGLOW_BOOTS;

    public static Item OBSIDIAN_HELMET;
    public static Item OBSIDIAN_CHESTPLATE;
    public static Item OBSIDIAN_LEGGINGS;
    public static Item OBSIDIAN_BOOTS;

    public static Item RGOLD_HELMET;
    public static Item RGOLD_CHESTPLATE;
    public static Item RGOLD_LEGGINGS;
    public static Item RGOLD_BOOTS;

    public static Item RLAPIS_HELMET;
    public static Item RLAPIS_CHESTPLATE;
    public static Item RLAPIS_LEGGINGS;
    public static Item RLAPIS_BOOTS;

    public static Item OVERPOWER_HELMET;
    public static Item OVERPOWER_CHESTPLATE;
    public static Item OVERPOWER_LEGGINGS;
    public static Item OVERPOWER_BOOTS;

    // Coal
    public static Item COAL_SWORD;
    public static Item COAL_PICKAXE;
    public static Item COAL_SHOVEL;
    public static Item COAL_AXE;
    public static Item COAL_HOE;
    public static Item COAL_HELMET;
    public static Item COAL_CHESTPLATE;
    public static Item COAL_LEGGINGS;
    public static Item COAL_BOOTS;

    // Ectoplasm
    public static Item RECTO_SWORD;
    public static Item RECTO_PICKAXE;
    public static Item RECTO_SHOVEL;
    public static Item RECTO_AXE;
    public static Item RECTO_HOE;

    public static Item ECTO_SWORD;
    public static Item ECTO_PICKAXE;
    public static Item ECTO_SHOVEL;
    public static Item ECTO_AXE;
    public static Item ECTO_HOE;
    public static Item ECTO_HELMET;
    public static Item ECTO_CHESTPLATE;
    public static Item ECTO_LEGGINGS;
    public static Item ECTO_BOOTS;

    // Cake
    public static Item CAKE_SWORD;
    public static Item CAKE_PICKAXE;
    public static Item CAKE_SHOVEL;
    public static Item CAKE_AXE;
    public static Item CAKE_HOE;
    public static Item CAKE_HELMET;
    public static Item CAKE_CHESTPLATE;
    public static Item CAKE_LEGGINGS;
    public static Item CAKE_BOOTS;

    // Raw material tools
    public static Item RRAW_GOLD_SWORD;
    public static Item RRAW_GOLD_PICKAXE;
    public static Item RRAW_GOLD_SHOVEL;
    public static Item RRAW_GOLD_AXE;
    public static Item RRAW_GOLD_HOE;
    public static Item RRAW_COPPER_SWORD;
    public static Item RRAW_COPPER_PICKAXE;
    public static Item RRAW_COPPER_SHOVEL;
    public static Item RRAW_COPPER_AXE;
    public static Item RRAW_COPPER_HOE;
    public static Item RRAW_IRON_SWORD;
    public static Item RRAW_IRON_PICKAXE;
    public static Item RRAW_IRON_SHOVEL;
    public static Item RRAW_IRON_AXE;
    public static Item RRAW_IRON_HOE;
    public static Item RRAW_RGOLD_SWORD;
    public static Item RRAW_RGOLD_PICKAXE;
    public static Item RRAW_RGOLD_SHOVEL;
    public static Item RRAW_RGOLD_AXE;
    public static Item RRAW_RGOLD_HOE;
    public static Item RSCRAP_SWORD;
    public static Item RSCRAP_PICKAXE;
    public static Item RSCRAP_SHOVEL;
    public static Item RSCRAP_AXE;
    public static Item RSCRAP_HOE;

    // Crystal rough tools
    public static Item RAMETHYST_SWORD;
    public static Item RAMETHYST_PICKAXE;
    public static Item RAMETHYST_SHOVEL;
    public static Item RAMETHYST_AXE;
    public static Item RAMETHYST_HOE;
    public static Item SNOW_SWORD;
    public static Item SNOW_PICKAXE;
    public static Item SNOW_SHOVEL;
    public static Item SNOW_AXE;
    public static Item SNOW_HOE;
    public static Item RQUARTZ_SWORD;
    public static Item RQUARTZ_PICKAXE;
    public static Item RQUARTZ_SHOVEL;
    public static Item RQUARTZ_AXE;
    public static Item RQUARTZ_HOE;
    public static Item RPRISM_SWORD;
    public static Item RPRISM_PICKAXE;
    public static Item RPRISM_SHOVEL;
    public static Item RPRISM_AXE;
    public static Item RPRISM_HOE;

    // Polished crystal tools + armor
    public static Item CAMETHYST_SWORD;
    public static Item CAMETHYST_PICKAXE;
    public static Item CAMETHYST_SHOVEL;
    public static Item CAMETHYST_AXE;
    public static Item CAMETHYST_HOE;
    public static Item CAMETHYST_HELMET;
    public static Item CAMETHYST_CHESTPLATE;
    public static Item CAMETHYST_LEGGINGS;
    public static Item CAMETHYST_BOOTS;
    public static Item ICE_SWORD;
    public static Item ICE_PICKAXE;
    public static Item ICE_SHOVEL;
    public static Item ICE_AXE;
    public static Item ICE_HOE;
    public static Item ICE_HELMET;
    public static Item ICE_CHESTPLATE;
    public static Item ICE_LEGGINGS;
    public static Item ICE_BOOTS;
    public static Item PQUARTZ_SWORD;
    public static Item PQUARTZ_PICKAXE;
    public static Item PQUARTZ_SHOVEL;
    public static Item PQUARTZ_AXE;
    public static Item PQUARTZ_HOE;
    public static Item PQUARTZ_HELMET;
    public static Item PQUARTZ_CHESTPLATE;
    public static Item PQUARTZ_LEGGINGS;
    public static Item PQUARTZ_BOOTS;
    public static Item PPRISM_SWORD;
    public static Item PPRISM_PICKAXE;
    public static Item PPRISM_SHOVEL;
    public static Item PPRISM_AXE;
    public static Item PPRISM_HOE;
    public static Item PPRISM_HELMET;
    public static Item PPRISM_CHESTPLATE;
    public static Item PPRISM_LEGGINGS;
    public static Item PPRISM_BOOTS;

    // Flint tools
    public static Item RFLINT_SWORD;
    public static Item RFLINT_PICKAXE;
    public static Item RFLINT_SHOVEL;
    public static Item RFLINT_AXE;
    public static Item RFLINT_HOE;
    public static Item FNI_SWORD;
    public static Item FNI_PICKAXE;
    public static Item FNI_SHOVEL;
    public static Item FNI_AXE;
    public static Item FNI_HOE;
    public static Item FNI_HELMET;
    public static Item FNI_CHESTPLATE;
    public static Item FNI_LEGGINGS;
    public static Item FNI_BOOTS;

    // Stone variant tools
    public static Item ANDESITE_SWORD;
    public static Item ANDESITE_PICKAXE;
    public static Item ANDESITE_SHOVEL;
    public static Item ANDESITE_AXE;
    public static Item ANDESITE_HOE;
    public static Item BASALT_SWORD;
    public static Item BASALT_PICKAXE;
    public static Item BASALT_SHOVEL;
    public static Item BASALT_AXE;
    public static Item BASALT_HOE;
    public static Item BLACKSTONE_SWORD;
    public static Item BLACKSTONE_PICKAXE;
    public static Item BLACKSTONE_SHOVEL;
    public static Item BLACKSTONE_AXE;
    public static Item BLACKSTONE_HOE;
    public static Item CALCITE_SWORD;
    public static Item CALCITE_PICKAXE;
    public static Item CALCITE_SHOVEL;
    public static Item CALCITE_AXE;
    public static Item CALCITE_HOE;
    public static Item DEEPSLATE_SWORD;
    public static Item DEEPSLATE_PICKAXE;
    public static Item DEEPSLATE_SHOVEL;
    public static Item DEEPSLATE_AXE;
    public static Item DEEPSLATE_HOE;
    public static Item DIORITE_SWORD;
    public static Item DIORITE_PICKAXE;
    public static Item DIORITE_SHOVEL;
    public static Item DIORITE_AXE;
    public static Item DIORITE_HOE;
    public static Item END_STONE_SWORD;
    public static Item END_STONE_PICKAXE;
    public static Item END_STONE_SHOVEL;
    public static Item END_STONE_AXE;
    public static Item END_STONE_HOE;
    public static Item GRANITE_SWORD;
    public static Item GRANITE_PICKAXE;
    public static Item GRANITE_SHOVEL;
    public static Item GRANITE_AXE;
    public static Item GRANITE_HOE;
    public static Item NETHERRACK_SWORD;
    public static Item NETHERRACK_PICKAXE;
    public static Item NETHERRACK_SHOVEL;
    public static Item NETHERRACK_AXE;
    public static Item NETHERRACK_HOE;
    public static Item SANDSTONE_SWORD;
    public static Item SANDSTONE_PICKAXE;
    public static Item SANDSTONE_SHOVEL;
    public static Item SANDSTONE_AXE;
    public static Item SANDSTONE_HOE;
    public static Item SMOOTH_BASALT_SWORD;
    public static Item SMOOTH_BASALT_PICKAXE;
    public static Item SMOOTH_BASALT_SHOVEL;
    public static Item SMOOTH_BASALT_AXE;
    public static Item SMOOTH_BASALT_HOE;
    public static Item TERRACOTTA_SWORD;
    public static Item TERRACOTTA_PICKAXE;
    public static Item TERRACOTTA_SHOVEL;
    public static Item TERRACOTTA_AXE;
    public static Item TERRACOTTA_HOE;
    public static Item TUFF_SWORD;
    public static Item TUFF_PICKAXE;
    public static Item TUFF_SHOVEL;
    public static Item TUFF_AXE;
    public static Item TUFF_HOE;

    // Wood variant tools
    public static Item OAK_SWORD;
    public static Item OAK_PICKAXE;
    public static Item OAK_SHOVEL;
    public static Item OAK_AXE;
    public static Item OAK_HOE;
    public static Item SPRUCE_SWORD;
    public static Item SPRUCE_PICKAXE;
    public static Item SPRUCE_SHOVEL;
    public static Item SPRUCE_AXE;
    public static Item SPRUCE_HOE;
    public static Item BIRCH_SWORD;
    public static Item BIRCH_PICKAXE;
    public static Item BIRCH_SHOVEL;
    public static Item BIRCH_AXE;
    public static Item BIRCH_HOE;
    public static Item JUNGLE_SWORD;
    public static Item JUNGLE_PICKAXE;
    public static Item JUNGLE_SHOVEL;
    public static Item JUNGLE_AXE;
    public static Item JUNGLE_HOE;
    public static Item ACACIA_SWORD;
    public static Item ACACIA_PICKAXE;
    public static Item ACACIA_SHOVEL;
    public static Item ACACIA_AXE;
    public static Item ACACIA_HOE;
    public static Item DARK_OAK_SWORD;
    public static Item DARK_OAK_PICKAXE;
    public static Item DARK_OAK_SHOVEL;
    public static Item DARK_OAK_AXE;
    public static Item DARK_OAK_HOE;
    public static Item MANGROVE_SWORD;
    public static Item MANGROVE_PICKAXE;
    public static Item MANGROVE_SHOVEL;
    public static Item MANGROVE_AXE;
    public static Item MANGROVE_HOE;
    public static Item CHERRY_SWORD;
    public static Item CHERRY_PICKAXE;
    public static Item CHERRY_SHOVEL;
    public static Item CHERRY_AXE;
    public static Item CHERRY_HOE;
    public static Item BAMBOO_SWORD;
    public static Item BAMBOO_PICKAXE;
    public static Item BAMBOO_SHOVEL;
    public static Item BAMBOO_AXE;
    public static Item BAMBOO_HOE;
    public static Item CRIMSON_SWORD;
    public static Item CRIMSON_PICKAXE;
    public static Item CRIMSON_SHOVEL;
    public static Item CRIMSON_AXE;
    public static Item CRIMSON_HOE;
    public static Item WARPED_SWORD;
    public static Item WARPED_PICKAXE;
    public static Item WARPED_SHOVEL;
    public static Item WARPED_AXE;
    public static Item WARPED_HOE;

    // Leather tools
    public static Item LEATHER_SWORD;
    public static Item LEATHER_PICKAXE;
    public static Item LEATHER_SHOVEL;
    public static Item LEATHER_AXE;
    public static Item LEATHER_HOE;

    // Paper tools
    public static Item PAPER_SWORD;
    public static Item PAPER_PICKAXE;
    public static Item PAPER_SHOVEL;
    public static Item PAPER_AXE;
    public static Item PAPER_HOE;

    // Feather tools
    public static Item FEATHER_SWORD;
    public static Item FEATHER_PICKAXE;
    public static Item FEATHER_SHOVEL;
    public static Item FEATHER_AXE;
    public static Item FEATHER_HOE;

    // Glass tools
    public static Item GLASS_SWORD;
    public static Item GLASS_PICKAXE;
    public static Item GLASS_SHOVEL;
    public static Item GLASS_AXE;
    public static Item GLASS_HOE;

    // Rabbit hide armor
    public static Item RABBIT_HIDE_HELMET;
    public static Item RABBIT_HIDE_CHESTPLATE;
    public static Item RABBIT_HIDE_LEGGINGS;
    public static Item RABBIT_HIDE_BOOTS;

    // Cactus tools + armor
    public static Item CACTUS_SWORD;
    public static Item CACTUS_PICKAXE;
    public static Item CACTUS_SHOVEL;
    public static Item CACTUS_AXE;
    public static Item CACTUS_HOE;
    public static Item CACTUS_HELMET;
    public static Item CACTUS_CHESTPLATE;
    public static Item CACTUS_LEGGINGS;
    public static Item CACTUS_BOOTS;

    // Sponge tools
    public static Item SPONGE_SWORD;
    public static Item SPONGE_PICKAXE;
    public static Item SPONGE_SHOVEL;
    public static Item SPONGE_AXE;
    public static Item SPONGE_HOE;

    // Bone tools + armor
    public static Item BONE_SWORD;
    public static Item BONE_PICKAXE;
    public static Item BONE_SHOVEL;
    public static Item BONE_AXE;
    public static Item BONE_HOE;
    public static Item BONE_HELMET;
    public static Item BONE_CHESTPLATE;
    public static Item BONE_LEGGINGS;
    public static Item BONE_BOOTS;

    // Clay tools + armor
    public static Item CLAY_SWORD;
    public static Item CLAY_PICKAXE;
    public static Item CLAY_SHOVEL;
    public static Item CLAY_AXE;
    public static Item CLAY_HOE;
    public static Item CLAY_HELMET;
    public static Item CLAY_CHESTPLATE;
    public static Item CLAY_LEGGINGS;
    public static Item CLAY_BOOTS;

    // Nether wart tools
    public static Item NETHER_WART_SWORD;
    public static Item NETHER_WART_PICKAXE;
    public static Item NETHER_WART_SHOVEL;
    public static Item NETHER_WART_AXE;
    public static Item NETHER_WART_HOE;

    // Brick tools + armor
    public static Item BRICK_SWORD;
    public static Item BRICK_PICKAXE;
    public static Item BRICK_SHOVEL;
    public static Item BRICK_AXE;
    public static Item BRICK_HOE;
    public static Item BRICK_HELMET;
    public static Item BRICK_CHESTPLATE;
    public static Item BRICK_LEGGINGS;
    public static Item BRICK_BOOTS;

    // Nether brick tools + armor
    public static Item NETHER_BRICK_SWORD;
    public static Item NETHER_BRICK_PICKAXE;
    public static Item NETHER_BRICK_SHOVEL;
    public static Item NETHER_BRICK_AXE;
    public static Item NETHER_BRICK_HOE;
    public static Item NETHER_BRICK_HELMET;
    public static Item NETHER_BRICK_CHESTPLATE;
    public static Item NETHER_BRICK_LEGGINGS;
    public static Item NETHER_BRICK_BOOTS;

    // Pointed dripstone tools
    public static Item POINTED_DRIPSTONE_SWORD;
    public static Item POINTED_DRIPSTONE_PICKAXE;
    public static Item POINTED_DRIPSTONE_SHOVEL;
    public static Item POINTED_DRIPSTONE_AXE;
    public static Item POINTED_DRIPSTONE_HOE;

    // Copper tools + armor
    public static Item COPPER_SWORD;
    public static Item COPPER_PICKAXE;
    public static Item COPPER_SHOVEL;
    public static Item COPPER_AXE;
    public static Item COPPER_HOE;
    public static Item COPPER_HELMET;
    public static Item COPPER_CHESTPLATE;
    public static Item COPPER_LEGGINGS;
    public static Item COPPER_BOOTS;

    // Phantom tools + armor
    public static Item PHANTOM_SWORD;
    public static Item PHANTOM_PICKAXE;
    public static Item PHANTOM_SHOVEL;
    public static Item PHANTOM_AXE;
    public static Item PHANTOM_HOE;
    public static Item PHANTOM_HELMET;
    public static Item PHANTOM_CHESTPLATE;
    public static Item PHANTOM_LEGGINGS;
    public static Item PHANTOM_BOOTS;

    // Magma cream tools + armor
    public static Item MAGMA_CREAM_SWORD;
    public static Item MAGMA_CREAM_PICKAXE;
    public static Item MAGMA_CREAM_SHOVEL;
    public static Item MAGMA_CREAM_AXE;
    public static Item MAGMA_CREAM_HOE;
    public static Item MAGMA_CREAM_HELMET;
    public static Item MAGMA_CREAM_CHESTPLATE;
    public static Item MAGMA_CREAM_LEGGINGS;
    public static Item MAGMA_CREAM_BOOTS;

    // Slime tools + armor
    public static Item SLIME_SWORD;
    public static Item SLIME_PICKAXE;
    public static Item SLIME_SHOVEL;
    public static Item SLIME_AXE;
    public static Item SLIME_HOE;
    public static Item SLIME_HELMET;
    public static Item SLIME_CHESTPLATE;
    public static Item SLIME_LEGGINGS;
    public static Item SLIME_BOOTS;

    // Blaze tools + armor
    public static Item BLAZE_SWORD;
    public static Item BLAZE_PICKAXE;
    public static Item BLAZE_SHOVEL;
    public static Item BLAZE_AXE;
    public static Item BLAZE_HOE;
    public static Item BLAZE_HELMET;
    public static Item BLAZE_CHESTPLATE;
    public static Item BLAZE_LEGGINGS;
    public static Item BLAZE_BOOTS;

    // Nautilus tools + armor
    public static Item NAUTILUS_SWORD;
    public static Item NAUTILUS_PICKAXE;
    public static Item NAUTILUS_SHOVEL;
    public static Item NAUTILUS_AXE;
    public static Item NAUTILUS_HOE;
    public static Item NAUTILUS_HELMET;
    public static Item NAUTILUS_CHESTPLATE;
    public static Item NAUTILUS_LEGGINGS;
    public static Item NAUTILUS_BOOTS;

    // Purpur tools + armor
    public static Item PURPUR_SWORD;
    public static Item PURPUR_PICKAXE;
    public static Item PURPUR_SHOVEL;
    public static Item PURPUR_AXE;
    public static Item PURPUR_HOE;
    public static Item PURPUR_HELMET;
    public static Item PURPUR_CHESTPLATE;
    public static Item PURPUR_LEGGINGS;
    public static Item PURPUR_BOOTS;

    // Ghast tear tools + armor
    public static Item GHAST_TEAR_SWORD;
    public static Item GHAST_TEAR_PICKAXE;
    public static Item GHAST_TEAR_SHOVEL;
    public static Item GHAST_TEAR_AXE;
    public static Item GHAST_TEAR_HOE;
    public static Item GHAST_TEAR_HELMET;
    public static Item GHAST_TEAR_CHESTPLATE;
    public static Item GHAST_TEAR_LEGGINGS;
    public static Item GHAST_TEAR_BOOTS;

    // Eye of ender tools + armor
    public static Item EYE_OF_ENDER_SWORD;
    public static Item EYE_OF_ENDER_PICKAXE;
    public static Item EYE_OF_ENDER_SHOVEL;
    public static Item EYE_OF_ENDER_AXE;
    public static Item EYE_OF_ENDER_HOE;
    public static Item EYE_OF_ENDER_HELMET;
    public static Item EYE_OF_ENDER_CHESTPLATE;
    public static Item EYE_OF_ENDER_LEGGINGS;
    public static Item EYE_OF_ENDER_BOOTS;

    // Shulker tools + armor
    public static Item SHULKER_SWORD;
    public static Item SHULKER_PICKAXE;
    public static Item SHULKER_SHOVEL;
    public static Item SHULKER_AXE;
    public static Item SHULKER_HOE;
    public static Item SHULKER_HELMET;
    public static Item SHULKER_CHESTPLATE;
    public static Item SHULKER_LEGGINGS;
    public static Item SHULKER_BOOTS;

    // Turtle scute armor
    public static Item TURTLE_SCUTE_HELMET;
    public static Item TURTLE_SCUTE_CHESTPLATE;
    public static Item TURTLE_SCUTE_LEGGINGS;
    public static Item TURTLE_SCUTE_BOOTS;

    // Echo shard tools + armor
    public static Item ECHO_SHARD_SWORD;
    public static Item ECHO_SHARD_PICKAXE;
    public static Item ECHO_SHARD_SHOVEL;
    public static Item ECHO_SHARD_AXE;
    public static Item ECHO_SHARD_HOE;
    public static Item ECHO_SHARD_HELMET;
    public static Item ECHO_SHARD_CHESTPLATE;
    public static Item ECHO_SHARD_LEGGINGS;
    public static Item ECHO_SHARD_BOOTS;

    // Dragon breath tools + armor
    public static Item DRAGON_BREATH_SWORD;
    public static Item DRAGON_BREATH_PICKAXE;
    public static Item DRAGON_BREATH_SHOVEL;
    public static Item DRAGON_BREATH_AXE;
    public static Item DRAGON_BREATH_HOE;
    public static Item DRAGON_BREATH_HELMET;
    public static Item DRAGON_BREATH_CHESTPLATE;
    public static Item DRAGON_BREATH_LEGGINGS;
    public static Item DRAGON_BREATH_BOOTS;

    // Food tools + armor: Bread
    public static Item BREAD_SWORD;
    public static Item BREAD_PICKAXE;
    public static Item BREAD_SHOVEL;
    public static Item BREAD_AXE;
    public static Item BREAD_HOE;
    public static Item BREAD_HELMET;
    public static Item BREAD_CHESTPLATE;
    public static Item BREAD_LEGGINGS;
    public static Item BREAD_BOOTS;

    // Food tools + armor: Dried Kelp
    public static Item DRIED_KELP_SWORD;
    public static Item DRIED_KELP_PICKAXE;
    public static Item DRIED_KELP_SHOVEL;
    public static Item DRIED_KELP_AXE;
    public static Item DRIED_KELP_HOE;
    public static Item DRIED_KELP_HELMET;
    public static Item DRIED_KELP_CHESTPLATE;
    public static Item DRIED_KELP_LEGGINGS;
    public static Item DRIED_KELP_BOOTS;

    // Food tools + armor: Rotten Flesh
    public static Item ROTTEN_FLESH_SWORD;
    public static Item ROTTEN_FLESH_PICKAXE;
    public static Item ROTTEN_FLESH_SHOVEL;
    public static Item ROTTEN_FLESH_AXE;
    public static Item ROTTEN_FLESH_HOE;
    public static Item ROTTEN_FLESH_HELMET;
    public static Item ROTTEN_FLESH_CHESTPLATE;
    public static Item ROTTEN_FLESH_LEGGINGS;
    public static Item ROTTEN_FLESH_BOOTS;

    // Food tools + armor: Melon
    public static Item MELON_SWORD;
    public static Item MELON_PICKAXE;
    public static Item MELON_SHOVEL;
    public static Item MELON_AXE;
    public static Item MELON_HOE;
    public static Item MELON_HELMET;
    public static Item MELON_CHESTPLATE;
    public static Item MELON_LEGGINGS;
    public static Item MELON_BOOTS;

    // Food tools + armor: Sweet Berry
    public static Item SWEET_BERRY_SWORD;
    public static Item SWEET_BERRY_PICKAXE;
    public static Item SWEET_BERRY_SHOVEL;
    public static Item SWEET_BERRY_AXE;
    public static Item SWEET_BERRY_HOE;
    public static Item SWEET_BERRY_HELMET;
    public static Item SWEET_BERRY_CHESTPLATE;
    public static Item SWEET_BERRY_LEGGINGS;
    public static Item SWEET_BERRY_BOOTS;

    // Food tools + armor: Pumpkin Pie
    public static Item PUMPKIN_PIE_SWORD;
    public static Item PUMPKIN_PIE_PICKAXE;
    public static Item PUMPKIN_PIE_SHOVEL;
    public static Item PUMPKIN_PIE_AXE;
    public static Item PUMPKIN_PIE_HOE;
    public static Item PUMPKIN_PIE_HELMET;
    public static Item PUMPKIN_PIE_CHESTPLATE;
    public static Item PUMPKIN_PIE_LEGGINGS;
    public static Item PUMPKIN_PIE_BOOTS;

    // Food tools + armor: Mushroom
    public static Item MUSHROOM_SWORD;
    public static Item MUSHROOM_PICKAXE;
    public static Item MUSHROOM_SHOVEL;
    public static Item MUSHROOM_AXE;
    public static Item MUSHROOM_HOE;
    public static Item MUSHROOM_HELMET;
    public static Item MUSHROOM_CHESTPLATE;
    public static Item MUSHROOM_LEGGINGS;
    public static Item MUSHROOM_BOOTS;

    // Food tools + armor: Pufferfish
    public static Item PUFFERFISH_SWORD;
    public static Item PUFFERFISH_PICKAXE;
    public static Item PUFFERFISH_SHOVEL;
    public static Item PUFFERFISH_AXE;
    public static Item PUFFERFISH_HOE;
    public static Item PUFFERFISH_HELMET;
    public static Item PUFFERFISH_CHESTPLATE;
    public static Item PUFFERFISH_LEGGINGS;
    public static Item PUFFERFISH_BOOTS;

    // Food tools + armor: Honey
    public static Item HONEY_SWORD;
    public static Item HONEY_PICKAXE;
    public static Item HONEY_SHOVEL;
    public static Item HONEY_AXE;
    public static Item HONEY_HOE;
    public static Item HONEY_HELMET;
    public static Item HONEY_CHESTPLATE;
    public static Item HONEY_LEGGINGS;
    public static Item HONEY_BOOTS;

    // Food tools + armor: Chorus Fruit
    public static Item CHORUS_FRUIT_SWORD;
    public static Item CHORUS_FRUIT_PICKAXE;
    public static Item CHORUS_FRUIT_SHOVEL;
    public static Item CHORUS_FRUIT_AXE;
    public static Item CHORUS_FRUIT_HOE;
    public static Item CHORUS_FRUIT_HELMET;
    public static Item CHORUS_FRUIT_CHESTPLATE;
    public static Item CHORUS_FRUIT_LEGGINGS;
    public static Item CHORUS_FRUIT_BOOTS;

    // Food tools + armor: Golden Apple
    public static Item GOLDEN_APPLE_SWORD;
    public static Item GOLDEN_APPLE_PICKAXE;
    public static Item GOLDEN_APPLE_SHOVEL;
    public static Item GOLDEN_APPLE_AXE;
    public static Item GOLDEN_APPLE_HOE;
    public static Item GOLDEN_APPLE_HELMET;
    public static Item GOLDEN_APPLE_CHESTPLATE;
    public static Item GOLDEN_APPLE_LEGGINGS;
    public static Item GOLDEN_APPLE_BOOTS;

    // Spawn eggs
    public static Item GHOST_SPAWN_EGG;

    public static void register() {
        // Raw materials
        RGOLD = reg("rgold", new Item(new Item.Settings().maxCount(64)));
        RAW_RGOLD = reg("raw_rgold", new Item(new Item.Settings().maxCount(64)));
        OBSHARD = reg("obshard", new Item(new Item.Settings().maxCount(64)));
        SEM = reg("sem", new Item(new Item.Settings().maxCount(64)));
        OBINGOT = reg("obingot", new Item(new Item.Settings().maxCount(64)));
        HRED = reg("hred", new Item(new Item.Settings().maxCount(64)));
        HGLOW = reg("hglow", new Item(new Item.Settings().maxCount(64)));
        RLAPIS = reg("rlapis", new Item(new Item.Settings().maxCount(64)));
        COAL_DUST = reg("coal_dust", new Item(new Item.Settings().maxCount(64)));
        HARDENED_COAL = reg("hardened_coal", new Item(new Item.Settings().maxCount(64)));
        ECTOPLASM = reg("ectoplasm", new Item(new Item.Settings().maxCount(64)));
        REFINED_ECTOPLASM = reg("refined_ectoplasm", new Item(new Item.Settings().maxCount(64)));
        CALCIFIED_AMETHYST = reg("calcified_amethyst", new Item(new Item.Settings().maxCount(64)));
        GLACIAL_SHARD = reg("glacial_shard", new Item(new Item.Settings().maxCount(64)));
        POLISHED_QUARTZ = reg("polished_quartz", new Item(new Item.Settings().maxCount(64)));
        POLISHED_PRISMARINE = reg("polished_prismarine", new Item(new Item.Settings().maxCount(64)));

        // Throwables
        GRENADE = reg("grenade", new Grenade(new Item.Settings().maxCount(16)));
        DYNAMITE = reg("dynamite", new Dynamite(new Item.Settings().maxCount(16).fireproof()));

        // Remerald
        REMERALD_SWORD = reg("remerald_sword", new SwordItem(ModToolTiers.REMERALD,
                new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ModToolTiers.REMERALD, 3, -2.4f))));
        REMERALD_PICKAXE = reg("remerald_pickaxe", new PickaxeItem(ModToolTiers.REMERALD,
                new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolTiers.REMERALD, 1, -2.8f))));
        REMERALD_SHOVEL = reg("remerald_shovel", new ShovelItem(ModToolTiers.REMERALD,
                new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolTiers.REMERALD, 1.5f, -3f))));
        REMERALD_AXE = reg("remerald_axe", new AxeItem(ModToolTiers.REMERALD,
                new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolTiers.REMERALD, 6, -3.2f))));
        REMERALD_HOE = reg("remerald_hoe", new HoeItem(ModToolTiers.REMERALD,
                new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ModToolTiers.REMERALD, 0, -3f))));

        // Pemerald
        PEMERALD_SWORD = reg("pemerald_sword", new SwordItem(ModToolTiers.PEMERALD,
                new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ModToolTiers.PEMERALD, 3, -2.4f))));
        PEMERALD_PICKAXE = reg("pemerald_pickaxe", new PickaxeItem(ModToolTiers.PEMERALD,
                new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolTiers.PEMERALD, 1, -2.8f))));
        PEMERALD_SHOVEL = reg("pemerald_shovel", new ShovelItem(ModToolTiers.PEMERALD,
                new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolTiers.PEMERALD, 1.5f, -3f))));
        PEMERALD_AXE = reg("pemerald_axe", new AxeItem(ModToolTiers.PEMERALD,
                new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolTiers.PEMERALD, 6, -3.2f))));
        PEMERALD_HOE = reg("pemerald_hoe", new HoeItem(ModToolTiers.PEMERALD,
                new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ModToolTiers.PEMERALD, 0, -3f))));

        // Robsidian
        ROBSIDIAN_SWORD = reg("robsidian_sword", new SwordItem(ModToolTiers.ROBSIDIAN,
                new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ModToolTiers.ROBSIDIAN, 3, -2.4f))));
        ROBSIDIAN_PICKAXE = reg("robsidian_pickaxe", new PickaxeItem(ModToolTiers.ROBSIDIAN,
                new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolTiers.ROBSIDIAN, 1, -2.8f))));
        ROBSIDIAN_SHOVEL = reg("robsidian_shovel", new ShovelItem(ModToolTiers.ROBSIDIAN,
                new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolTiers.ROBSIDIAN, 1.5f, -3f))));
        ROBSIDIAN_AXE = reg("robsidian_axe", new AxeItem(ModToolTiers.ROBSIDIAN,
                new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolTiers.ROBSIDIAN, 6, -3.2f))));
        ROBSIDIAN_HOE = reg("robsidian_hoe", new HoeItem(ModToolTiers.ROBSIDIAN,
                new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ModToolTiers.ROBSIDIAN, 0, -3f))));

        // Pobsidian
        POBSIDIAN_SWORD = reg("pobsidian_sword", new SwordItem(ModToolTiers.POBSIDIAN,
                new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ModToolTiers.POBSIDIAN, 3, -2.4f))));
        POBSIDIAN_PICKAXE = reg("pobsidian_pickaxe", new PickaxeItem(ModToolTiers.POBSIDIAN,
                new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolTiers.POBSIDIAN, 1, -2.8f))));
        POBSIDIAN_SHOVEL = reg("pobsidian_shovel", new ShovelItem(ModToolTiers.POBSIDIAN,
                new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolTiers.POBSIDIAN, 1.5f, -3f))));
        POBSIDIAN_AXE = reg("pobsidian_axe", new AxeItem(ModToolTiers.POBSIDIAN,
                new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolTiers.POBSIDIAN, 6, -3.2f))));
        POBSIDIAN_HOE = reg("pobsidian_hoe", new HoeItem(ModToolTiers.POBSIDIAN,
                new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ModToolTiers.POBSIDIAN, 0, -3f))));

        // Overpower
        OVERPOWER_SWORD = reg("overpower_sword", new SwordItem(ModToolTiers.OVERPOWER,
                new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ModToolTiers.OVERPOWER, 3, -2.4f))));
        OVERPOWER_PICKAXE = reg("overpower_pickaxe", new PickaxeItem(ModToolTiers.OVERPOWER,
                new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolTiers.OVERPOWER, 1, -2.8f))));
        OVERPOWER_SHOVEL = reg("overpower_shovel", new ShovelItem(ModToolTiers.OVERPOWER,
                new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolTiers.OVERPOWER, 1.5f, -3f))));
        OVERPOWER_AXE = reg("overpower_axe", new AxeItem(ModToolTiers.OVERPOWER,
                new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolTiers.OVERPOWER, 6, -3.2f))));

        // Hredstone
        HREDSTONE_SWORD = reg("hredstone_sword", new SwordItem(ModToolTiers.HREDSTONE,
                new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ModToolTiers.HREDSTONE, 3, -2.4f))));
        HREDSTONE_PICKAXE = reg("hredstone_pickaxe", new PickaxeItem(ModToolTiers.HREDSTONE,
                new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolTiers.HREDSTONE, 1, -2.8f))));
        HREDSTONE_SHOVEL = reg("hredstone_shovel", new ShovelItem(ModToolTiers.HREDSTONE,
                new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolTiers.HREDSTONE, 1.5f, -3f))));
        HREDSTONE_AXE = reg("hredstone_axe", new AxeItem(ModToolTiers.HREDSTONE,
                new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolTiers.HREDSTONE, 6, -3.2f))));
        HREDSTONE_HOE = reg("hredstone_hoe", new HoeItem(ModToolTiers.HREDSTONE,
                new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ModToolTiers.HREDSTONE, 0, -3f))));

        // Hglowstone
        HGLOWSTONE_SWORD = reg("hglowstone_sword", new SwordItem(ModToolTiers.HGLOWSTONE,
                new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ModToolTiers.HGLOWSTONE, 3, -2.4f))));
        HGLOWSTONE_PICKAXE = reg("hglowstone_pickaxe", new PickaxeItem(ModToolTiers.HGLOWSTONE,
                new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolTiers.HGLOWSTONE, 1, -2.8f))));
        HGLOWSTONE_SHOVEL = reg("hglowstone_shovel", new ShovelItem(ModToolTiers.HGLOWSTONE,
                new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolTiers.HGLOWSTONE, 1.5f, -3f))));
        HGLOWSTONE_AXE = reg("hglowstone_axe", new AxeItem(ModToolTiers.HGLOWSTONE,
                new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolTiers.HGLOWSTONE, 6, -3.2f))));
        HGLOWSTONE_HOE = reg("hglowstone_hoe", new HoeItem(ModToolTiers.HGLOWSTONE,
                new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ModToolTiers.HGLOWSTONE, 0, -3f))));

        // Rgold
        RGOLD_SWORD = reg("rgold_sword", new SwordItem(ModToolTiers.RGOLD,
                new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ModToolTiers.RGOLD, 3, -2.4f))));
        RGOLD_PICKAXE = reg("rgold_pickaxe", new PickaxeItem(ModToolTiers.RGOLD,
                new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolTiers.RGOLD, 1, -2.8f))));
        RGOLD_SHOVEL = reg("rgold_shovel", new ShovelItem(ModToolTiers.RGOLD,
                new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolTiers.RGOLD, 1.5f, -3f))));
        RGOLD_AXE = reg("rgold_axe", new AxeItem(ModToolTiers.RGOLD,
                new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolTiers.RGOLD, 6, -3.2f))));
        RGOLD_HOE = reg("rgold_hoe", new HoeItem(ModToolTiers.RGOLD,
                new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ModToolTiers.RGOLD, 0, -3f))));

        // Rlapis
        RLAPIS_SWORD = reg("rlapis_sword", new SwordItem(ModToolTiers.RLAPIS,
                new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ModToolTiers.RLAPIS, 3, -2.4f))));
        RLAPIS_PICKAXE = reg("rlapis_pickaxe", new PickaxeItem(ModToolTiers.RLAPIS,
                new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolTiers.RLAPIS, 1, -2.8f))));
        RLAPIS_SHOVEL = reg("rlapis_shovel", new ShovelItem(ModToolTiers.RLAPIS,
                new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolTiers.RLAPIS, 1.5f, -3f))));
        RLAPIS_AXE = reg("rlapis_axe", new AxeItem(ModToolTiers.RLAPIS,
                new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolTiers.RLAPIS, 6, -3.2f))));
        RLAPIS_HOE = reg("rlapis_hoe", new HoeItem(ModToolTiers.RLAPIS,
                new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ModToolTiers.RLAPIS, 0, -3f))));

        // Armor
        EMERALD_HELMET = reg("emerald_helmet", new ArmorItem(ModArmorMaterials.EMERALD_ARMOR_MATERIAL,
                ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(33))));
        EMERALD_CHESTPLATE = reg("emerald_chestplate", new ArmorItem(ModArmorMaterials.EMERALD_ARMOR_MATERIAL,
                ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(33))));
        EMERALD_LEGGINGS = reg("emerald_leggings", new ArmorItem(ModArmorMaterials.EMERALD_ARMOR_MATERIAL,
                ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(33))));
        EMERALD_BOOTS = reg("emerald_boots", new ArmorItem(ModArmorMaterials.EMERALD_ARMOR_MATERIAL,
                ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(33))));

        HRED_HELMET = reg("hred_helmet", new ArmorItem(ModArmorMaterials.HRED_ARMOR_MATERIAL,
                ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(20))));
        HRED_CHESTPLATE = reg("hred_chestplate", new ArmorItem(ModArmorMaterials.HRED_ARMOR_MATERIAL,
                ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(20))));
        HRED_LEGGINGS = reg("hred_leggings", new ArmorItem(ModArmorMaterials.HRED_ARMOR_MATERIAL,
                ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(20))));
        HRED_BOOTS = reg("hred_boots", new ArmorItem(ModArmorMaterials.HRED_ARMOR_MATERIAL,
                ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(20))));

        HGLOW_HELMET = reg("hglow_helmet", new ArmorItem(ModArmorMaterials.HGLOW_ARMOR_MATERIAL,
                ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(18))));
        HGLOW_CHESTPLATE = reg("hglow_chestplate", new ArmorItem(ModArmorMaterials.HGLOW_ARMOR_MATERIAL,
                ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(18))));
        HGLOW_LEGGINGS = reg("hglow_leggings", new ArmorItem(ModArmorMaterials.HGLOW_ARMOR_MATERIAL,
                ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(18))));
        HGLOW_BOOTS = reg("hglow_boots", new ArmorItem(ModArmorMaterials.HGLOW_ARMOR_MATERIAL,
                ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(18))));

        OBSIDIAN_HELMET = reg("obsidian_helmet", new ArmorItem(ModArmorMaterials.OBSIDIAN_ARMOR_MATERIAL,
                ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(45))));
        OBSIDIAN_CHESTPLATE = reg("obsidian_chestplate", new ArmorItem(ModArmorMaterials.OBSIDIAN_ARMOR_MATERIAL,
                ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(45))));
        OBSIDIAN_LEGGINGS = reg("obsidian_leggings", new ArmorItem(ModArmorMaterials.OBSIDIAN_ARMOR_MATERIAL,
                ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(45))));
        OBSIDIAN_BOOTS = reg("obsidian_boots", new ArmorItem(ModArmorMaterials.OBSIDIAN_ARMOR_MATERIAL,
                ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(45))));

        RGOLD_HELMET = reg("rgold_helmet", new ArmorItem(ModArmorMaterials.RGOLD_ARMOR_MATERIAL,
                ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(18))));
        RGOLD_CHESTPLATE = reg("rgold_chestplate", new ArmorItem(ModArmorMaterials.RGOLD_ARMOR_MATERIAL,
                ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(18))));
        RGOLD_LEGGINGS = reg("rgold_leggings", new ArmorItem(ModArmorMaterials.RGOLD_ARMOR_MATERIAL,
                ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(18))));
        RGOLD_BOOTS = reg("rgold_boots", new ArmorItem(ModArmorMaterials.RGOLD_ARMOR_MATERIAL,
                ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(18))));

        RLAPIS_HELMET = reg("rlapis_helmet", new ArmorItem(ModArmorMaterials.RLAPIS_ARMOR_MATERIAL,
                ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(17))));
        RLAPIS_CHESTPLATE = reg("rlapis_chestplate", new ArmorItem(ModArmorMaterials.RLAPIS_ARMOR_MATERIAL,
                ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(17))));
        RLAPIS_LEGGINGS = reg("rlapis_leggings", new ArmorItem(ModArmorMaterials.RLAPIS_ARMOR_MATERIAL,
                ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(17))));
        RLAPIS_BOOTS = reg("rlapis_boots", new ArmorItem(ModArmorMaterials.RLAPIS_ARMOR_MATERIAL,
                ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(17))));

        OVERPOWER_HELMET = reg("overpower_helmet", new ModArmorItem(ModArmorMaterials.OVERPOWER_ARMOR_MATERIAL,
                ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(100))));
        OVERPOWER_CHESTPLATE = reg("overpower_chestplate", new ArmorItem(ModArmorMaterials.OVERPOWER_ARMOR_MATERIAL,
                ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(100))));
        OVERPOWER_LEGGINGS = reg("overpower_leggings", new ArmorItem(ModArmorMaterials.OVERPOWER_ARMOR_MATERIAL,
                ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(100))));
        OVERPOWER_BOOTS = reg("overpower_boots", new ArmorItem(ModArmorMaterials.OVERPOWER_ARMOR_MATERIAL,
                ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(100))));

        // Coal
        COAL_SWORD = reg("coal_sword", new CoalSwordItem(ModToolTiers.COAL_TOOL,
                new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ModToolTiers.COAL_TOOL, 3, -2.4f))));
        COAL_PICKAXE = reg("coal_pickaxe", new CoalPickaxeItem(ModToolTiers.COAL_TOOL,
                new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolTiers.COAL_TOOL, 1, -2.8f))));
        COAL_SHOVEL = reg("coal_shovel", new CoalShovelItem(ModToolTiers.COAL_TOOL,
                new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolTiers.COAL_TOOL, 1.5f, -3f))));
        COAL_AXE = reg("coal_axe", new CoalAxeItem(ModToolTiers.COAL_TOOL,
                new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolTiers.COAL_TOOL, 6, -3.2f))));
        COAL_HOE = reg("coal_hoe", new CoalHoeItem(ModToolTiers.COAL_TOOL,
                new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ModToolTiers.COAL_TOOL, 0, -3f))));

        COAL_HELMET = reg("coal_helmet", new CoalArmorItem(ModArmorMaterials.COAL_ARMOR_MATERIAL,
                ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(5))));
        COAL_CHESTPLATE = reg("coal_chestplate", new CoalArmorItem(ModArmorMaterials.COAL_ARMOR_MATERIAL,
                ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(8))));
        COAL_LEGGINGS = reg("coal_leggings", new CoalArmorItem(ModArmorMaterials.COAL_ARMOR_MATERIAL,
                ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(7))));
        COAL_BOOTS = reg("coal_boots", new CoalArmorItem(ModArmorMaterials.COAL_ARMOR_MATERIAL,
                ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(5))));

        // Ectoplasm
        RECTO_SWORD = reg("recto_sword", new EctoSwordItem(ModToolTiers.RECTO,
                new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ModToolTiers.RECTO, 3, -2.4f))));
        RECTO_PICKAXE = reg("recto_pickaxe", new EctoPickaxeItem(ModToolTiers.RECTO,
                new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolTiers.RECTO, 1, -2.8f))));
        RECTO_SHOVEL = reg("recto_shovel", new EctoShovelItem(ModToolTiers.RECTO,
                new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolTiers.RECTO, 1.5f, -3f))));
        RECTO_AXE = reg("recto_axe", new EctoAxeItem(ModToolTiers.RECTO,
                new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolTiers.RECTO, 6, -3.2f))));
        RECTO_HOE = reg("recto_hoe", new EctoHoeItem(ModToolTiers.RECTO,
                new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ModToolTiers.RECTO, 0, -3f))));

        ECTO_SWORD = reg("ecto_sword", new EctoSwordItem(ModToolTiers.ECTO,
                new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ModToolTiers.ECTO, 3, -2.4f))));
        ECTO_PICKAXE = reg("ecto_pickaxe", new EctoPickaxeItem(ModToolTiers.ECTO,
                new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolTiers.ECTO, 1, -2.8f))));
        ECTO_SHOVEL = reg("ecto_shovel", new EctoShovelItem(ModToolTiers.ECTO,
                new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolTiers.ECTO, 1.5f, -3f))));
        ECTO_AXE = reg("ecto_axe", new EctoAxeItem(ModToolTiers.ECTO,
                new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolTiers.ECTO, 6, -3.2f))));
        ECTO_HOE = reg("ecto_hoe", new EctoHoeItem(ModToolTiers.ECTO,
                new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ModToolTiers.ECTO, 0, -3f))));

        ECTO_HELMET = reg("ecto_helmet", new ArmorItem(ModArmorMaterials.ECTO_ARMOR_MATERIAL,
                ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(33))));
        ECTO_CHESTPLATE = reg("ecto_chestplate", new ArmorItem(ModArmorMaterials.ECTO_ARMOR_MATERIAL,
                ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(48))));
        ECTO_LEGGINGS = reg("ecto_leggings", new ArmorItem(ModArmorMaterials.ECTO_ARMOR_MATERIAL,
                ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(45))));
        ECTO_BOOTS = reg("ecto_boots", new ArmorItem(ModArmorMaterials.ECTO_ARMOR_MATERIAL,
                ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(33))));

        // Cake
        CAKE_SWORD = reg("cake_sword", new CakeSwordItem(ModToolTiers.CAKE_TOOL,
                new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ModToolTiers.CAKE_TOOL, 3, -2.4f))));
        CAKE_PICKAXE = reg("cake_pickaxe", new CakePickaxeItem(ModToolTiers.CAKE_TOOL,
                new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolTiers.CAKE_TOOL, 1, -2.8f))));
        CAKE_SHOVEL = reg("cake_shovel", new CakeShovelItem(ModToolTiers.CAKE_TOOL,
                new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolTiers.CAKE_TOOL, 1.5f, -3f))));
        CAKE_AXE = reg("cake_axe", new CakeAxeItem(ModToolTiers.CAKE_TOOL,
                new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolTiers.CAKE_TOOL, 6, -3.2f))));
        CAKE_HOE = reg("cake_hoe", new CakeHoeItem(ModToolTiers.CAKE_TOOL,
                new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ModToolTiers.CAKE_TOOL, 0, -3f))));

        CAKE_HELMET = reg("cake_helmet", new CakeArmorItem(ModArmorMaterials.CAKE_ARMOR_MATERIAL,
                ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(5))));
        CAKE_CHESTPLATE = reg("cake_chestplate", new CakeArmorItem(ModArmorMaterials.CAKE_ARMOR_MATERIAL,
                ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(8))));
        CAKE_LEGGINGS = reg("cake_leggings", new CakeArmorItem(ModArmorMaterials.CAKE_ARMOR_MATERIAL,
                ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(7))));
        CAKE_BOOTS = reg("cake_boots", new CakeArmorItem(ModArmorMaterials.CAKE_ARMOR_MATERIAL,
                ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(5))));

        // Raw material tools
        regStdToolSet("rraw_gold", ModToolTiers.RRAW_GOLD, (t,s)->RRAW_GOLD_SWORD=s, (t,s)->RRAW_GOLD_PICKAXE=s, (t,s)->RRAW_GOLD_SHOVEL=s, (t,s)->RRAW_GOLD_AXE=s, (t,s)->RRAW_GOLD_HOE=s);
        regStdToolSet("rraw_copper", ModToolTiers.RRAW_COPPER, (t,s)->RRAW_COPPER_SWORD=s, (t,s)->RRAW_COPPER_PICKAXE=s, (t,s)->RRAW_COPPER_SHOVEL=s, (t,s)->RRAW_COPPER_AXE=s, (t,s)->RRAW_COPPER_HOE=s);
        regStdToolSet("rraw_iron", ModToolTiers.RRAW_IRON, (t,s)->RRAW_IRON_SWORD=s, (t,s)->RRAW_IRON_PICKAXE=s, (t,s)->RRAW_IRON_SHOVEL=s, (t,s)->RRAW_IRON_AXE=s, (t,s)->RRAW_IRON_HOE=s);
        regStdToolSet("rraw_rgold", ModToolTiers.RRAW_RGOLD, (t,s)->RRAW_RGOLD_SWORD=s, (t,s)->RRAW_RGOLD_PICKAXE=s, (t,s)->RRAW_RGOLD_SHOVEL=s, (t,s)->RRAW_RGOLD_AXE=s, (t,s)->RRAW_RGOLD_HOE=s);
        regStdToolSet("rscrap", ModToolTiers.RSCRAP, (t,s)->RSCRAP_SWORD=s, (t,s)->RSCRAP_PICKAXE=s, (t,s)->RSCRAP_SHOVEL=s, (t,s)->RSCRAP_AXE=s, (t,s)->RSCRAP_HOE=s);

        // Crystal rough tools
        regStdToolSet("ramethyst", ModToolTiers.RAMETHYST, (t,s)->RAMETHYST_SWORD=s, (t,s)->RAMETHYST_PICKAXE=s, (t,s)->RAMETHYST_SHOVEL=s, (t,s)->RAMETHYST_AXE=s, (t,s)->RAMETHYST_HOE=s);
        regStdToolSet("snow", ModToolTiers.SNOW_TOOL, (t,s)->SNOW_SWORD=s, (t,s)->SNOW_PICKAXE=s, (t,s)->SNOW_SHOVEL=s, (t,s)->SNOW_AXE=s, (t,s)->SNOW_HOE=s);
        regStdToolSet("rquartz", ModToolTiers.RQUARTZ, (t,s)->RQUARTZ_SWORD=s, (t,s)->RQUARTZ_PICKAXE=s, (t,s)->RQUARTZ_SHOVEL=s, (t,s)->RQUARTZ_AXE=s, (t,s)->RQUARTZ_HOE=s);
        regStdToolSet("rprism", ModToolTiers.RPRISM, (t,s)->RPRISM_SWORD=s, (t,s)->RPRISM_PICKAXE=s, (t,s)->RPRISM_SHOVEL=s, (t,s)->RPRISM_AXE=s, (t,s)->RPRISM_HOE=s);

        // Polished crystal tools + armor
        CAMETHYST_SWORD = reg("camethyst_sword", new SwordItem(ModToolTiers.CAMETHYST,
                new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ModToolTiers.CAMETHYST, 3, -2.4f))));
        CAMETHYST_PICKAXE = reg("camethyst_pickaxe", new PickaxeItem(ModToolTiers.CAMETHYST,
                new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolTiers.CAMETHYST, 1, -2.8f))));
        CAMETHYST_SHOVEL = reg("camethyst_shovel", new ShovelItem(ModToolTiers.CAMETHYST,
                new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolTiers.CAMETHYST, 1.5f, -3f))));
        CAMETHYST_AXE = reg("camethyst_axe", new AxeItem(ModToolTiers.CAMETHYST,
                new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolTiers.CAMETHYST, 6, -3.2f))));
        CAMETHYST_HOE = reg("camethyst_hoe", new HoeItem(ModToolTiers.CAMETHYST,
                new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ModToolTiers.CAMETHYST, 0, -3f))));
        CAMETHYST_HELMET = reg("camethyst_helmet", new ArmorItem(ModArmorMaterials.CAMETHYST_ARMOR_MATERIAL,
                ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(14))));
        CAMETHYST_CHESTPLATE = reg("camethyst_chestplate", new ArmorItem(ModArmorMaterials.CAMETHYST_ARMOR_MATERIAL,
                ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(14))));
        CAMETHYST_LEGGINGS = reg("camethyst_leggings", new ArmorItem(ModArmorMaterials.CAMETHYST_ARMOR_MATERIAL,
                ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(14))));
        CAMETHYST_BOOTS = reg("camethyst_boots", new ArmorItem(ModArmorMaterials.CAMETHYST_ARMOR_MATERIAL,
                ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(14))));

        ICE_SWORD = reg("ice_sword", new SwordItem(ModToolTiers.ICE_TOOL,
                new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ModToolTiers.ICE_TOOL, 3, -2.4f))));
        ICE_PICKAXE = reg("ice_pickaxe", new PickaxeItem(ModToolTiers.ICE_TOOL,
                new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolTiers.ICE_TOOL, 1, -2.8f))));
        ICE_SHOVEL = reg("ice_shovel", new ShovelItem(ModToolTiers.ICE_TOOL,
                new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolTiers.ICE_TOOL, 1.5f, -3f))));
        ICE_AXE = reg("ice_axe", new AxeItem(ModToolTiers.ICE_TOOL,
                new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolTiers.ICE_TOOL, 6, -3.2f))));
        ICE_HOE = reg("ice_hoe", new HoeItem(ModToolTiers.ICE_TOOL,
                new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ModToolTiers.ICE_TOOL, 0, -3f))));
        ICE_HELMET = reg("ice_helmet", new ArmorItem(ModArmorMaterials.ICE_ARMOR_MATERIAL,
                ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(8))));
        ICE_CHESTPLATE = reg("ice_chestplate", new ArmorItem(ModArmorMaterials.ICE_ARMOR_MATERIAL,
                ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(8))));
        ICE_LEGGINGS = reg("ice_leggings", new ArmorItem(ModArmorMaterials.ICE_ARMOR_MATERIAL,
                ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(8))));
        ICE_BOOTS = reg("ice_boots", new ArmorItem(ModArmorMaterials.ICE_ARMOR_MATERIAL,
                ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(8))));

        PQUARTZ_SWORD = reg("pquartz_sword", new SwordItem(ModToolTiers.PQUARTZ,
                new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ModToolTiers.PQUARTZ, 3, -2.4f))));
        PQUARTZ_PICKAXE = reg("pquartz_pickaxe", new PickaxeItem(ModToolTiers.PQUARTZ,
                new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolTiers.PQUARTZ, 1, -2.8f))));
        PQUARTZ_SHOVEL = reg("pquartz_shovel", new ShovelItem(ModToolTiers.PQUARTZ,
                new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolTiers.PQUARTZ, 1.5f, -3f))));
        PQUARTZ_AXE = reg("pquartz_axe", new AxeItem(ModToolTiers.PQUARTZ,
                new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolTiers.PQUARTZ, 6, -3.2f))));
        PQUARTZ_HOE = reg("pquartz_hoe", new HoeItem(ModToolTiers.PQUARTZ,
                new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ModToolTiers.PQUARTZ, 0, -3f))));
        PQUARTZ_HELMET = reg("pquartz_helmet", new ArmorItem(ModArmorMaterials.PQUARTZ_ARMOR_MATERIAL,
                ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(15))));
        PQUARTZ_CHESTPLATE = reg("pquartz_chestplate", new ArmorItem(ModArmorMaterials.PQUARTZ_ARMOR_MATERIAL,
                ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(15))));
        PQUARTZ_LEGGINGS = reg("pquartz_leggings", new ArmorItem(ModArmorMaterials.PQUARTZ_ARMOR_MATERIAL,
                ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(15))));
        PQUARTZ_BOOTS = reg("pquartz_boots", new ArmorItem(ModArmorMaterials.PQUARTZ_ARMOR_MATERIAL,
                ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(15))));

        PPRISM_SWORD = reg("pprism_sword", new SwordItem(ModToolTiers.PPRISM,
                new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ModToolTiers.PPRISM, 3, -2.4f))));
        PPRISM_PICKAXE = reg("pprism_pickaxe", new PickaxeItem(ModToolTiers.PPRISM,
                new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolTiers.PPRISM, 1, -2.8f))));
        PPRISM_SHOVEL = reg("pprism_shovel", new ShovelItem(ModToolTiers.PPRISM,
                new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolTiers.PPRISM, 1.5f, -3f))));
        PPRISM_AXE = reg("pprism_axe", new AxeItem(ModToolTiers.PPRISM,
                new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolTiers.PPRISM, 6, -3.2f))));
        PPRISM_HOE = reg("pprism_hoe", new HoeItem(ModToolTiers.PPRISM,
                new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ModToolTiers.PPRISM, 0, -3f))));
        PPRISM_HELMET = reg("pprism_helmet", new ArmorItem(ModArmorMaterials.PPRISM_ARMOR_MATERIAL,
                ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(14))));
        PPRISM_CHESTPLATE = reg("pprism_chestplate", new ArmorItem(ModArmorMaterials.PPRISM_ARMOR_MATERIAL,
                ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(14))));
        PPRISM_LEGGINGS = reg("pprism_leggings", new ArmorItem(ModArmorMaterials.PPRISM_ARMOR_MATERIAL,
                ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(14))));
        PPRISM_BOOTS = reg("pprism_boots", new ArmorItem(ModArmorMaterials.PPRISM_ARMOR_MATERIAL,
                ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(14))));

        // Flint tools
        RFLINT_SWORD = reg("rflint_sword", new SwordItem(ModToolTiers.RFLINT,
                new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ModToolTiers.RFLINT, 3, -2.4f))));
        RFLINT_PICKAXE = reg("rflint_pickaxe", new PickaxeItem(ModToolTiers.RFLINT,
                new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolTiers.RFLINT, 1, -2.8f))));
        RFLINT_SHOVEL = reg("rflint_shovel", new ShovelItem(ModToolTiers.RFLINT,
                new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolTiers.RFLINT, 1.5f, -3f))));
        RFLINT_AXE = reg("rflint_axe", new AxeItem(ModToolTiers.RFLINT,
                new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolTiers.RFLINT, 6, -3.2f))));
        RFLINT_HOE = reg("rflint_hoe", new HoeItem(ModToolTiers.RFLINT,
                new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ModToolTiers.RFLINT, 0, -3f))));

        FNI_SWORD = reg("fni_sword", new SwordItem(ModToolTiers.FNI,
                new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ModToolTiers.FNI, 3, -2.4f))));
        FNI_PICKAXE = reg("fni_pickaxe", new PickaxeItem(ModToolTiers.FNI,
                new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolTiers.FNI, 1, -2.8f))));
        FNI_SHOVEL = reg("fni_shovel", new ShovelItem(ModToolTiers.FNI,
                new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolTiers.FNI, 1.5f, -3f))));
        FNI_AXE = reg("fni_axe", new AxeItem(ModToolTiers.FNI,
                new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolTiers.FNI, 6, -3.2f))));
        FNI_HOE = reg("fni_hoe", new HoeItem(ModToolTiers.FNI,
                new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ModToolTiers.FNI, 0, -3f))));
        FNI_HELMET = reg("fni_helmet", new ArmorItem(ModArmorMaterials.FNI_ARMOR_MATERIAL,
                ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(9))));
        FNI_CHESTPLATE = reg("fni_chestplate", new ArmorItem(ModArmorMaterials.FNI_ARMOR_MATERIAL,
                ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(9))));
        FNI_LEGGINGS = reg("fni_leggings", new ArmorItem(ModArmorMaterials.FNI_ARMOR_MATERIAL,
                ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(9))));
        FNI_BOOTS = reg("fni_boots", new ArmorItem(ModArmorMaterials.FNI_ARMOR_MATERIAL,
                ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(9))));

        // Stone variant tools (using custom attack modifiers from 1.20 reference)
        ANDESITE_SWORD = reg("andesite_sword", new SwordItem(ModToolTiers.STONE_ANDESITE, new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ModToolTiers.STONE_ANDESITE, 3, -2.4f))));
        ANDESITE_PICKAXE = reg("andesite_pickaxe", new PickaxeItem(ModToolTiers.STONE_ANDESITE, new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolTiers.STONE_ANDESITE, 1, -2.8f))));
        ANDESITE_SHOVEL = reg("andesite_shovel", new ShovelItem(ModToolTiers.STONE_ANDESITE, new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolTiers.STONE_ANDESITE, 1.5f, -3f))));
        ANDESITE_AXE = reg("andesite_axe", new AxeItem(ModToolTiers.STONE_ANDESITE, new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolTiers.STONE_ANDESITE, 6, -3.2f))));
        ANDESITE_HOE = reg("andesite_hoe", new HoeItem(ModToolTiers.STONE_ANDESITE, new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ModToolTiers.STONE_ANDESITE, 0, -3f))));

        BASALT_SWORD = reg("basalt_sword", new SwordItem(ModToolTiers.STONE_BASALT, new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ModToolTiers.STONE_BASALT, 3, -2.5f))));
        BASALT_PICKAXE = reg("basalt_pickaxe", new PickaxeItem(ModToolTiers.STONE_BASALT, new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolTiers.STONE_BASALT, 1, -2.9f))));
        BASALT_SHOVEL = reg("basalt_shovel", new ShovelItem(ModToolTiers.STONE_BASALT, new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolTiers.STONE_BASALT, 2.0f, -3.1f))));
        BASALT_AXE = reg("basalt_axe", new AxeItem(ModToolTiers.STONE_BASALT, new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolTiers.STONE_BASALT, 7, -3.3f))));
        BASALT_HOE = reg("basalt_hoe", new HoeItem(ModToolTiers.STONE_BASALT, new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ModToolTiers.STONE_BASALT, 0, -3.1f))));

        BLACKSTONE_SWORD = reg("blackstone_sword", new SwordItem(ModToolTiers.STONE_BLACKSTONE, new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ModToolTiers.STONE_BLACKSTONE, 4, -2.5f))));
        BLACKSTONE_PICKAXE = reg("blackstone_pickaxe", new PickaxeItem(ModToolTiers.STONE_BLACKSTONE, new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolTiers.STONE_BLACKSTONE, 1, -2.9f))));
        BLACKSTONE_SHOVEL = reg("blackstone_shovel", new ShovelItem(ModToolTiers.STONE_BLACKSTONE, new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolTiers.STONE_BLACKSTONE, 2.0f, -3.1f))));
        BLACKSTONE_AXE = reg("blackstone_axe", new AxeItem(ModToolTiers.STONE_BLACKSTONE, new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolTiers.STONE_BLACKSTONE, 7, -3.35f))));
        BLACKSTONE_HOE = reg("blackstone_hoe", new HoeItem(ModToolTiers.STONE_BLACKSTONE, new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ModToolTiers.STONE_BLACKSTONE, 0, -3.1f))));

        CALCITE_SWORD = reg("calcite_sword", new SwordItem(ModToolTiers.STONE_CALCITE, new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ModToolTiers.STONE_CALCITE, 2, -2.2f))));
        CALCITE_PICKAXE = reg("calcite_pickaxe", new PickaxeItem(ModToolTiers.STONE_CALCITE, new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolTiers.STONE_CALCITE, 1, -2.6f))));
        CALCITE_SHOVEL = reg("calcite_shovel", new ShovelItem(ModToolTiers.STONE_CALCITE, new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolTiers.STONE_CALCITE, 1.0f, -2.8f))));
        CALCITE_AXE = reg("calcite_axe", new AxeItem(ModToolTiers.STONE_CALCITE, new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolTiers.STONE_CALCITE, 5, -3.0f))));
        CALCITE_HOE = reg("calcite_hoe", new HoeItem(ModToolTiers.STONE_CALCITE, new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ModToolTiers.STONE_CALCITE, 0, -2.6f))));

        DEEPSLATE_SWORD = reg("deepslate_sword", new SwordItem(ModToolTiers.STONE_DEEPSLATE, new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ModToolTiers.STONE_DEEPSLATE, 4, -2.55f))));
        DEEPSLATE_PICKAXE = reg("deepslate_pickaxe", new PickaxeItem(ModToolTiers.STONE_DEEPSLATE, new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolTiers.STONE_DEEPSLATE, 1, -2.95f))));
        DEEPSLATE_SHOVEL = reg("deepslate_shovel", new ShovelItem(ModToolTiers.STONE_DEEPSLATE, new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolTiers.STONE_DEEPSLATE, 2.0f, -3.15f))));
        DEEPSLATE_AXE = reg("deepslate_axe", new AxeItem(ModToolTiers.STONE_DEEPSLATE, new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolTiers.STONE_DEEPSLATE, 7, -3.4f))));
        DEEPSLATE_HOE = reg("deepslate_hoe", new HoeItem(ModToolTiers.STONE_DEEPSLATE, new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ModToolTiers.STONE_DEEPSLATE, 0, -3.1f))));

        DIORITE_SWORD = reg("diorite_sword", new SwordItem(ModToolTiers.STONE_DIORITE, new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ModToolTiers.STONE_DIORITE, 3, -2.4f))));
        DIORITE_PICKAXE = reg("diorite_pickaxe", new PickaxeItem(ModToolTiers.STONE_DIORITE, new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolTiers.STONE_DIORITE, 1, -2.8f))));
        DIORITE_SHOVEL = reg("diorite_shovel", new ShovelItem(ModToolTiers.STONE_DIORITE, new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolTiers.STONE_DIORITE, 1.5f, -3f))));
        DIORITE_AXE = reg("diorite_axe", new AxeItem(ModToolTiers.STONE_DIORITE, new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolTiers.STONE_DIORITE, 6, -3.2f))));
        DIORITE_HOE = reg("diorite_hoe", new HoeItem(ModToolTiers.STONE_DIORITE, new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ModToolTiers.STONE_DIORITE, 0, -2.9f))));

        END_STONE_SWORD = reg("end_stone_sword", new SwordItem(ModToolTiers.STONE_END_STONE, new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ModToolTiers.STONE_END_STONE, 3, -2.35f))));
        END_STONE_PICKAXE = reg("end_stone_pickaxe", new PickaxeItem(ModToolTiers.STONE_END_STONE, new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolTiers.STONE_END_STONE, 1, -2.75f))));
        END_STONE_SHOVEL = reg("end_stone_shovel", new ShovelItem(ModToolTiers.STONE_END_STONE, new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolTiers.STONE_END_STONE, 1.5f, -2.95f))));
        END_STONE_AXE = reg("end_stone_axe", new AxeItem(ModToolTiers.STONE_END_STONE, new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolTiers.STONE_END_STONE, 6, -3.15f))));
        END_STONE_HOE = reg("end_stone_hoe", new HoeItem(ModToolTiers.STONE_END_STONE, new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ModToolTiers.STONE_END_STONE, 0, -2.8f))));

        GRANITE_SWORD = reg("granite_sword", new SwordItem(ModToolTiers.STONE_GRANITE, new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ModToolTiers.STONE_GRANITE, 3, -2.5f))));
        GRANITE_PICKAXE = reg("granite_pickaxe", new PickaxeItem(ModToolTiers.STONE_GRANITE, new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolTiers.STONE_GRANITE, 1, -2.9f))));
        GRANITE_SHOVEL = reg("granite_shovel", new ShovelItem(ModToolTiers.STONE_GRANITE, new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolTiers.STONE_GRANITE, 2.0f, -3.1f))));
        GRANITE_AXE = reg("granite_axe", new AxeItem(ModToolTiers.STONE_GRANITE, new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolTiers.STONE_GRANITE, 7, -3.3f))));
        GRANITE_HOE = reg("granite_hoe", new HoeItem(ModToolTiers.STONE_GRANITE, new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ModToolTiers.STONE_GRANITE, 0, -3.1f))));

        NETHERRACK_SWORD = reg("netherrack_sword", new SwordItem(ModToolTiers.STONE_NETHERRACK, new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ModToolTiers.STONE_NETHERRACK, 2, -2.2f))));
        NETHERRACK_PICKAXE = reg("netherrack_pickaxe", new PickaxeItem(ModToolTiers.STONE_NETHERRACK, new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolTiers.STONE_NETHERRACK, 1, -2.6f))));
        NETHERRACK_SHOVEL = reg("netherrack_shovel", new ShovelItem(ModToolTiers.STONE_NETHERRACK, new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolTiers.STONE_NETHERRACK, 1.0f, -2.8f))));
        NETHERRACK_AXE = reg("netherrack_axe", new AxeItem(ModToolTiers.STONE_NETHERRACK, new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolTiers.STONE_NETHERRACK, 5, -3.0f))));
        NETHERRACK_HOE = reg("netherrack_hoe", new HoeItem(ModToolTiers.STONE_NETHERRACK, new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ModToolTiers.STONE_NETHERRACK, 0, -2.5f))));

        SANDSTONE_SWORD = reg("sandstone_sword", new SwordItem(ModToolTiers.STONE_SANDSTONE, new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ModToolTiers.STONE_SANDSTONE, 2, -2.3f))));
        SANDSTONE_PICKAXE = reg("sandstone_pickaxe", new PickaxeItem(ModToolTiers.STONE_SANDSTONE, new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolTiers.STONE_SANDSTONE, 1, -2.7f))));
        SANDSTONE_SHOVEL = reg("sandstone_shovel", new ShovelItem(ModToolTiers.STONE_SANDSTONE, new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolTiers.STONE_SANDSTONE, 1.0f, -2.9f))));
        SANDSTONE_AXE = reg("sandstone_axe", new AxeItem(ModToolTiers.STONE_SANDSTONE, new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolTiers.STONE_SANDSTONE, 5, -3.1f))));
        SANDSTONE_HOE = reg("sandstone_hoe", new HoeItem(ModToolTiers.STONE_SANDSTONE, new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ModToolTiers.STONE_SANDSTONE, 0, -2.7f))));

        SMOOTH_BASALT_SWORD = reg("smooth_basalt_sword", new SwordItem(ModToolTiers.STONE_SMOOTH_BASALT, new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ModToolTiers.STONE_SMOOTH_BASALT, 3, -2.45f))));
        SMOOTH_BASALT_PICKAXE = reg("smooth_basalt_pickaxe", new PickaxeItem(ModToolTiers.STONE_SMOOTH_BASALT, new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolTiers.STONE_SMOOTH_BASALT, 1, -2.85f))));
        SMOOTH_BASALT_SHOVEL = reg("smooth_basalt_shovel", new ShovelItem(ModToolTiers.STONE_SMOOTH_BASALT, new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolTiers.STONE_SMOOTH_BASALT, 1.5f, -3.05f))));
        SMOOTH_BASALT_AXE = reg("smooth_basalt_axe", new AxeItem(ModToolTiers.STONE_SMOOTH_BASALT, new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolTiers.STONE_SMOOTH_BASALT, 6, -3.25f))));
        SMOOTH_BASALT_HOE = reg("smooth_basalt_hoe", new HoeItem(ModToolTiers.STONE_SMOOTH_BASALT, new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ModToolTiers.STONE_SMOOTH_BASALT, 0, -3.0f))));

        TERRACOTTA_SWORD = reg("terracotta_sword", new SwordItem(ModToolTiers.STONE_TERRACOTTA, new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ModToolTiers.STONE_TERRACOTTA, 3, -2.35f))));
        TERRACOTTA_PICKAXE = reg("terracotta_pickaxe", new PickaxeItem(ModToolTiers.STONE_TERRACOTTA, new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolTiers.STONE_TERRACOTTA, 1, -2.75f))));
        TERRACOTTA_SHOVEL = reg("terracotta_shovel", new ShovelItem(ModToolTiers.STONE_TERRACOTTA, new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolTiers.STONE_TERRACOTTA, 1.5f, -2.95f))));
        TERRACOTTA_AXE = reg("terracotta_axe", new AxeItem(ModToolTiers.STONE_TERRACOTTA, new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolTiers.STONE_TERRACOTTA, 6, -3.15f))));
        TERRACOTTA_HOE = reg("terracotta_hoe", new HoeItem(ModToolTiers.STONE_TERRACOTTA, new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ModToolTiers.STONE_TERRACOTTA, 0, -2.8f))));

        TUFF_SWORD = reg("tuff_sword", new SwordItem(ModToolTiers.STONE_TUFF, new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ModToolTiers.STONE_TUFF, 2, -2.35f))));
        TUFF_PICKAXE = reg("tuff_pickaxe", new PickaxeItem(ModToolTiers.STONE_TUFF, new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolTiers.STONE_TUFF, 1, -2.75f))));
        TUFF_SHOVEL = reg("tuff_shovel", new ShovelItem(ModToolTiers.STONE_TUFF, new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolTiers.STONE_TUFF, 1.5f, -2.95f))));
        TUFF_AXE = reg("tuff_axe", new AxeItem(ModToolTiers.STONE_TUFF, new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolTiers.STONE_TUFF, 5, -3.15f))));
        TUFF_HOE = reg("tuff_hoe", new HoeItem(ModToolTiers.STONE_TUFF, new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ModToolTiers.STONE_TUFF, 0, -2.8f))));

        // Wood variant tools
        regStdToolSet("oak", ModToolTiers.OAK_TOOL, (t,s)->OAK_SWORD=s, (t,s)->OAK_PICKAXE=s, (t,s)->OAK_SHOVEL=s, (t,s)->OAK_AXE=s, (t,s)->OAK_HOE=s);
        regStdToolSet("spruce", ModToolTiers.SPRUCE_TOOL, (t,s)->SPRUCE_SWORD=s, (t,s)->SPRUCE_PICKAXE=s, (t,s)->SPRUCE_SHOVEL=s, (t,s)->SPRUCE_AXE=s, (t,s)->SPRUCE_HOE=s);
        regStdToolSet("birch", ModToolTiers.BIRCH_TOOL, (t,s)->BIRCH_SWORD=s, (t,s)->BIRCH_PICKAXE=s, (t,s)->BIRCH_SHOVEL=s, (t,s)->BIRCH_AXE=s, (t,s)->BIRCH_HOE=s);
        regStdToolSet("jungle", ModToolTiers.JUNGLE_TOOL, (t,s)->JUNGLE_SWORD=s, (t,s)->JUNGLE_PICKAXE=s, (t,s)->JUNGLE_SHOVEL=s, (t,s)->JUNGLE_AXE=s, (t,s)->JUNGLE_HOE=s);
        regStdToolSet("acacia", ModToolTiers.ACACIA_TOOL, (t,s)->ACACIA_SWORD=s, (t,s)->ACACIA_PICKAXE=s, (t,s)->ACACIA_SHOVEL=s, (t,s)->ACACIA_AXE=s, (t,s)->ACACIA_HOE=s);
        regStdToolSet("dark_oak", ModToolTiers.DARK_OAK_TOOL, (t,s)->DARK_OAK_SWORD=s, (t,s)->DARK_OAK_PICKAXE=s, (t,s)->DARK_OAK_SHOVEL=s, (t,s)->DARK_OAK_AXE=s, (t,s)->DARK_OAK_HOE=s);
        regStdToolSet("mangrove", ModToolTiers.MANGROVE_TOOL, (t,s)->MANGROVE_SWORD=s, (t,s)->MANGROVE_PICKAXE=s, (t,s)->MANGROVE_SHOVEL=s, (t,s)->MANGROVE_AXE=s, (t,s)->MANGROVE_HOE=s);
        regStdToolSet("cherry", ModToolTiers.CHERRY_TOOL, (t,s)->CHERRY_SWORD=s, (t,s)->CHERRY_PICKAXE=s, (t,s)->CHERRY_SHOVEL=s, (t,s)->CHERRY_AXE=s, (t,s)->CHERRY_HOE=s);
        regStdToolSet("bamboo", ModToolTiers.BAMBOO_TOOL, (t,s)->BAMBOO_SWORD=s, (t,s)->BAMBOO_PICKAXE=s, (t,s)->BAMBOO_SHOVEL=s, (t,s)->BAMBOO_AXE=s, (t,s)->BAMBOO_HOE=s);
        regStdToolSet("crimson", ModToolTiers.CRIMSON_TOOL, (t,s)->CRIMSON_SWORD=s, (t,s)->CRIMSON_PICKAXE=s, (t,s)->CRIMSON_SHOVEL=s, (t,s)->CRIMSON_AXE=s, (t,s)->CRIMSON_HOE=s);
        regStdToolSet("warped", ModToolTiers.WARPED_TOOL, (t,s)->WARPED_SWORD=s, (t,s)->WARPED_PICKAXE=s, (t,s)->WARPED_SHOVEL=s, (t,s)->WARPED_AXE=s, (t,s)->WARPED_HOE=s);

        // Leather tools
        regStdToolSet("leather", ModToolTiers.LEATHER_TOOL, (t,s)->LEATHER_SWORD=s, (t,s)->LEATHER_PICKAXE=s, (t,s)->LEATHER_SHOVEL=s, (t,s)->LEATHER_AXE=s, (t,s)->LEATHER_HOE=s);

        // Paper tools
        regStdToolSet("paper", ModToolTiers.PAPER, (t,s)->PAPER_SWORD=s, (t,s)->PAPER_PICKAXE=s, (t,s)->PAPER_SHOVEL=s, (t,s)->PAPER_AXE=s, (t,s)->PAPER_HOE=s);

        // Feather tools
        regStdToolSet("feather", ModToolTiers.FEATHER, (t,s)->FEATHER_SWORD=s, (t,s)->FEATHER_PICKAXE=s, (t,s)->FEATHER_SHOVEL=s, (t,s)->FEATHER_AXE=s, (t,s)->FEATHER_HOE=s);

        // Glass tools
        regStdToolSet("glass", ModToolTiers.GLASS, (t,s)->GLASS_SWORD=s, (t,s)->GLASS_PICKAXE=s, (t,s)->GLASS_SHOVEL=s, (t,s)->GLASS_AXE=s, (t,s)->GLASS_HOE=s);

        // Rabbit hide armor only
        RABBIT_HIDE_HELMET = reg("rabbit_hide_helmet", new ArmorItem(ModArmorMaterials.RABBIT_HIDE_ARMOR_MATERIAL,
                ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(5))));
        RABBIT_HIDE_CHESTPLATE = reg("rabbit_hide_chestplate", new ArmorItem(ModArmorMaterials.RABBIT_HIDE_ARMOR_MATERIAL,
                ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(5))));
        RABBIT_HIDE_LEGGINGS = reg("rabbit_hide_leggings", new ArmorItem(ModArmorMaterials.RABBIT_HIDE_ARMOR_MATERIAL,
                ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(5))));
        RABBIT_HIDE_BOOTS = reg("rabbit_hide_boots", new ArmorItem(ModArmorMaterials.RABBIT_HIDE_ARMOR_MATERIAL,
                ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(5))));

        // Cactus tools + armor
        regStdToolSet("cactus", ModToolTiers.CACTUS, (t,s)->CACTUS_SWORD=s, (t,s)->CACTUS_PICKAXE=s, (t,s)->CACTUS_SHOVEL=s, (t,s)->CACTUS_AXE=s, (t,s)->CACTUS_HOE=s);
        CACTUS_HELMET = reg("cactus_helmet", new ArmorItem(ModArmorMaterials.CACTUS_ARMOR_MATERIAL,
                ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(5))));
        CACTUS_CHESTPLATE = reg("cactus_chestplate", new ArmorItem(ModArmorMaterials.CACTUS_ARMOR_MATERIAL,
                ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(5))));
        CACTUS_LEGGINGS = reg("cactus_leggings", new ArmorItem(ModArmorMaterials.CACTUS_ARMOR_MATERIAL,
                ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(5))));
        CACTUS_BOOTS = reg("cactus_boots", new ArmorItem(ModArmorMaterials.CACTUS_ARMOR_MATERIAL,
                ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(5))));

        // Sponge tools only
        regStdToolSet("sponge", ModToolTiers.SPONGE, (t,s)->SPONGE_SWORD=s, (t,s)->SPONGE_PICKAXE=s, (t,s)->SPONGE_SHOVEL=s, (t,s)->SPONGE_AXE=s, (t,s)->SPONGE_HOE=s);

        // Bone tools + armor
        regStdToolSet("bone", ModToolTiers.BONE, (t,s)->BONE_SWORD=s, (t,s)->BONE_PICKAXE=s, (t,s)->BONE_SHOVEL=s, (t,s)->BONE_AXE=s, (t,s)->BONE_HOE=s);
        BONE_HELMET = reg("bone_helmet", new ArmorItem(ModArmorMaterials.BONE_ARMOR_MATERIAL,
                ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(6))));
        BONE_CHESTPLATE = reg("bone_chestplate", new ArmorItem(ModArmorMaterials.BONE_ARMOR_MATERIAL,
                ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(6))));
        BONE_LEGGINGS = reg("bone_leggings", new ArmorItem(ModArmorMaterials.BONE_ARMOR_MATERIAL,
                ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(6))));
        BONE_BOOTS = reg("bone_boots", new ArmorItem(ModArmorMaterials.BONE_ARMOR_MATERIAL,
                ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(6))));

        // Clay tools + armor
        regStdToolSet("clay", ModToolTiers.CLAY, (t,s)->CLAY_SWORD=s, (t,s)->CLAY_PICKAXE=s, (t,s)->CLAY_SHOVEL=s, (t,s)->CLAY_AXE=s, (t,s)->CLAY_HOE=s);
        CLAY_HELMET = reg("clay_helmet", new ArmorItem(ModArmorMaterials.CLAY_ARMOR_MATERIAL,
                ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(8))));
        CLAY_CHESTPLATE = reg("clay_chestplate", new ArmorItem(ModArmorMaterials.CLAY_ARMOR_MATERIAL,
                ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(8))));
        CLAY_LEGGINGS = reg("clay_leggings", new ArmorItem(ModArmorMaterials.CLAY_ARMOR_MATERIAL,
                ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(8))));
        CLAY_BOOTS = reg("clay_boots", new ArmorItem(ModArmorMaterials.CLAY_ARMOR_MATERIAL,
                ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(8))));

        // Nether wart tools only
        regStdToolSet("nether_wart", ModToolTiers.NETHER_WART, (t,s)->NETHER_WART_SWORD=s, (t,s)->NETHER_WART_PICKAXE=s, (t,s)->NETHER_WART_SHOVEL=s, (t,s)->NETHER_WART_AXE=s, (t,s)->NETHER_WART_HOE=s);

        // Brick tools + armor
        regStdToolSet("brick", ModToolTiers.BRICK, (t,s)->BRICK_SWORD=s, (t,s)->BRICK_PICKAXE=s, (t,s)->BRICK_SHOVEL=s, (t,s)->BRICK_AXE=s, (t,s)->BRICK_HOE=s);
        BRICK_HELMET = reg("brick_helmet", new ArmorItem(ModArmorMaterials.BRICK_ARMOR_MATERIAL,
                ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(10))));
        BRICK_CHESTPLATE = reg("brick_chestplate", new ArmorItem(ModArmorMaterials.BRICK_ARMOR_MATERIAL,
                ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(10))));
        BRICK_LEGGINGS = reg("brick_leggings", new ArmorItem(ModArmorMaterials.BRICK_ARMOR_MATERIAL,
                ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(10))));
        BRICK_BOOTS = reg("brick_boots", new ArmorItem(ModArmorMaterials.BRICK_ARMOR_MATERIAL,
                ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(10))));

        // Nether brick tools + armor
        regStdToolSet("nether_brick", ModToolTiers.NETHER_BRICK, (t,s)->NETHER_BRICK_SWORD=s, (t,s)->NETHER_BRICK_PICKAXE=s, (t,s)->NETHER_BRICK_SHOVEL=s, (t,s)->NETHER_BRICK_AXE=s, (t,s)->NETHER_BRICK_HOE=s);
        NETHER_BRICK_HELMET = reg("nether_brick_helmet", new ArmorItem(ModArmorMaterials.NETHER_BRICK_ARMOR_MATERIAL,
                ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(10))));
        NETHER_BRICK_CHESTPLATE = reg("nether_brick_chestplate", new ArmorItem(ModArmorMaterials.NETHER_BRICK_ARMOR_MATERIAL,
                ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(10))));
        NETHER_BRICK_LEGGINGS = reg("nether_brick_leggings", new ArmorItem(ModArmorMaterials.NETHER_BRICK_ARMOR_MATERIAL,
                ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(10))));
        NETHER_BRICK_BOOTS = reg("nether_brick_boots", new ArmorItem(ModArmorMaterials.NETHER_BRICK_ARMOR_MATERIAL,
                ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(10))));

        // Pointed dripstone tools only
        regStdToolSet("pointed_dripstone", ModToolTiers.POINTED_DRIPSTONE, (t,s)->POINTED_DRIPSTONE_SWORD=s, (t,s)->POINTED_DRIPSTONE_PICKAXE=s, (t,s)->POINTED_DRIPSTONE_SHOVEL=s, (t,s)->POINTED_DRIPSTONE_AXE=s, (t,s)->POINTED_DRIPSTONE_HOE=s);

        // Copper tools + armor
        regStdToolSet("copper", ModToolTiers.COPPER, (t,s)->COPPER_SWORD=s, (t,s)->COPPER_PICKAXE=s, (t,s)->COPPER_SHOVEL=s, (t,s)->COPPER_AXE=s, (t,s)->COPPER_HOE=s);
        COPPER_HELMET = reg("copper_helmet", new ArmorItem(ModArmorMaterials.COPPER_ARMOR_MATERIAL,
                ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(12))));
        COPPER_CHESTPLATE = reg("copper_chestplate", new ArmorItem(ModArmorMaterials.COPPER_ARMOR_MATERIAL,
                ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(12))));
        COPPER_LEGGINGS = reg("copper_leggings", new ArmorItem(ModArmorMaterials.COPPER_ARMOR_MATERIAL,
                ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(12))));
        COPPER_BOOTS = reg("copper_boots", new ArmorItem(ModArmorMaterials.COPPER_ARMOR_MATERIAL,
                ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(12))));

        // Phantom tools + armor
        regStdToolSet("phantom", ModToolTiers.PHANTOM_MEMBRANE, (t,s)->PHANTOM_SWORD=s, (t,s)->PHANTOM_PICKAXE=s, (t,s)->PHANTOM_SHOVEL=s, (t,s)->PHANTOM_AXE=s, (t,s)->PHANTOM_HOE=s);
        PHANTOM_HELMET = reg("phantom_helmet", new ArmorItem(ModArmorMaterials.PHANTOM_ARMOR_MATERIAL,
                ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(12))));
        PHANTOM_CHESTPLATE = reg("phantom_chestplate", new ArmorItem(ModArmorMaterials.PHANTOM_ARMOR_MATERIAL,
                ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(12))));
        PHANTOM_LEGGINGS = reg("phantom_leggings", new ArmorItem(ModArmorMaterials.PHANTOM_ARMOR_MATERIAL,
                ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(12))));
        PHANTOM_BOOTS = reg("phantom_boots", new ArmorItem(ModArmorMaterials.PHANTOM_ARMOR_MATERIAL,
                ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(12))));

        // Magma cream tools + armor
        regStdToolSet("magma_cream", ModToolTiers.MAGMA_CREAM, (t,s)->MAGMA_CREAM_SWORD=s, (t,s)->MAGMA_CREAM_PICKAXE=s, (t,s)->MAGMA_CREAM_SHOVEL=s, (t,s)->MAGMA_CREAM_AXE=s, (t,s)->MAGMA_CREAM_HOE=s);
        MAGMA_CREAM_HELMET = reg("magma_cream_helmet", new ArmorItem(ModArmorMaterials.MAGMA_CREAM_ARMOR_MATERIAL,
                ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(10))));
        MAGMA_CREAM_CHESTPLATE = reg("magma_cream_chestplate", new ArmorItem(ModArmorMaterials.MAGMA_CREAM_ARMOR_MATERIAL,
                ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(10))));
        MAGMA_CREAM_LEGGINGS = reg("magma_cream_leggings", new ArmorItem(ModArmorMaterials.MAGMA_CREAM_ARMOR_MATERIAL,
                ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(10))));
        MAGMA_CREAM_BOOTS = reg("magma_cream_boots", new ArmorItem(ModArmorMaterials.MAGMA_CREAM_ARMOR_MATERIAL,
                ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(10))));

        // Slime tools + armor
        regStdToolSet("slime", ModToolTiers.SLIME, (t,s)->SLIME_SWORD=s, (t,s)->SLIME_PICKAXE=s, (t,s)->SLIME_SHOVEL=s, (t,s)->SLIME_AXE=s, (t,s)->SLIME_HOE=s);
        SLIME_HELMET = reg("slime_helmet", new ArmorItem(ModArmorMaterials.SLIME_ARMOR_MATERIAL,
                ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(10))));
        SLIME_CHESTPLATE = reg("slime_chestplate", new ArmorItem(ModArmorMaterials.SLIME_ARMOR_MATERIAL,
                ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(10))));
        SLIME_LEGGINGS = reg("slime_leggings", new ArmorItem(ModArmorMaterials.SLIME_ARMOR_MATERIAL,
                ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(10))));
        SLIME_BOOTS = reg("slime_boots", new ArmorItem(ModArmorMaterials.SLIME_ARMOR_MATERIAL,
                ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(10))));

        // Blaze tools + armor
        regStdToolSet("blaze", ModToolTiers.BLAZE_ROD, (t,s)->BLAZE_SWORD=s, (t,s)->BLAZE_PICKAXE=s, (t,s)->BLAZE_SHOVEL=s, (t,s)->BLAZE_AXE=s, (t,s)->BLAZE_HOE=s);
        BLAZE_HELMET = reg("blaze_helmet", new ArmorItem(ModArmorMaterials.BLAZE_ARMOR_MATERIAL,
                ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(15))));
        BLAZE_CHESTPLATE = reg("blaze_chestplate", new ArmorItem(ModArmorMaterials.BLAZE_ARMOR_MATERIAL,
                ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(15))));
        BLAZE_LEGGINGS = reg("blaze_leggings", new ArmorItem(ModArmorMaterials.BLAZE_ARMOR_MATERIAL,
                ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(15))));
        BLAZE_BOOTS = reg("blaze_boots", new ArmorItem(ModArmorMaterials.BLAZE_ARMOR_MATERIAL,
                ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(15))));

        // Nautilus tools + armor
        regStdToolSet("nautilus", ModToolTiers.NAUTILUS_SHELL, (t,s)->NAUTILUS_SWORD=s, (t,s)->NAUTILUS_PICKAXE=s, (t,s)->NAUTILUS_SHOVEL=s, (t,s)->NAUTILUS_AXE=s, (t,s)->NAUTILUS_HOE=s);
        NAUTILUS_HELMET = reg("nautilus_helmet", new ArmorItem(ModArmorMaterials.NAUTILUS_ARMOR_MATERIAL,
                ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(15))));
        NAUTILUS_CHESTPLATE = reg("nautilus_chestplate", new ArmorItem(ModArmorMaterials.NAUTILUS_ARMOR_MATERIAL,
                ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(15))));
        NAUTILUS_LEGGINGS = reg("nautilus_leggings", new ArmorItem(ModArmorMaterials.NAUTILUS_ARMOR_MATERIAL,
                ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(15))));
        NAUTILUS_BOOTS = reg("nautilus_boots", new ArmorItem(ModArmorMaterials.NAUTILUS_ARMOR_MATERIAL,
                ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(15))));

        // Purpur tools + armor
        regStdToolSet("purpur", ModToolTiers.PURPUR, (t,s)->PURPUR_SWORD=s, (t,s)->PURPUR_PICKAXE=s, (t,s)->PURPUR_SHOVEL=s, (t,s)->PURPUR_AXE=s, (t,s)->PURPUR_HOE=s);
        PURPUR_HELMET = reg("purpur_helmet", new ArmorItem(ModArmorMaterials.PURPUR_ARMOR_MATERIAL,
                ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(15))));
        PURPUR_CHESTPLATE = reg("purpur_chestplate", new ArmorItem(ModArmorMaterials.PURPUR_ARMOR_MATERIAL,
                ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(15))));
        PURPUR_LEGGINGS = reg("purpur_leggings", new ArmorItem(ModArmorMaterials.PURPUR_ARMOR_MATERIAL,
                ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(15))));
        PURPUR_BOOTS = reg("purpur_boots", new ArmorItem(ModArmorMaterials.PURPUR_ARMOR_MATERIAL,
                ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(15))));

        // Ghast tear tools + armor
        regStdToolSet("ghast_tear", ModToolTiers.GHAST_TEAR, (t,s)->GHAST_TEAR_SWORD=s, (t,s)->GHAST_TEAR_PICKAXE=s, (t,s)->GHAST_TEAR_SHOVEL=s, (t,s)->GHAST_TEAR_AXE=s, (t,s)->GHAST_TEAR_HOE=s);
        GHAST_TEAR_HELMET = reg("ghast_tear_helmet", new ArmorItem(ModArmorMaterials.GHAST_TEAR_ARMOR_MATERIAL,
                ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(18))));
        GHAST_TEAR_CHESTPLATE = reg("ghast_tear_chestplate", new ArmorItem(ModArmorMaterials.GHAST_TEAR_ARMOR_MATERIAL,
                ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(18))));
        GHAST_TEAR_LEGGINGS = reg("ghast_tear_leggings", new ArmorItem(ModArmorMaterials.GHAST_TEAR_ARMOR_MATERIAL,
                ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(18))));
        GHAST_TEAR_BOOTS = reg("ghast_tear_boots", new ArmorItem(ModArmorMaterials.GHAST_TEAR_ARMOR_MATERIAL,
                ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(18))));

        // Eye of ender tools + armor
        regStdToolSet("eye_of_ender", ModToolTiers.EYE_OF_ENDER, (t,s)->EYE_OF_ENDER_SWORD=s, (t,s)->EYE_OF_ENDER_PICKAXE=s, (t,s)->EYE_OF_ENDER_SHOVEL=s, (t,s)->EYE_OF_ENDER_AXE=s, (t,s)->EYE_OF_ENDER_HOE=s);
        EYE_OF_ENDER_HELMET = reg("eye_of_ender_helmet", new ArmorItem(ModArmorMaterials.EYE_OF_ENDER_ARMOR_MATERIAL,
                ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(20))));
        EYE_OF_ENDER_CHESTPLATE = reg("eye_of_ender_chestplate", new ArmorItem(ModArmorMaterials.EYE_OF_ENDER_ARMOR_MATERIAL,
                ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(20))));
        EYE_OF_ENDER_LEGGINGS = reg("eye_of_ender_leggings", new ArmorItem(ModArmorMaterials.EYE_OF_ENDER_ARMOR_MATERIAL,
                ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(20))));
        EYE_OF_ENDER_BOOTS = reg("eye_of_ender_boots", new ArmorItem(ModArmorMaterials.EYE_OF_ENDER_ARMOR_MATERIAL,
                ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(20))));

        // Shulker tools + armor
        regStdToolSet("shulker", ModToolTiers.SHULKER_SHELL, (t,s)->SHULKER_SWORD=s, (t,s)->SHULKER_PICKAXE=s, (t,s)->SHULKER_SHOVEL=s, (t,s)->SHULKER_AXE=s, (t,s)->SHULKER_HOE=s);
        SHULKER_HELMET = reg("shulker_helmet", new ArmorItem(ModArmorMaterials.SHULKER_ARMOR_MATERIAL,
                ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(20))));
        SHULKER_CHESTPLATE = reg("shulker_chestplate", new ArmorItem(ModArmorMaterials.SHULKER_ARMOR_MATERIAL,
                ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(20))));
        SHULKER_LEGGINGS = reg("shulker_leggings", new ArmorItem(ModArmorMaterials.SHULKER_ARMOR_MATERIAL,
                ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(20))));
        SHULKER_BOOTS = reg("shulker_boots", new ArmorItem(ModArmorMaterials.SHULKER_ARMOR_MATERIAL,
                ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(20))));

        // Turtle scute armor only
        TURTLE_SCUTE_HELMET = reg("turtle_scute_helmet", new ArmorItem(ModArmorMaterials.TURTLE_SCUTE_ARMOR_MATERIAL,
                ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(20))));
        TURTLE_SCUTE_CHESTPLATE = reg("turtle_scute_chestplate", new ArmorItem(ModArmorMaterials.TURTLE_SCUTE_ARMOR_MATERIAL,
                ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(20))));
        TURTLE_SCUTE_LEGGINGS = reg("turtle_scute_leggings", new ArmorItem(ModArmorMaterials.TURTLE_SCUTE_ARMOR_MATERIAL,
                ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(20))));
        TURTLE_SCUTE_BOOTS = reg("turtle_scute_boots", new ArmorItem(ModArmorMaterials.TURTLE_SCUTE_ARMOR_MATERIAL,
                ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(20))));

        // Echo shard tools + armor
        regStdToolSet("echo_shard", ModToolTiers.ECHO_SHARD, (t,s)->ECHO_SHARD_SWORD=s, (t,s)->ECHO_SHARD_PICKAXE=s, (t,s)->ECHO_SHARD_SHOVEL=s, (t,s)->ECHO_SHARD_AXE=s, (t,s)->ECHO_SHARD_HOE=s);
        ECHO_SHARD_HELMET = reg("echo_shard_helmet", new ArmorItem(ModArmorMaterials.ECHO_SHARD_ARMOR_MATERIAL,
                ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(25))));
        ECHO_SHARD_CHESTPLATE = reg("echo_shard_chestplate", new ArmorItem(ModArmorMaterials.ECHO_SHARD_ARMOR_MATERIAL,
                ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(25))));
        ECHO_SHARD_LEGGINGS = reg("echo_shard_leggings", new ArmorItem(ModArmorMaterials.ECHO_SHARD_ARMOR_MATERIAL,
                ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(25))));
        ECHO_SHARD_BOOTS = reg("echo_shard_boots", new ArmorItem(ModArmorMaterials.ECHO_SHARD_ARMOR_MATERIAL,
                ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(25))));

        // Dragon breath tools + armor
        regStdToolSet("dragon_breath", ModToolTiers.DRAGON_BREATH, (t,s)->DRAGON_BREATH_SWORD=s, (t,s)->DRAGON_BREATH_PICKAXE=s, (t,s)->DRAGON_BREATH_SHOVEL=s, (t,s)->DRAGON_BREATH_AXE=s, (t,s)->DRAGON_BREATH_HOE=s);
        DRAGON_BREATH_HELMET = reg("dragon_breath_helmet", new ArmorItem(ModArmorMaterials.DRAGON_BREATH_ARMOR_MATERIAL,
                ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(30))));
        DRAGON_BREATH_CHESTPLATE = reg("dragon_breath_chestplate", new ArmorItem(ModArmorMaterials.DRAGON_BREATH_ARMOR_MATERIAL,
                ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(30))));
        DRAGON_BREATH_LEGGINGS = reg("dragon_breath_leggings", new ArmorItem(ModArmorMaterials.DRAGON_BREATH_ARMOR_MATERIAL,
                ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(30))));
        DRAGON_BREATH_BOOTS = reg("dragon_breath_boots", new ArmorItem(ModArmorMaterials.DRAGON_BREATH_ARMOR_MATERIAL,
                ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(30))));

        // Food tools + armor: Bread
        regEdibleToolSet("bread", ModToolTiers.BREAD_TOOL, (t,s)->BREAD_SWORD=s, (t,s)->BREAD_PICKAXE=s, (t,s)->BREAD_SHOVEL=s, (t,s)->BREAD_AXE=s, (t,s)->BREAD_HOE=s);
        regEdibleArmorSet("bread", ModArmorMaterials.BREAD_ARMOR_MATERIAL, 5, (t,s)->BREAD_HELMET=s, (t,s)->BREAD_CHESTPLATE=s, (t,s)->BREAD_LEGGINGS=s, (t,s)->BREAD_BOOTS=s);

        // Food tools + armor: Dried Kelp
        regEdibleToolSet("dried_kelp", ModToolTiers.DRIED_KELP_TOOL, (t,s)->DRIED_KELP_SWORD=s, (t,s)->DRIED_KELP_PICKAXE=s, (t,s)->DRIED_KELP_SHOVEL=s, (t,s)->DRIED_KELP_AXE=s, (t,s)->DRIED_KELP_HOE=s);
        regEdibleArmorSet("dried_kelp", ModArmorMaterials.DRIED_KELP_ARMOR_MATERIAL, 3, (t,s)->DRIED_KELP_HELMET=s, (t,s)->DRIED_KELP_CHESTPLATE=s, (t,s)->DRIED_KELP_LEGGINGS=s, (t,s)->DRIED_KELP_BOOTS=s);

        // Food tools + armor: Rotten Flesh
        regEdibleToolSet("rotten_flesh", ModToolTiers.ROTTEN_FLESH_TOOL, (t,s)->ROTTEN_FLESH_SWORD=s, (t,s)->ROTTEN_FLESH_PICKAXE=s, (t,s)->ROTTEN_FLESH_SHOVEL=s, (t,s)->ROTTEN_FLESH_AXE=s, (t,s)->ROTTEN_FLESH_HOE=s);
        regEdibleArmorSet("rotten_flesh", ModArmorMaterials.ROTTEN_FLESH_ARMOR_MATERIAL, 5, (t,s)->ROTTEN_FLESH_HELMET=s, (t,s)->ROTTEN_FLESH_CHESTPLATE=s, (t,s)->ROTTEN_FLESH_LEGGINGS=s, (t,s)->ROTTEN_FLESH_BOOTS=s);

        // Food tools + armor: Melon
        regEdibleToolSet("melon", ModToolTiers.MELON_TOOL, (t,s)->MELON_SWORD=s, (t,s)->MELON_PICKAXE=s, (t,s)->MELON_SHOVEL=s, (t,s)->MELON_AXE=s, (t,s)->MELON_HOE=s);
        regEdibleArmorSet("melon", ModArmorMaterials.MELON_ARMOR_MATERIAL, 5, (t,s)->MELON_HELMET=s, (t,s)->MELON_CHESTPLATE=s, (t,s)->MELON_LEGGINGS=s, (t,s)->MELON_BOOTS=s);

        // Food tools + armor: Sweet Berry
        regEdibleToolSet("sweet_berry", ModToolTiers.SWEET_BERRY_TOOL, (t,s)->SWEET_BERRY_SWORD=s, (t,s)->SWEET_BERRY_PICKAXE=s, (t,s)->SWEET_BERRY_SHOVEL=s, (t,s)->SWEET_BERRY_AXE=s, (t,s)->SWEET_BERRY_HOE=s);
        regEdibleArmorSet("sweet_berry", ModArmorMaterials.SWEET_BERRY_ARMOR_MATERIAL, 5, (t,s)->SWEET_BERRY_HELMET=s, (t,s)->SWEET_BERRY_CHESTPLATE=s, (t,s)->SWEET_BERRY_LEGGINGS=s, (t,s)->SWEET_BERRY_BOOTS=s);

        // Food tools + armor: Pumpkin Pie
        regEdibleToolSet("pumpkin_pie", ModToolTiers.PUMPKIN_PIE_TOOL, (t,s)->PUMPKIN_PIE_SWORD=s, (t,s)->PUMPKIN_PIE_PICKAXE=s, (t,s)->PUMPKIN_PIE_SHOVEL=s, (t,s)->PUMPKIN_PIE_AXE=s, (t,s)->PUMPKIN_PIE_HOE=s);
        regEdibleArmorSet("pumpkin_pie", ModArmorMaterials.PUMPKIN_PIE_ARMOR_MATERIAL, 7, (t,s)->PUMPKIN_PIE_HELMET=s, (t,s)->PUMPKIN_PIE_CHESTPLATE=s, (t,s)->PUMPKIN_PIE_LEGGINGS=s, (t,s)->PUMPKIN_PIE_BOOTS=s);

        // Food tools + armor: Mushroom
        regEdibleToolSet("mushroom", ModToolTiers.MUSHROOM_TOOL, (t,s)->MUSHROOM_SWORD=s, (t,s)->MUSHROOM_PICKAXE=s, (t,s)->MUSHROOM_SHOVEL=s, (t,s)->MUSHROOM_AXE=s, (t,s)->MUSHROOM_HOE=s);
        regEdibleArmorSet("mushroom", ModArmorMaterials.MUSHROOM_ARMOR_MATERIAL, 10, (t,s)->MUSHROOM_HELMET=s, (t,s)->MUSHROOM_CHESTPLATE=s, (t,s)->MUSHROOM_LEGGINGS=s, (t,s)->MUSHROOM_BOOTS=s);

        // Food tools + armor: Pufferfish
        regEdibleToolSet("pufferfish", ModToolTiers.PUFFERFISH_TOOL, (t,s)->PUFFERFISH_SWORD=s, (t,s)->PUFFERFISH_PICKAXE=s, (t,s)->PUFFERFISH_SHOVEL=s, (t,s)->PUFFERFISH_AXE=s, (t,s)->PUFFERFISH_HOE=s);
        regEdibleArmorSet("pufferfish", ModArmorMaterials.PUFFERFISH_ARMOR_MATERIAL, 8, (t,s)->PUFFERFISH_HELMET=s, (t,s)->PUFFERFISH_CHESTPLATE=s, (t,s)->PUFFERFISH_LEGGINGS=s, (t,s)->PUFFERFISH_BOOTS=s);

        // Food tools + armor: Honey
        regEdibleToolSet("honey", ModToolTiers.HONEY_TOOL, (t,s)->HONEY_SWORD=s, (t,s)->HONEY_PICKAXE=s, (t,s)->HONEY_SHOVEL=s, (t,s)->HONEY_AXE=s, (t,s)->HONEY_HOE=s);
        regEdibleArmorSet("honey", ModArmorMaterials.HONEY_ARMOR_MATERIAL, 10, (t,s)->HONEY_HELMET=s, (t,s)->HONEY_CHESTPLATE=s, (t,s)->HONEY_LEGGINGS=s, (t,s)->HONEY_BOOTS=s);

        // Food tools + armor: Chorus Fruit
        regEdibleToolSet("chorus_fruit", ModToolTiers.CHORUS_FRUIT_TOOL, (t,s)->CHORUS_FRUIT_SWORD=s, (t,s)->CHORUS_FRUIT_PICKAXE=s, (t,s)->CHORUS_FRUIT_SHOVEL=s, (t,s)->CHORUS_FRUIT_AXE=s, (t,s)->CHORUS_FRUIT_HOE=s);
        regEdibleArmorSet("chorus_fruit", ModArmorMaterials.CHORUS_FRUIT_ARMOR_MATERIAL, 15, (t,s)->CHORUS_FRUIT_HELMET=s, (t,s)->CHORUS_FRUIT_CHESTPLATE=s, (t,s)->CHORUS_FRUIT_LEGGINGS=s, (t,s)->CHORUS_FRUIT_BOOTS=s);

        // Food tools + armor: Golden Apple
        regEdibleToolSet("golden_apple", ModToolTiers.GOLDEN_APPLE_TOOL, (t,s)->GOLDEN_APPLE_SWORD=s, (t,s)->GOLDEN_APPLE_PICKAXE=s, (t,s)->GOLDEN_APPLE_SHOVEL=s, (t,s)->GOLDEN_APPLE_AXE=s, (t,s)->GOLDEN_APPLE_HOE=s);
        regEdibleArmorSet("golden_apple", ModArmorMaterials.GOLDEN_APPLE_ARMOR_MATERIAL, 22, (t,s)->GOLDEN_APPLE_HELMET=s, (t,s)->GOLDEN_APPLE_CHESTPLATE=s, (t,s)->GOLDEN_APPLE_LEGGINGS=s, (t,s)->GOLDEN_APPLE_BOOTS=s);

        // Spawn eggs
        GHOST_SPAWN_EGG = reg("ghost_spawn_egg",
                new SpawnEggItem(ModEntities.GHOST, 0xFFFFFF, 0x999999, new Item.Settings()));
    }

    // Helper: register a standard 5-tool set with default attack modifiers
    @FunctionalInterface
    private interface ItemSetter { void set(String type, Item item); }

    private static void regStdToolSet(String prefix, net.minecraft.item.ToolMaterial tier,
                                       ItemSetter sword, ItemSetter pickaxe, ItemSetter shovel, ItemSetter axe, ItemSetter hoe) {
        sword.set("sword", reg(prefix + "_sword", new SwordItem(tier,
                new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(tier, 3, -2.4f)))));
        pickaxe.set("pickaxe", reg(prefix + "_pickaxe", new PickaxeItem(tier,
                new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(tier, 1, -2.8f)))));
        shovel.set("shovel", reg(prefix + "_shovel", new ShovelItem(tier,
                new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(tier, 1.5f, -3f)))));
        axe.set("axe", reg(prefix + "_axe", new AxeItem(tier,
                new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(tier, 6, -3.2f)))));
        hoe.set("hoe", reg(prefix + "_hoe", new HoeItem(tier,
                new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(tier, 0, -3f)))));
    }

    // Helper: register an edible 5-tool set with default attack modifiers and food values
    private static void regEdibleToolSet(String prefix, net.minecraft.item.ToolMaterial tier,
                                          ItemSetter sword, ItemSetter pickaxe, ItemSetter shovel, ItemSetter axe, ItemSetter hoe) {
        sword.set("sword", reg(prefix + "_sword", new EdibleSwordItem(tier,
                new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(tier, 3, -2.4f))
                        .food(food(4)))));
        pickaxe.set("pickaxe", reg(prefix + "_pickaxe", new EdiblePickaxeItem(tier,
                new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(tier, 1, -2.8f))
                        .food(food(6)))));
        shovel.set("shovel", reg(prefix + "_shovel", new EdibleShovelItem(tier,
                new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(tier, 1.5f, -3f))
                        .food(food(2)))));
        axe.set("axe", reg(prefix + "_axe", new EdibleAxeItem(tier,
                new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(tier, 6, -3.2f))
                        .food(food(6)))));
        hoe.set("hoe", reg(prefix + "_hoe", new EdibleHoeItem(tier,
                new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(tier, 0, -3f))
                        .food(food(4)))));
    }

    // Helper: register an edible 4-armor set with food values
    private static void regEdibleArmorSet(String prefix, net.minecraft.registry.entry.RegistryEntry<net.minecraft.item.ArmorMaterial> material,
                                           int durabilityMultiplier,
                                           ItemSetter helmet, ItemSetter chestplate, ItemSetter leggings, ItemSetter boots) {
        helmet.set("helmet", reg(prefix + "_helmet", new EdibleArmorItem(material,
                ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(durabilityMultiplier))
                        .food(food(10)))));
        chestplate.set("chestplate", reg(prefix + "_chestplate", new EdibleArmorItem(material,
                ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(durabilityMultiplier))
                        .food(food(14)))));
        leggings.set("leggings", reg(prefix + "_leggings", new EdibleArmorItem(material,
                ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(durabilityMultiplier))
                        .food(food(14)))));
        boots.set("boots", reg(prefix + "_boots", new EdibleArmorItem(material,
                ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(durabilityMultiplier))
                        .food(food(8)))));
    }

    private static FoodComponent food(int nutrition) {
        return new FoodComponent.Builder().nutrition(nutrition).saturationModifier(0.1f).build();
    }

    private static <T extends Item> T reg(String name, T item) {
        Registry.register(Registries.ITEM, Identifier.of(UsefultoolsMod.MOD_ID, name), item);
        return item;
    }
}
