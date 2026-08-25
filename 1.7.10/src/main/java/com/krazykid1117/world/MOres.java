package com.krazykid1117.world;

import com.stonytark.usefultoolsmod.classic.ClassicOreGeneration;
import cpw.mods.fml.common.IWorldGenerator;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

/** Forge entry point for the canonical 2.3.0 ore-generation rules. */
public class MOres implements IWorldGenerator {
    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world,
                         IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
        ClassicOreGeneration.generate(world, random, chunkX, chunkZ);
    }
}
