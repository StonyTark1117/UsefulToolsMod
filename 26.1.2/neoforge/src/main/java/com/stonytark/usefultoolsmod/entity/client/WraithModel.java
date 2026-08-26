package com.stonytark.usefultoolsmod.entity.client;
import com.stonytark.usefultoolsmod.UsefultoolsMod;
import net.minecraft.client.model.EntityModel;import net.minecraft.client.model.geom.ModelLayerLocation;import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;import net.minecraft.client.model.geom.builders.*;import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.Identifier;import net.minecraft.util.Mth;
public class WraithModel extends EntityModel<LivingEntityRenderState>{
    public static final ModelLayerLocation LAYER_LOCATION=new ModelLayerLocation(Identifier.fromNamespaceAndPath(UsefultoolsMod.MOD_ID,"wraith"),"main");
    private final ModelPart root,head,leftArm,rightArm,tail;
    public WraithModel(ModelPart base){super(base.getChild("body"));root=base.getChild("body");head=root.getChild("head");leftArm=root.getChild("left_arm");rightArm=root.getChild("right_arm");tail=root.getChild("tail");}
    public static LayerDefinition createBodyLayer(){MeshDefinition mesh=new MeshDefinition();PartDefinition base=mesh.getRoot();
        PartDefinition body=base.addOrReplaceChild("body",CubeListBuilder.create().texOffs(0,18).addBox(-5,-15,-3,10,11,6),PartPose.offset(0,22,0));
        body.addOrReplaceChild("head",CubeListBuilder.create().texOffs(0,0).addBox(-6,-11,-5,12,11,10),PartPose.offset(0,-14,0));
        body.addOrReplaceChild("left_arm",CubeListBuilder.create().texOffs(34,0).addBox(0,-1,-2,4,14,4),PartPose.offset(5,-12,0));
        body.addOrReplaceChild("right_arm",CubeListBuilder.create().texOffs(34,0).mirror().addBox(-4,-1,-2,4,14,4),PartPose.offset(-5,-12,0));
        body.addOrReplaceChild("tail",CubeListBuilder.create().texOffs(0,35).addBox(-4,0,-2,8,9,4).texOffs(24,35).addBox(-2,8,-1,4,8,2),PartPose.offset(0,-4,0));
        return LayerDefinition.create(mesh,64,64);}
    @Override public void setupAnim(LivingEntityRenderState state){super.setupAnim(state);float wave=Mth.sin(state.ageInTicks*.12F);
        head.yRot=Mth.clamp(state.yRot,-35,35)*Mth.DEG_TO_RAD;head.xRot=Mth.clamp(state.xRot,-25,35)*Mth.DEG_TO_RAD;
        leftArm.zRot=-.28F+wave*.12F;rightArm.zRot=.28F-wave*.12F;leftArm.xRot=-.35F+Mth.sin(state.ageInTicks*.16F)*.18F;
        rightArm.xRot=-.35F-Mth.sin(state.ageInTicks*.16F)*.18F;tail.xRot=wave*.08F;root.y=22+wave*.7F;}}
