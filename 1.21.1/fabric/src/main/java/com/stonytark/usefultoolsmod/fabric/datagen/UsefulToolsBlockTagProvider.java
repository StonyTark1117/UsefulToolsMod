package com.stonytark.usefultoolsmod.fabric.datagen;

import com.stonytark.usefultoolsmod.block.ModBlocks;
import com.stonytark.usefultoolsmod.util.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.concurrent.CompletableFuture;

/** Fabric adapter that can deliberately reference vanilla tags not present in the datagen lookup. */
public final class UsefulToolsBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public UsefulToolsBlockTagProvider(FabricDataOutput output,
                                       CompletableFuture<HolderLookup.Provider> lookup) {
        super(output, lookup);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_PICKAXE).add(
                ModBlocks.RGOLDBLOCK.get(), ModBlocks.HRBLOCK.get(), ModBlocks.RGOLDORE.get(),
                ModBlocks.RGOLD_NETHER_ORE.get(), ModBlocks.RGOLD_END_ORE.get(),
                ModBlocks.RGOLD_DEEPSLATE_ORE.get(), ModBlocks.SEMBLOCK.get(), ModBlocks.SOBLOCK.get(),
                ModBlocks.LBLOCK.get(), ModBlocks.SPECTRAL_INFUSER.get(), ModBlocks.ECTOPLASM_BLOCK.get(),
                ModBlocks.HGLOW_BLOCK.get(), ModBlocks.RAW_RGOLD_BLOCK.get(),
                ModBlocks.REFINED_ECTOPLASM_BLOCK.get(), ModBlocks.HARDENED_COAL_BLOCK.get(),
                ModBlocks.OBSHARD_BLOCK.get(), ModBlocks.CALCIFIED_AMETHYST_BLOCK.get(),
                ModBlocks.GLACIAL_SHARD_BLOCK.get(), ModBlocks.POLISHED_QUARTZ_BLOCK.get(),
                ModBlocks.POLISHED_PRISMARINE_BLOCK.get());
        getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_SHOVEL).add(ModBlocks.COAL_DUST_BLOCK.get());
        getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL).add(ModBlocks.RGOLDBLOCK.get(), ModBlocks.LBLOCK.get(),
                ModBlocks.HRBLOCK.get(), ModBlocks.HGLOW_BLOCK.get(), ModBlocks.OBSHARD_BLOCK.get());
        getOrCreateTagBuilder(BlockTags.NEEDS_DIAMOND_TOOL).add(ModBlocks.SOBLOCK.get(), ModBlocks.SEMBLOCK.get());
        getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL).add(
                ModBlocks.RGOLDORE.get(), ModBlocks.RGOLD_NETHER_ORE.get(), ModBlocks.RGOLD_END_ORE.get(),
                ModBlocks.RGOLD_DEEPSLATE_ORE.get(), ModBlocks.SPECTRAL_INFUSER.get(),
                ModBlocks.ECTOPLASM_BLOCK.get(), ModBlocks.RAW_RGOLD_BLOCK.get(),
                ModBlocks.REFINED_ECTOPLASM_BLOCK.get(), ModBlocks.CALCIFIED_AMETHYST_BLOCK.get(),
                ModBlocks.GLACIAL_SHARD_BLOCK.get(), ModBlocks.POLISHED_QUARTZ_BLOCK.get(),
                ModBlocks.POLISHED_PRISMARINE_BLOCK.get());

        toolPair(ModTags.Blocks.NEEDS_HRED_TOOL, BlockTags.NEEDS_IRON_TOOL,
                ModTags.Blocks.INCORRECT_HRED_TOOL, BlockTags.INCORRECT_FOR_IRON_TOOL);
        toolPair(ModTags.Blocks.NEEDS_HGLOW_TOOL, BlockTags.NEEDS_IRON_TOOL,
                ModTags.Blocks.INCORRECT_HGLOW_TOOL, BlockTags.INCORRECT_FOR_IRON_TOOL);
        toolPair(ModTags.Blocks.NEEDS_JEM_TOOL, BlockTags.NEEDS_DIAMOND_TOOL,
                ModTags.Blocks.INCORRECT_JEM_TOOL, BlockTags.INCORRECT_FOR_DIAMOND_TOOL);
        toolPair(ModTags.Blocks.NEEDS_JOB_TOOL, BlockTags.NEEDS_DIAMOND_TOOL,
                ModTags.Blocks.INCORRECT_JOB_TOOL, BlockTags.INCORRECT_FOR_DIAMOND_TOOL);
        getOrCreateTagBuilder(ModTags.Blocks.NEEDS_OP_TOOL).forceAddTag(BlockTags.NEEDS_DIAMOND_TOOL).add(Blocks.BEDROCK);
        getOrCreateTagBuilder(ModTags.Blocks.INCORRECT_OP_TOOL).forceAddTag(BlockTags.INCORRECT_FOR_DIAMOND_TOOL);
        toolPair(ModTags.Blocks.NEEDS_RGOLD_TOOL, BlockTags.NEEDS_IRON_TOOL,
                ModTags.Blocks.INCORRECT_RGOLD_TOOL, BlockTags.INCORRECT_FOR_IRON_TOOL);
        toolPair(ModTags.Blocks.NEEDS_RLAPIS_TOOL, BlockTags.NEEDS_IRON_TOOL,
                ModTags.Blocks.INCORRECT_RLAPIS_TOOL, BlockTags.INCORRECT_FOR_IRON_TOOL);
        toolPair(ModTags.Blocks.NEEDS_SEM_TOOL, BlockTags.NEEDS_DIAMOND_TOOL,
                ModTags.Blocks.INCORRECT_SEM_TOOL, BlockTags.INCORRECT_FOR_DIAMOND_TOOL);
        getOrCreateTagBuilder(ModTags.Blocks.NEEDS_SOB_TOOL).forceAddTag(BlockTags.NEEDS_DIAMOND_TOOL).add(Blocks.BEDROCK);
        getOrCreateTagBuilder(ModTags.Blocks.INCORRECT_SOB_TOOL).forceAddTag(BlockTags.INCORRECT_FOR_DIAMOND_TOOL);
        toolPair(ModTags.Blocks.NEEDS_ECTO_TOOL, BlockTags.NEEDS_IRON_TOOL,
                ModTags.Blocks.INCORRECT_ECTO_TOOL, BlockTags.INCORRECT_FOR_IRON_TOOL);
        getOrCreateTagBuilder(ModTags.Blocks.NEEDS_RECTO_TOOL);
        getOrCreateTagBuilder(ModTags.Blocks.INCORRECT_RECTO_TOOL);

        getOrCreateTagBuilder(ModTags.Blocks.MAGNETIZATION_FERROMAGNETIC).add(
                ModBlocks.RGOLDORE.get(), ModBlocks.RGOLD_DEEPSLATE_ORE.get(),
                ModBlocks.RGOLD_NETHER_ORE.get(), ModBlocks.RGOLD_END_ORE.get(),
                ModBlocks.RGOLDBLOCK.get(), ModBlocks.RAW_RGOLD_BLOCK.get(), ModBlocks.LBLOCK.get(),
                ModBlocks.SEMBLOCK.get(), ModBlocks.SOBLOCK.get());
    }

    private void toolPair(TagKey<Block> required, TagKey<Block> vanillaRequired,
                          TagKey<Block> incorrect, TagKey<Block> vanillaIncorrect) {
        getOrCreateTagBuilder(required).forceAddTag(vanillaRequired);
        getOrCreateTagBuilder(incorrect).forceAddTag(vanillaIncorrect);
    }
}
