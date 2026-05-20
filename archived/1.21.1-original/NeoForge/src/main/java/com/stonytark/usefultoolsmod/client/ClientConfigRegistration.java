package com.stonytark.usefultoolsmod.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@OnlyIn(Dist.CLIENT)
public final class ClientConfigRegistration {
    private ClientConfigRegistration() {}

    public static void register(ModContainer container) {
        if (!ModList.get().isLoaded("cloth_config")) return;

        container.registerExtensionPoint(
                IConfigScreenFactory.class,
                (modContainer, parent) -> UsefulToolsConfigScreen.build(parent)
        );
    }
}
