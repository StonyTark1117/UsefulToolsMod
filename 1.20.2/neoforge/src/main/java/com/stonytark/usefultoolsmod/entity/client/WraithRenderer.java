package com.stonytark.usefultoolsmod.entity.client;

import com.stonytark.usefultoolsmod.UsefultoolsMod;
import com.stonytark.usefultoolsmod.entity.custom.WraithEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class WraithRenderer extends MobRenderer<WraithEntity, WraithModel> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(
            UsefultoolsMod.MOD_ID, "textures/entity/ghost/wraith.png");
    public WraithRenderer(EntityRendererProvider.Context context) {
        super(context, new WraithModel(context.bakeLayer(WraithModel.LAYER_LOCATION)), 0.55F);
    }
    @Override public ResourceLocation getTextureLocation(WraithEntity entity) { return TEXTURE; }
    @Nullable @Override protected RenderType getRenderType(WraithEntity entity, boolean visible, boolean translucent, boolean glowing) {
        return RenderType.entityTranslucentEmissive(TEXTURE);
    }
}
