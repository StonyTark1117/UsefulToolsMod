package com.stonytark.usefultoolsmod.datagen;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.stonytark.usefultoolsmod.UsefultoolsMod;
import com.stonytark.usefultoolsmod.block.ModBlocks;
import com.stonytark.usefultoolsmod.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.*;
import net.minecraft.item.Item;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.registry.Registries;

import java.util.LinkedHashMap;

/**
 * Fabric 1.20.1 Model Data Generator
 * Generates block state + item model JSON files
 */
public class ModItemModelProvider extends FabricModelProvider {
    public ModItemModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator gen) {
        gen.registerSimpleCubeAll(ModBlocks.RGOLDBLOCK);
        gen.registerSimpleCubeAll(ModBlocks.HRBLOCK);
        gen.registerSimpleCubeAll(ModBlocks.RGOLDORE);
        gen.registerSimpleCubeAll(ModBlocks.RGOLD_NETHER_ORE);
        gen.registerSimpleCubeAll(ModBlocks.RGOLD_END_ORE);
        gen.registerSimpleCubeAll(ModBlocks.RGOLD_DEEPSLATE_ORE);
        gen.registerSimpleCubeAll(ModBlocks.SEMBLOCK);
        gen.registerSimpleCubeAll(ModBlocks.SOBLOCK);
        gen.registerSimpleCubeAll(ModBlocks.LBLOCK);
        gen.registerSimpleCubeAll(ModBlocks.HGLOW_BLOCK);
        gen.registerSimpleCubeAll(ModBlocks.RAW_RGOLD_BLOCK);
        gen.registerSimpleCubeAll(ModBlocks.ECTOPLASM_BLOCK);
        gen.registerSimpleCubeAll(ModBlocks.REFINED_ECTOPLASM_BLOCK);
        gen.registerSimpleCubeAll(ModBlocks.HARDENED_COAL_BLOCK);
        gen.registerSimpleCubeAll(ModBlocks.COAL_DUST_BLOCK);
        gen.registerSimpleCubeAll(ModBlocks.OBSHARD_BLOCK);
        gen.registerSimpleCubeAll(ModBlocks.CALCIFIED_AMETHYST_BLOCK);
        gen.registerSimpleCubeAll(ModBlocks.GLACIAL_SHARD_BLOCK);
        gen.registerSimpleCubeAll(ModBlocks.POLISHED_QUARTZ_BLOCK);
        gen.registerSimpleCubeAll(ModBlocks.POLISHED_PRISMARINE_BLOCK);

        // Spectral Infuser - orientable block with LIT state
        registerSpectralInfuser(gen);
    }

    private void registerSpectralInfuser(BlockStateModelGenerator gen) {
        Identifier side = Identifier.of(UsefultoolsMod.MOD_ID, "block/spectral_infuser_side");
        Identifier front = Identifier.of(UsefultoolsMod.MOD_ID, "block/spectral_infuser_front");
        Identifier frontOn = Identifier.of(UsefultoolsMod.MOD_ID, "block/spectral_infuser_front_on");
        Identifier top = Identifier.of(UsefultoolsMod.MOD_ID, "block/spectral_infuser_top");

        TextureMap offTextures = new TextureMap()
                .put(TextureKey.SIDE, side)
                .put(TextureKey.FRONT, front)
                .put(TextureKey.TOP, top);

        TextureMap onTextures = new TextureMap()
                .put(TextureKey.SIDE, side)
                .put(TextureKey.FRONT, frontOn)
                .put(TextureKey.TOP, top);

        Identifier offModel = Models.ORIENTABLE.upload(ModBlocks.SPECTRAL_INFUSER, offTextures, gen.modelCollector);
        Identifier onModel = Models.ORIENTABLE.upload(
                ModelIds.getBlockSubModelId(ModBlocks.SPECTRAL_INFUSER, "_on"), onTextures, gen.modelCollector);

        gen.blockStateCollector.accept(
                VariantsBlockStateSupplier.create(ModBlocks.SPECTRAL_INFUSER)
                        .coordinate(BlockStateVariantMap.create(Properties.HORIZONTAL_FACING, Properties.LIT)
                                .register(Direction.NORTH, false, BlockStateVariant.create()
                                        .put(VariantSettings.MODEL, offModel))
                                .register(Direction.NORTH, true, BlockStateVariant.create()
                                        .put(VariantSettings.MODEL, onModel))
                                .register(Direction.SOUTH, false, BlockStateVariant.create()
                                        .put(VariantSettings.MODEL, offModel)
                                        .put(VariantSettings.Y, VariantSettings.Rotation.R180))
                                .register(Direction.SOUTH, true, BlockStateVariant.create()
                                        .put(VariantSettings.MODEL, onModel)
                                        .put(VariantSettings.Y, VariantSettings.Rotation.R180))
                                .register(Direction.WEST, false, BlockStateVariant.create()
                                        .put(VariantSettings.MODEL, offModel)
                                        .put(VariantSettings.Y, VariantSettings.Rotation.R270))
                                .register(Direction.WEST, true, BlockStateVariant.create()
                                        .put(VariantSettings.MODEL, onModel)
                                        .put(VariantSettings.Y, VariantSettings.Rotation.R270))
                                .register(Direction.EAST, false, BlockStateVariant.create()
                                        .put(VariantSettings.MODEL, offModel)
                                        .put(VariantSettings.Y, VariantSettings.Rotation.R90))
                                .register(Direction.EAST, true, BlockStateVariant.create()
                                        .put(VariantSettings.MODEL, onModel)
                                        .put(VariantSettings.Y, VariantSettings.Rotation.R90))
                        )
        );

        gen.registerParentedItemModel(ModBlocks.SPECTRAL_INFUSER, offModel);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        // ===== Material / Ingredient items (GENERATED) =====
        itemModelGenerator.register(ModItems.RGOLD, Models.GENERATED);
        itemModelGenerator.register(ModItems.RAW_RGOLD, Models.GENERATED);
        itemModelGenerator.register(ModItems.OBSHARD, Models.GENERATED);
        itemModelGenerator.register(ModItems.OBINGOT, Models.GENERATED);
        itemModelGenerator.register(ModItems.HRED, Models.GENERATED);
        itemModelGenerator.register(ModItems.HGLOW, Models.GENERATED);
        itemModelGenerator.register(ModItems.SEM, Models.GENERATED);
        itemModelGenerator.register(ModItems.RLAPIS, Models.GENERATED);
        itemModelGenerator.register(ModItems.ECTOPLASM, Models.GENERATED);
        itemModelGenerator.register(ModItems.REFINED_ECTOPLASM, Models.GENERATED);
        itemModelGenerator.register(ModItems.COAL_DUST, Models.GENERATED);
        itemModelGenerator.register(ModItems.HARDENED_COAL, Models.GENERATED);
        itemModelGenerator.register(ModItems.CALCIFIED_AMETHYST, Models.GENERATED);
        itemModelGenerator.register(ModItems.GLACIAL_SHARD, Models.GENERATED);
        itemModelGenerator.register(ModItems.POLISHED_QUARTZ, Models.GENERATED);
        itemModelGenerator.register(ModItems.POLISHED_PRISMARINE, Models.GENERATED);

        // ===== Tool items (HANDHELD) =====

        // Rough Emerald tools
        handheld(itemModelGenerator, ModItems.REMERALD_SWORD);
        handheld(itemModelGenerator, ModItems.REMERALD_PICKAXE);
        handheld(itemModelGenerator, ModItems.REMERALD_SHOVEL);
        handheld(itemModelGenerator, ModItems.REMERALD_AXE);
        handheld(itemModelGenerator, ModItems.REMERALD_HOE);

        // Polished Emerald tools
        handheld(itemModelGenerator, ModItems.PEMERALD_SWORD);
        handheld(itemModelGenerator, ModItems.PEMERALD_PICKAXE);
        handheld(itemModelGenerator, ModItems.PEMERALD_SHOVEL);
        handheld(itemModelGenerator, ModItems.PEMERALD_AXE);
        handheld(itemModelGenerator, ModItems.PEMERALD_HOE);

        // Rough Obsidian tools
        handheld(itemModelGenerator, ModItems.ROBSIDIAN_SWORD);
        handheld(itemModelGenerator, ModItems.ROBSIDIAN_PICKAXE);
        handheld(itemModelGenerator, ModItems.ROBSIDIAN_SHOVEL);
        handheld(itemModelGenerator, ModItems.ROBSIDIAN_AXE);
        handheld(itemModelGenerator, ModItems.ROBSIDIAN_HOE);

        // Polished Obsidian tools
        handheld(itemModelGenerator, ModItems.POBSIDIAN_SWORD);
        handheld(itemModelGenerator, ModItems.POBSIDIAN_PICKAXE);
        handheld(itemModelGenerator, ModItems.POBSIDIAN_SHOVEL);
        handheld(itemModelGenerator, ModItems.POBSIDIAN_AXE);
        handheld(itemModelGenerator, ModItems.POBSIDIAN_HOE);

        // Overpower tools
        handheld(itemModelGenerator, ModItems.OVERPOWER_SWORD);
        handheld(itemModelGenerator, ModItems.OVERPOWER_PICKAXE);
        handheld(itemModelGenerator, ModItems.OVERPOWER_SHOVEL);
        handheld(itemModelGenerator, ModItems.OVERPOWER_AXE);

        // Hardened Redstone tools
        handheld(itemModelGenerator, ModItems.HREDSTONE_SWORD);
        handheld(itemModelGenerator, ModItems.HREDSTONE_PICKAXE);
        handheld(itemModelGenerator, ModItems.HREDSTONE_SHOVEL);
        handheld(itemModelGenerator, ModItems.HREDSTONE_AXE);
        handheld(itemModelGenerator, ModItems.HREDSTONE_HOE);

        // Hardened Glowstone tools
        handheld(itemModelGenerator, ModItems.HGLOWSTONE_SWORD);
        handheld(itemModelGenerator, ModItems.HGLOWSTONE_PICKAXE);
        handheld(itemModelGenerator, ModItems.HGLOWSTONE_SHOVEL);
        handheld(itemModelGenerator, ModItems.HGLOWSTONE_AXE);
        handheld(itemModelGenerator, ModItems.HGLOWSTONE_HOE);

        // Ferrous Gold tools
        handheld(itemModelGenerator, ModItems.RGOLD_SWORD);
        handheld(itemModelGenerator, ModItems.RGOLD_PICKAXE);
        handheld(itemModelGenerator, ModItems.RGOLD_SHOVEL);
        handheld(itemModelGenerator, ModItems.RGOLD_AXE);
        handheld(itemModelGenerator, ModItems.RGOLD_HOE);

        // Refined Lapis tools
        handheld(itemModelGenerator, ModItems.RLAPIS_SWORD);
        handheld(itemModelGenerator, ModItems.RLAPIS_PICKAXE);
        handheld(itemModelGenerator, ModItems.RLAPIS_SHOVEL);
        handheld(itemModelGenerator, ModItems.RLAPIS_AXE);
        handheld(itemModelGenerator, ModItems.RLAPIS_HOE);

        // Rough Ectoplasm tools
        handheld(itemModelGenerator, ModItems.RECTO_SWORD);
        handheld(itemModelGenerator, ModItems.RECTO_PICKAXE);
        handheld(itemModelGenerator, ModItems.RECTO_SHOVEL);
        handheld(itemModelGenerator, ModItems.RECTO_AXE);
        handheld(itemModelGenerator, ModItems.RECTO_HOE);

        // Ectoplasm tools
        handheld(itemModelGenerator, ModItems.ECTO_SWORD);
        handheld(itemModelGenerator, ModItems.ECTO_PICKAXE);
        handheld(itemModelGenerator, ModItems.ECTO_SHOVEL);
        handheld(itemModelGenerator, ModItems.ECTO_AXE);
        handheld(itemModelGenerator, ModItems.ECTO_HOE);

        // Coal tools
        handheld(itemModelGenerator, ModItems.COAL_SWORD);
        handheld(itemModelGenerator, ModItems.COAL_PICKAXE);
        handheld(itemModelGenerator, ModItems.COAL_SHOVEL);
        handheld(itemModelGenerator, ModItems.COAL_AXE);
        handheld(itemModelGenerator, ModItems.COAL_HOE);

        // Leather tools
        handheld(itemModelGenerator, ModItems.LEATHER_SWORD);
        handheld(itemModelGenerator, ModItems.LEATHER_PICKAXE);
        handheld(itemModelGenerator, ModItems.LEATHER_SHOVEL);
        handheld(itemModelGenerator, ModItems.LEATHER_AXE);
        handheld(itemModelGenerator, ModItems.LEATHER_HOE);

        // Raw metal rough tools
        handheld(itemModelGenerator, ModItems.RRAW_GOLD_SWORD);
        handheld(itemModelGenerator, ModItems.RRAW_GOLD_PICKAXE);
        handheld(itemModelGenerator, ModItems.RRAW_GOLD_SHOVEL);
        handheld(itemModelGenerator, ModItems.RRAW_GOLD_AXE);
        handheld(itemModelGenerator, ModItems.RRAW_GOLD_HOE);
        handheld(itemModelGenerator, ModItems.RRAW_COPPER_SWORD);
        handheld(itemModelGenerator, ModItems.RRAW_COPPER_PICKAXE);
        handheld(itemModelGenerator, ModItems.RRAW_COPPER_SHOVEL);
        handheld(itemModelGenerator, ModItems.RRAW_COPPER_AXE);
        handheld(itemModelGenerator, ModItems.RRAW_COPPER_HOE);
        handheld(itemModelGenerator, ModItems.RRAW_IRON_SWORD);
        handheld(itemModelGenerator, ModItems.RRAW_IRON_PICKAXE);
        handheld(itemModelGenerator, ModItems.RRAW_IRON_SHOVEL);
        handheld(itemModelGenerator, ModItems.RRAW_IRON_AXE);
        handheld(itemModelGenerator, ModItems.RRAW_IRON_HOE);
        handheld(itemModelGenerator, ModItems.RRAW_RGOLD_SWORD);
        handheld(itemModelGenerator, ModItems.RRAW_RGOLD_PICKAXE);
        handheld(itemModelGenerator, ModItems.RRAW_RGOLD_SHOVEL);
        handheld(itemModelGenerator, ModItems.RRAW_RGOLD_AXE);
        handheld(itemModelGenerator, ModItems.RRAW_RGOLD_HOE);
        handheld(itemModelGenerator, ModItems.RSCRAP_SWORD);
        handheld(itemModelGenerator, ModItems.RSCRAP_PICKAXE);
        handheld(itemModelGenerator, ModItems.RSCRAP_SHOVEL);
        handheld(itemModelGenerator, ModItems.RSCRAP_AXE);
        handheld(itemModelGenerator, ModItems.RSCRAP_HOE);

        // Rough crystal tools
        handheld(itemModelGenerator, ModItems.RAMETHYST_SWORD);
        handheld(itemModelGenerator, ModItems.RAMETHYST_PICKAXE);
        handheld(itemModelGenerator, ModItems.RAMETHYST_SHOVEL);
        handheld(itemModelGenerator, ModItems.RAMETHYST_AXE);
        handheld(itemModelGenerator, ModItems.RAMETHYST_HOE);
        handheld(itemModelGenerator, ModItems.SNOW_SWORD);
        handheld(itemModelGenerator, ModItems.SNOW_PICKAXE);
        handheld(itemModelGenerator, ModItems.SNOW_SHOVEL);
        handheld(itemModelGenerator, ModItems.SNOW_AXE);
        handheld(itemModelGenerator, ModItems.SNOW_HOE);
        handheld(itemModelGenerator, ModItems.RQUARTZ_SWORD);
        handheld(itemModelGenerator, ModItems.RQUARTZ_PICKAXE);
        handheld(itemModelGenerator, ModItems.RQUARTZ_SHOVEL);
        handheld(itemModelGenerator, ModItems.RQUARTZ_AXE);
        handheld(itemModelGenerator, ModItems.RQUARTZ_HOE);
        handheld(itemModelGenerator, ModItems.RPRISM_SWORD);
        handheld(itemModelGenerator, ModItems.RPRISM_PICKAXE);
        handheld(itemModelGenerator, ModItems.RPRISM_SHOVEL);
        handheld(itemModelGenerator, ModItems.RPRISM_AXE);
        handheld(itemModelGenerator, ModItems.RPRISM_HOE);

        // Polished crystal tools
        handheld(itemModelGenerator, ModItems.CAMETHYST_SWORD);
        handheld(itemModelGenerator, ModItems.CAMETHYST_PICKAXE);
        handheld(itemModelGenerator, ModItems.CAMETHYST_SHOVEL);
        handheld(itemModelGenerator, ModItems.CAMETHYST_AXE);
        handheld(itemModelGenerator, ModItems.CAMETHYST_HOE);
        handheld(itemModelGenerator, ModItems.ICE_SWORD);
        handheld(itemModelGenerator, ModItems.ICE_PICKAXE);
        handheld(itemModelGenerator, ModItems.ICE_SHOVEL);
        handheld(itemModelGenerator, ModItems.ICE_AXE);
        handheld(itemModelGenerator, ModItems.ICE_HOE);
        handheld(itemModelGenerator, ModItems.PQUARTZ_SWORD);
        handheld(itemModelGenerator, ModItems.PQUARTZ_PICKAXE);
        handheld(itemModelGenerator, ModItems.PQUARTZ_SHOVEL);
        handheld(itemModelGenerator, ModItems.PQUARTZ_AXE);
        handheld(itemModelGenerator, ModItems.PQUARTZ_HOE);
        handheld(itemModelGenerator, ModItems.PPRISM_SWORD);
        handheld(itemModelGenerator, ModItems.PPRISM_PICKAXE);
        handheld(itemModelGenerator, ModItems.PPRISM_SHOVEL);
        handheld(itemModelGenerator, ModItems.PPRISM_AXE);
        handheld(itemModelGenerator, ModItems.PPRISM_HOE);

        // Flint and FNI tools
        handheld(itemModelGenerator, ModItems.RFLINT_SWORD);
        handheld(itemModelGenerator, ModItems.RFLINT_PICKAXE);
        handheld(itemModelGenerator, ModItems.RFLINT_SHOVEL);
        handheld(itemModelGenerator, ModItems.RFLINT_AXE);
        handheld(itemModelGenerator, ModItems.RFLINT_HOE);
        handheld(itemModelGenerator, ModItems.FNI_SWORD);
        handheld(itemModelGenerator, ModItems.FNI_PICKAXE);
        handheld(itemModelGenerator, ModItems.FNI_SHOVEL);
        handheld(itemModelGenerator, ModItems.FNI_AXE);
        handheld(itemModelGenerator, ModItems.FNI_HOE);

        // Stone Rock Variant tools
        handheld(itemModelGenerator, ModItems.ANDESITE_SWORD); handheld(itemModelGenerator, ModItems.ANDESITE_PICKAXE);
        handheld(itemModelGenerator, ModItems.ANDESITE_SHOVEL); handheld(itemModelGenerator, ModItems.ANDESITE_AXE); handheld(itemModelGenerator, ModItems.ANDESITE_HOE);
        handheld(itemModelGenerator, ModItems.BASALT_SWORD); handheld(itemModelGenerator, ModItems.BASALT_PICKAXE);
        handheld(itemModelGenerator, ModItems.BASALT_SHOVEL); handheld(itemModelGenerator, ModItems.BASALT_AXE); handheld(itemModelGenerator, ModItems.BASALT_HOE);
        handheld(itemModelGenerator, ModItems.BLACKSTONE_SWORD); handheld(itemModelGenerator, ModItems.BLACKSTONE_PICKAXE);
        handheld(itemModelGenerator, ModItems.BLACKSTONE_SHOVEL); handheld(itemModelGenerator, ModItems.BLACKSTONE_AXE); handheld(itemModelGenerator, ModItems.BLACKSTONE_HOE);
        handheld(itemModelGenerator, ModItems.CALCITE_SWORD); handheld(itemModelGenerator, ModItems.CALCITE_PICKAXE);
        handheld(itemModelGenerator, ModItems.CALCITE_SHOVEL); handheld(itemModelGenerator, ModItems.CALCITE_AXE); handheld(itemModelGenerator, ModItems.CALCITE_HOE);
        handheld(itemModelGenerator, ModItems.DEEPSLATE_SWORD); handheld(itemModelGenerator, ModItems.DEEPSLATE_PICKAXE);
        handheld(itemModelGenerator, ModItems.DEEPSLATE_SHOVEL); handheld(itemModelGenerator, ModItems.DEEPSLATE_AXE); handheld(itemModelGenerator, ModItems.DEEPSLATE_HOE);
        handheld(itemModelGenerator, ModItems.DIORITE_SWORD); handheld(itemModelGenerator, ModItems.DIORITE_PICKAXE);
        handheld(itemModelGenerator, ModItems.DIORITE_SHOVEL); handheld(itemModelGenerator, ModItems.DIORITE_AXE); handheld(itemModelGenerator, ModItems.DIORITE_HOE);
        handheld(itemModelGenerator, ModItems.END_STONE_SWORD); handheld(itemModelGenerator, ModItems.END_STONE_PICKAXE);
        handheld(itemModelGenerator, ModItems.END_STONE_SHOVEL); handheld(itemModelGenerator, ModItems.END_STONE_AXE); handheld(itemModelGenerator, ModItems.END_STONE_HOE);
        handheld(itemModelGenerator, ModItems.GRANITE_SWORD); handheld(itemModelGenerator, ModItems.GRANITE_PICKAXE);
        handheld(itemModelGenerator, ModItems.GRANITE_SHOVEL); handheld(itemModelGenerator, ModItems.GRANITE_AXE); handheld(itemModelGenerator, ModItems.GRANITE_HOE);
        handheld(itemModelGenerator, ModItems.NETHERRACK_SWORD); handheld(itemModelGenerator, ModItems.NETHERRACK_PICKAXE);
        handheld(itemModelGenerator, ModItems.NETHERRACK_SHOVEL); handheld(itemModelGenerator, ModItems.NETHERRACK_AXE); handheld(itemModelGenerator, ModItems.NETHERRACK_HOE);
        handheld(itemModelGenerator, ModItems.SANDSTONE_SWORD); handheld(itemModelGenerator, ModItems.SANDSTONE_PICKAXE);
        handheld(itemModelGenerator, ModItems.SANDSTONE_SHOVEL); handheld(itemModelGenerator, ModItems.SANDSTONE_AXE); handheld(itemModelGenerator, ModItems.SANDSTONE_HOE);
        handheld(itemModelGenerator, ModItems.SMOOTH_BASALT_SWORD); handheld(itemModelGenerator, ModItems.SMOOTH_BASALT_PICKAXE);
        handheld(itemModelGenerator, ModItems.SMOOTH_BASALT_SHOVEL); handheld(itemModelGenerator, ModItems.SMOOTH_BASALT_AXE); handheld(itemModelGenerator, ModItems.SMOOTH_BASALT_HOE);
        handheld(itemModelGenerator, ModItems.TERRACOTTA_SWORD); handheld(itemModelGenerator, ModItems.TERRACOTTA_PICKAXE);
        handheld(itemModelGenerator, ModItems.TERRACOTTA_SHOVEL); handheld(itemModelGenerator, ModItems.TERRACOTTA_AXE); handheld(itemModelGenerator, ModItems.TERRACOTTA_HOE);
        handheld(itemModelGenerator, ModItems.TUFF_SWORD); handheld(itemModelGenerator, ModItems.TUFF_PICKAXE);
        handheld(itemModelGenerator, ModItems.TUFF_SHOVEL); handheld(itemModelGenerator, ModItems.TUFF_AXE); handheld(itemModelGenerator, ModItems.TUFF_HOE);

        // Wood Variant tools
        handheld(itemModelGenerator, ModItems.OAK_SWORD); handheld(itemModelGenerator, ModItems.OAK_PICKAXE);
        handheld(itemModelGenerator, ModItems.OAK_SHOVEL); handheld(itemModelGenerator, ModItems.OAK_AXE); handheld(itemModelGenerator, ModItems.OAK_HOE);
        handheld(itemModelGenerator, ModItems.SPRUCE_SWORD); handheld(itemModelGenerator, ModItems.SPRUCE_PICKAXE);
        handheld(itemModelGenerator, ModItems.SPRUCE_SHOVEL); handheld(itemModelGenerator, ModItems.SPRUCE_AXE); handheld(itemModelGenerator, ModItems.SPRUCE_HOE);
        handheld(itemModelGenerator, ModItems.BIRCH_SWORD); handheld(itemModelGenerator, ModItems.BIRCH_PICKAXE);
        handheld(itemModelGenerator, ModItems.BIRCH_SHOVEL); handheld(itemModelGenerator, ModItems.BIRCH_AXE); handheld(itemModelGenerator, ModItems.BIRCH_HOE);
        handheld(itemModelGenerator, ModItems.JUNGLE_SWORD); handheld(itemModelGenerator, ModItems.JUNGLE_PICKAXE);
        handheld(itemModelGenerator, ModItems.JUNGLE_SHOVEL); handheld(itemModelGenerator, ModItems.JUNGLE_AXE); handheld(itemModelGenerator, ModItems.JUNGLE_HOE);
        handheld(itemModelGenerator, ModItems.ACACIA_SWORD); handheld(itemModelGenerator, ModItems.ACACIA_PICKAXE);
        handheld(itemModelGenerator, ModItems.ACACIA_SHOVEL); handheld(itemModelGenerator, ModItems.ACACIA_AXE); handheld(itemModelGenerator, ModItems.ACACIA_HOE);
        handheld(itemModelGenerator, ModItems.DARK_OAK_SWORD); handheld(itemModelGenerator, ModItems.DARK_OAK_PICKAXE);
        handheld(itemModelGenerator, ModItems.DARK_OAK_SHOVEL); handheld(itemModelGenerator, ModItems.DARK_OAK_AXE); handheld(itemModelGenerator, ModItems.DARK_OAK_HOE);
        handheld(itemModelGenerator, ModItems.MANGROVE_SWORD); handheld(itemModelGenerator, ModItems.MANGROVE_PICKAXE);
        handheld(itemModelGenerator, ModItems.MANGROVE_SHOVEL); handheld(itemModelGenerator, ModItems.MANGROVE_AXE); handheld(itemModelGenerator, ModItems.MANGROVE_HOE);
        handheld(itemModelGenerator, ModItems.CHERRY_SWORD); handheld(itemModelGenerator, ModItems.CHERRY_PICKAXE);
        handheld(itemModelGenerator, ModItems.CHERRY_SHOVEL); handheld(itemModelGenerator, ModItems.CHERRY_AXE); handheld(itemModelGenerator, ModItems.CHERRY_HOE);
        handheld(itemModelGenerator, ModItems.BAMBOO_SWORD); handheld(itemModelGenerator, ModItems.BAMBOO_PICKAXE);
        handheld(itemModelGenerator, ModItems.BAMBOO_SHOVEL); handheld(itemModelGenerator, ModItems.BAMBOO_AXE); handheld(itemModelGenerator, ModItems.BAMBOO_HOE);
        handheld(itemModelGenerator, ModItems.CRIMSON_SWORD); handheld(itemModelGenerator, ModItems.CRIMSON_PICKAXE);
        handheld(itemModelGenerator, ModItems.CRIMSON_SHOVEL); handheld(itemModelGenerator, ModItems.CRIMSON_AXE); handheld(itemModelGenerator, ModItems.CRIMSON_HOE);
        handheld(itemModelGenerator, ModItems.WARPED_SWORD); handheld(itemModelGenerator, ModItems.WARPED_PICKAXE);
        handheld(itemModelGenerator, ModItems.WARPED_SHOVEL); handheld(itemModelGenerator, ModItems.WARPED_AXE); handheld(itemModelGenerator, ModItems.WARPED_HOE);

        // Cake tools
        handheld(itemModelGenerator, ModItems.CAKE_SWORD); handheld(itemModelGenerator, ModItems.CAKE_PICKAXE);
        handheld(itemModelGenerator, ModItems.CAKE_SHOVEL); handheld(itemModelGenerator, ModItems.CAKE_AXE); handheld(itemModelGenerator, ModItems.CAKE_HOE);

        // Food set tools
        handheld(itemModelGenerator, ModItems.BREAD_SWORD); handheld(itemModelGenerator, ModItems.BREAD_PICKAXE);
        handheld(itemModelGenerator, ModItems.BREAD_SHOVEL); handheld(itemModelGenerator, ModItems.BREAD_AXE); handheld(itemModelGenerator, ModItems.BREAD_HOE);
        handheld(itemModelGenerator, ModItems.DRIED_KELP_SWORD); handheld(itemModelGenerator, ModItems.DRIED_KELP_PICKAXE);
        handheld(itemModelGenerator, ModItems.DRIED_KELP_SHOVEL); handheld(itemModelGenerator, ModItems.DRIED_KELP_AXE); handheld(itemModelGenerator, ModItems.DRIED_KELP_HOE);
        handheld(itemModelGenerator, ModItems.ROTTEN_FLESH_SWORD); handheld(itemModelGenerator, ModItems.ROTTEN_FLESH_PICKAXE);
        handheld(itemModelGenerator, ModItems.ROTTEN_FLESH_SHOVEL); handheld(itemModelGenerator, ModItems.ROTTEN_FLESH_AXE); handheld(itemModelGenerator, ModItems.ROTTEN_FLESH_HOE);
        handheld(itemModelGenerator, ModItems.MELON_SWORD); handheld(itemModelGenerator, ModItems.MELON_PICKAXE);
        handheld(itemModelGenerator, ModItems.MELON_SHOVEL); handheld(itemModelGenerator, ModItems.MELON_AXE); handheld(itemModelGenerator, ModItems.MELON_HOE);
        handheld(itemModelGenerator, ModItems.SWEET_BERRY_SWORD); handheld(itemModelGenerator, ModItems.SWEET_BERRY_PICKAXE);
        handheld(itemModelGenerator, ModItems.SWEET_BERRY_SHOVEL); handheld(itemModelGenerator, ModItems.SWEET_BERRY_AXE); handheld(itemModelGenerator, ModItems.SWEET_BERRY_HOE);
        handheld(itemModelGenerator, ModItems.PUMPKIN_PIE_SWORD); handheld(itemModelGenerator, ModItems.PUMPKIN_PIE_PICKAXE);
        handheld(itemModelGenerator, ModItems.PUMPKIN_PIE_SHOVEL); handheld(itemModelGenerator, ModItems.PUMPKIN_PIE_AXE); handheld(itemModelGenerator, ModItems.PUMPKIN_PIE_HOE);
        handheld(itemModelGenerator, ModItems.MUSHROOM_SWORD); handheld(itemModelGenerator, ModItems.MUSHROOM_PICKAXE);
        handheld(itemModelGenerator, ModItems.MUSHROOM_SHOVEL); handheld(itemModelGenerator, ModItems.MUSHROOM_AXE); handheld(itemModelGenerator, ModItems.MUSHROOM_HOE);
        handheld(itemModelGenerator, ModItems.PUFFERFISH_SWORD); handheld(itemModelGenerator, ModItems.PUFFERFISH_PICKAXE);
        handheld(itemModelGenerator, ModItems.PUFFERFISH_SHOVEL); handheld(itemModelGenerator, ModItems.PUFFERFISH_AXE); handheld(itemModelGenerator, ModItems.PUFFERFISH_HOE);
        handheld(itemModelGenerator, ModItems.HONEY_SWORD); handheld(itemModelGenerator, ModItems.HONEY_PICKAXE);
        handheld(itemModelGenerator, ModItems.HONEY_SHOVEL); handheld(itemModelGenerator, ModItems.HONEY_AXE); handheld(itemModelGenerator, ModItems.HONEY_HOE);
        handheld(itemModelGenerator, ModItems.CHORUS_FRUIT_SWORD); handheld(itemModelGenerator, ModItems.CHORUS_FRUIT_PICKAXE);
        handheld(itemModelGenerator, ModItems.CHORUS_FRUIT_SHOVEL); handheld(itemModelGenerator, ModItems.CHORUS_FRUIT_AXE); handheld(itemModelGenerator, ModItems.CHORUS_FRUIT_HOE);
        handheld(itemModelGenerator, ModItems.GOLDEN_APPLE_SWORD); handheld(itemModelGenerator, ModItems.GOLDEN_APPLE_PICKAXE);
        handheld(itemModelGenerator, ModItems.GOLDEN_APPLE_SHOVEL); handheld(itemModelGenerator, ModItems.GOLDEN_APPLE_AXE); handheld(itemModelGenerator, ModItems.GOLDEN_APPLE_HOE);

        // Paper, Feather, Glass tools
        handheld(itemModelGenerator, ModItems.PAPER_SWORD); handheld(itemModelGenerator, ModItems.PAPER_PICKAXE);
        handheld(itemModelGenerator, ModItems.PAPER_SHOVEL); handheld(itemModelGenerator, ModItems.PAPER_AXE); handheld(itemModelGenerator, ModItems.PAPER_HOE);
        handheld(itemModelGenerator, ModItems.FEATHER_SWORD); handheld(itemModelGenerator, ModItems.FEATHER_PICKAXE);
        handheld(itemModelGenerator, ModItems.FEATHER_SHOVEL); handheld(itemModelGenerator, ModItems.FEATHER_AXE); handheld(itemModelGenerator, ModItems.FEATHER_HOE);
        handheld(itemModelGenerator, ModItems.GLASS_SWORD); handheld(itemModelGenerator, ModItems.GLASS_PICKAXE);
        handheld(itemModelGenerator, ModItems.GLASS_SHOVEL); handheld(itemModelGenerator, ModItems.GLASS_AXE); handheld(itemModelGenerator, ModItems.GLASS_HOE);

        // Cactus tools
        handheld(itemModelGenerator, ModItems.CACTUS_SWORD); handheld(itemModelGenerator, ModItems.CACTUS_PICKAXE);
        handheld(itemModelGenerator, ModItems.CACTUS_SHOVEL); handheld(itemModelGenerator, ModItems.CACTUS_AXE); handheld(itemModelGenerator, ModItems.CACTUS_HOE);

        // Sponge tools
        handheld(itemModelGenerator, ModItems.SPONGE_SWORD); handheld(itemModelGenerator, ModItems.SPONGE_PICKAXE);
        handheld(itemModelGenerator, ModItems.SPONGE_SHOVEL); handheld(itemModelGenerator, ModItems.SPONGE_AXE); handheld(itemModelGenerator, ModItems.SPONGE_HOE);

        // Bone tools
        handheld(itemModelGenerator, ModItems.BONE_SWORD); handheld(itemModelGenerator, ModItems.BONE_PICKAXE);
        handheld(itemModelGenerator, ModItems.BONE_SHOVEL); handheld(itemModelGenerator, ModItems.BONE_AXE); handheld(itemModelGenerator, ModItems.BONE_HOE);

        // Clay tools
        handheld(itemModelGenerator, ModItems.CLAY_SWORD); handheld(itemModelGenerator, ModItems.CLAY_PICKAXE);
        handheld(itemModelGenerator, ModItems.CLAY_SHOVEL); handheld(itemModelGenerator, ModItems.CLAY_AXE); handheld(itemModelGenerator, ModItems.CLAY_HOE);

        // Nether Wart tools
        handheld(itemModelGenerator, ModItems.NETHER_WART_SWORD); handheld(itemModelGenerator, ModItems.NETHER_WART_PICKAXE);
        handheld(itemModelGenerator, ModItems.NETHER_WART_SHOVEL); handheld(itemModelGenerator, ModItems.NETHER_WART_AXE); handheld(itemModelGenerator, ModItems.NETHER_WART_HOE);

        // Brick tools
        handheld(itemModelGenerator, ModItems.BRICK_SWORD); handheld(itemModelGenerator, ModItems.BRICK_PICKAXE);
        handheld(itemModelGenerator, ModItems.BRICK_SHOVEL); handheld(itemModelGenerator, ModItems.BRICK_AXE); handheld(itemModelGenerator, ModItems.BRICK_HOE);

        // Nether Brick tools
        handheld(itemModelGenerator, ModItems.NETHER_BRICK_SWORD); handheld(itemModelGenerator, ModItems.NETHER_BRICK_PICKAXE);
        handheld(itemModelGenerator, ModItems.NETHER_BRICK_SHOVEL); handheld(itemModelGenerator, ModItems.NETHER_BRICK_AXE); handheld(itemModelGenerator, ModItems.NETHER_BRICK_HOE);

        // Pointed Dripstone tools
        handheld(itemModelGenerator, ModItems.POINTED_DRIPSTONE_SWORD); handheld(itemModelGenerator, ModItems.POINTED_DRIPSTONE_PICKAXE);
        handheld(itemModelGenerator, ModItems.POINTED_DRIPSTONE_SHOVEL); handheld(itemModelGenerator, ModItems.POINTED_DRIPSTONE_AXE); handheld(itemModelGenerator, ModItems.POINTED_DRIPSTONE_HOE);

        // Copper tools
        handheld(itemModelGenerator, ModItems.COPPER_SWORD); handheld(itemModelGenerator, ModItems.COPPER_PICKAXE);
        handheld(itemModelGenerator, ModItems.COPPER_SHOVEL); handheld(itemModelGenerator, ModItems.COPPER_AXE); handheld(itemModelGenerator, ModItems.COPPER_HOE);

        // Phantom tools
        handheld(itemModelGenerator, ModItems.PHANTOM_SWORD); handheld(itemModelGenerator, ModItems.PHANTOM_PICKAXE);
        handheld(itemModelGenerator, ModItems.PHANTOM_SHOVEL); handheld(itemModelGenerator, ModItems.PHANTOM_AXE); handheld(itemModelGenerator, ModItems.PHANTOM_HOE);

        // Magma Cream tools
        handheld(itemModelGenerator, ModItems.MAGMA_CREAM_SWORD); handheld(itemModelGenerator, ModItems.MAGMA_CREAM_PICKAXE);
        handheld(itemModelGenerator, ModItems.MAGMA_CREAM_SHOVEL); handheld(itemModelGenerator, ModItems.MAGMA_CREAM_AXE); handheld(itemModelGenerator, ModItems.MAGMA_CREAM_HOE);

        // Slime tools
        handheld(itemModelGenerator, ModItems.SLIME_SWORD); handheld(itemModelGenerator, ModItems.SLIME_PICKAXE);
        handheld(itemModelGenerator, ModItems.SLIME_SHOVEL); handheld(itemModelGenerator, ModItems.SLIME_AXE); handheld(itemModelGenerator, ModItems.SLIME_HOE);

        // Blaze tools
        handheld(itemModelGenerator, ModItems.BLAZE_SWORD); handheld(itemModelGenerator, ModItems.BLAZE_PICKAXE);
        handheld(itemModelGenerator, ModItems.BLAZE_SHOVEL); handheld(itemModelGenerator, ModItems.BLAZE_AXE); handheld(itemModelGenerator, ModItems.BLAZE_HOE);

        // Nautilus tools
        handheld(itemModelGenerator, ModItems.NAUTILUS_SWORD); handheld(itemModelGenerator, ModItems.NAUTILUS_PICKAXE);
        handheld(itemModelGenerator, ModItems.NAUTILUS_SHOVEL); handheld(itemModelGenerator, ModItems.NAUTILUS_AXE); handheld(itemModelGenerator, ModItems.NAUTILUS_HOE);

        // Purpur tools
        handheld(itemModelGenerator, ModItems.PURPUR_SWORD); handheld(itemModelGenerator, ModItems.PURPUR_PICKAXE);
        handheld(itemModelGenerator, ModItems.PURPUR_SHOVEL); handheld(itemModelGenerator, ModItems.PURPUR_AXE); handheld(itemModelGenerator, ModItems.PURPUR_HOE);

        // Ghast Tear tools
        handheld(itemModelGenerator, ModItems.GHAST_TEAR_SWORD); handheld(itemModelGenerator, ModItems.GHAST_TEAR_PICKAXE);
        handheld(itemModelGenerator, ModItems.GHAST_TEAR_SHOVEL); handheld(itemModelGenerator, ModItems.GHAST_TEAR_AXE); handheld(itemModelGenerator, ModItems.GHAST_TEAR_HOE);

        // Eye of Ender tools
        handheld(itemModelGenerator, ModItems.EYE_OF_ENDER_SWORD); handheld(itemModelGenerator, ModItems.EYE_OF_ENDER_PICKAXE);
        handheld(itemModelGenerator, ModItems.EYE_OF_ENDER_SHOVEL); handheld(itemModelGenerator, ModItems.EYE_OF_ENDER_AXE); handheld(itemModelGenerator, ModItems.EYE_OF_ENDER_HOE);

        // Shulker tools
        handheld(itemModelGenerator, ModItems.SHULKER_SWORD); handheld(itemModelGenerator, ModItems.SHULKER_PICKAXE);
        handheld(itemModelGenerator, ModItems.SHULKER_SHOVEL); handheld(itemModelGenerator, ModItems.SHULKER_AXE); handheld(itemModelGenerator, ModItems.SHULKER_HOE);

        // Echo Shard tools
        handheld(itemModelGenerator, ModItems.ECHO_SHARD_SWORD); handheld(itemModelGenerator, ModItems.ECHO_SHARD_PICKAXE);
        handheld(itemModelGenerator, ModItems.ECHO_SHARD_SHOVEL); handheld(itemModelGenerator, ModItems.ECHO_SHARD_AXE); handheld(itemModelGenerator, ModItems.ECHO_SHARD_HOE);

        // Dragon Breath tools
        handheld(itemModelGenerator, ModItems.DRAGON_BREATH_SWORD); handheld(itemModelGenerator, ModItems.DRAGON_BREATH_PICKAXE);
        handheld(itemModelGenerator, ModItems.DRAGON_BREATH_SHOVEL); handheld(itemModelGenerator, ModItems.DRAGON_BREATH_AXE); handheld(itemModelGenerator, ModItems.DRAGON_BREATH_HOE);

        // ===== Armor items (GENERATED) =====

        // Emerald armor
        trimmedArmor(itemModelGenerator, ModItems.EMERALD_HELMET);
        trimmedArmor(itemModelGenerator, ModItems.EMERALD_CHESTPLATE);
        trimmedArmor(itemModelGenerator, ModItems.EMERALD_LEGGINGS);
        trimmedArmor(itemModelGenerator, ModItems.EMERALD_BOOTS);

        // Hardened Redstone armor
        trimmedArmor(itemModelGenerator, ModItems.HRED_HELMET);
        trimmedArmor(itemModelGenerator, ModItems.HRED_CHESTPLATE);
        trimmedArmor(itemModelGenerator, ModItems.HRED_LEGGINGS);
        trimmedArmor(itemModelGenerator, ModItems.HRED_BOOTS);

        // Hardened Glowstone armor
        trimmedArmor(itemModelGenerator, ModItems.HGLOW_HELMET);
        trimmedArmor(itemModelGenerator, ModItems.HGLOW_CHESTPLATE);
        trimmedArmor(itemModelGenerator, ModItems.HGLOW_LEGGINGS);
        trimmedArmor(itemModelGenerator, ModItems.HGLOW_BOOTS);

        // Obsidian armor
        trimmedArmor(itemModelGenerator, ModItems.OBSIDIAN_HELMET);
        trimmedArmor(itemModelGenerator, ModItems.OBSIDIAN_CHESTPLATE);
        trimmedArmor(itemModelGenerator, ModItems.OBSIDIAN_LEGGINGS);
        trimmedArmor(itemModelGenerator, ModItems.OBSIDIAN_BOOTS);

        // Ferrous Gold armor
        trimmedArmor(itemModelGenerator, ModItems.RGOLD_HELMET);
        trimmedArmor(itemModelGenerator, ModItems.RGOLD_CHESTPLATE);
        trimmedArmor(itemModelGenerator, ModItems.RGOLD_LEGGINGS);
        trimmedArmor(itemModelGenerator, ModItems.RGOLD_BOOTS);

        // Refined Lapis armor
        trimmedArmor(itemModelGenerator, ModItems.RLAPIS_HELMET);
        trimmedArmor(itemModelGenerator, ModItems.RLAPIS_CHESTPLATE);
        trimmedArmor(itemModelGenerator, ModItems.RLAPIS_LEGGINGS);
        trimmedArmor(itemModelGenerator, ModItems.RLAPIS_BOOTS);

        // Overpower armor
        trimmedArmor(itemModelGenerator, ModItems.OVERPOWER_HELMET);
        trimmedArmor(itemModelGenerator, ModItems.OVERPOWER_CHESTPLATE);
        trimmedArmor(itemModelGenerator, ModItems.OVERPOWER_LEGGINGS);
        trimmedArmor(itemModelGenerator, ModItems.OVERPOWER_BOOTS);

        // Ectoplasm armor
        trimmedArmor(itemModelGenerator, ModItems.ECTO_HELMET);
        trimmedArmor(itemModelGenerator, ModItems.ECTO_CHESTPLATE);
        trimmedArmor(itemModelGenerator, ModItems.ECTO_LEGGINGS);
        trimmedArmor(itemModelGenerator, ModItems.ECTO_BOOTS);

        // Coal armor
        trimmedArmor(itemModelGenerator, ModItems.COAL_HELMET);
        trimmedArmor(itemModelGenerator, ModItems.COAL_CHESTPLATE);
        trimmedArmor(itemModelGenerator, ModItems.COAL_LEGGINGS);
        trimmedArmor(itemModelGenerator, ModItems.COAL_BOOTS);

        // Cake armor
        itemModelGenerator.register(ModItems.CAKE_HELMET, Models.GENERATED);
        itemModelGenerator.register(ModItems.CAKE_CHESTPLATE, Models.GENERATED);
        itemModelGenerator.register(ModItems.CAKE_LEGGINGS, Models.GENERATED);
        itemModelGenerator.register(ModItems.CAKE_BOOTS, Models.GENERATED);

        // Food set armors
        itemModelGenerator.register(ModItems.BREAD_HELMET, Models.GENERATED);
        itemModelGenerator.register(ModItems.BREAD_CHESTPLATE, Models.GENERATED);
        itemModelGenerator.register(ModItems.BREAD_LEGGINGS, Models.GENERATED);
        itemModelGenerator.register(ModItems.BREAD_BOOTS, Models.GENERATED);
        itemModelGenerator.register(ModItems.DRIED_KELP_HELMET, Models.GENERATED);
        itemModelGenerator.register(ModItems.DRIED_KELP_CHESTPLATE, Models.GENERATED);
        itemModelGenerator.register(ModItems.DRIED_KELP_LEGGINGS, Models.GENERATED);
        itemModelGenerator.register(ModItems.DRIED_KELP_BOOTS, Models.GENERATED);
        itemModelGenerator.register(ModItems.ROTTEN_FLESH_HELMET, Models.GENERATED);
        itemModelGenerator.register(ModItems.ROTTEN_FLESH_CHESTPLATE, Models.GENERATED);
        itemModelGenerator.register(ModItems.ROTTEN_FLESH_LEGGINGS, Models.GENERATED);
        itemModelGenerator.register(ModItems.ROTTEN_FLESH_BOOTS, Models.GENERATED);
        itemModelGenerator.register(ModItems.MELON_HELMET, Models.GENERATED);
        itemModelGenerator.register(ModItems.MELON_CHESTPLATE, Models.GENERATED);
        itemModelGenerator.register(ModItems.MELON_LEGGINGS, Models.GENERATED);
        itemModelGenerator.register(ModItems.MELON_BOOTS, Models.GENERATED);
        itemModelGenerator.register(ModItems.SWEET_BERRY_HELMET, Models.GENERATED);
        itemModelGenerator.register(ModItems.SWEET_BERRY_CHESTPLATE, Models.GENERATED);
        itemModelGenerator.register(ModItems.SWEET_BERRY_LEGGINGS, Models.GENERATED);
        itemModelGenerator.register(ModItems.SWEET_BERRY_BOOTS, Models.GENERATED);
        itemModelGenerator.register(ModItems.PUMPKIN_PIE_HELMET, Models.GENERATED);
        itemModelGenerator.register(ModItems.PUMPKIN_PIE_CHESTPLATE, Models.GENERATED);
        itemModelGenerator.register(ModItems.PUMPKIN_PIE_LEGGINGS, Models.GENERATED);
        itemModelGenerator.register(ModItems.PUMPKIN_PIE_BOOTS, Models.GENERATED);
        itemModelGenerator.register(ModItems.MUSHROOM_HELMET, Models.GENERATED);
        itemModelGenerator.register(ModItems.MUSHROOM_CHESTPLATE, Models.GENERATED);
        itemModelGenerator.register(ModItems.MUSHROOM_LEGGINGS, Models.GENERATED);
        itemModelGenerator.register(ModItems.MUSHROOM_BOOTS, Models.GENERATED);
        itemModelGenerator.register(ModItems.PUFFERFISH_HELMET, Models.GENERATED);
        itemModelGenerator.register(ModItems.PUFFERFISH_CHESTPLATE, Models.GENERATED);
        itemModelGenerator.register(ModItems.PUFFERFISH_LEGGINGS, Models.GENERATED);
        itemModelGenerator.register(ModItems.PUFFERFISH_BOOTS, Models.GENERATED);
        itemModelGenerator.register(ModItems.HONEY_HELMET, Models.GENERATED);
        itemModelGenerator.register(ModItems.HONEY_CHESTPLATE, Models.GENERATED);
        itemModelGenerator.register(ModItems.HONEY_LEGGINGS, Models.GENERATED);
        itemModelGenerator.register(ModItems.HONEY_BOOTS, Models.GENERATED);
        itemModelGenerator.register(ModItems.CHORUS_FRUIT_HELMET, Models.GENERATED);
        itemModelGenerator.register(ModItems.CHORUS_FRUIT_CHESTPLATE, Models.GENERATED);
        itemModelGenerator.register(ModItems.CHORUS_FRUIT_LEGGINGS, Models.GENERATED);
        itemModelGenerator.register(ModItems.CHORUS_FRUIT_BOOTS, Models.GENERATED);
        itemModelGenerator.register(ModItems.GOLDEN_APPLE_HELMET, Models.GENERATED);
        itemModelGenerator.register(ModItems.GOLDEN_APPLE_CHESTPLATE, Models.GENERATED);
        itemModelGenerator.register(ModItems.GOLDEN_APPLE_LEGGINGS, Models.GENERATED);
        itemModelGenerator.register(ModItems.GOLDEN_APPLE_BOOTS, Models.GENERATED);

        // Crystal/element armors
        trimmedArmor(itemModelGenerator, ModItems.CAMETHYST_HELMET);
        trimmedArmor(itemModelGenerator, ModItems.CAMETHYST_CHESTPLATE);
        trimmedArmor(itemModelGenerator, ModItems.CAMETHYST_LEGGINGS);
        trimmedArmor(itemModelGenerator, ModItems.CAMETHYST_BOOTS);
        trimmedArmor(itemModelGenerator, ModItems.ICE_HELMET);
        trimmedArmor(itemModelGenerator, ModItems.ICE_CHESTPLATE);
        trimmedArmor(itemModelGenerator, ModItems.ICE_LEGGINGS);
        trimmedArmor(itemModelGenerator, ModItems.ICE_BOOTS);
        trimmedArmor(itemModelGenerator, ModItems.PQUARTZ_HELMET);
        trimmedArmor(itemModelGenerator, ModItems.PQUARTZ_CHESTPLATE);
        trimmedArmor(itemModelGenerator, ModItems.PQUARTZ_LEGGINGS);
        trimmedArmor(itemModelGenerator, ModItems.PQUARTZ_BOOTS);
        trimmedArmor(itemModelGenerator, ModItems.PPRISM_HELMET);
        trimmedArmor(itemModelGenerator, ModItems.PPRISM_CHESTPLATE);
        trimmedArmor(itemModelGenerator, ModItems.PPRISM_LEGGINGS);
        trimmedArmor(itemModelGenerator, ModItems.PPRISM_BOOTS);

        // FNI armor
        trimmedArmor(itemModelGenerator, ModItems.FNI_HELMET);
        trimmedArmor(itemModelGenerator, ModItems.FNI_CHESTPLATE);
        trimmedArmor(itemModelGenerator, ModItems.FNI_LEGGINGS);
        trimmedArmor(itemModelGenerator, ModItems.FNI_BOOTS);

        // Rabbit Hide armor
        trimmedArmor(itemModelGenerator, ModItems.RABBIT_HIDE_HELMET);
        trimmedArmor(itemModelGenerator, ModItems.RABBIT_HIDE_CHESTPLATE);
        trimmedArmor(itemModelGenerator, ModItems.RABBIT_HIDE_LEGGINGS);
        trimmedArmor(itemModelGenerator, ModItems.RABBIT_HIDE_BOOTS);

        // Cactus armor
        trimmedArmor(itemModelGenerator, ModItems.CACTUS_HELMET);
        trimmedArmor(itemModelGenerator, ModItems.CACTUS_CHESTPLATE);
        trimmedArmor(itemModelGenerator, ModItems.CACTUS_LEGGINGS);
        trimmedArmor(itemModelGenerator, ModItems.CACTUS_BOOTS);

        // Bone armor
        trimmedArmor(itemModelGenerator, ModItems.BONE_HELMET);
        trimmedArmor(itemModelGenerator, ModItems.BONE_CHESTPLATE);
        trimmedArmor(itemModelGenerator, ModItems.BONE_LEGGINGS);
        trimmedArmor(itemModelGenerator, ModItems.BONE_BOOTS);

        // Clay armor
        trimmedArmor(itemModelGenerator, ModItems.CLAY_HELMET);
        trimmedArmor(itemModelGenerator, ModItems.CLAY_CHESTPLATE);
        trimmedArmor(itemModelGenerator, ModItems.CLAY_LEGGINGS);
        trimmedArmor(itemModelGenerator, ModItems.CLAY_BOOTS);

        // Brick armor
        trimmedArmor(itemModelGenerator, ModItems.BRICK_HELMET);
        trimmedArmor(itemModelGenerator, ModItems.BRICK_CHESTPLATE);
        trimmedArmor(itemModelGenerator, ModItems.BRICK_LEGGINGS);
        trimmedArmor(itemModelGenerator, ModItems.BRICK_BOOTS);

        // Nether Brick armor
        trimmedArmor(itemModelGenerator, ModItems.NETHER_BRICK_HELMET);
        trimmedArmor(itemModelGenerator, ModItems.NETHER_BRICK_CHESTPLATE);
        trimmedArmor(itemModelGenerator, ModItems.NETHER_BRICK_LEGGINGS);
        trimmedArmor(itemModelGenerator, ModItems.NETHER_BRICK_BOOTS);

        // Copper armor
        trimmedArmor(itemModelGenerator, ModItems.COPPER_HELMET);
        trimmedArmor(itemModelGenerator, ModItems.COPPER_CHESTPLATE);
        trimmedArmor(itemModelGenerator, ModItems.COPPER_LEGGINGS);
        trimmedArmor(itemModelGenerator, ModItems.COPPER_BOOTS);

        // Phantom armor
        trimmedArmor(itemModelGenerator, ModItems.PHANTOM_HELMET);
        trimmedArmor(itemModelGenerator, ModItems.PHANTOM_CHESTPLATE);
        trimmedArmor(itemModelGenerator, ModItems.PHANTOM_LEGGINGS);
        trimmedArmor(itemModelGenerator, ModItems.PHANTOM_BOOTS);

        // Magma Cream armor
        trimmedArmor(itemModelGenerator, ModItems.MAGMA_CREAM_HELMET);
        trimmedArmor(itemModelGenerator, ModItems.MAGMA_CREAM_CHESTPLATE);
        trimmedArmor(itemModelGenerator, ModItems.MAGMA_CREAM_LEGGINGS);
        trimmedArmor(itemModelGenerator, ModItems.MAGMA_CREAM_BOOTS);

        // Slime armor
        trimmedArmor(itemModelGenerator, ModItems.SLIME_HELMET);
        trimmedArmor(itemModelGenerator, ModItems.SLIME_CHESTPLATE);
        trimmedArmor(itemModelGenerator, ModItems.SLIME_LEGGINGS);
        trimmedArmor(itemModelGenerator, ModItems.SLIME_BOOTS);

        // Blaze armor
        trimmedArmor(itemModelGenerator, ModItems.BLAZE_HELMET);
        trimmedArmor(itemModelGenerator, ModItems.BLAZE_CHESTPLATE);
        trimmedArmor(itemModelGenerator, ModItems.BLAZE_LEGGINGS);
        trimmedArmor(itemModelGenerator, ModItems.BLAZE_BOOTS);

        // Nautilus armor
        trimmedArmor(itemModelGenerator, ModItems.NAUTILUS_HELMET);
        trimmedArmor(itemModelGenerator, ModItems.NAUTILUS_CHESTPLATE);
        trimmedArmor(itemModelGenerator, ModItems.NAUTILUS_LEGGINGS);
        trimmedArmor(itemModelGenerator, ModItems.NAUTILUS_BOOTS);

        // Purpur armor
        trimmedArmor(itemModelGenerator, ModItems.PURPUR_HELMET);
        trimmedArmor(itemModelGenerator, ModItems.PURPUR_CHESTPLATE);
        trimmedArmor(itemModelGenerator, ModItems.PURPUR_LEGGINGS);
        trimmedArmor(itemModelGenerator, ModItems.PURPUR_BOOTS);

        // Ghast Tear armor
        trimmedArmor(itemModelGenerator, ModItems.GHAST_TEAR_HELMET);
        trimmedArmor(itemModelGenerator, ModItems.GHAST_TEAR_CHESTPLATE);
        trimmedArmor(itemModelGenerator, ModItems.GHAST_TEAR_LEGGINGS);
        trimmedArmor(itemModelGenerator, ModItems.GHAST_TEAR_BOOTS);

        // Eye of Ender armor
        trimmedArmor(itemModelGenerator, ModItems.EYE_OF_ENDER_HELMET);
        trimmedArmor(itemModelGenerator, ModItems.EYE_OF_ENDER_CHESTPLATE);
        trimmedArmor(itemModelGenerator, ModItems.EYE_OF_ENDER_LEGGINGS);
        trimmedArmor(itemModelGenerator, ModItems.EYE_OF_ENDER_BOOTS);

        // Shulker armor
        trimmedArmor(itemModelGenerator, ModItems.SHULKER_HELMET);
        trimmedArmor(itemModelGenerator, ModItems.SHULKER_CHESTPLATE);
        trimmedArmor(itemModelGenerator, ModItems.SHULKER_LEGGINGS);
        trimmedArmor(itemModelGenerator, ModItems.SHULKER_BOOTS);

        // Turtle Scute armor
        trimmedArmor(itemModelGenerator, ModItems.TURTLE_SCUTE_HELMET);
        trimmedArmor(itemModelGenerator, ModItems.TURTLE_SCUTE_CHESTPLATE);
        trimmedArmor(itemModelGenerator, ModItems.TURTLE_SCUTE_LEGGINGS);
        trimmedArmor(itemModelGenerator, ModItems.TURTLE_SCUTE_BOOTS);

        // Echo Shard armor
        trimmedArmor(itemModelGenerator, ModItems.ECHO_SHARD_HELMET);
        trimmedArmor(itemModelGenerator, ModItems.ECHO_SHARD_CHESTPLATE);
        trimmedArmor(itemModelGenerator, ModItems.ECHO_SHARD_LEGGINGS);
        trimmedArmor(itemModelGenerator, ModItems.ECHO_SHARD_BOOTS);

        // Dragon Breath armor
        trimmedArmor(itemModelGenerator, ModItems.DRAGON_BREATH_HELMET);
        trimmedArmor(itemModelGenerator, ModItems.DRAGON_BREATH_CHESTPLATE);
        trimmedArmor(itemModelGenerator, ModItems.DRAGON_BREATH_LEGGINGS);
        trimmedArmor(itemModelGenerator, ModItems.DRAGON_BREATH_BOOTS);

        // ===== Spawn Eggs =====
        // Spawn eggs need no model registration — vanilla applies the template_spawn_egg parent automatically
    }

    /**
     * Helper to register an item with the HANDHELD model (for tools).
     */
    private void handheld(ItemModelGenerator itemModelGenerator, Item item) {
        itemModelGenerator.register(item, Models.HANDHELD);
    }

    private static final LinkedHashMap<String, Float> TRIM_MATERIALS = new LinkedHashMap<>();
    static {
        TRIM_MATERIALS.put("quartz", 0.1F);
        TRIM_MATERIALS.put("iron", 0.2F);
        TRIM_MATERIALS.put("netherite", 0.3F);
        TRIM_MATERIALS.put("redstone", 0.4F);
        TRIM_MATERIALS.put("copper", 0.5F);
        TRIM_MATERIALS.put("gold", 0.6F);
        TRIM_MATERIALS.put("emerald", 0.7F);
        TRIM_MATERIALS.put("diamond", 0.8F);
        TRIM_MATERIALS.put("lapis", 0.9F);
        TRIM_MATERIALS.put("amethyst", 1.0F);
    }

    /**
     * Generates a base armor item model with trim_type predicate overrides plus one
     * overlay model per trim material. Mirrors the Forge reference's trimmedArmorItem().
     */
    private void trimmedArmor(ItemModelGenerator gen, Item item) {
        Identifier itemId = Registries.ITEM.getId(item);
        String itemPath = itemId.getPath();
        String armorType;
        if (itemPath.endsWith("_helmet")) armorType = "helmet";
        else if (itemPath.endsWith("_chestplate")) armorType = "chestplate";
        else if (itemPath.endsWith("_leggings")) armorType = "leggings";
        else if (itemPath.endsWith("_boots")) armorType = "boots";
        else throw new IllegalArgumentException("trimmedArmor called on non-armor item: " + itemPath);

        JsonArray overrides = new JsonArray();
        TRIM_MATERIALS.forEach((material, value) -> {
            JsonObject override = new JsonObject();
            JsonObject predicate = new JsonObject();
            predicate.addProperty("trim_type", value);
            override.add("predicate", predicate);
            override.addProperty("model", "usefultoolsmod:item/" + itemPath + "_" + material + "_trim");
            overrides.add(override);
        });

        Identifier baseId = Identifier.of(itemId.getNamespace(), "item/" + itemPath);
        gen.writer.accept(baseId, () -> {
            JsonObject root = new JsonObject();
            root.addProperty("parent", "minecraft:item/generated");
            JsonObject textures = new JsonObject();
            textures.addProperty("layer0", "usefultoolsmod:item/" + itemPath);
            root.add("textures", textures);
            root.add("overrides", overrides);
            return root;
        });

        TRIM_MATERIALS.forEach((material, value) -> {
            String trimName = itemPath + "_" + material + "_trim";
            Identifier trimId = Identifier.of(itemId.getNamespace(), "item/" + trimName);
            gen.writer.accept(trimId, () -> {
                JsonObject root = new JsonObject();
                root.addProperty("parent", "minecraft:item/generated");
                JsonObject textures = new JsonObject();
                textures.addProperty("layer0", "usefultoolsmod:item/" + itemPath);
                textures.addProperty("layer1", "minecraft:trims/items/" + armorType + "_trim_" + material);
                root.add("textures", textures);
                return root;
            });
        });
    }
}
