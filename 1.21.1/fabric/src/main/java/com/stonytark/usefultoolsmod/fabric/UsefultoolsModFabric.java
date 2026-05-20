package com.stonytark.usefultoolsmod.fabric;

import com.stonytark.usefultoolsmod.UsefultoolsMod;
import net.fabricmc.api.ModInitializer;

public final class UsefultoolsModFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        UsefultoolsMod.init();
    }
}
