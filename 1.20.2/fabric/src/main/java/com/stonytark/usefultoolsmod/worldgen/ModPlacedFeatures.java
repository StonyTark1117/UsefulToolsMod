package com.stonytark.usefultoolsmod.worldgen;

import com.stonytark.usefultoolsmod.UsefultoolsMod;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.PlacedFeature;

public class ModPlacedFeatures {
    public static final RegistryKey<PlacedFeature> RGOLD_ORE_PLACED_KEY = registerKey("rgold_ore_placed");
    public static final RegistryKey<PlacedFeature> NETHER_RGOLD_ORE_PLACED_KEY = registerKey("nether_rgold_ore_placed");
    public static final RegistryKey<PlacedFeature> END_RGOLD_ORE_PLACED_KEY = registerKey("end_rgold_ore_placed");

    public static void bootstrap() {
        // In Fabric 1.20.1, worldgen is handled via data files
    }

    private static RegistryKey<PlacedFeature> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier(UsefultoolsMod.MOD_ID, name));
    }
}
