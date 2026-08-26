package com.stonytark.usefultoolsmod.classic.client;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelWraith extends ModelBase {
    private final ModelRenderer hood;
    private final ModelRenderer torso;
    private final ModelRenderer shroud;
    public ModelWraith() {
        textureWidth = 64; textureHeight = 64;
        hood = new ModelRenderer(this, 0, 0); hood.addBox(-6, -12, -6, 12, 12, 12); hood.setRotationPoint(0, 7, 0);
        torso = new ModelRenderer(this, 0, 24); torso.addBox(-5, 0, -4, 10, 12, 8); torso.setRotationPoint(0, 7, 0);
        shroud = new ModelRenderer(this, 36, 24); shroud.addBox(-3, 0, -3, 6, 10, 6); shroud.setRotationPoint(0, 18, 0);
    }
    @Override public void render(Entity entity, float limb, float amount, float age, float yaw, float pitch, float scale) {
        hood.rotateAngleY = yaw / 57.29578F; hood.rotateAngleX = pitch / 57.29578F;
        shroud.rotateAngleZ = (float)Math.sin(age * .1F) * .18F; hood.render(scale); torso.render(scale); shroud.render(scale);
    }
}
