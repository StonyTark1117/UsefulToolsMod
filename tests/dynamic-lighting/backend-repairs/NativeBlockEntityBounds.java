package com.stonytark.lightingqa.backend;

import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;

/** Private backend repair for the exact NeoForge 20.2.93 renderer extension. */
public final class NativeBlockEntityBounds {
    private NativeBlockEntityBounds() { }
    public static AABB get(BlockEntityRenderDispatcher dispatcher, BlockEntity entity) {
        BlockEntityRenderer<BlockEntity> renderer = dispatcher.getRenderer(entity);
        return renderer == null ? new AABB(entity.getBlockPos()) : renderer.getRenderBoundingBox(entity);
    }
}
