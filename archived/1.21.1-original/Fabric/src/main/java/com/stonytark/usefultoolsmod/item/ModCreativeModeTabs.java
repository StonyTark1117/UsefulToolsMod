package com.stonytark.usefultoolsmod.item;

import com.stonytark.usefultoolsmod.Config;
import com.stonytark.usefultoolsmod.UsefultoolsMod;
import com.stonytark.usefultoolsmod.block.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModCreativeModeTabs {

    public static void register() {
        // Custom creative tab with all mod items
        Registry.register(Registries.ITEM_GROUP,
                Identifier.of(UsefultoolsMod.MOD_ID, "useful_tools_tab"),
                FabricItemGroup.builder()
                        .displayName(Text.translatable("creativetab.usefultoolsmod.useful_tools"))
                        .icon(() -> new ItemStack(ModItems.RGOLD))
                        .entries((context, entries) -> {

                            // =============================================================
                            //  UTILITY
                            // =============================================================

                            if (Config.explosivesEnabled) {
                                entries.add(ModItems.DYNAMITE);
                                entries.add(ModItems.GRENADE);
                            }

                            // =============================================================
                            //  VANILLA VARIANT TOOLS (wood & stone tier reskins)
                            // =============================================================

                            // --- Leather ---
                            if (Config.leatherEnabled) {
                                entries.add(ModItems.LEATHER_SWORD);
                                entries.add(ModItems.LEATHER_PICKAXE);
                                entries.add(ModItems.LEATHER_SHOVEL);
                                entries.add(ModItems.LEATHER_AXE);
                                entries.add(ModItems.LEATHER_HOE);
                            }

                            // --- Wood Variants ---
                            if (Config.woodVariantsEnabled) {
                                entries.add(ModItems.OAK_SWORD);
                                entries.add(ModItems.OAK_PICKAXE);
                                entries.add(ModItems.OAK_SHOVEL);
                                entries.add(ModItems.OAK_AXE);
                                entries.add(ModItems.OAK_HOE);
                                entries.add(ModItems.SPRUCE_SWORD);
                                entries.add(ModItems.SPRUCE_PICKAXE);
                                entries.add(ModItems.SPRUCE_SHOVEL);
                                entries.add(ModItems.SPRUCE_AXE);
                                entries.add(ModItems.SPRUCE_HOE);
                                entries.add(ModItems.BIRCH_SWORD);
                                entries.add(ModItems.BIRCH_PICKAXE);
                                entries.add(ModItems.BIRCH_SHOVEL);
                                entries.add(ModItems.BIRCH_AXE);
                                entries.add(ModItems.BIRCH_HOE);
                                entries.add(ModItems.JUNGLE_SWORD);
                                entries.add(ModItems.JUNGLE_PICKAXE);
                                entries.add(ModItems.JUNGLE_SHOVEL);
                                entries.add(ModItems.JUNGLE_AXE);
                                entries.add(ModItems.JUNGLE_HOE);
                                entries.add(ModItems.ACACIA_SWORD);
                                entries.add(ModItems.ACACIA_PICKAXE);
                                entries.add(ModItems.ACACIA_SHOVEL);
                                entries.add(ModItems.ACACIA_AXE);
                                entries.add(ModItems.ACACIA_HOE);
                                entries.add(ModItems.DARK_OAK_SWORD);
                                entries.add(ModItems.DARK_OAK_PICKAXE);
                                entries.add(ModItems.DARK_OAK_SHOVEL);
                                entries.add(ModItems.DARK_OAK_AXE);
                                entries.add(ModItems.DARK_OAK_HOE);
                                entries.add(ModItems.MANGROVE_SWORD);
                                entries.add(ModItems.MANGROVE_PICKAXE);
                                entries.add(ModItems.MANGROVE_SHOVEL);
                                entries.add(ModItems.MANGROVE_AXE);
                                entries.add(ModItems.MANGROVE_HOE);
                                entries.add(ModItems.CHERRY_SWORD);
                                entries.add(ModItems.CHERRY_PICKAXE);
                                entries.add(ModItems.CHERRY_SHOVEL);
                                entries.add(ModItems.CHERRY_AXE);
                                entries.add(ModItems.CHERRY_HOE);
                                entries.add(ModItems.BAMBOO_SWORD);
                                entries.add(ModItems.BAMBOO_PICKAXE);
                                entries.add(ModItems.BAMBOO_SHOVEL);
                                entries.add(ModItems.BAMBOO_AXE);
                                entries.add(ModItems.BAMBOO_HOE);
                                entries.add(ModItems.CRIMSON_SWORD);
                                entries.add(ModItems.CRIMSON_PICKAXE);
                                entries.add(ModItems.CRIMSON_SHOVEL);
                                entries.add(ModItems.CRIMSON_AXE);
                                entries.add(ModItems.CRIMSON_HOE);
                                entries.add(ModItems.WARPED_SWORD);
                                entries.add(ModItems.WARPED_PICKAXE);
                                entries.add(ModItems.WARPED_SHOVEL);
                                entries.add(ModItems.WARPED_AXE);
                                entries.add(ModItems.WARPED_HOE);
                            }

                            // --- Stone Rock Variants ---
                            if (Config.stoneVariantsEnabled) {
                                entries.add(ModItems.ANDESITE_SWORD);
                                entries.add(ModItems.ANDESITE_PICKAXE);
                                entries.add(ModItems.ANDESITE_SHOVEL);
                                entries.add(ModItems.ANDESITE_AXE);
                                entries.add(ModItems.ANDESITE_HOE);
                                entries.add(ModItems.BASALT_SWORD);
                                entries.add(ModItems.BASALT_PICKAXE);
                                entries.add(ModItems.BASALT_SHOVEL);
                                entries.add(ModItems.BASALT_AXE);
                                entries.add(ModItems.BASALT_HOE);
                                entries.add(ModItems.BLACKSTONE_SWORD);
                                entries.add(ModItems.BLACKSTONE_PICKAXE);
                                entries.add(ModItems.BLACKSTONE_SHOVEL);
                                entries.add(ModItems.BLACKSTONE_AXE);
                                entries.add(ModItems.BLACKSTONE_HOE);
                                entries.add(ModItems.CALCITE_SWORD);
                                entries.add(ModItems.CALCITE_PICKAXE);
                                entries.add(ModItems.CALCITE_SHOVEL);
                                entries.add(ModItems.CALCITE_AXE);
                                entries.add(ModItems.CALCITE_HOE);
                                entries.add(ModItems.DEEPSLATE_SWORD);
                                entries.add(ModItems.DEEPSLATE_PICKAXE);
                                entries.add(ModItems.DEEPSLATE_SHOVEL);
                                entries.add(ModItems.DEEPSLATE_AXE);
                                entries.add(ModItems.DEEPSLATE_HOE);
                                entries.add(ModItems.DIORITE_SWORD);
                                entries.add(ModItems.DIORITE_PICKAXE);
                                entries.add(ModItems.DIORITE_SHOVEL);
                                entries.add(ModItems.DIORITE_AXE);
                                entries.add(ModItems.DIORITE_HOE);
                                entries.add(ModItems.END_STONE_SWORD);
                                entries.add(ModItems.END_STONE_PICKAXE);
                                entries.add(ModItems.END_STONE_SHOVEL);
                                entries.add(ModItems.END_STONE_AXE);
                                entries.add(ModItems.END_STONE_HOE);
                                entries.add(ModItems.GRANITE_SWORD);
                                entries.add(ModItems.GRANITE_PICKAXE);
                                entries.add(ModItems.GRANITE_SHOVEL);
                                entries.add(ModItems.GRANITE_AXE);
                                entries.add(ModItems.GRANITE_HOE);
                                entries.add(ModItems.NETHERRACK_SWORD);
                                entries.add(ModItems.NETHERRACK_PICKAXE);
                                entries.add(ModItems.NETHERRACK_SHOVEL);
                                entries.add(ModItems.NETHERRACK_AXE);
                                entries.add(ModItems.NETHERRACK_HOE);
                                entries.add(ModItems.SANDSTONE_SWORD);
                                entries.add(ModItems.SANDSTONE_PICKAXE);
                                entries.add(ModItems.SANDSTONE_SHOVEL);
                                entries.add(ModItems.SANDSTONE_AXE);
                                entries.add(ModItems.SANDSTONE_HOE);
                                entries.add(ModItems.SMOOTH_BASALT_SWORD);
                                entries.add(ModItems.SMOOTH_BASALT_PICKAXE);
                                entries.add(ModItems.SMOOTH_BASALT_SHOVEL);
                                entries.add(ModItems.SMOOTH_BASALT_AXE);
                                entries.add(ModItems.SMOOTH_BASALT_HOE);
                                entries.add(ModItems.TERRACOTTA_SWORD);
                                entries.add(ModItems.TERRACOTTA_PICKAXE);
                                entries.add(ModItems.TERRACOTTA_SHOVEL);
                                entries.add(ModItems.TERRACOTTA_AXE);
                                entries.add(ModItems.TERRACOTTA_HOE);
                                entries.add(ModItems.TUFF_SWORD);
                                entries.add(ModItems.TUFF_PICKAXE);
                                entries.add(ModItems.TUFF_SHOVEL);
                                entries.add(ModItems.TUFF_AXE);
                                entries.add(ModItems.TUFF_HOE);
                            }

                            // =============================================================
                            //  NATURAL MATERIAL SETS (flint, coal)
                            // =============================================================

                            // --- Flint ---
                            if (Config.flintEnabled) {
                                entries.add(ModItems.RFLINT_SWORD);
                                entries.add(ModItems.RFLINT_PICKAXE);
                                entries.add(ModItems.RFLINT_SHOVEL);
                                entries.add(ModItems.RFLINT_AXE);
                                entries.add(ModItems.RFLINT_HOE);
                            }

                            // --- Flint-Iron (FNI) ---
                            if (Config.fniEnabled) {
                                entries.add(ModItems.FNI_SWORD);
                                entries.add(ModItems.FNI_PICKAXE);
                                entries.add(ModItems.FNI_SHOVEL);
                                entries.add(ModItems.FNI_AXE);
                                entries.add(ModItems.FNI_HOE);
                                entries.add(ModItems.FNI_HELMET);
                                entries.add(ModItems.FNI_CHESTPLATE);
                                entries.add(ModItems.FNI_LEGGINGS);
                                entries.add(ModItems.FNI_BOOTS);
                            }

                            // --- Coal ---
                            if (Config.coalEnabled) {
                                entries.add(ModItems.COAL_DUST);
                                entries.add(ModBlocks.COAL_DUST_BLOCK);
                                entries.add(ModItems.HARDENED_COAL);
                                entries.add(ModBlocks.HARDENED_COAL_BLOCK);
                                entries.add(ModItems.COAL_SWORD);
                                entries.add(ModItems.COAL_PICKAXE);
                                entries.add(ModItems.COAL_SHOVEL);
                                entries.add(ModItems.COAL_AXE);
                                entries.add(ModItems.COAL_HOE);
                                entries.add(ModItems.COAL_HELMET);
                                entries.add(ModItems.COAL_CHESTPLATE);
                                entries.add(ModItems.COAL_LEGGINGS);
                                entries.add(ModItems.COAL_BOOTS);
                            }

                            // --- Raw Metal Rough ---
                            if (Config.rawMetalRoughEnabled) {
                                entries.add(ModItems.RRAW_GOLD_SWORD);
                                entries.add(ModItems.RRAW_GOLD_PICKAXE);
                                entries.add(ModItems.RRAW_GOLD_SHOVEL);
                                entries.add(ModItems.RRAW_GOLD_AXE);
                                entries.add(ModItems.RRAW_GOLD_HOE);
                                entries.add(ModItems.RRAW_COPPER_SWORD);
                                entries.add(ModItems.RRAW_COPPER_PICKAXE);
                                entries.add(ModItems.RRAW_COPPER_SHOVEL);
                                entries.add(ModItems.RRAW_COPPER_AXE);
                                entries.add(ModItems.RRAW_COPPER_HOE);
                                entries.add(ModItems.RRAW_IRON_SWORD);
                                entries.add(ModItems.RRAW_IRON_PICKAXE);
                                entries.add(ModItems.RRAW_IRON_SHOVEL);
                                entries.add(ModItems.RRAW_IRON_AXE);
                                entries.add(ModItems.RRAW_IRON_HOE);
                                entries.add(ModItems.RRAW_RGOLD_SWORD);
                                entries.add(ModItems.RRAW_RGOLD_PICKAXE);
                                entries.add(ModItems.RRAW_RGOLD_SHOVEL);
                                entries.add(ModItems.RRAW_RGOLD_AXE);
                                entries.add(ModItems.RRAW_RGOLD_HOE);
                                entries.add(ModItems.RSCRAP_SWORD);
                                entries.add(ModItems.RSCRAP_PICKAXE);
                                entries.add(ModItems.RSCRAP_SHOVEL);
                                entries.add(ModItems.RSCRAP_AXE);
                                entries.add(ModItems.RSCRAP_HOE);
                            }

                            // =============================================================
                            //  CRYSTAL & ICE SETS
                            // =============================================================

                            // --- Crystal material items ---
                            if (Config.roughCrystalEnabled || Config.polishedCrystalEnabled) {
                                entries.add(ModItems.CALCIFIED_AMETHYST);
                                entries.add(ModBlocks.CALCIFIED_AMETHYST_BLOCK);
                                entries.add(ModItems.POLISHED_QUARTZ);
                                entries.add(ModBlocks.POLISHED_QUARTZ_BLOCK);
                            }
                            if (Config.iceEnabled || Config.snowEnabled) {
                                entries.add(ModItems.GLACIAL_SHARD);
                                entries.add(ModBlocks.GLACIAL_SHARD_BLOCK);
                            }
                            if (Config.roughCrystalEnabled || Config.pprismEnabled) {
                                entries.add(ModItems.POLISHED_PRISMARINE);
                                entries.add(ModBlocks.POLISHED_PRISMARINE_BLOCK);
                            }

                            // --- Rough Crystal ---
                            if (Config.roughCrystalEnabled) {
                                entries.add(ModItems.RAMETHYST_SWORD);
                                entries.add(ModItems.RAMETHYST_PICKAXE);
                                entries.add(ModItems.RAMETHYST_SHOVEL);
                                entries.add(ModItems.RAMETHYST_AXE);
                                entries.add(ModItems.RAMETHYST_HOE);
                                entries.add(ModItems.RQUARTZ_SWORD);
                                entries.add(ModItems.RQUARTZ_PICKAXE);
                                entries.add(ModItems.RQUARTZ_SHOVEL);
                                entries.add(ModItems.RQUARTZ_AXE);
                                entries.add(ModItems.RQUARTZ_HOE);
                                entries.add(ModItems.RPRISM_SWORD);
                                entries.add(ModItems.RPRISM_PICKAXE);
                                entries.add(ModItems.RPRISM_SHOVEL);
                                entries.add(ModItems.RPRISM_AXE);
                                entries.add(ModItems.RPRISM_HOE);
                            }

                            // --- Snow ---
                            if (Config.snowEnabled) {
                                entries.add(ModItems.SNOW_SWORD);
                                entries.add(ModItems.SNOW_PICKAXE);
                                entries.add(ModItems.SNOW_SHOVEL);
                                entries.add(ModItems.SNOW_AXE);
                                entries.add(ModItems.SNOW_HOE);
                            }

                            // --- Ice (Glacial) ---
                            if (Config.iceEnabled) {
                                entries.add(ModItems.ICE_SWORD);
                                entries.add(ModItems.ICE_PICKAXE);
                                entries.add(ModItems.ICE_SHOVEL);
                                entries.add(ModItems.ICE_AXE);
                                entries.add(ModItems.ICE_HOE);
                                entries.add(ModItems.ICE_HELMET);
                                entries.add(ModItems.ICE_CHESTPLATE);
                                entries.add(ModItems.ICE_LEGGINGS);
                                entries.add(ModItems.ICE_BOOTS);
                            }

                            // --- Polished Crystal ---
                            if (Config.polishedCrystalEnabled) {
                                entries.add(ModItems.CAMETHYST_SWORD);
                                entries.add(ModItems.CAMETHYST_PICKAXE);
                                entries.add(ModItems.CAMETHYST_SHOVEL);
                                entries.add(ModItems.CAMETHYST_AXE);
                                entries.add(ModItems.CAMETHYST_HOE);
                                entries.add(ModItems.CAMETHYST_HELMET);
                                entries.add(ModItems.CAMETHYST_CHESTPLATE);
                                entries.add(ModItems.CAMETHYST_LEGGINGS);
                                entries.add(ModItems.CAMETHYST_BOOTS);
                                entries.add(ModItems.PQUARTZ_SWORD);
                                entries.add(ModItems.PQUARTZ_PICKAXE);
                                entries.add(ModItems.PQUARTZ_SHOVEL);
                                entries.add(ModItems.PQUARTZ_AXE);
                                entries.add(ModItems.PQUARTZ_HOE);
                                entries.add(ModItems.PQUARTZ_HELMET);
                                entries.add(ModItems.PQUARTZ_CHESTPLATE);
                                entries.add(ModItems.PQUARTZ_LEGGINGS);
                                entries.add(ModItems.PQUARTZ_BOOTS);
                            }

                            // --- Polished Prismarine ---
                            if (Config.pprismEnabled) {
                                entries.add(ModItems.PPRISM_SWORD);
                                entries.add(ModItems.PPRISM_PICKAXE);
                                entries.add(ModItems.PPRISM_SHOVEL);
                                entries.add(ModItems.PPRISM_AXE);
                                entries.add(ModItems.PPRISM_HOE);
                                entries.add(ModItems.PPRISM_HELMET);
                                entries.add(ModItems.PPRISM_CHESTPLATE);
                                entries.add(ModItems.PPRISM_LEGGINGS);
                                entries.add(ModItems.PPRISM_BOOTS);
                            }

                            // =============================================================
                            //  METAL & GEM SETS
                            // =============================================================

                            // --- Ferrous Gold ---
                            if (Config.ferrousGoldEnabled) {
                                entries.add(ModBlocks.RGOLDBLOCK);
                                entries.add(ModBlocks.RAW_RGOLD_BLOCK);
                                entries.add(ModBlocks.RGOLDORE);
                                entries.add(ModBlocks.RGOLD_NETHER_ORE);
                                entries.add(ModBlocks.RGOLD_END_ORE);
                                entries.add(ModBlocks.RGOLD_DEEPSLATE_ORE);
                                entries.add(ModItems.RAW_RGOLD);
                                entries.add(ModItems.RGOLD);
                                entries.add(ModItems.RGOLD_AXE);
                                entries.add(ModItems.RGOLD_PICKAXE);
                                entries.add(ModItems.RGOLD_SWORD);
                                entries.add(ModItems.RGOLD_SHOVEL);
                                entries.add(ModItems.RGOLD_HOE);
                                entries.add(ModItems.RGOLD_HELMET);
                                entries.add(ModItems.RGOLD_CHESTPLATE);
                                entries.add(ModItems.RGOLD_LEGGINGS);
                                entries.add(ModItems.RGOLD_BOOTS);
                            }

                            // --- Lapis ---
                            if (Config.lapisEnabled) {
                                entries.add(ModBlocks.LBLOCK);
                                entries.add(ModItems.RLAPIS);
                                entries.add(ModItems.RLAPIS_AXE);
                                entries.add(ModItems.RLAPIS_PICKAXE);
                                entries.add(ModItems.RLAPIS_SWORD);
                                entries.add(ModItems.RLAPIS_SHOVEL);
                                entries.add(ModItems.RLAPIS_HOE);
                                entries.add(ModItems.RLAPIS_HELMET);
                                entries.add(ModItems.RLAPIS_CHESTPLATE);
                                entries.add(ModItems.RLAPIS_LEGGINGS);
                                entries.add(ModItems.RLAPIS_BOOTS);
                            }

                            // --- Hardened Redstone ---
                            if (Config.hardenedRedstoneEnabled) {
                                entries.add(ModItems.HRED);
                                entries.add(ModBlocks.HRBLOCK);
                                entries.add(ModItems.HREDSTONE_AXE);
                                entries.add(ModItems.HREDSTONE_PICKAXE);
                                entries.add(ModItems.HREDSTONE_SWORD);
                                entries.add(ModItems.HREDSTONE_SHOVEL);
                                entries.add(ModItems.HREDSTONE_HOE);
                                entries.add(ModItems.HRED_HELMET);
                                entries.add(ModItems.HRED_CHESTPLATE);
                                entries.add(ModItems.HRED_LEGGINGS);
                                entries.add(ModItems.HRED_BOOTS);
                            }

                            // --- Hardened Glowstone ---
                            if (Config.hardenedGlowstoneEnabled) {
                                entries.add(ModItems.HGLOW);
                                entries.add(ModBlocks.HGLOW_BLOCK);
                                entries.add(ModItems.HGLOWSTONE_AXE);
                                entries.add(ModItems.HGLOWSTONE_PICKAXE);
                                entries.add(ModItems.HGLOWSTONE_SWORD);
                                entries.add(ModItems.HGLOWSTONE_SHOVEL);
                                entries.add(ModItems.HGLOWSTONE_HOE);
                                entries.add(ModItems.HGLOW_HELMET);
                                entries.add(ModItems.HGLOW_CHESTPLATE);
                                entries.add(ModItems.HGLOW_LEGGINGS);
                                entries.add(ModItems.HGLOW_BOOTS);
                            }

                            // --- Emerald ---
                            if (Config.emeraldEnabled) {
                                entries.add(ModBlocks.SEMBLOCK);
                                entries.add(ModItems.SEM);
                                entries.add(ModItems.PEMERALD_AXE);
                                entries.add(ModItems.PEMERALD_PICKAXE);
                                entries.add(ModItems.PEMERALD_SWORD);
                                entries.add(ModItems.PEMERALD_SHOVEL);
                                entries.add(ModItems.PEMERALD_HOE);
                                entries.add(ModItems.REMERALD_AXE);
                                entries.add(ModItems.REMERALD_PICKAXE);
                                entries.add(ModItems.REMERALD_SWORD);
                                entries.add(ModItems.REMERALD_SHOVEL);
                                entries.add(ModItems.REMERALD_HOE);
                                entries.add(ModItems.EMERALD_HELMET);
                                entries.add(ModItems.EMERALD_CHESTPLATE);
                                entries.add(ModItems.EMERALD_LEGGINGS);
                                entries.add(ModItems.EMERALD_BOOTS);
                            }

                            // --- Obsidian ---
                            if (Config.obsidianEnabled) {
                                entries.add(ModItems.OBINGOT);
                                entries.add(ModItems.OBSHARD);
                                entries.add(ModBlocks.SOBLOCK);
                                entries.add(ModBlocks.OBSHARD_BLOCK);
                                entries.add(ModItems.ROBSIDIAN_AXE);
                                entries.add(ModItems.ROBSIDIAN_PICKAXE);
                                entries.add(ModItems.ROBSIDIAN_SWORD);
                                entries.add(ModItems.ROBSIDIAN_SHOVEL);
                                entries.add(ModItems.ROBSIDIAN_HOE);
                                entries.add(ModItems.POBSIDIAN_AXE);
                                entries.add(ModItems.POBSIDIAN_PICKAXE);
                                entries.add(ModItems.POBSIDIAN_SWORD);
                                entries.add(ModItems.POBSIDIAN_SHOVEL);
                                entries.add(ModItems.POBSIDIAN_HOE);
                                entries.add(ModItems.OBSIDIAN_HELMET);
                                entries.add(ModItems.OBSIDIAN_CHESTPLATE);
                                entries.add(ModItems.OBSIDIAN_LEGGINGS);
                                entries.add(ModItems.OBSIDIAN_BOOTS);
                            }

                            // =============================================================
                            //  GHOST & ECTOPLASM
                            // =============================================================

                            if (Config.ghostEnabled) {
                                entries.add(ModItems.GHOST_SPAWN_EGG);
                                entries.add(ModItems.ECTOPLASM);
                            }

                            if (Config.spectralInfuserEnabled) {
                                entries.add(ModBlocks.SPECTRAL_INFUSER);
                            }

                            if (Config.ectoplasmSetEnabled) {
                                entries.add(ModBlocks.ECTOPLASM_BLOCK);
                                entries.add(ModItems.RECTO_SWORD);
                                entries.add(ModItems.RECTO_PICKAXE);
                                entries.add(ModItems.RECTO_SHOVEL);
                                entries.add(ModItems.RECTO_AXE);
                                entries.add(ModItems.RECTO_HOE);
                                entries.add(ModItems.REFINED_ECTOPLASM);
                                entries.add(ModBlocks.REFINED_ECTOPLASM_BLOCK);
                                entries.add(ModItems.ECTO_SWORD);
                                entries.add(ModItems.ECTO_PICKAXE);
                                entries.add(ModItems.ECTO_SHOVEL);
                                entries.add(ModItems.ECTO_AXE);
                                entries.add(ModItems.ECTO_HOE);
                                entries.add(ModItems.ECTO_HELMET);
                                entries.add(ModItems.ECTO_CHESTPLATE);
                                entries.add(ModItems.ECTO_LEGGINGS);
                                entries.add(ModItems.ECTO_BOOTS);
                            }

                            // =============================================================
                            //  OVERPOWER (end-game)
                            // =============================================================

                            if (Config.overpowerEnabled) {
                                entries.add(ModItems.OVERPOWER_AXE);
                                entries.add(ModItems.OVERPOWER_PICKAXE);
                                entries.add(ModItems.OVERPOWER_SWORD);
                                entries.add(ModItems.OVERPOWER_SHOVEL);
                                entries.add(ModItems.OVERPOWER_HELMET);
                                entries.add(ModItems.OVERPOWER_CHESTPLATE);
                                entries.add(ModItems.OVERPOWER_LEGGINGS);
                                entries.add(ModItems.OVERPOWER_BOOTS);
                            }

                            // =============================================================
                            //  FOOD SETS (edible novelty -- weakest to strongest)
                            // =============================================================

                            // --- Cake ---
                            if (Config.cakeEnabled) {
                                entries.add(ModItems.CAKE_SWORD);
                                entries.add(ModItems.CAKE_PICKAXE);
                                entries.add(ModItems.CAKE_SHOVEL);
                                entries.add(ModItems.CAKE_AXE);
                                entries.add(ModItems.CAKE_HOE);
                                entries.add(ModItems.CAKE_HELMET);
                                entries.add(ModItems.CAKE_CHESTPLATE);
                                entries.add(ModItems.CAKE_LEGGINGS);
                                entries.add(ModItems.CAKE_BOOTS);
                            }

                            // --- Dried Kelp ---
                            if (Config.driedKelpEnabled) {
                                entries.add(ModItems.DRIED_KELP_SWORD);
                                entries.add(ModItems.DRIED_KELP_PICKAXE);
                                entries.add(ModItems.DRIED_KELP_SHOVEL);
                                entries.add(ModItems.DRIED_KELP_AXE);
                                entries.add(ModItems.DRIED_KELP_HOE);
                                entries.add(ModItems.DRIED_KELP_HELMET);
                                entries.add(ModItems.DRIED_KELP_CHESTPLATE);
                                entries.add(ModItems.DRIED_KELP_LEGGINGS);
                                entries.add(ModItems.DRIED_KELP_BOOTS);
                            }

                            // --- Rotten Flesh ---
                            if (Config.rottenFleshEnabled) {
                                entries.add(ModItems.ROTTEN_FLESH_SWORD);
                                entries.add(ModItems.ROTTEN_FLESH_PICKAXE);
                                entries.add(ModItems.ROTTEN_FLESH_SHOVEL);
                                entries.add(ModItems.ROTTEN_FLESH_AXE);
                                entries.add(ModItems.ROTTEN_FLESH_HOE);
                                entries.add(ModItems.ROTTEN_FLESH_HELMET);
                                entries.add(ModItems.ROTTEN_FLESH_CHESTPLATE);
                                entries.add(ModItems.ROTTEN_FLESH_LEGGINGS);
                                entries.add(ModItems.ROTTEN_FLESH_BOOTS);
                            }

                            // --- Bread ---
                            if (Config.breadEnabled) {
                                entries.add(ModItems.BREAD_SWORD);
                                entries.add(ModItems.BREAD_PICKAXE);
                                entries.add(ModItems.BREAD_SHOVEL);
                                entries.add(ModItems.BREAD_AXE);
                                entries.add(ModItems.BREAD_HOE);
                                entries.add(ModItems.BREAD_HELMET);
                                entries.add(ModItems.BREAD_CHESTPLATE);
                                entries.add(ModItems.BREAD_LEGGINGS);
                                entries.add(ModItems.BREAD_BOOTS);
                            }

                            // --- Sweet Berries ---
                            if (Config.sweetBerryEnabled) {
                                entries.add(ModItems.SWEET_BERRY_SWORD);
                                entries.add(ModItems.SWEET_BERRY_PICKAXE);
                                entries.add(ModItems.SWEET_BERRY_SHOVEL);
                                entries.add(ModItems.SWEET_BERRY_AXE);
                                entries.add(ModItems.SWEET_BERRY_HOE);
                                entries.add(ModItems.SWEET_BERRY_HELMET);
                                entries.add(ModItems.SWEET_BERRY_CHESTPLATE);
                                entries.add(ModItems.SWEET_BERRY_LEGGINGS);
                                entries.add(ModItems.SWEET_BERRY_BOOTS);
                            }

                            // --- Pumpkin Pie ---
                            if (Config.pumpkinPieEnabled) {
                                entries.add(ModItems.PUMPKIN_PIE_SWORD);
                                entries.add(ModItems.PUMPKIN_PIE_PICKAXE);
                                entries.add(ModItems.PUMPKIN_PIE_SHOVEL);
                                entries.add(ModItems.PUMPKIN_PIE_AXE);
                                entries.add(ModItems.PUMPKIN_PIE_HOE);
                                entries.add(ModItems.PUMPKIN_PIE_HELMET);
                                entries.add(ModItems.PUMPKIN_PIE_CHESTPLATE);
                                entries.add(ModItems.PUMPKIN_PIE_LEGGINGS);
                                entries.add(ModItems.PUMPKIN_PIE_BOOTS);
                            }

                            // --- Melon ---
                            if (Config.melonEnabled) {
                                entries.add(ModItems.MELON_SWORD);
                                entries.add(ModItems.MELON_PICKAXE);
                                entries.add(ModItems.MELON_SHOVEL);
                                entries.add(ModItems.MELON_AXE);
                                entries.add(ModItems.MELON_HOE);
                                entries.add(ModItems.MELON_HELMET);
                                entries.add(ModItems.MELON_CHESTPLATE);
                                entries.add(ModItems.MELON_LEGGINGS);
                                entries.add(ModItems.MELON_BOOTS);
                            }

                            // --- Mushroom ---
                            if (Config.mushroomEnabled) {
                                entries.add(ModItems.MUSHROOM_SWORD);
                                entries.add(ModItems.MUSHROOM_PICKAXE);
                                entries.add(ModItems.MUSHROOM_SHOVEL);
                                entries.add(ModItems.MUSHROOM_AXE);
                                entries.add(ModItems.MUSHROOM_HOE);
                                entries.add(ModItems.MUSHROOM_HELMET);
                                entries.add(ModItems.MUSHROOM_CHESTPLATE);
                                entries.add(ModItems.MUSHROOM_LEGGINGS);
                                entries.add(ModItems.MUSHROOM_BOOTS);
                            }

                            // --- Pufferfish ---
                            if (Config.pufferfishEnabled) {
                                entries.add(ModItems.PUFFERFISH_SWORD);
                                entries.add(ModItems.PUFFERFISH_PICKAXE);
                                entries.add(ModItems.PUFFERFISH_SHOVEL);
                                entries.add(ModItems.PUFFERFISH_AXE);
                                entries.add(ModItems.PUFFERFISH_HOE);
                                entries.add(ModItems.PUFFERFISH_HELMET);
                                entries.add(ModItems.PUFFERFISH_CHESTPLATE);
                                entries.add(ModItems.PUFFERFISH_LEGGINGS);
                                entries.add(ModItems.PUFFERFISH_BOOTS);
                            }

                            // --- Honey ---
                            if (Config.honeyEnabled) {
                                entries.add(ModItems.HONEY_SWORD);
                                entries.add(ModItems.HONEY_PICKAXE);
                                entries.add(ModItems.HONEY_SHOVEL);
                                entries.add(ModItems.HONEY_AXE);
                                entries.add(ModItems.HONEY_HOE);
                                entries.add(ModItems.HONEY_HELMET);
                                entries.add(ModItems.HONEY_CHESTPLATE);
                                entries.add(ModItems.HONEY_LEGGINGS);
                                entries.add(ModItems.HONEY_BOOTS);
                            }

                            // --- Chorus Fruit ---
                            if (Config.chorusFruitEnabled) {
                                entries.add(ModItems.CHORUS_FRUIT_SWORD);
                                entries.add(ModItems.CHORUS_FRUIT_PICKAXE);
                                entries.add(ModItems.CHORUS_FRUIT_SHOVEL);
                                entries.add(ModItems.CHORUS_FRUIT_AXE);
                                entries.add(ModItems.CHORUS_FRUIT_HOE);
                                entries.add(ModItems.CHORUS_FRUIT_HELMET);
                                entries.add(ModItems.CHORUS_FRUIT_CHESTPLATE);
                                entries.add(ModItems.CHORUS_FRUIT_LEGGINGS);
                                entries.add(ModItems.CHORUS_FRUIT_BOOTS);
                            }

                            // --- Golden Apple ---
                            if (Config.goldenAppleEnabled) {
                                entries.add(ModItems.GOLDEN_APPLE_SWORD);
                                entries.add(ModItems.GOLDEN_APPLE_PICKAXE);
                                entries.add(ModItems.GOLDEN_APPLE_SHOVEL);
                                entries.add(ModItems.GOLDEN_APPLE_AXE);
                                entries.add(ModItems.GOLDEN_APPLE_HOE);
                                entries.add(ModItems.GOLDEN_APPLE_HELMET);
                                entries.add(ModItems.GOLDEN_APPLE_CHESTPLATE);
                                entries.add(ModItems.GOLDEN_APPLE_LEGGINGS);
                                entries.add(ModItems.GOLDEN_APPLE_BOOTS);
                            }

                            // =============================================================
                            //  VANILLA MATERIAL SETS (25 sets, ordered by power tier)
                            // =============================================================

                            // --- Paper (tools only) ---
                            if (Config.paperEnabled) {
                                entries.add(ModItems.PAPER_SWORD);
                                entries.add(ModItems.PAPER_PICKAXE);
                                entries.add(ModItems.PAPER_SHOVEL);
                                entries.add(ModItems.PAPER_AXE);
                                entries.add(ModItems.PAPER_HOE);
                            }

                            // --- Feather (tools only) ---
                            if (Config.featherEnabled) {
                                entries.add(ModItems.FEATHER_SWORD);
                                entries.add(ModItems.FEATHER_PICKAXE);
                                entries.add(ModItems.FEATHER_SHOVEL);
                                entries.add(ModItems.FEATHER_AXE);
                                entries.add(ModItems.FEATHER_HOE);
                            }

                            // --- Glass (tools only) ---
                            if (Config.glassEnabled) {
                                entries.add(ModItems.GLASS_SWORD);
                                entries.add(ModItems.GLASS_PICKAXE);
                                entries.add(ModItems.GLASS_SHOVEL);
                                entries.add(ModItems.GLASS_AXE);
                                entries.add(ModItems.GLASS_HOE);
                            }

                            // --- Rabbit Hide (armor only) ---
                            if (Config.rabbitHideEnabled) {
                                entries.add(ModItems.RABBIT_HIDE_HELMET);
                                entries.add(ModItems.RABBIT_HIDE_CHESTPLATE);
                                entries.add(ModItems.RABBIT_HIDE_LEGGINGS);
                                entries.add(ModItems.RABBIT_HIDE_BOOTS);
                            }

                            // --- Cactus (tools + armor) ---
                            if (Config.cactusEnabled) {
                                entries.add(ModItems.CACTUS_SWORD);
                                entries.add(ModItems.CACTUS_PICKAXE);
                                entries.add(ModItems.CACTUS_SHOVEL);
                                entries.add(ModItems.CACTUS_AXE);
                                entries.add(ModItems.CACTUS_HOE);
                                entries.add(ModItems.CACTUS_HELMET);
                                entries.add(ModItems.CACTUS_CHESTPLATE);
                                entries.add(ModItems.CACTUS_LEGGINGS);
                                entries.add(ModItems.CACTUS_BOOTS);
                            }

                            // --- Sponge (tools only) ---
                            if (Config.spongeEnabled) {
                                entries.add(ModItems.SPONGE_SWORD);
                                entries.add(ModItems.SPONGE_PICKAXE);
                                entries.add(ModItems.SPONGE_SHOVEL);
                                entries.add(ModItems.SPONGE_AXE);
                                entries.add(ModItems.SPONGE_HOE);
                            }

                            // --- Bone (tools + armor) ---
                            if (Config.boneEnabled) {
                                entries.add(ModItems.BONE_SWORD);
                                entries.add(ModItems.BONE_PICKAXE);
                                entries.add(ModItems.BONE_SHOVEL);
                                entries.add(ModItems.BONE_AXE);
                                entries.add(ModItems.BONE_HOE);
                                entries.add(ModItems.BONE_HELMET);
                                entries.add(ModItems.BONE_CHESTPLATE);
                                entries.add(ModItems.BONE_LEGGINGS);
                                entries.add(ModItems.BONE_BOOTS);
                            }

                            // --- Clay (tools + armor) ---
                            if (Config.clayEnabled) {
                                entries.add(ModItems.CLAY_SWORD);
                                entries.add(ModItems.CLAY_PICKAXE);
                                entries.add(ModItems.CLAY_SHOVEL);
                                entries.add(ModItems.CLAY_AXE);
                                entries.add(ModItems.CLAY_HOE);
                                entries.add(ModItems.CLAY_HELMET);
                                entries.add(ModItems.CLAY_CHESTPLATE);
                                entries.add(ModItems.CLAY_LEGGINGS);
                                entries.add(ModItems.CLAY_BOOTS);
                            }

                            // --- Nether Wart (tools only) ---
                            if (Config.netherWartEnabled) {
                                entries.add(ModItems.NETHER_WART_SWORD);
                                entries.add(ModItems.NETHER_WART_PICKAXE);
                                entries.add(ModItems.NETHER_WART_SHOVEL);
                                entries.add(ModItems.NETHER_WART_AXE);
                                entries.add(ModItems.NETHER_WART_HOE);
                            }

                            // --- Brick (tools + armor) ---
                            if (Config.brickEnabled) {
                                entries.add(ModItems.BRICK_SWORD);
                                entries.add(ModItems.BRICK_PICKAXE);
                                entries.add(ModItems.BRICK_SHOVEL);
                                entries.add(ModItems.BRICK_AXE);
                                entries.add(ModItems.BRICK_HOE);
                                entries.add(ModItems.BRICK_HELMET);
                                entries.add(ModItems.BRICK_CHESTPLATE);
                                entries.add(ModItems.BRICK_LEGGINGS);
                                entries.add(ModItems.BRICK_BOOTS);
                            }

                            // --- Nether Brick (tools + armor) ---
                            if (Config.netherBrickEnabled) {
                                entries.add(ModItems.NETHER_BRICK_SWORD);
                                entries.add(ModItems.NETHER_BRICK_PICKAXE);
                                entries.add(ModItems.NETHER_BRICK_SHOVEL);
                                entries.add(ModItems.NETHER_BRICK_AXE);
                                entries.add(ModItems.NETHER_BRICK_HOE);
                                entries.add(ModItems.NETHER_BRICK_HELMET);
                                entries.add(ModItems.NETHER_BRICK_CHESTPLATE);
                                entries.add(ModItems.NETHER_BRICK_LEGGINGS);
                                entries.add(ModItems.NETHER_BRICK_BOOTS);
                            }

                            // --- Pointed Dripstone (tools only) ---
                            if (Config.pointedDripstoneEnabled) {
                                entries.add(ModItems.POINTED_DRIPSTONE_SWORD);
                                entries.add(ModItems.POINTED_DRIPSTONE_PICKAXE);
                                entries.add(ModItems.POINTED_DRIPSTONE_SHOVEL);
                                entries.add(ModItems.POINTED_DRIPSTONE_AXE);
                                entries.add(ModItems.POINTED_DRIPSTONE_HOE);
                            }

                            // --- Copper (tools + armor) ---
                            if (Config.copperEnabled) {
                                entries.add(ModItems.COPPER_SWORD);
                                entries.add(ModItems.COPPER_PICKAXE);
                                entries.add(ModItems.COPPER_SHOVEL);
                                entries.add(ModItems.COPPER_AXE);
                                entries.add(ModItems.COPPER_HOE);
                                entries.add(ModItems.COPPER_HELMET);
                                entries.add(ModItems.COPPER_CHESTPLATE);
                                entries.add(ModItems.COPPER_LEGGINGS);
                                entries.add(ModItems.COPPER_BOOTS);
                            }

                            // --- Phantom Membrane (tools + armor) ---
                            if (Config.phantomEnabled) {
                                entries.add(ModItems.PHANTOM_SWORD);
                                entries.add(ModItems.PHANTOM_PICKAXE);
                                entries.add(ModItems.PHANTOM_SHOVEL);
                                entries.add(ModItems.PHANTOM_AXE);
                                entries.add(ModItems.PHANTOM_HOE);
                                entries.add(ModItems.PHANTOM_HELMET);
                                entries.add(ModItems.PHANTOM_CHESTPLATE);
                                entries.add(ModItems.PHANTOM_LEGGINGS);
                                entries.add(ModItems.PHANTOM_BOOTS);
                            }

                            // --- Magma Cream (tools + armor) ---
                            if (Config.magmaCreamEnabled) {
                                entries.add(ModItems.MAGMA_CREAM_SWORD);
                                entries.add(ModItems.MAGMA_CREAM_PICKAXE);
                                entries.add(ModItems.MAGMA_CREAM_SHOVEL);
                                entries.add(ModItems.MAGMA_CREAM_AXE);
                                entries.add(ModItems.MAGMA_CREAM_HOE);
                                entries.add(ModItems.MAGMA_CREAM_HELMET);
                                entries.add(ModItems.MAGMA_CREAM_CHESTPLATE);
                                entries.add(ModItems.MAGMA_CREAM_LEGGINGS);
                                entries.add(ModItems.MAGMA_CREAM_BOOTS);
                            }

                            // --- Slime (tools + armor) ---
                            if (Config.slimeEnabled) {
                                entries.add(ModItems.SLIME_SWORD);
                                entries.add(ModItems.SLIME_PICKAXE);
                                entries.add(ModItems.SLIME_SHOVEL);
                                entries.add(ModItems.SLIME_AXE);
                                entries.add(ModItems.SLIME_HOE);
                                entries.add(ModItems.SLIME_HELMET);
                                entries.add(ModItems.SLIME_CHESTPLATE);
                                entries.add(ModItems.SLIME_LEGGINGS);
                                entries.add(ModItems.SLIME_BOOTS);
                            }

                            // --- Blaze Rod (tools + armor) ---
                            if (Config.blazeEnabled) {
                                entries.add(ModItems.BLAZE_SWORD);
                                entries.add(ModItems.BLAZE_PICKAXE);
                                entries.add(ModItems.BLAZE_SHOVEL);
                                entries.add(ModItems.BLAZE_AXE);
                                entries.add(ModItems.BLAZE_HOE);
                                entries.add(ModItems.BLAZE_HELMET);
                                entries.add(ModItems.BLAZE_CHESTPLATE);
                                entries.add(ModItems.BLAZE_LEGGINGS);
                                entries.add(ModItems.BLAZE_BOOTS);
                            }

                            // --- Nautilus Shell (tools + armor) ---
                            if (Config.nautilusEnabled) {
                                entries.add(ModItems.NAUTILUS_SWORD);
                                entries.add(ModItems.NAUTILUS_PICKAXE);
                                entries.add(ModItems.NAUTILUS_SHOVEL);
                                entries.add(ModItems.NAUTILUS_AXE);
                                entries.add(ModItems.NAUTILUS_HOE);
                                entries.add(ModItems.NAUTILUS_HELMET);
                                entries.add(ModItems.NAUTILUS_CHESTPLATE);
                                entries.add(ModItems.NAUTILUS_LEGGINGS);
                                entries.add(ModItems.NAUTILUS_BOOTS);
                            }

                            // --- Purpur (tools + armor) ---
                            if (Config.purpurEnabled) {
                                entries.add(ModItems.PURPUR_SWORD);
                                entries.add(ModItems.PURPUR_PICKAXE);
                                entries.add(ModItems.PURPUR_SHOVEL);
                                entries.add(ModItems.PURPUR_AXE);
                                entries.add(ModItems.PURPUR_HOE);
                                entries.add(ModItems.PURPUR_HELMET);
                                entries.add(ModItems.PURPUR_CHESTPLATE);
                                entries.add(ModItems.PURPUR_LEGGINGS);
                                entries.add(ModItems.PURPUR_BOOTS);
                            }

                            // --- Ghast Tear (tools + armor) ---
                            if (Config.ghastTearEnabled) {
                                entries.add(ModItems.GHAST_TEAR_SWORD);
                                entries.add(ModItems.GHAST_TEAR_PICKAXE);
                                entries.add(ModItems.GHAST_TEAR_SHOVEL);
                                entries.add(ModItems.GHAST_TEAR_AXE);
                                entries.add(ModItems.GHAST_TEAR_HOE);
                                entries.add(ModItems.GHAST_TEAR_HELMET);
                                entries.add(ModItems.GHAST_TEAR_CHESTPLATE);
                                entries.add(ModItems.GHAST_TEAR_LEGGINGS);
                                entries.add(ModItems.GHAST_TEAR_BOOTS);
                            }

                            // --- Eye of Ender (tools + armor) ---
                            if (Config.eyeOfEnderEnabled) {
                                entries.add(ModItems.EYE_OF_ENDER_SWORD);
                                entries.add(ModItems.EYE_OF_ENDER_PICKAXE);
                                entries.add(ModItems.EYE_OF_ENDER_SHOVEL);
                                entries.add(ModItems.EYE_OF_ENDER_AXE);
                                entries.add(ModItems.EYE_OF_ENDER_HOE);
                                entries.add(ModItems.EYE_OF_ENDER_HELMET);
                                entries.add(ModItems.EYE_OF_ENDER_CHESTPLATE);
                                entries.add(ModItems.EYE_OF_ENDER_LEGGINGS);
                                entries.add(ModItems.EYE_OF_ENDER_BOOTS);
                            }

                            // --- Shulker Shell (tools + armor) ---
                            if (Config.shulkerEnabled) {
                                entries.add(ModItems.SHULKER_SWORD);
                                entries.add(ModItems.SHULKER_PICKAXE);
                                entries.add(ModItems.SHULKER_SHOVEL);
                                entries.add(ModItems.SHULKER_AXE);
                                entries.add(ModItems.SHULKER_HOE);
                                entries.add(ModItems.SHULKER_HELMET);
                                entries.add(ModItems.SHULKER_CHESTPLATE);
                                entries.add(ModItems.SHULKER_LEGGINGS);
                                entries.add(ModItems.SHULKER_BOOTS);
                            }

                            // --- Turtle Scute (armor only) ---
                            if (Config.turtleScuteEnabled) {
                                entries.add(ModItems.TURTLE_SCUTE_HELMET);
                                entries.add(ModItems.TURTLE_SCUTE_CHESTPLATE);
                                entries.add(ModItems.TURTLE_SCUTE_LEGGINGS);
                                entries.add(ModItems.TURTLE_SCUTE_BOOTS);
                            }

                            // --- Echo Shard (tools + armor) ---
                            if (Config.echoShardEnabled) {
                                entries.add(ModItems.ECHO_SHARD_SWORD);
                                entries.add(ModItems.ECHO_SHARD_PICKAXE);
                                entries.add(ModItems.ECHO_SHARD_SHOVEL);
                                entries.add(ModItems.ECHO_SHARD_AXE);
                                entries.add(ModItems.ECHO_SHARD_HOE);
                                entries.add(ModItems.ECHO_SHARD_HELMET);
                                entries.add(ModItems.ECHO_SHARD_CHESTPLATE);
                                entries.add(ModItems.ECHO_SHARD_LEGGINGS);
                                entries.add(ModItems.ECHO_SHARD_BOOTS);
                            }

                            // --- Dragon's Breath (tools + armor) ---
                            if (Config.dragonBreathEnabled) {
                                entries.add(ModItems.DRAGON_BREATH_SWORD);
                                entries.add(ModItems.DRAGON_BREATH_PICKAXE);
                                entries.add(ModItems.DRAGON_BREATH_SHOVEL);
                                entries.add(ModItems.DRAGON_BREATH_AXE);
                                entries.add(ModItems.DRAGON_BREATH_HOE);
                                entries.add(ModItems.DRAGON_BREATH_HELMET);
                                entries.add(ModItems.DRAGON_BREATH_CHESTPLATE);
                                entries.add(ModItems.DRAGON_BREATH_LEGGINGS);
                                entries.add(ModItems.DRAGON_BREATH_BOOTS);
                            }

                        })
                        .build());

        // Also add to vanilla tabs
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(content -> {
            if (Config.ferrousGoldEnabled) {
                content.add(ModItems.RGOLD);
                content.add(ModItems.RAW_RGOLD);
            }
            if (Config.obsidianEnabled) {
                content.add(ModItems.OBSHARD);
                content.add(ModItems.OBINGOT);
            }
            if (Config.emeraldEnabled) {
                content.add(ModItems.SEM);
            }
            if (Config.hardenedRedstoneEnabled) {
                content.add(ModItems.HRED);
            }
            if (Config.hardenedGlowstoneEnabled) {
                content.add(ModItems.HGLOW);
            }
            if (Config.lapisEnabled) {
                content.add(ModItems.RLAPIS);
            }
            if (Config.coalEnabled) {
                content.add(ModItems.COAL_DUST);
                content.add(ModItems.HARDENED_COAL);
            }
            if (Config.roughCrystalEnabled || Config.polishedCrystalEnabled) {
                content.add(ModItems.CALCIFIED_AMETHYST);
                content.add(ModItems.POLISHED_QUARTZ);
            }
            if (Config.iceEnabled || Config.snowEnabled) {
                content.add(ModItems.GLACIAL_SHARD);
            }
            if (Config.roughCrystalEnabled || Config.pprismEnabled) {
                content.add(ModItems.POLISHED_PRISMARINE);
            }
            if (Config.ghostEnabled) {
                content.add(ModItems.ECTOPLASM);
            }
            if (Config.ectoplasmSetEnabled) {
                content.add(ModItems.REFINED_ECTOPLASM);
            }
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(content -> {
            if (Config.explosivesEnabled) {
                content.add(ModItems.GRENADE);
                content.add(ModItems.DYNAMITE);
            }
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(content -> {
            if (Config.ferrousGoldEnabled) {
                content.add(ModBlocks.RGOLDBLOCK);
                content.add(ModBlocks.RAW_RGOLD_BLOCK);
            }
            if (Config.hardenedRedstoneEnabled) {
                content.add(ModBlocks.HRBLOCK);
            }
            if (Config.hardenedGlowstoneEnabled) {
                content.add(ModBlocks.HGLOW_BLOCK);
            }
            if (Config.emeraldEnabled) {
                content.add(ModBlocks.SEMBLOCK);
            }
            if (Config.obsidianEnabled) {
                content.add(ModBlocks.SOBLOCK);
                content.add(ModBlocks.OBSHARD_BLOCK);
            }
            if (Config.lapisEnabled) {
                content.add(ModBlocks.LBLOCK);
            }
            if (Config.coalEnabled) {
                content.add(ModBlocks.COAL_DUST_BLOCK);
                content.add(ModBlocks.HARDENED_COAL_BLOCK);
            }
            if (Config.roughCrystalEnabled || Config.polishedCrystalEnabled) {
                content.add(ModBlocks.CALCIFIED_AMETHYST_BLOCK);
                content.add(ModBlocks.POLISHED_QUARTZ_BLOCK);
            }
            if (Config.iceEnabled || Config.snowEnabled) {
                content.add(ModBlocks.GLACIAL_SHARD_BLOCK);
            }
            if (Config.roughCrystalEnabled || Config.pprismEnabled) {
                content.add(ModBlocks.POLISHED_PRISMARINE_BLOCK);
            }
            if (Config.ectoplasmSetEnabled) {
                content.add(ModBlocks.ECTOPLASM_BLOCK);
                content.add(ModBlocks.REFINED_ECTOPLASM_BLOCK);
            }
            if (Config.spectralInfuserEnabled) {
                content.add(ModBlocks.SPECTRAL_INFUSER);
            }
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(content -> {
            if (Config.ferrousGoldEnabled) {
                content.add(ModBlocks.RGOLDORE);
                content.add(ModBlocks.RGOLD_DEEPSLATE_ORE);
                content.add(ModBlocks.RGOLD_END_ORE);
                content.add(ModBlocks.RGOLD_NETHER_ORE);
            }
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(content -> {
            if (Config.ghostEnabled) {
                content.add(ModItems.GHOST_SPAWN_EGG);
            }
        });
    }
}
