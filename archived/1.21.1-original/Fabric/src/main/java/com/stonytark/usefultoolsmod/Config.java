package com.stonytark.usefultoolsmod;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Fabric 1.21.1 Config — loads/saves a JSON config file in the config directory.
 * All fields default to true so everything works even if the file is missing or malformed.
 */
public class Config {

    // =====================================================================
    //  Set toggles — "enabled" hides the entire set from the creative tab
    // =====================================================================

    // --- Explosives (Dynamite, Grenade) ---
    public static boolean explosivesEnabled = true;

    // --- Obsidian (Rough Obsidian tools, Polished Obsidian tools, Obsidian armor) ---
    public static boolean obsidianEnabled = true;

    // --- Emerald (Polished Emerald tools, Rough Emerald tools, Emerald armor) ---
    public static boolean emeraldEnabled = true;

    // --- Lapis (Reinforced Lapis tools + armor) ---
    public static boolean lapisEnabled = true;

    // --- Ferrous Gold (tools, armor, ores, blocks) ---
    public static boolean ferrousGoldEnabled = true;

    // --- Hardened Redstone (tools + armor) ---
    public static boolean hardenedRedstoneEnabled = true;

    // --- Hardened Glowstone (tools + armor) ---
    public static boolean hardenedGlowstoneEnabled = true;

    // --- Overpower (tools + armor) ---
    public static boolean overpowerEnabled = true;

    // --- Ghost + Ectoplasm ---
    public static boolean ghostEnabled = true;

    // --- Spectral Infuser ---
    public static boolean spectralInfuserEnabled = true;
    public static boolean infusedToolEffects = true;

    // --- Ectoplasm Set (tools + armor) ---
    public static boolean ectoplasmSetEnabled = true;
    public static boolean ectoplasmGhostAvoidance = true;
    public static boolean ectoplasmWallPhasing = true;

    // --- Raw Metal Rough (raw gold/copper/iron/rgold/scrap tools) ---
    public static boolean rawMetalRoughEnabled = true;

    // --- Rough Crystal (Rough Amethyst, Rough Quartz, Rough Prismarine) ---
    public static boolean roughCrystalEnabled = true;

    // --- Snow (tools only) ---
    public static boolean snowEnabled = true;

    // --- Polished Crystal (Calcite Amethyst, Polished Quartz — tools + armor) ---
    public static boolean polishedCrystalEnabled = true;

    // --- Ice / Glacial (tools + armor) ---
    public static boolean iceEnabled = true;

    // --- Polished Prismarine (tools + armor) ---
    public static boolean pprismEnabled = true;

    // --- Flint (Rough Flint tools) ---
    public static boolean flintEnabled = true;

    // --- Flint-Iron / FNI (tools + armor) ---
    public static boolean fniEnabled = true;

    // --- Wood Variants (11 wood-type tool sets) ---
    public static boolean woodVariantsEnabled = true;

    // --- Stone Variants (13 stone-type tool sets) ---
    public static boolean stoneVariantsEnabled = true;

    // --- Leather (tools only) ---
    public static boolean leatherEnabled = true;

    // --- Coal (tools + armor) ---
    public static boolean coalEnabled = true;

    // --- Cake (tools + armor) ---
    public static boolean cakeEnabled = true;

    // --- Food sets ---
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

    // =====================================================================
    //  Effect flags
    // =====================================================================
    public static boolean opToolEffectsEnabled = true;
    public static boolean opArmorEffectsEnabled = true;
    public static double ghostSpawnChance = 0.15;
    public static boolean snowMeltEffects = true;
    public static boolean iceEffects = true;
    public static boolean pprismWaterEffects = true;
    public static boolean fniFireEffects = true;
    public static boolean coalFireEffects = true;
    public static boolean cakeHungerEffects = true;
    public static boolean cakeArmorEffects = true;

    // =====================================================================
    //  Vanilla material sets
    // =====================================================================
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
    public static boolean pointedDripstoneEnabled = true;
    public static boolean pointedDripstoneEffects = true;
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

    // =====================================================================
    //  JSON config I/O
    // =====================================================================

