package com.stonytark.usefultoolsmod.block.entity;

import com.stonytark.usefultoolsmod.UsefultoolsMod;
import dev.architectury.registry.menu.MenuRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(UsefultoolsMod.MOD_ID, Registries.MENU);

    public static final RegistrySupplier<MenuType<SpectralInfuserMenu>> SPECTRAL_INFUSER_MENU =
            MENUS.register("spectral_infuser_menu",
                    () -> MenuRegistry.ofExtended(SpectralInfuserMenu::new));

    public static void register() {
        MENUS.register();
    }
}
