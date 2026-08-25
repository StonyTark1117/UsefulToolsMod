package com.stonytark.usefultoolsmod.worldgen;

import com.stonytark.usefultoolsmod.UsefultoolsMod;
import com.stonytark.usefultoolsmod.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public final class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_RGOLD_ORE = key("rgold_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> NETHER_RGOLD_ORE = key("nether_rgold_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> END_RGOLD_ORE = key("end_rgold_ore");

    private ModConfiguredFeatures() {}

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        List<OreConfiguration.TargetBlockState> overworld = List.of(
                OreConfiguration.target(new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES),
                        ModBlocks.RGOLDORE.get().defaultBlockState()),
                OreConfiguration.target(new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES),
                        ModBlocks.RGOLD_DEEPSLATE_ORE.get().defaultBlockState()));
        register(context, OVERWORLD_RGOLD_ORE, Feature.ORE, new OreConfiguration(overworld, 9));
        register(context, NETHER_RGOLD_ORE, Feature.ORE,
                new OreConfiguration(new BlockMatchTest(Blocks.NETHERRACK),
                        ModBlocks.RGOLD_NETHER_ORE.get().defaultBlockState(), 9));
        register(context, END_RGOLD_ORE, Feature.ORE,
                new OreConfiguration(new BlockMatchTest(Blocks.END_STONE),
                        ModBlocks.RGOLD_END_ORE.get().defaultBlockState(), 9));
    }

    private static ResourceKey<ConfiguredFeature<?, ?>> key(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE,
                ResourceLocation.fromNamespaceAndPath(UsefultoolsMod.MOD_ID, name));
    }

    private static <C extends FeatureConfiguration, F extends Feature<C>> void register(
            BootstrapContext<ConfiguredFeature<?, ?>> context,
            ResourceKey<ConfiguredFeature<?, ?>> key, F feature, C configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
