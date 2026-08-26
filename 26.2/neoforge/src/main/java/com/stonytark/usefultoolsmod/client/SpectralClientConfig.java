package com.stonytark.usefultoolsmod.client;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

/** Per-install visual preferences. This file is never synchronized by a server. */
public final class SpectralClientConfig {
    private static final Path PATH = Path.of("config", "usefultoolsmod-client.properties");
    public static final boolean particlesEnabled = load();
    private SpectralClientConfig() {}

    private static boolean load() {
        Properties properties = new Properties();
        try {
            if (Files.exists(PATH)) try (InputStream input = Files.newInputStream(PATH)) { properties.load(input); }
            boolean value = Boolean.parseBoolean(properties.getProperty("spectralParticlesEnabled", "true"));
            if (!Files.exists(PATH)) {
                Files.createDirectories(PATH.getParent());
                properties.setProperty("spectralParticlesEnabled", Boolean.toString(value));
                try (OutputStream output = Files.newOutputStream(PATH)) {
                    properties.store(output, "Useful Tools client-only visual settings");
                }
            }
            return value;
        } catch (Exception ignored) {
            return true;
        }
    }
}
