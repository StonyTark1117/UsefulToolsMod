package com.stonytark.usefultoolsmod.worldgen;

import com.stonytark.usefultoolsmod.UsefultoolsMod;
import com.stonytark.usefultoolsmod.entity.ModEntities;
import dev.architectury.registry.level.biome.BiomeModifications;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

/**
 * Replaces the NeoForge-only ModBiomeModifiers + datapack JSON worldgen path
 * with Architectury's runtime BiomeModifications API. Same behaviour, no JSON.
 */
public final class ModBiomeModifications {
    private ModBiomeModifications() {}

    private static ResourceKey<PlacedFeature> placedFeatureKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE,
                ResourceLocation.fromNamespaceAndPath(UsefultoolsMod.MOD_ID, name));
    }

    public static void register() {
        ResourceKey<PlacedFeature> rgoldOre       = placedFeatureKey("rgold_ore_placed");
        ResourceKey<PlacedFeature> netherRgoldOre = placedFeatureKey("nether_rgold_ore_placed");
        ResourceKey<PlacedFeature> endRgoldOre    = placedFeatureKey("end_rgold_ore_placed");

        // === Ore injections ===
        BiomeModifications.addProperties(
                ctx -> ctx.hasTag(BiomeTags.IS_OVERWORLD),
                (ctx, mut) -> mut.getGenerationProperties().addFeature(
                        GenerationStep.Decoration.UNDERGROUND_ORES, rgoldOre));

        BiomeModifications.addProperties(
                ctx -> ctx.hasTag(BiomeTags.IS_NETHER),
                (ctx, mut) -> mut.getGenerationProperties().addFeature(
                        GenerationStep.Decoration.UNDERGROUND_ORES, netherRgoldOre));

        BiomeModifications.addProperties(
                ctx -> ctx.hasTag(BiomeTags.IS_END),
                (ctx, mut) -> mut.getGenerationProperties().addFeature(
                        GenerationStep.Decoration.UNDERGROUND_ORES, endRgoldOre));

        // === Ghost spawn (overworld + nether + end, weight 20, 1-1) ===
        BiomeModifications.addProperties(
                ctx -> ctx.hasTag(BiomeTags.IS_OVERWORLD)
                    || ctx.hasTag(BiomeTags.IS_NETHER)
                    || ctx.hasTag(BiomeTags.IS_END),
                (ctx, mut) -> mut.getSpawnProperties().addSpawn(
                        MobCategory.MONSTER,
                        new MobSpawnSettings.SpawnerData(ModEntities.GHOST.get(), 20, 1, 1)));
    }
}
