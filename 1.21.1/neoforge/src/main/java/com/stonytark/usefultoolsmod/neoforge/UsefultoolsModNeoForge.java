package com.stonytark.usefultoolsmod.neoforge;

import com.stonytark.usefultoolsmod.UsefultoolsMod;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(UsefultoolsMod.MOD_ID)
public final class UsefultoolsModNeoForge {
    public UsefultoolsModNeoForge(IEventBus modEventBus) {
        UsefultoolsMod.init();
    }
}
