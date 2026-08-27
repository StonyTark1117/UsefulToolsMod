package com.stonytark.usefultoolsmod.classic;

import com.krazykid1117.Main.Mainregistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

/** Native Forge 1.7.10 registrations for the 2.3 spectral/explosives feature set. */
public final class ClassicFeatureRegistry {
    public static Item condensedEctoplasm;
    public static Item wraithSpawnEgg;
    public static Item spectralResonator;
    public static Item stickyDynamite;
    public static Item remoteDetonator;
    public static Block ectoplasmLantern;
    /** Deprecated registry alias retained for worlds saved before the rename. */
    public static Block legacySoulLantern;
    public static Block miningCharge;

    private ClassicFeatureRegistry() {}

    public static void register() {
        condensedEctoplasm = ClassicGeneratedCatalog.ITEMS.get("condensed_ectoplasm");
        wraithSpawnEgg = ClassicGeneratedCatalog.ITEMS.get("wraith_spawn_egg");
        spectralResonator = ClassicGeneratedCatalog.ITEMS.get("spectral_resonator");
        stickyDynamite = ClassicGeneratedCatalog.ITEMS.get("sticky_dynamite");
        remoteDetonator = ClassicGeneratedCatalog.ITEMS.get("remote_detonator");
        ectoplasmLantern = ClassicGeneratedCatalog.BLOCKS.get("ectoplasm_lantern");
        legacySoulLantern = ClassicGeneratedCatalog.BLOCKS.get("soul_lantern");
        miningCharge = ClassicGeneratedCatalog.BLOCKS.get("mining_charge");
        cpw.mods.fml.common.registry.GameRegistry.registerTileEntity(TileMiningCharge.class, "usefultoolsmod:mining_charge");

        EntityRegistry.registerModEntity(EntityWraith.class, "wraith", 2,
                Mainregistry.modInstance, 96, 3, true);
        EntityRegistry.registerModEntity(EntityStickyDynamite.class, "sticky_dynamite", 3,
                Mainregistry.modInstance, 64, 10, true);
    }
}
