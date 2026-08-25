package com.stonytark.usefultoolsmod.datagen;

import com.stonytark.usefultoolsmod.block.ModBlocks;
import com.stonytark.usefultoolsmod.util.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

/**
 * Fabric 1.20.1 Block Tag Data Generator
 * Generates block tags for mining levels, material types, etc.
 */
public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup registries) {
        // Add ore blocks to the correct mining tag
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
                .add(ModBlocks.RGOLDORE)
                .add(ModBlocks.RGOLD_NETHER_ORE)
                .add(ModBlocks.RGOLD_END_ORE)
                .add(ModBlocks.RGOLD_DEEPSLATE_ORE);

        // Storage blocks can be mined with pickaxe
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
                .add(ModBlocks.RGOLDBLOCK)
                .add(ModBlocks.HRBLOCK)
                .add(ModBlocks.SEMBLOCK)
                .add(ModBlocks.SOBLOCK)
                .add(ModBlocks.LBLOCK)
                .add(ModBlocks.HGLOW_BLOCK)
                .add(ModBlocks.RAW_RGOLD_BLOCK)
                .add(ModBlocks.ECTOPLASM_BLOCK)
                .add(ModBlocks.REFINED_ECTOPLASM_BLOCK)
                .add(ModBlocks.HARDENED_COAL_BLOCK)
                .add(ModBlocks.COAL_DUST_BLOCK)
                .add(ModBlocks.OBSHARD_BLOCK)
                .add(ModBlocks.CALCIFIED_AMETHYST_BLOCK)
                .add(ModBlocks.GLACIAL_SHARD_BLOCK)
                .add(ModBlocks.POLISHED_QUARTZ_BLOCK)
                .add(ModBlocks.POLISHED_PRISMARINE_BLOCK)
                .add(ModBlocks.SPECTRAL_INFUSER);

        // Ore blocks that require a specific mining level
        getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.RGOLDBLOCK)
                .add(ModBlocks.LBLOCK)
                .add(ModBlocks.HRBLOCK);

        getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL)
                .add(ModBlocks.RGOLDORE)
                .add(ModBlocks.RGOLD_NETHER_ORE)
                .add(ModBlocks.RGOLD_END_ORE)
                .add(ModBlocks.RGOLD_DEEPSLATE_ORE);

        getOrCreateTagBuilder(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(ModBlocks.SOBLOCK)
                .add(ModBlocks.SEMBLOCK);

        // Custom tool requirement tags
        getOrCreateTagBuilder(ModTags.Blocks.NEEDS_HRED_TOOL)
                .forceAddTag(BlockTags.NEEDS_IRON_TOOL);

        getOrCreateTagBuilder(ModTags.Blocks.NEEDS_JEM_TOOL)
                .forceAddTag(BlockTags.NEEDS_DIAMOND_TOOL);

        getOrCreateTagBuilder(ModTags.Blocks.NEEDS_JOB_TOOL)
                .forceAddTag(BlockTags.NEEDS_DIAMOND_TOOL);

        getOrCreateTagBuilder(ModTags.Blocks.NEEDS_OP_TOOL)
                .forceAddTag(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(Blocks.BEDROCK);

        getOrCreateTagBuilder(ModTags.Blocks.NEEDS_RGOLD_TOOL)
                .forceAddTag(BlockTags.NEEDS_IRON_TOOL);

        getOrCreateTagBuilder(ModTags.Blocks.NEEDS_RLAPIS_TOOL)
                .forceAddTag(BlockTags.NEEDS_IRON_TOOL);

        getOrCreateTagBuilder(ModTags.Blocks.NEEDS_SEM_TOOL)
                .forceAddTag(BlockTags.NEEDS_DIAMOND_TOOL);

        getOrCreateTagBuilder(ModTags.Blocks.NEEDS_SOB_TOOL)
                .forceAddTag(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(Blocks.BEDROCK);
    }
}
