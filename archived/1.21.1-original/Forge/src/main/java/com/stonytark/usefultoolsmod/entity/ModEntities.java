package com.stonytark.usefultoolsmod.entity;

import com.stonytark.usefultoolsmod.UsefultoolsMod;
import com.stonytark.usefultoolsmod.entity.custom.GrenadeEntity;
import com.stonytark.usefultoolsmod.entity.custom.GhostEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.eventbus.api.IEventBus;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, UsefultoolsMod.MOD_ID);

    public static final RegistryObject<EntityType<GrenadeEntity>> GRENADE =
            ENTITY_TYPES.register("grenade",
                    () -> EntityType.Builder.<GrenadeEntity>of(GrenadeEntity::new, MobCategory.MISC)
                            .sized(0.25F, 0.25F)
                            .clientTrackingRange(4)
                            .updateInterval(10)
                            .build("grenade") // <-- just use the name as a string
            );

    public static final RegistryObject<EntityType> GHOST =
            ENTITY_TYPES.register("ghost", () -> EntityType.Builder.of(GhostEntity::new, MobCategory.MONSTER)
                    .sized(1.5f, 1.5f).build("ghost"));

    public static void register(IEventBus modEventBus) {
        ENTITY_TYPES.register(modEventBus);
    }
}