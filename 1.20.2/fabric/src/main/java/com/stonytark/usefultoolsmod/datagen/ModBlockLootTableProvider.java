package com.stonytark.usefultoolsmod.datagen;

import com.stonytark.usefultoolsmod.block.ModBlocks;
import com.stonytark.usefultoolsmod.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;

/**
 * Fabric 1.20.1 Block Loot Table Data Generator
 * Generates loot tables for blocks
 */
public class ModBlockLootTableProvider extends FabricBlockLootTableProvider {
    public ModBlockLootTableProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate() {
        // Ore blocks drop RAW_RGOLD (like vanilla gold ore drops raw gold)
        addDrop(ModBlocks.RGOLDORE, oreDrops(ModBlocks.RGOLDORE, ModItems.RAW_RGOLD));
        addDrop(ModBlocks.RGOLD_NETHER_ORE, oreDrops(ModBlocks.RGOLD_NETHER_ORE, ModItems.RAW_RGOLD));
        addDrop(ModBlocks.RGOLD_END_ORE, oreDrops(ModBlocks.RGOLD_END_ORE, ModItems.RAW_RGOLD));
        addDrop(ModBlocks.RGOLD_DEEPSLATE_ORE, oreDrops(ModBlocks.RGOLD_DEEPSLATE_ORE, ModItems.RAW_RGOLD));

        // Storage blocks drop themselves
        addDrop(ModBlocks.RGOLDBLOCK);
        addDrop(ModBlocks.HRBLOCK);
        addDrop(ModBlocks.SEMBLOCK);
        addDrop(ModBlocks.SOBLOCK);
        addDrop(ModBlocks.LBLOCK);
        addDrop(ModBlocks.HGLOW_BLOCK);
        addDrop(ModBlocks.RAW_RGOLD_BLOCK);
        addDrop(ModBlocks.ECTOPLASM_BLOCK);
        addDrop(ModBlocks.REFINED_ECTOPLASM_BLOCK);
        addDrop(ModBlocks.HARDENED_COAL_BLOCK);
        addDrop(ModBlocks.COAL_DUST_BLOCK);
        addDrop(ModBlocks.OBSHARD_BLOCK);
        addDrop(ModBlocks.CALCIFIED_AMETHYST_BLOCK);
        addDrop(ModBlocks.GLACIAL_SHARD_BLOCK);
        addDrop(ModBlocks.POLISHED_QUARTZ_BLOCK);
        addDrop(ModBlocks.POLISHED_PRISMARINE_BLOCK);

        // Spectral Infuser drops itself
        addDrop(ModBlocks.SPECTRAL_INFUSER);
    }
}
