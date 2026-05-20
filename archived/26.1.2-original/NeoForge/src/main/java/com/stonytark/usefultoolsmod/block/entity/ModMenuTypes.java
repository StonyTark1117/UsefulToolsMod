package com.stonytark.usefultoolsmod.block.entity;


import net.minecraft.core.registries.BuiltInRegistries;
import com.stonytark.usefultoolsmod.UsefultoolsMod;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(BuiltInRegistries.MENU, UsefultoolsMod.MOD_ID);

    public static final DeferredHolder<MenuType<?>, MenuType<SpectralInfuserMenu>> SPECTRAL_INFUSER_MENU =
            MENUS.register("spectral_infuser_menu",
                    () -> IMenuTypeExtension.create(SpectralInfuserMenu::new));

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
