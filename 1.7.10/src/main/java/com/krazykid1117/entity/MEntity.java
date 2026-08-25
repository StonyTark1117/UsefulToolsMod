/*
 * Decompiled with CFR 0.152.
 *
 * Could not load the following classes:
 *  cpw.mods.fml.common.registry.EntityRegistry
 *  net.minecraft.entity.EntityList
 *  net.minecraft.entity.EntityList$EntityEggInfo
 */
package com.krazykid1117.entity;

import com.krazykid1117.Main.Mainregistry;
import com.krazykid1117.entity.EntityGrenade;
import cpw.mods.fml.common.registry.EntityRegistry;
import com.stonytark.usefultoolsmod.classic.EntityGhost;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.BiomeGenBase;
import java.util.ArrayList;
import java.util.List;

public class MEntity {
    public static void mainRegistry() {
        // Projectiles need a mod entity registration so Forge can spawn them on
        // clients. The recovered v1.6 source left this method empty and its old
        // helper incorrectly treated the grenade as a globally registered
        // living mob with a spawn egg.
        EntityRegistry.registerModEntity(EntityGrenade.class, "Grenade", 0,
                Mainregistry.modInstance, 64, 10, true);
        EntityRegistry.registerModEntity(EntityGhost.class, "ghost", 1,
                Mainregistry.modInstance, 80, 3, true);
        List<BiomeGenBase> biomes = new ArrayList<BiomeGenBase>();
        for (BiomeGenBase biome : BiomeGenBase.getBiomeGenArray()) {
            if (biome != null) biomes.add(biome);
        }
        EntityRegistry.addSpawn(EntityGhost.class, 4, 1, 2, EnumCreatureType.creature,
                biomes.toArray(new BiomeGenBase[biomes.size()]));
    }
}
