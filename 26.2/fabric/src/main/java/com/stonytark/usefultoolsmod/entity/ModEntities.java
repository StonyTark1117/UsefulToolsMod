package com.stonytark.usefultoolsmod.entity;

import com.stonytark.usefultoolsmod.generated.GeneratedRegistrationIds;

import com.stonytark.usefultoolsmod.UsefultoolsMod;
import com.stonytark.usefultoolsmod.entity.custom.GhostEntity;
import com.stonytark.usefultoolsmod.entity.custom.GrenadeEntity;
import com.stonytark.usefultoolsmod.entity.custom.WraithEntity;
import com.stonytark.usefultoolsmod.entity.custom.StickyDynamiteEntity;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class ModEntities {
    private static ResourceKey<EntityType<?>> key(String name) {
        return ResourceKey.create(Registries.ENTITY_TYPE,
                Identifier.fromNamespaceAndPath(UsefultoolsMod.MOD_ID, name));
    }

    public static final EntityType<GrenadeEntity> GRENADE = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            Identifier.fromNamespaceAndPath(UsefultoolsMod.MOD_ID, GeneratedRegistrationIds.Entities.GRENADE),
            EntityType.Builder.<GrenadeEntity>of(GrenadeEntity::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .clientTrackingRange(4)
                    .updateInterval(10)
                    .build(key(GeneratedRegistrationIds.Entities.GRENADE))
    );

    public static final EntityType<GhostEntity> GHOST = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            Identifier.fromNamespaceAndPath(UsefultoolsMod.MOD_ID, GeneratedRegistrationIds.Entities.GHOST),
            EntityType.Builder.<GhostEntity>of(GhostEntity::new, MobCategory.MONSTER)
                    .sized(1.5f, 1.5f)
                    .build(key(GeneratedRegistrationIds.Entities.GHOST))
    );
    public static final EntityType<WraithEntity> WRAITH=Registry.register(BuiltInRegistries.ENTITY_TYPE,
            Identifier.fromNamespaceAndPath(UsefultoolsMod.MOD_ID,GeneratedRegistrationIds.Entities.WRAITH),EntityType.Builder.<WraithEntity>of(WraithEntity::new,MobCategory.MONSTER)
                    .sized(1.2F,2.25F).clientTrackingRange(8).build(key(GeneratedRegistrationIds.Entities.WRAITH)));
    public static final EntityType<StickyDynamiteEntity> STICKY_DYNAMITE=Registry.register(BuiltInRegistries.ENTITY_TYPE,
            Identifier.fromNamespaceAndPath(UsefultoolsMod.MOD_ID,GeneratedRegistrationIds.Entities.STICKY_DYNAMITE),EntityType.Builder.<StickyDynamiteEntity>of(StickyDynamiteEntity::new,MobCategory.MISC)
                    .sized(.25F,.25F).clientTrackingRange(4).updateInterval(10).build(key(GeneratedRegistrationIds.Entities.STICKY_DYNAMITE)));

    public static void register() {
        // no-op; touching the class triggers static init
    }
}
