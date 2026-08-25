package com.stonytark.usefultoolsmod.entity;

import com.stonytark.usefultoolsmod.generated.GeneratedRegistrationIds;

import com.stonytark.usefultoolsmod.UsefultoolsMod;
import com.stonytark.usefultoolsmod.entity.custom.GhostEntity;
import com.stonytark.usefultoolsmod.entity.custom.GrenadeEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static EntityType<GrenadeEntity> GRENADE;
    public static EntityType<GhostEntity> GHOST;

    public static void register() {
        GRENADE = Registry.register(Registries.ENTITY_TYPE,
                new Identifier(UsefultoolsMod.MOD_ID, GeneratedRegistrationIds.Entities.GRENADE),
                EntityType.Builder.<GrenadeEntity>create(GrenadeEntity::new, SpawnGroup.MISC)
                        .setDimensions(0.25F, 0.25F)
                        .maxTrackingRange(4)
                        .trackingTickInterval(10)
                        .build("usefultoolsmod"));

        GHOST = Registry.register(Registries.ENTITY_TYPE,
                new Identifier(UsefultoolsMod.MOD_ID, GeneratedRegistrationIds.Entities.GHOST),
                EntityType.Builder.<GhostEntity>create(GhostEntity::new, SpawnGroup.MONSTER)
                        .setDimensions(1.5f, 1.5f)
                        .maxTrackingRange(8)
                        .trackingTickInterval(3)
                        .build("usefultoolsmod"));
    }
}
