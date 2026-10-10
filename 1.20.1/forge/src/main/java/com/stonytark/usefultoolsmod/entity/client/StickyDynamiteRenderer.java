package com.stonytark.usefultoolsmod.entity.client;

import com.stonytark.usefultoolsmod.entity.custom.StickyDynamiteEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.phys.Vec3;

/** Keeps attached item geometry on the impacted face without moving the explosion. */
public final class StickyDynamiteRenderer extends ThrownItemRenderer<StickyDynamiteEntity> {
    public StickyDynamiteRenderer(EntityRendererProvider.Context context) { super(context); }
    @Override public Vec3 getRenderOffset(StickyDynamiteEntity entity, float partialTick) {
        return super.getRenderOffset(entity, partialTick).add(entity.getModelOffset());
    }
    @Override protected int getBlockLightLevel(StickyDynamiteEntity entity, net.minecraft.core.BlockPos pos) {
        return super.getBlockLightLevel(entity, net.minecraft.core.BlockPos.containing(entity.position().add(entity.getModelOffset())));
    }
    @Override protected int getSkyLightLevel(StickyDynamiteEntity entity, net.minecraft.core.BlockPos pos) {
        return super.getSkyLightLevel(entity, net.minecraft.core.BlockPos.containing(entity.position().add(entity.getModelOffset())));
    }
}
