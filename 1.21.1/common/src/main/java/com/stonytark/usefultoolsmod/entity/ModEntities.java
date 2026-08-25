package com.stonytark.usefultoolsmod.entity;

import com.stonytark.usefultoolsmod.generated.GeneratedRegistrationIds;

import com.stonytark.usefultoolsmod.UsefultoolsMod;
import com.stonytark.usefultoolsmod.entity.custom.GhostEntity;
import com.stonytark.usefultoolsmod.entity.custom.GrenadeEntity;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(UsefultoolsMod.MOD_ID, Registries.ENTITY_TYPE);

    public static final RegistrySupplier<EntityType<GrenadeEntity>> GRENADE =
            ENTITY_TYPES.register(GeneratedRegistrationIds.Entities.GRENADE,
                    () -> EntityType.Builder.<GrenadeEntity>of(GrenadeEntity::new, MobCategory.MISC)
                            .sized(0.25F, 0.25F)
                            .clientTrackingRange(4)
                            .updateInterval(10)
                            .build(GeneratedRegistrationIds.Entities.GRENADE));

    public static final RegistrySupplier<EntityType<GhostEntity>> GHOST =
            ENTITY_TYPES.register(GeneratedRegistrationIds.Entities.GHOST,
                    () -> EntityType.Builder.<GhostEntity>of(GhostEntity::new, MobCategory.MONSTER)
                            .sized(1.5f, 1.5f)
                            .build(GeneratedRegistrationIds.Entities.GHOST));

    public static void register() {
        ENTITY_TYPES.register();
    }
}
