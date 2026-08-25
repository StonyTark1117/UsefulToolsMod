package com.stonytark.usefultoolsmod.item;

import com.stonytark.usefultoolsmod.generated.GeneratedRegistrationIds;

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
        RGOLD = reg(GeneratedRegistrationIds.Items.RGOLD, new Item(new Item.Settings().maxCount(64)));
        RAW_RGOLD = reg(GeneratedRegistrationIds.Items.RAW_RGOLD, new Item(new Item.Settings().maxCount(64)));
        OBSHARD = reg(GeneratedRegistrationIds.Items.OBSHARD, new Item(new Item.Settings().maxCount(64)));
        SEM = reg(GeneratedRegistrationIds.Items.SEM, new Item(new Item.Settings().maxCount(64)));
        OBINGOT = reg(GeneratedRegistrationIds.Items.OBINGOT, new Item(new Item.Settings().maxCount(64)));
        HRED = reg(GeneratedRegistrationIds.Items.HRED, new Item(new Item.Settings().maxCount(64)));
        HGLOW = reg(GeneratedRegistrationIds.Items.HGLOW, new Item(new Item.Settings().maxCount(64)));
        RLAPIS = reg(GeneratedRegistrationIds.Items.RLAPIS, new Item(new Item.Settings().maxCount(64)));
        COAL_DUST = reg(GeneratedRegistrationIds.Items.COAL_DUST, new Item(new Item.Settings().maxCount(64)));
        HARDENED_COAL = reg(GeneratedRegistrationIds.Items.HARDENED_COAL, new Item(new Item.Settings().maxCount(64)));
        ECTOPLASM = reg(GeneratedRegistrationIds.Items.ECTOPLASM, new Item(new Item.Settings().maxCount(64)));
        REFINED_ECTOPLASM = reg(GeneratedRegistrationIds.Items.REFINED_ECTOPLASM, new Item(new Item.Settings().maxCount(64)));
        CALCIFIED_AMETHYST = reg(GeneratedRegistrationIds.Items.CALCIFIED_AMETHYST, new Item(new Item.Settings().maxCount(64)));
        GLACIAL_SHARD = reg(GeneratedRegistrationIds.Items.GLACIAL_SHARD, new Item(new Item.Settings().maxCount(64)));
        POLISHED_QUARTZ = reg(GeneratedRegistrationIds.Items.POLISHED_QUARTZ, new Item(new Item.Settings().maxCount(64)));
        POLISHED_PRISMARINE = reg(GeneratedRegistrationIds.Items.POLISHED_PRISMARINE, new Item(new Item.Settings().maxCount(64)));

        // Throwables
        GRENADE = reg(GeneratedRegistrationIds.Items.GRENADE, new Grenade(new Item.Settings().maxCount(16)));
        DYNAMITE = reg(GeneratedRegistrationIds.Items.DYNAMITE, new Dynamite(new Item.Settings().maxCount(16).fireproof()));

        // Remerald
        REMERALD_SWORD = reg(GeneratedRegistrationIds.Items.REMERALD_SWORD, new SwordItem(ModToolTiers.REMERALD,
                new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ModToolTiers.REMERALD, 3, -2.4f))));
        REMERALD_PICKAXE = reg(GeneratedRegistrationIds.Items.REMERALD_PICKAXE, new PickaxeItem(ModToolTiers.REMERALD,
                new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolTiers.REMERALD, 1, -2.8f))));
        REMERALD_SHOVEL = reg(GeneratedRegistrationIds.Items.REMERALD_SHOVEL, new ShovelItem(ModToolTiers.REMERALD,
                new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolTiers.REMERALD, 1.5f, -3f))));
        REMERALD_AXE = reg(GeneratedRegistrationIds.Items.REMERALD_AXE, new AxeItem(ModToolTiers.REMERALD,
                new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolTiers.REMERALD, 6, -3.2f))));
        REMERALD_HOE = reg(GeneratedRegistrationIds.Items.REMERALD_HOE, new HoeItem(ModToolTiers.REMERALD,
                new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ModToolTiers.REMERALD, 0, -3f))));

        // Pemerald
        PEMERALD_SWORD = reg(GeneratedRegistrationIds.Items.PEMERALD_SWORD, new SwordItem(ModToolTiers.PEMERALD,
                new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ModToolTiers.PEMERALD, 3, -2.4f))));
        PEMERALD_PICKAXE = reg(GeneratedRegistrationIds.Items.PEMERALD_PICKAXE, new PickaxeItem(ModToolTiers.PEMERALD,
                new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolTiers.PEMERALD, 1, -2.8f))));
        PEMERALD_SHOVEL = reg(GeneratedRegistrationIds.Items.PEMERALD_SHOVEL, new ShovelItem(ModToolTiers.PEMERALD,
                new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolTiers.PEMERALD, 1.5f, -3f))));
        PEMERALD_AXE = reg(GeneratedRegistrationIds.Items.PEMERALD_AXE, new AxeItem(ModToolTiers.PEMERALD,
                new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolTiers.PEMERALD, 6, -3.2f))));
        PEMERALD_HOE = reg(GeneratedRegistrationIds.Items.PEMERALD_HOE, new HoeItem(ModToolTiers.PEMERALD,
                new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ModToolTiers.PEMERALD, 0, -3f))));

        // Robsidian
        ROBSIDIAN_SWORD = reg(GeneratedRegistrationIds.Items.ROBSIDIAN_SWORD, new SwordItem(ModToolTiers.ROBSIDIAN,
                new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ModToolTiers.ROBSIDIAN, 3, -2.4f))));
        ROBSIDIAN_PICKAXE = reg(GeneratedRegistrationIds.Items.ROBSIDIAN_PICKAXE, new PickaxeItem(ModToolTiers.ROBSIDIAN,
                new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolTiers.ROBSIDIAN, 1, -2.8f))));
        ROBSIDIAN_SHOVEL = reg(GeneratedRegistrationIds.Items.ROBSIDIAN_SHOVEL, new ShovelItem(ModToolTiers.ROBSIDIAN,
                new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolTiers.ROBSIDIAN, 1.5f, -3f))));
        ROBSIDIAN_AXE = reg(GeneratedRegistrationIds.Items.ROBSIDIAN_AXE, new AxeItem(ModToolTiers.ROBSIDIAN,
                new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolTiers.ROBSIDIAN, 6, -3.2f))));
        ROBSIDIAN_HOE = reg(GeneratedRegistrationIds.Items.ROBSIDIAN_HOE, new HoeItem(ModToolTiers.ROBSIDIAN,
                new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ModToolTiers.ROBSIDIAN, 0, -3f))));

        // Pobsidian
        POBSIDIAN_SWORD = reg(GeneratedRegistrationIds.Items.POBSIDIAN_SWORD, new SwordItem(ModToolTiers.POBSIDIAN,
                new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ModToolTiers.POBSIDIAN, 3, -2.4f))));
        POBSIDIAN_PICKAXE = reg(GeneratedRegistrationIds.Items.POBSIDIAN_PICKAXE, new PickaxeItem(ModToolTiers.POBSIDIAN,
                new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolTiers.POBSIDIAN, 1, -2.8f))));
        POBSIDIAN_SHOVEL = reg(GeneratedRegistrationIds.Items.POBSIDIAN_SHOVEL, new ShovelItem(ModToolTiers.POBSIDIAN,
                new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolTiers.POBSIDIAN, 1.5f, -3f))));
        POBSIDIAN_AXE = reg(GeneratedRegistrationIds.Items.POBSIDIAN_AXE, new AxeItem(ModToolTiers.POBSIDIAN,
                new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolTiers.POBSIDIAN, 6, -3.2f))));
        POBSIDIAN_HOE = reg(GeneratedRegistrationIds.Items.POBSIDIAN_HOE, new HoeItem(ModToolTiers.POBSIDIAN,
                new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ModToolTiers.POBSIDIAN, 0, -3f))));

        // Overpower
        OVERPOWER_SWORD = reg(GeneratedRegistrationIds.Items.OVERPOWER_SWORD, new SwordItem(ModToolTiers.OVERPOWER,
                new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ModToolTiers.OVERPOWER, 3, -2.4f))));
        OVERPOWER_PICKAXE = reg(GeneratedRegistrationIds.Items.OVERPOWER_PICKAXE, new PickaxeItem(ModToolTiers.OVERPOWER,
                new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolTiers.OVERPOWER, 1, -2.8f))));
        OVERPOWER_SHOVEL = reg(GeneratedRegistrationIds.Items.OVERPOWER_SHOVEL, new ShovelItem(ModToolTiers.OVERPOWER,
                new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolTiers.OVERPOWER, 1.5f, -3f))));
        OVERPOWER_AXE = reg(GeneratedRegistrationIds.Items.OVERPOWER_AXE, new AxeItem(ModToolTiers.OVERPOWER,
                new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolTiers.OVERPOWER, 6, -3.2f))));

        // Hredstone
        HREDSTONE_SWORD = reg(GeneratedRegistrationIds.Items.HREDSTONE_SWORD, new SwordItem(ModToolTiers.HREDSTONE,
                new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ModToolTiers.HREDSTONE, 3, -2.4f))));
        HREDSTONE_PICKAXE = reg(GeneratedRegistrationIds.Items.HREDSTONE_PICKAXE, new PickaxeItem(ModToolTiers.HREDSTONE,
                new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolTiers.HREDSTONE, 1, -2.8f))));
        HREDSTONE_SHOVEL = reg(GeneratedRegistrationIds.Items.HREDSTONE_SHOVEL, new ShovelItem(ModToolTiers.HREDSTONE,
                new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolTiers.HREDSTONE, 1.5f, -3f))));
        HREDSTONE_AXE = reg(GeneratedRegistrationIds.Items.HREDSTONE_AXE, new AxeItem(ModToolTiers.HREDSTONE,
                new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolTiers.HREDSTONE, 6, -3.2f))));
        HREDSTONE_HOE = reg(GeneratedRegistrationIds.Items.HREDSTONE_HOE, new HoeItem(ModToolTiers.HREDSTONE,
                new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ModToolTiers.HREDSTONE, 0, -3f))));

        // Hglowstone
        HGLOWSTONE_SWORD = reg(GeneratedRegistrationIds.Items.HGLOWSTONE_SWORD, new SwordItem(ModToolTiers.HGLOWSTONE,
                new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ModToolTiers.HGLOWSTONE, 3, -2.4f))));
        HGLOWSTONE_PICKAXE = reg(GeneratedRegistrationIds.Items.HGLOWSTONE_PICKAXE, new PickaxeItem(ModToolTiers.HGLOWSTONE,
                new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolTiers.HGLOWSTONE, 1, -2.8f))));
        HGLOWSTONE_SHOVEL = reg(GeneratedRegistrationIds.Items.HGLOWSTONE_SHOVEL, new ShovelItem(ModToolTiers.HGLOWSTONE,
                new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolTiers.HGLOWSTONE, 1.5f, -3f))));
        HGLOWSTONE_AXE = reg(GeneratedRegistrationIds.Items.HGLOWSTONE_AXE, new AxeItem(ModToolTiers.HGLOWSTONE,
                new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolTiers.HGLOWSTONE, 6, -3.2f))));
        HGLOWSTONE_HOE = reg(GeneratedRegistrationIds.Items.HGLOWSTONE_HOE, new HoeItem(ModToolTiers.HGLOWSTONE,
                new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ModToolTiers.HGLOWSTONE, 0, -3f))));

        // Rgold
        RGOLD_SWORD = reg(GeneratedRegistrationIds.Items.RGOLD_SWORD, new SwordItem(ModToolTiers.RGOLD,
                new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ModToolTiers.RGOLD, 3, -2.4f))));
        RGOLD_PICKAXE = reg(GeneratedRegistrationIds.Items.RGOLD_PICKAXE, new PickaxeItem(ModToolTiers.RGOLD,
                new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolTiers.RGOLD, 1, -2.8f))));
        RGOLD_SHOVEL = reg(GeneratedRegistrationIds.Items.RGOLD_SHOVEL, new ShovelItem(ModToolTiers.RGOLD,
                new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolTiers.RGOLD, 1.5f, -3f))));
        RGOLD_AXE = reg(GeneratedRegistrationIds.Items.RGOLD_AXE, new AxeItem(ModToolTiers.RGOLD,
                new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolTiers.RGOLD, 6, -3.2f))));
        RGOLD_HOE = reg(GeneratedRegistrationIds.Items.RGOLD_HOE, new HoeItem(ModToolTiers.RGOLD,
                new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ModToolTiers.RGOLD, 0, -3f))));

        // Rlapis
        RLAPIS_SWORD = reg(GeneratedRegistrationIds.Items.RLAPIS_SWORD, new SwordItem(ModToolTiers.RLAPIS,
                new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ModToolTiers.RLAPIS, 3, -2.4f))));
        RLAPIS_PICKAXE = reg(GeneratedRegistrationIds.Items.RLAPIS_PICKAXE, new PickaxeItem(ModToolTiers.RLAPIS,
                new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolTiers.RLAPIS, 1, -2.8f))));
        RLAPIS_SHOVEL = reg(GeneratedRegistrationIds.Items.RLAPIS_SHOVEL, new ShovelItem(ModToolTiers.RLAPIS,
                new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolTiers.RLAPIS, 1.5f, -3f))));
        RLAPIS_AXE = reg(GeneratedRegistrationIds.Items.RLAPIS_AXE, new AxeItem(ModToolTiers.RLAPIS,
                new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolTiers.RLAPIS, 6, -3.2f))));
        RLAPIS_HOE = reg(GeneratedRegistrationIds.Items.RLAPIS_HOE, new HoeItem(ModToolTiers.RLAPIS,
                new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ModToolTiers.RLAPIS, 0, -3f))));

        // Armor
        EMERALD_HELMET = reg(GeneratedRegistrationIds.Items.EMERALD_HELMET, new ArmorItem(ModArmorMaterials.EMERALD_ARMOR_MATERIAL,
                ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(33))));
        EMERALD_CHESTPLATE = reg(GeneratedRegistrationIds.Items.EMERALD_CHESTPLATE, new ArmorItem(ModArmorMaterials.EMERALD_ARMOR_MATERIAL,
                ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(33))));
        EMERALD_LEGGINGS = reg(GeneratedRegistrationIds.Items.EMERALD_LEGGINGS, new ArmorItem(ModArmorMaterials.EMERALD_ARMOR_MATERIAL,
                ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(33))));
        EMERALD_BOOTS = reg(GeneratedRegistrationIds.Items.EMERALD_BOOTS, new ArmorItem(ModArmorMaterials.EMERALD_ARMOR_MATERIAL,
                ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(33))));

        HRED_HELMET = reg(GeneratedRegistrationIds.Items.HRED_HELMET, new ArmorItem(ModArmorMaterials.HRED_ARMOR_MATERIAL,
                ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(20))));
        HRED_CHESTPLATE = reg(GeneratedRegistrationIds.Items.HRED_CHESTPLATE, new ArmorItem(ModArmorMaterials.HRED_ARMOR_MATERIAL,
                ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(20))));
        HRED_LEGGINGS = reg(GeneratedRegistrationIds.Items.HRED_LEGGINGS, new ArmorItem(ModArmorMaterials.HRED_ARMOR_MATERIAL,
                ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(20))));
        HRED_BOOTS = reg(GeneratedRegistrationIds.Items.HRED_BOOTS, new ArmorItem(ModArmorMaterials.HRED_ARMOR_MATERIAL,
                ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(20))));

        HGLOW_HELMET = reg(GeneratedRegistrationIds.Items.HGLOW_HELMET, new ArmorItem(ModArmorMaterials.HGLOW_ARMOR_MATERIAL,
                ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(18))));
        HGLOW_CHESTPLATE = reg(GeneratedRegistrationIds.Items.HGLOW_CHESTPLATE, new ArmorItem(ModArmorMaterials.HGLOW_ARMOR_MATERIAL,
                ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(18))));
        HGLOW_LEGGINGS = reg(GeneratedRegistrationIds.Items.HGLOW_LEGGINGS, new ArmorItem(ModArmorMaterials.HGLOW_ARMOR_MATERIAL,
                ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(18))));
        HGLOW_BOOTS = reg(GeneratedRegistrationIds.Items.HGLOW_BOOTS, new ArmorItem(ModArmorMaterials.HGLOW_ARMOR_MATERIAL,
                ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(18))));

        OBSIDIAN_HELMET = reg(GeneratedRegistrationIds.Items.OBSIDIAN_HELMET, new ArmorItem(ModArmorMaterials.OBSIDIAN_ARMOR_MATERIAL,
                ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(45))));
        OBSIDIAN_CHESTPLATE = reg(GeneratedRegistrationIds.Items.OBSIDIAN_CHESTPLATE, new ArmorItem(ModArmorMaterials.OBSIDIAN_ARMOR_MATERIAL,
                ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(45))));
        OBSIDIAN_LEGGINGS = reg(GeneratedRegistrationIds.Items.OBSIDIAN_LEGGINGS, new ArmorItem(ModArmorMaterials.OBSIDIAN_ARMOR_MATERIAL,
                ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(45))));
        OBSIDIAN_BOOTS = reg(GeneratedRegistrationIds.Items.OBSIDIAN_BOOTS, new ArmorItem(ModArmorMaterials.OBSIDIAN_ARMOR_MATERIAL,
                ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(45))));

        RGOLD_HELMET = reg(GeneratedRegistrationIds.Items.RGOLD_HELMET, new ArmorItem(ModArmorMaterials.RGOLD_ARMOR_MATERIAL,
                ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(18))));
        RGOLD_CHESTPLATE = reg(GeneratedRegistrationIds.Items.RGOLD_CHESTPLATE, new ArmorItem(ModArmorMaterials.RGOLD_ARMOR_MATERIAL,
                ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(18))));
        RGOLD_LEGGINGS = reg(GeneratedRegistrationIds.Items.RGOLD_LEGGINGS, new ArmorItem(ModArmorMaterials.RGOLD_ARMOR_MATERIAL,
                ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(18))));
        RGOLD_BOOTS = reg(GeneratedRegistrationIds.Items.RGOLD_BOOTS, new ArmorItem(ModArmorMaterials.RGOLD_ARMOR_MATERIAL,
                ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(18))));

        RLAPIS_HELMET = reg(GeneratedRegistrationIds.Items.RLAPIS_HELMET, new ArmorItem(ModArmorMaterials.RLAPIS_ARMOR_MATERIAL,
                ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(17))));
        RLAPIS_CHESTPLATE = reg(GeneratedRegistrationIds.Items.RLAPIS_CHESTPLATE, new ArmorItem(ModArmorMaterials.RLAPIS_ARMOR_MATERIAL,
                ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(17))));
        RLAPIS_LEGGINGS = reg(GeneratedRegistrationIds.Items.RLAPIS_LEGGINGS, new ArmorItem(ModArmorMaterials.RLAPIS_ARMOR_MATERIAL,
                ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(17))));
        RLAPIS_BOOTS = reg(GeneratedRegistrationIds.Items.RLAPIS_BOOTS, new ArmorItem(ModArmorMaterials.RLAPIS_ARMOR_MATERIAL,
                ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(17))));

        OVERPOWER_HELMET = reg(GeneratedRegistrationIds.Items.OVERPOWER_HELMET, new ModArmorItem(ModArmorMaterials.OVERPOWER_ARMOR_MATERIAL,
                ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(100))));
        OVERPOWER_CHESTPLATE = reg(GeneratedRegistrationIds.Items.OVERPOWER_CHESTPLATE, new ArmorItem(ModArmorMaterials.OVERPOWER_ARMOR_MATERIAL,
                ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(100))));
        OVERPOWER_LEGGINGS = reg(GeneratedRegistrationIds.Items.OVERPOWER_LEGGINGS, new ArmorItem(ModArmorMaterials.OVERPOWER_ARMOR_MATERIAL,
                ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(100))));
        OVERPOWER_BOOTS = reg(GeneratedRegistrationIds.Items.OVERPOWER_BOOTS, new ArmorItem(ModArmorMaterials.OVERPOWER_ARMOR_MATERIAL,
                ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(100))));

        // Coal
        COAL_SWORD = reg(GeneratedRegistrationIds.Items.COAL_SWORD, new CoalSwordItem(ModToolTiers.COAL_TOOL,
                new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ModToolTiers.COAL_TOOL, 3, -2.4f))));
        COAL_PICKAXE = reg(GeneratedRegistrationIds.Items.COAL_PICKAXE, new CoalPickaxeItem(ModToolTiers.COAL_TOOL,
                new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolTiers.COAL_TOOL, 1, -2.8f))));
        COAL_SHOVEL = reg(GeneratedRegistrationIds.Items.COAL_SHOVEL, new CoalShovelItem(ModToolTiers.COAL_TOOL,
                new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolTiers.COAL_TOOL, 1.5f, -3f))));
        COAL_AXE = reg(GeneratedRegistrationIds.Items.COAL_AXE, new CoalAxeItem(ModToolTiers.COAL_TOOL,
                new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolTiers.COAL_TOOL, 6, -3.2f))));
        COAL_HOE = reg(GeneratedRegistrationIds.Items.COAL_HOE, new CoalHoeItem(ModToolTiers.COAL_TOOL,
                new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ModToolTiers.COAL_TOOL, 0, -3f))));

        COAL_HELMET = reg(GeneratedRegistrationIds.Items.COAL_HELMET, new CoalArmorItem(ModArmorMaterials.COAL_ARMOR_MATERIAL,
                ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(5))));
        COAL_CHESTPLATE = reg(GeneratedRegistrationIds.Items.COAL_CHESTPLATE, new CoalArmorItem(ModArmorMaterials.COAL_ARMOR_MATERIAL,
                ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(8))));
        COAL_LEGGINGS = reg(GeneratedRegistrationIds.Items.COAL_LEGGINGS, new CoalArmorItem(ModArmorMaterials.COAL_ARMOR_MATERIAL,
                ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(7))));
        COAL_BOOTS = reg(GeneratedRegistrationIds.Items.COAL_BOOTS, new CoalArmorItem(ModArmorMaterials.COAL_ARMOR_MATERIAL,
                ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(5))));

        // Ectoplasm
        RECTO_SWORD = reg(GeneratedRegistrationIds.Items.RECTO_SWORD, new EctoSwordItem(ModToolTiers.RECTO,
                new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ModToolTiers.RECTO, 3, -2.4f))));
        RECTO_PICKAXE = reg(GeneratedRegistrationIds.Items.RECTO_PICKAXE, new EctoPickaxeItem(ModToolTiers.RECTO,
                new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolTiers.RECTO, 1, -2.8f))));
        RECTO_SHOVEL = reg(GeneratedRegistrationIds.Items.RECTO_SHOVEL, new EctoShovelItem(ModToolTiers.RECTO,
                new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolTiers.RECTO, 1.5f, -3f))));
        RECTO_AXE = reg(GeneratedRegistrationIds.Items.RECTO_AXE, new EctoAxeItem(ModToolTiers.RECTO,
                new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolTiers.RECTO, 6, -3.2f))));
        RECTO_HOE = reg(GeneratedRegistrationIds.Items.RECTO_HOE, new EctoHoeItem(ModToolTiers.RECTO,
                new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ModToolTiers.RECTO, 0, -3f))));

        ECTO_SWORD = reg(GeneratedRegistrationIds.Items.ECTO_SWORD, new EctoSwordItem(ModToolTiers.ECTO,
                new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ModToolTiers.ECTO, 3, -2.4f))));
        ECTO_PICKAXE = reg(GeneratedRegistrationIds.Items.ECTO_PICKAXE, new EctoPickaxeItem(ModToolTiers.ECTO,
                new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolTiers.ECTO, 1, -2.8f))));
        ECTO_SHOVEL = reg(GeneratedRegistrationIds.Items.ECTO_SHOVEL, new EctoShovelItem(ModToolTiers.ECTO,
                new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolTiers.ECTO, 1.5f, -3f))));
        ECTO_AXE = reg(GeneratedRegistrationIds.Items.ECTO_AXE, new EctoAxeItem(ModToolTiers.ECTO,
                new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolTiers.ECTO, 6, -3.2f))));
        ECTO_HOE = reg(GeneratedRegistrationIds.Items.ECTO_HOE, new EctoHoeItem(ModToolTiers.ECTO,
                new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ModToolTiers.ECTO, 0, -3f))));

        ECTO_HELMET = reg(GeneratedRegistrationIds.Items.ECTO_HELMET, new ArmorItem(ModArmorMaterials.ECTO_ARMOR_MATERIAL,
                ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(33))));
        ECTO_CHESTPLATE = reg(GeneratedRegistrationIds.Items.ECTO_CHESTPLATE, new ArmorItem(ModArmorMaterials.ECTO_ARMOR_MATERIAL,
                ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(48))));
        ECTO_LEGGINGS = reg(GeneratedRegistrationIds.Items.ECTO_LEGGINGS, new ArmorItem(ModArmorMaterials.ECTO_ARMOR_MATERIAL,
                ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(45))));
        ECTO_BOOTS = reg(GeneratedRegistrationIds.Items.ECTO_BOOTS, new ArmorItem(ModArmorMaterials.ECTO_ARMOR_MATERIAL,
                ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(33))));

        // Cake
        CAKE_SWORD = reg(GeneratedRegistrationIds.Items.CAKE_SWORD, new CakeSwordItem(ModToolTiers.CAKE_TOOL,
                new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ModToolTiers.CAKE_TOOL, 3, -2.4f))));
        CAKE_PICKAXE = reg(GeneratedRegistrationIds.Items.CAKE_PICKAXE, new CakePickaxeItem(ModToolTiers.CAKE_TOOL,
                new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolTiers.CAKE_TOOL, 1, -2.8f))));
        CAKE_SHOVEL = reg(GeneratedRegistrationIds.Items.CAKE_SHOVEL, new CakeShovelItem(ModToolTiers.CAKE_TOOL,
                new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolTiers.CAKE_TOOL, 1.5f, -3f))));
        CAKE_AXE = reg(GeneratedRegistrationIds.Items.CAKE_AXE, new CakeAxeItem(ModToolTiers.CAKE_TOOL,
                new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolTiers.CAKE_TOOL, 6, -3.2f))));
        CAKE_HOE = reg(GeneratedRegistrationIds.Items.CAKE_HOE, new CakeHoeItem(ModToolTiers.CAKE_TOOL,
                new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ModToolTiers.CAKE_TOOL, 0, -3f))));

        CAKE_HELMET = reg(GeneratedRegistrationIds.Items.CAKE_HELMET, new CakeArmorItem(ModArmorMaterials.CAKE_ARMOR_MATERIAL,
                ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(5))));
        CAKE_CHESTPLATE = reg(GeneratedRegistrationIds.Items.CAKE_CHESTPLATE, new CakeArmorItem(ModArmorMaterials.CAKE_ARMOR_MATERIAL,
                ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(8))));
        CAKE_LEGGINGS = reg(GeneratedRegistrationIds.Items.CAKE_LEGGINGS, new CakeArmorItem(ModArmorMaterials.CAKE_ARMOR_MATERIAL,
                ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(7))));
        CAKE_BOOTS = reg(GeneratedRegistrationIds.Items.CAKE_BOOTS, new CakeArmorItem(ModArmorMaterials.CAKE_ARMOR_MATERIAL,
                ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(5))));

        // Raw material tools
        regStdToolSet(GeneratedRegistrationIds.Items.toolSet("rraw_gold"), ModToolTiers.RRAW_GOLD, (t,s)->RRAW_GOLD_SWORD=s, (t,s)->RRAW_GOLD_PICKAXE=s, (t,s)->RRAW_GOLD_SHOVEL=s, (t,s)->RRAW_GOLD_AXE=s, (t,s)->RRAW_GOLD_HOE=s);
        regStdToolSet(GeneratedRegistrationIds.Items.toolSet("rraw_copper"), ModToolTiers.RRAW_COPPER, (t,s)->RRAW_COPPER_SWORD=s, (t,s)->RRAW_COPPER_PICKAXE=s, (t,s)->RRAW_COPPER_SHOVEL=s, (t,s)->RRAW_COPPER_AXE=s, (t,s)->RRAW_COPPER_HOE=s);
        regStdToolSet(GeneratedRegistrationIds.Items.toolSet("rraw_iron"), ModToolTiers.RRAW_IRON, (t,s)->RRAW_IRON_SWORD=s, (t,s)->RRAW_IRON_PICKAXE=s, (t,s)->RRAW_IRON_SHOVEL=s, (t,s)->RRAW_IRON_AXE=s, (t,s)->RRAW_IRON_HOE=s);
        regStdToolSet(GeneratedRegistrationIds.Items.toolSet("rraw_rgold"), ModToolTiers.RRAW_RGOLD, (t,s)->RRAW_RGOLD_SWORD=s, (t,s)->RRAW_RGOLD_PICKAXE=s, (t,s)->RRAW_RGOLD_SHOVEL=s, (t,s)->RRAW_RGOLD_AXE=s, (t,s)->RRAW_RGOLD_HOE=s);
        regStdToolSet(GeneratedRegistrationIds.Items.toolSet("rscrap"), ModToolTiers.RSCRAP, (t,s)->RSCRAP_SWORD=s, (t,s)->RSCRAP_PICKAXE=s, (t,s)->RSCRAP_SHOVEL=s, (t,s)->RSCRAP_AXE=s, (t,s)->RSCRAP_HOE=s);

        // Crystal rough tools
        regStdToolSet(GeneratedRegistrationIds.Items.toolSet("ramethyst"), ModToolTiers.RAMETHYST, (t,s)->RAMETHYST_SWORD=s, (t,s)->RAMETHYST_PICKAXE=s, (t,s)->RAMETHYST_SHOVEL=s, (t,s)->RAMETHYST_AXE=s, (t,s)->RAMETHYST_HOE=s);
        regStdToolSet(GeneratedRegistrationIds.Items.toolSet("snow"), ModToolTiers.SNOW_TOOL, (t,s)->SNOW_SWORD=s, (t,s)->SNOW_PICKAXE=s, (t,s)->SNOW_SHOVEL=s, (t,s)->SNOW_AXE=s, (t,s)->SNOW_HOE=s);
        regStdToolSet(GeneratedRegistrationIds.Items.toolSet("rquartz"), ModToolTiers.RQUARTZ, (t,s)->RQUARTZ_SWORD=s, (t,s)->RQUARTZ_PICKAXE=s, (t,s)->RQUARTZ_SHOVEL=s, (t,s)->RQUARTZ_AXE=s, (t,s)->RQUARTZ_HOE=s);
        regStdToolSet(GeneratedRegistrationIds.Items.toolSet("rprism"), ModToolTiers.RPRISM, (t,s)->RPRISM_SWORD=s, (t,s)->RPRISM_PICKAXE=s, (t,s)->RPRISM_SHOVEL=s, (t,s)->RPRISM_AXE=s, (t,s)->RPRISM_HOE=s);

        // Polished crystal tools + armor
        CAMETHYST_SWORD = reg(GeneratedRegistrationIds.Items.CAMETHYST_SWORD, new SwordItem(ModToolTiers.CAMETHYST,
                new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ModToolTiers.CAMETHYST, 3, -2.4f))));
        CAMETHYST_PICKAXE = reg(GeneratedRegistrationIds.Items.CAMETHYST_PICKAXE, new PickaxeItem(ModToolTiers.CAMETHYST,
                new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolTiers.CAMETHYST, 1, -2.8f))));
        CAMETHYST_SHOVEL = reg(GeneratedRegistrationIds.Items.CAMETHYST_SHOVEL, new ShovelItem(ModToolTiers.CAMETHYST,
                new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolTiers.CAMETHYST, 1.5f, -3f))));
        CAMETHYST_AXE = reg(GeneratedRegistrationIds.Items.CAMETHYST_AXE, new AxeItem(ModToolTiers.CAMETHYST,
                new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolTiers.CAMETHYST, 6, -3.2f))));
        CAMETHYST_HOE = reg(GeneratedRegistrationIds.Items.CAMETHYST_HOE, new HoeItem(ModToolTiers.CAMETHYST,
                new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ModToolTiers.CAMETHYST, 0, -3f))));
        CAMETHYST_HELMET = reg(GeneratedRegistrationIds.Items.CAMETHYST_HELMET, new ArmorItem(ModArmorMaterials.CAMETHYST_ARMOR_MATERIAL,
                ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(14))));
        CAMETHYST_CHESTPLATE = reg(GeneratedRegistrationIds.Items.CAMETHYST_CHESTPLATE, new ArmorItem(ModArmorMaterials.CAMETHYST_ARMOR_MATERIAL,
                ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(14))));
        CAMETHYST_LEGGINGS = reg(GeneratedRegistrationIds.Items.CAMETHYST_LEGGINGS, new ArmorItem(ModArmorMaterials.CAMETHYST_ARMOR_MATERIAL,
                ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(14))));
        CAMETHYST_BOOTS = reg(GeneratedRegistrationIds.Items.CAMETHYST_BOOTS, new ArmorItem(ModArmorMaterials.CAMETHYST_ARMOR_MATERIAL,
                ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(14))));

        ICE_SWORD = reg(GeneratedRegistrationIds.Items.ICE_SWORD, new SwordItem(ModToolTiers.ICE_TOOL,
                new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ModToolTiers.ICE_TOOL, 3, -2.4f))));
        ICE_PICKAXE = reg(GeneratedRegistrationIds.Items.ICE_PICKAXE, new PickaxeItem(ModToolTiers.ICE_TOOL,
                new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolTiers.ICE_TOOL, 1, -2.8f))));
        ICE_SHOVEL = reg(GeneratedRegistrationIds.Items.ICE_SHOVEL, new ShovelItem(ModToolTiers.ICE_TOOL,
                new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolTiers.ICE_TOOL, 1.5f, -3f))));
        ICE_AXE = reg(GeneratedRegistrationIds.Items.ICE_AXE, new AxeItem(ModToolTiers.ICE_TOOL,
                new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolTiers.ICE_TOOL, 6, -3.2f))));
        ICE_HOE = reg(GeneratedRegistrationIds.Items.ICE_HOE, new HoeItem(ModToolTiers.ICE_TOOL,
                new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ModToolTiers.ICE_TOOL, 0, -3f))));
        ICE_HELMET = reg(GeneratedRegistrationIds.Items.ICE_HELMET, new ArmorItem(ModArmorMaterials.ICE_ARMOR_MATERIAL,
                ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(8))));
        ICE_CHESTPLATE = reg(GeneratedRegistrationIds.Items.ICE_CHESTPLATE, new ArmorItem(ModArmorMaterials.ICE_ARMOR_MATERIAL,
                ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(8))));
        ICE_LEGGINGS = reg(GeneratedRegistrationIds.Items.ICE_LEGGINGS, new ArmorItem(ModArmorMaterials.ICE_ARMOR_MATERIAL,
                ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(8))));
        ICE_BOOTS = reg(GeneratedRegistrationIds.Items.ICE_BOOTS, new ArmorItem(ModArmorMaterials.ICE_ARMOR_MATERIAL,
                ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(8))));

        PQUARTZ_SWORD = reg(GeneratedRegistrationIds.Items.PQUARTZ_SWORD, new SwordItem(ModToolTiers.PQUARTZ,
                new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ModToolTiers.PQUARTZ, 3, -2.4f))));
        PQUARTZ_PICKAXE = reg(GeneratedRegistrationIds.Items.PQUARTZ_PICKAXE, new PickaxeItem(ModToolTiers.PQUARTZ,
                new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolTiers.PQUARTZ, 1, -2.8f))));
        PQUARTZ_SHOVEL = reg(GeneratedRegistrationIds.Items.PQUARTZ_SHOVEL, new ShovelItem(ModToolTiers.PQUARTZ,
                new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolTiers.PQUARTZ, 1.5f, -3f))));
        PQUARTZ_AXE = reg(GeneratedRegistrationIds.Items.PQUARTZ_AXE, new AxeItem(ModToolTiers.PQUARTZ,
                new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolTiers.PQUARTZ, 6, -3.2f))));
        PQUARTZ_HOE = reg(GeneratedRegistrationIds.Items.PQUARTZ_HOE, new HoeItem(ModToolTiers.PQUARTZ,
                new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ModToolTiers.PQUARTZ, 0, -3f))));
        PQUARTZ_HELMET = reg(GeneratedRegistrationIds.Items.PQUARTZ_HELMET, new ArmorItem(ModArmorMaterials.PQUARTZ_ARMOR_MATERIAL,
                ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(15))));
        PQUARTZ_CHESTPLATE = reg(GeneratedRegistrationIds.Items.PQUARTZ_CHESTPLATE, new ArmorItem(ModArmorMaterials.PQUARTZ_ARMOR_MATERIAL,
                ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(15))));
        PQUARTZ_LEGGINGS = reg(GeneratedRegistrationIds.Items.PQUARTZ_LEGGINGS, new ArmorItem(ModArmorMaterials.PQUARTZ_ARMOR_MATERIAL,
                ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(15))));
        PQUARTZ_BOOTS = reg(GeneratedRegistrationIds.Items.PQUARTZ_BOOTS, new ArmorItem(ModArmorMaterials.PQUARTZ_ARMOR_MATERIAL,
                ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(15))));

        PPRISM_SWORD = reg(GeneratedRegistrationIds.Items.PPRISM_SWORD, new SwordItem(ModToolTiers.PPRISM,
                new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ModToolTiers.PPRISM, 3, -2.4f))));
        PPRISM_PICKAXE = reg(GeneratedRegistrationIds.Items.PPRISM_PICKAXE, new PickaxeItem(ModToolTiers.PPRISM,
                new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolTiers.PPRISM, 1, -2.8f))));
        PPRISM_SHOVEL = reg(GeneratedRegistrationIds.Items.PPRISM_SHOVEL, new ShovelItem(ModToolTiers.PPRISM,
                new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolTiers.PPRISM, 1.5f, -3f))));
        PPRISM_AXE = reg(GeneratedRegistrationIds.Items.PPRISM_AXE, new AxeItem(ModToolTiers.PPRISM,
                new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolTiers.PPRISM, 6, -3.2f))));
        PPRISM_HOE = reg(GeneratedRegistrationIds.Items.PPRISM_HOE, new HoeItem(ModToolTiers.PPRISM,
                new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ModToolTiers.PPRISM, 0, -3f))));
        PPRISM_HELMET = reg(GeneratedRegistrationIds.Items.PPRISM_HELMET, new ArmorItem(ModArmorMaterials.PPRISM_ARMOR_MATERIAL,
                ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(14))));
        PPRISM_CHESTPLATE = reg(GeneratedRegistrationIds.Items.PPRISM_CHESTPLATE, new ArmorItem(ModArmorMaterials.PPRISM_ARMOR_MATERIAL,
                ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(14))));
        PPRISM_LEGGINGS = reg(GeneratedRegistrationIds.Items.PPRISM_LEGGINGS, new ArmorItem(ModArmorMaterials.PPRISM_ARMOR_MATERIAL,
                ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(14))));
        PPRISM_BOOTS = reg(GeneratedRegistrationIds.Items.PPRISM_BOOTS, new ArmorItem(ModArmorMaterials.PPRISM_ARMOR_MATERIAL,
                ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(14))));

        // Flint tools
        RFLINT_SWORD = reg(GeneratedRegistrationIds.Items.RFLINT_SWORD, new SwordItem(ModToolTiers.RFLINT,
                new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ModToolTiers.RFLINT, 3, -2.4f))));
        RFLINT_PICKAXE = reg(GeneratedRegistrationIds.Items.RFLINT_PICKAXE, new PickaxeItem(ModToolTiers.RFLINT,
                new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolTiers.RFLINT, 1, -2.8f))));
        RFLINT_SHOVEL = reg(GeneratedRegistrationIds.Items.RFLINT_SHOVEL, new ShovelItem(ModToolTiers.RFLINT,
                new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolTiers.RFLINT, 1.5f, -3f))));
        RFLINT_AXE = reg(GeneratedRegistrationIds.Items.RFLINT_AXE, new AxeItem(ModToolTiers.RFLINT,
                new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolTiers.RFLINT, 6, -3.2f))));
        RFLINT_HOE = reg(GeneratedRegistrationIds.Items.RFLINT_HOE, new HoeItem(ModToolTiers.RFLINT,
                new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ModToolTiers.RFLINT, 0, -3f))));

        FNI_SWORD = reg(GeneratedRegistrationIds.Items.FNI_SWORD, new SwordItem(ModToolTiers.FNI,
                new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ModToolTiers.FNI, 3, -2.4f))));
        FNI_PICKAXE = reg(GeneratedRegistrationIds.Items.FNI_PICKAXE, new PickaxeItem(ModToolTiers.FNI,
                new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolTiers.FNI, 1, -2.8f))));
        FNI_SHOVEL = reg(GeneratedRegistrationIds.Items.FNI_SHOVEL, new ShovelItem(ModToolTiers.FNI,
                new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolTiers.FNI, 1.5f, -3f))));
        FNI_AXE = reg(GeneratedRegistrationIds.Items.FNI_AXE, new AxeItem(ModToolTiers.FNI,
                new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolTiers.FNI, 6, -3.2f))));
        FNI_HOE = reg(GeneratedRegistrationIds.Items.FNI_HOE, new HoeItem(ModToolTiers.FNI,
                new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ModToolTiers.FNI, 0, -3f))));
        FNI_HELMET = reg(GeneratedRegistrationIds.Items.FNI_HELMET, new ArmorItem(ModArmorMaterials.FNI_ARMOR_MATERIAL,
                ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(9))));
        FNI_CHESTPLATE = reg(GeneratedRegistrationIds.Items.FNI_CHESTPLATE, new ArmorItem(ModArmorMaterials.FNI_ARMOR_MATERIAL,
                ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(9))));
        FNI_LEGGINGS = reg(GeneratedRegistrationIds.Items.FNI_LEGGINGS, new ArmorItem(ModArmorMaterials.FNI_ARMOR_MATERIAL,
                ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(9))));
        FNI_BOOTS = reg(GeneratedRegistrationIds.Items.FNI_BOOTS, new ArmorItem(ModArmorMaterials.FNI_ARMOR_MATERIAL,
                ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(9))));

        // Stone variant tools (using custom attack modifiers from 1.20 reference)
        ANDESITE_SWORD = reg(GeneratedRegistrationIds.Items.ANDESITE_SWORD, new SwordItem(ModToolTiers.STONE_ANDESITE, new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ModToolTiers.STONE_ANDESITE, 3, -2.4f))));
        ANDESITE_PICKAXE = reg(GeneratedRegistrationIds.Items.ANDESITE_PICKAXE, new PickaxeItem(ModToolTiers.STONE_ANDESITE, new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolTiers.STONE_ANDESITE, 1, -2.8f))));
        ANDESITE_SHOVEL = reg(GeneratedRegistrationIds.Items.ANDESITE_SHOVEL, new ShovelItem(ModToolTiers.STONE_ANDESITE, new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolTiers.STONE_ANDESITE, 1.5f, -3f))));
        ANDESITE_AXE = reg(GeneratedRegistrationIds.Items.ANDESITE_AXE, new AxeItem(ModToolTiers.STONE_ANDESITE, new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolTiers.STONE_ANDESITE, 6, -3.2f))));
        ANDESITE_HOE = reg(GeneratedRegistrationIds.Items.ANDESITE_HOE, new HoeItem(ModToolTiers.STONE_ANDESITE, new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ModToolTiers.STONE_ANDESITE, 0, -3f))));

        BASALT_SWORD = reg(GeneratedRegistrationIds.Items.BASALT_SWORD, new SwordItem(ModToolTiers.STONE_BASALT, new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ModToolTiers.STONE_BASALT, 3, -2.5f))));
        BASALT_PICKAXE = reg(GeneratedRegistrationIds.Items.BASALT_PICKAXE, new PickaxeItem(ModToolTiers.STONE_BASALT, new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolTiers.STONE_BASALT, 1, -2.9f))));
        BASALT_SHOVEL = reg(GeneratedRegistrationIds.Items.BASALT_SHOVEL, new ShovelItem(ModToolTiers.STONE_BASALT, new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolTiers.STONE_BASALT, 2.0f, -3.1f))));
        BASALT_AXE = reg(GeneratedRegistrationIds.Items.BASALT_AXE, new AxeItem(ModToolTiers.STONE_BASALT, new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolTiers.STONE_BASALT, 7, -3.3f))));
        BASALT_HOE = reg(GeneratedRegistrationIds.Items.BASALT_HOE, new HoeItem(ModToolTiers.STONE_BASALT, new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ModToolTiers.STONE_BASALT, 0, -3.1f))));

        BLACKSTONE_SWORD = reg(GeneratedRegistrationIds.Items.BLACKSTONE_SWORD, new SwordItem(ModToolTiers.STONE_BLACKSTONE, new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ModToolTiers.STONE_BLACKSTONE, 4, -2.5f))));
        BLACKSTONE_PICKAXE = reg(GeneratedRegistrationIds.Items.BLACKSTONE_PICKAXE, new PickaxeItem(ModToolTiers.STONE_BLACKSTONE, new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolTiers.STONE_BLACKSTONE, 1, -2.9f))));
        BLACKSTONE_SHOVEL = reg(GeneratedRegistrationIds.Items.BLACKSTONE_SHOVEL, new ShovelItem(ModToolTiers.STONE_BLACKSTONE, new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolTiers.STONE_BLACKSTONE, 2.0f, -3.1f))));
        BLACKSTONE_AXE = reg(GeneratedRegistrationIds.Items.BLACKSTONE_AXE, new AxeItem(ModToolTiers.STONE_BLACKSTONE, new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolTiers.STONE_BLACKSTONE, 7, -3.35f))));
        BLACKSTONE_HOE = reg(GeneratedRegistrationIds.Items.BLACKSTONE_HOE, new HoeItem(ModToolTiers.STONE_BLACKSTONE, new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ModToolTiers.STONE_BLACKSTONE, 0, -3.1f))));

        CALCITE_SWORD = reg(GeneratedRegistrationIds.Items.CALCITE_SWORD, new SwordItem(ModToolTiers.STONE_CALCITE, new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ModToolTiers.STONE_CALCITE, 2, -2.2f))));
        CALCITE_PICKAXE = reg(GeneratedRegistrationIds.Items.CALCITE_PICKAXE, new PickaxeItem(ModToolTiers.STONE_CALCITE, new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolTiers.STONE_CALCITE, 1, -2.6f))));
        CALCITE_SHOVEL = reg(GeneratedRegistrationIds.Items.CALCITE_SHOVEL, new ShovelItem(ModToolTiers.STONE_CALCITE, new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolTiers.STONE_CALCITE, 1.0f, -2.8f))));
        CALCITE_AXE = reg(GeneratedRegistrationIds.Items.CALCITE_AXE, new AxeItem(ModToolTiers.STONE_CALCITE, new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolTiers.STONE_CALCITE, 5, -3.0f))));
        CALCITE_HOE = reg(GeneratedRegistrationIds.Items.CALCITE_HOE, new HoeItem(ModToolTiers.STONE_CALCITE, new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ModToolTiers.STONE_CALCITE, 0, -2.6f))));

        DEEPSLATE_SWORD = reg(GeneratedRegistrationIds.Items.DEEPSLATE_SWORD, new SwordItem(ModToolTiers.STONE_DEEPSLATE, new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ModToolTiers.STONE_DEEPSLATE, 4, -2.55f))));
        DEEPSLATE_PICKAXE = reg(GeneratedRegistrationIds.Items.DEEPSLATE_PICKAXE, new PickaxeItem(ModToolTiers.STONE_DEEPSLATE, new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolTiers.STONE_DEEPSLATE, 1, -2.95f))));
        DEEPSLATE_SHOVEL = reg(GeneratedRegistrationIds.Items.DEEPSLATE_SHOVEL, new ShovelItem(ModToolTiers.STONE_DEEPSLATE, new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolTiers.STONE_DEEPSLATE, 2.0f, -3.15f))));
        DEEPSLATE_AXE = reg(GeneratedRegistrationIds.Items.DEEPSLATE_AXE, new AxeItem(ModToolTiers.STONE_DEEPSLATE, new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolTiers.STONE_DEEPSLATE, 7, -3.4f))));
        DEEPSLATE_HOE = reg(GeneratedRegistrationIds.Items.DEEPSLATE_HOE, new HoeItem(ModToolTiers.STONE_DEEPSLATE, new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ModToolTiers.STONE_DEEPSLATE, 0, -3.1f))));

        DIORITE_SWORD = reg(GeneratedRegistrationIds.Items.DIORITE_SWORD, new SwordItem(ModToolTiers.STONE_DIORITE, new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ModToolTiers.STONE_DIORITE, 3, -2.4f))));
        DIORITE_PICKAXE = reg(GeneratedRegistrationIds.Items.DIORITE_PICKAXE, new PickaxeItem(ModToolTiers.STONE_DIORITE, new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolTiers.STONE_DIORITE, 1, -2.8f))));
        DIORITE_SHOVEL = reg(GeneratedRegistrationIds.Items.DIORITE_SHOVEL, new ShovelItem(ModToolTiers.STONE_DIORITE, new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolTiers.STONE_DIORITE, 1.5f, -3f))));
        DIORITE_AXE = reg(GeneratedRegistrationIds.Items.DIORITE_AXE, new AxeItem(ModToolTiers.STONE_DIORITE, new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolTiers.STONE_DIORITE, 6, -3.2f))));
        DIORITE_HOE = reg(GeneratedRegistrationIds.Items.DIORITE_HOE, new HoeItem(ModToolTiers.STONE_DIORITE, new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ModToolTiers.STONE_DIORITE, 0, -2.9f))));

        END_STONE_SWORD = reg(GeneratedRegistrationIds.Items.END_STONE_SWORD, new SwordItem(ModToolTiers.STONE_END_STONE, new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ModToolTiers.STONE_END_STONE, 3, -2.35f))));
        END_STONE_PICKAXE = reg(GeneratedRegistrationIds.Items.END_STONE_PICKAXE, new PickaxeItem(ModToolTiers.STONE_END_STONE, new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolTiers.STONE_END_STONE, 1, -2.75f))));
        END_STONE_SHOVEL = reg(GeneratedRegistrationIds.Items.END_STONE_SHOVEL, new ShovelItem(ModToolTiers.STONE_END_STONE, new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolTiers.STONE_END_STONE, 1.5f, -2.95f))));
        END_STONE_AXE = reg(GeneratedRegistrationIds.Items.END_STONE_AXE, new AxeItem(ModToolTiers.STONE_END_STONE, new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolTiers.STONE_END_STONE, 6, -3.15f))));
        END_STONE_HOE = reg(GeneratedRegistrationIds.Items.END_STONE_HOE, new HoeItem(ModToolTiers.STONE_END_STONE, new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ModToolTiers.STONE_END_STONE, 0, -2.8f))));

        GRANITE_SWORD = reg(GeneratedRegistrationIds.Items.GRANITE_SWORD, new SwordItem(ModToolTiers.STONE_GRANITE, new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ModToolTiers.STONE_GRANITE, 3, -2.5f))));
        GRANITE_PICKAXE = reg(GeneratedRegistrationIds.Items.GRANITE_PICKAXE, new PickaxeItem(ModToolTiers.STONE_GRANITE, new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolTiers.STONE_GRANITE, 1, -2.9f))));
        GRANITE_SHOVEL = reg(GeneratedRegistrationIds.Items.GRANITE_SHOVEL, new ShovelItem(ModToolTiers.STONE_GRANITE, new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolTiers.STONE_GRANITE, 2.0f, -3.1f))));
        GRANITE_AXE = reg(GeneratedRegistrationIds.Items.GRANITE_AXE, new AxeItem(ModToolTiers.STONE_GRANITE, new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolTiers.STONE_GRANITE, 7, -3.3f))));
        GRANITE_HOE = reg(GeneratedRegistrationIds.Items.GRANITE_HOE, new HoeItem(ModToolTiers.STONE_GRANITE, new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ModToolTiers.STONE_GRANITE, 0, -3.1f))));

        NETHERRACK_SWORD = reg(GeneratedRegistrationIds.Items.NETHERRACK_SWORD, new SwordItem(ModToolTiers.STONE_NETHERRACK, new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ModToolTiers.STONE_NETHERRACK, 2, -2.2f))));
        NETHERRACK_PICKAXE = reg(GeneratedRegistrationIds.Items.NETHERRACK_PICKAXE, new PickaxeItem(ModToolTiers.STONE_NETHERRACK, new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolTiers.STONE_NETHERRACK, 1, -2.6f))));
        NETHERRACK_SHOVEL = reg(GeneratedRegistrationIds.Items.NETHERRACK_SHOVEL, new ShovelItem(ModToolTiers.STONE_NETHERRACK, new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolTiers.STONE_NETHERRACK, 1.0f, -2.8f))));
        NETHERRACK_AXE = reg(GeneratedRegistrationIds.Items.NETHERRACK_AXE, new AxeItem(ModToolTiers.STONE_NETHERRACK, new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolTiers.STONE_NETHERRACK, 5, -3.0f))));
        NETHERRACK_HOE = reg(GeneratedRegistrationIds.Items.NETHERRACK_HOE, new HoeItem(ModToolTiers.STONE_NETHERRACK, new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ModToolTiers.STONE_NETHERRACK, 0, -2.5f))));

        SANDSTONE_SWORD = reg(GeneratedRegistrationIds.Items.SANDSTONE_SWORD, new SwordItem(ModToolTiers.STONE_SANDSTONE, new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ModToolTiers.STONE_SANDSTONE, 2, -2.3f))));
        SANDSTONE_PICKAXE = reg(GeneratedRegistrationIds.Items.SANDSTONE_PICKAXE, new PickaxeItem(ModToolTiers.STONE_SANDSTONE, new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolTiers.STONE_SANDSTONE, 1, -2.7f))));
        SANDSTONE_SHOVEL = reg(GeneratedRegistrationIds.Items.SANDSTONE_SHOVEL, new ShovelItem(ModToolTiers.STONE_SANDSTONE, new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolTiers.STONE_SANDSTONE, 1.0f, -2.9f))));
        SANDSTONE_AXE = reg(GeneratedRegistrationIds.Items.SANDSTONE_AXE, new AxeItem(ModToolTiers.STONE_SANDSTONE, new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolTiers.STONE_SANDSTONE, 5, -3.1f))));
        SANDSTONE_HOE = reg(GeneratedRegistrationIds.Items.SANDSTONE_HOE, new HoeItem(ModToolTiers.STONE_SANDSTONE, new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ModToolTiers.STONE_SANDSTONE, 0, -2.7f))));

        SMOOTH_BASALT_SWORD = reg(GeneratedRegistrationIds.Items.SMOOTH_BASALT_SWORD, new SwordItem(ModToolTiers.STONE_SMOOTH_BASALT, new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ModToolTiers.STONE_SMOOTH_BASALT, 3, -2.45f))));
        SMOOTH_BASALT_PICKAXE = reg(GeneratedRegistrationIds.Items.SMOOTH_BASALT_PICKAXE, new PickaxeItem(ModToolTiers.STONE_SMOOTH_BASALT, new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolTiers.STONE_SMOOTH_BASALT, 1, -2.85f))));
        SMOOTH_BASALT_SHOVEL = reg(GeneratedRegistrationIds.Items.SMOOTH_BASALT_SHOVEL, new ShovelItem(ModToolTiers.STONE_SMOOTH_BASALT, new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolTiers.STONE_SMOOTH_BASALT, 1.5f, -3.05f))));
        SMOOTH_BASALT_AXE = reg(GeneratedRegistrationIds.Items.SMOOTH_BASALT_AXE, new AxeItem(ModToolTiers.STONE_SMOOTH_BASALT, new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolTiers.STONE_SMOOTH_BASALT, 6, -3.25f))));
        SMOOTH_BASALT_HOE = reg(GeneratedRegistrationIds.Items.SMOOTH_BASALT_HOE, new HoeItem(ModToolTiers.STONE_SMOOTH_BASALT, new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ModToolTiers.STONE_SMOOTH_BASALT, 0, -3.0f))));

        TERRACOTTA_SWORD = reg(GeneratedRegistrationIds.Items.TERRACOTTA_SWORD, new SwordItem(ModToolTiers.STONE_TERRACOTTA, new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ModToolTiers.STONE_TERRACOTTA, 3, -2.35f))));
        TERRACOTTA_PICKAXE = reg(GeneratedRegistrationIds.Items.TERRACOTTA_PICKAXE, new PickaxeItem(ModToolTiers.STONE_TERRACOTTA, new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolTiers.STONE_TERRACOTTA, 1, -2.75f))));
        TERRACOTTA_SHOVEL = reg(GeneratedRegistrationIds.Items.TERRACOTTA_SHOVEL, new ShovelItem(ModToolTiers.STONE_TERRACOTTA, new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolTiers.STONE_TERRACOTTA, 1.5f, -2.95f))));
        TERRACOTTA_AXE = reg(GeneratedRegistrationIds.Items.TERRACOTTA_AXE, new AxeItem(ModToolTiers.STONE_TERRACOTTA, new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolTiers.STONE_TERRACOTTA, 6, -3.15f))));
        TERRACOTTA_HOE = reg(GeneratedRegistrationIds.Items.TERRACOTTA_HOE, new HoeItem(ModToolTiers.STONE_TERRACOTTA, new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ModToolTiers.STONE_TERRACOTTA, 0, -2.8f))));

        TUFF_SWORD = reg(GeneratedRegistrationIds.Items.TUFF_SWORD, new SwordItem(ModToolTiers.STONE_TUFF, new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(ModToolTiers.STONE_TUFF, 2, -2.35f))));
        TUFF_PICKAXE = reg(GeneratedRegistrationIds.Items.TUFF_PICKAXE, new PickaxeItem(ModToolTiers.STONE_TUFF, new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolTiers.STONE_TUFF, 1, -2.75f))));
        TUFF_SHOVEL = reg(GeneratedRegistrationIds.Items.TUFF_SHOVEL, new ShovelItem(ModToolTiers.STONE_TUFF, new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(ModToolTiers.STONE_TUFF, 1.5f, -2.95f))));
        TUFF_AXE = reg(GeneratedRegistrationIds.Items.TUFF_AXE, new AxeItem(ModToolTiers.STONE_TUFF, new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(ModToolTiers.STONE_TUFF, 5, -3.15f))));
        TUFF_HOE = reg(GeneratedRegistrationIds.Items.TUFF_HOE, new HoeItem(ModToolTiers.STONE_TUFF, new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(ModToolTiers.STONE_TUFF, 0, -2.8f))));

        // Wood variant tools
        regStdToolSet(GeneratedRegistrationIds.Items.toolSet("oak"), ModToolTiers.OAK_TOOL, (t,s)->OAK_SWORD=s, (t,s)->OAK_PICKAXE=s, (t,s)->OAK_SHOVEL=s, (t,s)->OAK_AXE=s, (t,s)->OAK_HOE=s);
        regStdToolSet(GeneratedRegistrationIds.Items.toolSet("spruce"), ModToolTiers.SPRUCE_TOOL, (t,s)->SPRUCE_SWORD=s, (t,s)->SPRUCE_PICKAXE=s, (t,s)->SPRUCE_SHOVEL=s, (t,s)->SPRUCE_AXE=s, (t,s)->SPRUCE_HOE=s);
        regStdToolSet(GeneratedRegistrationIds.Items.toolSet("birch"), ModToolTiers.BIRCH_TOOL, (t,s)->BIRCH_SWORD=s, (t,s)->BIRCH_PICKAXE=s, (t,s)->BIRCH_SHOVEL=s, (t,s)->BIRCH_AXE=s, (t,s)->BIRCH_HOE=s);
        regStdToolSet(GeneratedRegistrationIds.Items.toolSet("jungle"), ModToolTiers.JUNGLE_TOOL, (t,s)->JUNGLE_SWORD=s, (t,s)->JUNGLE_PICKAXE=s, (t,s)->JUNGLE_SHOVEL=s, (t,s)->JUNGLE_AXE=s, (t,s)->JUNGLE_HOE=s);
        regStdToolSet(GeneratedRegistrationIds.Items.toolSet("acacia"), ModToolTiers.ACACIA_TOOL, (t,s)->ACACIA_SWORD=s, (t,s)->ACACIA_PICKAXE=s, (t,s)->ACACIA_SHOVEL=s, (t,s)->ACACIA_AXE=s, (t,s)->ACACIA_HOE=s);
        regStdToolSet(GeneratedRegistrationIds.Items.toolSet("dark_oak"), ModToolTiers.DARK_OAK_TOOL, (t,s)->DARK_OAK_SWORD=s, (t,s)->DARK_OAK_PICKAXE=s, (t,s)->DARK_OAK_SHOVEL=s, (t,s)->DARK_OAK_AXE=s, (t,s)->DARK_OAK_HOE=s);
        regStdToolSet(GeneratedRegistrationIds.Items.toolSet("mangrove"), ModToolTiers.MANGROVE_TOOL, (t,s)->MANGROVE_SWORD=s, (t,s)->MANGROVE_PICKAXE=s, (t,s)->MANGROVE_SHOVEL=s, (t,s)->MANGROVE_AXE=s, (t,s)->MANGROVE_HOE=s);
        regStdToolSet(GeneratedRegistrationIds.Items.toolSet("cherry"), ModToolTiers.CHERRY_TOOL, (t,s)->CHERRY_SWORD=s, (t,s)->CHERRY_PICKAXE=s, (t,s)->CHERRY_SHOVEL=s, (t,s)->CHERRY_AXE=s, (t,s)->CHERRY_HOE=s);
        regStdToolSet(GeneratedRegistrationIds.Items.toolSet("bamboo"), ModToolTiers.BAMBOO_TOOL, (t,s)->BAMBOO_SWORD=s, (t,s)->BAMBOO_PICKAXE=s, (t,s)->BAMBOO_SHOVEL=s, (t,s)->BAMBOO_AXE=s, (t,s)->BAMBOO_HOE=s);
        regStdToolSet(GeneratedRegistrationIds.Items.toolSet("crimson"), ModToolTiers.CRIMSON_TOOL, (t,s)->CRIMSON_SWORD=s, (t,s)->CRIMSON_PICKAXE=s, (t,s)->CRIMSON_SHOVEL=s, (t,s)->CRIMSON_AXE=s, (t,s)->CRIMSON_HOE=s);
        regStdToolSet(GeneratedRegistrationIds.Items.toolSet("warped"), ModToolTiers.WARPED_TOOL, (t,s)->WARPED_SWORD=s, (t,s)->WARPED_PICKAXE=s, (t,s)->WARPED_SHOVEL=s, (t,s)->WARPED_AXE=s, (t,s)->WARPED_HOE=s);

        // Leather tools
        regStdToolSet(GeneratedRegistrationIds.Items.toolSet("leather"), ModToolTiers.LEATHER_TOOL, (t,s)->LEATHER_SWORD=s, (t,s)->LEATHER_PICKAXE=s, (t,s)->LEATHER_SHOVEL=s, (t,s)->LEATHER_AXE=s, (t,s)->LEATHER_HOE=s);

        // Paper tools
        regStdToolSet(GeneratedRegistrationIds.Items.toolSet("paper"), ModToolTiers.PAPER, (t,s)->PAPER_SWORD=s, (t,s)->PAPER_PICKAXE=s, (t,s)->PAPER_SHOVEL=s, (t,s)->PAPER_AXE=s, (t,s)->PAPER_HOE=s);

        // Feather tools
        regStdToolSet(GeneratedRegistrationIds.Items.toolSet("feather"), ModToolTiers.FEATHER, (t,s)->FEATHER_SWORD=s, (t,s)->FEATHER_PICKAXE=s, (t,s)->FEATHER_SHOVEL=s, (t,s)->FEATHER_AXE=s, (t,s)->FEATHER_HOE=s);

        // Glass tools
        regStdToolSet(GeneratedRegistrationIds.Items.toolSet("glass"), ModToolTiers.GLASS, (t,s)->GLASS_SWORD=s, (t,s)->GLASS_PICKAXE=s, (t,s)->GLASS_SHOVEL=s, (t,s)->GLASS_AXE=s, (t,s)->GLASS_HOE=s);

        // Rabbit hide armor only
        RABBIT_HIDE_HELMET = reg(GeneratedRegistrationIds.Items.RABBIT_HIDE_HELMET, new ArmorItem(ModArmorMaterials.RABBIT_HIDE_ARMOR_MATERIAL,
                ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(5))));
        RABBIT_HIDE_CHESTPLATE = reg(GeneratedRegistrationIds.Items.RABBIT_HIDE_CHESTPLATE, new ArmorItem(ModArmorMaterials.RABBIT_HIDE_ARMOR_MATERIAL,
                ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(5))));
        RABBIT_HIDE_LEGGINGS = reg(GeneratedRegistrationIds.Items.RABBIT_HIDE_LEGGINGS, new ArmorItem(ModArmorMaterials.RABBIT_HIDE_ARMOR_MATERIAL,
                ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(5))));
        RABBIT_HIDE_BOOTS = reg(GeneratedRegistrationIds.Items.RABBIT_HIDE_BOOTS, new ArmorItem(ModArmorMaterials.RABBIT_HIDE_ARMOR_MATERIAL,
                ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(5))));

        // Cactus tools + armor
        regStdToolSet(GeneratedRegistrationIds.Items.toolSet("cactus"), ModToolTiers.CACTUS, (t,s)->CACTUS_SWORD=s, (t,s)->CACTUS_PICKAXE=s, (t,s)->CACTUS_SHOVEL=s, (t,s)->CACTUS_AXE=s, (t,s)->CACTUS_HOE=s);
        CACTUS_HELMET = reg(GeneratedRegistrationIds.Items.CACTUS_HELMET, new ArmorItem(ModArmorMaterials.CACTUS_ARMOR_MATERIAL,
                ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(5))));
        CACTUS_CHESTPLATE = reg(GeneratedRegistrationIds.Items.CACTUS_CHESTPLATE, new ArmorItem(ModArmorMaterials.CACTUS_ARMOR_MATERIAL,
                ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(5))));
        CACTUS_LEGGINGS = reg(GeneratedRegistrationIds.Items.CACTUS_LEGGINGS, new ArmorItem(ModArmorMaterials.CACTUS_ARMOR_MATERIAL,
                ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(5))));
        CACTUS_BOOTS = reg(GeneratedRegistrationIds.Items.CACTUS_BOOTS, new ArmorItem(ModArmorMaterials.CACTUS_ARMOR_MATERIAL,
                ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(5))));

        // Sponge tools only
        regStdToolSet(GeneratedRegistrationIds.Items.toolSet("sponge"), ModToolTiers.SPONGE, (t,s)->SPONGE_SWORD=s, (t,s)->SPONGE_PICKAXE=s, (t,s)->SPONGE_SHOVEL=s, (t,s)->SPONGE_AXE=s, (t,s)->SPONGE_HOE=s);

        // Bone tools + armor
        regStdToolSet(GeneratedRegistrationIds.Items.toolSet("bone"), ModToolTiers.BONE, (t,s)->BONE_SWORD=s, (t,s)->BONE_PICKAXE=s, (t,s)->BONE_SHOVEL=s, (t,s)->BONE_AXE=s, (t,s)->BONE_HOE=s);
        BONE_HELMET = reg(GeneratedRegistrationIds.Items.BONE_HELMET, new ArmorItem(ModArmorMaterials.BONE_ARMOR_MATERIAL,
                ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(6))));
        BONE_CHESTPLATE = reg(GeneratedRegistrationIds.Items.BONE_CHESTPLATE, new ArmorItem(ModArmorMaterials.BONE_ARMOR_MATERIAL,
                ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(6))));
        BONE_LEGGINGS = reg(GeneratedRegistrationIds.Items.BONE_LEGGINGS, new ArmorItem(ModArmorMaterials.BONE_ARMOR_MATERIAL,
                ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(6))));
        BONE_BOOTS = reg(GeneratedRegistrationIds.Items.BONE_BOOTS, new ArmorItem(ModArmorMaterials.BONE_ARMOR_MATERIAL,
                ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(6))));

        // Clay tools + armor
        regStdToolSet(GeneratedRegistrationIds.Items.toolSet("clay"), ModToolTiers.CLAY, (t,s)->CLAY_SWORD=s, (t,s)->CLAY_PICKAXE=s, (t,s)->CLAY_SHOVEL=s, (t,s)->CLAY_AXE=s, (t,s)->CLAY_HOE=s);
        CLAY_HELMET = reg(GeneratedRegistrationIds.Items.CLAY_HELMET, new ArmorItem(ModArmorMaterials.CLAY_ARMOR_MATERIAL,
                ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(8))));
        CLAY_CHESTPLATE = reg(GeneratedRegistrationIds.Items.CLAY_CHESTPLATE, new ArmorItem(ModArmorMaterials.CLAY_ARMOR_MATERIAL,
                ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(8))));
        CLAY_LEGGINGS = reg(GeneratedRegistrationIds.Items.CLAY_LEGGINGS, new ArmorItem(ModArmorMaterials.CLAY_ARMOR_MATERIAL,
                ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(8))));
        CLAY_BOOTS = reg(GeneratedRegistrationIds.Items.CLAY_BOOTS, new ArmorItem(ModArmorMaterials.CLAY_ARMOR_MATERIAL,
                ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(8))));

        // Nether wart tools only
        regStdToolSet(GeneratedRegistrationIds.Items.toolSet("nether_wart"), ModToolTiers.NETHER_WART, (t,s)->NETHER_WART_SWORD=s, (t,s)->NETHER_WART_PICKAXE=s, (t,s)->NETHER_WART_SHOVEL=s, (t,s)->NETHER_WART_AXE=s, (t,s)->NETHER_WART_HOE=s);

        // Brick tools + armor
        regStdToolSet(GeneratedRegistrationIds.Items.toolSet("brick"), ModToolTiers.BRICK, (t,s)->BRICK_SWORD=s, (t,s)->BRICK_PICKAXE=s, (t,s)->BRICK_SHOVEL=s, (t,s)->BRICK_AXE=s, (t,s)->BRICK_HOE=s);
        BRICK_HELMET = reg(GeneratedRegistrationIds.Items.BRICK_HELMET, new ArmorItem(ModArmorMaterials.BRICK_ARMOR_MATERIAL,
                ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(10))));
        BRICK_CHESTPLATE = reg(GeneratedRegistrationIds.Items.BRICK_CHESTPLATE, new ArmorItem(ModArmorMaterials.BRICK_ARMOR_MATERIAL,
                ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(10))));
        BRICK_LEGGINGS = reg(GeneratedRegistrationIds.Items.BRICK_LEGGINGS, new ArmorItem(ModArmorMaterials.BRICK_ARMOR_MATERIAL,
                ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(10))));
        BRICK_BOOTS = reg(GeneratedRegistrationIds.Items.BRICK_BOOTS, new ArmorItem(ModArmorMaterials.BRICK_ARMOR_MATERIAL,
                ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(10))));

        // Nether brick tools + armor
        regStdToolSet(GeneratedRegistrationIds.Items.toolSet("nether_brick"), ModToolTiers.NETHER_BRICK, (t,s)->NETHER_BRICK_SWORD=s, (t,s)->NETHER_BRICK_PICKAXE=s, (t,s)->NETHER_BRICK_SHOVEL=s, (t,s)->NETHER_BRICK_AXE=s, (t,s)->NETHER_BRICK_HOE=s);
        NETHER_BRICK_HELMET = reg(GeneratedRegistrationIds.Items.NETHER_BRICK_HELMET, new ArmorItem(ModArmorMaterials.NETHER_BRICK_ARMOR_MATERIAL,
                ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(10))));
        NETHER_BRICK_CHESTPLATE = reg(GeneratedRegistrationIds.Items.NETHER_BRICK_CHESTPLATE, new ArmorItem(ModArmorMaterials.NETHER_BRICK_ARMOR_MATERIAL,
                ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(10))));
        NETHER_BRICK_LEGGINGS = reg(GeneratedRegistrationIds.Items.NETHER_BRICK_LEGGINGS, new ArmorItem(ModArmorMaterials.NETHER_BRICK_ARMOR_MATERIAL,
                ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(10))));
        NETHER_BRICK_BOOTS = reg(GeneratedRegistrationIds.Items.NETHER_BRICK_BOOTS, new ArmorItem(ModArmorMaterials.NETHER_BRICK_ARMOR_MATERIAL,
                ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(10))));

        // Pointed dripstone tools only
        regStdToolSet(GeneratedRegistrationIds.Items.toolSet("pointed_dripstone"), ModToolTiers.POINTED_DRIPSTONE, (t,s)->POINTED_DRIPSTONE_SWORD=s, (t,s)->POINTED_DRIPSTONE_PICKAXE=s, (t,s)->POINTED_DRIPSTONE_SHOVEL=s, (t,s)->POINTED_DRIPSTONE_AXE=s, (t,s)->POINTED_DRIPSTONE_HOE=s);

        // Copper tools + armor
        regStdToolSet(GeneratedRegistrationIds.Items.toolSet("copper"), ModToolTiers.COPPER, (t,s)->COPPER_SWORD=s, (t,s)->COPPER_PICKAXE=s, (t,s)->COPPER_SHOVEL=s, (t,s)->COPPER_AXE=s, (t,s)->COPPER_HOE=s);
        COPPER_HELMET = reg(GeneratedRegistrationIds.Items.COPPER_HELMET, new ArmorItem(ModArmorMaterials.COPPER_ARMOR_MATERIAL,
                ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(12))));
        COPPER_CHESTPLATE = reg(GeneratedRegistrationIds.Items.COPPER_CHESTPLATE, new ArmorItem(ModArmorMaterials.COPPER_ARMOR_MATERIAL,
                ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(12))));
        COPPER_LEGGINGS = reg(GeneratedRegistrationIds.Items.COPPER_LEGGINGS, new ArmorItem(ModArmorMaterials.COPPER_ARMOR_MATERIAL,
                ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(12))));
        COPPER_BOOTS = reg(GeneratedRegistrationIds.Items.COPPER_BOOTS, new ArmorItem(ModArmorMaterials.COPPER_ARMOR_MATERIAL,
                ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(12))));

        // Phantom tools + armor
        regStdToolSet(GeneratedRegistrationIds.Items.toolSet("phantom"), ModToolTiers.PHANTOM_MEMBRANE, (t,s)->PHANTOM_SWORD=s, (t,s)->PHANTOM_PICKAXE=s, (t,s)->PHANTOM_SHOVEL=s, (t,s)->PHANTOM_AXE=s, (t,s)->PHANTOM_HOE=s);
        PHANTOM_HELMET = reg(GeneratedRegistrationIds.Items.PHANTOM_HELMET, new ArmorItem(ModArmorMaterials.PHANTOM_ARMOR_MATERIAL,
                ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(12))));
        PHANTOM_CHESTPLATE = reg(GeneratedRegistrationIds.Items.PHANTOM_CHESTPLATE, new ArmorItem(ModArmorMaterials.PHANTOM_ARMOR_MATERIAL,
                ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(12))));
        PHANTOM_LEGGINGS = reg(GeneratedRegistrationIds.Items.PHANTOM_LEGGINGS, new ArmorItem(ModArmorMaterials.PHANTOM_ARMOR_MATERIAL,
                ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(12))));
        PHANTOM_BOOTS = reg(GeneratedRegistrationIds.Items.PHANTOM_BOOTS, new ArmorItem(ModArmorMaterials.PHANTOM_ARMOR_MATERIAL,
                ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(12))));

        // Magma cream tools + armor
        regStdToolSet(GeneratedRegistrationIds.Items.toolSet("magma_cream"), ModToolTiers.MAGMA_CREAM, (t,s)->MAGMA_CREAM_SWORD=s, (t,s)->MAGMA_CREAM_PICKAXE=s, (t,s)->MAGMA_CREAM_SHOVEL=s, (t,s)->MAGMA_CREAM_AXE=s, (t,s)->MAGMA_CREAM_HOE=s);
        MAGMA_CREAM_HELMET = reg(GeneratedRegistrationIds.Items.MAGMA_CREAM_HELMET, new ArmorItem(ModArmorMaterials.MAGMA_CREAM_ARMOR_MATERIAL,
                ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(10))));
        MAGMA_CREAM_CHESTPLATE = reg(GeneratedRegistrationIds.Items.MAGMA_CREAM_CHESTPLATE, new ArmorItem(ModArmorMaterials.MAGMA_CREAM_ARMOR_MATERIAL,
                ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(10))));
        MAGMA_CREAM_LEGGINGS = reg(GeneratedRegistrationIds.Items.MAGMA_CREAM_LEGGINGS, new ArmorItem(ModArmorMaterials.MAGMA_CREAM_ARMOR_MATERIAL,
                ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(10))));
        MAGMA_CREAM_BOOTS = reg(GeneratedRegistrationIds.Items.MAGMA_CREAM_BOOTS, new ArmorItem(ModArmorMaterials.MAGMA_CREAM_ARMOR_MATERIAL,
                ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(10))));

        // Slime tools + armor
        regStdToolSet(GeneratedRegistrationIds.Items.toolSet("slime"), ModToolTiers.SLIME, (t,s)->SLIME_SWORD=s, (t,s)->SLIME_PICKAXE=s, (t,s)->SLIME_SHOVEL=s, (t,s)->SLIME_AXE=s, (t,s)->SLIME_HOE=s);
        SLIME_HELMET = reg(GeneratedRegistrationIds.Items.SLIME_HELMET, new ArmorItem(ModArmorMaterials.SLIME_ARMOR_MATERIAL,
                ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(10))));
        SLIME_CHESTPLATE = reg(GeneratedRegistrationIds.Items.SLIME_CHESTPLATE, new ArmorItem(ModArmorMaterials.SLIME_ARMOR_MATERIAL,
                ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(10))));
        SLIME_LEGGINGS = reg(GeneratedRegistrationIds.Items.SLIME_LEGGINGS, new ArmorItem(ModArmorMaterials.SLIME_ARMOR_MATERIAL,
                ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(10))));
        SLIME_BOOTS = reg(GeneratedRegistrationIds.Items.SLIME_BOOTS, new ArmorItem(ModArmorMaterials.SLIME_ARMOR_MATERIAL,
                ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(10))));

        // Blaze tools + armor
        regStdToolSet(GeneratedRegistrationIds.Items.toolSet("blaze"), ModToolTiers.BLAZE_ROD, (t,s)->BLAZE_SWORD=s, (t,s)->BLAZE_PICKAXE=s, (t,s)->BLAZE_SHOVEL=s, (t,s)->BLAZE_AXE=s, (t,s)->BLAZE_HOE=s);
        BLAZE_HELMET = reg(GeneratedRegistrationIds.Items.BLAZE_HELMET, new ArmorItem(ModArmorMaterials.BLAZE_ARMOR_MATERIAL,
                ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(15))));
        BLAZE_CHESTPLATE = reg(GeneratedRegistrationIds.Items.BLAZE_CHESTPLATE, new ArmorItem(ModArmorMaterials.BLAZE_ARMOR_MATERIAL,
                ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(15))));
        BLAZE_LEGGINGS = reg(GeneratedRegistrationIds.Items.BLAZE_LEGGINGS, new ArmorItem(ModArmorMaterials.BLAZE_ARMOR_MATERIAL,
                ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(15))));
        BLAZE_BOOTS = reg(GeneratedRegistrationIds.Items.BLAZE_BOOTS, new ArmorItem(ModArmorMaterials.BLAZE_ARMOR_MATERIAL,
                ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(15))));

        // Nautilus tools + armor
        regStdToolSet(GeneratedRegistrationIds.Items.toolSet("nautilus"), ModToolTiers.NAUTILUS_SHELL, (t,s)->NAUTILUS_SWORD=s, (t,s)->NAUTILUS_PICKAXE=s, (t,s)->NAUTILUS_SHOVEL=s, (t,s)->NAUTILUS_AXE=s, (t,s)->NAUTILUS_HOE=s);
        NAUTILUS_HELMET = reg(GeneratedRegistrationIds.Items.NAUTILUS_HELMET, new ArmorItem(ModArmorMaterials.NAUTILUS_ARMOR_MATERIAL,
                ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(15))));
        NAUTILUS_CHESTPLATE = reg(GeneratedRegistrationIds.Items.NAUTILUS_CHESTPLATE, new ArmorItem(ModArmorMaterials.NAUTILUS_ARMOR_MATERIAL,
                ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(15))));
        NAUTILUS_LEGGINGS = reg(GeneratedRegistrationIds.Items.NAUTILUS_LEGGINGS, new ArmorItem(ModArmorMaterials.NAUTILUS_ARMOR_MATERIAL,
                ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(15))));
        NAUTILUS_BOOTS = reg(GeneratedRegistrationIds.Items.NAUTILUS_BOOTS, new ArmorItem(ModArmorMaterials.NAUTILUS_ARMOR_MATERIAL,
                ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(15))));

        // Purpur tools + armor
        regStdToolSet(GeneratedRegistrationIds.Items.toolSet("purpur"), ModToolTiers.PURPUR, (t,s)->PURPUR_SWORD=s, (t,s)->PURPUR_PICKAXE=s, (t,s)->PURPUR_SHOVEL=s, (t,s)->PURPUR_AXE=s, (t,s)->PURPUR_HOE=s);
        PURPUR_HELMET = reg(GeneratedRegistrationIds.Items.PURPUR_HELMET, new ArmorItem(ModArmorMaterials.PURPUR_ARMOR_MATERIAL,
                ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(15))));
        PURPUR_CHESTPLATE = reg(GeneratedRegistrationIds.Items.PURPUR_CHESTPLATE, new ArmorItem(ModArmorMaterials.PURPUR_ARMOR_MATERIAL,
                ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(15))));
        PURPUR_LEGGINGS = reg(GeneratedRegistrationIds.Items.PURPUR_LEGGINGS, new ArmorItem(ModArmorMaterials.PURPUR_ARMOR_MATERIAL,
                ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(15))));
        PURPUR_BOOTS = reg(GeneratedRegistrationIds.Items.PURPUR_BOOTS, new ArmorItem(ModArmorMaterials.PURPUR_ARMOR_MATERIAL,
                ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(15))));

        // Ghast tear tools + armor
        regStdToolSet(GeneratedRegistrationIds.Items.toolSet("ghast_tear"), ModToolTiers.GHAST_TEAR, (t,s)->GHAST_TEAR_SWORD=s, (t,s)->GHAST_TEAR_PICKAXE=s, (t,s)->GHAST_TEAR_SHOVEL=s, (t,s)->GHAST_TEAR_AXE=s, (t,s)->GHAST_TEAR_HOE=s);
        GHAST_TEAR_HELMET = reg(GeneratedRegistrationIds.Items.GHAST_TEAR_HELMET, new ArmorItem(ModArmorMaterials.GHAST_TEAR_ARMOR_MATERIAL,
                ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(18))));
        GHAST_TEAR_CHESTPLATE = reg(GeneratedRegistrationIds.Items.GHAST_TEAR_CHESTPLATE, new ArmorItem(ModArmorMaterials.GHAST_TEAR_ARMOR_MATERIAL,
                ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(18))));
        GHAST_TEAR_LEGGINGS = reg(GeneratedRegistrationIds.Items.GHAST_TEAR_LEGGINGS, new ArmorItem(ModArmorMaterials.GHAST_TEAR_ARMOR_MATERIAL,
                ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(18))));
        GHAST_TEAR_BOOTS = reg(GeneratedRegistrationIds.Items.GHAST_TEAR_BOOTS, new ArmorItem(ModArmorMaterials.GHAST_TEAR_ARMOR_MATERIAL,
                ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(18))));

        // Eye of ender tools + armor
        regStdToolSet(GeneratedRegistrationIds.Items.toolSet("eye_of_ender"), ModToolTiers.EYE_OF_ENDER, (t,s)->EYE_OF_ENDER_SWORD=s, (t,s)->EYE_OF_ENDER_PICKAXE=s, (t,s)->EYE_OF_ENDER_SHOVEL=s, (t,s)->EYE_OF_ENDER_AXE=s, (t,s)->EYE_OF_ENDER_HOE=s);
        EYE_OF_ENDER_HELMET = reg(GeneratedRegistrationIds.Items.EYE_OF_ENDER_HELMET, new ArmorItem(ModArmorMaterials.EYE_OF_ENDER_ARMOR_MATERIAL,
                ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(20))));
        EYE_OF_ENDER_CHESTPLATE = reg(GeneratedRegistrationIds.Items.EYE_OF_ENDER_CHESTPLATE, new ArmorItem(ModArmorMaterials.EYE_OF_ENDER_ARMOR_MATERIAL,
                ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(20))));
        EYE_OF_ENDER_LEGGINGS = reg(GeneratedRegistrationIds.Items.EYE_OF_ENDER_LEGGINGS, new ArmorItem(ModArmorMaterials.EYE_OF_ENDER_ARMOR_MATERIAL,
                ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(20))));
        EYE_OF_ENDER_BOOTS = reg(GeneratedRegistrationIds.Items.EYE_OF_ENDER_BOOTS, new ArmorItem(ModArmorMaterials.EYE_OF_ENDER_ARMOR_MATERIAL,
                ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(20))));

        // Shulker tools + armor
        regStdToolSet(GeneratedRegistrationIds.Items.toolSet("shulker"), ModToolTiers.SHULKER_SHELL, (t,s)->SHULKER_SWORD=s, (t,s)->SHULKER_PICKAXE=s, (t,s)->SHULKER_SHOVEL=s, (t,s)->SHULKER_AXE=s, (t,s)->SHULKER_HOE=s);
        SHULKER_HELMET = reg(GeneratedRegistrationIds.Items.SHULKER_HELMET, new ArmorItem(ModArmorMaterials.SHULKER_ARMOR_MATERIAL,
                ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(20))));
        SHULKER_CHESTPLATE = reg(GeneratedRegistrationIds.Items.SHULKER_CHESTPLATE, new ArmorItem(ModArmorMaterials.SHULKER_ARMOR_MATERIAL,
                ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(20))));
        SHULKER_LEGGINGS = reg(GeneratedRegistrationIds.Items.SHULKER_LEGGINGS, new ArmorItem(ModArmorMaterials.SHULKER_ARMOR_MATERIAL,
                ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(20))));
        SHULKER_BOOTS = reg(GeneratedRegistrationIds.Items.SHULKER_BOOTS, new ArmorItem(ModArmorMaterials.SHULKER_ARMOR_MATERIAL,
                ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(20))));

        // Turtle scute armor only
        TURTLE_SCUTE_HELMET = reg(GeneratedRegistrationIds.Items.TURTLE_SCUTE_HELMET, new ArmorItem(ModArmorMaterials.TURTLE_SCUTE_ARMOR_MATERIAL,
                ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(20))));
        TURTLE_SCUTE_CHESTPLATE = reg(GeneratedRegistrationIds.Items.TURTLE_SCUTE_CHESTPLATE, new ArmorItem(ModArmorMaterials.TURTLE_SCUTE_ARMOR_MATERIAL,
                ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(20))));
        TURTLE_SCUTE_LEGGINGS = reg(GeneratedRegistrationIds.Items.TURTLE_SCUTE_LEGGINGS, new ArmorItem(ModArmorMaterials.TURTLE_SCUTE_ARMOR_MATERIAL,
                ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(20))));
        TURTLE_SCUTE_BOOTS = reg(GeneratedRegistrationIds.Items.TURTLE_SCUTE_BOOTS, new ArmorItem(ModArmorMaterials.TURTLE_SCUTE_ARMOR_MATERIAL,
                ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(20))));

        // Echo shard tools + armor
        regStdToolSet(GeneratedRegistrationIds.Items.toolSet("echo_shard"), ModToolTiers.ECHO_SHARD, (t,s)->ECHO_SHARD_SWORD=s, (t,s)->ECHO_SHARD_PICKAXE=s, (t,s)->ECHO_SHARD_SHOVEL=s, (t,s)->ECHO_SHARD_AXE=s, (t,s)->ECHO_SHARD_HOE=s);
        ECHO_SHARD_HELMET = reg(GeneratedRegistrationIds.Items.ECHO_SHARD_HELMET, new ArmorItem(ModArmorMaterials.ECHO_SHARD_ARMOR_MATERIAL,
                ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(25))));
        ECHO_SHARD_CHESTPLATE = reg(GeneratedRegistrationIds.Items.ECHO_SHARD_CHESTPLATE, new ArmorItem(ModArmorMaterials.ECHO_SHARD_ARMOR_MATERIAL,
                ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(25))));
        ECHO_SHARD_LEGGINGS = reg(GeneratedRegistrationIds.Items.ECHO_SHARD_LEGGINGS, new ArmorItem(ModArmorMaterials.ECHO_SHARD_ARMOR_MATERIAL,
                ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(25))));
        ECHO_SHARD_BOOTS = reg(GeneratedRegistrationIds.Items.ECHO_SHARD_BOOTS, new ArmorItem(ModArmorMaterials.ECHO_SHARD_ARMOR_MATERIAL,
                ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(25))));

        // Dragon breath tools + armor
        regStdToolSet(GeneratedRegistrationIds.Items.toolSet("dragon_breath"), ModToolTiers.DRAGON_BREATH, (t,s)->DRAGON_BREATH_SWORD=s, (t,s)->DRAGON_BREATH_PICKAXE=s, (t,s)->DRAGON_BREATH_SHOVEL=s, (t,s)->DRAGON_BREATH_AXE=s, (t,s)->DRAGON_BREATH_HOE=s);
        DRAGON_BREATH_HELMET = reg(GeneratedRegistrationIds.Items.DRAGON_BREATH_HELMET, new ArmorItem(ModArmorMaterials.DRAGON_BREATH_ARMOR_MATERIAL,
                ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(30))));
        DRAGON_BREATH_CHESTPLATE = reg(GeneratedRegistrationIds.Items.DRAGON_BREATH_CHESTPLATE, new ArmorItem(ModArmorMaterials.DRAGON_BREATH_ARMOR_MATERIAL,
                ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(30))));
        DRAGON_BREATH_LEGGINGS = reg(GeneratedRegistrationIds.Items.DRAGON_BREATH_LEGGINGS, new ArmorItem(ModArmorMaterials.DRAGON_BREATH_ARMOR_MATERIAL,
                ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(30))));
        DRAGON_BREATH_BOOTS = reg(GeneratedRegistrationIds.Items.DRAGON_BREATH_BOOTS, new ArmorItem(ModArmorMaterials.DRAGON_BREATH_ARMOR_MATERIAL,
                ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(30))));

        // Food tools + armor: Bread
        regEdibleToolSet(GeneratedRegistrationIds.Items.toolSet("bread"), ModToolTiers.BREAD_TOOL, (t,s)->BREAD_SWORD=s, (t,s)->BREAD_PICKAXE=s, (t,s)->BREAD_SHOVEL=s, (t,s)->BREAD_AXE=s, (t,s)->BREAD_HOE=s);
        regEdibleArmorSet(GeneratedRegistrationIds.Items.armorSet("bread"), ModArmorMaterials.BREAD_ARMOR_MATERIAL, 5, (t,s)->BREAD_HELMET=s, (t,s)->BREAD_CHESTPLATE=s, (t,s)->BREAD_LEGGINGS=s, (t,s)->BREAD_BOOTS=s);

        // Food tools + armor: Dried Kelp
        regEdibleToolSet(GeneratedRegistrationIds.Items.toolSet("dried_kelp"), ModToolTiers.DRIED_KELP_TOOL, (t,s)->DRIED_KELP_SWORD=s, (t,s)->DRIED_KELP_PICKAXE=s, (t,s)->DRIED_KELP_SHOVEL=s, (t,s)->DRIED_KELP_AXE=s, (t,s)->DRIED_KELP_HOE=s);
        regEdibleArmorSet(GeneratedRegistrationIds.Items.armorSet("dried_kelp"), ModArmorMaterials.DRIED_KELP_ARMOR_MATERIAL, 3, (t,s)->DRIED_KELP_HELMET=s, (t,s)->DRIED_KELP_CHESTPLATE=s, (t,s)->DRIED_KELP_LEGGINGS=s, (t,s)->DRIED_KELP_BOOTS=s);

        // Food tools + armor: Rotten Flesh
        regEdibleToolSet(GeneratedRegistrationIds.Items.toolSet("rotten_flesh"), ModToolTiers.ROTTEN_FLESH_TOOL, (t,s)->ROTTEN_FLESH_SWORD=s, (t,s)->ROTTEN_FLESH_PICKAXE=s, (t,s)->ROTTEN_FLESH_SHOVEL=s, (t,s)->ROTTEN_FLESH_AXE=s, (t,s)->ROTTEN_FLESH_HOE=s);
        regEdibleArmorSet(GeneratedRegistrationIds.Items.armorSet("rotten_flesh"), ModArmorMaterials.ROTTEN_FLESH_ARMOR_MATERIAL, 5, (t,s)->ROTTEN_FLESH_HELMET=s, (t,s)->ROTTEN_FLESH_CHESTPLATE=s, (t,s)->ROTTEN_FLESH_LEGGINGS=s, (t,s)->ROTTEN_FLESH_BOOTS=s);

        // Food tools + armor: Melon
        regEdibleToolSet(GeneratedRegistrationIds.Items.toolSet("melon"), ModToolTiers.MELON_TOOL, (t,s)->MELON_SWORD=s, (t,s)->MELON_PICKAXE=s, (t,s)->MELON_SHOVEL=s, (t,s)->MELON_AXE=s, (t,s)->MELON_HOE=s);
        regEdibleArmorSet(GeneratedRegistrationIds.Items.armorSet("melon"), ModArmorMaterials.MELON_ARMOR_MATERIAL, 5, (t,s)->MELON_HELMET=s, (t,s)->MELON_CHESTPLATE=s, (t,s)->MELON_LEGGINGS=s, (t,s)->MELON_BOOTS=s);

        // Food tools + armor: Sweet Berry
        regEdibleToolSet(GeneratedRegistrationIds.Items.toolSet("sweet_berry"), ModToolTiers.SWEET_BERRY_TOOL, (t,s)->SWEET_BERRY_SWORD=s, (t,s)->SWEET_BERRY_PICKAXE=s, (t,s)->SWEET_BERRY_SHOVEL=s, (t,s)->SWEET_BERRY_AXE=s, (t,s)->SWEET_BERRY_HOE=s);
        regEdibleArmorSet(GeneratedRegistrationIds.Items.armorSet("sweet_berry"), ModArmorMaterials.SWEET_BERRY_ARMOR_MATERIAL, 5, (t,s)->SWEET_BERRY_HELMET=s, (t,s)->SWEET_BERRY_CHESTPLATE=s, (t,s)->SWEET_BERRY_LEGGINGS=s, (t,s)->SWEET_BERRY_BOOTS=s);

        // Food tools + armor: Pumpkin Pie
        regEdibleToolSet(GeneratedRegistrationIds.Items.toolSet("pumpkin_pie"), ModToolTiers.PUMPKIN_PIE_TOOL, (t,s)->PUMPKIN_PIE_SWORD=s, (t,s)->PUMPKIN_PIE_PICKAXE=s, (t,s)->PUMPKIN_PIE_SHOVEL=s, (t,s)->PUMPKIN_PIE_AXE=s, (t,s)->PUMPKIN_PIE_HOE=s);
        regEdibleArmorSet(GeneratedRegistrationIds.Items.armorSet("pumpkin_pie"), ModArmorMaterials.PUMPKIN_PIE_ARMOR_MATERIAL, 7, (t,s)->PUMPKIN_PIE_HELMET=s, (t,s)->PUMPKIN_PIE_CHESTPLATE=s, (t,s)->PUMPKIN_PIE_LEGGINGS=s, (t,s)->PUMPKIN_PIE_BOOTS=s);

        // Food tools + armor: Mushroom
        regEdibleToolSet(GeneratedRegistrationIds.Items.toolSet("mushroom"), ModToolTiers.MUSHROOM_TOOL, (t,s)->MUSHROOM_SWORD=s, (t,s)->MUSHROOM_PICKAXE=s, (t,s)->MUSHROOM_SHOVEL=s, (t,s)->MUSHROOM_AXE=s, (t,s)->MUSHROOM_HOE=s);
        regEdibleArmorSet(GeneratedRegistrationIds.Items.armorSet("mushroom"), ModArmorMaterials.MUSHROOM_ARMOR_MATERIAL, 10, (t,s)->MUSHROOM_HELMET=s, (t,s)->MUSHROOM_CHESTPLATE=s, (t,s)->MUSHROOM_LEGGINGS=s, (t,s)->MUSHROOM_BOOTS=s);

        // Food tools + armor: Pufferfish
        regEdibleToolSet(GeneratedRegistrationIds.Items.toolSet("pufferfish"), ModToolTiers.PUFFERFISH_TOOL, (t,s)->PUFFERFISH_SWORD=s, (t,s)->PUFFERFISH_PICKAXE=s, (t,s)->PUFFERFISH_SHOVEL=s, (t,s)->PUFFERFISH_AXE=s, (t,s)->PUFFERFISH_HOE=s);
        regEdibleArmorSet(GeneratedRegistrationIds.Items.armorSet("pufferfish"), ModArmorMaterials.PUFFERFISH_ARMOR_MATERIAL, 8, (t,s)->PUFFERFISH_HELMET=s, (t,s)->PUFFERFISH_CHESTPLATE=s, (t,s)->PUFFERFISH_LEGGINGS=s, (t,s)->PUFFERFISH_BOOTS=s);

        // Food tools + armor: Honey
        regEdibleToolSet(GeneratedRegistrationIds.Items.toolSet("honey"), ModToolTiers.HONEY_TOOL, (t,s)->HONEY_SWORD=s, (t,s)->HONEY_PICKAXE=s, (t,s)->HONEY_SHOVEL=s, (t,s)->HONEY_AXE=s, (t,s)->HONEY_HOE=s);
        regEdibleArmorSet(GeneratedRegistrationIds.Items.armorSet("honey"), ModArmorMaterials.HONEY_ARMOR_MATERIAL, 10, (t,s)->HONEY_HELMET=s, (t,s)->HONEY_CHESTPLATE=s, (t,s)->HONEY_LEGGINGS=s, (t,s)->HONEY_BOOTS=s);

        // Food tools + armor: Chorus Fruit
        regEdibleToolSet(GeneratedRegistrationIds.Items.toolSet("chorus_fruit"), ModToolTiers.CHORUS_FRUIT_TOOL, (t,s)->CHORUS_FRUIT_SWORD=s, (t,s)->CHORUS_FRUIT_PICKAXE=s, (t,s)->CHORUS_FRUIT_SHOVEL=s, (t,s)->CHORUS_FRUIT_AXE=s, (t,s)->CHORUS_FRUIT_HOE=s);
        regEdibleArmorSet(GeneratedRegistrationIds.Items.armorSet("chorus_fruit"), ModArmorMaterials.CHORUS_FRUIT_ARMOR_MATERIAL, 15, (t,s)->CHORUS_FRUIT_HELMET=s, (t,s)->CHORUS_FRUIT_CHESTPLATE=s, (t,s)->CHORUS_FRUIT_LEGGINGS=s, (t,s)->CHORUS_FRUIT_BOOTS=s);

        // Food tools + armor: Golden Apple
        regEdibleToolSet(GeneratedRegistrationIds.Items.toolSet("golden_apple"), ModToolTiers.GOLDEN_APPLE_TOOL, (t,s)->GOLDEN_APPLE_SWORD=s, (t,s)->GOLDEN_APPLE_PICKAXE=s, (t,s)->GOLDEN_APPLE_SHOVEL=s, (t,s)->GOLDEN_APPLE_AXE=s, (t,s)->GOLDEN_APPLE_HOE=s);
        regEdibleArmorSet(GeneratedRegistrationIds.Items.armorSet("golden_apple"), ModArmorMaterials.GOLDEN_APPLE_ARMOR_MATERIAL, 22, (t,s)->GOLDEN_APPLE_HELMET=s, (t,s)->GOLDEN_APPLE_CHESTPLATE=s, (t,s)->GOLDEN_APPLE_LEGGINGS=s, (t,s)->GOLDEN_APPLE_BOOTS=s);

        // Spawn eggs
        GHOST_SPAWN_EGG = reg(GeneratedRegistrationIds.Items.GHOST_SPAWN_EGG,
                new SpawnEggItem(ModEntities.GHOST, 0xFFFFFF, 0x999999, new Item.Settings()));
    }

    // Helper: register a standard 5-tool set with default attack modifiers
    @FunctionalInterface
    private interface ItemSetter { void set(String type, Item item); }

    private static void regStdToolSet(String[] ids, net.minecraft.item.ToolMaterial tier,
                                       ItemSetter sword, ItemSetter pickaxe, ItemSetter shovel, ItemSetter axe, ItemSetter hoe) {
        sword.set("sword", reg(ids[0], new SwordItem(tier,
                new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(tier, 3, -2.4f)))));
        pickaxe.set("pickaxe", reg(ids[1], new PickaxeItem(tier,
                new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(tier, 1, -2.8f)))));
        shovel.set("shovel", reg(ids[2], new ShovelItem(tier,
                new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(tier, 1.5f, -3f)))));
        axe.set("axe", reg(ids[3], new AxeItem(tier,
                new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(tier, 6, -3.2f)))));
        hoe.set("hoe", reg(ids[4], new HoeItem(tier,
                new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(tier, 0, -3f)))));
    }

    // Helper: register an edible 5-tool set with default attack modifiers and food values
    private static void regEdibleToolSet(String[] ids, net.minecraft.item.ToolMaterial tier,
                                          ItemSetter sword, ItemSetter pickaxe, ItemSetter shovel, ItemSetter axe, ItemSetter hoe) {
        sword.set("sword", reg(ids[0], new EdibleSwordItem(tier,
                new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(tier, 3, -2.4f))
                        .food(food(4)))));
        pickaxe.set("pickaxe", reg(ids[1], new EdiblePickaxeItem(tier,
                new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(tier, 1, -2.8f))
                        .food(food(6)))));
        shovel.set("shovel", reg(ids[2], new EdibleShovelItem(tier,
                new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(tier, 1.5f, -3f))
                        .food(food(2)))));
        axe.set("axe", reg(ids[3], new EdibleAxeItem(tier,
                new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(tier, 6, -3.2f))
                        .food(food(6)))));
        hoe.set("hoe", reg(ids[4], new EdibleHoeItem(tier,
                new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(tier, 0, -3f))
                        .food(food(4)))));
    }

    // Helper: register an edible 4-armor set with food values
    private static void regEdibleArmorSet(String[] ids, net.minecraft.registry.entry.RegistryEntry<net.minecraft.item.ArmorMaterial> material,
                                           int durabilityMultiplier,
                                           ItemSetter helmet, ItemSetter chestplate, ItemSetter leggings, ItemSetter boots) {
        helmet.set("helmet", reg(ids[0], new EdibleArmorItem(material,
                ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(durabilityMultiplier))
                        .food(food(10)))));
        chestplate.set("chestplate", reg(ids[1], new EdibleArmorItem(material,
                ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(durabilityMultiplier))
                        .food(food(14)))));
        leggings.set("leggings", reg(ids[2], new EdibleArmorItem(material,
                ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(durabilityMultiplier))
                        .food(food(14)))));
        boots.set("boots", reg(ids[3], new EdibleArmorItem(material,
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
