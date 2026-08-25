package com.stonytark.usefultoolsmod.fabric.datagen;

import com.stonytark.usefultoolsmod.datagen.ModAdvancementProvider;
import com.stonytark.usefultoolsmod.datagen.ModItemTagProvider;
import com.stonytark.usefultoolsmod.datagen.ModModelProvider;
import com.stonytark.usefultoolsmod.datagen.ModRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;

import com.stonytark.usefultoolsmod.worldgen.ModConfiguredFeatures;
import com.stonytark.usefultoolsmod.worldgen.ModPlacedFeatures;

/**
 * Fabric datagen entrypoint. Bridges Fabric's data-gen API to the common
 * provider classes for recipes, advancements, and block loot tables.
 */
public final class UsefulToolsFabricDataGen implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        FabricDataGenerator.Pack pack = generator.createPack();
        pack.addProvider(UsefulToolsBlockLootProvider::new);
        pack.addProvider(UsefulToolsBlockTagProvider::new);
        pack.addProvider((output, registries) -> new ModItemTagProvider(output, registries));
        pack.addProvider(UsefulToolsWorldgenProvider::new);
        pack.addProvider((output, registries) -> new ModModelProvider(output));
        pack.addProvider((output, registries) -> new ModRecipeProvider(output, registries));
        pack.addProvider((output, registries) -> new ModAdvancementProvider(output, registries));
    }

    @Override
    public void buildRegistry(RegistrySetBuilder builder) {
        builder.add(Registries.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap)
                .add(Registries.PLACED_FEATURE, ModPlacedFeatures::bootstrap);
    }
}
