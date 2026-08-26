package com.stonytark.usefultoolsmod.item;

import com.stonytark.usefultoolsmod.generated.GeneratedRegistrationIds;


import com.stonytark.usefultoolsmod.UsefultoolsMod;
import com.stonytark.usefultoolsmod.entity.ModEntities;
import com.stonytark.usefultoolsmod.item.custom.*;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Function;

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
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, UsefultoolsMod.MOD_ID);

    /**
     * Forge 26.1 does not ship {@code DeferredRegister.Items#registerItem(name, factory)} —
     * that's a NeoForge-only helper. This local replacement mirrors its shape: hand it a
     * fresh {@link Item.Properties} that's already had its id stamped via
     * {@code ITEMS.key(name)}, so callers don't have to repeat that boilerplate on every
     * line.
     */
    public static <T extends Item> RegistryObject<T> registerItem(String name, Function<Item.Properties, T> factory) {
        return ITEMS.register(name, () -> factory.apply(new Item.Properties().setId(ITEMS.key(name))));
    }

    private static FoodProperties food(int nutrition) {
        return new FoodProperties.Builder().nutrition(nutrition).saturationModifier(0.1f).build();
    }

    // ── Material items ─────────────────────────────────────────────────────

    public static final RegistryObject<Item> RGOLD = registerItem(GeneratedRegistrationIds.Items.RGOLD,
            p -> new Item(p.stacksTo(64)));
    public static final RegistryObject<Item> RAW_RGOLD = registerItem(GeneratedRegistrationIds.Items.RAW_RGOLD,
            p -> new Item(p.stacksTo(64)));
    public static final RegistryObject<Item> OBSHARD = registerItem(GeneratedRegistrationIds.Items.OBSHARD,
            p -> new Item(p.stacksTo(64)));
    public static final RegistryObject<Item> SEM = registerItem(GeneratedRegistrationIds.Items.SEM,
            p -> new Item(p.stacksTo(64)));
    public static final RegistryObject<Item> OBINGOT = registerItem(GeneratedRegistrationIds.Items.OBINGOT,
            p -> new Item(p.stacksTo(64)));
    public static final RegistryObject<Item> GRENADE = registerItem(GeneratedRegistrationIds.Items.GRENADE,
            p -> new Grenade(p.stacksTo(16)));
    public static final RegistryObject<Item> HRED = registerItem(GeneratedRegistrationIds.Items.HRED,
            p -> new Item(p.stacksTo(64)));
    public static final RegistryObject<Item> HGLOW = registerItem(GeneratedRegistrationIds.Items.HGLOW,
            p -> new Item(p.stacksTo(64)));
    public static final RegistryObject<Item> RLAPIS = registerItem(GeneratedRegistrationIds.Items.RLAPIS,
            p -> new Item(p.stacksTo(64)));
    public static final RegistryObject<Item> DYNAMITE = registerItem(GeneratedRegistrationIds.Items.DYNAMITE,
            p -> new Dynamite(p.stacksTo(16).fireResistant()));

    // ── Emerald-line tools ─────────────────────────────────────────────────
    public static final RegistryObject<Item> REMERALD_SWORD = registerItem(GeneratedRegistrationIds.Items.REMERALD_SWORD,
            p -> new Item(p.sword(ModToolTiers.REMERALD, 3, -2.4f)));
    public static final RegistryObject<Item> REMERALD_PICKAXE = registerItem(GeneratedRegistrationIds.Items.REMERALD_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.REMERALD, 1, -2.8f)));
    public static final RegistryObject<Item> REMERALD_SHOVEL = registerItem(GeneratedRegistrationIds.Items.REMERALD_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.REMERALD, 1.5f, -3f)));
    public static final RegistryObject<Item> REMERALD_AXE = registerItem(GeneratedRegistrationIds.Items.REMERALD_AXE,
            p -> new Item(p.axe(ModToolTiers.REMERALD, 6, -3.2f)));
    public static final RegistryObject<Item> REMERALD_HOE = registerItem(GeneratedRegistrationIds.Items.REMERALD_HOE,
            p -> new Item(p.hoe(ModToolTiers.REMERALD, 0, -3f)));

    public static final RegistryObject<Item> PEMERALD_SWORD = registerItem(GeneratedRegistrationIds.Items.PEMERALD_SWORD,
            p -> new Item(p.sword(ModToolTiers.PEMERALD, 3, -2.4f)));
    public static final RegistryObject<Item> PEMERALD_PICKAXE = registerItem(GeneratedRegistrationIds.Items.PEMERALD_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.PEMERALD, 1, -2.8f)));
    public static final RegistryObject<Item> PEMERALD_SHOVEL = registerItem(GeneratedRegistrationIds.Items.PEMERALD_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.PEMERALD, 1.5f, -3f)));
    public static final RegistryObject<Item> PEMERALD_AXE = registerItem(GeneratedRegistrationIds.Items.PEMERALD_AXE,
            p -> new Item(p.axe(ModToolTiers.PEMERALD, 6, -3.2f)));
    public static final RegistryObject<Item> PEMERALD_HOE = registerItem(GeneratedRegistrationIds.Items.PEMERALD_HOE,
            p -> new Item(p.hoe(ModToolTiers.PEMERALD, 0, -3f)));

    public static final RegistryObject<Item> ROBSIDIAN_SWORD = registerItem(GeneratedRegistrationIds.Items.ROBSIDIAN_SWORD,
            p -> new Item(p.sword(ModToolTiers.ROBSIDIAN, 3, -2.4f)));
    public static final RegistryObject<Item> ROBSIDIAN_PICKAXE = registerItem(GeneratedRegistrationIds.Items.ROBSIDIAN_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.ROBSIDIAN, 1, -2.8f)));
    public static final RegistryObject<Item> ROBSIDIAN_SHOVEL = registerItem(GeneratedRegistrationIds.Items.ROBSIDIAN_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.ROBSIDIAN, 1.5f, -3f)));
    public static final RegistryObject<Item> ROBSIDIAN_AXE = registerItem(GeneratedRegistrationIds.Items.ROBSIDIAN_AXE,
            p -> new Item(p.axe(ModToolTiers.ROBSIDIAN, 6, -3.2f)));
    public static final RegistryObject<Item> ROBSIDIAN_HOE = registerItem(GeneratedRegistrationIds.Items.ROBSIDIAN_HOE,
            p -> new Item(p.hoe(ModToolTiers.ROBSIDIAN, 0, -3f)));

    public static final RegistryObject<Item> POBSIDIAN_SWORD = registerItem(GeneratedRegistrationIds.Items.POBSIDIAN_SWORD,
            p -> new Item(p.sword(ModToolTiers.POBSIDIAN, 3, -2.4f)));
    public static final RegistryObject<Item> POBSIDIAN_PICKAXE = registerItem(GeneratedRegistrationIds.Items.POBSIDIAN_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.POBSIDIAN, 1, -2.8f)));
    public static final RegistryObject<Item> POBSIDIAN_SHOVEL = registerItem(GeneratedRegistrationIds.Items.POBSIDIAN_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.POBSIDIAN, 1.5f, -3f)));
    public static final RegistryObject<Item> POBSIDIAN_AXE = registerItem(GeneratedRegistrationIds.Items.POBSIDIAN_AXE,
            p -> new Item(p.axe(ModToolTiers.POBSIDIAN, 6, -3.2f)));
    public static final RegistryObject<Item> POBSIDIAN_HOE = registerItem(GeneratedRegistrationIds.Items.POBSIDIAN_HOE,
            p -> new Item(p.hoe(ModToolTiers.POBSIDIAN, 0, -3f)));

    public static final RegistryObject<Item> OVERPOWER_SWORD = registerItem(GeneratedRegistrationIds.Items.OVERPOWER_SWORD,
            p -> new Item(p.sword(ModToolTiers.OVERPOWER, 3, -2.4f)));
    public static final RegistryObject<Item> OVERPOWER_PICKAXE = registerItem(GeneratedRegistrationIds.Items.OVERPOWER_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.OVERPOWER, 1, -2.8f)));
    public static final RegistryObject<Item> OVERPOWER_SHOVEL = registerItem(GeneratedRegistrationIds.Items.OVERPOWER_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.OVERPOWER, 1.5f, -3f)));
    public static final RegistryObject<Item> OVERPOWER_AXE = registerItem(GeneratedRegistrationIds.Items.OVERPOWER_AXE,
            p -> new Item(p.axe(ModToolTiers.OVERPOWER, 6, -3.2f)));

    public static final RegistryObject<Item> HREDSTONE_SWORD = registerItem(GeneratedRegistrationIds.Items.HREDSTONE_SWORD,
            p -> new Item(p.sword(ModToolTiers.HREDSTONE, 3, -2.4f)));
    public static final RegistryObject<Item> HREDSTONE_PICKAXE = registerItem(GeneratedRegistrationIds.Items.HREDSTONE_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.HREDSTONE, 1, -2.8f)));
    public static final RegistryObject<Item> HREDSTONE_SHOVEL = registerItem(GeneratedRegistrationIds.Items.HREDSTONE_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.HREDSTONE, 1.5f, -3f)));
    public static final RegistryObject<Item> HREDSTONE_AXE = registerItem(GeneratedRegistrationIds.Items.HREDSTONE_AXE,
            p -> new Item(p.axe(ModToolTiers.HREDSTONE, 6, -3.2f)));
    public static final RegistryObject<Item> HREDSTONE_HOE = registerItem(GeneratedRegistrationIds.Items.HREDSTONE_HOE,
            p -> new Item(p.hoe(ModToolTiers.HREDSTONE, 0, -3f)));

    public static final RegistryObject<Item> HGLOWSTONE_SWORD = registerItem(GeneratedRegistrationIds.Items.HGLOWSTONE_SWORD,
            p -> new Item(p.sword(ModToolTiers.HGLOWSTONE, 3, -2.4f)));
    public static final RegistryObject<Item> HGLOWSTONE_PICKAXE = registerItem(GeneratedRegistrationIds.Items.HGLOWSTONE_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.HGLOWSTONE, 1, -2.8f)));
    public static final RegistryObject<Item> HGLOWSTONE_SHOVEL = registerItem(GeneratedRegistrationIds.Items.HGLOWSTONE_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.HGLOWSTONE, 1.5f, -3f)));
    public static final RegistryObject<Item> HGLOWSTONE_AXE = registerItem(GeneratedRegistrationIds.Items.HGLOWSTONE_AXE,
            p -> new Item(p.axe(ModToolTiers.HGLOWSTONE, 6, -3.2f)));
    public static final RegistryObject<Item> HGLOWSTONE_HOE = registerItem(GeneratedRegistrationIds.Items.HGLOWSTONE_HOE,
            p -> new Item(p.hoe(ModToolTiers.HGLOWSTONE, 0, -3f)));

    public static final RegistryObject<Item> RGOLD_SWORD = registerItem(GeneratedRegistrationIds.Items.RGOLD_SWORD,
            p -> new Item(p.sword(ModToolTiers.RGOLD, 3, -2.4f)));
    public static final RegistryObject<Item> RGOLD_PICKAXE = registerItem(GeneratedRegistrationIds.Items.RGOLD_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.RGOLD, 1, -2.8f)));
    public static final RegistryObject<Item> RGOLD_SHOVEL = registerItem(GeneratedRegistrationIds.Items.RGOLD_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.RGOLD, 1.5f, -3f)));
    public static final RegistryObject<Item> RGOLD_AXE = registerItem(GeneratedRegistrationIds.Items.RGOLD_AXE,
            p -> new Item(p.axe(ModToolTiers.RGOLD, 6, -3.2f)));
    public static final RegistryObject<Item> RGOLD_HOE = registerItem(GeneratedRegistrationIds.Items.RGOLD_HOE,
            p -> new Item(p.hoe(ModToolTiers.RGOLD, 0, -3f)));

    public static final RegistryObject<Item> RLAPIS_SWORD = registerItem(GeneratedRegistrationIds.Items.RLAPIS_SWORD,
            p -> new Item(p.sword(ModToolTiers.RLAPIS, 3, -2.4f)));
    public static final RegistryObject<Item> RLAPIS_PICKAXE = registerItem(GeneratedRegistrationIds.Items.RLAPIS_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.RLAPIS, 1, -2.8f)));
    public static final RegistryObject<Item> RLAPIS_SHOVEL = registerItem(GeneratedRegistrationIds.Items.RLAPIS_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.RLAPIS, 1.5f, -3f)));
    public static final RegistryObject<Item> RLAPIS_AXE = registerItem(GeneratedRegistrationIds.Items.RLAPIS_AXE,
            p -> new Item(p.axe(ModToolTiers.RLAPIS, 6, -3.2f)));
    public static final RegistryObject<Item> RLAPIS_HOE = registerItem(GeneratedRegistrationIds.Items.RLAPIS_HOE,
            p -> new Item(p.hoe(ModToolTiers.RLAPIS, 0, -3f)));

    // ── Armor pieces ───────────────────────────────────────────────────────
    public static final RegistryObject<Item> EMERALD_HELMET = registerItem(GeneratedRegistrationIds.Items.EMERALD_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.EMERALD_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final RegistryObject<Item> EMERALD_CHESTPLATE = registerItem(GeneratedRegistrationIds.Items.EMERALD_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.EMERALD_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final RegistryObject<Item> EMERALD_LEGGINGS = registerItem(GeneratedRegistrationIds.Items.EMERALD_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.EMERALD_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final RegistryObject<Item> EMERALD_BOOTS = registerItem(GeneratedRegistrationIds.Items.EMERALD_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.EMERALD_ARMOR_MATERIAL, ArmorType.BOOTS)));

    public static final RegistryObject<Item> HRED_HELMET = registerItem(GeneratedRegistrationIds.Items.HRED_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.HRED_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final RegistryObject<Item> HRED_CHESTPLATE = registerItem(GeneratedRegistrationIds.Items.HRED_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.HRED_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final RegistryObject<Item> HRED_LEGGINGS = registerItem(GeneratedRegistrationIds.Items.HRED_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.HRED_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final RegistryObject<Item> HRED_BOOTS = registerItem(GeneratedRegistrationIds.Items.HRED_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.HRED_ARMOR_MATERIAL, ArmorType.BOOTS)));

    public static final RegistryObject<Item> HGLOW_HELMET = registerItem(GeneratedRegistrationIds.Items.HGLOW_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.HGLOW_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final RegistryObject<Item> HGLOW_CHESTPLATE = registerItem(GeneratedRegistrationIds.Items.HGLOW_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.HGLOW_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final RegistryObject<Item> HGLOW_LEGGINGS = registerItem(GeneratedRegistrationIds.Items.HGLOW_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.HGLOW_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final RegistryObject<Item> HGLOW_BOOTS = registerItem(GeneratedRegistrationIds.Items.HGLOW_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.HGLOW_ARMOR_MATERIAL, ArmorType.BOOTS)));

    public static final RegistryObject<Item> OBSIDIAN_HELMET = registerItem(GeneratedRegistrationIds.Items.OBSIDIAN_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.OBSIDIAN_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final RegistryObject<Item> OBSIDIAN_CHESTPLATE = registerItem(GeneratedRegistrationIds.Items.OBSIDIAN_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.OBSIDIAN_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final RegistryObject<Item> OBSIDIAN_LEGGINGS = registerItem(GeneratedRegistrationIds.Items.OBSIDIAN_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.OBSIDIAN_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final RegistryObject<Item> OBSIDIAN_BOOTS = registerItem(GeneratedRegistrationIds.Items.OBSIDIAN_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.OBSIDIAN_ARMOR_MATERIAL, ArmorType.BOOTS)));

    public static final RegistryObject<Item> RGOLD_HELMET = registerItem(GeneratedRegistrationIds.Items.RGOLD_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.RGOLD_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final RegistryObject<Item> RGOLD_CHESTPLATE = registerItem(GeneratedRegistrationIds.Items.RGOLD_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.RGOLD_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final RegistryObject<Item> RGOLD_LEGGINGS = registerItem(GeneratedRegistrationIds.Items.RGOLD_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.RGOLD_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final RegistryObject<Item> RGOLD_BOOTS = registerItem(GeneratedRegistrationIds.Items.RGOLD_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.RGOLD_ARMOR_MATERIAL, ArmorType.BOOTS)));

    public static final RegistryObject<Item> RLAPIS_HELMET = registerItem(GeneratedRegistrationIds.Items.RLAPIS_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.RLAPIS_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final RegistryObject<Item> RLAPIS_CHESTPLATE = registerItem(GeneratedRegistrationIds.Items.RLAPIS_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.RLAPIS_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final RegistryObject<Item> RLAPIS_LEGGINGS = registerItem(GeneratedRegistrationIds.Items.RLAPIS_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.RLAPIS_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final RegistryObject<Item> RLAPIS_BOOTS = registerItem(GeneratedRegistrationIds.Items.RLAPIS_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.RLAPIS_ARMOR_MATERIAL, ArmorType.BOOTS)));

    // OVERPOWER armor — helmet uses ModArmorItem to drive set-effects.
    public static final RegistryObject<Item> OVERPOWER_HELMET = registerItem(GeneratedRegistrationIds.Items.OVERPOWER_HELMET,
            p -> new ModArmorItem(p.humanoidArmor(ModArmorMaterials.OVERPOWER_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final RegistryObject<Item> OVERPOWER_CHESTPLATE = registerItem(GeneratedRegistrationIds.Items.OVERPOWER_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.OVERPOWER_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final RegistryObject<Item> OVERPOWER_LEGGINGS = registerItem(GeneratedRegistrationIds.Items.OVERPOWER_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.OVERPOWER_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final RegistryObject<Item> OVERPOWER_BOOTS = registerItem(GeneratedRegistrationIds.Items.OVERPOWER_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.OVERPOWER_ARMOR_MATERIAL, ArmorType.BOOTS)));

    // 1.21.5+: SpawnEggItem(Properties) only; the entity type is carried by the
    // ENTITY_DATA component set via Properties#spawnEgg(EntityType).
    public static final RegistryObject<Item> GHOST_SPAWN_EGG = registerItem(GeneratedRegistrationIds.Items.GHOST_SPAWN_EGG,
            p -> new SpawnEggItem(p.spawnEgg(ModEntities.GHOST.get())));

    public static final RegistryObject<Item> ECTOPLASM = registerItem(GeneratedRegistrationIds.Items.ECTOPLASM,
            p -> new Item(p.stacksTo(64)));
    public static final RegistryObject<Item> CONDENSED_ECTOPLASM = registerItem(GeneratedRegistrationIds.Items.CONDENSED_ECTOPLASM, p -> new Item(p.stacksTo(64)));
    public static final RegistryObject<Item> WRAITH_SPAWN_EGG = registerItem(GeneratedRegistrationIds.Items.WRAITH_SPAWN_EGG,
            p -> new SpawnEggItem(p.spawnEgg(ModEntities.WRAITH.get())));
    public static final RegistryObject<Item> SPECTRAL_RESONATOR = registerItem(GeneratedRegistrationIds.Items.SPECTRAL_RESONATOR,
            p -> new SpectralResonatorItem(p.durability(128)));
    public static final RegistryObject<Item> STICKY_DYNAMITE = registerItem(GeneratedRegistrationIds.Items.STICKY_DYNAMITE,
            p -> new StickyDynamiteItem(p.stacksTo(16)));
    public static final RegistryObject<Item> REMOTE_DETONATOR = registerItem(GeneratedRegistrationIds.Items.REMOTE_DETONATOR,
            p -> new RemoteDetonatorItem(p.stacksTo(1)));

    // ── Rough Ectoplasm tools (RECTO, stone-tier) ──────────────────────────
    public static final RegistryObject<Item> RECTO_SWORD = registerItem(GeneratedRegistrationIds.Items.RECTO_SWORD,
            p -> new EctoSwordItem(p.sword(ModToolTiers.RECTO, 3, -2.4f)));
    public static final RegistryObject<Item> RECTO_PICKAXE = registerItem(GeneratedRegistrationIds.Items.RECTO_PICKAXE,
            p -> new EctoPickaxeItem(p.pickaxe(ModToolTiers.RECTO, 1, -2.8f)));
    public static final RegistryObject<Item> RECTO_SHOVEL = registerItem(GeneratedRegistrationIds.Items.RECTO_SHOVEL,
            p -> new EctoShovelItem(p.shovel(ModToolTiers.RECTO, 1.5f, -3f)));
    public static final RegistryObject<Item> RECTO_AXE = registerItem(GeneratedRegistrationIds.Items.RECTO_AXE,
            p -> new EctoAxeItem(p.axe(ModToolTiers.RECTO, 6, -3.2f)));
    public static final RegistryObject<Item> RECTO_HOE = registerItem(GeneratedRegistrationIds.Items.RECTO_HOE,
            p -> new EctoHoeItem(p.hoe(ModToolTiers.RECTO, 0, -3f)));

    // ── Refined Ectoplasm + Ectoplasm tools/armor ──────────────────────────
    public static final RegistryObject<Item> REFINED_ECTOPLASM = registerItem(GeneratedRegistrationIds.Items.REFINED_ECTOPLASM,
            p -> new Item(p.stacksTo(64)));

    public static final RegistryObject<Item> ECTO_SWORD = registerItem(GeneratedRegistrationIds.Items.ECTO_SWORD,
            p -> new EctoSwordItem(p.sword(ModToolTiers.ECTOPLASM, 3, -2.4f)));
    public static final RegistryObject<Item> ECTO_PICKAXE = registerItem(GeneratedRegistrationIds.Items.ECTO_PICKAXE,
            p -> new EctoPickaxeItem(p.pickaxe(ModToolTiers.ECTOPLASM, 1, -2.8f)));
    public static final RegistryObject<Item> ECTO_SHOVEL = registerItem(GeneratedRegistrationIds.Items.ECTO_SHOVEL,
            p -> new EctoShovelItem(p.shovel(ModToolTiers.ECTOPLASM, 1.5f, -3f)));
    public static final RegistryObject<Item> ECTO_AXE = registerItem(GeneratedRegistrationIds.Items.ECTO_AXE,
            p -> new EctoAxeItem(p.axe(ModToolTiers.ECTOPLASM, 6, -3.1f)));
    public static final RegistryObject<Item> ECTO_HOE = registerItem(GeneratedRegistrationIds.Items.ECTO_HOE,
            p -> new EctoHoeItem(p.hoe(ModToolTiers.ECTOPLASM, 0, -3f)));

    public static final RegistryObject<Item> ECTO_HELMET = registerItem(GeneratedRegistrationIds.Items.ECTO_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.ECTO_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final RegistryObject<Item> ECTO_CHESTPLATE = registerItem(GeneratedRegistrationIds.Items.ECTO_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.ECTO_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final RegistryObject<Item> ECTO_LEGGINGS = registerItem(GeneratedRegistrationIds.Items.ECTO_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.ECTO_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final RegistryObject<Item> ECTO_BOOTS = registerItem(GeneratedRegistrationIds.Items.ECTO_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.ECTO_ARMOR_MATERIAL, ArmorType.BOOTS)));

    // ── Coal material items + tools + armor ────────────────────────────────
    public static final RegistryObject<Item> COAL_DUST = registerItem(GeneratedRegistrationIds.Items.COAL_DUST,
            p -> new Item(p.stacksTo(64)));
    public static final RegistryObject<Item> HARDENED_COAL = registerItem(GeneratedRegistrationIds.Items.HARDENED_COAL,
            p -> new Item(p.stacksTo(64)));

    public static final RegistryObject<Item> COAL_SWORD = registerItem(GeneratedRegistrationIds.Items.COAL_SWORD,
            p -> new CoalSwordItem(p.sword(ModToolTiers.COAL_TOOL, 2, -2.4f)));
    public static final RegistryObject<Item> COAL_PICKAXE = registerItem(GeneratedRegistrationIds.Items.COAL_PICKAXE,
            p -> new CoalPickaxeItem(p.pickaxe(ModToolTiers.COAL_TOOL, 1, -2.8f)));
    public static final RegistryObject<Item> COAL_SHOVEL = registerItem(GeneratedRegistrationIds.Items.COAL_SHOVEL,
            p -> new CoalShovelItem(p.shovel(ModToolTiers.COAL_TOOL, 1.5f, -3f)));
    public static final RegistryObject<Item> COAL_AXE = registerItem(GeneratedRegistrationIds.Items.COAL_AXE,
            p -> new CoalAxeItem(p.axe(ModToolTiers.COAL_TOOL, 5, -3.2f)));
    public static final RegistryObject<Item> COAL_HOE = registerItem(GeneratedRegistrationIds.Items.COAL_HOE,
            p -> new CoalHoeItem(p.hoe(ModToolTiers.COAL_TOOL, 0, -3f)));

    public static final RegistryObject<Item> COAL_HELMET = registerItem(GeneratedRegistrationIds.Items.COAL_HELMET,
            p -> new CoalArmorItem(p.humanoidArmor(ModArmorMaterials.COAL_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final RegistryObject<Item> COAL_CHESTPLATE = registerItem(GeneratedRegistrationIds.Items.COAL_CHESTPLATE,
            p -> new CoalArmorItem(p.humanoidArmor(ModArmorMaterials.COAL_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final RegistryObject<Item> COAL_LEGGINGS = registerItem(GeneratedRegistrationIds.Items.COAL_LEGGINGS,
            p -> new CoalArmorItem(p.humanoidArmor(ModArmorMaterials.COAL_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final RegistryObject<Item> COAL_BOOTS = registerItem(GeneratedRegistrationIds.Items.COAL_BOOTS,
            p -> new CoalArmorItem(p.humanoidArmor(ModArmorMaterials.COAL_ARMOR_MATERIAL, ArmorType.BOOTS)));

    // ── Raw metal rough tool sets ──────────────────────────────────────────
    public static final RegistryObject<Item> RRAW_GOLD_SWORD = registerItem(GeneratedRegistrationIds.Items.RRAW_GOLD_SWORD,
            p -> new Item(p.sword(ModToolTiers.RRAW_GOLD, 3, -2.4f)));
    public static final RegistryObject<Item> RRAW_GOLD_PICKAXE = registerItem(GeneratedRegistrationIds.Items.RRAW_GOLD_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.RRAW_GOLD, 1, -2.8f)));
    public static final RegistryObject<Item> RRAW_GOLD_SHOVEL = registerItem(GeneratedRegistrationIds.Items.RRAW_GOLD_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.RRAW_GOLD, 1.5f, -3f)));
    public static final RegistryObject<Item> RRAW_GOLD_AXE = registerItem(GeneratedRegistrationIds.Items.RRAW_GOLD_AXE,
            p -> new Item(p.axe(ModToolTiers.RRAW_GOLD, 6, -3.2f)));
    public static final RegistryObject<Item> RRAW_GOLD_HOE = registerItem(GeneratedRegistrationIds.Items.RRAW_GOLD_HOE,
            p -> new Item(p.hoe(ModToolTiers.RRAW_GOLD, 0, -3f)));

    public static final RegistryObject<Item> RRAW_COPPER_SWORD = registerItem(GeneratedRegistrationIds.Items.RRAW_COPPER_SWORD,
            p -> new Item(p.sword(ModToolTiers.RRAW_COPPER, 3, -2.4f)));
    public static final RegistryObject<Item> RRAW_COPPER_PICKAXE = registerItem(GeneratedRegistrationIds.Items.RRAW_COPPER_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.RRAW_COPPER, 1, -2.8f)));
    public static final RegistryObject<Item> RRAW_COPPER_SHOVEL = registerItem(GeneratedRegistrationIds.Items.RRAW_COPPER_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.RRAW_COPPER, 1.5f, -3f)));
    public static final RegistryObject<Item> RRAW_COPPER_AXE = registerItem(GeneratedRegistrationIds.Items.RRAW_COPPER_AXE,
            p -> new Item(p.axe(ModToolTiers.RRAW_COPPER, 6, -3.2f)));
    public static final RegistryObject<Item> RRAW_COPPER_HOE = registerItem(GeneratedRegistrationIds.Items.RRAW_COPPER_HOE,
            p -> new Item(p.hoe(ModToolTiers.RRAW_COPPER, 0, -3f)));

    public static final RegistryObject<Item> RRAW_IRON_SWORD = registerItem(GeneratedRegistrationIds.Items.RRAW_IRON_SWORD,
            p -> new Item(p.sword(ModToolTiers.RRAW_IRON, 3, -2.4f)));
    public static final RegistryObject<Item> RRAW_IRON_PICKAXE = registerItem(GeneratedRegistrationIds.Items.RRAW_IRON_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.RRAW_IRON, 1, -2.8f)));
    public static final RegistryObject<Item> RRAW_IRON_SHOVEL = registerItem(GeneratedRegistrationIds.Items.RRAW_IRON_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.RRAW_IRON, 1.5f, -3f)));
    public static final RegistryObject<Item> RRAW_IRON_AXE = registerItem(GeneratedRegistrationIds.Items.RRAW_IRON_AXE,
            p -> new Item(p.axe(ModToolTiers.RRAW_IRON, 6, -3.2f)));
    public static final RegistryObject<Item> RRAW_IRON_HOE = registerItem(GeneratedRegistrationIds.Items.RRAW_IRON_HOE,
            p -> new Item(p.hoe(ModToolTiers.RRAW_IRON, 0, -3f)));

    public static final RegistryObject<Item> RRAW_RGOLD_SWORD = registerItem(GeneratedRegistrationIds.Items.RRAW_RGOLD_SWORD,
            p -> new Item(p.sword(ModToolTiers.RRAW_RGOLD, 3, -2.4f)));
    public static final RegistryObject<Item> RRAW_RGOLD_PICKAXE = registerItem(GeneratedRegistrationIds.Items.RRAW_RGOLD_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.RRAW_RGOLD, 1, -2.8f)));
    public static final RegistryObject<Item> RRAW_RGOLD_SHOVEL = registerItem(GeneratedRegistrationIds.Items.RRAW_RGOLD_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.RRAW_RGOLD, 1.5f, -3f)));
    public static final RegistryObject<Item> RRAW_RGOLD_AXE = registerItem(GeneratedRegistrationIds.Items.RRAW_RGOLD_AXE,
            p -> new Item(p.axe(ModToolTiers.RRAW_RGOLD, 6, -3.2f)));
    public static final RegistryObject<Item> RRAW_RGOLD_HOE = registerItem(GeneratedRegistrationIds.Items.RRAW_RGOLD_HOE,
            p -> new Item(p.hoe(ModToolTiers.RRAW_RGOLD, 0, -3f)));

    public static final RegistryObject<Item> RSCRAP_SWORD = registerItem(GeneratedRegistrationIds.Items.RSCRAP_SWORD,
            p -> new Item(p.sword(ModToolTiers.RSCRAP, 3, -2.4f)));
    public static final RegistryObject<Item> RSCRAP_PICKAXE = registerItem(GeneratedRegistrationIds.Items.RSCRAP_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.RSCRAP, 1, -2.8f)));
    public static final RegistryObject<Item> RSCRAP_SHOVEL = registerItem(GeneratedRegistrationIds.Items.RSCRAP_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.RSCRAP, 1.5f, -3f)));
    public static final RegistryObject<Item> RSCRAP_AXE = registerItem(GeneratedRegistrationIds.Items.RSCRAP_AXE,
            p -> new Item(p.axe(ModToolTiers.RSCRAP, 6, -3.2f)));
    public static final RegistryObject<Item> RSCRAP_HOE = registerItem(GeneratedRegistrationIds.Items.RSCRAP_HOE,
            p -> new Item(p.hoe(ModToolTiers.RSCRAP, 0, -3f)));

    // ── Crystal / element materials ────────────────────────────────────────
    public static final RegistryObject<Item> CALCIFIED_AMETHYST = registerItem(GeneratedRegistrationIds.Items.CALCIFIED_AMETHYST,
            p -> new Item(p.stacksTo(64)));
    public static final RegistryObject<Item> GLACIAL_SHARD = registerItem(GeneratedRegistrationIds.Items.GLACIAL_SHARD,
            p -> new Item(p.stacksTo(64)));
    public static final RegistryObject<Item> POLISHED_QUARTZ = registerItem(GeneratedRegistrationIds.Items.POLISHED_QUARTZ,
            p -> new Item(p.stacksTo(64)));
    public static final RegistryObject<Item> POLISHED_PRISMARINE = registerItem(GeneratedRegistrationIds.Items.POLISHED_PRISMARINE,
            p -> new Item(p.stacksTo(64)));

    public static final RegistryObject<Item> RAMETHYST_SWORD = registerItem(GeneratedRegistrationIds.Items.RAMETHYST_SWORD,
            p -> new Item(p.sword(ModToolTiers.RAMETHYST, 3, -2.4f)));
    public static final RegistryObject<Item> RAMETHYST_PICKAXE = registerItem(GeneratedRegistrationIds.Items.RAMETHYST_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.RAMETHYST, 1, -2.8f)));
    public static final RegistryObject<Item> RAMETHYST_SHOVEL = registerItem(GeneratedRegistrationIds.Items.RAMETHYST_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.RAMETHYST, 1.5f, -3f)));
    public static final RegistryObject<Item> RAMETHYST_AXE = registerItem(GeneratedRegistrationIds.Items.RAMETHYST_AXE,
            p -> new Item(p.axe(ModToolTiers.RAMETHYST, 6, -3.2f)));
    public static final RegistryObject<Item> RAMETHYST_HOE = registerItem(GeneratedRegistrationIds.Items.RAMETHYST_HOE,
            p -> new Item(p.hoe(ModToolTiers.RAMETHYST, 0, -3f)));

    public static final RegistryObject<Item> SNOW_SWORD = registerItem(GeneratedRegistrationIds.Items.SNOW_SWORD,
            p -> new Item(p.sword(ModToolTiers.SNOW_TOOL, 3, -2.4f)));
    public static final RegistryObject<Item> SNOW_PICKAXE = registerItem(GeneratedRegistrationIds.Items.SNOW_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.SNOW_TOOL, 1, -2.8f)));
    public static final RegistryObject<Item> SNOW_SHOVEL = registerItem(GeneratedRegistrationIds.Items.SNOW_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.SNOW_TOOL, 1.5f, -3f)));
    public static final RegistryObject<Item> SNOW_AXE = registerItem(GeneratedRegistrationIds.Items.SNOW_AXE,
            p -> new Item(p.axe(ModToolTiers.SNOW_TOOL, 6, -3.2f)));
    public static final RegistryObject<Item> SNOW_HOE = registerItem(GeneratedRegistrationIds.Items.SNOW_HOE,
            p -> new Item(p.hoe(ModToolTiers.SNOW_TOOL, 0, -3f)));

    public static final RegistryObject<Item> RQUARTZ_SWORD = registerItem(GeneratedRegistrationIds.Items.RQUARTZ_SWORD,
            p -> new Item(p.sword(ModToolTiers.RQUARTZ, 3, -2.4f)));
    public static final RegistryObject<Item> RQUARTZ_PICKAXE = registerItem(GeneratedRegistrationIds.Items.RQUARTZ_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.RQUARTZ, 1, -2.8f)));
    public static final RegistryObject<Item> RQUARTZ_SHOVEL = registerItem(GeneratedRegistrationIds.Items.RQUARTZ_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.RQUARTZ, 1.5f, -3f)));
    public static final RegistryObject<Item> RQUARTZ_AXE = registerItem(GeneratedRegistrationIds.Items.RQUARTZ_AXE,
            p -> new Item(p.axe(ModToolTiers.RQUARTZ, 6, -3.2f)));
    public static final RegistryObject<Item> RQUARTZ_HOE = registerItem(GeneratedRegistrationIds.Items.RQUARTZ_HOE,
            p -> new Item(p.hoe(ModToolTiers.RQUARTZ, 0, -3f)));

    public static final RegistryObject<Item> RPRISM_SWORD = registerItem(GeneratedRegistrationIds.Items.RPRISM_SWORD,
            p -> new Item(p.sword(ModToolTiers.RPRISM, 3, -2.4f)));
    public static final RegistryObject<Item> RPRISM_PICKAXE = registerItem(GeneratedRegistrationIds.Items.RPRISM_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.RPRISM, 1, -2.8f)));
    public static final RegistryObject<Item> RPRISM_SHOVEL = registerItem(GeneratedRegistrationIds.Items.RPRISM_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.RPRISM, 1.5f, -3f)));
    public static final RegistryObject<Item> RPRISM_AXE = registerItem(GeneratedRegistrationIds.Items.RPRISM_AXE,
            p -> new Item(p.axe(ModToolTiers.RPRISM, 6, -3.2f)));
    public static final RegistryObject<Item> RPRISM_HOE = registerItem(GeneratedRegistrationIds.Items.RPRISM_HOE,
            p -> new Item(p.hoe(ModToolTiers.RPRISM, 0, -3f)));

    public static final RegistryObject<Item> CAMETHYST_SWORD = registerItem(GeneratedRegistrationIds.Items.CAMETHYST_SWORD,
            p -> new Item(p.sword(ModToolTiers.CAMETHYST, 3, -2.4f)));
    public static final RegistryObject<Item> CAMETHYST_PICKAXE = registerItem(GeneratedRegistrationIds.Items.CAMETHYST_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.CAMETHYST, 1, -2.8f)));
    public static final RegistryObject<Item> CAMETHYST_SHOVEL = registerItem(GeneratedRegistrationIds.Items.CAMETHYST_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.CAMETHYST, 1.5f, -3f)));
    public static final RegistryObject<Item> CAMETHYST_AXE = registerItem(GeneratedRegistrationIds.Items.CAMETHYST_AXE,
            p -> new Item(p.axe(ModToolTiers.CAMETHYST, 6, -3.2f)));
    public static final RegistryObject<Item> CAMETHYST_HOE = registerItem(GeneratedRegistrationIds.Items.CAMETHYST_HOE,
            p -> new Item(p.hoe(ModToolTiers.CAMETHYST, 0, -3f)));
    public static final RegistryObject<Item> CAMETHYST_HELMET = registerItem(GeneratedRegistrationIds.Items.CAMETHYST_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.CAMETHYST_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final RegistryObject<Item> CAMETHYST_CHESTPLATE = registerItem(GeneratedRegistrationIds.Items.CAMETHYST_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.CAMETHYST_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final RegistryObject<Item> CAMETHYST_LEGGINGS = registerItem(GeneratedRegistrationIds.Items.CAMETHYST_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.CAMETHYST_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final RegistryObject<Item> CAMETHYST_BOOTS = registerItem(GeneratedRegistrationIds.Items.CAMETHYST_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.CAMETHYST_ARMOR_MATERIAL, ArmorType.BOOTS)));

    public static final RegistryObject<Item> ICE_SWORD = registerItem(GeneratedRegistrationIds.Items.ICE_SWORD,
            p -> new Item(p.sword(ModToolTiers.ICE_TOOL, 3, -2.4f)));
    public static final RegistryObject<Item> ICE_PICKAXE = registerItem(GeneratedRegistrationIds.Items.ICE_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.ICE_TOOL, 1, -2.8f)));
    public static final RegistryObject<Item> ICE_SHOVEL = registerItem(GeneratedRegistrationIds.Items.ICE_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.ICE_TOOL, 1.5f, -3f)));
    public static final RegistryObject<Item> ICE_AXE = registerItem(GeneratedRegistrationIds.Items.ICE_AXE,
            p -> new Item(p.axe(ModToolTiers.ICE_TOOL, 6, -3.2f)));
    public static final RegistryObject<Item> ICE_HOE = registerItem(GeneratedRegistrationIds.Items.ICE_HOE,
            p -> new Item(p.hoe(ModToolTiers.ICE_TOOL, 0, -3f)));
    public static final RegistryObject<Item> ICE_HELMET = registerItem(GeneratedRegistrationIds.Items.ICE_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.ICE_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final RegistryObject<Item> ICE_CHESTPLATE = registerItem(GeneratedRegistrationIds.Items.ICE_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.ICE_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final RegistryObject<Item> ICE_LEGGINGS = registerItem(GeneratedRegistrationIds.Items.ICE_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.ICE_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final RegistryObject<Item> ICE_BOOTS = registerItem(GeneratedRegistrationIds.Items.ICE_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.ICE_ARMOR_MATERIAL, ArmorType.BOOTS)));

    public static final RegistryObject<Item> PQUARTZ_SWORD = registerItem(GeneratedRegistrationIds.Items.PQUARTZ_SWORD,
            p -> new Item(p.sword(ModToolTiers.PQUARTZ, 3, -2.4f)));
    public static final RegistryObject<Item> PQUARTZ_PICKAXE = registerItem(GeneratedRegistrationIds.Items.PQUARTZ_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.PQUARTZ, 1, -2.8f)));
    public static final RegistryObject<Item> PQUARTZ_SHOVEL = registerItem(GeneratedRegistrationIds.Items.PQUARTZ_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.PQUARTZ, 1.5f, -3f)));
    public static final RegistryObject<Item> PQUARTZ_AXE = registerItem(GeneratedRegistrationIds.Items.PQUARTZ_AXE,
            p -> new Item(p.axe(ModToolTiers.PQUARTZ, 6, -3.2f)));
    public static final RegistryObject<Item> PQUARTZ_HOE = registerItem(GeneratedRegistrationIds.Items.PQUARTZ_HOE,
            p -> new Item(p.hoe(ModToolTiers.PQUARTZ, 0, -3f)));
    public static final RegistryObject<Item> PQUARTZ_HELMET = registerItem(GeneratedRegistrationIds.Items.PQUARTZ_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.PQUARTZ_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final RegistryObject<Item> PQUARTZ_CHESTPLATE = registerItem(GeneratedRegistrationIds.Items.PQUARTZ_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.PQUARTZ_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final RegistryObject<Item> PQUARTZ_LEGGINGS = registerItem(GeneratedRegistrationIds.Items.PQUARTZ_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.PQUARTZ_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final RegistryObject<Item> PQUARTZ_BOOTS = registerItem(GeneratedRegistrationIds.Items.PQUARTZ_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.PQUARTZ_ARMOR_MATERIAL, ArmorType.BOOTS)));

    public static final RegistryObject<Item> PPRISM_SWORD = registerItem(GeneratedRegistrationIds.Items.PPRISM_SWORD,
            p -> new Item(p.sword(ModToolTiers.PPRISM, 3, -2.4f)));
    public static final RegistryObject<Item> PPRISM_PICKAXE = registerItem(GeneratedRegistrationIds.Items.PPRISM_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.PPRISM, 1, -2.8f)));
    public static final RegistryObject<Item> PPRISM_SHOVEL = registerItem(GeneratedRegistrationIds.Items.PPRISM_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.PPRISM, 1.5f, -3f)));
    public static final RegistryObject<Item> PPRISM_AXE = registerItem(GeneratedRegistrationIds.Items.PPRISM_AXE,
            p -> new Item(p.axe(ModToolTiers.PPRISM, 6, -3.2f)));
    public static final RegistryObject<Item> PPRISM_HOE = registerItem(GeneratedRegistrationIds.Items.PPRISM_HOE,
            p -> new Item(p.hoe(ModToolTiers.PPRISM, 0, -3f)));
    public static final RegistryObject<Item> PPRISM_HELMET = registerItem(GeneratedRegistrationIds.Items.PPRISM_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.PPRISM_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final RegistryObject<Item> PPRISM_CHESTPLATE = registerItem(GeneratedRegistrationIds.Items.PPRISM_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.PPRISM_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final RegistryObject<Item> PPRISM_LEGGINGS = registerItem(GeneratedRegistrationIds.Items.PPRISM_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.PPRISM_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final RegistryObject<Item> PPRISM_BOOTS = registerItem(GeneratedRegistrationIds.Items.PPRISM_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.PPRISM_ARMOR_MATERIAL, ArmorType.BOOTS)));

    // ── Flint + FNI ────────────────────────────────────────────────────────
    public static final RegistryObject<Item> RFLINT_SWORD = registerItem(GeneratedRegistrationIds.Items.RFLINT_SWORD,
            p -> new Item(p.sword(ModToolTiers.RFLINT, 3, -2.4f)));
    public static final RegistryObject<Item> RFLINT_PICKAXE = registerItem(GeneratedRegistrationIds.Items.RFLINT_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.RFLINT, 1, -2.8f)));
    public static final RegistryObject<Item> RFLINT_SHOVEL = registerItem(GeneratedRegistrationIds.Items.RFLINT_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.RFLINT, 1.5f, -3f)));
    public static final RegistryObject<Item> RFLINT_AXE = registerItem(GeneratedRegistrationIds.Items.RFLINT_AXE,
            p -> new Item(p.axe(ModToolTiers.RFLINT, 6, -3.2f)));
    public static final RegistryObject<Item> RFLINT_HOE = registerItem(GeneratedRegistrationIds.Items.RFLINT_HOE,
            p -> new Item(p.hoe(ModToolTiers.RFLINT, 0, -3f)));

    public static final RegistryObject<Item> FNI_SWORD = registerItem(GeneratedRegistrationIds.Items.FNI_SWORD,
            p -> new Item(p.sword(ModToolTiers.FNI_TOOLS, 3, -2.4f)));
    public static final RegistryObject<Item> FNI_PICKAXE = registerItem(GeneratedRegistrationIds.Items.FNI_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.FNI_TOOLS, 1, -2.8f)));
    public static final RegistryObject<Item> FNI_SHOVEL = registerItem(GeneratedRegistrationIds.Items.FNI_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.FNI_TOOLS, 1.5f, -3f)));
    public static final RegistryObject<Item> FNI_AXE = registerItem(GeneratedRegistrationIds.Items.FNI_AXE,
            p -> new Item(p.axe(ModToolTiers.FNI_TOOLS, 6, -3.2f)));
    public static final RegistryObject<Item> FNI_HOE = registerItem(GeneratedRegistrationIds.Items.FNI_HOE,
            p -> new Item(p.hoe(ModToolTiers.FNI_TOOLS, 0, -3f)));
    public static final RegistryObject<Item> FNI_HELMET = registerItem(GeneratedRegistrationIds.Items.FNI_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.FNI_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final RegistryObject<Item> FNI_CHESTPLATE = registerItem(GeneratedRegistrationIds.Items.FNI_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.FNI_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final RegistryObject<Item> FNI_LEGGINGS = registerItem(GeneratedRegistrationIds.Items.FNI_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.FNI_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final RegistryObject<Item> FNI_BOOTS = registerItem(GeneratedRegistrationIds.Items.FNI_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.FNI_ARMOR_MATERIAL, ArmorType.BOOTS)));

    // ── Stone-rock variants (13 sets × 5 tools) ────────────────────────────
    public static final RegistryObject<Item> ANDESITE_SWORD = registerItem(GeneratedRegistrationIds.Items.ANDESITE_SWORD,
            p -> new Item(p.sword(ModToolTiers.STONE_ANDESITE, 3, -2.4f)));
    public static final RegistryObject<Item> ANDESITE_PICKAXE = registerItem(GeneratedRegistrationIds.Items.ANDESITE_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.STONE_ANDESITE, 1, -2.8f)));
    public static final RegistryObject<Item> ANDESITE_SHOVEL = registerItem(GeneratedRegistrationIds.Items.ANDESITE_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.STONE_ANDESITE, 1.5f, -3f)));
    public static final RegistryObject<Item> ANDESITE_AXE = registerItem(GeneratedRegistrationIds.Items.ANDESITE_AXE,
            p -> new Item(p.axe(ModToolTiers.STONE_ANDESITE, 6, -3.2f)));
    public static final RegistryObject<Item> ANDESITE_HOE = registerItem(GeneratedRegistrationIds.Items.ANDESITE_HOE,
            p -> new Item(p.hoe(ModToolTiers.STONE_ANDESITE, 0, -3f)));

    public static final RegistryObject<Item> BASALT_SWORD = registerItem(GeneratedRegistrationIds.Items.BASALT_SWORD,
            p -> new Item(p.sword(ModToolTiers.STONE_BASALT, 3, -2.5f)));
    public static final RegistryObject<Item> BASALT_PICKAXE = registerItem(GeneratedRegistrationIds.Items.BASALT_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.STONE_BASALT, 1, -2.9f)));
    public static final RegistryObject<Item> BASALT_SHOVEL = registerItem(GeneratedRegistrationIds.Items.BASALT_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.STONE_BASALT, 2.0f, -3.1f)));
    public static final RegistryObject<Item> BASALT_AXE = registerItem(GeneratedRegistrationIds.Items.BASALT_AXE,
            p -> new Item(p.axe(ModToolTiers.STONE_BASALT, 7, -3.3f)));
    public static final RegistryObject<Item> BASALT_HOE = registerItem(GeneratedRegistrationIds.Items.BASALT_HOE,
            p -> new Item(p.hoe(ModToolTiers.STONE_BASALT, 0, -3.1f)));

    public static final RegistryObject<Item> BLACKSTONE_SWORD = registerItem(GeneratedRegistrationIds.Items.BLACKSTONE_SWORD,
            p -> new Item(p.sword(ModToolTiers.STONE_BLACKSTONE, 4, -2.5f)));
    public static final RegistryObject<Item> BLACKSTONE_PICKAXE = registerItem(GeneratedRegistrationIds.Items.BLACKSTONE_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.STONE_BLACKSTONE, 1, -2.9f)));
    public static final RegistryObject<Item> BLACKSTONE_SHOVEL = registerItem(GeneratedRegistrationIds.Items.BLACKSTONE_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.STONE_BLACKSTONE, 2.0f, -3.1f)));
    public static final RegistryObject<Item> BLACKSTONE_AXE = registerItem(GeneratedRegistrationIds.Items.BLACKSTONE_AXE,
            p -> new Item(p.axe(ModToolTiers.STONE_BLACKSTONE, 7, -3.35f)));
    public static final RegistryObject<Item> BLACKSTONE_HOE = registerItem(GeneratedRegistrationIds.Items.BLACKSTONE_HOE,
            p -> new Item(p.hoe(ModToolTiers.STONE_BLACKSTONE, 0, -3.1f)));

    public static final RegistryObject<Item> CALCITE_SWORD = registerItem(GeneratedRegistrationIds.Items.CALCITE_SWORD,
            p -> new Item(p.sword(ModToolTiers.STONE_CALCITE, 2, -2.2f)));
    public static final RegistryObject<Item> CALCITE_PICKAXE = registerItem(GeneratedRegistrationIds.Items.CALCITE_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.STONE_CALCITE, 1, -2.6f)));
    public static final RegistryObject<Item> CALCITE_SHOVEL = registerItem(GeneratedRegistrationIds.Items.CALCITE_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.STONE_CALCITE, 1.0f, -2.8f)));
    public static final RegistryObject<Item> CALCITE_AXE = registerItem(GeneratedRegistrationIds.Items.CALCITE_AXE,
            p -> new Item(p.axe(ModToolTiers.STONE_CALCITE, 5, -3.0f)));
    public static final RegistryObject<Item> CALCITE_HOE = registerItem(GeneratedRegistrationIds.Items.CALCITE_HOE,
            p -> new Item(p.hoe(ModToolTiers.STONE_CALCITE, 0, -2.6f)));

    public static final RegistryObject<Item> DEEPSLATE_SWORD = registerItem(GeneratedRegistrationIds.Items.DEEPSLATE_SWORD,
            p -> new Item(p.sword(ModToolTiers.STONE_DEEPSLATE, 4, -2.55f)));
    public static final RegistryObject<Item> DEEPSLATE_PICKAXE = registerItem(GeneratedRegistrationIds.Items.DEEPSLATE_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.STONE_DEEPSLATE, 1, -2.95f)));
    public static final RegistryObject<Item> DEEPSLATE_SHOVEL = registerItem(GeneratedRegistrationIds.Items.DEEPSLATE_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.STONE_DEEPSLATE, 2.0f, -3.15f)));
    public static final RegistryObject<Item> DEEPSLATE_AXE = registerItem(GeneratedRegistrationIds.Items.DEEPSLATE_AXE,
            p -> new Item(p.axe(ModToolTiers.STONE_DEEPSLATE, 7, -3.4f)));
    public static final RegistryObject<Item> DEEPSLATE_HOE = registerItem(GeneratedRegistrationIds.Items.DEEPSLATE_HOE,
            p -> new Item(p.hoe(ModToolTiers.STONE_DEEPSLATE, 0, -3.1f)));

    public static final RegistryObject<Item> DIORITE_SWORD = registerItem(GeneratedRegistrationIds.Items.DIORITE_SWORD,
            p -> new Item(p.sword(ModToolTiers.STONE_DIORITE, 3, -2.4f)));
    public static final RegistryObject<Item> DIORITE_PICKAXE = registerItem(GeneratedRegistrationIds.Items.DIORITE_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.STONE_DIORITE, 1, -2.8f)));
    public static final RegistryObject<Item> DIORITE_SHOVEL = registerItem(GeneratedRegistrationIds.Items.DIORITE_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.STONE_DIORITE, 1.5f, -3f)));
    public static final RegistryObject<Item> DIORITE_AXE = registerItem(GeneratedRegistrationIds.Items.DIORITE_AXE,
            p -> new Item(p.axe(ModToolTiers.STONE_DIORITE, 6, -3.2f)));
    public static final RegistryObject<Item> DIORITE_HOE = registerItem(GeneratedRegistrationIds.Items.DIORITE_HOE,
            p -> new Item(p.hoe(ModToolTiers.STONE_DIORITE, 0, -2.9f)));

    public static final RegistryObject<Item> END_STONE_SWORD = registerItem(GeneratedRegistrationIds.Items.END_STONE_SWORD,
            p -> new Item(p.sword(ModToolTiers.STONE_END_STONE, 3, -2.35f)));
    public static final RegistryObject<Item> END_STONE_PICKAXE = registerItem(GeneratedRegistrationIds.Items.END_STONE_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.STONE_END_STONE, 1, -2.75f)));
    public static final RegistryObject<Item> END_STONE_SHOVEL = registerItem(GeneratedRegistrationIds.Items.END_STONE_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.STONE_END_STONE, 1.5f, -2.95f)));
    public static final RegistryObject<Item> END_STONE_AXE = registerItem(GeneratedRegistrationIds.Items.END_STONE_AXE,
            p -> new Item(p.axe(ModToolTiers.STONE_END_STONE, 6, -3.15f)));
    public static final RegistryObject<Item> END_STONE_HOE = registerItem(GeneratedRegistrationIds.Items.END_STONE_HOE,
            p -> new Item(p.hoe(ModToolTiers.STONE_END_STONE, 0, -2.8f)));

    public static final RegistryObject<Item> GRANITE_SWORD = registerItem(GeneratedRegistrationIds.Items.GRANITE_SWORD,
            p -> new Item(p.sword(ModToolTiers.STONE_GRANITE, 3, -2.5f)));
    public static final RegistryObject<Item> GRANITE_PICKAXE = registerItem(GeneratedRegistrationIds.Items.GRANITE_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.STONE_GRANITE, 1, -2.9f)));
    public static final RegistryObject<Item> GRANITE_SHOVEL = registerItem(GeneratedRegistrationIds.Items.GRANITE_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.STONE_GRANITE, 2.0f, -3.1f)));
    public static final RegistryObject<Item> GRANITE_AXE = registerItem(GeneratedRegistrationIds.Items.GRANITE_AXE,
            p -> new Item(p.axe(ModToolTiers.STONE_GRANITE, 7, -3.3f)));
    public static final RegistryObject<Item> GRANITE_HOE = registerItem(GeneratedRegistrationIds.Items.GRANITE_HOE,
            p -> new Item(p.hoe(ModToolTiers.STONE_GRANITE, 0, -3.1f)));

    public static final RegistryObject<Item> NETHERRACK_SWORD = registerItem(GeneratedRegistrationIds.Items.NETHERRACK_SWORD,
            p -> new Item(p.sword(ModToolTiers.STONE_NETHERRACK, 2, -2.2f)));
    public static final RegistryObject<Item> NETHERRACK_PICKAXE = registerItem(GeneratedRegistrationIds.Items.NETHERRACK_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.STONE_NETHERRACK, 1, -2.6f)));
    public static final RegistryObject<Item> NETHERRACK_SHOVEL = registerItem(GeneratedRegistrationIds.Items.NETHERRACK_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.STONE_NETHERRACK, 1.0f, -2.8f)));
    public static final RegistryObject<Item> NETHERRACK_AXE = registerItem(GeneratedRegistrationIds.Items.NETHERRACK_AXE,
            p -> new Item(p.axe(ModToolTiers.STONE_NETHERRACK, 5, -3.0f)));
    public static final RegistryObject<Item> NETHERRACK_HOE = registerItem(GeneratedRegistrationIds.Items.NETHERRACK_HOE,
            p -> new Item(p.hoe(ModToolTiers.STONE_NETHERRACK, 0, -2.5f)));

    public static final RegistryObject<Item> SANDSTONE_SWORD = registerItem(GeneratedRegistrationIds.Items.SANDSTONE_SWORD,
            p -> new Item(p.sword(ModToolTiers.STONE_SANDSTONE, 2, -2.3f)));
    public static final RegistryObject<Item> SANDSTONE_PICKAXE = registerItem(GeneratedRegistrationIds.Items.SANDSTONE_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.STONE_SANDSTONE, 1, -2.7f)));
    public static final RegistryObject<Item> SANDSTONE_SHOVEL = registerItem(GeneratedRegistrationIds.Items.SANDSTONE_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.STONE_SANDSTONE, 1.0f, -2.9f)));
    public static final RegistryObject<Item> SANDSTONE_AXE = registerItem(GeneratedRegistrationIds.Items.SANDSTONE_AXE,
            p -> new Item(p.axe(ModToolTiers.STONE_SANDSTONE, 5, -3.1f)));
    public static final RegistryObject<Item> SANDSTONE_HOE = registerItem(GeneratedRegistrationIds.Items.SANDSTONE_HOE,
            p -> new Item(p.hoe(ModToolTiers.STONE_SANDSTONE, 0, -2.7f)));

    public static final RegistryObject<Item> SMOOTH_BASALT_SWORD = registerItem(GeneratedRegistrationIds.Items.SMOOTH_BASALT_SWORD,
            p -> new Item(p.sword(ModToolTiers.STONE_SMOOTH_BASALT, 3, -2.45f)));
    public static final RegistryObject<Item> SMOOTH_BASALT_PICKAXE = registerItem(GeneratedRegistrationIds.Items.SMOOTH_BASALT_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.STONE_SMOOTH_BASALT, 1, -2.85f)));
    public static final RegistryObject<Item> SMOOTH_BASALT_SHOVEL = registerItem(GeneratedRegistrationIds.Items.SMOOTH_BASALT_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.STONE_SMOOTH_BASALT, 1.5f, -3.05f)));
    public static final RegistryObject<Item> SMOOTH_BASALT_AXE = registerItem(GeneratedRegistrationIds.Items.SMOOTH_BASALT_AXE,
            p -> new Item(p.axe(ModToolTiers.STONE_SMOOTH_BASALT, 6, -3.25f)));
    public static final RegistryObject<Item> SMOOTH_BASALT_HOE = registerItem(GeneratedRegistrationIds.Items.SMOOTH_BASALT_HOE,
            p -> new Item(p.hoe(ModToolTiers.STONE_SMOOTH_BASALT, 0, -3.0f)));

    public static final RegistryObject<Item> TERRACOTTA_SWORD = registerItem(GeneratedRegistrationIds.Items.TERRACOTTA_SWORD,
            p -> new Item(p.sword(ModToolTiers.STONE_TERRACOTTA, 3, -2.35f)));
    public static final RegistryObject<Item> TERRACOTTA_PICKAXE = registerItem(GeneratedRegistrationIds.Items.TERRACOTTA_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.STONE_TERRACOTTA, 1, -2.75f)));
    public static final RegistryObject<Item> TERRACOTTA_SHOVEL = registerItem(GeneratedRegistrationIds.Items.TERRACOTTA_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.STONE_TERRACOTTA, 1.5f, -2.95f)));
    public static final RegistryObject<Item> TERRACOTTA_AXE = registerItem(GeneratedRegistrationIds.Items.TERRACOTTA_AXE,
            p -> new Item(p.axe(ModToolTiers.STONE_TERRACOTTA, 6, -3.15f)));
    public static final RegistryObject<Item> TERRACOTTA_HOE = registerItem(GeneratedRegistrationIds.Items.TERRACOTTA_HOE,
            p -> new Item(p.hoe(ModToolTiers.STONE_TERRACOTTA, 0, -2.8f)));

    public static final RegistryObject<Item> TUFF_SWORD = registerItem(GeneratedRegistrationIds.Items.TUFF_SWORD,
            p -> new Item(p.sword(ModToolTiers.STONE_TUFF, 2, -2.35f)));
    public static final RegistryObject<Item> TUFF_PICKAXE = registerItem(GeneratedRegistrationIds.Items.TUFF_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.STONE_TUFF, 1, -2.75f)));
    public static final RegistryObject<Item> TUFF_SHOVEL = registerItem(GeneratedRegistrationIds.Items.TUFF_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.STONE_TUFF, 1.5f, -2.95f)));
    public static final RegistryObject<Item> TUFF_AXE = registerItem(GeneratedRegistrationIds.Items.TUFF_AXE,
            p -> new Item(p.axe(ModToolTiers.STONE_TUFF, 5, -3.15f)));
    public static final RegistryObject<Item> TUFF_HOE = registerItem(GeneratedRegistrationIds.Items.TUFF_HOE,
            p -> new Item(p.hoe(ModToolTiers.STONE_TUFF, 0, -2.8f)));

    // ── Wood variants (11 × 5) ─────────────────────────────────────────────
    public static final RegistryObject<Item> OAK_SWORD = registerItem(GeneratedRegistrationIds.Items.OAK_SWORD,
            p -> new Item(p.sword(ModToolTiers.WOOD_OAK, 3, -2.4f)));
    public static final RegistryObject<Item> OAK_PICKAXE = registerItem(GeneratedRegistrationIds.Items.OAK_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.WOOD_OAK, 1, -2.8f)));
    public static final RegistryObject<Item> OAK_SHOVEL = registerItem(GeneratedRegistrationIds.Items.OAK_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.WOOD_OAK, 1.5f, -3f)));
    public static final RegistryObject<Item> OAK_AXE = registerItem(GeneratedRegistrationIds.Items.OAK_AXE,
            p -> new Item(p.axe(ModToolTiers.WOOD_OAK, 6, -3.2f)));
    public static final RegistryObject<Item> OAK_HOE = registerItem(GeneratedRegistrationIds.Items.OAK_HOE,
            p -> new Item(p.hoe(ModToolTiers.WOOD_OAK, 0, -3f)));

    public static final RegistryObject<Item> SPRUCE_SWORD = registerItem(GeneratedRegistrationIds.Items.SPRUCE_SWORD,
            p -> new Item(p.sword(ModToolTiers.WOOD_SPRUCE, 3, -2.4f)));
    public static final RegistryObject<Item> SPRUCE_PICKAXE = registerItem(GeneratedRegistrationIds.Items.SPRUCE_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.WOOD_SPRUCE, 1, -2.8f)));
    public static final RegistryObject<Item> SPRUCE_SHOVEL = registerItem(GeneratedRegistrationIds.Items.SPRUCE_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.WOOD_SPRUCE, 1.5f, -3f)));
    public static final RegistryObject<Item> SPRUCE_AXE = registerItem(GeneratedRegistrationIds.Items.SPRUCE_AXE,
            p -> new Item(p.axe(ModToolTiers.WOOD_SPRUCE, 6, -3.2f)));
    public static final RegistryObject<Item> SPRUCE_HOE = registerItem(GeneratedRegistrationIds.Items.SPRUCE_HOE,
            p -> new Item(p.hoe(ModToolTiers.WOOD_SPRUCE, 0, -3f)));

    public static final RegistryObject<Item> BIRCH_SWORD = registerItem(GeneratedRegistrationIds.Items.BIRCH_SWORD,
            p -> new Item(p.sword(ModToolTiers.WOOD_BIRCH, 3, -2.4f)));
    public static final RegistryObject<Item> BIRCH_PICKAXE = registerItem(GeneratedRegistrationIds.Items.BIRCH_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.WOOD_BIRCH, 1, -2.8f)));
    public static final RegistryObject<Item> BIRCH_SHOVEL = registerItem(GeneratedRegistrationIds.Items.BIRCH_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.WOOD_BIRCH, 1.5f, -3f)));
    public static final RegistryObject<Item> BIRCH_AXE = registerItem(GeneratedRegistrationIds.Items.BIRCH_AXE,
            p -> new Item(p.axe(ModToolTiers.WOOD_BIRCH, 6, -3.2f)));
    public static final RegistryObject<Item> BIRCH_HOE = registerItem(GeneratedRegistrationIds.Items.BIRCH_HOE,
            p -> new Item(p.hoe(ModToolTiers.WOOD_BIRCH, 0, -3f)));

    public static final RegistryObject<Item> JUNGLE_SWORD = registerItem(GeneratedRegistrationIds.Items.JUNGLE_SWORD,
            p -> new Item(p.sword(ModToolTiers.WOOD_JUNGLE, 3, -2.4f)));
    public static final RegistryObject<Item> JUNGLE_PICKAXE = registerItem(GeneratedRegistrationIds.Items.JUNGLE_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.WOOD_JUNGLE, 1, -2.8f)));
    public static final RegistryObject<Item> JUNGLE_SHOVEL = registerItem(GeneratedRegistrationIds.Items.JUNGLE_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.WOOD_JUNGLE, 1.5f, -3f)));
    public static final RegistryObject<Item> JUNGLE_AXE = registerItem(GeneratedRegistrationIds.Items.JUNGLE_AXE,
            p -> new Item(p.axe(ModToolTiers.WOOD_JUNGLE, 6, -3.2f)));
    public static final RegistryObject<Item> JUNGLE_HOE = registerItem(GeneratedRegistrationIds.Items.JUNGLE_HOE,
            p -> new Item(p.hoe(ModToolTiers.WOOD_JUNGLE, 0, -3f)));

    public static final RegistryObject<Item> ACACIA_SWORD = registerItem(GeneratedRegistrationIds.Items.ACACIA_SWORD,
            p -> new Item(p.sword(ModToolTiers.WOOD_ACACIA, 3, -2.4f)));
    public static final RegistryObject<Item> ACACIA_PICKAXE = registerItem(GeneratedRegistrationIds.Items.ACACIA_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.WOOD_ACACIA, 1, -2.8f)));
    public static final RegistryObject<Item> ACACIA_SHOVEL = registerItem(GeneratedRegistrationIds.Items.ACACIA_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.WOOD_ACACIA, 1.5f, -3f)));
    public static final RegistryObject<Item> ACACIA_AXE = registerItem(GeneratedRegistrationIds.Items.ACACIA_AXE,
            p -> new Item(p.axe(ModToolTiers.WOOD_ACACIA, 6, -3.2f)));
    public static final RegistryObject<Item> ACACIA_HOE = registerItem(GeneratedRegistrationIds.Items.ACACIA_HOE,
            p -> new Item(p.hoe(ModToolTiers.WOOD_ACACIA, 0, -3f)));

    public static final RegistryObject<Item> DARK_OAK_SWORD = registerItem(GeneratedRegistrationIds.Items.DARK_OAK_SWORD,
            p -> new Item(p.sword(ModToolTiers.WOOD_DARK_OAK, 3, -2.4f)));
    public static final RegistryObject<Item> DARK_OAK_PICKAXE = registerItem(GeneratedRegistrationIds.Items.DARK_OAK_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.WOOD_DARK_OAK, 1, -2.8f)));
    public static final RegistryObject<Item> DARK_OAK_SHOVEL = registerItem(GeneratedRegistrationIds.Items.DARK_OAK_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.WOOD_DARK_OAK, 1.5f, -3f)));
    public static final RegistryObject<Item> DARK_OAK_AXE = registerItem(GeneratedRegistrationIds.Items.DARK_OAK_AXE,
            p -> new Item(p.axe(ModToolTiers.WOOD_DARK_OAK, 6, -3.2f)));
    public static final RegistryObject<Item> DARK_OAK_HOE = registerItem(GeneratedRegistrationIds.Items.DARK_OAK_HOE,
            p -> new Item(p.hoe(ModToolTiers.WOOD_DARK_OAK, 0, -3f)));

    public static final RegistryObject<Item> MANGROVE_SWORD = registerItem(GeneratedRegistrationIds.Items.MANGROVE_SWORD,
            p -> new Item(p.sword(ModToolTiers.WOOD_MANGROVE, 3, -2.4f)));
    public static final RegistryObject<Item> MANGROVE_PICKAXE = registerItem(GeneratedRegistrationIds.Items.MANGROVE_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.WOOD_MANGROVE, 1, -2.8f)));
    public static final RegistryObject<Item> MANGROVE_SHOVEL = registerItem(GeneratedRegistrationIds.Items.MANGROVE_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.WOOD_MANGROVE, 1.5f, -3f)));
    public static final RegistryObject<Item> MANGROVE_AXE = registerItem(GeneratedRegistrationIds.Items.MANGROVE_AXE,
            p -> new Item(p.axe(ModToolTiers.WOOD_MANGROVE, 6, -3.2f)));
    public static final RegistryObject<Item> MANGROVE_HOE = registerItem(GeneratedRegistrationIds.Items.MANGROVE_HOE,
            p -> new Item(p.hoe(ModToolTiers.WOOD_MANGROVE, 0, -3f)));

    public static final RegistryObject<Item> CHERRY_SWORD = registerItem(GeneratedRegistrationIds.Items.CHERRY_SWORD,
            p -> new Item(p.sword(ModToolTiers.WOOD_CHERRY, 3, -2.4f)));
    public static final RegistryObject<Item> CHERRY_PICKAXE = registerItem(GeneratedRegistrationIds.Items.CHERRY_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.WOOD_CHERRY, 1, -2.8f)));
    public static final RegistryObject<Item> CHERRY_SHOVEL = registerItem(GeneratedRegistrationIds.Items.CHERRY_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.WOOD_CHERRY, 1.5f, -3f)));
    public static final RegistryObject<Item> CHERRY_AXE = registerItem(GeneratedRegistrationIds.Items.CHERRY_AXE,
            p -> new Item(p.axe(ModToolTiers.WOOD_CHERRY, 6, -3.2f)));
    public static final RegistryObject<Item> CHERRY_HOE = registerItem(GeneratedRegistrationIds.Items.CHERRY_HOE,
            p -> new Item(p.hoe(ModToolTiers.WOOD_CHERRY, 0, -3f)));

    public static final RegistryObject<Item> BAMBOO_SWORD = registerItem(GeneratedRegistrationIds.Items.BAMBOO_SWORD,
            p -> new Item(p.sword(ModToolTiers.WOOD_BAMBOO, 3, -2.4f)));
    public static final RegistryObject<Item> BAMBOO_PICKAXE = registerItem(GeneratedRegistrationIds.Items.BAMBOO_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.WOOD_BAMBOO, 1, -2.8f)));
    public static final RegistryObject<Item> BAMBOO_SHOVEL = registerItem(GeneratedRegistrationIds.Items.BAMBOO_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.WOOD_BAMBOO, 1.5f, -3f)));
    public static final RegistryObject<Item> BAMBOO_AXE = registerItem(GeneratedRegistrationIds.Items.BAMBOO_AXE,
            p -> new Item(p.axe(ModToolTiers.WOOD_BAMBOO, 6, -3.2f)));
    public static final RegistryObject<Item> BAMBOO_HOE = registerItem(GeneratedRegistrationIds.Items.BAMBOO_HOE,
            p -> new Item(p.hoe(ModToolTiers.WOOD_BAMBOO, 0, -3f)));

    public static final RegistryObject<Item> CRIMSON_SWORD = registerItem(GeneratedRegistrationIds.Items.CRIMSON_SWORD,
            p -> new Item(p.sword(ModToolTiers.WOOD_CRIMSON, 3, -2.4f)));
    public static final RegistryObject<Item> CRIMSON_PICKAXE = registerItem(GeneratedRegistrationIds.Items.CRIMSON_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.WOOD_CRIMSON, 1, -2.8f)));
    public static final RegistryObject<Item> CRIMSON_SHOVEL = registerItem(GeneratedRegistrationIds.Items.CRIMSON_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.WOOD_CRIMSON, 1.5f, -3f)));
    public static final RegistryObject<Item> CRIMSON_AXE = registerItem(GeneratedRegistrationIds.Items.CRIMSON_AXE,
            p -> new Item(p.axe(ModToolTiers.WOOD_CRIMSON, 6, -3.2f)));
    public static final RegistryObject<Item> CRIMSON_HOE = registerItem(GeneratedRegistrationIds.Items.CRIMSON_HOE,
            p -> new Item(p.hoe(ModToolTiers.WOOD_CRIMSON, 0, -3f)));

    public static final RegistryObject<Item> WARPED_SWORD = registerItem(GeneratedRegistrationIds.Items.WARPED_SWORD,
            p -> new Item(p.sword(ModToolTiers.WOOD_WARPED, 3, -2.4f)));
    public static final RegistryObject<Item> WARPED_PICKAXE = registerItem(GeneratedRegistrationIds.Items.WARPED_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.WOOD_WARPED, 1, -2.8f)));
    public static final RegistryObject<Item> WARPED_SHOVEL = registerItem(GeneratedRegistrationIds.Items.WARPED_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.WOOD_WARPED, 1.5f, -3f)));
    public static final RegistryObject<Item> WARPED_AXE = registerItem(GeneratedRegistrationIds.Items.WARPED_AXE,
            p -> new Item(p.axe(ModToolTiers.WOOD_WARPED, 6, -3.2f)));
    public static final RegistryObject<Item> WARPED_HOE = registerItem(GeneratedRegistrationIds.Items.WARPED_HOE,
            p -> new Item(p.hoe(ModToolTiers.WOOD_WARPED, 0, -3f)));

    // ── Leather tools ──────────────────────────────────────────────────────
    public static final RegistryObject<Item> LEATHER_SWORD = registerItem(GeneratedRegistrationIds.Items.LEATHER_SWORD,
            p -> new Item(p.sword(ModToolTiers.LEATHER, 3, -2.4f)));
    public static final RegistryObject<Item> LEATHER_PICKAXE = registerItem(GeneratedRegistrationIds.Items.LEATHER_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.LEATHER, 1, -2.8f)));
    public static final RegistryObject<Item> LEATHER_SHOVEL = registerItem(GeneratedRegistrationIds.Items.LEATHER_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.LEATHER, 1.5f, -3f)));
    public static final RegistryObject<Item> LEATHER_AXE = registerItem(GeneratedRegistrationIds.Items.LEATHER_AXE,
            p -> new Item(p.axe(ModToolTiers.LEATHER, 6, -3.2f)));
    public static final RegistryObject<Item> LEATHER_HOE = registerItem(GeneratedRegistrationIds.Items.LEATHER_HOE,
            p -> new Item(p.hoe(ModToolTiers.LEATHER, 0, -3f)));

    // ── Vanilla material sets ──────────────────────────────────────────────

    public static final RegistryObject<Item> PAPER_SWORD = registerItem(GeneratedRegistrationIds.Items.PAPER_SWORD,
            p -> new Item(p.sword(ModToolTiers.PAPER, 3, -2.4f)));
    public static final RegistryObject<Item> PAPER_PICKAXE = registerItem(GeneratedRegistrationIds.Items.PAPER_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.PAPER, 1, -2.8f)));
    public static final RegistryObject<Item> PAPER_SHOVEL = registerItem(GeneratedRegistrationIds.Items.PAPER_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.PAPER, 1.5f, -3f)));
    public static final RegistryObject<Item> PAPER_AXE = registerItem(GeneratedRegistrationIds.Items.PAPER_AXE,
            p -> new Item(p.axe(ModToolTiers.PAPER, 6, -3.2f)));
    public static final RegistryObject<Item> PAPER_HOE = registerItem(GeneratedRegistrationIds.Items.PAPER_HOE,
            p -> new Item(p.hoe(ModToolTiers.PAPER, 0, -3f)));

    public static final RegistryObject<Item> FEATHER_SWORD = registerItem(GeneratedRegistrationIds.Items.FEATHER_SWORD,
            p -> new Item(p.sword(ModToolTiers.FEATHER, 3, -2.4f)));
    public static final RegistryObject<Item> FEATHER_PICKAXE = registerItem(GeneratedRegistrationIds.Items.FEATHER_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.FEATHER, 1, -2.8f)));
    public static final RegistryObject<Item> FEATHER_SHOVEL = registerItem(GeneratedRegistrationIds.Items.FEATHER_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.FEATHER, 1.5f, -3f)));
    public static final RegistryObject<Item> FEATHER_AXE = registerItem(GeneratedRegistrationIds.Items.FEATHER_AXE,
            p -> new Item(p.axe(ModToolTiers.FEATHER, 6, -3.2f)));
    public static final RegistryObject<Item> FEATHER_HOE = registerItem(GeneratedRegistrationIds.Items.FEATHER_HOE,
            p -> new Item(p.hoe(ModToolTiers.FEATHER, 0, -3f)));

    public static final RegistryObject<Item> GLASS_SWORD = registerItem(GeneratedRegistrationIds.Items.GLASS_SWORD,
            p -> new Item(p.sword(ModToolTiers.GLASS, 3, -2.4f)));
    public static final RegistryObject<Item> GLASS_PICKAXE = registerItem(GeneratedRegistrationIds.Items.GLASS_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.GLASS, 1, -2.8f)));
    public static final RegistryObject<Item> GLASS_SHOVEL = registerItem(GeneratedRegistrationIds.Items.GLASS_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.GLASS, 1.5f, -3f)));
    public static final RegistryObject<Item> GLASS_AXE = registerItem(GeneratedRegistrationIds.Items.GLASS_AXE,
            p -> new Item(p.axe(ModToolTiers.GLASS, 6, -3.2f)));
    public static final RegistryObject<Item> GLASS_HOE = registerItem(GeneratedRegistrationIds.Items.GLASS_HOE,
            p -> new Item(p.hoe(ModToolTiers.GLASS, 0, -3f)));

    public static final RegistryObject<Item> RABBIT_HIDE_HELMET = registerItem(GeneratedRegistrationIds.Items.RABBIT_HIDE_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.RABBIT_HIDE_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final RegistryObject<Item> RABBIT_HIDE_CHESTPLATE = registerItem(GeneratedRegistrationIds.Items.RABBIT_HIDE_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.RABBIT_HIDE_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final RegistryObject<Item> RABBIT_HIDE_LEGGINGS = registerItem(GeneratedRegistrationIds.Items.RABBIT_HIDE_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.RABBIT_HIDE_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final RegistryObject<Item> RABBIT_HIDE_BOOTS = registerItem(GeneratedRegistrationIds.Items.RABBIT_HIDE_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.RABBIT_HIDE_ARMOR_MATERIAL, ArmorType.BOOTS)));

    public static final RegistryObject<Item> CACTUS_SWORD = registerItem(GeneratedRegistrationIds.Items.CACTUS_SWORD,
            p -> new Item(p.sword(ModToolTiers.CACTUS, 3, -2.4f)));
    public static final RegistryObject<Item> CACTUS_PICKAXE = registerItem(GeneratedRegistrationIds.Items.CACTUS_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.CACTUS, 1, -2.8f)));
    public static final RegistryObject<Item> CACTUS_SHOVEL = registerItem(GeneratedRegistrationIds.Items.CACTUS_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.CACTUS, 1.5f, -3f)));
    public static final RegistryObject<Item> CACTUS_AXE = registerItem(GeneratedRegistrationIds.Items.CACTUS_AXE,
            p -> new Item(p.axe(ModToolTiers.CACTUS, 6, -3.2f)));
    public static final RegistryObject<Item> CACTUS_HOE = registerItem(GeneratedRegistrationIds.Items.CACTUS_HOE,
            p -> new Item(p.hoe(ModToolTiers.CACTUS, 0, -3f)));
    public static final RegistryObject<Item> CACTUS_HELMET = registerItem(GeneratedRegistrationIds.Items.CACTUS_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.CACTUS_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final RegistryObject<Item> CACTUS_CHESTPLATE = registerItem(GeneratedRegistrationIds.Items.CACTUS_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.CACTUS_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final RegistryObject<Item> CACTUS_LEGGINGS = registerItem(GeneratedRegistrationIds.Items.CACTUS_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.CACTUS_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final RegistryObject<Item> CACTUS_BOOTS = registerItem(GeneratedRegistrationIds.Items.CACTUS_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.CACTUS_ARMOR_MATERIAL, ArmorType.BOOTS)));

    public static final RegistryObject<Item> SPONGE_SWORD = registerItem(GeneratedRegistrationIds.Items.SPONGE_SWORD,
            p -> new Item(p.sword(ModToolTiers.SPONGE, 3, -2.4f)));
    public static final RegistryObject<Item> SPONGE_PICKAXE = registerItem(GeneratedRegistrationIds.Items.SPONGE_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.SPONGE, 1, -2.8f)));
    public static final RegistryObject<Item> SPONGE_SHOVEL = registerItem(GeneratedRegistrationIds.Items.SPONGE_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.SPONGE, 1.5f, -3f)));
    public static final RegistryObject<Item> SPONGE_AXE = registerItem(GeneratedRegistrationIds.Items.SPONGE_AXE,
            p -> new Item(p.axe(ModToolTiers.SPONGE, 6, -3.2f)));
    public static final RegistryObject<Item> SPONGE_HOE = registerItem(GeneratedRegistrationIds.Items.SPONGE_HOE,
            p -> new Item(p.hoe(ModToolTiers.SPONGE, 0, -3f)));

    public static final RegistryObject<Item> BONE_SWORD = registerItem(GeneratedRegistrationIds.Items.BONE_SWORD,
            p -> new Item(p.sword(ModToolTiers.BONE, 3, -2.4f)));
    public static final RegistryObject<Item> BONE_PICKAXE = registerItem(GeneratedRegistrationIds.Items.BONE_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.BONE, 1, -2.8f)));
    public static final RegistryObject<Item> BONE_SHOVEL = registerItem(GeneratedRegistrationIds.Items.BONE_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.BONE, 1.5f, -3f)));
    public static final RegistryObject<Item> BONE_AXE = registerItem(GeneratedRegistrationIds.Items.BONE_AXE,
            p -> new Item(p.axe(ModToolTiers.BONE, 6, -3.2f)));
    public static final RegistryObject<Item> BONE_HOE = registerItem(GeneratedRegistrationIds.Items.BONE_HOE,
            p -> new Item(p.hoe(ModToolTiers.BONE, 0, -3f)));
    public static final RegistryObject<Item> BONE_HELMET = registerItem(GeneratedRegistrationIds.Items.BONE_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.BONE_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final RegistryObject<Item> BONE_CHESTPLATE = registerItem(GeneratedRegistrationIds.Items.BONE_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.BONE_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final RegistryObject<Item> BONE_LEGGINGS = registerItem(GeneratedRegistrationIds.Items.BONE_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.BONE_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final RegistryObject<Item> BONE_BOOTS = registerItem(GeneratedRegistrationIds.Items.BONE_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.BONE_ARMOR_MATERIAL, ArmorType.BOOTS)));

    public static final RegistryObject<Item> CLAY_SWORD = registerItem(GeneratedRegistrationIds.Items.CLAY_SWORD,
            p -> new Item(p.sword(ModToolTiers.CLAY, 3, -2.4f)));
    public static final RegistryObject<Item> CLAY_PICKAXE = registerItem(GeneratedRegistrationIds.Items.CLAY_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.CLAY, 1, -2.8f)));
    public static final RegistryObject<Item> CLAY_SHOVEL = registerItem(GeneratedRegistrationIds.Items.CLAY_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.CLAY, 1.5f, -3f)));
    public static final RegistryObject<Item> CLAY_AXE = registerItem(GeneratedRegistrationIds.Items.CLAY_AXE,
            p -> new Item(p.axe(ModToolTiers.CLAY, 6, -3.2f)));
    public static final RegistryObject<Item> CLAY_HOE = registerItem(GeneratedRegistrationIds.Items.CLAY_HOE,
            p -> new Item(p.hoe(ModToolTiers.CLAY, 0, -3f)));
    public static final RegistryObject<Item> CLAY_HELMET = registerItem(GeneratedRegistrationIds.Items.CLAY_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.CLAY_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final RegistryObject<Item> CLAY_CHESTPLATE = registerItem(GeneratedRegistrationIds.Items.CLAY_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.CLAY_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final RegistryObject<Item> CLAY_LEGGINGS = registerItem(GeneratedRegistrationIds.Items.CLAY_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.CLAY_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final RegistryObject<Item> CLAY_BOOTS = registerItem(GeneratedRegistrationIds.Items.CLAY_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.CLAY_ARMOR_MATERIAL, ArmorType.BOOTS)));

    public static final RegistryObject<Item> NETHER_WART_SWORD = registerItem(GeneratedRegistrationIds.Items.NETHER_WART_SWORD,
            p -> new Item(p.sword(ModToolTiers.NETHER_WART, 3, -2.4f)));
    public static final RegistryObject<Item> NETHER_WART_PICKAXE = registerItem(GeneratedRegistrationIds.Items.NETHER_WART_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.NETHER_WART, 1, -2.8f)));
    public static final RegistryObject<Item> NETHER_WART_SHOVEL = registerItem(GeneratedRegistrationIds.Items.NETHER_WART_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.NETHER_WART, 1.5f, -3f)));
    public static final RegistryObject<Item> NETHER_WART_AXE = registerItem(GeneratedRegistrationIds.Items.NETHER_WART_AXE,
            p -> new Item(p.axe(ModToolTiers.NETHER_WART, 6, -3.2f)));
    public static final RegistryObject<Item> NETHER_WART_HOE = registerItem(GeneratedRegistrationIds.Items.NETHER_WART_HOE,
            p -> new Item(p.hoe(ModToolTiers.NETHER_WART, 0, -3f)));

    public static final RegistryObject<Item> BRICK_SWORD = registerItem(GeneratedRegistrationIds.Items.BRICK_SWORD,
            p -> new Item(p.sword(ModToolTiers.BRICK, 3, -2.4f)));
    public static final RegistryObject<Item> BRICK_PICKAXE = registerItem(GeneratedRegistrationIds.Items.BRICK_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.BRICK, 1, -2.8f)));
    public static final RegistryObject<Item> BRICK_SHOVEL = registerItem(GeneratedRegistrationIds.Items.BRICK_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.BRICK, 1.5f, -3f)));
    public static final RegistryObject<Item> BRICK_AXE = registerItem(GeneratedRegistrationIds.Items.BRICK_AXE,
            p -> new Item(p.axe(ModToolTiers.BRICK, 6, -3.2f)));
    public static final RegistryObject<Item> BRICK_HOE = registerItem(GeneratedRegistrationIds.Items.BRICK_HOE,
            p -> new Item(p.hoe(ModToolTiers.BRICK, 0, -3f)));
    public static final RegistryObject<Item> BRICK_HELMET = registerItem(GeneratedRegistrationIds.Items.BRICK_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.BRICK_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final RegistryObject<Item> BRICK_CHESTPLATE = registerItem(GeneratedRegistrationIds.Items.BRICK_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.BRICK_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final RegistryObject<Item> BRICK_LEGGINGS = registerItem(GeneratedRegistrationIds.Items.BRICK_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.BRICK_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final RegistryObject<Item> BRICK_BOOTS = registerItem(GeneratedRegistrationIds.Items.BRICK_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.BRICK_ARMOR_MATERIAL, ArmorType.BOOTS)));

    public static final RegistryObject<Item> NETHER_BRICK_SWORD = registerItem(GeneratedRegistrationIds.Items.NETHER_BRICK_SWORD,
            p -> new Item(p.sword(ModToolTiers.NETHER_BRICK, 3, -2.4f)));
    public static final RegistryObject<Item> NETHER_BRICK_PICKAXE = registerItem(GeneratedRegistrationIds.Items.NETHER_BRICK_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.NETHER_BRICK, 1, -2.8f)));
    public static final RegistryObject<Item> NETHER_BRICK_SHOVEL = registerItem(GeneratedRegistrationIds.Items.NETHER_BRICK_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.NETHER_BRICK, 1.5f, -3f)));
    public static final RegistryObject<Item> NETHER_BRICK_AXE = registerItem(GeneratedRegistrationIds.Items.NETHER_BRICK_AXE,
            p -> new Item(p.axe(ModToolTiers.NETHER_BRICK, 6, -3.2f)));
    public static final RegistryObject<Item> NETHER_BRICK_HOE = registerItem(GeneratedRegistrationIds.Items.NETHER_BRICK_HOE,
            p -> new Item(p.hoe(ModToolTiers.NETHER_BRICK, 0, -3f)));
    public static final RegistryObject<Item> NETHER_BRICK_HELMET = registerItem(GeneratedRegistrationIds.Items.NETHER_BRICK_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.NETHER_BRICK_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final RegistryObject<Item> NETHER_BRICK_CHESTPLATE = registerItem(GeneratedRegistrationIds.Items.NETHER_BRICK_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.NETHER_BRICK_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final RegistryObject<Item> NETHER_BRICK_LEGGINGS = registerItem(GeneratedRegistrationIds.Items.NETHER_BRICK_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.NETHER_BRICK_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final RegistryObject<Item> NETHER_BRICK_BOOTS = registerItem(GeneratedRegistrationIds.Items.NETHER_BRICK_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.NETHER_BRICK_ARMOR_MATERIAL, ArmorType.BOOTS)));

    public static final RegistryObject<Item> POINTED_DRIPSTONE_SWORD = registerItem(GeneratedRegistrationIds.Items.POINTED_DRIPSTONE_SWORD,
            p -> new Item(p.sword(ModToolTiers.POINTED_DRIPSTONE, 3, -2.4f)));
    public static final RegistryObject<Item> POINTED_DRIPSTONE_PICKAXE = registerItem(GeneratedRegistrationIds.Items.POINTED_DRIPSTONE_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.POINTED_DRIPSTONE, 1, -2.8f)));
    public static final RegistryObject<Item> POINTED_DRIPSTONE_SHOVEL = registerItem(GeneratedRegistrationIds.Items.POINTED_DRIPSTONE_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.POINTED_DRIPSTONE, 1.5f, -3f)));
    public static final RegistryObject<Item> POINTED_DRIPSTONE_AXE = registerItem(GeneratedRegistrationIds.Items.POINTED_DRIPSTONE_AXE,
            p -> new Item(p.axe(ModToolTiers.POINTED_DRIPSTONE, 6, -3.2f)));
    public static final RegistryObject<Item> POINTED_DRIPSTONE_HOE = registerItem(GeneratedRegistrationIds.Items.POINTED_DRIPSTONE_HOE,
            p -> new Item(p.hoe(ModToolTiers.POINTED_DRIPSTONE, 0, -3f)));

    public static final RegistryObject<Item> COPPER_SWORD = registerItem(GeneratedRegistrationIds.Items.COPPER_SWORD,
            p -> new Item(p.sword(ModToolTiers.COPPER, 3, -2.4f)));
    public static final RegistryObject<Item> COPPER_PICKAXE = registerItem(GeneratedRegistrationIds.Items.COPPER_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.COPPER, 1, -2.8f)));
    public static final RegistryObject<Item> COPPER_SHOVEL = registerItem(GeneratedRegistrationIds.Items.COPPER_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.COPPER, 1.5f, -3f)));
    public static final RegistryObject<Item> COPPER_AXE = registerItem(GeneratedRegistrationIds.Items.COPPER_AXE,
            p -> new Item(p.axe(ModToolTiers.COPPER, 6, -3.2f)));
    public static final RegistryObject<Item> COPPER_HOE = registerItem(GeneratedRegistrationIds.Items.COPPER_HOE,
            p -> new Item(p.hoe(ModToolTiers.COPPER, 0, -3f)));
    public static final RegistryObject<Item> COPPER_HELMET = registerItem(GeneratedRegistrationIds.Items.COPPER_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.COPPER_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final RegistryObject<Item> COPPER_CHESTPLATE = registerItem(GeneratedRegistrationIds.Items.COPPER_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.COPPER_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final RegistryObject<Item> COPPER_LEGGINGS = registerItem(GeneratedRegistrationIds.Items.COPPER_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.COPPER_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final RegistryObject<Item> COPPER_BOOTS = registerItem(GeneratedRegistrationIds.Items.COPPER_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.COPPER_ARMOR_MATERIAL, ArmorType.BOOTS)));

    public static final RegistryObject<Item> PHANTOM_SWORD = registerItem(GeneratedRegistrationIds.Items.PHANTOM_SWORD,
            p -> new Item(p.sword(ModToolTiers.PHANTOM_MEMBRANE, 3, -2.4f)));
    public static final RegistryObject<Item> PHANTOM_PICKAXE = registerItem(GeneratedRegistrationIds.Items.PHANTOM_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.PHANTOM_MEMBRANE, 1, -2.8f)));
    public static final RegistryObject<Item> PHANTOM_SHOVEL = registerItem(GeneratedRegistrationIds.Items.PHANTOM_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.PHANTOM_MEMBRANE, 1.5f, -3f)));
    public static final RegistryObject<Item> PHANTOM_AXE = registerItem(GeneratedRegistrationIds.Items.PHANTOM_AXE,
            p -> new Item(p.axe(ModToolTiers.PHANTOM_MEMBRANE, 6, -3.2f)));
    public static final RegistryObject<Item> PHANTOM_HOE = registerItem(GeneratedRegistrationIds.Items.PHANTOM_HOE,
            p -> new Item(p.hoe(ModToolTiers.PHANTOM_MEMBRANE, 0, -3f)));
    public static final RegistryObject<Item> PHANTOM_HELMET = registerItem(GeneratedRegistrationIds.Items.PHANTOM_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.PHANTOM_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final RegistryObject<Item> PHANTOM_CHESTPLATE = registerItem(GeneratedRegistrationIds.Items.PHANTOM_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.PHANTOM_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final RegistryObject<Item> PHANTOM_LEGGINGS = registerItem(GeneratedRegistrationIds.Items.PHANTOM_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.PHANTOM_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final RegistryObject<Item> PHANTOM_BOOTS = registerItem(GeneratedRegistrationIds.Items.PHANTOM_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.PHANTOM_ARMOR_MATERIAL, ArmorType.BOOTS)));

    public static final RegistryObject<Item> MAGMA_CREAM_SWORD = registerItem(GeneratedRegistrationIds.Items.MAGMA_CREAM_SWORD,
            p -> new Item(p.sword(ModToolTiers.MAGMA_CREAM, 3, -2.4f)));
    public static final RegistryObject<Item> MAGMA_CREAM_PICKAXE = registerItem(GeneratedRegistrationIds.Items.MAGMA_CREAM_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.MAGMA_CREAM, 1, -2.8f)));
    public static final RegistryObject<Item> MAGMA_CREAM_SHOVEL = registerItem(GeneratedRegistrationIds.Items.MAGMA_CREAM_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.MAGMA_CREAM, 1.5f, -3f)));
    public static final RegistryObject<Item> MAGMA_CREAM_AXE = registerItem(GeneratedRegistrationIds.Items.MAGMA_CREAM_AXE,
            p -> new Item(p.axe(ModToolTiers.MAGMA_CREAM, 6, -3.2f)));
    public static final RegistryObject<Item> MAGMA_CREAM_HOE = registerItem(GeneratedRegistrationIds.Items.MAGMA_CREAM_HOE,
            p -> new Item(p.hoe(ModToolTiers.MAGMA_CREAM, 0, -3f)));
    public static final RegistryObject<Item> MAGMA_CREAM_HELMET = registerItem(GeneratedRegistrationIds.Items.MAGMA_CREAM_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.MAGMA_CREAM_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final RegistryObject<Item> MAGMA_CREAM_CHESTPLATE = registerItem(GeneratedRegistrationIds.Items.MAGMA_CREAM_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.MAGMA_CREAM_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final RegistryObject<Item> MAGMA_CREAM_LEGGINGS = registerItem(GeneratedRegistrationIds.Items.MAGMA_CREAM_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.MAGMA_CREAM_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final RegistryObject<Item> MAGMA_CREAM_BOOTS = registerItem(GeneratedRegistrationIds.Items.MAGMA_CREAM_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.MAGMA_CREAM_ARMOR_MATERIAL, ArmorType.BOOTS)));

    public static final RegistryObject<Item> SLIME_SWORD = registerItem(GeneratedRegistrationIds.Items.SLIME_SWORD,
            p -> new Item(p.sword(ModToolTiers.SLIME, 3, -2.4f)));
    public static final RegistryObject<Item> SLIME_PICKAXE = registerItem(GeneratedRegistrationIds.Items.SLIME_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.SLIME, 1, -2.8f)));
    public static final RegistryObject<Item> SLIME_SHOVEL = registerItem(GeneratedRegistrationIds.Items.SLIME_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.SLIME, 1.5f, -3f)));
    public static final RegistryObject<Item> SLIME_AXE = registerItem(GeneratedRegistrationIds.Items.SLIME_AXE,
            p -> new Item(p.axe(ModToolTiers.SLIME, 6, -3.2f)));
    public static final RegistryObject<Item> SLIME_HOE = registerItem(GeneratedRegistrationIds.Items.SLIME_HOE,
            p -> new Item(p.hoe(ModToolTiers.SLIME, 0, -3f)));
    public static final RegistryObject<Item> SLIME_HELMET = registerItem(GeneratedRegistrationIds.Items.SLIME_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.SLIME_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final RegistryObject<Item> SLIME_CHESTPLATE = registerItem(GeneratedRegistrationIds.Items.SLIME_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.SLIME_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final RegistryObject<Item> SLIME_LEGGINGS = registerItem(GeneratedRegistrationIds.Items.SLIME_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.SLIME_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final RegistryObject<Item> SLIME_BOOTS = registerItem(GeneratedRegistrationIds.Items.SLIME_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.SLIME_ARMOR_MATERIAL, ArmorType.BOOTS)));

    public static final RegistryObject<Item> BLAZE_SWORD = registerItem(GeneratedRegistrationIds.Items.BLAZE_SWORD,
            p -> new Item(p.sword(ModToolTiers.BLAZE_ROD, 3, -2.4f)));
    public static final RegistryObject<Item> BLAZE_PICKAXE = registerItem(GeneratedRegistrationIds.Items.BLAZE_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.BLAZE_ROD, 1, -2.8f)));
    public static final RegistryObject<Item> BLAZE_SHOVEL = registerItem(GeneratedRegistrationIds.Items.BLAZE_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.BLAZE_ROD, 1.5f, -3f)));
    public static final RegistryObject<Item> BLAZE_AXE = registerItem(GeneratedRegistrationIds.Items.BLAZE_AXE,
            p -> new Item(p.axe(ModToolTiers.BLAZE_ROD, 6, -3.2f)));
    public static final RegistryObject<Item> BLAZE_HOE = registerItem(GeneratedRegistrationIds.Items.BLAZE_HOE,
            p -> new Item(p.hoe(ModToolTiers.BLAZE_ROD, 0, -3f)));
    public static final RegistryObject<Item> BLAZE_HELMET = registerItem(GeneratedRegistrationIds.Items.BLAZE_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.BLAZE_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final RegistryObject<Item> BLAZE_CHESTPLATE = registerItem(GeneratedRegistrationIds.Items.BLAZE_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.BLAZE_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final RegistryObject<Item> BLAZE_LEGGINGS = registerItem(GeneratedRegistrationIds.Items.BLAZE_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.BLAZE_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final RegistryObject<Item> BLAZE_BOOTS = registerItem(GeneratedRegistrationIds.Items.BLAZE_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.BLAZE_ARMOR_MATERIAL, ArmorType.BOOTS)));

    public static final RegistryObject<Item> NAUTILUS_SWORD = registerItem(GeneratedRegistrationIds.Items.NAUTILUS_SWORD,
            p -> new Item(p.sword(ModToolTiers.NAUTILUS_SHELL, 3, -2.4f)));
    public static final RegistryObject<Item> NAUTILUS_PICKAXE = registerItem(GeneratedRegistrationIds.Items.NAUTILUS_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.NAUTILUS_SHELL, 1, -2.8f)));
    public static final RegistryObject<Item> NAUTILUS_SHOVEL = registerItem(GeneratedRegistrationIds.Items.NAUTILUS_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.NAUTILUS_SHELL, 1.5f, -3f)));
    public static final RegistryObject<Item> NAUTILUS_AXE = registerItem(GeneratedRegistrationIds.Items.NAUTILUS_AXE,
            p -> new Item(p.axe(ModToolTiers.NAUTILUS_SHELL, 6, -3.2f)));
    public static final RegistryObject<Item> NAUTILUS_HOE = registerItem(GeneratedRegistrationIds.Items.NAUTILUS_HOE,
            p -> new Item(p.hoe(ModToolTiers.NAUTILUS_SHELL, 0, -3f)));
    public static final RegistryObject<Item> NAUTILUS_HELMET = registerItem(GeneratedRegistrationIds.Items.NAUTILUS_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.NAUTILUS_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final RegistryObject<Item> NAUTILUS_CHESTPLATE = registerItem(GeneratedRegistrationIds.Items.NAUTILUS_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.NAUTILUS_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final RegistryObject<Item> NAUTILUS_LEGGINGS = registerItem(GeneratedRegistrationIds.Items.NAUTILUS_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.NAUTILUS_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final RegistryObject<Item> NAUTILUS_BOOTS = registerItem(GeneratedRegistrationIds.Items.NAUTILUS_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.NAUTILUS_ARMOR_MATERIAL, ArmorType.BOOTS)));

    public static final RegistryObject<Item> PURPUR_SWORD = registerItem(GeneratedRegistrationIds.Items.PURPUR_SWORD,
            p -> new Item(p.sword(ModToolTiers.PURPUR, 3, -2.4f)));
    public static final RegistryObject<Item> PURPUR_PICKAXE = registerItem(GeneratedRegistrationIds.Items.PURPUR_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.PURPUR, 1, -2.8f)));
    public static final RegistryObject<Item> PURPUR_SHOVEL = registerItem(GeneratedRegistrationIds.Items.PURPUR_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.PURPUR, 1.5f, -3f)));
    public static final RegistryObject<Item> PURPUR_AXE = registerItem(GeneratedRegistrationIds.Items.PURPUR_AXE,
            p -> new Item(p.axe(ModToolTiers.PURPUR, 6, -3.2f)));
    public static final RegistryObject<Item> PURPUR_HOE = registerItem(GeneratedRegistrationIds.Items.PURPUR_HOE,
            p -> new Item(p.hoe(ModToolTiers.PURPUR, 0, -3f)));
    public static final RegistryObject<Item> PURPUR_HELMET = registerItem(GeneratedRegistrationIds.Items.PURPUR_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.PURPUR_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final RegistryObject<Item> PURPUR_CHESTPLATE = registerItem(GeneratedRegistrationIds.Items.PURPUR_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.PURPUR_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final RegistryObject<Item> PURPUR_LEGGINGS = registerItem(GeneratedRegistrationIds.Items.PURPUR_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.PURPUR_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final RegistryObject<Item> PURPUR_BOOTS = registerItem(GeneratedRegistrationIds.Items.PURPUR_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.PURPUR_ARMOR_MATERIAL, ArmorType.BOOTS)));

    public static final RegistryObject<Item> GHAST_TEAR_SWORD = registerItem(GeneratedRegistrationIds.Items.GHAST_TEAR_SWORD,
            p -> new Item(p.sword(ModToolTiers.GHAST_TEAR, 3, -2.4f)));
    public static final RegistryObject<Item> GHAST_TEAR_PICKAXE = registerItem(GeneratedRegistrationIds.Items.GHAST_TEAR_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.GHAST_TEAR, 1, -2.8f)));
    public static final RegistryObject<Item> GHAST_TEAR_SHOVEL = registerItem(GeneratedRegistrationIds.Items.GHAST_TEAR_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.GHAST_TEAR, 1.5f, -3f)));
    public static final RegistryObject<Item> GHAST_TEAR_AXE = registerItem(GeneratedRegistrationIds.Items.GHAST_TEAR_AXE,
            p -> new Item(p.axe(ModToolTiers.GHAST_TEAR, 6, -3.2f)));
    public static final RegistryObject<Item> GHAST_TEAR_HOE = registerItem(GeneratedRegistrationIds.Items.GHAST_TEAR_HOE,
            p -> new Item(p.hoe(ModToolTiers.GHAST_TEAR, 0, -3f)));
    public static final RegistryObject<Item> GHAST_TEAR_HELMET = registerItem(GeneratedRegistrationIds.Items.GHAST_TEAR_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.GHAST_TEAR_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final RegistryObject<Item> GHAST_TEAR_CHESTPLATE = registerItem(GeneratedRegistrationIds.Items.GHAST_TEAR_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.GHAST_TEAR_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final RegistryObject<Item> GHAST_TEAR_LEGGINGS = registerItem(GeneratedRegistrationIds.Items.GHAST_TEAR_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.GHAST_TEAR_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final RegistryObject<Item> GHAST_TEAR_BOOTS = registerItem(GeneratedRegistrationIds.Items.GHAST_TEAR_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.GHAST_TEAR_ARMOR_MATERIAL, ArmorType.BOOTS)));

    public static final RegistryObject<Item> EYE_OF_ENDER_SWORD = registerItem(GeneratedRegistrationIds.Items.EYE_OF_ENDER_SWORD,
            p -> new Item(p.sword(ModToolTiers.EYE_OF_ENDER, 3, -2.4f)));
    public static final RegistryObject<Item> EYE_OF_ENDER_PICKAXE = registerItem(GeneratedRegistrationIds.Items.EYE_OF_ENDER_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.EYE_OF_ENDER, 1, -2.8f)));
    public static final RegistryObject<Item> EYE_OF_ENDER_SHOVEL = registerItem(GeneratedRegistrationIds.Items.EYE_OF_ENDER_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.EYE_OF_ENDER, 1.5f, -3f)));
    public static final RegistryObject<Item> EYE_OF_ENDER_AXE = registerItem(GeneratedRegistrationIds.Items.EYE_OF_ENDER_AXE,
            p -> new Item(p.axe(ModToolTiers.EYE_OF_ENDER, 6, -3.2f)));
    public static final RegistryObject<Item> EYE_OF_ENDER_HOE = registerItem(GeneratedRegistrationIds.Items.EYE_OF_ENDER_HOE,
            p -> new Item(p.hoe(ModToolTiers.EYE_OF_ENDER, 0, -3f)));
    public static final RegistryObject<Item> EYE_OF_ENDER_HELMET = registerItem(GeneratedRegistrationIds.Items.EYE_OF_ENDER_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.EYE_OF_ENDER_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final RegistryObject<Item> EYE_OF_ENDER_CHESTPLATE = registerItem(GeneratedRegistrationIds.Items.EYE_OF_ENDER_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.EYE_OF_ENDER_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final RegistryObject<Item> EYE_OF_ENDER_LEGGINGS = registerItem(GeneratedRegistrationIds.Items.EYE_OF_ENDER_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.EYE_OF_ENDER_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final RegistryObject<Item> EYE_OF_ENDER_BOOTS = registerItem(GeneratedRegistrationIds.Items.EYE_OF_ENDER_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.EYE_OF_ENDER_ARMOR_MATERIAL, ArmorType.BOOTS)));

    public static final RegistryObject<Item> SHULKER_SWORD = registerItem(GeneratedRegistrationIds.Items.SHULKER_SWORD,
            p -> new Item(p.sword(ModToolTiers.SHULKER_SHELL, 3, -2.4f)));
    public static final RegistryObject<Item> SHULKER_PICKAXE = registerItem(GeneratedRegistrationIds.Items.SHULKER_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.SHULKER_SHELL, 1, -2.8f)));
    public static final RegistryObject<Item> SHULKER_SHOVEL = registerItem(GeneratedRegistrationIds.Items.SHULKER_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.SHULKER_SHELL, 1.5f, -3f)));
    public static final RegistryObject<Item> SHULKER_AXE = registerItem(GeneratedRegistrationIds.Items.SHULKER_AXE,
            p -> new Item(p.axe(ModToolTiers.SHULKER_SHELL, 6, -3.2f)));
    public static final RegistryObject<Item> SHULKER_HOE = registerItem(GeneratedRegistrationIds.Items.SHULKER_HOE,
            p -> new Item(p.hoe(ModToolTiers.SHULKER_SHELL, 0, -3f)));
    public static final RegistryObject<Item> SHULKER_HELMET = registerItem(GeneratedRegistrationIds.Items.SHULKER_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.SHULKER_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final RegistryObject<Item> SHULKER_CHESTPLATE = registerItem(GeneratedRegistrationIds.Items.SHULKER_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.SHULKER_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final RegistryObject<Item> SHULKER_LEGGINGS = registerItem(GeneratedRegistrationIds.Items.SHULKER_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.SHULKER_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final RegistryObject<Item> SHULKER_BOOTS = registerItem(GeneratedRegistrationIds.Items.SHULKER_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.SHULKER_ARMOR_MATERIAL, ArmorType.BOOTS)));

    public static final RegistryObject<Item> TURTLE_SCUTE_HELMET = registerItem(GeneratedRegistrationIds.Items.TURTLE_SCUTE_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.TURTLE_SCUTE_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final RegistryObject<Item> TURTLE_SCUTE_CHESTPLATE = registerItem(GeneratedRegistrationIds.Items.TURTLE_SCUTE_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.TURTLE_SCUTE_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final RegistryObject<Item> TURTLE_SCUTE_LEGGINGS = registerItem(GeneratedRegistrationIds.Items.TURTLE_SCUTE_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.TURTLE_SCUTE_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final RegistryObject<Item> TURTLE_SCUTE_BOOTS = registerItem(GeneratedRegistrationIds.Items.TURTLE_SCUTE_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.TURTLE_SCUTE_ARMOR_MATERIAL, ArmorType.BOOTS)));

    public static final RegistryObject<Item> ECHO_SHARD_SWORD = registerItem(GeneratedRegistrationIds.Items.ECHO_SHARD_SWORD,
            p -> new Item(p.sword(ModToolTiers.ECHO_SHARD, 3, -2.4f)));
    public static final RegistryObject<Item> ECHO_SHARD_PICKAXE = registerItem(GeneratedRegistrationIds.Items.ECHO_SHARD_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.ECHO_SHARD, 1, -2.8f)));
    public static final RegistryObject<Item> ECHO_SHARD_SHOVEL = registerItem(GeneratedRegistrationIds.Items.ECHO_SHARD_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.ECHO_SHARD, 1.5f, -3f)));
    public static final RegistryObject<Item> ECHO_SHARD_AXE = registerItem(GeneratedRegistrationIds.Items.ECHO_SHARD_AXE,
            p -> new Item(p.axe(ModToolTiers.ECHO_SHARD, 6, -3.2f)));
    public static final RegistryObject<Item> ECHO_SHARD_HOE = registerItem(GeneratedRegistrationIds.Items.ECHO_SHARD_HOE,
            p -> new Item(p.hoe(ModToolTiers.ECHO_SHARD, 0, -3f)));
    public static final RegistryObject<Item> ECHO_SHARD_HELMET = registerItem(GeneratedRegistrationIds.Items.ECHO_SHARD_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.ECHO_SHARD_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final RegistryObject<Item> ECHO_SHARD_CHESTPLATE = registerItem(GeneratedRegistrationIds.Items.ECHO_SHARD_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.ECHO_SHARD_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final RegistryObject<Item> ECHO_SHARD_LEGGINGS = registerItem(GeneratedRegistrationIds.Items.ECHO_SHARD_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.ECHO_SHARD_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final RegistryObject<Item> ECHO_SHARD_BOOTS = registerItem(GeneratedRegistrationIds.Items.ECHO_SHARD_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.ECHO_SHARD_ARMOR_MATERIAL, ArmorType.BOOTS)));

    public static final RegistryObject<Item> DRAGON_BREATH_SWORD = registerItem(GeneratedRegistrationIds.Items.DRAGON_BREATH_SWORD,
            p -> new Item(p.sword(ModToolTiers.DRAGON_BREATH, 3, -2.4f)));
    public static final RegistryObject<Item> DRAGON_BREATH_PICKAXE = registerItem(GeneratedRegistrationIds.Items.DRAGON_BREATH_PICKAXE,
            p -> new Item(p.pickaxe(ModToolTiers.DRAGON_BREATH, 1, -2.8f)));
    public static final RegistryObject<Item> DRAGON_BREATH_SHOVEL = registerItem(GeneratedRegistrationIds.Items.DRAGON_BREATH_SHOVEL,
            p -> new Item(p.shovel(ModToolTiers.DRAGON_BREATH, 1.5f, -3f)));
    public static final RegistryObject<Item> DRAGON_BREATH_AXE = registerItem(GeneratedRegistrationIds.Items.DRAGON_BREATH_AXE,
            p -> new Item(p.axe(ModToolTiers.DRAGON_BREATH, 6, -3.2f)));
    public static final RegistryObject<Item> DRAGON_BREATH_HOE = registerItem(GeneratedRegistrationIds.Items.DRAGON_BREATH_HOE,
            p -> new Item(p.hoe(ModToolTiers.DRAGON_BREATH, 0, -3f)));
    public static final RegistryObject<Item> DRAGON_BREATH_HELMET = registerItem(GeneratedRegistrationIds.Items.DRAGON_BREATH_HELMET,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.DRAGON_BREATH_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final RegistryObject<Item> DRAGON_BREATH_CHESTPLATE = registerItem(GeneratedRegistrationIds.Items.DRAGON_BREATH_CHESTPLATE,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.DRAGON_BREATH_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final RegistryObject<Item> DRAGON_BREATH_LEGGINGS = registerItem(GeneratedRegistrationIds.Items.DRAGON_BREATH_LEGGINGS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.DRAGON_BREATH_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final RegistryObject<Item> DRAGON_BREATH_BOOTS = registerItem(GeneratedRegistrationIds.Items.DRAGON_BREATH_BOOTS,
            p -> new Item(p.humanoidArmor(ModArmorMaterials.DRAGON_BREATH_ARMOR_MATERIAL, ArmorType.BOOTS)));

    // ── Edible food-themed sets ────────────────────────────────────────────
    // Cake
    public static final RegistryObject<Item> CAKE_SWORD = registerItem(GeneratedRegistrationIds.Items.CAKE_SWORD,
            p -> new EdibleSwordItem(p.sword(ModToolTiers.CAKE, 3, -2.4f).food(food(4))));
    public static final RegistryObject<Item> CAKE_PICKAXE = registerItem(GeneratedRegistrationIds.Items.CAKE_PICKAXE,
            p -> new EdiblePickaxeItem(p.pickaxe(ModToolTiers.CAKE, 1, -2.8f).food(food(6))));
    public static final RegistryObject<Item> CAKE_SHOVEL = registerItem(GeneratedRegistrationIds.Items.CAKE_SHOVEL,
            p -> new EdibleShovelItem(p.shovel(ModToolTiers.CAKE, 1.5f, -3f).food(food(2))));
    public static final RegistryObject<Item> CAKE_AXE = registerItem(GeneratedRegistrationIds.Items.CAKE_AXE,
            p -> new EdibleAxeItem(p.axe(ModToolTiers.CAKE, 6, -3.2f).food(food(6))));
    public static final RegistryObject<Item> CAKE_HOE = registerItem(GeneratedRegistrationIds.Items.CAKE_HOE,
            p -> new EdibleHoeItem(p.hoe(ModToolTiers.CAKE, 0, -3f).food(food(4))));
    public static final RegistryObject<Item> CAKE_HELMET = registerItem(GeneratedRegistrationIds.Items.CAKE_HELMET,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.CAKE_ARMOR_MATERIAL, ArmorType.HELMET).food(food(10))));
    public static final RegistryObject<Item> CAKE_CHESTPLATE = registerItem(GeneratedRegistrationIds.Items.CAKE_CHESTPLATE,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.CAKE_ARMOR_MATERIAL, ArmorType.CHESTPLATE).food(food(14))));
    public static final RegistryObject<Item> CAKE_LEGGINGS = registerItem(GeneratedRegistrationIds.Items.CAKE_LEGGINGS,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.CAKE_ARMOR_MATERIAL, ArmorType.LEGGINGS).food(food(14))));
    public static final RegistryObject<Item> CAKE_BOOTS = registerItem(GeneratedRegistrationIds.Items.CAKE_BOOTS,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.CAKE_ARMOR_MATERIAL, ArmorType.BOOTS).food(food(8))));

    // Bread
    public static final RegistryObject<Item> BREAD_SWORD = registerItem(GeneratedRegistrationIds.Items.BREAD_SWORD,
            p -> new EdibleSwordItem(p.sword(ModToolTiers.BREAD, 3, -2.4f).food(food(4))));
    public static final RegistryObject<Item> BREAD_PICKAXE = registerItem(GeneratedRegistrationIds.Items.BREAD_PICKAXE,
            p -> new EdiblePickaxeItem(p.pickaxe(ModToolTiers.BREAD, 1, -2.8f).food(food(6))));
    public static final RegistryObject<Item> BREAD_SHOVEL = registerItem(GeneratedRegistrationIds.Items.BREAD_SHOVEL,
            p -> new EdibleShovelItem(p.shovel(ModToolTiers.BREAD, 1.5f, -3f).food(food(2))));
    public static final RegistryObject<Item> BREAD_AXE = registerItem(GeneratedRegistrationIds.Items.BREAD_AXE,
            p -> new EdibleAxeItem(p.axe(ModToolTiers.BREAD, 6, -3.2f).food(food(6))));
    public static final RegistryObject<Item> BREAD_HOE = registerItem(GeneratedRegistrationIds.Items.BREAD_HOE,
            p -> new EdibleHoeItem(p.hoe(ModToolTiers.BREAD, 0, -3f).food(food(4))));
    public static final RegistryObject<Item> BREAD_HELMET = registerItem(GeneratedRegistrationIds.Items.BREAD_HELMET,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.BREAD_ARMOR_MATERIAL, ArmorType.HELMET).food(food(10))));
    public static final RegistryObject<Item> BREAD_CHESTPLATE = registerItem(GeneratedRegistrationIds.Items.BREAD_CHESTPLATE,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.BREAD_ARMOR_MATERIAL, ArmorType.CHESTPLATE).food(food(14))));
    public static final RegistryObject<Item> BREAD_LEGGINGS = registerItem(GeneratedRegistrationIds.Items.BREAD_LEGGINGS,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.BREAD_ARMOR_MATERIAL, ArmorType.LEGGINGS).food(food(14))));
    public static final RegistryObject<Item> BREAD_BOOTS = registerItem(GeneratedRegistrationIds.Items.BREAD_BOOTS,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.BREAD_ARMOR_MATERIAL, ArmorType.BOOTS).food(food(8))));

    // Dried Kelp
    public static final RegistryObject<Item> DRIED_KELP_SWORD = registerItem(GeneratedRegistrationIds.Items.DRIED_KELP_SWORD,
            p -> new EdibleSwordItem(p.sword(ModToolTiers.DRIED_KELP, 3, -2.4f).food(food(4))));
    public static final RegistryObject<Item> DRIED_KELP_PICKAXE = registerItem(GeneratedRegistrationIds.Items.DRIED_KELP_PICKAXE,
            p -> new EdiblePickaxeItem(p.pickaxe(ModToolTiers.DRIED_KELP, 1, -2.8f).food(food(6))));
    public static final RegistryObject<Item> DRIED_KELP_SHOVEL = registerItem(GeneratedRegistrationIds.Items.DRIED_KELP_SHOVEL,
            p -> new EdibleShovelItem(p.shovel(ModToolTiers.DRIED_KELP, 1.5f, -3f).food(food(2))));
    public static final RegistryObject<Item> DRIED_KELP_AXE = registerItem(GeneratedRegistrationIds.Items.DRIED_KELP_AXE,
            p -> new EdibleAxeItem(p.axe(ModToolTiers.DRIED_KELP, 6, -3.2f).food(food(6))));
    public static final RegistryObject<Item> DRIED_KELP_HOE = registerItem(GeneratedRegistrationIds.Items.DRIED_KELP_HOE,
            p -> new EdibleHoeItem(p.hoe(ModToolTiers.DRIED_KELP, 0, -3f).food(food(4))));
    public static final RegistryObject<Item> DRIED_KELP_HELMET = registerItem(GeneratedRegistrationIds.Items.DRIED_KELP_HELMET,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.DRIED_KELP_ARMOR_MATERIAL, ArmorType.HELMET).food(food(10))));
    public static final RegistryObject<Item> DRIED_KELP_CHESTPLATE = registerItem(GeneratedRegistrationIds.Items.DRIED_KELP_CHESTPLATE,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.DRIED_KELP_ARMOR_MATERIAL, ArmorType.CHESTPLATE).food(food(14))));
    public static final RegistryObject<Item> DRIED_KELP_LEGGINGS = registerItem(GeneratedRegistrationIds.Items.DRIED_KELP_LEGGINGS,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.DRIED_KELP_ARMOR_MATERIAL, ArmorType.LEGGINGS).food(food(14))));
    public static final RegistryObject<Item> DRIED_KELP_BOOTS = registerItem(GeneratedRegistrationIds.Items.DRIED_KELP_BOOTS,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.DRIED_KELP_ARMOR_MATERIAL, ArmorType.BOOTS).food(food(8))));

    // Rotten Flesh
    public static final RegistryObject<Item> ROTTEN_FLESH_SWORD = registerItem(GeneratedRegistrationIds.Items.ROTTEN_FLESH_SWORD,
            p -> new EdibleSwordItem(p.sword(ModToolTiers.ROTTEN_FLESH, 3, -2.4f).food(food(4))));
    public static final RegistryObject<Item> ROTTEN_FLESH_PICKAXE = registerItem(GeneratedRegistrationIds.Items.ROTTEN_FLESH_PICKAXE,
            p -> new EdiblePickaxeItem(p.pickaxe(ModToolTiers.ROTTEN_FLESH, 1, -2.8f).food(food(6))));
    public static final RegistryObject<Item> ROTTEN_FLESH_SHOVEL = registerItem(GeneratedRegistrationIds.Items.ROTTEN_FLESH_SHOVEL,
            p -> new EdibleShovelItem(p.shovel(ModToolTiers.ROTTEN_FLESH, 1.5f, -3f).food(food(2))));
    public static final RegistryObject<Item> ROTTEN_FLESH_AXE = registerItem(GeneratedRegistrationIds.Items.ROTTEN_FLESH_AXE,
            p -> new EdibleAxeItem(p.axe(ModToolTiers.ROTTEN_FLESH, 6, -3.2f).food(food(6))));
    public static final RegistryObject<Item> ROTTEN_FLESH_HOE = registerItem(GeneratedRegistrationIds.Items.ROTTEN_FLESH_HOE,
            p -> new EdibleHoeItem(p.hoe(ModToolTiers.ROTTEN_FLESH, 0, -3f).food(food(4))));
    public static final RegistryObject<Item> ROTTEN_FLESH_HELMET = registerItem(GeneratedRegistrationIds.Items.ROTTEN_FLESH_HELMET,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.ROTTEN_FLESH_ARMOR_MATERIAL, ArmorType.HELMET).food(food(10))));
    public static final RegistryObject<Item> ROTTEN_FLESH_CHESTPLATE = registerItem(GeneratedRegistrationIds.Items.ROTTEN_FLESH_CHESTPLATE,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.ROTTEN_FLESH_ARMOR_MATERIAL, ArmorType.CHESTPLATE).food(food(14))));
    public static final RegistryObject<Item> ROTTEN_FLESH_LEGGINGS = registerItem(GeneratedRegistrationIds.Items.ROTTEN_FLESH_LEGGINGS,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.ROTTEN_FLESH_ARMOR_MATERIAL, ArmorType.LEGGINGS).food(food(14))));
    public static final RegistryObject<Item> ROTTEN_FLESH_BOOTS = registerItem(GeneratedRegistrationIds.Items.ROTTEN_FLESH_BOOTS,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.ROTTEN_FLESH_ARMOR_MATERIAL, ArmorType.BOOTS).food(food(8))));

    // Melon
    public static final RegistryObject<Item> MELON_SWORD = registerItem(GeneratedRegistrationIds.Items.MELON_SWORD,
            p -> new EdibleSwordItem(p.sword(ModToolTiers.MELON, 3, -2.4f).food(food(4))));
    public static final RegistryObject<Item> MELON_PICKAXE = registerItem(GeneratedRegistrationIds.Items.MELON_PICKAXE,
            p -> new EdiblePickaxeItem(p.pickaxe(ModToolTiers.MELON, 1, -2.8f).food(food(6))));
    public static final RegistryObject<Item> MELON_SHOVEL = registerItem(GeneratedRegistrationIds.Items.MELON_SHOVEL,
            p -> new EdibleShovelItem(p.shovel(ModToolTiers.MELON, 1.5f, -3f).food(food(2))));
    public static final RegistryObject<Item> MELON_AXE = registerItem(GeneratedRegistrationIds.Items.MELON_AXE,
            p -> new EdibleAxeItem(p.axe(ModToolTiers.MELON, 6, -3.2f).food(food(6))));
    public static final RegistryObject<Item> MELON_HOE = registerItem(GeneratedRegistrationIds.Items.MELON_HOE,
            p -> new EdibleHoeItem(p.hoe(ModToolTiers.MELON, 0, -3f).food(food(4))));
    public static final RegistryObject<Item> MELON_HELMET = registerItem(GeneratedRegistrationIds.Items.MELON_HELMET,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.MELON_ARMOR_MATERIAL, ArmorType.HELMET).food(food(10))));
    public static final RegistryObject<Item> MELON_CHESTPLATE = registerItem(GeneratedRegistrationIds.Items.MELON_CHESTPLATE,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.MELON_ARMOR_MATERIAL, ArmorType.CHESTPLATE).food(food(14))));
    public static final RegistryObject<Item> MELON_LEGGINGS = registerItem(GeneratedRegistrationIds.Items.MELON_LEGGINGS,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.MELON_ARMOR_MATERIAL, ArmorType.LEGGINGS).food(food(14))));
    public static final RegistryObject<Item> MELON_BOOTS = registerItem(GeneratedRegistrationIds.Items.MELON_BOOTS,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.MELON_ARMOR_MATERIAL, ArmorType.BOOTS).food(food(8))));

    // Sweet Berries
    public static final RegistryObject<Item> SWEET_BERRY_SWORD = registerItem(GeneratedRegistrationIds.Items.SWEET_BERRY_SWORD,
            p -> new EdibleSwordItem(p.sword(ModToolTiers.SWEET_BERRIES, 3, -2.4f).food(food(4))));
    public static final RegistryObject<Item> SWEET_BERRY_PICKAXE = registerItem(GeneratedRegistrationIds.Items.SWEET_BERRY_PICKAXE,
            p -> new EdiblePickaxeItem(p.pickaxe(ModToolTiers.SWEET_BERRIES, 1, -2.8f).food(food(6))));
    public static final RegistryObject<Item> SWEET_BERRY_SHOVEL = registerItem(GeneratedRegistrationIds.Items.SWEET_BERRY_SHOVEL,
            p -> new EdibleShovelItem(p.shovel(ModToolTiers.SWEET_BERRIES, 1.5f, -3f).food(food(2))));
    public static final RegistryObject<Item> SWEET_BERRY_AXE = registerItem(GeneratedRegistrationIds.Items.SWEET_BERRY_AXE,
            p -> new EdibleAxeItem(p.axe(ModToolTiers.SWEET_BERRIES, 6, -3.2f).food(food(6))));
    public static final RegistryObject<Item> SWEET_BERRY_HOE = registerItem(GeneratedRegistrationIds.Items.SWEET_BERRY_HOE,
            p -> new EdibleHoeItem(p.hoe(ModToolTiers.SWEET_BERRIES, 0, -3f).food(food(4))));
    public static final RegistryObject<Item> SWEET_BERRY_HELMET = registerItem(GeneratedRegistrationIds.Items.SWEET_BERRY_HELMET,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.SWEET_BERRY_ARMOR_MATERIAL, ArmorType.HELMET).food(food(10))));
    public static final RegistryObject<Item> SWEET_BERRY_CHESTPLATE = registerItem(GeneratedRegistrationIds.Items.SWEET_BERRY_CHESTPLATE,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.SWEET_BERRY_ARMOR_MATERIAL, ArmorType.CHESTPLATE).food(food(14))));
    public static final RegistryObject<Item> SWEET_BERRY_LEGGINGS = registerItem(GeneratedRegistrationIds.Items.SWEET_BERRY_LEGGINGS,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.SWEET_BERRY_ARMOR_MATERIAL, ArmorType.LEGGINGS).food(food(14))));
    public static final RegistryObject<Item> SWEET_BERRY_BOOTS = registerItem(GeneratedRegistrationIds.Items.SWEET_BERRY_BOOTS,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.SWEET_BERRY_ARMOR_MATERIAL, ArmorType.BOOTS).food(food(8))));

    // Pumpkin Pie
    public static final RegistryObject<Item> PUMPKIN_PIE_SWORD = registerItem(GeneratedRegistrationIds.Items.PUMPKIN_PIE_SWORD,
            p -> new EdibleSwordItem(p.sword(ModToolTiers.PUMPKIN_PIE, 3, -2.4f).food(food(4))));
    public static final RegistryObject<Item> PUMPKIN_PIE_PICKAXE = registerItem(GeneratedRegistrationIds.Items.PUMPKIN_PIE_PICKAXE,
            p -> new EdiblePickaxeItem(p.pickaxe(ModToolTiers.PUMPKIN_PIE, 1, -2.8f).food(food(6))));
    public static final RegistryObject<Item> PUMPKIN_PIE_SHOVEL = registerItem(GeneratedRegistrationIds.Items.PUMPKIN_PIE_SHOVEL,
            p -> new EdibleShovelItem(p.shovel(ModToolTiers.PUMPKIN_PIE, 1.5f, -3f).food(food(2))));
    public static final RegistryObject<Item> PUMPKIN_PIE_AXE = registerItem(GeneratedRegistrationIds.Items.PUMPKIN_PIE_AXE,
            p -> new EdibleAxeItem(p.axe(ModToolTiers.PUMPKIN_PIE, 6, -3.2f).food(food(6))));
    public static final RegistryObject<Item> PUMPKIN_PIE_HOE = registerItem(GeneratedRegistrationIds.Items.PUMPKIN_PIE_HOE,
            p -> new EdibleHoeItem(p.hoe(ModToolTiers.PUMPKIN_PIE, 0, -3f).food(food(4))));
    public static final RegistryObject<Item> PUMPKIN_PIE_HELMET = registerItem(GeneratedRegistrationIds.Items.PUMPKIN_PIE_HELMET,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.PUMPKIN_PIE_ARMOR_MATERIAL, ArmorType.HELMET).food(food(10))));
    public static final RegistryObject<Item> PUMPKIN_PIE_CHESTPLATE = registerItem(GeneratedRegistrationIds.Items.PUMPKIN_PIE_CHESTPLATE,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.PUMPKIN_PIE_ARMOR_MATERIAL, ArmorType.CHESTPLATE).food(food(14))));
    public static final RegistryObject<Item> PUMPKIN_PIE_LEGGINGS = registerItem(GeneratedRegistrationIds.Items.PUMPKIN_PIE_LEGGINGS,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.PUMPKIN_PIE_ARMOR_MATERIAL, ArmorType.LEGGINGS).food(food(14))));
    public static final RegistryObject<Item> PUMPKIN_PIE_BOOTS = registerItem(GeneratedRegistrationIds.Items.PUMPKIN_PIE_BOOTS,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.PUMPKIN_PIE_ARMOR_MATERIAL, ArmorType.BOOTS).food(food(8))));

    // Mushroom
    public static final RegistryObject<Item> MUSHROOM_SWORD = registerItem(GeneratedRegistrationIds.Items.MUSHROOM_SWORD,
            p -> new EdibleSwordItem(p.sword(ModToolTiers.MUSHROOM, 3, -2.4f).food(food(4))));
    public static final RegistryObject<Item> MUSHROOM_PICKAXE = registerItem(GeneratedRegistrationIds.Items.MUSHROOM_PICKAXE,
            p -> new EdiblePickaxeItem(p.pickaxe(ModToolTiers.MUSHROOM, 1, -2.8f).food(food(6))));
    public static final RegistryObject<Item> MUSHROOM_SHOVEL = registerItem(GeneratedRegistrationIds.Items.MUSHROOM_SHOVEL,
            p -> new EdibleShovelItem(p.shovel(ModToolTiers.MUSHROOM, 1.5f, -3f).food(food(2))));
    public static final RegistryObject<Item> MUSHROOM_AXE = registerItem(GeneratedRegistrationIds.Items.MUSHROOM_AXE,
            p -> new EdibleAxeItem(p.axe(ModToolTiers.MUSHROOM, 6, -3.2f).food(food(6))));
    public static final RegistryObject<Item> MUSHROOM_HOE = registerItem(GeneratedRegistrationIds.Items.MUSHROOM_HOE,
            p -> new EdibleHoeItem(p.hoe(ModToolTiers.MUSHROOM, 0, -3f).food(food(4))));
    public static final RegistryObject<Item> MUSHROOM_HELMET = registerItem(GeneratedRegistrationIds.Items.MUSHROOM_HELMET,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.MUSHROOM_ARMOR_MATERIAL, ArmorType.HELMET).food(food(10))));
    public static final RegistryObject<Item> MUSHROOM_CHESTPLATE = registerItem(GeneratedRegistrationIds.Items.MUSHROOM_CHESTPLATE,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.MUSHROOM_ARMOR_MATERIAL, ArmorType.CHESTPLATE).food(food(14))));
    public static final RegistryObject<Item> MUSHROOM_LEGGINGS = registerItem(GeneratedRegistrationIds.Items.MUSHROOM_LEGGINGS,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.MUSHROOM_ARMOR_MATERIAL, ArmorType.LEGGINGS).food(food(14))));
    public static final RegistryObject<Item> MUSHROOM_BOOTS = registerItem(GeneratedRegistrationIds.Items.MUSHROOM_BOOTS,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.MUSHROOM_ARMOR_MATERIAL, ArmorType.BOOTS).food(food(8))));

    // Pufferfish
    public static final RegistryObject<Item> PUFFERFISH_SWORD = registerItem(GeneratedRegistrationIds.Items.PUFFERFISH_SWORD,
            p -> new EdibleSwordItem(p.sword(ModToolTiers.PUFFERFISH, 3, -2.4f).food(food(4))));
    public static final RegistryObject<Item> PUFFERFISH_PICKAXE = registerItem(GeneratedRegistrationIds.Items.PUFFERFISH_PICKAXE,
            p -> new EdiblePickaxeItem(p.pickaxe(ModToolTiers.PUFFERFISH, 1, -2.8f).food(food(6))));
    public static final RegistryObject<Item> PUFFERFISH_SHOVEL = registerItem(GeneratedRegistrationIds.Items.PUFFERFISH_SHOVEL,
            p -> new EdibleShovelItem(p.shovel(ModToolTiers.PUFFERFISH, 1.5f, -3f).food(food(2))));
    public static final RegistryObject<Item> PUFFERFISH_AXE = registerItem(GeneratedRegistrationIds.Items.PUFFERFISH_AXE,
            p -> new EdibleAxeItem(p.axe(ModToolTiers.PUFFERFISH, 6, -3.2f).food(food(6))));
    public static final RegistryObject<Item> PUFFERFISH_HOE = registerItem(GeneratedRegistrationIds.Items.PUFFERFISH_HOE,
            p -> new EdibleHoeItem(p.hoe(ModToolTiers.PUFFERFISH, 0, -3f).food(food(4))));
    public static final RegistryObject<Item> PUFFERFISH_HELMET = registerItem(GeneratedRegistrationIds.Items.PUFFERFISH_HELMET,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.PUFFERFISH_ARMOR_MATERIAL, ArmorType.HELMET).food(food(10))));
    public static final RegistryObject<Item> PUFFERFISH_CHESTPLATE = registerItem(GeneratedRegistrationIds.Items.PUFFERFISH_CHESTPLATE,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.PUFFERFISH_ARMOR_MATERIAL, ArmorType.CHESTPLATE).food(food(14))));
    public static final RegistryObject<Item> PUFFERFISH_LEGGINGS = registerItem(GeneratedRegistrationIds.Items.PUFFERFISH_LEGGINGS,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.PUFFERFISH_ARMOR_MATERIAL, ArmorType.LEGGINGS).food(food(14))));
    public static final RegistryObject<Item> PUFFERFISH_BOOTS = registerItem(GeneratedRegistrationIds.Items.PUFFERFISH_BOOTS,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.PUFFERFISH_ARMOR_MATERIAL, ArmorType.BOOTS).food(food(8))));

    // Honey
    public static final RegistryObject<Item> HONEY_SWORD = registerItem(GeneratedRegistrationIds.Items.HONEY_SWORD,
            p -> new EdibleSwordItem(p.sword(ModToolTiers.HONEY, 3, -2.4f).food(food(4))));
    public static final RegistryObject<Item> HONEY_PICKAXE = registerItem(GeneratedRegistrationIds.Items.HONEY_PICKAXE,
            p -> new EdiblePickaxeItem(p.pickaxe(ModToolTiers.HONEY, 1, -2.8f).food(food(6))));
    public static final RegistryObject<Item> HONEY_SHOVEL = registerItem(GeneratedRegistrationIds.Items.HONEY_SHOVEL,
            p -> new EdibleShovelItem(p.shovel(ModToolTiers.HONEY, 1.5f, -3f).food(food(2))));
    public static final RegistryObject<Item> HONEY_AXE = registerItem(GeneratedRegistrationIds.Items.HONEY_AXE,
            p -> new EdibleAxeItem(p.axe(ModToolTiers.HONEY, 6, -3.2f).food(food(6))));
    public static final RegistryObject<Item> HONEY_HOE = registerItem(GeneratedRegistrationIds.Items.HONEY_HOE,
            p -> new EdibleHoeItem(p.hoe(ModToolTiers.HONEY, 0, -3f).food(food(4))));
    public static final RegistryObject<Item> HONEY_HELMET = registerItem(GeneratedRegistrationIds.Items.HONEY_HELMET,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.HONEY_ARMOR_MATERIAL, ArmorType.HELMET).food(food(10))));
    public static final RegistryObject<Item> HONEY_CHESTPLATE = registerItem(GeneratedRegistrationIds.Items.HONEY_CHESTPLATE,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.HONEY_ARMOR_MATERIAL, ArmorType.CHESTPLATE).food(food(14))));
    public static final RegistryObject<Item> HONEY_LEGGINGS = registerItem(GeneratedRegistrationIds.Items.HONEY_LEGGINGS,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.HONEY_ARMOR_MATERIAL, ArmorType.LEGGINGS).food(food(14))));
    public static final RegistryObject<Item> HONEY_BOOTS = registerItem(GeneratedRegistrationIds.Items.HONEY_BOOTS,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.HONEY_ARMOR_MATERIAL, ArmorType.BOOTS).food(food(8))));

    // Chorus Fruit
    public static final RegistryObject<Item> CHORUS_FRUIT_SWORD = registerItem(GeneratedRegistrationIds.Items.CHORUS_FRUIT_SWORD,
            p -> new EdibleSwordItem(p.sword(ModToolTiers.CHORUS_FRUIT, 3, -2.4f).food(food(4))));
    public static final RegistryObject<Item> CHORUS_FRUIT_PICKAXE = registerItem(GeneratedRegistrationIds.Items.CHORUS_FRUIT_PICKAXE,
            p -> new EdiblePickaxeItem(p.pickaxe(ModToolTiers.CHORUS_FRUIT, 1, -2.8f).food(food(6))));
    public static final RegistryObject<Item> CHORUS_FRUIT_SHOVEL = registerItem(GeneratedRegistrationIds.Items.CHORUS_FRUIT_SHOVEL,
            p -> new EdibleShovelItem(p.shovel(ModToolTiers.CHORUS_FRUIT, 1.5f, -3f).food(food(2))));
    public static final RegistryObject<Item> CHORUS_FRUIT_AXE = registerItem(GeneratedRegistrationIds.Items.CHORUS_FRUIT_AXE,
            p -> new EdibleAxeItem(p.axe(ModToolTiers.CHORUS_FRUIT, 6, -3.2f).food(food(6))));
    public static final RegistryObject<Item> CHORUS_FRUIT_HOE = registerItem(GeneratedRegistrationIds.Items.CHORUS_FRUIT_HOE,
            p -> new EdibleHoeItem(p.hoe(ModToolTiers.CHORUS_FRUIT, 0, -3f).food(food(4))));
    public static final RegistryObject<Item> CHORUS_FRUIT_HELMET = registerItem(GeneratedRegistrationIds.Items.CHORUS_FRUIT_HELMET,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.CHORUS_FRUIT_ARMOR_MATERIAL, ArmorType.HELMET).food(food(10))));
    public static final RegistryObject<Item> CHORUS_FRUIT_CHESTPLATE = registerItem(GeneratedRegistrationIds.Items.CHORUS_FRUIT_CHESTPLATE,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.CHORUS_FRUIT_ARMOR_MATERIAL, ArmorType.CHESTPLATE).food(food(14))));
    public static final RegistryObject<Item> CHORUS_FRUIT_LEGGINGS = registerItem(GeneratedRegistrationIds.Items.CHORUS_FRUIT_LEGGINGS,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.CHORUS_FRUIT_ARMOR_MATERIAL, ArmorType.LEGGINGS).food(food(14))));
    public static final RegistryObject<Item> CHORUS_FRUIT_BOOTS = registerItem(GeneratedRegistrationIds.Items.CHORUS_FRUIT_BOOTS,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.CHORUS_FRUIT_ARMOR_MATERIAL, ArmorType.BOOTS).food(food(8))));

    // Golden Apple
    public static final RegistryObject<Item> GOLDEN_APPLE_SWORD = registerItem(GeneratedRegistrationIds.Items.GOLDEN_APPLE_SWORD,
            p -> new EdibleSwordItem(p.sword(ModToolTiers.GOLDEN_APPLE, 3, -2.4f).food(food(4))));
    public static final RegistryObject<Item> GOLDEN_APPLE_PICKAXE = registerItem(GeneratedRegistrationIds.Items.GOLDEN_APPLE_PICKAXE,
            p -> new EdiblePickaxeItem(p.pickaxe(ModToolTiers.GOLDEN_APPLE, 1, -2.8f).food(food(6))));
    public static final RegistryObject<Item> GOLDEN_APPLE_SHOVEL = registerItem(GeneratedRegistrationIds.Items.GOLDEN_APPLE_SHOVEL,
            p -> new EdibleShovelItem(p.shovel(ModToolTiers.GOLDEN_APPLE, 1.5f, -3f).food(food(2))));
    public static final RegistryObject<Item> GOLDEN_APPLE_AXE = registerItem(GeneratedRegistrationIds.Items.GOLDEN_APPLE_AXE,
            p -> new EdibleAxeItem(p.axe(ModToolTiers.GOLDEN_APPLE, 6, -3.2f).food(food(6))));
    public static final RegistryObject<Item> GOLDEN_APPLE_HOE = registerItem(GeneratedRegistrationIds.Items.GOLDEN_APPLE_HOE,
            p -> new EdibleHoeItem(p.hoe(ModToolTiers.GOLDEN_APPLE, 0, -3f).food(food(4))));
    public static final RegistryObject<Item> GOLDEN_APPLE_HELMET = registerItem(GeneratedRegistrationIds.Items.GOLDEN_APPLE_HELMET,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.GOLDEN_APPLE_ARMOR_MATERIAL, ArmorType.HELMET).food(food(10))));
    public static final RegistryObject<Item> GOLDEN_APPLE_CHESTPLATE = registerItem(GeneratedRegistrationIds.Items.GOLDEN_APPLE_CHESTPLATE,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.GOLDEN_APPLE_ARMOR_MATERIAL, ArmorType.CHESTPLATE).food(food(14))));
    public static final RegistryObject<Item> GOLDEN_APPLE_LEGGINGS = registerItem(GeneratedRegistrationIds.Items.GOLDEN_APPLE_LEGGINGS,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.GOLDEN_APPLE_ARMOR_MATERIAL, ArmorType.LEGGINGS).food(food(14))));
    public static final RegistryObject<Item> GOLDEN_APPLE_BOOTS = registerItem(GeneratedRegistrationIds.Items.GOLDEN_APPLE_BOOTS,
            p -> new EdibleArmorItem(p.humanoidArmor(ModArmorMaterials.GOLDEN_APPLE_ARMOR_MATERIAL, ArmorType.BOOTS).food(food(8))));

    public static void register(BusGroup busGroup) {
        ITEMS.register(busGroup);
    }
}