    private static final String CONFIG_FILE = "usefultoolsmod.json";
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    /** Call once during mod initialization to load (or create) the config file. */
    public static void load() {
        Path configDir = FabricLoader.getInstance().getConfigDir();
        Path configPath = configDir.resolve(CONFIG_FILE);

        if (Files.exists(configPath)) {
            try (Reader reader = Files.newBufferedReader(configPath)) {
                ConfigData data = GSON.fromJson(reader, ConfigData.class);
                if (data != null) {
                    data.apply();
                }
            } catch (Exception e) {
                UsefultoolsMod.LOGGER.warn("Failed to read config file, using defaults: {}", e.getMessage());
            }
        }

        // Always save so new fields get written and formatting is up to date
        save(configPath);
    }

    private static void save(Path configPath) {
        try (Writer writer = Files.newBufferedWriter(configPath)) {
            GSON.toJson(ConfigData.capture(), writer);
        } catch (IOException e) {
            UsefultoolsMod.LOGGER.warn("Failed to write config file: {}", e.getMessage());
        }
    }

    // =====================================================================
    //  ConfigData — serializable mirror of the static fields
    // =====================================================================

    @SuppressWarnings("unused") // fields are accessed by Gson reflection
    private static class ConfigData {
        // Set toggles
        boolean explosivesEnabled = true;
        boolean obsidianEnabled = true;
        boolean emeraldEnabled = true;
        boolean lapisEnabled = true;
        boolean ferrousGoldEnabled = true;
        boolean hardenedRedstoneEnabled = true;
        boolean hardenedGlowstoneEnabled = true;
        boolean overpowerEnabled = true;
        boolean ghostEnabled = true;
        boolean spectralInfuserEnabled = true;
        boolean infusedToolEffects = true;
        boolean ectoplasmSetEnabled = true;
        boolean ectoplasmGhostAvoidance = true;
        boolean ectoplasmWallPhasing = true;
        boolean rawMetalRoughEnabled = true;
        boolean roughCrystalEnabled = true;
        boolean snowEnabled = true;
        boolean polishedCrystalEnabled = true;
        boolean iceEnabled = true;
        boolean pprismEnabled = true;
        boolean flintEnabled = true;
        boolean fniEnabled = true;
        boolean woodVariantsEnabled = true;
        boolean stoneVariantsEnabled = true;
        boolean leatherEnabled = true;
        boolean coalEnabled = true;
        boolean cakeEnabled = true;
        boolean foodHungerDrain = true;
        boolean breadEnabled = true;
        boolean breadArmorEffects = true;
        boolean driedKelpEnabled = true;
        boolean driedKelpArmorEffects = true;
        boolean rottenFleshEnabled = true;
        boolean rottenFleshArmorEffects = true;
        boolean rottenFleshUndeadNeutral = true;
        boolean melonEnabled = true;
        boolean melonArmorEffects = true;
        boolean sweetBerryEnabled = true;
        boolean sweetBerryArmorEffects = true;
        boolean sweetBerryThorns = true;
        boolean pumpkinPieEnabled = true;
        boolean pumpkinPieArmorEffects = true;
        boolean pumpkinPieEndermanAvoidance = true;
        boolean mushroomEnabled = true;
        boolean mushroomArmorEffects = true;
        boolean mushroomSporeCloud = true;
        boolean pufferfishEnabled = true;
        boolean pufferfishArmorEffects = true;
        boolean pufferfishPoisonAura = true;
        boolean honeyEnabled = true;
        boolean honeyArmorEffects = true;
        boolean honeySticky = true;
        boolean chorusFruitEnabled = true;
        boolean chorusFruitArmorEffects = true;
        boolean chorusFruitTeleport = true;
        boolean goldenAppleEnabled = true;
        boolean goldenAppleArmorEffects = true;

        // Effect flags
        boolean opToolEffectsEnabled = true;
        boolean opArmorEffectsEnabled = true;
        double ghostSpawnChance = 0.15;
        boolean snowMeltEffects = true;
        boolean iceEffects = true;
        boolean pprismWaterEffects = true;
        boolean fniFireEffects = true;
        boolean coalFireEffects = true;
        boolean cakeHungerEffects = true;
        boolean cakeArmorEffects = true;

