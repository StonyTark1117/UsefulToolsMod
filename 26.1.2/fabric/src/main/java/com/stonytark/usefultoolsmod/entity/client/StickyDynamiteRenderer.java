package com.stonytark.usefultoolsmod.entity.client;

import com.stonytark.usefultoolsmod.entity.custom.StickyDynamiteEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.entity.state.ThrownItemRenderState;
import net.minecraft.world.phys.Vec3;

/** Keeps attached item geometry on the impacted face without moving the explosion. */
public final class StickyDynamiteRenderer extends ThrownItemRenderer<StickyDynamiteEntity> {
    public StickyDynamiteRenderer(EntityRendererProvider.Context context) { super(context); }
    public static final class State extends ThrownItemRenderState {
        Vec3 modelOffset = Vec3.ZERO;
    }
    @Override public State createRenderState() { return new State(); }
    @Override public void extractRenderState(StickyDynamiteEntity entity, ThrownItemRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        ((State) state).modelOffset = entity.getModelOffset();
    }
    @Override public Vec3 getRenderOffset(ThrownItemRenderState state) {
        return super.getRenderOffset(state).add(((State) state).modelOffset);
    }
    @Override protected int getBlockLightLevel(StickyDynamiteEntity entity, net.minecraft.core.BlockPos pos) {
        return super.getBlockLightLevel(entity, net.minecraft.core.BlockPos.containing(entity.position().add(entity.getModelOffset())));
    }
    @Override protected int getSkyLightLevel(StickyDynamiteEntity entity, net.minecraft.core.BlockPos pos) {
        return super.getSkyLightLevel(entity, net.minecraft.core.BlockPos.containing(entity.position().add(entity.getModelOffset())));
    }
}
