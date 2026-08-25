package com.stonytark.usefultoolsmod.compat.modmenu;

import com.stonytark.usefultoolsmod.client.UsefulToolsConfigScreen;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

public final class UsefulToolsModMenuPlugin implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return UsefulToolsConfigScreen::new;
    }
}
