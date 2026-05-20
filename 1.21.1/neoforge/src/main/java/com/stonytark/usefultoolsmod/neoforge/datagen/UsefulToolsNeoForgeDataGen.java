package com.stonytark.usefultoolsmod.neoforge.datagen;

import com.stonytark.usefultoolsmod.UsefultoolsMod;
import com.stonytark.usefultoolsmod.datagen.ModAdvancementProvider;
import com.stonytark.usefultoolsmod.datagen.ModRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

/**
 * NeoForge datagen entrypoint. Registers Recipe + Advancement providers
 * against GatherDataEvent. LootTable + Tag + model providers TBD — see
 * FUTURE_WORK.md and the matching note in UsefulToolsFabricDataGen.
 */
@EventBusSubscriber(modid = UsefultoolsMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public final class UsefulToolsNeoForgeDataGen {
    private UsefulToolsNeoForgeDataGen() {}

    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        PackOutput out = gen.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookup = event.getLookupProvider();

        gen.addProvider(event.includeServer(), new ModRecipeProvider(out, lookup));
        gen.addProvider(event.includeServer(), new ModAdvancementProvider(out, lookup));
    }
}
