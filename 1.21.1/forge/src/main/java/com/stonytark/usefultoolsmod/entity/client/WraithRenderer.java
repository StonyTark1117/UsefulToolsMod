package com.stonytark.usefultoolsmod.entity.client;

import com.stonytark.usefultoolsmod.UsefultoolsMod;
import com.stonytark.usefultoolsmod.entity.custom.WraithEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class WraithRenderer extends MobRenderer<WraithEntity, WraithModel> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(
            UsefultoolsMod.MOD_ID, "textures/entity/ghost/wraith.png");
    private static final ResourceLocation SHADER_TEXTURE = ResourceLocation.fromNamespaceAndPath(
            UsefultoolsMod.MOD_ID, "textures/entity/ghost/wraith_shader.png");
    public WraithRenderer(EntityRendererProvider.Context context) {
        super(context, new WraithModel(context.bakeLayer(WraithModel.LAYER_LOCATION)), 0.55F);
    }
    @Override public ResourceLocation getTextureLocation(WraithEntity entity) { return SpectralShaderSupport.isShaderPackInUse() ? SHADER_TEXTURE : TEXTURE; }
    @Nullable @Override protected RenderType getRenderType(WraithEntity entity, boolean visible, boolean translucent, boolean glowing) {
        return SpectralShaderSupport.shouldUseVanillaEmissive(visible, translucent, glowing) ? RenderType.entityTranslucentEmissive(TEXTURE) : super.getRenderType(entity, visible, translucent, glowing);
    }
    @Override
    protected int getBlockLightLevel(WraithEntity entity, BlockPos pos) {
        return SpectralShaderSupport.wraithBlockLightLevel(super.getBlockLightLevel(entity, pos));
    }
}
