package com.stonytark.usefultoolsmod.entity.client;

import com.stonytark.usefultoolsmod.UsefultoolsMod;
import com.stonytark.usefultoolsmod.entity.custom.GhostEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class GhostModel<T extends GhostEntity> extends SinglePartEntityModel<T> {
    public static final EntityModelLayer LAYER_LOCATION =
            new EntityModelLayer(Identifier.of(UsefultoolsMod.MOD_ID, "ghost"), "main");

    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart babyFace;

    public GhostModel(ModelPart root) {
        this.body = root.getChild("body");
        this.head = body.getChild("head");
        this.babyFace = body.getChild("baby_face");
    }

    public static TexturedModelData createBodyLayer() {
        ModelData meshdefinition = new ModelData();
        ModelPartData partdefinition = meshdefinition.getRoot();

        ModelPartData body = partdefinition.addChild("body",
                ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        body.addChild("head",
                ModelPartBuilder.create().uv(0, 0).cuboid(-5.0F, -23.0F, -13.0F, 10.0F, 10.0F, 10.0F, new Dilation(2.0F)),
                ModelTransform.NONE);
        body.addChild("baby_face",
                ModelPartBuilder.create().uv(50, 12).cuboid(-3.0F, -20.0F, -15.1F, 6.0F, 3.0F, 1.0F),
                ModelTransform.NONE);

        ModelPartData tail = body.addChild("tail",
                ModelPartBuilder.create(), ModelTransform.NONE);

        tail.addChild("tail_start",
                ModelPartBuilder.create().uv(0, 20).cuboid(-1.0F, -18.0F, 3.0F, 4.0F, 4.0F, 1.0F, new Dilation(2.0F)),
                ModelTransform.NONE);

        tail.addChild("tail_second",
                ModelPartBuilder.create().uv(18, 20).cuboid(0.0F, -16.0F, 9.0F, 2.0F, 2.0F, 1.0F, new Dilation(1.0F)),
                ModelTransform.NONE);

        tail.addChild("tail_third",
                ModelPartBuilder.create().uv(18, 23).cuboid(1.0F, -18.0F, 13.0F, 1.0F, 1.0F, 1.0F, new Dilation(1.0F)),
                ModelTransform.NONE);

        tail.addChild("tail_end",
                ModelPartBuilder.create().uv(10, 20).cuboid(-1.0F, -18.0F, 16.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)),
                ModelTransform.NONE);

        return TexturedModelData.of(meshdefinition, 64, 64);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);
        this.applyHeadRotation(headYaw, headPitch);
        this.babyFace.visible = entity.isBaby();

        this.updateAnimation(entity.idleAnimationState, GhostAnimations.ANIM_GHOST_IDLE, animationProgress, 1f);
    }

    private void applyHeadRotation(float headYaw, float headPitch) {
        headYaw = MathHelper.clamp(headYaw, -30.0F, 30.0F);
        headPitch = MathHelper.clamp(headPitch, -25.0F, 45.0F);
        this.head.yaw = headYaw * ((float) Math.PI / 180F);
        this.head.pitch = headPitch * ((float) Math.PI / 180F);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
        body.render(matrices, vertices, light, overlay, color);
    }

    @Override
    public ModelPart getPart() {
        return body;
    }
}
