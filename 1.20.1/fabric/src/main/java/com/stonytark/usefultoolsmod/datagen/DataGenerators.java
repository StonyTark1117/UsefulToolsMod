package com.stonytark.usefultoolsmod.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

/**
 * Fabric 1.20.1 Data Generator Entrypoint
 * Registers all data generators for the mod
 */
public class DataGenerators implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        FabricDataGenerator.Pack pack = generator.createPack();

        // Model providers (block states + item models combined - Fabric requires single FabricModelProvider)
        pack.addProvider(ModItemModelProvider::new);

        // Server-side providers
        pack.addProvider(ModBlockLootTableProvider::new);
        pack.addProvider(ModRecipeProvider::new);
        pack.addProvider(ModBlockTagProvider::new);
        pack.addProvider(ModItemTagProvider::new);
        pack.addProvider(ModAdvancementProvider::new);
        pack.addProvider(ModDatapackEntries::new);
    }
}
