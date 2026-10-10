package com.stonytark.usefultools.lighting.classic;

import java.lang.reflect.Method;
import net.minecraft.client.Minecraft;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/** Exercises the installed native meshing cache; absent backends add no assertion. */
public final class ClassicBeddiumLightingQa {
    private ClassicBeddiumLightingQa() {}

    public static void sample(Minecraft mc, int x, int y, int z, int stage) throws Exception {
        Class<?> cacheType;
        try {
            cacheType = Class.forName("com.ventooth.beddium.api.cache.SimpleChunkCache");
        } catch (ClassNotFoundException absent) {
            return;
        }
        Class<?> rendererType = Class.forName("com.ventooth.beddium.modules.TerrainRendering.CeleritasWorldRenderer");
        Object renderer = rendererType.getMethod("instanceNullable").invoke(null);
        if (renderer == null) throw new AssertionError("Beddium terrain renderer is not active");
        Object cache = cacheType.getConstructor(World.class, int.class, int.class, int.class,
                int.class, int.class, int.class).newInstance(mc.theWorld,
                        x - 1, y - 1, z - 1, x + 1, y + 1, z + 1);
        Method start = cacheType.getMethod("renderStart"), finish = cacheType.getMethod("renderFinish");
        start.invoke(cache);
        try {
            IBlockAccess access = (IBlockAccess) cache;
            int vanilla = access.getLightBrightnessForSkyBlocks(x, y, z, 0);
            int sampled = access.getBlock(x, y, z).getMixedBrightnessForBlock(access, x, y, z);
            int required = (int) (ClassicClientLighting.FIELD.light(x + .5, y + .5, z + .5) * 16);
            if ((sampled & 255) < required) throw new AssertionError("Beddium meshing cache omitted visual light");
            if ((sampled & ~255) != (vanilla & ~255)) throw new AssertionError("Beddium sky/packed metadata changed");
            if (vanilla != access.getLightBrightnessForSkyBlocks(x, y, z, 0))
                throw new AssertionError("Beddium cached vanilla light was mutated");
            System.out.println("USEFUL_TOOLS_LIGHTING_QA_BACKEND_PASS beddium_cache stage=" + stage
                    + " packed=" + sampled + " required=" + required + " vanilla_cache_unchanged=true");
        } finally {
            finish.invoke(cache);
        }
    }
}
