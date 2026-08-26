package com.stonytark.usefultoolsmod.item;

import com.stonytark.usefultoolsmod.generated.GeneratedRegistrationIds;


import com.stonytark.usefultoolsmod.UsefultoolsMod;
import com.stonytark.usefultoolsmod.entity.ModEntities;
import com.stonytark.usefultoolsmod.item.custom.*;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.SpawnEggItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Items registry — migrated from 1.21.1 to NeoForge 26.1.
 *
 * <p>Key shape changes vs 1.21.1:
 * <ul>
 *   <li>Sword/Pickaxe/Shovel/Axe/Hoe/ArmorItem classes were removed in 1.21.5. We use plain
 *       {@link Item} with {@code Item.Properties#sword/.pickaxe/.shovel/.axe/.hoe/.humanoidArmor}
 *       to apply tool/armor shape.</li>
 *   <li>{@code Item.Properties} now requires an id; we use {@link DeferredRegister.Items} and
 *       its {@code registerItem(name, props -> new Item(props))} helper which sets it for us.</li>
 *   <li>Custom subclasses (CoalSwordItem, EctoSwordItem, EdibleSwordItem, ModArmorItem, etc.)
 *       now extend plain {@code Item} with a single-arg {@code (Item.Properties)} constructor.</li>
 *   <li>{@code ArmorItem.Type} -> {@link ArmorType}.</li>
 * </ul>
 */
public class ModItems {
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(UsefultoolsMod.MOD_ID);

    private static FoodProperties food(int nutrition) {
        return new FoodProperties.Builder().nutrition(nutrition).saturationModifier(0.1f).build();
    }

    // ── Material items ─────────────────────────────────────────────────────

    public static final DeferredItem<Item> RGOLD = ITEMS.registerItem(GeneratedRegistrationIds.Items.RGOLD,
            p -> new Item(p.stacksTo(64)));
    public static final DeferredItem<Item> RAW_RGOLD = ITEMS.registerItem(GeneratedRegistrationIds.Items.RAW_RGOLD,
            p -> new Item(p.stacksTo(64)));
    public static final DeferredItem<Item> OBSHARD = ITEMS.registerItem(GeneratedRegistrationIds.Items.OBSHARD,
            p -> new Item(p.stacksTo(64)));
    public static final DeferredItem<Item> SEM = ITEMS.registerItem(GeneratedRegistrationIds.Items.SEM,
            p -> new Item(p.stacksTo(64)));
    public static final DeferredItem<Item> OBINGOT = ITEMS.registerItem(GeneratedRegistrationIds.Items.OBINGOT,
            p -> new Item(p.stacksTo(64)));
    public static final DeferredItem<Item> GRENADE = ITEMS.registerItem(GeneratedRegistrationIds.Items.GRENADE,
            p -> new Grenade(p.stacksTo(16)));
    public static final DeferredItem<Item> HRED = ITEMS.registerItem(GeneratedRegistrationIds.Items.HRED,
            p -> new Item(p.stacksTo(64)));
    public static final DeferredItem<Item> HGLOW = ITEMS.registerItem(GeneratedRegistrationIds.Items.HGLOW,
            p -> new Item(p.stacksTo(64)));
    public static final DeferredItem<Item> RLAPIS = ITEMS.registerItem(GeneratedRegistrationIds.Items.RLAPIS,
            p -> new Item(p.stacksTo(64)));
    public static final DeferredItem<Item> DYNAMITE = ITEMS.registerItem(GeneratedRegistrationIds.Items.DYNAMITE,
            p -> new Dynamite(p.stacksTo(16).fireResistant()));

    // ── Emerald-line tools ─────────────────────────────────────────────────
    public static final DeferredItem<Item> REMERALD_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.REMERALD_SWORD,
            p -> new Item(p.sword(ModToolTiers.REMERALD, 3, -2.4f)));
    public static final DeferredItem<Item> REMERALD_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.REMERALD_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.REMERALD, 1, -2.8f)));
    public static final DeferredItem<Item> REMERALD_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.REMERALD_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.REMERALD, 1.5f, -3f)));
    public static final DeferredItem<Item> REMERALD_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.REMERALD_AXE,
            p -> new Item(p.axe(ModToolTiers.REMERALD, 6, -3.2f)));
    public static final DeferredItem<Item> REMERALD_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.REMERALD_HOE,
            p -> new Item(p.hoe(ModToolTiers.REMERALD, 0, -3f)));

    public static final DeferredItem<Item> PEMERALD_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.PEMERALD_SWORD,
            p -> new Item(p.sword(ModToolTiers.PEMERALD, 3, -2.4f)));
    public static final DeferredItem<Item> PEMERALD_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.PEMERALD_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.PEMERALD, 1, -2.8f)));
    public static final DeferredItem<Item> PEMERALD_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.PEMERALD_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.PEMERALD, 1.5f, -3f)));
    public static final DeferredItem<Item> PEMERALD_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.PEMERALD_AXE,
            p -> new Item(p.axe(ModToolTiers.PEMERALD, 6, -3.2f)));
    public static final DeferredItem<Item> PEMERALD_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.PEMERALD_HOE,
            p -> new Item(p.hoe(ModToolTiers.PEMERALD, 0, -3f)));

    public static final DeferredItem<Item> ROBSIDIAN_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.ROBSIDIAN_SWORD,
            p -> new Item(p.sword(ModToolTiers.ROBSIDIAN, 3, -2.4f)));
    public static final DeferredItem<Item> ROBSIDIAN_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.ROBSIDIAN_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.ROBSIDIAN, 1, -2.8f)));
    public static final DeferredItem<Item> ROBSIDIAN_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.ROBSIDIAN_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.ROBSIDIAN, 1.5f, -3f)));
    public static final DeferredItem<Item> ROBSIDIAN_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.ROBSIDIAN_AXE,
            p -> new Item(p.axe(ModToolTiers.ROBSIDIAN, 6, -3.2f)));
    public static final DeferredItem<Item> ROBSIDIAN_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.ROBSIDIAN_HOE,
            p -> new Item(p.hoe(ModToolTiers.ROBSIDIAN, 0, -3f)));

    public static final DeferredItem<Item> POBSIDIAN_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.POBSIDIAN_SWORD,
            p -> new Item(p.sword(ModToolTiers.POBSIDIAN, 3, -2.4f)));
    public static final DeferredItem<Item> POBSIDIAN_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.POBSIDIAN_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.POBSIDIAN, 1, -2.8f)));
    public static final DeferredItem<Item> POBSIDIAN_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.POBSIDIAN_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.POBSIDIAN, 1.5f, -3f)));
    public static final DeferredItem<Item> POBSIDIAN_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.POBSIDIAN_AXE,
            p -> new Item(p.axe(ModToolTiers.POBSIDIAN, 6, -3.2f)));
    public static final DeferredItem<Item> POBSIDIAN_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.POBSIDIAN_HOE,
            p -> new Item(p.hoe(ModToolTiers.POBSIDIAN, 0, -3f)));

    public static final DeferredItem<Item> OVERPOWER_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.OVERPOWER_SWORD,
            p -> new Item(p.sword(ModToolTiers.OVERPOWER, 3, -2.4f)));
    public static final DeferredItem<Item> OVERPOWER_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.OVERPOWER_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.OVERPOWER, 1, -2.8f)));
    public static final DeferredItem<Item> OVERPOWER_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.OVERPOWER_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.OVERPOWER, 1.5f, -3f)));
    public static final DeferredItem<Item> OVERPOWER_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.OVERPOWER_AXE,
            p -> new Item(p.axe(ModToolTiers.OVERPOWER, 6, -3.2f)));

    public static final DeferredItem<Item> HREDSTONE_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.HREDSTONE_SWORD,
            p -> new Item(p.sword(ModToolTiers.HREDSTONE, 3, -2.4f)));
    public static final DeferredItem<Item> HREDSTONE_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.HREDSTONE_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.HREDSTONE, 1, -2.8f)));
    public static final DeferredItem<Item> HREDSTONE_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.HREDSTONE_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.HREDSTONE, 1.5f, -3f)));
    public static final DeferredItem<Item> HREDSTONE_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.HREDSTONE_AXE,
            p -> new Item(p.axe(ModToolTiers.HREDSTONE, 6, -3.2f)));
    public static final DeferredItem<Item> HREDSTONE_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.HREDSTONE_HOE,
            p -> new Item(p.hoe(ModToolTiers.HREDSTONE, 0, -3f)));

    public static final DeferredItem<Item> HGLOWSTONE_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.HGLOWSTONE_SWORD,
            p -> new Item(p.sword(ModToolTiers.HGLOWSTONE, 3, -2.4f)));
    public static final DeferredItem<Item> HGLOWSTONE_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.HGLOWSTONE_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.HGLOWSTONE, 1, -2.8f)));
    public static final DeferredItem<Item> HGLOWSTONE_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.HGLOWSTONE_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.HGLOWSTONE, 1.5f, -3f)));
    public static final DeferredItem<Item> HGLOWSTONE_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.HGLOWSTONE_AXE,
            p -> new Item(p.axe(ModToolTiers.HGLOWSTONE, 6, -3.2f)));
    public static final DeferredItem<Item> HGLOWSTONE_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.HGLOWSTONE_HOE,
            p -> new Item(p.hoe(ModToolTiers.HGLOWSTONE, 0, -3f)));

    public static final DeferredItem<Item> RGOLD_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.RGOLD_SWORD,
            p -> new Item(p.sword(ModToolTiers.RGOLD, 3, -2.4f)));
    public static final DeferredItem<Item> RGOLD_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.RGOLD_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.RGOLD, 1, -2.8f)));
    public static final DeferredItem<Item> RGOLD_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.RGOLD_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.RGOLD, 1.5f, -3f)));
    public static final DeferredItem<Item> RGOLD_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.RGOLD_AXE,
            p -> new Item(p.axe(ModToolTiers.RGOLD, 6, -3.2f)));
    public static final DeferredItem<Item> RGOLD_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.RGOLD_HOE,
            p -> new Item(p.hoe(ModToolTiers.RGOLD, 0, -3f)));

    public static final DeferredItem<Item> RLAPIS_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.RLAPIS_SWORD,
            p -> new Item(p.sword(ModToolTiers.RLAPIS, 3, -2.4f)));
    public static final DeferredItem<Item> RLAPIS_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.RLAPIS_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.RLAPIS, 1, -2.8f)));
    public static final DeferredItem<Item> RLAPIS_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.RLAPIS_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.RLAPIS, 1.5f, -3f)));
    public static final DeferredItem<Item> RLAPIS_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.RLAPIS_AXE,
            p -> new Item(p.axe(ModToolTiers.RLAPIS, 6, -3.2f)));
    public static final DeferredItem<Item> RLAPIS_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.RLAPIS_HOE,
            p -> new Item(p.hoe(ModToolTiers.RLAPIS, 0, -3f)));

    // ── Armor pieces ───────────────────────────────────────────────────────
    public static final DeferredItem<Item> EMERALD_HELMET = ITEMS.registerItem(GeneratedRegistrationIds.Items.EMERALD_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.EMERALD_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final DeferredItem<Item> EMERALD_CHESTPLATE = ITEMS.registerItem(GeneratedRegistrationIds.Items.EMERALD_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.EMERALD_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final DeferredItem<Item> EMERALD_LEGGINGS = ITEMS.registerItem(GeneratedRegistrationIds.Items.EMERALD_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.EMERALD_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final DeferredItem<Item> EMERALD_BOOTS = ITEMS.registerItem(GeneratedRegistrationIds.Items.EMERALD_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.EMERALD_ARMOR_MATERIAL, ArmorType.BOOTS)));

    public static final DeferredItem<Item> HRED_HELMET = ITEMS.registerItem(GeneratedRegistrationIds.Items.HRED_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.HRED_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final DeferredItem<Item> HRED_CHESTPLATE = ITEMS.registerItem(GeneratedRegistrationIds.Items.HRED_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.HRED_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final DeferredItem<Item> HRED_LEGGINGS = ITEMS.registerItem(GeneratedRegistrationIds.Items.HRED_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.HRED_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final DeferredItem<Item> HRED_BOOTS = ITEMS.registerItem(GeneratedRegistrationIds.Items.HRED_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.HRED_ARMOR_MATERIAL, ArmorType.BOOTS)));

    public static final DeferredItem<Item> HGLOW_HELMET = ITEMS.registerItem(GeneratedRegistrationIds.Items.HGLOW_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.HGLOW_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final DeferredItem<Item> HGLOW_CHESTPLATE = ITEMS.registerItem(GeneratedRegistrationIds.Items.HGLOW_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.HGLOW_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final DeferredItem<Item> HGLOW_LEGGINGS = ITEMS.registerItem(GeneratedRegistrationIds.Items.HGLOW_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.HGLOW_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final DeferredItem<Item> HGLOW_BOOTS = ITEMS.registerItem(GeneratedRegistrationIds.Items.HGLOW_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.HGLOW_ARMOR_MATERIAL, ArmorType.BOOTS)));

    public static final DeferredItem<Item> OBSIDIAN_HELMET = ITEMS.registerItem(GeneratedRegistrationIds.Items.OBSIDIAN_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.OBSIDIAN_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final DeferredItem<Item> OBSIDIAN_CHESTPLATE = ITEMS.registerItem(GeneratedRegistrationIds.Items.OBSIDIAN_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.OBSIDIAN_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final DeferredItem<Item> OBSIDIAN_LEGGINGS = ITEMS.registerItem(GeneratedRegistrationIds.Items.OBSIDIAN_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.OBSIDIAN_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final DeferredItem<Item> OBSIDIAN_BOOTS = ITEMS.registerItem(GeneratedRegistrationIds.Items.OBSIDIAN_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.OBSIDIAN_ARMOR_MATERIAL, ArmorType.BOOTS)));

    public static final DeferredItem<Item> RGOLD_HELMET = ITEMS.registerItem(GeneratedRegistrationIds.Items.RGOLD_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.RGOLD_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final DeferredItem<Item> RGOLD_CHESTPLATE = ITEMS.registerItem(GeneratedRegistrationIds.Items.RGOLD_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.RGOLD_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final DeferredItem<Item> RGOLD_LEGGINGS = ITEMS.registerItem(GeneratedRegistrationIds.Items.RGOLD_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.RGOLD_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final DeferredItem<Item> RGOLD_BOOTS = ITEMS.registerItem(GeneratedRegistrationIds.Items.RGOLD_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.RGOLD_ARMOR_MATERIAL, ArmorType.BOOTS)));

    public static final DeferredItem<Item> RLAPIS_HELMET = ITEMS.registerItem(GeneratedRegistrationIds.Items.RLAPIS_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.RLAPIS_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final DeferredItem<Item> RLAPIS_CHESTPLATE = ITEMS.registerItem(GeneratedRegistrationIds.Items.RLAPIS_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.RLAPIS_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final DeferredItem<Item> RLAPIS_LEGGINGS = ITEMS.registerItem(GeneratedRegistrationIds.Items.RLAPIS_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.RLAPIS_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final DeferredItem<Item> RLAPIS_BOOTS = ITEMS.registerItem(GeneratedRegistrationIds.Items.RLAPIS_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.RLAPIS_ARMOR_MATERIAL, ArmorType.BOOTS)));

    // OVERPOWER armor — helmet uses ModArmorItem to drive set-effects.
    public static final DeferredItem<Item> OVERPOWER_HELMET = ITEMS.registerItem(GeneratedRegistrationIds.Items.OVERPOWER_HELMET,
            p -> new ModArmorItem(p.humanoidArmor(ModArmorMaterials.OVERPOWER_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final DeferredItem<Item> OVERPOWER_CHESTPLATE = ITEMS.registerItem(GeneratedRegistrationIds.Items.OVERPOWER_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.OVERPOWER_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final DeferredItem<Item> OVERPOWER_LEGGINGS = ITEMS.registerItem(GeneratedRegistrationIds.Items.OVERPOWER_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.OVERPOWER_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final DeferredItem<Item> OVERPOWER_BOOTS = ITEMS.registerItem(GeneratedRegistrationIds.Items.OVERPOWER_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.OVERPOWER_ARMOR_MATERIAL, ArmorType.BOOTS)));

    // 1.21.5+: SpawnEggItem(Properties) only; the entity type is carried by the
    // ENTITY_DATA component set via Properties#spawnEgg(EntityType).
    public static final DeferredItem<Item> GHOST_SPAWN_EGG = ITEMS.registerItem(GeneratedRegistrationIds.Items.GHOST_SPAWN_EGG,
            p -> new SpawnEggItem(p.spawnEgg(ModEntities.GHOST.get())));

    public static final DeferredItem<Item> ECTOPLASM = ITEMS.registerItem(GeneratedRegistrationIds.Items.ECTOPLASM,
            p -> new Item(p.stacksTo(64)));
    public static final DeferredItem<Item> CONDENSED_ECTOPLASM=ITEMS.registerItem(GeneratedRegistrationIds.Items.CONDENSED_ECTOPLASM,p->new Item(p.stacksTo(64)));
    public static final DeferredItem<Item> WRAITH_SPAWN_EGG=ITEMS.registerItem(GeneratedRegistrationIds.Items.WRAITH_SPAWN_EGG,p->new SpawnEggItem(p.spawnEgg(ModEntities.WRAITH.get())));
    public static final DeferredItem<Item> SPECTRAL_RESONATOR=ITEMS.registerItem(GeneratedRegistrationIds.Items.SPECTRAL_RESONATOR,p->new SpectralResonatorItem(p.durability(128)));
    public static final DeferredItem<Item> STICKY_DYNAMITE=ITEMS.registerItem(GeneratedRegistrationIds.Items.STICKY_DYNAMITE,p->new StickyDynamiteItem(p.stacksTo(16)));
    public static final DeferredItem<Item> REMOTE_DETONATOR=ITEMS.registerItem(GeneratedRegistrationIds.Items.REMOTE_DETONATOR,p->new RemoteDetonatorItem(p.stacksTo(1)));

    // ── Rough Ectoplasm tools (RECTO, stone-tier) ──────────────────────────
    public static final DeferredItem<Item> RECTO_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.RECTO_SWORD,
            p -> new EctoSwordItem(p.sword(ModToolTiers.RECTO, 3, -2.4f)));
    public static final DeferredItem<Item> RECTO_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.RECTO_PICKAXE,
            p -> new EctoPickaxeItem(p.pickaxe(ModToolTiers.RECTO, 1, -2.8f)));
    public static final DeferredItem<Item> RECTO_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.RECTO_SHOVEL,
            p -> new EctoShovelItem(p.shovel(ModToolTiers.RECTO, 1.5f, -3f)));
    public static final DeferredItem<Item> RECTO_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.RECTO_AXE,
            p -> new EctoAxeItem(p.axe(ModToolTiers.RECTO, 6, -3.2f)));
    public static final DeferredItem<Item> RECTO_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.RECTO_HOE,
            p -> new EctoHoeItem(p.hoe(ModToolTiers.RECTO, 0, -3f)));

    // ── Refined Ectoplasm + Ectoplasm tools/armor ──────────────────────────
    public static final DeferredItem<Item> REFINED_ECTOPLASM = ITEMS.registerItem(GeneratedRegistrationIds.Items.REFINED_ECTOPLASM,
            p -> new Item(p.stacksTo(64)));

    public static final DeferredItem<Item> ECTO_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.ECTO_SWORD,
            p -> new EctoSwordItem(p.sword(ModToolTiers.ECTOPLASM, 3, -2.4f)));
    public static final DeferredItem<Item> ECTO_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.ECTO_PICKAXE,
            p -> new EctoPickaxeItem(p.pickaxe(ModToolTiers.ECTOPLASM, 1, -2.8f)));
    public static final DeferredItem<Item> ECTO_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.ECTO_SHOVEL,
            p -> new EctoShovelItem(p.shovel(ModToolTiers.ECTOPLASM, 1.5f, -3f)));
    public static final DeferredItem<Item> ECTO_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.ECTO_AXE,
            p -> new EctoAxeItem(p.axe(ModToolTiers.ECTOPLASM, 6, -3.1f)));
    public static final DeferredItem<Item> ECTO_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.ECTO_HOE,
            p -> new EctoHoeItem(p.hoe(ModToolTiers.ECTOPLASM, 0, -3f)));

    public static final DeferredItem<Item> ECTO_HELMET = ITEMS.registerItem(GeneratedRegistrationIds.Items.ECTO_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.ECTO_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final DeferredItem<Item> ECTO_CHESTPLATE = ITEMS.registerItem(GeneratedRegistrationIds.Items.ECTO_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.ECTO_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final DeferredItem<Item> ECTO_LEGGINGS = ITEMS.registerItem(GeneratedRegistrationIds.Items.ECTO_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.ECTO_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final DeferredItem<Item> ECTO_BOOTS = ITEMS.registerItem(GeneratedRegistrationIds.Items.ECTO_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.ECTO_ARMOR_MATERIAL, ArmorType.BOOTS)));

    // ── Coal material items + tools + armor ────────────────────────────────
    public static final DeferredItem<Item> COAL_DUST = ITEMS.registerItem(GeneratedRegistrationIds.Items.COAL_DUST,
            p -> new Item(p.stacksTo(64)));
    public static final DeferredItem<Item> HARDENED_COAL = ITEMS.registerItem(GeneratedRegistrationIds.Items.HARDENED_COAL,
            p -> new Item(p.stacksTo(64)));

    public static final DeferredItem<Item> COAL_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.COAL_SWORD,
            p -> new CoalSwordItem(p.sword(ModToolTiers.COAL_TOOL, 2, -2.4f)));
    public static final DeferredItem<Item> COAL_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.COAL_PICKAXE,
            p -> new CoalPickaxeItem(p.pickaxe(ModToolTiers.COAL_TOOL, 1, -2.8f)));
    public static final DeferredItem<Item> COAL_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.COAL_SHOVEL,
            p -> new CoalShovelItem(p.shovel(ModToolTiers.COAL_TOOL, 1.5f, -3f)));
    public static final DeferredItem<Item> COAL_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.COAL_AXE,
            p -> new CoalAxeItem(p.axe(ModToolTiers.COAL_TOOL, 5, -3.2f)));
    public static final DeferredItem<Item> COAL_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.COAL_HOE,
            p -> new CoalHoeItem(p.hoe(ModToolTiers.COAL_TOOL, 0, -3f)));

    public static final DeferredItem<Item> COAL_HELMET = ITEMS.registerItem(GeneratedRegistrationIds.Items.COAL_HELMET,
            p -> new CoalArmorItem(p.humanoidArmor(ModArmorMaterials.COAL_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final DeferredItem<Item> COAL_CHESTPLATE = ITEMS.registerItem(GeneratedRegistrationIds.Items.COAL_CHESTPLATE,
            p -> new CoalArmorItem(p.humanoidArmor(ModArmorMaterials.COAL_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final DeferredItem<Item> COAL_LEGGINGS = ITEMS.registerItem(GeneratedRegistrationIds.Items.COAL_LEGGINGS,
            p -> new CoalArmorItem(p.humanoidArmor(ModArmorMaterials.COAL_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final DeferredItem<Item> COAL_BOOTS = ITEMS.registerItem(GeneratedRegistrationIds.Items.COAL_BOOTS,
            p -> new CoalArmorItem(p.humanoidArmor(ModArmorMaterials.COAL_ARMOR_MATERIAL, ArmorType.BOOTS)));

    // ── Raw metal rough tool sets ──────────────────────────────────────────
    public static final DeferredItem<Item> RRAW_GOLD_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.RRAW_GOLD_SWORD,
            p -> new Item(p.sword(ModToolTiers.RRAW_GOLD, 3, -2.4f)));
    public static final DeferredItem<Item> RRAW_GOLD_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.RRAW_GOLD_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.RRAW_GOLD, 1, -2.8f)));
    public static final DeferredItem<Item> RRAW_GOLD_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.RRAW_GOLD_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.RRAW_GOLD, 1.5f, -3f)));
    public static final DeferredItem<Item> RRAW_GOLD_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.RRAW_GOLD_AXE,
            p -> new Item(p.axe(ModToolTiers.RRAW_GOLD, 6, -3.2f)));
    public static final DeferredItem<Item> RRAW_GOLD_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.RRAW_GOLD_HOE,
            p -> new Item(p.hoe(ModToolTiers.RRAW_GOLD, 0, -3f)));

    public static final DeferredItem<Item> RRAW_COPPER_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.RRAW_COPPER_SWORD,
            p -> new Item(p.sword(ModToolTiers.RRAW_COPPER, 3, -2.4f)));
    public static final DeferredItem<Item> RRAW_COPPER_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.RRAW_COPPER_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.RRAW_COPPER, 1, -2.8f)));
    public static final DeferredItem<Item> RRAW_COPPER_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.RRAW_COPPER_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.RRAW_COPPER, 1.5f, -3f)));
    public static final DeferredItem<Item> RRAW_COPPER_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.RRAW_COPPER_AXE,
            p -> new Item(p.axe(ModToolTiers.RRAW_COPPER, 6, -3.2f)));
    public static final DeferredItem<Item> RRAW_COPPER_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.RRAW_COPPER_HOE,
            p -> new Item(p.hoe(ModToolTiers.RRAW_COPPER, 0, -3f)));

    public static final DeferredItem<Item> RRAW_IRON_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.RRAW_IRON_SWORD,
            p -> new Item(p.sword(ModToolTiers.RRAW_IRON, 3, -2.4f)));
    public static final DeferredItem<Item> RRAW_IRON_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.RRAW_IRON_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.RRAW_IRON, 1, -2.8f)));
    public static final DeferredItem<Item> RRAW_IRON_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.RRAW_IRON_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.RRAW_IRON, 1.5f, -3f)));
    public static final DeferredItem<Item> RRAW_IRON_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.RRAW_IRON_AXE,
            p -> new Item(p.axe(ModToolTiers.RRAW_IRON, 6, -3.2f)));
    public static final DeferredItem<Item> RRAW_IRON_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.RRAW_IRON_HOE,
            p -> new Item(p.hoe(ModToolTiers.RRAW_IRON, 0, -3f)));

    public static final DeferredItem<Item> RRAW_RGOLD_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.RRAW_RGOLD_SWORD,
            p -> new Item(p.sword(ModToolTiers.RRAW_RGOLD, 3, -2.4f)));
    public static final DeferredItem<Item> RRAW_RGOLD_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.RRAW_RGOLD_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.RRAW_RGOLD, 1, -2.8f)));
    public static final DeferredItem<Item> RRAW_RGOLD_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.RRAW_RGOLD_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.RRAW_RGOLD, 1.5f, -3f)));
    public static final DeferredItem<Item> RRAW_RGOLD_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.RRAW_RGOLD_AXE,
            p -> new Item(p.axe(ModToolTiers.RRAW_RGOLD, 6, -3.2f)));
    public static final DeferredItem<Item> RRAW_RGOLD_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.RRAW_RGOLD_HOE,
            p -> new Item(p.hoe(ModToolTiers.RRAW_RGOLD, 0, -3f)));

    public static final DeferredItem<Item> RSCRAP_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.RSCRAP_SWORD,
            p -> new Item(p.sword(ModToolTiers.RSCRAP, 3, -2.4f)));
    public static final DeferredItem<Item> RSCRAP_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.RSCRAP_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.RSCRAP, 1, -2.8f)));
    public static final DeferredItem<Item> RSCRAP_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.RSCRAP_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.RSCRAP, 1.5f, -3f)));
    public static final DeferredItem<Item> RSCRAP_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.RSCRAP_AXE,
            p -> new Item(p.axe(ModToolTiers.RSCRAP, 6, -3.2f)));
    public static final DeferredItem<Item> RSCRAP_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.RSCRAP_HOE,
            p -> new Item(p.hoe(ModToolTiers.RSCRAP, 0, -3f)));

    // ── Crystal / element materials ────────────────────────────────────────
    public static final DeferredItem<Item> CALCIFIED_AMETHYST = ITEMS.registerItem(GeneratedRegistrationIds.Items.CALCIFIED_AMETHYST,
            p -> new Item(p.stacksTo(64)));
    public static final DeferredItem<Item> GLACIAL_SHARD = ITEMS.registerItem(GeneratedRegistrationIds.Items.GLACIAL_SHARD,
            p -> new Item(p.stacksTo(64)));
    public static final DeferredItem<Item> POLISHED_QUARTZ = ITEMS.registerItem(GeneratedRegistrationIds.Items.POLISHED_QUARTZ,
            p -> new Item(p.stacksTo(64)));
    public static final DeferredItem<Item> POLISHED_PRISMARINE = ITEMS.registerItem(GeneratedRegistrationIds.Items.POLISHED_PRISMARINE,
            p -> new Item(p.stacksTo(64)));

    public static final DeferredItem<Item> RAMETHYST_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.RAMETHYST_SWORD,
            p -> new Item(p.sword(ModToolTiers.RAMETHYST, 3, -2.4f)));
    public static final DeferredItem<Item> RAMETHYST_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.RAMETHYST_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.RAMETHYST, 1, -2.8f)));
    public static final DeferredItem<Item> RAMETHYST_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.RAMETHYST_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.RAMETHYST, 1.5f, -3f)));
    public static final DeferredItem<Item> RAMETHYST_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.RAMETHYST_AXE,
            p -> new Item(p.axe(ModToolTiers.RAMETHYST, 6, -3.2f)));
    public static final DeferredItem<Item> RAMETHYST_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.RAMETHYST_HOE,
            p -> new Item(p.hoe(ModToolTiers.RAMETHYST, 0, -3f)));

    public static final DeferredItem<Item> SNOW_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.SNOW_SWORD,
            p -> new Item(p.sword(ModToolTiers.SNOW_TOOL, 3, -2.4f)));
    public static final DeferredItem<Item> SNOW_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.SNOW_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.SNOW_TOOL, 1, -2.8f)));
    public static final DeferredItem<Item> SNOW_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.SNOW_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.SNOW_TOOL, 1.5f, -3f)));
    public static final DeferredItem<Item> SNOW_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.SNOW_AXE,
            p -> new Item(p.axe(ModToolTiers.SNOW_TOOL, 6, -3.2f)));
    public static final DeferredItem<Item> SNOW_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.SNOW_HOE,
            p -> new Item(p.hoe(ModToolTiers.SNOW_TOOL, 0, -3f)));

    public static final DeferredItem<Item> RQUARTZ_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.RQUARTZ_SWORD,
            p -> new Item(p.sword(ModToolTiers.RQUARTZ, 3, -2.4f)));
    public static final DeferredItem<Item> RQUARTZ_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.RQUARTZ_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.RQUARTZ, 1, -2.8f)));
    public static final DeferredItem<Item> RQUARTZ_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.RQUARTZ_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.RQUARTZ, 1.5f, -3f)));
    public static final DeferredItem<Item> RQUARTZ_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.RQUARTZ_AXE,
            p -> new Item(p.axe(ModToolTiers.RQUARTZ, 6, -3.2f)));
    public static final DeferredItem<Item> RQUARTZ_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.RQUARTZ_HOE,
            p -> new Item(p.hoe(ModToolTiers.RQUARTZ, 0, -3f)));

    public static final DeferredItem<Item> RPRISM_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.RPRISM_SWORD,
            p -> new Item(p.sword(ModToolTiers.RPRISM, 3, -2.4f)));
    public static final DeferredItem<Item> RPRISM_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.RPRISM_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.RPRISM, 1, -2.8f)));
    public static final DeferredItem<Item> RPRISM_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.RPRISM_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.RPRISM, 1.5f, -3f)));
    public static final DeferredItem<Item> RPRISM_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.RPRISM_AXE,
            p -> new Item(p.axe(ModToolTiers.RPRISM, 6, -3.2f)));
    public static final DeferredItem<Item> RPRISM_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.RPRISM_HOE,
            p -> new Item(p.hoe(ModToolTiers.RPRISM, 0, -3f)));

    public static final DeferredItem<Item> CAMETHYST_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.CAMETHYST_SWORD,
            p -> new Item(p.sword(ModToolTiers.CAMETHYST, 3, -2.4f)));
    public static final DeferredItem<Item> CAMETHYST_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.CAMETHYST_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.CAMETHYST, 1, -2.8f)));
    public static final DeferredItem<Item> CAMETHYST_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.CAMETHYST_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.CAMETHYST, 1.5f, -3f)));
    public static final DeferredItem<Item> CAMETHYST_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.CAMETHYST_AXE,
            p -> new Item(p.axe(ModToolTiers.CAMETHYST, 6, -3.2f)));
    public static final DeferredItem<Item> CAMETHYST_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.CAMETHYST_HOE,
            p -> new Item(p.hoe(ModToolTiers.CAMETHYST, 0, -3f)));
    public static final DeferredItem<Item> CAMETHYST_HELMET = ITEMS.registerItem(GeneratedRegistrationIds.Items.CAMETHYST_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.CAMETHYST_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final DeferredItem<Item> CAMETHYST_CHESTPLATE = ITEMS.registerItem(GeneratedRegistrationIds.Items.CAMETHYST_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.CAMETHYST_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final DeferredItem<Item> CAMETHYST_LEGGINGS = ITEMS.registerItem(GeneratedRegistrationIds.Items.CAMETHYST_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.CAMETHYST_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final DeferredItem<Item> CAMETHYST_BOOTS = ITEMS.registerItem(GeneratedRegistrationIds.Items.CAMETHYST_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.CAMETHYST_ARMOR_MATERIAL, ArmorType.BOOTS)));

    public static final DeferredItem<Item> ICE_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.ICE_SWORD,
            p -> new Item(p.sword(ModToolTiers.ICE_TOOL, 3, -2.4f)));
    public static final DeferredItem<Item> ICE_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.ICE_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.ICE_TOOL, 1, -2.8f)));
    public static final DeferredItem<Item> ICE_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.ICE_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.ICE_TOOL, 1.5f, -3f)));
    public static final DeferredItem<Item> ICE_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.ICE_AXE,
            p -> new Item(p.axe(ModToolTiers.ICE_TOOL, 6, -3.2f)));
    public static final DeferredItem<Item> ICE_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.ICE_HOE,
            p -> new Item(p.hoe(ModToolTiers.ICE_TOOL, 0, -3f)));
    public static final DeferredItem<Item> ICE_HELMET = ITEMS.registerItem(GeneratedRegistrationIds.Items.ICE_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.ICE_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final DeferredItem<Item> ICE_CHESTPLATE = ITEMS.registerItem(GeneratedRegistrationIds.Items.ICE_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.ICE_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final DeferredItem<Item> ICE_LEGGINGS = ITEMS.registerItem(GeneratedRegistrationIds.Items.ICE_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.ICE_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final DeferredItem<Item> ICE_BOOTS = ITEMS.registerItem(GeneratedRegistrationIds.Items.ICE_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.ICE_ARMOR_MATERIAL, ArmorType.BOOTS)));

    public static final DeferredItem<Item> PQUARTZ_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.PQUARTZ_SWORD,
            p -> new Item(p.sword(ModToolTiers.PQUARTZ, 3, -2.4f)));
    public static final DeferredItem<Item> PQUARTZ_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.PQUARTZ_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.PQUARTZ, 1, -2.8f)));
    public static final DeferredItem<Item> PQUARTZ_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.PQUARTZ_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.PQUARTZ, 1.5f, -3f)));
    public static final DeferredItem<Item> PQUARTZ_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.PQUARTZ_AXE,
            p -> new Item(p.axe(ModToolTiers.PQUARTZ, 6, -3.2f)));
    public static final DeferredItem<Item> PQUARTZ_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.PQUARTZ_HOE,
            p -> new Item(p.hoe(ModToolTiers.PQUARTZ, 0, -3f)));
    public static final DeferredItem<Item> PQUARTZ_HELMET = ITEMS.registerItem(GeneratedRegistrationIds.Items.PQUARTZ_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.PQUARTZ_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final DeferredItem<Item> PQUARTZ_CHESTPLATE = ITEMS.registerItem(GeneratedRegistrationIds.Items.PQUARTZ_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.PQUARTZ_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final DeferredItem<Item> PQUARTZ_LEGGINGS = ITEMS.registerItem(GeneratedRegistrationIds.Items.PQUARTZ_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.PQUARTZ_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final DeferredItem<Item> PQUARTZ_BOOTS = ITEMS.registerItem(GeneratedRegistrationIds.Items.PQUARTZ_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.PQUARTZ_ARMOR_MATERIAL, ArmorType.BOOTS)));

    public static final DeferredItem<Item> PPRISM_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.PPRISM_SWORD,
            p -> new Item(p.sword(ModToolTiers.PPRISM, 3, -2.4f)));
    public static final DeferredItem<Item> PPRISM_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.PPRISM_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.PPRISM, 1, -2.8f)));
    public static final DeferredItem<Item> PPRISM_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.PPRISM_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.PPRISM, 1.5f, -3f)));
    public static final DeferredItem<Item> PPRISM_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.PPRISM_AXE,
            p -> new Item(p.axe(ModToolTiers.PPRISM, 6, -3.2f)));
    public static final DeferredItem<Item> PPRISM_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.PPRISM_HOE,
            p -> new Item(p.hoe(ModToolTiers.PPRISM, 0, -3f)));
    public static final DeferredItem<Item> PPRISM_HELMET = ITEMS.registerItem(GeneratedRegistrationIds.Items.PPRISM_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.PPRISM_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final DeferredItem<Item> PPRISM_CHESTPLATE = ITEMS.registerItem(GeneratedRegistrationIds.Items.PPRISM_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.PPRISM_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final DeferredItem<Item> PPRISM_LEGGINGS = ITEMS.registerItem(GeneratedRegistrationIds.Items.PPRISM_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.PPRISM_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final DeferredItem<Item> PPRISM_BOOTS = ITEMS.registerItem(GeneratedRegistrationIds.Items.PPRISM_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.PPRISM_ARMOR_MATERIAL, ArmorType.BOOTS)));

    // ── Flint + FNI ────────────────────────────────────────────────────────
    public static final DeferredItem<Item> RFLINT_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.RFLINT_SWORD,
            p -> new Item(p.sword(ModToolTiers.RFLINT, 3, -2.4f)));
    public static final DeferredItem<Item> RFLINT_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.RFLINT_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.RFLINT, 1, -2.8f)));
    public static final DeferredItem<Item> RFLINT_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.RFLINT_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.RFLINT, 1.5f, -3f)));
    public static final DeferredItem<Item> RFLINT_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.RFLINT_AXE,
            p -> new Item(p.axe(ModToolTiers.RFLINT, 6, -3.2f)));
    public static final DeferredItem<Item> RFLINT_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.RFLINT_HOE,
            p -> new Item(p.hoe(ModToolTiers.RFLINT, 0, -3f)));

    public static final DeferredItem<Item> FNI_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.FNI_SWORD,
            p -> new Item(p.sword(ModToolTiers.FNI_TOOLS, 3, -2.4f)));
    public static final DeferredItem<Item> FNI_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.FNI_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.FNI_TOOLS, 1, -2.8f)));
    public static final DeferredItem<Item> FNI_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.FNI_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.FNI_TOOLS, 1.5f, -3f)));
    public static final DeferredItem<Item> FNI_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.FNI_AXE,
            p -> new Item(p.axe(ModToolTiers.FNI_TOOLS, 6, -3.2f)));
    public static final DeferredItem<Item> FNI_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.FNI_HOE,
            p -> new Item(p.hoe(ModToolTiers.FNI_TOOLS, 0, -3f)));
    public static final DeferredItem<Item> FNI_HELMET = ITEMS.registerItem(GeneratedRegistrationIds.Items.FNI_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.FNI_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final DeferredItem<Item> FNI_CHESTPLATE = ITEMS.registerItem(GeneratedRegistrationIds.Items.FNI_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.FNI_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final DeferredItem<Item> FNI_LEGGINGS = ITEMS.registerItem(GeneratedRegistrationIds.Items.FNI_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.FNI_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final DeferredItem<Item> FNI_BOOTS = ITEMS.registerItem(GeneratedRegistrationIds.Items.FNI_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.FNI_ARMOR_MATERIAL, ArmorType.BOOTS)));

    // ── Stone-rock variants (13 sets × 5 tools) ────────────────────────────
    public static final DeferredItem<Item> ANDESITE_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.ANDESITE_SWORD,
            p -> new Item(p.sword(ModToolTiers.STONE_ANDESITE, 3, -2.4f)));
    public static final DeferredItem<Item> ANDESITE_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.ANDESITE_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.STONE_ANDESITE, 1, -2.8f)));
    public static final DeferredItem<Item> ANDESITE_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.ANDESITE_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.STONE_ANDESITE, 1.5f, -3f)));
    public static final DeferredItem<Item> ANDESITE_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.ANDESITE_AXE,
            p -> new Item(p.axe(ModToolTiers.STONE_ANDESITE, 6, -3.2f)));
    public static final DeferredItem<Item> ANDESITE_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.ANDESITE_HOE,
            p -> new Item(p.hoe(ModToolTiers.STONE_ANDESITE, 0, -3f)));

    public static final DeferredItem<Item> BASALT_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.BASALT_SWORD,
            p -> new Item(p.sword(ModToolTiers.STONE_BASALT, 3, -2.5f)));
    public static final DeferredItem<Item> BASALT_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.BASALT_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.STONE_BASALT, 1, -2.9f)));
    public static final DeferredItem<Item> BASALT_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.BASALT_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.STONE_BASALT, 2.0f, -3.1f)));
    public static final DeferredItem<Item> BASALT_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.BASALT_AXE,
            p -> new Item(p.axe(ModToolTiers.STONE_BASALT, 7, -3.3f)));
    public static final DeferredItem<Item> BASALT_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.BASALT_HOE,
            p -> new Item(p.hoe(ModToolTiers.STONE_BASALT, 0, -3.1f)));

    public static final DeferredItem<Item> BLACKSTONE_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.BLACKSTONE_SWORD,
            p -> new Item(p.sword(ModToolTiers.STONE_BLACKSTONE, 4, -2.5f)));
    public static final DeferredItem<Item> BLACKSTONE_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.BLACKSTONE_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.STONE_BLACKSTONE, 1, -2.9f)));
    public static final DeferredItem<Item> BLACKSTONE_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.BLACKSTONE_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.STONE_BLACKSTONE, 2.0f, -3.1f)));
    public static final DeferredItem<Item> BLACKSTONE_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.BLACKSTONE_AXE,
            p -> new Item(p.axe(ModToolTiers.STONE_BLACKSTONE, 7, -3.35f)));
    public static final DeferredItem<Item> BLACKSTONE_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.BLACKSTONE_HOE,
            p -> new Item(p.hoe(ModToolTiers.STONE_BLACKSTONE, 0, -3.1f)));

    public static final DeferredItem<Item> CALCITE_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.CALCITE_SWORD,
            p -> new Item(p.sword(ModToolTiers.STONE_CALCITE, 2, -2.2f)));
    public static final DeferredItem<Item> CALCITE_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.CALCITE_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.STONE_CALCITE, 1, -2.6f)));
    public static final DeferredItem<Item> CALCITE_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.CALCITE_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.STONE_CALCITE, 1.0f, -2.8f)));
    public static final DeferredItem<Item> CALCITE_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.CALCITE_AXE,
            p -> new Item(p.axe(ModToolTiers.STONE_CALCITE, 5, -3.0f)));
    public static final DeferredItem<Item> CALCITE_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.CALCITE_HOE,
            p -> new Item(p.hoe(ModToolTiers.STONE_CALCITE, 0, -2.6f)));

    public static final DeferredItem<Item> DEEPSLATE_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.DEEPSLATE_SWORD,
            p -> new Item(p.sword(ModToolTiers.STONE_DEEPSLATE, 4, -2.55f)));
    public static final DeferredItem<Item> DEEPSLATE_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.DEEPSLATE_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.STONE_DEEPSLATE, 1, -2.95f)));
    public static final DeferredItem<Item> DEEPSLATE_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.DEEPSLATE_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.STONE_DEEPSLATE, 2.0f, -3.15f)));
    public static final DeferredItem<Item> DEEPSLATE_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.DEEPSLATE_AXE,
            p -> new Item(p.axe(ModToolTiers.STONE_DEEPSLATE, 7, -3.4f)));
    public static final DeferredItem<Item> DEEPSLATE_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.DEEPSLATE_HOE,
            p -> new Item(p.hoe(ModToolTiers.STONE_DEEPSLATE, 0, -3.1f)));

    public static final DeferredItem<Item> DIORITE_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.DIORITE_SWORD,
            p -> new Item(p.sword(ModToolTiers.STONE_DIORITE, 3, -2.4f)));
    public static final DeferredItem<Item> DIORITE_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.DIORITE_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.STONE_DIORITE, 1, -2.8f)));
    public static final DeferredItem<Item> DIORITE_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.DIORITE_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.STONE_DIORITE, 1.5f, -3f)));
    public static final DeferredItem<Item> DIORITE_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.DIORITE_AXE,
            p -> new Item(p.axe(ModToolTiers.STONE_DIORITE, 6, -3.2f)));
    public static final DeferredItem<Item> DIORITE_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.DIORITE_HOE,
            p -> new Item(p.hoe(ModToolTiers.STONE_DIORITE, 0, -2.9f)));

    public static final DeferredItem<Item> END_STONE_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.END_STONE_SWORD,
            p -> new Item(p.sword(ModToolTiers.STONE_END_STONE, 3, -2.35f)));
    public static final DeferredItem<Item> END_STONE_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.END_STONE_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.STONE_END_STONE, 1, -2.75f)));
    public static final DeferredItem<Item> END_STONE_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.END_STONE_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.STONE_END_STONE, 1.5f, -2.95f)));
    public static final DeferredItem<Item> END_STONE_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.END_STONE_AXE,
            p -> new Item(p.axe(ModToolTiers.STONE_END_STONE, 6, -3.15f)));
    public static final DeferredItem<Item> END_STONE_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.END_STONE_HOE,
            p -> new Item(p.hoe(ModToolTiers.STONE_END_STONE, 0, -2.8f)));

    public static final DeferredItem<Item> GRANITE_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.GRANITE_SWORD,
            p -> new Item(p.sword(ModToolTiers.STONE_GRANITE, 3, -2.5f)));
    public static final DeferredItem<Item> GRANITE_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.GRANITE_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.STONE_GRANITE, 1, -2.9f)));
    public static final DeferredItem<Item> GRANITE_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.GRANITE_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.STONE_GRANITE, 2.0f, -3.1f)));
    public static final DeferredItem<Item> GRANITE_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.GRANITE_AXE,
            p -> new Item(p.axe(ModToolTiers.STONE_GRANITE, 7, -3.3f)));
    public static final DeferredItem<Item> GRANITE_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.GRANITE_HOE,
            p -> new Item(p.hoe(ModToolTiers.STONE_GRANITE, 0, -3.1f)));

    public static final DeferredItem<Item> NETHERRACK_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.NETHERRACK_SWORD,
            p -> new Item(p.sword(ModToolTiers.STONE_NETHERRACK, 2, -2.2f)));
    public static final DeferredItem<Item> NETHERRACK_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.NETHERRACK_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.STONE_NETHERRACK, 1, -2.6f)));
    public static final DeferredItem<Item> NETHERRACK_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.NETHERRACK_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.STONE_NETHERRACK, 1.0f, -2.8f)));
    public static final DeferredItem<Item> NETHERRACK_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.NETHERRACK_AXE,
            p -> new Item(p.axe(ModToolTiers.STONE_NETHERRACK, 5, -3.0f)));
    public static final DeferredItem<Item> NETHERRACK_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.NETHERRACK_HOE,
            p -> new Item(p.hoe(ModToolTiers.STONE_NETHERRACK, 0, -2.5f)));

    public static final DeferredItem<Item> SANDSTONE_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.SANDSTONE_SWORD,
            p -> new Item(p.sword(ModToolTiers.STONE_SANDSTONE, 2, -2.3f)));
    public static final DeferredItem<Item> SANDSTONE_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.SANDSTONE_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.STONE_SANDSTONE, 1, -2.7f)));
    public static final DeferredItem<Item> SANDSTONE_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.SANDSTONE_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.STONE_SANDSTONE, 1.0f, -2.9f)));
    public static final DeferredItem<Item> SANDSTONE_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.SANDSTONE_AXE,
            p -> new Item(p.axe(ModToolTiers.STONE_SANDSTONE, 5, -3.1f)));
    public static final DeferredItem<Item> SANDSTONE_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.SANDSTONE_HOE,
            p -> new Item(p.hoe(ModToolTiers.STONE_SANDSTONE, 0, -2.7f)));

    public static final DeferredItem<Item> SMOOTH_BASALT_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.SMOOTH_BASALT_SWORD,
            p -> new Item(p.sword(ModToolTiers.STONE_SMOOTH_BASALT, 3, -2.45f)));
    public static final DeferredItem<Item> SMOOTH_BASALT_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.SMOOTH_BASALT_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.STONE_SMOOTH_BASALT, 1, -2.85f)));
    public static final DeferredItem<Item> SMOOTH_BASALT_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.SMOOTH_BASALT_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.STONE_SMOOTH_BASALT, 1.5f, -3.05f)));
    public static final DeferredItem<Item> SMOOTH_BASALT_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.SMOOTH_BASALT_AXE,
            p -> new Item(p.axe(ModToolTiers.STONE_SMOOTH_BASALT, 6, -3.25f)));
    public static final DeferredItem<Item> SMOOTH_BASALT_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.SMOOTH_BASALT_HOE,
            p -> new Item(p.hoe(ModToolTiers.STONE_SMOOTH_BASALT, 0, -3.0f)));

    public static final DeferredItem<Item> TERRACOTTA_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.TERRACOTTA_SWORD,
            p -> new Item(p.sword(ModToolTiers.STONE_TERRACOTTA, 3, -2.35f)));
    public static final DeferredItem<Item> TERRACOTTA_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.TERRACOTTA_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.STONE_TERRACOTTA, 1, -2.75f)));
    public static final DeferredItem<Item> TERRACOTTA_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.TERRACOTTA_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.STONE_TERRACOTTA, 1.5f, -2.95f)));
    public static final DeferredItem<Item> TERRACOTTA_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.TERRACOTTA_AXE,
            p -> new Item(p.axe(ModToolTiers.STONE_TERRACOTTA, 6, -3.15f)));
    public static final DeferredItem<Item> TERRACOTTA_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.TERRACOTTA_HOE,
            p -> new Item(p.hoe(ModToolTiers.STONE_TERRACOTTA, 0, -2.8f)));

    public static final DeferredItem<Item> TUFF_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.TUFF_SWORD,
            p -> new Item(p.sword(ModToolTiers.STONE_TUFF, 2, -2.35f)));
    public static final DeferredItem<Item> TUFF_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.TUFF_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.STONE_TUFF, 1, -2.75f)));
    public static final DeferredItem<Item> TUFF_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.TUFF_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.STONE_TUFF, 1.5f, -2.95f)));
    public static final DeferredItem<Item> TUFF_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.TUFF_AXE,
            p -> new Item(p.axe(ModToolTiers.STONE_TUFF, 5, -3.15f)));
    public static final DeferredItem<Item> TUFF_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.TUFF_HOE,
            p -> new Item(p.hoe(ModToolTiers.STONE_TUFF, 0, -2.8f)));

    // ── Wood variants (11 × 5) ─────────────────────────────────────────────
    public static final DeferredItem<Item> OAK_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.OAK_SWORD,
            p -> new Item(p.sword(ModToolTiers.WOOD_OAK, 3, -2.4f)));
    public static final DeferredItem<Item> OAK_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.OAK_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.WOOD_OAK, 1, -2.8f)));
    public static final DeferredItem<Item> OAK_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.OAK_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.WOOD_OAK, 1.5f, -3f)));
    public static final DeferredItem<Item> OAK_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.OAK_AXE,
            p -> new Item(p.axe(ModToolTiers.WOOD_OAK, 6, -3.2f)));
    public static final DeferredItem<Item> OAK_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.OAK_HOE,
            p -> new Item(p.hoe(ModToolTiers.WOOD_OAK, 0, -3f)));

    public static final DeferredItem<Item> SPRUCE_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.SPRUCE_SWORD,
            p -> new Item(p.sword(ModToolTiers.WOOD_SPRUCE, 3, -2.4f)));
    public static final DeferredItem<Item> SPRUCE_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.SPRUCE_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.WOOD_SPRUCE, 1, -2.8f)));
    public static final DeferredItem<Item> SPRUCE_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.SPRUCE_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.WOOD_SPRUCE, 1.5f, -3f)));
    public static final DeferredItem<Item> SPRUCE_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.SPRUCE_AXE,
            p -> new Item(p.axe(ModToolTiers.WOOD_SPRUCE, 6, -3.2f)));
    public static final DeferredItem<Item> SPRUCE_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.SPRUCE_HOE,
            p -> new Item(p.hoe(ModToolTiers.WOOD_SPRUCE, 0, -3f)));

    public static final DeferredItem<Item> BIRCH_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.BIRCH_SWORD,
            p -> new Item(p.sword(ModToolTiers.WOOD_BIRCH, 3, -2.4f)));
    public static final DeferredItem<Item> BIRCH_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.BIRCH_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.WOOD_BIRCH, 1, -2.8f)));
    public static final DeferredItem<Item> BIRCH_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.BIRCH_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.WOOD_BIRCH, 1.5f, -3f)));
    public static final DeferredItem<Item> BIRCH_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.BIRCH_AXE,
            p -> new Item(p.axe(ModToolTiers.WOOD_BIRCH, 6, -3.2f)));
    public static final DeferredItem<Item> BIRCH_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.BIRCH_HOE,
            p -> new Item(p.hoe(ModToolTiers.WOOD_BIRCH, 0, -3f)));

    public static final DeferredItem<Item> JUNGLE_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.JUNGLE_SWORD,
            p -> new Item(p.sword(ModToolTiers.WOOD_JUNGLE, 3, -2.4f)));
    public static final DeferredItem<Item> JUNGLE_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.JUNGLE_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.WOOD_JUNGLE, 1, -2.8f)));
    public static final DeferredItem<Item> JUNGLE_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.JUNGLE_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.WOOD_JUNGLE, 1.5f, -3f)));
    public static final DeferredItem<Item> JUNGLE_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.JUNGLE_AXE,
            p -> new Item(p.axe(ModToolTiers.WOOD_JUNGLE, 6, -3.2f)));
    public static final DeferredItem<Item> JUNGLE_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.JUNGLE_HOE,
            p -> new Item(p.hoe(ModToolTiers.WOOD_JUNGLE, 0, -3f)));

    public static final DeferredItem<Item> ACACIA_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.ACACIA_SWORD,
            p -> new Item(p.sword(ModToolTiers.WOOD_ACACIA, 3, -2.4f)));
    public static final DeferredItem<Item> ACACIA_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.ACACIA_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.WOOD_ACACIA, 1, -2.8f)));
    public static final DeferredItem<Item> ACACIA_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.ACACIA_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.WOOD_ACACIA, 1.5f, -3f)));
    public static final DeferredItem<Item> ACACIA_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.ACACIA_AXE,
            p -> new Item(p.axe(ModToolTiers.WOOD_ACACIA, 6, -3.2f)));
    public static final DeferredItem<Item> ACACIA_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.ACACIA_HOE,
            p -> new Item(p.hoe(ModToolTiers.WOOD_ACACIA, 0, -3f)));

    public static final DeferredItem<Item> DARK_OAK_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.DARK_OAK_SWORD,
            p -> new Item(p.sword(ModToolTiers.WOOD_DARK_OAK, 3, -2.4f)));
    public static final DeferredItem<Item> DARK_OAK_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.DARK_OAK_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.WOOD_DARK_OAK, 1, -2.8f)));
    public static final DeferredItem<Item> DARK_OAK_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.DARK_OAK_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.WOOD_DARK_OAK, 1.5f, -3f)));
    public static final DeferredItem<Item> DARK_OAK_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.DARK_OAK_AXE,
            p -> new Item(p.axe(ModToolTiers.WOOD_DARK_OAK, 6, -3.2f)));
    public static final DeferredItem<Item> DARK_OAK_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.DARK_OAK_HOE,
            p -> new Item(p.hoe(ModToolTiers.WOOD_DARK_OAK, 0, -3f)));

    public static final DeferredItem<Item> MANGROVE_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.MANGROVE_SWORD,
            p -> new Item(p.sword(ModToolTiers.WOOD_MANGROVE, 3, -2.4f)));
    public static final DeferredItem<Item> MANGROVE_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.MANGROVE_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.WOOD_MANGROVE, 1, -2.8f)));
    public static final DeferredItem<Item> MANGROVE_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.MANGROVE_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.WOOD_MANGROVE, 1.5f, -3f)));
    public static final DeferredItem<Item> MANGROVE_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.MANGROVE_AXE,
            p -> new Item(p.axe(ModToolTiers.WOOD_MANGROVE, 6, -3.2f)));
    public static final DeferredItem<Item> MANGROVE_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.MANGROVE_HOE,
            p -> new Item(p.hoe(ModToolTiers.WOOD_MANGROVE, 0, -3f)));

    public static final DeferredItem<Item> CHERRY_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.CHERRY_SWORD,
            p -> new Item(p.sword(ModToolTiers.WOOD_CHERRY, 3, -2.4f)));
    public static final DeferredItem<Item> CHERRY_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.CHERRY_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.WOOD_CHERRY, 1, -2.8f)));
    public static final DeferredItem<Item> CHERRY_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.CHERRY_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.WOOD_CHERRY, 1.5f, -3f)));
    public static final DeferredItem<Item> CHERRY_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.CHERRY_AXE,
            p -> new Item(p.axe(ModToolTiers.WOOD_CHERRY, 6, -3.2f)));
    public static final DeferredItem<Item> CHERRY_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.CHERRY_HOE,
            p -> new Item(p.hoe(ModToolTiers.WOOD_CHERRY, 0, -3f)));

    public static final DeferredItem<Item> BAMBOO_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.BAMBOO_SWORD,
            p -> new Item(p.sword(ModToolTiers.WOOD_BAMBOO, 3, -2.4f)));
    public static final DeferredItem<Item> BAMBOO_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.BAMBOO_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.WOOD_BAMBOO, 1, -2.8f)));
    public static final DeferredItem<Item> BAMBOO_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.BAMBOO_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.WOOD_BAMBOO, 1.5f, -3f)));
    public static final DeferredItem<Item> BAMBOO_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.BAMBOO_AXE,
            p -> new Item(p.axe(ModToolTiers.WOOD_BAMBOO, 6, -3.2f)));
    public static final DeferredItem<Item> BAMBOO_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.BAMBOO_HOE,
            p -> new Item(p.hoe(ModToolTiers.WOOD_BAMBOO, 0, -3f)));

    public static final DeferredItem<Item> CRIMSON_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.CRIMSON_SWORD,
            p -> new Item(p.sword(ModToolTiers.WOOD_CRIMSON, 3, -2.4f)));
    public static final DeferredItem<Item> CRIMSON_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.CRIMSON_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.WOOD_CRIMSON, 1, -2.8f)));
    public static final DeferredItem<Item> CRIMSON_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.CRIMSON_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.WOOD_CRIMSON, 1.5f, -3f)));
    public static final DeferredItem<Item> CRIMSON_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.CRIMSON_AXE,
            p -> new Item(p.axe(ModToolTiers.WOOD_CRIMSON, 6, -3.2f)));
    public static final DeferredItem<Item> CRIMSON_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.CRIMSON_HOE,
            p -> new Item(p.hoe(ModToolTiers.WOOD_CRIMSON, 0, -3f)));

    public static final DeferredItem<Item> WARPED_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.WARPED_SWORD,
            p -> new Item(p.sword(ModToolTiers.WOOD_WARPED, 3, -2.4f)));
    public static final DeferredItem<Item> WARPED_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.WARPED_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.WOOD_WARPED, 1, -2.8f)));
    public static final DeferredItem<Item> WARPED_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.WARPED_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.WOOD_WARPED, 1.5f, -3f)));
    public static final DeferredItem<Item> WARPED_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.WARPED_AXE,
            p -> new Item(p.axe(ModToolTiers.WOOD_WARPED, 6, -3.2f)));
    public static final DeferredItem<Item> WARPED_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.WARPED_HOE,
            p -> new Item(p.hoe(ModToolTiers.WOOD_WARPED, 0, -3f)));

    // ── Leather tools ──────────────────────────────────────────────────────
    public static final DeferredItem<Item> LEATHER_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.LEATHER_SWORD,
            p -> new Item(p.sword(ModToolTiers.LEATHER, 3, -2.4f)));
    public static final DeferredItem<Item> LEATHER_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.LEATHER_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.LEATHER, 1, -2.8f)));
    public static final DeferredItem<Item> LEATHER_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.LEATHER_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.LEATHER, 1.5f, -3f)));
    public static final DeferredItem<Item> LEATHER_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.LEATHER_AXE,
            p -> new Item(p.axe(ModToolTiers.LEATHER, 6, -3.2f)));
    public static final DeferredItem<Item> LEATHER_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.LEATHER_HOE,
            p -> new Item(p.hoe(ModToolTiers.LEATHER, 0, -3f)));

    // ── Vanilla material sets ──────────────────────────────────────────────

    public static final DeferredItem<Item> PAPER_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.PAPER_SWORD,
            p -> new Item(p.sword(ModToolTiers.PAPER, 3, -2.4f)));
    public static final DeferredItem<Item> PAPER_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.PAPER_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.PAPER, 1, -2.8f)));
    public static final DeferredItem<Item> PAPER_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.PAPER_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.PAPER, 1.5f, -3f)));
    public static final DeferredItem<Item> PAPER_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.PAPER_AXE,
            p -> new Item(p.axe(ModToolTiers.PAPER, 6, -3.2f)));
    public static final DeferredItem<Item> PAPER_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.PAPER_HOE,
            p -> new Item(p.hoe(ModToolTiers.PAPER, 0, -3f)));

    public static final DeferredItem<Item> FEATHER_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.FEATHER_SWORD,
            p -> new Item(p.sword(ModToolTiers.FEATHER, 3, -2.4f)));
    public static final DeferredItem<Item> FEATHER_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.FEATHER_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.FEATHER, 1, -2.8f)));
    public static final DeferredItem<Item> FEATHER_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.FEATHER_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.FEATHER, 1.5f, -3f)));
    public static final DeferredItem<Item> FEATHER_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.FEATHER_AXE,
            p -> new Item(p.axe(ModToolTiers.FEATHER, 6, -3.2f)));
    public static final DeferredItem<Item> FEATHER_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.FEATHER_HOE,
            p -> new Item(p.hoe(ModToolTiers.FEATHER, 0, -3f)));

    public static final DeferredItem<Item> GLASS_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.GLASS_SWORD,
            p -> new Item(p.sword(ModToolTiers.GLASS, 3, -2.4f)));
    public static final DeferredItem<Item> GLASS_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.GLASS_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.GLASS, 1, -2.8f)));
    public static final DeferredItem<Item> GLASS_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.GLASS_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.GLASS, 1.5f, -3f)));
    public static final DeferredItem<Item> GLASS_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.GLASS_AXE,
            p -> new Item(p.axe(ModToolTiers.GLASS, 6, -3.2f)));
    public static final DeferredItem<Item> GLASS_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.GLASS_HOE,
            p -> new Item(p.hoe(ModToolTiers.GLASS, 0, -3f)));

    public static final DeferredItem<Item> RABBIT_HIDE_HELMET = ITEMS.registerItem(GeneratedRegistrationIds.Items.RABBIT_HIDE_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.RABBIT_HIDE_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final DeferredItem<Item> RABBIT_HIDE_CHESTPLATE = ITEMS.registerItem(GeneratedRegistrationIds.Items.RABBIT_HIDE_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.RABBIT_HIDE_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final DeferredItem<Item> RABBIT_HIDE_LEGGINGS = ITEMS.registerItem(GeneratedRegistrationIds.Items.RABBIT_HIDE_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.RABBIT_HIDE_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final DeferredItem<Item> RABBIT_HIDE_BOOTS = ITEMS.registerItem(GeneratedRegistrationIds.Items.RABBIT_HIDE_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.RABBIT_HIDE_ARMOR_MATERIAL, ArmorType.BOOTS)));

    public static final DeferredItem<Item> CACTUS_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.CACTUS_SWORD,
            p -> new Item(p.sword(ModToolTiers.CACTUS, 3, -2.4f)));
    public static final DeferredItem<Item> CACTUS_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.CACTUS_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.CACTUS, 1, -2.8f)));
    public static final DeferredItem<Item> CACTUS_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.CACTUS_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.CACTUS, 1.5f, -3f)));
    public static final DeferredItem<Item> CACTUS_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.CACTUS_AXE,
            p -> new Item(p.axe(ModToolTiers.CACTUS, 6, -3.2f)));
    public static final DeferredItem<Item> CACTUS_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.CACTUS_HOE,
            p -> new Item(p.hoe(ModToolTiers.CACTUS, 0, -3f)));
    public static final DeferredItem<Item> CACTUS_HELMET = ITEMS.registerItem(GeneratedRegistrationIds.Items.CACTUS_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.CACTUS_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final DeferredItem<Item> CACTUS_CHESTPLATE = ITEMS.registerItem(GeneratedRegistrationIds.Items.CACTUS_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.CACTUS_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final DeferredItem<Item> CACTUS_LEGGINGS = ITEMS.registerItem(GeneratedRegistrationIds.Items.CACTUS_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.CACTUS_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final DeferredItem<Item> CACTUS_BOOTS = ITEMS.registerItem(GeneratedRegistrationIds.Items.CACTUS_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.CACTUS_ARMOR_MATERIAL, ArmorType.BOOTS)));

    public static final DeferredItem<Item> SPONGE_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.SPONGE_SWORD,
            p -> new Item(p.sword(ModToolTiers.SPONGE, 3, -2.4f)));
    public static final DeferredItem<Item> SPONGE_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.SPONGE_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.SPONGE, 1, -2.8f)));
    public static final DeferredItem<Item> SPONGE_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.SPONGE_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.SPONGE, 1.5f, -3f)));
    public static final DeferredItem<Item> SPONGE_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.SPONGE_AXE,
            p -> new Item(p.axe(ModToolTiers.SPONGE, 6, -3.2f)));
    public static final DeferredItem<Item> SPONGE_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.SPONGE_HOE,
            p -> new Item(p.hoe(ModToolTiers.SPONGE, 0, -3f)));

    public static final DeferredItem<Item> BONE_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.BONE_SWORD,
            p -> new Item(p.sword(ModToolTiers.BONE, 3, -2.4f)));
    public static final DeferredItem<Item> BONE_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.BONE_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.BONE, 1, -2.8f)));
    public static final DeferredItem<Item> BONE_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.BONE_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.BONE, 1.5f, -3f)));
    public static final DeferredItem<Item> BONE_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.BONE_AXE,
            p -> new Item(p.axe(ModToolTiers.BONE, 6, -3.2f)));
    public static final DeferredItem<Item> BONE_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.BONE_HOE,
            p -> new Item(p.hoe(ModToolTiers.BONE, 0, -3f)));
    public static final DeferredItem<Item> BONE_HELMET = ITEMS.registerItem(GeneratedRegistrationIds.Items.BONE_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.BONE_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final DeferredItem<Item> BONE_CHESTPLATE = ITEMS.registerItem(GeneratedRegistrationIds.Items.BONE_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.BONE_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final DeferredItem<Item> BONE_LEGGINGS = ITEMS.registerItem(GeneratedRegistrationIds.Items.BONE_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.BONE_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final DeferredItem<Item> BONE_BOOTS = ITEMS.registerItem(GeneratedRegistrationIds.Items.BONE_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.BONE_ARMOR_MATERIAL, ArmorType.BOOTS)));

    public static final DeferredItem<Item> CLAY_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.CLAY_SWORD,
            p -> new Item(p.sword(ModToolTiers.CLAY, 3, -2.4f)));
    public static final DeferredItem<Item> CLAY_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.CLAY_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.CLAY, 1, -2.8f)));
    public static final DeferredItem<Item> CLAY_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.CLAY_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.CLAY, 1.5f, -3f)));
    public static final DeferredItem<Item> CLAY_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.CLAY_AXE,
            p -> new Item(p.axe(ModToolTiers.CLAY, 6, -3.2f)));
    public static final DeferredItem<Item> CLAY_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.CLAY_HOE,
            p -> new Item(p.hoe(ModToolTiers.CLAY, 0, -3f)));
    public static final DeferredItem<Item> CLAY_HELMET = ITEMS.registerItem(GeneratedRegistrationIds.Items.CLAY_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.CLAY_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final DeferredItem<Item> CLAY_CHESTPLATE = ITEMS.registerItem(GeneratedRegistrationIds.Items.CLAY_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.CLAY_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final DeferredItem<Item> CLAY_LEGGINGS = ITEMS.registerItem(GeneratedRegistrationIds.Items.CLAY_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.CLAY_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final DeferredItem<Item> CLAY_BOOTS = ITEMS.registerItem(GeneratedRegistrationIds.Items.CLAY_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.CLAY_ARMOR_MATERIAL, ArmorType.BOOTS)));

    public static final DeferredItem<Item> NETHER_WART_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.NETHER_WART_SWORD,
            p -> new Item(p.sword(ModToolTiers.NETHER_WART, 3, -2.4f)));
    public static final DeferredItem<Item> NETHER_WART_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.NETHER_WART_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.NETHER_WART, 1, -2.8f)));
    public static final DeferredItem<Item> NETHER_WART_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.NETHER_WART_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.NETHER_WART, 1.5f, -3f)));
    public static final DeferredItem<Item> NETHER_WART_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.NETHER_WART_AXE,
            p -> new Item(p.axe(ModToolTiers.NETHER_WART, 6, -3.2f)));
    public static final DeferredItem<Item> NETHER_WART_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.NETHER_WART_HOE,
            p -> new Item(p.hoe(ModToolTiers.NETHER_WART, 0, -3f)));

    public static final DeferredItem<Item> BRICK_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.BRICK_SWORD,
            p -> new Item(p.sword(ModToolTiers.BRICK, 3, -2.4f)));
    public static final DeferredItem<Item> BRICK_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.BRICK_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.BRICK, 1, -2.8f)));
    public static final DeferredItem<Item> BRICK_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.BRICK_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.BRICK, 1.5f, -3f)));
    public static final DeferredItem<Item> BRICK_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.BRICK_AXE,
            p -> new Item(p.axe(ModToolTiers.BRICK, 6, -3.2f)));
    public static final DeferredItem<Item> BRICK_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.BRICK_HOE,
            p -> new Item(p.hoe(ModToolTiers.BRICK, 0, -3f)));
    public static final DeferredItem<Item> BRICK_HELMET = ITEMS.registerItem(GeneratedRegistrationIds.Items.BRICK_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.BRICK_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final DeferredItem<Item> BRICK_CHESTPLATE = ITEMS.registerItem(GeneratedRegistrationIds.Items.BRICK_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.BRICK_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final DeferredItem<Item> BRICK_LEGGINGS = ITEMS.registerItem(GeneratedRegistrationIds.Items.BRICK_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.BRICK_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final DeferredItem<Item> BRICK_BOOTS = ITEMS.registerItem(GeneratedRegistrationIds.Items.BRICK_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.BRICK_ARMOR_MATERIAL, ArmorType.BOOTS)));

    public static final DeferredItem<Item> NETHER_BRICK_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.NETHER_BRICK_SWORD,
            p -> new Item(p.sword(ModToolTiers.NETHER_BRICK, 3, -2.4f)));
    public static final DeferredItem<Item> NETHER_BRICK_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.NETHER_BRICK_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.NETHER_BRICK, 1, -2.8f)));
    public static final DeferredItem<Item> NETHER_BRICK_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.NETHER_BRICK_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.NETHER_BRICK, 1.5f, -3f)));
    public static final DeferredItem<Item> NETHER_BRICK_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.NETHER_BRICK_AXE,
            p -> new Item(p.axe(ModToolTiers.NETHER_BRICK, 6, -3.2f)));
    public static final DeferredItem<Item> NETHER_BRICK_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.NETHER_BRICK_HOE,
            p -> new Item(p.hoe(ModToolTiers.NETHER_BRICK, 0, -3f)));
    public static final DeferredItem<Item> NETHER_BRICK_HELMET = ITEMS.registerItem(GeneratedRegistrationIds.Items.NETHER_BRICK_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.NETHER_BRICK_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final DeferredItem<Item> NETHER_BRICK_CHESTPLATE = ITEMS.registerItem(GeneratedRegistrationIds.Items.NETHER_BRICK_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.NETHER_BRICK_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final DeferredItem<Item> NETHER_BRICK_LEGGINGS = ITEMS.registerItem(GeneratedRegistrationIds.Items.NETHER_BRICK_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.NETHER_BRICK_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final DeferredItem<Item> NETHER_BRICK_BOOTS = ITEMS.registerItem(GeneratedRegistrationIds.Items.NETHER_BRICK_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.NETHER_BRICK_ARMOR_MATERIAL, ArmorType.BOOTS)));

    public static final DeferredItem<Item> POINTED_DRIPSTONE_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.POINTED_DRIPSTONE_SWORD,
            p -> new Item(p.sword(ModToolTiers.POINTED_DRIPSTONE, 3, -2.4f)));
    public static final DeferredItem<Item> POINTED_DRIPSTONE_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.POINTED_DRIPSTONE_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.POINTED_DRIPSTONE, 1, -2.8f)));
    public static final DeferredItem<Item> POINTED_DRIPSTONE_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.POINTED_DRIPSTONE_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.POINTED_DRIPSTONE, 1.5f, -3f)));
    public static final DeferredItem<Item> POINTED_DRIPSTONE_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.POINTED_DRIPSTONE_AXE,
            p -> new Item(p.axe(ModToolTiers.POINTED_DRIPSTONE, 6, -3.2f)));
    public static final DeferredItem<Item> POINTED_DRIPSTONE_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.POINTED_DRIPSTONE_HOE,
            p -> new Item(p.hoe(ModToolTiers.POINTED_DRIPSTONE, 0, -3f)));

    public static final DeferredItem<Item> COPPER_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.COPPER_SWORD,
            p -> new Item(p.sword(ModToolTiers.COPPER, 3, -2.4f)));
    public static final DeferredItem<Item> COPPER_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.COPPER_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.COPPER, 1, -2.8f)));
    public static final DeferredItem<Item> COPPER_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.COPPER_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.COPPER, 1.5f, -3f)));
    public static final DeferredItem<Item> COPPER_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.COPPER_AXE,
            p -> new Item(p.axe(ModToolTiers.COPPER, 6, -3.2f)));
    public static final DeferredItem<Item> COPPER_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.COPPER_HOE,
            p -> new Item(p.hoe(ModToolTiers.COPPER, 0, -3f)));
    public static final DeferredItem<Item> COPPER_HELMET = ITEMS.registerItem(GeneratedRegistrationIds.Items.COPPER_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.COPPER_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final DeferredItem<Item> COPPER_CHESTPLATE = ITEMS.registerItem(GeneratedRegistrationIds.Items.COPPER_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.COPPER_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final DeferredItem<Item> COPPER_LEGGINGS = ITEMS.registerItem(GeneratedRegistrationIds.Items.COPPER_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.COPPER_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final DeferredItem<Item> COPPER_BOOTS = ITEMS.registerItem(GeneratedRegistrationIds.Items.COPPER_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.COPPER_ARMOR_MATERIAL, ArmorType.BOOTS)));

    public static final DeferredItem<Item> PHANTOM_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.PHANTOM_SWORD,
            p -> new Item(p.sword(ModToolTiers.PHANTOM_MEMBRANE, 3, -2.4f)));
    public static final DeferredItem<Item> PHANTOM_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.PHANTOM_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.PHANTOM_MEMBRANE, 1, -2.8f)));
    public static final DeferredItem<Item> PHANTOM_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.PHANTOM_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.PHANTOM_MEMBRANE, 1.5f, -3f)));
    public static final DeferredItem<Item> PHANTOM_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.PHANTOM_AXE,
            p -> new Item(p.axe(ModToolTiers.PHANTOM_MEMBRANE, 6, -3.2f)));
    public static final DeferredItem<Item> PHANTOM_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.PHANTOM_HOE,
            p -> new Item(p.hoe(ModToolTiers.PHANTOM_MEMBRANE, 0, -3f)));
    public static final DeferredItem<Item> PHANTOM_HELMET = ITEMS.registerItem(GeneratedRegistrationIds.Items.PHANTOM_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.PHANTOM_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final DeferredItem<Item> PHANTOM_CHESTPLATE = ITEMS.registerItem(GeneratedRegistrationIds.Items.PHANTOM_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.PHANTOM_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final DeferredItem<Item> PHANTOM_LEGGINGS = ITEMS.registerItem(GeneratedRegistrationIds.Items.PHANTOM_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.PHANTOM_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final DeferredItem<Item> PHANTOM_BOOTS = ITEMS.registerItem(GeneratedRegistrationIds.Items.PHANTOM_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.PHANTOM_ARMOR_MATERIAL, ArmorType.BOOTS)));

    public static final DeferredItem<Item> MAGMA_CREAM_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.MAGMA_CREAM_SWORD,
            p -> new Item(p.sword(ModToolTiers.MAGMA_CREAM, 3, -2.4f)));
    public static final DeferredItem<Item> MAGMA_CREAM_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.MAGMA_CREAM_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.MAGMA_CREAM, 1, -2.8f)));
    public static final DeferredItem<Item> MAGMA_CREAM_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.MAGMA_CREAM_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.MAGMA_CREAM, 1.5f, -3f)));
    public static final DeferredItem<Item> MAGMA_CREAM_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.MAGMA_CREAM_AXE,
            p -> new Item(p.axe(ModToolTiers.MAGMA_CREAM, 6, -3.2f)));
    public static final DeferredItem<Item> MAGMA_CREAM_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.MAGMA_CREAM_HOE,
            p -> new Item(p.hoe(ModToolTiers.MAGMA_CREAM, 0, -3f)));
    public static final DeferredItem<Item> MAGMA_CREAM_HELMET = ITEMS.registerItem(GeneratedRegistrationIds.Items.MAGMA_CREAM_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.MAGMA_CREAM_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final DeferredItem<Item> MAGMA_CREAM_CHESTPLATE = ITEMS.registerItem(GeneratedRegistrationIds.Items.MAGMA_CREAM_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.MAGMA_CREAM_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final DeferredItem<Item> MAGMA_CREAM_LEGGINGS = ITEMS.registerItem(GeneratedRegistrationIds.Items.MAGMA_CREAM_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.MAGMA_CREAM_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final DeferredItem<Item> MAGMA_CREAM_BOOTS = ITEMS.registerItem(GeneratedRegistrationIds.Items.MAGMA_CREAM_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.MAGMA_CREAM_ARMOR_MATERIAL, ArmorType.BOOTS)));

    public static final DeferredItem<Item> SLIME_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.SLIME_SWORD,
            p -> new Item(p.sword(ModToolTiers.SLIME, 3, -2.4f)));
    public static final DeferredItem<Item> SLIME_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.SLIME_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.SLIME, 1, -2.8f)));
    public static final DeferredItem<Item> SLIME_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.SLIME_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.SLIME, 1.5f, -3f)));
    public static final DeferredItem<Item> SLIME_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.SLIME_AXE,
            p -> new Item(p.axe(ModToolTiers.SLIME, 6, -3.2f)));
    public static final DeferredItem<Item> SLIME_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.SLIME_HOE,
            p -> new Item(p.hoe(ModToolTiers.SLIME, 0, -3f)));
    public static final DeferredItem<Item> SLIME_HELMET = ITEMS.registerItem(GeneratedRegistrationIds.Items.SLIME_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.SLIME_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final DeferredItem<Item> SLIME_CHESTPLATE = ITEMS.registerItem(GeneratedRegistrationIds.Items.SLIME_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.SLIME_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final DeferredItem<Item> SLIME_LEGGINGS = ITEMS.registerItem(GeneratedRegistrationIds.Items.SLIME_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.SLIME_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final DeferredItem<Item> SLIME_BOOTS = ITEMS.registerItem(GeneratedRegistrationIds.Items.SLIME_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.SLIME_ARMOR_MATERIAL, ArmorType.BOOTS)));

    public static final DeferredItem<Item> BLAZE_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.BLAZE_SWORD,
            p -> new Item(p.sword(ModToolTiers.BLAZE_ROD, 3, -2.4f)));
    public static final DeferredItem<Item> BLAZE_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.BLAZE_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.BLAZE_ROD, 1, -2.8f)));
    public static final DeferredItem<Item> BLAZE_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.BLAZE_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.BLAZE_ROD, 1.5f, -3f)));
    public static final DeferredItem<Item> BLAZE_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.BLAZE_AXE,
            p -> new Item(p.axe(ModToolTiers.BLAZE_ROD, 6, -3.2f)));
    public static final DeferredItem<Item> BLAZE_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.BLAZE_HOE,
            p -> new Item(p.hoe(ModToolTiers.BLAZE_ROD, 0, -3f)));
    public static final DeferredItem<Item> BLAZE_HELMET = ITEMS.registerItem(GeneratedRegistrationIds.Items.BLAZE_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.BLAZE_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final DeferredItem<Item> BLAZE_CHESTPLATE = ITEMS.registerItem(GeneratedRegistrationIds.Items.BLAZE_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.BLAZE_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final DeferredItem<Item> BLAZE_LEGGINGS = ITEMS.registerItem(GeneratedRegistrationIds.Items.BLAZE_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.BLAZE_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final DeferredItem<Item> BLAZE_BOOTS = ITEMS.registerItem(GeneratedRegistrationIds.Items.BLAZE_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.BLAZE_ARMOR_MATERIAL, ArmorType.BOOTS)));

    public static final DeferredItem<Item> NAUTILUS_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.NAUTILUS_SWORD,
            p -> new Item(p.sword(ModToolTiers.NAUTILUS_SHELL, 3, -2.4f)));
    public static final DeferredItem<Item> NAUTILUS_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.NAUTILUS_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.NAUTILUS_SHELL, 1, -2.8f)));
    public static final DeferredItem<Item> NAUTILUS_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.NAUTILUS_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.NAUTILUS_SHELL, 1.5f, -3f)));
    public static final DeferredItem<Item> NAUTILUS_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.NAUTILUS_AXE,
            p -> new Item(p.axe(ModToolTiers.NAUTILUS_SHELL, 6, -3.2f)));
    public static final DeferredItem<Item> NAUTILUS_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.NAUTILUS_HOE,
            p -> new Item(p.hoe(ModToolTiers.NAUTILUS_SHELL, 0, -3f)));
    public static final DeferredItem<Item> NAUTILUS_HELMET = ITEMS.registerItem(GeneratedRegistrationIds.Items.NAUTILUS_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.NAUTILUS_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final DeferredItem<Item> NAUTILUS_CHESTPLATE = ITEMS.registerItem(GeneratedRegistrationIds.Items.NAUTILUS_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.NAUTILUS_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final DeferredItem<Item> NAUTILUS_LEGGINGS = ITEMS.registerItem(GeneratedRegistrationIds.Items.NAUTILUS_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.NAUTILUS_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final DeferredItem<Item> NAUTILUS_BOOTS = ITEMS.registerItem(GeneratedRegistrationIds.Items.NAUTILUS_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.NAUTILUS_ARMOR_MATERIAL, ArmorType.BOOTS)));

    public static final DeferredItem<Item> PURPUR_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.PURPUR_SWORD,
            p -> new Item(p.sword(ModToolTiers.PURPUR, 3, -2.4f)));
    public static final DeferredItem<Item> PURPUR_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.PURPUR_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.PURPUR, 1, -2.8f)));
    public static final DeferredItem<Item> PURPUR_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.PURPUR_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.PURPUR, 1.5f, -3f)));
    public static final DeferredItem<Item> PURPUR_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.PURPUR_AXE,
            p -> new Item(p.axe(ModToolTiers.PURPUR, 6, -3.2f)));
    public static final DeferredItem<Item> PURPUR_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.PURPUR_HOE,
            p -> new Item(p.hoe(ModToolTiers.PURPUR, 0, -3f)));
    public static final DeferredItem<Item> PURPUR_HELMET = ITEMS.registerItem(GeneratedRegistrationIds.Items.PURPUR_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.PURPUR_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final DeferredItem<Item> PURPUR_CHESTPLATE = ITEMS.registerItem(GeneratedRegistrationIds.Items.PURPUR_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.PURPUR_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final DeferredItem<Item> PURPUR_LEGGINGS = ITEMS.registerItem(GeneratedRegistrationIds.Items.PURPUR_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.PURPUR_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final DeferredItem<Item> PURPUR_BOOTS = ITEMS.registerItem(GeneratedRegistrationIds.Items.PURPUR_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.PURPUR_ARMOR_MATERIAL, ArmorType.BOOTS)));

    public static final DeferredItem<Item> GHAST_TEAR_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.GHAST_TEAR_SWORD,
            p -> new Item(p.sword(ModToolTiers.GHAST_TEAR, 3, -2.4f)));
    public static final DeferredItem<Item> GHAST_TEAR_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.GHAST_TEAR_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.GHAST_TEAR, 1, -2.8f)));
    public static final DeferredItem<Item> GHAST_TEAR_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.GHAST_TEAR_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.GHAST_TEAR, 1.5f, -3f)));
    public static final DeferredItem<Item> GHAST_TEAR_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.GHAST_TEAR_AXE,
            p -> new Item(p.axe(ModToolTiers.GHAST_TEAR, 6, -3.2f)));
    public static final DeferredItem<Item> GHAST_TEAR_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.GHAST_TEAR_HOE,
            p -> new Item(p.hoe(ModToolTiers.GHAST_TEAR, 0, -3f)));
    public static final DeferredItem<Item> GHAST_TEAR_HELMET = ITEMS.registerItem(GeneratedRegistrationIds.Items.GHAST_TEAR_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.GHAST_TEAR_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final DeferredItem<Item> GHAST_TEAR_CHESTPLATE = ITEMS.registerItem(GeneratedRegistrationIds.Items.GHAST_TEAR_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.GHAST_TEAR_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final DeferredItem<Item> GHAST_TEAR_LEGGINGS = ITEMS.registerItem(GeneratedRegistrationIds.Items.GHAST_TEAR_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.GHAST_TEAR_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final DeferredItem<Item> GHAST_TEAR_BOOTS = ITEMS.registerItem(GeneratedRegistrationIds.Items.GHAST_TEAR_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.GHAST_TEAR_ARMOR_MATERIAL, ArmorType.BOOTS)));

    public static final DeferredItem<Item> EYE_OF_ENDER_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.EYE_OF_ENDER_SWORD,
            p -> new Item(p.sword(ModToolTiers.EYE_OF_ENDER, 3, -2.4f)));
    public static final DeferredItem<Item> EYE_OF_ENDER_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.EYE_OF_ENDER_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.EYE_OF_ENDER, 1, -2.8f)));
    public static final DeferredItem<Item> EYE_OF_ENDER_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.EYE_OF_ENDER_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.EYE_OF_ENDER, 1.5f, -3f)));
    public static final DeferredItem<Item> EYE_OF_ENDER_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.EYE_OF_ENDER_AXE,
            p -> new Item(p.axe(ModToolTiers.EYE_OF_ENDER, 6, -3.2f)));
    public static final DeferredItem<Item> EYE_OF_ENDER_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.EYE_OF_ENDER_HOE,
            p -> new Item(p.hoe(ModToolTiers.EYE_OF_ENDER, 0, -3f)));
    public static final DeferredItem<Item> EYE_OF_ENDER_HELMET = ITEMS.registerItem(GeneratedRegistrationIds.Items.EYE_OF_ENDER_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.EYE_OF_ENDER_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final DeferredItem<Item> EYE_OF_ENDER_CHESTPLATE = ITEMS.registerItem(GeneratedRegistrationIds.Items.EYE_OF_ENDER_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.EYE_OF_ENDER_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final DeferredItem<Item> EYE_OF_ENDER_LEGGINGS = ITEMS.registerItem(GeneratedRegistrationIds.Items.EYE_OF_ENDER_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.EYE_OF_ENDER_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final DeferredItem<Item> EYE_OF_ENDER_BOOTS = ITEMS.registerItem(GeneratedRegistrationIds.Items.EYE_OF_ENDER_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.EYE_OF_ENDER_ARMOR_MATERIAL, ArmorType.BOOTS)));

    public static final DeferredItem<Item> SHULKER_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.SHULKER_SWORD,
            p -> new Item(p.sword(ModToolTiers.SHULKER_SHELL, 3, -2.4f)));
    public static final DeferredItem<Item> SHULKER_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.SHULKER_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.SHULKER_SHELL, 1, -2.8f)));
    public static final DeferredItem<Item> SHULKER_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.SHULKER_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.SHULKER_SHELL, 1.5f, -3f)));
    public static final DeferredItem<Item> SHULKER_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.SHULKER_AXE,
            p -> new Item(p.axe(ModToolTiers.SHULKER_SHELL, 6, -3.2f)));
    public static final DeferredItem<Item> SHULKER_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.SHULKER_HOE,
            p -> new Item(p.hoe(ModToolTiers.SHULKER_SHELL, 0, -3f)));
    public static final DeferredItem<Item> SHULKER_HELMET = ITEMS.registerItem(GeneratedRegistrationIds.Items.SHULKER_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.SHULKER_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final DeferredItem<Item> SHULKER_CHESTPLATE = ITEMS.registerItem(GeneratedRegistrationIds.Items.SHULKER_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.SHULKER_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final DeferredItem<Item> SHULKER_LEGGINGS = ITEMS.registerItem(GeneratedRegistrationIds.Items.SHULKER_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.SHULKER_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final DeferredItem<Item> SHULKER_BOOTS = ITEMS.registerItem(GeneratedRegistrationIds.Items.SHULKER_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.SHULKER_ARMOR_MATERIAL, ArmorType.BOOTS)));

    public static final DeferredItem<Item> TURTLE_SCUTE_HELMET = ITEMS.registerItem(GeneratedRegistrationIds.Items.TURTLE_SCUTE_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.TURTLE_SCUTE_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final DeferredItem<Item> TURTLE_SCUTE_CHESTPLATE = ITEMS.registerItem(GeneratedRegistrationIds.Items.TURTLE_SCUTE_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.TURTLE_SCUTE_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final DeferredItem<Item> TURTLE_SCUTE_LEGGINGS = ITEMS.registerItem(GeneratedRegistrationIds.Items.TURTLE_SCUTE_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.TURTLE_SCUTE_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final DeferredItem<Item> TURTLE_SCUTE_BOOTS = ITEMS.registerItem(GeneratedRegistrationIds.Items.TURTLE_SCUTE_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.TURTLE_SCUTE_ARMOR_MATERIAL, ArmorType.BOOTS)));

    public static final DeferredItem<Item> ECHO_SHARD_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.ECHO_SHARD_SWORD,
            p -> new Item(p.sword(ModToolTiers.ECHO_SHARD, 3, -2.4f)));
    public static final DeferredItem<Item> ECHO_SHARD_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.ECHO_SHARD_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.ECHO_SHARD, 1, -2.8f)));
    public static final DeferredItem<Item> ECHO_SHARD_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.ECHO_SHARD_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.ECHO_SHARD, 1.5f, -3f)));
    public static final DeferredItem<Item> ECHO_SHARD_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.ECHO_SHARD_AXE,
            p -> new Item(p.axe(ModToolTiers.ECHO_SHARD, 6, -3.2f)));
    public static final DeferredItem<Item> ECHO_SHARD_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.ECHO_SHARD_HOE,
            p -> new Item(p.hoe(ModToolTiers.ECHO_SHARD, 0, -3f)));
    public static final DeferredItem<Item> ECHO_SHARD_HELMET = ITEMS.registerItem(GeneratedRegistrationIds.Items.ECHO_SHARD_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.ECHO_SHARD_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final DeferredItem<Item> ECHO_SHARD_CHESTPLATE = ITEMS.registerItem(GeneratedRegistrationIds.Items.ECHO_SHARD_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.ECHO_SHARD_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final DeferredItem<Item> ECHO_SHARD_LEGGINGS = ITEMS.registerItem(GeneratedRegistrationIds.Items.ECHO_SHARD_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.ECHO_SHARD_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final DeferredItem<Item> ECHO_SHARD_BOOTS = ITEMS.registerItem(GeneratedRegistrationIds.Items.ECHO_SHARD_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.ECHO_SHARD_ARMOR_MATERIAL, ArmorType.BOOTS)));

    public static final DeferredItem<Item> DRAGON_BREATH_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.DRAGON_BREATH_SWORD,
            p -> new Item(p.sword(ModToolTiers.DRAGON_BREATH, 3, -2.4f)));
    public static final DeferredItem<Item> DRAGON_BREATH_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.DRAGON_BREATH_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.DRAGON_BREATH, 1, -2.8f)));
    public static final DeferredItem<Item> DRAGON_BREATH_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.DRAGON_BREATH_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.DRAGON_BREATH, 1.5f, -3f)));
    public static final DeferredItem<Item> DRAGON_BREATH_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.DRAGON_BREATH_AXE,
            p -> new Item(p.axe(ModToolTiers.DRAGON_BREATH, 6, -3.2f)));
    public static final DeferredItem<Item> DRAGON_BREATH_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.DRAGON_BREATH_HOE,
            p -> new Item(p.hoe(ModToolTiers.DRAGON_BREATH, 0, -3f)));
    public static final DeferredItem<Item> DRAGON_BREATH_HELMET = ITEMS.registerItem(GeneratedRegistrationIds.Items.DRAGON_BREATH_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.DRAGON_BREATH_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final DeferredItem<Item> DRAGON_BREATH_CHESTPLATE = ITEMS.registerItem(GeneratedRegistrationIds.Items.DRAGON_BREATH_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.DRAGON_BREATH_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final DeferredItem<Item> DRAGON_BREATH_LEGGINGS = ITEMS.registerItem(GeneratedRegistrationIds.Items.DRAGON_BREATH_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.DRAGON_BREATH_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final DeferredItem<Item> DRAGON_BREATH_BOOTS = ITEMS.registerItem(GeneratedRegistrationIds.Items.DRAGON_BREATH_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.DRAGON_BREATH_ARMOR_MATERIAL, ArmorType.BOOTS)));

    // ── Edible food-themed sets ────────────────────────────────────────────
    // Cake
    public static final DeferredItem<Item> CAKE_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.CAKE_SWORD,
            p -> new EdibleSwordItem(p.sword(ModToolTiers.CAKE, 3, -2.4f).food(food(4))));
    public static final DeferredItem<Item> CAKE_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.CAKE_PICKAXE,
            p -> new EdiblePickaxeItem(p.pickaxe(ModToolTiers.CAKE, 1, -2.8f).food(food(6))));
    public static final DeferredItem<Item> CAKE_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.CAKE_SHOVEL,
            p -> new EdibleShovelItem(p.shovel(ModToolTiers.CAKE, 1.5f, -3f).food(food(2))));
    public static final DeferredItem<Item> CAKE_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.CAKE_AXE,
            p -> new EdibleAxeItem(p.axe(ModToolTiers.CAKE, 6, -3.2f).food(food(6))));
    public static final DeferredItem<Item> CAKE_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.CAKE_HOE,
            p -> new EdibleHoeItem(p.hoe(ModToolTiers.CAKE, 0, -3f).food(food(4))));
    public static final DeferredItem<Item> CAKE_HELMET = ITEMS.registerItem(GeneratedRegistrationIds.Items.CAKE_HELMET,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.CAKE_ARMOR_MATERIAL, ArmorType.HELMET).food(food(10))));
    public static final DeferredItem<Item> CAKE_CHESTPLATE = ITEMS.registerItem(GeneratedRegistrationIds.Items.CAKE_CHESTPLATE,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.CAKE_ARMOR_MATERIAL, ArmorType.CHESTPLATE).food(food(14))));
    public static final DeferredItem<Item> CAKE_LEGGINGS = ITEMS.registerItem(GeneratedRegistrationIds.Items.CAKE_LEGGINGS,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.CAKE_ARMOR_MATERIAL, ArmorType.LEGGINGS).food(food(14))));
    public static final DeferredItem<Item> CAKE_BOOTS = ITEMS.registerItem(GeneratedRegistrationIds.Items.CAKE_BOOTS,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.CAKE_ARMOR_MATERIAL, ArmorType.BOOTS).food(food(8))));

    // Bread
    public static final DeferredItem<Item> BREAD_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.BREAD_SWORD,
            p -> new EdibleSwordItem(p.sword(ModToolTiers.BREAD, 3, -2.4f).food(food(4))));
    public static final DeferredItem<Item> BREAD_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.BREAD_PICKAXE,
            p -> new EdiblePickaxeItem(p.pickaxe(ModToolTiers.BREAD, 1, -2.8f).food(food(6))));
    public static final DeferredItem<Item> BREAD_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.BREAD_SHOVEL,
            p -> new EdibleShovelItem(p.shovel(ModToolTiers.BREAD, 1.5f, -3f).food(food(2))));
    public static final DeferredItem<Item> BREAD_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.BREAD_AXE,
            p -> new EdibleAxeItem(p.axe(ModToolTiers.BREAD, 6, -3.2f).food(food(6))));
    public static final DeferredItem<Item> BREAD_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.BREAD_HOE,
            p -> new EdibleHoeItem(p.hoe(ModToolTiers.BREAD, 0, -3f).food(food(4))));
    public static final DeferredItem<Item> BREAD_HELMET = ITEMS.registerItem(GeneratedRegistrationIds.Items.BREAD_HELMET,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.BREAD_ARMOR_MATERIAL, ArmorType.HELMET).food(food(10))));
    public static final DeferredItem<Item> BREAD_CHESTPLATE = ITEMS.registerItem(GeneratedRegistrationIds.Items.BREAD_CHESTPLATE,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.BREAD_ARMOR_MATERIAL, ArmorType.CHESTPLATE).food(food(14))));
    public static final DeferredItem<Item> BREAD_LEGGINGS = ITEMS.registerItem(GeneratedRegistrationIds.Items.BREAD_LEGGINGS,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.BREAD_ARMOR_MATERIAL, ArmorType.LEGGINGS).food(food(14))));
    public static final DeferredItem<Item> BREAD_BOOTS = ITEMS.registerItem(GeneratedRegistrationIds.Items.BREAD_BOOTS,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.BREAD_ARMOR_MATERIAL, ArmorType.BOOTS).food(food(8))));

    // Dried Kelp
    public static final DeferredItem<Item> DRIED_KELP_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.DRIED_KELP_SWORD,
            p -> new EdibleSwordItem(p.sword(ModToolTiers.DRIED_KELP, 3, -2.4f).food(food(4))));
    public static final DeferredItem<Item> DRIED_KELP_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.DRIED_KELP_PICKAXE,
            p -> new EdiblePickaxeItem(p.pickaxe(ModToolTiers.DRIED_KELP, 1, -2.8f).food(food(6))));
    public static final DeferredItem<Item> DRIED_KELP_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.DRIED_KELP_SHOVEL,
            p -> new EdibleShovelItem(p.shovel(ModToolTiers.DRIED_KELP, 1.5f, -3f).food(food(2))));
    public static final DeferredItem<Item> DRIED_KELP_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.DRIED_KELP_AXE,
            p -> new EdibleAxeItem(p.axe(ModToolTiers.DRIED_KELP, 6, -3.2f).food(food(6))));
    public static final DeferredItem<Item> DRIED_KELP_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.DRIED_KELP_HOE,
            p -> new EdibleHoeItem(p.hoe(ModToolTiers.DRIED_KELP, 0, -3f).food(food(4))));
    public static final DeferredItem<Item> DRIED_KELP_HELMET = ITEMS.registerItem(GeneratedRegistrationIds.Items.DRIED_KELP_HELMET,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.DRIED_KELP_ARMOR_MATERIAL, ArmorType.HELMET).food(food(10))));
    public static final DeferredItem<Item> DRIED_KELP_CHESTPLATE = ITEMS.registerItem(GeneratedRegistrationIds.Items.DRIED_KELP_CHESTPLATE,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.DRIED_KELP_ARMOR_MATERIAL, ArmorType.CHESTPLATE).food(food(14))));
    public static final DeferredItem<Item> DRIED_KELP_LEGGINGS = ITEMS.registerItem(GeneratedRegistrationIds.Items.DRIED_KELP_LEGGINGS,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.DRIED_KELP_ARMOR_MATERIAL, ArmorType.LEGGINGS).food(food(14))));
    public static final DeferredItem<Item> DRIED_KELP_BOOTS = ITEMS.registerItem(GeneratedRegistrationIds.Items.DRIED_KELP_BOOTS,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.DRIED_KELP_ARMOR_MATERIAL, ArmorType.BOOTS).food(food(8))));

    // Rotten Flesh
    public static final DeferredItem<Item> ROTTEN_FLESH_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.ROTTEN_FLESH_SWORD,
            p -> new EdibleSwordItem(p.sword(ModToolTiers.ROTTEN_FLESH, 3, -2.4f).food(food(4))));
    public static final DeferredItem<Item> ROTTEN_FLESH_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.ROTTEN_FLESH_PICKAXE,
            p -> new EdiblePickaxeItem(p.pickaxe(ModToolTiers.ROTTEN_FLESH, 1, -2.8f).food(food(6))));
    public static final DeferredItem<Item> ROTTEN_FLESH_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.ROTTEN_FLESH_SHOVEL,
            p -> new EdibleShovelItem(p.shovel(ModToolTiers.ROTTEN_FLESH, 1.5f, -3f).food(food(2))));
    public static final DeferredItem<Item> ROTTEN_FLESH_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.ROTTEN_FLESH_AXE,
            p -> new EdibleAxeItem(p.axe(ModToolTiers.ROTTEN_FLESH, 6, -3.2f).food(food(6))));
    public static final DeferredItem<Item> ROTTEN_FLESH_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.ROTTEN_FLESH_HOE,
            p -> new EdibleHoeItem(p.hoe(ModToolTiers.ROTTEN_FLESH, 0, -3f).food(food(4))));
    public static final DeferredItem<Item> ROTTEN_FLESH_HELMET = ITEMS.registerItem(GeneratedRegistrationIds.Items.ROTTEN_FLESH_HELMET,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.ROTTEN_FLESH_ARMOR_MATERIAL, ArmorType.HELMET).food(food(10))));
    public static final DeferredItem<Item> ROTTEN_FLESH_CHESTPLATE = ITEMS.registerItem(GeneratedRegistrationIds.Items.ROTTEN_FLESH_CHESTPLATE,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.ROTTEN_FLESH_ARMOR_MATERIAL, ArmorType.CHESTPLATE).food(food(14))));
    public static final DeferredItem<Item> ROTTEN_FLESH_LEGGINGS = ITEMS.registerItem(GeneratedRegistrationIds.Items.ROTTEN_FLESH_LEGGINGS,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.ROTTEN_FLESH_ARMOR_MATERIAL, ArmorType.LEGGINGS).food(food(14))));
    public static final DeferredItem<Item> ROTTEN_FLESH_BOOTS = ITEMS.registerItem(GeneratedRegistrationIds.Items.ROTTEN_FLESH_BOOTS,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.ROTTEN_FLESH_ARMOR_MATERIAL, ArmorType.BOOTS).food(food(8))));

    // Melon
    public static final DeferredItem<Item> MELON_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.MELON_SWORD,
            p -> new EdibleSwordItem(p.sword(ModToolTiers.MELON, 3, -2.4f).food(food(4))));
    public static final DeferredItem<Item> MELON_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.MELON_PICKAXE,
            p -> new EdiblePickaxeItem(p.pickaxe(ModToolTiers.MELON, 1, -2.8f).food(food(6))));
    public static final DeferredItem<Item> MELON_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.MELON_SHOVEL,
            p -> new EdibleShovelItem(p.shovel(ModToolTiers.MELON, 1.5f, -3f).food(food(2))));
    public static final DeferredItem<Item> MELON_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.MELON_AXE,
            p -> new EdibleAxeItem(p.axe(ModToolTiers.MELON, 6, -3.2f).food(food(6))));
    public static final DeferredItem<Item> MELON_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.MELON_HOE,
            p -> new EdibleHoeItem(p.hoe(ModToolTiers.MELON, 0, -3f).food(food(4))));
    public static final DeferredItem<Item> MELON_HELMET = ITEMS.registerItem(GeneratedRegistrationIds.Items.MELON_HELMET,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.MELON_ARMOR_MATERIAL, ArmorType.HELMET).food(food(10))));
    public static final DeferredItem<Item> MELON_CHESTPLATE = ITEMS.registerItem(GeneratedRegistrationIds.Items.MELON_CHESTPLATE,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.MELON_ARMOR_MATERIAL, ArmorType.CHESTPLATE).food(food(14))));
    public static final DeferredItem<Item> MELON_LEGGINGS = ITEMS.registerItem(GeneratedRegistrationIds.Items.MELON_LEGGINGS,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.MELON_ARMOR_MATERIAL, ArmorType.LEGGINGS).food(food(14))));
    public static final DeferredItem<Item> MELON_BOOTS = ITEMS.registerItem(GeneratedRegistrationIds.Items.MELON_BOOTS,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.MELON_ARMOR_MATERIAL, ArmorType.BOOTS).food(food(8))));

    // Sweet Berries
    public static final DeferredItem<Item> SWEET_BERRY_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.SWEET_BERRY_SWORD,
            p -> new EdibleSwordItem(p.sword(ModToolTiers.SWEET_BERRIES, 3, -2.4f).food(food(4))));
    public static final DeferredItem<Item> SWEET_BERRY_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.SWEET_BERRY_PICKAXE,
            p -> new EdiblePickaxeItem(p.pickaxe(ModToolTiers.SWEET_BERRIES, 1, -2.8f).food(food(6))));
    public static final DeferredItem<Item> SWEET_BERRY_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.SWEET_BERRY_SHOVEL,
            p -> new EdibleShovelItem(p.shovel(ModToolTiers.SWEET_BERRIES, 1.5f, -3f).food(food(2))));
    public static final DeferredItem<Item> SWEET_BERRY_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.SWEET_BERRY_AXE,
            p -> new EdibleAxeItem(p.axe(ModToolTiers.SWEET_BERRIES, 6, -3.2f).food(food(6))));
    public static final DeferredItem<Item> SWEET_BERRY_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.SWEET_BERRY_HOE,
            p -> new EdibleHoeItem(p.hoe(ModToolTiers.SWEET_BERRIES, 0, -3f).food(food(4))));
    public static final DeferredItem<Item> SWEET_BERRY_HELMET = ITEMS.registerItem(GeneratedRegistrationIds.Items.SWEET_BERRY_HELMET,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.SWEET_BERRY_ARMOR_MATERIAL, ArmorType.HELMET).food(food(10))));
    public static final DeferredItem<Item> SWEET_BERRY_CHESTPLATE = ITEMS.registerItem(GeneratedRegistrationIds.Items.SWEET_BERRY_CHESTPLATE,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.SWEET_BERRY_ARMOR_MATERIAL, ArmorType.CHESTPLATE).food(food(14))));
    public static final DeferredItem<Item> SWEET_BERRY_LEGGINGS = ITEMS.registerItem(GeneratedRegistrationIds.Items.SWEET_BERRY_LEGGINGS,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.SWEET_BERRY_ARMOR_MATERIAL, ArmorType.LEGGINGS).food(food(14))));
    public static final DeferredItem<Item> SWEET_BERRY_BOOTS = ITEMS.registerItem(GeneratedRegistrationIds.Items.SWEET_BERRY_BOOTS,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.SWEET_BERRY_ARMOR_MATERIAL, ArmorType.BOOTS).food(food(8))));

    // Pumpkin Pie
    public static final DeferredItem<Item> PUMPKIN_PIE_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.PUMPKIN_PIE_SWORD,
            p -> new EdibleSwordItem(p.sword(ModToolTiers.PUMPKIN_PIE, 3, -2.4f).food(food(4))));
    public static final DeferredItem<Item> PUMPKIN_PIE_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.PUMPKIN_PIE_PICKAXE,
            p -> new EdiblePickaxeItem(p.pickaxe(ModToolTiers.PUMPKIN_PIE, 1, -2.8f).food(food(6))));
    public static final DeferredItem<Item> PUMPKIN_PIE_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.PUMPKIN_PIE_SHOVEL,
            p -> new EdibleShovelItem(p.shovel(ModToolTiers.PUMPKIN_PIE, 1.5f, -3f).food(food(2))));
    public static final DeferredItem<Item> PUMPKIN_PIE_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.PUMPKIN_PIE_AXE,
            p -> new EdibleAxeItem(p.axe(ModToolTiers.PUMPKIN_PIE, 6, -3.2f).food(food(6))));
    public static final DeferredItem<Item> PUMPKIN_PIE_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.PUMPKIN_PIE_HOE,
            p -> new EdibleHoeItem(p.hoe(ModToolTiers.PUMPKIN_PIE, 0, -3f).food(food(4))));
    public static final DeferredItem<Item> PUMPKIN_PIE_HELMET = ITEMS.registerItem(GeneratedRegistrationIds.Items.PUMPKIN_PIE_HELMET,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.PUMPKIN_PIE_ARMOR_MATERIAL, ArmorType.HELMET).food(food(10))));
    public static final DeferredItem<Item> PUMPKIN_PIE_CHESTPLATE = ITEMS.registerItem(GeneratedRegistrationIds.Items.PUMPKIN_PIE_CHESTPLATE,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.PUMPKIN_PIE_ARMOR_MATERIAL, ArmorType.CHESTPLATE).food(food(14))));
    public static final DeferredItem<Item> PUMPKIN_PIE_LEGGINGS = ITEMS.registerItem(GeneratedRegistrationIds.Items.PUMPKIN_PIE_LEGGINGS,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.PUMPKIN_PIE_ARMOR_MATERIAL, ArmorType.LEGGINGS).food(food(14))));
    public static final DeferredItem<Item> PUMPKIN_PIE_BOOTS = ITEMS.registerItem(GeneratedRegistrationIds.Items.PUMPKIN_PIE_BOOTS,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.PUMPKIN_PIE_ARMOR_MATERIAL, ArmorType.BOOTS).food(food(8))));

    // Mushroom
    public static final DeferredItem<Item> MUSHROOM_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.MUSHROOM_SWORD,
            p -> new EdibleSwordItem(p.sword(ModToolTiers.MUSHROOM, 3, -2.4f).food(food(4))));
    public static final DeferredItem<Item> MUSHROOM_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.MUSHROOM_PICKAXE,
            p -> new EdiblePickaxeItem(p.pickaxe(ModToolTiers.MUSHROOM, 1, -2.8f).food(food(6))));
    public static final DeferredItem<Item> MUSHROOM_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.MUSHROOM_SHOVEL,
            p -> new EdibleShovelItem(p.shovel(ModToolTiers.MUSHROOM, 1.5f, -3f).food(food(2))));
    public static final DeferredItem<Item> MUSHROOM_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.MUSHROOM_AXE,
            p -> new EdibleAxeItem(p.axe(ModToolTiers.MUSHROOM, 6, -3.2f).food(food(6))));
    public static final DeferredItem<Item> MUSHROOM_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.MUSHROOM_HOE,
            p -> new EdibleHoeItem(p.hoe(ModToolTiers.MUSHROOM, 0, -3f).food(food(4))));
    public static final DeferredItem<Item> MUSHROOM_HELMET = ITEMS.registerItem(GeneratedRegistrationIds.Items.MUSHROOM_HELMET,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.MUSHROOM_ARMOR_MATERIAL, ArmorType.HELMET).food(food(10))));
    public static final DeferredItem<Item> MUSHROOM_CHESTPLATE = ITEMS.registerItem(GeneratedRegistrationIds.Items.MUSHROOM_CHESTPLATE,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.MUSHROOM_ARMOR_MATERIAL, ArmorType.CHESTPLATE).food(food(14))));
    public static final DeferredItem<Item> MUSHROOM_LEGGINGS = ITEMS.registerItem(GeneratedRegistrationIds.Items.MUSHROOM_LEGGINGS,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.MUSHROOM_ARMOR_MATERIAL, ArmorType.LEGGINGS).food(food(14))));
    public static final DeferredItem<Item> MUSHROOM_BOOTS = ITEMS.registerItem(GeneratedRegistrationIds.Items.MUSHROOM_BOOTS,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.MUSHROOM_ARMOR_MATERIAL, ArmorType.BOOTS).food(food(8))));

    // Pufferfish
    public static final DeferredItem<Item> PUFFERFISH_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.PUFFERFISH_SWORD,
            p -> new EdibleSwordItem(p.sword(ModToolTiers.PUFFERFISH, 3, -2.4f).food(food(4))));
    public static final DeferredItem<Item> PUFFERFISH_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.PUFFERFISH_PICKAXE,
            p -> new EdiblePickaxeItem(p.pickaxe(ModToolTiers.PUFFERFISH, 1, -2.8f).food(food(6))));
    public static final DeferredItem<Item> PUFFERFISH_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.PUFFERFISH_SHOVEL,
            p -> new EdibleShovelItem(p.shovel(ModToolTiers.PUFFERFISH, 1.5f, -3f).food(food(2))));
    public static final DeferredItem<Item> PUFFERFISH_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.PUFFERFISH_AXE,
            p -> new EdibleAxeItem(p.axe(ModToolTiers.PUFFERFISH, 6, -3.2f).food(food(6))));
    public static final DeferredItem<Item> PUFFERFISH_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.PUFFERFISH_HOE,
            p -> new EdibleHoeItem(p.hoe(ModToolTiers.PUFFERFISH, 0, -3f).food(food(4))));
    public static final DeferredItem<Item> PUFFERFISH_HELMET = ITEMS.registerItem(GeneratedRegistrationIds.Items.PUFFERFISH_HELMET,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.PUFFERFISH_ARMOR_MATERIAL, ArmorType.HELMET).food(food(10))));
    public static final DeferredItem<Item> PUFFERFISH_CHESTPLATE = ITEMS.registerItem(GeneratedRegistrationIds.Items.PUFFERFISH_CHESTPLATE,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.PUFFERFISH_ARMOR_MATERIAL, ArmorType.CHESTPLATE).food(food(14))));
    public static final DeferredItem<Item> PUFFERFISH_LEGGINGS = ITEMS.registerItem(GeneratedRegistrationIds.Items.PUFFERFISH_LEGGINGS,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.PUFFERFISH_ARMOR_MATERIAL, ArmorType.LEGGINGS).food(food(14))));
    public static final DeferredItem<Item> PUFFERFISH_BOOTS = ITEMS.registerItem(GeneratedRegistrationIds.Items.PUFFERFISH_BOOTS,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.PUFFERFISH_ARMOR_MATERIAL, ArmorType.BOOTS).food(food(8))));

    // Honey
    public static final DeferredItem<Item> HONEY_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.HONEY_SWORD,
            p -> new EdibleSwordItem(p.sword(ModToolTiers.HONEY, 3, -2.4f).food(food(4))));
    public static final DeferredItem<Item> HONEY_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.HONEY_PICKAXE,
            p -> new EdiblePickaxeItem(p.pickaxe(ModToolTiers.HONEY, 1, -2.8f).food(food(6))));
    public static final DeferredItem<Item> HONEY_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.HONEY_SHOVEL,
            p -> new EdibleShovelItem(p.shovel(ModToolTiers.HONEY, 1.5f, -3f).food(food(2))));
    public static final DeferredItem<Item> HONEY_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.HONEY_AXE,
            p -> new EdibleAxeItem(p.axe(ModToolTiers.HONEY, 6, -3.2f).food(food(6))));
    public static final DeferredItem<Item> HONEY_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.HONEY_HOE,
            p -> new EdibleHoeItem(p.hoe(ModToolTiers.HONEY, 0, -3f).food(food(4))));
    public static final DeferredItem<Item> HONEY_HELMET = ITEMS.registerItem(GeneratedRegistrationIds.Items.HONEY_HELMET,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.HONEY_ARMOR_MATERIAL, ArmorType.HELMET).food(food(10))));
    public static final DeferredItem<Item> HONEY_CHESTPLATE = ITEMS.registerItem(GeneratedRegistrationIds.Items.HONEY_CHESTPLATE,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.HONEY_ARMOR_MATERIAL, ArmorType.CHESTPLATE).food(food(14))));
    public static final DeferredItem<Item> HONEY_LEGGINGS = ITEMS.registerItem(GeneratedRegistrationIds.Items.HONEY_LEGGINGS,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.HONEY_ARMOR_MATERIAL, ArmorType.LEGGINGS).food(food(14))));
    public static final DeferredItem<Item> HONEY_BOOTS = ITEMS.registerItem(GeneratedRegistrationIds.Items.HONEY_BOOTS,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.HONEY_ARMOR_MATERIAL, ArmorType.BOOTS).food(food(8))));

    // Chorus Fruit
    public static final DeferredItem<Item> CHORUS_FRUIT_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.CHORUS_FRUIT_SWORD,
            p -> new EdibleSwordItem(p.sword(ModToolTiers.CHORUS_FRUIT, 3, -2.4f).food(food(4))));
    public static final DeferredItem<Item> CHORUS_FRUIT_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.CHORUS_FRUIT_PICKAXE,
            p -> new EdiblePickaxeItem(p.pickaxe(ModToolTiers.CHORUS_FRUIT, 1, -2.8f).food(food(6))));
    public static final DeferredItem<Item> CHORUS_FRUIT_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.CHORUS_FRUIT_SHOVEL,
            p -> new EdibleShovelItem(p.shovel(ModToolTiers.CHORUS_FRUIT, 1.5f, -3f).food(food(2))));
    public static final DeferredItem<Item> CHORUS_FRUIT_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.CHORUS_FRUIT_AXE,
            p -> new EdibleAxeItem(p.axe(ModToolTiers.CHORUS_FRUIT, 6, -3.2f).food(food(6))));
    public static final DeferredItem<Item> CHORUS_FRUIT_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.CHORUS_FRUIT_HOE,
            p -> new EdibleHoeItem(p.hoe(ModToolTiers.CHORUS_FRUIT, 0, -3f).food(food(4))));
    public static final DeferredItem<Item> CHORUS_FRUIT_HELMET = ITEMS.registerItem(GeneratedRegistrationIds.Items.CHORUS_FRUIT_HELMET,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.CHORUS_FRUIT_ARMOR_MATERIAL, ArmorType.HELMET).food(food(10))));
    public static final DeferredItem<Item> CHORUS_FRUIT_CHESTPLATE = ITEMS.registerItem(GeneratedRegistrationIds.Items.CHORUS_FRUIT_CHESTPLATE,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.CHORUS_FRUIT_ARMOR_MATERIAL, ArmorType.CHESTPLATE).food(food(14))));
    public static final DeferredItem<Item> CHORUS_FRUIT_LEGGINGS = ITEMS.registerItem(GeneratedRegistrationIds.Items.CHORUS_FRUIT_LEGGINGS,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.CHORUS_FRUIT_ARMOR_MATERIAL, ArmorType.LEGGINGS).food(food(14))));
    public static final DeferredItem<Item> CHORUS_FRUIT_BOOTS = ITEMS.registerItem(GeneratedRegistrationIds.Items.CHORUS_FRUIT_BOOTS,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.CHORUS_FRUIT_ARMOR_MATERIAL, ArmorType.BOOTS).food(food(8))));

    // Golden Apple
    public static final DeferredItem<Item> GOLDEN_APPLE_SWORD = ITEMS.registerItem(GeneratedRegistrationIds.Items.GOLDEN_APPLE_SWORD,
            p -> new EdibleSwordItem(p.sword(ModToolTiers.GOLDEN_APPLE, 3, -2.4f).food(food(4))));
    public static final DeferredItem<Item> GOLDEN_APPLE_PICKAXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.GOLDEN_APPLE_PICKAXE,
            p -> new EdiblePickaxeItem(p.pickaxe(ModToolTiers.GOLDEN_APPLE, 1, -2.8f).food(food(6))));
    public static final DeferredItem<Item> GOLDEN_APPLE_SHOVEL = ITEMS.registerItem(GeneratedRegistrationIds.Items.GOLDEN_APPLE_SHOVEL,
            p -> new EdibleShovelItem(p.shovel(ModToolTiers.GOLDEN_APPLE, 1.5f, -3f).food(food(2))));
    public static final DeferredItem<Item> GOLDEN_APPLE_AXE = ITEMS.registerItem(GeneratedRegistrationIds.Items.GOLDEN_APPLE_AXE,
            p -> new EdibleAxeItem(p.axe(ModToolTiers.GOLDEN_APPLE, 6, -3.2f).food(food(6))));
    public static final DeferredItem<Item> GOLDEN_APPLE_HOE = ITEMS.registerItem(GeneratedRegistrationIds.Items.GOLDEN_APPLE_HOE,
            p -> new EdibleHoeItem(p.hoe(ModToolTiers.GOLDEN_APPLE, 0, -3f).food(food(4))));
    public static final DeferredItem<Item> GOLDEN_APPLE_HELMET = ITEMS.registerItem(GeneratedRegistrationIds.Items.GOLDEN_APPLE_HELMET,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.GOLDEN_APPLE_ARMOR_MATERIAL, ArmorType.HELMET).food(food(10))));
    public static final DeferredItem<Item> GOLDEN_APPLE_CHESTPLATE = ITEMS.registerItem(GeneratedRegistrationIds.Items.GOLDEN_APPLE_CHESTPLATE,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.GOLDEN_APPLE_ARMOR_MATERIAL, ArmorType.CHESTPLATE).food(food(14))));
    public static final DeferredItem<Item> GOLDEN_APPLE_LEGGINGS = ITEMS.registerItem(GeneratedRegistrationIds.Items.GOLDEN_APPLE_LEGGINGS,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.GOLDEN_APPLE_ARMOR_MATERIAL, ArmorType.LEGGINGS).food(food(14))));
    public static final DeferredItem<Item> GOLDEN_APPLE_BOOTS = ITEMS.registerItem(GeneratedRegistrationIds.Items.GOLDEN_APPLE_BOOTS,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.GOLDEN_APPLE_ARMOR_MATERIAL, ArmorType.BOOTS).food(food(8))));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
