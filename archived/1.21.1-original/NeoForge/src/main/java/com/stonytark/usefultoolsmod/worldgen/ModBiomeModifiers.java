package com.stonytark.usefultoolsmod.worldgen;


import com.stonytark.usefultoolsmod.UsefultoolsMod;
import com.stonytark.usefultoolsmod.entity.ModEntities;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.List;

public class ModBiomeModifiers {
    public static final ResourceKey<BiomeModifier> ADD_RGOLD_ORE = registerKey("add_rgold_ore");
    public static final ResourceKey<BiomeModifier> ADD_NETHER_RGOLD_ORE = registerKey("add_nether_rgold_ore");
    public static final ResourceKey<BiomeModifier> ADD_END_RGOLD_ORE = registerKey("add_end_rgold_ore");

    public static final ResourceKey<BiomeModifier> SPAWN_GHOST = registerKey("spawn_ghost");
    public static final ResourceKey<BiomeModifier> SPAWN_GHOST_NETHER = registerKey("spawn_ghost_nether");
    public static final ResourceKey<BiomeModifier> SPAWN_GHOST_END = registerKey("spawn_ghost_end");


    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
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

        // Ghost spawns in ALL biomes (overworld, nether, end) — spawn rules handle night propensity
        HolderSet.Named<Biome> overworldBiomes = biomes.getOrThrow(BiomeTags.IS_OVERWORLD);
        HolderSet.Named<Biome> netherBiomes = biomes.getOrThrow(BiomeTags.IS_NETHER);
        HolderSet.Named<Biome> endBiomes = biomes.getOrThrow(BiomeTags.IS_END);
        List<MobSpawnSettings.SpawnerData> ghostSpawn = List.of(
                new MobSpawnSettings.SpawnerData(ModEntities.GHOST.get(), 20, 1, 1));

        context.register(SPAWN_GHOST, new BiomeModifiers.AddSpawnsBiomeModifier(overworldBiomes, ghostSpawn));
        context.register(SPAWN_GHOST_NETHER, new BiomeModifiers.AddSpawnsBiomeModifier(netherBiomes, ghostSpawn));
        context.register(SPAWN_GHOST_END, new BiomeModifiers.AddSpawnsBiomeModifier(endBiomes, ghostSpawn));
    }

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ResourceLocation.fromNamespaceAndPath(UsefultoolsMod.MOD_ID, name));
    }
}
