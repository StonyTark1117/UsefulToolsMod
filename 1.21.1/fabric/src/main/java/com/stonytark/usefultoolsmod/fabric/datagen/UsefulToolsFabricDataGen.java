package com.stonytark.usefultoolsmod.fabric.datagen;

import com.stonytark.usefultoolsmod.datagen.ModAdvancementProvider;
import com.stonytark.usefultoolsmod.datagen.ModRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

/**
 * Fabric datagen entrypoint. Bridges Fabric's data-gen API to the common
 * provider classes for Recipe + Advancement.
 *
 * LootTable, BlockTag, ItemTag, BlockState, and ItemModel providers are
 * still TBD — they all need per-loader subclasses or non-trivial bridging
 * due to API divergences between Fabric and NeoForge. The pre-generated
 * JSON in common/resources/data/ remains authoritative for those.
 */
public final class UsefulToolsFabricDataGen implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        FabricDataGenerator.Pack pack = generator.createPack();
        pack.addProvider((output, registries) -> new ModRecipeProvider(output, registries));
        pack.addProvider((output, registries) -> new ModAdvancementProvider(output, registries));
    }
}
