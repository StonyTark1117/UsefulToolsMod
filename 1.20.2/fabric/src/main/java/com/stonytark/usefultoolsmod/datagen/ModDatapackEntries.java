package com.stonytark.usefultoolsmod.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

/**
 * Fabric 1.20.1 Datapack Entries Data Generator
 * Placeholder for any custom datapack content generation
 * (This would typically handle worldgen features, structures, etc.)
 */
public class ModDatapackEntries extends FabricDynamicRegistryProvider {
    public ModDatapackEntries(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup registries, Entries entries) {
        // Register custom worldgen features, biomes, structures, etc. here
        // Currently a placeholder - add entries as needed
    }

    @Override
    public String getName() {
        return "Useful Tools Datapack Entries";
    }
}
