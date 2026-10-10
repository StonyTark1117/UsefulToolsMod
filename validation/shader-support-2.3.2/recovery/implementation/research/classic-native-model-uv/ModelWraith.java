package com.stonytark.usefultoolsmod.classic.client;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelWraith extends ModelBase {
    private final ModelRenderer hood;
    private final ModelRenderer torso;
    private final ModelRenderer shroud;
    private final ModelRenderer shaderHood;
    private final ModelRenderer shaderTorso;
    private final ModelRenderer shaderTail;
    private final ModelRenderer leftArm;
    private final ModelRenderer rightArm;
    public ModelWraith() {
        textureWidth = 64; textureHeight = 64;
        hood = new ModelRenderer(this, 0, 0); hood.addBox(-6, -12, -6, 12, 12, 12); hood.setRotationPoint(0, 7, 0);
        torso = new ModelRenderer(this, 0, 24); torso.addBox(-5, 0, -4, 10, 12, 8); torso.setRotationPoint(0, 7, 0);
        shroud = new ModelRenderer(this, 36, 24); shroud.addBox(-3, 0, -3, 6, 10, 6); shroud.setRotationPoint(0, 18, 0);
        // The shared texture's face is laid out for the modern 12x11x10 hood.
        // Reusing the classic 12x12x12 UV unwrap clips eyes and mouth under packs.
        shaderHood = new ModelRenderer(this, 0, 0); shaderHood.addBox(-6, -11, -5, 12, 11, 10);
        shaderTorso = new ModelRenderer(this, 0, 18); shaderTorso.addBox(-5, -15, -3, 10, 11, 6);
        shaderTail = new ModelRenderer(this, 0, 35); shaderTail.addBox(-4, 0, -2, 8, 9, 4);
        shaderTail.setTextureOffset(24, 35).addBox(-2, 8, -1, 4, 8, 2);
        leftArm = new ModelRenderer(this, 34, 0); leftArm.addBox(0, -1, -2, 4, 14, 4); leftArm.setRotationPoint(5, 10, 0);
        rightArm = new ModelRenderer(this, 34, 0); rightArm.mirror = true;
        rightArm.addBox(-4, -1, -2, 4, 14, 4); rightArm.setRotationPoint(-5, 10, 0);
    }
    @Override public void render(Entity entity, float limb, float amount, float age, float yaw, float pitch, float scale) {
        if (!SpectralShaderSupport.isShaderPackInUse()) {
            // Preserve the original three-part native model when packs are absent/off.
            hood.rotateAngleY = yaw / 57.29578F; hood.rotateAngleX = pitch / 57.29578F;
            shroud.rotateAngleZ = (float)Math.sin(age * .1F) * .18F;
            hood.render(scale); torso.render(scale); shroud.render(scale);
        } else {
            // One native model render, using the maintained texture's geometry.
            // Native occlusion, damage and invisibility cover every part together.
            float wave = (float)Math.sin(age * .12F);
            float hover = wave * .7F;
            shaderHood.setRotationPoint(0, 8 + hover, 0);
            shaderTorso.setRotationPoint(0, 22 + hover, 0);
            shaderTail.setRotationPoint(0, 18 + hover, 0);
            leftArm.setRotationPoint(5, 10 + hover, 0);
            rightArm.setRotationPoint(-5, 10 + hover, 0);
            shaderHood.rotateAngleY = Math.max(-35, Math.min(35, yaw)) / 57.29578F;
            shaderHood.rotateAngleX = Math.max(-25, Math.min(35, pitch)) / 57.29578F;
            shaderTail.rotateAngleX = wave * .08F;
            leftArm.rotateAngleZ = -.28F + wave * .12F;
            rightArm.rotateAngleZ = .28F - wave * .12F;
            leftArm.rotateAngleX = -.35F + (float)Math.sin(age * .16F) * .18F;
            rightArm.rotateAngleX = -.35F - (float)Math.sin(age * .16F) * .18F;
            shaderHood.render(scale); shaderTorso.render(scale); shaderTail.render(scale);
            leftArm.render(scale); rightArm.render(scale);
        }
    }
}
