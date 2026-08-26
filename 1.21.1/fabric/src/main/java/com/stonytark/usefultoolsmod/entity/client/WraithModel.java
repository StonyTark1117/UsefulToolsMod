package com.stonytark.usefultoolsmod.entity.client;

import com.stonytark.usefultoolsmod.UsefultoolsMod;
import com.stonytark.usefultoolsmod.entity.custom.WraithEntity;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class WraithModel extends SinglePartEntityModel<WraithEntity> {
    public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(
            Identifier.of(UsefultoolsMod.MOD_ID, "wraith"), "main");
    private final ModelPart root, head, leftArm, rightArm, tail;
    public WraithModel(ModelPart base) {
        root = base.getChild("body"); head = root.getChild("head"); leftArm = root.getChild("left_arm");
        rightArm = root.getChild("right_arm"); tail = root.getChild("tail");
    }
    public static TexturedModelData createBodyLayer() {
        ModelData data = new ModelData(); ModelPartData base = data.getRoot();
        ModelPartData body = base.addChild("body", ModelPartBuilder.create().uv(0, 18)
                .cuboid(-5, -15, -3, 10, 11, 6), ModelTransform.pivot(0, 22, 0));
        body.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-6, -11, -5, 12, 11, 10), ModelTransform.pivot(0, -14, 0));
        body.addChild("left_arm", ModelPartBuilder.create().uv(34, 0).cuboid(0, -1, -2, 4, 14, 4), ModelTransform.pivot(5, -12, 0));
        body.addChild("right_arm", ModelPartBuilder.create().uv(34, 0).mirrored().cuboid(-4, -1, -2, 4, 14, 4), ModelTransform.pivot(-5, -12, 0));
        body.addChild("tail", ModelPartBuilder.create().uv(0, 35).cuboid(-4, 0, -2, 8, 9, 4)
                .uv(24, 35).cuboid(-2, 8, -1, 4, 8, 2), ModelTransform.pivot(0, -4, 0));
        return TexturedModelData.of(data, 64, 64);
    }
    @Override public void setAngles(WraithEntity entity, float limbAngle, float limbDistance, float age, float yaw, float pitch) {
        float wave = MathHelper.sin(age * 0.12F); head.yaw = MathHelper.clamp(yaw, -35, 35) * MathHelper.RADIANS_PER_DEGREE;
        head.pitch = MathHelper.clamp(pitch, -25, 35) * MathHelper.RADIANS_PER_DEGREE;
        leftArm.roll = -0.28F + wave * 0.12F; rightArm.roll = 0.28F - wave * 0.12F;
        leftArm.pitch = -0.35F + MathHelper.sin(age * 0.16F) * 0.18F;
        rightArm.pitch = -0.35F - MathHelper.sin(age * 0.16F) * 0.18F; tail.pitch = wave * 0.08F; root.pivotY = 22 + wave * 0.7F;
    }
    @Override public ModelPart getPart() { return root; }
}
