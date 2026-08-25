package com.stonytark.usefultoolsmod.datagen;

import com.stonytark.usefultoolsmod.block.ModBlocks;
import com.stonytark.usefultoolsmod.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModBlockLootTableProvider extends FabricBlockLootTableProvider {
    public ModBlockLootTableProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(output, registryLookup);
    }

    @Override
    public void generate() {
        addDrop(ModBlocks.RGOLDORE, oreDrops(ModBlocks.RGOLDORE, ModItems.RAW_RGOLD));
        addDrop(ModBlocks.RGOLD_NETHER_ORE, oreDrops(ModBlocks.RGOLD_NETHER_ORE, ModItems.RAW_RGOLD));
        addDrop(ModBlocks.RGOLD_END_ORE, oreDrops(ModBlocks.RGOLD_END_ORE, ModItems.RAW_RGOLD));
        addDrop(ModBlocks.RGOLD_DEEPSLATE_ORE, oreDrops(ModBlocks.RGOLD_DEEPSLATE_ORE, ModItems.RAW_RGOLD));

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

        addDrop(ModBlocks.SPECTRAL_INFUSER);
    }
}
