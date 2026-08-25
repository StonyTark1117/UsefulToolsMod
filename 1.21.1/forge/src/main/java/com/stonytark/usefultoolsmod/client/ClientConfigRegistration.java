package com.stonytark.usefultoolsmod.client;

import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.ModContainer;

/** Registers the dependency-free config screen with Forge's Mods screen. */
public final class ClientConfigRegistration {
    private ClientConfigRegistration() {}

    public static void register(ModContainer container) {
        container.registerExtensionPoint(
                ConfigScreenHandler.ConfigScreenFactory.class,
                () -> new ConfigScreenHandler.ConfigScreenFactory(UsefulToolsConfigScreen::build));
    }
}
