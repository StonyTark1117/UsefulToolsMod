package com.stonytark.usefultoolsmod.entity.client;

import com.stonytark.usefultoolsmod.entity.custom.StickyDynamiteEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.util.math.Vec3d;

/** Keeps attached item geometry on the impacted face without moving the explosion. */
public final class StickyDynamiteRenderer extends FlyingItemEntityRenderer<StickyDynamiteEntity> {
    public StickyDynamiteRenderer(EntityRendererFactory.Context context) { super(context); }
    @Override public Vec3d getPositionOffset(StickyDynamiteEntity entity, float tickDelta) {
        return super.getPositionOffset(entity, tickDelta).add(entity.getModelOffset());
    }
    @Override protected int getBlockLight(StickyDynamiteEntity entity, net.minecraft.util.math.BlockPos pos) {
        return super.getBlockLight(entity, net.minecraft.util.math.BlockPos.ofFloored(entity.getPos().add(entity.getModelOffset())));
    }
    @Override protected int getSkyLight(StickyDynamiteEntity entity, net.minecraft.util.math.BlockPos pos) {
        return super.getSkyLight(entity, net.minecraft.util.math.BlockPos.ofFloored(entity.getPos().add(entity.getModelOffset())));
    }
}