        // Vanilla material sets
        boolean paperEnabled = true;
        boolean paperEffects = true;
        boolean featherEnabled = true;
        boolean featherEffects = true;
        boolean glassEnabled = true;
        boolean glassEffects = true;
        boolean rabbitHideEnabled = true;
        boolean rabbitHideEffects = true;
        boolean cactusEnabled = true;
        boolean cactusEffects = true;
        boolean spongeEnabled = true;
        boolean spongeEffects = true;
        boolean boneEnabled = true;
        boolean boneEffects = true;
        boolean clayEnabled = true;
        boolean clayEffects = true;
        boolean netherWartEnabled = true;
        boolean netherWartEffects = true;
        boolean brickEnabled = true;
        boolean netherBrickEnabled = true;
        boolean netherBrickEffects = true;
        boolean pointedDripstoneEnabled = true;
        boolean pointedDripstoneEffects = true;
        boolean copperEnabled = true;
        boolean copperEffects = true;
        boolean phantomEnabled = true;
        boolean phantomEffects = true;
        boolean magmaCreamEnabled = true;
        boolean magmaCreamEffects = true;
        boolean slimeEnabled = true;
        boolean slimeEffects = true;
        boolean blazeEnabled = true;
        boolean blazeEffects = true;
        boolean nautilusEnabled = true;
        boolean nautilusEffects = true;
        boolean purpurEnabled = true;
        boolean purpurEffects = true;
        boolean ghastTearEnabled = true;
        boolean ghastTearEffects = true;
        boolean eyeOfEnderEnabled = true;
        boolean eyeOfEnderEffects = true;
        boolean shulkerEnabled = true;
        boolean shulkerEffects = true;
        boolean turtleScuteEnabled = true;
        boolean turtleScuteEffects = true;
        boolean echoShardEnabled = true;
        boolean echoShardEffects = true;
        boolean dragonBreathEnabled = true;
        boolean dragonBreathEffects = true;

