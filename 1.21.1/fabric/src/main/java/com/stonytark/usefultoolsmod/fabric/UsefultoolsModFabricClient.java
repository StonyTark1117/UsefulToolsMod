package com.stonytark.usefultoolsmod.fabric;

import com.stonytark.usefultoolsmod.UsefultoolsModClient;
import net.fabricmc.api.ClientModInitializer;

public final class UsefultoolsModFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        UsefultoolsModClient.initClient();
    }
}
