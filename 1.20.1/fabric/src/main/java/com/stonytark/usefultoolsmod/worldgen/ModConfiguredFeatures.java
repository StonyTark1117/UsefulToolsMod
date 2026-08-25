package com.stonytark.usefultoolsmod.worldgen;

import com.stonytark.usefultoolsmod.UsefultoolsMod;
import com.stonytark.usefultoolsmod.block.ModBlocks;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.util.Identifier;
import net.minecraft.block.Blocks;

import java.util.List;

public class ModConfiguredFeatures {
    public static final RegistryKey<ConfiguredFeature<?, ?>> OVERWORLD_RGOLD_ORE_KEY = registerKey("rgold_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> NETHER_RGOLD_ORE_KEY = registerKey("nether_rgold_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> END_RGOLD_ORE_KEY = registerKey("end_rgold_ore");

    public static void bootstrap() {
        // In Fabric 1.20.1, worldgen is handled via data files, not bootstrap
    }

    public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, new Identifier(UsefultoolsMod.MOD_ID, name));
    }
}
