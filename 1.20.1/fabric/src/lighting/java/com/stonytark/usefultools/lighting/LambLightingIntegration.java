package com.stonytark.usefultools.lighting;
import dev.lambdaurora.lambdynlights.api.DynamicLightsInitializer;
import dev.lambdaurora.lambdynlights.api.DynamicLightsContext;
import dev.lambdaurora.lambdynlights.api.item.ItemLightSourceManager;
/** The companion owns these enhanced sources; suppress their parallel Lamb item processing. */
public final class LambLightingIntegration implements DynamicLightsInitializer {
    @SuppressWarnings("removal")
    @Override public void onInitializeDynamicLights() {}
    @Override public void onInitializeDynamicLights(DynamicLightsContext context) { register(context.itemLightSourceManager()); }
    private static void register(ItemLightSourceManager manager) {
        manager.onRegisterEvent().register(registration -> {
            registration.register(net.minecraft.registry.Registries.ITEM.get(new net.minecraft.util.Identifier("usefultoolsmod:ectoplasm_lantern")), 0);
            registration.register(net.minecraft.registry.Registries.ITEM.get(new net.minecraft.util.Identifier("usefultoolsmod:soul_lantern")), 0);
            registration.register(net.minecraft.registry.Registries.ITEM.get(new net.minecraft.util.Identifier("usefultoolsmod:hglow_block")), 0);
            registration.register(net.minecraft.registry.Registries.ITEM.get(new net.minecraft.util.Identifier("usefultoolsmod:hglow")), 0);
            registration.register(net.minecraft.registry.Registries.ITEM.get(new net.minecraft.util.Identifier("usefultoolsmod:hglowstone_axe")), 0);
            registration.register(net.minecraft.registry.Registries.ITEM.get(new net.minecraft.util.Identifier("usefultoolsmod:hglowstone_hoe")), 0);
            registration.register(net.minecraft.registry.Registries.ITEM.get(new net.minecraft.util.Identifier("usefultoolsmod:hglowstone_pickaxe")), 0);
            registration.register(net.minecraft.registry.Registries.ITEM.get(new net.minecraft.util.Identifier("usefultoolsmod:hglowstone_shovel")), 0);
            registration.register(net.minecraft.registry.Registries.ITEM.get(new net.minecraft.util.Identifier("usefultoolsmod:hglowstone_sword")), 0);
            registration.register(net.minecraft.registry.Registries.ITEM.get(new net.minecraft.util.Identifier("usefultoolsmod:hglow_helmet")), 0);
            registration.register(net.minecraft.registry.Registries.ITEM.get(new net.minecraft.util.Identifier("usefultoolsmod:hglow_chestplate")), 0);
            registration.register(net.minecraft.registry.Registries.ITEM.get(new net.minecraft.util.Identifier("usefultoolsmod:hglow_leggings")), 0);
            registration.register(net.minecraft.registry.Registries.ITEM.get(new net.minecraft.util.Identifier("usefultoolsmod:hglow_boots")), 0);
        });
    }
}
