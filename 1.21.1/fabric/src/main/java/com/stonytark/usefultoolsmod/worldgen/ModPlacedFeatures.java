package com.stonytark.usefultoolsmod.worldgen;

import com.stonytark.usefultoolsmod.UsefultoolsMod;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.placementmodifier.HeightRangePlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;

import java.util.List;

public class ModPlacedFeatures {
    public static final RegistryKey<PlacedFeature> RGOLD_ORE_PLACED_KEY = registerKey("rgold_ore_placed");
    public static final RegistryKey<PlacedFeature> NETHER_RGOLD_ORE_PLACED_KEY = registerKey("nether_rgold_ore_placed");
    public static final RegistryKey<PlacedFeature> END_RGOLD_ORE_PLACED_KEY = registerKey("end_rgold_ore_placed");

    public static void bootstrap(Registerable<PlacedFeature> context) {
        var configuredFeatures = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);

        register(context, RGOLD_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.OVERWORLD_RGOLD_ORE_KEY),
                ModOrePlacement.commonOrePlacement(12,
                        HeightRangePlacementModifier.uniform(YOffset.aboveBottom(-64), YOffset.aboveBottom(80))));
        register(context, NETHER_RGOLD_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.NETHER_RGOLD_ORE_KEY),
                ModOrePlacement.commonOrePlacement(12,
                        HeightRangePlacementModifier.uniform(YOffset.aboveBottom(-64), YOffset.aboveBottom(80))));
        register(context, END_RGOLD_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.END_RGOLD_ORE_KEY),
                ModOrePlacement.commonOrePlacement(12,
                        HeightRangePlacementModifier.uniform(YOffset.aboveBottom(-64), YOffset.aboveBottom(80))));
    }

    private static RegistryKey<PlacedFeature> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(UsefultoolsMod.MOD_ID, name));
    }

    private static void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key,
                                RegistryEntry.Reference<ConfiguredFeature<?, ?>> configuration,
                                List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
