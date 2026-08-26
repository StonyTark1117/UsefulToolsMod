package com.stonytark.usefultoolsmod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.stonytark.usefultoolsmod.UsefultoolsMod;
import com.stonytark.usefultoolsmod.entity.custom.WraithEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class WraithModel extends HierarchicalModel<WraithEntity> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(UsefultoolsMod.MOD_ID, "wraith"), "main");
    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart leftArm;
    private final ModelPart rightArm;
    private final ModelPart tail;

    public WraithModel(ModelPart root) {
        this.root = root.getChild("body"); this.head = this.root.getChild("head");
        this.leftArm = this.root.getChild("left_arm"); this.rightArm = this.root.getChild("right_arm");
        this.tail = this.root.getChild("tail");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition(); PartDefinition base = mesh.getRoot();
        PartDefinition body = base.addOrReplaceChild("body", CubeListBuilder.create()
                .texOffs(0, 18).addBox(-5, -15, -3, 10, 11, 6, new CubeDeformation(0)), PartPose.offset(0, 22, 0));
        body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0)
                .addBox(-6, -11, -5, 12, 11, 10, new CubeDeformation(0)), PartPose.offset(0, -14, 0));
        body.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(34, 0)
                .addBox(0, -1, -2, 4, 14, 4, new CubeDeformation(0)), PartPose.offset(5, -12, 0));
        body.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(34, 0).mirror()
                .addBox(-4, -1, -2, 4, 14, 4, new CubeDeformation(0)), PartPose.offset(-5, -12, 0));
        body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 35)
                .addBox(-4, 0, -2, 8, 9, 4).texOffs(24, 35)
                .addBox(-2, 8, -1, 4, 8, 2), PartPose.offset(0, -4, 0));
        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override public void setupAnim(WraithEntity entity, float limbSwing, float limbAmount, float age, float yaw, float pitch) {
        float wave = Mth.sin(age * 0.12F);
        head.yRot = Mth.clamp(yaw, -35, 35) * Mth.DEG_TO_RAD; head.xRot = Mth.clamp(pitch, -25, 35) * Mth.DEG_TO_RAD;
        leftArm.zRot = -0.28F + wave * 0.12F; rightArm.zRot = 0.28F - wave * 0.12F;
        leftArm.xRot = -0.35F + Mth.sin(age * 0.16F) * 0.18F; rightArm.xRot = -0.35F - Mth.sin(age * 0.16F) * 0.18F;
        tail.xRot = wave * 0.08F; root.y = 22 + wave * 0.7F;
    }
    @Override public ModelPart root() { return root; }
    @Override public void renderToBuffer(PoseStack stack, VertexConsumer consumer, int light, int overlay,
                                         float red, float green, float blue, float alpha) {
        root.render(stack, consumer, light, overlay, red, green, blue, alpha * 0.82F);
    }
}
