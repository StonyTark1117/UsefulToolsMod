package com.stonytark.usefultoolsmod;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.architectury.platform.Platform;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Persistent configuration via plain JSON.
 *
 * Loaded from {@code <gamedir>/config/usefultoolsmod.json}. All 116 public-static
 * fields (declared below) are written/read by reflection — adding a new field
 * needs no boilerplate. Missing keys in the file are left at their default values.
 *
 * A full Cloth Config UI integration (ModMenu on Fabric, NeoForge config screen)
 * is the next deferred phase. Today users edit the JSON file directly and reload.
 */
public class Config {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static Path configPath() {
        return Platform.getConfigFolder().resolve("usefultoolsmod.json");
    }

    /** Called from UsefultoolsMod.init() — loads from disk, writes defaults if absent. */
    public static void load() {
        Path path = configPath();
        try {
            if (!Files.exists(path)) {
                save();
                return;
            }
            JsonObject root = JsonParser.parseString(Files.readString(path)).getAsJsonObject();
            for (Field f : Config.class.getDeclaredFields()) {
                if (!isConfigField(f) || !root.has(f.getName())) continue;
                try {
                    Class<?> type = f.getType();
                    if (type == boolean.class) f.setBoolean(null, root.get(f.getName()).getAsBoolean());
                    else if (type == int.class) f.setInt(null, root.get(f.getName()).getAsInt());
                    else if (type == double.class) f.setDouble(null, root.get(f.getName()).getAsDouble());
                    else if (type == float.class) f.setFloat(null, root.get(f.getName()).getAsFloat());
                    else if (type == long.class) f.setLong(null, root.get(f.getName()).getAsLong());
                    else if (type == String.class) f.set(null, root.get(f.getName()).getAsString());
                } catch (Exception e) {
                    UsefultoolsMod.LOGGER.warn("Config load failed for {}: {}", f.getName(), e.toString());
                }
            }
        } catch (Exception e) {
            UsefultoolsMod.LOGGER.warn("Config load failed: {}", e.toString());
        }
    }

    public static void save() {
        JsonObject root = new JsonObject();
        for (Field f : Config.class.getDeclaredFields()) {
            if (!isConfigField(f)) continue;
            try {
                Class<?> type = f.getType();
                if (type == boolean.class) root.addProperty(f.getName(), f.getBoolean(null));
                else if (type == int.class) root.addProperty(f.getName(), f.getInt(null));
                else if (type == double.class) root.addProperty(f.getName(), f.getDouble(null));
                else if (type == float.class) root.addProperty(f.getName(), f.getFloat(null));
                else if (type == long.class) root.addProperty(f.getName(), f.getLong(null));
                else if (type == String.class) root.addProperty(f.getName(), (String) f.get(null));
            } catch (Exception e) {
                UsefultoolsMod.LOGGER.warn("Config save failed for {}: {}", f.getName(), e.toString());
            }
        }
        try {
            Path path = configPath();
            Files.createDirectories(path.getParent());
            Files.writeString(path, GSON.toJson(root));
        } catch (Exception e) {
            UsefultoolsMod.LOGGER.warn("Config save failed: {}", e.toString());
        }
    }

    private static boolean isConfigField(Field f) {
        int m = f.getModifiers();
        return Modifier.isPublic(m) && Modifier.isStatic(m) && !Modifier.isFinal(m);
    }


