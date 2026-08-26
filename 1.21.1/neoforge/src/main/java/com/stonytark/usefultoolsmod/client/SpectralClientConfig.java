package com.stonytark.usefultoolsmod.client;

import com.stonytark.usefultoolsmod.UsefultoolsMod;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;

@EventBusSubscriber(modid = UsefultoolsMod.MOD_ID)
public final class SpectralClientConfig {
    private static final ModConfigSpec.BooleanValue PARTICLES;
    public static final ModConfigSpec SPEC;
    public static boolean particlesEnabled = true;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        PARTICLES = builder.comment("Show ambient Ghost, Wraith, lantern, and resonator particles.")
                .define("spectralParticlesEnabled", true);
        SPEC = builder.build();
    }
    private SpectralClientConfig() {}

    @SubscribeEvent static void onLoad(ModConfigEvent event) {
        if (event.getConfig().getSpec() == SPEC) particlesEnabled = PARTICLES.get();
    }
}
