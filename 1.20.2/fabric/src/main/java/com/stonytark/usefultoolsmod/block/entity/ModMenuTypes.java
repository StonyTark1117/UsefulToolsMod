package com.stonytark.usefultoolsmod.block.entity;

import com.stonytark.usefultoolsmod.UsefultoolsMod;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModMenuTypes {

    public static final ScreenHandlerType<SpectralInfuserMenu> SPECTRAL_INFUSER =
            Registry.register(Registries.SCREEN_HANDLER,
                    new Identifier(UsefultoolsMod.MOD_ID, "spectral_infuser_menu"),
                    new ExtendedScreenHandlerType<>((syncId, playerInventory, buf) -> {
                        var pos = buf.readBlockPos();
                        var world = playerInventory.player.getWorld();
                        var blockEntity = (SpectralInfuserBlockEntity) world.getBlockEntity(pos);
                        return new SpectralInfuserMenu(syncId, playerInventory, blockEntity, blockEntity.getPropertyDelegate());
                    }));

    public static void register() {
        // Registration happens via the static initializer
    }
}