        /** Copy all ConfigData fields -> static Config fields. */
        void apply() {
            Config.explosivesEnabled = explosivesEnabled;
            Config.obsidianEnabled = obsidianEnabled;
            Config.emeraldEnabled = emeraldEnabled;
            Config.lapisEnabled = lapisEnabled;
            Config.ferrousGoldEnabled = ferrousGoldEnabled;
            Config.hardenedRedstoneEnabled = hardenedRedstoneEnabled;
            Config.hardenedGlowstoneEnabled = hardenedGlowstoneEnabled;
            Config.overpowerEnabled = overpowerEnabled;
            Config.ghostEnabled = ghostEnabled;
            Config.spectralInfuserEnabled = spectralInfuserEnabled;
            Config.infusedToolEffects = infusedToolEffects;
            Config.ectoplasmSetEnabled = ectoplasmSetEnabled;
            Config.ectoplasmGhostAvoidance = ectoplasmGhostAvoidance;
            Config.ectoplasmWallPhasing = ectoplasmWallPhasing;
            Config.rawMetalRoughEnabled = rawMetalRoughEnabled;
            Config.roughCrystalEnabled = roughCrystalEnabled;
            Config.snowEnabled = snowEnabled;
            Config.polishedCrystalEnabled = polishedCrystalEnabled;
            Config.iceEnabled = iceEnabled;
            Config.pprismEnabled = pprismEnabled;
            Config.flintEnabled = flintEnabled;
            Config.fniEnabled = fniEnabled;
            Config.woodVariantsEnabled = woodVariantsEnabled;
            Config.stoneVariantsEnabled = stoneVariantsEnabled;
            Config.leatherEnabled = leatherEnabled;
            Config.coalEnabled = coalEnabled;
            Config.cakeEnabled = cakeEnabled;
            Config.foodHungerDrain = foodHungerDrain;
            Config.breadEnabled = breadEnabled;
            Config.breadArmorEffects = breadArmorEffects;
            Config.driedKelpEnabled = driedKelpEnabled;
            Config.driedKelpArmorEffects = driedKelpArmorEffects;
            Config.rottenFleshEnabled = rottenFleshEnabled;
            Config.rottenFleshArmorEffects = rottenFleshArmorEffects;
            Config.rottenFleshUndeadNeutral = rottenFleshUndeadNeutral;
            Config.melonEnabled = melonEnabled;
            Config.melonArmorEffects = melonArmorEffects;
            Config.sweetBerryEnabled = sweetBerryEnabled;
            Config.sweetBerryArmorEffects = sweetBerryArmorEffects;
            Config.sweetBerryThorns = sweetBerryThorns;
            Config.pumpkinPieEnabled = pumpkinPieEnabled;
            Config.pumpkinPieArmorEffects = pumpkinPieArmorEffects;
            Config.pumpkinPieEndermanAvoidance = pumpkinPieEndermanAvoidance;
            Config.mushroomEnabled = mushroomEnabled;
            Config.mushroomArmorEffects = mushroomArmorEffects;
            Config.mushroomSporeCloud = mushroomSporeCloud;
            Config.pufferfishEnabled = pufferfishEnabled;
            Config.pufferfishArmorEffects = pufferfishArmorEffects;
            Config.pufferfishPoisonAura = pufferfishPoisonAura;
            Config.honeyEnabled = honeyEnabled;
            Config.honeyArmorEffects = honeyArmorEffects;
            Config.honeySticky = honeySticky;
            Config.chorusFruitEnabled = chorusFruitEnabled;
            Config.chorusFruitArmorEffects = chorusFruitArmorEffects;
            Config.chorusFruitTeleport = chorusFruitTeleport;
            Config.goldenAppleEnabled = goldenAppleEnabled;
            Config.goldenAppleArmorEffects = goldenAppleArmorEffects;
            Config.opToolEffectsEnabled = opToolEffectsEnabled;
            Config.opArmorEffectsEnabled = opArmorEffectsEnabled;
            Config.ghostSpawnChance = ghostSpawnChance;
            Config.snowMeltEffects = snowMeltEffects;
            Config.iceEffects = iceEffects;
            Config.pprismWaterEffects = pprismWaterEffects;
            Config.fniFireEffects = fniFireEffects;
            Config.coalFireEffects = coalFireEffects;
            Config.cakeHungerEffects = cakeHungerEffects;
            Config.cakeArmorEffects = cakeArmorEffects;
            Config.paperEnabled = paperEnabled;
            Config.paperEffects = paperEffects;
            Config.featherEnabled = featherEnabled;
            Config.featherEffects = featherEffects;
            Config.glassEnabled = glassEnabled;
            Config.glassEffects = glassEffects;
            Config.rabbitHideEnabled = rabbitHideEnabled;
            Config.rabbitHideEffects = rabbitHideEffects;
            Config.cactusEnabled = cactusEnabled;
            Config.cactusEffects = cactusEffects;
            Config.spongeEnabled = spongeEnabled;
            Config.spongeEffects = spongeEffects;
            Config.boneEnabled = boneEnabled;
            Config.boneEffects = boneEffects;
            Config.clayEnabled = clayEnabled;
            Config.clayEffects = clayEffects;
            Config.netherWartEnabled = netherWartEnabled;
            Config.netherWartEffects = netherWartEffects;
            Config.brickEnabled = brickEnabled;
            Config.netherBrickEnabled = netherBrickEnabled;
            Config.netherBrickEffects = netherBrickEffects;
            Config.pointedDripstoneEnabled = pointedDripstoneEnabled;
            Config.pointedDripstoneEffects = pointedDripstoneEffects;
            Config.copperEnabled = copperEnabled;
            Config.copperEffects = copperEffects;
            Config.phantomEnabled = phantomEnabled;
            Config.phantomEffects = phantomEffects;
            Config.magmaCreamEnabled = magmaCreamEnabled;
            Config.magmaCreamEffects = magmaCreamEffects;
            Config.slimeEnabled = slimeEnabled;
            Config.slimeEffects = slimeEffects;
            Config.blazeEnabled = blazeEnabled;
            Config.blazeEffects = blazeEffects;
            Config.nautilusEnabled = nautilusEnabled;
            Config.nautilusEffects = nautilusEffects;
            Config.purpurEnabled = purpurEnabled;
            Config.purpurEffects = purpurEffects;
            Config.ghastTearEnabled = ghastTearEnabled;
            Config.ghastTearEffects = ghastTearEffects;
            Config.eyeOfEnderEnabled = eyeOfEnderEnabled;
            Config.eyeOfEnderEffects = eyeOfEnderEffects;
            Config.shulkerEnabled = shulkerEnabled;
            Config.shulkerEffects = shulkerEffects;
            Config.turtleScuteEnabled = turtleScuteEnabled;
            Config.turtleScuteEffects = turtleScuteEffects;
            Config.echoShardEnabled = echoShardEnabled;
            Config.echoShardEffects = echoShardEffects;
            Config.dragonBreathEnabled = dragonBreathEnabled;
            Config.dragonBreathEffects = dragonBreathEffects;
        }

