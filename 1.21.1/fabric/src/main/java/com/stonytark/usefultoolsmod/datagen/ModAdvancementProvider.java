package com.stonytark.usefultoolsmod.datagen;

import com.stonytark.usefultoolsmod.UsefultoolsMod;
import com.stonytark.usefultoolsmod.block.ModBlocks;
import com.stonytark.usefultoolsmod.item.ModItems;
import com.stonytark.usefultoolsmod.trigger.CoalToolIgnitedTrigger;
import com.stonytark.usefultoolsmod.trigger.GhostNearbyTrigger;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.criterion.ConsumeItemCriterion;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.predicate.entity.LootContextPredicate;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

/**
 * Fabric 1.20.1 Advancement Data Generator
 * Generates custom advancement/achievement JSON files
 */
public class ModAdvancementProvider extends FabricAdvancementProvider {

    private static final Identifier BACKGROUND =
            Identifier.of("textures/block/netherrack.png");

    public ModAdvancementProvider(FabricDataOutput output, java.util.concurrent.CompletableFuture<net.minecraft.registry.RegistryWrapper.WrapperLookup> registryLookup) {
        super(output, registryLookup);
    }

    @Override
    public void generateAdvancement(net.minecraft.registry.RegistryWrapper.WrapperLookup lookup, Consumer<net.minecraft.advancement.AdvancementEntry> consumer) {

        // ==================================================================
        // ROOT
        // ==================================================================
        net.minecraft.advancement.AdvancementEntry root = Advancement.Builder.create()
                .display(
                        ModItems.RAW_RGOLD,
                        title("root"),
                        desc("root"),
                        BACKGROUND,
                        AdvancementFrame.TASK,
                        false, false, false
                )
                .criterion("has_raw_rgold", InventoryChangedCriterion.Conditions.items(ModItems.RAW_RGOLD))
                .build(consumer, id("root"));

        // ==================================================================
        // RGOLD ingot (smelt raw ore -> ingot) -- bridges root to tools/armor
        // ==================================================================
        net.minecraft.advancement.AdvancementEntry rgold = Advancement.Builder.create()
                .parent(root)
                .display(ModItems.RGOLD, title("rgold"), desc("rgold"),
                        null, AdvancementFrame.TASK, true, true, false)
                .criterion("has_rgold",
                        InventoryChangedCriterion.Conditions.items(ModItems.RGOLD))
                .build(consumer, id("rgold"));

        // ==================================================================
        // BRANCH: Ore Discovery (from root)
        // ==================================================================
        net.minecraft.advancement.AdvancementEntry oreFound = Advancement.Builder.create()
                .parent(root)
                .display(ModBlocks.RGOLDORE, title("ore_found"), desc("ore_found"),
                        null, AdvancementFrame.TASK, true, true, false)
                .criterion("has_rgold_ore",
                        InventoryChangedCriterion.Conditions.items(ModBlocks.RGOLDORE))
                .build(consumer, id("ore_found"));

        Advancement.Builder.create()
                .parent(oreFound)
                .display(ModBlocks.RGOLD_NETHER_ORE, title("nether_ore"), desc("nether_ore"),
                        null, AdvancementFrame.TASK, true, true, false)
                .criterion("has_nether_ore",
                        InventoryChangedCriterion.Conditions.items(ModBlocks.RGOLD_NETHER_ORE))
                .build(consumer, id("nether_ore"));

        Advancement.Builder.create()
                .parent(oreFound)
                .display(ModBlocks.RGOLD_END_ORE, title("end_ore"), desc("end_ore"),
                        null, AdvancementFrame.TASK, true, true, false)
                .criterion("has_end_ore",
                        InventoryChangedCriterion.Conditions.items(ModBlocks.RGOLD_END_ORE))
                .build(consumer, id("end_ore"));

        Advancement.Builder.create()
                .parent(oreFound)
                .display(ModBlocks.RGOLD_DEEPSLATE_ORE, title("deepslate_ore"), desc("deepslate_ore"),
                        null, AdvancementFrame.TASK, true, true, false)
                .criterion("has_deepslate_ore",
                        InventoryChangedCriterion.Conditions.items(ModBlocks.RGOLD_DEEPSLATE_ORE))
                .build(consumer, id("deepslate_ore"));

        // ==================================================================
        // BRANCH: Reinforced Gold Tools / Armor (from rgold ingot)
        // ==================================================================
        net.minecraft.advancement.AdvancementEntry rgoldSword = Advancement.Builder.create()
                .parent(rgold)
                .display(ModItems.RGOLD_SWORD, title("rgold_tools"), desc("rgold_tools"),
                        null, AdvancementFrame.TASK, true, true, false)
                .criterion("has_rgold_sword",
                        InventoryChangedCriterion.Conditions.items(ModItems.RGOLD_SWORD))
                .build(consumer, id("rgold_tools"));

        Advancement.Builder.create()
                .parent(rgoldSword)
                .display(ModItems.RGOLD_HELMET, title("rgold_armor"), desc("rgold_armor"),
                        null, AdvancementFrame.TASK, true, true, false)
                .criterion("has_rgold_helmet",
                        InventoryChangedCriterion.Conditions.items(ModItems.RGOLD_HELMET))
                .build(consumer, id("rgold_armor"));

        // ==================================================================
        // BRANCH: Obsidian (from root)
        // ==================================================================
        net.minecraft.advancement.AdvancementEntry obshard = Advancement.Builder.create()
                .parent(root)
                .display(ModItems.OBSHARD, title("obshard"), desc("obshard"),
                        null, AdvancementFrame.TASK, true, true, false)
                .criterion("has_obshard",
                        InventoryChangedCriterion.Conditions.items(ModItems.OBSHARD))
                .build(consumer, id("obshard"));

        net.minecraft.advancement.AdvancementEntry obingot = Advancement.Builder.create()
                .parent(obshard)
                .display(ModItems.OBINGOT, title("obingot"), desc("obingot"),
                        null, AdvancementFrame.TASK, true, true, false)
                .criterion("has_obingot",
                        InventoryChangedCriterion.Conditions.items(ModItems.OBINGOT))
                .build(consumer, id("obingot"));

        Advancement.Builder.create()
                .parent(obingot)
                .display(ModItems.ROBSIDIAN_SWORD, title("robsidian"), desc("robsidian"),
                        null, AdvancementFrame.TASK, true, true, false)
                .criterion("has_robsidian_sword",
                        InventoryChangedCriterion.Conditions.items(ModItems.ROBSIDIAN_SWORD))
                .build(consumer, id("robsidian"));

        Advancement.Builder.create()
                .parent(obingot)
                .display(ModItems.POBSIDIAN_SWORD, title("pobsidian"), desc("pobsidian"),
                        null, AdvancementFrame.TASK, true, true, false)
                .criterion("has_pobsidian_sword",
                        InventoryChangedCriterion.Conditions.items(ModItems.POBSIDIAN_SWORD))
                .build(consumer, id("pobsidian"));

        // Full Obsidian armor (CHALLENGE -- requires all 4 pieces)
        Advancement.Builder.create()
                .parent(obingot)
                .display(ModItems.OBSIDIAN_CHESTPLATE, title("obsidian_armor"), desc("obsidian_armor"),
                        null, AdvancementFrame.CHALLENGE, true, true, false)
                .criterion("has_helmet",
                        InventoryChangedCriterion.Conditions.items(ModItems.OBSIDIAN_HELMET))
                .criterion("has_chestplate",
                        InventoryChangedCriterion.Conditions.items(ModItems.OBSIDIAN_CHESTPLATE))
                .criterion("has_leggings",
                        InventoryChangedCriterion.Conditions.items(ModItems.OBSIDIAN_LEGGINGS))
                .criterion("has_boots",
                        InventoryChangedCriterion.Conditions.items(ModItems.OBSIDIAN_BOOTS))
                .criteriaMerger(net.minecraft.advancement.AdvancementRequirements.CriterionMerger.AND)
                .build(consumer, id("obsidian_armor"));

        // ==================================================================
        // BRANCH: Polished Emerald (from root)
        // ==================================================================
        net.minecraft.advancement.AdvancementEntry sem = Advancement.Builder.create()
                .parent(root)
                .display(ModItems.SEM, title("sem"), desc("sem"),
                        null, AdvancementFrame.TASK, true, true, false)
                .criterion("has_sem",
                        InventoryChangedCriterion.Conditions.items(ModItems.SEM))
                .build(consumer, id("sem"));

        Advancement.Builder.create()
                .parent(sem)
                .display(ModItems.REMERALD_SWORD, title("remerald"), desc("remerald"),
                        null, AdvancementFrame.TASK, true, true, false)
                .criterion("has_remerald_sword",
                        InventoryChangedCriterion.Conditions.items(ModItems.REMERALD_SWORD))
                .build(consumer, id("remerald"));

        Advancement.Builder.create()
                .parent(sem)
                .display(ModItems.PEMERALD_SWORD, title("pemerald"), desc("pemerald"),
                        null, AdvancementFrame.TASK, true, true, false)
                .criterion("has_pemerald_sword",
                        InventoryChangedCriterion.Conditions.items(ModItems.PEMERALD_SWORD))
                .build(consumer, id("pemerald"));

        Advancement.Builder.create()
                .parent(sem)
                .display(ModItems.EMERALD_HELMET, title("emerald_armor"), desc("emerald_armor"),
                        null, AdvancementFrame.GOAL, true, true, false)
                .criterion("has_helmet",
                        InventoryChangedCriterion.Conditions.items(ModItems.EMERALD_HELMET))
                .criterion("has_chestplate",
                        InventoryChangedCriterion.Conditions.items(ModItems.EMERALD_CHESTPLATE))
                .criterion("has_leggings",
                        InventoryChangedCriterion.Conditions.items(ModItems.EMERALD_LEGGINGS))
                .criterion("has_boots",
                        InventoryChangedCriterion.Conditions.items(ModItems.EMERALD_BOOTS))
                .criteriaMerger(net.minecraft.advancement.AdvancementRequirements.CriterionMerger.AND)
                .build(consumer, id("emerald_armor"));

        // ==================================================================
        // BRANCH: Hardened Redstone (from root)
        // ==================================================================
        net.minecraft.advancement.AdvancementEntry hred = Advancement.Builder.create()
                .parent(root)
                .display(ModItems.HRED, title("hred"), desc("hred"),
                        null, AdvancementFrame.TASK, true, true, false)
                .criterion("has_hred",
                        InventoryChangedCriterion.Conditions.items(ModItems.HRED))
                .build(consumer, id("hred"));

        Advancement.Builder.create()
                .parent(hred)
                .display(ModItems.HREDSTONE_SWORD, title("hredstone_tools"), desc("hredstone_tools"),
                        null, AdvancementFrame.TASK, true, true, false)
                .criterion("has_hredstone_sword",
                        InventoryChangedCriterion.Conditions.items(ModItems.HREDSTONE_SWORD))
                .build(consumer, id("hredstone_tools"));

        Advancement.Builder.create()
                .parent(hred)
                .display(ModItems.HRED_HELMET, title("hred_armor"), desc("hred_armor"),
                        null, AdvancementFrame.GOAL, true, true, false)
                .criterion("has_helmet",
                        InventoryChangedCriterion.Conditions.items(ModItems.HRED_HELMET))
                .criterion("has_chestplate",
                        InventoryChangedCriterion.Conditions.items(ModItems.HRED_CHESTPLATE))
                .criterion("has_leggings",
                        InventoryChangedCriterion.Conditions.items(ModItems.HRED_LEGGINGS))
                .criterion("has_boots",
                        InventoryChangedCriterion.Conditions.items(ModItems.HRED_BOOTS))
                .criteriaMerger(net.minecraft.advancement.AdvancementRequirements.CriterionMerger.AND)
                .build(consumer, id("hred_armor"));

        // ==================================================================
        // BRANCH: Hardened Glowstone (from root)
        // ==================================================================
        net.minecraft.advancement.AdvancementEntry hglow = Advancement.Builder.create()
                .parent(root)
                .display(ModItems.HGLOW, title("hglow"), desc("hglow"),
                        null, AdvancementFrame.TASK, true, true, false)
                .criterion("has_hglow",
                        InventoryChangedCriterion.Conditions.items(ModItems.HGLOW))
                .build(consumer, id("hglow"));

        Advancement.Builder.create()
                .parent(hglow)
                .display(ModItems.HGLOWSTONE_SWORD, title("hglowstone_tools"), desc("hglowstone_tools"),
                        null, AdvancementFrame.TASK, true, true, false)
                .criterion("has_hglowstone_sword",
                        InventoryChangedCriterion.Conditions.items(ModItems.HGLOWSTONE_SWORD))
                .build(consumer, id("hglowstone_tools"));

        Advancement.Builder.create()
                .parent(hglow)
                .display(ModItems.HGLOW_HELMET, title("hglow_armor"), desc("hglow_armor"),
                        null, AdvancementFrame.GOAL, true, true, false)
                .criterion("has_helmet",
                        InventoryChangedCriterion.Conditions.items(ModItems.HGLOW_HELMET))
                .criterion("has_chestplate",
                        InventoryChangedCriterion.Conditions.items(ModItems.HGLOW_CHESTPLATE))
                .criterion("has_leggings",
                        InventoryChangedCriterion.Conditions.items(ModItems.HGLOW_LEGGINGS))
                .criterion("has_boots",
                        InventoryChangedCriterion.Conditions.items(ModItems.HGLOW_BOOTS))
                .criteriaMerger(net.minecraft.advancement.AdvancementRequirements.CriterionMerger.AND)
                .build(consumer, id("hglow_armor"));

        // ==================================================================
        // BRANCH: Reinforced Lapis (from root)
        // ==================================================================
        net.minecraft.advancement.AdvancementEntry rlapis = Advancement.Builder.create()
                .parent(root)
                .display(ModItems.RLAPIS, title("rlapis"), desc("rlapis"),
                        null, AdvancementFrame.TASK, true, true, false)
                .criterion("has_rlapis",
                        InventoryChangedCriterion.Conditions.items(ModItems.RLAPIS))
                .build(consumer, id("rlapis"));

        Advancement.Builder.create()
                .parent(rlapis)
                .display(ModItems.RLAPIS_SWORD, title("rlapis_tools"), desc("rlapis_tools"),
                        null, AdvancementFrame.TASK, true, true, false)
                .criterion("has_rlapis_sword",
                        InventoryChangedCriterion.Conditions.items(ModItems.RLAPIS_SWORD))
                .build(consumer, id("rlapis_tools"));

        Advancement.Builder.create()
                .parent(rlapis)
                .display(ModItems.RLAPIS_HELMET, title("rlapis_armor"), desc("rlapis_armor"),
                        null, AdvancementFrame.GOAL, true, true, false)
                .criterion("has_helmet",
                        InventoryChangedCriterion.Conditions.items(ModItems.RLAPIS_HELMET))
                .criterion("has_chestplate",
                        InventoryChangedCriterion.Conditions.items(ModItems.RLAPIS_CHESTPLATE))
                .criterion("has_leggings",
                        InventoryChangedCriterion.Conditions.items(ModItems.RLAPIS_LEGGINGS))
                .criterion("has_boots",
                        InventoryChangedCriterion.Conditions.items(ModItems.RLAPIS_BOOTS))
                .criteriaMerger(net.minecraft.advancement.AdvancementRequirements.CriterionMerger.AND)
                .build(consumer, id("rlapis_armor"));

        // ==================================================================
        // BRANCH: Coal Tools / Armor (from root)
        // ==================================================================
        net.minecraft.advancement.AdvancementEntry coalDust = Advancement.Builder.create()
                .parent(root)
                .display(ModItems.COAL_DUST, title("coal_dust"), desc("coal_dust"),
                        null, AdvancementFrame.TASK, true, true, false)
                .criterion("has_coal_dust",
                        InventoryChangedCriterion.Conditions.items(ModItems.COAL_DUST))
                .build(consumer, id("coal_dust"));

        net.minecraft.advancement.AdvancementEntry hardenedCoal = Advancement.Builder.create()
                .parent(coalDust)
                .display(ModItems.HARDENED_COAL, title("hardened_coal"), desc("hardened_coal"),
                        null, AdvancementFrame.TASK, true, true, false)
                .criterion("has_hardened_coal",
                        InventoryChangedCriterion.Conditions.items(ModItems.HARDENED_COAL))
                .build(consumer, id("hardened_coal"));

        net.minecraft.advancement.AdvancementEntry coalTools = Advancement.Builder.create()
                .parent(hardenedCoal)
                .display(ModItems.COAL_PICKAXE, title("coal_tools"), desc("coal_tools"),
                        null, AdvancementFrame.TASK, true, true, false)
                .criterion("has_pickaxe",
                        InventoryChangedCriterion.Conditions.items(ModItems.COAL_PICKAXE))
                .build(consumer, id("coal_tools"));

        // Trial by Fire -- custom trigger (CHALLENGE)
        Advancement.Builder.create()
                .parent(coalTools)
                .display(ModItems.COAL_SWORD, title("coal_trial_by_fire"), desc("coal_trial_by_fire"),
                        null, AdvancementFrame.CHALLENGE, true, true, false)
                .criterion("coal_tool_ignited",
                        com.stonytark.usefultoolsmod.trigger.ModTriggers.COAL_TOOL_IGNITED.create(new CoalToolIgnitedTrigger.Conditions(java.util.Optional.empty())))
                .build(consumer, id("coal_trial_by_fire"));

        net.minecraft.advancement.AdvancementEntry coalArmor = Advancement.Builder.create()
                .parent(hardenedCoal)
                .display(ModItems.COAL_HELMET, title("coal_armor"), desc("coal_armor"),
                        null, AdvancementFrame.TASK, true, true, false)
                .criterion("has_coal_helmet",
                        InventoryChangedCriterion.Conditions.items(ModItems.COAL_HELMET))
                .build(consumer, id("coal_armor"));

        // Burning Commitment -- full coal armor set (CHALLENGE)
        Advancement.Builder.create()
                .parent(coalArmor)
                .display(ModItems.COAL_CHESTPLATE, title("coal_full_set"), desc("coal_full_set"),
                        null, AdvancementFrame.CHALLENGE, true, true, true)
                .criterion("has_helmet",
                        InventoryChangedCriterion.Conditions.items(ModItems.COAL_HELMET))
                .criterion("has_chestplate",
                        InventoryChangedCriterion.Conditions.items(ModItems.COAL_CHESTPLATE))
                .criterion("has_leggings",
                        InventoryChangedCriterion.Conditions.items(ModItems.COAL_LEGGINGS))
                .criterion("has_boots",
                        InventoryChangedCriterion.Conditions.items(ModItems.COAL_BOOTS))
                .criteriaMerger(net.minecraft.advancement.AdvancementRequirements.CriterionMerger.AND)
                .build(consumer, id("coal_full_set"));

        // ==================================================================
        // BRANCH: Explosives (from root)
        // ==================================================================
        net.minecraft.advancement.AdvancementEntry grenade = Advancement.Builder.create()
                .parent(root)
                .display(ModItems.GRENADE, title("grenade"), desc("grenade"),
                        null, AdvancementFrame.TASK, true, true, false)
                .criterion("has_grenade",
                        InventoryChangedCriterion.Conditions.items(ModItems.GRENADE))
                .build(consumer, id("grenade"));

        Advancement.Builder.create()
                .parent(grenade)
                .display(ModItems.DYNAMITE, title("dynamite"), desc("dynamite"),
                        null, AdvancementFrame.TASK, true, true, false)
                .criterion("has_dynamite",
                        InventoryChangedCriterion.Conditions.items(ModItems.DYNAMITE))
                .build(consumer, id("dynamite"));

        // ==================================================================
        // BRANCH: Ghost (from root)
        // ==================================================================
        net.minecraft.advancement.AdvancementEntry ghostEncounter = Advancement.Builder.create()
                .parent(root)
                .display(ModItems.GHOST_SPAWN_EGG, title("ghost_encounter"), desc("ghost_encounter"),
                        null, AdvancementFrame.TASK, true, true, false)
                .criterion("ghost_nearby",
                        com.stonytark.usefultoolsmod.trigger.ModTriggers.GHOST_NEARBY.create(new GhostNearbyTrigger.Conditions(java.util.Optional.empty())))
                .build(consumer, id("ghost_encounter"));

        net.minecraft.advancement.AdvancementEntry ghostCompanion = Advancement.Builder.create()
                .parent(ghostEncounter)
                .display(ModItems.GHOST_SPAWN_EGG, title("ghost_companion"), desc("ghost_companion"),
                        null, AdvancementFrame.GOAL, true, true, true)
                .criterion("ghost_nearby_again",
                        com.stonytark.usefultoolsmod.trigger.ModTriggers.GHOST_NEARBY.create(new GhostNearbyTrigger.Conditions(java.util.Optional.empty())))
                .build(consumer, id("ghost_companion"));

        // ------------------------------------------------------------------
        // SUB-BRANCH: Ectoplasm -> Refined Ectoplasm -> Ecto tools/armor
        //                                             -> Spectral Infuser
        // ------------------------------------------------------------------
        net.minecraft.advancement.AdvancementEntry ectoplasmAdv = Advancement.Builder.create()
                .parent(ghostCompanion)
                .display(ModItems.ECTOPLASM, title("ectoplasm"), desc("ectoplasm"),
                        null, AdvancementFrame.TASK, true, true, false)
                .criterion("has_ectoplasm",
                        InventoryChangedCriterion.Conditions.items(ModItems.ECTOPLASM))
                .build(consumer, id("ectoplasm"));

        Advancement.Builder.create()
                .parent(ectoplasmAdv)
                .display(ModItems.RECTO_SWORD, title("recto"), desc("recto"),
                        null, AdvancementFrame.TASK, true, true, false)
                .criterion("has_recto_sword",
                        InventoryChangedCriterion.Conditions.items(ModItems.RECTO_SWORD))
                .build(consumer, id("recto"));

        net.minecraft.advancement.AdvancementEntry refinedEctoplasm = Advancement.Builder.create()
                .parent(ectoplasmAdv)
                .display(ModItems.REFINED_ECTOPLASM, title("refined_ectoplasm"), desc("refined_ectoplasm"),
                        null, AdvancementFrame.TASK, true, true, false)
                .criterion("has_refined_ectoplasm",
                        InventoryChangedCriterion.Conditions.items(ModItems.REFINED_ECTOPLASM))
                .build(consumer, id("refined_ectoplasm"));

        net.minecraft.advancement.AdvancementEntry ectoTools = Advancement.Builder.create()
                .parent(refinedEctoplasm)
                .display(ModItems.ECTO_SWORD, title("ecto_tools"), desc("ecto_tools"),
                        null, AdvancementFrame.TASK, true, true, false)
                .criterion("has_ecto_sword",
                        InventoryChangedCriterion.Conditions.items(ModItems.ECTO_SWORD))
                .build(consumer, id("ecto_tools"));

        Advancement.Builder.create()
                .parent(ectoTools)
                .display(ModItems.ECTO_HELMET, title("ecto_set"), desc("ecto_set"),
                        null, AdvancementFrame.GOAL, true, true, false)
                .criterion("has_sword",
                        InventoryChangedCriterion.Conditions.items(ModItems.ECTO_SWORD))
                .criterion("has_pickaxe",
                        InventoryChangedCriterion.Conditions.items(ModItems.ECTO_PICKAXE))
                .criterion("has_shovel",
                        InventoryChangedCriterion.Conditions.items(ModItems.ECTO_SHOVEL))
                .criterion("has_axe",
                        InventoryChangedCriterion.Conditions.items(ModItems.ECTO_AXE))
                .criterion("has_hoe",
                        InventoryChangedCriterion.Conditions.items(ModItems.ECTO_HOE))
                .criterion("has_helmet",
                        InventoryChangedCriterion.Conditions.items(ModItems.ECTO_HELMET))
                .criterion("has_chestplate",
                        InventoryChangedCriterion.Conditions.items(ModItems.ECTO_CHESTPLATE))
                .criterion("has_leggings",
                        InventoryChangedCriterion.Conditions.items(ModItems.ECTO_LEGGINGS))
                .criterion("has_boots",
                        InventoryChangedCriterion.Conditions.items(ModItems.ECTO_BOOTS))
                .criteriaMerger(net.minecraft.advancement.AdvancementRequirements.CriterionMerger.AND)
                .build(consumer, id("ecto_set"));

        // Spectral Infuser (from ectoplasm branch)
        Advancement.Builder.create()
                .parent(ectoplasmAdv)
                .display(ModBlocks.SPECTRAL_INFUSER, title("spectral_infuser"), desc("spectral_infuser"),
                        null, AdvancementFrame.TASK, true, true, false)
                .criterion("has_spectral_infuser",
                        InventoryChangedCriterion.Conditions.items(ModBlocks.SPECTRAL_INFUSER))
                .build(consumer, id("spectral_infuser"));

        // ==================================================================
        // BRANCH: Raw Metal Rough Tools (all from root)
        // ==================================================================

        // Raw Gold -> Rough Raw Gold tools -> full set
        net.minecraft.advancement.AdvancementEntry rrawGold = Advancement.Builder.create()
                .parent(root)
                .display(ModItems.RRAW_GOLD_SWORD, title("rraw_gold"), desc("rraw_gold"),
                        null, AdvancementFrame.TASK, true, true, false)
                .criterion("has_rraw_gold_sword",
                        InventoryChangedCriterion.Conditions.items(ModItems.RRAW_GOLD_SWORD))
                .build(consumer, id("rraw_gold"));

        Advancement.Builder.create()
                .parent(rrawGold)
                .display(ModItems.RRAW_GOLD_PICKAXE, title("rraw_gold_set"), desc("rraw_gold_set"),
                        null, AdvancementFrame.GOAL, true, true, false)
                .criterion("has_sword",
                        InventoryChangedCriterion.Conditions.items(ModItems.RRAW_GOLD_SWORD))
                .criterion("has_pickaxe",
                        InventoryChangedCriterion.Conditions.items(ModItems.RRAW_GOLD_PICKAXE))
                .criterion("has_shovel",
                        InventoryChangedCriterion.Conditions.items(ModItems.RRAW_GOLD_SHOVEL))
                .criterion("has_axe",
                        InventoryChangedCriterion.Conditions.items(ModItems.RRAW_GOLD_AXE))
                .criterion("has_hoe",
                        InventoryChangedCriterion.Conditions.items(ModItems.RRAW_GOLD_HOE))
                .criteriaMerger(net.minecraft.advancement.AdvancementRequirements.CriterionMerger.AND)
                .build(consumer, id("rraw_gold_set"));

        // Raw Copper -> Rough Raw Copper tools -> full set
        net.minecraft.advancement.AdvancementEntry rrawCopper = Advancement.Builder.create()
                .parent(root)
                .display(ModItems.RRAW_COPPER_SWORD, title("rraw_copper"), desc("rraw_copper"),
                        null, AdvancementFrame.TASK, true, true, false)
                .criterion("has_rraw_copper_sword",
                        InventoryChangedCriterion.Conditions.items(ModItems.RRAW_COPPER_SWORD))
                .build(consumer, id("rraw_copper"));

        Advancement.Builder.create()
                .parent(rrawCopper)
                .display(ModItems.RRAW_COPPER_PICKAXE, title("rraw_copper_set"), desc("rraw_copper_set"),
                        null, AdvancementFrame.GOAL, true, true, false)
                .criterion("has_sword",
                        InventoryChangedCriterion.Conditions.items(ModItems.RRAW_COPPER_SWORD))
                .criterion("has_pickaxe",
                        InventoryChangedCriterion.Conditions.items(ModItems.RRAW_COPPER_PICKAXE))
                .criterion("has_shovel",
                        InventoryChangedCriterion.Conditions.items(ModItems.RRAW_COPPER_SHOVEL))
                .criterion("has_axe",
                        InventoryChangedCriterion.Conditions.items(ModItems.RRAW_COPPER_AXE))
                .criterion("has_hoe",
                        InventoryChangedCriterion.Conditions.items(ModItems.RRAW_COPPER_HOE))
                .criteriaMerger(net.minecraft.advancement.AdvancementRequirements.CriterionMerger.AND)
                .build(consumer, id("rraw_copper_set"));

        // Raw Iron -> Rough Raw Iron tools -> full set
        net.minecraft.advancement.AdvancementEntry rrawIron = Advancement.Builder.create()
                .parent(root)
                .display(ModItems.RRAW_IRON_SWORD, title("rraw_iron"), desc("rraw_iron"),
                        null, AdvancementFrame.TASK, true, true, false)
                .criterion("has_rraw_iron_sword",
                        InventoryChangedCriterion.Conditions.items(ModItems.RRAW_IRON_SWORD))
                .build(consumer, id("rraw_iron"));

        Advancement.Builder.create()
                .parent(rrawIron)
                .display(ModItems.RRAW_IRON_PICKAXE, title("rraw_iron_set"), desc("rraw_iron_set"),
                        null, AdvancementFrame.GOAL, true, true, false)
                .criterion("has_sword",
                        InventoryChangedCriterion.Conditions.items(ModItems.RRAW_IRON_SWORD))
                .criterion("has_pickaxe",
                        InventoryChangedCriterion.Conditions.items(ModItems.RRAW_IRON_PICKAXE))
                .criterion("has_shovel",
                        InventoryChangedCriterion.Conditions.items(ModItems.RRAW_IRON_SHOVEL))
                .criterion("has_axe",
                        InventoryChangedCriterion.Conditions.items(ModItems.RRAW_IRON_AXE))
                .criterion("has_hoe",
                        InventoryChangedCriterion.Conditions.items(ModItems.RRAW_IRON_HOE))
                .criteriaMerger(net.minecraft.advancement.AdvancementRequirements.CriterionMerger.AND)
                .build(consumer, id("rraw_iron_set"));

        // Raw Ferrous Gold -> Rough Raw Ferrous Gold tools -> full set
        net.minecraft.advancement.AdvancementEntry rrawRgold = Advancement.Builder.create()
                .parent(root)
                .display(ModItems.RRAW_RGOLD_SWORD, title("rraw_rgold"), desc("rraw_rgold"),
                        null, AdvancementFrame.TASK, true, true, false)
                .criterion("has_rraw_rgold_sword",
                        InventoryChangedCriterion.Conditions.items(ModItems.RRAW_RGOLD_SWORD))
                .build(consumer, id("rraw_rgold"));

        Advancement.Builder.create()
                .parent(rrawRgold)
                .display(ModItems.RRAW_RGOLD_PICKAXE, title("rraw_rgold_set"), desc("rraw_rgold_set"),
                        null, AdvancementFrame.GOAL, true, true, false)
                .criterion("has_sword",
                        InventoryChangedCriterion.Conditions.items(ModItems.RRAW_RGOLD_SWORD))
                .criterion("has_pickaxe",
                        InventoryChangedCriterion.Conditions.items(ModItems.RRAW_RGOLD_PICKAXE))
                .criterion("has_shovel",
                        InventoryChangedCriterion.Conditions.items(ModItems.RRAW_RGOLD_SHOVEL))
                .criterion("has_axe",
                        InventoryChangedCriterion.Conditions.items(ModItems.RRAW_RGOLD_AXE))
                .criterion("has_hoe",
                        InventoryChangedCriterion.Conditions.items(ModItems.RRAW_RGOLD_HOE))
                .criteriaMerger(net.minecraft.advancement.AdvancementRequirements.CriterionMerger.AND)
                .build(consumer, id("rraw_rgold_set"));

        // Netherite Scrap -> Rough Scrap tools -> full set
        net.minecraft.advancement.AdvancementEntry rscrap = Advancement.Builder.create()
                .parent(root)
                .display(ModItems.RSCRAP_SWORD, title("rscrap"), desc("rscrap"),
                        null, AdvancementFrame.TASK, true, true, false)
                .criterion("has_rscrap_sword",
                        InventoryChangedCriterion.Conditions.items(ModItems.RSCRAP_SWORD))
                .build(consumer, id("rscrap"));

        Advancement.Builder.create()
                .parent(rscrap)
                .display(ModItems.RSCRAP_PICKAXE, title("rscrap_set"), desc("rscrap_set"),
                        null, AdvancementFrame.GOAL, true, true, false)
                .criterion("has_sword",
                        InventoryChangedCriterion.Conditions.items(ModItems.RSCRAP_SWORD))
                .criterion("has_pickaxe",
                        InventoryChangedCriterion.Conditions.items(ModItems.RSCRAP_PICKAXE))
                .criterion("has_shovel",
                        InventoryChangedCriterion.Conditions.items(ModItems.RSCRAP_SHOVEL))
                .criterion("has_axe",
                        InventoryChangedCriterion.Conditions.items(ModItems.RSCRAP_AXE))
                .criterion("has_hoe",
                        InventoryChangedCriterion.Conditions.items(ModItems.RSCRAP_HOE))
                .criteriaMerger(net.minecraft.advancement.AdvancementRequirements.CriterionMerger.AND)
                .build(consumer, id("rscrap_set"));

        // ==================================================================
        // BRANCH: Crystal / element sets (all from root)
        // ==================================================================

        // Rough Amethyst
        net.minecraft.advancement.AdvancementEntry ramethyst = Advancement.Builder.create()
                .parent(root)
                .display(ModItems.RAMETHYST_SWORD, title("ramethyst"), desc("ramethyst"),
                        null, AdvancementFrame.TASK, true, true, false)
                .criterion("has_ramethyst_sword",
                        InventoryChangedCriterion.Conditions.items(ModItems.RAMETHYST_SWORD))
                .build(consumer, id("ramethyst"));

        Advancement.Builder.create()
                .parent(ramethyst)
                .display(ModItems.RAMETHYST_PICKAXE, title("ramethyst_set"), desc("ramethyst_set"),
                        null, AdvancementFrame.GOAL, true, true, false)
                .criterion("has_sword",
                        InventoryChangedCriterion.Conditions.items(ModItems.RAMETHYST_SWORD))
                .criterion("has_pickaxe",
                        InventoryChangedCriterion.Conditions.items(ModItems.RAMETHYST_PICKAXE))
                .criterion("has_shovel",
                        InventoryChangedCriterion.Conditions.items(ModItems.RAMETHYST_SHOVEL))
                .criterion("has_axe",
                        InventoryChangedCriterion.Conditions.items(ModItems.RAMETHYST_AXE))
                .criterion("has_hoe",
                        InventoryChangedCriterion.Conditions.items(ModItems.RAMETHYST_HOE))
                .criteriaMerger(net.minecraft.advancement.AdvancementRequirements.CriterionMerger.AND)
                .build(consumer, id("ramethyst_set"));

        // Snow tools
        net.minecraft.advancement.AdvancementEntry snowTools = Advancement.Builder.create()
                .parent(root)
                .display(ModItems.SNOW_SWORD, title("snow_tools"), desc("snow_tools"),
                        null, AdvancementFrame.TASK, true, true, false)
                .criterion("has_snow_sword",
                        InventoryChangedCriterion.Conditions.items(ModItems.SNOW_SWORD))
                .build(consumer, id("snow_tools"));

        Advancement.Builder.create()
                .parent(snowTools)
                .display(ModItems.SNOW_PICKAXE, title("snow_set"), desc("snow_set"),
                        null, AdvancementFrame.GOAL, true, true, false)
                .criterion("has_sword",
                        InventoryChangedCriterion.Conditions.items(ModItems.SNOW_SWORD))
                .criterion("has_pickaxe",
                        InventoryChangedCriterion.Conditions.items(ModItems.SNOW_PICKAXE))
                .criterion("has_shovel",
                        InventoryChangedCriterion.Conditions.items(ModItems.SNOW_SHOVEL))
                .criterion("has_axe",
                        InventoryChangedCriterion.Conditions.items(ModItems.SNOW_AXE))
                .criterion("has_hoe",
                        InventoryChangedCriterion.Conditions.items(ModItems.SNOW_HOE))
                .criteriaMerger(net.minecraft.advancement.AdvancementRequirements.CriterionMerger.AND)
                .build(consumer, id("snow_set"));

        // Rough Quartz
        net.minecraft.advancement.AdvancementEntry rquartz = Advancement.Builder.create()
                .parent(root)
                .display(ModItems.RQUARTZ_SWORD, title("rquartz"), desc("rquartz"),
                        null, AdvancementFrame.TASK, true, true, false)
                .criterion("has_rquartz_sword",
                        InventoryChangedCriterion.Conditions.items(ModItems.RQUARTZ_SWORD))
                .build(consumer, id("rquartz"));

        Advancement.Builder.create()
                .parent(rquartz)
                .display(ModItems.RQUARTZ_PICKAXE, title("rquartz_set"), desc("rquartz_set"),
                        null, AdvancementFrame.GOAL, true, true, false)
                .criterion("has_sword",
                        InventoryChangedCriterion.Conditions.items(ModItems.RQUARTZ_SWORD))
                .criterion("has_pickaxe",
                        InventoryChangedCriterion.Conditions.items(ModItems.RQUARTZ_PICKAXE))
                .criterion("has_shovel",
                        InventoryChangedCriterion.Conditions.items(ModItems.RQUARTZ_SHOVEL))
                .criterion("has_axe",
                        InventoryChangedCriterion.Conditions.items(ModItems.RQUARTZ_AXE))
                .criterion("has_hoe",
                        InventoryChangedCriterion.Conditions.items(ModItems.RQUARTZ_HOE))
                .criteriaMerger(net.minecraft.advancement.AdvancementRequirements.CriterionMerger.AND)
                .build(consumer, id("rquartz_set"));

        // Rough Prismarine
        net.minecraft.advancement.AdvancementEntry rprism = Advancement.Builder.create()
                .parent(root)
                .display(ModItems.RPRISM_SWORD, title("rprism"), desc("rprism"),
                        null, AdvancementFrame.TASK, true, true, false)
                .criterion("has_rprism_sword",
                        InventoryChangedCriterion.Conditions.items(ModItems.RPRISM_SWORD))
                .build(consumer, id("rprism"));

        Advancement.Builder.create()
                .parent(rprism)
                .display(ModItems.RPRISM_PICKAXE, title("rprism_set"), desc("rprism_set"),
                        null, AdvancementFrame.GOAL, true, true, false)
                .criterion("has_sword",
                        InventoryChangedCriterion.Conditions.items(ModItems.RPRISM_SWORD))
                .criterion("has_pickaxe",
                        InventoryChangedCriterion.Conditions.items(ModItems.RPRISM_PICKAXE))
                .criterion("has_shovel",
                        InventoryChangedCriterion.Conditions.items(ModItems.RPRISM_SHOVEL))
                .criterion("has_axe",
                        InventoryChangedCriterion.Conditions.items(ModItems.RPRISM_AXE))
                .criterion("has_hoe",
                        InventoryChangedCriterion.Conditions.items(ModItems.RPRISM_HOE))
                .criteriaMerger(net.minecraft.advancement.AdvancementRequirements.CriterionMerger.AND)
                .build(consumer, id("rprism_set"));

        // Calcified Amethyst (polished) -- material + tools/armor
        net.minecraft.advancement.AdvancementEntry calciteAmethyst = Advancement.Builder.create()
                .parent(root)
                .display(ModItems.CALCIFIED_AMETHYST, title("calcified_amethyst"), desc("calcified_amethyst"),
                        null, AdvancementFrame.TASK, true, true, false)
                .criterion("has_calcified_amethyst",
                        InventoryChangedCriterion.Conditions.items(ModItems.CALCIFIED_AMETHYST))
                .build(consumer, id("calcified_amethyst"));

        net.minecraft.advancement.AdvancementEntry camethystTools = Advancement.Builder.create()
                .parent(calciteAmethyst)
                .display(ModItems.CAMETHYST_SWORD, title("camethyst_tools"), desc("camethyst_tools"),
                        null, AdvancementFrame.TASK, true, true, false)
                .criterion("has_camethyst_sword",
                        InventoryChangedCriterion.Conditions.items(ModItems.CAMETHYST_SWORD))
                .build(consumer, id("camethyst_tools"));

        Advancement.Builder.create()
                .parent(camethystTools)
                .display(ModItems.CAMETHYST_HELMET, title("camethyst_set"), desc("camethyst_set"),
                        null, AdvancementFrame.GOAL, true, true, false)
                .criterion("has_sword",
                        InventoryChangedCriterion.Conditions.items(ModItems.CAMETHYST_SWORD))
                .criterion("has_pickaxe",
                        InventoryChangedCriterion.Conditions.items(ModItems.CAMETHYST_PICKAXE))
                .criterion("has_shovel",
                        InventoryChangedCriterion.Conditions.items(ModItems.CAMETHYST_SHOVEL))
                .criterion("has_axe",
                        InventoryChangedCriterion.Conditions.items(ModItems.CAMETHYST_AXE))
                .criterion("has_hoe",
                        InventoryChangedCriterion.Conditions.items(ModItems.CAMETHYST_HOE))
                .criterion("has_helmet",
                        InventoryChangedCriterion.Conditions.items(ModItems.CAMETHYST_HELMET))
                .criterion("has_chestplate",
                        InventoryChangedCriterion.Conditions.items(ModItems.CAMETHYST_CHESTPLATE))
                .criterion("has_leggings",
                        InventoryChangedCriterion.Conditions.items(ModItems.CAMETHYST_LEGGINGS))
                .criterion("has_boots",
                        InventoryChangedCriterion.Conditions.items(ModItems.CAMETHYST_BOOTS))
                .criteriaMerger(net.minecraft.advancement.AdvancementRequirements.CriterionMerger.AND)
                .build(consumer, id("camethyst_set"));

        // Ice / Glacial (polished) -- material + tools/armor
        net.minecraft.advancement.AdvancementEntry glacialShard = Advancement.Builder.create()
                .parent(root)
                .display(ModItems.GLACIAL_SHARD, title("glacial_shard"), desc("glacial_shard"),
                        null, AdvancementFrame.TASK, true, true, false)
                .criterion("has_glacial_shard",
                        InventoryChangedCriterion.Conditions.items(ModItems.GLACIAL_SHARD))
                .build(consumer, id("glacial_shard"));

        net.minecraft.advancement.AdvancementEntry iceTools = Advancement.Builder.create()
                .parent(glacialShard)
                .display(ModItems.ICE_SWORD, title("ice_tools"), desc("ice_tools"),
                        null, AdvancementFrame.TASK, true, true, false)
                .criterion("has_ice_sword",
                        InventoryChangedCriterion.Conditions.items(ModItems.ICE_SWORD))
                .build(consumer, id("ice_tools"));

        Advancement.Builder.create()
                .parent(iceTools)
                .display(ModItems.ICE_HELMET, title("ice_set"), desc("ice_set"),
                        null, AdvancementFrame.GOAL, true, true, false)
                .criterion("has_sword",
                        InventoryChangedCriterion.Conditions.items(ModItems.ICE_SWORD))
                .criterion("has_pickaxe",
                        InventoryChangedCriterion.Conditions.items(ModItems.ICE_PICKAXE))
                .criterion("has_shovel",
                        InventoryChangedCriterion.Conditions.items(ModItems.ICE_SHOVEL))
                .criterion("has_axe",
                        InventoryChangedCriterion.Conditions.items(ModItems.ICE_AXE))
                .criterion("has_hoe",
                        InventoryChangedCriterion.Conditions.items(ModItems.ICE_HOE))
                .criterion("has_helmet",
                        InventoryChangedCriterion.Conditions.items(ModItems.ICE_HELMET))
                .criterion("has_chestplate",
                        InventoryChangedCriterion.Conditions.items(ModItems.ICE_CHESTPLATE))
                .criterion("has_leggings",
                        InventoryChangedCriterion.Conditions.items(ModItems.ICE_LEGGINGS))
                .criterion("has_boots",
                        InventoryChangedCriterion.Conditions.items(ModItems.ICE_BOOTS))
                .criteriaMerger(net.minecraft.advancement.AdvancementRequirements.CriterionMerger.AND)
                .build(consumer, id("ice_set"));

        // Polished Quartz -- material + tools/armor
        net.minecraft.advancement.AdvancementEntry polishedQuartz = Advancement.Builder.create()
                .parent(root)
                .display(ModItems.POLISHED_QUARTZ, title("polished_quartz"), desc("polished_quartz"),
                        null, AdvancementFrame.TASK, true, true, false)
                .criterion("has_polished_quartz",
                        InventoryChangedCriterion.Conditions.items(ModItems.POLISHED_QUARTZ))
                .build(consumer, id("polished_quartz"));

        net.minecraft.advancement.AdvancementEntry pquartzTools = Advancement.Builder.create()
                .parent(polishedQuartz)
                .display(ModItems.PQUARTZ_SWORD, title("pquartz_tools"), desc("pquartz_tools"),
                        null, AdvancementFrame.TASK, true, true, false)
                .criterion("has_pquartz_sword",
                        InventoryChangedCriterion.Conditions.items(ModItems.PQUARTZ_SWORD))
                .build(consumer, id("pquartz_tools"));

        Advancement.Builder.create()
                .parent(pquartzTools)
                .display(ModItems.PQUARTZ_HELMET, title("pquartz_set"), desc("pquartz_set"),
                        null, AdvancementFrame.GOAL, true, true, false)
                .criterion("has_sword",
                        InventoryChangedCriterion.Conditions.items(ModItems.PQUARTZ_SWORD))
                .criterion("has_pickaxe",
                        InventoryChangedCriterion.Conditions.items(ModItems.PQUARTZ_PICKAXE))
                .criterion("has_shovel",
                        InventoryChangedCriterion.Conditions.items(ModItems.PQUARTZ_SHOVEL))
                .criterion("has_axe",
                        InventoryChangedCriterion.Conditions.items(ModItems.PQUARTZ_AXE))
                .criterion("has_hoe",
                        InventoryChangedCriterion.Conditions.items(ModItems.PQUARTZ_HOE))
                .criterion("has_helmet",
                        InventoryChangedCriterion.Conditions.items(ModItems.PQUARTZ_HELMET))
                .criterion("has_chestplate",
                        InventoryChangedCriterion.Conditions.items(ModItems.PQUARTZ_CHESTPLATE))
                .criterion("has_leggings",
                        InventoryChangedCriterion.Conditions.items(ModItems.PQUARTZ_LEGGINGS))
                .criterion("has_boots",
                        InventoryChangedCriterion.Conditions.items(ModItems.PQUARTZ_BOOTS))
                .criteriaMerger(net.minecraft.advancement.AdvancementRequirements.CriterionMerger.AND)
                .build(consumer, id("pquartz_set"));

        // Polished Prismarine -- material + tools/armor
        net.minecraft.advancement.AdvancementEntry polishedPrismarine = Advancement.Builder.create()
                .parent(root)
                .display(ModItems.POLISHED_PRISMARINE, title("polished_prismarine"), desc("polished_prismarine"),
                        null, AdvancementFrame.TASK, true, true, false)
                .criterion("has_polished_prismarine",
                        InventoryChangedCriterion.Conditions.items(ModItems.POLISHED_PRISMARINE))
                .build(consumer, id("polished_prismarine"));

        net.minecraft.advancement.AdvancementEntry pprismTools = Advancement.Builder.create()
                .parent(polishedPrismarine)
                .display(ModItems.PPRISM_SWORD, title("pprism_tools"), desc("pprism_tools"),
                        null, AdvancementFrame.TASK, true, true, false)
                .criterion("has_pprism_sword",
                        InventoryChangedCriterion.Conditions.items(ModItems.PPRISM_SWORD))
                .build(consumer, id("pprism_tools"));

        Advancement.Builder.create()
                .parent(pprismTools)
                .display(ModItems.PPRISM_HELMET, title("pprism_set"), desc("pprism_set"),
                        null, AdvancementFrame.GOAL, true, true, false)
                .criterion("has_sword",
                        InventoryChangedCriterion.Conditions.items(ModItems.PPRISM_SWORD))
                .criterion("has_pickaxe",
                        InventoryChangedCriterion.Conditions.items(ModItems.PPRISM_PICKAXE))
                .criterion("has_shovel",
                        InventoryChangedCriterion.Conditions.items(ModItems.PPRISM_SHOVEL))
                .criterion("has_axe",
                        InventoryChangedCriterion.Conditions.items(ModItems.PPRISM_AXE))
                .criterion("has_hoe",
                        InventoryChangedCriterion.Conditions.items(ModItems.PPRISM_HOE))
                .criterion("has_helmet",
                        InventoryChangedCriterion.Conditions.items(ModItems.PPRISM_HELMET))
                .criterion("has_chestplate",
                        InventoryChangedCriterion.Conditions.items(ModItems.PPRISM_CHESTPLATE))
                .criterion("has_leggings",
                        InventoryChangedCriterion.Conditions.items(ModItems.PPRISM_LEGGINGS))
                .criterion("has_boots",
                        InventoryChangedCriterion.Conditions.items(ModItems.PPRISM_BOOTS))
                .criteriaMerger(net.minecraft.advancement.AdvancementRequirements.CriterionMerger.AND)
                .build(consumer, id("pprism_set"));

        // ==================================================================
        // BRANCH: Overpower (from root) -- CHALLENGE
        // ==================================================================
        net.minecraft.advancement.AdvancementEntry overpowerSword = Advancement.Builder.create()
                .parent(root)
                .display(ModItems.OVERPOWER_SWORD, title("overpower"), desc("overpower"),
                        null, AdvancementFrame.CHALLENGE, true, true, false)
                .criterion("has_overpower_sword",
                        InventoryChangedCriterion.Conditions.items(ModItems.OVERPOWER_SWORD))
                .build(consumer, id("overpower"));

        // Full Overpower set (GOAL)
        Advancement.Builder.create()
                .parent(overpowerSword)
                .display(ModItems.OVERPOWER_HELMET, title("overpower_set"), desc("overpower_set"),
                        null, AdvancementFrame.GOAL, true, true, false)
                .criterion("has_sword",
                        InventoryChangedCriterion.Conditions.items(ModItems.OVERPOWER_SWORD))
                .criterion("has_pickaxe",
                        InventoryChangedCriterion.Conditions.items(ModItems.OVERPOWER_PICKAXE))
                .criterion("has_shovel",
                        InventoryChangedCriterion.Conditions.items(ModItems.OVERPOWER_SHOVEL))
                .criterion("has_axe",
                        InventoryChangedCriterion.Conditions.items(ModItems.OVERPOWER_AXE))
                .criterion("has_helmet",
                        InventoryChangedCriterion.Conditions.items(ModItems.OVERPOWER_HELMET))
                .criterion("has_chestplate",
                        InventoryChangedCriterion.Conditions.items(ModItems.OVERPOWER_CHESTPLATE))
                .criterion("has_leggings",
                        InventoryChangedCriterion.Conditions.items(ModItems.OVERPOWER_LEGGINGS))
                .criterion("has_boots",
                        InventoryChangedCriterion.Conditions.items(ModItems.OVERPOWER_BOOTS))
                .criteriaMerger(net.minecraft.advancement.AdvancementRequirements.CriterionMerger.AND)
                .build(consumer, id("overpower_set"));

        // ==================================================================
        // BRANCH: Flint / FNI (from root)
        // ==================================================================
        net.minecraft.advancement.AdvancementEntry rflintSword = Advancement.Builder.create()
                .parent(root)
                .display(ModItems.RFLINT_SWORD, title("rflint"), desc("rflint"),
                        null, AdvancementFrame.TASK, true, true, false)
                .criterion("has_rflint_sword",
                        InventoryChangedCriterion.Conditions.items(ModItems.RFLINT_SWORD))
                .build(consumer, id("rflint"));

        net.minecraft.advancement.AdvancementEntry fniSword = Advancement.Builder.create()
                .parent(rflintSword)
                .display(ModItems.FNI_SWORD, title("fni_tools"), desc("fni_tools"),
                        null, AdvancementFrame.TASK, true, true, false)
                .criterion("has_fni_sword",
                        InventoryChangedCriterion.Conditions.items(ModItems.FNI_SWORD))
                .build(consumer, id("fni_tools"));

        Advancement.Builder.create()
                .parent(fniSword)
                .display(ModItems.FNI_BOOTS, title("fni_set"), desc("fni_set"),
                        null, AdvancementFrame.GOAL, true, true, false)
                .criterion("has_sword",
                        InventoryChangedCriterion.Conditions.items(ModItems.FNI_SWORD))
                .criterion("has_pickaxe",
                        InventoryChangedCriterion.Conditions.items(ModItems.FNI_PICKAXE))
                .criterion("has_shovel",
                        InventoryChangedCriterion.Conditions.items(ModItems.FNI_SHOVEL))
                .criterion("has_axe",
                        InventoryChangedCriterion.Conditions.items(ModItems.FNI_AXE))
                .criterion("has_helmet",
                        InventoryChangedCriterion.Conditions.items(ModItems.FNI_HELMET))
                .criterion("has_chestplate",
                        InventoryChangedCriterion.Conditions.items(ModItems.FNI_CHESTPLATE))
                .criterion("has_leggings",
                        InventoryChangedCriterion.Conditions.items(ModItems.FNI_LEGGINGS))
                .criterion("has_boots",
                        InventoryChangedCriterion.Conditions.items(ModItems.FNI_BOOTS))
                .criteriaMerger(net.minecraft.advancement.AdvancementRequirements.CriterionMerger.AND)
                .build(consumer, id("fni_set"));

        // ==================================================================
        // BRANCH: Stone Toolkit (from root) -- CHALLENGE
        // Collect a pickaxe crafted from every rock variant
        // ==================================================================
        Advancement.Builder.create()
                .parent(root)
                .display(ModItems.GRANITE_PICKAXE, title("stone_toolkit"), desc("stone_toolkit"),
                        null, AdvancementFrame.CHALLENGE, true, true, false)
                .criterion("has_andesite_pickaxe",
                        InventoryChangedCriterion.Conditions.items(ModItems.ANDESITE_PICKAXE))
                .criterion("has_basalt_pickaxe",
                        InventoryChangedCriterion.Conditions.items(ModItems.BASALT_PICKAXE))
                .criterion("has_blackstone_pickaxe",
                        InventoryChangedCriterion.Conditions.items(ModItems.BLACKSTONE_PICKAXE))
                .criterion("has_calcite_pickaxe",
                        InventoryChangedCriterion.Conditions.items(ModItems.CALCITE_PICKAXE))
                .criterion("has_deepslate_pickaxe",
                        InventoryChangedCriterion.Conditions.items(ModItems.DEEPSLATE_PICKAXE))
                .criterion("has_diorite_pickaxe",
                        InventoryChangedCriterion.Conditions.items(ModItems.DIORITE_PICKAXE))
                .criterion("has_end_stone_pickaxe",
                        InventoryChangedCriterion.Conditions.items(ModItems.END_STONE_PICKAXE))
                .criterion("has_granite_pickaxe",
                        InventoryChangedCriterion.Conditions.items(ModItems.GRANITE_PICKAXE))
                .criterion("has_netherrack_pickaxe",
                        InventoryChangedCriterion.Conditions.items(ModItems.NETHERRACK_PICKAXE))
                .criterion("has_sandstone_pickaxe",
                        InventoryChangedCriterion.Conditions.items(ModItems.SANDSTONE_PICKAXE))
                .criterion("has_smooth_basalt_pickaxe",
                        InventoryChangedCriterion.Conditions.items(ModItems.SMOOTH_BASALT_PICKAXE))
                .criterion("has_terracotta_pickaxe",
                        InventoryChangedCriterion.Conditions.items(ModItems.TERRACOTTA_PICKAXE))
                .criterion("has_tuff_pickaxe",
                        InventoryChangedCriterion.Conditions.items(ModItems.TUFF_PICKAXE))
                .criteriaMerger(net.minecraft.advancement.AdvancementRequirements.CriterionMerger.AND)
                .build(consumer, id("stone_toolkit"));

        // ==================================================================
        // BRANCH: Wood Toolkit (from root) -- CHALLENGE
        // Collect a sword crafted from every wood variant
        // ==================================================================
        Advancement.Builder.create()
                .parent(root)
                .display(ModItems.OAK_SWORD, title("wood_toolkit"), desc("wood_toolkit"),
                        null, AdvancementFrame.CHALLENGE, true, true, false)
                .criterion("has_oak_sword",
                        InventoryChangedCriterion.Conditions.items(ModItems.OAK_SWORD))
                .criterion("has_spruce_sword",
                        InventoryChangedCriterion.Conditions.items(ModItems.SPRUCE_SWORD))
                .criterion("has_birch_sword",
                        InventoryChangedCriterion.Conditions.items(ModItems.BIRCH_SWORD))
                .criterion("has_jungle_sword",
                        InventoryChangedCriterion.Conditions.items(ModItems.JUNGLE_SWORD))
                .criterion("has_acacia_sword",
                        InventoryChangedCriterion.Conditions.items(ModItems.ACACIA_SWORD))
                .criterion("has_dark_oak_sword",
                        InventoryChangedCriterion.Conditions.items(ModItems.DARK_OAK_SWORD))
                .criterion("has_mangrove_sword",
                        InventoryChangedCriterion.Conditions.items(ModItems.MANGROVE_SWORD))
                .criterion("has_cherry_sword",
                        InventoryChangedCriterion.Conditions.items(ModItems.CHERRY_SWORD))
                .criterion("has_bamboo_sword",
                        InventoryChangedCriterion.Conditions.items(ModItems.BAMBOO_SWORD))
                .criterion("has_crimson_sword",
                        InventoryChangedCriterion.Conditions.items(ModItems.CRIMSON_SWORD))
                .criterion("has_warped_sword",
                        InventoryChangedCriterion.Conditions.items(ModItems.WARPED_SWORD))
                .criteriaMerger(net.minecraft.advancement.AdvancementRequirements.CriterionMerger.AND)
                .build(consumer, id("wood_toolkit"));

        // ==================================================================
        // BRANCH: Vanilla Material Sets (from root)
        // ==================================================================

        // --- Tools-only sets (5 items each) ---
        buildVanillaToolAdv(consumer, root, "paper_craft", "paper_set",
                ModItems.PAPER_SWORD,
                ModItems.PAPER_SWORD, ModItems.PAPER_PICKAXE,
                ModItems.PAPER_AXE, ModItems.PAPER_SHOVEL, ModItems.PAPER_HOE);

        buildVanillaToolAdv(consumer, root, "feather_craft", "feather_set",
                ModItems.FEATHER_SWORD,
                ModItems.FEATHER_SWORD, ModItems.FEATHER_PICKAXE,
                ModItems.FEATHER_AXE, ModItems.FEATHER_SHOVEL, ModItems.FEATHER_HOE);

        buildVanillaToolAdv(consumer, root, "glass_craft", "glass_set",
                ModItems.GLASS_SWORD,
                ModItems.GLASS_SWORD, ModItems.GLASS_PICKAXE,
                ModItems.GLASS_AXE, ModItems.GLASS_SHOVEL, ModItems.GLASS_HOE);

        buildVanillaToolAdv(consumer, root, "sponge_craft", "sponge_set",
                ModItems.SPONGE_SWORD,
                ModItems.SPONGE_SWORD, ModItems.SPONGE_PICKAXE,
                ModItems.SPONGE_AXE, ModItems.SPONGE_SHOVEL, ModItems.SPONGE_HOE);

        buildVanillaToolAdv(consumer, root, "nether_wart_craft", "nether_wart_set",
                ModItems.NETHER_WART_SWORD,
                ModItems.NETHER_WART_SWORD, ModItems.NETHER_WART_PICKAXE,
                ModItems.NETHER_WART_AXE, ModItems.NETHER_WART_SHOVEL, ModItems.NETHER_WART_HOE);

        buildVanillaToolAdv(consumer, root, "pointed_dripstone_craft", "pointed_dripstone_set",
                ModItems.POINTED_DRIPSTONE_SWORD,
                ModItems.POINTED_DRIPSTONE_SWORD, ModItems.POINTED_DRIPSTONE_PICKAXE,
                ModItems.POINTED_DRIPSTONE_AXE, ModItems.POINTED_DRIPSTONE_SHOVEL, ModItems.POINTED_DRIPSTONE_HOE);

        // --- Armor-only sets (4 items each) ---
        net.minecraft.advancement.AdvancementEntry rabbitHideCraft = Advancement.Builder.create()
                .parent(root)
                .display(ModItems.RABBIT_HIDE_HELMET, title("rabbit_hide_craft"), desc("rabbit_hide_craft"),
                        null, AdvancementFrame.TASK, true, true, false)
                .criterion("has_helmet",
                        InventoryChangedCriterion.Conditions.items(ModItems.RABBIT_HIDE_HELMET))
                .criterion("has_chestplate",
                        InventoryChangedCriterion.Conditions.items(ModItems.RABBIT_HIDE_CHESTPLATE))
                .criterion("has_leggings",
                        InventoryChangedCriterion.Conditions.items(ModItems.RABBIT_HIDE_LEGGINGS))
                .criterion("has_boots",
                        InventoryChangedCriterion.Conditions.items(ModItems.RABBIT_HIDE_BOOTS))
                .criteriaMerger(net.minecraft.advancement.AdvancementRequirements.CriterionMerger.OR)
                .build(consumer, id("rabbit_hide_craft"));

        Advancement.Builder.create()
                .parent(rabbitHideCraft)
                .display(ModItems.RABBIT_HIDE_CHESTPLATE, title("rabbit_hide_set"), desc("rabbit_hide_set"),
                        null, AdvancementFrame.GOAL, true, true, false)
                .criterion("has_helmet",
                        InventoryChangedCriterion.Conditions.items(ModItems.RABBIT_HIDE_HELMET))
                .criterion("has_chestplate",
                        InventoryChangedCriterion.Conditions.items(ModItems.RABBIT_HIDE_CHESTPLATE))
                .criterion("has_leggings",
                        InventoryChangedCriterion.Conditions.items(ModItems.RABBIT_HIDE_LEGGINGS))
                .criterion("has_boots",
                        InventoryChangedCriterion.Conditions.items(ModItems.RABBIT_HIDE_BOOTS))
                .criteriaMerger(net.minecraft.advancement.AdvancementRequirements.CriterionMerger.AND)
                .build(consumer, id("rabbit_hide_set"));

        net.minecraft.advancement.AdvancementEntry turtleScuteCraft = Advancement.Builder.create()
                .parent(root)
                .display(ModItems.TURTLE_SCUTE_HELMET, title("turtle_scute_craft"), desc("turtle_scute_craft"),
                        null, AdvancementFrame.TASK, true, true, false)
                .criterion("has_helmet",
                        InventoryChangedCriterion.Conditions.items(ModItems.TURTLE_SCUTE_HELMET))
                .criterion("has_chestplate",
                        InventoryChangedCriterion.Conditions.items(ModItems.TURTLE_SCUTE_CHESTPLATE))
                .criterion("has_leggings",
                        InventoryChangedCriterion.Conditions.items(ModItems.TURTLE_SCUTE_LEGGINGS))
                .criterion("has_boots",
                        InventoryChangedCriterion.Conditions.items(ModItems.TURTLE_SCUTE_BOOTS))
                .criteriaMerger(net.minecraft.advancement.AdvancementRequirements.CriterionMerger.OR)
                .build(consumer, id("turtle_scute_craft"));

        Advancement.Builder.create()
                .parent(turtleScuteCraft)
                .display(ModItems.TURTLE_SCUTE_CHESTPLATE, title("turtle_scute_set"), desc("turtle_scute_set"),
                        null, AdvancementFrame.GOAL, true, true, false)
                .criterion("has_helmet",
                        InventoryChangedCriterion.Conditions.items(ModItems.TURTLE_SCUTE_HELMET))
                .criterion("has_chestplate",
                        InventoryChangedCriterion.Conditions.items(ModItems.TURTLE_SCUTE_CHESTPLATE))
                .criterion("has_leggings",
                        InventoryChangedCriterion.Conditions.items(ModItems.TURTLE_SCUTE_LEGGINGS))
                .criterion("has_boots",
                        InventoryChangedCriterion.Conditions.items(ModItems.TURTLE_SCUTE_BOOTS))
                .criteriaMerger(net.minecraft.advancement.AdvancementRequirements.CriterionMerger.AND)
                .build(consumer, id("turtle_scute_set"));

        // --- Tools+Armor sets (9 items each) ---
        buildVanillaSetAdv(consumer, root, "cactus_craft", "cactus_set",
                ModItems.CACTUS_SWORD,
                ModItems.CACTUS_SWORD, ModItems.CACTUS_PICKAXE,
                ModItems.CACTUS_AXE, ModItems.CACTUS_SHOVEL, ModItems.CACTUS_HOE,
                ModItems.CACTUS_HELMET, ModItems.CACTUS_CHESTPLATE,
                ModItems.CACTUS_LEGGINGS, ModItems.CACTUS_BOOTS);

        buildVanillaSetAdv(consumer, root, "bone_craft", "bone_set",
                ModItems.BONE_SWORD,
                ModItems.BONE_SWORD, ModItems.BONE_PICKAXE,
                ModItems.BONE_AXE, ModItems.BONE_SHOVEL, ModItems.BONE_HOE,
                ModItems.BONE_HELMET, ModItems.BONE_CHESTPLATE,
                ModItems.BONE_LEGGINGS, ModItems.BONE_BOOTS);

        buildVanillaSetAdv(consumer, root, "clay_craft", "clay_set",
                ModItems.CLAY_SWORD,
                ModItems.CLAY_SWORD, ModItems.CLAY_PICKAXE,
                ModItems.CLAY_AXE, ModItems.CLAY_SHOVEL, ModItems.CLAY_HOE,
                ModItems.CLAY_HELMET, ModItems.CLAY_CHESTPLATE,
                ModItems.CLAY_LEGGINGS, ModItems.CLAY_BOOTS);

        buildVanillaSetAdv(consumer, root, "brick_craft", "brick_set",
                ModItems.BRICK_SWORD,
                ModItems.BRICK_SWORD, ModItems.BRICK_PICKAXE,
                ModItems.BRICK_AXE, ModItems.BRICK_SHOVEL, ModItems.BRICK_HOE,
                ModItems.BRICK_HELMET, ModItems.BRICK_CHESTPLATE,
                ModItems.BRICK_LEGGINGS, ModItems.BRICK_BOOTS);

        buildVanillaSetAdv(consumer, root, "nether_brick_craft", "nether_brick_set",
                ModItems.NETHER_BRICK_SWORD,
                ModItems.NETHER_BRICK_SWORD, ModItems.NETHER_BRICK_PICKAXE,
                ModItems.NETHER_BRICK_AXE, ModItems.NETHER_BRICK_SHOVEL, ModItems.NETHER_BRICK_HOE,
                ModItems.NETHER_BRICK_HELMET, ModItems.NETHER_BRICK_CHESTPLATE,
                ModItems.NETHER_BRICK_LEGGINGS, ModItems.NETHER_BRICK_BOOTS);

        buildVanillaSetAdv(consumer, root, "copper_craft", "copper_set",
                ModItems.COPPER_SWORD,
                ModItems.COPPER_SWORD, ModItems.COPPER_PICKAXE,
                ModItems.COPPER_AXE, ModItems.COPPER_SHOVEL, ModItems.COPPER_HOE,
                ModItems.COPPER_HELMET, ModItems.COPPER_CHESTPLATE,
                ModItems.COPPER_LEGGINGS, ModItems.COPPER_BOOTS);

        buildVanillaSetAdv(consumer, root, "phantom_craft", "phantom_set",
                ModItems.PHANTOM_SWORD,
                ModItems.PHANTOM_SWORD, ModItems.PHANTOM_PICKAXE,
                ModItems.PHANTOM_AXE, ModItems.PHANTOM_SHOVEL, ModItems.PHANTOM_HOE,
                ModItems.PHANTOM_HELMET, ModItems.PHANTOM_CHESTPLATE,
                ModItems.PHANTOM_LEGGINGS, ModItems.PHANTOM_BOOTS);

        buildVanillaSetAdv(consumer, root, "magma_cream_craft", "magma_cream_set",
                ModItems.MAGMA_CREAM_SWORD,
                ModItems.MAGMA_CREAM_SWORD, ModItems.MAGMA_CREAM_PICKAXE,
                ModItems.MAGMA_CREAM_AXE, ModItems.MAGMA_CREAM_SHOVEL, ModItems.MAGMA_CREAM_HOE,
                ModItems.MAGMA_CREAM_HELMET, ModItems.MAGMA_CREAM_CHESTPLATE,
                ModItems.MAGMA_CREAM_LEGGINGS, ModItems.MAGMA_CREAM_BOOTS);

        buildVanillaSetAdv(consumer, root, "slime_craft", "slime_set",
                ModItems.SLIME_SWORD,
                ModItems.SLIME_SWORD, ModItems.SLIME_PICKAXE,
                ModItems.SLIME_AXE, ModItems.SLIME_SHOVEL, ModItems.SLIME_HOE,
                ModItems.SLIME_HELMET, ModItems.SLIME_CHESTPLATE,
                ModItems.SLIME_LEGGINGS, ModItems.SLIME_BOOTS);

        buildVanillaSetAdv(consumer, root, "blaze_craft", "blaze_set",
                ModItems.BLAZE_SWORD,
                ModItems.BLAZE_SWORD, ModItems.BLAZE_PICKAXE,
                ModItems.BLAZE_AXE, ModItems.BLAZE_SHOVEL, ModItems.BLAZE_HOE,
                ModItems.BLAZE_HELMET, ModItems.BLAZE_CHESTPLATE,
                ModItems.BLAZE_LEGGINGS, ModItems.BLAZE_BOOTS);

        buildVanillaSetAdv(consumer, root, "nautilus_craft", "nautilus_set",
                ModItems.NAUTILUS_SWORD,
                ModItems.NAUTILUS_SWORD, ModItems.NAUTILUS_PICKAXE,
                ModItems.NAUTILUS_AXE, ModItems.NAUTILUS_SHOVEL, ModItems.NAUTILUS_HOE,
                ModItems.NAUTILUS_HELMET, ModItems.NAUTILUS_CHESTPLATE,
                ModItems.NAUTILUS_LEGGINGS, ModItems.NAUTILUS_BOOTS);

        buildVanillaSetAdv(consumer, root, "purpur_craft", "purpur_set",
                ModItems.PURPUR_SWORD,
                ModItems.PURPUR_SWORD, ModItems.PURPUR_PICKAXE,
                ModItems.PURPUR_AXE, ModItems.PURPUR_SHOVEL, ModItems.PURPUR_HOE,
                ModItems.PURPUR_HELMET, ModItems.PURPUR_CHESTPLATE,
                ModItems.PURPUR_LEGGINGS, ModItems.PURPUR_BOOTS);

        buildVanillaSetAdv(consumer, root, "ghast_tear_craft", "ghast_tear_set",
                ModItems.GHAST_TEAR_SWORD,
                ModItems.GHAST_TEAR_SWORD, ModItems.GHAST_TEAR_PICKAXE,
                ModItems.GHAST_TEAR_AXE, ModItems.GHAST_TEAR_SHOVEL, ModItems.GHAST_TEAR_HOE,
                ModItems.GHAST_TEAR_HELMET, ModItems.GHAST_TEAR_CHESTPLATE,
                ModItems.GHAST_TEAR_LEGGINGS, ModItems.GHAST_TEAR_BOOTS);

        buildVanillaSetAdv(consumer, root, "eye_of_ender_craft", "eye_of_ender_set",
                ModItems.EYE_OF_ENDER_SWORD,
                ModItems.EYE_OF_ENDER_SWORD, ModItems.EYE_OF_ENDER_PICKAXE,
                ModItems.EYE_OF_ENDER_AXE, ModItems.EYE_OF_ENDER_SHOVEL, ModItems.EYE_OF_ENDER_HOE,
                ModItems.EYE_OF_ENDER_HELMET, ModItems.EYE_OF_ENDER_CHESTPLATE,
                ModItems.EYE_OF_ENDER_LEGGINGS, ModItems.EYE_OF_ENDER_BOOTS);

        buildVanillaSetAdv(consumer, root, "shulker_craft", "shulker_set",
                ModItems.SHULKER_SWORD,
                ModItems.SHULKER_SWORD, ModItems.SHULKER_PICKAXE,
                ModItems.SHULKER_AXE, ModItems.SHULKER_SHOVEL, ModItems.SHULKER_HOE,
                ModItems.SHULKER_HELMET, ModItems.SHULKER_CHESTPLATE,
                ModItems.SHULKER_LEGGINGS, ModItems.SHULKER_BOOTS);

        buildVanillaSetAdv(consumer, root, "echo_shard_craft", "echo_shard_set",
                ModItems.ECHO_SHARD_SWORD,
                ModItems.ECHO_SHARD_SWORD, ModItems.ECHO_SHARD_PICKAXE,
                ModItems.ECHO_SHARD_AXE, ModItems.ECHO_SHARD_SHOVEL, ModItems.ECHO_SHARD_HOE,
                ModItems.ECHO_SHARD_HELMET, ModItems.ECHO_SHARD_CHESTPLATE,
                ModItems.ECHO_SHARD_LEGGINGS, ModItems.ECHO_SHARD_BOOTS);

        buildVanillaSetAdv(consumer, root, "dragon_breath_craft", "dragon_breath_set",
                ModItems.DRAGON_BREATH_SWORD,
                ModItems.DRAGON_BREATH_SWORD, ModItems.DRAGON_BREATH_PICKAXE,
                ModItems.DRAGON_BREATH_AXE, ModItems.DRAGON_BREATH_SHOVEL, ModItems.DRAGON_BREATH_HOE,
                ModItems.DRAGON_BREATH_HELMET, ModItems.DRAGON_BREATH_CHESTPLATE,
                ModItems.DRAGON_BREATH_LEGGINGS, ModItems.DRAGON_BREATH_BOOTS);

        // ---------------------------------------------------------------
        // Leather tools (from root)
        // ---------------------------------------------------------------
        net.minecraft.advancement.AdvancementEntry leatherCraft = Advancement.Builder.create()
                .parent(root)
                .display(ModItems.LEATHER_SWORD, title("leather_craft"), desc("leather_craft"),
                        null, AdvancementFrame.TASK, true, true, false)
                .criterion("has_leather_sword",
                        InventoryChangedCriterion.Conditions.items(ModItems.LEATHER_SWORD))
                .criterion("has_leather_pickaxe",
                        InventoryChangedCriterion.Conditions.items(ModItems.LEATHER_PICKAXE))
                .criterion("has_leather_axe",
                        InventoryChangedCriterion.Conditions.items(ModItems.LEATHER_AXE))
                .criterion("has_leather_shovel",
                        InventoryChangedCriterion.Conditions.items(ModItems.LEATHER_SHOVEL))
                .criterion("has_leather_hoe",
                        InventoryChangedCriterion.Conditions.items(ModItems.LEATHER_HOE))
                .criteriaMerger(net.minecraft.advancement.AdvancementRequirements.CriterionMerger.OR)
                .build(consumer, id("leather_craft"));

        Advancement.Builder.create()
                .parent(leatherCraft)
                .display(ModItems.LEATHER_PICKAXE, title("leather_set"), desc("leather_set"),
                        null, AdvancementFrame.GOAL, true, true, false)
                .criterion("has_sword",
                        InventoryChangedCriterion.Conditions.items(ModItems.LEATHER_SWORD))
                .criterion("has_pickaxe",
                        InventoryChangedCriterion.Conditions.items(ModItems.LEATHER_PICKAXE))
                .criterion("has_shovel",
                        InventoryChangedCriterion.Conditions.items(ModItems.LEATHER_SHOVEL))
                .criterion("has_axe",
                        InventoryChangedCriterion.Conditions.items(ModItems.LEATHER_AXE))
                .criterion("has_hoe",
                        InventoryChangedCriterion.Conditions.items(ModItems.LEATHER_HOE))
                .criteriaMerger(net.minecraft.advancement.AdvancementRequirements.CriterionMerger.AND)
                .build(consumer, id("leather_set"));

        // ---------------------------------------------------------------
        // Cake -- novelty branch (from root)
        // ---------------------------------------------------------------
        net.minecraft.advancement.AdvancementEntry cakeAdv = Advancement.Builder.create()
                .parent(root)
                .display(ModItems.CAKE_SWORD, title("let_them_eat_cake"), desc("let_them_eat_cake"),
                        null, AdvancementFrame.TASK, true, true, false)
                .criterion("has_cake_sword",
                        InventoryChangedCriterion.Conditions.items(ModItems.CAKE_SWORD))
                .criterion("has_cake_pickaxe",
                        InventoryChangedCriterion.Conditions.items(ModItems.CAKE_PICKAXE))
                .criterion("has_cake_axe",
                        InventoryChangedCriterion.Conditions.items(ModItems.CAKE_AXE))
                .criterion("has_cake_shovel",
                        InventoryChangedCriterion.Conditions.items(ModItems.CAKE_SHOVEL))
                .criterion("has_cake_hoe",
                        InventoryChangedCriterion.Conditions.items(ModItems.CAKE_HOE))
                .criteriaMerger(net.minecraft.advancement.AdvancementRequirements.CriterionMerger.OR)
                .build(consumer, id("let_them_eat_cake"));

        Advancement.Builder.create()
                .parent(cakeAdv)
                .display(Items.CAKE, title("the_cake_is_a_lie"), desc("the_cake_is_a_lie"),
                        null, AdvancementFrame.TASK, true, true, true)
                .criterion("eat_cake_sword",
                        ConsumeItemCriterion.Conditions.item(ModItems.CAKE_SWORD))
                .criterion("eat_cake_pickaxe",
                        ConsumeItemCriterion.Conditions.item(ModItems.CAKE_PICKAXE))
                .criterion("eat_cake_axe",
                        ConsumeItemCriterion.Conditions.item(ModItems.CAKE_AXE))
                .criterion("eat_cake_shovel",
                        ConsumeItemCriterion.Conditions.item(ModItems.CAKE_SHOVEL))
                .criterion("eat_cake_hoe",
                        ConsumeItemCriterion.Conditions.item(ModItems.CAKE_HOE))
                .criteriaMerger(net.minecraft.advancement.AdvancementRequirements.CriterionMerger.OR)
                .build(consumer, id("the_cake_is_a_lie"));

        // ---------------------------------------------------------------
        // Food sets -- craft + eat advancements (from cake branch)
        // ---------------------------------------------------------------
        buildFoodAdv(consumer, cakeAdv, "bread_craft", "bread_eat", "bread_full_set",
                ModItems.BREAD_SWORD, Items.BREAD,
                ModItems.BREAD_SWORD, ModItems.BREAD_PICKAXE,
                ModItems.BREAD_AXE, ModItems.BREAD_SHOVEL, ModItems.BREAD_HOE,
                ModItems.BREAD_HELMET, ModItems.BREAD_CHESTPLATE,
                ModItems.BREAD_LEGGINGS, ModItems.BREAD_BOOTS);

        buildFoodAdv(consumer, cakeAdv, "dried_kelp_craft", "dried_kelp_eat", "dried_kelp_full_set",
                ModItems.DRIED_KELP_SWORD, Items.DRIED_KELP,
                ModItems.DRIED_KELP_SWORD, ModItems.DRIED_KELP_PICKAXE,
                ModItems.DRIED_KELP_AXE, ModItems.DRIED_KELP_SHOVEL, ModItems.DRIED_KELP_HOE,
                ModItems.DRIED_KELP_HELMET, ModItems.DRIED_KELP_CHESTPLATE,
                ModItems.DRIED_KELP_LEGGINGS, ModItems.DRIED_KELP_BOOTS);

        buildFoodAdv(consumer, cakeAdv, "rotten_flesh_craft", "rotten_flesh_eat", "rotten_flesh_full_set",
                ModItems.ROTTEN_FLESH_SWORD, Items.ROTTEN_FLESH,
                ModItems.ROTTEN_FLESH_SWORD, ModItems.ROTTEN_FLESH_PICKAXE,
                ModItems.ROTTEN_FLESH_AXE, ModItems.ROTTEN_FLESH_SHOVEL, ModItems.ROTTEN_FLESH_HOE,
                ModItems.ROTTEN_FLESH_HELMET, ModItems.ROTTEN_FLESH_CHESTPLATE,
                ModItems.ROTTEN_FLESH_LEGGINGS, ModItems.ROTTEN_FLESH_BOOTS);

        buildFoodAdv(consumer, cakeAdv, "melon_craft", "melon_eat", "melon_full_set",
                ModItems.MELON_SWORD, Items.MELON_SLICE,
                ModItems.MELON_SWORD, ModItems.MELON_PICKAXE,
                ModItems.MELON_AXE, ModItems.MELON_SHOVEL, ModItems.MELON_HOE,
                ModItems.MELON_HELMET, ModItems.MELON_CHESTPLATE,
                ModItems.MELON_LEGGINGS, ModItems.MELON_BOOTS);

        buildFoodAdv(consumer, cakeAdv, "sweet_berry_craft", "sweet_berry_eat", "sweet_berry_full_set",
                ModItems.SWEET_BERRY_SWORD, Items.SWEET_BERRIES,
                ModItems.SWEET_BERRY_SWORD, ModItems.SWEET_BERRY_PICKAXE,
                ModItems.SWEET_BERRY_AXE, ModItems.SWEET_BERRY_SHOVEL, ModItems.SWEET_BERRY_HOE,
                ModItems.SWEET_BERRY_HELMET, ModItems.SWEET_BERRY_CHESTPLATE,
                ModItems.SWEET_BERRY_LEGGINGS, ModItems.SWEET_BERRY_BOOTS);

        buildFoodAdv(consumer, cakeAdv, "pumpkin_pie_craft", "pumpkin_pie_eat", "pumpkin_pie_full_set",
                ModItems.PUMPKIN_PIE_SWORD, Items.PUMPKIN_PIE,
                ModItems.PUMPKIN_PIE_SWORD, ModItems.PUMPKIN_PIE_PICKAXE,
                ModItems.PUMPKIN_PIE_AXE, ModItems.PUMPKIN_PIE_SHOVEL, ModItems.PUMPKIN_PIE_HOE,
                ModItems.PUMPKIN_PIE_HELMET, ModItems.PUMPKIN_PIE_CHESTPLATE,
                ModItems.PUMPKIN_PIE_LEGGINGS, ModItems.PUMPKIN_PIE_BOOTS);

        buildFoodAdv(consumer, cakeAdv, "mushroom_craft", "mushroom_eat", "mushroom_full_set",
                ModItems.MUSHROOM_SWORD, Items.RED_MUSHROOM,
                ModItems.MUSHROOM_SWORD, ModItems.MUSHROOM_PICKAXE,
                ModItems.MUSHROOM_AXE, ModItems.MUSHROOM_SHOVEL, ModItems.MUSHROOM_HOE,
                ModItems.MUSHROOM_HELMET, ModItems.MUSHROOM_CHESTPLATE,
                ModItems.MUSHROOM_LEGGINGS, ModItems.MUSHROOM_BOOTS);

        buildFoodAdv(consumer, cakeAdv, "pufferfish_craft", "pufferfish_eat", "pufferfish_full_set",
                ModItems.PUFFERFISH_SWORD, Items.PUFFERFISH,
                ModItems.PUFFERFISH_SWORD, ModItems.PUFFERFISH_PICKAXE,
                ModItems.PUFFERFISH_AXE, ModItems.PUFFERFISH_SHOVEL, ModItems.PUFFERFISH_HOE,
                ModItems.PUFFERFISH_HELMET, ModItems.PUFFERFISH_CHESTPLATE,
                ModItems.PUFFERFISH_LEGGINGS, ModItems.PUFFERFISH_BOOTS);

        buildFoodAdv(consumer, cakeAdv, "honey_craft", "honey_eat", "honey_full_set",
                ModItems.HONEY_SWORD, Items.HONEY_BOTTLE,
                ModItems.HONEY_SWORD, ModItems.HONEY_PICKAXE,
                ModItems.HONEY_AXE, ModItems.HONEY_SHOVEL, ModItems.HONEY_HOE,
                ModItems.HONEY_HELMET, ModItems.HONEY_CHESTPLATE,
                ModItems.HONEY_LEGGINGS, ModItems.HONEY_BOOTS);

        buildFoodAdv(consumer, cakeAdv, "chorus_fruit_craft", "chorus_fruit_eat", "chorus_fruit_full_set",
                ModItems.CHORUS_FRUIT_SWORD, Items.CHORUS_FRUIT,
                ModItems.CHORUS_FRUIT_SWORD, ModItems.CHORUS_FRUIT_PICKAXE,
                ModItems.CHORUS_FRUIT_AXE, ModItems.CHORUS_FRUIT_SHOVEL, ModItems.CHORUS_FRUIT_HOE,
                ModItems.CHORUS_FRUIT_HELMET, ModItems.CHORUS_FRUIT_CHESTPLATE,
                ModItems.CHORUS_FRUIT_LEGGINGS, ModItems.CHORUS_FRUIT_BOOTS);

        buildFoodAdv(consumer, cakeAdv, "golden_apple_craft", "golden_apple_eat", "golden_apple_full_set",
                ModItems.GOLDEN_APPLE_SWORD, Items.GOLDEN_APPLE,
                ModItems.GOLDEN_APPLE_SWORD, ModItems.GOLDEN_APPLE_PICKAXE,
                ModItems.GOLDEN_APPLE_AXE, ModItems.GOLDEN_APPLE_SHOVEL, ModItems.GOLDEN_APPLE_HOE,
                ModItems.GOLDEN_APPLE_HELMET, ModItems.GOLDEN_APPLE_CHESTPLATE,
                ModItems.GOLDEN_APPLE_LEGGINGS, ModItems.GOLDEN_APPLE_BOOTS);
    }

