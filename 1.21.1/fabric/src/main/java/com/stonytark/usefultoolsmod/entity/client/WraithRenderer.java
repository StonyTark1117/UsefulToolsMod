package com.stonytark.usefultoolsmod.entity.client;

import com.stonytark.usefultoolsmod.UsefultoolsMod;
import com.stonytark.usefultoolsmod.entity.custom.WraithEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class WraithRenderer extends MobEntityRenderer<WraithEntity, WraithModel> {
    private static final Identifier TEXTURE = Identifier.of(UsefultoolsMod.MOD_ID, "textures/entity/ghost/wraith.png");
    public WraithRenderer(EntityRendererFactory.Context context) { super(context, new WraithModel(context.getPart(WraithModel.LAYER_LOCATION)), 0.55F); }
    @Override public Identifier getTexture(WraithEntity entity) { return TEXTURE; }
}