    // === Toggleable item sets ===
    public static boolean explosivesEnabled = true;
    public static boolean obsidianEnabled = true;
    public static boolean emeraldEnabled = true;
    public static boolean lapisEnabled = true;
    public static boolean ferrousGoldEnabled = true;
    public static boolean hardenedRedstoneEnabled = true;
    public static boolean hardenedGlowstoneEnabled = true;
    public static boolean overpowerEnabled = true;
    public static boolean ghostEnabled = true;
    public static boolean spectralInfuserEnabled = true;
    public static boolean infusedToolEffects = true;
    public static boolean rawMetalRoughEnabled = true;
    public static boolean roughCrystalEnabled = true;
    public static boolean snowEnabled = true;
    public static boolean polishedCrystalEnabled = true;
    public static boolean iceEnabled = true;
    public static boolean pprismEnabled = true;
    public static boolean flintEnabled = true;
    public static boolean fniEnabled = true;
    public static boolean woodVariantsEnabled = true;
    public static boolean stoneVariantsEnabled = true;
    public static boolean paperEnabled = true;
    public static boolean paperEffects = true;
    public static boolean featherEnabled = true;
    public static boolean featherEffects = true;
    public static boolean glassEnabled = true;
    public static boolean glassEffects = true;
    public static boolean rabbitHideEnabled = true;
    public static boolean rabbitHideEffects = true;
    public static boolean cactusEnabled = true;
    public static boolean cactusEffects = true;
    public static boolean spongeEnabled = true;
    public static boolean spongeEffects = true;
    public static boolean boneEnabled = true;
    public static boolean boneEffects = true;
    public static boolean clayEnabled = true;
    public static boolean clayEffects = true;
    public static boolean netherWartEnabled = true;
    public static boolean netherWartEffects = true;
    public static boolean brickEnabled = true;
    public static boolean netherBrickEnabled = true;
    public static boolean netherBrickEffects = true;
    public static boolean dripstoneEnabled = true;
    public static boolean dripstoneEffects = true;
    public static boolean copperEnabled = true;
    public static boolean copperEffects = true;
    public static boolean phantomEnabled = true;
    public static boolean phantomEffects = true;
    public static boolean magmaCreamEnabled = true;
    public static boolean magmaCreamEffects = true;
    public static boolean slimeEnabled = true;
    public static boolean slimeEffects = true;
    public static boolean blazeEnabled = true;
    public static boolean blazeEffects = true;
    public static boolean nautilusEnabled = true;
    public static boolean nautilusEffects = true;
    public static boolean purpurEnabled = true;
    public static boolean purpurEffects = true;
    public static boolean ghastTearEnabled = true;
    public static boolean ghastTearEffects = true;
    public static boolean eyeOfEnderEnabled = true;
    public static boolean eyeOfEnderEffects = true;
    public static boolean shulkerEnabled = true;
    public static boolean shulkerEffects = true;
    public static boolean turtleScuteEnabled = true;
    public static boolean turtleScuteEffects = true;
    public static boolean echoShardEnabled = true;
    public static boolean echoShardEffects = true;
    public static boolean dragonBreathEnabled = true;
    public static boolean dragonBreathEffects = true;
    public static boolean leatherEnabled = true;
    public static boolean coalEnabled = true;
    public static boolean cakeEnabled = true;
    public static boolean foodHungerDrain = true;
    public static boolean breadEnabled = true;
    public static boolean breadArmorEffects = true;
    public static boolean driedKelpEnabled = true;
    public static boolean driedKelpArmorEffects = true;
    public static boolean rottenFleshEnabled = true;
    public static boolean rottenFleshArmorEffects = true;
    public static boolean rottenFleshUndeadNeutral = true;
    public static boolean melonEnabled = true;
    public static boolean melonArmorEffects = true;
    public static boolean sweetBerryEnabled = true;
    public static boolean sweetBerryArmorEffects = true;
    public static boolean sweetBerryThorns = true;
    public static boolean pumpkinPieEnabled = true;
    public static boolean pumpkinPieArmorEffects = true;
    public static boolean pumpkinPieEndermanAvoidance = true;
    public static boolean mushroomEnabled = true;
    public static boolean mushroomArmorEffects = true;
    public static boolean mushroomSporeCloud = true;
    public static boolean pufferfishEnabled = true;
    public static boolean pufferfishArmorEffects = true;
    public static boolean pufferfishPoisonAura = true;
    public static boolean honeyEnabled = true;
    public static boolean honeyArmorEffects = true;
    public static boolean honeySticky = true;
    public static boolean chorusFruitEnabled = true;
    public static boolean chorusFruitArmorEffects = true;
    public static boolean chorusFruitTeleport = true;
    public static boolean goldenAppleEnabled = true;
    public static boolean goldenAppleArmorEffects = true;
    public static boolean ectoplasmSetEnabled = true;
    public static boolean opToolEffectsEnabled = true;
    public static boolean opArmorEffectsEnabled = true;

    // === Numeric and effect tuning ===
    public static double ghostSpawnChance = 0.15;
    public static boolean snowMeltEffects = true;
    public static boolean iceEffects = true;
    public static boolean pprismWaterEffects = true;
    public static boolean fniFireEffects = true;
    public static boolean coalFireEffects = true;
    public static boolean cakeHungerEffects = true;
    public static boolean cakeArmorEffects = true;
    public static boolean ectoplasmGhostAvoidance = true;
    public static boolean ectoplasmWallPhasing = true;

    private Config() {}
}
