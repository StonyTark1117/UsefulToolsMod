package com.stonytark.usefultoolsmod.datagen;

import com.stonytark.usefultoolsmod.block.ModBlocks;
import com.stonytark.usefultoolsmod.util.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagsProvider.BlockTagsProvider {

    private static ResourceKey<net.minecraft.world.level.block.Block> key(net.minecraft.world.level.block.Block block) {
        return BuiltInRegistries.BLOCK.getResourceKey(block)
                .orElseThrow(() -> new IllegalStateException("Unregistered block in tag provider: " + block));
    }

    public ModBlockTagProvider(FabricPackOutput output,
                               CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        builder(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(key(ModBlocks.RGOLDBLOCK))
                .add(key(ModBlocks.HRBLOCK))
                .add(key(ModBlocks.RGOLDORE))
                .add(key(ModBlocks.RGOLD_NETHER_ORE))
                .add(key(ModBlocks.RGOLD_END_ORE))
                .add(key(ModBlocks.RGOLD_DEEPSLATE_ORE))
                .add(key(ModBlocks.SEMBLOCK))
                .add(key(ModBlocks.SOBLOCK))
                .add(key(ModBlocks.LBLOCK))
                .add(key(ModBlocks.SPECTRAL_INFUSER))
                .add(key(ModBlocks.ECTOPLASM_BLOCK))
                .add(key(ModBlocks.HGLOW_BLOCK))
                .add(key(ModBlocks.RAW_RGOLD_BLOCK))
                .add(key(ModBlocks.REFINED_ECTOPLASM_BLOCK))
                .add(key(ModBlocks.HARDENED_COAL_BLOCK))
                .add(key(ModBlocks.OBSHARD_BLOCK))
                .add(key(ModBlocks.CALCIFIED_AMETHYST_BLOCK))
                .add(key(ModBlocks.GLACIAL_SHARD_BLOCK))
                .add(key(ModBlocks.POLISHED_QUARTZ_BLOCK))
                .add(key(ModBlocks.POLISHED_PRISMARINE_BLOCK));

        builder(BlockTags.MINEABLE_WITH_SHOVEL)
                .add(key(ModBlocks.COAL_DUST_BLOCK));

        builder(BlockTags.NEEDS_IRON_TOOL)
                .add(key(ModBlocks.RGOLDBLOCK))
                .add(key(ModBlocks.LBLOCK))
                .add(key(ModBlocks.HRBLOCK))
                .add(key(ModBlocks.HGLOW_BLOCK))
                .add(key(ModBlocks.OBSHARD_BLOCK));

        builder(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(key(ModBlocks.SOBLOCK))
                .add(key(ModBlocks.SEMBLOCK));

        builder(BlockTags.NEEDS_STONE_TOOL)
                .add(key(ModBlocks.RGOLDORE))
                .add(key(ModBlocks.RGOLD_NETHER_ORE))
                .add(key(ModBlocks.RGOLD_END_ORE))
                .add(key(ModBlocks.RGOLD_DEEPSLATE_ORE))
                .add(key(ModBlocks.SPECTRAL_INFUSER))
                .add(key(ModBlocks.ECTOPLASM_BLOCK))
                .add(key(ModBlocks.RAW_RGOLD_BLOCK))
                .add(key(ModBlocks.REFINED_ECTOPLASM_BLOCK))
                .add(key(ModBlocks.CALCIFIED_AMETHYST_BLOCK))
                .add(key(ModBlocks.GLACIAL_SHARD_BLOCK))
                .add(key(ModBlocks.POLISHED_QUARTZ_BLOCK))
                .add(key(ModBlocks.POLISHED_PRISMARINE_BLOCK));

        // Mirror each NEEDS_*_TOOL membership into the corresponding vanilla
        // INCORRECT_FOR_*_TOOL tag. Without this, an iron pickaxe would
        // successfully drop SEMBLOCK / SOBLOCK (those tags drive the
        // tool-correctness check at mining time, not NEEDS_*_TOOL), and
        // WTHIT can't establish a tier ordering between mod and vanilla
        // tags (logs "Unsolvable tier comparison" at startup). Adding mod
        // blocks at the highest tier they belong in is sufficient because
        // vanilla chains `incorrect_for_stone_tool` ⊃ `incorrect_for_iron_tool`
        // ⊃ `incorrect_for_diamond_tool` by tag reference.
        builder(BlockTags.INCORRECT_FOR_IRON_TOOL)
                .add(key(ModBlocks.SOBLOCK))
                .add(key(ModBlocks.SEMBLOCK));

        builder(BlockTags.INCORRECT_FOR_STONE_TOOL)
                .add(key(ModBlocks.RGOLDBLOCK))
                .add(key(ModBlocks.LBLOCK))
                .add(key(ModBlocks.HRBLOCK))
                .add(key(ModBlocks.HGLOW_BLOCK))
                .add(key(ModBlocks.OBSHARD_BLOCK));

        builder(ModTags.Blocks.NEEDS_HRED_TOOL).addOptionalTag(BlockTags.NEEDS_IRON_TOOL);
        builder(ModTags.Blocks.NEEDS_HGLOW_TOOL).addOptionalTag(BlockTags.NEEDS_IRON_TOOL);
        builder(ModTags.Blocks.NEEDS_JEM_TOOL).addOptionalTag(BlockTags.NEEDS_DIAMOND_TOOL);
        builder(ModTags.Blocks.NEEDS_JOB_TOOL).addOptionalTag(BlockTags.NEEDS_DIAMOND_TOOL);
        builder(ModTags.Blocks.NEEDS_OP_TOOL).addOptionalTag(BlockTags.NEEDS_DIAMOND_TOOL).add(key(Blocks.BEDROCK));
        builder(ModTags.Blocks.NEEDS_RGOLD_TOOL).addOptionalTag(BlockTags.NEEDS_IRON_TOOL);
        builder(ModTags.Blocks.NEEDS_RLAPIS_TOOL).addOptionalTag(BlockTags.NEEDS_IRON_TOOL);
        builder(ModTags.Blocks.NEEDS_SEM_TOOL).addOptionalTag(BlockTags.NEEDS_DIAMOND_TOOL);
        builder(ModTags.Blocks.NEEDS_SOB_TOOL).addOptionalTag(BlockTags.NEEDS_DIAMOND_TOOL).add(key(Blocks.BEDROCK));

        builder(ModTags.Blocks.INCORRECT_HRED_TOOL).addOptionalTag(BlockTags.INCORRECT_FOR_IRON_TOOL);
        builder(ModTags.Blocks.INCORRECT_HGLOW_TOOL).addOptionalTag(BlockTags.INCORRECT_FOR_IRON_TOOL);
        builder(ModTags.Blocks.INCORRECT_JEM_TOOL).addOptionalTag(BlockTags.INCORRECT_FOR_DIAMOND_TOOL);
        builder(ModTags.Blocks.INCORRECT_JOB_TOOL).addOptionalTag(BlockTags.INCORRECT_FOR_DIAMOND_TOOL);
        builder(ModTags.Blocks.INCORRECT_OP_TOOL).addOptionalTag(BlockTags.INCORRECT_FOR_DIAMOND_TOOL);
        builder(ModTags.Blocks.INCORRECT_RGOLD_TOOL).addOptionalTag(BlockTags.INCORRECT_FOR_IRON_TOOL);
        builder(ModTags.Blocks.INCORRECT_RLAPIS_TOOL).addOptionalTag(BlockTags.INCORRECT_FOR_IRON_TOOL);
        builder(ModTags.Blocks.INCORRECT_SEM_TOOL).addOptionalTag(BlockTags.INCORRECT_FOR_DIAMOND_TOOL);
        builder(ModTags.Blocks.INCORRECT_SOB_TOOL).addOptionalTag(BlockTags.INCORRECT_FOR_DIAMOND_TOOL);

        builder(ModTags.Blocks.NEEDS_ECTO_TOOL).addOptionalTag(BlockTags.NEEDS_IRON_TOOL);
        builder(ModTags.Blocks.INCORRECT_ECTO_TOOL).addOptionalTag(BlockTags.INCORRECT_FOR_IRON_TOOL);

        builder(ModTags.Blocks.NEEDS_RECTO_TOOL);
        builder(ModTags.Blocks.INCORRECT_RECTO_TOOL);

        // Magnetization addon: blocks the Magnetic Excavator can rip out.
        builder(ModTags.Blocks.MAGNETIZATION_FERROMAGNETIC)
                .add(key(ModBlocks.RGOLDORE))
                .add(key(ModBlocks.RGOLD_DEEPSLATE_ORE))
                .add(key(ModBlocks.RGOLD_NETHER_ORE))
                .add(key(ModBlocks.RGOLD_END_ORE))
                .add(key(ModBlocks.RGOLDBLOCK))
                .add(key(ModBlocks.RAW_RGOLD_BLOCK))
                .add(key(ModBlocks.LBLOCK))
                .add(key(ModBlocks.SEMBLOCK))
                .add(key(ModBlocks.SOBLOCK));
    }
}
