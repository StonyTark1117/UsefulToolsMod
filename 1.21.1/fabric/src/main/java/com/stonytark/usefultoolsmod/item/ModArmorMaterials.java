package com.stonytark.usefultoolsmod.item;

import com.stonytark.usefultoolsmod.UsefultoolsMod;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

public class ModArmorMaterials {
    public static RegistryEntry<ArmorMaterial> RGOLD_ARMOR_MATERIAL;
    public static RegistryEntry<ArmorMaterial> OBSIDIAN_ARMOR_MATERIAL;
    public static RegistryEntry<ArmorMaterial> EMERALD_ARMOR_MATERIAL;
    public static RegistryEntry<ArmorMaterial> OVERPOWER_ARMOR_MATERIAL;
    public static RegistryEntry<ArmorMaterial> HRED_ARMOR_MATERIAL;
    public static RegistryEntry<ArmorMaterial> HGLOW_ARMOR_MATERIAL;
    public static RegistryEntry<ArmorMaterial> RLAPIS_ARMOR_MATERIAL;
    public static RegistryEntry<ArmorMaterial> COAL_ARMOR_MATERIAL;
    public static RegistryEntry<ArmorMaterial> FNI_ARMOR_MATERIAL;
    public static RegistryEntry<ArmorMaterial> CAMETHYST_ARMOR_MATERIAL;
    public static RegistryEntry<ArmorMaterial> ICE_ARMOR_MATERIAL;
    public static RegistryEntry<ArmorMaterial> PQUARTZ_ARMOR_MATERIAL;
    public static RegistryEntry<ArmorMaterial> PPRISM_ARMOR_MATERIAL;
    public static RegistryEntry<ArmorMaterial> CAKE_ARMOR_MATERIAL;
    public static RegistryEntry<ArmorMaterial> BREAD_ARMOR_MATERIAL;
    public static RegistryEntry<ArmorMaterial> DRIED_KELP_ARMOR_MATERIAL;
    public static RegistryEntry<ArmorMaterial> ROTTEN_FLESH_ARMOR_MATERIAL;
    public static RegistryEntry<ArmorMaterial> MELON_ARMOR_MATERIAL;
    public static RegistryEntry<ArmorMaterial> SWEET_BERRY_ARMOR_MATERIAL;
    public static RegistryEntry<ArmorMaterial> PUMPKIN_PIE_ARMOR_MATERIAL;
    public static RegistryEntry<ArmorMaterial> MUSHROOM_ARMOR_MATERIAL;
    public static RegistryEntry<ArmorMaterial> PUFFERFISH_ARMOR_MATERIAL;
    public static RegistryEntry<ArmorMaterial> HONEY_ARMOR_MATERIAL;
    public static RegistryEntry<ArmorMaterial> CHORUS_FRUIT_ARMOR_MATERIAL;
    public static RegistryEntry<ArmorMaterial> GOLDEN_APPLE_ARMOR_MATERIAL;
    public static RegistryEntry<ArmorMaterial> RABBIT_HIDE_ARMOR_MATERIAL;
    public static RegistryEntry<ArmorMaterial> CACTUS_ARMOR_MATERIAL;
    public static RegistryEntry<ArmorMaterial> BONE_ARMOR_MATERIAL;
    public static RegistryEntry<ArmorMaterial> CLAY_ARMOR_MATERIAL;
    public static RegistryEntry<ArmorMaterial> BRICK_ARMOR_MATERIAL;
    public static RegistryEntry<ArmorMaterial> NETHER_BRICK_ARMOR_MATERIAL;
    public static RegistryEntry<ArmorMaterial> COPPER_ARMOR_MATERIAL;
    public static RegistryEntry<ArmorMaterial> PHANTOM_ARMOR_MATERIAL;
    public static RegistryEntry<ArmorMaterial> MAGMA_CREAM_ARMOR_MATERIAL;
    public static RegistryEntry<ArmorMaterial> SLIME_ARMOR_MATERIAL;
    public static RegistryEntry<ArmorMaterial> BLAZE_ARMOR_MATERIAL;
    public static RegistryEntry<ArmorMaterial> NAUTILUS_ARMOR_MATERIAL;
    public static RegistryEntry<ArmorMaterial> PURPUR_ARMOR_MATERIAL;
    public static RegistryEntry<ArmorMaterial> GHAST_TEAR_ARMOR_MATERIAL;
    public static RegistryEntry<ArmorMaterial> EYE_OF_ENDER_ARMOR_MATERIAL;
    public static RegistryEntry<ArmorMaterial> SHULKER_ARMOR_MATERIAL;
    public static RegistryEntry<ArmorMaterial> TURTLE_SCUTE_ARMOR_MATERIAL;
    public static RegistryEntry<ArmorMaterial> ECHO_SHARD_ARMOR_MATERIAL;
    public static RegistryEntry<ArmorMaterial> DRAGON_BREATH_ARMOR_MATERIAL;
    public static RegistryEntry<ArmorMaterial> ECTO_ARMOR_MATERIAL;

