package com.stonytark.usefultoolsmod.classic.client;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelGhost extends ModelBase {
    private final ModelRenderer head;
    private final ModelRenderer body;
    private final ModelRenderer tail;
    private final ModelRenderer babyFace;

    public ModelGhost() {
        textureWidth = 64;
        textureHeight = 64;
        head = new ModelRenderer(this, 0, 0);
        head.addBox(-6.0F, -10.0F, -6.0F, 12, 12, 12);
        head.setRotationPoint(0.0F, 10.0F, 0.0F);
        body = new ModelRenderer(this, 0, 24);
        body.addBox(-4.0F, 0.0F, -4.0F, 8, 8, 8);
        body.setRotationPoint(0.0F, 12.0F, 0.0F);
        tail = new ModelRenderer(this, 32, 24);
        tail.addBox(-2.0F, 0.0F, -2.0F, 4, 7, 4);
        tail.setRotationPoint(0.0F, 19.0F, 0.0F);
        babyFace = new ModelRenderer(this, 40, 0);
        babyFace.addBox(-4.0F, -7.0F, -6.4F, 8, 5, 1);
        babyFace.setRotationPoint(0.0F, 10.0F, 0.0F);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbAmount, float age, float yaw, float pitch, float scale) {
        head.rotateAngleY = yaw / 57.29578F;
        head.rotateAngleX = pitch / 57.29578F;
        tail.rotateAngleZ = (float) Math.sin(age * 0.08F) * 0.15F;
        if (isChild) {
            org.lwjgl.opengl.GL11.glPushMatrix();
            org.lwjgl.opengl.GL11.glScalef(.72F, .72F, .72F);
            org.lwjgl.opengl.GL11.glTranslatef(0F, 8F * scale, 0F);
            head.render(scale); body.render(scale); tail.render(scale); babyFace.render(scale);
            org.lwjgl.opengl.GL11.glPopMatrix();
        } else { head.render(scale); body.render(scale); tail.render(scale); }
    }
}
