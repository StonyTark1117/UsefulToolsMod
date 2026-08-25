package com.stonytark.usefultoolsmod.worldgen;

import com.stonytark.usefultoolsmod.UsefultoolsMod;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

import java.util.List;

public final class ModPlacedFeatures {
    public static final ResourceKey<PlacedFeature> OVERWORLD_RGOLD_ORE = key("rgold_ore_placed");
    public static final ResourceKey<PlacedFeature> NETHER_RGOLD_ORE = key("nether_rgold_ore_placed");
    public static final ResourceKey<PlacedFeature> END_RGOLD_ORE = key("end_rgold_ore_placed");

    private ModPlacedFeatures() {}

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        var configured = context.lookup(Registries.CONFIGURED_FEATURE);
        List<PlacementModifier> placement = ModOrePlacement.commonOrePlacement(12,
                HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(80)));
        register(context, OVERWORLD_RGOLD_ORE, configured.getOrThrow(ModConfiguredFeatures.OVERWORLD_RGOLD_ORE), placement);
        register(context, NETHER_RGOLD_ORE, configured.getOrThrow(ModConfiguredFeatures.NETHER_RGOLD_ORE), placement);
        register(context, END_RGOLD_ORE, configured.getOrThrow(ModConfiguredFeatures.END_RGOLD_ORE), placement);
    }

    private static ResourceKey<PlacedFeature> key(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE,
                ResourceLocation.fromNamespaceAndPath(UsefultoolsMod.MOD_ID, name));
    }

    private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key,
                                 Holder<ConfiguredFeature<?, ?>> feature, List<PlacementModifier> placement) {
        context.register(key, new PlacedFeature(feature, List.copyOf(placement)));
    }
}
