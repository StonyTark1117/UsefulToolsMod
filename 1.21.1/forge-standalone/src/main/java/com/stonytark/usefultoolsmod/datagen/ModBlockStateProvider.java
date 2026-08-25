package com.stonytark.usefultoolsmod.datagen;

import com.stonytark.usefultoolsmod.UsefultoolsMod;
import com.stonytark.usefultoolsmod.block.ModBlocks;
import com.stonytark.usefultoolsmod.block.custom.SpectralInfuserBlock;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, UsefultoolsMod.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ModBlocks.RGOLDBLOCK);
        blockWithItem(ModBlocks.HRBLOCK);
        blockWithItem(ModBlocks.RGOLDORE);
        blockWithItem(ModBlocks.SEMBLOCK);
        blockWithItem(ModBlocks.SOBLOCK);
        blockWithItem(ModBlocks.LBLOCK);
        blockWithItem(ModBlocks.RGOLD_NETHER_ORE);
        blockWithItem(ModBlocks.RGOLD_END_ORE);
        blockWithItem(ModBlocks.RGOLD_DEEPSLATE_ORE);

        blockWithItem(ModBlocks.HGLOW_BLOCK);
        blockWithItem(ModBlocks.RAW_RGOLD_BLOCK);
        blockWithItem(ModBlocks.ECTOPLASM_BLOCK);
        blockWithItem(ModBlocks.REFINED_ECTOPLASM_BLOCK);
        blockWithItem(ModBlocks.HARDENED_COAL_BLOCK);
        blockWithItem(ModBlocks.COAL_DUST_BLOCK);
        blockWithItem(ModBlocks.OBSHARD_BLOCK);
        blockWithItem(ModBlocks.CALCIFIED_AMETHYST_BLOCK);
        blockWithItem(ModBlocks.GLACIAL_SHARD_BLOCK);
        blockWithItem(ModBlocks.POLISHED_QUARTZ_BLOCK);
        blockWithItem(ModBlocks.POLISHED_PRISMARINE_BLOCK);

        spectralInfuser();
    }

    private void spectralInfuser() {
        ModelFile off = models().orientableWithBottom("spectral_infuser",
                modLoc("block/spectral_infuser_side"),
                modLoc("block/spectral_infuser_front"),
                modLoc("block/spectral_infuser_side"),
                modLoc("block/spectral_infuser_top"));
        ModelFile on = models().orientableWithBottom("spectral_infuser_on",
                modLoc("block/spectral_infuser_side"),
                modLoc("block/spectral_infuser_front_on"),
                modLoc("block/spectral_infuser_side"),
                modLoc("block/spectral_infuser_top"));

        var builder = getVariantBuilder(ModBlocks.SPECTRAL_INFUSER.get());
        for (Direction dir : Direction.Plane.HORIZONTAL) {
            int yRot = ((int) dir.toYRot() + 180) % 360;
            builder.partialState()
                    .with(SpectralInfuserBlock.FACING, dir)
                    .with(SpectralInfuserBlock.LIT, false)
                    .modelForState().modelFile(off).rotationY(yRot).addModel();
            builder.partialState()
                    .with(SpectralInfuserBlock.FACING, dir)
                    .with(SpectralInfuserBlock.LIT, true)
                    .modelForState().modelFile(on).rotationY(yRot).addModel();
        }
        simpleBlockItem(ModBlocks.SPECTRAL_INFUSER.get(), off);
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }

    private void blockItem(RegistryObject<? extends Block> blockRegistryObject) {
        simpleBlockItem(blockRegistryObject.get(), new ModelFile.UncheckedModelFile("tutorialmod:block/" +
                ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath()));
    }

    private void blockItem(RegistryObject<? extends Block> blockRegistryObject, String appendix) {
        simpleBlockItem(blockRegistryObject.get(), new ModelFile.UncheckedModelFile("tutorialmod:block/" +
                ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath() + appendix));
    }
}