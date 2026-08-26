package com.stonytark.usefultoolsmod.item;

import com.stonytark.usefultoolsmod.generated.GeneratedRegistrationIds;


import com.stonytark.usefultoolsmod.UsefultoolsMod;
import com.stonytark.usefultoolsmod.entity.ModEntities;
import com.stonytark.usefultoolsmod.item.custom.*;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.SpawnEggItem;

import java.util.function.Function;

/**
 * Items registry — migrated from 1.21.1 Fabric to Fabric 26.1.2.
 *
 * <p>Key shape changes vs 1.21.1:
 * <ul>
 *   <li>Sword/Pickaxe/Shovel/Axe/Hoe/ArmorItem classes were removed in 1.21.5. We use plain
 *       {@link Item} with {@code Item.Properties#sword/.pickaxe/.shovel/.axe/.hoe/.humanoidArmor}
 *       to apply tool/armor shape.</li>
 *   <li>{@code Item.Properties} requires an id (1.21.2+); the {@link #register(String, Function)}
 *       helper sets it via {@link Identifier#fromNamespaceAndPath} → {@link ResourceKey}.</li>
 *   <li>Custom subclasses (CoalSwordItem, EctoSwordItem, EdibleSwordItem, ModArmorItem, etc.)
 *       now extend plain {@code Item} with a single-arg {@code (Item.Properties)} constructor.</li>
 *   <li>{@code ArmorItem.Type} -> {@link ArmorType}.</li>
 * </ul>
 */
public class ModItems {

    private static FoodProperties food(int nutrition) {
        return new FoodProperties.Builder().nutrition(nutrition).saturationModifier(0.1f).build();
    }

    // ── Material items ─────────────────────────────────────────────────────

    public static final Item RGOLD = register(GeneratedRegistrationIds.Items.RGOLD,
            p -> new Item(p.stacksTo(64)));
    public static final Item RAW_RGOLD = register(GeneratedRegistrationIds.Items.RAW_RGOLD,
            p -> new Item(p.stacksTo(64)));
    public static final Item OBSHARD = register(GeneratedRegistrationIds.Items.OBSHARD,
            p -> new Item(p.stacksTo(64)));
    public static final Item SEM = register(GeneratedRegistrationIds.Items.SEM,
            p -> new Item(p.stacksTo(64)));
    public static final Item OBINGOT = register(GeneratedRegistrationIds.Items.OBINGOT,
            p -> new Item(p.stacksTo(64)));
    public static final Item GRENADE = register(GeneratedRegistrationIds.Items.GRENADE,
            p -> new Grenade(p.stacksTo(16)));
    public static final Item HRED = register(GeneratedRegistrationIds.Items.HRED,
            p -> new Item(p.stacksTo(64)));
    public static final Item HGLOW = register(GeneratedRegistrationIds.Items.HGLOW,
            p -> new Item(p.stacksTo(64)));
    public static final Item RLAPIS = register(GeneratedRegistrationIds.Items.RLAPIS,
            p -> new Item(p.stacksTo(64)));
    public static final Item DYNAMITE = register(GeneratedRegistrationIds.Items.DYNAMITE,
            p -> new Dynamite(p.stacksTo(16).fireResistant()));

