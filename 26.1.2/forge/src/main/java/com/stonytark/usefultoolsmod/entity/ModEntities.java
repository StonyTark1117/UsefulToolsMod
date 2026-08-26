package com.stonytark.usefultoolsmod.entity;

import com.stonytark.usefultoolsmod.generated.GeneratedRegistrationIds;


import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import com.stonytark.usefultoolsmod.UsefultoolsMod;
import com.stonytark.usefultoolsmod.entity.custom.GrenadeEntity;
import com.stonytark.usefultoolsmod.entity.custom.GhostEntity;
import com.stonytark.usefultoolsmod.entity.custom.WraithEntity;
import com.stonytark.usefultoolsmod.entity.custom.StickyDynamiteEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.eventbus.api.bus.BusGroup;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, UsefultoolsMod.MOD_ID);

    private static ResourceKey<EntityType<?>> key(String name) {
        return ResourceKey.create(Registries.ENTITY_TYPE,
                Identifier.fromNamespaceAndPath(UsefultoolsMod.MOD_ID, name));
    }

    public static final RegistryObject<EntityType<GrenadeEntity>> GRENADE =
            ENTITY_TYPES.register(GeneratedRegistrationIds.Entities.GRENADE,
                    () -> EntityType.Builder.<GrenadeEntity>of(GrenadeEntity::new, MobCategory.MISC)
                            .sized(0.25F, 0.25F)
                            .clientTrackingRange(4)
                            .updateInterval(10)
                            .build(key(GeneratedRegistrationIds.Entities.GRENADE))
            );

    public static final RegistryObject<EntityType<GhostEntity>> GHOST =
            ENTITY_TYPES.register(GeneratedRegistrationIds.Entities.GHOST, () -> EntityType.Builder.<GhostEntity>of(GhostEntity::new, MobCategory.MONSTER)
                    .sized(1.5f, 1.5f).build(key(GeneratedRegistrationIds.Entities.GHOST)));
    public static final RegistryObject<EntityType<WraithEntity>> WRAITH =
            ENTITY_TYPES.register(GeneratedRegistrationIds.Entities.WRAITH, () -> EntityType.Builder.<WraithEntity>of(WraithEntity::new, MobCategory.MONSTER)
                    .sized(1.2F, 2.25F).clientTrackingRange(8).build(key(GeneratedRegistrationIds.Entities.WRAITH)));
    public static final RegistryObject<EntityType<StickyDynamiteEntity>> STICKY_DYNAMITE=ENTITY_TYPES.register(GeneratedRegistrationIds.Entities.STICKY_DYNAMITE,
            ()->EntityType.Builder.<StickyDynamiteEntity>of(StickyDynamiteEntity::new,MobCategory.MISC).sized(.25F,.25F)
                    .clientTrackingRange(4).updateInterval(10).build(key(GeneratedRegistrationIds.Entities.STICKY_DYNAMITE)));

    public static void register(BusGroup busGroup) {
        ENTITY_TYPES.register(busGroup);
    }
}
