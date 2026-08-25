package com.stonytark.usefultoolsmod;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class ConfigPersistenceTest {
    @TempDir
    Path temporaryDirectory;

    @Test
    void malformedValuesDoNotDiscardValidNeighbors() throws Exception {
        Path path = temporaryDirectory.resolve("usefultoolsmod.json");
        boolean explosives = Config.explosivesEnabled;
        boolean obsidian = Config.obsidianEnabled;
        try {
            Config.explosivesEnabled = true;
            Config.obsidianEnabled = true;
            Files.writeString(path, "{\"explosivesEnabled\":false,\"obsidianEnabled\":{}}");
            Config.load(path);
            assertFalse(Config.explosivesEnabled);
            assertTrue(Config.obsidianEnabled);
        } finally {
            Config.explosivesEnabled = explosives;
            Config.obsidianEnabled = obsidian;
        }
    }

    @Test
    void legacyDripstoneKeysMigrateToCanonicalKeys() throws Exception {
        Path path = temporaryDirectory.resolve("usefultoolsmod.json");
        boolean enabled = Config.pointedDripstoneEnabled;
        boolean effects = Config.pointedDripstoneEffects;
        try {
            Files.writeString(path, "{\"pointedDripstoneEnabled\":false,\"pointedDripstoneEffects\":false}");
            Config.load(path);
            assertFalse(Config.pointedDripstoneEnabled);
            assertFalse(Config.pointedDripstoneEffects);
            String saved = Files.readString(path);
            assertTrue(saved.contains("\"dripstoneEnabled\""));
            assertTrue(saved.contains("\"dripstoneEffects\""));
            assertFalse(saved.contains("pointedDripstone"));
            assertFalse(Files.exists(path.resolveSibling("usefultoolsmod.json.tmp")));
        } finally {
            Config.pointedDripstoneEnabled = enabled;
            Config.pointedDripstoneEffects = effects;
        }
    }

    @Test
    void diskValuesAreBoundedAndMissingParentsAreCreated() throws Exception {
        Path path = temporaryDirectory.resolve("nested/usefultoolsmod.json");
        double chance = Config.ghostSpawnChance;
        try {
            Config.load(path);
            assertTrue(Files.isRegularFile(path));
            Files.writeString(path, "{\"ghostSpawnChance\":99}");
            Config.load(path);
            assertEquals(1.0, Config.ghostSpawnChance);
            Files.writeString(path, "{\"ghostSpawnChance\":-99}");
            Config.load(path);
            assertEquals(0.0, Config.ghostSpawnChance);
        } finally {
            Config.ghostSpawnChance = chance;
        }
    }
}
