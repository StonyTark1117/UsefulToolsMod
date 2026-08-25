package com.stonytark.usefultoolsmod.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.minecraft.client.Minecraft;

@OnlyIn(Dist.CLIENT)
public final class ClientConfigRegistration {
    private ClientConfigRegistration() {}

    public static void register(ModContainer container) {
        container.registerExtensionPoint(
                IConfigScreenFactory.class,
                (modContainer, parent) -> UsefulToolsConfigScreen.build(Minecraft.getInstance(), parent)
        );
    }
}
