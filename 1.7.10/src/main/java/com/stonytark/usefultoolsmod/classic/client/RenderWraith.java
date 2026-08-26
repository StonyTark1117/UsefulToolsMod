package com.stonytark.usefultoolsmod.classic.client;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderWraith extends RenderLiving {
    private static final ResourceLocation TEXTURE = new ResourceLocation("usefultoolsmod", "textures/entity/wraith.png");
    public RenderWraith() { super(new ModelWraith(), .7F); }
    @Override protected ResourceLocation getEntityTexture(Entity entity) { return TEXTURE; }
    @Override protected void preRenderCallback(net.minecraft.entity.EntityLivingBase entity, float partialTick) {
        GL11.glEnable(GL11.GL_BLEND); GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA); GL11.glScalef(1F, 1.15F, 1F);
    }
}
