package com.stonytark.usefultoolsmod.classic;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;

public class ClassicAchievements {
    public static Achievement canonicalTool;
    public static Achievement canonicalArmor;
    public static Achievement spectralInfuser;
    public static Achievement infusedItem;
    public static Achievement ectoplasm;
    public static Achievement ghostEgg;
    public static Achievement explosives;
    public static Achievement dimensionalOre;
    public static Achievement encounterWraith;
    public static Achievement condensedEctoplasm;
    public static Achievement stabilizeGhost;
    public static Achievement locateSpirit;
    public static Achievement remoteFire;
    public static Achievement precisionExcavation;

    public static void register() {
        ectoplasm = achievement("ectoplasm", -1, 2, "ectoplasm", null);
        canonicalTool = achievement("canonical_tool", 0, 0, "rgold_pickaxe", null);
        canonicalArmor = achievement("canonical_armor", 2, 0, "ecto_chestplate", canonicalTool);
        spectralInfuser = blockAchievement("spectral_infuser", 1, 2, "spectral_infuser", ectoplasm);
        infusedItem = achievement("infused_item", 3, 2, "ecto_sword", spectralInfuser);
        ghostEgg = achievement("ghost_egg", -2, 3, "ghost_spawn_egg", ectoplasm);
        explosives = achievement("explosives", 3, 0, "grenade", canonicalTool);
        dimensionalOre = blockAchievement("dimensional_ore", -3, 0, "rgold_end_ore", null);
        encounterWraith = achievement("encounter_wraith", -3, 5, "wraith_spawn_egg", ectoplasm);
        condensedEctoplasm = achievement("condensed_ectoplasm", -1, 5, "condensed_ectoplasm", encounterWraith);
        stabilizeGhost = blockAchievement("stabilize_ghost", 1, 5, "ectoplasm_lantern", ghostEgg);
        locateSpirit = achievement("locate_spirit", 3, 5, "spectral_resonator", stabilizeGhost);
        remoteFire = achievement("remote_fire", 5, 3, "remote_detonator", explosives);
        precisionExcavation = blockAchievement("precision_excavation", 5, 1, "mining_charge", remoteFire);
        AchievementPage.registerAchievementPage(new AchievementPage("Useful Tools 2.3.1",
                canonicalTool, canonicalArmor, spectralInfuser, infusedItem,
                ectoplasm, ghostEgg, explosives, dimensionalOre, encounterWraith, condensedEctoplasm,
                stabilizeGhost, locateSpirit, remoteFire, precisionExcavation));
    }

    private static Achievement achievement(String id, int x, int y, String item, Achievement parent) {
        return new Achievement("achievement.usefultoolsmod." + id, "usefultoolsmod." + id,
                x, y, ClassicGeneratedCatalog.ITEMS.get(item), parent).registerStat();
    }

    private static Achievement blockAchievement(String id, int x, int y, String block, Achievement parent) {
        return new Achievement("achievement.usefultoolsmod." + id, "usefultoolsmod." + id,
                x, y, new ItemStack(ClassicGeneratedCatalog.BLOCKS.get(block)), parent).registerStat();
    }

    @SubscribeEvent
    public void onCraft(PlayerEvent.ItemCraftedEvent event) {
        String id = id(event.crafting);
        if (id == null) return;
        if (isTool(id)) event.player.addStat(canonicalTool, 1);
        if (isArmor(id)) event.player.addStat(canonicalArmor, 1);
        if ("spectral_infuser".equals(id)) event.player.addStat(spectralInfuser, 1);
        if ("grenade".equals(id) || "dynamite".equals(id)) event.player.addStat(explosives, 1);
    }

    @SubscribeEvent
    public void onPickup(PlayerEvent.ItemPickupEvent event) {
        String id = id(event.pickedUp.getEntityItem());
        if ("ectoplasm".equals(id)) event.player.addStat(ectoplasm, 1);
        if ("condensed_ectoplasm".equals(id)) event.player.addStat(condensedEctoplasm, 1);
        if ("ghost_spawn_egg".equals(id)) event.player.addStat(ghostEgg, 1);
        if (id != null && (id.equals("rgoldore") || id.startsWith("rgold_") && id.endsWith("_ore")))
            event.player.addStat(dimensionalOre, 1);
    }

    static boolean isTool(String id) {
        return id.endsWith("_sword") || id.endsWith("_pickaxe") || id.endsWith("_shovel")
                || id.endsWith("_axe") || id.endsWith("_hoe");
    }

    static boolean isArmor(String id) {
        return id.endsWith("_helmet") || id.endsWith("_chestplate")
                || id.endsWith("_leggings") || id.endsWith("_boots");
    }

    private static String id(ItemStack stack) {
        if (stack == null) return null;
        GameRegistry.UniqueIdentifier identifier = GameRegistry.findUniqueIdentifierFor(stack.getItem());
        return identifier != null && "usefultoolsmod".equals(identifier.modId) ? identifier.name : null;
    }
}