    // -----------------------------------------------------------------------
    // Food advancement helper
    // -----------------------------------------------------------------------

    private static void buildFoodAdv(Consumer<net.minecraft.advancement.AdvancementEntry> consumer, net.minecraft.advancement.AdvancementEntry parent,
                                     String craftKey, String eatKey, String fullSetKey,
                                     Item displayItem, ItemConvertible eatIcon,
                                     Item sword, Item pickaxe,
                                     Item axe, Item shovel,
                                     Item hoe,
                                     Item helmet, Item chestplate,
                                     Item leggings, Item boots) {
        Item[] tools = {sword, pickaxe, axe, shovel, hoe};

        // Craft advancement -- OR of any tool
        Advancement.Builder craft = Advancement.Builder.create()
                .parent(parent)
                .display(displayItem, title(craftKey), desc(craftKey),
                        null, AdvancementFrame.TASK, true, true, false);
        for (int i = 0; i < tools.length; i++) {
            craft.criterion("has_tool_" + i, InventoryChangedCriterion.Conditions.items(tools[i]));
        }
        net.minecraft.advancement.AdvancementEntry craftAdv = craft.criteriaMerger(net.minecraft.advancement.AdvancementRequirements.CriterionMerger.OR)
                .build(consumer, id(craftKey));

        // Eat advancement -- OR of consuming any of the 5 tools
        Advancement.Builder eat = Advancement.Builder.create()
                .parent(craftAdv)
                .display(eatIcon, title(eatKey), desc(eatKey),
                        null, AdvancementFrame.TASK, true, true, true);
        for (int i = 0; i < tools.length; i++) {
            eat.criterion("eat_tool_" + i,
                    ConsumeItemCriterion.Conditions.item(tools[i]));
        }
        net.minecraft.advancement.AdvancementEntry eatAdv = eat.criteriaMerger(net.minecraft.advancement.AdvancementRequirements.CriterionMerger.OR)
                .build(consumer, id(eatKey));

        // Full set advancement -- AND of all 9 items
        Advancement.Builder.create()
                .parent(eatAdv)
                .display(chestplate, title(fullSetKey), desc(fullSetKey),
                        null, AdvancementFrame.GOAL, true, true, false)
                .criterion("has_sword", InventoryChangedCriterion.Conditions.items(sword))
                .criterion("has_pickaxe", InventoryChangedCriterion.Conditions.items(pickaxe))
                .criterion("has_axe", InventoryChangedCriterion.Conditions.items(axe))
                .criterion("has_shovel", InventoryChangedCriterion.Conditions.items(shovel))
                .criterion("has_hoe", InventoryChangedCriterion.Conditions.items(hoe))
                .criterion("has_helmet", InventoryChangedCriterion.Conditions.items(helmet))
                .criterion("has_chestplate", InventoryChangedCriterion.Conditions.items(chestplate))
                .criterion("has_leggings", InventoryChangedCriterion.Conditions.items(leggings))
                .criterion("has_boots", InventoryChangedCriterion.Conditions.items(boots))
                .criteriaMerger(net.minecraft.advancement.AdvancementRequirements.CriterionMerger.AND)
                .build(consumer, id(fullSetKey));
    }

