package com.stonytark.usefultoolsmod.entity.client;
import com.stonytark.usefultoolsmod.UsefultoolsMod;import com.stonytark.usefultoolsmod.entity.custom.WraithEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;import net.minecraft.resources.Identifier;
import net.minecraft.client.renderer.rendertype.RenderType;import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.core.BlockPos;
public class WraithRenderer extends MobRenderer<WraithEntity,LivingEntityRenderState,WraithModel>{
    private static final Identifier TEXTURE=Identifier.fromNamespaceAndPath(UsefultoolsMod.MOD_ID,"textures/entity/ghost/wraith.png");
    private static final Identifier SHADER_TEXTURE=Identifier.fromNamespaceAndPath(UsefultoolsMod.MOD_ID,"textures/entity/ghost/wraith_shader.png");
    public WraithRenderer(EntityRendererProvider.Context context){super(context,new WraithModel(context.bakeLayer(WraithModel.LAYER_LOCATION)),.55F);}
    @Override public Identifier getTextureLocation(LivingEntityRenderState state){return SpectralShaderSupport.isShaderPackInUse() ? SHADER_TEXTURE : TEXTURE;}
    @Override protected RenderType getRenderType(LivingEntityRenderState state,boolean visible,boolean translucent,boolean glowing){return SpectralShaderSupport.shouldUseVanillaEmissive(visible, translucent, glowing) ? RenderTypes.entityTranslucentEmissive(TEXTURE) : super.getRenderType(state, visible, translucent, glowing);}
    @Override
    protected int getBlockLightLevel(WraithEntity entity, BlockPos pos) {
        return SpectralShaderSupport.wraithBlockLightLevel(super.getBlockLightLevel(entity, pos));
    }
    @Override public LivingEntityRenderState createRenderState(){return new LivingEntityRenderState();}}
