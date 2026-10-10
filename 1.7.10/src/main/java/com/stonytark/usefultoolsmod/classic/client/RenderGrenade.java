package com.stonytark.usefultoolsmod.classic.client;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/** Uses the held grenade's solid geometry instead of RenderSnowball's flat quad. */
public final class RenderGrenade extends Render {
    @Override public void doRender(Entity entity, double x, double y, double z,
                                    float yaw, float partialTick) {
        GL11.glPushMatrix();
        GL11.glTranslated(x, y + 0.15D, z);
        GL11.glRotatef(210F - renderManager.playerViewY, 0, 1, 0);
        GL11.glScalef(0.7F, 0.7F, 0.7F);
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        ExplosiveItemRenderer.draw(ExplosiveGeometry.GRENADE);
        GL11.glPopMatrix();
    }

    @Override protected ResourceLocation getEntityTexture(Entity entity) {
        return ExplosiveItemRenderer.TEXTURE;
    }
}
