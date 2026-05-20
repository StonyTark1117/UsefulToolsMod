package com.stonytark.usefultoolsmod.entity;

import com.stonytark.usefultoolsmod.entity.custom.GhostEntity;
import dev.architectury.registry.level.entity.EntityAttributeRegistry;

public final class ModEntityAttributes {
    private ModEntityAttributes() {}

    public static void register() {
        EntityAttributeRegistry.register(ModEntities.GHOST, GhostEntity::createAttributes);
    }
}
