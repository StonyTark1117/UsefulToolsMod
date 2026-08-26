package com.stonytark.usefultoolsmod.client;

import com.stonytark.usefultoolsmod.UsefultoolsMod;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = UsefultoolsMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class SpectralClientConfig {
    private static final ForgeConfigSpec.BooleanValue PARTICLES;
    public static final ForgeConfigSpec SPEC;
    public static boolean particlesEnabled = true;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        PARTICLES = builder.comment("Show ambient Ghost, Wraith, lantern, and resonator particles.")
                .define("spectralParticlesEnabled", true);
        SPEC = builder.build();
    }
    private SpectralClientConfig() {}

    @SubscribeEvent static void onLoad(ModConfigEvent event) {
        if (event.getConfig().getSpec() == SPEC) particlesEnabled = PARTICLES.get();
    }
}
