package com.stonytark.usefultoolsmod.worldgen;

import com.stonytark.usefultoolsmod.UsefultoolsMod;
import com.stonytark.usefultoolsmod.entity.ModEntities;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.List;

// In 1.20.2 NeoForge: ForgeBiomeModifiers → NeoForgeBiomeModifiers, ForgeRegistries.Keys.BIOME_MODIFIERS → NeoForgeRegistries.Keys.BIOME_MODIFIERS
public class ModBiomeModifiers {
    public static final ResourceKey<BiomeModifier> ADD_RGOLD_ORE = registerKey("add_rgold_ore");
    public static final ResourceKey<BiomeModifier> ADD_NETHER_RGOLD_ORE = registerKey("add_nether_rgold_ore");
    public static final ResourceKey<BiomeModifier> ADD_END_RGOLD_ORE = registerKey("add_end_rgold_ore");
    public static final ResourceKey<BiomeModifier> SPAWN_GHOST = registerKey("spawn_ghost");

    public static void bootstrap(BootstapContext<BiomeModifier> context) {
        var placedFeature = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

        context.register(ADD_RGOLD_ORE, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(placedFeature.getOrThrow(ModPlacedFeatures.RGOLD_ORE_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(ADD_NETHER_RGOLD_ORE, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_NETHER),
                HolderSet.direct(placedFeature.getOrThrow(ModPlacedFeatures.NETHER_RGOLD_ORE_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(ADD_END_RGOLD_ORE, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_END),
                HolderSet.direct(placedFeature.getOrThrow(ModPlacedFeatures.END_RGOLD_ORE_PLACED_KEY)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(SPAWN_GHOST, new BiomeModifiers.AddSpawnsBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(Biomes.BADLANDS), biomes.getOrThrow(Biomes.PLAINS), biomes.getOrThrow(Biomes.BAMBOO_JUNGLE), biomes.getOrThrow(Biomes.BASALT_DELTAS), biomes.getOrThrow(Biomes.BEACH), biomes.getOrThrow(Biomes.BIRCH_FOREST), biomes.getOrThrow(Biomes.CHERRY_GROVE), biomes.getOrThrow(Biomes.CRIMSON_FOREST), biomes.getOrThrow(Biomes.DARK_FOREST), biomes.getOrThrow(Biomes.DEEP_DARK), biomes.getOrThrow(Biomes.DESERT), biomes.getOrThrow(Biomes.DRIPSTONE_CAVES), biomes.getOrThrow(Biomes.END_BARRENS), biomes.getOrThrow(Biomes.END_HIGHLANDS), biomes.getOrThrow(Biomes.END_MIDLANDS), biomes.getOrThrow(Biomes.ERODED_BADLANDS), biomes.getOrThrow(Biomes.FLOWER_FOREST), biomes.getOrThrow(Biomes.FOREST), biomes.getOrThrow(Biomes.FROZEN_PEAKS), biomes.getOrThrow(Biomes.GROVE), biomes.getOrThrow(Biomes.ICE_SPIKES), biomes.getOrThrow(Biomes.JAGGED_PEAKS), biomes.getOrThrow(Biomes.JUNGLE), biomes.getOrThrow(Biomes.LUSH_CAVES), biomes.getOrThrow(Biomes.MANGROVE_SWAMP), biomes.getOrThrow(Biomes.MEADOW), biomes.getOrThrow(Biomes.MUSHROOM_FIELDS), biomes.getOrThrow(Biomes.NETHER_WASTES), biomes.getOrThrow(Biomes.OLD_GROWTH_BIRCH_FOREST), biomes.getOrThrow(Biomes.OLD_GROWTH_PINE_TAIGA), biomes.getOrThrow(Biomes.OLD_GROWTH_SPRUCE_TAIGA), biomes.getOrThrow(Biomes.SAVANNA), biomes.getOrThrow(Biomes.SAVANNA_PLATEAU), biomes.getOrThrow(Biomes.SMALL_END_ISLANDS), biomes.getOrThrow(Biomes.SNOWY_BEACH), biomes.getOrThrow(Biomes.SNOWY_PLAINS), biomes.getOrThrow(Biomes.SNOWY_SLOPES), biomes.getOrThrow(Biomes.SNOWY_TAIGA), biomes.getOrThrow(Biomes.SOUL_SAND_VALLEY), biomes.getOrThrow(Biomes.SPARSE_JUNGLE), biomes.getOrThrow(Biomes.STONY_PEAKS), biomes.getOrThrow(Biomes.STONY_SHORE), biomes.getOrThrow(Biomes.SUNFLOWER_PLAINS), biomes.getOrThrow(Biomes.SWAMP), biomes.getOrThrow(Biomes.TAIGA), biomes.getOrThrow(Biomes.THE_END), biomes.getOrThrow(Biomes.WARPED_FOREST), biomes.getOrThrow(Biomes.WINDSWEPT_FOREST), biomes.getOrThrow(Biomes.WINDSWEPT_GRAVELLY_HILLS), biomes.getOrThrow(Biomes.WINDSWEPT_HILLS), biomes.getOrThrow(Biomes.WINDSWEPT_SAVANNA), biomes.getOrThrow(Biomes.WOODED_BADLANDS)),
                List.of(new MobSpawnSettings.SpawnerData(ModEntities.GHOST.get(), 10, 1, 1))));
    }

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(UsefultoolsMod.MOD_ID, name));
    }
}