    // ── Emerald-line tools ─────────────────────────────────────────────────
    public static final Item REMERALD_SWORD = register(GeneratedRegistrationIds.Items.REMERALD_SWORD,
            p -> new Item(p.sword(ModToolTiers.REMERALD, 3, -2.4f)));
    public static final Item REMERALD_PICKAXE = register(GeneratedRegistrationIds.Items.REMERALD_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.REMERALD, 1, -2.8f)));
    public static final Item REMERALD_SHOVEL = register(GeneratedRegistrationIds.Items.REMERALD_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.REMERALD, 1.5f, -3f)));
    public static final Item REMERALD_AXE = register(GeneratedRegistrationIds.Items.REMERALD_AXE,
            p -> new Item(p.axe(ModToolTiers.REMERALD, 6, -3.2f)));
    public static final Item REMERALD_HOE = register(GeneratedRegistrationIds.Items.REMERALD_HOE,
            p -> new Item(p.hoe(ModToolTiers.REMERALD, 0, -3f)));

    public static final Item PEMERALD_SWORD = register(GeneratedRegistrationIds.Items.PEMERALD_SWORD,
            p -> new Item(p.sword(ModToolTiers.PEMERALD, 3, -2.4f)));
    public static final Item PEMERALD_PICKAXE = register(GeneratedRegistrationIds.Items.PEMERALD_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.PEMERALD, 1, -2.8f)));
    public static final Item PEMERALD_SHOVEL = register(GeneratedRegistrationIds.Items.PEMERALD_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.PEMERALD, 1.5f, -3f)));
    public static final Item PEMERALD_AXE = register(GeneratedRegistrationIds.Items.PEMERALD_AXE,
            p -> new Item(p.axe(ModToolTiers.PEMERALD, 6, -3.2f)));
    public static final Item PEMERALD_HOE = register(GeneratedRegistrationIds.Items.PEMERALD_HOE,
            p -> new Item(p.hoe(ModToolTiers.PEMERALD, 0, -3f)));

    public static final Item ROBSIDIAN_SWORD = register(GeneratedRegistrationIds.Items.ROBSIDIAN_SWORD,
            p -> new Item(p.sword(ModToolTiers.ROBSIDIAN, 3, -2.4f)));
    public static final Item ROBSIDIAN_PICKAXE = register(GeneratedRegistrationIds.Items.ROBSIDIAN_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.ROBSIDIAN, 1, -2.8f)));
    public static final Item ROBSIDIAN_SHOVEL = register(GeneratedRegistrationIds.Items.ROBSIDIAN_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.ROBSIDIAN, 1.5f, -3f)));
    public static final Item ROBSIDIAN_AXE = register(GeneratedRegistrationIds.Items.ROBSIDIAN_AXE,
            p -> new Item(p.axe(ModToolTiers.ROBSIDIAN, 6, -3.2f)));
    public static final Item ROBSIDIAN_HOE = register(GeneratedRegistrationIds.Items.ROBSIDIAN_HOE,
            p -> new Item(p.hoe(ModToolTiers.ROBSIDIAN, 0, -3f)));

    public static final Item POBSIDIAN_SWORD = register(GeneratedRegistrationIds.Items.POBSIDIAN_SWORD,
            p -> new Item(p.sword(ModToolTiers.POBSIDIAN, 3, -2.4f)));
    public static final Item POBSIDIAN_PICKAXE = register(GeneratedRegistrationIds.Items.POBSIDIAN_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.POBSIDIAN, 1, -2.8f)));
    public static final Item POBSIDIAN_SHOVEL = register(GeneratedRegistrationIds.Items.POBSIDIAN_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.POBSIDIAN, 1.5f, -3f)));
    public static final Item POBSIDIAN_AXE = register(GeneratedRegistrationIds.Items.POBSIDIAN_AXE,
            p -> new Item(p.axe(ModToolTiers.POBSIDIAN, 6, -3.2f)));
    public static final Item POBSIDIAN_HOE = register(GeneratedRegistrationIds.Items.POBSIDIAN_HOE,
            p -> new Item(p.hoe(ModToolTiers.POBSIDIAN, 0, -3f)));

    public static final Item OVERPOWER_SWORD = register(GeneratedRegistrationIds.Items.OVERPOWER_SWORD,
            p -> new Item(p.sword(ModToolTiers.OVERPOWER, 3, -2.4f)));
    public static final Item OVERPOWER_PICKAXE = register(GeneratedRegistrationIds.Items.OVERPOWER_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.OVERPOWER, 1, -2.8f)));
    public static final Item OVERPOWER_SHOVEL = register(GeneratedRegistrationIds.Items.OVERPOWER_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.OVERPOWER, 1.5f, -3f)));
    public static final Item OVERPOWER_AXE = register(GeneratedRegistrationIds.Items.OVERPOWER_AXE,
            p -> new Item(p.axe(ModToolTiers.OVERPOWER, 6, -3.2f)));

    public static final Item HREDSTONE_SWORD = register(GeneratedRegistrationIds.Items.HREDSTONE_SWORD,
            p -> new Item(p.sword(ModToolTiers.HREDSTONE, 3, -2.4f)));
    public static final Item HREDSTONE_PICKAXE = register(GeneratedRegistrationIds.Items.HREDSTONE_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.HREDSTONE, 1, -2.8f)));
    public static final Item HREDSTONE_SHOVEL = register(GeneratedRegistrationIds.Items.HREDSTONE_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.HREDSTONE, 1.5f, -3f)));
    public static final Item HREDSTONE_AXE = register(GeneratedRegistrationIds.Items.HREDSTONE_AXE,
            p -> new Item(p.axe(ModToolTiers.HREDSTONE, 6, -3.2f)));
    public static final Item HREDSTONE_HOE = register(GeneratedRegistrationIds.Items.HREDSTONE_HOE,
            p -> new Item(p.hoe(ModToolTiers.HREDSTONE, 0, -3f)));

    public static final Item HGLOWSTONE_SWORD = register(GeneratedRegistrationIds.Items.HGLOWSTONE_SWORD,
            p -> new Item(p.sword(ModToolTiers.HGLOWSTONE, 3, -2.4f)));
    public static final Item HGLOWSTONE_PICKAXE = register(GeneratedRegistrationIds.Items.HGLOWSTONE_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.HGLOWSTONE, 1, -2.8f)));
    public static final Item HGLOWSTONE_SHOVEL = register(GeneratedRegistrationIds.Items.HGLOWSTONE_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.HGLOWSTONE, 1.5f, -3f)));
    public static final Item HGLOWSTONE_AXE = register(GeneratedRegistrationIds.Items.HGLOWSTONE_AXE,
            p -> new Item(p.axe(ModToolTiers.HGLOWSTONE, 6, -3.2f)));
    public static final Item HGLOWSTONE_HOE = register(GeneratedRegistrationIds.Items.HGLOWSTONE_HOE,
            p -> new Item(p.hoe(ModToolTiers.HGLOWSTONE, 0, -3f)));

    public static final Item RGOLD_SWORD = register(GeneratedRegistrationIds.Items.RGOLD_SWORD,
            p -> new Item(p.sword(ModToolTiers.RGOLD, 3, -2.4f)));
    public static final Item RGOLD_PICKAXE = register(GeneratedRegistrationIds.Items.RGOLD_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.RGOLD, 1, -2.8f)));
    public static final Item RGOLD_SHOVEL = register(GeneratedRegistrationIds.Items.RGOLD_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.RGOLD, 1.5f, -3f)));
    public static final Item RGOLD_AXE = register(GeneratedRegistrationIds.Items.RGOLD_AXE,
            p -> new Item(p.axe(ModToolTiers.RGOLD, 6, -3.2f)));
    public static final Item RGOLD_HOE = register(GeneratedRegistrationIds.Items.RGOLD_HOE,
            p -> new Item(p.hoe(ModToolTiers.RGOLD, 0, -3f)));

    public static final Item RLAPIS_SWORD = register(GeneratedRegistrationIds.Items.RLAPIS_SWORD,
            p -> new Item(p.sword(ModToolTiers.RLAPIS, 3, -2.4f)));
    public static final Item RLAPIS_PICKAXE = register(GeneratedRegistrationIds.Items.RLAPIS_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.RLAPIS, 1, -2.8f)));
    public static final Item RLAPIS_SHOVEL = register(GeneratedRegistrationIds.Items.RLAPIS_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.RLAPIS, 1.5f, -3f)));
    public static final Item RLAPIS_AXE = register(GeneratedRegistrationIds.Items.RLAPIS_AXE,
            p -> new Item(p.axe(ModToolTiers.RLAPIS, 6, -3.2f)));
    public static final Item RLAPIS_HOE = register(GeneratedRegistrationIds.Items.RLAPIS_HOE,
            p -> new Item(p.hoe(ModToolTiers.RLAPIS, 0, -3f)));

    // ── Armor pieces ───────────────────────────────────────────────────────
    public static final Item EMERALD_HELMET = register(GeneratedRegistrationIds.Items.EMERALD_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.EMERALD_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final Item EMERALD_CHESTPLATE = register(GeneratedRegistrationIds.Items.EMERALD_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.EMERALD_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final Item EMERALD_LEGGINGS = register(GeneratedRegistrationIds.Items.EMERALD_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.EMERALD_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final Item EMERALD_BOOTS = register(GeneratedRegistrationIds.Items.EMERALD_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.EMERALD_ARMOR_MATERIAL, ArmorType.BOOTS)));

    public static final Item HRED_HELMET = register(GeneratedRegistrationIds.Items.HRED_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.HRED_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final Item HRED_CHESTPLATE = register(GeneratedRegistrationIds.Items.HRED_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.HRED_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final Item HRED_LEGGINGS = register(GeneratedRegistrationIds.Items.HRED_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.HRED_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final Item HRED_BOOTS = register(GeneratedRegistrationIds.Items.HRED_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.HRED_ARMOR_MATERIAL, ArmorType.BOOTS)));

    public static final Item HGLOW_HELMET = register(GeneratedRegistrationIds.Items.HGLOW_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.HGLOW_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final Item HGLOW_CHESTPLATE = register(GeneratedRegistrationIds.Items.HGLOW_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.HGLOW_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final Item HGLOW_LEGGINGS = register(GeneratedRegistrationIds.Items.HGLOW_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.HGLOW_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final Item HGLOW_BOOTS = register(GeneratedRegistrationIds.Items.HGLOW_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.HGLOW_ARMOR_MATERIAL, ArmorType.BOOTS)));

    public static final Item OBSIDIAN_HELMET = register(GeneratedRegistrationIds.Items.OBSIDIAN_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.OBSIDIAN_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final Item OBSIDIAN_CHESTPLATE = register(GeneratedRegistrationIds.Items.OBSIDIAN_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.OBSIDIAN_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final Item OBSIDIAN_LEGGINGS = register(GeneratedRegistrationIds.Items.OBSIDIAN_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.OBSIDIAN_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final Item OBSIDIAN_BOOTS = register(GeneratedRegistrationIds.Items.OBSIDIAN_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.OBSIDIAN_ARMOR_MATERIAL, ArmorType.BOOTS)));

    public static final Item RGOLD_HELMET = register(GeneratedRegistrationIds.Items.RGOLD_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.RGOLD_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final Item RGOLD_CHESTPLATE = register(GeneratedRegistrationIds.Items.RGOLD_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.RGOLD_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final Item RGOLD_LEGGINGS = register(GeneratedRegistrationIds.Items.RGOLD_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.RGOLD_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final Item RGOLD_BOOTS = register(GeneratedRegistrationIds.Items.RGOLD_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.RGOLD_ARMOR_MATERIAL, ArmorType.BOOTS)));

    public static final Item RLAPIS_HELMET = register(GeneratedRegistrationIds.Items.RLAPIS_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.RLAPIS_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final Item RLAPIS_CHESTPLATE = register(GeneratedRegistrationIds.Items.RLAPIS_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.RLAPIS_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final Item RLAPIS_LEGGINGS = register(GeneratedRegistrationIds.Items.RLAPIS_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.RLAPIS_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final Item RLAPIS_BOOTS = register(GeneratedRegistrationIds.Items.RLAPIS_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.RLAPIS_ARMOR_MATERIAL, ArmorType.BOOTS)));

    // OVERPOWER armor — helmet uses ModArmorItem to drive set-effects.
    public static final Item OVERPOWER_HELMET = register(GeneratedRegistrationIds.Items.OVERPOWER_HELMET,
            p -> new ModArmorItem(p.humanoidArmor(ModArmorMaterials.OVERPOWER_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final Item OVERPOWER_CHESTPLATE = register(GeneratedRegistrationIds.Items.OVERPOWER_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.OVERPOWER_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final Item OVERPOWER_LEGGINGS = register(GeneratedRegistrationIds.Items.OVERPOWER_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.OVERPOWER_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final Item OVERPOWER_BOOTS = register(GeneratedRegistrationIds.Items.OVERPOWER_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.OVERPOWER_ARMOR_MATERIAL, ArmorType.BOOTS)));

    // 1.21.5+: SpawnEggItem(Properties) only; the entity type is carried by the
    // ENTITY_DATA component set via Properties#spawnEgg(EntityType).
    public static final Item GHOST_SPAWN_EGG = register(GeneratedRegistrationIds.Items.GHOST_SPAWN_EGG,
            p -> new SpawnEggItem(p.spawnEgg(ModEntities.GHOST)));

    public static final Item ECTOPLASM = register(GeneratedRegistrationIds.Items.ECTOPLASM,
            p -> new Item(p.stacksTo(64)));
    public static final Item CONDENSED_ECTOPLASM=register(GeneratedRegistrationIds.Items.CONDENSED_ECTOPLASM,p->new Item(p.stacksTo(64)));
    public static final Item WRAITH_SPAWN_EGG=register(GeneratedRegistrationIds.Items.WRAITH_SPAWN_EGG,p->new SpawnEggItem(p.spawnEgg(ModEntities.WRAITH)));
    public static final Item SPECTRAL_RESONATOR=register(GeneratedRegistrationIds.Items.SPECTRAL_RESONATOR,p->new SpectralResonatorItem(p.durability(128)));
    public static final Item STICKY_DYNAMITE=register(GeneratedRegistrationIds.Items.STICKY_DYNAMITE,p->new StickyDynamiteItem(p.stacksTo(16)));
    public static final Item REMOTE_DETONATOR=register(GeneratedRegistrationIds.Items.REMOTE_DETONATOR,p->new RemoteDetonatorItem(p.stacksTo(1)));

    // ── Rough Ectoplasm tools (RECTO, stone-tier) ──────────────────────────
    public static final Item RECTO_SWORD = register(GeneratedRegistrationIds.Items.RECTO_SWORD,
            p -> new EctoSwordItem(p.sword(ModToolTiers.RECTO, 3, -2.4f)));
    public static final Item RECTO_PICKAXE = register(GeneratedRegistrationIds.Items.RECTO_PICKAXE,
            p -> new EctoPickaxeItem(p.pickaxe(ModToolTiers.RECTO, 1, -2.8f)));
    public static final Item RECTO_SHOVEL = register(GeneratedRegistrationIds.Items.RECTO_SHOVEL,
            p -> new EctoShovelItem(p.shovel(ModToolTiers.RECTO, 1.5f, -3f)));
    public static final Item RECTO_AXE = register(GeneratedRegistrationIds.Items.RECTO_AXE,
            p -> new EctoAxeItem(p.axe(ModToolTiers.RECTO, 6, -3.2f)));
    public static final Item RECTO_HOE = register(GeneratedRegistrationIds.Items.RECTO_HOE,
            p -> new EctoHoeItem(p.hoe(ModToolTiers.RECTO, 0, -3f)));

    // ── Refined Ectoplasm + Ectoplasm tools/armor ──────────────────────────
    public static final Item REFINED_ECTOPLASM = register(GeneratedRegistrationIds.Items.REFINED_ECTOPLASM,
            p -> new Item(p.stacksTo(64)));

    public static final Item ECTO_SWORD = register(GeneratedRegistrationIds.Items.ECTO_SWORD,
            p -> new EctoSwordItem(p.sword(ModToolTiers.ECTOPLASM, 3, -2.4f)));
    public static final Item ECTO_PICKAXE = register(GeneratedRegistrationIds.Items.ECTO_PICKAXE,
            p -> new EctoPickaxeItem(p.pickaxe(ModToolTiers.ECTOPLASM, 1, -2.8f)));
    public static final Item ECTO_SHOVEL = register(GeneratedRegistrationIds.Items.ECTO_SHOVEL,
            p -> new EctoShovelItem(p.shovel(ModToolTiers.ECTOPLASM, 1.5f, -3f)));
    public static final Item ECTO_AXE = register(GeneratedRegistrationIds.Items.ECTO_AXE,
            p -> new EctoAxeItem(p.axe(ModToolTiers.ECTOPLASM, 6, -3.1f)));
    public static final Item ECTO_HOE = register(GeneratedRegistrationIds.Items.ECTO_HOE,
            p -> new EctoHoeItem(p.hoe(ModToolTiers.ECTOPLASM, 0, -3f)));

    public static final Item ECTO_HELMET = register(GeneratedRegistrationIds.Items.ECTO_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.ECTO_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final Item ECTO_CHESTPLATE = register(GeneratedRegistrationIds.Items.ECTO_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.ECTO_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final Item ECTO_LEGGINGS = register(GeneratedRegistrationIds.Items.ECTO_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.ECTO_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final Item ECTO_BOOTS = register(GeneratedRegistrationIds.Items.ECTO_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.ECTO_ARMOR_MATERIAL, ArmorType.BOOTS)));

    // ── Coal material items + tools + armor ────────────────────────────────
    public static final Item COAL_DUST = register(GeneratedRegistrationIds.Items.COAL_DUST,
            p -> new Item(p.stacksTo(64)));
    public static final Item HARDENED_COAL = register(GeneratedRegistrationIds.Items.HARDENED_COAL,
            p -> new Item(p.stacksTo(64)));

    public static final Item COAL_SWORD = register(GeneratedRegistrationIds.Items.COAL_SWORD,
            p -> new CoalSwordItem(p.sword(ModToolTiers.COAL_TOOL, 2, -2.4f)));
    public static final Item COAL_PICKAXE = register(GeneratedRegistrationIds.Items.COAL_PICKAXE,
            p -> new CoalPickaxeItem(p.pickaxe(ModToolTiers.COAL_TOOL, 1, -2.8f)));
    public static final Item COAL_SHOVEL = register(GeneratedRegistrationIds.Items.COAL_SHOVEL,
            p -> new CoalShovelItem(p.shovel(ModToolTiers.COAL_TOOL, 1.5f, -3f)));
    public static final Item COAL_AXE = register(GeneratedRegistrationIds.Items.COAL_AXE,
            p -> new CoalAxeItem(p.axe(ModToolTiers.COAL_TOOL, 5, -3.2f)));
    public static final Item COAL_HOE = register(GeneratedRegistrationIds.Items.COAL_HOE,
            p -> new CoalHoeItem(p.hoe(ModToolTiers.COAL_TOOL, 0, -3f)));

    public static final Item COAL_HELMET = register(GeneratedRegistrationIds.Items.COAL_HELMET,
            p -> new CoalArmorItem(p.humanoidArmor(ModArmorMaterials.COAL_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final Item COAL_CHESTPLATE = register(GeneratedRegistrationIds.Items.COAL_CHESTPLATE,
            p -> new CoalArmorItem(p.humanoidArmor(ModArmorMaterials.COAL_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final Item COAL_LEGGINGS = register(GeneratedRegistrationIds.Items.COAL_LEGGINGS,
            p -> new CoalArmorItem(p.humanoidArmor(ModArmorMaterials.COAL_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final Item COAL_BOOTS = register(GeneratedRegistrationIds.Items.COAL_BOOTS,
            p -> new CoalArmorItem(p.humanoidArmor(ModArmorMaterials.COAL_ARMOR_MATERIAL, ArmorType.BOOTS)));

    // ── Raw metal rough tool sets ──────────────────────────────────────────
    public static final Item RRAW_GOLD_SWORD = register(GeneratedRegistrationIds.Items.RRAW_GOLD_SWORD,
            p -> new Item(p.sword(ModToolTiers.RRAW_GOLD, 3, -2.4f)));
    public static final Item RRAW_GOLD_PICKAXE = register(GeneratedRegistrationIds.Items.RRAW_GOLD_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.RRAW_GOLD, 1, -2.8f)));
    public static final Item RRAW_GOLD_SHOVEL = register(GeneratedRegistrationIds.Items.RRAW_GOLD_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.RRAW_GOLD, 1.5f, -3f)));
    public static final Item RRAW_GOLD_AXE = register(GeneratedRegistrationIds.Items.RRAW_GOLD_AXE,
            p -> new Item(p.axe(ModToolTiers.RRAW_GOLD, 6, -3.2f)));
    public static final Item RRAW_GOLD_HOE = register(GeneratedRegistrationIds.Items.RRAW_GOLD_HOE,
            p -> new Item(p.hoe(ModToolTiers.RRAW_GOLD, 0, -3f)));

    public static final Item RRAW_COPPER_SWORD = register(GeneratedRegistrationIds.Items.RRAW_COPPER_SWORD,
            p -> new Item(p.sword(ModToolTiers.RRAW_COPPER, 3, -2.4f)));
    public static final Item RRAW_COPPER_PICKAXE = register(GeneratedRegistrationIds.Items.RRAW_COPPER_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.RRAW_COPPER, 1, -2.8f)));
    public static final Item RRAW_COPPER_SHOVEL = register(GeneratedRegistrationIds.Items.RRAW_COPPER_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.RRAW_COPPER, 1.5f, -3f)));
    public static final Item RRAW_COPPER_AXE = register(GeneratedRegistrationIds.Items.RRAW_COPPER_AXE,
            p -> new Item(p.axe(ModToolTiers.RRAW_COPPER, 6, -3.2f)));
    public static final Item RRAW_COPPER_HOE = register(GeneratedRegistrationIds.Items.RRAW_COPPER_HOE,
            p -> new Item(p.hoe(ModToolTiers.RRAW_COPPER, 0, -3f)));

    public static final Item RRAW_IRON_SWORD = register(GeneratedRegistrationIds.Items.RRAW_IRON_SWORD,
            p -> new Item(p.sword(ModToolTiers.RRAW_IRON, 3, -2.4f)));
    public static final Item RRAW_IRON_PICKAXE = register(GeneratedRegistrationIds.Items.RRAW_IRON_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.RRAW_IRON, 1, -2.8f)));
    public static final Item RRAW_IRON_SHOVEL = register(GeneratedRegistrationIds.Items.RRAW_IRON_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.RRAW_IRON, 1.5f, -3f)));
    public static final Item RRAW_IRON_AXE = register(GeneratedRegistrationIds.Items.RRAW_IRON_AXE,
            p -> new Item(p.axe(ModToolTiers.RRAW_IRON, 6, -3.2f)));
    public static final Item RRAW_IRON_HOE = register(GeneratedRegistrationIds.Items.RRAW_IRON_HOE,
            p -> new Item(p.hoe(ModToolTiers.RRAW_IRON, 0, -3f)));

    public static final Item RRAW_RGOLD_SWORD = register(GeneratedRegistrationIds.Items.RRAW_RGOLD_SWORD,
            p -> new Item(p.sword(ModToolTiers.RRAW_RGOLD, 3, -2.4f)));
    public static final Item RRAW_RGOLD_PICKAXE = register(GeneratedRegistrationIds.Items.RRAW_RGOLD_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.RRAW_RGOLD, 1, -2.8f)));
    public static final Item RRAW_RGOLD_SHOVEL = register(GeneratedRegistrationIds.Items.RRAW_RGOLD_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.RRAW_RGOLD, 1.5f, -3f)));
    public static final Item RRAW_RGOLD_AXE = register(GeneratedRegistrationIds.Items.RRAW_RGOLD_AXE,
            p -> new Item(p.axe(ModToolTiers.RRAW_RGOLD, 6, -3.2f)));
    public static final Item RRAW_RGOLD_HOE = register(GeneratedRegistrationIds.Items.RRAW_RGOLD_HOE,
            p -> new Item(p.hoe(ModToolTiers.RRAW_RGOLD, 0, -3f)));

    public static final Item RSCRAP_SWORD = register(GeneratedRegistrationIds.Items.RSCRAP_SWORD,
            p -> new Item(p.sword(ModToolTiers.RSCRAP, 3, -2.4f)));
    public static final Item RSCRAP_PICKAXE = register(GeneratedRegistrationIds.Items.RSCRAP_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.RSCRAP, 1, -2.8f)));
    public static final Item RSCRAP_SHOVEL = register(GeneratedRegistrationIds.Items.RSCRAP_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.RSCRAP, 1.5f, -3f)));
    public static final Item RSCRAP_AXE = register(GeneratedRegistrationIds.Items.RSCRAP_AXE,
            p -> new Item(p.axe(ModToolTiers.RSCRAP, 6, -3.2f)));
    public static final Item RSCRAP_HOE = register(GeneratedRegistrationIds.Items.RSCRAP_HOE,
            p -> new Item(p.hoe(ModToolTiers.RSCRAP, 0, -3f)));

    // ── Crystal / element materials ────────────────────────────────────────
    public static final Item CALCIFIED_AMETHYST = register(GeneratedRegistrationIds.Items.CALCIFIED_AMETHYST,
            p -> new Item(p.stacksTo(64)));
    public static final Item GLACIAL_SHARD = register(GeneratedRegistrationIds.Items.GLACIAL_SHARD,
            p -> new Item(p.stacksTo(64)));
    public static final Item POLISHED_QUARTZ = register(GeneratedRegistrationIds.Items.POLISHED_QUARTZ,
            p -> new Item(p.stacksTo(64)));
    public static final Item POLISHED_PRISMARINE = register(GeneratedRegistrationIds.Items.POLISHED_PRISMARINE,
            p -> new Item(p.stacksTo(64)));

    public static final Item RAMETHYST_SWORD = register(GeneratedRegistrationIds.Items.RAMETHYST_SWORD,
            p -> new Item(p.sword(ModToolTiers.RAMETHYST, 3, -2.4f)));
    public static final Item RAMETHYST_PICKAXE = register(GeneratedRegistrationIds.Items.RAMETHYST_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.RAMETHYST, 1, -2.8f)));
    public static final Item RAMETHYST_SHOVEL = register(GeneratedRegistrationIds.Items.RAMETHYST_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.RAMETHYST, 1.5f, -3f)));
    public static final Item RAMETHYST_AXE = register(GeneratedRegistrationIds.Items.RAMETHYST_AXE,
            p -> new Item(p.axe(ModToolTiers.RAMETHYST, 6, -3.2f)));
    public static final Item RAMETHYST_HOE = register(GeneratedRegistrationIds.Items.RAMETHYST_HOE,
            p -> new Item(p.hoe(ModToolTiers.RAMETHYST, 0, -3f)));

    public static final Item SNOW_SWORD = register(GeneratedRegistrationIds.Items.SNOW_SWORD,
            p -> new Item(p.sword(ModToolTiers.SNOW_TOOL, 3, -2.4f)));
    public static final Item SNOW_PICKAXE = register(GeneratedRegistrationIds.Items.SNOW_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.SNOW_TOOL, 1, -2.8f)));
    public static final Item SNOW_SHOVEL = register(GeneratedRegistrationIds.Items.SNOW_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.SNOW_TOOL, 1.5f, -3f)));
    public static final Item SNOW_AXE = register(GeneratedRegistrationIds.Items.SNOW_AXE,
            p -> new Item(p.axe(ModToolTiers.SNOW_TOOL, 6, -3.2f)));
    public static final Item SNOW_HOE = register(GeneratedRegistrationIds.Items.SNOW_HOE,
            p -> new Item(p.hoe(ModToolTiers.SNOW_TOOL, 0, -3f)));

    public static final Item RQUARTZ_SWORD = register(GeneratedRegistrationIds.Items.RQUARTZ_SWORD,
            p -> new Item(p.sword(ModToolTiers.RQUARTZ, 3, -2.4f)));
    public static final Item RQUARTZ_PICKAXE = register(GeneratedRegistrationIds.Items.RQUARTZ_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.RQUARTZ, 1, -2.8f)));
    public static final Item RQUARTZ_SHOVEL = register(GeneratedRegistrationIds.Items.RQUARTZ_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.RQUARTZ, 1.5f, -3f)));
    public static final Item RQUARTZ_AXE = register(GeneratedRegistrationIds.Items.RQUARTZ_AXE,
            p -> new Item(p.axe(ModToolTiers.RQUARTZ, 6, -3.2f)));
    public static final Item RQUARTZ_HOE = register(GeneratedRegistrationIds.Items.RQUARTZ_HOE,
            p -> new Item(p.hoe(ModToolTiers.RQUARTZ, 0, -3f)));

    public static final Item RPRISM_SWORD = register(GeneratedRegistrationIds.Items.RPRISM_SWORD,
            p -> new Item(p.sword(ModToolTiers.RPRISM, 3, -2.4f)));
    public static final Item RPRISM_PICKAXE = register(GeneratedRegistrationIds.Items.RPRISM_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.RPRISM, 1, -2.8f)));
    public static final Item RPRISM_SHOVEL = register(GeneratedRegistrationIds.Items.RPRISM_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.RPRISM, 1.5f, -3f)));
    public static final Item RPRISM_AXE = register(GeneratedRegistrationIds.Items.RPRISM_AXE,
            p -> new Item(p.axe(ModToolTiers.RPRISM, 6, -3.2f)));
    public static final Item RPRISM_HOE = register(GeneratedRegistrationIds.Items.RPRISM_HOE,
            p -> new Item(p.hoe(ModToolTiers.RPRISM, 0, -3f)));

    public static final Item CAMETHYST_SWORD = register(GeneratedRegistrationIds.Items.CAMETHYST_SWORD,
            p -> new Item(p.sword(ModToolTiers.CAMETHYST, 3, -2.4f)));
    public static final Item CAMETHYST_PICKAXE = register(GeneratedRegistrationIds.Items.CAMETHYST_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.CAMETHYST, 1, -2.8f)));
    public static final Item CAMETHYST_SHOVEL = register(GeneratedRegistrationIds.Items.CAMETHYST_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.CAMETHYST, 1.5f, -3f)));
    public static final Item CAMETHYST_AXE = register(GeneratedRegistrationIds.Items.CAMETHYST_AXE,
            p -> new Item(p.axe(ModToolTiers.CAMETHYST, 6, -3.2f)));
    public static final Item CAMETHYST_HOE = register(GeneratedRegistrationIds.Items.CAMETHYST_HOE,
            p -> new Item(p.hoe(ModToolTiers.CAMETHYST, 0, -3f)));
    public static final Item CAMETHYST_HELMET = register(GeneratedRegistrationIds.Items.CAMETHYST_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.CAMETHYST_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final Item CAMETHYST_CHESTPLATE = register(GeneratedRegistrationIds.Items.CAMETHYST_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.CAMETHYST_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final Item CAMETHYST_LEGGINGS = register(GeneratedRegistrationIds.Items.CAMETHYST_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.CAMETHYST_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final Item CAMETHYST_BOOTS = register(GeneratedRegistrationIds.Items.CAMETHYST_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.CAMETHYST_ARMOR_MATERIAL, ArmorType.BOOTS)));

    public static final Item ICE_SWORD = register(GeneratedRegistrationIds.Items.ICE_SWORD,
            p -> new Item(p.sword(ModToolTiers.ICE_TOOL, 3, -2.4f)));
    public static final Item ICE_PICKAXE = register(GeneratedRegistrationIds.Items.ICE_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.ICE_TOOL, 1, -2.8f)));
    public static final Item ICE_SHOVEL = register(GeneratedRegistrationIds.Items.ICE_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.ICE_TOOL, 1.5f, -3f)));
    public static final Item ICE_AXE = register(GeneratedRegistrationIds.Items.ICE_AXE,
            p -> new Item(p.axe(ModToolTiers.ICE_TOOL, 6, -3.2f)));
    public static final Item ICE_HOE = register(GeneratedRegistrationIds.Items.ICE_HOE,
            p -> new Item(p.hoe(ModToolTiers.ICE_TOOL, 0, -3f)));
    public static final Item ICE_HELMET = register(GeneratedRegistrationIds.Items.ICE_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.ICE_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final Item ICE_CHESTPLATE = register(GeneratedRegistrationIds.Items.ICE_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.ICE_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final Item ICE_LEGGINGS = register(GeneratedRegistrationIds.Items.ICE_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.ICE_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final Item ICE_BOOTS = register(GeneratedRegistrationIds.Items.ICE_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.ICE_ARMOR_MATERIAL, ArmorType.BOOTS)));

    public static final Item PQUARTZ_SWORD = register(GeneratedRegistrationIds.Items.PQUARTZ_SWORD,
            p -> new Item(p.sword(ModToolTiers.PQUARTZ, 3, -2.4f)));
    public static final Item PQUARTZ_PICKAXE = register(GeneratedRegistrationIds.Items.PQUARTZ_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.PQUARTZ, 1, -2.8f)));
    public static final Item PQUARTZ_SHOVEL = register(GeneratedRegistrationIds.Items.PQUARTZ_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.PQUARTZ, 1.5f, -3f)));
    public static final Item PQUARTZ_AXE = register(GeneratedRegistrationIds.Items.PQUARTZ_AXE,
            p -> new Item(p.axe(ModToolTiers.PQUARTZ, 6, -3.2f)));
    public static final Item PQUARTZ_HOE = register(GeneratedRegistrationIds.Items.PQUARTZ_HOE,
            p -> new Item(p.hoe(ModToolTiers.PQUARTZ, 0, -3f)));
    public static final Item PQUARTZ_HELMET = register(GeneratedRegistrationIds.Items.PQUARTZ_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.PQUARTZ_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final Item PQUARTZ_CHESTPLATE = register(GeneratedRegistrationIds.Items.PQUARTZ_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.PQUARTZ_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final Item PQUARTZ_LEGGINGS = register(GeneratedRegistrationIds.Items.PQUARTZ_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.PQUARTZ_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final Item PQUARTZ_BOOTS = register(GeneratedRegistrationIds.Items.PQUARTZ_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.PQUARTZ_ARMOR_MATERIAL, ArmorType.BOOTS)));

    public static final Item PPRISM_SWORD = register(GeneratedRegistrationIds.Items.PPRISM_SWORD,
            p -> new Item(p.sword(ModToolTiers.PPRISM, 3, -2.4f)));
    public static final Item PPRISM_PICKAXE = register(GeneratedRegistrationIds.Items.PPRISM_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.PPRISM, 1, -2.8f)));
    public static final Item PPRISM_SHOVEL = register(GeneratedRegistrationIds.Items.PPRISM_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.PPRISM, 1.5f, -3f)));
    public static final Item PPRISM_AXE = register(GeneratedRegistrationIds.Items.PPRISM_AXE,
            p -> new Item(p.axe(ModToolTiers.PPRISM, 6, -3.2f)));
    public static final Item PPRISM_HOE = register(GeneratedRegistrationIds.Items.PPRISM_HOE,
            p -> new Item(p.hoe(ModToolTiers.PPRISM, 0, -3f)));
    public static final Item PPRISM_HELMET = register(GeneratedRegistrationIds.Items.PPRISM_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.PPRISM_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final Item PPRISM_CHESTPLATE = register(GeneratedRegistrationIds.Items.PPRISM_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.PPRISM_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final Item PPRISM_LEGGINGS = register(GeneratedRegistrationIds.Items.PPRISM_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.PPRISM_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final Item PPRISM_BOOTS = register(GeneratedRegistrationIds.Items.PPRISM_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.PPRISM_ARMOR_MATERIAL, ArmorType.BOOTS)));

    // ── Flint + FNI ────────────────────────────────────────────────────────
    public static final Item RFLINT_SWORD = register(GeneratedRegistrationIds.Items.RFLINT_SWORD,
            p -> new Item(p.sword(ModToolTiers.RFLINT, 3, -2.4f)));
    public static final Item RFLINT_PICKAXE = register(GeneratedRegistrationIds.Items.RFLINT_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.RFLINT, 1, -2.8f)));
    public static final Item RFLINT_SHOVEL = register(GeneratedRegistrationIds.Items.RFLINT_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.RFLINT, 1.5f, -3f)));
    public static final Item RFLINT_AXE = register(GeneratedRegistrationIds.Items.RFLINT_AXE,
            p -> new Item(p.axe(ModToolTiers.RFLINT, 6, -3.2f)));
    public static final Item RFLINT_HOE = register(GeneratedRegistrationIds.Items.RFLINT_HOE,
            p -> new Item(p.hoe(ModToolTiers.RFLINT, 0, -3f)));

    public static final Item FNI_SWORD = register(GeneratedRegistrationIds.Items.FNI_SWORD,
            p -> new Item(p.sword(ModToolTiers.FNI_TOOLS, 3, -2.4f)));
    public static final Item FNI_PICKAXE = register(GeneratedRegistrationIds.Items.FNI_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.FNI_TOOLS, 1, -2.8f)));
    public static final Item FNI_SHOVEL = register(GeneratedRegistrationIds.Items.FNI_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.FNI_TOOLS, 1.5f, -3f)));
    public static final Item FNI_AXE = register(GeneratedRegistrationIds.Items.FNI_AXE,
            p -> new Item(p.axe(ModToolTiers.FNI_TOOLS, 6, -3.2f)));
    public static final Item FNI_HOE = register(GeneratedRegistrationIds.Items.FNI_HOE,
            p -> new Item(p.hoe(ModToolTiers.FNI_TOOLS, 0, -3f)));
    public static final Item FNI_HELMET = register(GeneratedRegistrationIds.Items.FNI_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.FNI_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final Item FNI_CHESTPLATE = register(GeneratedRegistrationIds.Items.FNI_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.FNI_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final Item FNI_LEGGINGS = register(GeneratedRegistrationIds.Items.FNI_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.FNI_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final Item FNI_BOOTS = register(GeneratedRegistrationIds.Items.FNI_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.FNI_ARMOR_MATERIAL, ArmorType.BOOTS)));

    // ── Stone-rock variants (13 sets × 5 tools) ────────────────────────────
    public static final Item ANDESITE_SWORD = register(GeneratedRegistrationIds.Items.ANDESITE_SWORD,
            p -> new Item(p.sword(ModToolTiers.STONE_ANDESITE, 3, -2.4f)));
    public static final Item ANDESITE_PICKAXE = register(GeneratedRegistrationIds.Items.ANDESITE_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.STONE_ANDESITE, 1, -2.8f)));
    public static final Item ANDESITE_SHOVEL = register(GeneratedRegistrationIds.Items.ANDESITE_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.STONE_ANDESITE, 1.5f, -3f)));
    public static final Item ANDESITE_AXE = register(GeneratedRegistrationIds.Items.ANDESITE_AXE,
            p -> new Item(p.axe(ModToolTiers.STONE_ANDESITE, 6, -3.2f)));
    public static final Item ANDESITE_HOE = register(GeneratedRegistrationIds.Items.ANDESITE_HOE,
            p -> new Item(p.hoe(ModToolTiers.STONE_ANDESITE, 0, -3f)));

    public static final Item BASALT_SWORD = register(GeneratedRegistrationIds.Items.BASALT_SWORD,
            p -> new Item(p.sword(ModToolTiers.STONE_BASALT, 3, -2.5f)));
    public static final Item BASALT_PICKAXE = register(GeneratedRegistrationIds.Items.BASALT_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.STONE_BASALT, 1, -2.9f)));
    public static final Item BASALT_SHOVEL = register(GeneratedRegistrationIds.Items.BASALT_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.STONE_BASALT, 2.0f, -3.1f)));
    public static final Item BASALT_AXE = register(GeneratedRegistrationIds.Items.BASALT_AXE,
            p -> new Item(p.axe(ModToolTiers.STONE_BASALT, 7, -3.3f)));
    public static final Item BASALT_HOE = register(GeneratedRegistrationIds.Items.BASALT_HOE,
            p -> new Item(p.hoe(ModToolTiers.STONE_BASALT, 0, -3.1f)));

    public static final Item BLACKSTONE_SWORD = register(GeneratedRegistrationIds.Items.BLACKSTONE_SWORD,
            p -> new Item(p.sword(ModToolTiers.STONE_BLACKSTONE, 4, -2.5f)));
    public static final Item BLACKSTONE_PICKAXE = register(GeneratedRegistrationIds.Items.BLACKSTONE_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.STONE_BLACKSTONE, 1, -2.9f)));
    public static final Item BLACKSTONE_SHOVEL = register(GeneratedRegistrationIds.Items.BLACKSTONE_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.STONE_BLACKSTONE, 2.0f, -3.1f)));
    public static final Item BLACKSTONE_AXE = register(GeneratedRegistrationIds.Items.BLACKSTONE_AXE,
            p -> new Item(p.axe(ModToolTiers.STONE_BLACKSTONE, 7, -3.35f)));
    public static final Item BLACKSTONE_HOE = register(GeneratedRegistrationIds.Items.BLACKSTONE_HOE,
            p -> new Item(p.hoe(ModToolTiers.STONE_BLACKSTONE, 0, -3.1f)));

    public static final Item CALCITE_SWORD = register(GeneratedRegistrationIds.Items.CALCITE_SWORD,
            p -> new Item(p.sword(ModToolTiers.STONE_CALCITE, 2, -2.2f)));
    public static final Item CALCITE_PICKAXE = register(GeneratedRegistrationIds.Items.CALCITE_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.STONE_CALCITE, 1, -2.6f)));
    public static final Item CALCITE_SHOVEL = register(GeneratedRegistrationIds.Items.CALCITE_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.STONE_CALCITE, 1.0f, -2.8f)));
    public static final Item CALCITE_AXE = register(GeneratedRegistrationIds.Items.CALCITE_AXE,
            p -> new Item(p.axe(ModToolTiers.STONE_CALCITE, 5, -3.0f)));
    public static final Item CALCITE_HOE = register(GeneratedRegistrationIds.Items.CALCITE_HOE,
            p -> new Item(p.hoe(ModToolTiers.STONE_CALCITE, 0, -2.6f)));

    public static final Item DEEPSLATE_SWORD = register(GeneratedRegistrationIds.Items.DEEPSLATE_SWORD,
            p -> new Item(p.sword(ModToolTiers.STONE_DEEPSLATE, 4, -2.55f)));
    public static final Item DEEPSLATE_PICKAXE = register(GeneratedRegistrationIds.Items.DEEPSLATE_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.STONE_DEEPSLATE, 1, -2.95f)));
    public static final Item DEEPSLATE_SHOVEL = register(GeneratedRegistrationIds.Items.DEEPSLATE_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.STONE_DEEPSLATE, 2.0f, -3.15f)));
    public static final Item DEEPSLATE_AXE = register(GeneratedRegistrationIds.Items.DEEPSLATE_AXE,
            p -> new Item(p.axe(ModToolTiers.STONE_DEEPSLATE, 7, -3.4f)));
    public static final Item DEEPSLATE_HOE = register(GeneratedRegistrationIds.Items.DEEPSLATE_HOE,
            p -> new Item(p.hoe(ModToolTiers.STONE_DEEPSLATE, 0, -3.1f)));

    public static final Item DIORITE_SWORD = register(GeneratedRegistrationIds.Items.DIORITE_SWORD,
            p -> new Item(p.sword(ModToolTiers.STONE_DIORITE, 3, -2.4f)));
    public static final Item DIORITE_PICKAXE = register(GeneratedRegistrationIds.Items.DIORITE_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.STONE_DIORITE, 1, -2.8f)));
    public static final Item DIORITE_SHOVEL = register(GeneratedRegistrationIds.Items.DIORITE_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.STONE_DIORITE, 1.5f, -3f)));
    public static final Item DIORITE_AXE = register(GeneratedRegistrationIds.Items.DIORITE_AXE,
            p -> new Item(p.axe(ModToolTiers.STONE_DIORITE, 6, -3.2f)));
    public static final Item DIORITE_HOE = register(GeneratedRegistrationIds.Items.DIORITE_HOE,
            p -> new Item(p.hoe(ModToolTiers.STONE_DIORITE, 0, -2.9f)));

    public static final Item END_STONE_SWORD = register(GeneratedRegistrationIds.Items.END_STONE_SWORD,
            p -> new Item(p.sword(ModToolTiers.STONE_END_STONE, 3, -2.35f)));
    public static final Item END_STONE_PICKAXE = register(GeneratedRegistrationIds.Items.END_STONE_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.STONE_END_STONE, 1, -2.75f)));
    public static final Item END_STONE_SHOVEL = register(GeneratedRegistrationIds.Items.END_STONE_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.STONE_END_STONE, 1.5f, -2.95f)));
    public static final Item END_STONE_AXE = register(GeneratedRegistrationIds.Items.END_STONE_AXE,
            p -> new Item(p.axe(ModToolTiers.STONE_END_STONE, 6, -3.15f)));
    public static final Item END_STONE_HOE = register(GeneratedRegistrationIds.Items.END_STONE_HOE,
            p -> new Item(p.hoe(ModToolTiers.STONE_END_STONE, 0, -2.8f)));

    public static final Item GRANITE_SWORD = register(GeneratedRegistrationIds.Items.GRANITE_SWORD,
            p -> new Item(p.sword(ModToolTiers.STONE_GRANITE, 3, -2.5f)));
    public static final Item GRANITE_PICKAXE = register(GeneratedRegistrationIds.Items.GRANITE_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.STONE_GRANITE, 1, -2.9f)));
    public static final Item GRANITE_SHOVEL = register(GeneratedRegistrationIds.Items.GRANITE_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.STONE_GRANITE, 2.0f, -3.1f)));
    public static final Item GRANITE_AXE = register(GeneratedRegistrationIds.Items.GRANITE_AXE,
            p -> new Item(p.axe(ModToolTiers.STONE_GRANITE, 7, -3.3f)));
    public static final Item GRANITE_HOE = register(GeneratedRegistrationIds.Items.GRANITE_HOE,
            p -> new Item(p.hoe(ModToolTiers.STONE_GRANITE, 0, -3.1f)));

    public static final Item NETHERRACK_SWORD = register(GeneratedRegistrationIds.Items.NETHERRACK_SWORD,
            p -> new Item(p.sword(ModToolTiers.STONE_NETHERRACK, 2, -2.2f)));
    public static final Item NETHERRACK_PICKAXE = register(GeneratedRegistrationIds.Items.NETHERRACK_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.STONE_NETHERRACK, 1, -2.6f)));
    public static final Item NETHERRACK_SHOVEL = register(GeneratedRegistrationIds.Items.NETHERRACK_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.STONE_NETHERRACK, 1.0f, -2.8f)));
    public static final Item NETHERRACK_AXE = register(GeneratedRegistrationIds.Items.NETHERRACK_AXE,
            p -> new Item(p.axe(ModToolTiers.STONE_NETHERRACK, 5, -3.0f)));
    public static final Item NETHERRACK_HOE = register(GeneratedRegistrationIds.Items.NETHERRACK_HOE,
            p -> new Item(p.hoe(ModToolTiers.STONE_NETHERRACK, 0, -2.5f)));

    public static final Item SANDSTONE_SWORD = register(GeneratedRegistrationIds.Items.SANDSTONE_SWORD,
            p -> new Item(p.sword(ModToolTiers.STONE_SANDSTONE, 2, -2.3f)));
    public static final Item SANDSTONE_PICKAXE = register(GeneratedRegistrationIds.Items.SANDSTONE_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.STONE_SANDSTONE, 1, -2.7f)));
    public static final Item SANDSTONE_SHOVEL = register(GeneratedRegistrationIds.Items.SANDSTONE_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.STONE_SANDSTONE, 1.0f, -2.9f)));
    public static final Item SANDSTONE_AXE = register(GeneratedRegistrationIds.Items.SANDSTONE_AXE,
            p -> new Item(p.axe(ModToolTiers.STONE_SANDSTONE, 5, -3.1f)));
    public static final Item SANDSTONE_HOE = register(GeneratedRegistrationIds.Items.SANDSTONE_HOE,
            p -> new Item(p.hoe(ModToolTiers.STONE_SANDSTONE, 0, -2.7f)));

    public static final Item SMOOTH_BASALT_SWORD = register(GeneratedRegistrationIds.Items.SMOOTH_BASALT_SWORD,
            p -> new Item(p.sword(ModToolTiers.STONE_SMOOTH_BASALT, 3, -2.45f)));
    public static final Item SMOOTH_BASALT_PICKAXE = register(GeneratedRegistrationIds.Items.SMOOTH_BASALT_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.STONE_SMOOTH_BASALT, 1, -2.85f)));
    public static final Item SMOOTH_BASALT_SHOVEL = register(GeneratedRegistrationIds.Items.SMOOTH_BASALT_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.STONE_SMOOTH_BASALT, 1.5f, -3.05f)));
    public static final Item SMOOTH_BASALT_AXE = register(GeneratedRegistrationIds.Items.SMOOTH_BASALT_AXE,
            p -> new Item(p.axe(ModToolTiers.STONE_SMOOTH_BASALT, 6, -3.25f)));
    public static final Item SMOOTH_BASALT_HOE = register(GeneratedRegistrationIds.Items.SMOOTH_BASALT_HOE,
            p -> new Item(p.hoe(ModToolTiers.STONE_SMOOTH_BASALT, 0, -3.0f)));

    public static final Item TERRACOTTA_SWORD = register(GeneratedRegistrationIds.Items.TERRACOTTA_SWORD,
            p -> new Item(p.sword(ModToolTiers.STONE_TERRACOTTA, 3, -2.35f)));
    public static final Item TERRACOTTA_PICKAXE = register(GeneratedRegistrationIds.Items.TERRACOTTA_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.STONE_TERRACOTTA, 1, -2.75f)));
    public static final Item TERRACOTTA_SHOVEL = register(GeneratedRegistrationIds.Items.TERRACOTTA_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.STONE_TERRACOTTA, 1.5f, -2.95f)));
    public static final Item TERRACOTTA_AXE = register(GeneratedRegistrationIds.Items.TERRACOTTA_AXE,
            p -> new Item(p.axe(ModToolTiers.STONE_TERRACOTTA, 6, -3.15f)));
    public static final Item TERRACOTTA_HOE = register(GeneratedRegistrationIds.Items.TERRACOTTA_HOE,
            p -> new Item(p.hoe(ModToolTiers.STONE_TERRACOTTA, 0, -2.8f)));

    public static final Item TUFF_SWORD = register(GeneratedRegistrationIds.Items.TUFF_SWORD,
            p -> new Item(p.sword(ModToolTiers.STONE_TUFF, 2, -2.35f)));
    public static final Item TUFF_PICKAXE = register(GeneratedRegistrationIds.Items.TUFF_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.STONE_TUFF, 1, -2.75f)));
    public static final Item TUFF_SHOVEL = register(GeneratedRegistrationIds.Items.TUFF_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.STONE_TUFF, 1.5f, -2.95f)));
    public static final Item TUFF_AXE = register(GeneratedRegistrationIds.Items.TUFF_AXE,
            p -> new Item(p.axe(ModToolTiers.STONE_TUFF, 5, -3.15f)));
    public static final Item TUFF_HOE = register(GeneratedRegistrationIds.Items.TUFF_HOE,
            p -> new Item(p.hoe(ModToolTiers.STONE_TUFF, 0, -2.8f)));

    // ── Wood variants (11 × 5) ─────────────────────────────────────────────
    public static final Item OAK_SWORD = register(GeneratedRegistrationIds.Items.OAK_SWORD,
            p -> new Item(p.sword(ModToolTiers.WOOD_OAK, 3, -2.4f)));
    public static final Item OAK_PICKAXE = register(GeneratedRegistrationIds.Items.OAK_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.WOOD_OAK, 1, -2.8f)));
    public static final Item OAK_SHOVEL = register(GeneratedRegistrationIds.Items.OAK_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.WOOD_OAK, 1.5f, -3f)));
    public static final Item OAK_AXE = register(GeneratedRegistrationIds.Items.OAK_AXE,
            p -> new Item(p.axe(ModToolTiers.WOOD_OAK, 6, -3.2f)));
    public static final Item OAK_HOE = register(GeneratedRegistrationIds.Items.OAK_HOE,
            p -> new Item(p.hoe(ModToolTiers.WOOD_OAK, 0, -3f)));

    public static final Item SPRUCE_SWORD = register(GeneratedRegistrationIds.Items.SPRUCE_SWORD,
            p -> new Item(p.sword(ModToolTiers.WOOD_SPRUCE, 3, -2.4f)));
    public static final Item SPRUCE_PICKAXE = register(GeneratedRegistrationIds.Items.SPRUCE_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.WOOD_SPRUCE, 1, -2.8f)));
    public static final Item SPRUCE_SHOVEL = register(GeneratedRegistrationIds.Items.SPRUCE_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.WOOD_SPRUCE, 1.5f, -3f)));
    public static final Item SPRUCE_AXE = register(GeneratedRegistrationIds.Items.SPRUCE_AXE,
            p -> new Item(p.axe(ModToolTiers.WOOD_SPRUCE, 6, -3.2f)));
    public static final Item SPRUCE_HOE = register(GeneratedRegistrationIds.Items.SPRUCE_HOE,
            p -> new Item(p.hoe(ModToolTiers.WOOD_SPRUCE, 0, -3f)));

    public static final Item BIRCH_SWORD = register(GeneratedRegistrationIds.Items.BIRCH_SWORD,
            p -> new Item(p.sword(ModToolTiers.WOOD_BIRCH, 3, -2.4f)));
    public static final Item BIRCH_PICKAXE = register(GeneratedRegistrationIds.Items.BIRCH_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.WOOD_BIRCH, 1, -2.8f)));
    public static final Item BIRCH_SHOVEL = register(GeneratedRegistrationIds.Items.BIRCH_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.WOOD_BIRCH, 1.5f, -3f)));
    public static final Item BIRCH_AXE = register(GeneratedRegistrationIds.Items.BIRCH_AXE,
            p -> new Item(p.axe(ModToolTiers.WOOD_BIRCH, 6, -3.2f)));
    public static final Item BIRCH_HOE = register(GeneratedRegistrationIds.Items.BIRCH_HOE,
            p -> new Item(p.hoe(ModToolTiers.WOOD_BIRCH, 0, -3f)));

    public static final Item JUNGLE_SWORD = register(GeneratedRegistrationIds.Items.JUNGLE_SWORD,
            p -> new Item(p.sword(ModToolTiers.WOOD_JUNGLE, 3, -2.4f)));
    public static final Item JUNGLE_PICKAXE = register(GeneratedRegistrationIds.Items.JUNGLE_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.WOOD_JUNGLE, 1, -2.8f)));
    public static final Item JUNGLE_SHOVEL = register(GeneratedRegistrationIds.Items.JUNGLE_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.WOOD_JUNGLE, 1.5f, -3f)));
    public static final Item JUNGLE_AXE = register(GeneratedRegistrationIds.Items.JUNGLE_AXE,
            p -> new Item(p.axe(ModToolTiers.WOOD_JUNGLE, 6, -3.2f)));
    public static final Item JUNGLE_HOE = register(GeneratedRegistrationIds.Items.JUNGLE_HOE,
            p -> new Item(p.hoe(ModToolTiers.WOOD_JUNGLE, 0, -3f)));

    public static final Item ACACIA_SWORD = register(GeneratedRegistrationIds.Items.ACACIA_SWORD,
            p -> new Item(p.sword(ModToolTiers.WOOD_ACACIA, 3, -2.4f)));
    public static final Item ACACIA_PICKAXE = register(GeneratedRegistrationIds.Items.ACACIA_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.WOOD_ACACIA, 1, -2.8f)));
    public static final Item ACACIA_SHOVEL = register(GeneratedRegistrationIds.Items.ACACIA_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.WOOD_ACACIA, 1.5f, -3f)));
    public static final Item ACACIA_AXE = register(GeneratedRegistrationIds.Items.ACACIA_AXE,
            p -> new Item(p.axe(ModToolTiers.WOOD_ACACIA, 6, -3.2f)));
    public static final Item ACACIA_HOE = register(GeneratedRegistrationIds.Items.ACACIA_HOE,
            p -> new Item(p.hoe(ModToolTiers.WOOD_ACACIA, 0, -3f)));

    public static final Item DARK_OAK_SWORD = register(GeneratedRegistrationIds.Items.DARK_OAK_SWORD,
            p -> new Item(p.sword(ModToolTiers.WOOD_DARK_OAK, 3, -2.4f)));
    public static final Item DARK_OAK_PICKAXE = register(GeneratedRegistrationIds.Items.DARK_OAK_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.WOOD_DARK_OAK, 1, -2.8f)));
    public static final Item DARK_OAK_SHOVEL = register(GeneratedRegistrationIds.Items.DARK_OAK_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.WOOD_DARK_OAK, 1.5f, -3f)));
    public static final Item DARK_OAK_AXE = register(GeneratedRegistrationIds.Items.DARK_OAK_AXE,
            p -> new Item(p.axe(ModToolTiers.WOOD_DARK_OAK, 6, -3.2f)));
    public static final Item DARK_OAK_HOE = register(GeneratedRegistrationIds.Items.DARK_OAK_HOE,
            p -> new Item(p.hoe(ModToolTiers.WOOD_DARK_OAK, 0, -3f)));

    public static final Item MANGROVE_SWORD = register(GeneratedRegistrationIds.Items.MANGROVE_SWORD,
            p -> new Item(p.sword(ModToolTiers.WOOD_MANGROVE, 3, -2.4f)));
    public static final Item MANGROVE_PICKAXE = register(GeneratedRegistrationIds.Items.MANGROVE_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.WOOD_MANGROVE, 1, -2.8f)));
    public static final Item MANGROVE_SHOVEL = register(GeneratedRegistrationIds.Items.MANGROVE_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.WOOD_MANGROVE, 1.5f, -3f)));
    public static final Item MANGROVE_AXE = register(GeneratedRegistrationIds.Items.MANGROVE_AXE,
            p -> new Item(p.axe(ModToolTiers.WOOD_MANGROVE, 6, -3.2f)));
    public static final Item MANGROVE_HOE = register(GeneratedRegistrationIds.Items.MANGROVE_HOE,
            p -> new Item(p.hoe(ModToolTiers.WOOD_MANGROVE, 0, -3f)));

    public static final Item CHERRY_SWORD = register(GeneratedRegistrationIds.Items.CHERRY_SWORD,
            p -> new Item(p.sword(ModToolTiers.WOOD_CHERRY, 3, -2.4f)));
    public static final Item CHERRY_PICKAXE = register(GeneratedRegistrationIds.Items.CHERRY_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.WOOD_CHERRY, 1, -2.8f)));
    public static final Item CHERRY_SHOVEL = register(GeneratedRegistrationIds.Items.CHERRY_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.WOOD_CHERRY, 1.5f, -3f)));
    public static final Item CHERRY_AXE = register(GeneratedRegistrationIds.Items.CHERRY_AXE,
            p -> new Item(p.axe(ModToolTiers.WOOD_CHERRY, 6, -3.2f)));
    public static final Item CHERRY_HOE = register(GeneratedRegistrationIds.Items.CHERRY_HOE,
            p -> new Item(p.hoe(ModToolTiers.WOOD_CHERRY, 0, -3f)));

    public static final Item BAMBOO_SWORD = register(GeneratedRegistrationIds.Items.BAMBOO_SWORD,
            p -> new Item(p.sword(ModToolTiers.WOOD_BAMBOO, 3, -2.4f)));
    public static final Item BAMBOO_PICKAXE = register(GeneratedRegistrationIds.Items.BAMBOO_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.WOOD_BAMBOO, 1, -2.8f)));
    public static final Item BAMBOO_SHOVEL = register(GeneratedRegistrationIds.Items.BAMBOO_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.WOOD_BAMBOO, 1.5f, -3f)));
    public static final Item BAMBOO_AXE = register(GeneratedRegistrationIds.Items.BAMBOO_AXE,
            p -> new Item(p.axe(ModToolTiers.WOOD_BAMBOO, 6, -3.2f)));
    public static final Item BAMBOO_HOE = register(GeneratedRegistrationIds.Items.BAMBOO_HOE,
            p -> new Item(p.hoe(ModToolTiers.WOOD_BAMBOO, 0, -3f)));

    public static final Item CRIMSON_SWORD = register(GeneratedRegistrationIds.Items.CRIMSON_SWORD,
            p -> new Item(p.sword(ModToolTiers.WOOD_CRIMSON, 3, -2.4f)));
    public static final Item CRIMSON_PICKAXE = register(GeneratedRegistrationIds.Items.CRIMSON_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.WOOD_CRIMSON, 1, -2.8f)));
    public static final Item CRIMSON_SHOVEL = register(GeneratedRegistrationIds.Items.CRIMSON_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.WOOD_CRIMSON, 1.5f, -3f)));
    public static final Item CRIMSON_AXE = register(GeneratedRegistrationIds.Items.CRIMSON_AXE,
            p -> new Item(p.axe(ModToolTiers.WOOD_CRIMSON, 6, -3.2f)));
    public static final Item CRIMSON_HOE = register(GeneratedRegistrationIds.Items.CRIMSON_HOE,
            p -> new Item(p.hoe(ModToolTiers.WOOD_CRIMSON, 0, -3f)));

    public static final Item WARPED_SWORD = register(GeneratedRegistrationIds.Items.WARPED_SWORD,
            p -> new Item(p.sword(ModToolTiers.WOOD_WARPED, 3, -2.4f)));
    public static final Item WARPED_PICKAXE = register(GeneratedRegistrationIds.Items.WARPED_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.WOOD_WARPED, 1, -2.8f)));
    public static final Item WARPED_SHOVEL = register(GeneratedRegistrationIds.Items.WARPED_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.WOOD_WARPED, 1.5f, -3f)));
    public static final Item WARPED_AXE = register(GeneratedRegistrationIds.Items.WARPED_AXE,
            p -> new Item(p.axe(ModToolTiers.WOOD_WARPED, 6, -3.2f)));
    public static final Item WARPED_HOE = register(GeneratedRegistrationIds.Items.WARPED_HOE,
            p -> new Item(p.hoe(ModToolTiers.WOOD_WARPED, 0, -3f)));

    // ── Leather tools ──────────────────────────────────────────────────────
    public static final Item LEATHER_SWORD = register(GeneratedRegistrationIds.Items.LEATHER_SWORD,
            p -> new Item(p.sword(ModToolTiers.LEATHER, 3, -2.4f)));
    public static final Item LEATHER_PICKAXE = register(GeneratedRegistrationIds.Items.LEATHER_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.LEATHER, 1, -2.8f)));
    public static final Item LEATHER_SHOVEL = register(GeneratedRegistrationIds.Items.LEATHER_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.LEATHER, 1.5f, -3f)));
    public static final Item LEATHER_AXE = register(GeneratedRegistrationIds.Items.LEATHER_AXE,
            p -> new Item(p.axe(ModToolTiers.LEATHER, 6, -3.2f)));
    public static final Item LEATHER_HOE = register(GeneratedRegistrationIds.Items.LEATHER_HOE,
            p -> new Item(p.hoe(ModToolTiers.LEATHER, 0, -3f)));

    // ── Vanilla material sets ──────────────────────────────────────────────

    public static final Item PAPER_SWORD = register(GeneratedRegistrationIds.Items.PAPER_SWORD,
            p -> new Item(p.sword(ModToolTiers.PAPER, 3, -2.4f)));
    public static final Item PAPER_PICKAXE = register(GeneratedRegistrationIds.Items.PAPER_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.PAPER, 1, -2.8f)));
    public static final Item PAPER_SHOVEL = register(GeneratedRegistrationIds.Items.PAPER_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.PAPER, 1.5f, -3f)));
    public static final Item PAPER_AXE = register(GeneratedRegistrationIds.Items.PAPER_AXE,
            p -> new Item(p.axe(ModToolTiers.PAPER, 6, -3.2f)));
    public static final Item PAPER_HOE = register(GeneratedRegistrationIds.Items.PAPER_HOE,
            p -> new Item(p.hoe(ModToolTiers.PAPER, 0, -3f)));

    public static final Item FEATHER_SWORD = register(GeneratedRegistrationIds.Items.FEATHER_SWORD,
            p -> new Item(p.sword(ModToolTiers.FEATHER, 3, -2.4f)));
    public static final Item FEATHER_PICKAXE = register(GeneratedRegistrationIds.Items.FEATHER_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.FEATHER, 1, -2.8f)));
    public static final Item FEATHER_SHOVEL = register(GeneratedRegistrationIds.Items.FEATHER_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.FEATHER, 1.5f, -3f)));
    public static final Item FEATHER_AXE = register(GeneratedRegistrationIds.Items.FEATHER_AXE,
            p -> new Item(p.axe(ModToolTiers.FEATHER, 6, -3.2f)));
    public static final Item FEATHER_HOE = register(GeneratedRegistrationIds.Items.FEATHER_HOE,
            p -> new Item(p.hoe(ModToolTiers.FEATHER, 0, -3f)));

    public static final Item GLASS_SWORD = register(GeneratedRegistrationIds.Items.GLASS_SWORD,
            p -> new Item(p.sword(ModToolTiers.GLASS, 3, -2.4f)));
    public static final Item GLASS_PICKAXE = register(GeneratedRegistrationIds.Items.GLASS_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.GLASS, 1, -2.8f)));
    public static final Item GLASS_SHOVEL = register(GeneratedRegistrationIds.Items.GLASS_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.GLASS, 1.5f, -3f)));
    public static final Item GLASS_AXE = register(GeneratedRegistrationIds.Items.GLASS_AXE,
            p -> new Item(p.axe(ModToolTiers.GLASS, 6, -3.2f)));
    public static final Item GLASS_HOE = register(GeneratedRegistrationIds.Items.GLASS_HOE,
            p -> new Item(p.hoe(ModToolTiers.GLASS, 0, -3f)));

    public static final Item RABBIT_HIDE_HELMET = register(GeneratedRegistrationIds.Items.RABBIT_HIDE_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.RABBIT_HIDE_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final Item RABBIT_HIDE_CHESTPLATE = register(GeneratedRegistrationIds.Items.RABBIT_HIDE_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.RABBIT_HIDE_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final Item RABBIT_HIDE_LEGGINGS = register(GeneratedRegistrationIds.Items.RABBIT_HIDE_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.RABBIT_HIDE_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final Item RABBIT_HIDE_BOOTS = register(GeneratedRegistrationIds.Items.RABBIT_HIDE_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.RABBIT_HIDE_ARMOR_MATERIAL, ArmorType.BOOTS)));

    public static final Item CACTUS_SWORD = register(GeneratedRegistrationIds.Items.CACTUS_SWORD,
            p -> new Item(p.sword(ModToolTiers.CACTUS, 3, -2.4f)));
    public static final Item CACTUS_PICKAXE = register(GeneratedRegistrationIds.Items.CACTUS_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.CACTUS, 1, -2.8f)));
    public static final Item CACTUS_SHOVEL = register(GeneratedRegistrationIds.Items.CACTUS_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.CACTUS, 1.5f, -3f)));
    public static final Item CACTUS_AXE = register(GeneratedRegistrationIds.Items.CACTUS_AXE,
            p -> new Item(p.axe(ModToolTiers.CACTUS, 6, -3.2f)));
    public static final Item CACTUS_HOE = register(GeneratedRegistrationIds.Items.CACTUS_HOE,
            p -> new Item(p.hoe(ModToolTiers.CACTUS, 0, -3f)));
    public static final Item CACTUS_HELMET = register(GeneratedRegistrationIds.Items.CACTUS_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.CACTUS_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final Item CACTUS_CHESTPLATE = register(GeneratedRegistrationIds.Items.CACTUS_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.CACTUS_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final Item CACTUS_LEGGINGS = register(GeneratedRegistrationIds.Items.CACTUS_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.CACTUS_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final Item CACTUS_BOOTS = register(GeneratedRegistrationIds.Items.CACTUS_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.CACTUS_ARMOR_MATERIAL, ArmorType.BOOTS)));

    public static final Item SPONGE_SWORD = register(GeneratedRegistrationIds.Items.SPONGE_SWORD,
            p -> new Item(p.sword(ModToolTiers.SPONGE, 3, -2.4f)));
    public static final Item SPONGE_PICKAXE = register(GeneratedRegistrationIds.Items.SPONGE_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.SPONGE, 1, -2.8f)));
    public static final Item SPONGE_SHOVEL = register(GeneratedRegistrationIds.Items.SPONGE_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.SPONGE, 1.5f, -3f)));
    public static final Item SPONGE_AXE = register(GeneratedRegistrationIds.Items.SPONGE_AXE,
            p -> new Item(p.axe(ModToolTiers.SPONGE, 6, -3.2f)));
    public static final Item SPONGE_HOE = register(GeneratedRegistrationIds.Items.SPONGE_HOE,
            p -> new Item(p.hoe(ModToolTiers.SPONGE, 0, -3f)));

    public static final Item BONE_SWORD = register(GeneratedRegistrationIds.Items.BONE_SWORD,
            p -> new Item(p.sword(ModToolTiers.BONE, 3, -2.4f)));
    public static final Item BONE_PICKAXE = register(GeneratedRegistrationIds.Items.BONE_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.BONE, 1, -2.8f)));
    public static final Item BONE_SHOVEL = register(GeneratedRegistrationIds.Items.BONE_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.BONE, 1.5f, -3f)));
    public static final Item BONE_AXE = register(GeneratedRegistrationIds.Items.BONE_AXE,
            p -> new Item(p.axe(ModToolTiers.BONE, 6, -3.2f)));
    public static final Item BONE_HOE = register(GeneratedRegistrationIds.Items.BONE_HOE,
            p -> new Item(p.hoe(ModToolTiers.BONE, 0, -3f)));
    public static final Item BONE_HELMET = register(GeneratedRegistrationIds.Items.BONE_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.BONE_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final Item BONE_CHESTPLATE = register(GeneratedRegistrationIds.Items.BONE_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.BONE_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final Item BONE_LEGGINGS = register(GeneratedRegistrationIds.Items.BONE_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.BONE_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final Item BONE_BOOTS = register(GeneratedRegistrationIds.Items.BONE_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.BONE_ARMOR_MATERIAL, ArmorType.BOOTS)));

    public static final Item CLAY_SWORD = register(GeneratedRegistrationIds.Items.CLAY_SWORD,
            p -> new Item(p.sword(ModToolTiers.CLAY, 3, -2.4f)));
    public static final Item CLAY_PICKAXE = register(GeneratedRegistrationIds.Items.CLAY_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.CLAY, 1, -2.8f)));
    public static final Item CLAY_SHOVEL = register(GeneratedRegistrationIds.Items.CLAY_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.CLAY, 1.5f, -3f)));
    public static final Item CLAY_AXE = register(GeneratedRegistrationIds.Items.CLAY_AXE,
            p -> new Item(p.axe(ModToolTiers.CLAY, 6, -3.2f)));
    public static final Item CLAY_HOE = register(GeneratedRegistrationIds.Items.CLAY_HOE,
            p -> new Item(p.hoe(ModToolTiers.CLAY, 0, -3f)));
    public static final Item CLAY_HELMET = register(GeneratedRegistrationIds.Items.CLAY_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.CLAY_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final Item CLAY_CHESTPLATE = register(GeneratedRegistrationIds.Items.CLAY_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.CLAY_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final Item CLAY_LEGGINGS = register(GeneratedRegistrationIds.Items.CLAY_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.CLAY_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final Item CLAY_BOOTS = register(GeneratedRegistrationIds.Items.CLAY_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.CLAY_ARMOR_MATERIAL, ArmorType.BOOTS)));

    public static final Item NETHER_WART_SWORD = register(GeneratedRegistrationIds.Items.NETHER_WART_SWORD,
            p -> new Item(p.sword(ModToolTiers.NETHER_WART, 3, -2.4f)));
    public static final Item NETHER_WART_PICKAXE = register(GeneratedRegistrationIds.Items.NETHER_WART_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.NETHER_WART, 1, -2.8f)));
    public static final Item NETHER_WART_SHOVEL = register(GeneratedRegistrationIds.Items.NETHER_WART_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.NETHER_WART, 1.5f, -3f)));
    public static final Item NETHER_WART_AXE = register(GeneratedRegistrationIds.Items.NETHER_WART_AXE,
            p -> new Item(p.axe(ModToolTiers.NETHER_WART, 6, -3.2f)));
    public static final Item NETHER_WART_HOE = register(GeneratedRegistrationIds.Items.NETHER_WART_HOE,
            p -> new Item(p.hoe(ModToolTiers.NETHER_WART, 0, -3f)));

    public static final Item BRICK_SWORD = register(GeneratedRegistrationIds.Items.BRICK_SWORD,
            p -> new Item(p.sword(ModToolTiers.BRICK, 3, -2.4f)));
    public static final Item BRICK_PICKAXE = register(GeneratedRegistrationIds.Items.BRICK_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.BRICK, 1, -2.8f)));
    public static final Item BRICK_SHOVEL = register(GeneratedRegistrationIds.Items.BRICK_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.BRICK, 1.5f, -3f)));
    public static final Item BRICK_AXE = register(GeneratedRegistrationIds.Items.BRICK_AXE,
            p -> new Item(p.axe(ModToolTiers.BRICK, 6, -3.2f)));
    public static final Item BRICK_HOE = register(GeneratedRegistrationIds.Items.BRICK_HOE,
            p -> new Item(p.hoe(ModToolTiers.BRICK, 0, -3f)));
    public static final Item BRICK_HELMET = register(GeneratedRegistrationIds.Items.BRICK_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.BRICK_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final Item BRICK_CHESTPLATE = register(GeneratedRegistrationIds.Items.BRICK_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.BRICK_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final Item BRICK_LEGGINGS = register(GeneratedRegistrationIds.Items.BRICK_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.BRICK_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final Item BRICK_BOOTS = register(GeneratedRegistrationIds.Items.BRICK_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.BRICK_ARMOR_MATERIAL, ArmorType.BOOTS)));

    public static final Item NETHER_BRICK_SWORD = register(GeneratedRegistrationIds.Items.NETHER_BRICK_SWORD,
            p -> new Item(p.sword(ModToolTiers.NETHER_BRICK, 3, -2.4f)));
    public static final Item NETHER_BRICK_PICKAXE = register(GeneratedRegistrationIds.Items.NETHER_BRICK_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.NETHER_BRICK, 1, -2.8f)));
    public static final Item NETHER_BRICK_SHOVEL = register(GeneratedRegistrationIds.Items.NETHER_BRICK_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.NETHER_BRICK, 1.5f, -3f)));
    public static final Item NETHER_BRICK_AXE = register(GeneratedRegistrationIds.Items.NETHER_BRICK_AXE,
            p -> new Item(p.axe(ModToolTiers.NETHER_BRICK, 6, -3.2f)));
    public static final Item NETHER_BRICK_HOE = register(GeneratedRegistrationIds.Items.NETHER_BRICK_HOE,
            p -> new Item(p.hoe(ModToolTiers.NETHER_BRICK, 0, -3f)));
    public static final Item NETHER_BRICK_HELMET = register(GeneratedRegistrationIds.Items.NETHER_BRICK_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.NETHER_BRICK_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final Item NETHER_BRICK_CHESTPLATE = register(GeneratedRegistrationIds.Items.NETHER_BRICK_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.NETHER_BRICK_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final Item NETHER_BRICK_LEGGINGS = register(GeneratedRegistrationIds.Items.NETHER_BRICK_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.NETHER_BRICK_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final Item NETHER_BRICK_BOOTS = register(GeneratedRegistrationIds.Items.NETHER_BRICK_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.NETHER_BRICK_ARMOR_MATERIAL, ArmorType.BOOTS)));

    public static final Item POINTED_DRIPSTONE_SWORD = register(GeneratedRegistrationIds.Items.POINTED_DRIPSTONE_SWORD,
            p -> new Item(p.sword(ModToolTiers.POINTED_DRIPSTONE, 3, -2.4f)));
    public static final Item POINTED_DRIPSTONE_PICKAXE = register(GeneratedRegistrationIds.Items.POINTED_DRIPSTONE_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.POINTED_DRIPSTONE, 1, -2.8f)));
    public static final Item POINTED_DRIPSTONE_SHOVEL = register(GeneratedRegistrationIds.Items.POINTED_DRIPSTONE_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.POINTED_DRIPSTONE, 1.5f, -3f)));
    public static final Item POINTED_DRIPSTONE_AXE = register(GeneratedRegistrationIds.Items.POINTED_DRIPSTONE_AXE,
            p -> new Item(p.axe(ModToolTiers.POINTED_DRIPSTONE, 6, -3.2f)));
    public static final Item POINTED_DRIPSTONE_HOE = register(GeneratedRegistrationIds.Items.POINTED_DRIPSTONE_HOE,
            p -> new Item(p.hoe(ModToolTiers.POINTED_DRIPSTONE, 0, -3f)));

    public static final Item COPPER_SWORD = register(GeneratedRegistrationIds.Items.COPPER_SWORD,
            p -> new Item(p.sword(ModToolTiers.COPPER, 3, -2.4f)));
    public static final Item COPPER_PICKAXE = register(GeneratedRegistrationIds.Items.COPPER_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.COPPER, 1, -2.8f)));
    public static final Item COPPER_SHOVEL = register(GeneratedRegistrationIds.Items.COPPER_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.COPPER, 1.5f, -3f)));
    public static final Item COPPER_AXE = register(GeneratedRegistrationIds.Items.COPPER_AXE,
            p -> new Item(p.axe(ModToolTiers.COPPER, 6, -3.2f)));
    public static final Item COPPER_HOE = register(GeneratedRegistrationIds.Items.COPPER_HOE,
            p -> new Item(p.hoe(ModToolTiers.COPPER, 0, -3f)));
    public static final Item COPPER_HELMET = register(GeneratedRegistrationIds.Items.COPPER_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.COPPER_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final Item COPPER_CHESTPLATE = register(GeneratedRegistrationIds.Items.COPPER_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.COPPER_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final Item COPPER_LEGGINGS = register(GeneratedRegistrationIds.Items.COPPER_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.COPPER_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final Item COPPER_BOOTS = register(GeneratedRegistrationIds.Items.COPPER_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.COPPER_ARMOR_MATERIAL, ArmorType.BOOTS)));

    public static final Item PHANTOM_SWORD = register(GeneratedRegistrationIds.Items.PHANTOM_SWORD,
            p -> new Item(p.sword(ModToolTiers.PHANTOM_MEMBRANE, 3, -2.4f)));
    public static final Item PHANTOM_PICKAXE = register(GeneratedRegistrationIds.Items.PHANTOM_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.PHANTOM_MEMBRANE, 1, -2.8f)));
    public static final Item PHANTOM_SHOVEL = register(GeneratedRegistrationIds.Items.PHANTOM_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.PHANTOM_MEMBRANE, 1.5f, -3f)));
    public static final Item PHANTOM_AXE = register(GeneratedRegistrationIds.Items.PHANTOM_AXE,
            p -> new Item(p.axe(ModToolTiers.PHANTOM_MEMBRANE, 6, -3.2f)));
    public static final Item PHANTOM_HOE = register(GeneratedRegistrationIds.Items.PHANTOM_HOE,
            p -> new Item(p.hoe(ModToolTiers.PHANTOM_MEMBRANE, 0, -3f)));
    public static final Item PHANTOM_HELMET = register(GeneratedRegistrationIds.Items.PHANTOM_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.PHANTOM_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final Item PHANTOM_CHESTPLATE = register(GeneratedRegistrationIds.Items.PHANTOM_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.PHANTOM_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final Item PHANTOM_LEGGINGS = register(GeneratedRegistrationIds.Items.PHANTOM_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.PHANTOM_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final Item PHANTOM_BOOTS = register(GeneratedRegistrationIds.Items.PHANTOM_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.PHANTOM_ARMOR_MATERIAL, ArmorType.BOOTS)));

    public static final Item MAGMA_CREAM_SWORD = register(GeneratedRegistrationIds.Items.MAGMA_CREAM_SWORD,
            p -> new Item(p.sword(ModToolTiers.MAGMA_CREAM, 3, -2.4f)));
    public static final Item MAGMA_CREAM_PICKAXE = register(GeneratedRegistrationIds.Items.MAGMA_CREAM_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.MAGMA_CREAM, 1, -2.8f)));
    public static final Item MAGMA_CREAM_SHOVEL = register(GeneratedRegistrationIds.Items.MAGMA_CREAM_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.MAGMA_CREAM, 1.5f, -3f)));
    public static final Item MAGMA_CREAM_AXE = register(GeneratedRegistrationIds.Items.MAGMA_CREAM_AXE,
            p -> new Item(p.axe(ModToolTiers.MAGMA_CREAM, 6, -3.2f)));
    public static final Item MAGMA_CREAM_HOE = register(GeneratedRegistrationIds.Items.MAGMA_CREAM_HOE,
            p -> new Item(p.hoe(ModToolTiers.MAGMA_CREAM, 0, -3f)));
    public static final Item MAGMA_CREAM_HELMET = register(GeneratedRegistrationIds.Items.MAGMA_CREAM_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.MAGMA_CREAM_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final Item MAGMA_CREAM_CHESTPLATE = register(GeneratedRegistrationIds.Items.MAGMA_CREAM_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.MAGMA_CREAM_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final Item MAGMA_CREAM_LEGGINGS = register(GeneratedRegistrationIds.Items.MAGMA_CREAM_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.MAGMA_CREAM_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final Item MAGMA_CREAM_BOOTS = register(GeneratedRegistrationIds.Items.MAGMA_CREAM_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.MAGMA_CREAM_ARMOR_MATERIAL, ArmorType.BOOTS)));

    public static final Item SLIME_SWORD = register(GeneratedRegistrationIds.Items.SLIME_SWORD,
            p -> new Item(p.sword(ModToolTiers.SLIME, 3, -2.4f)));
    public static final Item SLIME_PICKAXE = register(GeneratedRegistrationIds.Items.SLIME_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.SLIME, 1, -2.8f)));
    public static final Item SLIME_SHOVEL = register(GeneratedRegistrationIds.Items.SLIME_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.SLIME, 1.5f, -3f)));
    public static final Item SLIME_AXE = register(GeneratedRegistrationIds.Items.SLIME_AXE,
            p -> new Item(p.axe(ModToolTiers.SLIME, 6, -3.2f)));
    public static final Item SLIME_HOE = register(GeneratedRegistrationIds.Items.SLIME_HOE,
            p -> new Item(p.hoe(ModToolTiers.SLIME, 0, -3f)));
    public static final Item SLIME_HELMET = register(GeneratedRegistrationIds.Items.SLIME_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.SLIME_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final Item SLIME_CHESTPLATE = register(GeneratedRegistrationIds.Items.SLIME_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.SLIME_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final Item SLIME_LEGGINGS = register(GeneratedRegistrationIds.Items.SLIME_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.SLIME_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final Item SLIME_BOOTS = register(GeneratedRegistrationIds.Items.SLIME_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.SLIME_ARMOR_MATERIAL, ArmorType.BOOTS)));

    public static final Item BLAZE_SWORD = register(GeneratedRegistrationIds.Items.BLAZE_SWORD,
            p -> new Item(p.sword(ModToolTiers.BLAZE_ROD, 3, -2.4f)));
    public static final Item BLAZE_PICKAXE = register(GeneratedRegistrationIds.Items.BLAZE_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.BLAZE_ROD, 1, -2.8f)));
    public static final Item BLAZE_SHOVEL = register(GeneratedRegistrationIds.Items.BLAZE_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.BLAZE_ROD, 1.5f, -3f)));
    public static final Item BLAZE_AXE = register(GeneratedRegistrationIds.Items.BLAZE_AXE,
            p -> new Item(p.axe(ModToolTiers.BLAZE_ROD, 6, -3.2f)));
    public static final Item BLAZE_HOE = register(GeneratedRegistrationIds.Items.BLAZE_HOE,
            p -> new Item(p.hoe(ModToolTiers.BLAZE_ROD, 0, -3f)));
    public static final Item BLAZE_HELMET = register(GeneratedRegistrationIds.Items.BLAZE_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.BLAZE_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final Item BLAZE_CHESTPLATE = register(GeneratedRegistrationIds.Items.BLAZE_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.BLAZE_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final Item BLAZE_LEGGINGS = register(GeneratedRegistrationIds.Items.BLAZE_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.BLAZE_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final Item BLAZE_BOOTS = register(GeneratedRegistrationIds.Items.BLAZE_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.BLAZE_ARMOR_MATERIAL, ArmorType.BOOTS)));

    public static final Item NAUTILUS_SWORD = register(GeneratedRegistrationIds.Items.NAUTILUS_SWORD,
            p -> new Item(p.sword(ModToolTiers.NAUTILUS_SHELL, 3, -2.4f)));
    public static final Item NAUTILUS_PICKAXE = register(GeneratedRegistrationIds.Items.NAUTILUS_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.NAUTILUS_SHELL, 1, -2.8f)));
    public static final Item NAUTILUS_SHOVEL = register(GeneratedRegistrationIds.Items.NAUTILUS_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.NAUTILUS_SHELL, 1.5f, -3f)));
    public static final Item NAUTILUS_AXE = register(GeneratedRegistrationIds.Items.NAUTILUS_AXE,
            p -> new Item(p.axe(ModToolTiers.NAUTILUS_SHELL, 6, -3.2f)));
    public static final Item NAUTILUS_HOE = register(GeneratedRegistrationIds.Items.NAUTILUS_HOE,
            p -> new Item(p.hoe(ModToolTiers.NAUTILUS_SHELL, 0, -3f)));
    public static final Item NAUTILUS_HELMET = register(GeneratedRegistrationIds.Items.NAUTILUS_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.NAUTILUS_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final Item NAUTILUS_CHESTPLATE = register(GeneratedRegistrationIds.Items.NAUTILUS_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.NAUTILUS_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final Item NAUTILUS_LEGGINGS = register(GeneratedRegistrationIds.Items.NAUTILUS_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.NAUTILUS_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final Item NAUTILUS_BOOTS = register(GeneratedRegistrationIds.Items.NAUTILUS_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.NAUTILUS_ARMOR_MATERIAL, ArmorType.BOOTS)));

    public static final Item PURPUR_SWORD = register(GeneratedRegistrationIds.Items.PURPUR_SWORD,
            p -> new Item(p.sword(ModToolTiers.PURPUR, 3, -2.4f)));
    public static final Item PURPUR_PICKAXE = register(GeneratedRegistrationIds.Items.PURPUR_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.PURPUR, 1, -2.8f)));
    public static final Item PURPUR_SHOVEL = register(GeneratedRegistrationIds.Items.PURPUR_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.PURPUR, 1.5f, -3f)));
    public static final Item PURPUR_AXE = register(GeneratedRegistrationIds.Items.PURPUR_AXE,
            p -> new Item(p.axe(ModToolTiers.PURPUR, 6, -3.2f)));
    public static final Item PURPUR_HOE = register(GeneratedRegistrationIds.Items.PURPUR_HOE,
            p -> new Item(p.hoe(ModToolTiers.PURPUR, 0, -3f)));
    public static final Item PURPUR_HELMET = register(GeneratedRegistrationIds.Items.PURPUR_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.PURPUR_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final Item PURPUR_CHESTPLATE = register(GeneratedRegistrationIds.Items.PURPUR_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.PURPUR_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final Item PURPUR_LEGGINGS = register(GeneratedRegistrationIds.Items.PURPUR_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.PURPUR_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final Item PURPUR_BOOTS = register(GeneratedRegistrationIds.Items.PURPUR_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.PURPUR_ARMOR_MATERIAL, ArmorType.BOOTS)));

    public static final Item GHAST_TEAR_SWORD = register(GeneratedRegistrationIds.Items.GHAST_TEAR_SWORD,
            p -> new Item(p.sword(ModToolTiers.GHAST_TEAR, 3, -2.4f)));
    public static final Item GHAST_TEAR_PICKAXE = register(GeneratedRegistrationIds.Items.GHAST_TEAR_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.GHAST_TEAR, 1, -2.8f)));
    public static final Item GHAST_TEAR_SHOVEL = register(GeneratedRegistrationIds.Items.GHAST_TEAR_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.GHAST_TEAR, 1.5f, -3f)));
    public static final Item GHAST_TEAR_AXE = register(GeneratedRegistrationIds.Items.GHAST_TEAR_AXE,
            p -> new Item(p.axe(ModToolTiers.GHAST_TEAR, 6, -3.2f)));
    public static final Item GHAST_TEAR_HOE = register(GeneratedRegistrationIds.Items.GHAST_TEAR_HOE,
            p -> new Item(p.hoe(ModToolTiers.GHAST_TEAR, 0, -3f)));
    public static final Item GHAST_TEAR_HELMET = register(GeneratedRegistrationIds.Items.GHAST_TEAR_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.GHAST_TEAR_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final Item GHAST_TEAR_CHESTPLATE = register(GeneratedRegistrationIds.Items.GHAST_TEAR_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.GHAST_TEAR_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final Item GHAST_TEAR_LEGGINGS = register(GeneratedRegistrationIds.Items.GHAST_TEAR_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.GHAST_TEAR_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final Item GHAST_TEAR_BOOTS = register(GeneratedRegistrationIds.Items.GHAST_TEAR_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.GHAST_TEAR_ARMOR_MATERIAL, ArmorType.BOOTS)));

    public static final Item EYE_OF_ENDER_SWORD = register(GeneratedRegistrationIds.Items.EYE_OF_ENDER_SWORD,
            p -> new Item(p.sword(ModToolTiers.EYE_OF_ENDER, 3, -2.4f)));
    public static final Item EYE_OF_ENDER_PICKAXE = register(GeneratedRegistrationIds.Items.EYE_OF_ENDER_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.EYE_OF_ENDER, 1, -2.8f)));
    public static final Item EYE_OF_ENDER_SHOVEL = register(GeneratedRegistrationIds.Items.EYE_OF_ENDER_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.EYE_OF_ENDER, 1.5f, -3f)));
    public static final Item EYE_OF_ENDER_AXE = register(GeneratedRegistrationIds.Items.EYE_OF_ENDER_AXE,
            p -> new Item(p.axe(ModToolTiers.EYE_OF_ENDER, 6, -3.2f)));
    public static final Item EYE_OF_ENDER_HOE = register(GeneratedRegistrationIds.Items.EYE_OF_ENDER_HOE,
            p -> new Item(p.hoe(ModToolTiers.EYE_OF_ENDER, 0, -3f)));
    public static final Item EYE_OF_ENDER_HELMET = register(GeneratedRegistrationIds.Items.EYE_OF_ENDER_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.EYE_OF_ENDER_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final Item EYE_OF_ENDER_CHESTPLATE = register(GeneratedRegistrationIds.Items.EYE_OF_ENDER_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.EYE_OF_ENDER_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final Item EYE_OF_ENDER_LEGGINGS = register(GeneratedRegistrationIds.Items.EYE_OF_ENDER_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.EYE_OF_ENDER_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final Item EYE_OF_ENDER_BOOTS = register(GeneratedRegistrationIds.Items.EYE_OF_ENDER_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.EYE_OF_ENDER_ARMOR_MATERIAL, ArmorType.BOOTS)));

    public static final Item SHULKER_SWORD = register(GeneratedRegistrationIds.Items.SHULKER_SWORD,
            p -> new Item(p.sword(ModToolTiers.SHULKER_SHELL, 3, -2.4f)));
    public static final Item SHULKER_PICKAXE = register(GeneratedRegistrationIds.Items.SHULKER_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.SHULKER_SHELL, 1, -2.8f)));
    public static final Item SHULKER_SHOVEL = register(GeneratedRegistrationIds.Items.SHULKER_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.SHULKER_SHELL, 1.5f, -3f)));
    public static final Item SHULKER_AXE = register(GeneratedRegistrationIds.Items.SHULKER_AXE,
            p -> new Item(p.axe(ModToolTiers.SHULKER_SHELL, 6, -3.2f)));
    public static final Item SHULKER_HOE = register(GeneratedRegistrationIds.Items.SHULKER_HOE,
            p -> new Item(p.hoe(ModToolTiers.SHULKER_SHELL, 0, -3f)));
    public static final Item SHULKER_HELMET = register(GeneratedRegistrationIds.Items.SHULKER_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.SHULKER_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final Item SHULKER_CHESTPLATE = register(GeneratedRegistrationIds.Items.SHULKER_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.SHULKER_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final Item SHULKER_LEGGINGS = register(GeneratedRegistrationIds.Items.SHULKER_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.SHULKER_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final Item SHULKER_BOOTS = register(GeneratedRegistrationIds.Items.SHULKER_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.SHULKER_ARMOR_MATERIAL, ArmorType.BOOTS)));

    public static final Item TURTLE_SCUTE_HELMET = register(GeneratedRegistrationIds.Items.TURTLE_SCUTE_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.TURTLE_SCUTE_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final Item TURTLE_SCUTE_CHESTPLATE = register(GeneratedRegistrationIds.Items.TURTLE_SCUTE_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.TURTLE_SCUTE_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final Item TURTLE_SCUTE_LEGGINGS = register(GeneratedRegistrationIds.Items.TURTLE_SCUTE_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.TURTLE_SCUTE_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final Item TURTLE_SCUTE_BOOTS = register(GeneratedRegistrationIds.Items.TURTLE_SCUTE_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.TURTLE_SCUTE_ARMOR_MATERIAL, ArmorType.BOOTS)));

    public static final Item ECHO_SHARD_SWORD = register(GeneratedRegistrationIds.Items.ECHO_SHARD_SWORD,
            p -> new Item(p.sword(ModToolTiers.ECHO_SHARD, 3, -2.4f)));
    public static final Item ECHO_SHARD_PICKAXE = register(GeneratedRegistrationIds.Items.ECHO_SHARD_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.ECHO_SHARD, 1, -2.8f)));
    public static final Item ECHO_SHARD_SHOVEL = register(GeneratedRegistrationIds.Items.ECHO_SHARD_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.ECHO_SHARD, 1.5f, -3f)));
    public static final Item ECHO_SHARD_AXE = register(GeneratedRegistrationIds.Items.ECHO_SHARD_AXE,
            p -> new Item(p.axe(ModToolTiers.ECHO_SHARD, 6, -3.2f)));
    public static final Item ECHO_SHARD_HOE = register(GeneratedRegistrationIds.Items.ECHO_SHARD_HOE,
            p -> new Item(p.hoe(ModToolTiers.ECHO_SHARD, 0, -3f)));
    public static final Item ECHO_SHARD_HELMET = register(GeneratedRegistrationIds.Items.ECHO_SHARD_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.ECHO_SHARD_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final Item ECHO_SHARD_CHESTPLATE = register(GeneratedRegistrationIds.Items.ECHO_SHARD_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.ECHO_SHARD_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final Item ECHO_SHARD_LEGGINGS = register(GeneratedRegistrationIds.Items.ECHO_SHARD_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.ECHO_SHARD_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final Item ECHO_SHARD_BOOTS = register(GeneratedRegistrationIds.Items.ECHO_SHARD_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.ECHO_SHARD_ARMOR_MATERIAL, ArmorType.BOOTS)));

    public static final Item DRAGON_BREATH_SWORD = register(GeneratedRegistrationIds.Items.DRAGON_BREATH_SWORD,
            p -> new Item(p.sword(ModToolTiers.DRAGON_BREATH, 3, -2.4f)));
    public static final Item DRAGON_BREATH_PICKAXE = register(GeneratedRegistrationIds.Items.DRAGON_BREATH_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.DRAGON_BREATH, 1, -2.8f)));
    public static final Item DRAGON_BREATH_SHOVEL = register(GeneratedRegistrationIds.Items.DRAGON_BREATH_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.DRAGON_BREATH, 1.5f, -3f)));
    public static final Item DRAGON_BREATH_AXE = register(GeneratedRegistrationIds.Items.DRAGON_BREATH_AXE,
            p -> new Item(p.axe(ModToolTiers.DRAGON_BREATH, 6, -3.2f)));
    public static final Item DRAGON_BREATH_HOE = register(GeneratedRegistrationIds.Items.DRAGON_BREATH_HOE,
            p -> new Item(p.hoe(ModToolTiers.DRAGON_BREATH, 0, -3f)));
    public static final Item DRAGON_BREATH_HELMET = register(GeneratedRegistrationIds.Items.DRAGON_BREATH_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.DRAGON_BREATH_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final Item DRAGON_BREATH_CHESTPLATE = register(GeneratedRegistrationIds.Items.DRAGON_BREATH_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.DRAGON_BREATH_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final Item DRAGON_BREATH_LEGGINGS = register(GeneratedRegistrationIds.Items.DRAGON_BREATH_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.DRAGON_BREATH_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final Item DRAGON_BREATH_BOOTS = register(GeneratedRegistrationIds.Items.DRAGON_BREATH_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.DRAGON_BREATH_ARMOR_MATERIAL, ArmorType.BOOTS)));

    // ── Edible food-themed sets ────────────────────────────────────────────
    // Cake
    public static final Item CAKE_SWORD = register(GeneratedRegistrationIds.Items.CAKE_SWORD,
            p -> new EdibleSwordItem(p.sword(ModToolTiers.CAKE, 3, -2.4f).food(food(4))));
    public static final Item CAKE_PICKAXE = register(GeneratedRegistrationIds.Items.CAKE_PICKAXE,
            p -> new EdiblePickaxeItem(p.pickaxe(ModToolTiers.CAKE, 1, -2.8f).food(food(6))));
    public static final Item CAKE_SHOVEL = register(GeneratedRegistrationIds.Items.CAKE_SHOVEL,
            p -> new EdibleShovelItem(p.shovel(ModToolTiers.CAKE, 1.5f, -3f).food(food(2))));
    public static final Item CAKE_AXE = register(GeneratedRegistrationIds.Items.CAKE_AXE,
            p -> new EdibleAxeItem(p.axe(ModToolTiers.CAKE, 6, -3.2f).food(food(6))));
    public static final Item CAKE_HOE = register(GeneratedRegistrationIds.Items.CAKE_HOE,
            p -> new EdibleHoeItem(p.hoe(ModToolTiers.CAKE, 0, -3f).food(food(4))));
    public static final Item CAKE_HELMET = register(GeneratedRegistrationIds.Items.CAKE_HELMET,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.CAKE_ARMOR_MATERIAL, ArmorType.HELMET).food(food(10))));
    public static final Item CAKE_CHESTPLATE = register(GeneratedRegistrationIds.Items.CAKE_CHESTPLATE,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.CAKE_ARMOR_MATERIAL, ArmorType.CHESTPLATE).food(food(14))));
    public static final Item CAKE_LEGGINGS = register(GeneratedRegistrationIds.Items.CAKE_LEGGINGS,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.CAKE_ARMOR_MATERIAL, ArmorType.LEGGINGS).food(food(14))));
    public static final Item CAKE_BOOTS = register(GeneratedRegistrationIds.Items.CAKE_BOOTS,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.CAKE_ARMOR_MATERIAL, ArmorType.BOOTS).food(food(8))));

    // Bread
    public static final Item BREAD_SWORD = register(GeneratedRegistrationIds.Items.BREAD_SWORD,
            p -> new EdibleSwordItem(p.sword(ModToolTiers.BREAD, 3, -2.4f).food(food(4))));
    public static final Item BREAD_PICKAXE = register(GeneratedRegistrationIds.Items.BREAD_PICKAXE,
            p -> new EdiblePickaxeItem(p.pickaxe(ModToolTiers.BREAD, 1, -2.8f).food(food(6))));
    public static final Item BREAD_SHOVEL = register(GeneratedRegistrationIds.Items.BREAD_SHOVEL,
            p -> new EdibleShovelItem(p.shovel(ModToolTiers.BREAD, 1.5f, -3f).food(food(2))));
    public static final Item BREAD_AXE = register(GeneratedRegistrationIds.Items.BREAD_AXE,
            p -> new EdibleAxeItem(p.axe(ModToolTiers.BREAD, 6, -3.2f).food(food(6))));
    public static final Item BREAD_HOE = register(GeneratedRegistrationIds.Items.BREAD_HOE,
            p -> new EdibleHoeItem(p.hoe(ModToolTiers.BREAD, 0, -3f).food(food(4))));
    public static final Item BREAD_HELMET = register(GeneratedRegistrationIds.Items.BREAD_HELMET,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.BREAD_ARMOR_MATERIAL, ArmorType.HELMET).food(food(10))));
    public static final Item BREAD_CHESTPLATE = register(GeneratedRegistrationIds.Items.BREAD_CHESTPLATE,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.BREAD_ARMOR_MATERIAL, ArmorType.CHESTPLATE).food(food(14))));
    public static final Item BREAD_LEGGINGS = register(GeneratedRegistrationIds.Items.BREAD_LEGGINGS,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.BREAD_ARMOR_MATERIAL, ArmorType.LEGGINGS).food(food(14))));
    public static final Item BREAD_BOOTS = register(GeneratedRegistrationIds.Items.BREAD_BOOTS,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.BREAD_ARMOR_MATERIAL, ArmorType.BOOTS).food(food(8))));

    // Dried Kelp
    public static final Item DRIED_KELP_SWORD = register(GeneratedRegistrationIds.Items.DRIED_KELP_SWORD,
            p -> new EdibleSwordItem(p.sword(ModToolTiers.DRIED_KELP, 3, -2.4f).food(food(4))));
    public static final Item DRIED_KELP_PICKAXE = register(GeneratedRegistrationIds.Items.DRIED_KELP_PICKAXE,
            p -> new EdiblePickaxeItem(p.pickaxe(ModToolTiers.DRIED_KELP, 1, -2.8f).food(food(6))));
    public static final Item DRIED_KELP_SHOVEL = register(GeneratedRegistrationIds.Items.DRIED_KELP_SHOVEL,
            p -> new EdibleShovelItem(p.shovel(ModToolTiers.DRIED_KELP, 1.5f, -3f).food(food(2))));
    public static final Item DRIED_KELP_AXE = register(GeneratedRegistrationIds.Items.DRIED_KELP_AXE,
            p -> new EdibleAxeItem(p.axe(ModToolTiers.DRIED_KELP, 6, -3.2f).food(food(6))));
    public static final Item DRIED_KELP_HOE = register(GeneratedRegistrationIds.Items.DRIED_KELP_HOE,
            p -> new EdibleHoeItem(p.hoe(ModToolTiers.DRIED_KELP, 0, -3f).food(food(4))));
    public static final Item DRIED_KELP_HELMET = register(GeneratedRegistrationIds.Items.DRIED_KELP_HELMET,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.DRIED_KELP_ARMOR_MATERIAL, ArmorType.HELMET).food(food(10))));
    public static final Item DRIED_KELP_CHESTPLATE = register(GeneratedRegistrationIds.Items.DRIED_KELP_CHESTPLATE,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.DRIED_KELP_ARMOR_MATERIAL, ArmorType.CHESTPLATE).food(food(14))));
    public static final Item DRIED_KELP_LEGGINGS = register(GeneratedRegistrationIds.Items.DRIED_KELP_LEGGINGS,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.DRIED_KELP_ARMOR_MATERIAL, ArmorType.LEGGINGS).food(food(14))));
    public static final Item DRIED_KELP_BOOTS = register(GeneratedRegistrationIds.Items.DRIED_KELP_BOOTS,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.DRIED_KELP_ARMOR_MATERIAL, ArmorType.BOOTS).food(food(8))));

    // Rotten Flesh
    public static final Item ROTTEN_FLESH_SWORD = register(GeneratedRegistrationIds.Items.ROTTEN_FLESH_SWORD,
            p -> new EdibleSwordItem(p.sword(ModToolTiers.ROTTEN_FLESH, 3, -2.4f).food(food(4))));
    public static final Item ROTTEN_FLESH_PICKAXE = register(GeneratedRegistrationIds.Items.ROTTEN_FLESH_PICKAXE,
            p -> new EdiblePickaxeItem(p.pickaxe(ModToolTiers.ROTTEN_FLESH, 1, -2.8f).food(food(6))));
    public static final Item ROTTEN_FLESH_SHOVEL = register(GeneratedRegistrationIds.Items.ROTTEN_FLESH_SHOVEL,
            p -> new EdibleShovelItem(p.shovel(ModToolTiers.ROTTEN_FLESH, 1.5f, -3f).food(food(2))));
    public static final Item ROTTEN_FLESH_AXE = register(GeneratedRegistrationIds.Items.ROTTEN_FLESH_AXE,
            p -> new EdibleAxeItem(p.axe(ModToolTiers.ROTTEN_FLESH, 6, -3.2f).food(food(6))));
    public static final Item ROTTEN_FLESH_HOE = register(GeneratedRegistrationIds.Items.ROTTEN_FLESH_HOE,
            p -> new EdibleHoeItem(p.hoe(ModToolTiers.ROTTEN_FLESH, 0, -3f).food(food(4))));
    public static final Item ROTTEN_FLESH_HELMET = register(GeneratedRegistrationIds.Items.ROTTEN_FLESH_HELMET,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.ROTTEN_FLESH_ARMOR_MATERIAL, ArmorType.HELMET).food(food(10))));
    public static final Item ROTTEN_FLESH_CHESTPLATE = register(GeneratedRegistrationIds.Items.ROTTEN_FLESH_CHESTPLATE,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.ROTTEN_FLESH_ARMOR_MATERIAL, ArmorType.CHESTPLATE).food(food(14))));
    public static final Item ROTTEN_FLESH_LEGGINGS = register(GeneratedRegistrationIds.Items.ROTTEN_FLESH_LEGGINGS,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.ROTTEN_FLESH_ARMOR_MATERIAL, ArmorType.LEGGINGS).food(food(14))));
    public static final Item ROTTEN_FLESH_BOOTS = register(GeneratedRegistrationIds.Items.ROTTEN_FLESH_BOOTS,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.ROTTEN_FLESH_ARMOR_MATERIAL, ArmorType.BOOTS).food(food(8))));

    // Melon
    public static final Item MELON_SWORD = register(GeneratedRegistrationIds.Items.MELON_SWORD,
            p -> new EdibleSwordItem(p.sword(ModToolTiers.MELON, 3, -2.4f).food(food(4))));
    public static final Item MELON_PICKAXE = register(GeneratedRegistrationIds.Items.MELON_PICKAXE,
            p -> new EdiblePickaxeItem(p.pickaxe(ModToolTiers.MELON, 1, -2.8f).food(food(6))));
    public static final Item MELON_SHOVEL = register(GeneratedRegistrationIds.Items.MELON_SHOVEL,
            p -> new EdibleShovelItem(p.shovel(ModToolTiers.MELON, 1.5f, -3f).food(food(2))));
    public static final Item MELON_AXE = register(GeneratedRegistrationIds.Items.MELON_AXE,
            p -> new EdibleAxeItem(p.axe(ModToolTiers.MELON, 6, -3.2f).food(food(6))));
    public static final Item MELON_HOE = register(GeneratedRegistrationIds.Items.MELON_HOE,
            p -> new EdibleHoeItem(p.hoe(ModToolTiers.MELON, 0, -3f).food(food(4))));
    public static final Item MELON_HELMET = register(GeneratedRegistrationIds.Items.MELON_HELMET,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.MELON_ARMOR_MATERIAL, ArmorType.HELMET).food(food(10))));
    public static final Item MELON_CHESTPLATE = register(GeneratedRegistrationIds.Items.MELON_CHESTPLATE,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.MELON_ARMOR_MATERIAL, ArmorType.CHESTPLATE).food(food(14))));
    public static final Item MELON_LEGGINGS = register(GeneratedRegistrationIds.Items.MELON_LEGGINGS,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.MELON_ARMOR_MATERIAL, ArmorType.LEGGINGS).food(food(14))));
    public static final Item MELON_BOOTS = register(GeneratedRegistrationIds.Items.MELON_BOOTS,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.MELON_ARMOR_MATERIAL, ArmorType.BOOTS).food(food(8))));

    // Sweet Berries
    public static final Item SWEET_BERRY_SWORD = register(GeneratedRegistrationIds.Items.SWEET_BERRY_SWORD,
            p -> new EdibleSwordItem(p.sword(ModToolTiers.SWEET_BERRIES, 3, -2.4f).food(food(4))));
    public static final Item SWEET_BERRY_PICKAXE = register(GeneratedRegistrationIds.Items.SWEET_BERRY_PICKAXE,
            p -> new EdiblePickaxeItem(p.pickaxe(ModToolTiers.SWEET_BERRIES, 1, -2.8f).food(food(6))));
    public static final Item SWEET_BERRY_SHOVEL = register(GeneratedRegistrationIds.Items.SWEET_BERRY_SHOVEL,
            p -> new EdibleShovelItem(p.shovel(ModToolTiers.SWEET_BERRIES, 1.5f, -3f).food(food(2))));
    public static final Item SWEET_BERRY_AXE = register(GeneratedRegistrationIds.Items.SWEET_BERRY_AXE,
            p -> new EdibleAxeItem(p.axe(ModToolTiers.SWEET_BERRIES, 6, -3.2f).food(food(6))));
    public static final Item SWEET_BERRY_HOE = register(GeneratedRegistrationIds.Items.SWEET_BERRY_HOE,
            p -> new EdibleHoeItem(p.hoe(ModToolTiers.SWEET_BERRIES, 0, -3f).food(food(4))));
    public static final Item SWEET_BERRY_HELMET = register(GeneratedRegistrationIds.Items.SWEET_BERRY_HELMET,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.SWEET_BERRY_ARMOR_MATERIAL, ArmorType.HELMET).food(food(10))));
    public static final Item SWEET_BERRY_CHESTPLATE = register(GeneratedRegistrationIds.Items.SWEET_BERRY_CHESTPLATE,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.SWEET_BERRY_ARMOR_MATERIAL, ArmorType.CHESTPLATE).food(food(14))));
    public static final Item SWEET_BERRY_LEGGINGS = register(GeneratedRegistrationIds.Items.SWEET_BERRY_LEGGINGS,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.SWEET_BERRY_ARMOR_MATERIAL, ArmorType.LEGGINGS).food(food(14))));
    public static final Item SWEET_BERRY_BOOTS = register(GeneratedRegistrationIds.Items.SWEET_BERRY_BOOTS,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.SWEET_BERRY_ARMOR_MATERIAL, ArmorType.BOOTS).food(food(8))));

    // Pumpkin Pie
    public static final Item PUMPKIN_PIE_SWORD = register(GeneratedRegistrationIds.Items.PUMPKIN_PIE_SWORD,
            p -> new EdibleSwordItem(p.sword(ModToolTiers.PUMPKIN_PIE, 3, -2.4f).food(food(4))));
    public static final Item PUMPKIN_PIE_PICKAXE = register(GeneratedRegistrationIds.Items.PUMPKIN_PIE_PICKAXE,
            p -> new EdiblePickaxeItem(p.pickaxe(ModToolTiers.PUMPKIN_PIE, 1, -2.8f).food(food(6))));
    public static final Item PUMPKIN_PIE_SHOVEL = register(GeneratedRegistrationIds.Items.PUMPKIN_PIE_SHOVEL,
            p -> new EdibleShovelItem(p.shovel(ModToolTiers.PUMPKIN_PIE, 1.5f, -3f).food(food(2))));
    public static final Item PUMPKIN_PIE_AXE = register(GeneratedRegistrationIds.Items.PUMPKIN_PIE_AXE,
            p -> new EdibleAxeItem(p.axe(ModToolTiers.PUMPKIN_PIE, 6, -3.2f).food(food(6))));
    public static final Item PUMPKIN_PIE_HOE = register(GeneratedRegistrationIds.Items.PUMPKIN_PIE_HOE,
            p -> new EdibleHoeItem(p.hoe(ModToolTiers.PUMPKIN_PIE, 0, -3f).food(food(4))));
    public static final Item PUMPKIN_PIE_HELMET = register(GeneratedRegistrationIds.Items.PUMPKIN_PIE_HELMET,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.PUMPKIN_PIE_ARMOR_MATERIAL, ArmorType.HELMET).food(food(10))));
    public static final Item PUMPKIN_PIE_CHESTPLATE = register(GeneratedRegistrationIds.Items.PUMPKIN_PIE_CHESTPLATE,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.PUMPKIN_PIE_ARMOR_MATERIAL, ArmorType.CHESTPLATE).food(food(14))));
    public static final Item PUMPKIN_PIE_LEGGINGS = register(GeneratedRegistrationIds.Items.PUMPKIN_PIE_LEGGINGS,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.PUMPKIN_PIE_ARMOR_MATERIAL, ArmorType.LEGGINGS).food(food(14))));
    public static final Item PUMPKIN_PIE_BOOTS = register(GeneratedRegistrationIds.Items.PUMPKIN_PIE_BOOTS,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.PUMPKIN_PIE_ARMOR_MATERIAL, ArmorType.BOOTS).food(food(8))));

    // Mushroom
    public static final Item MUSHROOM_SWORD = register(GeneratedRegistrationIds.Items.MUSHROOM_SWORD,
            p -> new EdibleSwordItem(p.sword(ModToolTiers.MUSHROOM, 3, -2.4f).food(food(4))));
    public static final Item MUSHROOM_PICKAXE = register(GeneratedRegistrationIds.Items.MUSHROOM_PICKAXE,
            p -> new EdiblePickaxeItem(p.pickaxe(ModToolTiers.MUSHROOM, 1, -2.8f).food(food(6))));
    public static final Item MUSHROOM_SHOVEL = register(GeneratedRegistrationIds.Items.MUSHROOM_SHOVEL,
            p -> new EdibleShovelItem(p.shovel(ModToolTiers.MUSHROOM, 1.5f, -3f).food(food(2))));
    public static final Item MUSHROOM_AXE = register(GeneratedRegistrationIds.Items.MUSHROOM_AXE,
            p -> new EdibleAxeItem(p.axe(ModToolTiers.MUSHROOM, 6, -3.2f).food(food(6))));
    public static final Item MUSHROOM_HOE = register(GeneratedRegistrationIds.Items.MUSHROOM_HOE,
            p -> new EdibleHoeItem(p.hoe(ModToolTiers.MUSHROOM, 0, -3f).food(food(4))));
    public static final Item MUSHROOM_HELMET = register(GeneratedRegistrationIds.Items.MUSHROOM_HELMET,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.MUSHROOM_ARMOR_MATERIAL, ArmorType.HELMET).food(food(10))));
    public static final Item MUSHROOM_CHESTPLATE = register(GeneratedRegistrationIds.Items.MUSHROOM_CHESTPLATE,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.MUSHROOM_ARMOR_MATERIAL, ArmorType.CHESTPLATE).food(food(14))));
    public static final Item MUSHROOM_LEGGINGS = register(GeneratedRegistrationIds.Items.MUSHROOM_LEGGINGS,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.MUSHROOM_ARMOR_MATERIAL, ArmorType.LEGGINGS).food(food(14))));
    public static final Item MUSHROOM_BOOTS = register(GeneratedRegistrationIds.Items.MUSHROOM_BOOTS,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.MUSHROOM_ARMOR_MATERIAL, ArmorType.BOOTS).food(food(8))));

    // Pufferfish
    public static final Item PUFFERFISH_SWORD = register(GeneratedRegistrationIds.Items.PUFFERFISH_SWORD,
            p -> new EdibleSwordItem(p.sword(ModToolTiers.PUFFERFISH, 3, -2.4f).food(food(4))));
    public static final Item PUFFERFISH_PICKAXE = register(GeneratedRegistrationIds.Items.PUFFERFISH_PICKAXE,
            p -> new EdiblePickaxeItem(p.pickaxe(ModToolTiers.PUFFERFISH, 1, -2.8f).food(food(6))));
    public static final Item PUFFERFISH_SHOVEL = register(GeneratedRegistrationIds.Items.PUFFERFISH_SHOVEL,
            p -> new EdibleShovelItem(p.shovel(ModToolTiers.PUFFERFISH, 1.5f, -3f).food(food(2))));
    public static final Item PUFFERFISH_AXE = register(GeneratedRegistrationIds.Items.PUFFERFISH_AXE,
            p -> new EdibleAxeItem(p.axe(ModToolTiers.PUFFERFISH, 6, -3.2f).food(food(6))));
    public static final Item PUFFERFISH_HOE = register(GeneratedRegistrationIds.Items.PUFFERFISH_HOE,
            p -> new EdibleHoeItem(p.hoe(ModToolTiers.PUFFERFISH, 0, -3f).food(food(4))));
    public static final Item PUFFERFISH_HELMET = register(GeneratedRegistrationIds.Items.PUFFERFISH_HELMET,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.PUFFERFISH_ARMOR_MATERIAL, ArmorType.HELMET).food(food(10))));
    public static final Item PUFFERFISH_CHESTPLATE = register(GeneratedRegistrationIds.Items.PUFFERFISH_CHESTPLATE,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.PUFFERFISH_ARMOR_MATERIAL, ArmorType.CHESTPLATE).food(food(14))));
    public static final Item PUFFERFISH_LEGGINGS = register(GeneratedRegistrationIds.Items.PUFFERFISH_LEGGINGS,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.PUFFERFISH_ARMOR_MATERIAL, ArmorType.LEGGINGS).food(food(14))));
    public static final Item PUFFERFISH_BOOTS = register(GeneratedRegistrationIds.Items.PUFFERFISH_BOOTS,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.PUFFERFISH_ARMOR_MATERIAL, ArmorType.BOOTS).food(food(8))));

    // Honey
    public static final Item HONEY_SWORD = register(GeneratedRegistrationIds.Items.HONEY_SWORD,
            p -> new EdibleSwordItem(p.sword(ModToolTiers.HONEY, 3, -2.4f).food(food(4))));
    public static final Item HONEY_PICKAXE = register(GeneratedRegistrationIds.Items.HONEY_PICKAXE,
            p -> new EdiblePickaxeItem(p.pickaxe(ModToolTiers.HONEY, 1, -2.8f).food(food(6))));
    public static final Item HONEY_SHOVEL = register(GeneratedRegistrationIds.Items.HONEY_SHOVEL,
            p -> new EdibleShovelItem(p.shovel(ModToolTiers.HONEY, 1.5f, -3f).food(food(2))));
    public static final Item HONEY_AXE = register(GeneratedRegistrationIds.Items.HONEY_AXE,
            p -> new EdibleAxeItem(p.axe(ModToolTiers.HONEY, 6, -3.2f).food(food(6))));
    public static final Item HONEY_HOE = register(GeneratedRegistrationIds.Items.HONEY_HOE,
            p -> new EdibleHoeItem(p.hoe(ModToolTiers.HONEY, 0, -3f).food(food(4))));
    public static final Item HONEY_HELMET = register(GeneratedRegistrationIds.Items.HONEY_HELMET,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.HONEY_ARMOR_MATERIAL, ArmorType.HELMET).food(food(10))));
    public static final Item HONEY_CHESTPLATE = register(GeneratedRegistrationIds.Items.HONEY_CHESTPLATE,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.HONEY_ARMOR_MATERIAL, ArmorType.CHESTPLATE).food(food(14))));
    public static final Item HONEY_LEGGINGS = register(GeneratedRegistrationIds.Items.HONEY_LEGGINGS,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.HONEY_ARMOR_MATERIAL, ArmorType.LEGGINGS).food(food(14))));
    public static final Item HONEY_BOOTS = register(GeneratedRegistrationIds.Items.HONEY_BOOTS,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.HONEY_ARMOR_MATERIAL, ArmorType.BOOTS).food(food(8))));

    // Chorus Fruit
    public static final Item CHORUS_FRUIT_SWORD = register(GeneratedRegistrationIds.Items.CHORUS_FRUIT_SWORD,
            p -> new EdibleSwordItem(p.sword(ModToolTiers.CHORUS_FRUIT, 3, -2.4f).food(food(4))));
    public static final Item CHORUS_FRUIT_PICKAXE = register(GeneratedRegistrationIds.Items.CHORUS_FRUIT_PICKAXE,
            p -> new EdiblePickaxeItem(p.pickaxe(ModToolTiers.CHORUS_FRUIT, 1, -2.8f).food(food(6))));
    public static final Item CHORUS_FRUIT_SHOVEL = register(GeneratedRegistrationIds.Items.CHORUS_FRUIT_SHOVEL,
            p -> new EdibleShovelItem(p.shovel(ModToolTiers.CHORUS_FRUIT, 1.5f, -3f).food(food(2))));
    public static final Item CHORUS_FRUIT_AXE = register(GeneratedRegistrationIds.Items.CHORUS_FRUIT_AXE,
            p -> new EdibleAxeItem(p.axe(ModToolTiers.CHORUS_FRUIT, 6, -3.2f).food(food(6))));
    public static final Item CHORUS_FRUIT_HOE = register(GeneratedRegistrationIds.Items.CHORUS_FRUIT_HOE,
            p -> new EdibleHoeItem(p.hoe(ModToolTiers.CHORUS_FRUIT, 0, -3f).food(food(4))));
    public static final Item CHORUS_FRUIT_HELMET = register(GeneratedRegistrationIds.Items.CHORUS_FRUIT_HELMET,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.CHORUS_FRUIT_ARMOR_MATERIAL, ArmorType.HELMET).food(food(10))));
    public static final Item CHORUS_FRUIT_CHESTPLATE = register(GeneratedRegistrationIds.Items.CHORUS_FRUIT_CHESTPLATE,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.CHORUS_FRUIT_ARMOR_MATERIAL, ArmorType.CHESTPLATE).food(food(14))));
    public static final Item CHORUS_FRUIT_LEGGINGS = register(GeneratedRegistrationIds.Items.CHORUS_FRUIT_LEGGINGS,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.CHORUS_FRUIT_ARMOR_MATERIAL, ArmorType.LEGGINGS).food(food(14))));
    public static final Item CHORUS_FRUIT_BOOTS = register(GeneratedRegistrationIds.Items.CHORUS_FRUIT_BOOTS,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.CHORUS_FRUIT_ARMOR_MATERIAL, ArmorType.BOOTS).food(food(8))));

    // Golden Apple
    public static final Item GOLDEN_APPLE_SWORD = register(GeneratedRegistrationIds.Items.GOLDEN_APPLE_SWORD,
            p -> new EdibleSwordItem(p.sword(ModToolTiers.GOLDEN_APPLE, 3, -2.4f).food(food(4))));
    public static final Item GOLDEN_APPLE_PICKAXE = register(GeneratedRegistrationIds.Items.GOLDEN_APPLE_PICKAXE,
            p -> new EdiblePickaxeItem(p.pickaxe(ModToolTiers.GOLDEN_APPLE, 1, -2.8f).food(food(6))));
    public static final Item GOLDEN_APPLE_SHOVEL = register(GeneratedRegistrationIds.Items.GOLDEN_APPLE_SHOVEL,
            p -> new EdibleShovelItem(p.shovel(ModToolTiers.GOLDEN_APPLE, 1.5f, -3f).food(food(2))));
    public static final Item GOLDEN_APPLE_AXE = register(GeneratedRegistrationIds.Items.GOLDEN_APPLE_AXE,
            p -> new EdibleAxeItem(p.axe(ModToolTiers.GOLDEN_APPLE, 6, -3.2f).food(food(6))));
    public static final Item GOLDEN_APPLE_HOE = register(GeneratedRegistrationIds.Items.GOLDEN_APPLE_HOE,
            p -> new EdibleHoeItem(p.hoe(ModToolTiers.GOLDEN_APPLE, 0, -3f).food(food(4))));
    public static final Item GOLDEN_APPLE_HELMET = register(GeneratedRegistrationIds.Items.GOLDEN_APPLE_HELMET,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.GOLDEN_APPLE_ARMOR_MATERIAL, ArmorType.HELMET).food(food(10))));
    public static final Item GOLDEN_APPLE_CHESTPLATE = register(GeneratedRegistrationIds.Items.GOLDEN_APPLE_CHESTPLATE,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.GOLDEN_APPLE_ARMOR_MATERIAL, ArmorType.CHESTPLATE).food(food(14))));
    public static final Item GOLDEN_APPLE_LEGGINGS = register(GeneratedRegistrationIds.Items.GOLDEN_APPLE_LEGGINGS,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.GOLDEN_APPLE_ARMOR_MATERIAL, ArmorType.LEGGINGS).food(food(14))));
    public static final Item GOLDEN_APPLE_BOOTS = register(GeneratedRegistrationIds.Items.GOLDEN_APPLE_BOOTS,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.GOLDEN_APPLE_ARMOR_MATERIAL, ArmorType.BOOTS).food(food(8))));

    private static Item register(String name, Function<Item.Properties, Item> factory) {
        Identifier id = Identifier.fromNamespaceAndPath(UsefultoolsMod.MOD_ID, name);
        ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, id);
        Item item = factory.apply(new Item.Properties().setId(key));
        return Registry.register(BuiltInRegistries.ITEM, id, item);
    }

    /**
     * Force-loads this class so its static initializers run and all items get registered
     * with the vanilla registry. Idempotent.
     */
    public static void register() {
        // no-op; touching the class is enough
    }
}
