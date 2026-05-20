package com.stonytark.usefultoolsmod.entity;

import com.stonytark.usefultoolsmod.entity.custom.GhostEntity;
import dev.architectury.registry.level.entity.SpawnPlacementsRegistry;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.level.levelgen.Heightmap;

public final class ModSpawnPlacements {
    private ModSpawnPlacements() {}

    public static void register() {
        SpawnPlacementsRegistry.register(
                ModEntities.GHOST,
                SpawnPlacementTypes.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                GhostEntity::checkGhostSpawnRules
        );
    }
}
