package com.stonytark.usefultoolsmod.neoforge;

import com.stonytark.usefultoolsmod.UsefultoolsMod;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.data.loading.DatagenModLoader;
import dev.architectury.event.events.common.LifecycleEvent;

@Mod(UsefultoolsMod.MOD_ID)
public final class UsefultoolsModNeoForge {
    public UsefultoolsModNeoForge(IEventBus modEventBus, ModContainer container) {
        UsefultoolsMod.init();
        if (FMLEnvironment.dist == Dist.CLIENT && !DatagenModLoader.isRunningDataGen()) {
            com.stonytark.usefultoolsmod.neoforge.client.NeoForgeClientRegistration.register(modEventBus);
            com.stonytark.usefultoolsmod.neoforge.client.ClientConfigRegistration.register(container);
        }
        // JER resolves registered blocks/items, so defer it until the shared
        // setup phase just like fuel registration. Mod construction is too
        // early for NeoForge deferred registries.
        LifecycleEvent.SETUP.register(() -> {
            if (dev.architectury.platform.Platform.isModLoaded("jeresources")) {
                try {
                    com.stonytark.usefultoolsmod.compat.jer.UsefulToolsJerPlugin.register();
                } catch (Throwable t) {
                    UsefultoolsMod.LOGGER.warn("JER plugin registration skipped: {}", t.toString());
                }
            }
        });
    }
}
