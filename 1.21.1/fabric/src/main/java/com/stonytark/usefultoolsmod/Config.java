package com.stonytark.usefultoolsmod;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.loader.api.FabricLoader;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Persistent configuration via plain JSON.
 *
 * Loaded from {@code <gamedir>/config/usefultoolsmod.json}. All 116 public-static
 * fields (declared below) are written/read by reflection — adding a new field
 * needs no boilerplate. Missing keys in the file are left at their default values.
 *
 * The built-in config screen is generated from the same reflected field list, so
 * persistence and UI cannot silently drift apart.
 */
public class Config {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static Path configPath() {
        return FabricLoader.getInstance().getConfigDir().resolve("usefultoolsmod.json");
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
                if (!isConfigField(f)) continue;
                String key = f.getName();
                // The Fabric 2.2.x line used the longer pointedDripstone names.
                // Accept them forever, but save only the canonical names.
                if (!root.has(key) && key.equals("dripstoneEnabled") && root.has("pointedDripstoneEnabled")) {
                    key = "pointedDripstoneEnabled";
                } else if (!root.has(key) && key.equals("dripstoneEffects") && root.has("pointedDripstoneEffects")) {
                    key = "pointedDripstoneEffects";
                }
                if (!root.has(key)) continue;
                try {
                    Class<?> type = f.getType();
                    if (type == boolean.class) f.setBoolean(null, root.get(key).getAsBoolean());
                    else if (type == int.class) f.setInt(null, root.get(key).getAsInt());
                    else if (type == double.class) f.setDouble(null, validatedDouble(f.getName(), root.get(key).getAsDouble()));
                    else if (type == float.class) f.setFloat(null, root.get(key).getAsFloat());
                    else if (type == long.class) f.setLong(null, root.get(key).getAsLong());
                    else if (type == String.class) f.set(null, root.get(key).getAsString());
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
            Path temporary = path.resolveSibling(path.getFileName() + ".tmp");
            Files.writeString(temporary, GSON.toJson(root));
            try {
                Files.move(temporary, path, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
            } catch (AtomicMoveNotSupportedException ignored) {
                Files.move(temporary, path, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (Exception e) {
            UsefultoolsMod.LOGGER.warn("Config save failed: {}", e.toString());
        }
    }

    private static boolean isConfigField(Field f) {
        int m = f.getModifiers();
        return Modifier.isPublic(m) && Modifier.isStatic(m) && !Modifier.isFinal(m);
    }

    private static double validatedDouble(String name, double value) {
        if (name.equals("ghostSpawnChance")) return Math.max(0.0, Math.min(1.0, value));
        return value;
    }

    /** Stable descriptor consumed by the built-in screen and validation tests. */
    public record Option(Field field, String category, String label, double minimum, double maximum) {
        public Object get() {
            try {
                return field.get(null);
            } catch (IllegalAccessException e) {
                throw new IllegalStateException("Cannot read config option " + field.getName(), e);
            }
        }

        public void set(Object value) {
            try {
                if (field.getType() == double.class) {
                    field.setDouble(null, validatedDouble(field.getName(), ((Number) value).doubleValue()));
                } else {
                    field.set(null, value);
                }
            } catch (IllegalAccessException e) {
                throw new IllegalStateException("Cannot write config option " + field.getName(), e);
            }
        }
    }

    private static final List<Option> OPTIONS = buildOptions();

    public static List<Option> options() {
        return OPTIONS;
    }

    private static List<Option> buildOptions() {
        List<Option> result = new ArrayList<>();
        for (Field field : Config.class.getDeclaredFields()) {
            if (!isConfigField(field)) continue;
            String name = field.getName();
            double min = name.equals("ghostSpawnChance") ? 0.0 : -Double.MAX_VALUE;
            double max = name.equals("ghostSpawnChance") ? 1.0 : Double.MAX_VALUE;
            result.add(new Option(field, category(name), prettify(name), min, max));
        }
        return Collections.unmodifiableList(result);
    }

    private static String category(String name) {
        if (name.equals("ghostSpawnChance") || name.endsWith("Effects") || name.endsWith("Avoidance")
                || name.endsWith("Phasing") || name.endsWith("Drain") || name.endsWith("Sticky")
                || name.endsWith("Thorns") || name.endsWith("Teleport")) return "Effects";
        if (name.contains("bread") || name.contains("Kelp") || name.contains("Flesh") || name.contains("Melon")
                || name.contains("Berry") || name.contains("Pie") || name.contains("mushroom")
                || name.contains("pufferfish") || name.contains("honey") || name.contains("Fruit")
                || name.contains("Apple") || name.startsWith("cake")) return "Food Sets";
        return "Content Sets";
    }

    private static String prettify(String raw) {
        String spaced = raw.replaceAll("([a-z0-9])([A-Z])", "$1 $2").replace('_', ' ');
        return Character.toUpperCase(spaced.charAt(0)) + spaced.substring(1);
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
