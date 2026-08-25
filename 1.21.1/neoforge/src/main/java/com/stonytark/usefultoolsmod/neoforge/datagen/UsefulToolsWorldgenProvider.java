package com.stonytark.usefultoolsmod.neoforge.datagen;

import com.stonytark.usefultoolsmod.UsefultoolsMod;
import com.stonytark.usefultoolsmod.worldgen.ModConfiguredFeatures;
import com.stonytark.usefultoolsmod.worldgen.ModPlacedFeatures;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public final class UsefulToolsWorldgenProvider extends DatapackBuiltinEntriesProvider {
    private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, ModPlacedFeatures::bootstrap);

    public UsefulToolsWorldgenProvider(PackOutput output,
                                       CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(UsefultoolsMod.MOD_ID));
    }
}
