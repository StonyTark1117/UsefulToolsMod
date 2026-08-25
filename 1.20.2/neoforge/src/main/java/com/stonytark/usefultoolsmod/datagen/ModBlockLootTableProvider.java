package com.stonytark.usefultoolsmod.datagen;

import com.stonytark.usefultoolsmod.block.ModBlocks;
import com.stonytark.usefultoolsmod.item.ModItems;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.Set;

// In 1.20.1, BlockLootSubProvider(Set<Item>, FeatureFlagSet) — no pRegistries parameter.
// Enchantments constants are used directly without HolderLookup.
public class ModBlockLootTableProvider extends BlockLootSubProvider {
    protected ModBlockLootTableProvider() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        dropSelf(ModBlocks.RGOLDBLOCK.get());
        dropSelf(ModBlocks.HRBLOCK.get());
        dropSelf(ModBlocks.SEMBLOCK.get());
        dropSelf(ModBlocks.SOBLOCK.get());
        dropSelf(ModBlocks.LBLOCK.get());
        dropSelf(ModBlocks.SPECTRAL_INFUSER.get());
        dropSelf(ModBlocks.HGLOW_BLOCK.get());
        dropSelf(ModBlocks.RAW_RGOLD_BLOCK.get());
        dropSelf(ModBlocks.ECTOPLASM_BLOCK.get());
        dropSelf(ModBlocks.REFINED_ECTOPLASM_BLOCK.get());
        dropSelf(ModBlocks.HARDENED_COAL_BLOCK.get());
        dropSelf(ModBlocks.COAL_DUST_BLOCK.get());
        dropSelf(ModBlocks.OBSHARD_BLOCK.get());
        dropSelf(ModBlocks.CALCIFIED_AMETHYST_BLOCK.get());
        dropSelf(ModBlocks.GLACIAL_SHARD_BLOCK.get());
        dropSelf(ModBlocks.POLISHED_QUARTZ_BLOCK.get());
        dropSelf(ModBlocks.POLISHED_PRISMARINE_BLOCK.get());

        this.add(ModBlocks.RGOLDORE.get(),
                block -> createOreDrop(ModBlocks.RGOLDORE.get(), ModItems.RAW_RGOLD.get()));
        this.add(ModBlocks.RGOLD_NETHER_ORE.get(),
                block -> createOreDrop(ModBlocks.RGOLD_NETHER_ORE.get(), ModItems.RAW_RGOLD.get()));
        this.add(ModBlocks.RGOLD_END_ORE.get(),
                block -> createOreDrop(ModBlocks.RGOLD_END_ORE.get(), ModItems.RAW_RGOLD.get()));
        this.add(ModBlocks.RGOLD_DEEPSLATE_ORE.get(),
                block -> createOreDrop(ModBlocks.RGOLD_DEEPSLATE_ORE.get(), ModItems.RAW_RGOLD.get()));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(e -> (Block) e.get())::iterator;
    }
}
