package com.stonytark.usefultoolsmod.datagen;

import com.stonytark.usefultoolsmod.UsefultoolsMod;
import com.stonytark.usefultoolsmod.block.ModBlocks;
import com.stonytark.usefultoolsmod.block.custom.SpectralInfuserBlock;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.data.client.*;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

/**
 * Fabric 1.20.1 Block State Data Generator
 * Generates block state JSON files for all blocks
 */
public class ModBlockStateProvider extends FabricModelProvider {
    public ModBlockStateProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        // Register simple cube blocks with item models
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.RGOLDBLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.HRBLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.RGOLDORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.RGOLD_NETHER_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.RGOLD_END_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.RGOLD_DEEPSLATE_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.SEMBLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.SOBLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.LBLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.HGLOW_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.RAW_RGOLD_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.ECTOPLASM_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.REFINED_ECTOPLASM_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.HARDENED_COAL_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.COAL_DUST_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.OBSHARD_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.CALCIFIED_AMETHYST_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.GLACIAL_SHARD_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.POLISHED_QUARTZ_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.POLISHED_PRISMARINE_BLOCK);

        // Spectral Infuser - orientable block with LIT state
        registerSpectralInfuser(blockStateModelGenerator);
    }

    private void registerSpectralInfuser(BlockStateModelGenerator blockStateModelGenerator) {
        Identifier side = new Identifier(UsefultoolsMod.MOD_ID, "block/spectral_infuser_side");
        Identifier front = new Identifier(UsefultoolsMod.MOD_ID, "block/spectral_infuser_front");
        Identifier frontOn = new Identifier(UsefultoolsMod.MOD_ID, "block/spectral_infuser_front_on");
        Identifier top = new Identifier(UsefultoolsMod.MOD_ID, "block/spectral_infuser_top");

        TextureMap offTextures = new TextureMap()
                .put(TextureKey.SIDE, side)
                .put(TextureKey.FRONT, front)
                .put(TextureKey.TOP, top);

        TextureMap onTextures = new TextureMap()
                .put(TextureKey.SIDE, side)
                .put(TextureKey.FRONT, frontOn)
                .put(TextureKey.TOP, top);

        Identifier offModel = Models.ORIENTABLE.upload(ModBlocks.SPECTRAL_INFUSER, offTextures,
                blockStateModelGenerator.modelCollector);
        Identifier onModel = Models.ORIENTABLE.upload(
                ModelIds.getBlockSubModelId(ModBlocks.SPECTRAL_INFUSER, "_on"), onTextures,
                blockStateModelGenerator.modelCollector);

        blockStateModelGenerator.blockStateCollector.accept(
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

        // Register the block item model to use the off model
        blockStateModelGenerator.registerParentedItemModel(ModBlocks.SPECTRAL_INFUSER, offModel);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        // Item models are generated in ModItemModelProvider
    }
}
