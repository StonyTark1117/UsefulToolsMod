package com.stonytark.usefultoolsmod.neoforge;

import com.stonytark.usefultoolsmod.UsefultoolsMod;
import com.stonytark.usefultoolsmod.UsefultoolsModClient;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;

@Mod(UsefultoolsMod.MOD_ID)
public final class UsefultoolsModNeoForge {
    public UsefultoolsModNeoForge(IEventBus modEventBus) {
        UsefultoolsMod.init();
        if (FMLEnvironment.dist == Dist.CLIENT) {
            UsefultoolsModClient.initClient();
        }
    }
}
