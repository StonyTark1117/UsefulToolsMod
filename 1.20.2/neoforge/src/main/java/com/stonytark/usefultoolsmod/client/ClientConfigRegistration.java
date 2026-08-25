package com.stonytark.usefultoolsmod.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.neoforge.client.ConfigScreenHandler;

@OnlyIn(Dist.CLIENT)
public final class ClientConfigRegistration {
    private ClientConfigRegistration() {}

    public static void register() {
        ModLoadingContext.get().registerExtensionPoint(
                ConfigScreenHandler.ConfigScreenFactory.class,
                () -> new ConfigScreenHandler.ConfigScreenFactory(UsefulToolsConfigScreen::build)
        );
    }
}
