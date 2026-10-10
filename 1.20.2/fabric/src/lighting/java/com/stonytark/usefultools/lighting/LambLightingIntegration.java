package com.stonytark.usefultools.lighting;
import dev.lambdaurora.lambdynlights.api.DynamicLightsInitializer;
import dev.lambdaurora.lambdynlights.api.item.ItemLightSource;
import dev.lambdaurora.lambdynlights.api.item.ItemLightSources;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
public final class LambLightingIntegration implements DynamicLightsInitializer {
    @Override public void onInitializeDynamicLights() {
        ItemLightSources.registerItemLightSource(new ItemLightSource.StaticItemLightSource(new Identifier("usefultools_lighting", "ectoplasm_lantern"), Registries.ITEM.get(new Identifier("usefultoolsmod", "ectoplasm_lantern")), 0, false));
        ItemLightSources.registerItemLightSource(new ItemLightSource.StaticItemLightSource(new Identifier("usefultools_lighting", "soul_lantern"), Registries.ITEM.get(new Identifier("usefultoolsmod", "soul_lantern")), 0, false));
        ItemLightSources.registerItemLightSource(new ItemLightSource.StaticItemLightSource(new Identifier("usefultools_lighting", "hglow_block"), Registries.ITEM.get(new Identifier("usefultoolsmod", "hglow_block")), 0, false));
        ItemLightSources.registerItemLightSource(new ItemLightSource.StaticItemLightSource(new Identifier("usefultools_lighting", "hglow"), Registries.ITEM.get(new Identifier("usefultoolsmod", "hglow")), 0, false));
        ItemLightSources.registerItemLightSource(new ItemLightSource.StaticItemLightSource(new Identifier("usefultools_lighting", "hglowstone_axe"), Registries.ITEM.get(new Identifier("usefultoolsmod", "hglowstone_axe")), 0, false));
        ItemLightSources.registerItemLightSource(new ItemLightSource.StaticItemLightSource(new Identifier("usefultools_lighting", "hglowstone_hoe"), Registries.ITEM.get(new Identifier("usefultoolsmod", "hglowstone_hoe")), 0, false));
        ItemLightSources.registerItemLightSource(new ItemLightSource.StaticItemLightSource(new Identifier("usefultools_lighting", "hglowstone_pickaxe"), Registries.ITEM.get(new Identifier("usefultoolsmod", "hglowstone_pickaxe")), 0, false));
        ItemLightSources.registerItemLightSource(new ItemLightSource.StaticItemLightSource(new Identifier("usefultools_lighting", "hglowstone_shovel"), Registries.ITEM.get(new Identifier("usefultoolsmod", "hglowstone_shovel")), 0, false));
        ItemLightSources.registerItemLightSource(new ItemLightSource.StaticItemLightSource(new Identifier("usefultools_lighting", "hglowstone_sword"), Registries.ITEM.get(new Identifier("usefultoolsmod", "hglowstone_sword")), 0, false));
        ItemLightSources.registerItemLightSource(new ItemLightSource.StaticItemLightSource(new Identifier("usefultools_lighting", "hglow_helmet"), Registries.ITEM.get(new Identifier("usefultoolsmod", "hglow_helmet")), 0, false));
        ItemLightSources.registerItemLightSource(new ItemLightSource.StaticItemLightSource(new Identifier("usefultools_lighting", "hglow_chestplate"), Registries.ITEM.get(new Identifier("usefultoolsmod", "hglow_chestplate")), 0, false));
        ItemLightSources.registerItemLightSource(new ItemLightSource.StaticItemLightSource(new Identifier("usefultools_lighting", "hglow_leggings"), Registries.ITEM.get(new Identifier("usefultoolsmod", "hglow_leggings")), 0, false));
        ItemLightSources.registerItemLightSource(new ItemLightSource.StaticItemLightSource(new Identifier("usefultools_lighting", "hglow_boots"), Registries.ITEM.get(new Identifier("usefultoolsmod", "hglow_boots")), 0, false));
    }
}
