package com.stonytark.usefultoolsmod;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class ConfigDescriptorTest {
    @TempDir
    Path temporaryDirectory;

    @Test
    void everyPersistentFieldHasExactlyOneUiDescriptor() {
        assertEquals(116, Config.options().size());
        HashSet<String> names = new HashSet<>();
        for (Config.Option option : Config.options()) {
            assertTrue(names.add(option.field().getName()), option.field().getName());
            assertFalse(option.category().isBlank());
            assertFalse(option.label().isBlank());
        }
        assertTrue(names.contains("cakeHungerEffects"));
        assertTrue(names.contains("ghostSpawnChance"));
    }

    @Test
    void ghostSpawnChanceIsClampedToItsPublicContract() {
        Config.Option option = Config.options().stream()
                .filter(candidate -> candidate.field().getName().equals("ghostSpawnChance"))
                .findFirst().orElseThrow();
        double original = Config.ghostSpawnChance;
        try {
            option.set(-1.0);
            assertEquals(0.0, Config.ghostSpawnChance);
            option.set(2.0);
            assertEquals(1.0, Config.ghostSpawnChance);
        } finally {
            option.set(original);
        }
    }

    @Test
    void legacyDripstoneNamesLoadAndCanonicalNamesAreSaved() throws Exception {
        Path path = temporaryDirectory.resolve("usefultoolsmod.json");
        boolean enabled = Config.dripstoneEnabled;
        boolean effects = Config.dripstoneEffects;
        try {
            Files.writeString(path, "{\"pointedDripstoneEnabled\":false,\"pointedDripstoneEffects\":false}");
            Config.load(path);
            assertFalse(Config.dripstoneEnabled);
            assertFalse(Config.dripstoneEffects);

            Config.save(path);
            String saved = Files.readString(path);
            assertTrue(saved.contains("\"dripstoneEnabled\""));
            assertTrue(saved.contains("\"dripstoneEffects\""));
            assertFalse(saved.contains("pointedDripstone"));
            assertFalse(Files.exists(path.resolveSibling("usefultoolsmod.json.tmp")));
        } finally {
            Config.dripstoneEnabled = enabled;
            Config.dripstoneEffects = effects;
        }
    }

    @Test
    void malformedValuesDoNotDiscardOtherValidOptions() throws Exception {
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
    void diskLoadClampsGhostSpawnChance() throws Exception {
        Path path = temporaryDirectory.resolve("usefultoolsmod.json");
        double original = Config.ghostSpawnChance;
        try {
            Files.writeString(path, "{\"ghostSpawnChance\":99}");
            Config.load(path);
            assertEquals(1.0, Config.ghostSpawnChance);
            Files.writeString(path, "{\"ghostSpawnChance\":-99}");
            Config.load(path);
            assertEquals(0.0, Config.ghostSpawnChance);
        } finally {
            Config.ghostSpawnChance = original;
        }
    }

    @Test
    void missingFileWritesACompleteDefaultDocument() throws Exception {
        Path path = temporaryDirectory.resolve("nested/usefultoolsmod.json");
        Config.load(path);
        assertTrue(Files.isRegularFile(path));
        String saved = Files.readString(path);
        assertTrue(saved.contains("\"explosivesEnabled\""));
        assertTrue(saved.contains("\"ghostSpawnChance\""));
        assertFalse(Files.exists(path.resolveSibling("usefultoolsmod.json.tmp")));
    }
}
