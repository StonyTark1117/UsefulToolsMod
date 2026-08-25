package com.stonytark.usefultoolsmod.classic.client;

import com.stonytark.usefultoolsmod.classic.ClassicGeneratedConfig;
import cpw.mods.fml.client.IModGuiFactory;
import cpw.mods.fml.client.config.GuiConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

import java.util.Set;

public class ClassicGuiFactory implements IModGuiFactory {
    @Override public void initialize(Minecraft minecraft) {}
    @Override public Class<? extends GuiScreen> mainConfigGuiClass() { return ConfigScreen.class; }
    @Override public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() { return null; }
    @Override public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element) { return null; }

    public static class ConfigScreen extends GuiConfig {
        public ConfigScreen(GuiScreen parent) {
            super(parent, ClassicGeneratedConfig.elements(), "usefultoolsmod", false, false,
                    "Useful Tools Configuration");
        }
    }
}
