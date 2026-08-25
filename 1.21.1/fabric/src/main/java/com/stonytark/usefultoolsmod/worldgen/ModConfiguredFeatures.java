package com.stonytark.usefultoolsmod.worldgen;

import com.stonytark.usefultoolsmod.UsefultoolsMod;
import com.stonytark.usefultoolsmod.block.ModBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.structure.rule.TagMatchRuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;

import java.util.List;

public class ModConfiguredFeatures {
    public static final RegistryKey<ConfiguredFeature<?, ?>> OVERWORLD_RGOLD_ORE_KEY = registerKey("rgold_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> NETHER_RGOLD_ORE_KEY = registerKey("nether_rgold_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> END_RGOLD_ORE_KEY = registerKey("end_rgold_ore");

    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> context) {
        RuleTest stoneReplaceables = new TagMatchRuleTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchRuleTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        RuleTest netherrackReplaceables = new BlockMatchRuleTest(Blocks.NETHERRACK);
        RuleTest endstoneReplaceables = new BlockMatchRuleTest(Blocks.END_STONE);

        List<OreFeatureConfig.Target> overworldRgoldOres = List.of(
                OreFeatureConfig.createTarget(stoneReplaceables, ModBlocks.RGOLDORE.getDefaultState()),
                OreFeatureConfig.createTarget(deepslateReplaceables, ModBlocks.RGOLD_DEEPSLATE_ORE.getDefaultState()));

        register(context, OVERWORLD_RGOLD_ORE_KEY, Feature.ORE, new OreFeatureConfig(overworldRgoldOres, 9));
        register(context, NETHER_RGOLD_ORE_KEY, Feature.ORE, new OreFeatureConfig(List.of(
                OreFeatureConfig.createTarget(netherrackReplaceables,
                        ModBlocks.RGOLD_NETHER_ORE.getDefaultState())), 9));
        register(context, END_RGOLD_ORE_KEY, Feature.ORE, new OreFeatureConfig(List.of(
                OreFeatureConfig.createTarget(endstoneReplaceables,
                        ModBlocks.RGOLD_END_ORE.getDefaultState())), 9));
    }

    public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Identifier.of(UsefultoolsMod.MOD_ID, name));
    }

    private static <FC extends net.minecraft.world.gen.feature.FeatureConfig, F extends Feature<FC>> void register(
            Registerable<ConfiguredFeature<?, ?>> context,
            RegistryKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
