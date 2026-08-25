package com.stonytark.usefultoolsmod.classic;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenMinable;

/** Version-native realization of the modern RGold placed features. */
public final class ClassicOreGeneration {
    public static final class Rule {
        public final int dimension;
        public final String oreId;
        public final Block replacement;
        public final int minY;
        public final int maxY;
        public final int veinSize;
        public final int attempts;

        Rule(int dimension, String oreId, Block replacement, int minY, int maxY,
             int veinSize, int attempts) {
            this.dimension = dimension;
            this.oreId = oreId;
            this.replacement = replacement;
            this.minY = minY;
            this.maxY = maxY;
            this.veinSize = veinSize;
            this.attempts = attempts;
        }
    }

    public static final Rule[] RULES = new Rule[] {
            new Rule(0, "rgold_deepslate_ore", Blocks.stone, 1, 17, 9, 12),
            new Rule(0, "rgoldore", Blocks.stone, 17, 80, 9, 12),
            new Rule(-1, "rgold_nether_ore", Blocks.netherrack, 8, 120, 9, 12),
            new Rule(1, "rgold_end_ore", Blocks.end_stone, 8, 120, 9, 12)
    };

    private ClassicOreGeneration() {}

    public static void generate(World world, Random random, int chunkX, int chunkZ) {
        for (Rule rule : RULES) {
            if (rule.dimension != world.provider.dimensionId) continue;
            Block ore = ClassicGeneratedCatalog.BLOCKS.get(rule.oreId);
            if (ore == null) throw new IllegalStateException("Missing canonical block " + rule.oreId);
            WorldGenMinable generator = new WorldGenMinable(ore, rule.veinSize, rule.replacement);
            for (int attempt = 0; attempt < rule.attempts; attempt++) {
                int x = chunkX * 16 + random.nextInt(16);
                int y = rule.minY + random.nextInt(rule.maxY - rule.minY);
                int z = chunkZ * 16 + random.nextInt(16);
                generator.generate(world, random, x, y, z);
            }
        }
    }
}