    // -----------------------------------------------------------------------
    // Vanilla material set helpers
    // -----------------------------------------------------------------------

    /** Tools-only set: craft (OR any tool) + full set (AND all 5 tools). */
    private static void buildVanillaToolAdv(Consumer<net.minecraft.advancement.AdvancementEntry> consumer, net.minecraft.advancement.AdvancementEntry parent,
                                            String craftKey, String setKey,
                                            Item displayItem,
                                            Item... tools) {
        Advancement.Builder craft = Advancement.Builder.create()
                .parent(parent)
                .display(displayItem, title(craftKey), desc(craftKey),
                        null, AdvancementFrame.TASK, true, true, false);
        for (int i = 0; i < tools.length; i++) {
            craft.criterion("has_tool_" + i, InventoryChangedCriterion.Conditions.items(tools[i]));
        }
        net.minecraft.advancement.AdvancementEntry craftAdv = craft.criteriaMerger(net.minecraft.advancement.AdvancementRequirements.CriterionMerger.OR)
                .build(consumer, id(craftKey));

        Advancement.Builder set = Advancement.Builder.create()
                .parent(craftAdv)
                .display(tools[1], title(setKey), desc(setKey),
                        null, AdvancementFrame.GOAL, true, true, false);
        for (int i = 0; i < tools.length; i++) {
            set.criterion("has_tool_" + i, InventoryChangedCriterion.Conditions.items(tools[i]));
        }
        set.criteriaMerger(net.minecraft.advancement.AdvancementRequirements.CriterionMerger.AND)
                .build(consumer, id(setKey));
    }

