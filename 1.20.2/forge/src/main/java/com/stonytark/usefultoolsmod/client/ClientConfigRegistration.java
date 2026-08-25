package com.stonytark.usefultoolsmod.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.client.ConfigScreenHandler;

@OnlyIn(Dist.CLIENT)
public final class ClientConfigRegistration {
    private ClientConfigRegistration() {}

    /** Forge 48's Mods-screen hook is available only from its removal-marked static context. */
    @SuppressWarnings("removal")
    public static void register() {
        ModLoadingContext.get().registerExtensionPoint(
                ConfigScreenHandler.ConfigScreenFactory.class,
                () -> new ConfigScreenHandler.ConfigScreenFactory(UsefulToolsConfigScreen::build)
        );
    }
}
