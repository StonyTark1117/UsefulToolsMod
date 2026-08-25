package com.stonytark.usefultoolsmod.block.entity;

import com.stonytark.usefultoolsmod.UsefultoolsMod;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class ModMenuTypes {
    public static final ScreenHandlerType<SpectralInfuserMenu> SPECTRAL_INFUSER_MENU =
            Registry.register(Registries.SCREEN_HANDLER,
                    Identifier.of(UsefultoolsMod.MOD_ID, "spectral_infuser"),
                    new ExtendedScreenHandlerType<>(SpectralInfuserMenu::new, BlockPos.PACKET_CODEC));

    public static void register() {}
}
