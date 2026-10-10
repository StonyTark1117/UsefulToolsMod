package com.stonytark.usefultoolsmod.spectralqa;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.stonytark.usefultoolsmod.entity.client.GhostRenderer;
import com.stonytark.usefultoolsmod.entity.client.WraithRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.argument;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

/** Experimental facial mask and full-frame intervals; kept out of release binaries. */
public final class SpectralQa implements ClientModInitializer {
    private static final Logger LOGGER = LoggerFactory.getLogger("SpectralQa");
    private static int strength;
    private static int remaining;
    private static long previous;
    private static final List<Long> FRAMES = new ArrayList<>();

    @Override
    public void onInitializeClient() {
        LivingEntityFeatureRendererRegistrationCallback.EVENT.register((type, renderer, helper, context) -> {
            if (renderer instanceof WraithRenderer wraith) helper.register(new Mask<>(wraith, "wraith"));
            if (renderer instanceof GhostRenderer ghost) helper.register(new Mask<>(ghost, "ghost"));
        });
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, access) -> dispatcher.register(
                literal("spectralqa")
                    .then(literal("mask").then(argument("strength", IntegerArgumentType.integer(0, 3))
                        .executes(command -> {
                            strength = IntegerArgumentType.getInteger(command, "strength");
                            command.getSource().sendFeedback(Text.literal("SPECTRAL_QA_MASK " + strength));
                            LOGGER.info("SPECTRAL_QA_MASK {}", strength);
                            return 1;
                        })))
                    .then(literal("frames").executes(command -> {
                        FRAMES.clear(); previous = 0; remaining = 1801;
                        command.getSource().sendFeedback(Text.literal("SPECTRAL_QA_FRAMES_STARTED"));
                        LOGGER.info("SPECTRAL_QA_FRAMES_STARTED");
                        return 1;
                    }))));
        WorldRenderEvents.END.register(context -> {
            if (remaining == 0) return;
            long now = System.nanoTime();
            if (previous != 0) FRAMES.add(now - previous);
            previous = now;
            if (--remaining == 0) {
                Collections.sort(FRAMES);
                LOGGER.info("SPECTRAL_QA_FRAMES_COMPLETE mask={} samples={} median_ns={} p95_ns={}",
                        strength, FRAMES.size(), FRAMES.get(FRAMES.size()/2),
                        FRAMES.get((int) Math.ceil(FRAMES.size()*.95)-1));
            }
        });
    }

    private static final class Mask<T extends LivingEntity, M extends EntityModel<T>> extends FeatureRenderer<T, M> {
        private final Identifier texture;
        Mask(FeatureRendererContext<T, M> renderer, String entity) {
            super(renderer);
            texture = Identifier.of("usefultools_spectral_qa", "textures/" + entity + "_mask.png");
        }
        @Override
        public void render(MatrixStack matrices, VertexConsumerProvider buffers, int light, T entity,
                           float limbAngle, float limbDistance, float tickDelta, float age, float yaw, float pitch) {
            if (strength == 0 || entity.isInvisible() || entity.isGlowing() || entity.isSpectator()) return;
            int color = switch (strength) { case 1 -> 0xff303030; case 2 -> 0xff505050; default -> 0xff707070; };
            getContextModel().render(matrices, buffers.getBuffer(RenderLayer.getEyes(texture)),
                    0xF000F0, OverlayTexture.DEFAULT_UV, color);
        }
    }
}
