package com.stonytark.usefultoolsmod.datagen;

import com.stonytark.usefultoolsmod.UsefultoolsMod;
import com.stonytark.usefultoolsmod.block.ModBlocks;
import com.stonytark.usefultoolsmod.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends TagsProvider<net.minecraft.world.level.block.Block> {
    public ModBlockTagProvider(PackOutput output,
                               CompletableFuture<HolderLookup.Provider> lookupProvider,
                               ExistingFileHelper existingFileHelper) {
        // Forge's BlockTagsProvider ctor takes ExistingFileHelper; the NeoForge
        // 3-arg form (output, lookupProvider, modid) doesn't exist here.
        super(output, Registries.BLOCK, lookupProvider, UsefultoolsMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.RGOLDBLOCK.getKey())
                .add(ModBlocks.HRBLOCK.getKey())
                .add(ModBlocks.RGOLDORE.getKey())
                .add(ModBlocks.RGOLD_NETHER_ORE.getKey())
                .add(ModBlocks.RGOLD_END_ORE.getKey())
                .add(ModBlocks.RGOLD_DEEPSLATE_ORE.getKey())
                .add(ModBlocks.SEMBLOCK.getKey())
                .add(ModBlocks.SOBLOCK.getKey())
                .add(ModBlocks.LBLOCK.getKey())
                .add(ModBlocks.SPECTRAL_INFUSER.getKey())
                .add(ModBlocks.ECTOPLASM_BLOCK.getKey())
                .add(ModBlocks.HGLOW_BLOCK.getKey())
                .add(ModBlocks.RAW_RGOLD_BLOCK.getKey())
                .add(ModBlocks.REFINED_ECTOPLASM_BLOCK.getKey())
                .add(ModBlocks.HARDENED_COAL_BLOCK.getKey())
                .add(ModBlocks.OBSHARD_BLOCK.getKey())
                .add(ModBlocks.CALCIFIED_AMETHYST_BLOCK.getKey())
                .add(ModBlocks.GLACIAL_SHARD_BLOCK.getKey())
                .add(ModBlocks.POLISHED_QUARTZ_BLOCK.getKey())
                .add(ModBlocks.POLISHED_PRISMARINE_BLOCK.getKey());

        tag(BlockTags.MINEABLE_WITH_SHOVEL)
                .add(ModBlocks.COAL_DUST_BLOCK.getKey());

        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.RGOLDBLOCK.getKey())
                .add(ModBlocks.LBLOCK.getKey())
                .add(ModBlocks.HRBLOCK.getKey())
                .add(ModBlocks.HGLOW_BLOCK.getKey())
                .add(ModBlocks.OBSHARD_BLOCK.getKey());

        tag(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(ModBlocks.SOBLOCK.getKey())
                .add(ModBlocks.SEMBLOCK.getKey());

        tag(BlockTags.NEEDS_STONE_TOOL)
                .add(ModBlocks.RGOLDORE.getKey())
                .add(ModBlocks.RGOLD_NETHER_ORE.getKey())
                .add(ModBlocks.RGOLD_END_ORE.getKey())
                .add(ModBlocks.RGOLD_DEEPSLATE_ORE.getKey())
                .add(ModBlocks.SPECTRAL_INFUSER.getKey())
                .add(ModBlocks.ECTOPLASM_BLOCK.getKey())
                .add(ModBlocks.RAW_RGOLD_BLOCK.getKey())
                .add(ModBlocks.REFINED_ECTOPLASM_BLOCK.getKey())
                .add(ModBlocks.CALCIFIED_AMETHYST_BLOCK.getKey())
                .add(ModBlocks.GLACIAL_SHARD_BLOCK.getKey())
                .add(ModBlocks.POLISHED_QUARTZ_BLOCK.getKey())
                .add(ModBlocks.POLISHED_PRISMARINE_BLOCK.getKey());

        tag(ModTags.Blocks.NEEDS_HRED_TOOL)
                .addTag(BlockTags.NEEDS_IRON_TOOL);

        tag(ModTags.Blocks.NEEDS_HGLOW_TOOL)
                .addTag(BlockTags.NEEDS_IRON_TOOL);

        tag(ModTags.Blocks.NEEDS_JEM_TOOL)
                .addTag(BlockTags.NEEDS_DIAMOND_TOOL);

        tag(ModTags.Blocks.NEEDS_JOB_TOOL)
                .addTag(BlockTags.NEEDS_DIAMOND_TOOL);

        tag(ModTags.Blocks.NEEDS_OP_TOOL)
                .addTag(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(BuiltInRegistries.BLOCK.getResourceKey(Blocks.BEDROCK).orElseThrow());

        tag(ModTags.Blocks.NEEDS_RGOLD_TOOL)
                .addTag(BlockTags.NEEDS_IRON_TOOL);

        tag(ModTags.Blocks.NEEDS_RLAPIS_TOOL)
                .addTag(BlockTags.NEEDS_IRON_TOOL);

        tag(ModTags.Blocks.NEEDS_SEM_TOOL)
                .addTag(BlockTags.NEEDS_DIAMOND_TOOL);

        tag(ModTags.Blocks.NEEDS_SOB_TOOL)
                .addTag(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(BuiltInRegistries.BLOCK.getResourceKey(Blocks.BEDROCK).orElseThrow());

        tag(ModTags.Blocks.INCORRECT_HRED_TOOL)
                .addTag(BlockTags.INCORRECT_FOR_IRON_TOOL);

        tag(ModTags.Blocks.INCORRECT_HGLOW_TOOL)
                .addTag(BlockTags.INCORRECT_FOR_IRON_TOOL);

        tag(ModTags.Blocks.INCORRECT_JEM_TOOL)
                .addTag(BlockTags.INCORRECT_FOR_DIAMOND_TOOL);

        tag(ModTags.Blocks.INCORRECT_JOB_TOOL)
                .addTag(BlockTags.INCORRECT_FOR_DIAMOND_TOOL);

        tag(ModTags.Blocks.INCORRECT_OP_TOOL)
                .addTag(BlockTags.INCORRECT_FOR_DIAMOND_TOOL);

        tag(ModTags.Blocks.INCORRECT_RGOLD_TOOL)
                .addTag(BlockTags.INCORRECT_FOR_IRON_TOOL);

        tag(ModTags.Blocks.INCORRECT_RLAPIS_TOOL)
                .addTag(BlockTags.INCORRECT_FOR_IRON_TOOL);


        tag(ModTags.Blocks.INCORRECT_SEM_TOOL)
                .addTag(BlockTags.INCORRECT_FOR_DIAMOND_TOOL);

        tag(ModTags.Blocks.INCORRECT_SOB_TOOL)
                .addTag(BlockTags.INCORRECT_FOR_DIAMOND_TOOL);

        tag(ModTags.Blocks.NEEDS_ECTO_TOOL)
                .addTag(BlockTags.NEEDS_IRON_TOOL);

        tag(ModTags.Blocks.INCORRECT_ECTO_TOOL)
                .addTag(BlockTags.INCORRECT_FOR_IRON_TOOL);

        tag(ModTags.Blocks.NEEDS_RECTO_TOOL);
        tag(ModTags.Blocks.INCORRECT_RECTO_TOOL);

        // Magnetization addon: ores and iron-bearing storage blocks the
        // Magnetic Excavator can rip out of the ground. LBLOCK/SEMBLOCK/
        // SOBLOCK are 9× of an iron-bearing composite ingot
        // (RLAPIS/SEM/OBINGOT respectively), so they qualify too.
        tag(ModTags.Blocks.MAGNETIZATION_FERROMAGNETIC)
                .add(ModBlocks.RGOLDORE.getKey())
                .add(ModBlocks.RGOLD_DEEPSLATE_ORE.getKey())
                .add(ModBlocks.RGOLD_NETHER_ORE.getKey())
                .add(ModBlocks.RGOLD_END_ORE.getKey())
                .add(ModBlocks.RGOLDBLOCK.getKey())
                .add(ModBlocks.RAW_RGOLD_BLOCK.getKey())
                .add(ModBlocks.LBLOCK.getKey())
                .add(ModBlocks.SEMBLOCK.getKey())
                .add(ModBlocks.SOBLOCK.getKey());
    }
}
