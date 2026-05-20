package com.stonytark.usefultoolsmod.entity.client;

import com.stonytark.usefultoolsmod.UsefultoolsMod;
import com.stonytark.usefultoolsmod.entity.custom.GhostEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class GhostRenderer extends MobEntityRenderer<GhostEntity, GhostModel<GhostEntity>> {

    public GhostRenderer(EntityRendererFactory.Context context) {
        super(context, new GhostModel<>(context.getPart(GhostModel.LAYER_LOCATION)), 0.85f);
    }

    @Override
    public Identifier getTexture(GhostEntity entity) {
        return Identifier.of(UsefultoolsMod.MOD_ID, "textures/entity/ghost/ghost_white.png");
    }

    @Override
    public void render(GhostEntity entity, float yaw, float tickDelta, MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers, int light) {
        if (entity.isBaby()) {
            matrices.scale(0.5f, 0.5f, 0.5f);
        }
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }
}
