package com.stonytark.usefultoolsmod.entity.client;

import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.animation.AnimationHelper;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.render.entity.animation.Transformation;

public class GhostAnimations {

    public static final Animation ANIM_GHOST_IDLE = Animation.Builder.create(5.5f).looping()
            .addBoneAnimation("head",
                    new Transformation(Transformation.Targets.TRANSLATE,
                            new Keyframe(0f, AnimationHelper.createTranslationalVector(-1f, -2f, 1f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(1.75f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(3.75f, AnimationHelper.createTranslationalVector(1f, 2f, -1f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(5.5f, AnimationHelper.createTranslationalVector(-1f, -2f, 1f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("tail_end",
                    new Transformation(Transformation.Targets.TRANSLATE,
                            new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(1.75f, AnimationHelper.createTranslationalVector(-2f, -2f, -1f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(3.75f, AnimationHelper.createTranslationalVector(2f, 2f, 1f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(5.5f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("tail_start",
                    new Transformation(Transformation.Targets.TRANSLATE,
                            new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(1.75f, AnimationHelper.createTranslationalVector(0f, 8f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(3.75f, AnimationHelper.createTranslationalVector(0f, -4f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(5.5f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("tail_second",
                    new Transformation(Transformation.Targets.TRANSLATE,
                            new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(1.75f, AnimationHelper.createTranslationalVector(0f, -4f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(3.75f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(5.5f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("tail_third",
                    new Transformation(Transformation.Targets.TRANSLATE,
                            new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(1.75f, AnimationHelper.createTranslationalVector(0f, 3f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(3.75f, AnimationHelper.createTranslationalVector(0f, -3f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(5.5f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .build();

    public static final Animation ANIM_GHOST_WALKING = Animation.Builder.create(12f).looping()
            .addBoneAnimation("head",
                    new Transformation(Transformation.Targets.TRANSLATE,
                            new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(4f, AnimationHelper.createTranslationalVector(0f, 14f, -7f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(8f, AnimationHelper.createTranslationalVector(0f, 0f, -14f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(12f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("tail_end",
                    new Transformation(Transformation.Targets.TRANSLATE,
                            new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(4f, AnimationHelper.createTranslationalVector(-1f, 0.5f, -10f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(8f, AnimationHelper.createTranslationalVector(-2f, -1f, -20f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(12f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("tail_start",
                    new Transformation(Transformation.Targets.TRANSLATE,
                            new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(4f, AnimationHelper.createTranslationalVector(0f, 9f, -8f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(8f, AnimationHelper.createTranslationalVector(0f, 0f, -16f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(12f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("tail_second",
                    new Transformation(Transformation.Targets.TRANSLATE,
                            new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(4f, AnimationHelper.createTranslationalVector(0f, 6f, -9f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(8f, AnimationHelper.createTranslationalVector(0f, 0f, -18f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(12f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .addBoneAnimation("tail_third",
                    new Transformation(Transformation.Targets.TRANSLATE,
                            new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(4f, AnimationHelper.createTranslationalVector(0f, 2f, -9.5f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(8f, AnimationHelper.createTranslationalVector(0f, -2f, -19f),
                                    Transformation.Interpolations.LINEAR),
                            new Keyframe(12f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
                                    Transformation.Interpolations.LINEAR)))
            .build();
}
