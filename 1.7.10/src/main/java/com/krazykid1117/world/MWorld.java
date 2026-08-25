/*
 * Decompiled with CFR 0.152.
 *
 * Could not load the following classes:
 *  cpw.mods.fml.common.IWorldGenerator
 *  cpw.mods.fml.common.registry.GameRegistry
 */
package com.krazykid1117.world;

import com.krazykid1117.world.MOres;
import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.common.registry.GameRegistry;

public class MWorld {
    public static void MainRegistry() {
        MWorld.initialiseWorldGen();
    }

    public static void initialiseWorldGen() {
        MWorld.registerWorldGen(new MOres(), 1);
    }

    public static void registerWorldGen(IWorldGenerator worldgenclass, int weightedprobability) {
        GameRegistry.registerWorldGenerator((IWorldGenerator)worldgenclass, (int)weightedprobability);
    }
}
