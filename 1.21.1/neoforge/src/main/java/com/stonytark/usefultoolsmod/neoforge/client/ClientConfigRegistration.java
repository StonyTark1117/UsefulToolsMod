package com.stonytark.usefultoolsmod.neoforge.client;

import com.stonytark.usefultoolsmod.client.UsefulToolsConfigScreen;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

/** Registers the dependency-free screen with NeoForge's Mods screen. */
public final class ClientConfigRegistration {
    private ClientConfigRegistration() {}

    public static void register(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class,
                (modContainer, parent) -> UsefulToolsConfigScreen.create(parent));
    }
}
