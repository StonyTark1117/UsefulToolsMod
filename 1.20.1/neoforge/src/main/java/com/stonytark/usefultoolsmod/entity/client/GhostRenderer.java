package com.stonytark.usefultoolsmod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.stonytark.usefultoolsmod.UsefultoolsMod;
import com.stonytark.usefultoolsmod.entity.custom.GhostEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class GhostRenderer extends MobRenderer<GhostEntity, GhostModel<GhostEntity>> {

    public GhostRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new GhostModel<>(pContext.bakeLayer(GhostModel.LAYER_LOCATION)), 0.45f);
    }

    @Nullable
    @Override
    protected RenderType getRenderType(GhostEntity entity, boolean visible, boolean translucent, boolean glowing) {
        return RenderType.entityTranslucentEmissive(getTextureLocation(entity));
    }

    @Override
    public ResourceLocation getTextureLocation(GhostEntity pEntity) {
        return new ResourceLocation(UsefultoolsMod.MOD_ID, "textures/entity/ghost/ghost_white.png");
    }

    @Override
    public void render(GhostEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        if(pEntity.isBaby()){
            pPoseStack.scale(0.5f, 0.5f, 0.5f);
        }
        else{
            pPoseStack.scale(1f, 1f, 1f);
        }

        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
    }
}
