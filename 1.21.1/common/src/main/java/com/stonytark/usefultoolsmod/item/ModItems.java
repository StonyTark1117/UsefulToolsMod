package com.stonytark.usefultoolsmod.item;

import com.stonytark.usefultoolsmod.generated.GeneratedRegistrationIds;


import net.minecraft.core.registries.Registries;
import com.stonytark.usefultoolsmod.UsefultoolsMod;
import com.stonytark.usefultoolsmod.entity.ModEntities;
import com.stonytark.usefultoolsmod.item.custom.*;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
// Architectury: use vanilla SpawnEggItem with ModEntities.GHOST.get()

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(UsefultoolsMod.MOD_ID, Registries.ITEM);

    public static final RegistrySupplier<Item> RGOLD = ITEMS.register(GeneratedRegistrationIds.Items.RGOLD,
            () -> new Item(new Item.Properties().stacksTo(64)));

    public static final RegistrySupplier<Item> RAW_RGOLD = ITEMS.register(GeneratedRegistrationIds.Items.RAW_RGOLD,
            () -> new Item(new Item.Properties().stacksTo(64)));

    public static final RegistrySupplier<Item> OBSHARD = ITEMS.register(GeneratedRegistrationIds.Items.OBSHARD,
            () -> new Item(new Item.Properties().stacksTo(64)));

    public static final RegistrySupplier<Item> SEM = ITEMS.register(GeneratedRegistrationIds.Items.SEM,
            () -> new Item(new Item.Properties().stacksTo(64)));

    public static final RegistrySupplier<Item> OBINGOT = ITEMS.register(GeneratedRegistrationIds.Items.OBINGOT,
            () -> new Item(new Item.Properties().stacksTo(64)));

    public static final RegistrySupplier<Item> GRENADE = ITEMS.register(GeneratedRegistrationIds.Items.GRENADE,
            () -> new Grenade(new Item.Properties().stacksTo(16)));

    public static final RegistrySupplier<Item> HRED = ITEMS.register(GeneratedRegistrationIds.Items.HRED,
            () -> new Item(new Item.Properties().stacksTo(64)));

    public static final RegistrySupplier<Item> HGLOW = ITEMS.register(GeneratedRegistrationIds.Items.HGLOW,
            () -> new Item(new Item.Properties().stacksTo(64)));

    public static final RegistrySupplier<Item> RLAPIS = ITEMS.register(GeneratedRegistrationIds.Items.RLAPIS,
            () -> new Item(new Item.Properties().stacksTo(64)));

    public static final RegistrySupplier<Item> DYNAMITE = ITEMS.register(GeneratedRegistrationIds.Items.DYNAMITE,
                    () -> new Dynamite(new Item.Properties()
                            .stacksTo(16)
                            .fireResistant()  // optional
                    )
            );

    public static final RegistrySupplier<Item> REMERALD_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.REMERALD_SWORD,
            () -> new SwordItem(ModToolTiers.REMERALD, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.REMERALD, 3, -2.4f))));
    public static final RegistrySupplier<Item> REMERALD_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.REMERALD_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.REMERALD, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.REMERALD, 1, -2.8f))));
    public static final RegistrySupplier<Item> REMERALD_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.REMERALD_SHOVEL,
            () -> new ShovelItem(ModToolTiers.REMERALD, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.REMERALD, 1.5f, -3f))));
    public static final RegistrySupplier<Item> REMERALD_AXE = ITEMS.register(GeneratedRegistrationIds.Items.REMERALD_AXE,
            () -> new AxeItem(ModToolTiers.REMERALD, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.REMERALD, 6, -3.2f))));
    public static final RegistrySupplier<Item> REMERALD_HOE = ITEMS.register(GeneratedRegistrationIds.Items.REMERALD_HOE,
            () -> new HoeItem(ModToolTiers.REMERALD, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.REMERALD, 0, -3f))));

    public static final RegistrySupplier<Item> PEMERALD_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.PEMERALD_SWORD,
            () -> new SwordItem(ModToolTiers.PEMERALD, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.PEMERALD, 3, -2.4f))));
    public static final RegistrySupplier<Item> PEMERALD_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.PEMERALD_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.PEMERALD, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.PEMERALD, 1, -2.8f))));
    public static final RegistrySupplier<Item> PEMERALD_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.PEMERALD_SHOVEL,
            () -> new ShovelItem(ModToolTiers.PEMERALD, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.PEMERALD, 1.5f, -3f))));
    public static final RegistrySupplier<Item> PEMERALD_AXE = ITEMS.register(GeneratedRegistrationIds.Items.PEMERALD_AXE,
            () -> new AxeItem(ModToolTiers.PEMERALD, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.PEMERALD, 6, -3.2f))));
    public static final RegistrySupplier<Item> PEMERALD_HOE = ITEMS.register(GeneratedRegistrationIds.Items.PEMERALD_HOE,
            () -> new HoeItem(ModToolTiers.PEMERALD, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.PEMERALD, 0, -3f))));

    public static final RegistrySupplier<Item> ROBSIDIAN_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.ROBSIDIAN_SWORD,
            () -> new SwordItem(ModToolTiers.ROBSIDIAN, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.ROBSIDIAN, 3, -2.4f))));
    public static final RegistrySupplier<Item> ROBSIDIAN_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.ROBSIDIAN_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.ROBSIDIAN, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.ROBSIDIAN, 1, -2.8f))));
    public static final RegistrySupplier<Item> ROBSIDIAN_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.ROBSIDIAN_SHOVEL,
            () -> new ShovelItem(ModToolTiers.ROBSIDIAN, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.ROBSIDIAN, 1.5f, -3f))));
    public static final RegistrySupplier<Item> ROBSIDIAN_AXE = ITEMS.register(GeneratedRegistrationIds.Items.ROBSIDIAN_AXE,
            () -> new AxeItem(ModToolTiers.ROBSIDIAN, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.ROBSIDIAN, 6, -3.2f))));
    public static final RegistrySupplier<Item> ROBSIDIAN_HOE = ITEMS.register(GeneratedRegistrationIds.Items.ROBSIDIAN_HOE,
            () -> new HoeItem(ModToolTiers.ROBSIDIAN, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.ROBSIDIAN, 0, -3f))));

    public static final RegistrySupplier<Item> POBSIDIAN_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.POBSIDIAN_SWORD,
            () -> new SwordItem(ModToolTiers.POBSIDIAN, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.POBSIDIAN, 3, -2.4f))));
    public static final RegistrySupplier<Item> POBSIDIAN_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.POBSIDIAN_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.POBSIDIAN, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.POBSIDIAN, 1, -2.8f))));
    public static final RegistrySupplier<Item> POBSIDIAN_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.POBSIDIAN_SHOVEL,
            () -> new ShovelItem(ModToolTiers.POBSIDIAN, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.POBSIDIAN, 1.5f, -3f))));
    public static final RegistrySupplier<Item> POBSIDIAN_AXE = ITEMS.register(GeneratedRegistrationIds.Items.POBSIDIAN_AXE,
            () -> new AxeItem(ModToolTiers.POBSIDIAN, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.POBSIDIAN, 6, -3.2f))));
    public static final RegistrySupplier<Item> POBSIDIAN_HOE = ITEMS.register(GeneratedRegistrationIds.Items.POBSIDIAN_HOE,
            () -> new HoeItem(ModToolTiers.POBSIDIAN, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.POBSIDIAN, 0, -3f))));

    public static final RegistrySupplier<Item> OVERPOWER_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.OVERPOWER_SWORD,
            () -> new SwordItem(ModToolTiers.OVERPOWER, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.OVERPOWER, 3, -2.4f))));
    public static final RegistrySupplier<Item> OVERPOWER_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.OVERPOWER_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.OVERPOWER, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.OVERPOWER, 1, -2.8f))));
    public static final RegistrySupplier<Item> OVERPOWER_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.OVERPOWER_SHOVEL,
            () -> new ShovelItem(ModToolTiers.OVERPOWER, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.OVERPOWER, 1.5f, -3f))));
    public static final RegistrySupplier<Item> OVERPOWER_AXE = ITEMS.register(GeneratedRegistrationIds.Items.OVERPOWER_AXE,
            () -> new AxeItem(ModToolTiers.OVERPOWER, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.OVERPOWER, 6, -3.2f))));


    public static final RegistrySupplier<Item> HREDSTONE_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.HREDSTONE_SWORD,
            () -> new SwordItem(ModToolTiers.HREDSTONE, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.HREDSTONE, 3, -2.4f))));
    public static final RegistrySupplier<Item> HREDSTONE_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.HREDSTONE_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.HREDSTONE, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.HREDSTONE, 1, -2.8f))));
    public static final RegistrySupplier<Item> HREDSTONE_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.HREDSTONE_SHOVEL,
            () -> new ShovelItem(ModToolTiers.HREDSTONE, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.HREDSTONE, 1.5f, -3f))));
    public static final RegistrySupplier<Item> HREDSTONE_AXE = ITEMS.register(GeneratedRegistrationIds.Items.HREDSTONE_AXE,
            () -> new AxeItem(ModToolTiers.HREDSTONE, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.HREDSTONE, 6, -3.2f))));
    public static final RegistrySupplier<Item> HREDSTONE_HOE = ITEMS.register(GeneratedRegistrationIds.Items.HREDSTONE_HOE,
            () -> new HoeItem(ModToolTiers.HREDSTONE, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.HREDSTONE, 0, -3f))));

    public static final RegistrySupplier<Item> HGLOWSTONE_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.HGLOWSTONE_SWORD,
            () -> new SwordItem(ModToolTiers.HGLOWSTONE, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.HGLOWSTONE, 3, -2.4f))));
    public static final RegistrySupplier<Item> HGLOWSTONE_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.HGLOWSTONE_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.HGLOWSTONE, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.HGLOWSTONE, 1, -2.8f))));
    public static final RegistrySupplier<Item> HGLOWSTONE_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.HGLOWSTONE_SHOVEL,
            () -> new ShovelItem(ModToolTiers.HGLOWSTONE, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.HGLOWSTONE, 1.5f, -3f))));
    public static final RegistrySupplier<Item> HGLOWSTONE_AXE = ITEMS.register(GeneratedRegistrationIds.Items.HGLOWSTONE_AXE,
            () -> new AxeItem(ModToolTiers.HGLOWSTONE, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.HGLOWSTONE, 6, -3.2f))));
    public static final RegistrySupplier<Item> HGLOWSTONE_HOE = ITEMS.register(GeneratedRegistrationIds.Items.HGLOWSTONE_HOE,
            () -> new HoeItem(ModToolTiers.HGLOWSTONE, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.HGLOWSTONE, 0, -3f))));

    public static final RegistrySupplier<Item> RGOLD_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.RGOLD_SWORD,
            () -> new SwordItem(ModToolTiers.RGOLD, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.RGOLD, 3, -2.4f))));
    public static final RegistrySupplier<Item> RGOLD_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.RGOLD_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.RGOLD, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.RGOLD, 1, -2.8f))));
    public static final RegistrySupplier<Item> RGOLD_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.RGOLD_SHOVEL,
            () -> new ShovelItem(ModToolTiers.RGOLD, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.RGOLD, 1.5f, -3f))));
    public static final RegistrySupplier<Item> RGOLD_AXE = ITEMS.register(GeneratedRegistrationIds.Items.RGOLD_AXE,
            () -> new AxeItem(ModToolTiers.RGOLD, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.RGOLD, 6, -3.2f))));
    public static final RegistrySupplier<Item> RGOLD_HOE = ITEMS.register(GeneratedRegistrationIds.Items.RGOLD_HOE,
            () -> new HoeItem(ModToolTiers.RGOLD, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.RGOLD, 0, -3f))));

    public static final RegistrySupplier<Item> RLAPIS_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.RLAPIS_SWORD,
            () -> new SwordItem(ModToolTiers.RLAPIS, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.RLAPIS, 3, -2.4f))));
    public static final RegistrySupplier<Item> RLAPIS_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.RLAPIS_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.RLAPIS, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.RLAPIS, 1, -2.8f))));
    public static final RegistrySupplier<Item> RLAPIS_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.RLAPIS_SHOVEL,
            () -> new ShovelItem(ModToolTiers.RLAPIS, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.RLAPIS, 1.5f, -3f))));
    public static final RegistrySupplier<Item> RLAPIS_AXE = ITEMS.register(GeneratedRegistrationIds.Items.RLAPIS_AXE,
            () -> new AxeItem(ModToolTiers.RLAPIS, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.RLAPIS, 6, -3.2f))));
    public static final RegistrySupplier<Item> RLAPIS_HOE = ITEMS.register(GeneratedRegistrationIds.Items.RLAPIS_HOE,
            () -> new HoeItem(ModToolTiers.RLAPIS, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.RLAPIS, 0, -3f))));

    public static final RegistrySupplier<Item> EMERALD_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.EMERALD_HELMET,
            () -> new ArmorItem(ModArmorMaterials.EMERALD_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(33))));
    public static final RegistrySupplier<Item> EMERALD_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.EMERALD_CHESTPLATE,
            () -> new ArmorItem(ModArmorMaterials.EMERALD_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(33))));
    public static final RegistrySupplier<Item> EMERALD_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.EMERALD_LEGGINGS,
            () -> new ArmorItem(ModArmorMaterials.EMERALD_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(33))));
    public static final RegistrySupplier<Item> EMERALD_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.EMERALD_BOOTS,
            () -> new ArmorItem(ModArmorMaterials.EMERALD_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(33))));

    public static final RegistrySupplier<Item> HRED_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.HRED_HELMET,
            () -> new ArmorItem(ModArmorMaterials.HRED_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(20))));
    public static final RegistrySupplier<Item> HRED_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.HRED_CHESTPLATE,
            () -> new ArmorItem(ModArmorMaterials.HRED_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(20))));
    public static final RegistrySupplier<Item> HRED_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.HRED_LEGGINGS,
            () -> new ArmorItem(ModArmorMaterials.HRED_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(20))));
    public static final RegistrySupplier<Item> HRED_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.HRED_BOOTS,
            () -> new ArmorItem(ModArmorMaterials.HRED_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(20))));

    public static final RegistrySupplier<Item> HGLOW_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.HGLOW_HELMET,
            () -> new ArmorItem(ModArmorMaterials.HGLOW_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(18))));
    public static final RegistrySupplier<Item> HGLOW_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.HGLOW_CHESTPLATE,
            () -> new ArmorItem(ModArmorMaterials.HGLOW_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(18))));
    public static final RegistrySupplier<Item> HGLOW_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.HGLOW_LEGGINGS,
            () -> new ArmorItem(ModArmorMaterials.HGLOW_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(18))));
    public static final RegistrySupplier<Item> HGLOW_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.HGLOW_BOOTS,
            () -> new ArmorItem(ModArmorMaterials.HGLOW_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(18))));

    public static final RegistrySupplier<Item> OBSIDIAN_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.OBSIDIAN_HELMET,
            () -> new ArmorItem(ModArmorMaterials.OBSIDIAN_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(45))));
    public static final RegistrySupplier<Item> OBSIDIAN_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.OBSIDIAN_CHESTPLATE,
            () -> new ArmorItem(ModArmorMaterials.OBSIDIAN_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(45))));
    public static final RegistrySupplier<Item> OBSIDIAN_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.OBSIDIAN_LEGGINGS,
            () -> new ArmorItem(ModArmorMaterials.OBSIDIAN_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(45))));
    public static final RegistrySupplier<Item> OBSIDIAN_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.OBSIDIAN_BOOTS,
            () -> new ArmorItem(ModArmorMaterials.OBSIDIAN_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(45))));

    public static final RegistrySupplier<Item> RGOLD_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.RGOLD_HELMET,
            () -> new ArmorItem(ModArmorMaterials.RGOLD_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(18))));
    public static final RegistrySupplier<Item> RGOLD_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.RGOLD_CHESTPLATE,
            () -> new ArmorItem(ModArmorMaterials.RGOLD_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(18))));
    public static final RegistrySupplier<Item> RGOLD_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.RGOLD_LEGGINGS,
            () -> new ArmorItem(ModArmorMaterials.RGOLD_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(18))));
    public static final RegistrySupplier<Item> RGOLD_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.RGOLD_BOOTS,
            () -> new ArmorItem(ModArmorMaterials.RGOLD_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(18))));

    public static final RegistrySupplier<Item> RLAPIS_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.RLAPIS_HELMET,
            () -> new ArmorItem(ModArmorMaterials.RLAPIS_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(17))));
    public static final RegistrySupplier<Item> RLAPIS_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.RLAPIS_CHESTPLATE,
            () -> new ArmorItem(ModArmorMaterials.RLAPIS_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(17))));
    public static final RegistrySupplier<Item> RLAPIS_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.RLAPIS_LEGGINGS,
            () -> new ArmorItem(ModArmorMaterials.RLAPIS_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(17))));
    public static final RegistrySupplier<Item> RLAPIS_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.RLAPIS_BOOTS,
            () -> new ArmorItem(ModArmorMaterials.RLAPIS_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(17))));

    public static final RegistrySupplier<Item> OVERPOWER_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.OVERPOWER_HELMET,
            () -> new ModArmorItem(ModArmorMaterials.OVERPOWER_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(100))));
    public static final RegistrySupplier<Item> OVERPOWER_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.OVERPOWER_CHESTPLATE,
            () -> new ArmorItem(ModArmorMaterials.OVERPOWER_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(100))));
    public static final RegistrySupplier<Item> OVERPOWER_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.OVERPOWER_LEGGINGS,
            () -> new ArmorItem(ModArmorMaterials.OVERPOWER_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(100))));
    public static final RegistrySupplier<Item> OVERPOWER_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.OVERPOWER_BOOTS,
            () -> new ArmorItem(ModArmorMaterials.OVERPOWER_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(100))));

    public static final RegistrySupplier<Item> GHOST_SPAWN_EGG = ITEMS.register(GeneratedRegistrationIds.Items.GHOST_SPAWN_EGG,
            () -> new SpawnEggItem(ModEntities.GHOST.get(), 0xFFFFFF, 0x999999, new Item.Properties()));

    public static final RegistrySupplier<Item> ECTOPLASM = ITEMS.register(GeneratedRegistrationIds.Items.ECTOPLASM,
            () -> new Item(new Item.Properties().stacksTo(64)));

    // -------------------------------------------------------------------------
    // Rough Ectoplasm tools (raw ectoplasm, stone-tier equivalent)
    // -------------------------------------------------------------------------
    public static final RegistrySupplier<Item> RECTO_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.RECTO_SWORD,
            () -> new EctoSwordItem(ModToolTiers.RECTO, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.RECTO, 3, -2.4f))));

    public static final RegistrySupplier<Item> RECTO_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.RECTO_PICKAXE,
            () -> new EctoPickaxeItem(ModToolTiers.RECTO, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.RECTO, 1, -2.8f))));

    public static final RegistrySupplier<Item> RECTO_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.RECTO_SHOVEL,
            () -> new EctoShovelItem(ModToolTiers.RECTO, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.RECTO, 1.5f, -3f))));

    public static final RegistrySupplier<Item> RECTO_AXE = ITEMS.register(GeneratedRegistrationIds.Items.RECTO_AXE,
            () -> new EctoAxeItem(ModToolTiers.RECTO, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.RECTO, 6, -3.2f))));

    public static final RegistrySupplier<Item> RECTO_HOE = ITEMS.register(GeneratedRegistrationIds.Items.RECTO_HOE,
            () -> new EctoHoeItem(ModToolTiers.RECTO, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.RECTO, 0, -3f))));

    // -------------------------------------------------------------------------
    // Refined Ectoplasm + Ectoplasm tools + armor
    // -------------------------------------------------------------------------
    public static final RegistrySupplier<Item> REFINED_ECTOPLASM = ITEMS.register(GeneratedRegistrationIds.Items.REFINED_ECTOPLASM,
            () -> new Item(new Item.Properties().stacksTo(64)));

    public static final RegistrySupplier<Item> ECTO_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.ECTO_SWORD,
            () -> new EctoSwordItem(ModToolTiers.ECTOPLASM, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.ECTOPLASM, 3, -2.4f))));

    public static final RegistrySupplier<Item> ECTO_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.ECTO_PICKAXE,
            () -> new EctoPickaxeItem(ModToolTiers.ECTOPLASM, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.ECTOPLASM, 1, -2.8f))));

    public static final RegistrySupplier<Item> ECTO_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.ECTO_SHOVEL,
            () -> new EctoShovelItem(ModToolTiers.ECTOPLASM, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.ECTOPLASM, 1.5f, -3f))));

    public static final RegistrySupplier<Item> ECTO_AXE = ITEMS.register(GeneratedRegistrationIds.Items.ECTO_AXE,
            () -> new EctoAxeItem(ModToolTiers.ECTOPLASM, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.ECTOPLASM, 6, -3.1f))));

    public static final RegistrySupplier<Item> ECTO_HOE = ITEMS.register(GeneratedRegistrationIds.Items.ECTO_HOE,
            () -> new EctoHoeItem(ModToolTiers.ECTOPLASM, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.ECTOPLASM, 0, -3f))));

    public static final RegistrySupplier<Item> ECTO_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.ECTO_HELMET,
            () -> new ArmorItem(ModArmorMaterials.ECTO_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(16))));

    public static final RegistrySupplier<Item> ECTO_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.ECTO_CHESTPLATE,
            () -> new ArmorItem(ModArmorMaterials.ECTO_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(16))));

    public static final RegistrySupplier<Item> ECTO_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.ECTO_LEGGINGS,
            () -> new ArmorItem(ModArmorMaterials.ECTO_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(16))));

    public static final RegistrySupplier<Item> ECTO_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.ECTO_BOOTS,
            () -> new ArmorItem(ModArmorMaterials.ECTO_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(16))));

    // -------------------------------------------------------------------------
    // Coal material items
    // -------------------------------------------------------------------------
    public static final RegistrySupplier<Item> COAL_DUST = ITEMS.register(GeneratedRegistrationIds.Items.COAL_DUST,
            () -> new Item(new Item.Properties().stacksTo(64)));

    public static final RegistrySupplier<Item> HARDENED_COAL = ITEMS.register(GeneratedRegistrationIds.Items.HARDENED_COAL,
            () -> new Item(new Item.Properties().stacksTo(64)));

    // -------------------------------------------------------------------------
    // Coal tools  (wood mining level, durability ~120, flammable)
    // -------------------------------------------------------------------------
    public static final RegistrySupplier<Item> COAL_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.COAL_SWORD,
            () -> new CoalSwordItem(ModToolTiers.COAL_TOOL, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.COAL_TOOL, 2, -2.4f))));

    public static final RegistrySupplier<Item> COAL_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.COAL_PICKAXE,
            () -> new CoalPickaxeItem(ModToolTiers.COAL_TOOL, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.COAL_TOOL, 1, -2.8f))));

    public static final RegistrySupplier<Item> COAL_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.COAL_SHOVEL,
            () -> new CoalShovelItem(ModToolTiers.COAL_TOOL, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.COAL_TOOL, 1.5f, -3f))));

    public static final RegistrySupplier<Item> COAL_AXE = ITEMS.register(GeneratedRegistrationIds.Items.COAL_AXE,
            () -> new CoalAxeItem(ModToolTiers.COAL_TOOL, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.COAL_TOOL, 5, -3.2f))));

    public static final RegistrySupplier<Item> COAL_HOE = ITEMS.register(GeneratedRegistrationIds.Items.COAL_HOE,
            () -> new CoalHoeItem(ModToolTiers.COAL_TOOL, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.COAL_TOOL, 0, -3f))));

    // -------------------------------------------------------------------------
    // Coal armor  (between leather and chainmail, flammable)
    // -------------------------------------------------------------------------
    public static final RegistrySupplier<Item> COAL_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.COAL_HELMET,
            () -> new CoalArmorItem(ModArmorMaterials.COAL_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(8))));

    public static final RegistrySupplier<Item> COAL_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.COAL_CHESTPLATE,
            () -> new CoalArmorItem(ModArmorMaterials.COAL_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(8))));

    public static final RegistrySupplier<Item> COAL_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.COAL_LEGGINGS,
            () -> new CoalArmorItem(ModArmorMaterials.COAL_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(8))));

    public static final RegistrySupplier<Item> COAL_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.COAL_BOOTS,
            () -> new CoalArmorItem(ModArmorMaterials.COAL_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(8))));



    // -------------------------------------------------------------------------
    // Raw metal rough tool sets
    // -------------------------------------------------------------------------

    // Rough Raw Gold (wood mining level — fast, fragile)
    public static final RegistrySupplier<Item> RRAW_GOLD_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.RRAW_GOLD_SWORD,
            () -> new SwordItem(ModToolTiers.RRAW_GOLD, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.RRAW_GOLD, 3, -2.4f))));
    public static final RegistrySupplier<Item> RRAW_GOLD_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.RRAW_GOLD_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.RRAW_GOLD, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.RRAW_GOLD, 1, -2.8f))));
    public static final RegistrySupplier<Item> RRAW_GOLD_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.RRAW_GOLD_SHOVEL,
            () -> new ShovelItem(ModToolTiers.RRAW_GOLD, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.RRAW_GOLD, 1.5f, -3f))));
    public static final RegistrySupplier<Item> RRAW_GOLD_AXE = ITEMS.register(GeneratedRegistrationIds.Items.RRAW_GOLD_AXE,
            () -> new AxeItem(ModToolTiers.RRAW_GOLD, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.RRAW_GOLD, 6, -3.2f))));
    public static final RegistrySupplier<Item> RRAW_GOLD_HOE = ITEMS.register(GeneratedRegistrationIds.Items.RRAW_GOLD_HOE,
            () -> new HoeItem(ModToolTiers.RRAW_GOLD, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.RRAW_GOLD, 0, -3f))));

    // Rough Raw Copper (stone mining level)
    public static final RegistrySupplier<Item> RRAW_COPPER_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.RRAW_COPPER_SWORD,
            () -> new SwordItem(ModToolTiers.RRAW_COPPER, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.RRAW_COPPER, 3, -2.4f))));
    public static final RegistrySupplier<Item> RRAW_COPPER_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.RRAW_COPPER_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.RRAW_COPPER, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.RRAW_COPPER, 1, -2.8f))));
    public static final RegistrySupplier<Item> RRAW_COPPER_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.RRAW_COPPER_SHOVEL,
            () -> new ShovelItem(ModToolTiers.RRAW_COPPER, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.RRAW_COPPER, 1.5f, -3f))));
    public static final RegistrySupplier<Item> RRAW_COPPER_AXE = ITEMS.register(GeneratedRegistrationIds.Items.RRAW_COPPER_AXE,
            () -> new AxeItem(ModToolTiers.RRAW_COPPER, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.RRAW_COPPER, 6, -3.2f))));
    public static final RegistrySupplier<Item> RRAW_COPPER_HOE = ITEMS.register(GeneratedRegistrationIds.Items.RRAW_COPPER_HOE,
            () -> new HoeItem(ModToolTiers.RRAW_COPPER, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.RRAW_COPPER, 0, -3f))));

    // Rough Raw Iron (iron mining level)
    public static final RegistrySupplier<Item> RRAW_IRON_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.RRAW_IRON_SWORD,
            () -> new SwordItem(ModToolTiers.RRAW_IRON, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.RRAW_IRON, 3, -2.4f))));
    public static final RegistrySupplier<Item> RRAW_IRON_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.RRAW_IRON_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.RRAW_IRON, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.RRAW_IRON, 1, -2.8f))));
    public static final RegistrySupplier<Item> RRAW_IRON_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.RRAW_IRON_SHOVEL,
            () -> new ShovelItem(ModToolTiers.RRAW_IRON, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.RRAW_IRON, 1.5f, -3f))));
    public static final RegistrySupplier<Item> RRAW_IRON_AXE = ITEMS.register(GeneratedRegistrationIds.Items.RRAW_IRON_AXE,
            () -> new AxeItem(ModToolTiers.RRAW_IRON, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.RRAW_IRON, 6, -3.2f))));
    public static final RegistrySupplier<Item> RRAW_IRON_HOE = ITEMS.register(GeneratedRegistrationIds.Items.RRAW_IRON_HOE,
            () -> new HoeItem(ModToolTiers.RRAW_IRON, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.RRAW_IRON, 0, -3f))));

    // Rough Raw Ferrous Gold (iron mining level)
    public static final RegistrySupplier<Item> RRAW_RGOLD_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.RRAW_RGOLD_SWORD,
            () -> new SwordItem(ModToolTiers.RRAW_RGOLD, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.RRAW_RGOLD, 3, -2.4f))));
    public static final RegistrySupplier<Item> RRAW_RGOLD_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.RRAW_RGOLD_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.RRAW_RGOLD, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.RRAW_RGOLD, 1, -2.8f))));
    public static final RegistrySupplier<Item> RRAW_RGOLD_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.RRAW_RGOLD_SHOVEL,
            () -> new ShovelItem(ModToolTiers.RRAW_RGOLD, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.RRAW_RGOLD, 1.5f, -3f))));
    public static final RegistrySupplier<Item> RRAW_RGOLD_AXE = ITEMS.register(GeneratedRegistrationIds.Items.RRAW_RGOLD_AXE,
            () -> new AxeItem(ModToolTiers.RRAW_RGOLD, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.RRAW_RGOLD, 6, -3.2f))));
    public static final RegistrySupplier<Item> RRAW_RGOLD_HOE = ITEMS.register(GeneratedRegistrationIds.Items.RRAW_RGOLD_HOE,
            () -> new HoeItem(ModToolTiers.RRAW_RGOLD, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.RRAW_RGOLD, 0, -3f))));

    // Rough Netherite Scrap (diamond mining level)
    public static final RegistrySupplier<Item> RSCRAP_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.RSCRAP_SWORD,
            () -> new SwordItem(ModToolTiers.RSCRAP, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.RSCRAP, 3, -2.4f))));
    public static final RegistrySupplier<Item> RSCRAP_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.RSCRAP_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.RSCRAP, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.RSCRAP, 1, -2.8f))));
    public static final RegistrySupplier<Item> RSCRAP_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.RSCRAP_SHOVEL,
            () -> new ShovelItem(ModToolTiers.RSCRAP, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.RSCRAP, 1.5f, -3f))));
    public static final RegistrySupplier<Item> RSCRAP_AXE = ITEMS.register(GeneratedRegistrationIds.Items.RSCRAP_AXE,
            () -> new AxeItem(ModToolTiers.RSCRAP, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.RSCRAP, 6, -3.2f))));
    public static final RegistrySupplier<Item> RSCRAP_HOE = ITEMS.register(GeneratedRegistrationIds.Items.RSCRAP_HOE,
            () -> new HoeItem(ModToolTiers.RSCRAP, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.RSCRAP, 0, -3f))));

    // -------------------------------------------------------------------------
    // Crystal / element material items
    // -------------------------------------------------------------------------

    public static final RegistrySupplier<Item> CALCIFIED_AMETHYST = ITEMS.register(GeneratedRegistrationIds.Items.CALCIFIED_AMETHYST,
            () -> new Item(new Item.Properties().stacksTo(64)));

    public static final RegistrySupplier<Item> GLACIAL_SHARD = ITEMS.register(GeneratedRegistrationIds.Items.GLACIAL_SHARD,
            () -> new Item(new Item.Properties().stacksTo(64)));

    public static final RegistrySupplier<Item> POLISHED_QUARTZ = ITEMS.register(GeneratedRegistrationIds.Items.POLISHED_QUARTZ,
            () -> new Item(new Item.Properties().stacksTo(64)));

    public static final RegistrySupplier<Item> POLISHED_PRISMARINE = ITEMS.register(GeneratedRegistrationIds.Items.POLISHED_PRISMARINE,
            () -> new Item(new Item.Properties().stacksTo(64)));

    // -------------------------------------------------------------------------
    // Rough Amethyst tools (iron mining level)
    // -------------------------------------------------------------------------
    public static final RegistrySupplier<Item> RAMETHYST_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.RAMETHYST_SWORD,
            () -> new SwordItem(ModToolTiers.RAMETHYST, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.RAMETHYST, 3, -2.4f))));
    public static final RegistrySupplier<Item> RAMETHYST_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.RAMETHYST_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.RAMETHYST, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.RAMETHYST, 1, -2.8f))));
    public static final RegistrySupplier<Item> RAMETHYST_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.RAMETHYST_SHOVEL,
            () -> new ShovelItem(ModToolTiers.RAMETHYST, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.RAMETHYST, 1.5f, -3f))));
    public static final RegistrySupplier<Item> RAMETHYST_AXE = ITEMS.register(GeneratedRegistrationIds.Items.RAMETHYST_AXE,
            () -> new AxeItem(ModToolTiers.RAMETHYST, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.RAMETHYST, 6, -3.2f))));
    public static final RegistrySupplier<Item> RAMETHYST_HOE = ITEMS.register(GeneratedRegistrationIds.Items.RAMETHYST_HOE,
            () -> new HoeItem(ModToolTiers.RAMETHYST, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.RAMETHYST, 0, -3f))));

    // -------------------------------------------------------------------------
    // Snow tools (stone mining level)
    // -------------------------------------------------------------------------
    public static final RegistrySupplier<Item> SNOW_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.SNOW_SWORD,
            () -> new SwordItem(ModToolTiers.SNOW_TOOL, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.SNOW_TOOL, 3, -2.4f))));
    public static final RegistrySupplier<Item> SNOW_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.SNOW_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.SNOW_TOOL, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.SNOW_TOOL, 1, -2.8f))));
    public static final RegistrySupplier<Item> SNOW_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.SNOW_SHOVEL,
            () -> new ShovelItem(ModToolTiers.SNOW_TOOL, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.SNOW_TOOL, 1.5f, -3f))));
    public static final RegistrySupplier<Item> SNOW_AXE = ITEMS.register(GeneratedRegistrationIds.Items.SNOW_AXE,
            () -> new AxeItem(ModToolTiers.SNOW_TOOL, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.SNOW_TOOL, 6, -3.2f))));
    public static final RegistrySupplier<Item> SNOW_HOE = ITEMS.register(GeneratedRegistrationIds.Items.SNOW_HOE,
            () -> new HoeItem(ModToolTiers.SNOW_TOOL, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.SNOW_TOOL, 0, -3f))));

    // -------------------------------------------------------------------------
    // Rough Quartz tools (iron mining level)
    // -------------------------------------------------------------------------
    public static final RegistrySupplier<Item> RQUARTZ_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.RQUARTZ_SWORD,
            () -> new SwordItem(ModToolTiers.RQUARTZ, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.RQUARTZ, 3, -2.4f))));
    public static final RegistrySupplier<Item> RQUARTZ_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.RQUARTZ_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.RQUARTZ, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.RQUARTZ, 1, -2.8f))));
    public static final RegistrySupplier<Item> RQUARTZ_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.RQUARTZ_SHOVEL,
            () -> new ShovelItem(ModToolTiers.RQUARTZ, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.RQUARTZ, 1.5f, -3f))));
    public static final RegistrySupplier<Item> RQUARTZ_AXE = ITEMS.register(GeneratedRegistrationIds.Items.RQUARTZ_AXE,
            () -> new AxeItem(ModToolTiers.RQUARTZ, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.RQUARTZ, 6, -3.2f))));
    public static final RegistrySupplier<Item> RQUARTZ_HOE = ITEMS.register(GeneratedRegistrationIds.Items.RQUARTZ_HOE,
            () -> new HoeItem(ModToolTiers.RQUARTZ, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.RQUARTZ, 0, -3f))));

    // -------------------------------------------------------------------------
    // Rough Prismarine tools (iron mining level)
    // -------------------------------------------------------------------------
    public static final RegistrySupplier<Item> RPRISM_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.RPRISM_SWORD,
            () -> new SwordItem(ModToolTiers.RPRISM, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.RPRISM, 3, -2.4f))));
    public static final RegistrySupplier<Item> RPRISM_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.RPRISM_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.RPRISM, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.RPRISM, 1, -2.8f))));
    public static final RegistrySupplier<Item> RPRISM_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.RPRISM_SHOVEL,
            () -> new ShovelItem(ModToolTiers.RPRISM, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.RPRISM, 1.5f, -3f))));
    public static final RegistrySupplier<Item> RPRISM_AXE = ITEMS.register(GeneratedRegistrationIds.Items.RPRISM_AXE,
            () -> new AxeItem(ModToolTiers.RPRISM, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.RPRISM, 6, -3.2f))));
    public static final RegistrySupplier<Item> RPRISM_HOE = ITEMS.register(GeneratedRegistrationIds.Items.RPRISM_HOE,
            () -> new HoeItem(ModToolTiers.RPRISM, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.RPRISM, 0, -3f))));

    // -------------------------------------------------------------------------
    // Calcified Amethyst tools (iron mining level)
    // -------------------------------------------------------------------------
    public static final RegistrySupplier<Item> CAMETHYST_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.CAMETHYST_SWORD,
            () -> new SwordItem(ModToolTiers.CAMETHYST, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.CAMETHYST, 3, -2.4f))));
    public static final RegistrySupplier<Item> CAMETHYST_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.CAMETHYST_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.CAMETHYST, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.CAMETHYST, 1, -2.8f))));
    public static final RegistrySupplier<Item> CAMETHYST_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.CAMETHYST_SHOVEL,
            () -> new ShovelItem(ModToolTiers.CAMETHYST, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.CAMETHYST, 1.5f, -3f))));
    public static final RegistrySupplier<Item> CAMETHYST_AXE = ITEMS.register(GeneratedRegistrationIds.Items.CAMETHYST_AXE,
            () -> new AxeItem(ModToolTiers.CAMETHYST, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.CAMETHYST, 6, -3.2f))));
    public static final RegistrySupplier<Item> CAMETHYST_HOE = ITEMS.register(GeneratedRegistrationIds.Items.CAMETHYST_HOE,
            () -> new HoeItem(ModToolTiers.CAMETHYST, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.CAMETHYST, 0, -3f))));
    public static final RegistrySupplier<Item> CAMETHYST_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.CAMETHYST_HELMET,
            () -> new ArmorItem(ModArmorMaterials.CAMETHYST_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(15))));
    public static final RegistrySupplier<Item> CAMETHYST_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.CAMETHYST_CHESTPLATE,
            () -> new ArmorItem(ModArmorMaterials.CAMETHYST_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(15))));
    public static final RegistrySupplier<Item> CAMETHYST_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.CAMETHYST_LEGGINGS,
            () -> new ArmorItem(ModArmorMaterials.CAMETHYST_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(15))));
    public static final RegistrySupplier<Item> CAMETHYST_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.CAMETHYST_BOOTS,
            () -> new ArmorItem(ModArmorMaterials.CAMETHYST_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(15))));

    // -------------------------------------------------------------------------
    // Ice (Glacial) tools (diamond mining level)
    // -------------------------------------------------------------------------
    public static final RegistrySupplier<Item> ICE_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.ICE_SWORD,
            () -> new SwordItem(ModToolTiers.ICE_TOOL, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.ICE_TOOL, 3, -2.4f))));
    public static final RegistrySupplier<Item> ICE_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.ICE_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.ICE_TOOL, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.ICE_TOOL, 1, -2.8f))));
    public static final RegistrySupplier<Item> ICE_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.ICE_SHOVEL,
            () -> new ShovelItem(ModToolTiers.ICE_TOOL, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.ICE_TOOL, 1.5f, -3f))));
    public static final RegistrySupplier<Item> ICE_AXE = ITEMS.register(GeneratedRegistrationIds.Items.ICE_AXE,
            () -> new AxeItem(ModToolTiers.ICE_TOOL, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.ICE_TOOL, 6, -3.2f))));
    public static final RegistrySupplier<Item> ICE_HOE = ITEMS.register(GeneratedRegistrationIds.Items.ICE_HOE,
            () -> new HoeItem(ModToolTiers.ICE_TOOL, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.ICE_TOOL, 0, -3f))));
    public static final RegistrySupplier<Item> ICE_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.ICE_HELMET,
            () -> new ArmorItem(ModArmorMaterials.ICE_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(10))));
    public static final RegistrySupplier<Item> ICE_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.ICE_CHESTPLATE,
            () -> new ArmorItem(ModArmorMaterials.ICE_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(10))));
    public static final RegistrySupplier<Item> ICE_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.ICE_LEGGINGS,
            () -> new ArmorItem(ModArmorMaterials.ICE_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(10))));
    public static final RegistrySupplier<Item> ICE_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.ICE_BOOTS,
            () -> new ArmorItem(ModArmorMaterials.ICE_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(10))));

    // -------------------------------------------------------------------------
    // Polished Quartz tools (diamond mining level)
    // -------------------------------------------------------------------------
    public static final RegistrySupplier<Item> PQUARTZ_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.PQUARTZ_SWORD,
            () -> new SwordItem(ModToolTiers.PQUARTZ, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.PQUARTZ, 3, -2.4f))));
    public static final RegistrySupplier<Item> PQUARTZ_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.PQUARTZ_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.PQUARTZ, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.PQUARTZ, 1, -2.8f))));
    public static final RegistrySupplier<Item> PQUARTZ_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.PQUARTZ_SHOVEL,
            () -> new ShovelItem(ModToolTiers.PQUARTZ, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.PQUARTZ, 1.5f, -3f))));
    public static final RegistrySupplier<Item> PQUARTZ_AXE = ITEMS.register(GeneratedRegistrationIds.Items.PQUARTZ_AXE,
            () -> new AxeItem(ModToolTiers.PQUARTZ, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.PQUARTZ, 6, -3.2f))));
    public static final RegistrySupplier<Item> PQUARTZ_HOE = ITEMS.register(GeneratedRegistrationIds.Items.PQUARTZ_HOE,
            () -> new HoeItem(ModToolTiers.PQUARTZ, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.PQUARTZ, 0, -3f))));
    public static final RegistrySupplier<Item> PQUARTZ_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.PQUARTZ_HELMET,
            () -> new ArmorItem(ModArmorMaterials.PQUARTZ_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(16))));
    public static final RegistrySupplier<Item> PQUARTZ_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.PQUARTZ_CHESTPLATE,
            () -> new ArmorItem(ModArmorMaterials.PQUARTZ_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(16))));
    public static final RegistrySupplier<Item> PQUARTZ_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.PQUARTZ_LEGGINGS,
            () -> new ArmorItem(ModArmorMaterials.PQUARTZ_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(16))));
    public static final RegistrySupplier<Item> PQUARTZ_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.PQUARTZ_BOOTS,
            () -> new ArmorItem(ModArmorMaterials.PQUARTZ_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(16))));

    // -------------------------------------------------------------------------
    // Polished Prismarine tools (diamond mining level)
    // -------------------------------------------------------------------------
    public static final RegistrySupplier<Item> PPRISM_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.PPRISM_SWORD,
            () -> new SwordItem(ModToolTiers.PPRISM, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.PPRISM, 3, -2.4f))));
    public static final RegistrySupplier<Item> PPRISM_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.PPRISM_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.PPRISM, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.PPRISM, 1, -2.8f))));
    public static final RegistrySupplier<Item> PPRISM_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.PPRISM_SHOVEL,
            () -> new ShovelItem(ModToolTiers.PPRISM, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.PPRISM, 1.5f, -3f))));
    public static final RegistrySupplier<Item> PPRISM_AXE = ITEMS.register(GeneratedRegistrationIds.Items.PPRISM_AXE,
            () -> new AxeItem(ModToolTiers.PPRISM, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.PPRISM, 6, -3.2f))));
    public static final RegistrySupplier<Item> PPRISM_HOE = ITEMS.register(GeneratedRegistrationIds.Items.PPRISM_HOE,
            () -> new HoeItem(ModToolTiers.PPRISM, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.PPRISM, 0, -3f))));
    public static final RegistrySupplier<Item> PPRISM_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.PPRISM_HELMET,
            () -> new ArmorItem(ModArmorMaterials.PPRISM_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(14))));
    public static final RegistrySupplier<Item> PPRISM_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.PPRISM_CHESTPLATE,
            () -> new ArmorItem(ModArmorMaterials.PPRISM_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(14))));
    public static final RegistrySupplier<Item> PPRISM_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.PPRISM_LEGGINGS,
            () -> new ArmorItem(ModArmorMaterials.PPRISM_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(14))));
    public static final RegistrySupplier<Item> PPRISM_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.PPRISM_BOOTS,
            () -> new ArmorItem(ModArmorMaterials.PPRISM_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(14))));

    // -------------------------------------------------------------------------
    // Flint Tools (rough, stone level)
    // -------------------------------------------------------------------------
    public static final RegistrySupplier<Item> RFLINT_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.RFLINT_SWORD,
            () -> new SwordItem(ModToolTiers.RFLINT, new Item.Properties().attributes(SwordItem.createAttributes(ModToolTiers.RFLINT, 3, -2.4f))));
    public static final RegistrySupplier<Item> RFLINT_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.RFLINT_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.RFLINT, new Item.Properties().attributes(PickaxeItem.createAttributes(ModToolTiers.RFLINT, 1, -2.8f))));
    public static final RegistrySupplier<Item> RFLINT_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.RFLINT_SHOVEL,
            () -> new ShovelItem(ModToolTiers.RFLINT, new Item.Properties().attributes(ShovelItem.createAttributes(ModToolTiers.RFLINT, 1.5f, -3f))));
    public static final RegistrySupplier<Item> RFLINT_AXE = ITEMS.register(GeneratedRegistrationIds.Items.RFLINT_AXE,
            () -> new AxeItem(ModToolTiers.RFLINT, new Item.Properties().attributes(AxeItem.createAttributes(ModToolTiers.RFLINT, 6, -3.2f))));
    public static final RegistrySupplier<Item> RFLINT_HOE = ITEMS.register(GeneratedRegistrationIds.Items.RFLINT_HOE,
            () -> new HoeItem(ModToolTiers.RFLINT, new Item.Properties().attributes(HoeItem.createAttributes(ModToolTiers.RFLINT, 0, -3f))));

    // -------------------------------------------------------------------------
    // Flint-Iron (FNI) Tools + Armor
    // -------------------------------------------------------------------------
    public static final RegistrySupplier<Item> FNI_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.FNI_SWORD,
            () -> new SwordItem(ModToolTiers.FNI_TOOLS, new Item.Properties().attributes(SwordItem.createAttributes(ModToolTiers.FNI_TOOLS, 3, -2.4f))));
    public static final RegistrySupplier<Item> FNI_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.FNI_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.FNI_TOOLS, new Item.Properties().attributes(PickaxeItem.createAttributes(ModToolTiers.FNI_TOOLS, 1, -2.8f))));
    public static final RegistrySupplier<Item> FNI_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.FNI_SHOVEL,
            () -> new ShovelItem(ModToolTiers.FNI_TOOLS, new Item.Properties().attributes(ShovelItem.createAttributes(ModToolTiers.FNI_TOOLS, 1.5f, -3f))));
    public static final RegistrySupplier<Item> FNI_AXE = ITEMS.register(GeneratedRegistrationIds.Items.FNI_AXE,
            () -> new AxeItem(ModToolTiers.FNI_TOOLS, new Item.Properties().attributes(AxeItem.createAttributes(ModToolTiers.FNI_TOOLS, 6, -3.2f))));
    public static final RegistrySupplier<Item> FNI_HOE = ITEMS.register(GeneratedRegistrationIds.Items.FNI_HOE,
            () -> new HoeItem(ModToolTiers.FNI_TOOLS, new Item.Properties().attributes(HoeItem.createAttributes(ModToolTiers.FNI_TOOLS, 0, -3f))));

    public static final RegistrySupplier<Item> FNI_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.FNI_HELMET,
            () -> new ArmorItem(ModArmorMaterials.FNI_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(13))));
    public static final RegistrySupplier<Item> FNI_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.FNI_CHESTPLATE,
            () -> new ArmorItem(ModArmorMaterials.FNI_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(13))));
    public static final RegistrySupplier<Item> FNI_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.FNI_LEGGINGS,
            () -> new ArmorItem(ModArmorMaterials.FNI_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(13))));
    public static final RegistrySupplier<Item> FNI_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.FNI_BOOTS,
            () -> new ArmorItem(ModArmorMaterials.FNI_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(13))));

    // -------------------------------------------------------------------------
    // Stone Rock Variant Tools (13 types × 5 tools, each with its own tier)
    // -------------------------------------------------------------------------

    // Andesite — vanilla stone baseline
    public static final RegistrySupplier<Item> ANDESITE_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.ANDESITE_SWORD,
            () -> new SwordItem(ModToolTiers.STONE_ANDESITE, new Item.Properties().attributes(SwordItem.createAttributes(ModToolTiers.STONE_ANDESITE, 3, -2.4f))));
    public static final RegistrySupplier<Item> ANDESITE_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.ANDESITE_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.STONE_ANDESITE, new Item.Properties().attributes(PickaxeItem.createAttributes(ModToolTiers.STONE_ANDESITE, 1, -2.8f))));
    public static final RegistrySupplier<Item> ANDESITE_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.ANDESITE_SHOVEL,
            () -> new ShovelItem(ModToolTiers.STONE_ANDESITE, new Item.Properties().attributes(ShovelItem.createAttributes(ModToolTiers.STONE_ANDESITE, 1.5f, -3f))));
    public static final RegistrySupplier<Item> ANDESITE_AXE = ITEMS.register(GeneratedRegistrationIds.Items.ANDESITE_AXE,
            () -> new AxeItem(ModToolTiers.STONE_ANDESITE, new Item.Properties().attributes(AxeItem.createAttributes(ModToolTiers.STONE_ANDESITE, 6, -3.2f))));
    public static final RegistrySupplier<Item> ANDESITE_HOE = ITEMS.register(GeneratedRegistrationIds.Items.ANDESITE_HOE,
            () -> new HoeItem(ModToolTiers.STONE_ANDESITE, new Item.Properties().attributes(HoeItem.createAttributes(ModToolTiers.STONE_ANDESITE, 0, -3f))));

    // Basalt — dense, durable, slow: heavy swing, harder axe/shovel
    public static final RegistrySupplier<Item> BASALT_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.BASALT_SWORD,
            () -> new SwordItem(ModToolTiers.STONE_BASALT, new Item.Properties().attributes(SwordItem.createAttributes(ModToolTiers.STONE_BASALT, 3, -2.5f))));
    public static final RegistrySupplier<Item> BASALT_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.BASALT_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.STONE_BASALT, new Item.Properties().attributes(PickaxeItem.createAttributes(ModToolTiers.STONE_BASALT, 1, -2.9f))));
    public static final RegistrySupplier<Item> BASALT_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.BASALT_SHOVEL,
            () -> new ShovelItem(ModToolTiers.STONE_BASALT, new Item.Properties().attributes(ShovelItem.createAttributes(ModToolTiers.STONE_BASALT, 2.0f, -3.1f))));
    public static final RegistrySupplier<Item> BASALT_AXE = ITEMS.register(GeneratedRegistrationIds.Items.BASALT_AXE,
            () -> new AxeItem(ModToolTiers.STONE_BASALT, new Item.Properties().attributes(AxeItem.createAttributes(ModToolTiers.STONE_BASALT, 7, -3.3f))));
    public static final RegistrySupplier<Item> BASALT_HOE = ITEMS.register(GeneratedRegistrationIds.Items.BASALT_HOE,
            () -> new HoeItem(ModToolTiers.STONE_BASALT, new Item.Properties().attributes(HoeItem.createAttributes(ModToolTiers.STONE_BASALT, 0, -3.1f))));

    // Blackstone — toughest stone variant: heavy swing, hardest hits
    public static final RegistrySupplier<Item> BLACKSTONE_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.BLACKSTONE_SWORD,
            () -> new SwordItem(ModToolTiers.STONE_BLACKSTONE, new Item.Properties().attributes(SwordItem.createAttributes(ModToolTiers.STONE_BLACKSTONE, 4, -2.5f))));
    public static final RegistrySupplier<Item> BLACKSTONE_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.BLACKSTONE_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.STONE_BLACKSTONE, new Item.Properties().attributes(PickaxeItem.createAttributes(ModToolTiers.STONE_BLACKSTONE, 1, -2.9f))));
    public static final RegistrySupplier<Item> BLACKSTONE_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.BLACKSTONE_SHOVEL,
            () -> new ShovelItem(ModToolTiers.STONE_BLACKSTONE, new Item.Properties().attributes(ShovelItem.createAttributes(ModToolTiers.STONE_BLACKSTONE, 2.0f, -3.1f))));
    public static final RegistrySupplier<Item> BLACKSTONE_AXE = ITEMS.register(GeneratedRegistrationIds.Items.BLACKSTONE_AXE,
            () -> new AxeItem(ModToolTiers.STONE_BLACKSTONE, new Item.Properties().attributes(AxeItem.createAttributes(ModToolTiers.STONE_BLACKSTONE, 7, -3.35f))));
    public static final RegistrySupplier<Item> BLACKSTONE_HOE = ITEMS.register(GeneratedRegistrationIds.Items.BLACKSTONE_HOE,
            () -> new HoeItem(ModToolTiers.STONE_BLACKSTONE, new Item.Properties().attributes(HoeItem.createAttributes(ModToolTiers.STONE_BLACKSTONE, 0, -3.1f))));

    // Calcite — fragile, light, enchantable: fast swing, weaker hits
    public static final RegistrySupplier<Item> CALCITE_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.CALCITE_SWORD,
            () -> new SwordItem(ModToolTiers.STONE_CALCITE, new Item.Properties().attributes(SwordItem.createAttributes(ModToolTiers.STONE_CALCITE, 2, -2.2f))));
    public static final RegistrySupplier<Item> CALCITE_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.CALCITE_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.STONE_CALCITE, new Item.Properties().attributes(PickaxeItem.createAttributes(ModToolTiers.STONE_CALCITE, 1, -2.6f))));
    public static final RegistrySupplier<Item> CALCITE_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.CALCITE_SHOVEL,
            () -> new ShovelItem(ModToolTiers.STONE_CALCITE, new Item.Properties().attributes(ShovelItem.createAttributes(ModToolTiers.STONE_CALCITE, 1.0f, -2.8f))));
    public static final RegistrySupplier<Item> CALCITE_AXE = ITEMS.register(GeneratedRegistrationIds.Items.CALCITE_AXE,
            () -> new AxeItem(ModToolTiers.STONE_CALCITE, new Item.Properties().attributes(AxeItem.createAttributes(ModToolTiers.STONE_CALCITE, 5, -3.0f))));
    public static final RegistrySupplier<Item> CALCITE_HOE = ITEMS.register(GeneratedRegistrationIds.Items.CALCITE_HOE,
            () -> new HoeItem(ModToolTiers.STONE_CALCITE, new Item.Properties().attributes(HoeItem.createAttributes(ModToolTiers.STONE_CALCITE, 0, -2.6f))));

    // Deepslate — most durable stone variant, sluggish: heaviest swing, big hits
    public static final RegistrySupplier<Item> DEEPSLATE_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.DEEPSLATE_SWORD,
            () -> new SwordItem(ModToolTiers.STONE_DEEPSLATE, new Item.Properties().attributes(SwordItem.createAttributes(ModToolTiers.STONE_DEEPSLATE, 4, -2.55f))));
    public static final RegistrySupplier<Item> DEEPSLATE_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.DEEPSLATE_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.STONE_DEEPSLATE, new Item.Properties().attributes(PickaxeItem.createAttributes(ModToolTiers.STONE_DEEPSLATE, 1, -2.95f))));
    public static final RegistrySupplier<Item> DEEPSLATE_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.DEEPSLATE_SHOVEL,
            () -> new ShovelItem(ModToolTiers.STONE_DEEPSLATE, new Item.Properties().attributes(ShovelItem.createAttributes(ModToolTiers.STONE_DEEPSLATE, 2.0f, -3.15f))));
    public static final RegistrySupplier<Item> DEEPSLATE_AXE = ITEMS.register(GeneratedRegistrationIds.Items.DEEPSLATE_AXE,
            () -> new AxeItem(ModToolTiers.STONE_DEEPSLATE, new Item.Properties().attributes(AxeItem.createAttributes(ModToolTiers.STONE_DEEPSLATE, 7, -3.4f))));
    public static final RegistrySupplier<Item> DEEPSLATE_HOE = ITEMS.register(GeneratedRegistrationIds.Items.DEEPSLATE_HOE,
            () -> new HoeItem(ModToolTiers.STONE_DEEPSLATE, new Item.Properties().attributes(HoeItem.createAttributes(ModToolTiers.STONE_DEEPSLATE, 0, -3.1f))));

    // Diorite — slightly better all-round than andesite: marginally faster hoe
    public static final RegistrySupplier<Item> DIORITE_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.DIORITE_SWORD,
            () -> new SwordItem(ModToolTiers.STONE_DIORITE, new Item.Properties().attributes(SwordItem.createAttributes(ModToolTiers.STONE_DIORITE, 3, -2.4f))));
    public static final RegistrySupplier<Item> DIORITE_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.DIORITE_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.STONE_DIORITE, new Item.Properties().attributes(PickaxeItem.createAttributes(ModToolTiers.STONE_DIORITE, 1, -2.8f))));
    public static final RegistrySupplier<Item> DIORITE_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.DIORITE_SHOVEL,
            () -> new ShovelItem(ModToolTiers.STONE_DIORITE, new Item.Properties().attributes(ShovelItem.createAttributes(ModToolTiers.STONE_DIORITE, 1.5f, -3f))));
    public static final RegistrySupplier<Item> DIORITE_AXE = ITEMS.register(GeneratedRegistrationIds.Items.DIORITE_AXE,
            () -> new AxeItem(ModToolTiers.STONE_DIORITE, new Item.Properties().attributes(AxeItem.createAttributes(ModToolTiers.STONE_DIORITE, 6, -3.2f))));
    public static final RegistrySupplier<Item> DIORITE_HOE = ITEMS.register(GeneratedRegistrationIds.Items.DIORITE_HOE,
            () -> new HoeItem(ModToolTiers.STONE_DIORITE, new Item.Properties().attributes(HoeItem.createAttributes(ModToolTiers.STONE_DIORITE, 0, -2.9f))));

    // End Stone — hard alien material, highly enchantable: slightly faster across the board
    public static final RegistrySupplier<Item> END_STONE_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.END_STONE_SWORD,
            () -> new SwordItem(ModToolTiers.STONE_END_STONE, new Item.Properties().attributes(SwordItem.createAttributes(ModToolTiers.STONE_END_STONE, 3, -2.35f))));
    public static final RegistrySupplier<Item> END_STONE_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.END_STONE_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.STONE_END_STONE, new Item.Properties().attributes(PickaxeItem.createAttributes(ModToolTiers.STONE_END_STONE, 1, -2.75f))));
    public static final RegistrySupplier<Item> END_STONE_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.END_STONE_SHOVEL,
            () -> new ShovelItem(ModToolTiers.STONE_END_STONE, new Item.Properties().attributes(ShovelItem.createAttributes(ModToolTiers.STONE_END_STONE, 1.5f, -2.95f))));
    public static final RegistrySupplier<Item> END_STONE_AXE = ITEMS.register(GeneratedRegistrationIds.Items.END_STONE_AXE,
            () -> new AxeItem(ModToolTiers.STONE_END_STONE, new Item.Properties().attributes(AxeItem.createAttributes(ModToolTiers.STONE_END_STONE, 6, -3.15f))));
    public static final RegistrySupplier<Item> END_STONE_HOE = ITEMS.register(GeneratedRegistrationIds.Items.END_STONE_HOE,
            () -> new HoeItem(ModToolTiers.STONE_END_STONE, new Item.Properties().attributes(HoeItem.createAttributes(ModToolTiers.STONE_END_STONE, 0, -2.8f))));

    // Granite — hard igneous, strong and heavy: heavy swing, harder axe/shovel
    public static final RegistrySupplier<Item> GRANITE_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.GRANITE_SWORD,
            () -> new SwordItem(ModToolTiers.STONE_GRANITE, new Item.Properties().attributes(SwordItem.createAttributes(ModToolTiers.STONE_GRANITE, 3, -2.5f))));
    public static final RegistrySupplier<Item> GRANITE_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.GRANITE_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.STONE_GRANITE, new Item.Properties().attributes(PickaxeItem.createAttributes(ModToolTiers.STONE_GRANITE, 1, -2.9f))));
    public static final RegistrySupplier<Item> GRANITE_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.GRANITE_SHOVEL,
            () -> new ShovelItem(ModToolTiers.STONE_GRANITE, new Item.Properties().attributes(ShovelItem.createAttributes(ModToolTiers.STONE_GRANITE, 2.0f, -3.1f))));
    public static final RegistrySupplier<Item> GRANITE_AXE = ITEMS.register(GeneratedRegistrationIds.Items.GRANITE_AXE,
            () -> new AxeItem(ModToolTiers.STONE_GRANITE, new Item.Properties().attributes(AxeItem.createAttributes(ModToolTiers.STONE_GRANITE, 7, -3.3f))));
    public static final RegistrySupplier<Item> GRANITE_HOE = ITEMS.register(GeneratedRegistrationIds.Items.GRANITE_HOE,
            () -> new HoeItem(ModToolTiers.STONE_GRANITE, new Item.Properties().attributes(HoeItem.createAttributes(ModToolTiers.STONE_GRANITE, 0, -3.1f))));

    // Netherrack — crumbly, barely functional, extremely fast: fastest swing, weakest hits
    public static final RegistrySupplier<Item> NETHERRACK_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.NETHERRACK_SWORD,
            () -> new SwordItem(ModToolTiers.STONE_NETHERRACK, new Item.Properties().attributes(SwordItem.createAttributes(ModToolTiers.STONE_NETHERRACK, 2, -2.2f))));
    public static final RegistrySupplier<Item> NETHERRACK_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.NETHERRACK_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.STONE_NETHERRACK, new Item.Properties().attributes(PickaxeItem.createAttributes(ModToolTiers.STONE_NETHERRACK, 1, -2.6f))));
    public static final RegistrySupplier<Item> NETHERRACK_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.NETHERRACK_SHOVEL,
            () -> new ShovelItem(ModToolTiers.STONE_NETHERRACK, new Item.Properties().attributes(ShovelItem.createAttributes(ModToolTiers.STONE_NETHERRACK, 1.0f, -2.8f))));
    public static final RegistrySupplier<Item> NETHERRACK_AXE = ITEMS.register(GeneratedRegistrationIds.Items.NETHERRACK_AXE,
            () -> new AxeItem(ModToolTiers.STONE_NETHERRACK, new Item.Properties().attributes(AxeItem.createAttributes(ModToolTiers.STONE_NETHERRACK, 5, -3.0f))));
    public static final RegistrySupplier<Item> NETHERRACK_HOE = ITEMS.register(GeneratedRegistrationIds.Items.NETHERRACK_HOE,
            () -> new HoeItem(ModToolTiers.STONE_NETHERRACK, new Item.Properties().attributes(HoeItem.createAttributes(ModToolTiers.STONE_NETHERRACK, 0, -2.5f))));

    // Sandstone — soft sedimentary, brittle but quick: light swing, weaker hits
    public static final RegistrySupplier<Item> SANDSTONE_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.SANDSTONE_SWORD,
            () -> new SwordItem(ModToolTiers.STONE_SANDSTONE, new Item.Properties().attributes(SwordItem.createAttributes(ModToolTiers.STONE_SANDSTONE, 2, -2.3f))));
    public static final RegistrySupplier<Item> SANDSTONE_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.SANDSTONE_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.STONE_SANDSTONE, new Item.Properties().attributes(PickaxeItem.createAttributes(ModToolTiers.STONE_SANDSTONE, 1, -2.7f))));
    public static final RegistrySupplier<Item> SANDSTONE_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.SANDSTONE_SHOVEL,
            () -> new ShovelItem(ModToolTiers.STONE_SANDSTONE, new Item.Properties().attributes(ShovelItem.createAttributes(ModToolTiers.STONE_SANDSTONE, 1.0f, -2.9f))));
    public static final RegistrySupplier<Item> SANDSTONE_AXE = ITEMS.register(GeneratedRegistrationIds.Items.SANDSTONE_AXE,
            () -> new AxeItem(ModToolTiers.STONE_SANDSTONE, new Item.Properties().attributes(AxeItem.createAttributes(ModToolTiers.STONE_SANDSTONE, 5, -3.1f))));
    public static final RegistrySupplier<Item> SANDSTONE_HOE = ITEMS.register(GeneratedRegistrationIds.Items.SANDSTONE_HOE,
            () -> new HoeItem(ModToolTiers.STONE_SANDSTONE, new Item.Properties().attributes(HoeItem.createAttributes(ModToolTiers.STONE_SANDSTONE, 0, -2.7f))));

    // Smooth Basalt — polished volcanic, balanced between basalt and andesite: slightly heavy
    public static final RegistrySupplier<Item> SMOOTH_BASALT_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.SMOOTH_BASALT_SWORD,
            () -> new SwordItem(ModToolTiers.STONE_SMOOTH_BASALT, new Item.Properties().attributes(SwordItem.createAttributes(ModToolTiers.STONE_SMOOTH_BASALT, 3, -2.45f))));
    public static final RegistrySupplier<Item> SMOOTH_BASALT_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.SMOOTH_BASALT_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.STONE_SMOOTH_BASALT, new Item.Properties().attributes(PickaxeItem.createAttributes(ModToolTiers.STONE_SMOOTH_BASALT, 1, -2.85f))));
    public static final RegistrySupplier<Item> SMOOTH_BASALT_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.SMOOTH_BASALT_SHOVEL,
            () -> new ShovelItem(ModToolTiers.STONE_SMOOTH_BASALT, new Item.Properties().attributes(ShovelItem.createAttributes(ModToolTiers.STONE_SMOOTH_BASALT, 1.5f, -3.05f))));
    public static final RegistrySupplier<Item> SMOOTH_BASALT_AXE = ITEMS.register(GeneratedRegistrationIds.Items.SMOOTH_BASALT_AXE,
            () -> new AxeItem(ModToolTiers.STONE_SMOOTH_BASALT, new Item.Properties().attributes(AxeItem.createAttributes(ModToolTiers.STONE_SMOOTH_BASALT, 6, -3.25f))));
    public static final RegistrySupplier<Item> SMOOTH_BASALT_HOE = ITEMS.register(GeneratedRegistrationIds.Items.SMOOTH_BASALT_HOE,
            () -> new HoeItem(ModToolTiers.STONE_SMOOTH_BASALT, new Item.Properties().attributes(HoeItem.createAttributes(ModToolTiers.STONE_SMOOTH_BASALT, 0, -3.0f))));

    // Terracotta — baked clay, moderate with better enchantability: slightly faster, lighter
    public static final RegistrySupplier<Item> TERRACOTTA_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.TERRACOTTA_SWORD,
            () -> new SwordItem(ModToolTiers.STONE_TERRACOTTA, new Item.Properties().attributes(SwordItem.createAttributes(ModToolTiers.STONE_TERRACOTTA, 3, -2.35f))));
    public static final RegistrySupplier<Item> TERRACOTTA_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.TERRACOTTA_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.STONE_TERRACOTTA, new Item.Properties().attributes(PickaxeItem.createAttributes(ModToolTiers.STONE_TERRACOTTA, 1, -2.75f))));
    public static final RegistrySupplier<Item> TERRACOTTA_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.TERRACOTTA_SHOVEL,
            () -> new ShovelItem(ModToolTiers.STONE_TERRACOTTA, new Item.Properties().attributes(ShovelItem.createAttributes(ModToolTiers.STONE_TERRACOTTA, 1.5f, -2.95f))));
    public static final RegistrySupplier<Item> TERRACOTTA_AXE = ITEMS.register(GeneratedRegistrationIds.Items.TERRACOTTA_AXE,
            () -> new AxeItem(ModToolTiers.STONE_TERRACOTTA, new Item.Properties().attributes(AxeItem.createAttributes(ModToolTiers.STONE_TERRACOTTA, 6, -3.15f))));
    public static final RegistrySupplier<Item> TERRACOTTA_HOE = ITEMS.register(GeneratedRegistrationIds.Items.TERRACOTTA_HOE,
            () -> new HoeItem(ModToolTiers.STONE_TERRACOTTA, new Item.Properties().attributes(HoeItem.createAttributes(ModToolTiers.STONE_TERRACOTTA, 0, -2.8f))));

    // Tuff — compressed volcanic ash, below-average overall: slightly faster, slightly weaker
    public static final RegistrySupplier<Item> TUFF_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.TUFF_SWORD,
            () -> new SwordItem(ModToolTiers.STONE_TUFF, new Item.Properties().attributes(SwordItem.createAttributes(ModToolTiers.STONE_TUFF, 2, -2.35f))));
    public static final RegistrySupplier<Item> TUFF_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.TUFF_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.STONE_TUFF, new Item.Properties().attributes(PickaxeItem.createAttributes(ModToolTiers.STONE_TUFF, 1, -2.75f))));
    public static final RegistrySupplier<Item> TUFF_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.TUFF_SHOVEL,
            () -> new ShovelItem(ModToolTiers.STONE_TUFF, new Item.Properties().attributes(ShovelItem.createAttributes(ModToolTiers.STONE_TUFF, 1.5f, -2.95f))));
    public static final RegistrySupplier<Item> TUFF_AXE = ITEMS.register(GeneratedRegistrationIds.Items.TUFF_AXE,
            () -> new AxeItem(ModToolTiers.STONE_TUFF, new Item.Properties().attributes(AxeItem.createAttributes(ModToolTiers.STONE_TUFF, 5, -3.15f))));
    public static final RegistrySupplier<Item> TUFF_HOE = ITEMS.register(GeneratedRegistrationIds.Items.TUFF_HOE,
            () -> new HoeItem(ModToolTiers.STONE_TUFF, new Item.Properties().attributes(HoeItem.createAttributes(ModToolTiers.STONE_TUFF, 0, -2.8f))));

    // -------------------------------------------------------------------------
    // Wood Variant Tools (11 types × 5 tools, each with its own tier)
    // -------------------------------------------------------------------------

    // Oak — vanilla baseline
    public static final RegistrySupplier<Item> OAK_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.OAK_SWORD,
            () -> new SwordItem(ModToolTiers.WOOD_OAK, new Item.Properties().attributes(SwordItem.createAttributes(ModToolTiers.WOOD_OAK, 3, -2.4f))));
    public static final RegistrySupplier<Item> OAK_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.OAK_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.WOOD_OAK, new Item.Properties().attributes(PickaxeItem.createAttributes(ModToolTiers.WOOD_OAK, 1, -2.8f))));
    public static final RegistrySupplier<Item> OAK_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.OAK_SHOVEL,
            () -> new ShovelItem(ModToolTiers.WOOD_OAK, new Item.Properties().attributes(ShovelItem.createAttributes(ModToolTiers.WOOD_OAK, 1.5f, -3f))));
    public static final RegistrySupplier<Item> OAK_AXE = ITEMS.register(GeneratedRegistrationIds.Items.OAK_AXE,
            () -> new AxeItem(ModToolTiers.WOOD_OAK, new Item.Properties().attributes(AxeItem.createAttributes(ModToolTiers.WOOD_OAK, 6, -3.2f))));
    public static final RegistrySupplier<Item> OAK_HOE = ITEMS.register(GeneratedRegistrationIds.Items.OAK_HOE,
            () -> new HoeItem(ModToolTiers.WOOD_OAK, new Item.Properties().attributes(HoeItem.createAttributes(ModToolTiers.WOOD_OAK, 0, -3f))));

    // Spruce — sturdy conifer
    public static final RegistrySupplier<Item> SPRUCE_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.SPRUCE_SWORD,
            () -> new SwordItem(ModToolTiers.WOOD_SPRUCE, new Item.Properties().attributes(SwordItem.createAttributes(ModToolTiers.WOOD_SPRUCE, 3, -2.4f))));
    public static final RegistrySupplier<Item> SPRUCE_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.SPRUCE_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.WOOD_SPRUCE, new Item.Properties().attributes(PickaxeItem.createAttributes(ModToolTiers.WOOD_SPRUCE, 1, -2.8f))));
    public static final RegistrySupplier<Item> SPRUCE_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.SPRUCE_SHOVEL,
            () -> new ShovelItem(ModToolTiers.WOOD_SPRUCE, new Item.Properties().attributes(ShovelItem.createAttributes(ModToolTiers.WOOD_SPRUCE, 1.5f, -3f))));
    public static final RegistrySupplier<Item> SPRUCE_AXE = ITEMS.register(GeneratedRegistrationIds.Items.SPRUCE_AXE,
            () -> new AxeItem(ModToolTiers.WOOD_SPRUCE, new Item.Properties().attributes(AxeItem.createAttributes(ModToolTiers.WOOD_SPRUCE, 6, -3.2f))));
    public static final RegistrySupplier<Item> SPRUCE_HOE = ITEMS.register(GeneratedRegistrationIds.Items.SPRUCE_HOE,
            () -> new HoeItem(ModToolTiers.WOOD_SPRUCE, new Item.Properties().attributes(HoeItem.createAttributes(ModToolTiers.WOOD_SPRUCE, 0, -3f))));

    // Birch — light, fast, enchantable
    public static final RegistrySupplier<Item> BIRCH_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.BIRCH_SWORD,
            () -> new SwordItem(ModToolTiers.WOOD_BIRCH, new Item.Properties().attributes(SwordItem.createAttributes(ModToolTiers.WOOD_BIRCH, 3, -2.4f))));
    public static final RegistrySupplier<Item> BIRCH_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.BIRCH_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.WOOD_BIRCH, new Item.Properties().attributes(PickaxeItem.createAttributes(ModToolTiers.WOOD_BIRCH, 1, -2.8f))));
    public static final RegistrySupplier<Item> BIRCH_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.BIRCH_SHOVEL,
            () -> new ShovelItem(ModToolTiers.WOOD_BIRCH, new Item.Properties().attributes(ShovelItem.createAttributes(ModToolTiers.WOOD_BIRCH, 1.5f, -3f))));
    public static final RegistrySupplier<Item> BIRCH_AXE = ITEMS.register(GeneratedRegistrationIds.Items.BIRCH_AXE,
            () -> new AxeItem(ModToolTiers.WOOD_BIRCH, new Item.Properties().attributes(AxeItem.createAttributes(ModToolTiers.WOOD_BIRCH, 6, -3.2f))));
    public static final RegistrySupplier<Item> BIRCH_HOE = ITEMS.register(GeneratedRegistrationIds.Items.BIRCH_HOE,
            () -> new HoeItem(ModToolTiers.WOOD_BIRCH, new Item.Properties().attributes(HoeItem.createAttributes(ModToolTiers.WOOD_BIRCH, 0, -3f))));

    // Jungle — dense tropical
    public static final RegistrySupplier<Item> JUNGLE_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.JUNGLE_SWORD,
            () -> new SwordItem(ModToolTiers.WOOD_JUNGLE, new Item.Properties().attributes(SwordItem.createAttributes(ModToolTiers.WOOD_JUNGLE, 3, -2.4f))));
    public static final RegistrySupplier<Item> JUNGLE_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.JUNGLE_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.WOOD_JUNGLE, new Item.Properties().attributes(PickaxeItem.createAttributes(ModToolTiers.WOOD_JUNGLE, 1, -2.8f))));
    public static final RegistrySupplier<Item> JUNGLE_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.JUNGLE_SHOVEL,
            () -> new ShovelItem(ModToolTiers.WOOD_JUNGLE, new Item.Properties().attributes(ShovelItem.createAttributes(ModToolTiers.WOOD_JUNGLE, 1.5f, -3f))));
    public static final RegistrySupplier<Item> JUNGLE_AXE = ITEMS.register(GeneratedRegistrationIds.Items.JUNGLE_AXE,
            () -> new AxeItem(ModToolTiers.WOOD_JUNGLE, new Item.Properties().attributes(AxeItem.createAttributes(ModToolTiers.WOOD_JUNGLE, 6, -3.2f))));
    public static final RegistrySupplier<Item> JUNGLE_HOE = ITEMS.register(GeneratedRegistrationIds.Items.JUNGLE_HOE,
            () -> new HoeItem(ModToolTiers.WOOD_JUNGLE, new Item.Properties().attributes(HoeItem.createAttributes(ModToolTiers.WOOD_JUNGLE, 0, -3f))));

    // Acacia — hard African wood
    public static final RegistrySupplier<Item> ACACIA_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.ACACIA_SWORD,
            () -> new SwordItem(ModToolTiers.WOOD_ACACIA, new Item.Properties().attributes(SwordItem.createAttributes(ModToolTiers.WOOD_ACACIA, 3, -2.4f))));
    public static final RegistrySupplier<Item> ACACIA_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.ACACIA_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.WOOD_ACACIA, new Item.Properties().attributes(PickaxeItem.createAttributes(ModToolTiers.WOOD_ACACIA, 1, -2.8f))));
    public static final RegistrySupplier<Item> ACACIA_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.ACACIA_SHOVEL,
            () -> new ShovelItem(ModToolTiers.WOOD_ACACIA, new Item.Properties().attributes(ShovelItem.createAttributes(ModToolTiers.WOOD_ACACIA, 1.5f, -3f))));
    public static final RegistrySupplier<Item> ACACIA_AXE = ITEMS.register(GeneratedRegistrationIds.Items.ACACIA_AXE,
            () -> new AxeItem(ModToolTiers.WOOD_ACACIA, new Item.Properties().attributes(AxeItem.createAttributes(ModToolTiers.WOOD_ACACIA, 6, -3.2f))));
    public static final RegistrySupplier<Item> ACACIA_HOE = ITEMS.register(GeneratedRegistrationIds.Items.ACACIA_HOE,
            () -> new HoeItem(ModToolTiers.WOOD_ACACIA, new Item.Properties().attributes(HoeItem.createAttributes(ModToolTiers.WOOD_ACACIA, 0, -3f))));

    // Dark Oak — dense, durable, slow
    public static final RegistrySupplier<Item> DARK_OAK_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.DARK_OAK_SWORD,
            () -> new SwordItem(ModToolTiers.WOOD_DARK_OAK, new Item.Properties().attributes(SwordItem.createAttributes(ModToolTiers.WOOD_DARK_OAK, 3, -2.4f))));
    public static final RegistrySupplier<Item> DARK_OAK_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.DARK_OAK_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.WOOD_DARK_OAK, new Item.Properties().attributes(PickaxeItem.createAttributes(ModToolTiers.WOOD_DARK_OAK, 1, -2.8f))));
    public static final RegistrySupplier<Item> DARK_OAK_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.DARK_OAK_SHOVEL,
            () -> new ShovelItem(ModToolTiers.WOOD_DARK_OAK, new Item.Properties().attributes(ShovelItem.createAttributes(ModToolTiers.WOOD_DARK_OAK, 1.5f, -3f))));
    public static final RegistrySupplier<Item> DARK_OAK_AXE = ITEMS.register(GeneratedRegistrationIds.Items.DARK_OAK_AXE,
            () -> new AxeItem(ModToolTiers.WOOD_DARK_OAK, new Item.Properties().attributes(AxeItem.createAttributes(ModToolTiers.WOOD_DARK_OAK, 6, -3.2f))));
    public static final RegistrySupplier<Item> DARK_OAK_HOE = ITEMS.register(GeneratedRegistrationIds.Items.DARK_OAK_HOE,
            () -> new HoeItem(ModToolTiers.WOOD_DARK_OAK, new Item.Properties().attributes(HoeItem.createAttributes(ModToolTiers.WOOD_DARK_OAK, 0, -3f))));

    // Mangrove — tough tropical
    public static final RegistrySupplier<Item> MANGROVE_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.MANGROVE_SWORD,
            () -> new SwordItem(ModToolTiers.WOOD_MANGROVE, new Item.Properties().attributes(SwordItem.createAttributes(ModToolTiers.WOOD_MANGROVE, 3, -2.4f))));
    public static final RegistrySupplier<Item> MANGROVE_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.MANGROVE_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.WOOD_MANGROVE, new Item.Properties().attributes(PickaxeItem.createAttributes(ModToolTiers.WOOD_MANGROVE, 1, -2.8f))));
    public static final RegistrySupplier<Item> MANGROVE_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.MANGROVE_SHOVEL,
            () -> new ShovelItem(ModToolTiers.WOOD_MANGROVE, new Item.Properties().attributes(ShovelItem.createAttributes(ModToolTiers.WOOD_MANGROVE, 1.5f, -3f))));
    public static final RegistrySupplier<Item> MANGROVE_AXE = ITEMS.register(GeneratedRegistrationIds.Items.MANGROVE_AXE,
            () -> new AxeItem(ModToolTiers.WOOD_MANGROVE, new Item.Properties().attributes(AxeItem.createAttributes(ModToolTiers.WOOD_MANGROVE, 6, -3.2f))));
    public static final RegistrySupplier<Item> MANGROVE_HOE = ITEMS.register(GeneratedRegistrationIds.Items.MANGROVE_HOE,
            () -> new HoeItem(ModToolTiers.WOOD_MANGROVE, new Item.Properties().attributes(HoeItem.createAttributes(ModToolTiers.WOOD_MANGROVE, 0, -3f))));

    // Cherry — beautiful, very enchantable
    public static final RegistrySupplier<Item> CHERRY_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.CHERRY_SWORD,
            () -> new SwordItem(ModToolTiers.WOOD_CHERRY, new Item.Properties().attributes(SwordItem.createAttributes(ModToolTiers.WOOD_CHERRY, 3, -2.4f))));
    public static final RegistrySupplier<Item> CHERRY_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.CHERRY_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.WOOD_CHERRY, new Item.Properties().attributes(PickaxeItem.createAttributes(ModToolTiers.WOOD_CHERRY, 1, -2.8f))));
    public static final RegistrySupplier<Item> CHERRY_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.CHERRY_SHOVEL,
            () -> new ShovelItem(ModToolTiers.WOOD_CHERRY, new Item.Properties().attributes(ShovelItem.createAttributes(ModToolTiers.WOOD_CHERRY, 1.5f, -3f))));
    public static final RegistrySupplier<Item> CHERRY_AXE = ITEMS.register(GeneratedRegistrationIds.Items.CHERRY_AXE,
            () -> new AxeItem(ModToolTiers.WOOD_CHERRY, new Item.Properties().attributes(AxeItem.createAttributes(ModToolTiers.WOOD_CHERRY, 6, -3.2f))));
    public static final RegistrySupplier<Item> CHERRY_HOE = ITEMS.register(GeneratedRegistrationIds.Items.CHERRY_HOE,
            () -> new HoeItem(ModToolTiers.WOOD_CHERRY, new Item.Properties().attributes(HoeItem.createAttributes(ModToolTiers.WOOD_CHERRY, 0, -3f))));

    // Bamboo — fastest, most fragile
    public static final RegistrySupplier<Item> BAMBOO_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.BAMBOO_SWORD,
            () -> new SwordItem(ModToolTiers.WOOD_BAMBOO, new Item.Properties().attributes(SwordItem.createAttributes(ModToolTiers.WOOD_BAMBOO, 3, -2.4f))));
    public static final RegistrySupplier<Item> BAMBOO_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.BAMBOO_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.WOOD_BAMBOO, new Item.Properties().attributes(PickaxeItem.createAttributes(ModToolTiers.WOOD_BAMBOO, 1, -2.8f))));
    public static final RegistrySupplier<Item> BAMBOO_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.BAMBOO_SHOVEL,
            () -> new ShovelItem(ModToolTiers.WOOD_BAMBOO, new Item.Properties().attributes(ShovelItem.createAttributes(ModToolTiers.WOOD_BAMBOO, 1.5f, -3f))));
    public static final RegistrySupplier<Item> BAMBOO_AXE = ITEMS.register(GeneratedRegistrationIds.Items.BAMBOO_AXE,
            () -> new AxeItem(ModToolTiers.WOOD_BAMBOO, new Item.Properties().attributes(AxeItem.createAttributes(ModToolTiers.WOOD_BAMBOO, 6, -3.2f))));
    public static final RegistrySupplier<Item> BAMBOO_HOE = ITEMS.register(GeneratedRegistrationIds.Items.BAMBOO_HOE,
            () -> new HoeItem(ModToolTiers.WOOD_BAMBOO, new Item.Properties().attributes(HoeItem.createAttributes(ModToolTiers.WOOD_BAMBOO, 0, -3f))));

    // Crimson — tough nether fungus
    public static final RegistrySupplier<Item> CRIMSON_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.CRIMSON_SWORD,
            () -> new SwordItem(ModToolTiers.WOOD_CRIMSON, new Item.Properties().attributes(SwordItem.createAttributes(ModToolTiers.WOOD_CRIMSON, 3, -2.4f))));
    public static final RegistrySupplier<Item> CRIMSON_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.CRIMSON_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.WOOD_CRIMSON, new Item.Properties().attributes(PickaxeItem.createAttributes(ModToolTiers.WOOD_CRIMSON, 1, -2.8f))));
    public static final RegistrySupplier<Item> CRIMSON_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.CRIMSON_SHOVEL,
            () -> new ShovelItem(ModToolTiers.WOOD_CRIMSON, new Item.Properties().attributes(ShovelItem.createAttributes(ModToolTiers.WOOD_CRIMSON, 1.5f, -3f))));
    public static final RegistrySupplier<Item> CRIMSON_AXE = ITEMS.register(GeneratedRegistrationIds.Items.CRIMSON_AXE,
            () -> new AxeItem(ModToolTiers.WOOD_CRIMSON, new Item.Properties().attributes(AxeItem.createAttributes(ModToolTiers.WOOD_CRIMSON, 6, -3.2f))));
    public static final RegistrySupplier<Item> CRIMSON_HOE = ITEMS.register(GeneratedRegistrationIds.Items.CRIMSON_HOE,
            () -> new HoeItem(ModToolTiers.WOOD_CRIMSON, new Item.Properties().attributes(HoeItem.createAttributes(ModToolTiers.WOOD_CRIMSON, 0, -3f))));

    // Warped — resilient, enchantable
    public static final RegistrySupplier<Item> WARPED_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.WARPED_SWORD,
            () -> new SwordItem(ModToolTiers.WOOD_WARPED, new Item.Properties().attributes(SwordItem.createAttributes(ModToolTiers.WOOD_WARPED, 3, -2.4f))));
    public static final RegistrySupplier<Item> WARPED_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.WARPED_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.WOOD_WARPED, new Item.Properties().attributes(PickaxeItem.createAttributes(ModToolTiers.WOOD_WARPED, 1, -2.8f))));
    public static final RegistrySupplier<Item> WARPED_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.WARPED_SHOVEL,
            () -> new ShovelItem(ModToolTiers.WOOD_WARPED, new Item.Properties().attributes(ShovelItem.createAttributes(ModToolTiers.WOOD_WARPED, 1.5f, -3f))));
    public static final RegistrySupplier<Item> WARPED_AXE = ITEMS.register(GeneratedRegistrationIds.Items.WARPED_AXE,
            () -> new AxeItem(ModToolTiers.WOOD_WARPED, new Item.Properties().attributes(AxeItem.createAttributes(ModToolTiers.WOOD_WARPED, 6, -3.2f))));
    public static final RegistrySupplier<Item> WARPED_HOE = ITEMS.register(GeneratedRegistrationIds.Items.WARPED_HOE,
            () -> new HoeItem(ModToolTiers.WOOD_WARPED, new Item.Properties().attributes(HoeItem.createAttributes(ModToolTiers.WOOD_WARPED, 0, -3f))));

    // -------------------------------------------------------------------------
    // Leather tools (very weak, worse than wood)
    // -------------------------------------------------------------------------
    public static final RegistrySupplier<Item> LEATHER_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.LEATHER_SWORD,
            () -> new SwordItem(ModToolTiers.LEATHER, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.LEATHER, 3, -2.4f))));
    public static final RegistrySupplier<Item> LEATHER_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.LEATHER_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.LEATHER, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.LEATHER, 1, -2.8f))));
    public static final RegistrySupplier<Item> LEATHER_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.LEATHER_SHOVEL,
            () -> new ShovelItem(ModToolTiers.LEATHER, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.LEATHER, 1.5f, -3f))));
    public static final RegistrySupplier<Item> LEATHER_AXE = ITEMS.register(GeneratedRegistrationIds.Items.LEATHER_AXE,
            () -> new AxeItem(ModToolTiers.LEATHER, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.LEATHER, 6, -3.2f))));
    public static final RegistrySupplier<Item> LEATHER_HOE = ITEMS.register(GeneratedRegistrationIds.Items.LEATHER_HOE,
            () -> new HoeItem(ModToolTiers.LEATHER, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.LEATHER, 0, -3f))));

    // =========================================================================
    // Vanilla Material Sets (25 sets, ordered by power tier)
    // =========================================================================

    // ── Paper (tools only, ultra-weak) ──────────────────────────────────────
    public static final RegistrySupplier<Item> PAPER_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.PAPER_SWORD,
            () -> new SwordItem(ModToolTiers.PAPER, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.PAPER, 3, -2.4f))));
    public static final RegistrySupplier<Item> PAPER_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.PAPER_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.PAPER, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.PAPER, 1, -2.8f))));
    public static final RegistrySupplier<Item> PAPER_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.PAPER_SHOVEL,
            () -> new ShovelItem(ModToolTiers.PAPER, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.PAPER, 1.5f, -3f))));
    public static final RegistrySupplier<Item> PAPER_AXE = ITEMS.register(GeneratedRegistrationIds.Items.PAPER_AXE,
            () -> new AxeItem(ModToolTiers.PAPER, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.PAPER, 6, -3.2f))));
    public static final RegistrySupplier<Item> PAPER_HOE = ITEMS.register(GeneratedRegistrationIds.Items.PAPER_HOE,
            () -> new HoeItem(ModToolTiers.PAPER, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.PAPER, 0, -3f))));

    // ── Feather (tools only, ultra-weak) ────────────────────────────────────
    public static final RegistrySupplier<Item> FEATHER_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.FEATHER_SWORD,
            () -> new SwordItem(ModToolTiers.FEATHER, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.FEATHER, 3, -2.4f))));
    public static final RegistrySupplier<Item> FEATHER_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.FEATHER_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.FEATHER, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.FEATHER, 1, -2.8f))));
    public static final RegistrySupplier<Item> FEATHER_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.FEATHER_SHOVEL,
            () -> new ShovelItem(ModToolTiers.FEATHER, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.FEATHER, 1.5f, -3f))));
    public static final RegistrySupplier<Item> FEATHER_AXE = ITEMS.register(GeneratedRegistrationIds.Items.FEATHER_AXE,
            () -> new AxeItem(ModToolTiers.FEATHER, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.FEATHER, 6, -3.2f))));
    public static final RegistrySupplier<Item> FEATHER_HOE = ITEMS.register(GeneratedRegistrationIds.Items.FEATHER_HOE,
            () -> new HoeItem(ModToolTiers.FEATHER, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.FEATHER, 0, -3f))));

    // ── Glass (tools only, fragile but sharp) ───────────────────────────────
    public static final RegistrySupplier<Item> GLASS_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.GLASS_SWORD,
            () -> new SwordItem(ModToolTiers.GLASS, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.GLASS, 3, -2.4f))));
    public static final RegistrySupplier<Item> GLASS_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.GLASS_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.GLASS, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.GLASS, 1, -2.8f))));
    public static final RegistrySupplier<Item> GLASS_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.GLASS_SHOVEL,
            () -> new ShovelItem(ModToolTiers.GLASS, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.GLASS, 1.5f, -3f))));
    public static final RegistrySupplier<Item> GLASS_AXE = ITEMS.register(GeneratedRegistrationIds.Items.GLASS_AXE,
            () -> new AxeItem(ModToolTiers.GLASS, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.GLASS, 6, -3.2f))));
    public static final RegistrySupplier<Item> GLASS_HOE = ITEMS.register(GeneratedRegistrationIds.Items.GLASS_HOE,
            () -> new HoeItem(ModToolTiers.GLASS, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.GLASS, 0, -3f))));

    // ── Rabbit Hide (armor only) ────────────────────────────────────────────
    public static final RegistrySupplier<Item> RABBIT_HIDE_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.RABBIT_HIDE_HELMET,
            () -> new ArmorItem(ModArmorMaterials.RABBIT_HIDE_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(6))));
    public static final RegistrySupplier<Item> RABBIT_HIDE_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.RABBIT_HIDE_CHESTPLATE,
            () -> new ArmorItem(ModArmorMaterials.RABBIT_HIDE_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(6))));
    public static final RegistrySupplier<Item> RABBIT_HIDE_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.RABBIT_HIDE_LEGGINGS,
            () -> new ArmorItem(ModArmorMaterials.RABBIT_HIDE_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(6))));
    public static final RegistrySupplier<Item> RABBIT_HIDE_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.RABBIT_HIDE_BOOTS,
            () -> new ArmorItem(ModArmorMaterials.RABBIT_HIDE_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(6))));

    // ── Cactus (tools + armor) ──────────────────────────────────────────────
    public static final RegistrySupplier<Item> CACTUS_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.CACTUS_SWORD,
            () -> new SwordItem(ModToolTiers.CACTUS, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.CACTUS, 3, -2.4f))));
    public static final RegistrySupplier<Item> CACTUS_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.CACTUS_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.CACTUS, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.CACTUS, 1, -2.8f))));
    public static final RegistrySupplier<Item> CACTUS_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.CACTUS_SHOVEL,
            () -> new ShovelItem(ModToolTiers.CACTUS, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.CACTUS, 1.5f, -3f))));
    public static final RegistrySupplier<Item> CACTUS_AXE = ITEMS.register(GeneratedRegistrationIds.Items.CACTUS_AXE,
            () -> new AxeItem(ModToolTiers.CACTUS, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.CACTUS, 6, -3.2f))));
    public static final RegistrySupplier<Item> CACTUS_HOE = ITEMS.register(GeneratedRegistrationIds.Items.CACTUS_HOE,
            () -> new HoeItem(ModToolTiers.CACTUS, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.CACTUS, 0, -3f))));
    public static final RegistrySupplier<Item> CACTUS_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.CACTUS_HELMET,
            () -> new ArmorItem(ModArmorMaterials.CACTUS_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(5))));
    public static final RegistrySupplier<Item> CACTUS_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.CACTUS_CHESTPLATE,
            () -> new ArmorItem(ModArmorMaterials.CACTUS_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(5))));
    public static final RegistrySupplier<Item> CACTUS_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.CACTUS_LEGGINGS,
            () -> new ArmorItem(ModArmorMaterials.CACTUS_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(5))));
    public static final RegistrySupplier<Item> CACTUS_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.CACTUS_BOOTS,
            () -> new ArmorItem(ModArmorMaterials.CACTUS_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(5))));

    // ── Sponge (tools only) ─────────────────────────────────────────────────
    public static final RegistrySupplier<Item> SPONGE_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.SPONGE_SWORD,
            () -> new SwordItem(ModToolTiers.SPONGE, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.SPONGE, 3, -2.4f))));
    public static final RegistrySupplier<Item> SPONGE_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.SPONGE_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.SPONGE, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.SPONGE, 1, -2.8f))));
    public static final RegistrySupplier<Item> SPONGE_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.SPONGE_SHOVEL,
            () -> new ShovelItem(ModToolTiers.SPONGE, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.SPONGE, 1.5f, -3f))));
    public static final RegistrySupplier<Item> SPONGE_AXE = ITEMS.register(GeneratedRegistrationIds.Items.SPONGE_AXE,
            () -> new AxeItem(ModToolTiers.SPONGE, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.SPONGE, 6, -3.2f))));
    public static final RegistrySupplier<Item> SPONGE_HOE = ITEMS.register(GeneratedRegistrationIds.Items.SPONGE_HOE,
            () -> new HoeItem(ModToolTiers.SPONGE, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.SPONGE, 0, -3f))));

    // ── Bone (tools + armor) ────────────────────────────────────────────────
    public static final RegistrySupplier<Item> BONE_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.BONE_SWORD,
            () -> new SwordItem(ModToolTiers.BONE, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.BONE, 3, -2.4f))));
    public static final RegistrySupplier<Item> BONE_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.BONE_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.BONE, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.BONE, 1, -2.8f))));
    public static final RegistrySupplier<Item> BONE_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.BONE_SHOVEL,
            () -> new ShovelItem(ModToolTiers.BONE, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.BONE, 1.5f, -3f))));
    public static final RegistrySupplier<Item> BONE_AXE = ITEMS.register(GeneratedRegistrationIds.Items.BONE_AXE,
            () -> new AxeItem(ModToolTiers.BONE, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.BONE, 6, -3.2f))));
    public static final RegistrySupplier<Item> BONE_HOE = ITEMS.register(GeneratedRegistrationIds.Items.BONE_HOE,
            () -> new HoeItem(ModToolTiers.BONE, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.BONE, 0, -3f))));
    public static final RegistrySupplier<Item> BONE_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.BONE_HELMET,
            () -> new ArmorItem(ModArmorMaterials.BONE_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(8))));
    public static final RegistrySupplier<Item> BONE_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.BONE_CHESTPLATE,
            () -> new ArmorItem(ModArmorMaterials.BONE_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(8))));
    public static final RegistrySupplier<Item> BONE_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.BONE_LEGGINGS,
            () -> new ArmorItem(ModArmorMaterials.BONE_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(8))));
    public static final RegistrySupplier<Item> BONE_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.BONE_BOOTS,
            () -> new ArmorItem(ModArmorMaterials.BONE_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(8))));

    // ── Clay (tools + armor) ────────────────────────────────────────────────
    public static final RegistrySupplier<Item> CLAY_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.CLAY_SWORD,
            () -> new SwordItem(ModToolTiers.CLAY, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.CLAY, 3, -2.4f))));
    public static final RegistrySupplier<Item> CLAY_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.CLAY_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.CLAY, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.CLAY, 1, -2.8f))));
    public static final RegistrySupplier<Item> CLAY_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.CLAY_SHOVEL,
            () -> new ShovelItem(ModToolTiers.CLAY, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.CLAY, 1.5f, -3f))));
    public static final RegistrySupplier<Item> CLAY_AXE = ITEMS.register(GeneratedRegistrationIds.Items.CLAY_AXE,
            () -> new AxeItem(ModToolTiers.CLAY, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.CLAY, 6, -3.2f))));
    public static final RegistrySupplier<Item> CLAY_HOE = ITEMS.register(GeneratedRegistrationIds.Items.CLAY_HOE,
            () -> new HoeItem(ModToolTiers.CLAY, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.CLAY, 0, -3f))));
    public static final RegistrySupplier<Item> CLAY_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.CLAY_HELMET,
            () -> new ArmorItem(ModArmorMaterials.CLAY_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(6))));
    public static final RegistrySupplier<Item> CLAY_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.CLAY_CHESTPLATE,
            () -> new ArmorItem(ModArmorMaterials.CLAY_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(6))));
    public static final RegistrySupplier<Item> CLAY_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.CLAY_LEGGINGS,
            () -> new ArmorItem(ModArmorMaterials.CLAY_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(6))));
    public static final RegistrySupplier<Item> CLAY_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.CLAY_BOOTS,
            () -> new ArmorItem(ModArmorMaterials.CLAY_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(6))));

    // ── Nether Wart (tools only) ────────────────────────────────────────────
    public static final RegistrySupplier<Item> NETHER_WART_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.NETHER_WART_SWORD,
            () -> new SwordItem(ModToolTiers.NETHER_WART, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.NETHER_WART, 3, -2.4f))));
    public static final RegistrySupplier<Item> NETHER_WART_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.NETHER_WART_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.NETHER_WART, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.NETHER_WART, 1, -2.8f))));
    public static final RegistrySupplier<Item> NETHER_WART_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.NETHER_WART_SHOVEL,
            () -> new ShovelItem(ModToolTiers.NETHER_WART, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.NETHER_WART, 1.5f, -3f))));
    public static final RegistrySupplier<Item> NETHER_WART_AXE = ITEMS.register(GeneratedRegistrationIds.Items.NETHER_WART_AXE,
            () -> new AxeItem(ModToolTiers.NETHER_WART, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.NETHER_WART, 6, -3.2f))));
    public static final RegistrySupplier<Item> NETHER_WART_HOE = ITEMS.register(GeneratedRegistrationIds.Items.NETHER_WART_HOE,
            () -> new HoeItem(ModToolTiers.NETHER_WART, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.NETHER_WART, 0, -3f))));

    // ── Brick (tools + armor) ───────────────────────────────────────────────
    public static final RegistrySupplier<Item> BRICK_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.BRICK_SWORD,
            () -> new SwordItem(ModToolTiers.BRICK, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.BRICK, 3, -2.4f))));
    public static final RegistrySupplier<Item> BRICK_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.BRICK_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.BRICK, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.BRICK, 1, -2.8f))));
    public static final RegistrySupplier<Item> BRICK_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.BRICK_SHOVEL,
            () -> new ShovelItem(ModToolTiers.BRICK, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.BRICK, 1.5f, -3f))));
    public static final RegistrySupplier<Item> BRICK_AXE = ITEMS.register(GeneratedRegistrationIds.Items.BRICK_AXE,
            () -> new AxeItem(ModToolTiers.BRICK, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.BRICK, 6, -3.2f))));
    public static final RegistrySupplier<Item> BRICK_HOE = ITEMS.register(GeneratedRegistrationIds.Items.BRICK_HOE,
            () -> new HoeItem(ModToolTiers.BRICK, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.BRICK, 0, -3f))));
    public static final RegistrySupplier<Item> BRICK_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.BRICK_HELMET,
            () -> new ArmorItem(ModArmorMaterials.BRICK_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(12))));
    public static final RegistrySupplier<Item> BRICK_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.BRICK_CHESTPLATE,
            () -> new ArmorItem(ModArmorMaterials.BRICK_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(12))));
    public static final RegistrySupplier<Item> BRICK_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.BRICK_LEGGINGS,
            () -> new ArmorItem(ModArmorMaterials.BRICK_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(12))));
    public static final RegistrySupplier<Item> BRICK_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.BRICK_BOOTS,
            () -> new ArmorItem(ModArmorMaterials.BRICK_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(12))));

    // ── Nether Brick (tools + armor) ────────────────────────────────────────
    public static final RegistrySupplier<Item> NETHER_BRICK_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.NETHER_BRICK_SWORD,
            () -> new SwordItem(ModToolTiers.NETHER_BRICK, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.NETHER_BRICK, 3, -2.4f))));
    public static final RegistrySupplier<Item> NETHER_BRICK_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.NETHER_BRICK_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.NETHER_BRICK, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.NETHER_BRICK, 1, -2.8f))));
    public static final RegistrySupplier<Item> NETHER_BRICK_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.NETHER_BRICK_SHOVEL,
            () -> new ShovelItem(ModToolTiers.NETHER_BRICK, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.NETHER_BRICK, 1.5f, -3f))));
    public static final RegistrySupplier<Item> NETHER_BRICK_AXE = ITEMS.register(GeneratedRegistrationIds.Items.NETHER_BRICK_AXE,
            () -> new AxeItem(ModToolTiers.NETHER_BRICK, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.NETHER_BRICK, 6, -3.2f))));
    public static final RegistrySupplier<Item> NETHER_BRICK_HOE = ITEMS.register(GeneratedRegistrationIds.Items.NETHER_BRICK_HOE,
            () -> new HoeItem(ModToolTiers.NETHER_BRICK, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.NETHER_BRICK, 0, -3f))));
    public static final RegistrySupplier<Item> NETHER_BRICK_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.NETHER_BRICK_HELMET,
            () -> new ArmorItem(ModArmorMaterials.NETHER_BRICK_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(12))));
    public static final RegistrySupplier<Item> NETHER_BRICK_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.NETHER_BRICK_CHESTPLATE,
            () -> new ArmorItem(ModArmorMaterials.NETHER_BRICK_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(12))));
    public static final RegistrySupplier<Item> NETHER_BRICK_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.NETHER_BRICK_LEGGINGS,
            () -> new ArmorItem(ModArmorMaterials.NETHER_BRICK_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(12))));
    public static final RegistrySupplier<Item> NETHER_BRICK_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.NETHER_BRICK_BOOTS,
            () -> new ArmorItem(ModArmorMaterials.NETHER_BRICK_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(12))));

    // ── Pointed Dripstone (tools only) ──────────────────────────────────────
    public static final RegistrySupplier<Item> POINTED_DRIPSTONE_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.POINTED_DRIPSTONE_SWORD,
            () -> new SwordItem(ModToolTiers.POINTED_DRIPSTONE, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.POINTED_DRIPSTONE, 3, -2.4f))));
    public static final RegistrySupplier<Item> POINTED_DRIPSTONE_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.POINTED_DRIPSTONE_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.POINTED_DRIPSTONE, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.POINTED_DRIPSTONE, 1, -2.8f))));
    public static final RegistrySupplier<Item> POINTED_DRIPSTONE_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.POINTED_DRIPSTONE_SHOVEL,
            () -> new ShovelItem(ModToolTiers.POINTED_DRIPSTONE, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.POINTED_DRIPSTONE, 1.5f, -3f))));
    public static final RegistrySupplier<Item> POINTED_DRIPSTONE_AXE = ITEMS.register(GeneratedRegistrationIds.Items.POINTED_DRIPSTONE_AXE,
            () -> new AxeItem(ModToolTiers.POINTED_DRIPSTONE, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.POINTED_DRIPSTONE, 6, -3.2f))));
    public static final RegistrySupplier<Item> POINTED_DRIPSTONE_HOE = ITEMS.register(GeneratedRegistrationIds.Items.POINTED_DRIPSTONE_HOE,
            () -> new HoeItem(ModToolTiers.POINTED_DRIPSTONE, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.POINTED_DRIPSTONE, 0, -3f))));

    // ── Copper (tools + armor) ──────────────────────────────────────────────
    public static final RegistrySupplier<Item> COPPER_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.COPPER_SWORD,
            () -> new SwordItem(ModToolTiers.COPPER, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.COPPER, 3, -2.4f))));
    public static final RegistrySupplier<Item> COPPER_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.COPPER_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.COPPER, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.COPPER, 1, -2.8f))));
    public static final RegistrySupplier<Item> COPPER_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.COPPER_SHOVEL,
            () -> new ShovelItem(ModToolTiers.COPPER, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.COPPER, 1.5f, -3f))));
    public static final RegistrySupplier<Item> COPPER_AXE = ITEMS.register(GeneratedRegistrationIds.Items.COPPER_AXE,
            () -> new AxeItem(ModToolTiers.COPPER, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.COPPER, 6, -3.2f))));
    public static final RegistrySupplier<Item> COPPER_HOE = ITEMS.register(GeneratedRegistrationIds.Items.COPPER_HOE,
            () -> new HoeItem(ModToolTiers.COPPER, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.COPPER, 0, -3f))));
    public static final RegistrySupplier<Item> COPPER_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.COPPER_HELMET,
            () -> new ArmorItem(ModArmorMaterials.COPPER_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(12))));
    public static final RegistrySupplier<Item> COPPER_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.COPPER_CHESTPLATE,
            () -> new ArmorItem(ModArmorMaterials.COPPER_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(12))));
    public static final RegistrySupplier<Item> COPPER_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.COPPER_LEGGINGS,
            () -> new ArmorItem(ModArmorMaterials.COPPER_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(12))));
    public static final RegistrySupplier<Item> COPPER_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.COPPER_BOOTS,
            () -> new ArmorItem(ModArmorMaterials.COPPER_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(12))));

    // ── Phantom Membrane (tools + armor) ────────────────────────────────────
    public static final RegistrySupplier<Item> PHANTOM_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.PHANTOM_SWORD,
            () -> new SwordItem(ModToolTiers.PHANTOM_MEMBRANE, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.PHANTOM_MEMBRANE, 3, -2.4f))));
    public static final RegistrySupplier<Item> PHANTOM_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.PHANTOM_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.PHANTOM_MEMBRANE, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.PHANTOM_MEMBRANE, 1, -2.8f))));
    public static final RegistrySupplier<Item> PHANTOM_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.PHANTOM_SHOVEL,
            () -> new ShovelItem(ModToolTiers.PHANTOM_MEMBRANE, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.PHANTOM_MEMBRANE, 1.5f, -3f))));
    public static final RegistrySupplier<Item> PHANTOM_AXE = ITEMS.register(GeneratedRegistrationIds.Items.PHANTOM_AXE,
            () -> new AxeItem(ModToolTiers.PHANTOM_MEMBRANE, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.PHANTOM_MEMBRANE, 6, -3.2f))));
    public static final RegistrySupplier<Item> PHANTOM_HOE = ITEMS.register(GeneratedRegistrationIds.Items.PHANTOM_HOE,
            () -> new HoeItem(ModToolTiers.PHANTOM_MEMBRANE, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.PHANTOM_MEMBRANE, 0, -3f))));
    public static final RegistrySupplier<Item> PHANTOM_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.PHANTOM_HELMET,
            () -> new ArmorItem(ModArmorMaterials.PHANTOM_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(10))));
    public static final RegistrySupplier<Item> PHANTOM_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.PHANTOM_CHESTPLATE,
            () -> new ArmorItem(ModArmorMaterials.PHANTOM_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(10))));
    public static final RegistrySupplier<Item> PHANTOM_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.PHANTOM_LEGGINGS,
            () -> new ArmorItem(ModArmorMaterials.PHANTOM_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(10))));
    public static final RegistrySupplier<Item> PHANTOM_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.PHANTOM_BOOTS,
            () -> new ArmorItem(ModArmorMaterials.PHANTOM_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(10))));

    // ── Magma Cream (tools + armor) ─────────────────────────────────────────
    public static final RegistrySupplier<Item> MAGMA_CREAM_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.MAGMA_CREAM_SWORD,
            () -> new SwordItem(ModToolTiers.MAGMA_CREAM, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.MAGMA_CREAM, 3, -2.4f))));
    public static final RegistrySupplier<Item> MAGMA_CREAM_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.MAGMA_CREAM_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.MAGMA_CREAM, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.MAGMA_CREAM, 1, -2.8f))));
    public static final RegistrySupplier<Item> MAGMA_CREAM_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.MAGMA_CREAM_SHOVEL,
            () -> new ShovelItem(ModToolTiers.MAGMA_CREAM, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.MAGMA_CREAM, 1.5f, -3f))));
    public static final RegistrySupplier<Item> MAGMA_CREAM_AXE = ITEMS.register(GeneratedRegistrationIds.Items.MAGMA_CREAM_AXE,
            () -> new AxeItem(ModToolTiers.MAGMA_CREAM, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.MAGMA_CREAM, 6, -3.2f))));
    public static final RegistrySupplier<Item> MAGMA_CREAM_HOE = ITEMS.register(GeneratedRegistrationIds.Items.MAGMA_CREAM_HOE,
            () -> new HoeItem(ModToolTiers.MAGMA_CREAM, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.MAGMA_CREAM, 0, -3f))));
    public static final RegistrySupplier<Item> MAGMA_CREAM_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.MAGMA_CREAM_HELMET,
            () -> new ArmorItem(ModArmorMaterials.MAGMA_CREAM_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(10))));
    public static final RegistrySupplier<Item> MAGMA_CREAM_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.MAGMA_CREAM_CHESTPLATE,
            () -> new ArmorItem(ModArmorMaterials.MAGMA_CREAM_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(10))));
    public static final RegistrySupplier<Item> MAGMA_CREAM_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.MAGMA_CREAM_LEGGINGS,
            () -> new ArmorItem(ModArmorMaterials.MAGMA_CREAM_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(10))));
    public static final RegistrySupplier<Item> MAGMA_CREAM_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.MAGMA_CREAM_BOOTS,
            () -> new ArmorItem(ModArmorMaterials.MAGMA_CREAM_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(10))));

    // ── Slime (tools + armor) ───────────────────────────────────────────────
    public static final RegistrySupplier<Item> SLIME_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.SLIME_SWORD,
            () -> new SwordItem(ModToolTiers.SLIME, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.SLIME, 3, -2.4f))));
    public static final RegistrySupplier<Item> SLIME_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.SLIME_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.SLIME, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.SLIME, 1, -2.8f))));
    public static final RegistrySupplier<Item> SLIME_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.SLIME_SHOVEL,
            () -> new ShovelItem(ModToolTiers.SLIME, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.SLIME, 1.5f, -3f))));
    public static final RegistrySupplier<Item> SLIME_AXE = ITEMS.register(GeneratedRegistrationIds.Items.SLIME_AXE,
            () -> new AxeItem(ModToolTiers.SLIME, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.SLIME, 6, -3.2f))));
    public static final RegistrySupplier<Item> SLIME_HOE = ITEMS.register(GeneratedRegistrationIds.Items.SLIME_HOE,
            () -> new HoeItem(ModToolTiers.SLIME, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.SLIME, 0, -3f))));
    public static final RegistrySupplier<Item> SLIME_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.SLIME_HELMET,
            () -> new ArmorItem(ModArmorMaterials.SLIME_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(8))));
    public static final RegistrySupplier<Item> SLIME_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.SLIME_CHESTPLATE,
            () -> new ArmorItem(ModArmorMaterials.SLIME_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(8))));
    public static final RegistrySupplier<Item> SLIME_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.SLIME_LEGGINGS,
            () -> new ArmorItem(ModArmorMaterials.SLIME_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(8))));
    public static final RegistrySupplier<Item> SLIME_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.SLIME_BOOTS,
            () -> new ArmorItem(ModArmorMaterials.SLIME_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(8))));

    // ── Blaze Rod (tools + armor) ───────────────────────────────────────────
    public static final RegistrySupplier<Item> BLAZE_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.BLAZE_SWORD,
            () -> new SwordItem(ModToolTiers.BLAZE_ROD, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.BLAZE_ROD, 3, -2.4f))));
    public static final RegistrySupplier<Item> BLAZE_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.BLAZE_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.BLAZE_ROD, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.BLAZE_ROD, 1, -2.8f))));
    public static final RegistrySupplier<Item> BLAZE_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.BLAZE_SHOVEL,
            () -> new ShovelItem(ModToolTiers.BLAZE_ROD, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.BLAZE_ROD, 1.5f, -3f))));
    public static final RegistrySupplier<Item> BLAZE_AXE = ITEMS.register(GeneratedRegistrationIds.Items.BLAZE_AXE,
            () -> new AxeItem(ModToolTiers.BLAZE_ROD, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.BLAZE_ROD, 6, -3.2f))));
    public static final RegistrySupplier<Item> BLAZE_HOE = ITEMS.register(GeneratedRegistrationIds.Items.BLAZE_HOE,
            () -> new HoeItem(ModToolTiers.BLAZE_ROD, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.BLAZE_ROD, 0, -3f))));
    public static final RegistrySupplier<Item> BLAZE_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.BLAZE_HELMET,
            () -> new ArmorItem(ModArmorMaterials.BLAZE_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(14))));
    public static final RegistrySupplier<Item> BLAZE_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.BLAZE_CHESTPLATE,
            () -> new ArmorItem(ModArmorMaterials.BLAZE_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(14))));
    public static final RegistrySupplier<Item> BLAZE_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.BLAZE_LEGGINGS,
            () -> new ArmorItem(ModArmorMaterials.BLAZE_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(14))));
    public static final RegistrySupplier<Item> BLAZE_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.BLAZE_BOOTS,
            () -> new ArmorItem(ModArmorMaterials.BLAZE_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(14))));

    // ── Nautilus Shell (tools + armor) ──────────────────────────────────────
    public static final RegistrySupplier<Item> NAUTILUS_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.NAUTILUS_SWORD,
            () -> new SwordItem(ModToolTiers.NAUTILUS_SHELL, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.NAUTILUS_SHELL, 3, -2.4f))));
    public static final RegistrySupplier<Item> NAUTILUS_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.NAUTILUS_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.NAUTILUS_SHELL, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.NAUTILUS_SHELL, 1, -2.8f))));
    public static final RegistrySupplier<Item> NAUTILUS_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.NAUTILUS_SHOVEL,
            () -> new ShovelItem(ModToolTiers.NAUTILUS_SHELL, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.NAUTILUS_SHELL, 1.5f, -3f))));
    public static final RegistrySupplier<Item> NAUTILUS_AXE = ITEMS.register(GeneratedRegistrationIds.Items.NAUTILUS_AXE,
            () -> new AxeItem(ModToolTiers.NAUTILUS_SHELL, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.NAUTILUS_SHELL, 6, -3.2f))));
    public static final RegistrySupplier<Item> NAUTILUS_HOE = ITEMS.register(GeneratedRegistrationIds.Items.NAUTILUS_HOE,
            () -> new HoeItem(ModToolTiers.NAUTILUS_SHELL, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.NAUTILUS_SHELL, 0, -3f))));
    public static final RegistrySupplier<Item> NAUTILUS_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.NAUTILUS_HELMET,
            () -> new ArmorItem(ModArmorMaterials.NAUTILUS_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(14))));
    public static final RegistrySupplier<Item> NAUTILUS_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.NAUTILUS_CHESTPLATE,
            () -> new ArmorItem(ModArmorMaterials.NAUTILUS_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(14))));
    public static final RegistrySupplier<Item> NAUTILUS_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.NAUTILUS_LEGGINGS,
            () -> new ArmorItem(ModArmorMaterials.NAUTILUS_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(14))));
    public static final RegistrySupplier<Item> NAUTILUS_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.NAUTILUS_BOOTS,
            () -> new ArmorItem(ModArmorMaterials.NAUTILUS_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(14))));

    // ── Purpur (tools + armor) ──────────────────────────────────────────────
    public static final RegistrySupplier<Item> PURPUR_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.PURPUR_SWORD,
            () -> new SwordItem(ModToolTiers.PURPUR, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.PURPUR, 3, -2.4f))));
    public static final RegistrySupplier<Item> PURPUR_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.PURPUR_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.PURPUR, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.PURPUR, 1, -2.8f))));
    public static final RegistrySupplier<Item> PURPUR_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.PURPUR_SHOVEL,
            () -> new ShovelItem(ModToolTiers.PURPUR, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.PURPUR, 1.5f, -3f))));
    public static final RegistrySupplier<Item> PURPUR_AXE = ITEMS.register(GeneratedRegistrationIds.Items.PURPUR_AXE,
            () -> new AxeItem(ModToolTiers.PURPUR, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.PURPUR, 6, -3.2f))));
    public static final RegistrySupplier<Item> PURPUR_HOE = ITEMS.register(GeneratedRegistrationIds.Items.PURPUR_HOE,
            () -> new HoeItem(ModToolTiers.PURPUR, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.PURPUR, 0, -3f))));
    public static final RegistrySupplier<Item> PURPUR_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.PURPUR_HELMET,
            () -> new ArmorItem(ModArmorMaterials.PURPUR_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(14))));
    public static final RegistrySupplier<Item> PURPUR_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.PURPUR_CHESTPLATE,
            () -> new ArmorItem(ModArmorMaterials.PURPUR_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(14))));
    public static final RegistrySupplier<Item> PURPUR_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.PURPUR_LEGGINGS,
            () -> new ArmorItem(ModArmorMaterials.PURPUR_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(14))));
    public static final RegistrySupplier<Item> PURPUR_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.PURPUR_BOOTS,
            () -> new ArmorItem(ModArmorMaterials.PURPUR_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(14))));

    // ── Ghast Tear (tools + armor) ──────────────────────────────────────────
    public static final RegistrySupplier<Item> GHAST_TEAR_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.GHAST_TEAR_SWORD,
            () -> new SwordItem(ModToolTiers.GHAST_TEAR, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.GHAST_TEAR, 3, -2.4f))));
    public static final RegistrySupplier<Item> GHAST_TEAR_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.GHAST_TEAR_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.GHAST_TEAR, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.GHAST_TEAR, 1, -2.8f))));
    public static final RegistrySupplier<Item> GHAST_TEAR_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.GHAST_TEAR_SHOVEL,
            () -> new ShovelItem(ModToolTiers.GHAST_TEAR, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.GHAST_TEAR, 1.5f, -3f))));
    public static final RegistrySupplier<Item> GHAST_TEAR_AXE = ITEMS.register(GeneratedRegistrationIds.Items.GHAST_TEAR_AXE,
            () -> new AxeItem(ModToolTiers.GHAST_TEAR, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.GHAST_TEAR, 6, -3.2f))));
    public static final RegistrySupplier<Item> GHAST_TEAR_HOE = ITEMS.register(GeneratedRegistrationIds.Items.GHAST_TEAR_HOE,
            () -> new HoeItem(ModToolTiers.GHAST_TEAR, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.GHAST_TEAR, 0, -3f))));
    public static final RegistrySupplier<Item> GHAST_TEAR_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.GHAST_TEAR_HELMET,
            () -> new ArmorItem(ModArmorMaterials.GHAST_TEAR_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(16))));
    public static final RegistrySupplier<Item> GHAST_TEAR_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.GHAST_TEAR_CHESTPLATE,
            () -> new ArmorItem(ModArmorMaterials.GHAST_TEAR_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(16))));
    public static final RegistrySupplier<Item> GHAST_TEAR_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.GHAST_TEAR_LEGGINGS,
            () -> new ArmorItem(ModArmorMaterials.GHAST_TEAR_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(16))));
    public static final RegistrySupplier<Item> GHAST_TEAR_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.GHAST_TEAR_BOOTS,
            () -> new ArmorItem(ModArmorMaterials.GHAST_TEAR_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(16))));

    // ── Eye of Ender (tools + armor) ────────────────────────────────────────
    public static final RegistrySupplier<Item> EYE_OF_ENDER_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.EYE_OF_ENDER_SWORD,
            () -> new SwordItem(ModToolTiers.EYE_OF_ENDER, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.EYE_OF_ENDER, 3, -2.4f))));
    public static final RegistrySupplier<Item> EYE_OF_ENDER_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.EYE_OF_ENDER_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.EYE_OF_ENDER, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.EYE_OF_ENDER, 1, -2.8f))));
    public static final RegistrySupplier<Item> EYE_OF_ENDER_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.EYE_OF_ENDER_SHOVEL,
            () -> new ShovelItem(ModToolTiers.EYE_OF_ENDER, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.EYE_OF_ENDER, 1.5f, -3f))));
    public static final RegistrySupplier<Item> EYE_OF_ENDER_AXE = ITEMS.register(GeneratedRegistrationIds.Items.EYE_OF_ENDER_AXE,
            () -> new AxeItem(ModToolTiers.EYE_OF_ENDER, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.EYE_OF_ENDER, 6, -3.2f))));
    public static final RegistrySupplier<Item> EYE_OF_ENDER_HOE = ITEMS.register(GeneratedRegistrationIds.Items.EYE_OF_ENDER_HOE,
            () -> new HoeItem(ModToolTiers.EYE_OF_ENDER, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.EYE_OF_ENDER, 0, -3f))));
    public static final RegistrySupplier<Item> EYE_OF_ENDER_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.EYE_OF_ENDER_HELMET,
            () -> new ArmorItem(ModArmorMaterials.EYE_OF_ENDER_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(16))));
    public static final RegistrySupplier<Item> EYE_OF_ENDER_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.EYE_OF_ENDER_CHESTPLATE,
            () -> new ArmorItem(ModArmorMaterials.EYE_OF_ENDER_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(16))));
    public static final RegistrySupplier<Item> EYE_OF_ENDER_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.EYE_OF_ENDER_LEGGINGS,
            () -> new ArmorItem(ModArmorMaterials.EYE_OF_ENDER_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(16))));
    public static final RegistrySupplier<Item> EYE_OF_ENDER_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.EYE_OF_ENDER_BOOTS,
            () -> new ArmorItem(ModArmorMaterials.EYE_OF_ENDER_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(16))));

    // ── Shulker Shell (tools + armor) ───────────────────────────────────────
    public static final RegistrySupplier<Item> SHULKER_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.SHULKER_SWORD,
            () -> new SwordItem(ModToolTiers.SHULKER_SHELL, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.SHULKER_SHELL, 3, -2.4f))));
    public static final RegistrySupplier<Item> SHULKER_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.SHULKER_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.SHULKER_SHELL, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.SHULKER_SHELL, 1, -2.8f))));
    public static final RegistrySupplier<Item> SHULKER_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.SHULKER_SHOVEL,
            () -> new ShovelItem(ModToolTiers.SHULKER_SHELL, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.SHULKER_SHELL, 1.5f, -3f))));
    public static final RegistrySupplier<Item> SHULKER_AXE = ITEMS.register(GeneratedRegistrationIds.Items.SHULKER_AXE,
            () -> new AxeItem(ModToolTiers.SHULKER_SHELL, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.SHULKER_SHELL, 6, -3.2f))));
    public static final RegistrySupplier<Item> SHULKER_HOE = ITEMS.register(GeneratedRegistrationIds.Items.SHULKER_HOE,
            () -> new HoeItem(ModToolTiers.SHULKER_SHELL, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.SHULKER_SHELL, 0, -3f))));
    public static final RegistrySupplier<Item> SHULKER_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.SHULKER_HELMET,
            () -> new ArmorItem(ModArmorMaterials.SHULKER_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(18))));
    public static final RegistrySupplier<Item> SHULKER_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.SHULKER_CHESTPLATE,
            () -> new ArmorItem(ModArmorMaterials.SHULKER_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(18))));
    public static final RegistrySupplier<Item> SHULKER_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.SHULKER_LEGGINGS,
            () -> new ArmorItem(ModArmorMaterials.SHULKER_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(18))));
    public static final RegistrySupplier<Item> SHULKER_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.SHULKER_BOOTS,
            () -> new ArmorItem(ModArmorMaterials.SHULKER_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(18))));

    // ── Turtle Scute (armor only) ───────────────────────────────────────────
    public static final RegistrySupplier<Item> TURTLE_SCUTE_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.TURTLE_SCUTE_HELMET,
            () -> new ArmorItem(ModArmorMaterials.TURTLE_SCUTE_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(20))));
    public static final RegistrySupplier<Item> TURTLE_SCUTE_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.TURTLE_SCUTE_CHESTPLATE,
            () -> new ArmorItem(ModArmorMaterials.TURTLE_SCUTE_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(20))));
    public static final RegistrySupplier<Item> TURTLE_SCUTE_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.TURTLE_SCUTE_LEGGINGS,
            () -> new ArmorItem(ModArmorMaterials.TURTLE_SCUTE_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(20))));
    public static final RegistrySupplier<Item> TURTLE_SCUTE_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.TURTLE_SCUTE_BOOTS,
            () -> new ArmorItem(ModArmorMaterials.TURTLE_SCUTE_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(20))));

    // ── Echo Shard (tools + armor) ──────────────────────────────────────────
    public static final RegistrySupplier<Item> ECHO_SHARD_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.ECHO_SHARD_SWORD,
            () -> new SwordItem(ModToolTiers.ECHO_SHARD, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.ECHO_SHARD, 3, -2.4f))));
    public static final RegistrySupplier<Item> ECHO_SHARD_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.ECHO_SHARD_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.ECHO_SHARD, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.ECHO_SHARD, 1, -2.8f))));
    public static final RegistrySupplier<Item> ECHO_SHARD_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.ECHO_SHARD_SHOVEL,
            () -> new ShovelItem(ModToolTiers.ECHO_SHARD, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.ECHO_SHARD, 1.5f, -3f))));
    public static final RegistrySupplier<Item> ECHO_SHARD_AXE = ITEMS.register(GeneratedRegistrationIds.Items.ECHO_SHARD_AXE,
            () -> new AxeItem(ModToolTiers.ECHO_SHARD, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.ECHO_SHARD, 6, -3.2f))));
    public static final RegistrySupplier<Item> ECHO_SHARD_HOE = ITEMS.register(GeneratedRegistrationIds.Items.ECHO_SHARD_HOE,
            () -> new HoeItem(ModToolTiers.ECHO_SHARD, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.ECHO_SHARD, 0, -3f))));
    public static final RegistrySupplier<Item> ECHO_SHARD_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.ECHO_SHARD_HELMET,
            () -> new ArmorItem(ModArmorMaterials.ECHO_SHARD_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(22))));
    public static final RegistrySupplier<Item> ECHO_SHARD_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.ECHO_SHARD_CHESTPLATE,
            () -> new ArmorItem(ModArmorMaterials.ECHO_SHARD_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(22))));
    public static final RegistrySupplier<Item> ECHO_SHARD_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.ECHO_SHARD_LEGGINGS,
            () -> new ArmorItem(ModArmorMaterials.ECHO_SHARD_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(22))));
    public static final RegistrySupplier<Item> ECHO_SHARD_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.ECHO_SHARD_BOOTS,
            () -> new ArmorItem(ModArmorMaterials.ECHO_SHARD_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(22))));

    // ── Dragon's Breath (tools + armor) ─────────────────────────────────────
    public static final RegistrySupplier<Item> DRAGON_BREATH_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.DRAGON_BREATH_SWORD,
            () -> new SwordItem(ModToolTiers.DRAGON_BREATH, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.DRAGON_BREATH, 3, -2.4f))));
    public static final RegistrySupplier<Item> DRAGON_BREATH_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.DRAGON_BREATH_PICKAXE,
            () -> new PickaxeItem(ModToolTiers.DRAGON_BREATH, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.DRAGON_BREATH, 1, -2.8f))));
    public static final RegistrySupplier<Item> DRAGON_BREATH_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.DRAGON_BREATH_SHOVEL,
            () -> new ShovelItem(ModToolTiers.DRAGON_BREATH, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.DRAGON_BREATH, 1.5f, -3f))));
    public static final RegistrySupplier<Item> DRAGON_BREATH_AXE = ITEMS.register(GeneratedRegistrationIds.Items.DRAGON_BREATH_AXE,
            () -> new AxeItem(ModToolTiers.DRAGON_BREATH, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.DRAGON_BREATH, 6, -3.2f))));
    public static final RegistrySupplier<Item> DRAGON_BREATH_HOE = ITEMS.register(GeneratedRegistrationIds.Items.DRAGON_BREATH_HOE,
            () -> new HoeItem(ModToolTiers.DRAGON_BREATH, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.DRAGON_BREATH, 0, -3f))));
    public static final RegistrySupplier<Item> DRAGON_BREATH_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.DRAGON_BREATH_HELMET,
            () -> new ArmorItem(ModArmorMaterials.DRAGON_BREATH_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(25))));
    public static final RegistrySupplier<Item> DRAGON_BREATH_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.DRAGON_BREATH_CHESTPLATE,
            () -> new ArmorItem(ModArmorMaterials.DRAGON_BREATH_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(25))));
    public static final RegistrySupplier<Item> DRAGON_BREATH_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.DRAGON_BREATH_LEGGINGS,
            () -> new ArmorItem(ModArmorMaterials.DRAGON_BREATH_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(25))));
    public static final RegistrySupplier<Item> DRAGON_BREATH_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.DRAGON_BREATH_BOOTS,
            () -> new ArmorItem(ModArmorMaterials.DRAGON_BREATH_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(25))));

    // -------------------------------------------------------------------------
    // Cake tools  (below wood tier, edible — it's cake!)
    // -------------------------------------------------------------------------
    public static final RegistrySupplier<Item> CAKE_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.CAKE_SWORD,
            () -> new EdibleSwordItem(ModToolTiers.CAKE, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.CAKE, 3, -2.4f))
                    .food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.1f).build())));

    public static final RegistrySupplier<Item> CAKE_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.CAKE_PICKAXE,
            () -> new EdiblePickaxeItem(ModToolTiers.CAKE, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.CAKE, 1, -2.8f))
                    .food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.1f).build())));

    public static final RegistrySupplier<Item> CAKE_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.CAKE_SHOVEL,
            () -> new EdibleShovelItem(ModToolTiers.CAKE, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.CAKE, 1.5f, -3f))
                    .food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.1f).build())));

    public static final RegistrySupplier<Item> CAKE_AXE = ITEMS.register(GeneratedRegistrationIds.Items.CAKE_AXE,
            () -> new EdibleAxeItem(ModToolTiers.CAKE, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.CAKE, 6, -3.2f))
                    .food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.1f).build())));

    public static final RegistrySupplier<Item> CAKE_HOE = ITEMS.register(GeneratedRegistrationIds.Items.CAKE_HOE,
            () -> new EdibleHoeItem(ModToolTiers.CAKE, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.CAKE, 0, -3f))
                    .food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.1f).build())));

    // -------------------------------------------------------------------------
    // Cake armor  (below leather, edible)
    // -------------------------------------------------------------------------
    public static final RegistrySupplier<Item> CAKE_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.CAKE_HELMET,
            () -> new EdibleArmorItem(ModArmorMaterials.CAKE_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(3))
                            .food(new FoodProperties.Builder().nutrition(10).saturationModifier(0.1f).build())));

    public static final RegistrySupplier<Item> CAKE_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.CAKE_CHESTPLATE,
            () -> new EdibleArmorItem(ModArmorMaterials.CAKE_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(3))
                            .food(new FoodProperties.Builder().nutrition(14).saturationModifier(0.1f).build())));

    public static final RegistrySupplier<Item> CAKE_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.CAKE_LEGGINGS,
            () -> new EdibleArmorItem(ModArmorMaterials.CAKE_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(3))
                            .food(new FoodProperties.Builder().nutrition(14).saturationModifier(0.1f).build())));

    public static final RegistrySupplier<Item> CAKE_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.CAKE_BOOTS,
            () -> new EdibleArmorItem(ModArmorMaterials.CAKE_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(3))
                            .food(new FoodProperties.Builder().nutrition(8).saturationModifier(0.1f).build())));

    // =========================================================================
    // Bread tools + armor (below wood tier, edible)
    // =========================================================================
    public static final RegistrySupplier<Item> BREAD_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.BREAD_SWORD,
            () -> new EdibleSwordItem(ModToolTiers.BREAD, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.BREAD, 3, -2.4f))
                    .food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> BREAD_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.BREAD_PICKAXE,
            () -> new EdiblePickaxeItem(ModToolTiers.BREAD, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.BREAD, 1, -2.8f))
                    .food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> BREAD_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.BREAD_SHOVEL,
            () -> new EdibleShovelItem(ModToolTiers.BREAD, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.BREAD, 1.5f, -3f))
                    .food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> BREAD_AXE = ITEMS.register(GeneratedRegistrationIds.Items.BREAD_AXE,
            () -> new EdibleAxeItem(ModToolTiers.BREAD, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.BREAD, 6, -3.2f))
                    .food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> BREAD_HOE = ITEMS.register(GeneratedRegistrationIds.Items.BREAD_HOE,
            () -> new EdibleHoeItem(ModToolTiers.BREAD, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.BREAD, 0, -3f))
                    .food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> BREAD_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.BREAD_HELMET,
            () -> new EdibleArmorItem(ModArmorMaterials.BREAD_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(3))
                            .food(new FoodProperties.Builder().nutrition(10).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> BREAD_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.BREAD_CHESTPLATE,
            () -> new EdibleArmorItem(ModArmorMaterials.BREAD_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(3))
                            .food(new FoodProperties.Builder().nutrition(14).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> BREAD_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.BREAD_LEGGINGS,
            () -> new EdibleArmorItem(ModArmorMaterials.BREAD_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(3))
                            .food(new FoodProperties.Builder().nutrition(14).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> BREAD_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.BREAD_BOOTS,
            () -> new EdibleArmorItem(ModArmorMaterials.BREAD_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(3))
                            .food(new FoodProperties.Builder().nutrition(8).saturationModifier(0.1f).build())));

    // =========================================================================
    // Dried Kelp tools + armor
    // =========================================================================
    public static final RegistrySupplier<Item> DRIED_KELP_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.DRIED_KELP_SWORD,
            () -> new EdibleSwordItem(ModToolTiers.DRIED_KELP, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.DRIED_KELP, 3, -2.4f))
                    .food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> DRIED_KELP_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.DRIED_KELP_PICKAXE,
            () -> new EdiblePickaxeItem(ModToolTiers.DRIED_KELP, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.DRIED_KELP, 1, -2.8f))
                    .food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> DRIED_KELP_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.DRIED_KELP_SHOVEL,
            () -> new EdibleShovelItem(ModToolTiers.DRIED_KELP, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.DRIED_KELP, 1.5f, -3f))
                    .food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> DRIED_KELP_AXE = ITEMS.register(GeneratedRegistrationIds.Items.DRIED_KELP_AXE,
            () -> new EdibleAxeItem(ModToolTiers.DRIED_KELP, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.DRIED_KELP, 6, -3.2f))
                    .food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> DRIED_KELP_HOE = ITEMS.register(GeneratedRegistrationIds.Items.DRIED_KELP_HOE,
            () -> new EdibleHoeItem(ModToolTiers.DRIED_KELP, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.DRIED_KELP, 0, -3f))
                    .food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> DRIED_KELP_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.DRIED_KELP_HELMET,
            () -> new EdibleArmorItem(ModArmorMaterials.DRIED_KELP_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(2))
                            .food(new FoodProperties.Builder().nutrition(10).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> DRIED_KELP_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.DRIED_KELP_CHESTPLATE,
            () -> new EdibleArmorItem(ModArmorMaterials.DRIED_KELP_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(2))
                            .food(new FoodProperties.Builder().nutrition(14).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> DRIED_KELP_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.DRIED_KELP_LEGGINGS,
            () -> new EdibleArmorItem(ModArmorMaterials.DRIED_KELP_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(2))
                            .food(new FoodProperties.Builder().nutrition(14).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> DRIED_KELP_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.DRIED_KELP_BOOTS,
            () -> new EdibleArmorItem(ModArmorMaterials.DRIED_KELP_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(2))
                            .food(new FoodProperties.Builder().nutrition(8).saturationModifier(0.1f).build())));

    // =========================================================================
    // Rotten Flesh tools + armor
    // =========================================================================
    public static final RegistrySupplier<Item> ROTTEN_FLESH_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.ROTTEN_FLESH_SWORD,
            () -> new EdibleSwordItem(ModToolTiers.ROTTEN_FLESH, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.ROTTEN_FLESH, 3, -2.4f))
                    .food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> ROTTEN_FLESH_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.ROTTEN_FLESH_PICKAXE,
            () -> new EdiblePickaxeItem(ModToolTiers.ROTTEN_FLESH, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.ROTTEN_FLESH, 1, -2.8f))
                    .food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> ROTTEN_FLESH_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.ROTTEN_FLESH_SHOVEL,
            () -> new EdibleShovelItem(ModToolTiers.ROTTEN_FLESH, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.ROTTEN_FLESH, 1.5f, -3f))
                    .food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> ROTTEN_FLESH_AXE = ITEMS.register(GeneratedRegistrationIds.Items.ROTTEN_FLESH_AXE,
            () -> new EdibleAxeItem(ModToolTiers.ROTTEN_FLESH, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.ROTTEN_FLESH, 6, -3.2f))
                    .food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> ROTTEN_FLESH_HOE = ITEMS.register(GeneratedRegistrationIds.Items.ROTTEN_FLESH_HOE,
            () -> new EdibleHoeItem(ModToolTiers.ROTTEN_FLESH, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.ROTTEN_FLESH, 0, -3f))
                    .food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> ROTTEN_FLESH_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.ROTTEN_FLESH_HELMET,
            () -> new EdibleArmorItem(ModArmorMaterials.ROTTEN_FLESH_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(2))
                            .food(new FoodProperties.Builder().nutrition(10).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> ROTTEN_FLESH_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.ROTTEN_FLESH_CHESTPLATE,
            () -> new EdibleArmorItem(ModArmorMaterials.ROTTEN_FLESH_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(2))
                            .food(new FoodProperties.Builder().nutrition(14).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> ROTTEN_FLESH_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.ROTTEN_FLESH_LEGGINGS,
            () -> new EdibleArmorItem(ModArmorMaterials.ROTTEN_FLESH_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(2))
                            .food(new FoodProperties.Builder().nutrition(14).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> ROTTEN_FLESH_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.ROTTEN_FLESH_BOOTS,
            () -> new EdibleArmorItem(ModArmorMaterials.ROTTEN_FLESH_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(2))
                            .food(new FoodProperties.Builder().nutrition(8).saturationModifier(0.1f).build())));

    // =========================================================================
    // Melon tools + armor (wood-equivalent)
    // =========================================================================
    public static final RegistrySupplier<Item> MELON_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.MELON_SWORD,
            () -> new EdibleSwordItem(ModToolTiers.MELON, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.MELON, 3, -2.4f))
                    .food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> MELON_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.MELON_PICKAXE,
            () -> new EdiblePickaxeItem(ModToolTiers.MELON, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.MELON, 1, -2.8f))
                    .food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> MELON_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.MELON_SHOVEL,
            () -> new EdibleShovelItem(ModToolTiers.MELON, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.MELON, 1.5f, -3f))
                    .food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> MELON_AXE = ITEMS.register(GeneratedRegistrationIds.Items.MELON_AXE,
            () -> new EdibleAxeItem(ModToolTiers.MELON, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.MELON, 6, -3.2f))
                    .food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> MELON_HOE = ITEMS.register(GeneratedRegistrationIds.Items.MELON_HOE,
            () -> new EdibleHoeItem(ModToolTiers.MELON, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.MELON, 0, -3f))
                    .food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> MELON_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.MELON_HELMET,
            () -> new EdibleArmorItem(ModArmorMaterials.MELON_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(5))
                            .food(new FoodProperties.Builder().nutrition(10).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> MELON_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.MELON_CHESTPLATE,
            () -> new EdibleArmorItem(ModArmorMaterials.MELON_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(5))
                            .food(new FoodProperties.Builder().nutrition(14).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> MELON_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.MELON_LEGGINGS,
            () -> new EdibleArmorItem(ModArmorMaterials.MELON_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(5))
                            .food(new FoodProperties.Builder().nutrition(14).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> MELON_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.MELON_BOOTS,
            () -> new EdibleArmorItem(ModArmorMaterials.MELON_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(5))
                            .food(new FoodProperties.Builder().nutrition(8).saturationModifier(0.1f).build())));

    // =========================================================================
    // Sweet Berries tools + armor (wood-equivalent)
    // =========================================================================
    public static final RegistrySupplier<Item> SWEET_BERRY_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.SWEET_BERRY_SWORD,
            () -> new EdibleSwordItem(ModToolTiers.SWEET_BERRIES, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.SWEET_BERRIES, 3, -2.4f))
                    .food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> SWEET_BERRY_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.SWEET_BERRY_PICKAXE,
            () -> new EdiblePickaxeItem(ModToolTiers.SWEET_BERRIES, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.SWEET_BERRIES, 1, -2.8f))
                    .food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> SWEET_BERRY_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.SWEET_BERRY_SHOVEL,
            () -> new EdibleShovelItem(ModToolTiers.SWEET_BERRIES, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.SWEET_BERRIES, 1.5f, -3f))
                    .food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> SWEET_BERRY_AXE = ITEMS.register(GeneratedRegistrationIds.Items.SWEET_BERRY_AXE,
            () -> new EdibleAxeItem(ModToolTiers.SWEET_BERRIES, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.SWEET_BERRIES, 6, -3.2f))
                    .food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> SWEET_BERRY_HOE = ITEMS.register(GeneratedRegistrationIds.Items.SWEET_BERRY_HOE,
            () -> new EdibleHoeItem(ModToolTiers.SWEET_BERRIES, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.SWEET_BERRIES, 0, -3f))
                    .food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> SWEET_BERRY_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.SWEET_BERRY_HELMET,
            () -> new EdibleArmorItem(ModArmorMaterials.SWEET_BERRY_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(4))
                            .food(new FoodProperties.Builder().nutrition(10).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> SWEET_BERRY_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.SWEET_BERRY_CHESTPLATE,
            () -> new EdibleArmorItem(ModArmorMaterials.SWEET_BERRY_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(4))
                            .food(new FoodProperties.Builder().nutrition(14).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> SWEET_BERRY_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.SWEET_BERRY_LEGGINGS,
            () -> new EdibleArmorItem(ModArmorMaterials.SWEET_BERRY_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(4))
                            .food(new FoodProperties.Builder().nutrition(14).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> SWEET_BERRY_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.SWEET_BERRY_BOOTS,
            () -> new EdibleArmorItem(ModArmorMaterials.SWEET_BERRY_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(4))
                            .food(new FoodProperties.Builder().nutrition(8).saturationModifier(0.1f).build())));

    // =========================================================================
    // Pumpkin Pie tools + armor (wood-equivalent)
    // =========================================================================
    public static final RegistrySupplier<Item> PUMPKIN_PIE_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.PUMPKIN_PIE_SWORD,
            () -> new EdibleSwordItem(ModToolTiers.PUMPKIN_PIE, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.PUMPKIN_PIE, 3, -2.4f))
                    .food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> PUMPKIN_PIE_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.PUMPKIN_PIE_PICKAXE,
            () -> new EdiblePickaxeItem(ModToolTiers.PUMPKIN_PIE, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.PUMPKIN_PIE, 1, -2.8f))
                    .food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> PUMPKIN_PIE_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.PUMPKIN_PIE_SHOVEL,
            () -> new EdibleShovelItem(ModToolTiers.PUMPKIN_PIE, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.PUMPKIN_PIE, 1.5f, -3f))
                    .food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> PUMPKIN_PIE_AXE = ITEMS.register(GeneratedRegistrationIds.Items.PUMPKIN_PIE_AXE,
            () -> new EdibleAxeItem(ModToolTiers.PUMPKIN_PIE, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.PUMPKIN_PIE, 6, -3.2f))
                    .food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> PUMPKIN_PIE_HOE = ITEMS.register(GeneratedRegistrationIds.Items.PUMPKIN_PIE_HOE,
            () -> new EdibleHoeItem(ModToolTiers.PUMPKIN_PIE, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.PUMPKIN_PIE, 0, -3f))
                    .food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> PUMPKIN_PIE_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.PUMPKIN_PIE_HELMET,
            () -> new EdibleArmorItem(ModArmorMaterials.PUMPKIN_PIE_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(4))
                            .food(new FoodProperties.Builder().nutrition(10).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> PUMPKIN_PIE_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.PUMPKIN_PIE_CHESTPLATE,
            () -> new EdibleArmorItem(ModArmorMaterials.PUMPKIN_PIE_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(4))
                            .food(new FoodProperties.Builder().nutrition(14).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> PUMPKIN_PIE_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.PUMPKIN_PIE_LEGGINGS,
            () -> new EdibleArmorItem(ModArmorMaterials.PUMPKIN_PIE_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(4))
                            .food(new FoodProperties.Builder().nutrition(14).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> PUMPKIN_PIE_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.PUMPKIN_PIE_BOOTS,
            () -> new EdibleArmorItem(ModArmorMaterials.PUMPKIN_PIE_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(4))
                            .food(new FoodProperties.Builder().nutrition(8).saturationModifier(0.1f).build())));

    // =========================================================================
    // Mushroom tools + armor (stone-equivalent)
    // =========================================================================
    public static final RegistrySupplier<Item> MUSHROOM_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.MUSHROOM_SWORD,
            () -> new EdibleSwordItem(ModToolTiers.MUSHROOM, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.MUSHROOM, 3, -2.4f))
                    .food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> MUSHROOM_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.MUSHROOM_PICKAXE,
            () -> new EdiblePickaxeItem(ModToolTiers.MUSHROOM, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.MUSHROOM, 1, -2.8f))
                    .food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> MUSHROOM_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.MUSHROOM_SHOVEL,
            () -> new EdibleShovelItem(ModToolTiers.MUSHROOM, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.MUSHROOM, 1.5f, -3f))
                    .food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> MUSHROOM_AXE = ITEMS.register(GeneratedRegistrationIds.Items.MUSHROOM_AXE,
            () -> new EdibleAxeItem(ModToolTiers.MUSHROOM, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.MUSHROOM, 6, -3.2f))
                    .food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> MUSHROOM_HOE = ITEMS.register(GeneratedRegistrationIds.Items.MUSHROOM_HOE,
            () -> new EdibleHoeItem(ModToolTiers.MUSHROOM, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.MUSHROOM, 0, -3f))
                    .food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> MUSHROOM_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.MUSHROOM_HELMET,
            () -> new EdibleArmorItem(ModArmorMaterials.MUSHROOM_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(8))
                            .food(new FoodProperties.Builder().nutrition(10).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> MUSHROOM_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.MUSHROOM_CHESTPLATE,
            () -> new EdibleArmorItem(ModArmorMaterials.MUSHROOM_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(8))
                            .food(new FoodProperties.Builder().nutrition(14).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> MUSHROOM_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.MUSHROOM_LEGGINGS,
            () -> new EdibleArmorItem(ModArmorMaterials.MUSHROOM_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(8))
                            .food(new FoodProperties.Builder().nutrition(14).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> MUSHROOM_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.MUSHROOM_BOOTS,
            () -> new EdibleArmorItem(ModArmorMaterials.MUSHROOM_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(8))
                            .food(new FoodProperties.Builder().nutrition(8).saturationModifier(0.1f).build())));

    // =========================================================================
    // Pufferfish tools + armor (stone-equivalent)
    // =========================================================================
    public static final RegistrySupplier<Item> PUFFERFISH_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.PUFFERFISH_SWORD,
            () -> new EdibleSwordItem(ModToolTiers.PUFFERFISH, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.PUFFERFISH, 3, -2.4f))
                    .food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> PUFFERFISH_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.PUFFERFISH_PICKAXE,
            () -> new EdiblePickaxeItem(ModToolTiers.PUFFERFISH, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.PUFFERFISH, 1, -2.8f))
                    .food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> PUFFERFISH_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.PUFFERFISH_SHOVEL,
            () -> new EdibleShovelItem(ModToolTiers.PUFFERFISH, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.PUFFERFISH, 1.5f, -3f))
                    .food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> PUFFERFISH_AXE = ITEMS.register(GeneratedRegistrationIds.Items.PUFFERFISH_AXE,
            () -> new EdibleAxeItem(ModToolTiers.PUFFERFISH, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.PUFFERFISH, 6, -3.2f))
                    .food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> PUFFERFISH_HOE = ITEMS.register(GeneratedRegistrationIds.Items.PUFFERFISH_HOE,
            () -> new EdibleHoeItem(ModToolTiers.PUFFERFISH, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.PUFFERFISH, 0, -3f))
                    .food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> PUFFERFISH_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.PUFFERFISH_HELMET,
            () -> new EdibleArmorItem(ModArmorMaterials.PUFFERFISH_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(6))
                            .food(new FoodProperties.Builder().nutrition(10).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> PUFFERFISH_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.PUFFERFISH_CHESTPLATE,
            () -> new EdibleArmorItem(ModArmorMaterials.PUFFERFISH_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(6))
                            .food(new FoodProperties.Builder().nutrition(14).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> PUFFERFISH_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.PUFFERFISH_LEGGINGS,
            () -> new EdibleArmorItem(ModArmorMaterials.PUFFERFISH_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(6))
                            .food(new FoodProperties.Builder().nutrition(14).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> PUFFERFISH_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.PUFFERFISH_BOOTS,
            () -> new EdibleArmorItem(ModArmorMaterials.PUFFERFISH_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(6))
                            .food(new FoodProperties.Builder().nutrition(8).saturationModifier(0.1f).build())));

    // =========================================================================
    // Honey tools + armor (stone-equivalent)
    // =========================================================================
    public static final RegistrySupplier<Item> HONEY_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.HONEY_SWORD,
            () -> new EdibleSwordItem(ModToolTiers.HONEY, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.HONEY, 3, -2.4f))
                    .food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> HONEY_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.HONEY_PICKAXE,
            () -> new EdiblePickaxeItem(ModToolTiers.HONEY, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.HONEY, 1, -2.8f))
                    .food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> HONEY_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.HONEY_SHOVEL,
            () -> new EdibleShovelItem(ModToolTiers.HONEY, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.HONEY, 1.5f, -3f))
                    .food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> HONEY_AXE = ITEMS.register(GeneratedRegistrationIds.Items.HONEY_AXE,
            () -> new EdibleAxeItem(ModToolTiers.HONEY, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.HONEY, 6, -3.2f))
                    .food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> HONEY_HOE = ITEMS.register(GeneratedRegistrationIds.Items.HONEY_HOE,
            () -> new EdibleHoeItem(ModToolTiers.HONEY, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.HONEY, 0, -3f))
                    .food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> HONEY_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.HONEY_HELMET,
            () -> new EdibleArmorItem(ModArmorMaterials.HONEY_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(10))
                            .food(new FoodProperties.Builder().nutrition(10).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> HONEY_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.HONEY_CHESTPLATE,
            () -> new EdibleArmorItem(ModArmorMaterials.HONEY_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(10))
                            .food(new FoodProperties.Builder().nutrition(14).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> HONEY_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.HONEY_LEGGINGS,
            () -> new EdibleArmorItem(ModArmorMaterials.HONEY_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(10))
                            .food(new FoodProperties.Builder().nutrition(14).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> HONEY_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.HONEY_BOOTS,
            () -> new EdibleArmorItem(ModArmorMaterials.HONEY_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(10))
                            .food(new FoodProperties.Builder().nutrition(8).saturationModifier(0.1f).build())));

    // =========================================================================
    // Chorus Fruit tools + armor (iron-equivalent)
    // =========================================================================
    public static final RegistrySupplier<Item> CHORUS_FRUIT_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.CHORUS_FRUIT_SWORD,
            () -> new EdibleSwordItem(ModToolTiers.CHORUS_FRUIT, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.CHORUS_FRUIT, 3, -2.4f))
                    .food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> CHORUS_FRUIT_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.CHORUS_FRUIT_PICKAXE,
            () -> new EdiblePickaxeItem(ModToolTiers.CHORUS_FRUIT, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.CHORUS_FRUIT, 1, -2.8f))
                    .food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> CHORUS_FRUIT_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.CHORUS_FRUIT_SHOVEL,
            () -> new EdibleShovelItem(ModToolTiers.CHORUS_FRUIT, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.CHORUS_FRUIT, 1.5f, -3f))
                    .food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> CHORUS_FRUIT_AXE = ITEMS.register(GeneratedRegistrationIds.Items.CHORUS_FRUIT_AXE,
            () -> new EdibleAxeItem(ModToolTiers.CHORUS_FRUIT, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.CHORUS_FRUIT, 6, -3.2f))
                    .food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> CHORUS_FRUIT_HOE = ITEMS.register(GeneratedRegistrationIds.Items.CHORUS_FRUIT_HOE,
            () -> new EdibleHoeItem(ModToolTiers.CHORUS_FRUIT, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.CHORUS_FRUIT, 0, -3f))
                    .food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> CHORUS_FRUIT_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.CHORUS_FRUIT_HELMET,
            () -> new EdibleArmorItem(ModArmorMaterials.CHORUS_FRUIT_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(15))
                            .food(new FoodProperties.Builder().nutrition(10).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> CHORUS_FRUIT_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.CHORUS_FRUIT_CHESTPLATE,
            () -> new EdibleArmorItem(ModArmorMaterials.CHORUS_FRUIT_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(15))
                            .food(new FoodProperties.Builder().nutrition(14).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> CHORUS_FRUIT_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.CHORUS_FRUIT_LEGGINGS,
            () -> new EdibleArmorItem(ModArmorMaterials.CHORUS_FRUIT_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(15))
                            .food(new FoodProperties.Builder().nutrition(14).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> CHORUS_FRUIT_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.CHORUS_FRUIT_BOOTS,
            () -> new EdibleArmorItem(ModArmorMaterials.CHORUS_FRUIT_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(15))
                            .food(new FoodProperties.Builder().nutrition(8).saturationModifier(0.1f).build())));

    // =========================================================================
    // Golden Apple tools + armor (iron-equivalent, luxury)
    // =========================================================================
    public static final RegistrySupplier<Item> GOLDEN_APPLE_SWORD = ITEMS.register(GeneratedRegistrationIds.Items.GOLDEN_APPLE_SWORD,
            () -> new EdibleSwordItem(ModToolTiers.GOLDEN_APPLE, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.GOLDEN_APPLE, 3, -2.4f))
                    .food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> GOLDEN_APPLE_PICKAXE = ITEMS.register(GeneratedRegistrationIds.Items.GOLDEN_APPLE_PICKAXE,
            () -> new EdiblePickaxeItem(ModToolTiers.GOLDEN_APPLE, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.GOLDEN_APPLE, 1, -2.8f))
                    .food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> GOLDEN_APPLE_SHOVEL = ITEMS.register(GeneratedRegistrationIds.Items.GOLDEN_APPLE_SHOVEL,
            () -> new EdibleShovelItem(ModToolTiers.GOLDEN_APPLE, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.GOLDEN_APPLE, 1.5f, -3f))
                    .food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> GOLDEN_APPLE_AXE = ITEMS.register(GeneratedRegistrationIds.Items.GOLDEN_APPLE_AXE,
            () -> new EdibleAxeItem(ModToolTiers.GOLDEN_APPLE, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.GOLDEN_APPLE, 6, -3.2f))
                    .food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> GOLDEN_APPLE_HOE = ITEMS.register(GeneratedRegistrationIds.Items.GOLDEN_APPLE_HOE,
            () -> new EdibleHoeItem(ModToolTiers.GOLDEN_APPLE, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.GOLDEN_APPLE, 0, -3f))
                    .food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> GOLDEN_APPLE_HELMET = ITEMS.register(GeneratedRegistrationIds.Items.GOLDEN_APPLE_HELMET,
            () -> new EdibleArmorItem(ModArmorMaterials.GOLDEN_APPLE_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(18))
                            .food(new FoodProperties.Builder().nutrition(10).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> GOLDEN_APPLE_CHESTPLATE = ITEMS.register(GeneratedRegistrationIds.Items.GOLDEN_APPLE_CHESTPLATE,
            () -> new EdibleArmorItem(ModArmorMaterials.GOLDEN_APPLE_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(18))
                            .food(new FoodProperties.Builder().nutrition(14).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> GOLDEN_APPLE_LEGGINGS = ITEMS.register(GeneratedRegistrationIds.Items.GOLDEN_APPLE_LEGGINGS,
            () -> new EdibleArmorItem(ModArmorMaterials.GOLDEN_APPLE_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(18))
                            .food(new FoodProperties.Builder().nutrition(14).saturationModifier(0.1f).build())));
    public static final RegistrySupplier<Item> GOLDEN_APPLE_BOOTS = ITEMS.register(GeneratedRegistrationIds.Items.GOLDEN_APPLE_BOOTS,
            () -> new EdibleArmorItem(ModArmorMaterials.GOLDEN_APPLE_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(18))
                            .food(new FoodProperties.Builder().nutrition(8).saturationModifier(0.1f).build())));

    public static void register() {
        ITEMS.register();
    }
}
