package com.stonytark.usefultoolsmod.entity.client;
import com.stonytark.usefultoolsmod.UsefultoolsMod;import com.stonytark.usefultoolsmod.entity.custom.WraithEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;import net.minecraft.resources.Identifier;
import net.minecraft.client.renderer.rendertype.RenderType;import net.minecraft.client.renderer.rendertype.RenderTypes;
public class WraithRenderer extends MobRenderer<WraithEntity,LivingEntityRenderState,WraithModel>{
    private static final Identifier TEXTURE=Identifier.fromNamespaceAndPath(UsefultoolsMod.MOD_ID,"textures/entity/ghost/wraith.png");
    public WraithRenderer(EntityRendererProvider.Context context){super(context,new WraithModel(context.bakeLayer(WraithModel.LAYER_LOCATION)),.55F);}
    @Override public Identifier getTextureLocation(LivingEntityRenderState state){return TEXTURE;}
    @Override protected RenderType getRenderType(LivingEntityRenderState state,boolean visible,boolean translucent,boolean glowing){return RenderTypes.entityTranslucentEmissive(TEXTURE);}
    @Override public LivingEntityRenderState createRenderState(){return new LivingEntityRenderState();}}
