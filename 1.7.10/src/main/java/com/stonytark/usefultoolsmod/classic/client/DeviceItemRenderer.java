package com.stonytark.usefultoolsmod.classic.client;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

/** Native 1.7.10 item geometry for the explosive and spectral utility devices. */
public final class DeviceItemRenderer implements IItemRenderer {
    static final ResourceLocation TEXTURE = new ResourceLocation(
            "usefultoolsmod", "textures/items/device_materials.png");
    private final float[][] geometry;
    private final boolean charge;
    private final boolean panel;
    private final boolean lantern;

    public DeviceItemRenderer(String name) {
        charge = "mining_charge".equals(name);
        panel = "remote_detonator".equals(name) || "spectral_resonator".equals(name);
        lantern = "ectoplasm_lantern".equals(name) || "soul_lantern".equals(name);
        if ("sticky_dynamite".equals(name)) geometry = DeviceGeometry.STICKY_DYNAMITE;
        else if ("remote_detonator".equals(name)) geometry = DeviceGeometry.REMOTE_DETONATOR;
        else if ("spectral_resonator".equals(name)) geometry = DeviceGeometry.SPECTRAL_RESONATOR;
        else if (charge) geometry = DeviceGeometry.MINING_CHARGE;
        else if ("ectoplasm_lantern".equals(name) || "soul_lantern".equals(name))
            geometry = DeviceGeometry.ECTOPLASM_LANTERN;
        else throw new IllegalArgumentException("Unknown device model: " + name);
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
        if (type == ItemRenderType.INVENTORY || type == ItemRenderType.ENTITY) {
            GL11.glTranslatef(-0.5F, charge ? -0.2F : -0.5F, -0.5F);
        } else if (type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
            GL11.glTranslatef(0, charge || lantern ? 0.85F : 0.65F, 0);
        }
        if (panel) {
            GL11.glTranslatef(0.5F, 0, 0.5F);
            GL11.glRotatef(type == ItemRenderType.EQUIPPED_FIRST_PERSON ? 90 : 180, 0, 1, 0);
            GL11.glTranslatef(-0.5F, 0, -0.5F);
        }
        ExplosiveItemRenderer.draw(geometry, TEXTURE);
        GL11.glPopMatrix();
    }
}
