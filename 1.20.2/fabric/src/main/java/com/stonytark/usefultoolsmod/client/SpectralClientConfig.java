package com.stonytark.usefultoolsmod.client;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.stonytark.usefultoolsmod.UsefultoolsMod;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Files;
import java.nio.file.Path;

public final class SpectralClientConfig {
    public static boolean particlesEnabled = true;
    private SpectralClientConfig() {}

    public static void load() {
        Path path = FabricLoader.getInstance().getConfigDir().resolve("usefultoolsmod-client.json");
        try {
            if (Files.exists(path)) {
                JsonObject root = JsonParser.parseString(Files.readString(path)).getAsJsonObject();
                if (root.has("spectralParticlesEnabled")) particlesEnabled = root.get("spectralParticlesEnabled").getAsBoolean();
            }
            Files.createDirectories(path.getParent());
            Files.writeString(path, "{\n  \"spectralParticlesEnabled\": " + particlesEnabled + "\n}\n");
        } catch (Exception exception) {
            UsefultoolsMod.LOGGER.warn("Failed to load client spectral particle setting: {}", exception.toString());
        }
    }
}
