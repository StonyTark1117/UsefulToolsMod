package com.stonytark.usefultoolsmod.entity;

import com.stonytark.usefultoolsmod.generated.GeneratedRegistrationIds;

import com.stonytark.usefultoolsmod.UsefultoolsMod;
import com.stonytark.usefultoolsmod.entity.custom.GrenadeEntity;
import com.stonytark.usefultoolsmod.entity.custom.GhostEntity;
import com.stonytark.usefultoolsmod.entity.custom.WraithEntity;
import com.stonytark.usefultoolsmod.entity.custom.StickyDynamiteEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, UsefultoolsMod.MOD_ID);

    public static final DeferredHolder<EntityType<?>, EntityType<GrenadeEntity>> GRENADE =
            ENTITY_TYPES.register(GeneratedRegistrationIds.Entities.GRENADE,
                    () -> EntityType.Builder.<GrenadeEntity>of(GrenadeEntity::new, MobCategory.MISC)
                            .sized(0.25F, 0.25F)
                            .clientTrackingRange(4)
                            .updateInterval(10)
                            .build(GeneratedRegistrationIds.Entities.GRENADE)
            );

    public static final DeferredHolder<EntityType<?>, EntityType<GhostEntity>> GHOST =
            ENTITY_TYPES.register(GeneratedRegistrationIds.Entities.GHOST, () -> EntityType.Builder.<GhostEntity>of(GhostEntity::new, MobCategory.MONSTER)
                    .sized(1.5f, 1.5f).build(GeneratedRegistrationIds.Entities.GHOST));

    public static final DeferredHolder<EntityType<?>, EntityType<WraithEntity>> WRAITH =
            ENTITY_TYPES.register(GeneratedRegistrationIds.Entities.WRAITH, () -> EntityType.Builder.<WraithEntity>of(WraithEntity::new, MobCategory.MONSTER)
                    .sized(1.2F, 2.25F).clientTrackingRange(8).build(GeneratedRegistrationIds.Entities.WRAITH));
    public static final DeferredHolder<EntityType<?>, EntityType<StickyDynamiteEntity>> STICKY_DYNAMITE =
            ENTITY_TYPES.register(GeneratedRegistrationIds.Entities.STICKY_DYNAMITE, () -> EntityType.Builder.<StickyDynamiteEntity>of(
                    StickyDynamiteEntity::new, MobCategory.MISC).sized(0.25F, 0.25F)
                    .clientTrackingRange(4).updateInterval(10).build(GeneratedRegistrationIds.Entities.STICKY_DYNAMITE));

    public static void register(IEventBus modEventBus) {
        ENTITY_TYPES.register(modEventBus);
    }
}