    public static void register() {
        RGOLD_ARMOR_MATERIAL = register("rgold", Util.make(new EnumMap<>(ArmorItem.Type.class), m -> {
            m.put(ArmorItem.Type.BOOTS, 3);
            m.put(ArmorItem.Type.LEGGINGS, 5);
            m.put(ArmorItem.Type.CHESTPLATE, 5);
            m.put(ArmorItem.Type.HELMET, 3);
            m.put(ArmorItem.Type.BODY, 9);
        }), 25, 2f, 0.1f, () -> ModItems.RGOLD);

        OBSIDIAN_ARMOR_MATERIAL = register("obsidian", Util.make(new EnumMap<>(ArmorItem.Type.class), m -> {
            m.put(ArmorItem.Type.BOOTS, 6);
            m.put(ArmorItem.Type.LEGGINGS, 7);
            m.put(ArmorItem.Type.CHESTPLATE, 9);
            m.put(ArmorItem.Type.HELMET, 6);
            m.put(ArmorItem.Type.BODY, 10);
        }), 10, 4f, 0.4f, () -> ModItems.OBINGOT);

        EMERALD_ARMOR_MATERIAL = register("emerald", Util.make(new EnumMap<>(ArmorItem.Type.class), m -> {
            m.put(ArmorItem.Type.BOOTS, 4);
            m.put(ArmorItem.Type.LEGGINGS, 6);
            m.put(ArmorItem.Type.CHESTPLATE, 8);
            m.put(ArmorItem.Type.HELMET, 4);
            m.put(ArmorItem.Type.BODY, 9);
        }), 30, 2f, 0.15f, () -> ModItems.SEM);

        OVERPOWER_ARMOR_MATERIAL = register("overpower", Util.make(new EnumMap<>(ArmorItem.Type.class), m -> {
            m.put(ArmorItem.Type.BOOTS, 15);
            m.put(ArmorItem.Type.LEGGINGS, 15);
            m.put(ArmorItem.Type.CHESTPLATE, 15);
            m.put(ArmorItem.Type.HELMET, 15);
            m.put(ArmorItem.Type.BODY, 15);
        }), 50, 8f, 1f, () -> ModItems.OBINGOT);

        HRED_ARMOR_MATERIAL = register("hred", Util.make(new EnumMap<>(ArmorItem.Type.class), m -> {
            m.put(ArmorItem.Type.BOOTS, 2);
            m.put(ArmorItem.Type.LEGGINGS, 4);
            m.put(ArmorItem.Type.CHESTPLATE, 4);
            m.put(ArmorItem.Type.HELMET, 3);
            m.put(ArmorItem.Type.BODY, 6);
        }), 23, 1.1f, 0.08f, () -> ModItems.HRED);

        HGLOW_ARMOR_MATERIAL = register("hglow", Util.make(new EnumMap<>(ArmorItem.Type.class), m -> {
            m.put(ArmorItem.Type.BOOTS, 1);
            m.put(ArmorItem.Type.LEGGINGS, 4);
            m.put(ArmorItem.Type.CHESTPLATE, 5);
            m.put(ArmorItem.Type.HELMET, 2);
            m.put(ArmorItem.Type.BODY, 6);
        }), 25, 0.0f, 0.0f, () -> ModItems.HGLOW);

        RLAPIS_ARMOR_MATERIAL = register("rlapis", Util.make(new EnumMap<>(ArmorItem.Type.class), m -> {
            m.put(ArmorItem.Type.BOOTS, 3);
            m.put(ArmorItem.Type.LEGGINGS, 6);
            m.put(ArmorItem.Type.CHESTPLATE, 4);
            m.put(ArmorItem.Type.HELMET, 4);
            m.put(ArmorItem.Type.BODY, 8);
        }), 32, 1.6f, 0.15f, () -> ModItems.RLAPIS);

        COAL_ARMOR_MATERIAL = register("coal", Util.make(new EnumMap<>(ArmorItem.Type.class), m -> {
            m.put(ArmorItem.Type.BOOTS, 1);
            m.put(ArmorItem.Type.LEGGINGS, 2);
            m.put(ArmorItem.Type.CHESTPLATE, 3);
            m.put(ArmorItem.Type.HELMET, 1);
            m.put(ArmorItem.Type.BODY, 4);
        }), 8, 0f, 0f, () -> ModItems.HARDENED_COAL);

        FNI_ARMOR_MATERIAL = register("fni", Util.make(new EnumMap<>(ArmorItem.Type.class), m -> {
            m.put(ArmorItem.Type.BOOTS, 1);
            m.put(ArmorItem.Type.LEGGINGS, 4);
            m.put(ArmorItem.Type.CHESTPLATE, 5);
            m.put(ArmorItem.Type.HELMET, 2);
            m.put(ArmorItem.Type.BODY, 6);
        }), 9, 0.0f, 0.0f, () -> net.minecraft.item.Items.FLINT);

        CAMETHYST_ARMOR_MATERIAL = register("camethyst", Util.make(new EnumMap<>(ArmorItem.Type.class), m -> {
            m.put(ArmorItem.Type.BOOTS, 2);
            m.put(ArmorItem.Type.LEGGINGS, 5);
            m.put(ArmorItem.Type.CHESTPLATE, 6);
            m.put(ArmorItem.Type.HELMET, 2);
            m.put(ArmorItem.Type.BODY, 7);
        }), 14, 0.5f, 0.0f, () -> ModItems.CALCIFIED_AMETHYST);

        ICE_ARMOR_MATERIAL = register("ice", Util.make(new EnumMap<>(ArmorItem.Type.class), m -> {
            m.put(ArmorItem.Type.BOOTS, 1);
            m.put(ArmorItem.Type.LEGGINGS, 3);
            m.put(ArmorItem.Type.CHESTPLATE, 4);
            m.put(ArmorItem.Type.HELMET, 1);
            m.put(ArmorItem.Type.BODY, 5);
        }), 8, 0.0f, 0.0f, () -> ModItems.GLACIAL_SHARD);

        PQUARTZ_ARMOR_MATERIAL = register("pquartz", Util.make(new EnumMap<>(ArmorItem.Type.class), m -> {
            m.put(ArmorItem.Type.BOOTS, 2);
            m.put(ArmorItem.Type.LEGGINGS, 5);
            m.put(ArmorItem.Type.CHESTPLATE, 6);
            m.put(ArmorItem.Type.HELMET, 2);
            m.put(ArmorItem.Type.BODY, 7);
        }), 10, 0.5f, 0.05f, () -> ModItems.POLISHED_QUARTZ);

        PPRISM_ARMOR_MATERIAL = register("pprism", Util.make(new EnumMap<>(ArmorItem.Type.class), m -> {
            m.put(ArmorItem.Type.BOOTS, 2);
            m.put(ArmorItem.Type.LEGGINGS, 5);
            m.put(ArmorItem.Type.CHESTPLATE, 6);
            m.put(ArmorItem.Type.HELMET, 2);
            m.put(ArmorItem.Type.BODY, 7);
        }), 12, 0.0f, 0.0f, () -> ModItems.POLISHED_PRISMARINE);

        CAKE_ARMOR_MATERIAL = register("cake", Util.make(new EnumMap<>(ArmorItem.Type.class), m -> {
            m.put(ArmorItem.Type.BOOTS, 0);
            m.put(ArmorItem.Type.LEGGINGS, 1);
            m.put(ArmorItem.Type.CHESTPLATE, 1);
            m.put(ArmorItem.Type.HELMET, 0);
            m.put(ArmorItem.Type.BODY, 1);
        }), 1, 0f, 0f, () -> net.minecraft.item.Items.CAKE);

        BREAD_ARMOR_MATERIAL = register("bread", Util.make(new EnumMap<>(ArmorItem.Type.class), m -> {
            m.put(ArmorItem.Type.BOOTS, 0);
            m.put(ArmorItem.Type.LEGGINGS, 1);
            m.put(ArmorItem.Type.CHESTPLATE, 2);
            m.put(ArmorItem.Type.HELMET, 0);
            m.put(ArmorItem.Type.BODY, 1);
        }), 2, 0f, 0f, () -> net.minecraft.item.Items.BREAD);

        DRIED_KELP_ARMOR_MATERIAL = register("dried_kelp", Util.make(new EnumMap<>(ArmorItem.Type.class), m -> {
            m.put(ArmorItem.Type.BOOTS, 1);
            m.put(ArmorItem.Type.LEGGINGS, 1);
            m.put(ArmorItem.Type.CHESTPLATE, 1);
            m.put(ArmorItem.Type.HELMET, 0);
            m.put(ArmorItem.Type.BODY, 1);
        }), 1, 0f, 0f, () -> net.minecraft.item.Items.DRIED_KELP);

        ROTTEN_FLESH_ARMOR_MATERIAL = register("rotten_flesh", Util.make(new EnumMap<>(ArmorItem.Type.class), m -> {
            m.put(ArmorItem.Type.BOOTS, 0);
            m.put(ArmorItem.Type.LEGGINGS, 2);
            m.put(ArmorItem.Type.CHESTPLATE, 1);
            m.put(ArmorItem.Type.HELMET, 0);
            m.put(ArmorItem.Type.BODY, 1);
        }), 3, 0f, 0f, () -> net.minecraft.item.Items.ROTTEN_FLESH);

        MELON_ARMOR_MATERIAL = register("melon", Util.make(new EnumMap<>(ArmorItem.Type.class), m -> {
            m.put(ArmorItem.Type.BOOTS, 1);
            m.put(ArmorItem.Type.LEGGINGS, 2);
            m.put(ArmorItem.Type.CHESTPLATE, 3);
            m.put(ArmorItem.Type.HELMET, 1);
            m.put(ArmorItem.Type.BODY, 2);
        }), 4, 0f, 0f, () -> net.minecraft.item.Items.MELON_SLICE);

        SWEET_BERRY_ARMOR_MATERIAL = register("sweet_berry", Util.make(new EnumMap<>(ArmorItem.Type.class), m -> {
            m.put(ArmorItem.Type.BOOTS, 1);
            m.put(ArmorItem.Type.LEGGINGS, 2);
            m.put(ArmorItem.Type.CHESTPLATE, 2);
            m.put(ArmorItem.Type.HELMET, 1);
            m.put(ArmorItem.Type.BODY, 2);
        }), 5, 0f, 0f, () -> net.minecraft.item.Items.SWEET_BERRIES);

        PUMPKIN_PIE_ARMOR_MATERIAL = register("pumpkin_pie", Util.make(new EnumMap<>(ArmorItem.Type.class), m -> {
            m.put(ArmorItem.Type.BOOTS, 1);
            m.put(ArmorItem.Type.LEGGINGS, 2);
            m.put(ArmorItem.Type.CHESTPLATE, 2);
            m.put(ArmorItem.Type.HELMET, 2);
            m.put(ArmorItem.Type.BODY, 2);
        }), 7, 0f, 0f, () -> net.minecraft.item.Items.PUMPKIN_PIE);

        MUSHROOM_ARMOR_MATERIAL = register("mushroom", Util.make(new EnumMap<>(ArmorItem.Type.class), m -> {
            m.put(ArmorItem.Type.BOOTS, 1);
            m.put(ArmorItem.Type.LEGGINGS, 3);
            m.put(ArmorItem.Type.CHESTPLATE, 4);
            m.put(ArmorItem.Type.HELMET, 2);
            m.put(ArmorItem.Type.BODY, 3);
        }), 10, 0f, 0f, () -> net.minecraft.item.Items.RED_MUSHROOM);

        PUFFERFISH_ARMOR_MATERIAL = register("pufferfish", Util.make(new EnumMap<>(ArmorItem.Type.class), m -> {
            m.put(ArmorItem.Type.BOOTS, 1);
            m.put(ArmorItem.Type.LEGGINGS, 3);
            m.put(ArmorItem.Type.CHESTPLATE, 3);
            m.put(ArmorItem.Type.HELMET, 1);
            m.put(ArmorItem.Type.BODY, 3);
        }), 8, 0f, 0f, () -> net.minecraft.item.Items.PUFFERFISH);

        HONEY_ARMOR_MATERIAL = register("honey", Util.make(new EnumMap<>(ArmorItem.Type.class), m -> {
            m.put(ArmorItem.Type.BOOTS, 2);
            m.put(ArmorItem.Type.LEGGINGS, 3);
            m.put(ArmorItem.Type.CHESTPLATE, 4);
            m.put(ArmorItem.Type.HELMET, 2);
            m.put(ArmorItem.Type.BODY, 3);
        }), 10, 0f, 0f, () -> net.minecraft.item.Items.HONEY_BOTTLE);

        CHORUS_FRUIT_ARMOR_MATERIAL = register("chorus_fruit", Util.make(new EnumMap<>(ArmorItem.Type.class), m -> {
            m.put(ArmorItem.Type.BOOTS, 2);
            m.put(ArmorItem.Type.LEGGINGS, 5);
            m.put(ArmorItem.Type.CHESTPLATE, 6);
            m.put(ArmorItem.Type.HELMET, 2);
            m.put(ArmorItem.Type.BODY, 5);
        }), 15, 0.5f, 0f, () -> net.minecraft.item.Items.CHORUS_FRUIT);

        GOLDEN_APPLE_ARMOR_MATERIAL = register("golden_apple", Util.make(new EnumMap<>(ArmorItem.Type.class), m -> {
            m.put(ArmorItem.Type.BOOTS, 2);
            m.put(ArmorItem.Type.LEGGINGS, 5);
            m.put(ArmorItem.Type.CHESTPLATE, 6);
            m.put(ArmorItem.Type.HELMET, 3);
            m.put(ArmorItem.Type.BODY, 5);
        }), 22, 1.0f, 0f, () -> net.minecraft.item.Items.GOLDEN_APPLE);

        RABBIT_HIDE_ARMOR_MATERIAL = register("rabbit_hide", Util.make(new EnumMap<>(ArmorItem.Type.class), m -> {
            m.put(ArmorItem.Type.BOOTS, 1);
            m.put(ArmorItem.Type.LEGGINGS, 2);
            m.put(ArmorItem.Type.CHESTPLATE, 3);
            m.put(ArmorItem.Type.HELMET, 1);
            m.put(ArmorItem.Type.BODY, 3);
        }), 12, 0f, 0f, () -> net.minecraft.item.Items.RABBIT_HIDE);

        CACTUS_ARMOR_MATERIAL = register("cactus", Util.make(new EnumMap<>(ArmorItem.Type.class), m -> {
            m.put(ArmorItem.Type.BOOTS, 1);
            m.put(ArmorItem.Type.LEGGINGS, 2);
            m.put(ArmorItem.Type.CHESTPLATE, 3);
            m.put(ArmorItem.Type.HELMET, 1);
            m.put(ArmorItem.Type.BODY, 3);
        }), 5, 0f, 0f, () -> net.minecraft.item.Items.CACTUS);

        BONE_ARMOR_MATERIAL = register("bone", Util.make(new EnumMap<>(ArmorItem.Type.class), m -> {
            m.put(ArmorItem.Type.BOOTS, 1);
            m.put(ArmorItem.Type.LEGGINGS, 3);
            m.put(ArmorItem.Type.CHESTPLATE, 4);
            m.put(ArmorItem.Type.HELMET, 1);
            m.put(ArmorItem.Type.BODY, 4);
        }), 6, 0f, 0f, () -> net.minecraft.item.Items.BONE);

        CLAY_ARMOR_MATERIAL = register("clay", Util.make(new EnumMap<>(ArmorItem.Type.class), m -> {
            m.put(ArmorItem.Type.BOOTS, 1);
            m.put(ArmorItem.Type.LEGGINGS, 2);
            m.put(ArmorItem.Type.CHESTPLATE, 3);
            m.put(ArmorItem.Type.HELMET, 1);
            m.put(ArmorItem.Type.BODY, 3);
        }), 8, 0f, 0f, () -> net.minecraft.item.Items.CLAY_BALL);

        BRICK_ARMOR_MATERIAL = register("brick", Util.make(new EnumMap<>(ArmorItem.Type.class), m -> {
            m.put(ArmorItem.Type.BOOTS, 1);
            m.put(ArmorItem.Type.LEGGINGS, 4);
            m.put(ArmorItem.Type.CHESTPLATE, 5);
            m.put(ArmorItem.Type.HELMET, 2);
            m.put(ArmorItem.Type.BODY, 5);
        }), 5, 0.5f, 0f, () -> net.minecraft.item.Items.BRICK);

        NETHER_BRICK_ARMOR_MATERIAL = register("nether_brick", Util.make(new EnumMap<>(ArmorItem.Type.class), m -> {
            m.put(ArmorItem.Type.BOOTS, 1);
            m.put(ArmorItem.Type.LEGGINGS, 4);
            m.put(ArmorItem.Type.CHESTPLATE, 5);
            m.put(ArmorItem.Type.HELMET, 2);
            m.put(ArmorItem.Type.BODY, 5);
        }), 5, 0.5f, 0f, () -> net.minecraft.item.Items.NETHER_BRICK);

        COPPER_ARMOR_MATERIAL = register("copper", Util.make(new EnumMap<>(ArmorItem.Type.class), m -> {
            m.put(ArmorItem.Type.BOOTS, 2);
            m.put(ArmorItem.Type.LEGGINGS, 4);
            m.put(ArmorItem.Type.CHESTPLATE, 5);
            m.put(ArmorItem.Type.HELMET, 2);
            m.put(ArmorItem.Type.BODY, 5);
        }), 8, 0f, 0f, () -> net.minecraft.item.Items.COPPER_INGOT);

        PHANTOM_ARMOR_MATERIAL = register("phantom", Util.make(new EnumMap<>(ArmorItem.Type.class), m -> {
            m.put(ArmorItem.Type.BOOTS, 2);
            m.put(ArmorItem.Type.LEGGINGS, 4);
            m.put(ArmorItem.Type.CHESTPLATE, 5);
            m.put(ArmorItem.Type.HELMET, 2);
            m.put(ArmorItem.Type.BODY, 5);
        }), 12, 0f, 0f, () -> net.minecraft.item.Items.PHANTOM_MEMBRANE);

        MAGMA_CREAM_ARMOR_MATERIAL = register("magma_cream", Util.make(new EnumMap<>(ArmorItem.Type.class), m -> {
            m.put(ArmorItem.Type.BOOTS, 2);
            m.put(ArmorItem.Type.LEGGINGS, 4);
            m.put(ArmorItem.Type.CHESTPLATE, 5);
            m.put(ArmorItem.Type.HELMET, 2);
            m.put(ArmorItem.Type.BODY, 5);
        }), 8, 0.5f, 0f, () -> net.minecraft.item.Items.MAGMA_CREAM);

        SLIME_ARMOR_MATERIAL = register("slime", Util.make(new EnumMap<>(ArmorItem.Type.class), m -> {
            m.put(ArmorItem.Type.BOOTS, 2);
            m.put(ArmorItem.Type.LEGGINGS, 3);
            m.put(ArmorItem.Type.CHESTPLATE, 4);
            m.put(ArmorItem.Type.HELMET, 2);
            m.put(ArmorItem.Type.BODY, 4);
        }), 10, 0f, 0.1f, () -> net.minecraft.item.Items.SLIME_BALL);

        BLAZE_ARMOR_MATERIAL = register("blaze", Util.make(new EnumMap<>(ArmorItem.Type.class), m -> {
            m.put(ArmorItem.Type.BOOTS, 2);
            m.put(ArmorItem.Type.LEGGINGS, 5);
            m.put(ArmorItem.Type.CHESTPLATE, 6);
            m.put(ArmorItem.Type.HELMET, 2);
            m.put(ArmorItem.Type.BODY, 6);
        }), 10, 0.5f, 0f, () -> net.minecraft.item.Items.BLAZE_ROD);

        NAUTILUS_ARMOR_MATERIAL = register("nautilus", Util.make(new EnumMap<>(ArmorItem.Type.class), m -> {
            m.put(ArmorItem.Type.BOOTS, 2);
            m.put(ArmorItem.Type.LEGGINGS, 5);
            m.put(ArmorItem.Type.CHESTPLATE, 6);
            m.put(ArmorItem.Type.HELMET, 2);
            m.put(ArmorItem.Type.BODY, 6);
        }), 14, 0.5f, 0f, () -> net.minecraft.item.Items.NAUTILUS_SHELL);

        PURPUR_ARMOR_MATERIAL = register("purpur", Util.make(new EnumMap<>(ArmorItem.Type.class), m -> {
            m.put(ArmorItem.Type.BOOTS, 2);
            m.put(ArmorItem.Type.LEGGINGS, 5);
            m.put(ArmorItem.Type.CHESTPLATE, 6);
            m.put(ArmorItem.Type.HELMET, 2);
            m.put(ArmorItem.Type.BODY, 6);
        }), 12, 0.5f, 0f, () -> net.minecraft.item.Items.POPPED_CHORUS_FRUIT);

        GHAST_TEAR_ARMOR_MATERIAL = register("ghast_tear", Util.make(new EnumMap<>(ArmorItem.Type.class), m -> {
            m.put(ArmorItem.Type.BOOTS, 2);
            m.put(ArmorItem.Type.LEGGINGS, 5);
            m.put(ArmorItem.Type.CHESTPLATE, 6);
            m.put(ArmorItem.Type.HELMET, 3);
            m.put(ArmorItem.Type.BODY, 6);
        }), 18, 1.0f, 0f, () -> net.minecraft.item.Items.GHAST_TEAR);

        EYE_OF_ENDER_ARMOR_MATERIAL = register("eye_of_ender", Util.make(new EnumMap<>(ArmorItem.Type.class), m -> {
            m.put(ArmorItem.Type.BOOTS, 2);
            m.put(ArmorItem.Type.LEGGINGS, 5);
            m.put(ArmorItem.Type.CHESTPLATE, 7);
            m.put(ArmorItem.Type.HELMET, 3);
            m.put(ArmorItem.Type.BODY, 6);
        }), 20, 1.0f, 0.05f, () -> net.minecraft.item.Items.ENDER_EYE);

        SHULKER_ARMOR_MATERIAL = register("shulker", Util.make(new EnumMap<>(ArmorItem.Type.class), m -> {
            m.put(ArmorItem.Type.BOOTS, 3);
            m.put(ArmorItem.Type.LEGGINGS, 6);
            m.put(ArmorItem.Type.CHESTPLATE, 7);
            m.put(ArmorItem.Type.HELMET, 3);
            m.put(ArmorItem.Type.BODY, 7);
        }), 16, 1.5f, 0.1f, () -> net.minecraft.item.Items.SHULKER_SHELL);

        TURTLE_SCUTE_ARMOR_MATERIAL = register("turtle_scute", Util.make(new EnumMap<>(ArmorItem.Type.class), m -> {
            m.put(ArmorItem.Type.BOOTS, 2);
            m.put(ArmorItem.Type.LEGGINGS, 5);
            m.put(ArmorItem.Type.CHESTPLATE, 7);
            m.put(ArmorItem.Type.HELMET, 3);
            m.put(ArmorItem.Type.BODY, 7);
        }), 16, 1.5f, 0.05f, () -> net.minecraft.item.Items.TURTLE_SCUTE);

        ECHO_SHARD_ARMOR_MATERIAL = register("echo_shard", Util.make(new EnumMap<>(ArmorItem.Type.class), m -> {
            m.put(ArmorItem.Type.BOOTS, 3);
            m.put(ArmorItem.Type.LEGGINGS, 6);
            m.put(ArmorItem.Type.CHESTPLATE, 7);
            m.put(ArmorItem.Type.HELMET, 3);
            m.put(ArmorItem.Type.BODY, 7);
        }), 18, 2.0f, 0.05f, () -> net.minecraft.item.Items.ECHO_SHARD);

        DRAGON_BREATH_ARMOR_MATERIAL = register("dragon_breath", Util.make(new EnumMap<>(ArmorItem.Type.class), m -> {
            m.put(ArmorItem.Type.BOOTS, 3);
            m.put(ArmorItem.Type.LEGGINGS, 6);
            m.put(ArmorItem.Type.CHESTPLATE, 8);
            m.put(ArmorItem.Type.HELMET, 3);
            m.put(ArmorItem.Type.BODY, 7);
        }), 20, 2.5f, 0.1f, () -> net.minecraft.item.Items.DRAGON_BREATH);

        ECTO_ARMOR_MATERIAL = register("ecto", Util.make(new EnumMap<>(ArmorItem.Type.class), m -> {
            m.put(ArmorItem.Type.BOOTS, 2);
            m.put(ArmorItem.Type.LEGGINGS, 5);
            m.put(ArmorItem.Type.CHESTPLATE, 6);
            m.put(ArmorItem.Type.HELMET, 2);
            m.put(ArmorItem.Type.BODY, 7);
        }), 16, 0.5f, 0.0f, () -> ModItems.REFINED_ECTOPLASM);
    }

    private static RegistryEntry<ArmorMaterial> register(String name, EnumMap<ArmorItem.Type, Integer> protection,
                                                          int enchantability, float toughness, float knockbackResistance,
                                                          Supplier<Item> repairItem) {
        Identifier id = Identifier.of(UsefultoolsMod.MOD_ID, name);
        RegistryEntry<SoundEvent> equipSound = SoundEvents.ITEM_ARMOR_EQUIP_GENERIC;
        List<ArmorMaterial.Layer> layers = List.of(new ArmorMaterial.Layer(id));
        ArmorMaterial material = new ArmorMaterial(protection, enchantability, equipSound,
                () -> Ingredient.ofItems(repairItem.get()), layers, toughness, knockbackResistance);
        return Registry.registerReference(Registries.ARMOR_MATERIAL, id, material);
    }
}
