package com.stonytark.usefultoolsmod.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.client.ConfigScreenHandler;

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
