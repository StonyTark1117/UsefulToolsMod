package com.stonytark.usefultoolsmod.neoforge.datagen;

import com.stonytark.usefultoolsmod.UsefultoolsMod;
import com.stonytark.usefultoolsmod.datagen.ModAdvancementProvider;
import com.stonytark.usefultoolsmod.datagen.ModBlockLootTableProvider;
import com.stonytark.usefultoolsmod.datagen.ModItemTagProvider;
import com.stonytark.usefultoolsmod.datagen.ModModelProvider;
import com.stonytark.usefultoolsmod.datagen.ModRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * NeoForge datagen entrypoint. Registers the shared server-data providers
 * against GatherDataEvent.
 */
@EventBusSubscriber(modid = UsefultoolsMod.MOD_ID)
public final class UsefulToolsNeoForgeDataGen {
    private UsefulToolsNeoForgeDataGen() {}

    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        PackOutput out = gen.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookup = event.getLookupProvider();

        gen.addProvider(event.includeServer(), new LootTableProvider(out, Collections.emptySet(),
                List.of(new LootTableProvider.SubProviderEntry(ModBlockLootTableProvider::new,
                        LootContextParamSets.BLOCK)), lookup));
        gen.addProvider(event.includeServer(), new UsefulToolsNeoBlockTagProvider(
                out, lookup, event.getExistingFileHelper()));
        gen.addProvider(event.includeServer(), new ModItemTagProvider(out, lookup));
        gen.addProvider(event.includeServer(), new UsefulToolsWorldgenProvider(out, lookup));
        gen.addProvider(event.includeClient(), new ModModelProvider(out));
        gen.addProvider(event.includeServer(), new ModRecipeProvider(out, lookup));
        gen.addProvider(event.includeServer(), new ModAdvancementProvider(out, lookup));
    }
}
