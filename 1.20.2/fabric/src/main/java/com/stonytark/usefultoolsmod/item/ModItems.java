package com.stonytark.usefultoolsmod.item;

import com.stonytark.usefultoolsmod.generated.GeneratedRegistrationIds;

import com.stonytark.usefultoolsmod.UsefultoolsMod;
import com.stonytark.usefultoolsmod.entity.ModEntities;
import com.stonytark.usefultoolsmod.item.custom.*;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    public static Item RGOLD;
    public static Item RAW_RGOLD;
    public static Item OBSHARD;
    public static Item SEM;
    public static Item OBINGOT;
    public static Item GRENADE;
    public static Item HRED;
    public static Item HGLOW;
    public static Item RLAPIS;
    public static Item DYNAMITE;
    public static Item REMERALD_SWORD;
    public static Item REMERALD_PICKAXE;
    public static Item REMERALD_SHOVEL;
    public static Item REMERALD_AXE;
    public static Item REMERALD_HOE;
    public static Item PEMERALD_SWORD;
    public static Item PEMERALD_PICKAXE;
    public static Item PEMERALD_SHOVEL;
    public static Item PEMERALD_AXE;
    public static Item PEMERALD_HOE;
    public static Item ROBSIDIAN_SWORD;
    public static Item ROBSIDIAN_PICKAXE;
    public static Item ROBSIDIAN_SHOVEL;
    public static Item ROBSIDIAN_AXE;
    public static Item ROBSIDIAN_HOE;
    public static Item POBSIDIAN_SWORD;
    public static Item POBSIDIAN_PICKAXE;
    public static Item POBSIDIAN_SHOVEL;
    public static Item POBSIDIAN_AXE;
    public static Item POBSIDIAN_HOE;
    public static Item OVERPOWER_SWORD;
    public static Item OVERPOWER_PICKAXE;
    public static Item OVERPOWER_SHOVEL;
    public static Item OVERPOWER_AXE;
    public static Item HREDSTONE_SWORD;
    public static Item HREDSTONE_PICKAXE;
    public static Item HREDSTONE_SHOVEL;
    public static Item HREDSTONE_AXE;
    public static Item HREDSTONE_HOE;
    public static Item HGLOWSTONE_SWORD;
    public static Item HGLOWSTONE_PICKAXE;
    public static Item HGLOWSTONE_SHOVEL;
    public static Item HGLOWSTONE_AXE;
    public static Item HGLOWSTONE_HOE;
    public static Item RGOLD_SWORD;
    public static Item RGOLD_PICKAXE;
    public static Item RGOLD_SHOVEL;
    public static Item RGOLD_AXE;
    public static Item RGOLD_HOE;
    public static Item RLAPIS_SWORD;
    public static Item RLAPIS_PICKAXE;
    public static Item RLAPIS_SHOVEL;
    public static Item RLAPIS_AXE;
    public static Item RLAPIS_HOE;
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
    public static Item GHOST_SPAWN_EGG;
    public static Item ECTOPLASM;
    public static Item CONDENSED_ECTOPLASM;
    public static Item WRAITH_SPAWN_EGG;
    public static Item SPECTRAL_RESONATOR;
    public static Item STICKY_DYNAMITE;
    public static Item REMOTE_DETONATOR;
    public static Item RECTO_SWORD;
    public static Item RECTO_PICKAXE;
    public static Item RECTO_SHOVEL;
    public static Item RECTO_AXE;
    public static Item RECTO_HOE;
    public static Item REFINED_ECTOPLASM;
    public static Item ECTO_SWORD;
    public static Item ECTO_PICKAXE;
    public static Item ECTO_SHOVEL;
    public static Item ECTO_AXE;
    public static Item ECTO_HOE;
    public static Item ECTO_HELMET;
    public static Item ECTO_CHESTPLATE;
    public static Item ECTO_LEGGINGS;
    public static Item ECTO_BOOTS;
    public static Item COAL_DUST;
    public static Item HARDENED_COAL;
    public static Item COAL_SWORD;
    public static Item COAL_PICKAXE;
    public static Item COAL_SHOVEL;
    public static Item COAL_AXE;
    public static Item COAL_HOE;
    public static Item COAL_HELMET;
    public static Item COAL_CHESTPLATE;
    public static Item COAL_LEGGINGS;
    public static Item COAL_BOOTS;
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
    public static Item CALCIFIED_AMETHYST;
    public static Item GLACIAL_SHARD;
    public static Item POLISHED_QUARTZ;
    public static Item POLISHED_PRISMARINE;
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
    public static Item LEATHER_SWORD;
    public static Item LEATHER_PICKAXE;
    public static Item LEATHER_SHOVEL;
    public static Item LEATHER_AXE;
    public static Item LEATHER_HOE;
    public static Item PAPER_SWORD;
    public static Item PAPER_PICKAXE;
    public static Item PAPER_SHOVEL;
    public static Item PAPER_AXE;
    public static Item PAPER_HOE;
    public static Item FEATHER_SWORD;
    public static Item FEATHER_PICKAXE;
    public static Item FEATHER_SHOVEL;
    public static Item FEATHER_AXE;
    public static Item FEATHER_HOE;
    public static Item GLASS_SWORD;
    public static Item GLASS_PICKAXE;
    public static Item GLASS_SHOVEL;
    public static Item GLASS_AXE;
    public static Item GLASS_HOE;
    public static Item RABBIT_HIDE_HELMET;
    public static Item RABBIT_HIDE_CHESTPLATE;
    public static Item RABBIT_HIDE_LEGGINGS;
    public static Item RABBIT_HIDE_BOOTS;
    public static Item CACTUS_SWORD;
    public static Item CACTUS_PICKAXE;
    public static Item CACTUS_SHOVEL;
    public static Item CACTUS_AXE;
    public static Item CACTUS_HOE;
    public static Item CACTUS_HELMET;
    public static Item CACTUS_CHESTPLATE;
    public static Item CACTUS_LEGGINGS;
    public static Item CACTUS_BOOTS;
    public static Item SPONGE_SWORD;
    public static Item SPONGE_PICKAXE;
    public static Item SPONGE_SHOVEL;
    public static Item SPONGE_AXE;
    public static Item SPONGE_HOE;
    public static Item BONE_SWORD;
    public static Item BONE_PICKAXE;
    public static Item BONE_SHOVEL;
    public static Item BONE_AXE;
    public static Item BONE_HOE;
    public static Item BONE_HELMET;
    public static Item BONE_CHESTPLATE;
    public static Item BONE_LEGGINGS;
    public static Item BONE_BOOTS;
    public static Item CLAY_SWORD;
    public static Item CLAY_PICKAXE;
    public static Item CLAY_SHOVEL;
    public static Item CLAY_AXE;
    public static Item CLAY_HOE;
    public static Item CLAY_HELMET;
    public static Item CLAY_CHESTPLATE;
    public static Item CLAY_LEGGINGS;
    public static Item CLAY_BOOTS;
    public static Item NETHER_WART_SWORD;
    public static Item NETHER_WART_PICKAXE;
    public static Item NETHER_WART_SHOVEL;
    public static Item NETHER_WART_AXE;
    public static Item NETHER_WART_HOE;
    public static Item BRICK_SWORD;
    public static Item BRICK_PICKAXE;
    public static Item BRICK_SHOVEL;
    public static Item BRICK_AXE;
    public static Item BRICK_HOE;
    public static Item BRICK_HELMET;
    public static Item BRICK_CHESTPLATE;
    public static Item BRICK_LEGGINGS;
    public static Item BRICK_BOOTS;
    public static Item NETHER_BRICK_SWORD;
    public static Item NETHER_BRICK_PICKAXE;
    public static Item NETHER_BRICK_SHOVEL;
    public static Item NETHER_BRICK_AXE;
    public static Item NETHER_BRICK_HOE;
    public static Item NETHER_BRICK_HELMET;
    public static Item NETHER_BRICK_CHESTPLATE;
    public static Item NETHER_BRICK_LEGGINGS;
    public static Item NETHER_BRICK_BOOTS;
    public static Item POINTED_DRIPSTONE_SWORD;
    public static Item POINTED_DRIPSTONE_PICKAXE;
    public static Item POINTED_DRIPSTONE_SHOVEL;
    public static Item POINTED_DRIPSTONE_AXE;
    public static Item POINTED_DRIPSTONE_HOE;
    public static Item COPPER_SWORD;
    public static Item COPPER_PICKAXE;
    public static Item COPPER_SHOVEL;
    public static Item COPPER_AXE;
    public static Item COPPER_HOE;
    public static Item COPPER_HELMET;
    public static Item COPPER_CHESTPLATE;
    public static Item COPPER_LEGGINGS;
    public static Item COPPER_BOOTS;
    public static Item PHANTOM_SWORD;
    public static Item PHANTOM_PICKAXE;
    public static Item PHANTOM_SHOVEL;
    public static Item PHANTOM_AXE;
    public static Item PHANTOM_HOE;
    public static Item PHANTOM_HELMET;
    public static Item PHANTOM_CHESTPLATE;
    public static Item PHANTOM_LEGGINGS;
    public static Item PHANTOM_BOOTS;
    public static Item MAGMA_CREAM_SWORD;
    public static Item MAGMA_CREAM_PICKAXE;
    public static Item MAGMA_CREAM_SHOVEL;
    public static Item MAGMA_CREAM_AXE;
    public static Item MAGMA_CREAM_HOE;
    public static Item MAGMA_CREAM_HELMET;
    public static Item MAGMA_CREAM_CHESTPLATE;
    public static Item MAGMA_CREAM_LEGGINGS;
    public static Item MAGMA_CREAM_BOOTS;
    public static Item SLIME_SWORD;
    public static Item SLIME_PICKAXE;
    public static Item SLIME_SHOVEL;
    public static Item SLIME_AXE;
    public static Item SLIME_HOE;
    public static Item SLIME_HELMET;
    public static Item SLIME_CHESTPLATE;
    public static Item SLIME_LEGGINGS;
    public static Item SLIME_BOOTS;
    public static Item BLAZE_SWORD;
    public static Item BLAZE_PICKAXE;
    public static Item BLAZE_SHOVEL;
    public static Item BLAZE_AXE;
    public static Item BLAZE_HOE;
    public static Item BLAZE_HELMET;
    public static Item BLAZE_CHESTPLATE;
    public static Item BLAZE_LEGGINGS;
    public static Item BLAZE_BOOTS;
    public static Item NAUTILUS_SWORD;
    public static Item NAUTILUS_PICKAXE;
    public static Item NAUTILUS_SHOVEL;
    public static Item NAUTILUS_AXE;
    public static Item NAUTILUS_HOE;
    public static Item NAUTILUS_HELMET;
    public static Item NAUTILUS_CHESTPLATE;
    public static Item NAUTILUS_LEGGINGS;
    public static Item NAUTILUS_BOOTS;
    public static Item PURPUR_SWORD;
    public static Item PURPUR_PICKAXE;
    public static Item PURPUR_SHOVEL;
    public static Item PURPUR_AXE;
    public static Item PURPUR_HOE;
    public static Item PURPUR_HELMET;
    public static Item PURPUR_CHESTPLATE;
    public static Item PURPUR_LEGGINGS;
    public static Item PURPUR_BOOTS;
    public static Item GHAST_TEAR_SWORD;
    public static Item GHAST_TEAR_PICKAXE;
    public static Item GHAST_TEAR_SHOVEL;
    public static Item GHAST_TEAR_AXE;
    public static Item GHAST_TEAR_HOE;
    public static Item GHAST_TEAR_HELMET;
    public static Item GHAST_TEAR_CHESTPLATE;
    public static Item GHAST_TEAR_LEGGINGS;
    public static Item GHAST_TEAR_BOOTS;
    public static Item EYE_OF_ENDER_SWORD;
    public static Item EYE_OF_ENDER_PICKAXE;
    public static Item EYE_OF_ENDER_SHOVEL;
    public static Item EYE_OF_ENDER_AXE;
    public static Item EYE_OF_ENDER_HOE;
    public static Item EYE_OF_ENDER_HELMET;
    public static Item EYE_OF_ENDER_CHESTPLATE;
    public static Item EYE_OF_ENDER_LEGGINGS;
    public static Item EYE_OF_ENDER_BOOTS;
    public static Item SHULKER_SWORD;
    public static Item SHULKER_PICKAXE;
    public static Item SHULKER_SHOVEL;
    public static Item SHULKER_AXE;
    public static Item SHULKER_HOE;
    public static Item SHULKER_HELMET;
    public static Item SHULKER_CHESTPLATE;
    public static Item SHULKER_LEGGINGS;
    public static Item SHULKER_BOOTS;
    public static Item TURTLE_SCUTE_HELMET;
    public static Item TURTLE_SCUTE_CHESTPLATE;
    public static Item TURTLE_SCUTE_LEGGINGS;
    public static Item TURTLE_SCUTE_BOOTS;
    public static Item ECHO_SHARD_SWORD;
    public static Item ECHO_SHARD_PICKAXE;
    public static Item ECHO_SHARD_SHOVEL;
    public static Item ECHO_SHARD_AXE;
    public static Item ECHO_SHARD_HOE;
    public static Item ECHO_SHARD_HELMET;
    public static Item ECHO_SHARD_CHESTPLATE;
    public static Item ECHO_SHARD_LEGGINGS;
    public static Item ECHO_SHARD_BOOTS;
    public static Item DRAGON_BREATH_SWORD;
    public static Item DRAGON_BREATH_PICKAXE;
    public static Item DRAGON_BREATH_SHOVEL;
    public static Item DRAGON_BREATH_AXE;
    public static Item DRAGON_BREATH_HOE;
    public static Item DRAGON_BREATH_HELMET;
    public static Item DRAGON_BREATH_CHESTPLATE;
    public static Item DRAGON_BREATH_LEGGINGS;
    public static Item DRAGON_BREATH_BOOTS;
    public static Item CAKE_SWORD;
    public static Item CAKE_PICKAXE;
    public static Item CAKE_SHOVEL;
    public static Item CAKE_AXE;
    public static Item CAKE_HOE;
    public static Item CAKE_HELMET;
    public static Item CAKE_CHESTPLATE;
    public static Item CAKE_LEGGINGS;
    public static Item CAKE_BOOTS;
    public static Item BREAD_SWORD;
    public static Item BREAD_PICKAXE;
    public static Item BREAD_SHOVEL;
    public static Item BREAD_AXE;
    public static Item BREAD_HOE;
    public static Item BREAD_HELMET;
    public static Item BREAD_CHESTPLATE;
    public static Item BREAD_LEGGINGS;
    public static Item BREAD_BOOTS;
    public static Item DRIED_KELP_SWORD;
    public static Item DRIED_KELP_PICKAXE;
    public static Item DRIED_KELP_SHOVEL;
    public static Item DRIED_KELP_AXE;
    public static Item DRIED_KELP_HOE;
    public static Item DRIED_KELP_HELMET;
    public static Item DRIED_KELP_CHESTPLATE;
    public static Item DRIED_KELP_LEGGINGS;
    public static Item DRIED_KELP_BOOTS;
    public static Item ROTTEN_FLESH_SWORD;
    public static Item ROTTEN_FLESH_PICKAXE;
    public static Item ROTTEN_FLESH_SHOVEL;
    public static Item ROTTEN_FLESH_AXE;
    public static Item ROTTEN_FLESH_HOE;
    public static Item ROTTEN_FLESH_HELMET;
    public static Item ROTTEN_FLESH_CHESTPLATE;
    public static Item ROTTEN_FLESH_LEGGINGS;
    public static Item ROTTEN_FLESH_BOOTS;
    public static Item MELON_SWORD;
    public static Item MELON_PICKAXE;
    public static Item MELON_SHOVEL;
    public static Item MELON_AXE;
    public static Item MELON_HOE;
    public static Item MELON_HELMET;
    public static Item MELON_CHESTPLATE;
    public static Item MELON_LEGGINGS;
    public static Item MELON_BOOTS;
    public static Item SWEET_BERRY_SWORD;
    public static Item SWEET_BERRY_PICKAXE;
    public static Item SWEET_BERRY_SHOVEL;
    public static Item SWEET_BERRY_AXE;
    public static Item SWEET_BERRY_HOE;
    public static Item SWEET_BERRY_HELMET;
    public static Item SWEET_BERRY_CHESTPLATE;
    public static Item SWEET_BERRY_LEGGINGS;
    public static Item SWEET_BERRY_BOOTS;
    public static Item PUMPKIN_PIE_SWORD;
    public static Item PUMPKIN_PIE_PICKAXE;
    public static Item PUMPKIN_PIE_SHOVEL;
    public static Item PUMPKIN_PIE_AXE;
    public static Item PUMPKIN_PIE_HOE;
    public static Item PUMPKIN_PIE_HELMET;
    public static Item PUMPKIN_PIE_CHESTPLATE;
    public static Item PUMPKIN_PIE_LEGGINGS;
    public static Item PUMPKIN_PIE_BOOTS;
    public static Item MUSHROOM_SWORD;
    public static Item MUSHROOM_PICKAXE;
    public static Item MUSHROOM_SHOVEL;
    public static Item MUSHROOM_AXE;
    public static Item MUSHROOM_HOE;
    public static Item MUSHROOM_HELMET;
    public static Item MUSHROOM_CHESTPLATE;
    public static Item MUSHROOM_LEGGINGS;
    public static Item MUSHROOM_BOOTS;
    public static Item PUFFERFISH_SWORD;
    public static Item PUFFERFISH_PICKAXE;
    public static Item PUFFERFISH_SHOVEL;
    public static Item PUFFERFISH_AXE;
    public static Item PUFFERFISH_HOE;
    public static Item PUFFERFISH_HELMET;
    public static Item PUFFERFISH_CHESTPLATE;
    public static Item PUFFERFISH_LEGGINGS;
    public static Item PUFFERFISH_BOOTS;
    public static Item HONEY_SWORD;
    public static Item HONEY_PICKAXE;
    public static Item HONEY_SHOVEL;
    public static Item HONEY_AXE;
    public static Item HONEY_HOE;
    public static Item HONEY_HELMET;
    public static Item HONEY_CHESTPLATE;
    public static Item HONEY_LEGGINGS;
    public static Item HONEY_BOOTS;
    public static Item CHORUS_FRUIT_SWORD;
    public static Item CHORUS_FRUIT_PICKAXE;
    public static Item CHORUS_FRUIT_SHOVEL;
    public static Item CHORUS_FRUIT_AXE;
    public static Item CHORUS_FRUIT_HOE;
    public static Item CHORUS_FRUIT_HELMET;
    public static Item CHORUS_FRUIT_CHESTPLATE;
    public static Item CHORUS_FRUIT_LEGGINGS;
    public static Item CHORUS_FRUIT_BOOTS;
    public static Item GOLDEN_APPLE_SWORD;
    public static Item GOLDEN_APPLE_PICKAXE;
    public static Item GOLDEN_APPLE_SHOVEL;
    public static Item GOLDEN_APPLE_AXE;
    public static Item GOLDEN_APPLE_HOE;
    public static Item GOLDEN_APPLE_HELMET;
    public static Item GOLDEN_APPLE_CHESTPLATE;
    public static Item GOLDEN_APPLE_LEGGINGS;
    public static Item GOLDEN_APPLE_BOOTS;

    public static void register() {
        RGOLD = reg(GeneratedRegistrationIds.Items.RGOLD, new Item(new Item.Settings().maxCount(64)));
        RAW_RGOLD = reg(GeneratedRegistrationIds.Items.RAW_RGOLD, new Item(new Item.Settings().maxCount(64)));
        OBSHARD = reg(GeneratedRegistrationIds.Items.OBSHARD, new Item(new Item.Settings().maxCount(64)));
        SEM = reg(GeneratedRegistrationIds.Items.SEM, new Item(new Item.Settings().maxCount(64)));
        OBINGOT = reg(GeneratedRegistrationIds.Items.OBINGOT, new Item(new Item.Settings().maxCount(64)));
        GRENADE = reg(GeneratedRegistrationIds.Items.GRENADE, new Grenade(new Item.Settings().maxCount(16)));
        HRED = reg(GeneratedRegistrationIds.Items.HRED, new Item(new Item.Settings().maxCount(64)));
        HGLOW = reg(GeneratedRegistrationIds.Items.HGLOW, new Item(new Item.Settings().maxCount(64)));
        RLAPIS = reg(GeneratedRegistrationIds.Items.RLAPIS, new Item(new Item.Settings().maxCount(64)));
        DYNAMITE = reg(GeneratedRegistrationIds.Items.DYNAMITE, new Dynamite(new Item.Settings()
                            .maxCount(16)
                            .fireproof()  // optional
                    ));
        REMERALD_SWORD = reg(GeneratedRegistrationIds.Items.REMERALD_SWORD, new SwordItem(ModToolTiers.REMERALD, 3, -2.4f, new Item.Settings()));
        REMERALD_PICKAXE = reg(GeneratedRegistrationIds.Items.REMERALD_PICKAXE, new PickaxeItem(ModToolTiers.REMERALD, 1, -2.8f, new Item.Settings()));
        REMERALD_SHOVEL = reg(GeneratedRegistrationIds.Items.REMERALD_SHOVEL, new ShovelItem(ModToolTiers.REMERALD, 1.5f, -3f, new Item.Settings()));
        REMERALD_AXE = reg(GeneratedRegistrationIds.Items.REMERALD_AXE, new AxeItem(ModToolTiers.REMERALD, 6, -3.2f, new Item.Settings()));
        REMERALD_HOE = reg(GeneratedRegistrationIds.Items.REMERALD_HOE, new HoeItem(ModToolTiers.REMERALD, 0, -3f, new Item.Settings()));
        PEMERALD_SWORD = reg(GeneratedRegistrationIds.Items.PEMERALD_SWORD, new SwordItem(ModToolTiers.PEMERALD, 3, -2.4f, new Item.Settings()));
        PEMERALD_PICKAXE = reg(GeneratedRegistrationIds.Items.PEMERALD_PICKAXE, new PickaxeItem(ModToolTiers.PEMERALD, 1, -2.8f, new Item.Settings()));
        PEMERALD_SHOVEL = reg(GeneratedRegistrationIds.Items.PEMERALD_SHOVEL, new ShovelItem(ModToolTiers.PEMERALD, 1.5f, -3f, new Item.Settings()));
        PEMERALD_AXE = reg(GeneratedRegistrationIds.Items.PEMERALD_AXE, new AxeItem(ModToolTiers.PEMERALD, 6, -3.2f, new Item.Settings()));
        PEMERALD_HOE = reg(GeneratedRegistrationIds.Items.PEMERALD_HOE, new HoeItem(ModToolTiers.PEMERALD, 0, -3f, new Item.Settings()));
        ROBSIDIAN_SWORD = reg(GeneratedRegistrationIds.Items.ROBSIDIAN_SWORD, new SwordItem(ModToolTiers.ROBSIDIAN, 3, -2.4f, new Item.Settings()));
        ROBSIDIAN_PICKAXE = reg(GeneratedRegistrationIds.Items.ROBSIDIAN_PICKAXE, new PickaxeItem(ModToolTiers.ROBSIDIAN, 1, -2.8f, new Item.Settings()));
        ROBSIDIAN_SHOVEL = reg(GeneratedRegistrationIds.Items.ROBSIDIAN_SHOVEL, new ShovelItem(ModToolTiers.ROBSIDIAN, 1.5f, -3f, new Item.Settings()));
        ROBSIDIAN_AXE = reg(GeneratedRegistrationIds.Items.ROBSIDIAN_AXE, new AxeItem(ModToolTiers.ROBSIDIAN, 6, -3.2f, new Item.Settings()));
        ROBSIDIAN_HOE = reg(GeneratedRegistrationIds.Items.ROBSIDIAN_HOE, new HoeItem(ModToolTiers.ROBSIDIAN, 0, -3f, new Item.Settings()));
        POBSIDIAN_SWORD = reg(GeneratedRegistrationIds.Items.POBSIDIAN_SWORD, new SwordItem(ModToolTiers.POBSIDIAN, 3, -2.4f, new Item.Settings()));
        POBSIDIAN_PICKAXE = reg(GeneratedRegistrationIds.Items.POBSIDIAN_PICKAXE, new PickaxeItem(ModToolTiers.POBSIDIAN, 1, -2.8f, new Item.Settings()));
        POBSIDIAN_SHOVEL = reg(GeneratedRegistrationIds.Items.POBSIDIAN_SHOVEL, new ShovelItem(ModToolTiers.POBSIDIAN, 1.5f, -3f, new Item.Settings()));
        POBSIDIAN_AXE = reg(GeneratedRegistrationIds.Items.POBSIDIAN_AXE, new AxeItem(ModToolTiers.POBSIDIAN, 6, -3.2f, new Item.Settings()));
        POBSIDIAN_HOE = reg(GeneratedRegistrationIds.Items.POBSIDIAN_HOE, new HoeItem(ModToolTiers.POBSIDIAN, 0, -3f, new Item.Settings()));
        OVERPOWER_SWORD = reg(GeneratedRegistrationIds.Items.OVERPOWER_SWORD, new SwordItem(ModToolTiers.OVERPOWER, 3, -2.4f, new Item.Settings()));
        OVERPOWER_PICKAXE = reg(GeneratedRegistrationIds.Items.OVERPOWER_PICKAXE, new PickaxeItem(ModToolTiers.OVERPOWER, 1, -2.8f, new Item.Settings()));
        OVERPOWER_SHOVEL = reg(GeneratedRegistrationIds.Items.OVERPOWER_SHOVEL, new ShovelItem(ModToolTiers.OVERPOWER, 1.5f, -3f, new Item.Settings()));
        OVERPOWER_AXE = reg(GeneratedRegistrationIds.Items.OVERPOWER_AXE, new AxeItem(ModToolTiers.OVERPOWER, 6, -3.2f, new Item.Settings()));
        HREDSTONE_SWORD = reg(GeneratedRegistrationIds.Items.HREDSTONE_SWORD, new SwordItem(ModToolTiers.HREDSTONE, 3, -2.4f, new Item.Settings()));
        HREDSTONE_PICKAXE = reg(GeneratedRegistrationIds.Items.HREDSTONE_PICKAXE, new PickaxeItem(ModToolTiers.HREDSTONE, 1, -2.8f, new Item.Settings()));
        HREDSTONE_SHOVEL = reg(GeneratedRegistrationIds.Items.HREDSTONE_SHOVEL, new ShovelItem(ModToolTiers.HREDSTONE, 1.5f, -3f, new Item.Settings()));
        HREDSTONE_AXE = reg(GeneratedRegistrationIds.Items.HREDSTONE_AXE, new AxeItem(ModToolTiers.HREDSTONE, 6, -3.2f, new Item.Settings()));
        HREDSTONE_HOE = reg(GeneratedRegistrationIds.Items.HREDSTONE_HOE, new HoeItem(ModToolTiers.HREDSTONE, 0, -3f, new Item.Settings()));
        HGLOWSTONE_SWORD = reg(GeneratedRegistrationIds.Items.HGLOWSTONE_SWORD, new SwordItem(ModToolTiers.HGLOWSTONE, 3, -2.4f, new Item.Settings()));
        HGLOWSTONE_PICKAXE = reg(GeneratedRegistrationIds.Items.HGLOWSTONE_PICKAXE, new PickaxeItem(ModToolTiers.HGLOWSTONE, 1, -2.8f, new Item.Settings()));
        HGLOWSTONE_SHOVEL = reg(GeneratedRegistrationIds.Items.HGLOWSTONE_SHOVEL, new ShovelItem(ModToolTiers.HGLOWSTONE, 1.5f, -3f, new Item.Settings()));
        HGLOWSTONE_AXE = reg(GeneratedRegistrationIds.Items.HGLOWSTONE_AXE, new AxeItem(ModToolTiers.HGLOWSTONE, 6, -3.2f, new Item.Settings()));
        HGLOWSTONE_HOE = reg(GeneratedRegistrationIds.Items.HGLOWSTONE_HOE, new HoeItem(ModToolTiers.HGLOWSTONE, 0, -3f, new Item.Settings()));
        RGOLD_SWORD = reg(GeneratedRegistrationIds.Items.RGOLD_SWORD, new SwordItem(ModToolTiers.RGOLD, 3, -2.4f, new Item.Settings()));
        RGOLD_PICKAXE = reg(GeneratedRegistrationIds.Items.RGOLD_PICKAXE, new PickaxeItem(ModToolTiers.RGOLD, 1, -2.8f, new Item.Settings()));
        RGOLD_SHOVEL = reg(GeneratedRegistrationIds.Items.RGOLD_SHOVEL, new ShovelItem(ModToolTiers.RGOLD, 1.5f, -3f, new Item.Settings()));
        RGOLD_AXE = reg(GeneratedRegistrationIds.Items.RGOLD_AXE, new AxeItem(ModToolTiers.RGOLD, 6, -3.2f, new Item.Settings()));
        RGOLD_HOE = reg(GeneratedRegistrationIds.Items.RGOLD_HOE, new HoeItem(ModToolTiers.RGOLD, 0, -3f, new Item.Settings()));
        RLAPIS_SWORD = reg(GeneratedRegistrationIds.Items.RLAPIS_SWORD, new SwordItem(ModToolTiers.RLAPIS, 3, -2.4f, new Item.Settings()));
        RLAPIS_PICKAXE = reg(GeneratedRegistrationIds.Items.RLAPIS_PICKAXE, new PickaxeItem(ModToolTiers.RLAPIS, 1, -2.8f, new Item.Settings()));
        RLAPIS_SHOVEL = reg(GeneratedRegistrationIds.Items.RLAPIS_SHOVEL, new ShovelItem(ModToolTiers.RLAPIS, 1.5f, -3f, new Item.Settings()));
        RLAPIS_AXE = reg(GeneratedRegistrationIds.Items.RLAPIS_AXE, new AxeItem(ModToolTiers.RLAPIS, 6, -3.2f, new Item.Settings()));
        RLAPIS_HOE = reg(GeneratedRegistrationIds.Items.RLAPIS_HOE, new HoeItem(ModToolTiers.RLAPIS, 0, -3f, new Item.Settings()));
        EMERALD_HELMET = reg(GeneratedRegistrationIds.Items.EMERALD_HELMET, new ArmorItem(ModArmorMaterials.EMERALD, ArmorItem.Type.HELMET,
                    new Item.Settings()));
        EMERALD_CHESTPLATE = reg(GeneratedRegistrationIds.Items.EMERALD_CHESTPLATE, new ArmorItem(ModArmorMaterials.EMERALD, ArmorItem.Type.CHESTPLATE,
                    new Item.Settings()));
        EMERALD_LEGGINGS = reg(GeneratedRegistrationIds.Items.EMERALD_LEGGINGS, new ArmorItem(ModArmorMaterials.EMERALD, ArmorItem.Type.LEGGINGS,
                    new Item.Settings()));
        EMERALD_BOOTS = reg(GeneratedRegistrationIds.Items.EMERALD_BOOTS, new ArmorItem(ModArmorMaterials.EMERALD, ArmorItem.Type.BOOTS,
                    new Item.Settings()));
        HRED_HELMET = reg(GeneratedRegistrationIds.Items.HRED_HELMET, new ArmorItem(ModArmorMaterials.HRED, ArmorItem.Type.HELMET,
                    new Item.Settings()));
        HRED_CHESTPLATE = reg(GeneratedRegistrationIds.Items.HRED_CHESTPLATE, new ArmorItem(ModArmorMaterials.HRED, ArmorItem.Type.CHESTPLATE,
                    new Item.Settings()));
        HRED_LEGGINGS = reg(GeneratedRegistrationIds.Items.HRED_LEGGINGS, new ArmorItem(ModArmorMaterials.HRED, ArmorItem.Type.LEGGINGS,
                    new Item.Settings()));
        HRED_BOOTS = reg(GeneratedRegistrationIds.Items.HRED_BOOTS, new ArmorItem(ModArmorMaterials.HRED, ArmorItem.Type.BOOTS,
                    new Item.Settings()));
        HGLOW_HELMET = reg(GeneratedRegistrationIds.Items.HGLOW_HELMET, new ArmorItem(ModArmorMaterials.HGLOW, ArmorItem.Type.HELMET,
                    new Item.Settings()));
        HGLOW_CHESTPLATE = reg(GeneratedRegistrationIds.Items.HGLOW_CHESTPLATE, new ArmorItem(ModArmorMaterials.HGLOW, ArmorItem.Type.CHESTPLATE,
                    new Item.Settings()));
        HGLOW_LEGGINGS = reg(GeneratedRegistrationIds.Items.HGLOW_LEGGINGS, new ArmorItem(ModArmorMaterials.HGLOW, ArmorItem.Type.LEGGINGS,
                    new Item.Settings()));
        HGLOW_BOOTS = reg(GeneratedRegistrationIds.Items.HGLOW_BOOTS, new ArmorItem(ModArmorMaterials.HGLOW, ArmorItem.Type.BOOTS,
                    new Item.Settings()));
        OBSIDIAN_HELMET = reg(GeneratedRegistrationIds.Items.OBSIDIAN_HELMET, new ArmorItem(ModArmorMaterials.OBSIDIAN, ArmorItem.Type.HELMET,
                    new Item.Settings()));
        OBSIDIAN_CHESTPLATE = reg(GeneratedRegistrationIds.Items.OBSIDIAN_CHESTPLATE, new ArmorItem(ModArmorMaterials.OBSIDIAN, ArmorItem.Type.CHESTPLATE,
                    new Item.Settings()));
        OBSIDIAN_LEGGINGS = reg(GeneratedRegistrationIds.Items.OBSIDIAN_LEGGINGS, new ArmorItem(ModArmorMaterials.OBSIDIAN, ArmorItem.Type.LEGGINGS,
                    new Item.Settings()));
        OBSIDIAN_BOOTS = reg(GeneratedRegistrationIds.Items.OBSIDIAN_BOOTS, new ArmorItem(ModArmorMaterials.OBSIDIAN, ArmorItem.Type.BOOTS,
                    new Item.Settings()));
        RGOLD_HELMET = reg(GeneratedRegistrationIds.Items.RGOLD_HELMET, new ArmorItem(ModArmorMaterials.RGOLD, ArmorItem.Type.HELMET,
                    new Item.Settings()));
        RGOLD_CHESTPLATE = reg(GeneratedRegistrationIds.Items.RGOLD_CHESTPLATE, new ArmorItem(ModArmorMaterials.RGOLD, ArmorItem.Type.CHESTPLATE,
                    new Item.Settings()));
        RGOLD_LEGGINGS = reg(GeneratedRegistrationIds.Items.RGOLD_LEGGINGS, new ArmorItem(ModArmorMaterials.RGOLD, ArmorItem.Type.LEGGINGS,
                    new Item.Settings()));
        RGOLD_BOOTS = reg(GeneratedRegistrationIds.Items.RGOLD_BOOTS, new ArmorItem(ModArmorMaterials.RGOLD, ArmorItem.Type.BOOTS,
                    new Item.Settings()));
        RLAPIS_HELMET = reg(GeneratedRegistrationIds.Items.RLAPIS_HELMET, new ArmorItem(ModArmorMaterials.RLAPIS, ArmorItem.Type.HELMET,
                    new Item.Settings()));
        RLAPIS_CHESTPLATE = reg(GeneratedRegistrationIds.Items.RLAPIS_CHESTPLATE, new ArmorItem(ModArmorMaterials.RLAPIS, ArmorItem.Type.CHESTPLATE,
                    new Item.Settings()));
        RLAPIS_LEGGINGS = reg(GeneratedRegistrationIds.Items.RLAPIS_LEGGINGS, new ArmorItem(ModArmorMaterials.RLAPIS, ArmorItem.Type.LEGGINGS,
                    new Item.Settings()));
        RLAPIS_BOOTS = reg(GeneratedRegistrationIds.Items.RLAPIS_BOOTS, new ArmorItem(ModArmorMaterials.RLAPIS, ArmorItem.Type.BOOTS,
                    new Item.Settings()));
        OVERPOWER_HELMET = reg(GeneratedRegistrationIds.Items.OVERPOWER_HELMET, new ModArmorItem(ModArmorMaterials.OVERPOWER, ArmorItem.Type.HELMET,
                    new Item.Settings()));
        OVERPOWER_CHESTPLATE = reg(GeneratedRegistrationIds.Items.OVERPOWER_CHESTPLATE, new ArmorItem(ModArmorMaterials.OVERPOWER, ArmorItem.Type.CHESTPLATE,
                    new Item.Settings()));
        OVERPOWER_LEGGINGS = reg(GeneratedRegistrationIds.Items.OVERPOWER_LEGGINGS, new ArmorItem(ModArmorMaterials.OVERPOWER, ArmorItem.Type.LEGGINGS,
                    new Item.Settings()));
        OVERPOWER_BOOTS = reg(GeneratedRegistrationIds.Items.OVERPOWER_BOOTS, new ArmorItem(ModArmorMaterials.OVERPOWER, ArmorItem.Type.BOOTS,
                    new Item.Settings()));
        GHOST_SPAWN_EGG = reg(GeneratedRegistrationIds.Items.GHOST_SPAWN_EGG, new SpawnEggItem(ModEntities.GHOST, 0xFFFFFF, 0x999999, new Item.Settings()));
        ECTOPLASM = reg(GeneratedRegistrationIds.Items.ECTOPLASM, new Item(new Item.Settings().maxCount(64)));
        CONDENSED_ECTOPLASM = reg(GeneratedRegistrationIds.Items.CONDENSED_ECTOPLASM, new Item(new Item.Settings().maxCount(64)));
        WRAITH_SPAWN_EGG = reg(GeneratedRegistrationIds.Items.WRAITH_SPAWN_EGG, new SpawnEggItem(ModEntities.WRAITH, 0x241833, 0xA95CFF, new Item.Settings()));
        SPECTRAL_RESONATOR = reg(GeneratedRegistrationIds.Items.SPECTRAL_RESONATOR, new SpectralResonatorItem(new Item.Settings().maxDamage(128)));
        STICKY_DYNAMITE = reg(GeneratedRegistrationIds.Items.STICKY_DYNAMITE, new StickyDynamiteItem(new Item.Settings().maxCount(16)));
        REMOTE_DETONATOR = reg(GeneratedRegistrationIds.Items.REMOTE_DETONATOR, new RemoteDetonatorItem(new Item.Settings().maxCount(1)));
        RECTO_SWORD = reg(GeneratedRegistrationIds.Items.RECTO_SWORD, new EctoSwordItem(ModToolTiers.RECTO, new Item.Settings()));
        RECTO_PICKAXE = reg(GeneratedRegistrationIds.Items.RECTO_PICKAXE, new EctoPickaxeItem(ModToolTiers.RECTO, new Item.Settings()));
        RECTO_SHOVEL = reg(GeneratedRegistrationIds.Items.RECTO_SHOVEL, new EctoShovelItem(ModToolTiers.RECTO, 1.5f, -3f, new Item.Settings()));
        RECTO_AXE = reg(GeneratedRegistrationIds.Items.RECTO_AXE, new EctoAxeItem(ModToolTiers.RECTO, new Item.Settings()));
        RECTO_HOE = reg(GeneratedRegistrationIds.Items.RECTO_HOE, new EctoHoeItem(ModToolTiers.RECTO, 0, -3f, new Item.Settings()));
        REFINED_ECTOPLASM = reg(GeneratedRegistrationIds.Items.REFINED_ECTOPLASM, new Item(new Item.Settings().maxCount(64)));
        ECTO_SWORD = reg(GeneratedRegistrationIds.Items.ECTO_SWORD, new EctoSwordItem(ModToolTiers.ECTOPLASM, new Item.Settings()));
        ECTO_PICKAXE = reg(GeneratedRegistrationIds.Items.ECTO_PICKAXE, new EctoPickaxeItem(ModToolTiers.ECTOPLASM, new Item.Settings()));
        ECTO_SHOVEL = reg(GeneratedRegistrationIds.Items.ECTO_SHOVEL, new EctoShovelItem(ModToolTiers.ECTOPLASM, 1.5f, -3f, new Item.Settings()));
        ECTO_AXE = reg(GeneratedRegistrationIds.Items.ECTO_AXE, new EctoAxeItem(ModToolTiers.ECTOPLASM, new Item.Settings()));
        ECTO_HOE = reg(GeneratedRegistrationIds.Items.ECTO_HOE, new EctoHoeItem(ModToolTiers.ECTOPLASM, 0, -3f, new Item.Settings()));
        ECTO_HELMET = reg(GeneratedRegistrationIds.Items.ECTO_HELMET, new ArmorItem(ModArmorMaterials.ECTO, ArmorItem.Type.HELMET,
                    new Item.Settings()));
        ECTO_CHESTPLATE = reg(GeneratedRegistrationIds.Items.ECTO_CHESTPLATE, new ArmorItem(ModArmorMaterials.ECTO, ArmorItem.Type.CHESTPLATE,
                    new Item.Settings()));
        ECTO_LEGGINGS = reg(GeneratedRegistrationIds.Items.ECTO_LEGGINGS, new ArmorItem(ModArmorMaterials.ECTO, ArmorItem.Type.LEGGINGS,
                    new Item.Settings()));
        ECTO_BOOTS = reg(GeneratedRegistrationIds.Items.ECTO_BOOTS, new ArmorItem(ModArmorMaterials.ECTO, ArmorItem.Type.BOOTS,
                    new Item.Settings()));
        COAL_DUST = reg(GeneratedRegistrationIds.Items.COAL_DUST, new Item(new Item.Settings().maxCount(64)));
        HARDENED_COAL = reg(GeneratedRegistrationIds.Items.HARDENED_COAL, new Item(new Item.Settings().maxCount(64)));
        COAL_SWORD = reg(GeneratedRegistrationIds.Items.COAL_SWORD, new CoalSwordItem(ModToolTiers.COAL_TOOL, 2, -2.4f, new Item.Settings()));
        COAL_PICKAXE = reg(GeneratedRegistrationIds.Items.COAL_PICKAXE, new CoalPickaxeItem(ModToolTiers.COAL_TOOL, 1, -2.8f, new Item.Settings()));
        COAL_SHOVEL = reg(GeneratedRegistrationIds.Items.COAL_SHOVEL, new CoalShovelItem(ModToolTiers.COAL_TOOL, 1.5f, -3f, new Item.Settings()));
        COAL_AXE = reg(GeneratedRegistrationIds.Items.COAL_AXE, new CoalAxeItem(ModToolTiers.COAL_TOOL, 5, -3.2f, new Item.Settings()));
        COAL_HOE = reg(GeneratedRegistrationIds.Items.COAL_HOE, new CoalHoeItem(ModToolTiers.COAL_TOOL, 0, -3f, new Item.Settings()));
        COAL_HELMET = reg(GeneratedRegistrationIds.Items.COAL_HELMET, new CoalArmorItem(ModArmorMaterials.COAL, ArmorItem.Type.HELMET,
                    new Item.Settings()));
        COAL_CHESTPLATE = reg(GeneratedRegistrationIds.Items.COAL_CHESTPLATE, new CoalArmorItem(ModArmorMaterials.COAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Settings()));
        COAL_LEGGINGS = reg(GeneratedRegistrationIds.Items.COAL_LEGGINGS, new CoalArmorItem(ModArmorMaterials.COAL, ArmorItem.Type.LEGGINGS,
                    new Item.Settings()));
        COAL_BOOTS = reg(GeneratedRegistrationIds.Items.COAL_BOOTS, new CoalArmorItem(ModArmorMaterials.COAL, ArmorItem.Type.BOOTS,
                    new Item.Settings()));
        RRAW_GOLD_SWORD = reg(GeneratedRegistrationIds.Items.RRAW_GOLD_SWORD, new SwordItem(ModToolTiers.RRAW_GOLD, 3, -2.4f, new Item.Settings()));
        RRAW_GOLD_PICKAXE = reg(GeneratedRegistrationIds.Items.RRAW_GOLD_PICKAXE, new PickaxeItem(ModToolTiers.RRAW_GOLD, 1, -2.8f, new Item.Settings()));
        RRAW_GOLD_SHOVEL = reg(GeneratedRegistrationIds.Items.RRAW_GOLD_SHOVEL, new ShovelItem(ModToolTiers.RRAW_GOLD, 1.5f, -3f, new Item.Settings()));
        RRAW_GOLD_AXE = reg(GeneratedRegistrationIds.Items.RRAW_GOLD_AXE, new AxeItem(ModToolTiers.RRAW_GOLD, 6, -3.2f, new Item.Settings()));
        RRAW_GOLD_HOE = reg(GeneratedRegistrationIds.Items.RRAW_GOLD_HOE, new HoeItem(ModToolTiers.RRAW_GOLD, 0, -3f, new Item.Settings()));
        RRAW_COPPER_SWORD = reg(GeneratedRegistrationIds.Items.RRAW_COPPER_SWORD, new SwordItem(ModToolTiers.RRAW_COPPER, 3, -2.4f, new Item.Settings()));
        RRAW_COPPER_PICKAXE = reg(GeneratedRegistrationIds.Items.RRAW_COPPER_PICKAXE, new PickaxeItem(ModToolTiers.RRAW_COPPER, 1, -2.8f, new Item.Settings()));
        RRAW_COPPER_SHOVEL = reg(GeneratedRegistrationIds.Items.RRAW_COPPER_SHOVEL, new ShovelItem(ModToolTiers.RRAW_COPPER, 1.5f, -3f, new Item.Settings()));
        RRAW_COPPER_AXE = reg(GeneratedRegistrationIds.Items.RRAW_COPPER_AXE, new AxeItem(ModToolTiers.RRAW_COPPER, 6, -3.2f, new Item.Settings()));
        RRAW_COPPER_HOE = reg(GeneratedRegistrationIds.Items.RRAW_COPPER_HOE, new HoeItem(ModToolTiers.RRAW_COPPER, 0, -3f, new Item.Settings()));
        RRAW_IRON_SWORD = reg(GeneratedRegistrationIds.Items.RRAW_IRON_SWORD, new SwordItem(ModToolTiers.RRAW_IRON, 3, -2.4f, new Item.Settings()));
        RRAW_IRON_PICKAXE = reg(GeneratedRegistrationIds.Items.RRAW_IRON_PICKAXE, new PickaxeItem(ModToolTiers.RRAW_IRON, 1, -2.8f, new Item.Settings()));
        RRAW_IRON_SHOVEL = reg(GeneratedRegistrationIds.Items.RRAW_IRON_SHOVEL, new ShovelItem(ModToolTiers.RRAW_IRON, 1.5f, -3f, new Item.Settings()));
        RRAW_IRON_AXE = reg(GeneratedRegistrationIds.Items.RRAW_IRON_AXE, new AxeItem(ModToolTiers.RRAW_IRON, 6, -3.2f, new Item.Settings()));
        RRAW_IRON_HOE = reg(GeneratedRegistrationIds.Items.RRAW_IRON_HOE, new HoeItem(ModToolTiers.RRAW_IRON, 0, -3f, new Item.Settings()));
        RRAW_RGOLD_SWORD = reg(GeneratedRegistrationIds.Items.RRAW_RGOLD_SWORD, new SwordItem(ModToolTiers.RRAW_RGOLD, 3, -2.4f, new Item.Settings()));
        RRAW_RGOLD_PICKAXE = reg(GeneratedRegistrationIds.Items.RRAW_RGOLD_PICKAXE, new PickaxeItem(ModToolTiers.RRAW_RGOLD, 1, -2.8f, new Item.Settings()));
        RRAW_RGOLD_SHOVEL = reg(GeneratedRegistrationIds.Items.RRAW_RGOLD_SHOVEL, new ShovelItem(ModToolTiers.RRAW_RGOLD, 1.5f, -3f, new Item.Settings()));
        RRAW_RGOLD_AXE = reg(GeneratedRegistrationIds.Items.RRAW_RGOLD_AXE, new AxeItem(ModToolTiers.RRAW_RGOLD, 6, -3.2f, new Item.Settings()));
        RRAW_RGOLD_HOE = reg(GeneratedRegistrationIds.Items.RRAW_RGOLD_HOE, new HoeItem(ModToolTiers.RRAW_RGOLD, 0, -3f, new Item.Settings()));
        RSCRAP_SWORD = reg(GeneratedRegistrationIds.Items.RSCRAP_SWORD, new SwordItem(ModToolTiers.RSCRAP, 3, -2.4f, new Item.Settings()));
        RSCRAP_PICKAXE = reg(GeneratedRegistrationIds.Items.RSCRAP_PICKAXE, new PickaxeItem(ModToolTiers.RSCRAP, 1, -2.8f, new Item.Settings()));
        RSCRAP_SHOVEL = reg(GeneratedRegistrationIds.Items.RSCRAP_SHOVEL, new ShovelItem(ModToolTiers.RSCRAP, 1.5f, -3f, new Item.Settings()));
        RSCRAP_AXE = reg(GeneratedRegistrationIds.Items.RSCRAP_AXE, new AxeItem(ModToolTiers.RSCRAP, 6, -3.2f, new Item.Settings()));
        RSCRAP_HOE = reg(GeneratedRegistrationIds.Items.RSCRAP_HOE, new HoeItem(ModToolTiers.RSCRAP, 0, -3f, new Item.Settings()));
        CALCIFIED_AMETHYST = reg(GeneratedRegistrationIds.Items.CALCIFIED_AMETHYST, new Item(new Item.Settings().maxCount(64)));
        GLACIAL_SHARD = reg(GeneratedRegistrationIds.Items.GLACIAL_SHARD, new Item(new Item.Settings().maxCount(64)));
        POLISHED_QUARTZ = reg(GeneratedRegistrationIds.Items.POLISHED_QUARTZ, new Item(new Item.Settings().maxCount(64)));
        POLISHED_PRISMARINE = reg(GeneratedRegistrationIds.Items.POLISHED_PRISMARINE, new Item(new Item.Settings().maxCount(64)));
        RAMETHYST_SWORD = reg(GeneratedRegistrationIds.Items.RAMETHYST_SWORD, new SwordItem(ModToolTiers.RAMETHYST, 3, -2.4f, new Item.Settings()));
        RAMETHYST_PICKAXE = reg(GeneratedRegistrationIds.Items.RAMETHYST_PICKAXE, new PickaxeItem(ModToolTiers.RAMETHYST, 1, -2.8f, new Item.Settings()));
        RAMETHYST_SHOVEL = reg(GeneratedRegistrationIds.Items.RAMETHYST_SHOVEL, new ShovelItem(ModToolTiers.RAMETHYST, 1.5f, -3f, new Item.Settings()));
        RAMETHYST_AXE = reg(GeneratedRegistrationIds.Items.RAMETHYST_AXE, new AxeItem(ModToolTiers.RAMETHYST, 6, -3.2f, new Item.Settings()));
        RAMETHYST_HOE = reg(GeneratedRegistrationIds.Items.RAMETHYST_HOE, new HoeItem(ModToolTiers.RAMETHYST, 0, -3f, new Item.Settings()));
        SNOW_SWORD = reg(GeneratedRegistrationIds.Items.SNOW_SWORD, new SwordItem(ModToolTiers.SNOW_TOOL, 3, -2.4f, new Item.Settings()));
        SNOW_PICKAXE = reg(GeneratedRegistrationIds.Items.SNOW_PICKAXE, new PickaxeItem(ModToolTiers.SNOW_TOOL, 1, -2.8f, new Item.Settings()));
        SNOW_SHOVEL = reg(GeneratedRegistrationIds.Items.SNOW_SHOVEL, new ShovelItem(ModToolTiers.SNOW_TOOL, 1.5f, -3f, new Item.Settings()));
        SNOW_AXE = reg(GeneratedRegistrationIds.Items.SNOW_AXE, new AxeItem(ModToolTiers.SNOW_TOOL, 6, -3.2f, new Item.Settings()));
        SNOW_HOE = reg(GeneratedRegistrationIds.Items.SNOW_HOE, new HoeItem(ModToolTiers.SNOW_TOOL, 0, -3f, new Item.Settings()));
        RQUARTZ_SWORD = reg(GeneratedRegistrationIds.Items.RQUARTZ_SWORD, new SwordItem(ModToolTiers.RQUARTZ, 3, -2.4f, new Item.Settings()));
        RQUARTZ_PICKAXE = reg(GeneratedRegistrationIds.Items.RQUARTZ_PICKAXE, new PickaxeItem(ModToolTiers.RQUARTZ, 1, -2.8f, new Item.Settings()));
        RQUARTZ_SHOVEL = reg(GeneratedRegistrationIds.Items.RQUARTZ_SHOVEL, new ShovelItem(ModToolTiers.RQUARTZ, 1.5f, -3f, new Item.Settings()));
        RQUARTZ_AXE = reg(GeneratedRegistrationIds.Items.RQUARTZ_AXE, new AxeItem(ModToolTiers.RQUARTZ, 6, -3.2f, new Item.Settings()));
        RQUARTZ_HOE = reg(GeneratedRegistrationIds.Items.RQUARTZ_HOE, new HoeItem(ModToolTiers.RQUARTZ, 0, -3f, new Item.Settings()));
        RPRISM_SWORD = reg(GeneratedRegistrationIds.Items.RPRISM_SWORD, new SwordItem(ModToolTiers.RPRISM, 3, -2.4f, new Item.Settings()));
        RPRISM_PICKAXE = reg(GeneratedRegistrationIds.Items.RPRISM_PICKAXE, new PickaxeItem(ModToolTiers.RPRISM, 1, -2.8f, new Item.Settings()));
        RPRISM_SHOVEL = reg(GeneratedRegistrationIds.Items.RPRISM_SHOVEL, new ShovelItem(ModToolTiers.RPRISM, 1.5f, -3f, new Item.Settings()));
        RPRISM_AXE = reg(GeneratedRegistrationIds.Items.RPRISM_AXE, new AxeItem(ModToolTiers.RPRISM, 6, -3.2f, new Item.Settings()));
        RPRISM_HOE = reg(GeneratedRegistrationIds.Items.RPRISM_HOE, new HoeItem(ModToolTiers.RPRISM, 0, -3f, new Item.Settings()));
        CAMETHYST_SWORD = reg(GeneratedRegistrationIds.Items.CAMETHYST_SWORD, new SwordItem(ModToolTiers.CAMETHYST, 3, -2.4f, new Item.Settings()));
        CAMETHYST_PICKAXE = reg(GeneratedRegistrationIds.Items.CAMETHYST_PICKAXE, new PickaxeItem(ModToolTiers.CAMETHYST, 1, -2.8f, new Item.Settings()));
        CAMETHYST_SHOVEL = reg(GeneratedRegistrationIds.Items.CAMETHYST_SHOVEL, new ShovelItem(ModToolTiers.CAMETHYST, 1.5f, -3f, new Item.Settings()));
        CAMETHYST_AXE = reg(GeneratedRegistrationIds.Items.CAMETHYST_AXE, new AxeItem(ModToolTiers.CAMETHYST, 6, -3.2f, new Item.Settings()));
        CAMETHYST_HOE = reg(GeneratedRegistrationIds.Items.CAMETHYST_HOE, new HoeItem(ModToolTiers.CAMETHYST, 0, -3f, new Item.Settings()));
        CAMETHYST_HELMET = reg(GeneratedRegistrationIds.Items.CAMETHYST_HELMET, new ArmorItem(ModArmorMaterials.CAMETHYST, ArmorItem.Type.HELMET,
                    new Item.Settings()));
        CAMETHYST_CHESTPLATE = reg(GeneratedRegistrationIds.Items.CAMETHYST_CHESTPLATE, new ArmorItem(ModArmorMaterials.CAMETHYST, ArmorItem.Type.CHESTPLATE,
                    new Item.Settings()));
        CAMETHYST_LEGGINGS = reg(GeneratedRegistrationIds.Items.CAMETHYST_LEGGINGS, new ArmorItem(ModArmorMaterials.CAMETHYST, ArmorItem.Type.LEGGINGS,
                    new Item.Settings()));
        CAMETHYST_BOOTS = reg(GeneratedRegistrationIds.Items.CAMETHYST_BOOTS, new ArmorItem(ModArmorMaterials.CAMETHYST, ArmorItem.Type.BOOTS,
                    new Item.Settings()));
        ICE_SWORD = reg(GeneratedRegistrationIds.Items.ICE_SWORD, new SwordItem(ModToolTiers.ICE_TOOL, 3, -2.4f, new Item.Settings()));
        ICE_PICKAXE = reg(GeneratedRegistrationIds.Items.ICE_PICKAXE, new PickaxeItem(ModToolTiers.ICE_TOOL, 1, -2.8f, new Item.Settings()));
        ICE_SHOVEL = reg(GeneratedRegistrationIds.Items.ICE_SHOVEL, new ShovelItem(ModToolTiers.ICE_TOOL, 1.5f, -3f, new Item.Settings()));
        ICE_AXE = reg(GeneratedRegistrationIds.Items.ICE_AXE, new AxeItem(ModToolTiers.ICE_TOOL, 6, -3.2f, new Item.Settings()));
        ICE_HOE = reg(GeneratedRegistrationIds.Items.ICE_HOE, new HoeItem(ModToolTiers.ICE_TOOL, 0, -3f, new Item.Settings()));
        ICE_HELMET = reg(GeneratedRegistrationIds.Items.ICE_HELMET, new ArmorItem(ModArmorMaterials.ICE, ArmorItem.Type.HELMET,
                    new Item.Settings()));
        ICE_CHESTPLATE = reg(GeneratedRegistrationIds.Items.ICE_CHESTPLATE, new ArmorItem(ModArmorMaterials.ICE, ArmorItem.Type.CHESTPLATE,
                    new Item.Settings()));
        ICE_LEGGINGS = reg(GeneratedRegistrationIds.Items.ICE_LEGGINGS, new ArmorItem(ModArmorMaterials.ICE, ArmorItem.Type.LEGGINGS,
                    new Item.Settings()));
        ICE_BOOTS = reg(GeneratedRegistrationIds.Items.ICE_BOOTS, new ArmorItem(ModArmorMaterials.ICE, ArmorItem.Type.BOOTS,
                    new Item.Settings()));
        PQUARTZ_SWORD = reg(GeneratedRegistrationIds.Items.PQUARTZ_SWORD, new SwordItem(ModToolTiers.PQUARTZ, 3, -2.4f, new Item.Settings()));
        PQUARTZ_PICKAXE = reg(GeneratedRegistrationIds.Items.PQUARTZ_PICKAXE, new PickaxeItem(ModToolTiers.PQUARTZ, 1, -2.8f, new Item.Settings()));
        PQUARTZ_SHOVEL = reg(GeneratedRegistrationIds.Items.PQUARTZ_SHOVEL, new ShovelItem(ModToolTiers.PQUARTZ, 1.5f, -3f, new Item.Settings()));
        PQUARTZ_AXE = reg(GeneratedRegistrationIds.Items.PQUARTZ_AXE, new AxeItem(ModToolTiers.PQUARTZ, 6, -3.2f, new Item.Settings()));
        PQUARTZ_HOE = reg(GeneratedRegistrationIds.Items.PQUARTZ_HOE, new HoeItem(ModToolTiers.PQUARTZ, 0, -3f, new Item.Settings()));
        PQUARTZ_HELMET = reg(GeneratedRegistrationIds.Items.PQUARTZ_HELMET, new ArmorItem(ModArmorMaterials.PQUARTZ, ArmorItem.Type.HELMET,
                    new Item.Settings()));
        PQUARTZ_CHESTPLATE = reg(GeneratedRegistrationIds.Items.PQUARTZ_CHESTPLATE, new ArmorItem(ModArmorMaterials.PQUARTZ, ArmorItem.Type.CHESTPLATE,
                    new Item.Settings()));
        PQUARTZ_LEGGINGS = reg(GeneratedRegistrationIds.Items.PQUARTZ_LEGGINGS, new ArmorItem(ModArmorMaterials.PQUARTZ, ArmorItem.Type.LEGGINGS,
                    new Item.Settings()));
        PQUARTZ_BOOTS = reg(GeneratedRegistrationIds.Items.PQUARTZ_BOOTS, new ArmorItem(ModArmorMaterials.PQUARTZ, ArmorItem.Type.BOOTS,
                    new Item.Settings()));
        PPRISM_SWORD = reg(GeneratedRegistrationIds.Items.PPRISM_SWORD, new SwordItem(ModToolTiers.PPRISM, 3, -2.4f, new Item.Settings()));
        PPRISM_PICKAXE = reg(GeneratedRegistrationIds.Items.PPRISM_PICKAXE, new PickaxeItem(ModToolTiers.PPRISM, 1, -2.8f, new Item.Settings()));
        PPRISM_SHOVEL = reg(GeneratedRegistrationIds.Items.PPRISM_SHOVEL, new ShovelItem(ModToolTiers.PPRISM, 1.5f, -3f, new Item.Settings()));
        PPRISM_AXE = reg(GeneratedRegistrationIds.Items.PPRISM_AXE, new AxeItem(ModToolTiers.PPRISM, 6, -3.2f, new Item.Settings()));
        PPRISM_HOE = reg(GeneratedRegistrationIds.Items.PPRISM_HOE, new HoeItem(ModToolTiers.PPRISM, 0, -3f, new Item.Settings()));
        PPRISM_HELMET = reg(GeneratedRegistrationIds.Items.PPRISM_HELMET, new ArmorItem(ModArmorMaterials.PPRISM, ArmorItem.Type.HELMET,
                    new Item.Settings()));
        PPRISM_CHESTPLATE = reg(GeneratedRegistrationIds.Items.PPRISM_CHESTPLATE, new ArmorItem(ModArmorMaterials.PPRISM, ArmorItem.Type.CHESTPLATE,
                    new Item.Settings()));
        PPRISM_LEGGINGS = reg(GeneratedRegistrationIds.Items.PPRISM_LEGGINGS, new ArmorItem(ModArmorMaterials.PPRISM, ArmorItem.Type.LEGGINGS,
                    new Item.Settings()));
        PPRISM_BOOTS = reg(GeneratedRegistrationIds.Items.PPRISM_BOOTS, new ArmorItem(ModArmorMaterials.PPRISM, ArmorItem.Type.BOOTS,
                    new Item.Settings()));
        RFLINT_SWORD = reg(GeneratedRegistrationIds.Items.RFLINT_SWORD, new SwordItem(ModToolTiers.RFLINT, 3, -2.4f, new Item.Settings()));
        RFLINT_PICKAXE = reg(GeneratedRegistrationIds.Items.RFLINT_PICKAXE, new PickaxeItem(ModToolTiers.RFLINT, 1, -2.8f, new Item.Settings()));
        RFLINT_SHOVEL = reg(GeneratedRegistrationIds.Items.RFLINT_SHOVEL, new ShovelItem(ModToolTiers.RFLINT, 1.5f, -3f, new Item.Settings()));
        RFLINT_AXE = reg(GeneratedRegistrationIds.Items.RFLINT_AXE, new AxeItem(ModToolTiers.RFLINT, 6, -3.2f, new Item.Settings()));
        RFLINT_HOE = reg(GeneratedRegistrationIds.Items.RFLINT_HOE, new HoeItem(ModToolTiers.RFLINT, 0, -3f, new Item.Settings()));
        FNI_SWORD = reg(GeneratedRegistrationIds.Items.FNI_SWORD, new SwordItem(ModToolTiers.FNI_TOOLS, 3, -2.4f, new Item.Settings()));
        FNI_PICKAXE = reg(GeneratedRegistrationIds.Items.FNI_PICKAXE, new PickaxeItem(ModToolTiers.FNI_TOOLS, 1, -2.8f, new Item.Settings()));
        FNI_SHOVEL = reg(GeneratedRegistrationIds.Items.FNI_SHOVEL, new ShovelItem(ModToolTiers.FNI_TOOLS, 1.5f, -3f, new Item.Settings()));
        FNI_AXE = reg(GeneratedRegistrationIds.Items.FNI_AXE, new AxeItem(ModToolTiers.FNI_TOOLS, 6, -3.2f, new Item.Settings()));
        FNI_HOE = reg(GeneratedRegistrationIds.Items.FNI_HOE, new HoeItem(ModToolTiers.FNI_TOOLS, 0, -3f, new Item.Settings()));
        FNI_HELMET = reg(GeneratedRegistrationIds.Items.FNI_HELMET, new ArmorItem(ModArmorMaterials.FNI, ArmorItem.Type.HELMET,
                    new Item.Settings()));
        FNI_CHESTPLATE = reg(GeneratedRegistrationIds.Items.FNI_CHESTPLATE, new ArmorItem(ModArmorMaterials.FNI, ArmorItem.Type.CHESTPLATE,
                    new Item.Settings()));
        FNI_LEGGINGS = reg(GeneratedRegistrationIds.Items.FNI_LEGGINGS, new ArmorItem(ModArmorMaterials.FNI, ArmorItem.Type.LEGGINGS,
                    new Item.Settings()));
        FNI_BOOTS = reg(GeneratedRegistrationIds.Items.FNI_BOOTS, new ArmorItem(ModArmorMaterials.FNI, ArmorItem.Type.BOOTS,
                    new Item.Settings()));
        ANDESITE_SWORD = reg(GeneratedRegistrationIds.Items.ANDESITE_SWORD, new SwordItem(ModToolTiers.STONE_ANDESITE, 3, -2.4f, new Item.Settings()));
        ANDESITE_PICKAXE = reg(GeneratedRegistrationIds.Items.ANDESITE_PICKAXE, new PickaxeItem(ModToolTiers.STONE_ANDESITE, 1, -2.8f, new Item.Settings()));
        ANDESITE_SHOVEL = reg(GeneratedRegistrationIds.Items.ANDESITE_SHOVEL, new ShovelItem(ModToolTiers.STONE_ANDESITE, 1.5f, -3f, new Item.Settings()));
        ANDESITE_AXE = reg(GeneratedRegistrationIds.Items.ANDESITE_AXE, new AxeItem(ModToolTiers.STONE_ANDESITE, 6, -3.2f, new Item.Settings()));
        ANDESITE_HOE = reg(GeneratedRegistrationIds.Items.ANDESITE_HOE, new HoeItem(ModToolTiers.STONE_ANDESITE, 0, -3f, new Item.Settings()));
        BASALT_SWORD = reg(GeneratedRegistrationIds.Items.BASALT_SWORD, new SwordItem(ModToolTiers.STONE_BASALT, 3, -2.5f, new Item.Settings()));
        BASALT_PICKAXE = reg(GeneratedRegistrationIds.Items.BASALT_PICKAXE, new PickaxeItem(ModToolTiers.STONE_BASALT, 1, -2.9f, new Item.Settings()));
        BASALT_SHOVEL = reg(GeneratedRegistrationIds.Items.BASALT_SHOVEL, new ShovelItem(ModToolTiers.STONE_BASALT, 2.0f, -3.1f, new Item.Settings()));
        BASALT_AXE = reg(GeneratedRegistrationIds.Items.BASALT_AXE, new AxeItem(ModToolTiers.STONE_BASALT, 7, -3.3f, new Item.Settings()));
        BASALT_HOE = reg(GeneratedRegistrationIds.Items.BASALT_HOE, new HoeItem(ModToolTiers.STONE_BASALT, 0, -3.1f, new Item.Settings()));
        BLACKSTONE_SWORD = reg(GeneratedRegistrationIds.Items.BLACKSTONE_SWORD, new SwordItem(ModToolTiers.STONE_BLACKSTONE, 4, -2.5f, new Item.Settings()));
        BLACKSTONE_PICKAXE = reg(GeneratedRegistrationIds.Items.BLACKSTONE_PICKAXE, new PickaxeItem(ModToolTiers.STONE_BLACKSTONE, 1, -2.9f, new Item.Settings()));
        BLACKSTONE_SHOVEL = reg(GeneratedRegistrationIds.Items.BLACKSTONE_SHOVEL, new ShovelItem(ModToolTiers.STONE_BLACKSTONE, 2.0f, -3.1f, new Item.Settings()));
        BLACKSTONE_AXE = reg(GeneratedRegistrationIds.Items.BLACKSTONE_AXE, new AxeItem(ModToolTiers.STONE_BLACKSTONE, 7, -3.35f, new Item.Settings()));
        BLACKSTONE_HOE = reg(GeneratedRegistrationIds.Items.BLACKSTONE_HOE, new HoeItem(ModToolTiers.STONE_BLACKSTONE, 0, -3.1f, new Item.Settings()));
        CALCITE_SWORD = reg(GeneratedRegistrationIds.Items.CALCITE_SWORD, new SwordItem(ModToolTiers.STONE_CALCITE, 2, -2.2f, new Item.Settings()));
        CALCITE_PICKAXE = reg(GeneratedRegistrationIds.Items.CALCITE_PICKAXE, new PickaxeItem(ModToolTiers.STONE_CALCITE, 1, -2.6f, new Item.Settings()));
        CALCITE_SHOVEL = reg(GeneratedRegistrationIds.Items.CALCITE_SHOVEL, new ShovelItem(ModToolTiers.STONE_CALCITE, 1.0f, -2.8f, new Item.Settings()));
        CALCITE_AXE = reg(GeneratedRegistrationIds.Items.CALCITE_AXE, new AxeItem(ModToolTiers.STONE_CALCITE, 5, -3.0f, new Item.Settings()));
        CALCITE_HOE = reg(GeneratedRegistrationIds.Items.CALCITE_HOE, new HoeItem(ModToolTiers.STONE_CALCITE, 0, -2.6f, new Item.Settings()));
        DEEPSLATE_SWORD = reg(GeneratedRegistrationIds.Items.DEEPSLATE_SWORD, new SwordItem(ModToolTiers.STONE_DEEPSLATE, 4, -2.55f, new Item.Settings()));
        DEEPSLATE_PICKAXE = reg(GeneratedRegistrationIds.Items.DEEPSLATE_PICKAXE, new PickaxeItem(ModToolTiers.STONE_DEEPSLATE, 1, -2.95f, new Item.Settings()));
        DEEPSLATE_SHOVEL = reg(GeneratedRegistrationIds.Items.DEEPSLATE_SHOVEL, new ShovelItem(ModToolTiers.STONE_DEEPSLATE, 2.0f, -3.15f, new Item.Settings()));
        DEEPSLATE_AXE = reg(GeneratedRegistrationIds.Items.DEEPSLATE_AXE, new AxeItem(ModToolTiers.STONE_DEEPSLATE, 7, -3.4f, new Item.Settings()));
        DEEPSLATE_HOE = reg(GeneratedRegistrationIds.Items.DEEPSLATE_HOE, new HoeItem(ModToolTiers.STONE_DEEPSLATE, 0, -3.1f, new Item.Settings()));
        DIORITE_SWORD = reg(GeneratedRegistrationIds.Items.DIORITE_SWORD, new SwordItem(ModToolTiers.STONE_DIORITE, 3, -2.4f, new Item.Settings()));
        DIORITE_PICKAXE = reg(GeneratedRegistrationIds.Items.DIORITE_PICKAXE, new PickaxeItem(ModToolTiers.STONE_DIORITE, 1, -2.8f, new Item.Settings()));
        DIORITE_SHOVEL = reg(GeneratedRegistrationIds.Items.DIORITE_SHOVEL, new ShovelItem(ModToolTiers.STONE_DIORITE, 1.5f, -3f, new Item.Settings()));
        DIORITE_AXE = reg(GeneratedRegistrationIds.Items.DIORITE_AXE, new AxeItem(ModToolTiers.STONE_DIORITE, 6, -3.2f, new Item.Settings()));
        DIORITE_HOE = reg(GeneratedRegistrationIds.Items.DIORITE_HOE, new HoeItem(ModToolTiers.STONE_DIORITE, 0, -2.9f, new Item.Settings()));
        END_STONE_SWORD = reg(GeneratedRegistrationIds.Items.END_STONE_SWORD, new SwordItem(ModToolTiers.STONE_END_STONE, 3, -2.35f, new Item.Settings()));
        END_STONE_PICKAXE = reg(GeneratedRegistrationIds.Items.END_STONE_PICKAXE, new PickaxeItem(ModToolTiers.STONE_END_STONE, 1, -2.75f, new Item.Settings()));
        END_STONE_SHOVEL = reg(GeneratedRegistrationIds.Items.END_STONE_SHOVEL, new ShovelItem(ModToolTiers.STONE_END_STONE, 1.5f, -2.95f, new Item.Settings()));
        END_STONE_AXE = reg(GeneratedRegistrationIds.Items.END_STONE_AXE, new AxeItem(ModToolTiers.STONE_END_STONE, 6, -3.15f, new Item.Settings()));
        END_STONE_HOE = reg(GeneratedRegistrationIds.Items.END_STONE_HOE, new HoeItem(ModToolTiers.STONE_END_STONE, 0, -2.8f, new Item.Settings()));
        GRANITE_SWORD = reg(GeneratedRegistrationIds.Items.GRANITE_SWORD, new SwordItem(ModToolTiers.STONE_GRANITE, 3, -2.5f, new Item.Settings()));
        GRANITE_PICKAXE = reg(GeneratedRegistrationIds.Items.GRANITE_PICKAXE, new PickaxeItem(ModToolTiers.STONE_GRANITE, 1, -2.9f, new Item.Settings()));
        GRANITE_SHOVEL = reg(GeneratedRegistrationIds.Items.GRANITE_SHOVEL, new ShovelItem(ModToolTiers.STONE_GRANITE, 2.0f, -3.1f, new Item.Settings()));
        GRANITE_AXE = reg(GeneratedRegistrationIds.Items.GRANITE_AXE, new AxeItem(ModToolTiers.STONE_GRANITE, 7, -3.3f, new Item.Settings()));
        GRANITE_HOE = reg(GeneratedRegistrationIds.Items.GRANITE_HOE, new HoeItem(ModToolTiers.STONE_GRANITE, 0, -3.1f, new Item.Settings()));
        NETHERRACK_SWORD = reg(GeneratedRegistrationIds.Items.NETHERRACK_SWORD, new SwordItem(ModToolTiers.STONE_NETHERRACK, 2, -2.2f, new Item.Settings()));
        NETHERRACK_PICKAXE = reg(GeneratedRegistrationIds.Items.NETHERRACK_PICKAXE, new PickaxeItem(ModToolTiers.STONE_NETHERRACK, 1, -2.6f, new Item.Settings()));
        NETHERRACK_SHOVEL = reg(GeneratedRegistrationIds.Items.NETHERRACK_SHOVEL, new ShovelItem(ModToolTiers.STONE_NETHERRACK, 1.0f, -2.8f, new Item.Settings()));
        NETHERRACK_AXE = reg(GeneratedRegistrationIds.Items.NETHERRACK_AXE, new AxeItem(ModToolTiers.STONE_NETHERRACK, 5, -3.0f, new Item.Settings()));
        NETHERRACK_HOE = reg(GeneratedRegistrationIds.Items.NETHERRACK_HOE, new HoeItem(ModToolTiers.STONE_NETHERRACK, 0, -2.5f, new Item.Settings()));
        SANDSTONE_SWORD = reg(GeneratedRegistrationIds.Items.SANDSTONE_SWORD, new SwordItem(ModToolTiers.STONE_SANDSTONE, 2, -2.3f, new Item.Settings()));
        SANDSTONE_PICKAXE = reg(GeneratedRegistrationIds.Items.SANDSTONE_PICKAXE, new PickaxeItem(ModToolTiers.STONE_SANDSTONE, 1, -2.7f, new Item.Settings()));
        SANDSTONE_SHOVEL = reg(GeneratedRegistrationIds.Items.SANDSTONE_SHOVEL, new ShovelItem(ModToolTiers.STONE_SANDSTONE, 1.0f, -2.9f, new Item.Settings()));
        SANDSTONE_AXE = reg(GeneratedRegistrationIds.Items.SANDSTONE_AXE, new AxeItem(ModToolTiers.STONE_SANDSTONE, 5, -3.1f, new Item.Settings()));
        SANDSTONE_HOE = reg(GeneratedRegistrationIds.Items.SANDSTONE_HOE, new HoeItem(ModToolTiers.STONE_SANDSTONE, 0, -2.7f, new Item.Settings()));
        SMOOTH_BASALT_SWORD = reg(GeneratedRegistrationIds.Items.SMOOTH_BASALT_SWORD, new SwordItem(ModToolTiers.STONE_SMOOTH_BASALT, 3, -2.45f, new Item.Settings()));
        SMOOTH_BASALT_PICKAXE = reg(GeneratedRegistrationIds.Items.SMOOTH_BASALT_PICKAXE, new PickaxeItem(ModToolTiers.STONE_SMOOTH_BASALT, 1, -2.85f, new Item.Settings()));
        SMOOTH_BASALT_SHOVEL = reg(GeneratedRegistrationIds.Items.SMOOTH_BASALT_SHOVEL, new ShovelItem(ModToolTiers.STONE_SMOOTH_BASALT, 1.5f, -3.05f, new Item.Settings()));
        SMOOTH_BASALT_AXE = reg(GeneratedRegistrationIds.Items.SMOOTH_BASALT_AXE, new AxeItem(ModToolTiers.STONE_SMOOTH_BASALT, 6, -3.25f, new Item.Settings()));
        SMOOTH_BASALT_HOE = reg(GeneratedRegistrationIds.Items.SMOOTH_BASALT_HOE, new HoeItem(ModToolTiers.STONE_SMOOTH_BASALT, 0, -3.0f, new Item.Settings()));
        TERRACOTTA_SWORD = reg(GeneratedRegistrationIds.Items.TERRACOTTA_SWORD, new SwordItem(ModToolTiers.STONE_TERRACOTTA, 3, -2.35f, new Item.Settings()));
        TERRACOTTA_PICKAXE = reg(GeneratedRegistrationIds.Items.TERRACOTTA_PICKAXE, new PickaxeItem(ModToolTiers.STONE_TERRACOTTA, 1, -2.75f, new Item.Settings()));
        TERRACOTTA_SHOVEL = reg(GeneratedRegistrationIds.Items.TERRACOTTA_SHOVEL, new ShovelItem(ModToolTiers.STONE_TERRACOTTA, 1.5f, -2.95f, new Item.Settings()));
        TERRACOTTA_AXE = reg(GeneratedRegistrationIds.Items.TERRACOTTA_AXE, new AxeItem(ModToolTiers.STONE_TERRACOTTA, 6, -3.15f, new Item.Settings()));
        TERRACOTTA_HOE = reg(GeneratedRegistrationIds.Items.TERRACOTTA_HOE, new HoeItem(ModToolTiers.STONE_TERRACOTTA, 0, -2.8f, new Item.Settings()));
        TUFF_SWORD = reg(GeneratedRegistrationIds.Items.TUFF_SWORD, new SwordItem(ModToolTiers.STONE_TUFF, 2, -2.35f, new Item.Settings()));
        TUFF_PICKAXE = reg(GeneratedRegistrationIds.Items.TUFF_PICKAXE, new PickaxeItem(ModToolTiers.STONE_TUFF, 1, -2.75f, new Item.Settings()));
        TUFF_SHOVEL = reg(GeneratedRegistrationIds.Items.TUFF_SHOVEL, new ShovelItem(ModToolTiers.STONE_TUFF, 1.5f, -2.95f, new Item.Settings()));
        TUFF_AXE = reg(GeneratedRegistrationIds.Items.TUFF_AXE, new AxeItem(ModToolTiers.STONE_TUFF, 5, -3.15f, new Item.Settings()));
        TUFF_HOE = reg(GeneratedRegistrationIds.Items.TUFF_HOE, new HoeItem(ModToolTiers.STONE_TUFF, 0, -2.8f, new Item.Settings()));
        OAK_SWORD = reg(GeneratedRegistrationIds.Items.OAK_SWORD, new SwordItem(ModToolTiers.WOOD_OAK, 3, -2.4f, new Item.Settings()));
        OAK_PICKAXE = reg(GeneratedRegistrationIds.Items.OAK_PICKAXE, new PickaxeItem(ModToolTiers.WOOD_OAK, 1, -2.8f, new Item.Settings()));
        OAK_SHOVEL = reg(GeneratedRegistrationIds.Items.OAK_SHOVEL, new ShovelItem(ModToolTiers.WOOD_OAK, 1.5f, -3f, new Item.Settings()));
        OAK_AXE = reg(GeneratedRegistrationIds.Items.OAK_AXE, new AxeItem(ModToolTiers.WOOD_OAK, 6, -3.2f, new Item.Settings()));
        OAK_HOE = reg(GeneratedRegistrationIds.Items.OAK_HOE, new HoeItem(ModToolTiers.WOOD_OAK, 0, -3f, new Item.Settings()));
        SPRUCE_SWORD = reg(GeneratedRegistrationIds.Items.SPRUCE_SWORD, new SwordItem(ModToolTiers.WOOD_SPRUCE, 3, -2.4f, new Item.Settings()));
        SPRUCE_PICKAXE = reg(GeneratedRegistrationIds.Items.SPRUCE_PICKAXE, new PickaxeItem(ModToolTiers.WOOD_SPRUCE, 1, -2.8f, new Item.Settings()));
        SPRUCE_SHOVEL = reg(GeneratedRegistrationIds.Items.SPRUCE_SHOVEL, new ShovelItem(ModToolTiers.WOOD_SPRUCE, 1.5f, -3f, new Item.Settings()));
        SPRUCE_AXE = reg(GeneratedRegistrationIds.Items.SPRUCE_AXE, new AxeItem(ModToolTiers.WOOD_SPRUCE, 6, -3.2f, new Item.Settings()));
        SPRUCE_HOE = reg(GeneratedRegistrationIds.Items.SPRUCE_HOE, new HoeItem(ModToolTiers.WOOD_SPRUCE, 0, -3f, new Item.Settings()));
        BIRCH_SWORD = reg(GeneratedRegistrationIds.Items.BIRCH_SWORD, new SwordItem(ModToolTiers.WOOD_BIRCH, 3, -2.4f, new Item.Settings()));
        BIRCH_PICKAXE = reg(GeneratedRegistrationIds.Items.BIRCH_PICKAXE, new PickaxeItem(ModToolTiers.WOOD_BIRCH, 1, -2.8f, new Item.Settings()));
        BIRCH_SHOVEL = reg(GeneratedRegistrationIds.Items.BIRCH_SHOVEL, new ShovelItem(ModToolTiers.WOOD_BIRCH, 1.5f, -3f, new Item.Settings()));
        BIRCH_AXE = reg(GeneratedRegistrationIds.Items.BIRCH_AXE, new AxeItem(ModToolTiers.WOOD_BIRCH, 6, -3.2f, new Item.Settings()));
        BIRCH_HOE = reg(GeneratedRegistrationIds.Items.BIRCH_HOE, new HoeItem(ModToolTiers.WOOD_BIRCH, 0, -3f, new Item.Settings()));
        JUNGLE_SWORD = reg(GeneratedRegistrationIds.Items.JUNGLE_SWORD, new SwordItem(ModToolTiers.WOOD_JUNGLE, 3, -2.4f, new Item.Settings()));
        JUNGLE_PICKAXE = reg(GeneratedRegistrationIds.Items.JUNGLE_PICKAXE, new PickaxeItem(ModToolTiers.WOOD_JUNGLE, 1, -2.8f, new Item.Settings()));
        JUNGLE_SHOVEL = reg(GeneratedRegistrationIds.Items.JUNGLE_SHOVEL, new ShovelItem(ModToolTiers.WOOD_JUNGLE, 1.5f, -3f, new Item.Settings()));
        JUNGLE_AXE = reg(GeneratedRegistrationIds.Items.JUNGLE_AXE, new AxeItem(ModToolTiers.WOOD_JUNGLE, 6, -3.2f, new Item.Settings()));
        JUNGLE_HOE = reg(GeneratedRegistrationIds.Items.JUNGLE_HOE, new HoeItem(ModToolTiers.WOOD_JUNGLE, 0, -3f, new Item.Settings()));
        ACACIA_SWORD = reg(GeneratedRegistrationIds.Items.ACACIA_SWORD, new SwordItem(ModToolTiers.WOOD_ACACIA, 3, -2.4f, new Item.Settings()));
        ACACIA_PICKAXE = reg(GeneratedRegistrationIds.Items.ACACIA_PICKAXE, new PickaxeItem(ModToolTiers.WOOD_ACACIA, 1, -2.8f, new Item.Settings()));
        ACACIA_SHOVEL = reg(GeneratedRegistrationIds.Items.ACACIA_SHOVEL, new ShovelItem(ModToolTiers.WOOD_ACACIA, 1.5f, -3f, new Item.Settings()));
        ACACIA_AXE = reg(GeneratedRegistrationIds.Items.ACACIA_AXE, new AxeItem(ModToolTiers.WOOD_ACACIA, 6, -3.2f, new Item.Settings()));
        ACACIA_HOE = reg(GeneratedRegistrationIds.Items.ACACIA_HOE, new HoeItem(ModToolTiers.WOOD_ACACIA, 0, -3f, new Item.Settings()));
        DARK_OAK_SWORD = reg(GeneratedRegistrationIds.Items.DARK_OAK_SWORD, new SwordItem(ModToolTiers.WOOD_DARK_OAK, 3, -2.4f, new Item.Settings()));
        DARK_OAK_PICKAXE = reg(GeneratedRegistrationIds.Items.DARK_OAK_PICKAXE, new PickaxeItem(ModToolTiers.WOOD_DARK_OAK, 1, -2.8f, new Item.Settings()));
        DARK_OAK_SHOVEL = reg(GeneratedRegistrationIds.Items.DARK_OAK_SHOVEL, new ShovelItem(ModToolTiers.WOOD_DARK_OAK, 1.5f, -3f, new Item.Settings()));
        DARK_OAK_AXE = reg(GeneratedRegistrationIds.Items.DARK_OAK_AXE, new AxeItem(ModToolTiers.WOOD_DARK_OAK, 6, -3.2f, new Item.Settings()));
        DARK_OAK_HOE = reg(GeneratedRegistrationIds.Items.DARK_OAK_HOE, new HoeItem(ModToolTiers.WOOD_DARK_OAK, 0, -3f, new Item.Settings()));
        MANGROVE_SWORD = reg(GeneratedRegistrationIds.Items.MANGROVE_SWORD, new SwordItem(ModToolTiers.WOOD_MANGROVE, 3, -2.4f, new Item.Settings()));
        MANGROVE_PICKAXE = reg(GeneratedRegistrationIds.Items.MANGROVE_PICKAXE, new PickaxeItem(ModToolTiers.WOOD_MANGROVE, 1, -2.8f, new Item.Settings()));
        MANGROVE_SHOVEL = reg(GeneratedRegistrationIds.Items.MANGROVE_SHOVEL, new ShovelItem(ModToolTiers.WOOD_MANGROVE, 1.5f, -3f, new Item.Settings()));
        MANGROVE_AXE = reg(GeneratedRegistrationIds.Items.MANGROVE_AXE, new AxeItem(ModToolTiers.WOOD_MANGROVE, 6, -3.2f, new Item.Settings()));
        MANGROVE_HOE = reg(GeneratedRegistrationIds.Items.MANGROVE_HOE, new HoeItem(ModToolTiers.WOOD_MANGROVE, 0, -3f, new Item.Settings()));
        CHERRY_SWORD = reg(GeneratedRegistrationIds.Items.CHERRY_SWORD, new SwordItem(ModToolTiers.WOOD_CHERRY, 3, -2.4f, new Item.Settings()));
        CHERRY_PICKAXE = reg(GeneratedRegistrationIds.Items.CHERRY_PICKAXE, new PickaxeItem(ModToolTiers.WOOD_CHERRY, 1, -2.8f, new Item.Settings()));
        CHERRY_SHOVEL = reg(GeneratedRegistrationIds.Items.CHERRY_SHOVEL, new ShovelItem(ModToolTiers.WOOD_CHERRY, 1.5f, -3f, new Item.Settings()));
        CHERRY_AXE = reg(GeneratedRegistrationIds.Items.CHERRY_AXE, new AxeItem(ModToolTiers.WOOD_CHERRY, 6, -3.2f, new Item.Settings()));
        CHERRY_HOE = reg(GeneratedRegistrationIds.Items.CHERRY_HOE, new HoeItem(ModToolTiers.WOOD_CHERRY, 0, -3f, new Item.Settings()));
        BAMBOO_SWORD = reg(GeneratedRegistrationIds.Items.BAMBOO_SWORD, new SwordItem(ModToolTiers.WOOD_BAMBOO, 3, -2.4f, new Item.Settings()));
        BAMBOO_PICKAXE = reg(GeneratedRegistrationIds.Items.BAMBOO_PICKAXE, new PickaxeItem(ModToolTiers.WOOD_BAMBOO, 1, -2.8f, new Item.Settings()));
        BAMBOO_SHOVEL = reg(GeneratedRegistrationIds.Items.BAMBOO_SHOVEL, new ShovelItem(ModToolTiers.WOOD_BAMBOO, 1.5f, -3f, new Item.Settings()));
        BAMBOO_AXE = reg(GeneratedRegistrationIds.Items.BAMBOO_AXE, new AxeItem(ModToolTiers.WOOD_BAMBOO, 6, -3.2f, new Item.Settings()));
        BAMBOO_HOE = reg(GeneratedRegistrationIds.Items.BAMBOO_HOE, new HoeItem(ModToolTiers.WOOD_BAMBOO, 0, -3f, new Item.Settings()));
        CRIMSON_SWORD = reg(GeneratedRegistrationIds.Items.CRIMSON_SWORD, new SwordItem(ModToolTiers.WOOD_CRIMSON, 3, -2.4f, new Item.Settings()));
        CRIMSON_PICKAXE = reg(GeneratedRegistrationIds.Items.CRIMSON_PICKAXE, new PickaxeItem(ModToolTiers.WOOD_CRIMSON, 1, -2.8f, new Item.Settings()));
        CRIMSON_SHOVEL = reg(GeneratedRegistrationIds.Items.CRIMSON_SHOVEL, new ShovelItem(ModToolTiers.WOOD_CRIMSON, 1.5f, -3f, new Item.Settings()));
        CRIMSON_AXE = reg(GeneratedRegistrationIds.Items.CRIMSON_AXE, new AxeItem(ModToolTiers.WOOD_CRIMSON, 6, -3.2f, new Item.Settings()));
        CRIMSON_HOE = reg(GeneratedRegistrationIds.Items.CRIMSON_HOE, new HoeItem(ModToolTiers.WOOD_CRIMSON, 0, -3f, new Item.Settings()));
        WARPED_SWORD = reg(GeneratedRegistrationIds.Items.WARPED_SWORD, new SwordItem(ModToolTiers.WOOD_WARPED, 3, -2.4f, new Item.Settings()));
        WARPED_PICKAXE = reg(GeneratedRegistrationIds.Items.WARPED_PICKAXE, new PickaxeItem(ModToolTiers.WOOD_WARPED, 1, -2.8f, new Item.Settings()));
        WARPED_SHOVEL = reg(GeneratedRegistrationIds.Items.WARPED_SHOVEL, new ShovelItem(ModToolTiers.WOOD_WARPED, 1.5f, -3f, new Item.Settings()));
        WARPED_AXE = reg(GeneratedRegistrationIds.Items.WARPED_AXE, new AxeItem(ModToolTiers.WOOD_WARPED, 6, -3.2f, new Item.Settings()));
        WARPED_HOE = reg(GeneratedRegistrationIds.Items.WARPED_HOE, new HoeItem(ModToolTiers.WOOD_WARPED, 0, -3f, new Item.Settings()));
        LEATHER_SWORD = reg(GeneratedRegistrationIds.Items.LEATHER_SWORD, new SwordItem(ModToolTiers.LEATHER, 3, -2.4f, new Item.Settings()));
        LEATHER_PICKAXE = reg(GeneratedRegistrationIds.Items.LEATHER_PICKAXE, new PickaxeItem(ModToolTiers.LEATHER, 1, -2.8f, new Item.Settings()));
        LEATHER_SHOVEL = reg(GeneratedRegistrationIds.Items.LEATHER_SHOVEL, new ShovelItem(ModToolTiers.LEATHER, 1.5f, -3f, new Item.Settings()));
        LEATHER_AXE = reg(GeneratedRegistrationIds.Items.LEATHER_AXE, new AxeItem(ModToolTiers.LEATHER, 6, -3.2f, new Item.Settings()));
        LEATHER_HOE = reg(GeneratedRegistrationIds.Items.LEATHER_HOE, new HoeItem(ModToolTiers.LEATHER, 0, -3f, new Item.Settings()));
        PAPER_SWORD = reg(GeneratedRegistrationIds.Items.PAPER_SWORD, new SwordItem(ModToolTiers.PAPER, 3, -2.4f, new Item.Settings()));
        PAPER_PICKAXE = reg(GeneratedRegistrationIds.Items.PAPER_PICKAXE, new PickaxeItem(ModToolTiers.PAPER, 1, -2.8f, new Item.Settings()));
        PAPER_SHOVEL = reg(GeneratedRegistrationIds.Items.PAPER_SHOVEL, new ShovelItem(ModToolTiers.PAPER, 1.5f, -3f, new Item.Settings()));
        PAPER_AXE = reg(GeneratedRegistrationIds.Items.PAPER_AXE, new AxeItem(ModToolTiers.PAPER, 6, -3.2f, new Item.Settings()));
        PAPER_HOE = reg(GeneratedRegistrationIds.Items.PAPER_HOE, new HoeItem(ModToolTiers.PAPER, 0, -3f, new Item.Settings()));
        FEATHER_SWORD = reg(GeneratedRegistrationIds.Items.FEATHER_SWORD, new SwordItem(ModToolTiers.FEATHER, 3, -2.4f, new Item.Settings()));
        FEATHER_PICKAXE = reg(GeneratedRegistrationIds.Items.FEATHER_PICKAXE, new PickaxeItem(ModToolTiers.FEATHER, 1, -2.8f, new Item.Settings()));
        FEATHER_SHOVEL = reg(GeneratedRegistrationIds.Items.FEATHER_SHOVEL, new ShovelItem(ModToolTiers.FEATHER, 1.5f, -3f, new Item.Settings()));
        FEATHER_AXE = reg(GeneratedRegistrationIds.Items.FEATHER_AXE, new AxeItem(ModToolTiers.FEATHER, 6, -3.2f, new Item.Settings()));
        FEATHER_HOE = reg(GeneratedRegistrationIds.Items.FEATHER_HOE, new HoeItem(ModToolTiers.FEATHER, 0, -3f, new Item.Settings()));
        GLASS_SWORD = reg(GeneratedRegistrationIds.Items.GLASS_SWORD, new SwordItem(ModToolTiers.GLASS, 3, -2.4f, new Item.Settings()));
        GLASS_PICKAXE = reg(GeneratedRegistrationIds.Items.GLASS_PICKAXE, new PickaxeItem(ModToolTiers.GLASS, 1, -2.8f, new Item.Settings()));
        GLASS_SHOVEL = reg(GeneratedRegistrationIds.Items.GLASS_SHOVEL, new ShovelItem(ModToolTiers.GLASS, 1.5f, -3f, new Item.Settings()));
        GLASS_AXE = reg(GeneratedRegistrationIds.Items.GLASS_AXE, new AxeItem(ModToolTiers.GLASS, 6, -3.2f, new Item.Settings()));
        GLASS_HOE = reg(GeneratedRegistrationIds.Items.GLASS_HOE, new HoeItem(ModToolTiers.GLASS, 0, -3f, new Item.Settings()));
        RABBIT_HIDE_HELMET = reg(GeneratedRegistrationIds.Items.RABBIT_HIDE_HELMET, new ArmorItem(ModArmorMaterials.RABBIT_HIDE, ArmorItem.Type.HELMET,
                    new Item.Settings()));
        RABBIT_HIDE_CHESTPLATE = reg(GeneratedRegistrationIds.Items.RABBIT_HIDE_CHESTPLATE, new ArmorItem(ModArmorMaterials.RABBIT_HIDE, ArmorItem.Type.CHESTPLATE,
                    new Item.Settings()));
        RABBIT_HIDE_LEGGINGS = reg(GeneratedRegistrationIds.Items.RABBIT_HIDE_LEGGINGS, new ArmorItem(ModArmorMaterials.RABBIT_HIDE, ArmorItem.Type.LEGGINGS,
                    new Item.Settings()));
        RABBIT_HIDE_BOOTS = reg(GeneratedRegistrationIds.Items.RABBIT_HIDE_BOOTS, new ArmorItem(ModArmorMaterials.RABBIT_HIDE, ArmorItem.Type.BOOTS,
                    new Item.Settings()));
        CACTUS_SWORD = reg(GeneratedRegistrationIds.Items.CACTUS_SWORD, new SwordItem(ModToolTiers.CACTUS, 3, -2.4f, new Item.Settings()));
        CACTUS_PICKAXE = reg(GeneratedRegistrationIds.Items.CACTUS_PICKAXE, new PickaxeItem(ModToolTiers.CACTUS, 1, -2.8f, new Item.Settings()));
        CACTUS_SHOVEL = reg(GeneratedRegistrationIds.Items.CACTUS_SHOVEL, new ShovelItem(ModToolTiers.CACTUS, 1.5f, -3f, new Item.Settings()));
        CACTUS_AXE = reg(GeneratedRegistrationIds.Items.CACTUS_AXE, new AxeItem(ModToolTiers.CACTUS, 6, -3.2f, new Item.Settings()));
        CACTUS_HOE = reg(GeneratedRegistrationIds.Items.CACTUS_HOE, new HoeItem(ModToolTiers.CACTUS, 0, -3f, new Item.Settings()));
        CACTUS_HELMET = reg(GeneratedRegistrationIds.Items.CACTUS_HELMET, new ArmorItem(ModArmorMaterials.CACTUS, ArmorItem.Type.HELMET,
                    new Item.Settings()));
        CACTUS_CHESTPLATE = reg(GeneratedRegistrationIds.Items.CACTUS_CHESTPLATE, new ArmorItem(ModArmorMaterials.CACTUS, ArmorItem.Type.CHESTPLATE,
                    new Item.Settings()));
        CACTUS_LEGGINGS = reg(GeneratedRegistrationIds.Items.CACTUS_LEGGINGS, new ArmorItem(ModArmorMaterials.CACTUS, ArmorItem.Type.LEGGINGS,
                    new Item.Settings()));
        CACTUS_BOOTS = reg(GeneratedRegistrationIds.Items.CACTUS_BOOTS, new ArmorItem(ModArmorMaterials.CACTUS, ArmorItem.Type.BOOTS,
                    new Item.Settings()));
        SPONGE_SWORD = reg(GeneratedRegistrationIds.Items.SPONGE_SWORD, new SwordItem(ModToolTiers.SPONGE, 3, -2.4f, new Item.Settings()));
        SPONGE_PICKAXE = reg(GeneratedRegistrationIds.Items.SPONGE_PICKAXE, new PickaxeItem(ModToolTiers.SPONGE, 1, -2.8f, new Item.Settings()));
        SPONGE_SHOVEL = reg(GeneratedRegistrationIds.Items.SPONGE_SHOVEL, new ShovelItem(ModToolTiers.SPONGE, 1.5f, -3f, new Item.Settings()));
        SPONGE_AXE = reg(GeneratedRegistrationIds.Items.SPONGE_AXE, new AxeItem(ModToolTiers.SPONGE, 6, -3.2f, new Item.Settings()));
        SPONGE_HOE = reg(GeneratedRegistrationIds.Items.SPONGE_HOE, new HoeItem(ModToolTiers.SPONGE, 0, -3f, new Item.Settings()));
        BONE_SWORD = reg(GeneratedRegistrationIds.Items.BONE_SWORD, new SwordItem(ModToolTiers.BONE, 3, -2.4f, new Item.Settings()));
        BONE_PICKAXE = reg(GeneratedRegistrationIds.Items.BONE_PICKAXE, new PickaxeItem(ModToolTiers.BONE, 1, -2.8f, new Item.Settings()));
        BONE_SHOVEL = reg(GeneratedRegistrationIds.Items.BONE_SHOVEL, new ShovelItem(ModToolTiers.BONE, 1.5f, -3f, new Item.Settings()));
        BONE_AXE = reg(GeneratedRegistrationIds.Items.BONE_AXE, new AxeItem(ModToolTiers.BONE, 6, -3.2f, new Item.Settings()));
        BONE_HOE = reg(GeneratedRegistrationIds.Items.BONE_HOE, new HoeItem(ModToolTiers.BONE, 0, -3f, new Item.Settings()));
        BONE_HELMET = reg(GeneratedRegistrationIds.Items.BONE_HELMET, new ArmorItem(ModArmorMaterials.BONE, ArmorItem.Type.HELMET,
                    new Item.Settings()));
        BONE_CHESTPLATE = reg(GeneratedRegistrationIds.Items.BONE_CHESTPLATE, new ArmorItem(ModArmorMaterials.BONE, ArmorItem.Type.CHESTPLATE,
                    new Item.Settings()));
        BONE_LEGGINGS = reg(GeneratedRegistrationIds.Items.BONE_LEGGINGS, new ArmorItem(ModArmorMaterials.BONE, ArmorItem.Type.LEGGINGS,
                    new Item.Settings()));
        BONE_BOOTS = reg(GeneratedRegistrationIds.Items.BONE_BOOTS, new ArmorItem(ModArmorMaterials.BONE, ArmorItem.Type.BOOTS,
                    new Item.Settings()));
        CLAY_SWORD = reg(GeneratedRegistrationIds.Items.CLAY_SWORD, new SwordItem(ModToolTiers.CLAY, 3, -2.4f, new Item.Settings()));
        CLAY_PICKAXE = reg(GeneratedRegistrationIds.Items.CLAY_PICKAXE, new PickaxeItem(ModToolTiers.CLAY, 1, -2.8f, new Item.Settings()));
        CLAY_SHOVEL = reg(GeneratedRegistrationIds.Items.CLAY_SHOVEL, new ShovelItem(ModToolTiers.CLAY, 1.5f, -3f, new Item.Settings()));
        CLAY_AXE = reg(GeneratedRegistrationIds.Items.CLAY_AXE, new AxeItem(ModToolTiers.CLAY, 6, -3.2f, new Item.Settings()));
        CLAY_HOE = reg(GeneratedRegistrationIds.Items.CLAY_HOE, new HoeItem(ModToolTiers.CLAY, 0, -3f, new Item.Settings()));
        CLAY_HELMET = reg(GeneratedRegistrationIds.Items.CLAY_HELMET, new ArmorItem(ModArmorMaterials.CLAY, ArmorItem.Type.HELMET,
                    new Item.Settings()));
        CLAY_CHESTPLATE = reg(GeneratedRegistrationIds.Items.CLAY_CHESTPLATE, new ArmorItem(ModArmorMaterials.CLAY, ArmorItem.Type.CHESTPLATE,
                    new Item.Settings()));
        CLAY_LEGGINGS = reg(GeneratedRegistrationIds.Items.CLAY_LEGGINGS, new ArmorItem(ModArmorMaterials.CLAY, ArmorItem.Type.LEGGINGS,
                    new Item.Settings()));
        CLAY_BOOTS = reg(GeneratedRegistrationIds.Items.CLAY_BOOTS, new ArmorItem(ModArmorMaterials.CLAY, ArmorItem.Type.BOOTS,
                    new Item.Settings()));
        NETHER_WART_SWORD = reg(GeneratedRegistrationIds.Items.NETHER_WART_SWORD, new SwordItem(ModToolTiers.NETHER_WART, 3, -2.4f, new Item.Settings()));
        NETHER_WART_PICKAXE = reg(GeneratedRegistrationIds.Items.NETHER_WART_PICKAXE, new PickaxeItem(ModToolTiers.NETHER_WART, 1, -2.8f, new Item.Settings()));
        NETHER_WART_SHOVEL = reg(GeneratedRegistrationIds.Items.NETHER_WART_SHOVEL, new ShovelItem(ModToolTiers.NETHER_WART, 1.5f, -3f, new Item.Settings()));
        NETHER_WART_AXE = reg(GeneratedRegistrationIds.Items.NETHER_WART_AXE, new AxeItem(ModToolTiers.NETHER_WART, 6, -3.2f, new Item.Settings()));
        NETHER_WART_HOE = reg(GeneratedRegistrationIds.Items.NETHER_WART_HOE, new HoeItem(ModToolTiers.NETHER_WART, 0, -3f, new Item.Settings()));
        BRICK_SWORD = reg(GeneratedRegistrationIds.Items.BRICK_SWORD, new SwordItem(ModToolTiers.BRICK, 3, -2.4f, new Item.Settings()));
        BRICK_PICKAXE = reg(GeneratedRegistrationIds.Items.BRICK_PICKAXE, new PickaxeItem(ModToolTiers.BRICK, 1, -2.8f, new Item.Settings()));
        BRICK_SHOVEL = reg(GeneratedRegistrationIds.Items.BRICK_SHOVEL, new ShovelItem(ModToolTiers.BRICK, 1.5f, -3f, new Item.Settings()));
        BRICK_AXE = reg(GeneratedRegistrationIds.Items.BRICK_AXE, new AxeItem(ModToolTiers.BRICK, 6, -3.2f, new Item.Settings()));
        BRICK_HOE = reg(GeneratedRegistrationIds.Items.BRICK_HOE, new HoeItem(ModToolTiers.BRICK, 0, -3f, new Item.Settings()));
        BRICK_HELMET = reg(GeneratedRegistrationIds.Items.BRICK_HELMET, new ArmorItem(ModArmorMaterials.BRICK, ArmorItem.Type.HELMET,
                    new Item.Settings()));
        BRICK_CHESTPLATE = reg(GeneratedRegistrationIds.Items.BRICK_CHESTPLATE, new ArmorItem(ModArmorMaterials.BRICK, ArmorItem.Type.CHESTPLATE,
                    new Item.Settings()));
        BRICK_LEGGINGS = reg(GeneratedRegistrationIds.Items.BRICK_LEGGINGS, new ArmorItem(ModArmorMaterials.BRICK, ArmorItem.Type.LEGGINGS,
                    new Item.Settings()));
        BRICK_BOOTS = reg(GeneratedRegistrationIds.Items.BRICK_BOOTS, new ArmorItem(ModArmorMaterials.BRICK, ArmorItem.Type.BOOTS,
                    new Item.Settings()));
        NETHER_BRICK_SWORD = reg(GeneratedRegistrationIds.Items.NETHER_BRICK_SWORD, new SwordItem(ModToolTiers.NETHER_BRICK, 3, -2.4f, new Item.Settings()));
        NETHER_BRICK_PICKAXE = reg(GeneratedRegistrationIds.Items.NETHER_BRICK_PICKAXE, new PickaxeItem(ModToolTiers.NETHER_BRICK, 1, -2.8f, new Item.Settings()));
        NETHER_BRICK_SHOVEL = reg(GeneratedRegistrationIds.Items.NETHER_BRICK_SHOVEL, new ShovelItem(ModToolTiers.NETHER_BRICK, 1.5f, -3f, new Item.Settings()));
        NETHER_BRICK_AXE = reg(GeneratedRegistrationIds.Items.NETHER_BRICK_AXE, new AxeItem(ModToolTiers.NETHER_BRICK, 6, -3.2f, new Item.Settings()));
        NETHER_BRICK_HOE = reg(GeneratedRegistrationIds.Items.NETHER_BRICK_HOE, new HoeItem(ModToolTiers.NETHER_BRICK, 0, -3f, new Item.Settings()));
        NETHER_BRICK_HELMET = reg(GeneratedRegistrationIds.Items.NETHER_BRICK_HELMET, new ArmorItem(ModArmorMaterials.NETHER_BRICK, ArmorItem.Type.HELMET,
                    new Item.Settings()));
        NETHER_BRICK_CHESTPLATE = reg(GeneratedRegistrationIds.Items.NETHER_BRICK_CHESTPLATE, new ArmorItem(ModArmorMaterials.NETHER_BRICK, ArmorItem.Type.CHESTPLATE,
                    new Item.Settings()));
        NETHER_BRICK_LEGGINGS = reg(GeneratedRegistrationIds.Items.NETHER_BRICK_LEGGINGS, new ArmorItem(ModArmorMaterials.NETHER_BRICK, ArmorItem.Type.LEGGINGS,
                    new Item.Settings()));
        NETHER_BRICK_BOOTS = reg(GeneratedRegistrationIds.Items.NETHER_BRICK_BOOTS, new ArmorItem(ModArmorMaterials.NETHER_BRICK, ArmorItem.Type.BOOTS,
                    new Item.Settings()));
        POINTED_DRIPSTONE_SWORD = reg(GeneratedRegistrationIds.Items.POINTED_DRIPSTONE_SWORD, new SwordItem(ModToolTiers.POINTED_DRIPSTONE, 3, -2.4f, new Item.Settings()));
        POINTED_DRIPSTONE_PICKAXE = reg(GeneratedRegistrationIds.Items.POINTED_DRIPSTONE_PICKAXE, new PickaxeItem(ModToolTiers.POINTED_DRIPSTONE, 1, -2.8f, new Item.Settings()));
        POINTED_DRIPSTONE_SHOVEL = reg(GeneratedRegistrationIds.Items.POINTED_DRIPSTONE_SHOVEL, new ShovelItem(ModToolTiers.POINTED_DRIPSTONE, 1.5f, -3f, new Item.Settings()));
        POINTED_DRIPSTONE_AXE = reg(GeneratedRegistrationIds.Items.POINTED_DRIPSTONE_AXE, new AxeItem(ModToolTiers.POINTED_DRIPSTONE, 6, -3.2f, new Item.Settings()));
        POINTED_DRIPSTONE_HOE = reg(GeneratedRegistrationIds.Items.POINTED_DRIPSTONE_HOE, new HoeItem(ModToolTiers.POINTED_DRIPSTONE, 0, -3f, new Item.Settings()));
        COPPER_SWORD = reg(GeneratedRegistrationIds.Items.COPPER_SWORD, new SwordItem(ModToolTiers.COPPER, 3, -2.4f, new Item.Settings()));
        COPPER_PICKAXE = reg(GeneratedRegistrationIds.Items.COPPER_PICKAXE, new PickaxeItem(ModToolTiers.COPPER, 1, -2.8f, new Item.Settings()));
        COPPER_SHOVEL = reg(GeneratedRegistrationIds.Items.COPPER_SHOVEL, new ShovelItem(ModToolTiers.COPPER, 1.5f, -3f, new Item.Settings()));
        COPPER_AXE = reg(GeneratedRegistrationIds.Items.COPPER_AXE, new AxeItem(ModToolTiers.COPPER, 6, -3.2f, new Item.Settings()));
        COPPER_HOE = reg(GeneratedRegistrationIds.Items.COPPER_HOE, new HoeItem(ModToolTiers.COPPER, 0, -3f, new Item.Settings()));
        COPPER_HELMET = reg(GeneratedRegistrationIds.Items.COPPER_HELMET, new ArmorItem(ModArmorMaterials.COPPER, ArmorItem.Type.HELMET,
                    new Item.Settings()));
        COPPER_CHESTPLATE = reg(GeneratedRegistrationIds.Items.COPPER_CHESTPLATE, new ArmorItem(ModArmorMaterials.COPPER, ArmorItem.Type.CHESTPLATE,
                    new Item.Settings()));
        COPPER_LEGGINGS = reg(GeneratedRegistrationIds.Items.COPPER_LEGGINGS, new ArmorItem(ModArmorMaterials.COPPER, ArmorItem.Type.LEGGINGS,
                    new Item.Settings()));
        COPPER_BOOTS = reg(GeneratedRegistrationIds.Items.COPPER_BOOTS, new ArmorItem(ModArmorMaterials.COPPER, ArmorItem.Type.BOOTS,
                    new Item.Settings()));
        PHANTOM_SWORD = reg(GeneratedRegistrationIds.Items.PHANTOM_SWORD, new SwordItem(ModToolTiers.PHANTOM_MEMBRANE, 3, -2.4f, new Item.Settings()));
        PHANTOM_PICKAXE = reg(GeneratedRegistrationIds.Items.PHANTOM_PICKAXE, new PickaxeItem(ModToolTiers.PHANTOM_MEMBRANE, 1, -2.8f, new Item.Settings()));
        PHANTOM_SHOVEL = reg(GeneratedRegistrationIds.Items.PHANTOM_SHOVEL, new ShovelItem(ModToolTiers.PHANTOM_MEMBRANE, 1.5f, -3f, new Item.Settings()));
        PHANTOM_AXE = reg(GeneratedRegistrationIds.Items.PHANTOM_AXE, new AxeItem(ModToolTiers.PHANTOM_MEMBRANE, 6, -3.2f, new Item.Settings()));
        PHANTOM_HOE = reg(GeneratedRegistrationIds.Items.PHANTOM_HOE, new HoeItem(ModToolTiers.PHANTOM_MEMBRANE, 0, -3f, new Item.Settings()));
        PHANTOM_HELMET = reg(GeneratedRegistrationIds.Items.PHANTOM_HELMET, new ArmorItem(ModArmorMaterials.PHANTOM, ArmorItem.Type.HELMET,
                    new Item.Settings()));
        PHANTOM_CHESTPLATE = reg(GeneratedRegistrationIds.Items.PHANTOM_CHESTPLATE, new ArmorItem(ModArmorMaterials.PHANTOM, ArmorItem.Type.CHESTPLATE,
                    new Item.Settings()));
        PHANTOM_LEGGINGS = reg(GeneratedRegistrationIds.Items.PHANTOM_LEGGINGS, new ArmorItem(ModArmorMaterials.PHANTOM, ArmorItem.Type.LEGGINGS,
                    new Item.Settings()));
        PHANTOM_BOOTS = reg(GeneratedRegistrationIds.Items.PHANTOM_BOOTS, new ArmorItem(ModArmorMaterials.PHANTOM, ArmorItem.Type.BOOTS,
                    new Item.Settings()));
        MAGMA_CREAM_SWORD = reg(GeneratedRegistrationIds.Items.MAGMA_CREAM_SWORD, new SwordItem(ModToolTiers.MAGMA_CREAM, 3, -2.4f, new Item.Settings()));
        MAGMA_CREAM_PICKAXE = reg(GeneratedRegistrationIds.Items.MAGMA_CREAM_PICKAXE, new PickaxeItem(ModToolTiers.MAGMA_CREAM, 1, -2.8f, new Item.Settings()));
        MAGMA_CREAM_SHOVEL = reg(GeneratedRegistrationIds.Items.MAGMA_CREAM_SHOVEL, new ShovelItem(ModToolTiers.MAGMA_CREAM, 1.5f, -3f, new Item.Settings()));
        MAGMA_CREAM_AXE = reg(GeneratedRegistrationIds.Items.MAGMA_CREAM_AXE, new AxeItem(ModToolTiers.MAGMA_CREAM, 6, -3.2f, new Item.Settings()));
        MAGMA_CREAM_HOE = reg(GeneratedRegistrationIds.Items.MAGMA_CREAM_HOE, new HoeItem(ModToolTiers.MAGMA_CREAM, 0, -3f, new Item.Settings()));
        MAGMA_CREAM_HELMET = reg(GeneratedRegistrationIds.Items.MAGMA_CREAM_HELMET, new ArmorItem(ModArmorMaterials.MAGMA_CREAM, ArmorItem.Type.HELMET,
                    new Item.Settings()));
        MAGMA_CREAM_CHESTPLATE = reg(GeneratedRegistrationIds.Items.MAGMA_CREAM_CHESTPLATE, new ArmorItem(ModArmorMaterials.MAGMA_CREAM, ArmorItem.Type.CHESTPLATE,
                    new Item.Settings()));
        MAGMA_CREAM_LEGGINGS = reg(GeneratedRegistrationIds.Items.MAGMA_CREAM_LEGGINGS, new ArmorItem(ModArmorMaterials.MAGMA_CREAM, ArmorItem.Type.LEGGINGS,
                    new Item.Settings()));
        MAGMA_CREAM_BOOTS = reg(GeneratedRegistrationIds.Items.MAGMA_CREAM_BOOTS, new ArmorItem(ModArmorMaterials.MAGMA_CREAM, ArmorItem.Type.BOOTS,
                    new Item.Settings()));
        SLIME_SWORD = reg(GeneratedRegistrationIds.Items.SLIME_SWORD, new SwordItem(ModToolTiers.SLIME, 3, -2.4f, new Item.Settings()));
        SLIME_PICKAXE = reg(GeneratedRegistrationIds.Items.SLIME_PICKAXE, new PickaxeItem(ModToolTiers.SLIME, 1, -2.8f, new Item.Settings()));
        SLIME_SHOVEL = reg(GeneratedRegistrationIds.Items.SLIME_SHOVEL, new ShovelItem(ModToolTiers.SLIME, 1.5f, -3f, new Item.Settings()));
        SLIME_AXE = reg(GeneratedRegistrationIds.Items.SLIME_AXE, new AxeItem(ModToolTiers.SLIME, 6, -3.2f, new Item.Settings()));
        SLIME_HOE = reg(GeneratedRegistrationIds.Items.SLIME_HOE, new HoeItem(ModToolTiers.SLIME, 0, -3f, new Item.Settings()));
        SLIME_HELMET = reg(GeneratedRegistrationIds.Items.SLIME_HELMET, new ArmorItem(ModArmorMaterials.SLIME, ArmorItem.Type.HELMET,
                    new Item.Settings()));
        SLIME_CHESTPLATE = reg(GeneratedRegistrationIds.Items.SLIME_CHESTPLATE, new ArmorItem(ModArmorMaterials.SLIME, ArmorItem.Type.CHESTPLATE,
                    new Item.Settings()));
        SLIME_LEGGINGS = reg(GeneratedRegistrationIds.Items.SLIME_LEGGINGS, new ArmorItem(ModArmorMaterials.SLIME, ArmorItem.Type.LEGGINGS,
                    new Item.Settings()));
        SLIME_BOOTS = reg(GeneratedRegistrationIds.Items.SLIME_BOOTS, new ArmorItem(ModArmorMaterials.SLIME, ArmorItem.Type.BOOTS,
                    new Item.Settings()));
        BLAZE_SWORD = reg(GeneratedRegistrationIds.Items.BLAZE_SWORD, new SwordItem(ModToolTiers.BLAZE_ROD, 3, -2.4f, new Item.Settings()));
        BLAZE_PICKAXE = reg(GeneratedRegistrationIds.Items.BLAZE_PICKAXE, new PickaxeItem(ModToolTiers.BLAZE_ROD, 1, -2.8f, new Item.Settings()));
        BLAZE_SHOVEL = reg(GeneratedRegistrationIds.Items.BLAZE_SHOVEL, new ShovelItem(ModToolTiers.BLAZE_ROD, 1.5f, -3f, new Item.Settings()));
        BLAZE_AXE = reg(GeneratedRegistrationIds.Items.BLAZE_AXE, new AxeItem(ModToolTiers.BLAZE_ROD, 6, -3.2f, new Item.Settings()));
        BLAZE_HOE = reg(GeneratedRegistrationIds.Items.BLAZE_HOE, new HoeItem(ModToolTiers.BLAZE_ROD, 0, -3f, new Item.Settings()));
        BLAZE_HELMET = reg(GeneratedRegistrationIds.Items.BLAZE_HELMET, new ArmorItem(ModArmorMaterials.BLAZE, ArmorItem.Type.HELMET,
                    new Item.Settings()));
        BLAZE_CHESTPLATE = reg(GeneratedRegistrationIds.Items.BLAZE_CHESTPLATE, new ArmorItem(ModArmorMaterials.BLAZE, ArmorItem.Type.CHESTPLATE,
                    new Item.Settings()));
        BLAZE_LEGGINGS = reg(GeneratedRegistrationIds.Items.BLAZE_LEGGINGS, new ArmorItem(ModArmorMaterials.BLAZE, ArmorItem.Type.LEGGINGS,
                    new Item.Settings()));
        BLAZE_BOOTS = reg(GeneratedRegistrationIds.Items.BLAZE_BOOTS, new ArmorItem(ModArmorMaterials.BLAZE, ArmorItem.Type.BOOTS,
                    new Item.Settings()));
        NAUTILUS_SWORD = reg(GeneratedRegistrationIds.Items.NAUTILUS_SWORD, new SwordItem(ModToolTiers.NAUTILUS_SHELL, 3, -2.4f, new Item.Settings()));
        NAUTILUS_PICKAXE = reg(GeneratedRegistrationIds.Items.NAUTILUS_PICKAXE, new PickaxeItem(ModToolTiers.NAUTILUS_SHELL, 1, -2.8f, new Item.Settings()));
        NAUTILUS_SHOVEL = reg(GeneratedRegistrationIds.Items.NAUTILUS_SHOVEL, new ShovelItem(ModToolTiers.NAUTILUS_SHELL, 1.5f, -3f, new Item.Settings()));
        NAUTILUS_AXE = reg(GeneratedRegistrationIds.Items.NAUTILUS_AXE, new AxeItem(ModToolTiers.NAUTILUS_SHELL, 6, -3.2f, new Item.Settings()));
        NAUTILUS_HOE = reg(GeneratedRegistrationIds.Items.NAUTILUS_HOE, new HoeItem(ModToolTiers.NAUTILUS_SHELL, 0, -3f, new Item.Settings()));
        NAUTILUS_HELMET = reg(GeneratedRegistrationIds.Items.NAUTILUS_HELMET, new ArmorItem(ModArmorMaterials.NAUTILUS, ArmorItem.Type.HELMET,
                    new Item.Settings()));
        NAUTILUS_CHESTPLATE = reg(GeneratedRegistrationIds.Items.NAUTILUS_CHESTPLATE, new ArmorItem(ModArmorMaterials.NAUTILUS, ArmorItem.Type.CHESTPLATE,
                    new Item.Settings()));
        NAUTILUS_LEGGINGS = reg(GeneratedRegistrationIds.Items.NAUTILUS_LEGGINGS, new ArmorItem(ModArmorMaterials.NAUTILUS, ArmorItem.Type.LEGGINGS,
                    new Item.Settings()));
        NAUTILUS_BOOTS = reg(GeneratedRegistrationIds.Items.NAUTILUS_BOOTS, new ArmorItem(ModArmorMaterials.NAUTILUS, ArmorItem.Type.BOOTS,
                    new Item.Settings()));
        PURPUR_SWORD = reg(GeneratedRegistrationIds.Items.PURPUR_SWORD, new SwordItem(ModToolTiers.PURPUR, 3, -2.4f, new Item.Settings()));
        PURPUR_PICKAXE = reg(GeneratedRegistrationIds.Items.PURPUR_PICKAXE, new PickaxeItem(ModToolTiers.PURPUR, 1, -2.8f, new Item.Settings()));
        PURPUR_SHOVEL = reg(GeneratedRegistrationIds.Items.PURPUR_SHOVEL, new ShovelItem(ModToolTiers.PURPUR, 1.5f, -3f, new Item.Settings()));
        PURPUR_AXE = reg(GeneratedRegistrationIds.Items.PURPUR_AXE, new AxeItem(ModToolTiers.PURPUR, 6, -3.2f, new Item.Settings()));
        PURPUR_HOE = reg(GeneratedRegistrationIds.Items.PURPUR_HOE, new HoeItem(ModToolTiers.PURPUR, 0, -3f, new Item.Settings()));
        PURPUR_HELMET = reg(GeneratedRegistrationIds.Items.PURPUR_HELMET, new ArmorItem(ModArmorMaterials.PURPUR, ArmorItem.Type.HELMET,
                    new Item.Settings()));
        PURPUR_CHESTPLATE = reg(GeneratedRegistrationIds.Items.PURPUR_CHESTPLATE, new ArmorItem(ModArmorMaterials.PURPUR, ArmorItem.Type.CHESTPLATE,
                    new Item.Settings()));
        PURPUR_LEGGINGS = reg(GeneratedRegistrationIds.Items.PURPUR_LEGGINGS, new ArmorItem(ModArmorMaterials.PURPUR, ArmorItem.Type.LEGGINGS,
                    new Item.Settings()));
        PURPUR_BOOTS = reg(GeneratedRegistrationIds.Items.PURPUR_BOOTS, new ArmorItem(ModArmorMaterials.PURPUR, ArmorItem.Type.BOOTS,
                    new Item.Settings()));
        GHAST_TEAR_SWORD = reg(GeneratedRegistrationIds.Items.GHAST_TEAR_SWORD, new SwordItem(ModToolTiers.GHAST_TEAR, 3, -2.4f, new Item.Settings()));
        GHAST_TEAR_PICKAXE = reg(GeneratedRegistrationIds.Items.GHAST_TEAR_PICKAXE, new PickaxeItem(ModToolTiers.GHAST_TEAR, 1, -2.8f, new Item.Settings()));
        GHAST_TEAR_SHOVEL = reg(GeneratedRegistrationIds.Items.GHAST_TEAR_SHOVEL, new ShovelItem(ModToolTiers.GHAST_TEAR, 1.5f, -3f, new Item.Settings()));
        GHAST_TEAR_AXE = reg(GeneratedRegistrationIds.Items.GHAST_TEAR_AXE, new AxeItem(ModToolTiers.GHAST_TEAR, 6, -3.2f, new Item.Settings()));
        GHAST_TEAR_HOE = reg(GeneratedRegistrationIds.Items.GHAST_TEAR_HOE, new HoeItem(ModToolTiers.GHAST_TEAR, 0, -3f, new Item.Settings()));
        GHAST_TEAR_HELMET = reg(GeneratedRegistrationIds.Items.GHAST_TEAR_HELMET, new ArmorItem(ModArmorMaterials.GHAST_TEAR, ArmorItem.Type.HELMET,
                    new Item.Settings()));
        GHAST_TEAR_CHESTPLATE = reg(GeneratedRegistrationIds.Items.GHAST_TEAR_CHESTPLATE, new ArmorItem(ModArmorMaterials.GHAST_TEAR, ArmorItem.Type.CHESTPLATE,
                    new Item.Settings()));
        GHAST_TEAR_LEGGINGS = reg(GeneratedRegistrationIds.Items.GHAST_TEAR_LEGGINGS, new ArmorItem(ModArmorMaterials.GHAST_TEAR, ArmorItem.Type.LEGGINGS,
                    new Item.Settings()));
        GHAST_TEAR_BOOTS = reg(GeneratedRegistrationIds.Items.GHAST_TEAR_BOOTS, new ArmorItem(ModArmorMaterials.GHAST_TEAR, ArmorItem.Type.BOOTS,
                    new Item.Settings()));
        EYE_OF_ENDER_SWORD = reg(GeneratedRegistrationIds.Items.EYE_OF_ENDER_SWORD, new SwordItem(ModToolTiers.EYE_OF_ENDER, 3, -2.4f, new Item.Settings()));
        EYE_OF_ENDER_PICKAXE = reg(GeneratedRegistrationIds.Items.EYE_OF_ENDER_PICKAXE, new PickaxeItem(ModToolTiers.EYE_OF_ENDER, 1, -2.8f, new Item.Settings()));
        EYE_OF_ENDER_SHOVEL = reg(GeneratedRegistrationIds.Items.EYE_OF_ENDER_SHOVEL, new ShovelItem(ModToolTiers.EYE_OF_ENDER, 1.5f, -3f, new Item.Settings()));
        EYE_OF_ENDER_AXE = reg(GeneratedRegistrationIds.Items.EYE_OF_ENDER_AXE, new AxeItem(ModToolTiers.EYE_OF_ENDER, 6, -3.2f, new Item.Settings()));
        EYE_OF_ENDER_HOE = reg(GeneratedRegistrationIds.Items.EYE_OF_ENDER_HOE, new HoeItem(ModToolTiers.EYE_OF_ENDER, 0, -3f, new Item.Settings()));
        EYE_OF_ENDER_HELMET = reg(GeneratedRegistrationIds.Items.EYE_OF_ENDER_HELMET, new ArmorItem(ModArmorMaterials.EYE_OF_ENDER, ArmorItem.Type.HELMET,
                    new Item.Settings()));
        EYE_OF_ENDER_CHESTPLATE = reg(GeneratedRegistrationIds.Items.EYE_OF_ENDER_CHESTPLATE, new ArmorItem(ModArmorMaterials.EYE_OF_ENDER, ArmorItem.Type.CHESTPLATE,
                    new Item.Settings()));
        EYE_OF_ENDER_LEGGINGS = reg(GeneratedRegistrationIds.Items.EYE_OF_ENDER_LEGGINGS, new ArmorItem(ModArmorMaterials.EYE_OF_ENDER, ArmorItem.Type.LEGGINGS,
                    new Item.Settings()));
        EYE_OF_ENDER_BOOTS = reg(GeneratedRegistrationIds.Items.EYE_OF_ENDER_BOOTS, new ArmorItem(ModArmorMaterials.EYE_OF_ENDER, ArmorItem.Type.BOOTS,
                    new Item.Settings()));
        SHULKER_SWORD = reg(GeneratedRegistrationIds.Items.SHULKER_SWORD, new SwordItem(ModToolTiers.SHULKER_SHELL, 3, -2.4f, new Item.Settings()));
        SHULKER_PICKAXE = reg(GeneratedRegistrationIds.Items.SHULKER_PICKAXE, new PickaxeItem(ModToolTiers.SHULKER_SHELL, 1, -2.8f, new Item.Settings()));
        SHULKER_SHOVEL = reg(GeneratedRegistrationIds.Items.SHULKER_SHOVEL, new ShovelItem(ModToolTiers.SHULKER_SHELL, 1.5f, -3f, new Item.Settings()));
        SHULKER_AXE = reg(GeneratedRegistrationIds.Items.SHULKER_AXE, new AxeItem(ModToolTiers.SHULKER_SHELL, 6, -3.2f, new Item.Settings()));
        SHULKER_HOE = reg(GeneratedRegistrationIds.Items.SHULKER_HOE, new HoeItem(ModToolTiers.SHULKER_SHELL, 0, -3f, new Item.Settings()));
        SHULKER_HELMET = reg(GeneratedRegistrationIds.Items.SHULKER_HELMET, new ArmorItem(ModArmorMaterials.SHULKER, ArmorItem.Type.HELMET,
                    new Item.Settings()));
        SHULKER_CHESTPLATE = reg(GeneratedRegistrationIds.Items.SHULKER_CHESTPLATE, new ArmorItem(ModArmorMaterials.SHULKER, ArmorItem.Type.CHESTPLATE,
                    new Item.Settings()));
        SHULKER_LEGGINGS = reg(GeneratedRegistrationIds.Items.SHULKER_LEGGINGS, new ArmorItem(ModArmorMaterials.SHULKER, ArmorItem.Type.LEGGINGS,
                    new Item.Settings()));
        SHULKER_BOOTS = reg(GeneratedRegistrationIds.Items.SHULKER_BOOTS, new ArmorItem(ModArmorMaterials.SHULKER, ArmorItem.Type.BOOTS,
                    new Item.Settings()));
        TURTLE_SCUTE_HELMET = reg(GeneratedRegistrationIds.Items.TURTLE_SCUTE_HELMET, new ArmorItem(ModArmorMaterials.TURTLE_SCUTE, ArmorItem.Type.HELMET,
                    new Item.Settings()));
        TURTLE_SCUTE_CHESTPLATE = reg(GeneratedRegistrationIds.Items.TURTLE_SCUTE_CHESTPLATE, new ArmorItem(ModArmorMaterials.TURTLE_SCUTE, ArmorItem.Type.CHESTPLATE,
                    new Item.Settings()));
        TURTLE_SCUTE_LEGGINGS = reg(GeneratedRegistrationIds.Items.TURTLE_SCUTE_LEGGINGS, new ArmorItem(ModArmorMaterials.TURTLE_SCUTE, ArmorItem.Type.LEGGINGS,
                    new Item.Settings()));
        TURTLE_SCUTE_BOOTS = reg(GeneratedRegistrationIds.Items.TURTLE_SCUTE_BOOTS, new ArmorItem(ModArmorMaterials.TURTLE_SCUTE, ArmorItem.Type.BOOTS,
                    new Item.Settings()));
        ECHO_SHARD_SWORD = reg(GeneratedRegistrationIds.Items.ECHO_SHARD_SWORD, new SwordItem(ModToolTiers.ECHO_SHARD, 3, -2.4f, new Item.Settings()));
        ECHO_SHARD_PICKAXE = reg(GeneratedRegistrationIds.Items.ECHO_SHARD_PICKAXE, new PickaxeItem(ModToolTiers.ECHO_SHARD, 1, -2.8f, new Item.Settings()));
        ECHO_SHARD_SHOVEL = reg(GeneratedRegistrationIds.Items.ECHO_SHARD_SHOVEL, new ShovelItem(ModToolTiers.ECHO_SHARD, 1.5f, -3f, new Item.Settings()));
        ECHO_SHARD_AXE = reg(GeneratedRegistrationIds.Items.ECHO_SHARD_AXE, new AxeItem(ModToolTiers.ECHO_SHARD, 6, -3.2f, new Item.Settings()));
        ECHO_SHARD_HOE = reg(GeneratedRegistrationIds.Items.ECHO_SHARD_HOE, new HoeItem(ModToolTiers.ECHO_SHARD, 0, -3f, new Item.Settings()));
        ECHO_SHARD_HELMET = reg(GeneratedRegistrationIds.Items.ECHO_SHARD_HELMET, new ArmorItem(ModArmorMaterials.ECHO_SHARD, ArmorItem.Type.HELMET,
                    new Item.Settings()));
        ECHO_SHARD_CHESTPLATE = reg(GeneratedRegistrationIds.Items.ECHO_SHARD_CHESTPLATE, new ArmorItem(ModArmorMaterials.ECHO_SHARD, ArmorItem.Type.CHESTPLATE,
                    new Item.Settings()));
        ECHO_SHARD_LEGGINGS = reg(GeneratedRegistrationIds.Items.ECHO_SHARD_LEGGINGS, new ArmorItem(ModArmorMaterials.ECHO_SHARD, ArmorItem.Type.LEGGINGS,
                    new Item.Settings()));
        ECHO_SHARD_BOOTS = reg(GeneratedRegistrationIds.Items.ECHO_SHARD_BOOTS, new ArmorItem(ModArmorMaterials.ECHO_SHARD, ArmorItem.Type.BOOTS,
                    new Item.Settings()));
        DRAGON_BREATH_SWORD = reg(GeneratedRegistrationIds.Items.DRAGON_BREATH_SWORD, new SwordItem(ModToolTiers.DRAGON_BREATH, 3, -2.4f, new Item.Settings()));
        DRAGON_BREATH_PICKAXE = reg(GeneratedRegistrationIds.Items.DRAGON_BREATH_PICKAXE, new PickaxeItem(ModToolTiers.DRAGON_BREATH, 1, -2.8f, new Item.Settings()));
        DRAGON_BREATH_SHOVEL = reg(GeneratedRegistrationIds.Items.DRAGON_BREATH_SHOVEL, new ShovelItem(ModToolTiers.DRAGON_BREATH, 1.5f, -3f, new Item.Settings()));
        DRAGON_BREATH_AXE = reg(GeneratedRegistrationIds.Items.DRAGON_BREATH_AXE, new AxeItem(ModToolTiers.DRAGON_BREATH, 6, -3.2f, new Item.Settings()));
        DRAGON_BREATH_HOE = reg(GeneratedRegistrationIds.Items.DRAGON_BREATH_HOE, new HoeItem(ModToolTiers.DRAGON_BREATH, 0, -3f, new Item.Settings()));
        DRAGON_BREATH_HELMET = reg(GeneratedRegistrationIds.Items.DRAGON_BREATH_HELMET, new ArmorItem(ModArmorMaterials.DRAGON_BREATH, ArmorItem.Type.HELMET,
                    new Item.Settings()));
        DRAGON_BREATH_CHESTPLATE = reg(GeneratedRegistrationIds.Items.DRAGON_BREATH_CHESTPLATE, new ArmorItem(ModArmorMaterials.DRAGON_BREATH, ArmorItem.Type.CHESTPLATE,
                    new Item.Settings()));
        DRAGON_BREATH_LEGGINGS = reg(GeneratedRegistrationIds.Items.DRAGON_BREATH_LEGGINGS, new ArmorItem(ModArmorMaterials.DRAGON_BREATH, ArmorItem.Type.LEGGINGS,
                    new Item.Settings()));
        DRAGON_BREATH_BOOTS = reg(GeneratedRegistrationIds.Items.DRAGON_BREATH_BOOTS, new ArmorItem(ModArmorMaterials.DRAGON_BREATH, ArmorItem.Type.BOOTS,
                    new Item.Settings()));
        CAKE_SWORD = reg(GeneratedRegistrationIds.Items.CAKE_SWORD, new EdibleSwordItem(ModToolTiers.CAKE, new Item.Settings()
                    .food(new FoodComponent.Builder().hunger(4).saturationModifier(0.1f).build())));
        CAKE_PICKAXE = reg(GeneratedRegistrationIds.Items.CAKE_PICKAXE, new EdiblePickaxeItem(ModToolTiers.CAKE, new Item.Settings()
                    .food(new FoodComponent.Builder().hunger(6).saturationModifier(0.1f).build())));
        CAKE_SHOVEL = reg(GeneratedRegistrationIds.Items.CAKE_SHOVEL, new EdibleShovelItem(ModToolTiers.CAKE, new Item.Settings()
                    .food(new FoodComponent.Builder().hunger(2).saturationModifier(0.1f).build())));
        CAKE_AXE = reg(GeneratedRegistrationIds.Items.CAKE_AXE, new EdibleAxeItem(ModToolTiers.CAKE, new Item.Settings()
                    .food(new FoodComponent.Builder().hunger(6).saturationModifier(0.1f).build())));
        CAKE_HOE = reg(GeneratedRegistrationIds.Items.CAKE_HOE, new EdibleHoeItem(ModToolTiers.CAKE, new Item.Settings()
                    .food(new FoodComponent.Builder().hunger(4).saturationModifier(0.1f).build())));
        CAKE_HELMET = reg(GeneratedRegistrationIds.Items.CAKE_HELMET, new EdibleArmorItem(ModArmorMaterials.CAKE, ArmorItem.Type.HELMET,
                    new Item.Settings()
                            .food(new FoodComponent.Builder().hunger(10).saturationModifier(0.1f).build())));
        CAKE_CHESTPLATE = reg(GeneratedRegistrationIds.Items.CAKE_CHESTPLATE, new EdibleArmorItem(ModArmorMaterials.CAKE, ArmorItem.Type.CHESTPLATE,
                    new Item.Settings()
                            .food(new FoodComponent.Builder().hunger(14).saturationModifier(0.1f).build())));
        CAKE_LEGGINGS = reg(GeneratedRegistrationIds.Items.CAKE_LEGGINGS, new EdibleArmorItem(ModArmorMaterials.CAKE, ArmorItem.Type.LEGGINGS,
                    new Item.Settings()
                            .food(new FoodComponent.Builder().hunger(14).saturationModifier(0.1f).build())));
        CAKE_BOOTS = reg(GeneratedRegistrationIds.Items.CAKE_BOOTS, new EdibleArmorItem(ModArmorMaterials.CAKE, ArmorItem.Type.BOOTS,
                    new Item.Settings()
                            .food(new FoodComponent.Builder().hunger(8).saturationModifier(0.1f).build())));
        BREAD_SWORD = reg(GeneratedRegistrationIds.Items.BREAD_SWORD, new EdibleSwordItem(ModToolTiers.BREAD, new Item.Settings()
                    .food(new FoodComponent.Builder().hunger(4).saturationModifier(0.1f).build())));
        BREAD_PICKAXE = reg(GeneratedRegistrationIds.Items.BREAD_PICKAXE, new EdiblePickaxeItem(ModToolTiers.BREAD, new Item.Settings()
                    .food(new FoodComponent.Builder().hunger(6).saturationModifier(0.1f).build())));
        BREAD_SHOVEL = reg(GeneratedRegistrationIds.Items.BREAD_SHOVEL, new EdibleShovelItem(ModToolTiers.BREAD, new Item.Settings()
                    .food(new FoodComponent.Builder().hunger(2).saturationModifier(0.1f).build())));
        BREAD_AXE = reg(GeneratedRegistrationIds.Items.BREAD_AXE, new EdibleAxeItem(ModToolTiers.BREAD, new Item.Settings()
                    .food(new FoodComponent.Builder().hunger(6).saturationModifier(0.1f).build())));
        BREAD_HOE = reg(GeneratedRegistrationIds.Items.BREAD_HOE, new EdibleHoeItem(ModToolTiers.BREAD, new Item.Settings()
                    .food(new FoodComponent.Builder().hunger(4).saturationModifier(0.1f).build())));
        BREAD_HELMET = reg(GeneratedRegistrationIds.Items.BREAD_HELMET, new EdibleArmorItem(ModArmorMaterials.BREAD, ArmorItem.Type.HELMET,
                    new Item.Settings()
                            .food(new FoodComponent.Builder().hunger(10).saturationModifier(0.1f).build())));
        BREAD_CHESTPLATE = reg(GeneratedRegistrationIds.Items.BREAD_CHESTPLATE, new EdibleArmorItem(ModArmorMaterials.BREAD, ArmorItem.Type.CHESTPLATE,
                    new Item.Settings()
                            .food(new FoodComponent.Builder().hunger(14).saturationModifier(0.1f).build())));
        BREAD_LEGGINGS = reg(GeneratedRegistrationIds.Items.BREAD_LEGGINGS, new EdibleArmorItem(ModArmorMaterials.BREAD, ArmorItem.Type.LEGGINGS,
                    new Item.Settings()
                            .food(new FoodComponent.Builder().hunger(14).saturationModifier(0.1f).build())));
        BREAD_BOOTS = reg(GeneratedRegistrationIds.Items.BREAD_BOOTS, new EdibleArmorItem(ModArmorMaterials.BREAD, ArmorItem.Type.BOOTS,
                    new Item.Settings()
                            .food(new FoodComponent.Builder().hunger(8).saturationModifier(0.1f).build())));
        DRIED_KELP_SWORD = reg(GeneratedRegistrationIds.Items.DRIED_KELP_SWORD, new EdibleSwordItem(ModToolTiers.DRIED_KELP, new Item.Settings()
                    .food(new FoodComponent.Builder().hunger(4).saturationModifier(0.1f).build())));
        DRIED_KELP_PICKAXE = reg(GeneratedRegistrationIds.Items.DRIED_KELP_PICKAXE, new EdiblePickaxeItem(ModToolTiers.DRIED_KELP, new Item.Settings()
                    .food(new FoodComponent.Builder().hunger(6).saturationModifier(0.1f).build())));
        DRIED_KELP_SHOVEL = reg(GeneratedRegistrationIds.Items.DRIED_KELP_SHOVEL, new EdibleShovelItem(ModToolTiers.DRIED_KELP, new Item.Settings()
                    .food(new FoodComponent.Builder().hunger(2).saturationModifier(0.1f).build())));
        DRIED_KELP_AXE = reg(GeneratedRegistrationIds.Items.DRIED_KELP_AXE, new EdibleAxeItem(ModToolTiers.DRIED_KELP, new Item.Settings()
                    .food(new FoodComponent.Builder().hunger(6).saturationModifier(0.1f).build())));
        DRIED_KELP_HOE = reg(GeneratedRegistrationIds.Items.DRIED_KELP_HOE, new EdibleHoeItem(ModToolTiers.DRIED_KELP, new Item.Settings()
                    .food(new FoodComponent.Builder().hunger(4).saturationModifier(0.1f).build())));
        DRIED_KELP_HELMET = reg(GeneratedRegistrationIds.Items.DRIED_KELP_HELMET, new EdibleArmorItem(ModArmorMaterials.DRIED_KELP, ArmorItem.Type.HELMET,
                    new Item.Settings()
                            .food(new FoodComponent.Builder().hunger(10).saturationModifier(0.1f).build())));
        DRIED_KELP_CHESTPLATE = reg(GeneratedRegistrationIds.Items.DRIED_KELP_CHESTPLATE, new EdibleArmorItem(ModArmorMaterials.DRIED_KELP, ArmorItem.Type.CHESTPLATE,
                    new Item.Settings()
                            .food(new FoodComponent.Builder().hunger(14).saturationModifier(0.1f).build())));
        DRIED_KELP_LEGGINGS = reg(GeneratedRegistrationIds.Items.DRIED_KELP_LEGGINGS, new EdibleArmorItem(ModArmorMaterials.DRIED_KELP, ArmorItem.Type.LEGGINGS,
                    new Item.Settings()
                            .food(new FoodComponent.Builder().hunger(14).saturationModifier(0.1f).build())));
        DRIED_KELP_BOOTS = reg(GeneratedRegistrationIds.Items.DRIED_KELP_BOOTS, new EdibleArmorItem(ModArmorMaterials.DRIED_KELP, ArmorItem.Type.BOOTS,
                    new Item.Settings()
                            .food(new FoodComponent.Builder().hunger(8).saturationModifier(0.1f).build())));
        ROTTEN_FLESH_SWORD = reg(GeneratedRegistrationIds.Items.ROTTEN_FLESH_SWORD, new EdibleSwordItem(ModToolTiers.ROTTEN_FLESH, new Item.Settings()
                    .food(new FoodComponent.Builder().hunger(4).saturationModifier(0.1f).build())));
        ROTTEN_FLESH_PICKAXE = reg(GeneratedRegistrationIds.Items.ROTTEN_FLESH_PICKAXE, new EdiblePickaxeItem(ModToolTiers.ROTTEN_FLESH, new Item.Settings()
                    .food(new FoodComponent.Builder().hunger(6).saturationModifier(0.1f).build())));
        ROTTEN_FLESH_SHOVEL = reg(GeneratedRegistrationIds.Items.ROTTEN_FLESH_SHOVEL, new EdibleShovelItem(ModToolTiers.ROTTEN_FLESH, new Item.Settings()
                    .food(new FoodComponent.Builder().hunger(2).saturationModifier(0.1f).build())));
        ROTTEN_FLESH_AXE = reg(GeneratedRegistrationIds.Items.ROTTEN_FLESH_AXE, new EdibleAxeItem(ModToolTiers.ROTTEN_FLESH, new Item.Settings()
                    .food(new FoodComponent.Builder().hunger(6).saturationModifier(0.1f).build())));
        ROTTEN_FLESH_HOE = reg(GeneratedRegistrationIds.Items.ROTTEN_FLESH_HOE, new EdibleHoeItem(ModToolTiers.ROTTEN_FLESH, new Item.Settings()
                    .food(new FoodComponent.Builder().hunger(4).saturationModifier(0.1f).build())));
        ROTTEN_FLESH_HELMET = reg(GeneratedRegistrationIds.Items.ROTTEN_FLESH_HELMET, new EdibleArmorItem(ModArmorMaterials.ROTTEN_FLESH, ArmorItem.Type.HELMET,
                    new Item.Settings()
                            .food(new FoodComponent.Builder().hunger(10).saturationModifier(0.1f).build())));
        ROTTEN_FLESH_CHESTPLATE = reg(GeneratedRegistrationIds.Items.ROTTEN_FLESH_CHESTPLATE, new EdibleArmorItem(ModArmorMaterials.ROTTEN_FLESH, ArmorItem.Type.CHESTPLATE,
                    new Item.Settings()
                            .food(new FoodComponent.Builder().hunger(14).saturationModifier(0.1f).build())));
        ROTTEN_FLESH_LEGGINGS = reg(GeneratedRegistrationIds.Items.ROTTEN_FLESH_LEGGINGS, new EdibleArmorItem(ModArmorMaterials.ROTTEN_FLESH, ArmorItem.Type.LEGGINGS,
                    new Item.Settings()
                            .food(new FoodComponent.Builder().hunger(14).saturationModifier(0.1f).build())));
        ROTTEN_FLESH_BOOTS = reg(GeneratedRegistrationIds.Items.ROTTEN_FLESH_BOOTS, new EdibleArmorItem(ModArmorMaterials.ROTTEN_FLESH, ArmorItem.Type.BOOTS,
                    new Item.Settings()
                            .food(new FoodComponent.Builder().hunger(8).saturationModifier(0.1f).build())));
        MELON_SWORD = reg(GeneratedRegistrationIds.Items.MELON_SWORD, new EdibleSwordItem(ModToolTiers.MELON, new Item.Settings()
                    .food(new FoodComponent.Builder().hunger(4).saturationModifier(0.1f).build())));
        MELON_PICKAXE = reg(GeneratedRegistrationIds.Items.MELON_PICKAXE, new EdiblePickaxeItem(ModToolTiers.MELON, new Item.Settings()
                    .food(new FoodComponent.Builder().hunger(6).saturationModifier(0.1f).build())));
        MELON_SHOVEL = reg(GeneratedRegistrationIds.Items.MELON_SHOVEL, new EdibleShovelItem(ModToolTiers.MELON, new Item.Settings()
                    .food(new FoodComponent.Builder().hunger(2).saturationModifier(0.1f).build())));
        MELON_AXE = reg(GeneratedRegistrationIds.Items.MELON_AXE, new EdibleAxeItem(ModToolTiers.MELON, new Item.Settings()
                    .food(new FoodComponent.Builder().hunger(6).saturationModifier(0.1f).build())));
        MELON_HOE = reg(GeneratedRegistrationIds.Items.MELON_HOE, new EdibleHoeItem(ModToolTiers.MELON, new Item.Settings()
                    .food(new FoodComponent.Builder().hunger(4).saturationModifier(0.1f).build())));
        MELON_HELMET = reg(GeneratedRegistrationIds.Items.MELON_HELMET, new EdibleArmorItem(ModArmorMaterials.MELON, ArmorItem.Type.HELMET,
                    new Item.Settings()
                            .food(new FoodComponent.Builder().hunger(10).saturationModifier(0.1f).build())));
        MELON_CHESTPLATE = reg(GeneratedRegistrationIds.Items.MELON_CHESTPLATE, new EdibleArmorItem(ModArmorMaterials.MELON, ArmorItem.Type.CHESTPLATE,
                    new Item.Settings()
                            .food(new FoodComponent.Builder().hunger(14).saturationModifier(0.1f).build())));
        MELON_LEGGINGS = reg(GeneratedRegistrationIds.Items.MELON_LEGGINGS, new EdibleArmorItem(ModArmorMaterials.MELON, ArmorItem.Type.LEGGINGS,
                    new Item.Settings()
                            .food(new FoodComponent.Builder().hunger(14).saturationModifier(0.1f).build())));
        MELON_BOOTS = reg(GeneratedRegistrationIds.Items.MELON_BOOTS, new EdibleArmorItem(ModArmorMaterials.MELON, ArmorItem.Type.BOOTS,
                    new Item.Settings()
                            .food(new FoodComponent.Builder().hunger(8).saturationModifier(0.1f).build())));
        SWEET_BERRY_SWORD = reg(GeneratedRegistrationIds.Items.SWEET_BERRY_SWORD, new EdibleSwordItem(ModToolTiers.SWEET_BERRIES, new Item.Settings()
                    .food(new FoodComponent.Builder().hunger(4).saturationModifier(0.1f).build())));
        SWEET_BERRY_PICKAXE = reg(GeneratedRegistrationIds.Items.SWEET_BERRY_PICKAXE, new EdiblePickaxeItem(ModToolTiers.SWEET_BERRIES, new Item.Settings()
                    .food(new FoodComponent.Builder().hunger(6).saturationModifier(0.1f).build())));
        SWEET_BERRY_SHOVEL = reg(GeneratedRegistrationIds.Items.SWEET_BERRY_SHOVEL, new EdibleShovelItem(ModToolTiers.SWEET_BERRIES, new Item.Settings()
                    .food(new FoodComponent.Builder().hunger(2).saturationModifier(0.1f).build())));
        SWEET_BERRY_AXE = reg(GeneratedRegistrationIds.Items.SWEET_BERRY_AXE, new EdibleAxeItem(ModToolTiers.SWEET_BERRIES, new Item.Settings()
                    .food(new FoodComponent.Builder().hunger(6).saturationModifier(0.1f).build())));
        SWEET_BERRY_HOE = reg(GeneratedRegistrationIds.Items.SWEET_BERRY_HOE, new EdibleHoeItem(ModToolTiers.SWEET_BERRIES, new Item.Settings()
                    .food(new FoodComponent.Builder().hunger(4).saturationModifier(0.1f).build())));
        SWEET_BERRY_HELMET = reg(GeneratedRegistrationIds.Items.SWEET_BERRY_HELMET, new EdibleArmorItem(ModArmorMaterials.SWEET_BERRY, ArmorItem.Type.HELMET,
                    new Item.Settings()
                            .food(new FoodComponent.Builder().hunger(10).saturationModifier(0.1f).build())));
        SWEET_BERRY_CHESTPLATE = reg(GeneratedRegistrationIds.Items.SWEET_BERRY_CHESTPLATE, new EdibleArmorItem(ModArmorMaterials.SWEET_BERRY, ArmorItem.Type.CHESTPLATE,
                    new Item.Settings()
                            .food(new FoodComponent.Builder().hunger(14).saturationModifier(0.1f).build())));
        SWEET_BERRY_LEGGINGS = reg(GeneratedRegistrationIds.Items.SWEET_BERRY_LEGGINGS, new EdibleArmorItem(ModArmorMaterials.SWEET_BERRY, ArmorItem.Type.LEGGINGS,
                    new Item.Settings()
                            .food(new FoodComponent.Builder().hunger(14).saturationModifier(0.1f).build())));
        SWEET_BERRY_BOOTS = reg(GeneratedRegistrationIds.Items.SWEET_BERRY_BOOTS, new EdibleArmorItem(ModArmorMaterials.SWEET_BERRY, ArmorItem.Type.BOOTS,
                    new Item.Settings()
                            .food(new FoodComponent.Builder().hunger(8).saturationModifier(0.1f).build())));
        PUMPKIN_PIE_SWORD = reg(GeneratedRegistrationIds.Items.PUMPKIN_PIE_SWORD, new EdibleSwordItem(ModToolTiers.PUMPKIN_PIE, new Item.Settings()
                    .food(new FoodComponent.Builder().hunger(4).saturationModifier(0.1f).build())));
        PUMPKIN_PIE_PICKAXE = reg(GeneratedRegistrationIds.Items.PUMPKIN_PIE_PICKAXE, new EdiblePickaxeItem(ModToolTiers.PUMPKIN_PIE, new Item.Settings()
                    .food(new FoodComponent.Builder().hunger(6).saturationModifier(0.1f).build())));
        PUMPKIN_PIE_SHOVEL = reg(GeneratedRegistrationIds.Items.PUMPKIN_PIE_SHOVEL, new EdibleShovelItem(ModToolTiers.PUMPKIN_PIE, new Item.Settings()
                    .food(new FoodComponent.Builder().hunger(2).saturationModifier(0.1f).build())));
        PUMPKIN_PIE_AXE = reg(GeneratedRegistrationIds.Items.PUMPKIN_PIE_AXE, new EdibleAxeItem(ModToolTiers.PUMPKIN_PIE, new Item.Settings()
                    .food(new FoodComponent.Builder().hunger(6).saturationModifier(0.1f).build())));
        PUMPKIN_PIE_HOE = reg(GeneratedRegistrationIds.Items.PUMPKIN_PIE_HOE, new EdibleHoeItem(ModToolTiers.PUMPKIN_PIE, new Item.Settings()
                    .food(new FoodComponent.Builder().hunger(4).saturationModifier(0.1f).build())));
        PUMPKIN_PIE_HELMET = reg(GeneratedRegistrationIds.Items.PUMPKIN_PIE_HELMET, new EdibleArmorItem(ModArmorMaterials.PUMPKIN_PIE, ArmorItem.Type.HELMET,
                    new Item.Settings()
                            .food(new FoodComponent.Builder().hunger(10).saturationModifier(0.1f).build())));
        PUMPKIN_PIE_CHESTPLATE = reg(GeneratedRegistrationIds.Items.PUMPKIN_PIE_CHESTPLATE, new EdibleArmorItem(ModArmorMaterials.PUMPKIN_PIE, ArmorItem.Type.CHESTPLATE,
                    new Item.Settings()
                            .food(new FoodComponent.Builder().hunger(14).saturationModifier(0.1f).build())));
        PUMPKIN_PIE_LEGGINGS = reg(GeneratedRegistrationIds.Items.PUMPKIN_PIE_LEGGINGS, new EdibleArmorItem(ModArmorMaterials.PUMPKIN_PIE, ArmorItem.Type.LEGGINGS,
                    new Item.Settings()
                            .food(new FoodComponent.Builder().hunger(14).saturationModifier(0.1f).build())));
        PUMPKIN_PIE_BOOTS = reg(GeneratedRegistrationIds.Items.PUMPKIN_PIE_BOOTS, new EdibleArmorItem(ModArmorMaterials.PUMPKIN_PIE, ArmorItem.Type.BOOTS,
                    new Item.Settings()
                            .food(new FoodComponent.Builder().hunger(8).saturationModifier(0.1f).build())));
        MUSHROOM_SWORD = reg(GeneratedRegistrationIds.Items.MUSHROOM_SWORD, new EdibleSwordItem(ModToolTiers.MUSHROOM, new Item.Settings()
                    .food(new FoodComponent.Builder().hunger(4).saturationModifier(0.1f).build())));
        MUSHROOM_PICKAXE = reg(GeneratedRegistrationIds.Items.MUSHROOM_PICKAXE, new EdiblePickaxeItem(ModToolTiers.MUSHROOM, new Item.Settings()
                    .food(new FoodComponent.Builder().hunger(6).saturationModifier(0.1f).build())));
        MUSHROOM_SHOVEL = reg(GeneratedRegistrationIds.Items.MUSHROOM_SHOVEL, new EdibleShovelItem(ModToolTiers.MUSHROOM, new Item.Settings()
                    .food(new FoodComponent.Builder().hunger(2).saturationModifier(0.1f).build())));
        MUSHROOM_AXE = reg(GeneratedRegistrationIds.Items.MUSHROOM_AXE, new EdibleAxeItem(ModToolTiers.MUSHROOM, new Item.Settings()
                    .food(new FoodComponent.Builder().hunger(6).saturationModifier(0.1f).build())));
        MUSHROOM_HOE = reg(GeneratedRegistrationIds.Items.MUSHROOM_HOE, new EdibleHoeItem(ModToolTiers.MUSHROOM, new Item.Settings()
                    .food(new FoodComponent.Builder().hunger(4).saturationModifier(0.1f).build())));
        MUSHROOM_HELMET = reg(GeneratedRegistrationIds.Items.MUSHROOM_HELMET, new EdibleArmorItem(ModArmorMaterials.MUSHROOM, ArmorItem.Type.HELMET,
                    new Item.Settings()
                            .food(new FoodComponent.Builder().hunger(10).saturationModifier(0.1f).build())));
        MUSHROOM_CHESTPLATE = reg(GeneratedRegistrationIds.Items.MUSHROOM_CHESTPLATE, new EdibleArmorItem(ModArmorMaterials.MUSHROOM, ArmorItem.Type.CHESTPLATE,
                    new Item.Settings()
                            .food(new FoodComponent.Builder().hunger(14).saturationModifier(0.1f).build())));
        MUSHROOM_LEGGINGS = reg(GeneratedRegistrationIds.Items.MUSHROOM_LEGGINGS, new EdibleArmorItem(ModArmorMaterials.MUSHROOM, ArmorItem.Type.LEGGINGS,
                    new Item.Settings()
                            .food(new FoodComponent.Builder().hunger(14).saturationModifier(0.1f).build())));
        MUSHROOM_BOOTS = reg(GeneratedRegistrationIds.Items.MUSHROOM_BOOTS, new EdibleArmorItem(ModArmorMaterials.MUSHROOM, ArmorItem.Type.BOOTS,
                    new Item.Settings()
                            .food(new FoodComponent.Builder().hunger(8).saturationModifier(0.1f).build())));
        PUFFERFISH_SWORD = reg(GeneratedRegistrationIds.Items.PUFFERFISH_SWORD, new EdibleSwordItem(ModToolTiers.PUFFERFISH, new Item.Settings()
                    .food(new FoodComponent.Builder().hunger(4).saturationModifier(0.1f).build())));
        PUFFERFISH_PICKAXE = reg(GeneratedRegistrationIds.Items.PUFFERFISH_PICKAXE, new EdiblePickaxeItem(ModToolTiers.PUFFERFISH, new Item.Settings()
                    .food(new FoodComponent.Builder().hunger(6).saturationModifier(0.1f).build())));
        PUFFERFISH_SHOVEL = reg(GeneratedRegistrationIds.Items.PUFFERFISH_SHOVEL, new EdibleShovelItem(ModToolTiers.PUFFERFISH, new Item.Settings()
                    .food(new FoodComponent.Builder().hunger(2).saturationModifier(0.1f).build())));
        PUFFERFISH_AXE = reg(GeneratedRegistrationIds.Items.PUFFERFISH_AXE, new EdibleAxeItem(ModToolTiers.PUFFERFISH, new Item.Settings()
                    .food(new FoodComponent.Builder().hunger(6).saturationModifier(0.1f).build())));
        PUFFERFISH_HOE = reg(GeneratedRegistrationIds.Items.PUFFERFISH_HOE, new EdibleHoeItem(ModToolTiers.PUFFERFISH, new Item.Settings()
                    .food(new FoodComponent.Builder().hunger(4).saturationModifier(0.1f).build())));
        PUFFERFISH_HELMET = reg(GeneratedRegistrationIds.Items.PUFFERFISH_HELMET, new EdibleArmorItem(ModArmorMaterials.PUFFERFISH, ArmorItem.Type.HELMET,
                    new Item.Settings()
                            .food(new FoodComponent.Builder().hunger(10).saturationModifier(0.1f).build())));
        PUFFERFISH_CHESTPLATE = reg(GeneratedRegistrationIds.Items.PUFFERFISH_CHESTPLATE, new EdibleArmorItem(ModArmorMaterials.PUFFERFISH, ArmorItem.Type.CHESTPLATE,
                    new Item.Settings()
                            .food(new FoodComponent.Builder().hunger(14).saturationModifier(0.1f).build())));
        PUFFERFISH_LEGGINGS = reg(GeneratedRegistrationIds.Items.PUFFERFISH_LEGGINGS, new EdibleArmorItem(ModArmorMaterials.PUFFERFISH, ArmorItem.Type.LEGGINGS,
                    new Item.Settings()
                            .food(new FoodComponent.Builder().hunger(14).saturationModifier(0.1f).build())));
        PUFFERFISH_BOOTS = reg(GeneratedRegistrationIds.Items.PUFFERFISH_BOOTS, new EdibleArmorItem(ModArmorMaterials.PUFFERFISH, ArmorItem.Type.BOOTS,
                    new Item.Settings()
                            .food(new FoodComponent.Builder().hunger(8).saturationModifier(0.1f).build())));
        HONEY_SWORD = reg(GeneratedRegistrationIds.Items.HONEY_SWORD, new EdibleSwordItem(ModToolTiers.HONEY, new Item.Settings()
                    .food(new FoodComponent.Builder().hunger(4).saturationModifier(0.1f).build())));
        HONEY_PICKAXE = reg(GeneratedRegistrationIds.Items.HONEY_PICKAXE, new EdiblePickaxeItem(ModToolTiers.HONEY, new Item.Settings()
                    .food(new FoodComponent.Builder().hunger(6).saturationModifier(0.1f).build())));
        HONEY_SHOVEL = reg(GeneratedRegistrationIds.Items.HONEY_SHOVEL, new EdibleShovelItem(ModToolTiers.HONEY, new Item.Settings()
                    .food(new FoodComponent.Builder().hunger(2).saturationModifier(0.1f).build())));
        HONEY_AXE = reg(GeneratedRegistrationIds.Items.HONEY_AXE, new EdibleAxeItem(ModToolTiers.HONEY, new Item.Settings()
                    .food(new FoodComponent.Builder().hunger(6).saturationModifier(0.1f).build())));
        HONEY_HOE = reg(GeneratedRegistrationIds.Items.HONEY_HOE, new EdibleHoeItem(ModToolTiers.HONEY, new Item.Settings()
                    .food(new FoodComponent.Builder().hunger(4).saturationModifier(0.1f).build())));
        HONEY_HELMET = reg(GeneratedRegistrationIds.Items.HONEY_HELMET, new EdibleArmorItem(ModArmorMaterials.HONEY, ArmorItem.Type.HELMET,
                    new Item.Settings()
                            .food(new FoodComponent.Builder().hunger(10).saturationModifier(0.1f).build())));
        HONEY_CHESTPLATE = reg(GeneratedRegistrationIds.Items.HONEY_CHESTPLATE, new EdibleArmorItem(ModArmorMaterials.HONEY, ArmorItem.Type.CHESTPLATE,
                    new Item.Settings()
                            .food(new FoodComponent.Builder().hunger(14).saturationModifier(0.1f).build())));
        HONEY_LEGGINGS = reg(GeneratedRegistrationIds.Items.HONEY_LEGGINGS, new EdibleArmorItem(ModArmorMaterials.HONEY, ArmorItem.Type.LEGGINGS,
                    new Item.Settings()
                            .food(new FoodComponent.Builder().hunger(14).saturationModifier(0.1f).build())));
        HONEY_BOOTS = reg(GeneratedRegistrationIds.Items.HONEY_BOOTS, new EdibleArmorItem(ModArmorMaterials.HONEY, ArmorItem.Type.BOOTS,
                    new Item.Settings()
                            .food(new FoodComponent.Builder().hunger(8).saturationModifier(0.1f).build())));
        CHORUS_FRUIT_SWORD = reg(GeneratedRegistrationIds.Items.CHORUS_FRUIT_SWORD, new EdibleSwordItem(ModToolTiers.CHORUS_FRUIT, new Item.Settings()
                    .food(new FoodComponent.Builder().hunger(4).saturationModifier(0.1f).build())));
        CHORUS_FRUIT_PICKAXE = reg(GeneratedRegistrationIds.Items.CHORUS_FRUIT_PICKAXE, new EdiblePickaxeItem(ModToolTiers.CHORUS_FRUIT, new Item.Settings()
                    .food(new FoodComponent.Builder().hunger(6).saturationModifier(0.1f).build())));
        CHORUS_FRUIT_SHOVEL = reg(GeneratedRegistrationIds.Items.CHORUS_FRUIT_SHOVEL, new EdibleShovelItem(ModToolTiers.CHORUS_FRUIT, new Item.Settings()
                    .food(new FoodComponent.Builder().hunger(2).saturationModifier(0.1f).build())));
        CHORUS_FRUIT_AXE = reg(GeneratedRegistrationIds.Items.CHORUS_FRUIT_AXE, new EdibleAxeItem(ModToolTiers.CHORUS_FRUIT, new Item.Settings()
                    .food(new FoodComponent.Builder().hunger(6).saturationModifier(0.1f).build())));
        CHORUS_FRUIT_HOE = reg(GeneratedRegistrationIds.Items.CHORUS_FRUIT_HOE, new EdibleHoeItem(ModToolTiers.CHORUS_FRUIT, new Item.Settings()
                    .food(new FoodComponent.Builder().hunger(4).saturationModifier(0.1f).build())));
        CHORUS_FRUIT_HELMET = reg(GeneratedRegistrationIds.Items.CHORUS_FRUIT_HELMET, new EdibleArmorItem(ModArmorMaterials.CHORUS_FRUIT, ArmorItem.Type.HELMET,
                    new Item.Settings()
                            .food(new FoodComponent.Builder().hunger(10).saturationModifier(0.1f).build())));
        CHORUS_FRUIT_CHESTPLATE = reg(GeneratedRegistrationIds.Items.CHORUS_FRUIT_CHESTPLATE, new EdibleArmorItem(ModArmorMaterials.CHORUS_FRUIT, ArmorItem.Type.CHESTPLATE,
                    new Item.Settings()
                            .food(new FoodComponent.Builder().hunger(14).saturationModifier(0.1f).build())));
        CHORUS_FRUIT_LEGGINGS = reg(GeneratedRegistrationIds.Items.CHORUS_FRUIT_LEGGINGS, new EdibleArmorItem(ModArmorMaterials.CHORUS_FRUIT, ArmorItem.Type.LEGGINGS,
                    new Item.Settings()
                            .food(new FoodComponent.Builder().hunger(14).saturationModifier(0.1f).build())));
        CHORUS_FRUIT_BOOTS = reg(GeneratedRegistrationIds.Items.CHORUS_FRUIT_BOOTS, new EdibleArmorItem(ModArmorMaterials.CHORUS_FRUIT, ArmorItem.Type.BOOTS,
                    new Item.Settings()
                            .food(new FoodComponent.Builder().hunger(8).saturationModifier(0.1f).build())));
        GOLDEN_APPLE_SWORD = reg(GeneratedRegistrationIds.Items.GOLDEN_APPLE_SWORD, new EdibleSwordItem(ModToolTiers.GOLDEN_APPLE, new Item.Settings()
                    .food(new FoodComponent.Builder().hunger(4).saturationModifier(0.1f).build())));
        GOLDEN_APPLE_PICKAXE = reg(GeneratedRegistrationIds.Items.GOLDEN_APPLE_PICKAXE, new EdiblePickaxeItem(ModToolTiers.GOLDEN_APPLE, new Item.Settings()
                    .food(new FoodComponent.Builder().hunger(6).saturationModifier(0.1f).build())));
        GOLDEN_APPLE_SHOVEL = reg(GeneratedRegistrationIds.Items.GOLDEN_APPLE_SHOVEL, new EdibleShovelItem(ModToolTiers.GOLDEN_APPLE, new Item.Settings()
                    .food(new FoodComponent.Builder().hunger(2).saturationModifier(0.1f).build())));
        GOLDEN_APPLE_AXE = reg(GeneratedRegistrationIds.Items.GOLDEN_APPLE_AXE, new EdibleAxeItem(ModToolTiers.GOLDEN_APPLE, new Item.Settings()
                    .food(new FoodComponent.Builder().hunger(6).saturationModifier(0.1f).build())));
        GOLDEN_APPLE_HOE = reg(GeneratedRegistrationIds.Items.GOLDEN_APPLE_HOE, new EdibleHoeItem(ModToolTiers.GOLDEN_APPLE, new Item.Settings()
                    .food(new FoodComponent.Builder().hunger(4).saturationModifier(0.1f).build())));
        GOLDEN_APPLE_HELMET = reg(GeneratedRegistrationIds.Items.GOLDEN_APPLE_HELMET, new EdibleArmorItem(ModArmorMaterials.GOLDEN_APPLE, ArmorItem.Type.HELMET,
                    new Item.Settings()
                            .food(new FoodComponent.Builder().hunger(10).saturationModifier(0.1f).build())));
        GOLDEN_APPLE_CHESTPLATE = reg(GeneratedRegistrationIds.Items.GOLDEN_APPLE_CHESTPLATE, new EdibleArmorItem(ModArmorMaterials.GOLDEN_APPLE, ArmorItem.Type.CHESTPLATE,
                    new Item.Settings()
                            .food(new FoodComponent.Builder().hunger(14).saturationModifier(0.1f).build())));
        GOLDEN_APPLE_LEGGINGS = reg(GeneratedRegistrationIds.Items.GOLDEN_APPLE_LEGGINGS, new EdibleArmorItem(ModArmorMaterials.GOLDEN_APPLE, ArmorItem.Type.LEGGINGS,
                    new Item.Settings()
                            .food(new FoodComponent.Builder().hunger(14).saturationModifier(0.1f).build())));
        GOLDEN_APPLE_BOOTS = reg(GeneratedRegistrationIds.Items.GOLDEN_APPLE_BOOTS, new EdibleArmorItem(ModArmorMaterials.GOLDEN_APPLE, ArmorItem.Type.BOOTS,
                    new Item.Settings()
                            .food(new FoodComponent.Builder().hunger(8).saturationModifier(0.1f).build())));
    }

    private static <T extends Item> T reg(String name, T item) {
        Registry.register(Registries.ITEM, new Identifier(UsefultoolsMod.MOD_ID, name), item);
        return item;
    }
}
