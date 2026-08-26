package com.stonytark.usefultoolsmod.item;

import com.stonytark.usefultoolsmod.generated.GeneratedRegistrationIds;

import com.stonytark.usefultoolsmod.UsefultoolsMod;
import com.stonytark.usefultoolsmod.entity.ModEntities;
import com.stonytark.usefultoolsmod.item.custom.*;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(BuiltInRegistries.ITEM, UsefultoolsMod.MOD_ID);

    public static final DeferredHolder<Item, Item> RGOLD = ITEMS.register(GeneratedRegistrationIds.Items.RGOLD,
            () -> new Item(new Item.Properties().stacksTo(64)));

    public static final DeferredHolder<Item, Item> RAW_RGOLD = ITEMS.register(GeneratedRegistrationIds.Items.RAW_RGOLD,
            () -> new Item(new Item.Properties().stacksTo(64)));

    public static final DeferredHolder<Item, Item> OBSHARD = ITEMS.register(GeneratedRegistrationIds.Items.OBSHARD,
            () -> new Item(new Item.Properties().stacksTo(64)));

    public static final DeferredHolder<Item, Item> SEM = ITEMS.register(GeneratedRegistrationIds.Items.SEM,
            () -> new Item(new Item.Properties().stacksTo(64)));

    public static final DeferredHolder<Item, Item> OBINGOT = ITEMS.register(GeneratedRegistrationIds.Items.OBINGOT,
            () -> new Item(new Item.Properties().stacksTo(64)));

    public static final DeferredHolder<Item, Item> GRENADE = ITEMS.register(GeneratedRegistrationIds.Items.GRENADE,
            () -> new Grenade(new Item.Properties().stacksTo(16)));

    public static final DeferredHolder<Item, Item> HRED = ITEMS.register(GeneratedRegistrationIds.Items.HRED,
            () -> new Item(new Item.Properties().stacksTo(64)));

    public static final DeferredHolder<Item, Item> HGLOW = ITEMS.register(GeneratedRegistrationIds.Items.HGLOW,
            () -> new Item(new Item.Properties().stacksTo(64)));

    public static final DeferredHolder<Item, Item> RLAPIS = ITEMS.register(GeneratedRegistrationIds.Items.RLAPIS,
            () -> new Item(new Item.Properties().stacksTo(64)));

    public static final DeferredHolder<Item, Item> DYNAMITE = ITEMS.register(GeneratedRegistrationIds.Items.DYNAMITE,
                    () -> new Dynamite(new Item.Properties()
                            .stacksTo(16)
                            .fireResistant()  // optional
                    )
            );

    public static final DeferredHolder<Item, Item> REMERALD_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.REMERALD_SWORD,
            () -> new SwordItem(ModToolTiers.REMERALD, 3, -2.4f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> REMERALD_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.REMERALD_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.REMERALD, 1, -2.8f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> REMERALD_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.REMERALD_SHOVEL,
            () -> new ShovelItem(ModToolTiers.REMERALD, 1.5f, -3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> REMERALD_AXE = ITEMS.register(GeneratedRegistrationIds.Items.REMERALD_AXE,
            () -> new AxeItem(ModToolTiers.REMERALD, 6, -3.2f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> REMERALD_HOE = ITEMS.register(GeneratedRegistrationIds.Items.REMERALD_HOE,
            () -> new HoeItem(ModToolTiers.REMERALD, 0, -3f, new Item.Properties()));

    public static final DeferredHolder<Item, Item> PEMERALD_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.PEMERALD_SWORD,
            () -> new SwordItem(ModToolTiers.PEMERALD, 3, -2.4f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> PEMERALD_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.PEMERALD_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.PEMERALD, 1, -2.8f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> PEMERALD_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.PEMERALD_SHOVEL,
            () -> new ShovelItem(ModToolTiers.PEMERALD, 1.5f, -3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> PEMERALD_AXE = ITEMS.register(GeneratedRegistrationIds.Items.PEMERALD_AXE,
            () -> new AxeItem(ModToolTiers.PEMERALD, 6, -3.2f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> PEMERALD_HOE = ITEMS.register(GeneratedRegistrationIds.Items.PEMERALD_HOE,
            () -> new HoeItem(ModToolTiers.PEMERALD, 0, -3f, new Item.Properties()));

    public static final DeferredHolder<Item, Item> ROBSIDIAN_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.ROBSIDIAN_SWORD,
            () -> new SwordItem(ModToolTiers.ROBSIDIAN, 3, -2.4f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> ROBSIDIAN_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.ROBSIDIAN_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.ROBSIDIAN, 1, -2.8f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> ROBSIDIAN_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.ROBSIDIAN_SHOVEL,
            () -> new ShovelItem(ModToolTiers.ROBSIDIAN, 1.5f, -3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> ROBSIDIAN_AXE = ITEMS.register(GeneratedRegistrationIds.Items.ROBSIDIAN_AXE,
            () -> new AxeItem(ModToolTiers.ROBSIDIAN, 6, -3.2f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> ROBSIDIAN_HOE = ITEMS.register(GeneratedRegistrationIds.Items.ROBSIDIAN_HOE,
            () -> new HoeItem(ModToolTiers.ROBSIDIAN, 0, -3f, new Item.Properties()));

    public static final DeferredHolder<Item, Item> POBSIDIAN_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.POBSIDIAN_SWORD,
            () -> new SwordItem(ModToolTiers.POBSIDIAN, 3, -2.4f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> POBSIDIAN_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.POBSIDIAN_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.POBSIDIAN, 1, -2.8f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> POBSIDIAN_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.POBSIDIAN_SHOVEL,
            () -> new ShovelItem(ModToolTiers.POBSIDIAN, 1.5f, -3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> POBSIDIAN_AXE = ITEMS.register(GeneratedRegistrationIds.Items.POBSIDIAN_AXE,
            () -> new AxeItem(ModToolTiers.POBSIDIAN, 6, -3.2f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> POBSIDIAN_HOE = ITEMS.register(GeneratedRegistrationIds.Items.POBSIDIAN_HOE,
            () -> new HoeItem(ModToolTiers.POBSIDIAN, 0, -3f, new Item.Properties()));

    public static final DeferredHolder<Item, Item> OVERPOWER_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.OVERPOWER_SWORD,
            () -> new SwordItem(ModToolTiers.OVERPOWER, 3, -2.4f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> OVERPOWER_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.OVERPOWER_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.OVERPOWER, 1, -2.8f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> OVERPOWER_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.OVERPOWER_SHOVEL,
            () -> new ShovelItem(ModToolTiers.OVERPOWER, 1.5f, -3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> OVERPOWER_AXE = ITEMS.register(GeneratedRegistrationIds.Items.OVERPOWER_AXE,
            () -> new AxeItem(ModToolTiers.OVERPOWER, 6, -3.2f, new Item.Properties()));


    public static final DeferredHolder<Item, Item> HREDSTONE_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.HREDSTONE_SWORD,
            () -> new SwordItem(ModToolTiers.HREDSTONE, 3, -2.4f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> HREDSTONE_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.HREDSTONE_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.HREDSTONE, 1, -2.8f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> HREDSTONE_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.HREDSTONE_SHOVEL,
            () -> new ShovelItem(ModToolTiers.HREDSTONE, 1.5f, -3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> HREDSTONE_AXE = ITEMS.register(GeneratedRegistrationIds.Items.HREDSTONE_AXE,
            () -> new AxeItem(ModToolTiers.HREDSTONE, 6, -3.2f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> HREDSTONE_HOE = ITEMS.register(GeneratedRegistrationIds.Items.HREDSTONE_HOE,
            () -> new HoeItem(ModToolTiers.HREDSTONE, 0, -3f, new Item.Properties()));

    public static final DeferredHolder<Item, Item> HGLOWSTONE_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.HGLOWSTONE_SWORD,
            () -> new SwordItem(ModToolTiers.HGLOWSTONE, 3, -2.4f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> HGLOWSTONE_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.HGLOWSTONE_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.HGLOWSTONE, 1, -2.8f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> HGLOWSTONE_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.HGLOWSTONE_SHOVEL,
            () -> new ShovelItem(ModToolTiers.HGLOWSTONE, 1.5f, -3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> HGLOWSTONE_AXE = ITEMS.register(GeneratedRegistrationIds.Items.HGLOWSTONE_AXE,
            () -> new AxeItem(ModToolTiers.HGLOWSTONE, 6, -3.2f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> HGLOWSTONE_HOE = ITEMS.register(GeneratedRegistrationIds.Items.HGLOWSTONE_HOE,
            () -> new HoeItem(ModToolTiers.HGLOWSTONE, 0, -3f, new Item.Properties()));

    public static final DeferredHolder<Item, Item> RGOLD_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.RGOLD_SWORD,
            () -> new SwordItem(ModToolTiers.RGOLD, 3, -2.4f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> RGOLD_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.RGOLD_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.RGOLD, 1, -2.8f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> RGOLD_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.RGOLD_SHOVEL,
            () -> new ShovelItem(ModToolTiers.RGOLD, 1.5f, -3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> RGOLD_AXE = ITEMS.register(GeneratedRegistrationIds.Items.RGOLD_AXE,
            () -> new AxeItem(ModToolTiers.RGOLD, 6, -3.2f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> RGOLD_HOE = ITEMS.register(GeneratedRegistrationIds.Items.RGOLD_HOE,
            () -> new HoeItem(ModToolTiers.RGOLD, 0, -3f, new Item.Properties()));

    public static final DeferredHolder<Item, Item> RLAPIS_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.RLAPIS_SWORD,
            () -> new SwordItem(ModToolTiers.RLAPIS, 3, -2.4f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> RLAPIS_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.RLAPIS_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.RLAPIS, 1, -2.8f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> RLAPIS_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.RLAPIS_SHOVEL,
            () -> new ShovelItem(ModToolTiers.RLAPIS, 1.5f, -3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> RLAPIS_AXE = ITEMS.register(GeneratedRegistrationIds.Items.RLAPIS_AXE,
            () -> new AxeItem(ModToolTiers.RLAPIS, 6, -3.2f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> RLAPIS_HOE = ITEMS.register(GeneratedRegistrationIds.Items.RLAPIS_HOE,
            () -> new HoeItem(ModToolTiers.RLAPIS, 0, -3f, new Item.Properties()));

    public static final DeferredHolder<Item, Item> EMERALD_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.EMERALD_HELMET,
            () -> new ArmorItem(ModArmorMaterials.EMERALD, ArmorItem.Type.HELMET,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> EMERALD_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.EMERALD_CHESTPLATE,
            () -> new ArmorItem(ModArmorMaterials.EMERALD, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> EMERALD_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.EMERALD_LEGGINGS,
            () -> new ArmorItem(ModArmorMaterials.EMERALD, ArmorItem.Type.LEGGINGS,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> EMERALD_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.EMERALD_BOOTS,
            () -> new ArmorItem(ModArmorMaterials.EMERALD, ArmorItem.Type.BOOTS,
                    new Item.Properties()));

    public static final DeferredHolder<Item, Item> HRED_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.HRED_HELMET,
            () -> new ArmorItem(ModArmorMaterials.HRED, ArmorItem.Type.HELMET,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> HRED_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.HRED_CHESTPLATE,
            () -> new ArmorItem(ModArmorMaterials.HRED, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> HRED_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.HRED_LEGGINGS,
            () -> new ArmorItem(ModArmorMaterials.HRED, ArmorItem.Type.LEGGINGS,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> HRED_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.HRED_BOOTS,
            () -> new ArmorItem(ModArmorMaterials.HRED, ArmorItem.Type.BOOTS,
                    new Item.Properties()));

    public static final DeferredHolder<Item, Item> HGLOW_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.HGLOW_HELMET,
            () -> new ArmorItem(ModArmorMaterials.HGLOW, ArmorItem.Type.HELMET,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> HGLOW_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.HGLOW_CHESTPLATE,
            () -> new ArmorItem(ModArmorMaterials.HGLOW, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> HGLOW_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.HGLOW_LEGGINGS,
            () -> new ArmorItem(ModArmorMaterials.HGLOW, ArmorItem.Type.LEGGINGS,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> HGLOW_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.HGLOW_BOOTS,
            () -> new ArmorItem(ModArmorMaterials.HGLOW, ArmorItem.Type.BOOTS,
                    new Item.Properties()));

    public static final DeferredHolder<Item, Item> OBSIDIAN_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.OBSIDIAN_HELMET,
            () -> new ArmorItem(ModArmorMaterials.OBSIDIAN, ArmorItem.Type.HELMET,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> OBSIDIAN_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.OBSIDIAN_CHESTPLATE,
            () -> new ArmorItem(ModArmorMaterials.OBSIDIAN, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> OBSIDIAN_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.OBSIDIAN_LEGGINGS,
            () -> new ArmorItem(ModArmorMaterials.OBSIDIAN, ArmorItem.Type.LEGGINGS,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> OBSIDIAN_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.OBSIDIAN_BOOTS,
            () -> new ArmorItem(ModArmorMaterials.OBSIDIAN, ArmorItem.Type.BOOTS,
                    new Item.Properties()));

    public static final DeferredHolder<Item, Item> RGOLD_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.RGOLD_HELMET,
            () -> new ArmorItem(ModArmorMaterials.RGOLD, ArmorItem.Type.HELMET,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> RGOLD_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.RGOLD_CHESTPLATE,
            () -> new ArmorItem(ModArmorMaterials.RGOLD, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> RGOLD_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.RGOLD_LEGGINGS,
            () -> new ArmorItem(ModArmorMaterials.RGOLD, ArmorItem.Type.LEGGINGS,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> RGOLD_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.RGOLD_BOOTS,
            () -> new ArmorItem(ModArmorMaterials.RGOLD, ArmorItem.Type.BOOTS,
                    new Item.Properties()));

    public static final DeferredHolder<Item, Item> RLAPIS_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.RLAPIS_HELMET,
            () -> new ArmorItem(ModArmorMaterials.RLAPIS, ArmorItem.Type.HELMET,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> RLAPIS_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.RLAPIS_CHESTPLATE,
            () -> new ArmorItem(ModArmorMaterials.RLAPIS, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> RLAPIS_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.RLAPIS_LEGGINGS,
            () -> new ArmorItem(ModArmorMaterials.RLAPIS, ArmorItem.Type.LEGGINGS,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> RLAPIS_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.RLAPIS_BOOTS,
            () -> new ArmorItem(ModArmorMaterials.RLAPIS, ArmorItem.Type.BOOTS,
                    new Item.Properties()));

    public static final DeferredHolder<Item, Item> OVERPOWER_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.OVERPOWER_HELMET,
            () -> new ModArmorItem(ModArmorMaterials.OVERPOWER, ArmorItem.Type.HELMET,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> OVERPOWER_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.OVERPOWER_CHESTPLATE,
            () -> new ArmorItem(ModArmorMaterials.OVERPOWER, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> OVERPOWER_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.OVERPOWER_LEGGINGS,
            () -> new ArmorItem(ModArmorMaterials.OVERPOWER, ArmorItem.Type.LEGGINGS,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> OVERPOWER_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.OVERPOWER_BOOTS,
            () -> new ArmorItem(ModArmorMaterials.OVERPOWER, ArmorItem.Type.BOOTS,
                    new Item.Properties()));

    public static final DeferredHolder<Item, Item> GHOST_SPAWN_EGG = ITEMS.register(GeneratedRegistrationIds.Items.GHOST_SPAWN_EGG,
            () -> new DeferredSpawnEggItem(() -> ModEntities.GHOST.get(), 0xFFFFFF, 0x999999, new Item.Properties()));

    public static final DeferredHolder<Item, Item> ECTOPLASM = ITEMS.register(GeneratedRegistrationIds.Items.ECTOPLASM,
            () -> new Item(new Item.Properties().stacksTo(64)));

    public static final DeferredHolder<Item, Item> CONDENSED_ECTOPLASM = ITEMS.register(GeneratedRegistrationIds.Items.CONDENSED_ECTOPLASM,
            () -> new Item(new Item.Properties().stacksTo(64)));
    public static final DeferredHolder<Item, Item> WRAITH_SPAWN_EGG = ITEMS.register(GeneratedRegistrationIds.Items.WRAITH_SPAWN_EGG,
            () -> new DeferredSpawnEggItem(() -> ModEntities.WRAITH.get(), 0x241833, 0xA95CFF, new Item.Properties()));
    public static final DeferredHolder<Item, Item> SPECTRAL_RESONATOR = ITEMS.register(GeneratedRegistrationIds.Items.SPECTRAL_RESONATOR,
            () -> new SpectralResonatorItem(new Item.Properties().durability(128)));
    public static final DeferredHolder<Item, Item> STICKY_DYNAMITE = ITEMS.register(GeneratedRegistrationIds.Items.STICKY_DYNAMITE,
            () -> new StickyDynamiteItem(new Item.Properties().stacksTo(16)));
    public static final DeferredHolder<Item, Item> REMOTE_DETONATOR = ITEMS.register(GeneratedRegistrationIds.Items.REMOTE_DETONATOR,
            () -> new RemoteDetonatorItem(new Item.Properties().stacksTo(1)));

    // -------------------------------------------------------------------------
    // Rough Ectoplasm tools (raw ectoplasm, stone-tier equivalent)
    // -------------------------------------------------------------------------
    public static final DeferredHolder<Item, Item> RECTO_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.RECTO_SWORD,
            () -> new EctoSwordItem(ModToolTiers.RECTO, 3, -2.4f, new Item.Properties()));

    public static final DeferredHolder<Item, Item> RECTO_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.RECTO_PICKAXE,
            () -> new EctoPickaxeItem(ModToolTiers.RECTO, 1, -2.8f, new Item.Properties()));

    public static final DeferredHolder<Item, Item> RECTO_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.RECTO_SHOVEL,
            () -> new EctoShovelItem(ModToolTiers.RECTO, 1.5f, -3f, new Item.Properties()));

    public static final DeferredHolder<Item, Item> RECTO_AXE = ITEMS.register(GeneratedRegistrationIds.Items.RECTO_AXE,
            () -> new EctoAxeItem(ModToolTiers.RECTO, 6, -3.2f, new Item.Properties()));

    public static final DeferredHolder<Item, Item> RECTO_HOE = ITEMS.register(GeneratedRegistrationIds.Items.RECTO_HOE,
            () -> new EctoHoeItem(ModToolTiers.RECTO, 0, -3f, new Item.Properties()));

    // -------------------------------------------------------------------------
    // Refined Ectoplasm + Ectoplasm tools + armor
    // -------------------------------------------------------------------------
    public static final DeferredHolder<Item, Item> REFINED_ECTOPLASM = ITEMS.register(GeneratedRegistrationIds.Items.REFINED_ECTOPLASM,
            () -> new Item(new Item.Properties().stacksTo(64)));

    public static final DeferredHolder<Item, Item> ECTO_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.ECTO_SWORD,
            () -> new EctoSwordItem(ModToolTiers.ECTOPLASM, 3, -2.4f, new Item.Properties()));

    public static final DeferredHolder<Item, Item> ECTO_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.ECTO_PICKAXE,
            () -> new EctoPickaxeItem(ModToolTiers.ECTOPLASM, 1, -2.8f, new Item.Properties()));

    public static final DeferredHolder<Item, Item> ECTO_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.ECTO_SHOVEL,
            () -> new EctoShovelItem(ModToolTiers.ECTOPLASM, 1.5f, -3f, new Item.Properties()));

    public static final DeferredHolder<Item, Item> ECTO_AXE = ITEMS.register(GeneratedRegistrationIds.Items.ECTO_AXE,
            () -> new EctoAxeItem(ModToolTiers.ECTOPLASM, 6, -3.1f, new Item.Properties()));

    public static final DeferredHolder<Item, Item> ECTO_HOE = ITEMS.register(GeneratedRegistrationIds.Items.ECTO_HOE,
            () -> new EctoHoeItem(ModToolTiers.ECTOPLASM, 0, -3f, new Item.Properties()));

    public static final DeferredHolder<Item, Item> ECTO_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.ECTO_HELMET,
            () -> new ArmorItem(ModArmorMaterials.ECTO, ArmorItem.Type.HELMET,
                    new Item.Properties()));

    public static final DeferredHolder<Item, Item> ECTO_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.ECTO_CHESTPLATE,
            () -> new ArmorItem(ModArmorMaterials.ECTO, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties()));

    public static final DeferredHolder<Item, Item> ECTO_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.ECTO_LEGGINGS,
            () -> new ArmorItem(ModArmorMaterials.ECTO, ArmorItem.Type.LEGGINGS,
                    new Item.Properties()));

    public static final DeferredHolder<Item, Item> ECTO_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.ECTO_BOOTS,
            () -> new ArmorItem(ModArmorMaterials.ECTO, ArmorItem.Type.BOOTS,
                    new Item.Properties()));

    // -------------------------------------------------------------------------
    // Coal material items
    // -------------------------------------------------------------------------
    public static final DeferredHolder<Item, Item> COAL_DUST = ITEMS.register(GeneratedRegistrationIds.Items.COAL_DUST,
            () -> new Item(new Item.Properties().stacksTo(64)));

    public static final DeferredHolder<Item, Item> HARDENED_COAL = ITEMS.register(GeneratedRegistrationIds.Items.HARDENED_COAL,
            () -> new Item(new Item.Properties().stacksTo(64)));

    // -------------------------------------------------------------------------
    // Coal tools  (wood mining level, durability ~120, flammable)
    // -------------------------------------------------------------------------
    public static final DeferredHolder<Item, Item> COAL_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.COAL_SWORD,
            () -> new CoalSwordItem(ModToolTiers.COAL_TOOL, 2, -2.4f, new Item.Properties()));

    public static final DeferredHolder<Item, Item> COAL_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.COAL_PICKAXE,
            () -> new CoalPickaxeItem(ModToolTiers.COAL_TOOL, 1, -2.8f, new Item.Properties()));

    public static final DeferredHolder<Item, Item> COAL_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.COAL_SHOVEL,
            () -> new CoalShovelItem(ModToolTiers.COAL_TOOL, 1.5f, -3f, new Item.Properties()));

    public static final DeferredHolder<Item, Item> COAL_AXE = ITEMS.register(GeneratedRegistrationIds.Items.COAL_AXE,
            () -> new CoalAxeItem(ModToolTiers.COAL_TOOL, 5, -3.2f, new Item.Properties()));

    public static final DeferredHolder<Item, Item> COAL_HOE = ITEMS.register(GeneratedRegistrationIds.Items.COAL_HOE,
            () -> new CoalHoeItem(ModToolTiers.COAL_TOOL, 0, -3f, new Item.Properties()));

    // -------------------------------------------------------------------------
    // Coal armor  (between leather and chainmail, flammable)
    // -------------------------------------------------------------------------
    public static final DeferredHolder<Item, Item> COAL_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.COAL_HELMET,
            () -> new CoalArmorItem(ModArmorMaterials.COAL, ArmorItem.Type.HELMET,
                    new Item.Properties()));

    public static final DeferredHolder<Item, Item> COAL_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.COAL_CHESTPLATE,
            () -> new CoalArmorItem(ModArmorMaterials.COAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties()));

    public static final DeferredHolder<Item, Item> COAL_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.COAL_LEGGINGS,
            () -> new CoalArmorItem(ModArmorMaterials.COAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties()));

    public static final DeferredHolder<Item, Item> COAL_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.COAL_BOOTS,
            () -> new CoalArmorItem(ModArmorMaterials.COAL, ArmorItem.Type.BOOTS,
                    new Item.Properties()));



    // -------------------------------------------------------------------------
    // Raw metal rough tool sets
    // -------------------------------------------------------------------------

    // Rough Raw Gold (wood mining level — fast, fragile)
    public static final DeferredHolder<Item, Item> RRAW_GOLD_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.RRAW_GOLD_SWORD,
            () -> new SwordItem(ModToolTiers.RRAW_GOLD, 3, -2.4f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> RRAW_GOLD_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.RRAW_GOLD_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.RRAW_GOLD, 1, -2.8f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> RRAW_GOLD_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.RRAW_GOLD_SHOVEL,
            () -> new ShovelItem(ModToolTiers.RRAW_GOLD, 1.5f, -3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> RRAW_GOLD_AXE = ITEMS.register(GeneratedRegistrationIds.Items.RRAW_GOLD_AXE,
            () -> new AxeItem(ModToolTiers.RRAW_GOLD, 6, -3.2f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> RRAW_GOLD_HOE = ITEMS.register(GeneratedRegistrationIds.Items.RRAW_GOLD_HOE,
            () -> new HoeItem(ModToolTiers.RRAW_GOLD, 0, -3f, new Item.Properties()));

    // Rough Raw Copper (stone mining level)
    public static final DeferredHolder<Item, Item> RRAW_COPPER_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.RRAW_COPPER_SWORD,
            () -> new SwordItem(ModToolTiers.RRAW_COPPER, 3, -2.4f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> RRAW_COPPER_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.RRAW_COPPER_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.RRAW_COPPER, 1, -2.8f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> RRAW_COPPER_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.RRAW_COPPER_SHOVEL,
            () -> new ShovelItem(ModToolTiers.RRAW_COPPER, 1.5f, -3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> RRAW_COPPER_AXE = ITEMS.register(GeneratedRegistrationIds.Items.RRAW_COPPER_AXE,
            () -> new AxeItem(ModToolTiers.RRAW_COPPER, 6, -3.2f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> RRAW_COPPER_HOE = ITEMS.register(GeneratedRegistrationIds.Items.RRAW_COPPER_HOE,
            () -> new HoeItem(ModToolTiers.RRAW_COPPER, 0, -3f, new Item.Properties()));

    // Rough Raw Iron (iron mining level)
    public static final DeferredHolder<Item, Item> RRAW_IRON_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.RRAW_IRON_SWORD,
            () -> new SwordItem(ModToolTiers.RRAW_IRON, 3, -2.4f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> RRAW_IRON_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.RRAW_IRON_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.RRAW_IRON, 1, -2.8f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> RRAW_IRON_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.RRAW_IRON_SHOVEL,
            () -> new ShovelItem(ModToolTiers.RRAW_IRON, 1.5f, -3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> RRAW_IRON_AXE = ITEMS.register(GeneratedRegistrationIds.Items.RRAW_IRON_AXE,
            () -> new AxeItem(ModToolTiers.RRAW_IRON, 6, -3.2f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> RRAW_IRON_HOE = ITEMS.register(GeneratedRegistrationIds.Items.RRAW_IRON_HOE,
            () -> new HoeItem(ModToolTiers.RRAW_IRON, 0, -3f, new Item.Properties()));

    // Rough Raw Ferrous Gold (iron mining level)
    public static final DeferredHolder<Item, Item> RRAW_RGOLD_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.RRAW_RGOLD_SWORD,
            () -> new SwordItem(ModToolTiers.RRAW_RGOLD, 3, -2.4f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> RRAW_RGOLD_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.RRAW_RGOLD_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.RRAW_RGOLD, 1, -2.8f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> RRAW_RGOLD_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.RRAW_RGOLD_SHOVEL,
            () -> new ShovelItem(ModToolTiers.RRAW_RGOLD, 1.5f, -3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> RRAW_RGOLD_AXE = ITEMS.register(GeneratedRegistrationIds.Items.RRAW_RGOLD_AXE,
            () -> new AxeItem(ModToolTiers.RRAW_RGOLD, 6, -3.2f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> RRAW_RGOLD_HOE = ITEMS.register(GeneratedRegistrationIds.Items.RRAW_RGOLD_HOE,
            () -> new HoeItem(ModToolTiers.RRAW_RGOLD, 0, -3f, new Item.Properties()));

    // Rough Netherite Scrap (diamond mining level)
    public static final DeferredHolder<Item, Item> RSCRAP_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.RSCRAP_SWORD,
            () -> new SwordItem(ModToolTiers.RSCRAP, 3, -2.4f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> RSCRAP_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.RSCRAP_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.RSCRAP, 1, -2.8f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> RSCRAP_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.RSCRAP_SHOVEL,
            () -> new ShovelItem(ModToolTiers.RSCRAP, 1.5f, -3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> RSCRAP_AXE = ITEMS.register(GeneratedRegistrationIds.Items.RSCRAP_AXE,
            () -> new AxeItem(ModToolTiers.RSCRAP, 6, -3.2f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> RSCRAP_HOE = ITEMS.register(GeneratedRegistrationIds.Items.RSCRAP_HOE,
            () -> new HoeItem(ModToolTiers.RSCRAP, 0, -3f, new Item.Properties()));

    // -------------------------------------------------------------------------
    // Crystal / element material items
    // -------------------------------------------------------------------------

    public static final DeferredHolder<Item, Item> CALCIFIED_AMETHYST = ITEMS.register(GeneratedRegistrationIds.Items.CALCIFIED_AMETHYST,
            () -> new Item(new Item.Properties().stacksTo(64)));

    public static final DeferredHolder<Item, Item> GLACIAL_SHARD = ITEMS.register(GeneratedRegistrationIds.Items.GLACIAL_SHARD,
            () -> new Item(new Item.Properties().stacksTo(64)));

    public static final DeferredHolder<Item, Item> POLISHED_QUARTZ = ITEMS.register(GeneratedRegistrationIds.Items.POLISHED_QUARTZ,
            () -> new Item(new Item.Properties().stacksTo(64)));

    public static final DeferredHolder<Item, Item> POLISHED_PRISMARINE = ITEMS.register(GeneratedRegistrationIds.Items.POLISHED_PRISMARINE,
            () -> new Item(new Item.Properties().stacksTo(64)));

    // -------------------------------------------------------------------------
    // Rough Amethyst tools (iron mining level)
    // -------------------------------------------------------------------------
    public static final DeferredHolder<Item, Item> RAMETHYST_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.RAMETHYST_SWORD,
            () -> new SwordItem(ModToolTiers.RAMETHYST, 3, -2.4f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> RAMETHYST_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.RAMETHYST_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.RAMETHYST, 1, -2.8f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> RAMETHYST_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.RAMETHYST_SHOVEL,
            () -> new ShovelItem(ModToolTiers.RAMETHYST, 1.5f, -3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> RAMETHYST_AXE = ITEMS.register(GeneratedRegistrationIds.Items.RAMETHYST_AXE,
            () -> new AxeItem(ModToolTiers.RAMETHYST, 6, -3.2f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> RAMETHYST_HOE = ITEMS.register(GeneratedRegistrationIds.Items.RAMETHYST_HOE,
            () -> new HoeItem(ModToolTiers.RAMETHYST, 0, -3f, new Item.Properties()));

    // -------------------------------------------------------------------------
    // Snow tools (stone mining level)
    // -------------------------------------------------------------------------
    public static final DeferredHolder<Item, Item> SNOW_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.SNOW_SWORD,
            () -> new SwordItem(ModToolTiers.SNOW_TOOL, 3, -2.4f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> SNOW_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.SNOW_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.SNOW_TOOL, 1, -2.8f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> SNOW_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.SNOW_SHOVEL,
            () -> new ShovelItem(ModToolTiers.SNOW_TOOL, 1.5f, -3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> SNOW_AXE = ITEMS.register(GeneratedRegistrationIds.Items.SNOW_AXE,
            () -> new AxeItem(ModToolTiers.SNOW_TOOL, 6, -3.2f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> SNOW_HOE = ITEMS.register(GeneratedRegistrationIds.Items.SNOW_HOE,
            () -> new HoeItem(ModToolTiers.SNOW_TOOL, 0, -3f, new Item.Properties()));

    // -------------------------------------------------------------------------
    // Rough Quartz tools (iron mining level)
    // -------------------------------------------------------------------------
    public static final DeferredHolder<Item, Item> RQUARTZ_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.RQUARTZ_SWORD,
            () -> new SwordItem(ModToolTiers.RQUARTZ, 3, -2.4f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> RQUARTZ_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.RQUARTZ_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.RQUARTZ, 1, -2.8f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> RQUARTZ_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.RQUARTZ_SHOVEL,
            () -> new ShovelItem(ModToolTiers.RQUARTZ, 1.5f, -3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> RQUARTZ_AXE = ITEMS.register(GeneratedRegistrationIds.Items.RQUARTZ_AXE,
            () -> new AxeItem(ModToolTiers.RQUARTZ, 6, -3.2f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> RQUARTZ_HOE = ITEMS.register(GeneratedRegistrationIds.Items.RQUARTZ_HOE,
            () -> new HoeItem(ModToolTiers.RQUARTZ, 0, -3f, new Item.Properties()));

    // -------------------------------------------------------------------------
    // Rough Prismarine tools (iron mining level)
    // -------------------------------------------------------------------------
    public static final DeferredHolder<Item, Item> RPRISM_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.RPRISM_SWORD,
            () -> new SwordItem(ModToolTiers.RPRISM, 3, -2.4f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> RPRISM_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.RPRISM_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.RPRISM, 1, -2.8f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> RPRISM_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.RPRISM_SHOVEL,
            () -> new ShovelItem(ModToolTiers.RPRISM, 1.5f, -3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> RPRISM_AXE = ITEMS.register(GeneratedRegistrationIds.Items.RPRISM_AXE,
            () -> new AxeItem(ModToolTiers.RPRISM, 6, -3.2f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> RPRISM_HOE = ITEMS.register(GeneratedRegistrationIds.Items.RPRISM_HOE,
            () -> new HoeItem(ModToolTiers.RPRISM, 0, -3f, new Item.Properties()));

    // -------------------------------------------------------------------------
    // Calcified Amethyst tools (iron mining level)
    // -------------------------------------------------------------------------
    public static final DeferredHolder<Item, Item> CAMETHYST_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.CAMETHYST_SWORD,
            () -> new SwordItem(ModToolTiers.CAMETHYST, 3, -2.4f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> CAMETHYST_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.CAMETHYST_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.CAMETHYST, 1, -2.8f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> CAMETHYST_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.CAMETHYST_SHOVEL,
            () -> new ShovelItem(ModToolTiers.CAMETHYST, 1.5f, -3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> CAMETHYST_AXE = ITEMS.register(GeneratedRegistrationIds.Items.CAMETHYST_AXE,
            () -> new AxeItem(ModToolTiers.CAMETHYST, 6, -3.2f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> CAMETHYST_HOE = ITEMS.register(GeneratedRegistrationIds.Items.CAMETHYST_HOE,
            () -> new HoeItem(ModToolTiers.CAMETHYST, 0, -3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> CAMETHYST_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.CAMETHYST_HELMET,
            () -> new ArmorItem(ModArmorMaterials.CAMETHYST, ArmorItem.Type.HELMET,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> CAMETHYST_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.CAMETHYST_CHESTPLATE,
            () -> new ArmorItem(ModArmorMaterials.CAMETHYST, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> CAMETHYST_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.CAMETHYST_LEGGINGS,
            () -> new ArmorItem(ModArmorMaterials.CAMETHYST, ArmorItem.Type.LEGGINGS,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> CAMETHYST_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.CAMETHYST_BOOTS,
            () -> new ArmorItem(ModArmorMaterials.CAMETHYST, ArmorItem.Type.BOOTS,
                    new Item.Properties()));

    // -------------------------------------------------------------------------
    // Ice (Glacial) tools (diamond mining level)
    // -------------------------------------------------------------------------
    public static final DeferredHolder<Item, Item> ICE_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.ICE_SWORD,
            () -> new SwordItem(ModToolTiers.ICE_TOOL, 3, -2.4f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> ICE_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.ICE_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.ICE_TOOL, 1, -2.8f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> ICE_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.ICE_SHOVEL,
            () -> new ShovelItem(ModToolTiers.ICE_TOOL, 1.5f, -3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> ICE_AXE = ITEMS.register(GeneratedRegistrationIds.Items.ICE_AXE,
            () -> new AxeItem(ModToolTiers.ICE_TOOL, 6, -3.2f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> ICE_HOE = ITEMS.register(GeneratedRegistrationIds.Items.ICE_HOE,
            () -> new HoeItem(ModToolTiers.ICE_TOOL, 0, -3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> ICE_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.ICE_HELMET,
            () -> new ArmorItem(ModArmorMaterials.ICE, ArmorItem.Type.HELMET,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> ICE_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.ICE_CHESTPLATE,
            () -> new ArmorItem(ModArmorMaterials.ICE, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> ICE_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.ICE_LEGGINGS,
            () -> new ArmorItem(ModArmorMaterials.ICE, ArmorItem.Type.LEGGINGS,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> ICE_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.ICE_BOOTS,
            () -> new ArmorItem(ModArmorMaterials.ICE, ArmorItem.Type.BOOTS,
                    new Item.Properties()));

    // -------------------------------------------------------------------------
    // Polished Quartz tools (diamond mining level)
    // -------------------------------------------------------------------------
    public static final DeferredHolder<Item, Item> PQUARTZ_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.PQUARTZ_SWORD,
            () -> new SwordItem(ModToolTiers.PQUARTZ, 3, -2.4f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> PQUARTZ_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.PQUARTZ_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.PQUARTZ, 1, -2.8f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> PQUARTZ_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.PQUARTZ_SHOVEL,
            () -> new ShovelItem(ModToolTiers.PQUARTZ, 1.5f, -3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> PQUARTZ_AXE = ITEMS.register(GeneratedRegistrationIds.Items.PQUARTZ_AXE,
            () -> new AxeItem(ModToolTiers.PQUARTZ, 6, -3.2f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> PQUARTZ_HOE = ITEMS.register(GeneratedRegistrationIds.Items.PQUARTZ_HOE,
            () -> new HoeItem(ModToolTiers.PQUARTZ, 0, -3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> PQUARTZ_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.PQUARTZ_HELMET,
            () -> new ArmorItem(ModArmorMaterials.PQUARTZ, ArmorItem.Type.HELMET,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> PQUARTZ_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.PQUARTZ_CHESTPLATE,
            () -> new ArmorItem(ModArmorMaterials.PQUARTZ, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> PQUARTZ_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.PQUARTZ_LEGGINGS,
            () -> new ArmorItem(ModArmorMaterials.PQUARTZ, ArmorItem.Type.LEGGINGS,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> PQUARTZ_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.PQUARTZ_BOOTS,
            () -> new ArmorItem(ModArmorMaterials.PQUARTZ, ArmorItem.Type.BOOTS,
                    new Item.Properties()));

    // -------------------------------------------------------------------------
    // Polished Prismarine tools (diamond mining level)
    // -------------------------------------------------------------------------
    public static final DeferredHolder<Item, Item> PPRISM_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.PPRISM_SWORD,
            () -> new SwordItem(ModToolTiers.PPRISM, 3, -2.4f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> PPRISM_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.PPRISM_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.PPRISM, 1, -2.8f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> PPRISM_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.PPRISM_SHOVEL,
            () -> new ShovelItem(ModToolTiers.PPRISM, 1.5f, -3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> PPRISM_AXE = ITEMS.register(GeneratedRegistrationIds.Items.PPRISM_AXE,
            () -> new AxeItem(ModToolTiers.PPRISM, 6, -3.2f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> PPRISM_HOE = ITEMS.register(GeneratedRegistrationIds.Items.PPRISM_HOE,
            () -> new HoeItem(ModToolTiers.PPRISM, 0, -3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> PPRISM_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.PPRISM_HELMET,
            () -> new ArmorItem(ModArmorMaterials.PPRISM, ArmorItem.Type.HELMET,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> PPRISM_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.PPRISM_CHESTPLATE,
            () -> new ArmorItem(ModArmorMaterials.PPRISM, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> PPRISM_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.PPRISM_LEGGINGS,
            () -> new ArmorItem(ModArmorMaterials.PPRISM, ArmorItem.Type.LEGGINGS,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> PPRISM_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.PPRISM_BOOTS,
            () -> new ArmorItem(ModArmorMaterials.PPRISM, ArmorItem.Type.BOOTS,
                    new Item.Properties()));

    // -------------------------------------------------------------------------
    // Flint Tools (rough, stone level)
    // -------------------------------------------------------------------------
    public static final DeferredHolder<Item, Item> RFLINT_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.RFLINT_SWORD,
            () -> new SwordItem(ModToolTiers.RFLINT, 3, -2.4f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> RFLINT_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.RFLINT_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.RFLINT, 1, -2.8f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> RFLINT_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.RFLINT_SHOVEL,
            () -> new ShovelItem(ModToolTiers.RFLINT, 1.5f, -3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> RFLINT_AXE = ITEMS.register(GeneratedRegistrationIds.Items.RFLINT_AXE,
            () -> new AxeItem(ModToolTiers.RFLINT, 6, -3.2f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> RFLINT_HOE = ITEMS.register(GeneratedRegistrationIds.Items.RFLINT_HOE,
            () -> new HoeItem(ModToolTiers.RFLINT, 0, -3f, new Item.Properties()));

    // -------------------------------------------------------------------------
    // Flint-Iron (FNI) Tools + Armor
    // -------------------------------------------------------------------------
    public static final DeferredHolder<Item, Item> FNI_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.FNI_SWORD,
            () -> new SwordItem(ModToolTiers.FNI_TOOLS, 3, -2.4f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> FNI_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.FNI_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.FNI_TOOLS, 1, -2.8f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> FNI_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.FNI_SHOVEL,
            () -> new ShovelItem(ModToolTiers.FNI_TOOLS, 1.5f, -3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> FNI_AXE = ITEMS.register(GeneratedRegistrationIds.Items.FNI_AXE,
            () -> new AxeItem(ModToolTiers.FNI_TOOLS, 6, -3.2f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> FNI_HOE = ITEMS.register(GeneratedRegistrationIds.Items.FNI_HOE,
            () -> new HoeItem(ModToolTiers.FNI_TOOLS, 0, -3f, new Item.Properties()));

    public static final DeferredHolder<Item, Item> FNI_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.FNI_HELMET,
            () -> new ArmorItem(ModArmorMaterials.FNI, ArmorItem.Type.HELMET,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> FNI_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.FNI_CHESTPLATE,
            () -> new ArmorItem(ModArmorMaterials.FNI, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> FNI_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.FNI_LEGGINGS,
            () -> new ArmorItem(ModArmorMaterials.FNI, ArmorItem.Type.LEGGINGS,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> FNI_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.FNI_BOOTS,
            () -> new ArmorItem(ModArmorMaterials.FNI, ArmorItem.Type.BOOTS,
                    new Item.Properties()));

    // -------------------------------------------------------------------------
    // Stone Rock Variant Tools (13 types × 5 tools, each with its own tier)
    // -------------------------------------------------------------------------

    // Andesite — vanilla stone baseline
    public static final DeferredHolder<Item, Item> ANDESITE_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.ANDESITE_SWORD,
            () -> new SwordItem(ModToolTiers.STONE_ANDESITE, 3, -2.4f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> ANDESITE_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.ANDESITE_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.STONE_ANDESITE, 1, -2.8f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> ANDESITE_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.ANDESITE_SHOVEL,
            () -> new ShovelItem(ModToolTiers.STONE_ANDESITE, 1.5f, -3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> ANDESITE_AXE = ITEMS.register(GeneratedRegistrationIds.Items.ANDESITE_AXE,
            () -> new AxeItem(ModToolTiers.STONE_ANDESITE, 6, -3.2f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> ANDESITE_HOE = ITEMS.register(GeneratedRegistrationIds.Items.ANDESITE_HOE,
            () -> new HoeItem(ModToolTiers.STONE_ANDESITE, 0, -3f, new Item.Properties()));

    // Basalt — dense, durable, slow: heavy swing, harder axe/shovel
    public static final DeferredHolder<Item, Item> BASALT_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.BASALT_SWORD,
            () -> new SwordItem(ModToolTiers.STONE_BASALT, 3, -2.5f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> BASALT_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.BASALT_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.STONE_BASALT, 1, -2.9f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> BASALT_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.BASALT_SHOVEL,
            () -> new ShovelItem(ModToolTiers.STONE_BASALT, 2.0f, -3.1f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> BASALT_AXE = ITEMS.register(GeneratedRegistrationIds.Items.BASALT_AXE,
            () -> new AxeItem(ModToolTiers.STONE_BASALT, 7, -3.3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> BASALT_HOE = ITEMS.register(GeneratedRegistrationIds.Items.BASALT_HOE,
            () -> new HoeItem(ModToolTiers.STONE_BASALT, 0, -3.1f, new Item.Properties()));

    // Blackstone — toughest stone variant: heavy swing, hardest hits
    public static final DeferredHolder<Item, Item> BLACKSTONE_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.BLACKSTONE_SWORD,
            () -> new SwordItem(ModToolTiers.STONE_BLACKSTONE, 4, -2.5f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> BLACKSTONE_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.BLACKSTONE_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.STONE_BLACKSTONE, 1, -2.9f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> BLACKSTONE_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.BLACKSTONE_SHOVEL,
            () -> new ShovelItem(ModToolTiers.STONE_BLACKSTONE, 2.0f, -3.1f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> BLACKSTONE_AXE = ITEMS.register(GeneratedRegistrationIds.Items.BLACKSTONE_AXE,
            () -> new AxeItem(ModToolTiers.STONE_BLACKSTONE, 7, -3.35f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> BLACKSTONE_HOE = ITEMS.register(GeneratedRegistrationIds.Items.BLACKSTONE_HOE,
            () -> new HoeItem(ModToolTiers.STONE_BLACKSTONE, 0, -3.1f, new Item.Properties()));

    // Calcite — fragile, light, enchantable: fast swing, weaker hits
    public static final DeferredHolder<Item, Item> CALCITE_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.CALCITE_SWORD,
            () -> new SwordItem(ModToolTiers.STONE_CALCITE, 2, -2.2f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> CALCITE_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.CALCITE_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.STONE_CALCITE, 1, -2.6f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> CALCITE_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.CALCITE_SHOVEL,
            () -> new ShovelItem(ModToolTiers.STONE_CALCITE, 1.0f, -2.8f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> CALCITE_AXE = ITEMS.register(GeneratedRegistrationIds.Items.CALCITE_AXE,
            () -> new AxeItem(ModToolTiers.STONE_CALCITE, 5, -3.0f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> CALCITE_HOE = ITEMS.register(GeneratedRegistrationIds.Items.CALCITE_HOE,
            () -> new HoeItem(ModToolTiers.STONE_CALCITE, 0, -2.6f, new Item.Properties()));

    // Deepslate — most durable stone variant, sluggish: heaviest swing, big hits
    public static final DeferredHolder<Item, Item> DEEPSLATE_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.DEEPSLATE_SWORD,
            () -> new SwordItem(ModToolTiers.STONE_DEEPSLATE, 4, -2.55f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> DEEPSLATE_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.DEEPSLATE_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.STONE_DEEPSLATE, 1, -2.95f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> DEEPSLATE_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.DEEPSLATE_SHOVEL,
            () -> new ShovelItem(ModToolTiers.STONE_DEEPSLATE, 2.0f, -3.15f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> DEEPSLATE_AXE = ITEMS.register(GeneratedRegistrationIds.Items.DEEPSLATE_AXE,
            () -> new AxeItem(ModToolTiers.STONE_DEEPSLATE, 7, -3.4f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> DEEPSLATE_HOE = ITEMS.register(GeneratedRegistrationIds.Items.DEEPSLATE_HOE,
            () -> new HoeItem(ModToolTiers.STONE_DEEPSLATE, 0, -3.1f, new Item.Properties()));

    // Diorite — slightly better all-round than andesite: marginally faster hoe
    public static final DeferredHolder<Item, Item> DIORITE_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.DIORITE_SWORD,
            () -> new SwordItem(ModToolTiers.STONE_DIORITE, 3, -2.4f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> DIORITE_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.DIORITE_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.STONE_DIORITE, 1, -2.8f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> DIORITE_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.DIORITE_SHOVEL,
            () -> new ShovelItem(ModToolTiers.STONE_DIORITE, 1.5f, -3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> DIORITE_AXE = ITEMS.register(GeneratedRegistrationIds.Items.DIORITE_AXE,
            () -> new AxeItem(ModToolTiers.STONE_DIORITE, 6, -3.2f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> DIORITE_HOE = ITEMS.register(GeneratedRegistrationIds.Items.DIORITE_HOE,
            () -> new HoeItem(ModToolTiers.STONE_DIORITE, 0, -2.9f, new Item.Properties()));

    // End Stone — hard alien material, highly enchantable: slightly faster across the board
    public static final DeferredHolder<Item, Item> END_STONE_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.END_STONE_SWORD,
            () -> new SwordItem(ModToolTiers.STONE_END_STONE, 3, -2.35f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> END_STONE_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.END_STONE_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.STONE_END_STONE, 1, -2.75f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> END_STONE_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.END_STONE_SHOVEL,
            () -> new ShovelItem(ModToolTiers.STONE_END_STONE, 1.5f, -2.95f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> END_STONE_AXE = ITEMS.register(GeneratedRegistrationIds.Items.END_STONE_AXE,
            () -> new AxeItem(ModToolTiers.STONE_END_STONE, 6, -3.15f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> END_STONE_HOE = ITEMS.register(GeneratedRegistrationIds.Items.END_STONE_HOE,
            () -> new HoeItem(ModToolTiers.STONE_END_STONE, 0, -2.8f, new Item.Properties()));

    // Granite — hard igneous, strong and heavy: heavy swing, harder axe/shovel
    public static final DeferredHolder<Item, Item> GRANITE_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.GRANITE_SWORD,
            () -> new SwordItem(ModToolTiers.STONE_GRANITE, 3, -2.5f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> GRANITE_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.GRANITE_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.STONE_GRANITE, 1, -2.9f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> GRANITE_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.GRANITE_SHOVEL,
            () -> new ShovelItem(ModToolTiers.STONE_GRANITE, 2.0f, -3.1f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> GRANITE_AXE = ITEMS.register(GeneratedRegistrationIds.Items.GRANITE_AXE,
            () -> new AxeItem(ModToolTiers.STONE_GRANITE, 7, -3.3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> GRANITE_HOE = ITEMS.register(GeneratedRegistrationIds.Items.GRANITE_HOE,
            () -> new HoeItem(ModToolTiers.STONE_GRANITE, 0, -3.1f, new Item.Properties()));

    // Netherrack — crumbly, barely functional, extremely fast: fastest swing, weakest hits
    public static final DeferredHolder<Item, Item> NETHERRACK_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.NETHERRACK_SWORD,
            () -> new SwordItem(ModToolTiers.STONE_NETHERRACK, 2, -2.2f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> NETHERRACK_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.NETHERRACK_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.STONE_NETHERRACK, 1, -2.6f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> NETHERRACK_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.NETHERRACK_SHOVEL,
            () -> new ShovelItem(ModToolTiers.STONE_NETHERRACK, 1.0f, -2.8f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> NETHERRACK_AXE = ITEMS.register(GeneratedRegistrationIds.Items.NETHERRACK_AXE,
            () -> new AxeItem(ModToolTiers.STONE_NETHERRACK, 5, -3.0f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> NETHERRACK_HOE = ITEMS.register(GeneratedRegistrationIds.Items.NETHERRACK_HOE,
            () -> new HoeItem(ModToolTiers.STONE_NETHERRACK, 0, -2.5f, new Item.Properties()));

    // Sandstone — soft sedimentary, brittle but quick: light swing, weaker hits
    public static final DeferredHolder<Item, Item> SANDSTONE_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.SANDSTONE_SWORD,
            () -> new SwordItem(ModToolTiers.STONE_SANDSTONE, 2, -2.3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> SANDSTONE_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.SANDSTONE_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.STONE_SANDSTONE, 1, -2.7f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> SANDSTONE_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.SANDSTONE_SHOVEL,
            () -> new ShovelItem(ModToolTiers.STONE_SANDSTONE, 1.0f, -2.9f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> SANDSTONE_AXE = ITEMS.register(GeneratedRegistrationIds.Items.SANDSTONE_AXE,
            () -> new AxeItem(ModToolTiers.STONE_SANDSTONE, 5, -3.1f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> SANDSTONE_HOE = ITEMS.register(GeneratedRegistrationIds.Items.SANDSTONE_HOE,
            () -> new HoeItem(ModToolTiers.STONE_SANDSTONE, 0, -2.7f, new Item.Properties()));

    // Smooth Basalt — polished volcanic, balanced between basalt and andesite: slightly heavy
    public static final DeferredHolder<Item, Item> SMOOTH_BASALT_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.SMOOTH_BASALT_SWORD,
            () -> new SwordItem(ModToolTiers.STONE_SMOOTH_BASALT, 3, -2.45f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> SMOOTH_BASALT_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.SMOOTH_BASALT_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.STONE_SMOOTH_BASALT, 1, -2.85f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> SMOOTH_BASALT_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.SMOOTH_BASALT_SHOVEL,
            () -> new ShovelItem(ModToolTiers.STONE_SMOOTH_BASALT, 1.5f, -3.05f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> SMOOTH_BASALT_AXE = ITEMS.register(GeneratedRegistrationIds.Items.SMOOTH_BASALT_AXE,
            () -> new AxeItem(ModToolTiers.STONE_SMOOTH_BASALT, 6, -3.25f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> SMOOTH_BASALT_HOE = ITEMS.register(GeneratedRegistrationIds.Items.SMOOTH_BASALT_HOE,
            () -> new HoeItem(ModToolTiers.STONE_SMOOTH_BASALT, 0, -3.0f, new Item.Properties()));

    // Terracotta — baked clay, moderate with better enchantability: slightly faster, lighter
    public static final DeferredHolder<Item, Item> TERRACOTTA_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.TERRACOTTA_SWORD,
            () -> new SwordItem(ModToolTiers.STONE_TERRACOTTA, 3, -2.35f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> TERRACOTTA_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.TERRACOTTA_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.STONE_TERRACOTTA, 1, -2.75f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> TERRACOTTA_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.TERRACOTTA_SHOVEL,
            () -> new ShovelItem(ModToolTiers.STONE_TERRACOTTA, 1.5f, -2.95f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> TERRACOTTA_AXE = ITEMS.register(GeneratedRegistrationIds.Items.TERRACOTTA_AXE,
            () -> new AxeItem(ModToolTiers.STONE_TERRACOTTA, 6, -3.15f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> TERRACOTTA_HOE = ITEMS.register(GeneratedRegistrationIds.Items.TERRACOTTA_HOE,
            () -> new HoeItem(ModToolTiers.STONE_TERRACOTTA, 0, -2.8f, new Item.Properties()));

    // Tuff — compressed volcanic ash, below-average overall: slightly faster, slightly weaker
    public static final DeferredHolder<Item, Item> TUFF_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.TUFF_SWORD,
            () -> new SwordItem(ModToolTiers.STONE_TUFF, 2, -2.35f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> TUFF_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.TUFF_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.STONE_TUFF, 1, -2.75f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> TUFF_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.TUFF_SHOVEL,
            () -> new ShovelItem(ModToolTiers.STONE_TUFF, 1.5f, -2.95f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> TUFF_AXE = ITEMS.register(GeneratedRegistrationIds.Items.TUFF_AXE,
            () -> new AxeItem(ModToolTiers.STONE_TUFF, 5, -3.15f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> TUFF_HOE = ITEMS.register(GeneratedRegistrationIds.Items.TUFF_HOE,
            () -> new HoeItem(ModToolTiers.STONE_TUFF, 0, -2.8f, new Item.Properties()));

    // -------------------------------------------------------------------------
    // Wood Variant Tools (11 types × 5 tools, each with its own tier)
    // -------------------------------------------------------------------------

    // Oak — vanilla baseline
    public static final DeferredHolder<Item, Item> OAK_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.OAK_SWORD,
            () -> new SwordItem(ModToolTiers.WOOD_OAK, 3, -2.4f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> OAK_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.OAK_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.WOOD_OAK, 1, -2.8f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> OAK_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.OAK_SHOVEL,
            () -> new ShovelItem(ModToolTiers.WOOD_OAK, 1.5f, -3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> OAK_AXE = ITEMS.register(GeneratedRegistrationIds.Items.OAK_AXE,
            () -> new AxeItem(ModToolTiers.WOOD_OAK, 6, -3.2f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> OAK_HOE = ITEMS.register(GeneratedRegistrationIds.Items.OAK_HOE,
            () -> new HoeItem(ModToolTiers.WOOD_OAK, 0, -3f, new Item.Properties()));

    // Spruce — sturdy conifer
    public static final DeferredHolder<Item, Item> SPRUCE_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.SPRUCE_SWORD,
            () -> new SwordItem(ModToolTiers.WOOD_SPRUCE, 3, -2.4f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> SPRUCE_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.SPRUCE_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.WOOD_SPRUCE, 1, -2.8f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> SPRUCE_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.SPRUCE_SHOVEL,
            () -> new ShovelItem(ModToolTiers.WOOD_SPRUCE, 1.5f, -3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> SPRUCE_AXE = ITEMS.register(GeneratedRegistrationIds.Items.SPRUCE_AXE,
            () -> new AxeItem(ModToolTiers.WOOD_SPRUCE, 6, -3.2f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> SPRUCE_HOE = ITEMS.register(GeneratedRegistrationIds.Items.SPRUCE_HOE,
            () -> new HoeItem(ModToolTiers.WOOD_SPRUCE, 0, -3f, new Item.Properties()));

    // Birch — light, fast, enchantable
    public static final DeferredHolder<Item, Item> BIRCH_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.BIRCH_SWORD,
            () -> new SwordItem(ModToolTiers.WOOD_BIRCH, 3, -2.4f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> BIRCH_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.BIRCH_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.WOOD_BIRCH, 1, -2.8f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> BIRCH_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.BIRCH_SHOVEL,
            () -> new ShovelItem(ModToolTiers.WOOD_BIRCH, 1.5f, -3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> BIRCH_AXE = ITEMS.register(GeneratedRegistrationIds.Items.BIRCH_AXE,
            () -> new AxeItem(ModToolTiers.WOOD_BIRCH, 6, -3.2f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> BIRCH_HOE = ITEMS.register(GeneratedRegistrationIds.Items.BIRCH_HOE,
            () -> new HoeItem(ModToolTiers.WOOD_BIRCH, 0, -3f, new Item.Properties()));

    // Jungle — dense tropical
    public static final DeferredHolder<Item, Item> JUNGLE_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.JUNGLE_SWORD,
            () -> new SwordItem(ModToolTiers.WOOD_JUNGLE, 3, -2.4f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> JUNGLE_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.JUNGLE_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.WOOD_JUNGLE, 1, -2.8f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> JUNGLE_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.JUNGLE_SHOVEL,
            () -> new ShovelItem(ModToolTiers.WOOD_JUNGLE, 1.5f, -3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> JUNGLE_AXE = ITEMS.register(GeneratedRegistrationIds.Items.JUNGLE_AXE,
            () -> new AxeItem(ModToolTiers.WOOD_JUNGLE, 6, -3.2f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> JUNGLE_HOE = ITEMS.register(GeneratedRegistrationIds.Items.JUNGLE_HOE,
            () -> new HoeItem(ModToolTiers.WOOD_JUNGLE, 0, -3f, new Item.Properties()));

    // Acacia — hard African wood
    public static final DeferredHolder<Item, Item> ACACIA_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.ACACIA_SWORD,
            () -> new SwordItem(ModToolTiers.WOOD_ACACIA, 3, -2.4f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> ACACIA_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.ACACIA_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.WOOD_ACACIA, 1, -2.8f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> ACACIA_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.ACACIA_SHOVEL,
            () -> new ShovelItem(ModToolTiers.WOOD_ACACIA, 1.5f, -3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> ACACIA_AXE = ITEMS.register(GeneratedRegistrationIds.Items.ACACIA_AXE,
            () -> new AxeItem(ModToolTiers.WOOD_ACACIA, 6, -3.2f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> ACACIA_HOE = ITEMS.register(GeneratedRegistrationIds.Items.ACACIA_HOE,
            () -> new HoeItem(ModToolTiers.WOOD_ACACIA, 0, -3f, new Item.Properties()));

    // Dark Oak — dense, durable, slow
    public static final DeferredHolder<Item, Item> DARK_OAK_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.DARK_OAK_SWORD,
            () -> new SwordItem(ModToolTiers.WOOD_DARK_OAK, 3, -2.4f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> DARK_OAK_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.DARK_OAK_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.WOOD_DARK_OAK, 1, -2.8f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> DARK_OAK_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.DARK_OAK_SHOVEL,
            () -> new ShovelItem(ModToolTiers.WOOD_DARK_OAK, 1.5f, -3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> DARK_OAK_AXE = ITEMS.register(GeneratedRegistrationIds.Items.DARK_OAK_AXE,
            () -> new AxeItem(ModToolTiers.WOOD_DARK_OAK, 6, -3.2f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> DARK_OAK_HOE = ITEMS.register(GeneratedRegistrationIds.Items.DARK_OAK_HOE,
            () -> new HoeItem(ModToolTiers.WOOD_DARK_OAK, 0, -3f, new Item.Properties()));

    // Mangrove — tough tropical
    public static final DeferredHolder<Item, Item> MANGROVE_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.MANGROVE_SWORD,
            () -> new SwordItem(ModToolTiers.WOOD_MANGROVE, 3, -2.4f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> MANGROVE_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.MANGROVE_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.WOOD_MANGROVE, 1, -2.8f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> MANGROVE_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.MANGROVE_SHOVEL,
            () -> new ShovelItem(ModToolTiers.WOOD_MANGROVE, 1.5f, -3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> MANGROVE_AXE = ITEMS.register(GeneratedRegistrationIds.Items.MANGROVE_AXE,
            () -> new AxeItem(ModToolTiers.WOOD_MANGROVE, 6, -3.2f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> MANGROVE_HOE = ITEMS.register(GeneratedRegistrationIds.Items.MANGROVE_HOE,
            () -> new HoeItem(ModToolTiers.WOOD_MANGROVE, 0, -3f, new Item.Properties()));

    // Cherry — beautiful, very enchantable
    public static final DeferredHolder<Item, Item> CHERRY_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.CHERRY_SWORD,
            () -> new SwordItem(ModToolTiers.WOOD_CHERRY, 3, -2.4f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> CHERRY_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.CHERRY_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.WOOD_CHERRY, 1, -2.8f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> CHERRY_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.CHERRY_SHOVEL,
            () -> new ShovelItem(ModToolTiers.WOOD_CHERRY, 1.5f, -3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> CHERRY_AXE = ITEMS.register(GeneratedRegistrationIds.Items.CHERRY_AXE,
            () -> new AxeItem(ModToolTiers.WOOD_CHERRY, 6, -3.2f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> CHERRY_HOE = ITEMS.register(GeneratedRegistrationIds.Items.CHERRY_HOE,
            () -> new HoeItem(ModToolTiers.WOOD_CHERRY, 0, -3f, new Item.Properties()));

    // Bamboo — fastest, most fragile
    public static final DeferredHolder<Item, Item> BAMBOO_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.BAMBOO_SWORD,
            () -> new SwordItem(ModToolTiers.WOOD_BAMBOO, 3, -2.4f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> BAMBOO_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.BAMBOO_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.WOOD_BAMBOO, 1, -2.8f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> BAMBOO_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.BAMBOO_SHOVEL,
            () -> new ShovelItem(ModToolTiers.WOOD_BAMBOO, 1.5f, -3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> BAMBOO_AXE = ITEMS.register(GeneratedRegistrationIds.Items.BAMBOO_AXE,
            () -> new AxeItem(ModToolTiers.WOOD_BAMBOO, 6, -3.2f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> BAMBOO_HOE = ITEMS.register(GeneratedRegistrationIds.Items.BAMBOO_HOE,
            () -> new HoeItem(ModToolTiers.WOOD_BAMBOO, 0, -3f, new Item.Properties()));

    // Crimson — tough nether fungus
    public static final DeferredHolder<Item, Item> CRIMSON_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.CRIMSON_SWORD,
            () -> new SwordItem(ModToolTiers.WOOD_CRIMSON, 3, -2.4f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> CRIMSON_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.CRIMSON_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.WOOD_CRIMSON, 1, -2.8f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> CRIMSON_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.CRIMSON_SHOVEL,
            () -> new ShovelItem(ModToolTiers.WOOD_CRIMSON, 1.5f, -3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> CRIMSON_AXE = ITEMS.register(GeneratedRegistrationIds.Items.CRIMSON_AXE,
            () -> new AxeItem(ModToolTiers.WOOD_CRIMSON, 6, -3.2f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> CRIMSON_HOE = ITEMS.register(GeneratedRegistrationIds.Items.CRIMSON_HOE,
            () -> new HoeItem(ModToolTiers.WOOD_CRIMSON, 0, -3f, new Item.Properties()));

    // Warped — resilient, enchantable
    public static final DeferredHolder<Item, Item> WARPED_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.WARPED_SWORD,
            () -> new SwordItem(ModToolTiers.WOOD_WARPED, 3, -2.4f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> WARPED_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.WARPED_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.WOOD_WARPED, 1, -2.8f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> WARPED_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.WARPED_SHOVEL,
            () -> new ShovelItem(ModToolTiers.WOOD_WARPED, 1.5f, -3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> WARPED_AXE = ITEMS.register(GeneratedRegistrationIds.Items.WARPED_AXE,
            () -> new AxeItem(ModToolTiers.WOOD_WARPED, 6, -3.2f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> WARPED_HOE = ITEMS.register(GeneratedRegistrationIds.Items.WARPED_HOE,
            () -> new HoeItem(ModToolTiers.WOOD_WARPED, 0, -3f, new Item.Properties()));

    // -------------------------------------------------------------------------
    // Leather tools (very weak, worse than wood)
    // -------------------------------------------------------------------------
    public static final DeferredHolder<Item, Item> LEATHER_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.LEATHER_SWORD,
            () -> new SwordItem(ModToolTiers.LEATHER, 3, -2.4f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> LEATHER_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.LEATHER_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.LEATHER, 1, -2.8f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> LEATHER_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.LEATHER_SHOVEL,
            () -> new ShovelItem(ModToolTiers.LEATHER, 1.5f, -3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> LEATHER_AXE = ITEMS.register(GeneratedRegistrationIds.Items.LEATHER_AXE,
            () -> new AxeItem(ModToolTiers.LEATHER, 6, -3.2f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> LEATHER_HOE = ITEMS.register(GeneratedRegistrationIds.Items.LEATHER_HOE,
            () -> new HoeItem(ModToolTiers.LEATHER, 0, -3f, new Item.Properties()));

    // =========================================================================
    // Vanilla Material Sets (25 sets, ordered by power tier)
    // =========================================================================

    // ── Paper (tools only, ultra-weak) ──────────────────────────────────────
    public static final DeferredHolder<Item, Item> PAPER_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.PAPER_SWORD,
            () -> new SwordItem(ModToolTiers.PAPER, 3, -2.4f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> PAPER_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.PAPER_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.PAPER, 1, -2.8f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> PAPER_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.PAPER_SHOVEL,
            () -> new ShovelItem(ModToolTiers.PAPER, 1.5f, -3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> PAPER_AXE = ITEMS.register(GeneratedRegistrationIds.Items.PAPER_AXE,
            () -> new AxeItem(ModToolTiers.PAPER, 6, -3.2f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> PAPER_HOE = ITEMS.register(GeneratedRegistrationIds.Items.PAPER_HOE,
            () -> new HoeItem(ModToolTiers.PAPER, 0, -3f, new Item.Properties()));

    // ── Feather (tools only, ultra-weak) ────────────────────────────────────
    public static final DeferredHolder<Item, Item> FEATHER_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.FEATHER_SWORD,
            () -> new SwordItem(ModToolTiers.FEATHER, 3, -2.4f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> FEATHER_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.FEATHER_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.FEATHER, 1, -2.8f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> FEATHER_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.FEATHER_SHOVEL,
            () -> new ShovelItem(ModToolTiers.FEATHER, 1.5f, -3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> FEATHER_AXE = ITEMS.register(GeneratedRegistrationIds.Items.FEATHER_AXE,
            () -> new AxeItem(ModToolTiers.FEATHER, 6, -3.2f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> FEATHER_HOE = ITEMS.register(GeneratedRegistrationIds.Items.FEATHER_HOE,
            () -> new HoeItem(ModToolTiers.FEATHER, 0, -3f, new Item.Properties()));

    // ── Glass (tools only, fragile but sharp) ───────────────────────────────
    public static final DeferredHolder<Item, Item> GLASS_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.GLASS_SWORD,
            () -> new SwordItem(ModToolTiers.GLASS, 3, -2.4f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> GLASS_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.GLASS_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.GLASS, 1, -2.8f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> GLASS_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.GLASS_SHOVEL,
            () -> new ShovelItem(ModToolTiers.GLASS, 1.5f, -3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> GLASS_AXE = ITEMS.register(GeneratedRegistrationIds.Items.GLASS_AXE,
            () -> new AxeItem(ModToolTiers.GLASS, 6, -3.2f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> GLASS_HOE = ITEMS.register(GeneratedRegistrationIds.Items.GLASS_HOE,
            () -> new HoeItem(ModToolTiers.GLASS, 0, -3f, new Item.Properties()));

    // ── Rabbit Hide (armor only) ────────────────────────────────────────────
    public static final DeferredHolder<Item, Item> RABBIT_HIDE_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.RABBIT_HIDE_HELMET,
            () -> new ArmorItem(ModArmorMaterials.RABBIT_HIDE, ArmorItem.Type.HELMET,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> RABBIT_HIDE_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.RABBIT_HIDE_CHESTPLATE,
            () -> new ArmorItem(ModArmorMaterials.RABBIT_HIDE, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> RABBIT_HIDE_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.RABBIT_HIDE_LEGGINGS,
            () -> new ArmorItem(ModArmorMaterials.RABBIT_HIDE, ArmorItem.Type.LEGGINGS,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> RABBIT_HIDE_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.RABBIT_HIDE_BOOTS,
            () -> new ArmorItem(ModArmorMaterials.RABBIT_HIDE, ArmorItem.Type.BOOTS,
                    new Item.Properties()));

    // ── Cactus (tools + armor) ──────────────────────────────────────────────
    public static final DeferredHolder<Item, Item> CACTUS_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.CACTUS_SWORD,
            () -> new SwordItem(ModToolTiers.CACTUS, 3, -2.4f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> CACTUS_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.CACTUS_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.CACTUS, 1, -2.8f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> CACTUS_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.CACTUS_SHOVEL,
            () -> new ShovelItem(ModToolTiers.CACTUS, 1.5f, -3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> CACTUS_AXE = ITEMS.register(GeneratedRegistrationIds.Items.CACTUS_AXE,
            () -> new AxeItem(ModToolTiers.CACTUS, 6, -3.2f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> CACTUS_HOE = ITEMS.register(GeneratedRegistrationIds.Items.CACTUS_HOE,
            () -> new HoeItem(ModToolTiers.CACTUS, 0, -3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> CACTUS_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.CACTUS_HELMET,
            () -> new ArmorItem(ModArmorMaterials.CACTUS, ArmorItem.Type.HELMET,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> CACTUS_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.CACTUS_CHESTPLATE,
            () -> new ArmorItem(ModArmorMaterials.CACTUS, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> CACTUS_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.CACTUS_LEGGINGS,
            () -> new ArmorItem(ModArmorMaterials.CACTUS, ArmorItem.Type.LEGGINGS,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> CACTUS_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.CACTUS_BOOTS,
            () -> new ArmorItem(ModArmorMaterials.CACTUS, ArmorItem.Type.BOOTS,
                    new Item.Properties()));

    // ── Sponge (tools only) ─────────────────────────────────────────────────
    public static final DeferredHolder<Item, Item> SPONGE_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.SPONGE_SWORD,
            () -> new SwordItem(ModToolTiers.SPONGE, 3, -2.4f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> SPONGE_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.SPONGE_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.SPONGE, 1, -2.8f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> SPONGE_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.SPONGE_SHOVEL,
            () -> new ShovelItem(ModToolTiers.SPONGE, 1.5f, -3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> SPONGE_AXE = ITEMS.register(GeneratedRegistrationIds.Items.SPONGE_AXE,
            () -> new AxeItem(ModToolTiers.SPONGE, 6, -3.2f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> SPONGE_HOE = ITEMS.register(GeneratedRegistrationIds.Items.SPONGE_HOE,
            () -> new HoeItem(ModToolTiers.SPONGE, 0, -3f, new Item.Properties()));

    // ── Bone (tools + armor) ────────────────────────────────────────────────
    public static final DeferredHolder<Item, Item> BONE_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.BONE_SWORD,
            () -> new SwordItem(ModToolTiers.BONE, 3, -2.4f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> BONE_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.BONE_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.BONE, 1, -2.8f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> BONE_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.BONE_SHOVEL,
            () -> new ShovelItem(ModToolTiers.BONE, 1.5f, -3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> BONE_AXE = ITEMS.register(GeneratedRegistrationIds.Items.BONE_AXE,
            () -> new AxeItem(ModToolTiers.BONE, 6, -3.2f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> BONE_HOE = ITEMS.register(GeneratedRegistrationIds.Items.BONE_HOE,
            () -> new HoeItem(ModToolTiers.BONE, 0, -3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> BONE_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.BONE_HELMET,
            () -> new ArmorItem(ModArmorMaterials.BONE, ArmorItem.Type.HELMET,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> BONE_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.BONE_CHESTPLATE,
            () -> new ArmorItem(ModArmorMaterials.BONE, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> BONE_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.BONE_LEGGINGS,
            () -> new ArmorItem(ModArmorMaterials.BONE, ArmorItem.Type.LEGGINGS,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> BONE_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.BONE_BOOTS,
            () -> new ArmorItem(ModArmorMaterials.BONE, ArmorItem.Type.BOOTS,
                    new Item.Properties()));

    // ── Clay (tools + armor) ────────────────────────────────────────────────
    public static final DeferredHolder<Item, Item> CLAY_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.CLAY_SWORD,
            () -> new SwordItem(ModToolTiers.CLAY, 3, -2.4f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> CLAY_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.CLAY_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.CLAY, 1, -2.8f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> CLAY_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.CLAY_SHOVEL,
            () -> new ShovelItem(ModToolTiers.CLAY, 1.5f, -3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> CLAY_AXE = ITEMS.register(GeneratedRegistrationIds.Items.CLAY_AXE,
            () -> new AxeItem(ModToolTiers.CLAY, 6, -3.2f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> CLAY_HOE = ITEMS.register(GeneratedRegistrationIds.Items.CLAY_HOE,
            () -> new HoeItem(ModToolTiers.CLAY, 0, -3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> CLAY_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.CLAY_HELMET,
            () -> new ArmorItem(ModArmorMaterials.CLAY, ArmorItem.Type.HELMET,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> CLAY_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.CLAY_CHESTPLATE,
            () -> new ArmorItem(ModArmorMaterials.CLAY, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> CLAY_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.CLAY_LEGGINGS,
            () -> new ArmorItem(ModArmorMaterials.CLAY, ArmorItem.Type.LEGGINGS,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> CLAY_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.CLAY_BOOTS,
            () -> new ArmorItem(ModArmorMaterials.CLAY, ArmorItem.Type.BOOTS,
                    new Item.Properties()));

    // ── Nether Wart (tools only) ────────────────────────────────────────────
    public static final DeferredHolder<Item, Item> NETHER_WART_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.NETHER_WART_SWORD,
            () -> new SwordItem(ModToolTiers.NETHER_WART, 3, -2.4f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> NETHER_WART_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.NETHER_WART_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.NETHER_WART, 1, -2.8f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> NETHER_WART_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.NETHER_WART_SHOVEL,
            () -> new ShovelItem(ModToolTiers.NETHER_WART, 1.5f, -3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> NETHER_WART_AXE = ITEMS.register(GeneratedRegistrationIds.Items.NETHER_WART_AXE,
            () -> new AxeItem(ModToolTiers.NETHER_WART, 6, -3.2f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> NETHER_WART_HOE = ITEMS.register(GeneratedRegistrationIds.Items.NETHER_WART_HOE,
            () -> new HoeItem(ModToolTiers.NETHER_WART, 0, -3f, new Item.Properties()));

    // ── Brick (tools + armor) ───────────────────────────────────────────────
    public static final DeferredHolder<Item, Item> BRICK_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.BRICK_SWORD,
            () -> new SwordItem(ModToolTiers.BRICK, 3, -2.4f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> BRICK_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.BRICK_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.BRICK, 1, -2.8f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> BRICK_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.BRICK_SHOVEL,
            () -> new ShovelItem(ModToolTiers.BRICK, 1.5f, -3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> BRICK_AXE = ITEMS.register(GeneratedRegistrationIds.Items.BRICK_AXE,
            () -> new AxeItem(ModToolTiers.BRICK, 6, -3.2f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> BRICK_HOE = ITEMS.register(GeneratedRegistrationIds.Items.BRICK_HOE,
            () -> new HoeItem(ModToolTiers.BRICK, 0, -3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> BRICK_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.BRICK_HELMET,
            () -> new ArmorItem(ModArmorMaterials.BRICK, ArmorItem.Type.HELMET,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> BRICK_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.BRICK_CHESTPLATE,
            () -> new ArmorItem(ModArmorMaterials.BRICK, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> BRICK_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.BRICK_LEGGINGS,
            () -> new ArmorItem(ModArmorMaterials.BRICK, ArmorItem.Type.LEGGINGS,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> BRICK_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.BRICK_BOOTS,
            () -> new ArmorItem(ModArmorMaterials.BRICK, ArmorItem.Type.BOOTS,
                    new Item.Properties()));

    // ── Nether Brick (tools + armor) ────────────────────────────────────────
    public static final DeferredHolder<Item, Item> NETHER_BRICK_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.NETHER_BRICK_SWORD,
            () -> new SwordItem(ModToolTiers.NETHER_BRICK, 3, -2.4f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> NETHER_BRICK_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.NETHER_BRICK_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.NETHER_BRICK, 1, -2.8f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> NETHER_BRICK_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.NETHER_BRICK_SHOVEL,
            () -> new ShovelItem(ModToolTiers.NETHER_BRICK, 1.5f, -3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> NETHER_BRICK_AXE = ITEMS.register(GeneratedRegistrationIds.Items.NETHER_BRICK_AXE,
            () -> new AxeItem(ModToolTiers.NETHER_BRICK, 6, -3.2f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> NETHER_BRICK_HOE = ITEMS.register(GeneratedRegistrationIds.Items.NETHER_BRICK_HOE,
            () -> new HoeItem(ModToolTiers.NETHER_BRICK, 0, -3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> NETHER_BRICK_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.NETHER_BRICK_HELMET,
            () -> new ArmorItem(ModArmorMaterials.NETHER_BRICK, ArmorItem.Type.HELMET,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> NETHER_BRICK_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.NETHER_BRICK_CHESTPLATE,
            () -> new ArmorItem(ModArmorMaterials.NETHER_BRICK, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> NETHER_BRICK_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.NETHER_BRICK_LEGGINGS,
            () -> new ArmorItem(ModArmorMaterials.NETHER_BRICK, ArmorItem.Type.LEGGINGS,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> NETHER_BRICK_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.NETHER_BRICK_BOOTS,
            () -> new ArmorItem(ModArmorMaterials.NETHER_BRICK, ArmorItem.Type.BOOTS,
                    new Item.Properties()));

    // ── Pointed Dripstone (tools only) ──────────────────────────────────────
    public static final DeferredHolder<Item, Item> POINTED_DRIPSTONE_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.POINTED_DRIPSTONE_SWORD,
            () -> new SwordItem(ModToolTiers.POINTED_DRIPSTONE, 3, -2.4f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> POINTED_DRIPSTONE_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.POINTED_DRIPSTONE_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.POINTED_DRIPSTONE, 1, -2.8f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> POINTED_DRIPSTONE_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.POINTED_DRIPSTONE_SHOVEL,
            () -> new ShovelItem(ModToolTiers.POINTED_DRIPSTONE, 1.5f, -3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> POINTED_DRIPSTONE_AXE = ITEMS.register(GeneratedRegistrationIds.Items.POINTED_DRIPSTONE_AXE,
            () -> new AxeItem(ModToolTiers.POINTED_DRIPSTONE, 6, -3.2f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> POINTED_DRIPSTONE_HOE = ITEMS.register(GeneratedRegistrationIds.Items.POINTED_DRIPSTONE_HOE,
            () -> new HoeItem(ModToolTiers.POINTED_DRIPSTONE, 0, -3f, new Item.Properties()));

    // ── Copper (tools + armor) ──────────────────────────────────────────────
    public static final DeferredHolder<Item, Item> COPPER_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.COPPER_SWORD,
            () -> new SwordItem(ModToolTiers.COPPER, 3, -2.4f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> COPPER_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.COPPER_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.COPPER, 1, -2.8f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> COPPER_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.COPPER_SHOVEL,
            () -> new ShovelItem(ModToolTiers.COPPER, 1.5f, -3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> COPPER_AXE = ITEMS.register(GeneratedRegistrationIds.Items.COPPER_AXE,
            () -> new AxeItem(ModToolTiers.COPPER, 6, -3.2f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> COPPER_HOE = ITEMS.register(GeneratedRegistrationIds.Items.COPPER_HOE,
            () -> new HoeItem(ModToolTiers.COPPER, 0, -3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> COPPER_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.COPPER_HELMET,
            () -> new ArmorItem(ModArmorMaterials.COPPER, ArmorItem.Type.HELMET,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> COPPER_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.COPPER_CHESTPLATE,
            () -> new ArmorItem(ModArmorMaterials.COPPER, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> COPPER_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.COPPER_LEGGINGS,
            () -> new ArmorItem(ModArmorMaterials.COPPER, ArmorItem.Type.LEGGINGS,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> COPPER_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.COPPER_BOOTS,
            () -> new ArmorItem(ModArmorMaterials.COPPER, ArmorItem.Type.BOOTS,
                    new Item.Properties()));

    // ── Phantom Membrane (tools + armor) ────────────────────────────────────
    public static final DeferredHolder<Item, Item> PHANTOM_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.PHANTOM_SWORD,
            () -> new SwordItem(ModToolTiers.PHANTOM_MEMBRANE, 3, -2.4f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> PHANTOM_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.PHANTOM_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.PHANTOM_MEMBRANE, 1, -2.8f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> PHANTOM_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.PHANTOM_SHOVEL,
            () -> new ShovelItem(ModToolTiers.PHANTOM_MEMBRANE, 1.5f, -3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> PHANTOM_AXE = ITEMS.register(GeneratedRegistrationIds.Items.PHANTOM_AXE,
            () -> new AxeItem(ModToolTiers.PHANTOM_MEMBRANE, 6, -3.2f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> PHANTOM_HOE = ITEMS.register(GeneratedRegistrationIds.Items.PHANTOM_HOE,
            () -> new HoeItem(ModToolTiers.PHANTOM_MEMBRANE, 0, -3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> PHANTOM_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.PHANTOM_HELMET,
            () -> new ArmorItem(ModArmorMaterials.PHANTOM, ArmorItem.Type.HELMET,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> PHANTOM_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.PHANTOM_CHESTPLATE,
            () -> new ArmorItem(ModArmorMaterials.PHANTOM, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> PHANTOM_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.PHANTOM_LEGGINGS,
            () -> new ArmorItem(ModArmorMaterials.PHANTOM, ArmorItem.Type.LEGGINGS,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> PHANTOM_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.PHANTOM_BOOTS,
            () -> new ArmorItem(ModArmorMaterials.PHANTOM, ArmorItem.Type.BOOTS,
                    new Item.Properties()));

    // ── Magma Cream (tools + armor) ─────────────────────────────────────────
    public static final DeferredHolder<Item, Item> MAGMA_CREAM_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.MAGMA_CREAM_SWORD,
            () -> new SwordItem(ModToolTiers.MAGMA_CREAM, 3, -2.4f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> MAGMA_CREAM_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.MAGMA_CREAM_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.MAGMA_CREAM, 1, -2.8f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> MAGMA_CREAM_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.MAGMA_CREAM_SHOVEL,
            () -> new ShovelItem(ModToolTiers.MAGMA_CREAM, 1.5f, -3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> MAGMA_CREAM_AXE = ITEMS.register(GeneratedRegistrationIds.Items.MAGMA_CREAM_AXE,
            () -> new AxeItem(ModToolTiers.MAGMA_CREAM, 6, -3.2f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> MAGMA_CREAM_HOE = ITEMS.register(GeneratedRegistrationIds.Items.MAGMA_CREAM_HOE,
            () -> new HoeItem(ModToolTiers.MAGMA_CREAM, 0, -3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> MAGMA_CREAM_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.MAGMA_CREAM_HELMET,
            () -> new ArmorItem(ModArmorMaterials.MAGMA_CREAM, ArmorItem.Type.HELMET,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> MAGMA_CREAM_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.MAGMA_CREAM_CHESTPLATE,
            () -> new ArmorItem(ModArmorMaterials.MAGMA_CREAM, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> MAGMA_CREAM_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.MAGMA_CREAM_LEGGINGS,
            () -> new ArmorItem(ModArmorMaterials.MAGMA_CREAM, ArmorItem.Type.LEGGINGS,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> MAGMA_CREAM_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.MAGMA_CREAM_BOOTS,
            () -> new ArmorItem(ModArmorMaterials.MAGMA_CREAM, ArmorItem.Type.BOOTS,
                    new Item.Properties()));

    // ── Slime (tools + armor) ───────────────────────────────────────────────
    public static final DeferredHolder<Item, Item> SLIME_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.SLIME_SWORD,
            () -> new SwordItem(ModToolTiers.SLIME, 3, -2.4f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> SLIME_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.SLIME_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.SLIME, 1, -2.8f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> SLIME_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.SLIME_SHOVEL,
            () -> new ShovelItem(ModToolTiers.SLIME, 1.5f, -3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> SLIME_AXE = ITEMS.register(GeneratedRegistrationIds.Items.SLIME_AXE,
            () -> new AxeItem(ModToolTiers.SLIME, 6, -3.2f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> SLIME_HOE = ITEMS.register(GeneratedRegistrationIds.Items.SLIME_HOE,
            () -> new HoeItem(ModToolTiers.SLIME, 0, -3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> SLIME_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.SLIME_HELMET,
            () -> new ArmorItem(ModArmorMaterials.SLIME, ArmorItem.Type.HELMET,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> SLIME_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.SLIME_CHESTPLATE,
            () -> new ArmorItem(ModArmorMaterials.SLIME, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> SLIME_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.SLIME_LEGGINGS,
            () -> new ArmorItem(ModArmorMaterials.SLIME, ArmorItem.Type.LEGGINGS,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> SLIME_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.SLIME_BOOTS,
            () -> new ArmorItem(ModArmorMaterials.SLIME, ArmorItem.Type.BOOTS,
                    new Item.Properties()));

    // ── Blaze Rod (tools + armor) ───────────────────────────────────────────
    public static final DeferredHolder<Item, Item> BLAZE_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.BLAZE_SWORD,
            () -> new SwordItem(ModToolTiers.BLAZE_ROD, 3, -2.4f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> BLAZE_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.BLAZE_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.BLAZE_ROD, 1, -2.8f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> BLAZE_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.BLAZE_SHOVEL,
            () -> new ShovelItem(ModToolTiers.BLAZE_ROD, 1.5f, -3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> BLAZE_AXE = ITEMS.register(GeneratedRegistrationIds.Items.BLAZE_AXE,
            () -> new AxeItem(ModToolTiers.BLAZE_ROD, 6, -3.2f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> BLAZE_HOE = ITEMS.register(GeneratedRegistrationIds.Items.BLAZE_HOE,
            () -> new HoeItem(ModToolTiers.BLAZE_ROD, 0, -3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> BLAZE_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.BLAZE_HELMET,
            () -> new ArmorItem(ModArmorMaterials.BLAZE, ArmorItem.Type.HELMET,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> BLAZE_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.BLAZE_CHESTPLATE,
            () -> new ArmorItem(ModArmorMaterials.BLAZE, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> BLAZE_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.BLAZE_LEGGINGS,
            () -> new ArmorItem(ModArmorMaterials.BLAZE, ArmorItem.Type.LEGGINGS,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> BLAZE_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.BLAZE_BOOTS,
            () -> new ArmorItem(ModArmorMaterials.BLAZE, ArmorItem.Type.BOOTS,
                    new Item.Properties()));

    // ── Nautilus Shell (tools + armor) ──────────────────────────────────────
    public static final DeferredHolder<Item, Item> NAUTILUS_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.NAUTILUS_SWORD,
            () -> new SwordItem(ModToolTiers.NAUTILUS_SHELL, 3, -2.4f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> NAUTILUS_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.NAUTILUS_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.NAUTILUS_SHELL, 1, -2.8f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> NAUTILUS_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.NAUTILUS_SHOVEL,
            () -> new ShovelItem(ModToolTiers.NAUTILUS_SHELL, 1.5f, -3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> NAUTILUS_AXE = ITEMS.register(GeneratedRegistrationIds.Items.NAUTILUS_AXE,
            () -> new AxeItem(ModToolTiers.NAUTILUS_SHELL, 6, -3.2f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> NAUTILUS_HOE = ITEMS.register(GeneratedRegistrationIds.Items.NAUTILUS_HOE,
            () -> new HoeItem(ModToolTiers.NAUTILUS_SHELL, 0, -3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> NAUTILUS_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.NAUTILUS_HELMET,
            () -> new ArmorItem(ModArmorMaterials.NAUTILUS, ArmorItem.Type.HELMET,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> NAUTILUS_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.NAUTILUS_CHESTPLATE,
            () -> new ArmorItem(ModArmorMaterials.NAUTILUS, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> NAUTILUS_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.NAUTILUS_LEGGINGS,
            () -> new ArmorItem(ModArmorMaterials.NAUTILUS, ArmorItem.Type.LEGGINGS,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> NAUTILUS_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.NAUTILUS_BOOTS,
            () -> new ArmorItem(ModArmorMaterials.NAUTILUS, ArmorItem.Type.BOOTS,
                    new Item.Properties()));

    // ── Purpur (tools + armor) ──────────────────────────────────────────────
    public static final DeferredHolder<Item, Item> PURPUR_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.PURPUR_SWORD,
            () -> new SwordItem(ModToolTiers.PURPUR, 3, -2.4f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> PURPUR_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.PURPUR_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.PURPUR, 1, -2.8f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> PURPUR_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.PURPUR_SHOVEL,
            () -> new ShovelItem(ModToolTiers.PURPUR, 1.5f, -3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> PURPUR_AXE = ITEMS.register(GeneratedRegistrationIds.Items.PURPUR_AXE,
            () -> new AxeItem(ModToolTiers.PURPUR, 6, -3.2f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> PURPUR_HOE = ITEMS.register(GeneratedRegistrationIds.Items.PURPUR_HOE,
            () -> new HoeItem(ModToolTiers.PURPUR, 0, -3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> PURPUR_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.PURPUR_HELMET,
            () -> new ArmorItem(ModArmorMaterials.PURPUR, ArmorItem.Type.HELMET,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> PURPUR_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.PURPUR_CHESTPLATE,
            () -> new ArmorItem(ModArmorMaterials.PURPUR, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> PURPUR_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.PURPUR_LEGGINGS,
            () -> new ArmorItem(ModArmorMaterials.PURPUR, ArmorItem.Type.LEGGINGS,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> PURPUR_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.PURPUR_BOOTS,
            () -> new ArmorItem(ModArmorMaterials.PURPUR, ArmorItem.Type.BOOTS,
                    new Item.Properties()));

    // ── Ghast Tear (tools + armor) ──────────────────────────────────────────
    public static final DeferredHolder<Item, Item> GHAST_TEAR_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.GHAST_TEAR_SWORD,
            () -> new SwordItem(ModToolTiers.GHAST_TEAR, 3, -2.4f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> GHAST_TEAR_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.GHAST_TEAR_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.GHAST_TEAR, 1, -2.8f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> GHAST_TEAR_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.GHAST_TEAR_SHOVEL,
            () -> new ShovelItem(ModToolTiers.GHAST_TEAR, 1.5f, -3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> GHAST_TEAR_AXE = ITEMS.register(GeneratedRegistrationIds.Items.GHAST_TEAR_AXE,
            () -> new AxeItem(ModToolTiers.GHAST_TEAR, 6, -3.2f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> GHAST_TEAR_HOE = ITEMS.register(GeneratedRegistrationIds.Items.GHAST_TEAR_HOE,
            () -> new HoeItem(ModToolTiers.GHAST_TEAR, 0, -3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> GHAST_TEAR_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.GHAST_TEAR_HELMET,
            () -> new ArmorItem(ModArmorMaterials.GHAST_TEAR, ArmorItem.Type.HELMET,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> GHAST_TEAR_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.GHAST_TEAR_CHESTPLATE,
            () -> new ArmorItem(ModArmorMaterials.GHAST_TEAR, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> GHAST_TEAR_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.GHAST_TEAR_LEGGINGS,
            () -> new ArmorItem(ModArmorMaterials.GHAST_TEAR, ArmorItem.Type.LEGGINGS,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> GHAST_TEAR_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.GHAST_TEAR_BOOTS,
            () -> new ArmorItem(ModArmorMaterials.GHAST_TEAR, ArmorItem.Type.BOOTS,
                    new Item.Properties()));

    // ── Eye of Ender (tools + armor) ────────────────────────────────────────
    public static final DeferredHolder<Item, Item> EYE_OF_ENDER_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.EYE_OF_ENDER_SWORD,
            () -> new SwordItem(ModToolTiers.EYE_OF_ENDER, 3, -2.4f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> EYE_OF_ENDER_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.EYE_OF_ENDER_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.EYE_OF_ENDER, 1, -2.8f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> EYE_OF_ENDER_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.EYE_OF_ENDER_SHOVEL,
            () -> new ShovelItem(ModToolTiers.EYE_OF_ENDER, 1.5f, -3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> EYE_OF_ENDER_AXE = ITEMS.register(GeneratedRegistrationIds.Items.EYE_OF_ENDER_AXE,
            () -> new AxeItem(ModToolTiers.EYE_OF_ENDER, 6, -3.2f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> EYE_OF_ENDER_HOE = ITEMS.register(GeneratedRegistrationIds.Items.EYE_OF_ENDER_HOE,
            () -> new HoeItem(ModToolTiers.EYE_OF_ENDER, 0, -3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> EYE_OF_ENDER_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.EYE_OF_ENDER_HELMET,
            () -> new ArmorItem(ModArmorMaterials.EYE_OF_ENDER, ArmorItem.Type.HELMET,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> EYE_OF_ENDER_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.EYE_OF_ENDER_CHESTPLATE,
            () -> new ArmorItem(ModArmorMaterials.EYE_OF_ENDER, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> EYE_OF_ENDER_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.EYE_OF_ENDER_LEGGINGS,
            () -> new ArmorItem(ModArmorMaterials.EYE_OF_ENDER, ArmorItem.Type.LEGGINGS,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> EYE_OF_ENDER_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.EYE_OF_ENDER_BOOTS,
            () -> new ArmorItem(ModArmorMaterials.EYE_OF_ENDER, ArmorItem.Type.BOOTS,
                    new Item.Properties()));

    // ── Shulker Shell (tools + armor) ───────────────────────────────────────
    public static final DeferredHolder<Item, Item> SHULKER_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.SHULKER_SWORD,
            () -> new SwordItem(ModToolTiers.SHULKER_SHELL, 3, -2.4f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> SHULKER_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.SHULKER_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.SHULKER_SHELL, 1, -2.8f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> SHULKER_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.SHULKER_SHOVEL,
            () -> new ShovelItem(ModToolTiers.SHULKER_SHELL, 1.5f, -3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> SHULKER_AXE = ITEMS.register(GeneratedRegistrationIds.Items.SHULKER_AXE,
            () -> new AxeItem(ModToolTiers.SHULKER_SHELL, 6, -3.2f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> SHULKER_HOE = ITEMS.register(GeneratedRegistrationIds.Items.SHULKER_HOE,
            () -> new HoeItem(ModToolTiers.SHULKER_SHELL, 0, -3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> SHULKER_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.SHULKER_HELMET,
            () -> new ArmorItem(ModArmorMaterials.SHULKER, ArmorItem.Type.HELMET,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> SHULKER_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.SHULKER_CHESTPLATE,
            () -> new ArmorItem(ModArmorMaterials.SHULKER, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> SHULKER_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.SHULKER_LEGGINGS,
            () -> new ArmorItem(ModArmorMaterials.SHULKER, ArmorItem.Type.LEGGINGS,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> SHULKER_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.SHULKER_BOOTS,
            () -> new ArmorItem(ModArmorMaterials.SHULKER, ArmorItem.Type.BOOTS,
                    new Item.Properties()));

    // ── Turtle Scute (armor only) ───────────────────────────────────────────
    public static final DeferredHolder<Item, Item> TURTLE_SCUTE_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.TURTLE_SCUTE_HELMET,
            () -> new ArmorItem(ModArmorMaterials.TURTLE_SCUTE, ArmorItem.Type.HELMET,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> TURTLE_SCUTE_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.TURTLE_SCUTE_CHESTPLATE,
            () -> new ArmorItem(ModArmorMaterials.TURTLE_SCUTE, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> TURTLE_SCUTE_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.TURTLE_SCUTE_LEGGINGS,
            () -> new ArmorItem(ModArmorMaterials.TURTLE_SCUTE, ArmorItem.Type.LEGGINGS,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> TURTLE_SCUTE_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.TURTLE_SCUTE_BOOTS,
            () -> new ArmorItem(ModArmorMaterials.TURTLE_SCUTE, ArmorItem.Type.BOOTS,
                    new Item.Properties()));

    // ── Echo Shard (tools + armor) ──────────────────────────────────────────
    public static final DeferredHolder<Item, Item> ECHO_SHARD_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.ECHO_SHARD_SWORD,
            () -> new SwordItem(ModToolTiers.ECHO_SHARD, 3, -2.4f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> ECHO_SHARD_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.ECHO_SHARD_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.ECHO_SHARD, 1, -2.8f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> ECHO_SHARD_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.ECHO_SHARD_SHOVEL,
            () -> new ShovelItem(ModToolTiers.ECHO_SHARD, 1.5f, -3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> ECHO_SHARD_AXE = ITEMS.register(GeneratedRegistrationIds.Items.ECHO_SHARD_AXE,
            () -> new AxeItem(ModToolTiers.ECHO_SHARD, 6, -3.2f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> ECHO_SHARD_HOE = ITEMS.register(GeneratedRegistrationIds.Items.ECHO_SHARD_HOE,
            () -> new HoeItem(ModToolTiers.ECHO_SHARD, 0, -3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> ECHO_SHARD_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.ECHO_SHARD_HELMET,
            () -> new ArmorItem(ModArmorMaterials.ECHO_SHARD, ArmorItem.Type.HELMET,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> ECHO_SHARD_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.ECHO_SHARD_CHESTPLATE,
            () -> new ArmorItem(ModArmorMaterials.ECHO_SHARD, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> ECHO_SHARD_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.ECHO_SHARD_LEGGINGS,
            () -> new ArmorItem(ModArmorMaterials.ECHO_SHARD, ArmorItem.Type.LEGGINGS,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> ECHO_SHARD_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.ECHO_SHARD_BOOTS,
            () -> new ArmorItem(ModArmorMaterials.ECHO_SHARD, ArmorItem.Type.BOOTS,
                    new Item.Properties()));

    // ── Dragon's Breath (tools + armor) ─────────────────────────────────────
    public static final DeferredHolder<Item, Item> DRAGON_BREATH_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.DRAGON_BREATH_SWORD,
            () -> new SwordItem(ModToolTiers.DRAGON_BREATH, 3, -2.4f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> DRAGON_BREATH_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.DRAGON_BREATH_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.DRAGON_BREATH, 1, -2.8f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> DRAGON_BREATH_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.DRAGON_BREATH_SHOVEL,
            () -> new ShovelItem(ModToolTiers.DRAGON_BREATH, 1.5f, -3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> DRAGON_BREATH_AXE = ITEMS.register(GeneratedRegistrationIds.Items.DRAGON_BREATH_AXE,
            () -> new AxeItem(ModToolTiers.DRAGON_BREATH, 6, -3.2f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> DRAGON_BREATH_HOE = ITEMS.register(GeneratedRegistrationIds.Items.DRAGON_BREATH_HOE,
            () -> new HoeItem(ModToolTiers.DRAGON_BREATH, 0, -3f, new Item.Properties()));
    public static final DeferredHolder<Item, Item> DRAGON_BREATH_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.DRAGON_BREATH_HELMET,
            () -> new ArmorItem(ModArmorMaterials.DRAGON_BREATH, ArmorItem.Type.HELMET,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> DRAGON_BREATH_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.DRAGON_BREATH_CHESTPLATE,
            () -> new ArmorItem(ModArmorMaterials.DRAGON_BREATH, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> DRAGON_BREATH_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.DRAGON_BREATH_LEGGINGS,
            () -> new ArmorItem(ModArmorMaterials.DRAGON_BREATH, ArmorItem.Type.LEGGINGS,
                    new Item.Properties()));
    public static final DeferredHolder<Item, Item> DRAGON_BREATH_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.DRAGON_BREATH_BOOTS,
            () -> new ArmorItem(ModArmorMaterials.DRAGON_BREATH, ArmorItem.Type.BOOTS,
                    new Item.Properties()));

    // -------------------------------------------------------------------------
    // Cake tools  (below wood tier, edible — it's cake!)
    // -------------------------------------------------------------------------
    public static final DeferredHolder<Item, Item> CAKE_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.CAKE_SWORD,
            () -> new EdibleSwordItem(ModToolTiers.CAKE, 3, -2.4f, new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationMod(0.1f).build())));

    public static final DeferredHolder<Item, Item> CAKE_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.CAKE_PICKAXE,
            () -> new EdiblePickaxeItem(ModToolTiers.CAKE, 1, -2.8f, new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationMod(0.1f).build())));

    public static final DeferredHolder<Item, Item> CAKE_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.CAKE_SHOVEL,
            () -> new EdibleShovelItem(ModToolTiers.CAKE, 1.5f, -3f, new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationMod(0.1f).build())));

    public static final DeferredHolder<Item, Item> CAKE_AXE = ITEMS.register(GeneratedRegistrationIds.Items.CAKE_AXE,
            () -> new EdibleAxeItem(ModToolTiers.CAKE, 6, -3.2f, new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationMod(0.1f).build())));

    public static final DeferredHolder<Item, Item> CAKE_HOE = ITEMS.register(GeneratedRegistrationIds.Items.CAKE_HOE,
            () -> new EdibleHoeItem(ModToolTiers.CAKE, 0, -3f, new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationMod(0.1f).build())));

    // -------------------------------------------------------------------------
    // Cake armor  (below leather, edible)
    // -------------------------------------------------------------------------
    public static final DeferredHolder<Item, Item> CAKE_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.CAKE_HELMET,
            () -> new EdibleArmorItem(ModArmorMaterials.CAKE, ArmorItem.Type.HELMET,
                    new Item.Properties()
                            .food(new FoodProperties.Builder().nutrition(10).saturationMod(0.1f).build())));

    public static final DeferredHolder<Item, Item> CAKE_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.CAKE_CHESTPLATE,
            () -> new EdibleArmorItem(ModArmorMaterials.CAKE, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties()
                            .food(new FoodProperties.Builder().nutrition(14).saturationMod(0.1f).build())));

    public static final DeferredHolder<Item, Item> CAKE_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.CAKE_LEGGINGS,
            () -> new EdibleArmorItem(ModArmorMaterials.CAKE, ArmorItem.Type.LEGGINGS,
                    new Item.Properties()
                            .food(new FoodProperties.Builder().nutrition(14).saturationMod(0.1f).build())));

    public static final DeferredHolder<Item, Item> CAKE_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.CAKE_BOOTS,
            () -> new EdibleArmorItem(ModArmorMaterials.CAKE, ArmorItem.Type.BOOTS,
                    new Item.Properties()
                            .food(new FoodProperties.Builder().nutrition(8).saturationMod(0.1f).build())));

    // =========================================================================
    // Bread tools + armor (below wood tier, edible)
    // =========================================================================
    public static final DeferredHolder<Item, Item> BREAD_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.BREAD_SWORD,
            () -> new EdibleSwordItem(ModToolTiers.BREAD, 3, -2.4f, new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> BREAD_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.BREAD_PICKAXE,
            () -> new EdiblePickaxeItem(ModToolTiers.BREAD, 1, -2.8f, new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> BREAD_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.BREAD_SHOVEL,
            () -> new EdibleShovelItem(ModToolTiers.BREAD, 1.5f, -3f, new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> BREAD_AXE = ITEMS.register(GeneratedRegistrationIds.Items.BREAD_AXE,
            () -> new EdibleAxeItem(ModToolTiers.BREAD, 6, -3.2f, new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> BREAD_HOE = ITEMS.register(GeneratedRegistrationIds.Items.BREAD_HOE,
            () -> new EdibleHoeItem(ModToolTiers.BREAD, 0, -3f, new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> BREAD_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.BREAD_HELMET,
            () -> new EdibleArmorItem(ModArmorMaterials.BREAD, ArmorItem.Type.HELMET,
                    new Item.Properties()
                            .food(new FoodProperties.Builder().nutrition(10).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> BREAD_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.BREAD_CHESTPLATE,
            () -> new EdibleArmorItem(ModArmorMaterials.BREAD, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties()
                            .food(new FoodProperties.Builder().nutrition(14).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> BREAD_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.BREAD_LEGGINGS,
            () -> new EdibleArmorItem(ModArmorMaterials.BREAD, ArmorItem.Type.LEGGINGS,
                    new Item.Properties()
                            .food(new FoodProperties.Builder().nutrition(14).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> BREAD_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.BREAD_BOOTS,
            () -> new EdibleArmorItem(ModArmorMaterials.BREAD, ArmorItem.Type.BOOTS,
                    new Item.Properties()
                            .food(new FoodProperties.Builder().nutrition(8).saturationMod(0.1f).build())));

    // =========================================================================
    // Dried Kelp tools + armor
    // =========================================================================
    public static final DeferredHolder<Item, Item> DRIED_KELP_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.DRIED_KELP_SWORD,
            () -> new EdibleSwordItem(ModToolTiers.DRIED_KELP, 3, -2.4f, new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> DRIED_KELP_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.DRIED_KELP_PICKAXE,
            () -> new EdiblePickaxeItem(ModToolTiers.DRIED_KELP, 1, -2.8f, new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> DRIED_KELP_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.DRIED_KELP_SHOVEL,
            () -> new EdibleShovelItem(ModToolTiers.DRIED_KELP, 1.5f, -3f, new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> DRIED_KELP_AXE = ITEMS.register(GeneratedRegistrationIds.Items.DRIED_KELP_AXE,
            () -> new EdibleAxeItem(ModToolTiers.DRIED_KELP, 6, -3.2f, new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> DRIED_KELP_HOE = ITEMS.register(GeneratedRegistrationIds.Items.DRIED_KELP_HOE,
            () -> new EdibleHoeItem(ModToolTiers.DRIED_KELP, 0, -3f, new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> DRIED_KELP_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.DRIED_KELP_HELMET,
            () -> new EdibleArmorItem(ModArmorMaterials.DRIED_KELP, ArmorItem.Type.HELMET,
                    new Item.Properties()
                            .food(new FoodProperties.Builder().nutrition(10).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> DRIED_KELP_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.DRIED_KELP_CHESTPLATE,
            () -> new EdibleArmorItem(ModArmorMaterials.DRIED_KELP, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties()
                            .food(new FoodProperties.Builder().nutrition(14).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> DRIED_KELP_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.DRIED_KELP_LEGGINGS,
            () -> new EdibleArmorItem(ModArmorMaterials.DRIED_KELP, ArmorItem.Type.LEGGINGS,
                    new Item.Properties()
                            .food(new FoodProperties.Builder().nutrition(14).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> DRIED_KELP_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.DRIED_KELP_BOOTS,
            () -> new EdibleArmorItem(ModArmorMaterials.DRIED_KELP, ArmorItem.Type.BOOTS,
                    new Item.Properties()
                            .food(new FoodProperties.Builder().nutrition(8).saturationMod(0.1f).build())));

    // =========================================================================
    // Rotten Flesh tools + armor
    // =========================================================================
    public static final DeferredHolder<Item, Item> ROTTEN_FLESH_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.ROTTEN_FLESH_SWORD,
            () -> new EdibleSwordItem(ModToolTiers.ROTTEN_FLESH, 3, -2.4f, new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> ROTTEN_FLESH_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.ROTTEN_FLESH_PICKAXE,
            () -> new EdiblePickaxeItem(ModToolTiers.ROTTEN_FLESH, 1, -2.8f, new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> ROTTEN_FLESH_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.ROTTEN_FLESH_SHOVEL,
            () -> new EdibleShovelItem(ModToolTiers.ROTTEN_FLESH, 1.5f, -3f, new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> ROTTEN_FLESH_AXE = ITEMS.register(GeneratedRegistrationIds.Items.ROTTEN_FLESH_AXE,
            () -> new EdibleAxeItem(ModToolTiers.ROTTEN_FLESH, 6, -3.2f, new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> ROTTEN_FLESH_HOE = ITEMS.register(GeneratedRegistrationIds.Items.ROTTEN_FLESH_HOE,
            () -> new EdibleHoeItem(ModToolTiers.ROTTEN_FLESH, 0, -3f, new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> ROTTEN_FLESH_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.ROTTEN_FLESH_HELMET,
            () -> new EdibleArmorItem(ModArmorMaterials.ROTTEN_FLESH, ArmorItem.Type.HELMET,
                    new Item.Properties()
                            .food(new FoodProperties.Builder().nutrition(10).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> ROTTEN_FLESH_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.ROTTEN_FLESH_CHESTPLATE,
            () -> new EdibleArmorItem(ModArmorMaterials.ROTTEN_FLESH, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties()
                            .food(new FoodProperties.Builder().nutrition(14).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> ROTTEN_FLESH_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.ROTTEN_FLESH_LEGGINGS,
            () -> new EdibleArmorItem(ModArmorMaterials.ROTTEN_FLESH, ArmorItem.Type.LEGGINGS,
                    new Item.Properties()
                            .food(new FoodProperties.Builder().nutrition(14).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> ROTTEN_FLESH_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.ROTTEN_FLESH_BOOTS,
            () -> new EdibleArmorItem(ModArmorMaterials.ROTTEN_FLESH, ArmorItem.Type.BOOTS,
                    new Item.Properties()
                            .food(new FoodProperties.Builder().nutrition(8).saturationMod(0.1f).build())));

    // =========================================================================
    // Melon tools + armor (wood-equivalent)
    // =========================================================================
    public static final DeferredHolder<Item, Item> MELON_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.MELON_SWORD,
            () -> new EdibleSwordItem(ModToolTiers.MELON, 3, -2.4f, new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> MELON_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.MELON_PICKAXE,
            () -> new EdiblePickaxeItem(ModToolTiers.MELON, 1, -2.8f, new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> MELON_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.MELON_SHOVEL,
            () -> new EdibleShovelItem(ModToolTiers.MELON, 1.5f, -3f, new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> MELON_AXE = ITEMS.register(GeneratedRegistrationIds.Items.MELON_AXE,
            () -> new EdibleAxeItem(ModToolTiers.MELON, 6, -3.2f, new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> MELON_HOE = ITEMS.register(GeneratedRegistrationIds.Items.MELON_HOE,
            () -> new EdibleHoeItem(ModToolTiers.MELON, 0, -3f, new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> MELON_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.MELON_HELMET,
            () -> new EdibleArmorItem(ModArmorMaterials.MELON, ArmorItem.Type.HELMET,
                    new Item.Properties()
                            .food(new FoodProperties.Builder().nutrition(10).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> MELON_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.MELON_CHESTPLATE,
            () -> new EdibleArmorItem(ModArmorMaterials.MELON, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties()
                            .food(new FoodProperties.Builder().nutrition(14).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> MELON_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.MELON_LEGGINGS,
            () -> new EdibleArmorItem(ModArmorMaterials.MELON, ArmorItem.Type.LEGGINGS,
                    new Item.Properties()
                            .food(new FoodProperties.Builder().nutrition(14).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> MELON_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.MELON_BOOTS,
            () -> new EdibleArmorItem(ModArmorMaterials.MELON, ArmorItem.Type.BOOTS,
                    new Item.Properties()
                            .food(new FoodProperties.Builder().nutrition(8).saturationMod(0.1f).build())));

    // =========================================================================
    // Sweet Berries tools + armor (wood-equivalent)
    // =========================================================================
    public static final DeferredHolder<Item, Item> SWEET_BERRY_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.SWEET_BERRY_SWORD,
            () -> new EdibleSwordItem(ModToolTiers.SWEET_BERRIES, 3, -2.4f, new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> SWEET_BERRY_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.SWEET_BERRY_PICKAXE,
            () -> new EdiblePickaxeItem(ModToolTiers.SWEET_BERRIES, 1, -2.8f, new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> SWEET_BERRY_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.SWEET_BERRY_SHOVEL,
            () -> new EdibleShovelItem(ModToolTiers.SWEET_BERRIES, 1.5f, -3f, new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> SWEET_BERRY_AXE = ITEMS.register(GeneratedRegistrationIds.Items.SWEET_BERRY_AXE,
            () -> new EdibleAxeItem(ModToolTiers.SWEET_BERRIES, 6, -3.2f, new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> SWEET_BERRY_HOE = ITEMS.register(GeneratedRegistrationIds.Items.SWEET_BERRY_HOE,
            () -> new EdibleHoeItem(ModToolTiers.SWEET_BERRIES, 0, -3f, new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> SWEET_BERRY_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.SWEET_BERRY_HELMET,
            () -> new EdibleArmorItem(ModArmorMaterials.SWEET_BERRY, ArmorItem.Type.HELMET,
                    new Item.Properties()
                            .food(new FoodProperties.Builder().nutrition(10).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> SWEET_BERRY_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.SWEET_BERRY_CHESTPLATE,
            () -> new EdibleArmorItem(ModArmorMaterials.SWEET_BERRY, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties()
                            .food(new FoodProperties.Builder().nutrition(14).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> SWEET_BERRY_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.SWEET_BERRY_LEGGINGS,
            () -> new EdibleArmorItem(ModArmorMaterials.SWEET_BERRY, ArmorItem.Type.LEGGINGS,
                    new Item.Properties()
                            .food(new FoodProperties.Builder().nutrition(14).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> SWEET_BERRY_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.SWEET_BERRY_BOOTS,
            () -> new EdibleArmorItem(ModArmorMaterials.SWEET_BERRY, ArmorItem.Type.BOOTS,
                    new Item.Properties()
                            .food(new FoodProperties.Builder().nutrition(8).saturationMod(0.1f).build())));

    // =========================================================================
    // Pumpkin Pie tools + armor (wood-equivalent)
    // =========================================================================
    public static final DeferredHolder<Item, Item> PUMPKIN_PIE_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.PUMPKIN_PIE_SWORD,
            () -> new EdibleSwordItem(ModToolTiers.PUMPKIN_PIE, 3, -2.4f, new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> PUMPKIN_PIE_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.PUMPKIN_PIE_PICKAXE,
            () -> new EdiblePickaxeItem(ModToolTiers.PUMPKIN_PIE, 1, -2.8f, new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> PUMPKIN_PIE_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.PUMPKIN_PIE_SHOVEL,
            () -> new EdibleShovelItem(ModToolTiers.PUMPKIN_PIE, 1.5f, -3f, new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> PUMPKIN_PIE_AXE = ITEMS.register(GeneratedRegistrationIds.Items.PUMPKIN_PIE_AXE,
            () -> new EdibleAxeItem(ModToolTiers.PUMPKIN_PIE, 6, -3.2f, new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> PUMPKIN_PIE_HOE = ITEMS.register(GeneratedRegistrationIds.Items.PUMPKIN_PIE_HOE,
            () -> new EdibleHoeItem(ModToolTiers.PUMPKIN_PIE, 0, -3f, new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> PUMPKIN_PIE_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.PUMPKIN_PIE_HELMET,
            () -> new EdibleArmorItem(ModArmorMaterials.PUMPKIN_PIE, ArmorItem.Type.HELMET,
                    new Item.Properties()
                            .food(new FoodProperties.Builder().nutrition(10).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> PUMPKIN_PIE_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.PUMPKIN_PIE_CHESTPLATE,
            () -> new EdibleArmorItem(ModArmorMaterials.PUMPKIN_PIE, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties()
                            .food(new FoodProperties.Builder().nutrition(14).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> PUMPKIN_PIE_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.PUMPKIN_PIE_LEGGINGS,
            () -> new EdibleArmorItem(ModArmorMaterials.PUMPKIN_PIE, ArmorItem.Type.LEGGINGS,
                    new Item.Properties()
                            .food(new FoodProperties.Builder().nutrition(14).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> PUMPKIN_PIE_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.PUMPKIN_PIE_BOOTS,
            () -> new EdibleArmorItem(ModArmorMaterials.PUMPKIN_PIE, ArmorItem.Type.BOOTS,
                    new Item.Properties()
                            .food(new FoodProperties.Builder().nutrition(8).saturationMod(0.1f).build())));

    // =========================================================================
    // Mushroom tools + armor (stone-equivalent)
    // =========================================================================
    public static final DeferredHolder<Item, Item> MUSHROOM_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.MUSHROOM_SWORD,
            () -> new EdibleSwordItem(ModToolTiers.MUSHROOM, 3, -2.4f, new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> MUSHROOM_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.MUSHROOM_PICKAXE,
            () -> new EdiblePickaxeItem(ModToolTiers.MUSHROOM, 1, -2.8f, new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> MUSHROOM_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.MUSHROOM_SHOVEL,
            () -> new EdibleShovelItem(ModToolTiers.MUSHROOM, 1.5f, -3f, new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> MUSHROOM_AXE = ITEMS.register(GeneratedRegistrationIds.Items.MUSHROOM_AXE,
            () -> new EdibleAxeItem(ModToolTiers.MUSHROOM, 6, -3.2f, new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> MUSHROOM_HOE = ITEMS.register(GeneratedRegistrationIds.Items.MUSHROOM_HOE,
            () -> new EdibleHoeItem(ModToolTiers.MUSHROOM, 0, -3f, new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> MUSHROOM_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.MUSHROOM_HELMET,
            () -> new EdibleArmorItem(ModArmorMaterials.MUSHROOM, ArmorItem.Type.HELMET,
                    new Item.Properties()
                            .food(new FoodProperties.Builder().nutrition(10).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> MUSHROOM_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.MUSHROOM_CHESTPLATE,
            () -> new EdibleArmorItem(ModArmorMaterials.MUSHROOM, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties()
                            .food(new FoodProperties.Builder().nutrition(14).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> MUSHROOM_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.MUSHROOM_LEGGINGS,
            () -> new EdibleArmorItem(ModArmorMaterials.MUSHROOM, ArmorItem.Type.LEGGINGS,
                    new Item.Properties()
                            .food(new FoodProperties.Builder().nutrition(14).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> MUSHROOM_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.MUSHROOM_BOOTS,
            () -> new EdibleArmorItem(ModArmorMaterials.MUSHROOM, ArmorItem.Type.BOOTS,
                    new Item.Properties()
                            .food(new FoodProperties.Builder().nutrition(8).saturationMod(0.1f).build())));

    // =========================================================================
    // Pufferfish tools + armor (stone-equivalent)
    // =========================================================================
    public static final DeferredHolder<Item, Item> PUFFERFISH_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.PUFFERFISH_SWORD,
            () -> new EdibleSwordItem(ModToolTiers.PUFFERFISH, 3, -2.4f, new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> PUFFERFISH_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.PUFFERFISH_PICKAXE,
            () -> new EdiblePickaxeItem(ModToolTiers.PUFFERFISH, 1, -2.8f, new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> PUFFERFISH_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.PUFFERFISH_SHOVEL,
            () -> new EdibleShovelItem(ModToolTiers.PUFFERFISH, 1.5f, -3f, new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> PUFFERFISH_AXE = ITEMS.register(GeneratedRegistrationIds.Items.PUFFERFISH_AXE,
            () -> new EdibleAxeItem(ModToolTiers.PUFFERFISH, 6, -3.2f, new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> PUFFERFISH_HOE = ITEMS.register(GeneratedRegistrationIds.Items.PUFFERFISH_HOE,
            () -> new EdibleHoeItem(ModToolTiers.PUFFERFISH, 0, -3f, new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> PUFFERFISH_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.PUFFERFISH_HELMET,
            () -> new EdibleArmorItem(ModArmorMaterials.PUFFERFISH, ArmorItem.Type.HELMET,
                    new Item.Properties()
                            .food(new FoodProperties.Builder().nutrition(10).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> PUFFERFISH_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.PUFFERFISH_CHESTPLATE,
            () -> new EdibleArmorItem(ModArmorMaterials.PUFFERFISH, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties()
                            .food(new FoodProperties.Builder().nutrition(14).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> PUFFERFISH_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.PUFFERFISH_LEGGINGS,
            () -> new EdibleArmorItem(ModArmorMaterials.PUFFERFISH, ArmorItem.Type.LEGGINGS,
                    new Item.Properties()
                            .food(new FoodProperties.Builder().nutrition(14).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> PUFFERFISH_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.PUFFERFISH_BOOTS,
            () -> new EdibleArmorItem(ModArmorMaterials.PUFFERFISH, ArmorItem.Type.BOOTS,
                    new Item.Properties()
                            .food(new FoodProperties.Builder().nutrition(8).saturationMod(0.1f).build())));

    // =========================================================================
    // Honey tools + armor (stone-equivalent)
    // =========================================================================
    public static final DeferredHolder<Item, Item> HONEY_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.HONEY_SWORD,
            () -> new EdibleSwordItem(ModToolTiers.HONEY, 3, -2.4f, new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> HONEY_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.HONEY_PICKAXE,
            () -> new EdiblePickaxeItem(ModToolTiers.HONEY, 1, -2.8f, new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> HONEY_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.HONEY_SHOVEL,
            () -> new EdibleShovelItem(ModToolTiers.HONEY, 1.5f, -3f, new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> HONEY_AXE = ITEMS.register(GeneratedRegistrationIds.Items.HONEY_AXE,
            () -> new EdibleAxeItem(ModToolTiers.HONEY, 6, -3.2f, new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> HONEY_HOE = ITEMS.register(GeneratedRegistrationIds.Items.HONEY_HOE,
            () -> new EdibleHoeItem(ModToolTiers.HONEY, 0, -3f, new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> HONEY_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.HONEY_HELMET,
            () -> new EdibleArmorItem(ModArmorMaterials.HONEY, ArmorItem.Type.HELMET,
                    new Item.Properties()
                            .food(new FoodProperties.Builder().nutrition(10).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> HONEY_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.HONEY_CHESTPLATE,
            () -> new EdibleArmorItem(ModArmorMaterials.HONEY, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties()
                            .food(new FoodProperties.Builder().nutrition(14).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> HONEY_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.HONEY_LEGGINGS,
            () -> new EdibleArmorItem(ModArmorMaterials.HONEY, ArmorItem.Type.LEGGINGS,
                    new Item.Properties()
                            .food(new FoodProperties.Builder().nutrition(14).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> HONEY_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.HONEY_BOOTS,
            () -> new EdibleArmorItem(ModArmorMaterials.HONEY, ArmorItem.Type.BOOTS,
                    new Item.Properties()
                            .food(new FoodProperties.Builder().nutrition(8).saturationMod(0.1f).build())));

    // =========================================================================
    // Chorus Fruit tools + armor (iron-equivalent)
    // =========================================================================
    public static final DeferredHolder<Item, Item> CHORUS_FRUIT_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.CHORUS_FRUIT_SWORD,
            () -> new EdibleSwordItem(ModToolTiers.CHORUS_FRUIT, 3, -2.4f, new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> CHORUS_FRUIT_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.CHORUS_FRUIT_PICKAXE,
            () -> new EdiblePickaxeItem(ModToolTiers.CHORUS_FRUIT, 1, -2.8f, new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> CHORUS_FRUIT_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.CHORUS_FRUIT_SHOVEL,
            () -> new EdibleShovelItem(ModToolTiers.CHORUS_FRUIT, 1.5f, -3f, new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> CHORUS_FRUIT_AXE = ITEMS.register(GeneratedRegistrationIds.Items.CHORUS_FRUIT_AXE,
            () -> new EdibleAxeItem(ModToolTiers.CHORUS_FRUIT, 6, -3.2f, new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> CHORUS_FRUIT_HOE = ITEMS.register(GeneratedRegistrationIds.Items.CHORUS_FRUIT_HOE,
            () -> new EdibleHoeItem(ModToolTiers.CHORUS_FRUIT, 0, -3f, new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> CHORUS_FRUIT_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.CHORUS_FRUIT_HELMET,
            () -> new EdibleArmorItem(ModArmorMaterials.CHORUS_FRUIT, ArmorItem.Type.HELMET,
                    new Item.Properties()
                            .food(new FoodProperties.Builder().nutrition(10).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> CHORUS_FRUIT_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.CHORUS_FRUIT_CHESTPLATE,
            () -> new EdibleArmorItem(ModArmorMaterials.CHORUS_FRUIT, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties()
                            .food(new FoodProperties.Builder().nutrition(14).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> CHORUS_FRUIT_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.CHORUS_FRUIT_LEGGINGS,
            () -> new EdibleArmorItem(ModArmorMaterials.CHORUS_FRUIT, ArmorItem.Type.LEGGINGS,
                    new Item.Properties()
                            .food(new FoodProperties.Builder().nutrition(14).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> CHORUS_FRUIT_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.CHORUS_FRUIT_BOOTS,
            () -> new EdibleArmorItem(ModArmorMaterials.CHORUS_FRUIT, ArmorItem.Type.BOOTS,
                    new Item.Properties()
                            .food(new FoodProperties.Builder().nutrition(8).saturationMod(0.1f).build())));

    // =========================================================================
    // Golden Apple tools + armor (iron-equivalent, luxury)
    // =========================================================================
    public static final DeferredHolder<Item, Item> GOLDEN_APPLE_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.GOLDEN_APPLE_SWORD,
            () -> new EdibleSwordItem(ModToolTiers.GOLDEN_APPLE, 3, -2.4f, new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> GOLDEN_APPLE_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.GOLDEN_APPLE_PICKAXE,
            () -> new EdiblePickaxeItem(ModToolTiers.GOLDEN_APPLE, 1, -2.8f, new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> GOLDEN_APPLE_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.GOLDEN_APPLE_SHOVEL,
            () -> new EdibleShovelItem(ModToolTiers.GOLDEN_APPLE, 1.5f, -3f, new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> GOLDEN_APPLE_AXE = ITEMS.register(GeneratedRegistrationIds.Items.GOLDEN_APPLE_AXE,
            () -> new EdibleAxeItem(ModToolTiers.GOLDEN_APPLE, 6, -3.2f, new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> GOLDEN_APPLE_HOE = ITEMS.register(GeneratedRegistrationIds.Items.GOLDEN_APPLE_HOE,
            () -> new EdibleHoeItem(ModToolTiers.GOLDEN_APPLE, 0, -3f, new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> GOLDEN_APPLE_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.GOLDEN_APPLE_HELMET,
            () -> new EdibleArmorItem(ModArmorMaterials.GOLDEN_APPLE, ArmorItem.Type.HELMET,
                    new Item.Properties()
                            .food(new FoodProperties.Builder().nutrition(10).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> GOLDEN_APPLE_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.GOLDEN_APPLE_CHESTPLATE,
            () -> new EdibleArmorItem(ModArmorMaterials.GOLDEN_APPLE, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties()
                            .food(new FoodProperties.Builder().nutrition(14).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> GOLDEN_APPLE_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.GOLDEN_APPLE_LEGGINGS,
            () -> new EdibleArmorItem(ModArmorMaterials.GOLDEN_APPLE, ArmorItem.Type.LEGGINGS,
                    new Item.Properties()
                            .food(new FoodProperties.Builder().nutrition(14).saturationMod(0.1f).build())));
    public static final DeferredHolder<Item, Item> GOLDEN_APPLE_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.GOLDEN_APPLE_BOOTS,
            () -> new EdibleArmorItem(ModArmorMaterials.GOLDEN_APPLE, ArmorItem.Type.BOOTS,
                    new Item.Properties()
                            .food(new FoodProperties.Builder().nutrition(8).saturationMod(0.1f).build())));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