        /** Snapshot the current static Config fields -> a new ConfigData. */
        static ConfigData capture() {
            ConfigData d = new ConfigData();
            d.explosivesEnabled = Config.explosivesEnabled;
            d.obsidianEnabled = Config.obsidianEnabled;
            d.emeraldEnabled = Config.emeraldEnabled;
            d.lapisEnabled = Config.lapisEnabled;
            d.ferrousGoldEnabled = Config.ferrousGoldEnabled;
            d.hardenedRedstoneEnabled = Config.hardenedRedstoneEnabled;
            d.hardenedGlowstoneEnabled = Config.hardenedGlowstoneEnabled;
            d.overpowerEnabled = Config.overpowerEnabled;
            d.ghostEnabled = Config.ghostEnabled;
            d.spectralInfuserEnabled = Config.spectralInfuserEnabled;
            d.infusedToolEffects = Config.infusedToolEffects;
            d.ectoplasmSetEnabled = Config.ectoplasmSetEnabled;
            d.ectoplasmGhostAvoidance = Config.ectoplasmGhostAvoidance;
            d.ectoplasmWallPhasing = Config.ectoplasmWallPhasing;
            d.rawMetalRoughEnabled = Config.rawMetalRoughEnabled;
            d.roughCrystalEnabled = Config.roughCrystalEnabled;
            d.snowEnabled = Config.snowEnabled;
            d.polishedCrystalEnabled = Config.polishedCrystalEnabled;
            d.iceEnabled = Config.iceEnabled;
            d.pprismEnabled = Config.pprismEnabled;
            d.flintEnabled = Config.flintEnabled;
            d.fniEnabled = Config.fniEnabled;
            d.woodVariantsEnabled = Config.woodVariantsEnabled;
            d.stoneVariantsEnabled = Config.stoneVariantsEnabled;
            d.leatherEnabled = Config.leatherEnabled;
            d.coalEnabled = Config.coalEnabled;
            d.cakeEnabled = Config.cakeEnabled;
            d.foodHungerDrain = Config.foodHungerDrain;
            d.breadEnabled = Config.breadEnabled;
            d.breadArmorEffects = Config.breadArmorEffects;
            d.driedKelpEnabled = Config.driedKelpEnabled;
            d.driedKelpArmorEffects = Config.driedKelpArmorEffects;
            d.rottenFleshEnabled = Config.rottenFleshEnabled;
            d.rottenFleshArmorEffects = Config.rottenFleshArmorEffects;
            d.rottenFleshUndeadNeutral = Config.rottenFleshUndeadNeutral;
            d.melonEnabled = Config.melonEnabled;
            d.melonArmorEffects = Config.melonArmorEffects;
            d.sweetBerryEnabled = Config.sweetBerryEnabled;
            d.sweetBerryArmorEffects = Config.sweetBerryArmorEffects;
            d.sweetBerryThorns = Config.sweetBerryThorns;
            d.pumpkinPieEnabled = Config.pumpkinPieEnabled;
            d.pumpkinPieArmorEffects = Config.pumpkinPieArmorEffects;
            d.pumpkinPieEndermanAvoidance = Config.pumpkinPieEndermanAvoidance;
            d.mushroomEnabled = Config.mushroomEnabled;
            d.mushroomArmorEffects = Config.mushroomArmorEffects;
            d.mushroomSporeCloud = Config.mushroomSporeCloud;
            d.pufferfishEnabled = Config.pufferfishEnabled;
            d.pufferfishArmorEffects = Config.pufferfishArmorEffects;
            d.pufferfishPoisonAura = Config.pufferfishPoisonAura;
            d.honeyEnabled = Config.honeyEnabled;
            d.honeyArmorEffects = Config.honeyArmorEffects;
            d.honeySticky = Config.honeySticky;
            d.chorusFruitEnabled = Config.chorusFruitEnabled;
            d.chorusFruitArmorEffects = Config.chorusFruitArmorEffects;
            d.chorusFruitTeleport = Config.chorusFruitTeleport;
            d.goldenAppleEnabled = Config.goldenAppleEnabled;
            d.goldenAppleArmorEffects = Config.goldenAppleArmorEffects;
            d.opToolEffectsEnabled = Config.opToolEffectsEnabled;
            d.opArmorEffectsEnabled = Config.opArmorEffectsEnabled;
            d.ghostSpawnChance = Config.ghostSpawnChance;
            d.snowMeltEffects = Config.snowMeltEffects;
            d.iceEffects = Config.iceEffects;
            d.pprismWaterEffects = Config.pprismWaterEffects;
            d.fniFireEffects = Config.fniFireEffects;
            d.coalFireEffects = Config.coalFireEffects;
            d.cakeHungerEffects = Config.cakeHungerEffects;
            d.cakeArmorEffects = Config.cakeArmorEffects;
            d.paperEnabled = Config.paperEnabled;
            d.paperEffects = Config.paperEffects;
            d.featherEnabled = Config.featherEnabled;
            d.featherEffects = Config.featherEffects;
            d.glassEnabled = Config.glassEnabled;
            d.glassEffects = Config.glassEffects;
            d.rabbitHideEnabled = Config.rabbitHideEnabled;
            d.rabbitHideEffects = Config.rabbitHideEffects;
            d.cactusEnabled = Config.cactusEnabled;
            d.cactusEffects = Config.cactusEffects;
            d.spongeEnabled = Config.spongeEnabled;
            d.spongeEffects = Config.spongeEffects;
            d.boneEnabled = Config.boneEnabled;
            d.boneEffects = Config.boneEffects;
            d.clayEnabled = Config.clayEnabled;
            d.clayEffects = Config.clayEffects;
            d.netherWartEnabled = Config.netherWartEnabled;
            d.netherWartEffects = Config.netherWartEffects;
            d.brickEnabled = Config.brickEnabled;
            d.netherBrickEnabled = Config.netherBrickEnabled;
            d.netherBrickEffects = Config.netherBrickEffects;
            d.pointedDripstoneEnabled = Config.pointedDripstoneEnabled;
            d.pointedDripstoneEffects = Config.pointedDripstoneEffects;
            d.copperEnabled = Config.copperEnabled;
            d.copperEffects = Config.copperEffects;
            d.phantomEnabled = Config.phantomEnabled;
            d.phantomEffects = Config.phantomEffects;
            d.magmaCreamEnabled = Config.magmaCreamEnabled;
            d.magmaCreamEffects = Config.magmaCreamEffects;
            d.slimeEnabled = Config.slimeEnabled;
            d.slimeEffects = Config.slimeEffects;
            d.blazeEnabled = Config.blazeEnabled;
            d.blazeEffects = Config.blazeEffects;
            d.nautilusEnabled = Config.nautilusEnabled;
            d.nautilusEffects = Config.nautilusEffects;
            d.purpurEnabled = Config.purpurEnabled;
            d.purpurEffects = Config.purpurEffects;
            d.ghastTearEnabled = Config.ghastTearEnabled;
            d.ghastTearEffects = Config.ghastTearEffects;
            d.eyeOfEnderEnabled = Config.eyeOfEnderEnabled;
            d.eyeOfEnderEffects = Config.eyeOfEnderEffects;
            d.shulkerEnabled = Config.shulkerEnabled;
            d.shulkerEffects = Config.shulkerEffects;
            d.turtleScuteEnabled = Config.turtleScuteEnabled;
            d.turtleScuteEffects = Config.turtleScuteEffects;
            d.echoShardEnabled = Config.echoShardEnabled;
            d.echoShardEffects = Config.echoShardEffects;
            d.dragonBreathEnabled = Config.dragonBreathEnabled;
            d.dragonBreathEffects = Config.dragonBreathEffects;
            return d;
        }
    }
}
