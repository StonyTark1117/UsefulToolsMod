package com.stonytark.usefultoolsmod.fabric.compat;

import com.stonytark.usefultoolsmod.client.UsefulToolsConfigScreen;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

/** Optional Mod Menu bridge; the config screen itself has no external dependency. */
public final class UsefulToolsModMenuPlugin implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return UsefulToolsConfigScreen::create;
    }
}
