package com.stonytark.usefultoolsmod.entity;


import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import com.stonytark.usefultoolsmod.UsefultoolsMod;
import com.stonytark.usefultoolsmod.entity.custom.GrenadeEntity;
import com.stonytark.usefultoolsmod.entity.custom.GhostEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.bus.api.IEventBus;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, UsefultoolsMod.MOD_ID);

    private static ResourceKey<EntityType<?>> key(String name) {
        return ResourceKey.create(Registries.ENTITY_TYPE,
                Identifier.fromNamespaceAndPath(UsefultoolsMod.MOD_ID, name));
    }

    public static final DeferredHolder<EntityType<?>, EntityType<GrenadeEntity>> GRENADE =
            ENTITY_TYPES.register("grenade",
                    () -> EntityType.Builder.<GrenadeEntity>of(GrenadeEntity::new, MobCategory.MISC)
                            .sized(0.25F, 0.25F)
                            .clientTrackingRange(4)
                            .updateInterval(10)
                            .build(key("grenade"))
            );

    public static final DeferredHolder<EntityType<?>, EntityType<GhostEntity>> GHOST =
            ENTITY_TYPES.register("ghost", () -> EntityType.Builder.of(GhostEntity::new, MobCategory.MONSTER)
                    .sized(1.5f, 1.5f).build(key("ghost")));

    public static void register(IEventBus modEventBus) {
        ENTITY_TYPES.register(modEventBus);
    }
}