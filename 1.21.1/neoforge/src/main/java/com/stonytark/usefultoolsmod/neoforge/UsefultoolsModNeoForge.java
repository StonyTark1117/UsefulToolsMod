package com.stonytark.usefultoolsmod.neoforge;

import com.stonytark.usefultoolsmod.UsefultoolsMod;
import com.stonytark.usefultoolsmod.UsefultoolsModClient;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;

@Mod(UsefultoolsMod.MOD_ID)
public final class UsefultoolsModNeoForge {
    public UsefultoolsModNeoForge(IEventBus modEventBus) {
        UsefultoolsMod.init();
        if (FMLEnvironment.dist == Dist.CLIENT) {
            UsefultoolsModClient.initClient();
        }
        // JER plugin — NeoForge-only artifact, gated on jeresources being loaded.
        if (dev.architectury.platform.Platform.isModLoaded("jeresources")) {
            try {
                com.stonytark.usefultoolsmod.compat.jer.UsefulToolsJerPlugin.register();
            } catch (Throwable t) {
                UsefultoolsMod.LOGGER.warn("JER plugin registration skipped: {}", t.toString());
            }
        }
    }
}
