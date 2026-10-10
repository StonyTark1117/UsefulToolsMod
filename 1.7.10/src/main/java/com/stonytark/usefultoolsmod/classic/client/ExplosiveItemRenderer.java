package com.stonytark.usefultoolsmod.classic.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;

/** Classic equivalent of the modern JSON geometry, with six opaque faces per cuboid. */
public final class ExplosiveItemRenderer implements IItemRenderer {
    static final ResourceLocation TEXTURE = new ResourceLocation(
            "usefultoolsmod", "textures/items/explosive_materials.png");
    private final float[][] geometry;

    public ExplosiveItemRenderer(boolean dynamite) {
        geometry = dynamite ? ExplosiveGeometry.DYNAMITE : ExplosiveGeometry.GRENADE;
    }

    @Override public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return type != ItemRenderType.FIRST_PERSON_MAP;
    }

    @Override public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
                                                   ItemRendererHelper helper) {
        return helper == ItemRendererHelper.INVENTORY_BLOCK
                || helper == ItemRendererHelper.EQUIPPED_BLOCK
                || helper == ItemRendererHelper.BLOCK_3D
                || helper == ItemRendererHelper.ENTITY_BOBBING
                || helper == ItemRendererHelper.ENTITY_ROTATION;
    }

    @Override public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        GL11.glPushMatrix();
        // Forge's inventory and dropped-block helpers expect centered geometry;
        // equipped helpers supply their own -0.5 translation.
        if (type == ItemRenderType.INVENTORY || type == ItemRenderType.ENTITY) {
            GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        } else if (type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
            // Legacy Minecraft places a block-shaped held item below the HUD.
            // Lift this shorter model so its bands, body and fuse stay visible.
            GL11.glTranslatef(0, 0.65F, 0);
        }
        draw(geometry);
        GL11.glPopMatrix();
    }

    static void draw(float[][] geometry) {
        draw(geometry, TEXTURE);
    }

    static void draw(float[][] geometry, ResourceLocation texture) {
        int activeTexture = GL11.glGetInteger(GL13.GL_ACTIVE_TEXTURE);
        float brightnessX = OpenGlHelper.lastBrightnessX;
        float brightnessY = OpenGlHelper.lastBrightnessY;
        // Third-person helpers also use material/lightmap state. Restore all
        // server attributes and Forge's cached brightness after drawing.
        GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
        OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glColor4f(1, 1, 1, 1);
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        for (float[] box : geometry) {
            double x0 = box[0] / 16D, y0 = box[1] / 16D, z0 = box[2] / 16D;
            double x1 = box[3] / 16D, y1 = box[4] / 16D, z1 = box[5] / 16D;
            face(box, 0, -1, 0, x0,y0,z1, x0,y0,z0, x1,y0,z0, x1,y0,z1);
            face(box, 0, 1, 0, x0,y1,z0, x0,y1,z1, x1,y1,z1, x1,y1,z0);
            face(box, 0, 0, -1, x1,y0,z0, x0,y0,z0, x0,y1,z0, x1,y1,z0);
            face(box, 0, 0, 1, x0,y0,z1, x1,y0,z1, x1,y1,z1, x0,y1,z1);
            face(box, -1, 0, 0, x0,y0,z0, x0,y0,z1, x0,y1,z1, x0,y1,z0);
            face(box, 1, 0, 0, x1,y0,z1, x1,y0,z0, x1,y1,z0, x1,y1,z1);
        }
        GL11.glPopAttrib();
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, brightnessX, brightnessY);
        OpenGlHelper.setActiveTexture(activeTexture);
    }

    private static void face(float[] box, float nx, float ny, float nz, double... vertices) {
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.setNormal(nx, ny, nz);
        double u0 = box[6] / 16D, v0 = box[7] / 16D;
        double u1 = box[8] / 16D, v1 = box[9] / 16D;
        tessellator.addVertexWithUV(vertices[0], vertices[1], vertices[2], u0, v1);
        tessellator.addVertexWithUV(vertices[3], vertices[4], vertices[5], u1, v1);
        tessellator.addVertexWithUV(vertices[6], vertices[7], vertices[8], u1, v0);
        tessellator.addVertexWithUV(vertices[9], vertices[10], vertices[11], u0, v0);
        tessellator.draw();
    }
}
