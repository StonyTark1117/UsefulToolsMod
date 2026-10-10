package com.stonytark.usefultoolsmod.classic.client;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/** The same adhesive-bound bundle is visible in hand, in flight and while attached. */
public final class RenderStickyDynamite extends Render {
    @Override public void doRender(Entity entity, double x, double y, double z,
                                    float yaw, float partialTick) {
        GL11.glPushMatrix();
        net.minecraft.util.Vec3 offset = ((com.stonytark.usefultoolsmod.classic.EntityStickyDynamite) entity).getModelOffset();
        GL11.glTranslated(x + offset.xCoord, y + offset.yCoord + 0.15D, z + offset.zCoord);
        GL11.glRotatef(210F - renderManager.playerViewY, 0, 1, 0);
        GL11.glScalef(0.7F, 0.7F, 0.7F);
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        ExplosiveItemRenderer.draw(DeviceGeometry.STICKY_DYNAMITE, DeviceItemRenderer.TEXTURE);
        GL11.glPopMatrix();
    }

    @Override protected ResourceLocation getEntityTexture(Entity entity) {
        return DeviceItemRenderer.TEXTURE;
    }
}