    /** Tools+Armor set: craft (OR any of 9 items) + full set (AND all 9 items). */
    private static void buildVanillaSetAdv(Consumer<net.minecraft.advancement.AdvancementEntry> consumer, net.minecraft.advancement.AdvancementEntry parent,
                                           String craftKey, String setKey,
                                           Item displayItem,
                                           Item sword, Item pickaxe,
                                           Item axe, Item shovel,
                                           Item hoe,
                                           Item helmet, Item chestplate,
                                           Item leggings, Item boots) {
        Item[] all = {sword, pickaxe, axe, shovel, hoe, helmet, chestplate, leggings, boots};

        Advancement.Builder craft = Advancement.Builder.create()
                .parent(parent)
                .display(displayItem, title(craftKey), desc(craftKey),
                        null, AdvancementFrame.TASK, true, true, false);
        for (int i = 0; i < all.length; i++) {
            craft.criterion("has_item_" + i, InventoryChangedCriterion.Conditions.items(all[i]));
        }
        net.minecraft.advancement.AdvancementEntry craftAdv = craft.criteriaMerger(net.minecraft.advancement.AdvancementRequirements.CriterionMerger.OR)
                .build(consumer, id(craftKey));

        Advancement.Builder set = Advancement.Builder.create()
                .parent(craftAdv)
                .display(chestplate, title(setKey), desc(setKey),
                        null, AdvancementFrame.GOAL, true, true, false);
        for (int i = 0; i < all.length; i++) {
            set.criterion("has_item_" + i, InventoryChangedCriterion.Conditions.items(all[i]));
        }
        set.criteriaMerger(net.minecraft.advancement.AdvancementRequirements.CriterionMerger.AND)
                .build(consumer, id(setKey));
    }

    // -----------------------------------------------------------------------
    // Helpers
    // -----------------------------------------------------------------------

    private static String id(String path) {
        return UsefultoolsMod.MOD_ID + ":" + path;
    }

    private static Text title(String key) {
        return Text.translatable("advancements." + UsefultoolsMod.MOD_ID + "." + key + ".title");
    }

    private static Text desc(String key) {
        return Text.translatable("advancements." + UsefultoolsMod.MOD_ID + "." + key + ".description");
    }
}
