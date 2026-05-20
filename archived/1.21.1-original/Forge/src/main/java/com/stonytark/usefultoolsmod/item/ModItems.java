package com.stonytark.usefultoolsmod.item;

import com.stonytark.usefultoolsmod.UsefultoolsMod;
import com.stonytark.usefultoolsmod.entity.ModEntities;
import com.stonytark.usefultoolsmod.item.custom.*;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, UsefultoolsMod.MOD_ID);

    public static final RegistryObject<Item> RGOLD = ITEMS.register("rgold",
            () -> new Item(new Item.Properties().stacksTo(64)));

    public static final RegistryObject<Item> RAW_RGOLD = ITEMS.register("raw_rgold",
            () -> new Item(new Item.Properties().stacksTo(64)));

    public static final RegistryObject<Item> OBSHARD = ITEMS.register("obshard",
            () -> new Item(new Item.Properties().stacksTo(64)));

    public static final RegistryObject<Item> SEM = ITEMS.register("sem",
            () -> new Item(new Item.Properties().stacksTo(64)));

    public static final RegistryObject<Item> OBINGOT = ITEMS.register("obingot",
            () -> new Item(new Item.Properties().stacksTo(64)));

    public static final RegistryObject<Item> GRENADE = ITEMS.register("grenade",
            () -> new Grenade(new Item.Properties().stacksTo(16)));

    public static final RegistryObject<Item> HRED = ITEMS.register("hred",
            () -> new Item(new Item.Properties().stacksTo(64)));

    public static final RegistryObject<Item> HGLOW = ITEMS.register("hglow",
            () -> new Item(new Item.Properties().stacksTo(64)));

    public static final RegistryObject<Item> RLAPIS = ITEMS.register("rlapis",
            () -> new Item(new Item.Properties().stacksTo(64)));

    public static final RegistryObject<Item> DYNAMITE = ITEMS.register("dynamite",
                    () -> new Dynamite(new Item.Properties()
                            .stacksTo(16)
                            .fireResistant()  // optional
                    )
            );

    public static final RegistryObject<Item> REMERALD_SWORD = ITEMS.register("remerald_sword",
            () -> new SwordItem(ModToolTiers.REMERALD, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.REMERALD, 3, -2.4f))));
    public static final RegistryObject<Item> REMERALD_PICKAXE = ITEMS.register("remerald_pickaxe",
            () -> new PickaxeItem(ModToolTiers.REMERALD, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.REMERALD, 1, -2.8f))));
    public static final RegistryObject<Item> REMERALD_SHOVEL = ITEMS.register("remerald_shovel",
            () -> new ShovelItem(ModToolTiers.REMERALD, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.REMERALD, 1.5f, -3f))));
    public static final RegistryObject<Item> REMERALD_AXE = ITEMS.register("remerald_axe",
            () -> new AxeItem(ModToolTiers.REMERALD, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.REMERALD, 6, -3.2f))));
    public static final RegistryObject<Item> REMERALD_HOE = ITEMS.register("remerald_hoe",
            () -> new HoeItem(ModToolTiers.REMERALD, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.REMERALD, 0, -3f))));

    public static final RegistryObject<Item> PEMERALD_SWORD = ITEMS.register("pemerald_sword",
            () -> new SwordItem(ModToolTiers.PEMERALD, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.PEMERALD, 3, -2.4f))));
    public static final RegistryObject<Item> PEMERALD_PICKAXE = ITEMS.register("pemerald_pickaxe",
            () -> new PickaxeItem(ModToolTiers.PEMERALD, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.PEMERALD, 1, -2.8f))));
    public static final RegistryObject<Item> PEMERALD_SHOVEL = ITEMS.register("pemerald_shovel",
            () -> new ShovelItem(ModToolTiers.PEMERALD, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.PEMERALD, 1.5f, -3f))));
    public static final RegistryObject<Item> PEMERALD_AXE = ITEMS.register("pemerald_axe",
            () -> new AxeItem(ModToolTiers.PEMERALD, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.PEMERALD, 6, -3.2f))));
    public static final RegistryObject<Item> PEMERALD_HOE = ITEMS.register("pemerald_hoe",
            () -> new HoeItem(ModToolTiers.PEMERALD, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.PEMERALD, 0, -3f))));

    public static final RegistryObject<Item> ROBSIDIAN_SWORD = ITEMS.register("robsidian_sword",
            () -> new SwordItem(ModToolTiers.ROBSIDIAN, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.ROBSIDIAN, 3, -2.4f))));
    public static final RegistryObject<Item> ROBSIDIAN_PICKAXE = ITEMS.register("robsidian_pickaxe",
            () -> new PickaxeItem(ModToolTiers.ROBSIDIAN, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.ROBSIDIAN, 1, -2.8f))));
    public static final RegistryObject<Item> ROBSIDIAN_SHOVEL = ITEMS.register("robsidian_shovel",
            () -> new ShovelItem(ModToolTiers.ROBSIDIAN, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.ROBSIDIAN, 1.5f, -3f))));
    public static final RegistryObject<Item> ROBSIDIAN_AXE = ITEMS.register("robsidian_axe",
            () -> new AxeItem(ModToolTiers.ROBSIDIAN, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.ROBSIDIAN, 6, -3.2f))));
    public static final RegistryObject<Item> ROBSIDIAN_HOE = ITEMS.register("robsidian_hoe",
            () -> new HoeItem(ModToolTiers.ROBSIDIAN, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.ROBSIDIAN, 0, -3f))));

    public static final RegistryObject<Item> POBSIDIAN_SWORD = ITEMS.register("pobsidian_sword",
            () -> new SwordItem(ModToolTiers.POBSIDIAN, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.POBSIDIAN, 3, -2.4f))));
    public static final RegistryObject<Item> POBSIDIAN_PICKAXE = ITEMS.register("pobsidian_pickaxe",
            () -> new PickaxeItem(ModToolTiers.POBSIDIAN, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.POBSIDIAN, 1, -2.8f))));
    public static final RegistryObject<Item> POBSIDIAN_SHOVEL = ITEMS.register("pobsidian_shovel",
            () -> new ShovelItem(ModToolTiers.POBSIDIAN, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.POBSIDIAN, 1.5f, -3f))));
    public static final RegistryObject<Item> POBSIDIAN_AXE = ITEMS.register("pobsidian_axe",
            () -> new AxeItem(ModToolTiers.POBSIDIAN, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.POBSIDIAN, 6, -3.2f))));
    public static final RegistryObject<Item> POBSIDIAN_HOE = ITEMS.register("pobsidian_hoe",
            () -> new HoeItem(ModToolTiers.POBSIDIAN, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.POBSIDIAN, 0, -3f))));

    public static final RegistryObject<Item> OVERPOWER_SWORD = ITEMS.register("overpower_sword",
            () -> new SwordItem(ModToolTiers.OVERPOWER, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.OVERPOWER, 3, -2.4f))));
    public static final RegistryObject<Item> OVERPOWER_PICKAXE = ITEMS.register("overpower_pickaxe",
            () -> new PickaxeItem(ModToolTiers.OVERPOWER, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.OVERPOWER, 1, -2.8f))));
    public static final RegistryObject<Item> OVERPOWER_SHOVEL = ITEMS.register("overpower_shovel",
            () -> new ShovelItem(ModToolTiers.OVERPOWER, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.OVERPOWER, 1.5f, -3f))));
    public static final RegistryObject<Item> OVERPOWER_AXE = ITEMS.register("overpower_axe",
            () -> new AxeItem(ModToolTiers.OVERPOWER, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.OVERPOWER, 6, -3.2f))));


    public static final RegistryObject<Item> HREDSTONE_SWORD = ITEMS.register("hredstone_sword",
            () -> new SwordItem(ModToolTiers.HREDSTONE, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.HREDSTONE, 3, -2.4f))));
    public static final RegistryObject<Item> HREDSTONE_PICKAXE = ITEMS.register("hredstone_pickaxe",
            () -> new PickaxeItem(ModToolTiers.HREDSTONE, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.HREDSTONE, 1, -2.8f))));
    public static final RegistryObject<Item> HREDSTONE_SHOVEL = ITEMS.register("hredstone_shovel",
            () -> new ShovelItem(ModToolTiers.HREDSTONE, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.HREDSTONE, 1.5f, -3f))));
    public static final RegistryObject<Item> HREDSTONE_AXE = ITEMS.register("hredstone_axe",
            () -> new AxeItem(ModToolTiers.HREDSTONE, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.HREDSTONE, 6, -3.2f))));
    public static final RegistryObject<Item> HREDSTONE_HOE = ITEMS.register("hredstone_hoe",
            () -> new HoeItem(ModToolTiers.HREDSTONE, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.HREDSTONE, 0, -3f))));

    public static final RegistryObject<Item> HGLOWSTONE_SWORD = ITEMS.register("hglowstone_sword",
            () -> new SwordItem(ModToolTiers.HGLOWSTONE, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.HGLOWSTONE, 3, -2.4f))));
    public static final RegistryObject<Item> HGLOWSTONE_PICKAXE = ITEMS.register("hglowstone_pickaxe",
            () -> new PickaxeItem(ModToolTiers.HGLOWSTONE, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.HGLOWSTONE, 1, -2.8f))));
    public static final RegistryObject<Item> HGLOWSTONE_SHOVEL = ITEMS.register("hglowstone_shovel",
            () -> new ShovelItem(ModToolTiers.HGLOWSTONE, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.HGLOWSTONE, 1.5f, -3f))));
    public static final RegistryObject<Item> HGLOWSTONE_AXE = ITEMS.register("hglowstone_axe",
            () -> new AxeItem(ModToolTiers.HGLOWSTONE, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.HGLOWSTONE, 6, -3.2f))));
    public static final RegistryObject<Item> HGLOWSTONE_HOE = ITEMS.register("hglowstone_hoe",
            () -> new HoeItem(ModToolTiers.HGLOWSTONE, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.HGLOWSTONE, 0, -3f))));

    public static final RegistryObject<Item> RGOLD_SWORD = ITEMS.register("rgold_sword",
            () -> new SwordItem(ModToolTiers.RGOLD, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.RGOLD, 3, -2.4f))));
    public static final RegistryObject<Item> RGOLD_PICKAXE = ITEMS.register("rgold_pickaxe",
            () -> new PickaxeItem(ModToolTiers.RGOLD, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.RGOLD, 1, -2.8f))));
    public static final RegistryObject<Item> RGOLD_SHOVEL = ITEMS.register("rgold_shovel",
            () -> new ShovelItem(ModToolTiers.RGOLD, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.RGOLD, 1.5f, -3f))));
    public static final RegistryObject<Item> RGOLD_AXE = ITEMS.register("rgold_axe",
            () -> new AxeItem(ModToolTiers.RGOLD, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.RGOLD, 6, -3.2f))));
    public static final RegistryObject<Item> RGOLD_HOE = ITEMS.register("rgold_hoe",
            () -> new HoeItem(ModToolTiers.RGOLD, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.RGOLD, 0, -3f))));

    public static final RegistryObject<Item> RLAPIS_SWORD = ITEMS.register("rlapis_sword",
            () -> new SwordItem(ModToolTiers.RLAPIS, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.RLAPIS, 3, -2.4f))));
    public static final RegistryObject<Item> RLAPIS_PICKAXE = ITEMS.register("rlapis_pickaxe",
            () -> new PickaxeItem(ModToolTiers.RLAPIS, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.RLAPIS, 1, -2.8f))));
    public static final RegistryObject<Item> RLAPIS_SHOVEL = ITEMS.register("rlapis_shovel",
            () -> new ShovelItem(ModToolTiers.RLAPIS, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.RLAPIS, 1.5f, -3f))));
    public static final RegistryObject<Item> RLAPIS_AXE = ITEMS.register("rlapis_axe",
            () -> new AxeItem(ModToolTiers.RLAPIS, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.RLAPIS, 6, -3.2f))));
    public static final RegistryObject<Item> RLAPIS_HOE = ITEMS.register("rlapis_hoe",
            () -> new HoeItem(ModToolTiers.RLAPIS, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.RLAPIS, 0, -3f))));

    public static final RegistryObject<Item> EMERALD_HELMET = ITEMS.register("emerald_helmet",
            () -> new ArmorItem(ModArmorMaterials.EMERALD_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(33))));
    public static final RegistryObject<Item> EMERALD_CHESTPLATE = ITEMS.register("emerald_chestplate",
            () -> new ArmorItem(ModArmorMaterials.EMERALD_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(33))));
    public static final RegistryObject<Item> EMERALD_LEGGINGS = ITEMS.register("emerald_leggings",
            () -> new ArmorItem(ModArmorMaterials.EMERALD_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(33))));
    public static final RegistryObject<Item> EMERALD_BOOTS = ITEMS.register("emerald_boots",
            () -> new ArmorItem(ModArmorMaterials.EMERALD_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(33))));

    public static final RegistryObject<Item> HRED_HELMET = ITEMS.register("hred_helmet",
            () -> new ArmorItem(ModArmorMaterials.HRED_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(20))));
    public static final RegistryObject<Item> HRED_CHESTPLATE = ITEMS.register("hred_chestplate",
            () -> new ArmorItem(ModArmorMaterials.HRED_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(20))));
    public static final RegistryObject<Item> HRED_LEGGINGS = ITEMS.register("hred_leggings",
            () -> new ArmorItem(ModArmorMaterials.HRED_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(20))));
    public static final RegistryObject<Item> HRED_BOOTS = ITEMS.register("hred_boots",
            () -> new ArmorItem(ModArmorMaterials.HRED_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(20))));

    public static final RegistryObject<Item> HGLOW_HELMET = ITEMS.register("hglow_helmet",
            () -> new ArmorItem(ModArmorMaterials.HGLOW_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(18))));
    public static final RegistryObject<Item> HGLOW_CHESTPLATE = ITEMS.register("hglow_chestplate",
            () -> new ArmorItem(ModArmorMaterials.HGLOW_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(18))));
    public static final RegistryObject<Item> HGLOW_LEGGINGS = ITEMS.register("hglow_leggings",
            () -> new ArmorItem(ModArmorMaterials.HGLOW_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(18))));
    public static final RegistryObject<Item> HGLOW_BOOTS = ITEMS.register("hglow_boots",
            () -> new ArmorItem(ModArmorMaterials.HGLOW_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(18))));

    public static final RegistryObject<Item> OBSIDIAN_HELMET = ITEMS.register("obsidian_helmet",
            () -> new ArmorItem(ModArmorMaterials.OBSIDIAN_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(45))));
    public static final RegistryObject<Item> OBSIDIAN_CHESTPLATE = ITEMS.register("obsidian_chestplate",
            () -> new ArmorItem(ModArmorMaterials.OBSIDIAN_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(45))));
    public static final RegistryObject<Item> OBSIDIAN_LEGGINGS = ITEMS.register("obsidian_leggings",
            () -> new ArmorItem(ModArmorMaterials.OBSIDIAN_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(45))));
    public static final RegistryObject<Item> OBSIDIAN_BOOTS = ITEMS.register("obsidian_boots",
            () -> new ArmorItem(ModArmorMaterials.OBSIDIAN_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(45))));

    public static final RegistryObject<Item> RGOLD_HELMET = ITEMS.register("rgold_helmet",
            () -> new ArmorItem(ModArmorMaterials.RGOLD_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(18))));
    public static final RegistryObject<Item> RGOLD_CHESTPLATE = ITEMS.register("rgold_chestplate",
            () -> new ArmorItem(ModArmorMaterials.RGOLD_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(18))));
    public static final RegistryObject<Item> RGOLD_LEGGINGS = ITEMS.register("rgold_leggings",
            () -> new ArmorItem(ModArmorMaterials.RGOLD_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(18))));
    public static final RegistryObject<Item> RGOLD_BOOTS = ITEMS.register("rgold_boots",
            () -> new ArmorItem(ModArmorMaterials.RGOLD_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(18))));

    public static final RegistryObject<Item> RLAPIS_HELMET = ITEMS.register("rlapis_helmet",
            () -> new ArmorItem(ModArmorMaterials.RLAPIS_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(17))));
    public static final RegistryObject<Item> RLAPIS_CHESTPLATE = ITEMS.register("rlapis_chestplate",
            () -> new ArmorItem(ModArmorMaterials.RLAPIS_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(17))));
    public static final RegistryObject<Item> RLAPIS_LEGGINGS = ITEMS.register("rlapis_leggings",
            () -> new ArmorItem(ModArmorMaterials.RLAPIS_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(17))));
    public static final RegistryObject<Item> RLAPIS_BOOTS = ITEMS.register("rlapis_boots",
            () -> new ArmorItem(ModArmorMaterials.RLAPIS_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(17))));

    public static final RegistryObject<Item> OVERPOWER_HELMET = ITEMS.register("overpower_helmet",
            () -> new ModArmorItem(ModArmorMaterials.OVERPOWER_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(100))));
    public static final RegistryObject<Item> OVERPOWER_CHESTPLATE = ITEMS.register("overpower_chestplate",
            () -> new ArmorItem(ModArmorMaterials.OVERPOWER_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(100))));
    public static final RegistryObject<Item> OVERPOWER_LEGGINGS = ITEMS.register("overpower_leggings",
            () -> new ArmorItem(ModArmorMaterials.OVERPOWER_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(100))));
    public static final RegistryObject<Item> OVERPOWER_BOOTS = ITEMS.register("overpower_boots",
            () -> new ArmorItem(ModArmorMaterials.OVERPOWER_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(100))));

    public static final RegistryObject<Item> GHOST_SPAWN_EGG = ITEMS.register("ghost_spawn_egg",
            () -> new ForgeSpawnEggItem(() -> ModEntities.GHOST.get(), 0xFFFFFF, 0x999999, new Item.Properties()));

    public static final RegistryObject<Item> ECTOPLASM = ITEMS.register("ectoplasm",
            () -> new Item(new Item.Properties().stacksTo(64)));

    // -------------------------------------------------------------------------
    // Rough Ectoplasm tools (raw ectoplasm, stone-tier equivalent)
    // -------------------------------------------------------------------------
    public static final RegistryObject<Item> RECTO_SWORD = ITEMS.register("recto_sword",
            () -> new EctoSwordItem(ModToolTiers.RECTO, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.RECTO, 3, -2.4f))));

    public static final RegistryObject<Item> RECTO_PICKAXE = ITEMS.register("recto_pickaxe",
            () -> new EctoPickaxeItem(ModToolTiers.RECTO, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.RECTO, 1, -2.8f))));

    public static final RegistryObject<Item> RECTO_SHOVEL = ITEMS.register("recto_shovel",
            () -> new EctoShovelItem(ModToolTiers.RECTO, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.RECTO, 1.5f, -3f))));

    public static final RegistryObject<Item> RECTO_AXE = ITEMS.register("recto_axe",
            () -> new EctoAxeItem(ModToolTiers.RECTO, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.RECTO, 6, -3.2f))));

    public static final RegistryObject<Item> RECTO_HOE = ITEMS.register("recto_hoe",
            () -> new EctoHoeItem(ModToolTiers.RECTO, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.RECTO, 0, -3f))));

    // -------------------------------------------------------------------------
    // Refined Ectoplasm + Ectoplasm tools + armor
    // -------------------------------------------------------------------------
    public static final RegistryObject<Item> REFINED_ECTOPLASM = ITEMS.register("refined_ectoplasm",
            () -> new Item(new Item.Properties().stacksTo(64)));

    public static final RegistryObject<Item> ECTO_SWORD = ITEMS.register("ecto_sword",
            () -> new EctoSwordItem(ModToolTiers.ECTOPLASM, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.ECTOPLASM, 3, -2.4f))));

    public static final RegistryObject<Item> ECTO_PICKAXE = ITEMS.register("ecto_pickaxe",
            () -> new EctoPickaxeItem(ModToolTiers.ECTOPLASM, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.ECTOPLASM, 1, -2.8f))));

    public static final RegistryObject<Item> ECTO_SHOVEL = ITEMS.register("ecto_shovel",
            () -> new EctoShovelItem(ModToolTiers.ECTOPLASM, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.ECTOPLASM, 1.5f, -3f))));

    public static final RegistryObject<Item> ECTO_AXE = ITEMS.register("ecto_axe",
            () -> new EctoAxeItem(ModToolTiers.ECTOPLASM, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.ECTOPLASM, 6, -3.1f))));

    public static final RegistryObject<Item> ECTO_HOE = ITEMS.register("ecto_hoe",
            () -> new EctoHoeItem(ModToolTiers.ECTOPLASM, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.ECTOPLASM, 0, -3f))));

    public static final RegistryObject<Item> ECTO_HELMET = ITEMS.register("ecto_helmet",
            () -> new ArmorItem(ModArmorMaterials.ECTO_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(16))));

    public static final RegistryObject<Item> ECTO_CHESTPLATE = ITEMS.register("ecto_chestplate",
            () -> new ArmorItem(ModArmorMaterials.ECTO_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(16))));

    public static final RegistryObject<Item> ECTO_LEGGINGS = ITEMS.register("ecto_leggings",
            () -> new ArmorItem(ModArmorMaterials.ECTO_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(16))));

    public static final RegistryObject<Item> ECTO_BOOTS = ITEMS.register("ecto_boots",
            () -> new ArmorItem(ModArmorMaterials.ECTO_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(16))));

    // -------------------------------------------------------------------------
    // Coal material items
    // -------------------------------------------------------------------------
    public static final RegistryObject<Item> COAL_DUST = ITEMS.register("coal_dust",
            () -> new Item(new Item.Properties().stacksTo(64)));

    public static final RegistryObject<Item> HARDENED_COAL = ITEMS.register("hardened_coal",
            () -> new Item(new Item.Properties().stacksTo(64)));

    // -------------------------------------------------------------------------
    // Coal tools  (wood mining level, durability ~120, flammable)
    // -------------------------------------------------------------------------
    public static final RegistryObject<Item> COAL_SWORD = ITEMS.register("coal_sword",
            () -> new CoalSwordItem(ModToolTiers.COAL_TOOL, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.COAL_TOOL, 2, -2.4f))));

    public static final RegistryObject<Item> COAL_PICKAXE = ITEMS.register("coal_pickaxe",
            () -> new CoalPickaxeItem(ModToolTiers.COAL_TOOL, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.COAL_TOOL, 1, -2.8f))));

    public static final RegistryObject<Item> COAL_SHOVEL = ITEMS.register("coal_shovel",
            () -> new CoalShovelItem(ModToolTiers.COAL_TOOL, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.COAL_TOOL, 1.5f, -3f))));

    public static final RegistryObject<Item> COAL_AXE = ITEMS.register("coal_axe",
            () -> new CoalAxeItem(ModToolTiers.COAL_TOOL, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.COAL_TOOL, 5, -3.2f))));

    public static final RegistryObject<Item> COAL_HOE = ITEMS.register("coal_hoe",
            () -> new CoalHoeItem(ModToolTiers.COAL_TOOL, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.COAL_TOOL, 0, -3f))));

    // -------------------------------------------------------------------------
    // Coal armor  (between leather and chainmail, flammable)
    // -------------------------------------------------------------------------
    public static final RegistryObject<Item> COAL_HELMET = ITEMS.register("coal_helmet",
            () -> new CoalArmorItem(ModArmorMaterials.COAL_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(8))));

    public static final RegistryObject<Item> COAL_CHESTPLATE = ITEMS.register("coal_chestplate",
            () -> new CoalArmorItem(ModArmorMaterials.COAL_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(8))));

    public static final RegistryObject<Item> COAL_LEGGINGS = ITEMS.register("coal_leggings",
            () -> new CoalArmorItem(ModArmorMaterials.COAL_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(8))));

    public static final RegistryObject<Item> COAL_BOOTS = ITEMS.register("coal_boots",
            () -> new CoalArmorItem(ModArmorMaterials.COAL_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(8))));



    // -------------------------------------------------------------------------
    // Raw metal rough tool sets
    // -------------------------------------------------------------------------

    // Rough Raw Gold (wood mining level — fast, fragile)
    public static final RegistryObject<Item> RRAW_GOLD_SWORD = ITEMS.register("rraw_gold_sword",
            () -> new SwordItem(ModToolTiers.RRAW_GOLD, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.RRAW_GOLD, 3, -2.4f))));
    public static final RegistryObject<Item> RRAW_GOLD_PICKAXE = ITEMS.register("rraw_gold_pickaxe",
            () -> new PickaxeItem(ModToolTiers.RRAW_GOLD, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.RRAW_GOLD, 1, -2.8f))));
    public static final RegistryObject<Item> RRAW_GOLD_SHOVEL = ITEMS.register("rraw_gold_shovel",
            () -> new ShovelItem(ModToolTiers.RRAW_GOLD, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.RRAW_GOLD, 1.5f, -3f))));
    public static final RegistryObject<Item> RRAW_GOLD_AXE = ITEMS.register("rraw_gold_axe",
            () -> new AxeItem(ModToolTiers.RRAW_GOLD, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.RRAW_GOLD, 6, -3.2f))));
    public static final RegistryObject<Item> RRAW_GOLD_HOE = ITEMS.register("rraw_gold_hoe",
            () -> new HoeItem(ModToolTiers.RRAW_GOLD, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.RRAW_GOLD, 0, -3f))));

    // Rough Raw Copper (stone mining level)
    public static final RegistryObject<Item> RRAW_COPPER_SWORD = ITEMS.register("rraw_copper_sword",
            () -> new SwordItem(ModToolTiers.RRAW_COPPER, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.RRAW_COPPER, 3, -2.4f))));
    public static final RegistryObject<Item> RRAW_COPPER_PICKAXE = ITEMS.register("rraw_copper_pickaxe",
            () -> new PickaxeItem(ModToolTiers.RRAW_COPPER, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.RRAW_COPPER, 1, -2.8f))));
    public static final RegistryObject<Item> RRAW_COPPER_SHOVEL = ITEMS.register("rraw_copper_shovel",
            () -> new ShovelItem(ModToolTiers.RRAW_COPPER, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.RRAW_COPPER, 1.5f, -3f))));
    public static final RegistryObject<Item> RRAW_COPPER_AXE = ITEMS.register("rraw_copper_axe",
            () -> new AxeItem(ModToolTiers.RRAW_COPPER, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.RRAW_COPPER, 6, -3.2f))));
    public static final RegistryObject<Item> RRAW_COPPER_HOE = ITEMS.register("rraw_copper_hoe",
            () -> new HoeItem(ModToolTiers.RRAW_COPPER, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.RRAW_COPPER, 0, -3f))));

    // Rough Raw Iron (iron mining level)
    public static final RegistryObject<Item> RRAW_IRON_SWORD = ITEMS.register("rraw_iron_sword",
            () -> new SwordItem(ModToolTiers.RRAW_IRON, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.RRAW_IRON, 3, -2.4f))));
    public static final RegistryObject<Item> RRAW_IRON_PICKAXE = ITEMS.register("rraw_iron_pickaxe",
            () -> new PickaxeItem(ModToolTiers.RRAW_IRON, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.RRAW_IRON, 1, -2.8f))));
    public static final RegistryObject<Item> RRAW_IRON_SHOVEL = ITEMS.register("rraw_iron_shovel",
            () -> new ShovelItem(ModToolTiers.RRAW_IRON, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.RRAW_IRON, 1.5f, -3f))));
    public static final RegistryObject<Item> RRAW_IRON_AXE = ITEMS.register("rraw_iron_axe",
            () -> new AxeItem(ModToolTiers.RRAW_IRON, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.RRAW_IRON, 6, -3.2f))));
    public static final RegistryObject<Item> RRAW_IRON_HOE = ITEMS.register("rraw_iron_hoe",
            () -> new HoeItem(ModToolTiers.RRAW_IRON, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.RRAW_IRON, 0, -3f))));

    // Rough Raw Ferrous Gold (iron mining level)
    public static final RegistryObject<Item> RRAW_RGOLD_SWORD = ITEMS.register("rraw_rgold_sword",
            () -> new SwordItem(ModToolTiers.RRAW_RGOLD, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.RRAW_RGOLD, 3, -2.4f))));
    public static final RegistryObject<Item> RRAW_RGOLD_PICKAXE = ITEMS.register("rraw_rgold_pickaxe",
            () -> new PickaxeItem(ModToolTiers.RRAW_RGOLD, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.RRAW_RGOLD, 1, -2.8f))));
    public static final RegistryObject<Item> RRAW_RGOLD_SHOVEL = ITEMS.register("rraw_rgold_shovel",
            () -> new ShovelItem(ModToolTiers.RRAW_RGOLD, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.RRAW_RGOLD, 1.5f, -3f))));
    public static final RegistryObject<Item> RRAW_RGOLD_AXE = ITEMS.register("rraw_rgold_axe",
            () -> new AxeItem(ModToolTiers.RRAW_RGOLD, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.RRAW_RGOLD, 6, -3.2f))));
    public static final RegistryObject<Item> RRAW_RGOLD_HOE = ITEMS.register("rraw_rgold_hoe",
            () -> new HoeItem(ModToolTiers.RRAW_RGOLD, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.RRAW_RGOLD, 0, -3f))));

    // Rough Netherite Scrap (diamond mining level)
    public static final RegistryObject<Item> RSCRAP_SWORD = ITEMS.register("rscrap_sword",
            () -> new SwordItem(ModToolTiers.RSCRAP, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.RSCRAP, 3, -2.4f))));
    public static final RegistryObject<Item> RSCRAP_PICKAXE = ITEMS.register("rscrap_pickaxe",
            () -> new PickaxeItem(ModToolTiers.RSCRAP, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.RSCRAP, 1, -2.8f))));
    public static final RegistryObject<Item> RSCRAP_SHOVEL = ITEMS.register("rscrap_shovel",
            () -> new ShovelItem(ModToolTiers.RSCRAP, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.RSCRAP, 1.5f, -3f))));
    public static final RegistryObject<Item> RSCRAP_AXE = ITEMS.register("rscrap_axe",
            () -> new AxeItem(ModToolTiers.RSCRAP, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.RSCRAP, 6, -3.2f))));
    public static final RegistryObject<Item> RSCRAP_HOE = ITEMS.register("rscrap_hoe",
            () -> new HoeItem(ModToolTiers.RSCRAP, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.RSCRAP, 0, -3f))));

    // -------------------------------------------------------------------------
    // Crystal / element material items
    // -------------------------------------------------------------------------

    public static final RegistryObject<Item> CALCIFIED_AMETHYST = ITEMS.register("calcified_amethyst",
            () -> new Item(new Item.Properties().stacksTo(64)));

    public static final RegistryObject<Item> GLACIAL_SHARD = ITEMS.register("glacial_shard",
            () -> new Item(new Item.Properties().stacksTo(64)));

    public static final RegistryObject<Item> POLISHED_QUARTZ = ITEMS.register("polished_quartz",
            () -> new Item(new Item.Properties().stacksTo(64)));

    public static final RegistryObject<Item> POLISHED_PRISMARINE = ITEMS.register("polished_prismarine",
            () -> new Item(new Item.Properties().stacksTo(64)));

    // -------------------------------------------------------------------------
    // Rough Amethyst tools (iron mining level)
    // -------------------------------------------------------------------------
    public static final RegistryObject<Item> RAMETHYST_SWORD = ITEMS.register("ramethyst_sword",
            () -> new SwordItem(ModToolTiers.RAMETHYST, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.RAMETHYST, 3, -2.4f))));
    public static final RegistryObject<Item> RAMETHYST_PICKAXE = ITEMS.register("ramethyst_pickaxe",
            () -> new PickaxeItem(ModToolTiers.RAMETHYST, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.RAMETHYST, 1, -2.8f))));
    public static final RegistryObject<Item> RAMETHYST_SHOVEL = ITEMS.register("ramethyst_shovel",
            () -> new ShovelItem(ModToolTiers.RAMETHYST, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.RAMETHYST, 1.5f, -3f))));
    public static final RegistryObject<Item> RAMETHYST_AXE = ITEMS.register("ramethyst_axe",
            () -> new AxeItem(ModToolTiers.RAMETHYST, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.RAMETHYST, 6, -3.2f))));
    public static final RegistryObject<Item> RAMETHYST_HOE = ITEMS.register("ramethyst_hoe",
            () -> new HoeItem(ModToolTiers.RAMETHYST, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.RAMETHYST, 0, -3f))));

    // -------------------------------------------------------------------------
    // Snow tools (stone mining level)
    // -------------------------------------------------------------------------
    public static final RegistryObject<Item> SNOW_SWORD = ITEMS.register("snow_sword",
            () -> new SwordItem(ModToolTiers.SNOW_TOOL, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.SNOW_TOOL, 3, -2.4f))));
    public static final RegistryObject<Item> SNOW_PICKAXE = ITEMS.register("snow_pickaxe",
            () -> new PickaxeItem(ModToolTiers.SNOW_TOOL, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.SNOW_TOOL, 1, -2.8f))));
    public static final RegistryObject<Item> SNOW_SHOVEL = ITEMS.register("snow_shovel",
            () -> new ShovelItem(ModToolTiers.SNOW_TOOL, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.SNOW_TOOL, 1.5f, -3f))));
    public static final RegistryObject<Item> SNOW_AXE = ITEMS.register("snow_axe",
            () -> new AxeItem(ModToolTiers.SNOW_TOOL, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.SNOW_TOOL, 6, -3.2f))));
    public static final RegistryObject<Item> SNOW_HOE = ITEMS.register("snow_hoe",
            () -> new HoeItem(ModToolTiers.SNOW_TOOL, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.SNOW_TOOL, 0, -3f))));

    // -------------------------------------------------------------------------
    // Rough Quartz tools (iron mining level)
    // -------------------------------------------------------------------------
    public static final RegistryObject<Item> RQUARTZ_SWORD = ITEMS.register("rquartz_sword",
            () -> new SwordItem(ModToolTiers.RQUARTZ, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.RQUARTZ, 3, -2.4f))));
    public static final RegistryObject<Item> RQUARTZ_PICKAXE = ITEMS.register("rquartz_pickaxe",
            () -> new PickaxeItem(ModToolTiers.RQUARTZ, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.RQUARTZ, 1, -2.8f))));
    public static final RegistryObject<Item> RQUARTZ_SHOVEL = ITEMS.register("rquartz_shovel",
            () -> new ShovelItem(ModToolTiers.RQUARTZ, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.RQUARTZ, 1.5f, -3f))));
    public static final RegistryObject<Item> RQUARTZ_AXE = ITEMS.register("rquartz_axe",
            () -> new AxeItem(ModToolTiers.RQUARTZ, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.RQUARTZ, 6, -3.2f))));
    public static final RegistryObject<Item> RQUARTZ_HOE = ITEMS.register("rquartz_hoe",
            () -> new HoeItem(ModToolTiers.RQUARTZ, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.RQUARTZ, 0, -3f))));

    // -------------------------------------------------------------------------
    // Rough Prismarine tools (iron mining level)
    // -------------------------------------------------------------------------
    public static final RegistryObject<Item> RPRISM_SWORD = ITEMS.register("rprism_sword",
            () -> new SwordItem(ModToolTiers.RPRISM, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.RPRISM, 3, -2.4f))));
    public static final RegistryObject<Item> RPRISM_PICKAXE = ITEMS.register("rprism_pickaxe",
            () -> new PickaxeItem(ModToolTiers.RPRISM, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.RPRISM, 1, -2.8f))));
    public static final RegistryObject<Item> RPRISM_SHOVEL = ITEMS.register("rprism_shovel",
            () -> new ShovelItem(ModToolTiers.RPRISM, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.RPRISM, 1.5f, -3f))));
    public static final RegistryObject<Item> RPRISM_AXE = ITEMS.register("rprism_axe",
            () -> new AxeItem(ModToolTiers.RPRISM, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.RPRISM, 6, -3.2f))));
    public static final RegistryObject<Item> RPRISM_HOE = ITEMS.register("rprism_hoe",
            () -> new HoeItem(ModToolTiers.RPRISM, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.RPRISM, 0, -3f))));

    // -------------------------------------------------------------------------
    // Calcified Amethyst tools (iron mining level)
    // -------------------------------------------------------------------------
    public static final RegistryObject<Item> CAMETHYST_SWORD = ITEMS.register("camethyst_sword",
            () -> new SwordItem(ModToolTiers.CAMETHYST, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.CAMETHYST, 3, -2.4f))));
    public static final RegistryObject<Item> CAMETHYST_PICKAXE = ITEMS.register("camethyst_pickaxe",
            () -> new PickaxeItem(ModToolTiers.CAMETHYST, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.CAMETHYST, 1, -2.8f))));
    public static final RegistryObject<Item> CAMETHYST_SHOVEL = ITEMS.register("camethyst_shovel",
            () -> new ShovelItem(ModToolTiers.CAMETHYST, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.CAMETHYST, 1.5f, -3f))));
    public static final RegistryObject<Item> CAMETHYST_AXE = ITEMS.register("camethyst_axe",
            () -> new AxeItem(ModToolTiers.CAMETHYST, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.CAMETHYST, 6, -3.2f))));
    public static final RegistryObject<Item> CAMETHYST_HOE = ITEMS.register("camethyst_hoe",
            () -> new HoeItem(ModToolTiers.CAMETHYST, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.CAMETHYST, 0, -3f))));
    public static final RegistryObject<Item> CAMETHYST_HELMET = ITEMS.register("camethyst_helmet",
            () -> new ArmorItem(ModArmorMaterials.CAMETHYST_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(15))));
    public static final RegistryObject<Item> CAMETHYST_CHESTPLATE = ITEMS.register("camethyst_chestplate",
            () -> new ArmorItem(ModArmorMaterials.CAMETHYST_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(15))));
    public static final RegistryObject<Item> CAMETHYST_LEGGINGS = ITEMS.register("camethyst_leggings",
            () -> new ArmorItem(ModArmorMaterials.CAMETHYST_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(15))));
    public static final RegistryObject<Item> CAMETHYST_BOOTS = ITEMS.register("camethyst_boots",
            () -> new ArmorItem(ModArmorMaterials.CAMETHYST_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(15))));

    // -------------------------------------------------------------------------
    // Ice (Glacial) tools (diamond mining level)
    // -------------------------------------------------------------------------
    public static final RegistryObject<Item> ICE_SWORD = ITEMS.register("ice_sword",
            () -> new SwordItem(ModToolTiers.ICE_TOOL, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.ICE_TOOL, 3, -2.4f))));
    public static final RegistryObject<Item> ICE_PICKAXE = ITEMS.register("ice_pickaxe",
            () -> new PickaxeItem(ModToolTiers.ICE_TOOL, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.ICE_TOOL, 1, -2.8f))));
    public static final RegistryObject<Item> ICE_SHOVEL = ITEMS.register("ice_shovel",
            () -> new ShovelItem(ModToolTiers.ICE_TOOL, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.ICE_TOOL, 1.5f, -3f))));
    public static final RegistryObject<Item> ICE_AXE = ITEMS.register("ice_axe",
            () -> new AxeItem(ModToolTiers.ICE_TOOL, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.ICE_TOOL, 6, -3.2f))));
    public static final RegistryObject<Item> ICE_HOE = ITEMS.register("ice_hoe",
            () -> new HoeItem(ModToolTiers.ICE_TOOL, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.ICE_TOOL, 0, -3f))));
    public static final RegistryObject<Item> ICE_HELMET = ITEMS.register("ice_helmet",
            () -> new ArmorItem(ModArmorMaterials.ICE_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(10))));
    public static final RegistryObject<Item> ICE_CHESTPLATE = ITEMS.register("ice_chestplate",
            () -> new ArmorItem(ModArmorMaterials.ICE_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(10))));
    public static final RegistryObject<Item> ICE_LEGGINGS = ITEMS.register("ice_leggings",
            () -> new ArmorItem(ModArmorMaterials.ICE_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(10))));
    public static final RegistryObject<Item> ICE_BOOTS = ITEMS.register("ice_boots",
            () -> new ArmorItem(ModArmorMaterials.ICE_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(10))));

    // -------------------------------------------------------------------------
    // Polished Quartz tools (diamond mining level)
    // -------------------------------------------------------------------------
    public static final RegistryObject<Item> PQUARTZ_SWORD = ITEMS.register("pquartz_sword",
            () -> new SwordItem(ModToolTiers.PQUARTZ, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.PQUARTZ, 3, -2.4f))));
    public static final RegistryObject<Item> PQUARTZ_PICKAXE = ITEMS.register("pquartz_pickaxe",
            () -> new PickaxeItem(ModToolTiers.PQUARTZ, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.PQUARTZ, 1, -2.8f))));
    public static final RegistryObject<Item> PQUARTZ_SHOVEL = ITEMS.register("pquartz_shovel",
            () -> new ShovelItem(ModToolTiers.PQUARTZ, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.PQUARTZ, 1.5f, -3f))));
    public static final RegistryObject<Item> PQUARTZ_AXE = ITEMS.register("pquartz_axe",
            () -> new AxeItem(ModToolTiers.PQUARTZ, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.PQUARTZ, 6, -3.2f))));
    public static final RegistryObject<Item> PQUARTZ_HOE = ITEMS.register("pquartz_hoe",
            () -> new HoeItem(ModToolTiers.PQUARTZ, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.PQUARTZ, 0, -3f))));
    public static final RegistryObject<Item> PQUARTZ_HELMET = ITEMS.register("pquartz_helmet",
            () -> new ArmorItem(ModArmorMaterials.PQUARTZ_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(16))));
    public static final RegistryObject<Item> PQUARTZ_CHESTPLATE = ITEMS.register("pquartz_chestplate",
            () -> new ArmorItem(ModArmorMaterials.PQUARTZ_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(16))));
    public static final RegistryObject<Item> PQUARTZ_LEGGINGS = ITEMS.register("pquartz_leggings",
            () -> new ArmorItem(ModArmorMaterials.PQUARTZ_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(16))));
    public static final RegistryObject<Item> PQUARTZ_BOOTS = ITEMS.register("pquartz_boots",
            () -> new ArmorItem(ModArmorMaterials.PQUARTZ_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(16))));

    // -------------------------------------------------------------------------
    // Polished Prismarine tools (diamond mining level)
    // -------------------------------------------------------------------------
    public static final RegistryObject<Item> PPRISM_SWORD = ITEMS.register("pprism_sword",
            () -> new SwordItem(ModToolTiers.PPRISM, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.PPRISM, 3, -2.4f))));
    public static final RegistryObject<Item> PPRISM_PICKAXE = ITEMS.register("pprism_pickaxe",
            () -> new PickaxeItem(ModToolTiers.PPRISM, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.PPRISM, 1, -2.8f))));
    public static final RegistryObject<Item> PPRISM_SHOVEL = ITEMS.register("pprism_shovel",
            () -> new ShovelItem(ModToolTiers.PPRISM, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.PPRISM, 1.5f, -3f))));
    public static final RegistryObject<Item> PPRISM_AXE = ITEMS.register("pprism_axe",
            () -> new AxeItem(ModToolTiers.PPRISM, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.PPRISM, 6, -3.2f))));
    public static final RegistryObject<Item> PPRISM_HOE = ITEMS.register("pprism_hoe",
            () -> new HoeItem(ModToolTiers.PPRISM, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.PPRISM, 0, -3f))));
    public static final RegistryObject<Item> PPRISM_HELMET = ITEMS.register("pprism_helmet",
            () -> new ArmorItem(ModArmorMaterials.PPRISM_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(14))));
    public static final RegistryObject<Item> PPRISM_CHESTPLATE = ITEMS.register("pprism_chestplate",
            () -> new ArmorItem(ModArmorMaterials.PPRISM_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(14))));
    public static final RegistryObject<Item> PPRISM_LEGGINGS = ITEMS.register("pprism_leggings",
            () -> new ArmorItem(ModArmorMaterials.PPRISM_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(14))));
    public static final RegistryObject<Item> PPRISM_BOOTS = ITEMS.register("pprism_boots",
            () -> new ArmorItem(ModArmorMaterials.PPRISM_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(14))));

    // -------------------------------------------------------------------------
    // Flint Tools (rough, stone level)
    // -------------------------------------------------------------------------
    public static final RegistryObject<Item> RFLINT_SWORD = ITEMS.register("rflint_sword",
            () -> new SwordItem(ModToolTiers.RFLINT, new Item.Properties().attributes(SwordItem.createAttributes(ModToolTiers.RFLINT, 3, -2.4f))));
    public static final RegistryObject<Item> RFLINT_PICKAXE = ITEMS.register("rflint_pickaxe",
            () -> new PickaxeItem(ModToolTiers.RFLINT, new Item.Properties().attributes(PickaxeItem.createAttributes(ModToolTiers.RFLINT, 1, -2.8f))));
    public static final RegistryObject<Item> RFLINT_SHOVEL = ITEMS.register("rflint_shovel",
            () -> new ShovelItem(ModToolTiers.RFLINT, new Item.Properties().attributes(ShovelItem.createAttributes(ModToolTiers.RFLINT, 1.5f, -3f))));
    public static final RegistryObject<Item> RFLINT_AXE = ITEMS.register("rflint_axe",
            () -> new AxeItem(ModToolTiers.RFLINT, new Item.Properties().attributes(AxeItem.createAttributes(ModToolTiers.RFLINT, 6, -3.2f))));
    public static final RegistryObject<Item> RFLINT_HOE = ITEMS.register("rflint_hoe",
            () -> new HoeItem(ModToolTiers.RFLINT, new Item.Properties().attributes(HoeItem.createAttributes(ModToolTiers.RFLINT, 0, -3f))));

    // -------------------------------------------------------------------------
    // Flint-Iron (FNI) Tools + Armor
    // -------------------------------------------------------------------------
    public static final RegistryObject<Item> FNI_SWORD = ITEMS.register("fni_sword",
            () -> new SwordItem(ModToolTiers.FNI_TOOLS, new Item.Properties().attributes(SwordItem.createAttributes(ModToolTiers.FNI_TOOLS, 3, -2.4f))));
    public static final RegistryObject<Item> FNI_PICKAXE = ITEMS.register("fni_pickaxe",
            () -> new PickaxeItem(ModToolTiers.FNI_TOOLS, new Item.Properties().attributes(PickaxeItem.createAttributes(ModToolTiers.FNI_TOOLS, 1, -2.8f))));
    public static final RegistryObject<Item> FNI_SHOVEL = ITEMS.register("fni_shovel",
            () -> new ShovelItem(ModToolTiers.FNI_TOOLS, new Item.Properties().attributes(ShovelItem.createAttributes(ModToolTiers.FNI_TOOLS, 1.5f, -3f))));
    public static final RegistryObject<Item> FNI_AXE = ITEMS.register("fni_axe",
            () -> new AxeItem(ModToolTiers.FNI_TOOLS, new Item.Properties().attributes(AxeItem.createAttributes(ModToolTiers.FNI_TOOLS, 6, -3.2f))));
    public static final RegistryObject<Item> FNI_HOE = ITEMS.register("fni_hoe",
            () -> new HoeItem(ModToolTiers.FNI_TOOLS, new Item.Properties().attributes(HoeItem.createAttributes(ModToolTiers.FNI_TOOLS, 0, -3f))));

    public static final RegistryObject<Item> FNI_HELMET = ITEMS.register("fni_helmet",
            () -> new ArmorItem(ModArmorMaterials.FNI_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(13))));
    public static final RegistryObject<Item> FNI_CHESTPLATE = ITEMS.register("fni_chestplate",
            () -> new ArmorItem(ModArmorMaterials.FNI_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(13))));
    public static final RegistryObject<Item> FNI_LEGGINGS = ITEMS.register("fni_leggings",
            () -> new ArmorItem(ModArmorMaterials.FNI_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(13))));
    public static final RegistryObject<Item> FNI_BOOTS = ITEMS.register("fni_boots",
            () -> new ArmorItem(ModArmorMaterials.FNI_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(13))));

    // -------------------------------------------------------------------------
    // Stone Rock Variant Tools (13 types × 5 tools, each with its own tier)
    // -------------------------------------------------------------------------

    // Andesite — vanilla stone baseline
    public static final RegistryObject<Item> ANDESITE_SWORD = ITEMS.register("andesite_sword",
            () -> new SwordItem(ModToolTiers.STONE_ANDESITE, new Item.Properties().attributes(SwordItem.createAttributes(ModToolTiers.STONE_ANDESITE, 3, -2.4f))));
    public static final RegistryObject<Item> ANDESITE_PICKAXE = ITEMS.register("andesite_pickaxe",
            () -> new PickaxeItem(ModToolTiers.STONE_ANDESITE, new Item.Properties().attributes(PickaxeItem.createAttributes(ModToolTiers.STONE_ANDESITE, 1, -2.8f))));
    public static final RegistryObject<Item> ANDESITE_SHOVEL = ITEMS.register("andesite_shovel",
            () -> new ShovelItem(ModToolTiers.STONE_ANDESITE, new Item.Properties().attributes(ShovelItem.createAttributes(ModToolTiers.STONE_ANDESITE, 1.5f, -3f))));
    public static final RegistryObject<Item> ANDESITE_AXE = ITEMS.register("andesite_axe",
            () -> new AxeItem(ModToolTiers.STONE_ANDESITE, new Item.Properties().attributes(AxeItem.createAttributes(ModToolTiers.STONE_ANDESITE, 6, -3.2f))));
    public static final RegistryObject<Item> ANDESITE_HOE = ITEMS.register("andesite_hoe",
            () -> new HoeItem(ModToolTiers.STONE_ANDESITE, new Item.Properties().attributes(HoeItem.createAttributes(ModToolTiers.STONE_ANDESITE, 0, -3f))));

    // Basalt — dense, durable, slow: heavy swing, harder axe/shovel
    public static final RegistryObject<Item> BASALT_SWORD = ITEMS.register("basalt_sword",
            () -> new SwordItem(ModToolTiers.STONE_BASALT, new Item.Properties().attributes(SwordItem.createAttributes(ModToolTiers.STONE_BASALT, 3, -2.5f))));
    public static final RegistryObject<Item> BASALT_PICKAXE = ITEMS.register("basalt_pickaxe",
            () -> new PickaxeItem(ModToolTiers.STONE_BASALT, new Item.Properties().attributes(PickaxeItem.createAttributes(ModToolTiers.STONE_BASALT, 1, -2.9f))));
    public static final RegistryObject<Item> BASALT_SHOVEL = ITEMS.register("basalt_shovel",
            () -> new ShovelItem(ModToolTiers.STONE_BASALT, new Item.Properties().attributes(ShovelItem.createAttributes(ModToolTiers.STONE_BASALT, 2.0f, -3.1f))));
    public static final RegistryObject<Item> BASALT_AXE = ITEMS.register("basalt_axe",
            () -> new AxeItem(ModToolTiers.STONE_BASALT, new Item.Properties().attributes(AxeItem.createAttributes(ModToolTiers.STONE_BASALT, 7, -3.3f))));
    public static final RegistryObject<Item> BASALT_HOE = ITEMS.register("basalt_hoe",
            () -> new HoeItem(ModToolTiers.STONE_BASALT, new Item.Properties().attributes(HoeItem.createAttributes(ModToolTiers.STONE_BASALT, 0, -3.1f))));

    // Blackstone — toughest stone variant: heavy swing, hardest hits
    public static final RegistryObject<Item> BLACKSTONE_SWORD = ITEMS.register("blackstone_sword",
            () -> new SwordItem(ModToolTiers.STONE_BLACKSTONE, new Item.Properties().attributes(SwordItem.createAttributes(ModToolTiers.STONE_BLACKSTONE, 4, -2.5f))));
    public static final RegistryObject<Item> BLACKSTONE_PICKAXE = ITEMS.register("blackstone_pickaxe",
            () -> new PickaxeItem(ModToolTiers.STONE_BLACKSTONE, new Item.Properties().attributes(PickaxeItem.createAttributes(ModToolTiers.STONE_BLACKSTONE, 1, -2.9f))));
    public static final RegistryObject<Item> BLACKSTONE_SHOVEL = ITEMS.register("blackstone_shovel",
            () -> new ShovelItem(ModToolTiers.STONE_BLACKSTONE, new Item.Properties().attributes(ShovelItem.createAttributes(ModToolTiers.STONE_BLACKSTONE, 2.0f, -3.1f))));
    public static final RegistryObject<Item> BLACKSTONE_AXE = ITEMS.register("blackstone_axe",
            () -> new AxeItem(ModToolTiers.STONE_BLACKSTONE, new Item.Properties().attributes(AxeItem.createAttributes(ModToolTiers.STONE_BLACKSTONE, 7, -3.35f))));
    public static final RegistryObject<Item> BLACKSTONE_HOE = ITEMS.register("blackstone_hoe",
            () -> new HoeItem(ModToolTiers.STONE_BLACKSTONE, new Item.Properties().attributes(HoeItem.createAttributes(ModToolTiers.STONE_BLACKSTONE, 0, -3.1f))));

    // Calcite — fragile, light, enchantable: fast swing, weaker hits
    public static final RegistryObject<Item> CALCITE_SWORD = ITEMS.register("calcite_sword",
            () -> new SwordItem(ModToolTiers.STONE_CALCITE, new Item.Properties().attributes(SwordItem.createAttributes(ModToolTiers.STONE_CALCITE, 2, -2.2f))));
    public static final RegistryObject<Item> CALCITE_PICKAXE = ITEMS.register("calcite_pickaxe",
            () -> new PickaxeItem(ModToolTiers.STONE_CALCITE, new Item.Properties().attributes(PickaxeItem.createAttributes(ModToolTiers.STONE_CALCITE, 1, -2.6f))));
    public static final RegistryObject<Item> CALCITE_SHOVEL = ITEMS.register("calcite_shovel",
            () -> new ShovelItem(ModToolTiers.STONE_CALCITE, new Item.Properties().attributes(ShovelItem.createAttributes(ModToolTiers.STONE_CALCITE, 1.0f, -2.8f))));
    public static final RegistryObject<Item> CALCITE_AXE = ITEMS.register("calcite_axe",
            () -> new AxeItem(ModToolTiers.STONE_CALCITE, new Item.Properties().attributes(AxeItem.createAttributes(ModToolTiers.STONE_CALCITE, 5, -3.0f))));
    public static final RegistryObject<Item> CALCITE_HOE = ITEMS.register("calcite_hoe",
            () -> new HoeItem(ModToolTiers.STONE_CALCITE, new Item.Properties().attributes(HoeItem.createAttributes(ModToolTiers.STONE_CALCITE, 0, -2.6f))));

    // Deepslate — most durable stone variant, sluggish: heaviest swing, big hits
    public static final RegistryObject<Item> DEEPSLATE_SWORD = ITEMS.register("deepslate_sword",
            () -> new SwordItem(ModToolTiers.STONE_DEEPSLATE, new Item.Properties().attributes(SwordItem.createAttributes(ModToolTiers.STONE_DEEPSLATE, 4, -2.55f))));
    public static final RegistryObject<Item> DEEPSLATE_PICKAXE = ITEMS.register("deepslate_pickaxe",
            () -> new PickaxeItem(ModToolTiers.STONE_DEEPSLATE, new Item.Properties().attributes(PickaxeItem.createAttributes(ModToolTiers.STONE_DEEPSLATE, 1, -2.95f))));
    public static final RegistryObject<Item> DEEPSLATE_SHOVEL = ITEMS.register("deepslate_shovel",
            () -> new ShovelItem(ModToolTiers.STONE_DEEPSLATE, new Item.Properties().attributes(ShovelItem.createAttributes(ModToolTiers.STONE_DEEPSLATE, 2.0f, -3.15f))));
    public static final RegistryObject<Item> DEEPSLATE_AXE = ITEMS.register("deepslate_axe",
            () -> new AxeItem(ModToolTiers.STONE_DEEPSLATE, new Item.Properties().attributes(AxeItem.createAttributes(ModToolTiers.STONE_DEEPSLATE, 7, -3.4f))));
    public static final RegistryObject<Item> DEEPSLATE_HOE = ITEMS.register("deepslate_hoe",
            () -> new HoeItem(ModToolTiers.STONE_DEEPSLATE, new Item.Properties().attributes(HoeItem.createAttributes(ModToolTiers.STONE_DEEPSLATE, 0, -3.1f))));

    // Diorite — slightly better all-round than andesite: marginally faster hoe
    public static final RegistryObject<Item> DIORITE_SWORD = ITEMS.register("diorite_sword",
            () -> new SwordItem(ModToolTiers.STONE_DIORITE, new Item.Properties().attributes(SwordItem.createAttributes(ModToolTiers.STONE_DIORITE, 3, -2.4f))));
    public static final RegistryObject<Item> DIORITE_PICKAXE = ITEMS.register("diorite_pickaxe",
            () -> new PickaxeItem(ModToolTiers.STONE_DIORITE, new Item.Properties().attributes(PickaxeItem.createAttributes(ModToolTiers.STONE_DIORITE, 1, -2.8f))));
    public static final RegistryObject<Item> DIORITE_SHOVEL = ITEMS.register("diorite_shovel",
            () -> new ShovelItem(ModToolTiers.STONE_DIORITE, new Item.Properties().attributes(ShovelItem.createAttributes(ModToolTiers.STONE_DIORITE, 1.5f, -3f))));
    public static final RegistryObject<Item> DIORITE_AXE = ITEMS.register("diorite_axe",
            () -> new AxeItem(ModToolTiers.STONE_DIORITE, new Item.Properties().attributes(AxeItem.createAttributes(ModToolTiers.STONE_DIORITE, 6, -3.2f))));
    public static final RegistryObject<Item> DIORITE_HOE = ITEMS.register("diorite_hoe",
            () -> new HoeItem(ModToolTiers.STONE_DIORITE, new Item.Properties().attributes(HoeItem.createAttributes(ModToolTiers.STONE_DIORITE, 0, -2.9f))));

    // End Stone — hard alien material, highly enchantable: slightly faster across the board
    public static final RegistryObject<Item> END_STONE_SWORD = ITEMS.register("end_stone_sword",
            () -> new SwordItem(ModToolTiers.STONE_END_STONE, new Item.Properties().attributes(SwordItem.createAttributes(ModToolTiers.STONE_END_STONE, 3, -2.35f))));
    public static final RegistryObject<Item> END_STONE_PICKAXE = ITEMS.register("end_stone_pickaxe",
            () -> new PickaxeItem(ModToolTiers.STONE_END_STONE, new Item.Properties().attributes(PickaxeItem.createAttributes(ModToolTiers.STONE_END_STONE, 1, -2.75f))));
    public static final RegistryObject<Item> END_STONE_SHOVEL = ITEMS.register("end_stone_shovel",
            () -> new ShovelItem(ModToolTiers.STONE_END_STONE, new Item.Properties().attributes(ShovelItem.createAttributes(ModToolTiers.STONE_END_STONE, 1.5f, -2.95f))));
    public static final RegistryObject<Item> END_STONE_AXE = ITEMS.register("end_stone_axe",
            () -> new AxeItem(ModToolTiers.STONE_END_STONE, new Item.Properties().attributes(AxeItem.createAttributes(ModToolTiers.STONE_END_STONE, 6, -3.15f))));
    public static final RegistryObject<Item> END_STONE_HOE = ITEMS.register("end_stone_hoe",
            () -> new HoeItem(ModToolTiers.STONE_END_STONE, new Item.Properties().attributes(HoeItem.createAttributes(ModToolTiers.STONE_END_STONE, 0, -2.8f))));

    // Granite — hard igneous, strong and heavy: heavy swing, harder axe/shovel
    public static final RegistryObject<Item> GRANITE_SWORD = ITEMS.register("granite_sword",
            () -> new SwordItem(ModToolTiers.STONE_GRANITE, new Item.Properties().attributes(SwordItem.createAttributes(ModToolTiers.STONE_GRANITE, 3, -2.5f))));
    public static final RegistryObject<Item> GRANITE_PICKAXE = ITEMS.register("granite_pickaxe",
            () -> new PickaxeItem(ModToolTiers.STONE_GRANITE, new Item.Properties().attributes(PickaxeItem.createAttributes(ModToolTiers.STONE_GRANITE, 1, -2.9f))));
    public static final RegistryObject<Item> GRANITE_SHOVEL = ITEMS.register("granite_shovel",
            () -> new ShovelItem(ModToolTiers.STONE_GRANITE, new Item.Properties().attributes(ShovelItem.createAttributes(ModToolTiers.STONE_GRANITE, 2.0f, -3.1f))));
    public static final RegistryObject<Item> GRANITE_AXE = ITEMS.register("granite_axe",
            () -> new AxeItem(ModToolTiers.STONE_GRANITE, new Item.Properties().attributes(AxeItem.createAttributes(ModToolTiers.STONE_GRANITE, 7, -3.3f))));
    public static final RegistryObject<Item> GRANITE_HOE = ITEMS.register("granite_hoe",
            () -> new HoeItem(ModToolTiers.STONE_GRANITE, new Item.Properties().attributes(HoeItem.createAttributes(ModToolTiers.STONE_GRANITE, 0, -3.1f))));

    // Netherrack — crumbly, barely functional, extremely fast: fastest swing, weakest hits
    public static final RegistryObject<Item> NETHERRACK_SWORD = ITEMS.register("netherrack_sword",
            () -> new SwordItem(ModToolTiers.STONE_NETHERRACK, new Item.Properties().attributes(SwordItem.createAttributes(ModToolTiers.STONE_NETHERRACK, 2, -2.2f))));
    public static final RegistryObject<Item> NETHERRACK_PICKAXE = ITEMS.register("netherrack_pickaxe",
            () -> new PickaxeItem(ModToolTiers.STONE_NETHERRACK, new Item.Properties().attributes(PickaxeItem.createAttributes(ModToolTiers.STONE_NETHERRACK, 1, -2.6f))));
    public static final RegistryObject<Item> NETHERRACK_SHOVEL = ITEMS.register("netherrack_shovel",
            () -> new ShovelItem(ModToolTiers.STONE_NETHERRACK, new Item.Properties().attributes(ShovelItem.createAttributes(ModToolTiers.STONE_NETHERRACK, 1.0f, -2.8f))));
    public static final RegistryObject<Item> NETHERRACK_AXE = ITEMS.register("netherrack_axe",
            () -> new AxeItem(ModToolTiers.STONE_NETHERRACK, new Item.Properties().attributes(AxeItem.createAttributes(ModToolTiers.STONE_NETHERRACK, 5, -3.0f))));
    public static final RegistryObject<Item> NETHERRACK_HOE = ITEMS.register("netherrack_hoe",
            () -> new HoeItem(ModToolTiers.STONE_NETHERRACK, new Item.Properties().attributes(HoeItem.createAttributes(ModToolTiers.STONE_NETHERRACK, 0, -2.5f))));

    // Sandstone — soft sedimentary, brittle but quick: light swing, weaker hits
    public static final RegistryObject<Item> SANDSTONE_SWORD = ITEMS.register("sandstone_sword",
            () -> new SwordItem(ModToolTiers.STONE_SANDSTONE, new Item.Properties().attributes(SwordItem.createAttributes(ModToolTiers.STONE_SANDSTONE, 2, -2.3f))));
    public static final RegistryObject<Item> SANDSTONE_PICKAXE = ITEMS.register("sandstone_pickaxe",
            () -> new PickaxeItem(ModToolTiers.STONE_SANDSTONE, new Item.Properties().attributes(PickaxeItem.createAttributes(ModToolTiers.STONE_SANDSTONE, 1, -2.7f))));
    public static final RegistryObject<Item> SANDSTONE_SHOVEL = ITEMS.register("sandstone_shovel",
            () -> new ShovelItem(ModToolTiers.STONE_SANDSTONE, new Item.Properties().attributes(ShovelItem.createAttributes(ModToolTiers.STONE_SANDSTONE, 1.0f, -2.9f))));
    public static final RegistryObject<Item> SANDSTONE_AXE = ITEMS.register("sandstone_axe",
            () -> new AxeItem(ModToolTiers.STONE_SANDSTONE, new Item.Properties().attributes(AxeItem.createAttributes(ModToolTiers.STONE_SANDSTONE, 5, -3.1f))));
    public static final RegistryObject<Item> SANDSTONE_HOE = ITEMS.register("sandstone_hoe",
            () -> new HoeItem(ModToolTiers.STONE_SANDSTONE, new Item.Properties().attributes(HoeItem.createAttributes(ModToolTiers.STONE_SANDSTONE, 0, -2.7f))));

    // Smooth Basalt — polished volcanic, balanced between basalt and andesite: slightly heavy
    public static final RegistryObject<Item> SMOOTH_BASALT_SWORD = ITEMS.register("smooth_basalt_sword",
            () -> new SwordItem(ModToolTiers.STONE_SMOOTH_BASALT, new Item.Properties().attributes(SwordItem.createAttributes(ModToolTiers.STONE_SMOOTH_BASALT, 3, -2.45f))));
    public static final RegistryObject<Item> SMOOTH_BASALT_PICKAXE = ITEMS.register("smooth_basalt_pickaxe",
            () -> new PickaxeItem(ModToolTiers.STONE_SMOOTH_BASALT, new Item.Properties().attributes(PickaxeItem.createAttributes(ModToolTiers.STONE_SMOOTH_BASALT, 1, -2.85f))));
    public static final RegistryObject<Item> SMOOTH_BASALT_SHOVEL = ITEMS.register("smooth_basalt_shovel",
            () -> new ShovelItem(ModToolTiers.STONE_SMOOTH_BASALT, new Item.Properties().attributes(ShovelItem.createAttributes(ModToolTiers.STONE_SMOOTH_BASALT, 1.5f, -3.05f))));
    public static final RegistryObject<Item> SMOOTH_BASALT_AXE = ITEMS.register("smooth_basalt_axe",
            () -> new AxeItem(ModToolTiers.STONE_SMOOTH_BASALT, new Item.Properties().attributes(AxeItem.createAttributes(ModToolTiers.STONE_SMOOTH_BASALT, 6, -3.25f))));
    public static final RegistryObject<Item> SMOOTH_BASALT_HOE = ITEMS.register("smooth_basalt_hoe",
            () -> new HoeItem(ModToolTiers.STONE_SMOOTH_BASALT, new Item.Properties().attributes(HoeItem.createAttributes(ModToolTiers.STONE_SMOOTH_BASALT, 0, -3.0f))));

    // Terracotta — baked clay, moderate with better enchantability: slightly faster, lighter
    public static final RegistryObject<Item> TERRACOTTA_SWORD = ITEMS.register("terracotta_sword",
            () -> new SwordItem(ModToolTiers.STONE_TERRACOTTA, new Item.Properties().attributes(SwordItem.createAttributes(ModToolTiers.STONE_TERRACOTTA, 3, -2.35f))));
    public static final RegistryObject<Item> TERRACOTTA_PICKAXE = ITEMS.register("terracotta_pickaxe",
            () -> new PickaxeItem(ModToolTiers.STONE_TERRACOTTA, new Item.Properties().attributes(PickaxeItem.createAttributes(ModToolTiers.STONE_TERRACOTTA, 1, -2.75f))));
    public static final RegistryObject<Item> TERRACOTTA_SHOVEL = ITEMS.register("terracotta_shovel",
            () -> new ShovelItem(ModToolTiers.STONE_TERRACOTTA, new Item.Properties().attributes(ShovelItem.createAttributes(ModToolTiers.STONE_TERRACOTTA, 1.5f, -2.95f))));
    public static final RegistryObject<Item> TERRACOTTA_AXE = ITEMS.register("terracotta_axe",
            () -> new AxeItem(ModToolTiers.STONE_TERRACOTTA, new Item.Properties().attributes(AxeItem.createAttributes(ModToolTiers.STONE_TERRACOTTA, 6, -3.15f))));
    public static final RegistryObject<Item> TERRACOTTA_HOE = ITEMS.register("terracotta_hoe",
            () -> new HoeItem(ModToolTiers.STONE_TERRACOTTA, new Item.Properties().attributes(HoeItem.createAttributes(ModToolTiers.STONE_TERRACOTTA, 0, -2.8f))));

    // Tuff — compressed volcanic ash, below-average overall: slightly faster, slightly weaker
    public static final RegistryObject<Item> TUFF_SWORD = ITEMS.register("tuff_sword",
            () -> new SwordItem(ModToolTiers.STONE_TUFF, new Item.Properties().attributes(SwordItem.createAttributes(ModToolTiers.STONE_TUFF, 2, -2.35f))));
    public static final RegistryObject<Item> TUFF_PICKAXE = ITEMS.register("tuff_pickaxe",
            () -> new PickaxeItem(ModToolTiers.STONE_TUFF, new Item.Properties().attributes(PickaxeItem.createAttributes(ModToolTiers.STONE_TUFF, 1, -2.75f))));
    public static final RegistryObject<Item> TUFF_SHOVEL = ITEMS.register("tuff_shovel",
            () -> new ShovelItem(ModToolTiers.STONE_TUFF, new Item.Properties().attributes(ShovelItem.createAttributes(ModToolTiers.STONE_TUFF, 1.5f, -2.95f))));
    public static final RegistryObject<Item> TUFF_AXE = ITEMS.register("tuff_axe",
            () -> new AxeItem(ModToolTiers.STONE_TUFF, new Item.Properties().attributes(AxeItem.createAttributes(ModToolTiers.STONE_TUFF, 5, -3.15f))));
    public static final RegistryObject<Item> TUFF_HOE = ITEMS.register("tuff_hoe",
            () -> new HoeItem(ModToolTiers.STONE_TUFF, new Item.Properties().attributes(HoeItem.createAttributes(ModToolTiers.STONE_TUFF, 0, -2.8f))));

    // -------------------------------------------------------------------------
    // Wood Variant Tools (11 types × 5 tools, each with its own tier)
    // -------------------------------------------------------------------------

    // Oak — vanilla baseline
    public static final RegistryObject<Item> OAK_SWORD = ITEMS.register("oak_sword",
            () -> new SwordItem(ModToolTiers.WOOD_OAK, new Item.Properties().attributes(SwordItem.createAttributes(ModToolTiers.WOOD_OAK, 3, -2.4f))));
    public static final RegistryObject<Item> OAK_PICKAXE = ITEMS.register("oak_pickaxe",
            () -> new PickaxeItem(ModToolTiers.WOOD_OAK, new Item.Properties().attributes(PickaxeItem.createAttributes(ModToolTiers.WOOD_OAK, 1, -2.8f))));
    public static final RegistryObject<Item> OAK_SHOVEL = ITEMS.register("oak_shovel",
            () -> new ShovelItem(ModToolTiers.WOOD_OAK, new Item.Properties().attributes(ShovelItem.createAttributes(ModToolTiers.WOOD_OAK, 1.5f, -3f))));
    public static final RegistryObject<Item> OAK_AXE = ITEMS.register("oak_axe",
            () -> new AxeItem(ModToolTiers.WOOD_OAK, new Item.Properties().attributes(AxeItem.createAttributes(ModToolTiers.WOOD_OAK, 6, -3.2f))));
    public static final RegistryObject<Item> OAK_HOE = ITEMS.register("oak_hoe",
            () -> new HoeItem(ModToolTiers.WOOD_OAK, new Item.Properties().attributes(HoeItem.createAttributes(ModToolTiers.WOOD_OAK, 0, -3f))));

    // Spruce — sturdy conifer
    public static final RegistryObject<Item> SPRUCE_SWORD = ITEMS.register("spruce_sword",
            () -> new SwordItem(ModToolTiers.WOOD_SPRUCE, new Item.Properties().attributes(SwordItem.createAttributes(ModToolTiers.WOOD_SPRUCE, 3, -2.4f))));
    public static final RegistryObject<Item> SPRUCE_PICKAXE = ITEMS.register("spruce_pickaxe",
            () -> new PickaxeItem(ModToolTiers.WOOD_SPRUCE, new Item.Properties().attributes(PickaxeItem.createAttributes(ModToolTiers.WOOD_SPRUCE, 1, -2.8f))));
    public static final RegistryObject<Item> SPRUCE_SHOVEL = ITEMS.register("spruce_shovel",
            () -> new ShovelItem(ModToolTiers.WOOD_SPRUCE, new Item.Properties().attributes(ShovelItem.createAttributes(ModToolTiers.WOOD_SPRUCE, 1.5f, -3f))));
    public static final RegistryObject<Item> SPRUCE_AXE = ITEMS.register("spruce_axe",
            () -> new AxeItem(ModToolTiers.WOOD_SPRUCE, new Item.Properties().attributes(AxeItem.createAttributes(ModToolTiers.WOOD_SPRUCE, 6, -3.2f))));
    public static final RegistryObject<Item> SPRUCE_HOE = ITEMS.register("spruce_hoe",
            () -> new HoeItem(ModToolTiers.WOOD_SPRUCE, new Item.Properties().attributes(HoeItem.createAttributes(ModToolTiers.WOOD_SPRUCE, 0, -3f))));

    // Birch — light, fast, enchantable
    public static final RegistryObject<Item> BIRCH_SWORD = ITEMS.register("birch_sword",
            () -> new SwordItem(ModToolTiers.WOOD_BIRCH, new Item.Properties().attributes(SwordItem.createAttributes(ModToolTiers.WOOD_BIRCH, 3, -2.4f))));
    public static final RegistryObject<Item> BIRCH_PICKAXE = ITEMS.register("birch_pickaxe",
            () -> new PickaxeItem(ModToolTiers.WOOD_BIRCH, new Item.Properties().attributes(PickaxeItem.createAttributes(ModToolTiers.WOOD_BIRCH, 1, -2.8f))));
    public static final RegistryObject<Item> BIRCH_SHOVEL = ITEMS.register("birch_shovel",
            () -> new ShovelItem(ModToolTiers.WOOD_BIRCH, new Item.Properties().attributes(ShovelItem.createAttributes(ModToolTiers.WOOD_BIRCH, 1.5f, -3f))));
    public static final RegistryObject<Item> BIRCH_AXE = ITEMS.register("birch_axe",
            () -> new AxeItem(ModToolTiers.WOOD_BIRCH, new Item.Properties().attributes(AxeItem.createAttributes(ModToolTiers.WOOD_BIRCH, 6, -3.2f))));
    public static final RegistryObject<Item> BIRCH_HOE = ITEMS.register("birch_hoe",
            () -> new HoeItem(ModToolTiers.WOOD_BIRCH, new Item.Properties().attributes(HoeItem.createAttributes(ModToolTiers.WOOD_BIRCH, 0, -3f))));

    // Jungle — dense tropical
    public static final RegistryObject<Item> JUNGLE_SWORD = ITEMS.register("jungle_sword",
            () -> new SwordItem(ModToolTiers.WOOD_JUNGLE, new Item.Properties().attributes(SwordItem.createAttributes(ModToolTiers.WOOD_JUNGLE, 3, -2.4f))));
    public static final RegistryObject<Item> JUNGLE_PICKAXE = ITEMS.register("jungle_pickaxe",
            () -> new PickaxeItem(ModToolTiers.WOOD_JUNGLE, new Item.Properties().attributes(PickaxeItem.createAttributes(ModToolTiers.WOOD_JUNGLE, 1, -2.8f))));
    public static final RegistryObject<Item> JUNGLE_SHOVEL = ITEMS.register("jungle_shovel",
            () -> new ShovelItem(ModToolTiers.WOOD_JUNGLE, new Item.Properties().attributes(ShovelItem.createAttributes(ModToolTiers.WOOD_JUNGLE, 1.5f, -3f))));
    public static final RegistryObject<Item> JUNGLE_AXE = ITEMS.register("jungle_axe",
            () -> new AxeItem(ModToolTiers.WOOD_JUNGLE, new Item.Properties().attributes(AxeItem.createAttributes(ModToolTiers.WOOD_JUNGLE, 6, -3.2f))));
    public static final RegistryObject<Item> JUNGLE_HOE = ITEMS.register("jungle_hoe",
            () -> new HoeItem(ModToolTiers.WOOD_JUNGLE, new Item.Properties().attributes(HoeItem.createAttributes(ModToolTiers.WOOD_JUNGLE, 0, -3f))));

    // Acacia — hard African wood
    public static final RegistryObject<Item> ACACIA_SWORD = ITEMS.register("acacia_sword",
            () -> new SwordItem(ModToolTiers.WOOD_ACACIA, new Item.Properties().attributes(SwordItem.createAttributes(ModToolTiers.WOOD_ACACIA, 3, -2.4f))));
    public static final RegistryObject<Item> ACACIA_PICKAXE = ITEMS.register("acacia_pickaxe",
            () -> new PickaxeItem(ModToolTiers.WOOD_ACACIA, new Item.Properties().attributes(PickaxeItem.createAttributes(ModToolTiers.WOOD_ACACIA, 1, -2.8f))));
    public static final RegistryObject<Item> ACACIA_SHOVEL = ITEMS.register("acacia_shovel",
            () -> new ShovelItem(ModToolTiers.WOOD_ACACIA, new Item.Properties().attributes(ShovelItem.createAttributes(ModToolTiers.WOOD_ACACIA, 1.5f, -3f))));
    public static final RegistryObject<Item> ACACIA_AXE = ITEMS.register("acacia_axe",
            () -> new AxeItem(ModToolTiers.WOOD_ACACIA, new Item.Properties().attributes(AxeItem.createAttributes(ModToolTiers.WOOD_ACACIA, 6, -3.2f))));
    public static final RegistryObject<Item> ACACIA_HOE = ITEMS.register("acacia_hoe",
            () -> new HoeItem(ModToolTiers.WOOD_ACACIA, new Item.Properties().attributes(HoeItem.createAttributes(ModToolTiers.WOOD_ACACIA, 0, -3f))));

    // Dark Oak — dense, durable, slow
    public static final RegistryObject<Item> DARK_OAK_SWORD = ITEMS.register("dark_oak_sword",
            () -> new SwordItem(ModToolTiers.WOOD_DARK_OAK, new Item.Properties().attributes(SwordItem.createAttributes(ModToolTiers.WOOD_DARK_OAK, 3, -2.4f))));
    public static final RegistryObject<Item> DARK_OAK_PICKAXE = ITEMS.register("dark_oak_pickaxe",
            () -> new PickaxeItem(ModToolTiers.WOOD_DARK_OAK, new Item.Properties().attributes(PickaxeItem.createAttributes(ModToolTiers.WOOD_DARK_OAK, 1, -2.8f))));
    public static final RegistryObject<Item> DARK_OAK_SHOVEL = ITEMS.register("dark_oak_shovel",
            () -> new ShovelItem(ModToolTiers.WOOD_DARK_OAK, new Item.Properties().attributes(ShovelItem.createAttributes(ModToolTiers.WOOD_DARK_OAK, 1.5f, -3f))));
    public static final RegistryObject<Item> DARK_OAK_AXE = ITEMS.register("dark_oak_axe",
            () -> new AxeItem(ModToolTiers.WOOD_DARK_OAK, new Item.Properties().attributes(AxeItem.createAttributes(ModToolTiers.WOOD_DARK_OAK, 6, -3.2f))));
    public static final RegistryObject<Item> DARK_OAK_HOE = ITEMS.register("dark_oak_hoe",
            () -> new HoeItem(ModToolTiers.WOOD_DARK_OAK, new Item.Properties().attributes(HoeItem.createAttributes(ModToolTiers.WOOD_DARK_OAK, 0, -3f))));

    // Mangrove — tough tropical
    public static final RegistryObject<Item> MANGROVE_SWORD = ITEMS.register("mangrove_sword",
            () -> new SwordItem(ModToolTiers.WOOD_MANGROVE, new Item.Properties().attributes(SwordItem.createAttributes(ModToolTiers.WOOD_MANGROVE, 3, -2.4f))));
    public static final RegistryObject<Item> MANGROVE_PICKAXE = ITEMS.register("mangrove_pickaxe",
            () -> new PickaxeItem(ModToolTiers.WOOD_MANGROVE, new Item.Properties().attributes(PickaxeItem.createAttributes(ModToolTiers.WOOD_MANGROVE, 1, -2.8f))));
    public static final RegistryObject<Item> MANGROVE_SHOVEL = ITEMS.register("mangrove_shovel",
            () -> new ShovelItem(ModToolTiers.WOOD_MANGROVE, new Item.Properties().attributes(ShovelItem.createAttributes(ModToolTiers.WOOD_MANGROVE, 1.5f, -3f))));
    public static final RegistryObject<Item> MANGROVE_AXE = ITEMS.register("mangrove_axe",
            () -> new AxeItem(ModToolTiers.WOOD_MANGROVE, new Item.Properties().attributes(AxeItem.createAttributes(ModToolTiers.WOOD_MANGROVE, 6, -3.2f))));
    public static final RegistryObject<Item> MANGROVE_HOE = ITEMS.register("mangrove_hoe",
            () -> new HoeItem(ModToolTiers.WOOD_MANGROVE, new Item.Properties().attributes(HoeItem.createAttributes(ModToolTiers.WOOD_MANGROVE, 0, -3f))));

    // Cherry — beautiful, very enchantable
    public static final RegistryObject<Item> CHERRY_SWORD = ITEMS.register("cherry_sword",
            () -> new SwordItem(ModToolTiers.WOOD_CHERRY, new Item.Properties().attributes(SwordItem.createAttributes(ModToolTiers.WOOD_CHERRY, 3, -2.4f))));
    public static final RegistryObject<Item> CHERRY_PICKAXE = ITEMS.register("cherry_pickaxe",
            () -> new PickaxeItem(ModToolTiers.WOOD_CHERRY, new Item.Properties().attributes(PickaxeItem.createAttributes(ModToolTiers.WOOD_CHERRY, 1, -2.8f))));
    public static final RegistryObject<Item> CHERRY_SHOVEL = ITEMS.register("cherry_shovel",
            () -> new ShovelItem(ModToolTiers.WOOD_CHERRY, new Item.Properties().attributes(ShovelItem.createAttributes(ModToolTiers.WOOD_CHERRY, 1.5f, -3f))));
    public static final RegistryObject<Item> CHERRY_AXE = ITEMS.register("cherry_axe",
            () -> new AxeItem(ModToolTiers.WOOD_CHERRY, new Item.Properties().attributes(AxeItem.createAttributes(ModToolTiers.WOOD_CHERRY, 6, -3.2f))));
    public static final RegistryObject<Item> CHERRY_HOE = ITEMS.register("cherry_hoe",
            () -> new HoeItem(ModToolTiers.WOOD_CHERRY, new Item.Properties().attributes(HoeItem.createAttributes(ModToolTiers.WOOD_CHERRY, 0, -3f))));

    // Bamboo — fastest, most fragile
    public static final RegistryObject<Item> BAMBOO_SWORD = ITEMS.register("bamboo_sword",
            () -> new SwordItem(ModToolTiers.WOOD_BAMBOO, new Item.Properties().attributes(SwordItem.createAttributes(ModToolTiers.WOOD_BAMBOO, 3, -2.4f))));
    public static final RegistryObject<Item> BAMBOO_PICKAXE = ITEMS.register("bamboo_pickaxe",
            () -> new PickaxeItem(ModToolTiers.WOOD_BAMBOO, new Item.Properties().attributes(PickaxeItem.createAttributes(ModToolTiers.WOOD_BAMBOO, 1, -2.8f))));
    public static final RegistryObject<Item> BAMBOO_SHOVEL = ITEMS.register("bamboo_shovel",
            () -> new ShovelItem(ModToolTiers.WOOD_BAMBOO, new Item.Properties().attributes(ShovelItem.createAttributes(ModToolTiers.WOOD_BAMBOO, 1.5f, -3f))));
    public static final RegistryObject<Item> BAMBOO_AXE = ITEMS.register("bamboo_axe",
            () -> new AxeItem(ModToolTiers.WOOD_BAMBOO, new Item.Properties().attributes(AxeItem.createAttributes(ModToolTiers.WOOD_BAMBOO, 6, -3.2f))));
    public static final RegistryObject<Item> BAMBOO_HOE = ITEMS.register("bamboo_hoe",
            () -> new HoeItem(ModToolTiers.WOOD_BAMBOO, new Item.Properties().attributes(HoeItem.createAttributes(ModToolTiers.WOOD_BAMBOO, 0, -3f))));

    // Crimson — tough nether fungus
    public static final RegistryObject<Item> CRIMSON_SWORD = ITEMS.register("crimson_sword",
            () -> new SwordItem(ModToolTiers.WOOD_CRIMSON, new Item.Properties().attributes(SwordItem.createAttributes(ModToolTiers.WOOD_CRIMSON, 3, -2.4f))));
    public static final RegistryObject<Item> CRIMSON_PICKAXE = ITEMS.register("crimson_pickaxe",
            () -> new PickaxeItem(ModToolTiers.WOOD_CRIMSON, new Item.Properties().attributes(PickaxeItem.createAttributes(ModToolTiers.WOOD_CRIMSON, 1, -2.8f))));
    public static final RegistryObject<Item> CRIMSON_SHOVEL = ITEMS.register("crimson_shovel",
            () -> new ShovelItem(ModToolTiers.WOOD_CRIMSON, new Item.Properties().attributes(ShovelItem.createAttributes(ModToolTiers.WOOD_CRIMSON, 1.5f, -3f))));
    public static final RegistryObject<Item> CRIMSON_AXE = ITEMS.register("crimson_axe",
            () -> new AxeItem(ModToolTiers.WOOD_CRIMSON, new Item.Properties().attributes(AxeItem.createAttributes(ModToolTiers.WOOD_CRIMSON, 6, -3.2f))));
    public static final RegistryObject<Item> CRIMSON_HOE = ITEMS.register("crimson_hoe",
            () -> new HoeItem(ModToolTiers.WOOD_CRIMSON, new Item.Properties().attributes(HoeItem.createAttributes(ModToolTiers.WOOD_CRIMSON, 0, -3f))));

    // Warped — resilient, enchantable
    public static final RegistryObject<Item> WARPED_SWORD = ITEMS.register("warped_sword",
            () -> new SwordItem(ModToolTiers.WOOD_WARPED, new Item.Properties().attributes(SwordItem.createAttributes(ModToolTiers.WOOD_WARPED, 3, -2.4f))));
    public static final RegistryObject<Item> WARPED_PICKAXE = ITEMS.register("warped_pickaxe",
            () -> new PickaxeItem(ModToolTiers.WOOD_WARPED, new Item.Properties().attributes(PickaxeItem.createAttributes(ModToolTiers.WOOD_WARPED, 1, -2.8f))));
    public static final RegistryObject<Item> WARPED_SHOVEL = ITEMS.register("warped_shovel",
            () -> new ShovelItem(ModToolTiers.WOOD_WARPED, new Item.Properties().attributes(ShovelItem.createAttributes(ModToolTiers.WOOD_WARPED, 1.5f, -3f))));
    public static final RegistryObject<Item> WARPED_AXE = ITEMS.register("warped_axe",
            () -> new AxeItem(ModToolTiers.WOOD_WARPED, new Item.Properties().attributes(AxeItem.createAttributes(ModToolTiers.WOOD_WARPED, 6, -3.2f))));
    public static final RegistryObject<Item> WARPED_HOE = ITEMS.register("warped_hoe",
            () -> new HoeItem(ModToolTiers.WOOD_WARPED, new Item.Properties().attributes(HoeItem.createAttributes(ModToolTiers.WOOD_WARPED, 0, -3f))));

    // -------------------------------------------------------------------------
    // Leather tools (very weak, worse than wood)
    // -------------------------------------------------------------------------
    public static final RegistryObject<Item> LEATHER_SWORD = ITEMS.register("leather_sword",
            () -> new SwordItem(ModToolTiers.LEATHER, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.LEATHER, 3, -2.4f))));
    public static final RegistryObject<Item> LEATHER_PICKAXE = ITEMS.register("leather_pickaxe",
            () -> new PickaxeItem(ModToolTiers.LEATHER, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.LEATHER, 1, -2.8f))));
    public static final RegistryObject<Item> LEATHER_SHOVEL = ITEMS.register("leather_shovel",
            () -> new ShovelItem(ModToolTiers.LEATHER, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.LEATHER, 1.5f, -3f))));
    public static final RegistryObject<Item> LEATHER_AXE = ITEMS.register("leather_axe",
            () -> new AxeItem(ModToolTiers.LEATHER, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.LEATHER, 6, -3.2f))));
    public static final RegistryObject<Item> LEATHER_HOE = ITEMS.register("leather_hoe",
            () -> new HoeItem(ModToolTiers.LEATHER, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.LEATHER, 0, -3f))));

    // =========================================================================
    // Vanilla Material Sets (25 sets, ordered by power tier)
    // =========================================================================

    // ── Paper (tools only, ultra-weak) ──────────────────────────────────────
    public static final RegistryObject<Item> PAPER_SWORD = ITEMS.register("paper_sword",
            () -> new SwordItem(ModToolTiers.PAPER, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.PAPER, 3, -2.4f))));
    public static final RegistryObject<Item> PAPER_PICKAXE = ITEMS.register("paper_pickaxe",
            () -> new PickaxeItem(ModToolTiers.PAPER, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.PAPER, 1, -2.8f))));
    public static final RegistryObject<Item> PAPER_SHOVEL = ITEMS.register("paper_shovel",
            () -> new ShovelItem(ModToolTiers.PAPER, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.PAPER, 1.5f, -3f))));
    public static final RegistryObject<Item> PAPER_AXE = ITEMS.register("paper_axe",
            () -> new AxeItem(ModToolTiers.PAPER, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.PAPER, 6, -3.2f))));
    public static final RegistryObject<Item> PAPER_HOE = ITEMS.register("paper_hoe",
            () -> new HoeItem(ModToolTiers.PAPER, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.PAPER, 0, -3f))));

    // ── Feather (tools only, ultra-weak) ────────────────────────────────────
    public static final RegistryObject<Item> FEATHER_SWORD = ITEMS.register("feather_sword",
            () -> new SwordItem(ModToolTiers.FEATHER, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.FEATHER, 3, -2.4f))));
    public static final RegistryObject<Item> FEATHER_PICKAXE = ITEMS.register("feather_pickaxe",
            () -> new PickaxeItem(ModToolTiers.FEATHER, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.FEATHER, 1, -2.8f))));
    public static final RegistryObject<Item> FEATHER_SHOVEL = ITEMS.register("feather_shovel",
            () -> new ShovelItem(ModToolTiers.FEATHER, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.FEATHER, 1.5f, -3f))));
    public static final RegistryObject<Item> FEATHER_AXE = ITEMS.register("feather_axe",
            () -> new AxeItem(ModToolTiers.FEATHER, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.FEATHER, 6, -3.2f))));
    public static final RegistryObject<Item> FEATHER_HOE = ITEMS.register("feather_hoe",
            () -> new HoeItem(ModToolTiers.FEATHER, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.FEATHER, 0, -3f))));

    // ── Glass (tools only, fragile but sharp) ───────────────────────────────
    public static final RegistryObject<Item> GLASS_SWORD = ITEMS.register("glass_sword",
            () -> new SwordItem(ModToolTiers.GLASS, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.GLASS, 3, -2.4f))));
    public static final RegistryObject<Item> GLASS_PICKAXE = ITEMS.register("glass_pickaxe",
            () -> new PickaxeItem(ModToolTiers.GLASS, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.GLASS, 1, -2.8f))));
    public static final RegistryObject<Item> GLASS_SHOVEL = ITEMS.register("glass_shovel",
            () -> new ShovelItem(ModToolTiers.GLASS, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.GLASS, 1.5f, -3f))));
    public static final RegistryObject<Item> GLASS_AXE = ITEMS.register("glass_axe",
            () -> new AxeItem(ModToolTiers.GLASS, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.GLASS, 6, -3.2f))));
    public static final RegistryObject<Item> GLASS_HOE = ITEMS.register("glass_hoe",
            () -> new HoeItem(ModToolTiers.GLASS, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.GLASS, 0, -3f))));

    // ── Rabbit Hide (armor only) ────────────────────────────────────────────
    public static final RegistryObject<Item> RABBIT_HIDE_HELMET = ITEMS.register("rabbit_hide_helmet",
            () -> new ArmorItem(ModArmorMaterials.RABBIT_HIDE_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(6))));
    public static final RegistryObject<Item> RABBIT_HIDE_CHESTPLATE = ITEMS.register("rabbit_hide_chestplate",
            () -> new ArmorItem(ModArmorMaterials.RABBIT_HIDE_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(6))));
    public static final RegistryObject<Item> RABBIT_HIDE_LEGGINGS = ITEMS.register("rabbit_hide_leggings",
            () -> new ArmorItem(ModArmorMaterials.RABBIT_HIDE_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(6))));
    public static final RegistryObject<Item> RABBIT_HIDE_BOOTS = ITEMS.register("rabbit_hide_boots",
            () -> new ArmorItem(ModArmorMaterials.RABBIT_HIDE_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(6))));

    // ── Cactus (tools + armor) ──────────────────────────────────────────────
    public static final RegistryObject<Item> CACTUS_SWORD = ITEMS.register("cactus_sword",
            () -> new SwordItem(ModToolTiers.CACTUS, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.CACTUS, 3, -2.4f))));
    public static final RegistryObject<Item> CACTUS_PICKAXE = ITEMS.register("cactus_pickaxe",
            () -> new PickaxeItem(ModToolTiers.CACTUS, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.CACTUS, 1, -2.8f))));
    public static final RegistryObject<Item> CACTUS_SHOVEL = ITEMS.register("cactus_shovel",
            () -> new ShovelItem(ModToolTiers.CACTUS, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.CACTUS, 1.5f, -3f))));
    public static final RegistryObject<Item> CACTUS_AXE = ITEMS.register("cactus_axe",
            () -> new AxeItem(ModToolTiers.CACTUS, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.CACTUS, 6, -3.2f))));
    public static final RegistryObject<Item> CACTUS_HOE = ITEMS.register("cactus_hoe",
            () -> new HoeItem(ModToolTiers.CACTUS, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.CACTUS, 0, -3f))));
    public static final RegistryObject<Item> CACTUS_HELMET = ITEMS.register("cactus_helmet",
            () -> new ArmorItem(ModArmorMaterials.CACTUS_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(5))));
    public static final RegistryObject<Item> CACTUS_CHESTPLATE = ITEMS.register("cactus_chestplate",
            () -> new ArmorItem(ModArmorMaterials.CACTUS_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(5))));
    public static final RegistryObject<Item> CACTUS_LEGGINGS = ITEMS.register("cactus_leggings",
            () -> new ArmorItem(ModArmorMaterials.CACTUS_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(5))));
    public static final RegistryObject<Item> CACTUS_BOOTS = ITEMS.register("cactus_boots",
            () -> new ArmorItem(ModArmorMaterials.CACTUS_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(5))));

    // ── Sponge (tools only) ─────────────────────────────────────────────────
    public static final RegistryObject<Item> SPONGE_SWORD = ITEMS.register("sponge_sword",
            () -> new SwordItem(ModToolTiers.SPONGE, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.SPONGE, 3, -2.4f))));
    public static final RegistryObject<Item> SPONGE_PICKAXE = ITEMS.register("sponge_pickaxe",
            () -> new PickaxeItem(ModToolTiers.SPONGE, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.SPONGE, 1, -2.8f))));
    public static final RegistryObject<Item> SPONGE_SHOVEL = ITEMS.register("sponge_shovel",
            () -> new ShovelItem(ModToolTiers.SPONGE, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.SPONGE, 1.5f, -3f))));
    public static final RegistryObject<Item> SPONGE_AXE = ITEMS.register("sponge_axe",
            () -> new AxeItem(ModToolTiers.SPONGE, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.SPONGE, 6, -3.2f))));
    public static final RegistryObject<Item> SPONGE_HOE = ITEMS.register("sponge_hoe",
            () -> new HoeItem(ModToolTiers.SPONGE, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.SPONGE, 0, -3f))));

    // ── Bone (tools + armor) ────────────────────────────────────────────────
    public static final RegistryObject<Item> BONE_SWORD = ITEMS.register("bone_sword",
            () -> new SwordItem(ModToolTiers.BONE, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.BONE, 3, -2.4f))));
    public static final RegistryObject<Item> BONE_PICKAXE = ITEMS.register("bone_pickaxe",
            () -> new PickaxeItem(ModToolTiers.BONE, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.BONE, 1, -2.8f))));
    public static final RegistryObject<Item> BONE_SHOVEL = ITEMS.register("bone_shovel",
            () -> new ShovelItem(ModToolTiers.BONE, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.BONE, 1.5f, -3f))));
    public static final RegistryObject<Item> BONE_AXE = ITEMS.register("bone_axe",
            () -> new AxeItem(ModToolTiers.BONE, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.BONE, 6, -3.2f))));
    public static final RegistryObject<Item> BONE_HOE = ITEMS.register("bone_hoe",
            () -> new HoeItem(ModToolTiers.BONE, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.BONE, 0, -3f))));
    public static final RegistryObject<Item> BONE_HELMET = ITEMS.register("bone_helmet",
            () -> new ArmorItem(ModArmorMaterials.BONE_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(8))));
    public static final RegistryObject<Item> BONE_CHESTPLATE = ITEMS.register("bone_chestplate",
            () -> new ArmorItem(ModArmorMaterials.BONE_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(8))));
    public static final RegistryObject<Item> BONE_LEGGINGS = ITEMS.register("bone_leggings",
            () -> new ArmorItem(ModArmorMaterials.BONE_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(8))));
    public static final RegistryObject<Item> BONE_BOOTS = ITEMS.register("bone_boots",
            () -> new ArmorItem(ModArmorMaterials.BONE_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(8))));

    // ── Clay (tools + armor) ────────────────────────────────────────────────
    public static final RegistryObject<Item> CLAY_SWORD = ITEMS.register("clay_sword",
            () -> new SwordItem(ModToolTiers.CLAY, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.CLAY, 3, -2.4f))));
    public static final RegistryObject<Item> CLAY_PICKAXE = ITEMS.register("clay_pickaxe",
            () -> new PickaxeItem(ModToolTiers.CLAY, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.CLAY, 1, -2.8f))));
    public static final RegistryObject<Item> CLAY_SHOVEL = ITEMS.register("clay_shovel",
            () -> new ShovelItem(ModToolTiers.CLAY, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.CLAY, 1.5f, -3f))));
    public static final RegistryObject<Item> CLAY_AXE = ITEMS.register("clay_axe",
            () -> new AxeItem(ModToolTiers.CLAY, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.CLAY, 6, -3.2f))));
    public static final RegistryObject<Item> CLAY_HOE = ITEMS.register("clay_hoe",
            () -> new HoeItem(ModToolTiers.CLAY, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.CLAY, 0, -3f))));
    public static final RegistryObject<Item> CLAY_HELMET = ITEMS.register("clay_helmet",
            () -> new ArmorItem(ModArmorMaterials.CLAY_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(6))));
    public static final RegistryObject<Item> CLAY_CHESTPLATE = ITEMS.register("clay_chestplate",
            () -> new ArmorItem(ModArmorMaterials.CLAY_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(6))));
    public static final RegistryObject<Item> CLAY_LEGGINGS = ITEMS.register("clay_leggings",
            () -> new ArmorItem(ModArmorMaterials.CLAY_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(6))));
    public static final RegistryObject<Item> CLAY_BOOTS = ITEMS.register("clay_boots",
            () -> new ArmorItem(ModArmorMaterials.CLAY_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(6))));

    // ── Nether Wart (tools only) ────────────────────────────────────────────
    public static final RegistryObject<Item> NETHER_WART_SWORD = ITEMS.register("nether_wart_sword",
            () -> new SwordItem(ModToolTiers.NETHER_WART, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.NETHER_WART, 3, -2.4f))));
    public static final RegistryObject<Item> NETHER_WART_PICKAXE = ITEMS.register("nether_wart_pickaxe",
            () -> new PickaxeItem(ModToolTiers.NETHER_WART, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.NETHER_WART, 1, -2.8f))));
    public static final RegistryObject<Item> NETHER_WART_SHOVEL = ITEMS.register("nether_wart_shovel",
            () -> new ShovelItem(ModToolTiers.NETHER_WART, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.NETHER_WART, 1.5f, -3f))));
    public static final RegistryObject<Item> NETHER_WART_AXE = ITEMS.register("nether_wart_axe",
            () -> new AxeItem(ModToolTiers.NETHER_WART, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.NETHER_WART, 6, -3.2f))));
    public static final RegistryObject<Item> NETHER_WART_HOE = ITEMS.register("nether_wart_hoe",
            () -> new HoeItem(ModToolTiers.NETHER_WART, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.NETHER_WART, 0, -3f))));

    // ── Brick (tools + armor) ───────────────────────────────────────────────
    public static final RegistryObject<Item> BRICK_SWORD = ITEMS.register("brick_sword",
            () -> new SwordItem(ModToolTiers.BRICK, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.BRICK, 3, -2.4f))));
    public static final RegistryObject<Item> BRICK_PICKAXE = ITEMS.register("brick_pickaxe",
            () -> new PickaxeItem(ModToolTiers.BRICK, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.BRICK, 1, -2.8f))));
    public static final RegistryObject<Item> BRICK_SHOVEL = ITEMS.register("brick_shovel",
            () -> new ShovelItem(ModToolTiers.BRICK, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.BRICK, 1.5f, -3f))));
    public static final RegistryObject<Item> BRICK_AXE = ITEMS.register("brick_axe",
            () -> new AxeItem(ModToolTiers.BRICK, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.BRICK, 6, -3.2f))));
    public static final RegistryObject<Item> BRICK_HOE = ITEMS.register("brick_hoe",
            () -> new HoeItem(ModToolTiers.BRICK, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.BRICK, 0, -3f))));
    public static final RegistryObject<Item> BRICK_HELMET = ITEMS.register("brick_helmet",
            () -> new ArmorItem(ModArmorMaterials.BRICK_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(12))));
    public static final RegistryObject<Item> BRICK_CHESTPLATE = ITEMS.register("brick_chestplate",
            () -> new ArmorItem(ModArmorMaterials.BRICK_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(12))));
    public static final RegistryObject<Item> BRICK_LEGGINGS = ITEMS.register("brick_leggings",
            () -> new ArmorItem(ModArmorMaterials.BRICK_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(12))));
    public static final RegistryObject<Item> BRICK_BOOTS = ITEMS.register("brick_boots",
            () -> new ArmorItem(ModArmorMaterials.BRICK_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(12))));

    // ── Nether Brick (tools + armor) ────────────────────────────────────────
    public static final RegistryObject<Item> NETHER_BRICK_SWORD = ITEMS.register("nether_brick_sword",
            () -> new SwordItem(ModToolTiers.NETHER_BRICK, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.NETHER_BRICK, 3, -2.4f))));
    public static final RegistryObject<Item> NETHER_BRICK_PICKAXE = ITEMS.register("nether_brick_pickaxe",
            () -> new PickaxeItem(ModToolTiers.NETHER_BRICK, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.NETHER_BRICK, 1, -2.8f))));
    public static final RegistryObject<Item> NETHER_BRICK_SHOVEL = ITEMS.register("nether_brick_shovel",
            () -> new ShovelItem(ModToolTiers.NETHER_BRICK, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.NETHER_BRICK, 1.5f, -3f))));
    public static final RegistryObject<Item> NETHER_BRICK_AXE = ITEMS.register("nether_brick_axe",
            () -> new AxeItem(ModToolTiers.NETHER_BRICK, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.NETHER_BRICK, 6, -3.2f))));
    public static final RegistryObject<Item> NETHER_BRICK_HOE = ITEMS.register("nether_brick_hoe",
            () -> new HoeItem(ModToolTiers.NETHER_BRICK, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.NETHER_BRICK, 0, -3f))));
    public static final RegistryObject<Item> NETHER_BRICK_HELMET = ITEMS.register("nether_brick_helmet",
            () -> new ArmorItem(ModArmorMaterials.NETHER_BRICK_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(12))));
    public static final RegistryObject<Item> NETHER_BRICK_CHESTPLATE = ITEMS.register("nether_brick_chestplate",
            () -> new ArmorItem(ModArmorMaterials.NETHER_BRICK_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(12))));
    public static final RegistryObject<Item> NETHER_BRICK_LEGGINGS = ITEMS.register("nether_brick_leggings",
            () -> new ArmorItem(ModArmorMaterials.NETHER_BRICK_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(12))));
    public static final RegistryObject<Item> NETHER_BRICK_BOOTS = ITEMS.register("nether_brick_boots",
            () -> new ArmorItem(ModArmorMaterials.NETHER_BRICK_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(12))));

    // ── Pointed Dripstone (tools only) ──────────────────────────────────────
    public static final RegistryObject<Item> POINTED_DRIPSTONE_SWORD = ITEMS.register("pointed_dripstone_sword",
            () -> new SwordItem(ModToolTiers.POINTED_DRIPSTONE, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.POINTED_DRIPSTONE, 3, -2.4f))));
    public static final RegistryObject<Item> POINTED_DRIPSTONE_PICKAXE = ITEMS.register("pointed_dripstone_pickaxe",
            () -> new PickaxeItem(ModToolTiers.POINTED_DRIPSTONE, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.POINTED_DRIPSTONE, 1, -2.8f))));
    public static final RegistryObject<Item> POINTED_DRIPSTONE_SHOVEL = ITEMS.register("pointed_dripstone_shovel",
            () -> new ShovelItem(ModToolTiers.POINTED_DRIPSTONE, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.POINTED_DRIPSTONE, 1.5f, -3f))));
    public static final RegistryObject<Item> POINTED_DRIPSTONE_AXE = ITEMS.register("pointed_dripstone_axe",
            () -> new AxeItem(ModToolTiers.POINTED_DRIPSTONE, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.POINTED_DRIPSTONE, 6, -3.2f))));
    public static final RegistryObject<Item> POINTED_DRIPSTONE_HOE = ITEMS.register("pointed_dripstone_hoe",
            () -> new HoeItem(ModToolTiers.POINTED_DRIPSTONE, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.POINTED_DRIPSTONE, 0, -3f))));

    // ── Copper (tools + armor) ──────────────────────────────────────────────
    public static final RegistryObject<Item> COPPER_SWORD = ITEMS.register("copper_sword",
            () -> new SwordItem(ModToolTiers.COPPER, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.COPPER, 3, -2.4f))));
    public static final RegistryObject<Item> COPPER_PICKAXE = ITEMS.register("copper_pickaxe",
            () -> new PickaxeItem(ModToolTiers.COPPER, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.COPPER, 1, -2.8f))));
    public static final RegistryObject<Item> COPPER_SHOVEL = ITEMS.register("copper_shovel",
            () -> new ShovelItem(ModToolTiers.COPPER, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.COPPER, 1.5f, -3f))));
    public static final RegistryObject<Item> COPPER_AXE = ITEMS.register("copper_axe",
            () -> new AxeItem(ModToolTiers.COPPER, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.COPPER, 6, -3.2f))));
    public static final RegistryObject<Item> COPPER_HOE = ITEMS.register("copper_hoe",
            () -> new HoeItem(ModToolTiers.COPPER, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.COPPER, 0, -3f))));
    public static final RegistryObject<Item> COPPER_HELMET = ITEMS.register("copper_helmet",
            () -> new ArmorItem(ModArmorMaterials.COPPER_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(12))));
    public static final RegistryObject<Item> COPPER_CHESTPLATE = ITEMS.register("copper_chestplate",
            () -> new ArmorItem(ModArmorMaterials.COPPER_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(12))));
    public static final RegistryObject<Item> COPPER_LEGGINGS = ITEMS.register("copper_leggings",
            () -> new ArmorItem(ModArmorMaterials.COPPER_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(12))));
    public static final RegistryObject<Item> COPPER_BOOTS = ITEMS.register("copper_boots",
            () -> new ArmorItem(ModArmorMaterials.COPPER_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(12))));

    // ── Phantom Membrane (tools + armor) ────────────────────────────────────
    public static final RegistryObject<Item> PHANTOM_SWORD = ITEMS.register("phantom_sword",
            () -> new SwordItem(ModToolTiers.PHANTOM_MEMBRANE, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.PHANTOM_MEMBRANE, 3, -2.4f))));
    public static final RegistryObject<Item> PHANTOM_PICKAXE = ITEMS.register("phantom_pickaxe",
            () -> new PickaxeItem(ModToolTiers.PHANTOM_MEMBRANE, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.PHANTOM_MEMBRANE, 1, -2.8f))));
    public static final RegistryObject<Item> PHANTOM_SHOVEL = ITEMS.register("phantom_shovel",
            () -> new ShovelItem(ModToolTiers.PHANTOM_MEMBRANE, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.PHANTOM_MEMBRANE, 1.5f, -3f))));
    public static final RegistryObject<Item> PHANTOM_AXE = ITEMS.register("phantom_axe",
            () -> new AxeItem(ModToolTiers.PHANTOM_MEMBRANE, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.PHANTOM_MEMBRANE, 6, -3.2f))));
    public static final RegistryObject<Item> PHANTOM_HOE = ITEMS.register("phantom_hoe",
            () -> new HoeItem(ModToolTiers.PHANTOM_MEMBRANE, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.PHANTOM_MEMBRANE, 0, -3f))));
    public static final RegistryObject<Item> PHANTOM_HELMET = ITEMS.register("phantom_helmet",
            () -> new ArmorItem(ModArmorMaterials.PHANTOM_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(10))));
    public static final RegistryObject<Item> PHANTOM_CHESTPLATE = ITEMS.register("phantom_chestplate",
            () -> new ArmorItem(ModArmorMaterials.PHANTOM_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(10))));
    public static final RegistryObject<Item> PHANTOM_LEGGINGS = ITEMS.register("phantom_leggings",
            () -> new ArmorItem(ModArmorMaterials.PHANTOM_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(10))));
    public static final RegistryObject<Item> PHANTOM_BOOTS = ITEMS.register("phantom_boots",
            () -> new ArmorItem(ModArmorMaterials.PHANTOM_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(10))));

    // ── Magma Cream (tools + armor) ─────────────────────────────────────────
    public static final RegistryObject<Item> MAGMA_CREAM_SWORD = ITEMS.register("magma_cream_sword",
            () -> new SwordItem(ModToolTiers.MAGMA_CREAM, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.MAGMA_CREAM, 3, -2.4f))));
    public static final RegistryObject<Item> MAGMA_CREAM_PICKAXE = ITEMS.register("magma_cream_pickaxe",
            () -> new PickaxeItem(ModToolTiers.MAGMA_CREAM, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.MAGMA_CREAM, 1, -2.8f))));
    public static final RegistryObject<Item> MAGMA_CREAM_SHOVEL = ITEMS.register("magma_cream_shovel",
            () -> new ShovelItem(ModToolTiers.MAGMA_CREAM, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.MAGMA_CREAM, 1.5f, -3f))));
    public static final RegistryObject<Item> MAGMA_CREAM_AXE = ITEMS.register("magma_cream_axe",
            () -> new AxeItem(ModToolTiers.MAGMA_CREAM, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.MAGMA_CREAM, 6, -3.2f))));
    public static final RegistryObject<Item> MAGMA_CREAM_HOE = ITEMS.register("magma_cream_hoe",
            () -> new HoeItem(ModToolTiers.MAGMA_CREAM, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.MAGMA_CREAM, 0, -3f))));
    public static final RegistryObject<Item> MAGMA_CREAM_HELMET = ITEMS.register("magma_cream_helmet",
            () -> new ArmorItem(ModArmorMaterials.MAGMA_CREAM_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(10))));
    public static final RegistryObject<Item> MAGMA_CREAM_CHESTPLATE = ITEMS.register("magma_cream_chestplate",
            () -> new ArmorItem(ModArmorMaterials.MAGMA_CREAM_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(10))));
    public static final RegistryObject<Item> MAGMA_CREAM_LEGGINGS = ITEMS.register("magma_cream_leggings",
            () -> new ArmorItem(ModArmorMaterials.MAGMA_CREAM_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(10))));
    public static final RegistryObject<Item> MAGMA_CREAM_BOOTS = ITEMS.register("magma_cream_boots",
            () -> new ArmorItem(ModArmorMaterials.MAGMA_CREAM_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(10))));

    // ── Slime (tools + armor) ───────────────────────────────────────────────
    public static final RegistryObject<Item> SLIME_SWORD = ITEMS.register("slime_sword",
            () -> new SwordItem(ModToolTiers.SLIME, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.SLIME, 3, -2.4f))));
    public static final RegistryObject<Item> SLIME_PICKAXE = ITEMS.register("slime_pickaxe",
            () -> new PickaxeItem(ModToolTiers.SLIME, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.SLIME, 1, -2.8f))));
    public static final RegistryObject<Item> SLIME_SHOVEL = ITEMS.register("slime_shovel",
            () -> new ShovelItem(ModToolTiers.SLIME, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.SLIME, 1.5f, -3f))));
    public static final RegistryObject<Item> SLIME_AXE = ITEMS.register("slime_axe",
            () -> new AxeItem(ModToolTiers.SLIME, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.SLIME, 6, -3.2f))));
    public static final RegistryObject<Item> SLIME_HOE = ITEMS.register("slime_hoe",
            () -> new HoeItem(ModToolTiers.SLIME, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.SLIME, 0, -3f))));
    public static final RegistryObject<Item> SLIME_HELMET = ITEMS.register("slime_helmet",
            () -> new ArmorItem(ModArmorMaterials.SLIME_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(8))));
    public static final RegistryObject<Item> SLIME_CHESTPLATE = ITEMS.register("slime_chestplate",
            () -> new ArmorItem(ModArmorMaterials.SLIME_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(8))));
    public static final RegistryObject<Item> SLIME_LEGGINGS = ITEMS.register("slime_leggings",
            () -> new ArmorItem(ModArmorMaterials.SLIME_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(8))));
    public static final RegistryObject<Item> SLIME_BOOTS = ITEMS.register("slime_boots",
            () -> new ArmorItem(ModArmorMaterials.SLIME_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(8))));

    // ── Blaze Rod (tools + armor) ───────────────────────────────────────────
    public static final RegistryObject<Item> BLAZE_SWORD = ITEMS.register("blaze_sword",
            () -> new SwordItem(ModToolTiers.BLAZE_ROD, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.BLAZE_ROD, 3, -2.4f))));
    public static final RegistryObject<Item> BLAZE_PICKAXE = ITEMS.register("blaze_pickaxe",
            () -> new PickaxeItem(ModToolTiers.BLAZE_ROD, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.BLAZE_ROD, 1, -2.8f))));
    public static final RegistryObject<Item> BLAZE_SHOVEL = ITEMS.register("blaze_shovel",
            () -> new ShovelItem(ModToolTiers.BLAZE_ROD, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.BLAZE_ROD, 1.5f, -3f))));
    public static final RegistryObject<Item> BLAZE_AXE = ITEMS.register("blaze_axe",
            () -> new AxeItem(ModToolTiers.BLAZE_ROD, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.BLAZE_ROD, 6, -3.2f))));
    public static final RegistryObject<Item> BLAZE_HOE = ITEMS.register("blaze_hoe",
            () -> new HoeItem(ModToolTiers.BLAZE_ROD, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.BLAZE_ROD, 0, -3f))));
    public static final RegistryObject<Item> BLAZE_HELMET = ITEMS.register("blaze_helmet",
            () -> new ArmorItem(ModArmorMaterials.BLAZE_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(14))));
    public static final RegistryObject<Item> BLAZE_CHESTPLATE = ITEMS.register("blaze_chestplate",
            () -> new ArmorItem(ModArmorMaterials.BLAZE_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(14))));
    public static final RegistryObject<Item> BLAZE_LEGGINGS = ITEMS.register("blaze_leggings",
            () -> new ArmorItem(ModArmorMaterials.BLAZE_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(14))));
    public static final RegistryObject<Item> BLAZE_BOOTS = ITEMS.register("blaze_boots",
            () -> new ArmorItem(ModArmorMaterials.BLAZE_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(14))));

    // ── Nautilus Shell (tools + armor) ──────────────────────────────────────
    public static final RegistryObject<Item> NAUTILUS_SWORD = ITEMS.register("nautilus_sword",
            () -> new SwordItem(ModToolTiers.NAUTILUS_SHELL, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.NAUTILUS_SHELL, 3, -2.4f))));
    public static final RegistryObject<Item> NAUTILUS_PICKAXE = ITEMS.register("nautilus_pickaxe",
            () -> new PickaxeItem(ModToolTiers.NAUTILUS_SHELL, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.NAUTILUS_SHELL, 1, -2.8f))));
    public static final RegistryObject<Item> NAUTILUS_SHOVEL = ITEMS.register("nautilus_shovel",
            () -> new ShovelItem(ModToolTiers.NAUTILUS_SHELL, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.NAUTILUS_SHELL, 1.5f, -3f))));
    public static final RegistryObject<Item> NAUTILUS_AXE = ITEMS.register("nautilus_axe",
            () -> new AxeItem(ModToolTiers.NAUTILUS_SHELL, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.NAUTILUS_SHELL, 6, -3.2f))));
    public static final RegistryObject<Item> NAUTILUS_HOE = ITEMS.register("nautilus_hoe",
            () -> new HoeItem(ModToolTiers.NAUTILUS_SHELL, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.NAUTILUS_SHELL, 0, -3f))));
    public static final RegistryObject<Item> NAUTILUS_HELMET = ITEMS.register("nautilus_helmet",
            () -> new ArmorItem(ModArmorMaterials.NAUTILUS_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(14))));
    public static final RegistryObject<Item> NAUTILUS_CHESTPLATE = ITEMS.register("nautilus_chestplate",
            () -> new ArmorItem(ModArmorMaterials.NAUTILUS_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(14))));
    public static final RegistryObject<Item> NAUTILUS_LEGGINGS = ITEMS.register("nautilus_leggings",
            () -> new ArmorItem(ModArmorMaterials.NAUTILUS_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(14))));
    public static final RegistryObject<Item> NAUTILUS_BOOTS = ITEMS.register("nautilus_boots",
            () -> new ArmorItem(ModArmorMaterials.NAUTILUS_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(14))));

    // ── Purpur (tools + armor) ──────────────────────────────────────────────
    public static final RegistryObject<Item> PURPUR_SWORD = ITEMS.register("purpur_sword",
            () -> new SwordItem(ModToolTiers.PURPUR, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.PURPUR, 3, -2.4f))));
    public static final RegistryObject<Item> PURPUR_PICKAXE = ITEMS.register("purpur_pickaxe",
            () -> new PickaxeItem(ModToolTiers.PURPUR, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.PURPUR, 1, -2.8f))));
    public static final RegistryObject<Item> PURPUR_SHOVEL = ITEMS.register("purpur_shovel",
            () -> new ShovelItem(ModToolTiers.PURPUR, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.PURPUR, 1.5f, -3f))));
    public static final RegistryObject<Item> PURPUR_AXE = ITEMS.register("purpur_axe",
            () -> new AxeItem(ModToolTiers.PURPUR, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.PURPUR, 6, -3.2f))));
    public static final RegistryObject<Item> PURPUR_HOE = ITEMS.register("purpur_hoe",
            () -> new HoeItem(ModToolTiers.PURPUR, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.PURPUR, 0, -3f))));
    public static final RegistryObject<Item> PURPUR_HELMET = ITEMS.register("purpur_helmet",
            () -> new ArmorItem(ModArmorMaterials.PURPUR_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(14))));
    public static final RegistryObject<Item> PURPUR_CHESTPLATE = ITEMS.register("purpur_chestplate",
            () -> new ArmorItem(ModArmorMaterials.PURPUR_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(14))));
    public static final RegistryObject<Item> PURPUR_LEGGINGS = ITEMS.register("purpur_leggings",
            () -> new ArmorItem(ModArmorMaterials.PURPUR_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(14))));
    public static final RegistryObject<Item> PURPUR_BOOTS = ITEMS.register("purpur_boots",
            () -> new ArmorItem(ModArmorMaterials.PURPUR_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(14))));

    // ── Ghast Tear (tools + armor) ──────────────────────────────────────────
    public static final RegistryObject<Item> GHAST_TEAR_SWORD = ITEMS.register("ghast_tear_sword",
            () -> new SwordItem(ModToolTiers.GHAST_TEAR, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.GHAST_TEAR, 3, -2.4f))));
    public static final RegistryObject<Item> GHAST_TEAR_PICKAXE = ITEMS.register("ghast_tear_pickaxe",
            () -> new PickaxeItem(ModToolTiers.GHAST_TEAR, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.GHAST_TEAR, 1, -2.8f))));
    public static final RegistryObject<Item> GHAST_TEAR_SHOVEL = ITEMS.register("ghast_tear_shovel",
            () -> new ShovelItem(ModToolTiers.GHAST_TEAR, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.GHAST_TEAR, 1.5f, -3f))));
    public static final RegistryObject<Item> GHAST_TEAR_AXE = ITEMS.register("ghast_tear_axe",
            () -> new AxeItem(ModToolTiers.GHAST_TEAR, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.GHAST_TEAR, 6, -3.2f))));
    public static final RegistryObject<Item> GHAST_TEAR_HOE = ITEMS.register("ghast_tear_hoe",
            () -> new HoeItem(ModToolTiers.GHAST_TEAR, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.GHAST_TEAR, 0, -3f))));
    public static final RegistryObject<Item> GHAST_TEAR_HELMET = ITEMS.register("ghast_tear_helmet",
            () -> new ArmorItem(ModArmorMaterials.GHAST_TEAR_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(16))));
    public static final RegistryObject<Item> GHAST_TEAR_CHESTPLATE = ITEMS.register("ghast_tear_chestplate",
            () -> new ArmorItem(ModArmorMaterials.GHAST_TEAR_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(16))));
    public static final RegistryObject<Item> GHAST_TEAR_LEGGINGS = ITEMS.register("ghast_tear_leggings",
            () -> new ArmorItem(ModArmorMaterials.GHAST_TEAR_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(16))));
    public static final RegistryObject<Item> GHAST_TEAR_BOOTS = ITEMS.register("ghast_tear_boots",
            () -> new ArmorItem(ModArmorMaterials.GHAST_TEAR_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(16))));

    // ── Eye of Ender (tools + armor) ────────────────────────────────────────
    public static final RegistryObject<Item> EYE_OF_ENDER_SWORD = ITEMS.register("eye_of_ender_sword",
            () -> new SwordItem(ModToolTiers.EYE_OF_ENDER, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.EYE_OF_ENDER, 3, -2.4f))));
    public static final RegistryObject<Item> EYE_OF_ENDER_PICKAXE = ITEMS.register("eye_of_ender_pickaxe",
            () -> new PickaxeItem(ModToolTiers.EYE_OF_ENDER, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.EYE_OF_ENDER, 1, -2.8f))));
    public static final RegistryObject<Item> EYE_OF_ENDER_SHOVEL = ITEMS.register("eye_of_ender_shovel",
            () -> new ShovelItem(ModToolTiers.EYE_OF_ENDER, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.EYE_OF_ENDER, 1.5f, -3f))));
    public static final RegistryObject<Item> EYE_OF_ENDER_AXE = ITEMS.register("eye_of_ender_axe",
            () -> new AxeItem(ModToolTiers.EYE_OF_ENDER, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.EYE_OF_ENDER, 6, -3.2f))));
    public static final RegistryObject<Item> EYE_OF_ENDER_HOE = ITEMS.register("eye_of_ender_hoe",
            () -> new HoeItem(ModToolTiers.EYE_OF_ENDER, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.EYE_OF_ENDER, 0, -3f))));
    public static final RegistryObject<Item> EYE_OF_ENDER_HELMET = ITEMS.register("eye_of_ender_helmet",
            () -> new ArmorItem(ModArmorMaterials.EYE_OF_ENDER_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(16))));
    public static final RegistryObject<Item> EYE_OF_ENDER_CHESTPLATE = ITEMS.register("eye_of_ender_chestplate",
            () -> new ArmorItem(ModArmorMaterials.EYE_OF_ENDER_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(16))));
    public static final RegistryObject<Item> EYE_OF_ENDER_LEGGINGS = ITEMS.register("eye_of_ender_leggings",
            () -> new ArmorItem(ModArmorMaterials.EYE_OF_ENDER_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(16))));
    public static final RegistryObject<Item> EYE_OF_ENDER_BOOTS = ITEMS.register("eye_of_ender_boots",
            () -> new ArmorItem(ModArmorMaterials.EYE_OF_ENDER_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(16))));

    // ── Shulker Shell (tools + armor) ───────────────────────────────────────
    public static final RegistryObject<Item> SHULKER_SWORD = ITEMS.register("shulker_sword",
            () -> new SwordItem(ModToolTiers.SHULKER_SHELL, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.SHULKER_SHELL, 3, -2.4f))));
    public static final RegistryObject<Item> SHULKER_PICKAXE = ITEMS.register("shulker_pickaxe",
            () -> new PickaxeItem(ModToolTiers.SHULKER_SHELL, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.SHULKER_SHELL, 1, -2.8f))));
    public static final RegistryObject<Item> SHULKER_SHOVEL = ITEMS.register("shulker_shovel",
            () -> new ShovelItem(ModToolTiers.SHULKER_SHELL, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.SHULKER_SHELL, 1.5f, -3f))));
    public static final RegistryObject<Item> SHULKER_AXE = ITEMS.register("shulker_axe",
            () -> new AxeItem(ModToolTiers.SHULKER_SHELL, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.SHULKER_SHELL, 6, -3.2f))));
    public static final RegistryObject<Item> SHULKER_HOE = ITEMS.register("shulker_hoe",
            () -> new HoeItem(ModToolTiers.SHULKER_SHELL, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.SHULKER_SHELL, 0, -3f))));
    public static final RegistryObject<Item> SHULKER_HELMET = ITEMS.register("shulker_helmet",
            () -> new ArmorItem(ModArmorMaterials.SHULKER_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(18))));
    public static final RegistryObject<Item> SHULKER_CHESTPLATE = ITEMS.register("shulker_chestplate",
            () -> new ArmorItem(ModArmorMaterials.SHULKER_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(18))));
    public static final RegistryObject<Item> SHULKER_LEGGINGS = ITEMS.register("shulker_leggings",
            () -> new ArmorItem(ModArmorMaterials.SHULKER_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(18))));
    public static final RegistryObject<Item> SHULKER_BOOTS = ITEMS.register("shulker_boots",
            () -> new ArmorItem(ModArmorMaterials.SHULKER_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(18))));

    // ── Turtle Scute (armor only) ───────────────────────────────────────────
    public static final RegistryObject<Item> TURTLE_SCUTE_HELMET = ITEMS.register("turtle_scute_helmet",
            () -> new ArmorItem(ModArmorMaterials.TURTLE_SCUTE_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(20))));
    public static final RegistryObject<Item> TURTLE_SCUTE_CHESTPLATE = ITEMS.register("turtle_scute_chestplate",
            () -> new ArmorItem(ModArmorMaterials.TURTLE_SCUTE_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(20))));
    public static final RegistryObject<Item> TURTLE_SCUTE_LEGGINGS = ITEMS.register("turtle_scute_leggings",
            () -> new ArmorItem(ModArmorMaterials.TURTLE_SCUTE_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(20))));
    public static final RegistryObject<Item> TURTLE_SCUTE_BOOTS = ITEMS.register("turtle_scute_boots",
            () -> new ArmorItem(ModArmorMaterials.TURTLE_SCUTE_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(20))));

    // ── Echo Shard (tools + armor) ──────────────────────────────────────────
    public static final RegistryObject<Item> ECHO_SHARD_SWORD = ITEMS.register("echo_shard_sword",
            () -> new SwordItem(ModToolTiers.ECHO_SHARD, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.ECHO_SHARD, 3, -2.4f))));
    public static final RegistryObject<Item> ECHO_SHARD_PICKAXE = ITEMS.register("echo_shard_pickaxe",
            () -> new PickaxeItem(ModToolTiers.ECHO_SHARD, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.ECHO_SHARD, 1, -2.8f))));
    public static final RegistryObject<Item> ECHO_SHARD_SHOVEL = ITEMS.register("echo_shard_shovel",
            () -> new ShovelItem(ModToolTiers.ECHO_SHARD, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.ECHO_SHARD, 1.5f, -3f))));
    public static final RegistryObject<Item> ECHO_SHARD_AXE = ITEMS.register("echo_shard_axe",
            () -> new AxeItem(ModToolTiers.ECHO_SHARD, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.ECHO_SHARD, 6, -3.2f))));
    public static final RegistryObject<Item> ECHO_SHARD_HOE = ITEMS.register("echo_shard_hoe",
            () -> new HoeItem(ModToolTiers.ECHO_SHARD, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.ECHO_SHARD, 0, -3f))));
    public static final RegistryObject<Item> ECHO_SHARD_HELMET = ITEMS.register("echo_shard_helmet",
            () -> new ArmorItem(ModArmorMaterials.ECHO_SHARD_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(22))));
    public static final RegistryObject<Item> ECHO_SHARD_CHESTPLATE = ITEMS.register("echo_shard_chestplate",
            () -> new ArmorItem(ModArmorMaterials.ECHO_SHARD_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(22))));
    public static final RegistryObject<Item> ECHO_SHARD_LEGGINGS = ITEMS.register("echo_shard_leggings",
            () -> new ArmorItem(ModArmorMaterials.ECHO_SHARD_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(22))));
    public static final RegistryObject<Item> ECHO_SHARD_BOOTS = ITEMS.register("echo_shard_boots",
            () -> new ArmorItem(ModArmorMaterials.ECHO_SHARD_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(22))));

    // ── Dragon's Breath (tools + armor) ─────────────────────────────────────
    public static final RegistryObject<Item> DRAGON_BREATH_SWORD = ITEMS.register("dragon_breath_sword",
            () -> new SwordItem(ModToolTiers.DRAGON_BREATH, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.DRAGON_BREATH, 3, -2.4f))));
    public static final RegistryObject<Item> DRAGON_BREATH_PICKAXE = ITEMS.register("dragon_breath_pickaxe",
            () -> new PickaxeItem(ModToolTiers.DRAGON_BREATH, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.DRAGON_BREATH, 1, -2.8f))));
    public static final RegistryObject<Item> DRAGON_BREATH_SHOVEL = ITEMS.register("dragon_breath_shovel",
            () -> new ShovelItem(ModToolTiers.DRAGON_BREATH, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.DRAGON_BREATH, 1.5f, -3f))));
    public static final RegistryObject<Item> DRAGON_BREATH_AXE = ITEMS.register("dragon_breath_axe",
            () -> new AxeItem(ModToolTiers.DRAGON_BREATH, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.DRAGON_BREATH, 6, -3.2f))));
    public static final RegistryObject<Item> DRAGON_BREATH_HOE = ITEMS.register("dragon_breath_hoe",
            () -> new HoeItem(ModToolTiers.DRAGON_BREATH, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.DRAGON_BREATH, 0, -3f))));
    public static final RegistryObject<Item> DRAGON_BREATH_HELMET = ITEMS.register("dragon_breath_helmet",
            () -> new ArmorItem(ModArmorMaterials.DRAGON_BREATH_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(25))));
    public static final RegistryObject<Item> DRAGON_BREATH_CHESTPLATE = ITEMS.register("dragon_breath_chestplate",
            () -> new ArmorItem(ModArmorMaterials.DRAGON_BREATH_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(25))));
    public static final RegistryObject<Item> DRAGON_BREATH_LEGGINGS = ITEMS.register("dragon_breath_leggings",
            () -> new ArmorItem(ModArmorMaterials.DRAGON_BREATH_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(25))));
    public static final RegistryObject<Item> DRAGON_BREATH_BOOTS = ITEMS.register("dragon_breath_boots",
            () -> new ArmorItem(ModArmorMaterials.DRAGON_BREATH_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(25))));

    // -------------------------------------------------------------------------
    // Cake tools  (below wood tier, edible — it's cake!)
    // -------------------------------------------------------------------------
    public static final RegistryObject<Item> CAKE_SWORD = ITEMS.register("cake_sword",
            () -> new EdibleSwordItem(ModToolTiers.CAKE, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.CAKE, 3, -2.4f))
                    .food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.1f).build())));

    public static final RegistryObject<Item> CAKE_PICKAXE = ITEMS.register("cake_pickaxe",
            () -> new EdiblePickaxeItem(ModToolTiers.CAKE, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.CAKE, 1, -2.8f))
                    .food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.1f).build())));

    public static final RegistryObject<Item> CAKE_SHOVEL = ITEMS.register("cake_shovel",
            () -> new EdibleShovelItem(ModToolTiers.CAKE, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.CAKE, 1.5f, -3f))
                    .food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.1f).build())));

    public static final RegistryObject<Item> CAKE_AXE = ITEMS.register("cake_axe",
            () -> new EdibleAxeItem(ModToolTiers.CAKE, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.CAKE, 6, -3.2f))
                    .food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.1f).build())));

    public static final RegistryObject<Item> CAKE_HOE = ITEMS.register("cake_hoe",
            () -> new EdibleHoeItem(ModToolTiers.CAKE, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.CAKE, 0, -3f))
                    .food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.1f).build())));

    // -------------------------------------------------------------------------
    // Cake armor  (below leather, edible)
    // -------------------------------------------------------------------------
    public static final RegistryObject<Item> CAKE_HELMET = ITEMS.register("cake_helmet",
            () -> new EdibleArmorItem(ModArmorMaterials.CAKE_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(3))
                            .food(new FoodProperties.Builder().nutrition(10).saturationModifier(0.1f).build())));

    public static final RegistryObject<Item> CAKE_CHESTPLATE = ITEMS.register("cake_chestplate",
            () -> new EdibleArmorItem(ModArmorMaterials.CAKE_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(3))
                            .food(new FoodProperties.Builder().nutrition(14).saturationModifier(0.1f).build())));

    public static final RegistryObject<Item> CAKE_LEGGINGS = ITEMS.register("cake_leggings",
            () -> new EdibleArmorItem(ModArmorMaterials.CAKE_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(3))
                            .food(new FoodProperties.Builder().nutrition(14).saturationModifier(0.1f).build())));

    public static final RegistryObject<Item> CAKE_BOOTS = ITEMS.register("cake_boots",
            () -> new EdibleArmorItem(ModArmorMaterials.CAKE_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(3))
                            .food(new FoodProperties.Builder().nutrition(8).saturationModifier(0.1f).build())));

    // =========================================================================
    // Bread tools + armor (below wood tier, edible)
    // =========================================================================
    public static final RegistryObject<Item> BREAD_SWORD = ITEMS.register("bread_sword",
            () -> new EdibleSwordItem(ModToolTiers.BREAD, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.BREAD, 3, -2.4f))
                    .food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> BREAD_PICKAXE = ITEMS.register("bread_pickaxe",
            () -> new EdiblePickaxeItem(ModToolTiers.BREAD, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.BREAD, 1, -2.8f))
                    .food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> BREAD_SHOVEL = ITEMS.register("bread_shovel",
            () -> new EdibleShovelItem(ModToolTiers.BREAD, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.BREAD, 1.5f, -3f))
                    .food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> BREAD_AXE = ITEMS.register("bread_axe",
            () -> new EdibleAxeItem(ModToolTiers.BREAD, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.BREAD, 6, -3.2f))
                    .food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> BREAD_HOE = ITEMS.register("bread_hoe",
            () -> new EdibleHoeItem(ModToolTiers.BREAD, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.BREAD, 0, -3f))
                    .food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> BREAD_HELMET = ITEMS.register("bread_helmet",
            () -> new EdibleArmorItem(ModArmorMaterials.BREAD_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(3))
                            .food(new FoodProperties.Builder().nutrition(10).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> BREAD_CHESTPLATE = ITEMS.register("bread_chestplate",
            () -> new EdibleArmorItem(ModArmorMaterials.BREAD_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(3))
                            .food(new FoodProperties.Builder().nutrition(14).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> BREAD_LEGGINGS = ITEMS.register("bread_leggings",
            () -> new EdibleArmorItem(ModArmorMaterials.BREAD_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(3))
                            .food(new FoodProperties.Builder().nutrition(14).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> BREAD_BOOTS = ITEMS.register("bread_boots",
            () -> new EdibleArmorItem(ModArmorMaterials.BREAD_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(3))
                            .food(new FoodProperties.Builder().nutrition(8).saturationModifier(0.1f).build())));

    // =========================================================================
    // Dried Kelp tools + armor
    // =========================================================================
    public static final RegistryObject<Item> DRIED_KELP_SWORD = ITEMS.register("dried_kelp_sword",
            () -> new EdibleSwordItem(ModToolTiers.DRIED_KELP, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.DRIED_KELP, 3, -2.4f))
                    .food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> DRIED_KELP_PICKAXE = ITEMS.register("dried_kelp_pickaxe",
            () -> new EdiblePickaxeItem(ModToolTiers.DRIED_KELP, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.DRIED_KELP, 1, -2.8f))
                    .food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> DRIED_KELP_SHOVEL = ITEMS.register("dried_kelp_shovel",
            () -> new EdibleShovelItem(ModToolTiers.DRIED_KELP, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.DRIED_KELP, 1.5f, -3f))
                    .food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> DRIED_KELP_AXE = ITEMS.register("dried_kelp_axe",
            () -> new EdibleAxeItem(ModToolTiers.DRIED_KELP, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.DRIED_KELP, 6, -3.2f))
                    .food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> DRIED_KELP_HOE = ITEMS.register("dried_kelp_hoe",
            () -> new EdibleHoeItem(ModToolTiers.DRIED_KELP, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.DRIED_KELP, 0, -3f))
                    .food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> DRIED_KELP_HELMET = ITEMS.register("dried_kelp_helmet",
            () -> new EdibleArmorItem(ModArmorMaterials.DRIED_KELP_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(2))
                            .food(new FoodProperties.Builder().nutrition(10).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> DRIED_KELP_CHESTPLATE = ITEMS.register("dried_kelp_chestplate",
            () -> new EdibleArmorItem(ModArmorMaterials.DRIED_KELP_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(2))
                            .food(new FoodProperties.Builder().nutrition(14).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> DRIED_KELP_LEGGINGS = ITEMS.register("dried_kelp_leggings",
            () -> new EdibleArmorItem(ModArmorMaterials.DRIED_KELP_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(2))
                            .food(new FoodProperties.Builder().nutrition(14).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> DRIED_KELP_BOOTS = ITEMS.register("dried_kelp_boots",
            () -> new EdibleArmorItem(ModArmorMaterials.DRIED_KELP_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(2))
                            .food(new FoodProperties.Builder().nutrition(8).saturationModifier(0.1f).build())));

    // =========================================================================
    // Rotten Flesh tools + armor
    // =========================================================================
    public static final RegistryObject<Item> ROTTEN_FLESH_SWORD = ITEMS.register("rotten_flesh_sword",
            () -> new EdibleSwordItem(ModToolTiers.ROTTEN_FLESH, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.ROTTEN_FLESH, 3, -2.4f))
                    .food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> ROTTEN_FLESH_PICKAXE = ITEMS.register("rotten_flesh_pickaxe",
            () -> new EdiblePickaxeItem(ModToolTiers.ROTTEN_FLESH, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.ROTTEN_FLESH, 1, -2.8f))
                    .food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> ROTTEN_FLESH_SHOVEL = ITEMS.register("rotten_flesh_shovel",
            () -> new EdibleShovelItem(ModToolTiers.ROTTEN_FLESH, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.ROTTEN_FLESH, 1.5f, -3f))
                    .food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> ROTTEN_FLESH_AXE = ITEMS.register("rotten_flesh_axe",
            () -> new EdibleAxeItem(ModToolTiers.ROTTEN_FLESH, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.ROTTEN_FLESH, 6, -3.2f))
                    .food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> ROTTEN_FLESH_HOE = ITEMS.register("rotten_flesh_hoe",
            () -> new EdibleHoeItem(ModToolTiers.ROTTEN_FLESH, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.ROTTEN_FLESH, 0, -3f))
                    .food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> ROTTEN_FLESH_HELMET = ITEMS.register("rotten_flesh_helmet",
            () -> new EdibleArmorItem(ModArmorMaterials.ROTTEN_FLESH_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(2))
                            .food(new FoodProperties.Builder().nutrition(10).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> ROTTEN_FLESH_CHESTPLATE = ITEMS.register("rotten_flesh_chestplate",
            () -> new EdibleArmorItem(ModArmorMaterials.ROTTEN_FLESH_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(2))
                            .food(new FoodProperties.Builder().nutrition(14).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> ROTTEN_FLESH_LEGGINGS = ITEMS.register("rotten_flesh_leggings",
            () -> new EdibleArmorItem(ModArmorMaterials.ROTTEN_FLESH_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(2))
                            .food(new FoodProperties.Builder().nutrition(14).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> ROTTEN_FLESH_BOOTS = ITEMS.register("rotten_flesh_boots",
            () -> new EdibleArmorItem(ModArmorMaterials.ROTTEN_FLESH_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(2))
                            .food(new FoodProperties.Builder().nutrition(8).saturationModifier(0.1f).build())));

    // =========================================================================
    // Melon tools + armor (wood-equivalent)
    // =========================================================================
    public static final RegistryObject<Item> MELON_SWORD = ITEMS.register("melon_sword",
            () -> new EdibleSwordItem(ModToolTiers.MELON, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.MELON, 3, -2.4f))
                    .food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> MELON_PICKAXE = ITEMS.register("melon_pickaxe",
            () -> new EdiblePickaxeItem(ModToolTiers.MELON, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.MELON, 1, -2.8f))
                    .food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> MELON_SHOVEL = ITEMS.register("melon_shovel",
            () -> new EdibleShovelItem(ModToolTiers.MELON, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.MELON, 1.5f, -3f))
                    .food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> MELON_AXE = ITEMS.register("melon_axe",
            () -> new EdibleAxeItem(ModToolTiers.MELON, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.MELON, 6, -3.2f))
                    .food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> MELON_HOE = ITEMS.register("melon_hoe",
            () -> new EdibleHoeItem(ModToolTiers.MELON, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.MELON, 0, -3f))
                    .food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> MELON_HELMET = ITEMS.register("melon_helmet",
            () -> new EdibleArmorItem(ModArmorMaterials.MELON_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(5))
                            .food(new FoodProperties.Builder().nutrition(10).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> MELON_CHESTPLATE = ITEMS.register("melon_chestplate",
            () -> new EdibleArmorItem(ModArmorMaterials.MELON_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(5))
                            .food(new FoodProperties.Builder().nutrition(14).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> MELON_LEGGINGS = ITEMS.register("melon_leggings",
            () -> new EdibleArmorItem(ModArmorMaterials.MELON_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(5))
                            .food(new FoodProperties.Builder().nutrition(14).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> MELON_BOOTS = ITEMS.register("melon_boots",
            () -> new EdibleArmorItem(ModArmorMaterials.MELON_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(5))
                            .food(new FoodProperties.Builder().nutrition(8).saturationModifier(0.1f).build())));

    // =========================================================================
    // Sweet Berries tools + armor (wood-equivalent)
    // =========================================================================
    public static final RegistryObject<Item> SWEET_BERRY_SWORD = ITEMS.register("sweet_berry_sword",
            () -> new EdibleSwordItem(ModToolTiers.SWEET_BERRIES, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.SWEET_BERRIES, 3, -2.4f))
                    .food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> SWEET_BERRY_PICKAXE = ITEMS.register("sweet_berry_pickaxe",
            () -> new EdiblePickaxeItem(ModToolTiers.SWEET_BERRIES, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.SWEET_BERRIES, 1, -2.8f))
                    .food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> SWEET_BERRY_SHOVEL = ITEMS.register("sweet_berry_shovel",
            () -> new EdibleShovelItem(ModToolTiers.SWEET_BERRIES, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.SWEET_BERRIES, 1.5f, -3f))
                    .food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> SWEET_BERRY_AXE = ITEMS.register("sweet_berry_axe",
            () -> new EdibleAxeItem(ModToolTiers.SWEET_BERRIES, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.SWEET_BERRIES, 6, -3.2f))
                    .food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> SWEET_BERRY_HOE = ITEMS.register("sweet_berry_hoe",
            () -> new EdibleHoeItem(ModToolTiers.SWEET_BERRIES, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.SWEET_BERRIES, 0, -3f))
                    .food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> SWEET_BERRY_HELMET = ITEMS.register("sweet_berry_helmet",
            () -> new EdibleArmorItem(ModArmorMaterials.SWEET_BERRY_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(4))
                            .food(new FoodProperties.Builder().nutrition(10).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> SWEET_BERRY_CHESTPLATE = ITEMS.register("sweet_berry_chestplate",
            () -> new EdibleArmorItem(ModArmorMaterials.SWEET_BERRY_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(4))
                            .food(new FoodProperties.Builder().nutrition(14).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> SWEET_BERRY_LEGGINGS = ITEMS.register("sweet_berry_leggings",
            () -> new EdibleArmorItem(ModArmorMaterials.SWEET_BERRY_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(4))
                            .food(new FoodProperties.Builder().nutrition(14).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> SWEET_BERRY_BOOTS = ITEMS.register("sweet_berry_boots",
            () -> new EdibleArmorItem(ModArmorMaterials.SWEET_BERRY_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(4))
                            .food(new FoodProperties.Builder().nutrition(8).saturationModifier(0.1f).build())));

    // =========================================================================
    // Pumpkin Pie tools + armor (wood-equivalent)
    // =========================================================================
    public static final RegistryObject<Item> PUMPKIN_PIE_SWORD = ITEMS.register("pumpkin_pie_sword",
            () -> new EdibleSwordItem(ModToolTiers.PUMPKIN_PIE, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.PUMPKIN_PIE, 3, -2.4f))
                    .food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> PUMPKIN_PIE_PICKAXE = ITEMS.register("pumpkin_pie_pickaxe",
            () -> new EdiblePickaxeItem(ModToolTiers.PUMPKIN_PIE, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.PUMPKIN_PIE, 1, -2.8f))
                    .food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> PUMPKIN_PIE_SHOVEL = ITEMS.register("pumpkin_pie_shovel",
            () -> new EdibleShovelItem(ModToolTiers.PUMPKIN_PIE, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.PUMPKIN_PIE, 1.5f, -3f))
                    .food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> PUMPKIN_PIE_AXE = ITEMS.register("pumpkin_pie_axe",
            () -> new EdibleAxeItem(ModToolTiers.PUMPKIN_PIE, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.PUMPKIN_PIE, 6, -3.2f))
                    .food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> PUMPKIN_PIE_HOE = ITEMS.register("pumpkin_pie_hoe",
            () -> new EdibleHoeItem(ModToolTiers.PUMPKIN_PIE, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.PUMPKIN_PIE, 0, -3f))
                    .food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> PUMPKIN_PIE_HELMET = ITEMS.register("pumpkin_pie_helmet",
            () -> new EdibleArmorItem(ModArmorMaterials.PUMPKIN_PIE_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(4))
                            .food(new FoodProperties.Builder().nutrition(10).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> PUMPKIN_PIE_CHESTPLATE = ITEMS.register("pumpkin_pie_chestplate",
            () -> new EdibleArmorItem(ModArmorMaterials.PUMPKIN_PIE_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(4))
                            .food(new FoodProperties.Builder().nutrition(14).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> PUMPKIN_PIE_LEGGINGS = ITEMS.register("pumpkin_pie_leggings",
            () -> new EdibleArmorItem(ModArmorMaterials.PUMPKIN_PIE_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(4))
                            .food(new FoodProperties.Builder().nutrition(14).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> PUMPKIN_PIE_BOOTS = ITEMS.register("pumpkin_pie_boots",
            () -> new EdibleArmorItem(ModArmorMaterials.PUMPKIN_PIE_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(4))
                            .food(new FoodProperties.Builder().nutrition(8).saturationModifier(0.1f).build())));

    // =========================================================================
    // Mushroom tools + armor (stone-equivalent)
    // =========================================================================
    public static final RegistryObject<Item> MUSHROOM_SWORD = ITEMS.register("mushroom_sword",
            () -> new EdibleSwordItem(ModToolTiers.MUSHROOM, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.MUSHROOM, 3, -2.4f))
                    .food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> MUSHROOM_PICKAXE = ITEMS.register("mushroom_pickaxe",
            () -> new EdiblePickaxeItem(ModToolTiers.MUSHROOM, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.MUSHROOM, 1, -2.8f))
                    .food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> MUSHROOM_SHOVEL = ITEMS.register("mushroom_shovel",
            () -> new EdibleShovelItem(ModToolTiers.MUSHROOM, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.MUSHROOM, 1.5f, -3f))
                    .food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> MUSHROOM_AXE = ITEMS.register("mushroom_axe",
            () -> new EdibleAxeItem(ModToolTiers.MUSHROOM, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.MUSHROOM, 6, -3.2f))
                    .food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> MUSHROOM_HOE = ITEMS.register("mushroom_hoe",
            () -> new EdibleHoeItem(ModToolTiers.MUSHROOM, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.MUSHROOM, 0, -3f))
                    .food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> MUSHROOM_HELMET = ITEMS.register("mushroom_helmet",
            () -> new EdibleArmorItem(ModArmorMaterials.MUSHROOM_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(8))
                            .food(new FoodProperties.Builder().nutrition(10).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> MUSHROOM_CHESTPLATE = ITEMS.register("mushroom_chestplate",
            () -> new EdibleArmorItem(ModArmorMaterials.MUSHROOM_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(8))
                            .food(new FoodProperties.Builder().nutrition(14).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> MUSHROOM_LEGGINGS = ITEMS.register("mushroom_leggings",
            () -> new EdibleArmorItem(ModArmorMaterials.MUSHROOM_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(8))
                            .food(new FoodProperties.Builder().nutrition(14).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> MUSHROOM_BOOTS = ITEMS.register("mushroom_boots",
            () -> new EdibleArmorItem(ModArmorMaterials.MUSHROOM_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(8))
                            .food(new FoodProperties.Builder().nutrition(8).saturationModifier(0.1f).build())));

    // =========================================================================
    // Pufferfish tools + armor (stone-equivalent)
    // =========================================================================
    public static final RegistryObject<Item> PUFFERFISH_SWORD = ITEMS.register("pufferfish_sword",
            () -> new EdibleSwordItem(ModToolTiers.PUFFERFISH, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.PUFFERFISH, 3, -2.4f))
                    .food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> PUFFERFISH_PICKAXE = ITEMS.register("pufferfish_pickaxe",
            () -> new EdiblePickaxeItem(ModToolTiers.PUFFERFISH, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.PUFFERFISH, 1, -2.8f))
                    .food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> PUFFERFISH_SHOVEL = ITEMS.register("pufferfish_shovel",
            () -> new EdibleShovelItem(ModToolTiers.PUFFERFISH, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.PUFFERFISH, 1.5f, -3f))
                    .food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> PUFFERFISH_AXE = ITEMS.register("pufferfish_axe",
            () -> new EdibleAxeItem(ModToolTiers.PUFFERFISH, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.PUFFERFISH, 6, -3.2f))
                    .food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> PUFFERFISH_HOE = ITEMS.register("pufferfish_hoe",
            () -> new EdibleHoeItem(ModToolTiers.PUFFERFISH, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.PUFFERFISH, 0, -3f))
                    .food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> PUFFERFISH_HELMET = ITEMS.register("pufferfish_helmet",
            () -> new EdibleArmorItem(ModArmorMaterials.PUFFERFISH_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(6))
                            .food(new FoodProperties.Builder().nutrition(10).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> PUFFERFISH_CHESTPLATE = ITEMS.register("pufferfish_chestplate",
            () -> new EdibleArmorItem(ModArmorMaterials.PUFFERFISH_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(6))
                            .food(new FoodProperties.Builder().nutrition(14).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> PUFFERFISH_LEGGINGS = ITEMS.register("pufferfish_leggings",
            () -> new EdibleArmorItem(ModArmorMaterials.PUFFERFISH_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(6))
                            .food(new FoodProperties.Builder().nutrition(14).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> PUFFERFISH_BOOTS = ITEMS.register("pufferfish_boots",
            () -> new EdibleArmorItem(ModArmorMaterials.PUFFERFISH_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(6))
                            .food(new FoodProperties.Builder().nutrition(8).saturationModifier(0.1f).build())));

    // =========================================================================
    // Honey tools + armor (stone-equivalent)
    // =========================================================================
    public static final RegistryObject<Item> HONEY_SWORD = ITEMS.register("honey_sword",
            () -> new EdibleSwordItem(ModToolTiers.HONEY, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.HONEY, 3, -2.4f))
                    .food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> HONEY_PICKAXE = ITEMS.register("honey_pickaxe",
            () -> new EdiblePickaxeItem(ModToolTiers.HONEY, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.HONEY, 1, -2.8f))
                    .food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> HONEY_SHOVEL = ITEMS.register("honey_shovel",
            () -> new EdibleShovelItem(ModToolTiers.HONEY, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.HONEY, 1.5f, -3f))
                    .food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> HONEY_AXE = ITEMS.register("honey_axe",
            () -> new EdibleAxeItem(ModToolTiers.HONEY, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.HONEY, 6, -3.2f))
                    .food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> HONEY_HOE = ITEMS.register("honey_hoe",
            () -> new EdibleHoeItem(ModToolTiers.HONEY, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.HONEY, 0, -3f))
                    .food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> HONEY_HELMET = ITEMS.register("honey_helmet",
            () -> new EdibleArmorItem(ModArmorMaterials.HONEY_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(10))
                            .food(new FoodProperties.Builder().nutrition(10).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> HONEY_CHESTPLATE = ITEMS.register("honey_chestplate",
            () -> new EdibleArmorItem(ModArmorMaterials.HONEY_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(10))
                            .food(new FoodProperties.Builder().nutrition(14).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> HONEY_LEGGINGS = ITEMS.register("honey_leggings",
            () -> new EdibleArmorItem(ModArmorMaterials.HONEY_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(10))
                            .food(new FoodProperties.Builder().nutrition(14).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> HONEY_BOOTS = ITEMS.register("honey_boots",
            () -> new EdibleArmorItem(ModArmorMaterials.HONEY_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(10))
                            .food(new FoodProperties.Builder().nutrition(8).saturationModifier(0.1f).build())));

    // =========================================================================
    // Chorus Fruit tools + armor (iron-equivalent)
    // =========================================================================
    public static final RegistryObject<Item> CHORUS_FRUIT_SWORD = ITEMS.register("chorus_fruit_sword",
            () -> new EdibleSwordItem(ModToolTiers.CHORUS_FRUIT, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.CHORUS_FRUIT, 3, -2.4f))
                    .food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> CHORUS_FRUIT_PICKAXE = ITEMS.register("chorus_fruit_pickaxe",
            () -> new EdiblePickaxeItem(ModToolTiers.CHORUS_FRUIT, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.CHORUS_FRUIT, 1, -2.8f))
                    .food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> CHORUS_FRUIT_SHOVEL = ITEMS.register("chorus_fruit_shovel",
            () -> new EdibleShovelItem(ModToolTiers.CHORUS_FRUIT, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.CHORUS_FRUIT, 1.5f, -3f))
                    .food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> CHORUS_FRUIT_AXE = ITEMS.register("chorus_fruit_axe",
            () -> new EdibleAxeItem(ModToolTiers.CHORUS_FRUIT, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.CHORUS_FRUIT, 6, -3.2f))
                    .food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> CHORUS_FRUIT_HOE = ITEMS.register("chorus_fruit_hoe",
            () -> new EdibleHoeItem(ModToolTiers.CHORUS_FRUIT, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.CHORUS_FRUIT, 0, -3f))
                    .food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> CHORUS_FRUIT_HELMET = ITEMS.register("chorus_fruit_helmet",
            () -> new EdibleArmorItem(ModArmorMaterials.CHORUS_FRUIT_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(15))
                            .food(new FoodProperties.Builder().nutrition(10).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> CHORUS_FRUIT_CHESTPLATE = ITEMS.register("chorus_fruit_chestplate",
            () -> new EdibleArmorItem(ModArmorMaterials.CHORUS_FRUIT_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(15))
                            .food(new FoodProperties.Builder().nutrition(14).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> CHORUS_FRUIT_LEGGINGS = ITEMS.register("chorus_fruit_leggings",
            () -> new EdibleArmorItem(ModArmorMaterials.CHORUS_FRUIT_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(15))
                            .food(new FoodProperties.Builder().nutrition(14).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> CHORUS_FRUIT_BOOTS = ITEMS.register("chorus_fruit_boots",
            () -> new EdibleArmorItem(ModArmorMaterials.CHORUS_FRUIT_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(15))
                            .food(new FoodProperties.Builder().nutrition(8).saturationModifier(0.1f).build())));

    // =========================================================================
    // Golden Apple tools + armor (iron-equivalent, luxury)
    // =========================================================================
    public static final RegistryObject<Item> GOLDEN_APPLE_SWORD = ITEMS.register("golden_apple_sword",
            () -> new EdibleSwordItem(ModToolTiers.GOLDEN_APPLE, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.GOLDEN_APPLE, 3, -2.4f))
                    .food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> GOLDEN_APPLE_PICKAXE = ITEMS.register("golden_apple_pickaxe",
            () -> new EdiblePickaxeItem(ModToolTiers.GOLDEN_APPLE, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.GOLDEN_APPLE, 1, -2.8f))
                    .food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> GOLDEN_APPLE_SHOVEL = ITEMS.register("golden_apple_shovel",
            () -> new EdibleShovelItem(ModToolTiers.GOLDEN_APPLE, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.GOLDEN_APPLE, 1.5f, -3f))
                    .food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> GOLDEN_APPLE_AXE = ITEMS.register("golden_apple_axe",
            () -> new EdibleAxeItem(ModToolTiers.GOLDEN_APPLE, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.GOLDEN_APPLE, 6, -3.2f))
                    .food(new FoodProperties.Builder().nutrition(6).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> GOLDEN_APPLE_HOE = ITEMS.register("golden_apple_hoe",
            () -> new EdibleHoeItem(ModToolTiers.GOLDEN_APPLE, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.GOLDEN_APPLE, 0, -3f))
                    .food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> GOLDEN_APPLE_HELMET = ITEMS.register("golden_apple_helmet",
            () -> new EdibleArmorItem(ModArmorMaterials.GOLDEN_APPLE_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(18))
                            .food(new FoodProperties.Builder().nutrition(10).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> GOLDEN_APPLE_CHESTPLATE = ITEMS.register("golden_apple_chestplate",
            () -> new EdibleArmorItem(ModArmorMaterials.GOLDEN_APPLE_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(18))
                            .food(new FoodProperties.Builder().nutrition(14).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> GOLDEN_APPLE_LEGGINGS = ITEMS.register("golden_apple_leggings",
            () -> new EdibleArmorItem(ModArmorMaterials.GOLDEN_APPLE_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(18))
                            .food(new FoodProperties.Builder().nutrition(14).saturationModifier(0.1f).build())));
    public static final RegistryObject<Item> GOLDEN_APPLE_BOOTS = ITEMS.register("golden_apple_boots",
            () -> new EdibleArmorItem(ModArmorMaterials.GOLDEN_APPLE_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(18))
                            .food(new FoodProperties.Builder().nutrition(8).saturationModifier(0.1f).build())));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
