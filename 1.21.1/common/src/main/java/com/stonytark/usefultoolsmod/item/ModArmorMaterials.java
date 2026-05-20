package com.stonytark.usefultoolsmod.item;

import com.stonytark.usefultoolsmod.UsefultoolsMod;
import com.stonytark.usefultoolsmod.block.ModBlocks;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

public class ModArmorMaterials {
    public static final Holder<ArmorMaterial> RGOLD_ARMOR_MATERIAL = register("rgold", Util.make(new EnumMap<>(ArmorItem.Type.class),
            attribute -> {
                attribute.put(ArmorItem.Type.BOOTS, 3);
                attribute.put(ArmorItem.Type.LEGGINGS, 5);
                attribute.put(ArmorItem.Type.CHESTPLATE, 5);
                attribute.put(ArmorItem.Type.HELMET, 3);
                attribute.put(ArmorItem.Type.BODY, 9);
            }), 25, 2f, 0.1f, () -> ModItems.RGOLD.get());

    public static final Holder<ArmorMaterial> OBSIDIAN_ARMOR_MATERIAL = register("obsidian", Util.make(new EnumMap<>(ArmorItem.Type.class),
            attribute -> {
                attribute.put(ArmorItem.Type.BOOTS, 6);
                attribute.put(ArmorItem.Type.LEGGINGS, 7);
                attribute.put(ArmorItem.Type.CHESTPLATE, 9);
                attribute.put(ArmorItem.Type.HELMET, 6);
                attribute.put(ArmorItem.Type.BODY, 10);
            }), 10, 4f, 0.4f, () -> ModItems.OBINGOT.get());

    public static final Holder<ArmorMaterial> EMERALD_ARMOR_MATERIAL = register("emerald", Util.make(new EnumMap<>(ArmorItem.Type.class),
            attribute -> {
                attribute.put(ArmorItem.Type.BOOTS, 4);
                attribute.put(ArmorItem.Type.LEGGINGS, 6);
                attribute.put(ArmorItem.Type.CHESTPLATE, 8);
                attribute.put(ArmorItem.Type.HELMET, 4);
                attribute.put(ArmorItem.Type.BODY, 9);
            }), 30, 2f, 0.15f, () -> ModItems.SEM.get());

    public static final Holder<ArmorMaterial> OVERPOWER_ARMOR_MATERIAL = register("overpower", Util.make(new EnumMap<>(ArmorItem.Type.class),
            attribute -> {
                attribute.put(ArmorItem.Type.BOOTS, 15);
                attribute.put(ArmorItem.Type.LEGGINGS, 15);
                attribute.put(ArmorItem.Type.CHESTPLATE, 15);
                attribute.put(ArmorItem.Type.HELMET, 15);
                attribute.put(ArmorItem.Type.BODY, 15);
            }), 50, 8f, 1f, () -> ModItems.OBINGOT.get());

    public static final Holder<ArmorMaterial> HRED_ARMOR_MATERIAL = register("hred", Util.make(new EnumMap<>(ArmorItem.Type.class),
            attribute -> {
                attribute.put(ArmorItem.Type.BOOTS, 2);
                attribute.put(ArmorItem.Type.LEGGINGS, 4);
                attribute.put(ArmorItem.Type.CHESTPLATE, 4);
                attribute.put(ArmorItem.Type.HELMET, 3);
                attribute.put(ArmorItem.Type.BODY, 6);
            }), 23, 1.1f, 0.08f, () -> ModItems.HRED.get());

    /**
     * Hardened Glowstone — lighter protection than HRED but more enchantable.
     * Toughness and knockback resistance sacrificed for magical affinity.
     * Complement to HRED: HRED for power, HGLOW for utility/enchanting.
     */
    public static final Holder<ArmorMaterial> HGLOW_ARMOR_MATERIAL = register("hglow", Util.make(new EnumMap<>(ArmorItem.Type.class),
            attribute -> {
                attribute.put(ArmorItem.Type.BOOTS, 1);
                attribute.put(ArmorItem.Type.LEGGINGS, 4);
                attribute.put(ArmorItem.Type.CHESTPLATE, 5);
                attribute.put(ArmorItem.Type.HELMET, 2);
                attribute.put(ArmorItem.Type.BODY, 6);
            }), 25, 0.0f, 0.0f, () -> ModItems.HGLOW.get());

    public static final Holder<ArmorMaterial> RLAPIS_ARMOR_MATERIAL = register("rlapis", Util.make(new EnumMap<>(ArmorItem.Type.class),
            attribute -> {
                attribute.put(ArmorItem.Type.BOOTS, 3);
                attribute.put(ArmorItem.Type.LEGGINGS, 6);
                attribute.put(ArmorItem.Type.CHESTPLATE, 4);
                attribute.put(ArmorItem.Type.HELMET, 4);
                attribute.put(ArmorItem.Type.BODY, 8);
            }), 32, 1.6f, 0.15f, () -> ModItems.RLAPIS.get());

    /**
     * Coal armor material — sits between leather and chainmail.
     * Low protection, low durability multiplier.
     */
    public static final Holder<ArmorMaterial> COAL_ARMOR_MATERIAL = register("coal", Util.make(new EnumMap<>(ArmorItem.Type.class),
            attribute -> {
                attribute.put(ArmorItem.Type.BOOTS, 1);
                attribute.put(ArmorItem.Type.LEGGINGS, 2);
                attribute.put(ArmorItem.Type.CHESTPLATE, 3);
                attribute.put(ArmorItem.Type.HELMET, 1);
                attribute.put(ArmorItem.Type.BODY, 4);
            }), 8, 0f, 0f, () -> ModItems.HARDENED_COAL.get());

    /**
     * Flint-Iron (FNI) armor — slightly less protective than vanilla iron.
     * Iron: boots 2, legs 5, chest 6, helm 2 (total 15). FNI: 1/4/5/2 (total 12).
     * Flint shards are sharp but the assembly is imperfect; enchantability matches iron.
     */
    public static final Holder<ArmorMaterial> FNI_ARMOR_MATERIAL = register("fni", Util.make(new EnumMap<>(ArmorItem.Type.class),
            attribute -> {
                attribute.put(ArmorItem.Type.BOOTS, 1);
                attribute.put(ArmorItem.Type.LEGGINGS, 4);
                attribute.put(ArmorItem.Type.CHESTPLATE, 5);
                attribute.put(ArmorItem.Type.HELMET, 2);
                attribute.put(ArmorItem.Type.BODY, 6);
            }), 9, 0.0f, 0.0f, () -> Items.FLINT);

    // ── Crystal / element polished armor materials (iron-level) ─────────────────
    // Vanilla iron: boots 2, legs 5, chest 6, helm 2 = 15 pts, ×15 multiplier
    // These sit at or just around vanilla iron, below any mod-custom material.

    /** Calcified Amethyst — iron-equivalent protection, higher enchantability. */
    public static final Holder<ArmorMaterial> CAMETHYST_ARMOR_MATERIAL = register("camethyst", Util.make(new EnumMap<>(ArmorItem.Type.class),
            attribute -> {
                attribute.put(ArmorItem.Type.BOOTS, 2);
                attribute.put(ArmorItem.Type.LEGGINGS, 5);
                attribute.put(ArmorItem.Type.CHESTPLATE, 6);
                attribute.put(ArmorItem.Type.HELMET, 2);
                attribute.put(ArmorItem.Type.BODY, 7);
            }), 14, 0.5f, 0.0f, () -> ModItems.CALCIFIED_AMETHYST.get());

    /** Glacial (Ice) — below chainmail protection; brittle and temporary. */
    public static final Holder<ArmorMaterial> ICE_ARMOR_MATERIAL = register("ice", Util.make(new EnumMap<>(ArmorItem.Type.class),
            attribute -> {
                attribute.put(ArmorItem.Type.BOOTS, 1);
                attribute.put(ArmorItem.Type.LEGGINGS, 3);
                attribute.put(ArmorItem.Type.CHESTPLATE, 4);
                attribute.put(ArmorItem.Type.HELMET, 1);
                attribute.put(ArmorItem.Type.BODY, 5);
            }), 8, 0.0f, 0.0f, () -> ModItems.GLACIAL_SHARD.get());

    /** Polished Quartz — iron-equivalent protection, hardest of the polished set. */
    public static final Holder<ArmorMaterial> PQUARTZ_ARMOR_MATERIAL = register("pquartz", Util.make(new EnumMap<>(ArmorItem.Type.class),
            attribute -> {
                attribute.put(ArmorItem.Type.BOOTS, 2);
                attribute.put(ArmorItem.Type.LEGGINGS, 5);
                attribute.put(ArmorItem.Type.CHESTPLATE, 6);
                attribute.put(ArmorItem.Type.HELMET, 2);
                attribute.put(ArmorItem.Type.BODY, 7);
            }), 10, 0.5f, 0.05f, () -> ModItems.POLISHED_QUARTZ.get());

    /** Polished Prismarine — iron-equivalent protection, ocean-enchanted. */
    public static final Holder<ArmorMaterial> PPRISM_ARMOR_MATERIAL = register("pprism", Util.make(new EnumMap<>(ArmorItem.Type.class),
            attribute -> {
                attribute.put(ArmorItem.Type.BOOTS, 2);
                attribute.put(ArmorItem.Type.LEGGINGS, 5);
                attribute.put(ArmorItem.Type.CHESTPLATE, 6);
                attribute.put(ArmorItem.Type.HELMET, 2);
                attribute.put(ArmorItem.Type.BODY, 7);
            }), 12, 0.0f, 0.0f, () -> ModItems.POLISHED_PRISMARINE.get());

    /**
     * Cake — practically useless armor. It's cake. You can eat it.
     * Below leather protection; very low durability.
     */
    public static final Holder<ArmorMaterial> CAKE_ARMOR_MATERIAL = register("cake", Util.make(new EnumMap<>(ArmorItem.Type.class),
            attribute -> {
                attribute.put(ArmorItem.Type.BOOTS, 0);
                attribute.put(ArmorItem.Type.LEGGINGS, 1);
                attribute.put(ArmorItem.Type.CHESTPLATE, 1);
                attribute.put(ArmorItem.Type.HELMET, 0);
                attribute.put(ArmorItem.Type.BODY, 1);
            }), 1, 0f, 0f, () -> Items.CAKE);

    // ── Food armor materials (edible novelty sets) ────────────────────────────

    public static final Holder<ArmorMaterial> BREAD_ARMOR_MATERIAL = register("bread", Util.make(new EnumMap<>(ArmorItem.Type.class),
            attribute -> {
                attribute.put(ArmorItem.Type.BOOTS, 0);
                attribute.put(ArmorItem.Type.LEGGINGS, 1);
                attribute.put(ArmorItem.Type.CHESTPLATE, 2);
                attribute.put(ArmorItem.Type.HELMET, 0);
                attribute.put(ArmorItem.Type.BODY, 1);
            }), 2, 0f, 0f, () -> Items.BREAD);

    public static final Holder<ArmorMaterial> DRIED_KELP_ARMOR_MATERIAL = register("dried_kelp", Util.make(new EnumMap<>(ArmorItem.Type.class),
            attribute -> {
                attribute.put(ArmorItem.Type.BOOTS, 1);
                attribute.put(ArmorItem.Type.LEGGINGS, 1);
                attribute.put(ArmorItem.Type.CHESTPLATE, 1);
                attribute.put(ArmorItem.Type.HELMET, 0);
                attribute.put(ArmorItem.Type.BODY, 1);
            }), 1, 0f, 0f, () -> Items.DRIED_KELP);

    public static final Holder<ArmorMaterial> ROTTEN_FLESH_ARMOR_MATERIAL = register("rotten_flesh", Util.make(new EnumMap<>(ArmorItem.Type.class),
            attribute -> {
                attribute.put(ArmorItem.Type.BOOTS, 0);
                attribute.put(ArmorItem.Type.LEGGINGS, 2);
                attribute.put(ArmorItem.Type.CHESTPLATE, 1);
                attribute.put(ArmorItem.Type.HELMET, 0);
                attribute.put(ArmorItem.Type.BODY, 1);
            }), 3, 0f, 0f, () -> Items.ROTTEN_FLESH);

    public static final Holder<ArmorMaterial> MELON_ARMOR_MATERIAL = register("melon", Util.make(new EnumMap<>(ArmorItem.Type.class),
            attribute -> {
                attribute.put(ArmorItem.Type.BOOTS, 1);
                attribute.put(ArmorItem.Type.LEGGINGS, 2);
                attribute.put(ArmorItem.Type.CHESTPLATE, 3);
                attribute.put(ArmorItem.Type.HELMET, 1);
                attribute.put(ArmorItem.Type.BODY, 2);
            }), 4, 0f, 0f, () -> Items.MELON_SLICE);

    public static final Holder<ArmorMaterial> SWEET_BERRY_ARMOR_MATERIAL = register("sweet_berry", Util.make(new EnumMap<>(ArmorItem.Type.class),
            attribute -> {
                attribute.put(ArmorItem.Type.BOOTS, 1);
                attribute.put(ArmorItem.Type.LEGGINGS, 2);
                attribute.put(ArmorItem.Type.CHESTPLATE, 2);
                attribute.put(ArmorItem.Type.HELMET, 1);
                attribute.put(ArmorItem.Type.BODY, 2);
            }), 5, 0f, 0f, () -> Items.SWEET_BERRIES);

    public static final Holder<ArmorMaterial> PUMPKIN_PIE_ARMOR_MATERIAL = register("pumpkin_pie", Util.make(new EnumMap<>(ArmorItem.Type.class),
            attribute -> {
                attribute.put(ArmorItem.Type.BOOTS, 1);
                attribute.put(ArmorItem.Type.LEGGINGS, 2);
                attribute.put(ArmorItem.Type.CHESTPLATE, 2);
                attribute.put(ArmorItem.Type.HELMET, 2);
                attribute.put(ArmorItem.Type.BODY, 2);
            }), 7, 0f, 0f, () -> Items.PUMPKIN_PIE);

    public static final Holder<ArmorMaterial> MUSHROOM_ARMOR_MATERIAL = register("mushroom", Util.make(new EnumMap<>(ArmorItem.Type.class),
            attribute -> {
                attribute.put(ArmorItem.Type.BOOTS, 1);
                attribute.put(ArmorItem.Type.LEGGINGS, 3);
                attribute.put(ArmorItem.Type.CHESTPLATE, 4);
                attribute.put(ArmorItem.Type.HELMET, 2);
                attribute.put(ArmorItem.Type.BODY, 3);
            }), 10, 0f, 0f, () -> Items.RED_MUSHROOM);

    public static final Holder<ArmorMaterial> PUFFERFISH_ARMOR_MATERIAL = register("pufferfish", Util.make(new EnumMap<>(ArmorItem.Type.class),
            attribute -> {
                attribute.put(ArmorItem.Type.BOOTS, 1);
                attribute.put(ArmorItem.Type.LEGGINGS, 3);
                attribute.put(ArmorItem.Type.CHESTPLATE, 3);
                attribute.put(ArmorItem.Type.HELMET, 1);
                attribute.put(ArmorItem.Type.BODY, 3);
            }), 8, 0f, 0f, () -> Items.PUFFERFISH);

    public static final Holder<ArmorMaterial> HONEY_ARMOR_MATERIAL = register("honey", Util.make(new EnumMap<>(ArmorItem.Type.class),
            attribute -> {
                attribute.put(ArmorItem.Type.BOOTS, 2);
                attribute.put(ArmorItem.Type.LEGGINGS, 3);
                attribute.put(ArmorItem.Type.CHESTPLATE, 4);
                attribute.put(ArmorItem.Type.HELMET, 2);
                attribute.put(ArmorItem.Type.BODY, 3);
            }), 10, 0f, 0f, () -> Items.HONEY_BOTTLE);

    public static final Holder<ArmorMaterial> CHORUS_FRUIT_ARMOR_MATERIAL = register("chorus_fruit", Util.make(new EnumMap<>(ArmorItem.Type.class),
            attribute -> {
                attribute.put(ArmorItem.Type.BOOTS, 2);
                attribute.put(ArmorItem.Type.LEGGINGS, 5);
                attribute.put(ArmorItem.Type.CHESTPLATE, 6);
                attribute.put(ArmorItem.Type.HELMET, 2);
                attribute.put(ArmorItem.Type.BODY, 5);
            }), 15, 0.5f, 0f, () -> Items.CHORUS_FRUIT);

    public static final Holder<ArmorMaterial> GOLDEN_APPLE_ARMOR_MATERIAL = register("golden_apple", Util.make(new EnumMap<>(ArmorItem.Type.class),
            attribute -> {
                attribute.put(ArmorItem.Type.BOOTS, 2);
                attribute.put(ArmorItem.Type.LEGGINGS, 5);
                attribute.put(ArmorItem.Type.CHESTPLATE, 6);
                attribute.put(ArmorItem.Type.HELMET, 3);
                attribute.put(ArmorItem.Type.BODY, 5);
            }), 22, 1.0f, 0f, () -> Items.GOLDEN_APPLE);

    // ── Vanilla material armor materials ───────────────────────────────────────

    /** Rabbit Hide — weak leather-equivalent, bunny hop full set. */
    public static final Holder<ArmorMaterial> RABBIT_HIDE_ARMOR_MATERIAL = register("rabbit_hide", Util.make(new EnumMap<>(ArmorItem.Type.class),
            attribute -> {
                attribute.put(ArmorItem.Type.BOOTS, 1);
                attribute.put(ArmorItem.Type.LEGGINGS, 2);
                attribute.put(ArmorItem.Type.CHESTPLATE, 3);
                attribute.put(ArmorItem.Type.HELMET, 1);
                attribute.put(ArmorItem.Type.BODY, 3);
            }), 12, 0f, 0f, () -> Items.RABBIT_HIDE);

    /** Cactus — prickly weak armor, thorns on hit. */
    public static final Holder<ArmorMaterial> CACTUS_ARMOR_MATERIAL = register("cactus", Util.make(new EnumMap<>(ArmorItem.Type.class),
            attribute -> {
                attribute.put(ArmorItem.Type.BOOTS, 1);
                attribute.put(ArmorItem.Type.LEGGINGS, 2);
                attribute.put(ArmorItem.Type.CHESTPLATE, 3);
                attribute.put(ArmorItem.Type.HELMET, 1);
                attribute.put(ArmorItem.Type.BODY, 3);
            }), 5, 0f, 0f, () -> Items.CACTUS);

    /** Bone — low-mid tier, undead bane synergy. */
    public static final Holder<ArmorMaterial> BONE_ARMOR_MATERIAL = register("bone", Util.make(new EnumMap<>(ArmorItem.Type.class),
            attribute -> {
                attribute.put(ArmorItem.Type.BOOTS, 1);
                attribute.put(ArmorItem.Type.LEGGINGS, 3);
                attribute.put(ArmorItem.Type.CHESTPLATE, 4);
                attribute.put(ArmorItem.Type.HELMET, 1);
                attribute.put(ArmorItem.Type.BODY, 4);
            }), 6, 0f, 0f, () -> Items.BONE);

    /** Clay — low-mid tier, enchantable. */
    public static final Holder<ArmorMaterial> CLAY_ARMOR_MATERIAL = register("clay", Util.make(new EnumMap<>(ArmorItem.Type.class),
            attribute -> {
                attribute.put(ArmorItem.Type.BOOTS, 1);
                attribute.put(ArmorItem.Type.LEGGINGS, 2);
                attribute.put(ArmorItem.Type.CHESTPLATE, 3);
                attribute.put(ArmorItem.Type.HELMET, 1);
                attribute.put(ArmorItem.Type.BODY, 3);
            }), 8, 0f, 0f, () -> Items.CLAY_BALL);

    /** Brick — stone-tier, durable and tough. */
    public static final Holder<ArmorMaterial> BRICK_ARMOR_MATERIAL = register("brick", Util.make(new EnumMap<>(ArmorItem.Type.class),
            attribute -> {
                attribute.put(ArmorItem.Type.BOOTS, 1);
                attribute.put(ArmorItem.Type.LEGGINGS, 4);
                attribute.put(ArmorItem.Type.CHESTPLATE, 5);
                attribute.put(ArmorItem.Type.HELMET, 2);
                attribute.put(ArmorItem.Type.BODY, 5);
            }), 5, 0.5f, 0f, () -> Items.BRICK);

    /** Nether Brick — stone-tier, fire-themed. */
    public static final Holder<ArmorMaterial> NETHER_BRICK_ARMOR_MATERIAL = register("nether_brick", Util.make(new EnumMap<>(ArmorItem.Type.class),
            attribute -> {
                attribute.put(ArmorItem.Type.BOOTS, 1);
                attribute.put(ArmorItem.Type.LEGGINGS, 4);
                attribute.put(ArmorItem.Type.CHESTPLATE, 5);
                attribute.put(ArmorItem.Type.HELMET, 2);
                attribute.put(ArmorItem.Type.BODY, 5);
            }), 5, 0.5f, 0f, () -> Items.NETHER_BRICK);

    /** Copper — stone-tier, oxidizes over time. */
    public static final Holder<ArmorMaterial> COPPER_ARMOR_MATERIAL = register("copper", Util.make(new EnumMap<>(ArmorItem.Type.class),
            attribute -> {
                attribute.put(ArmorItem.Type.BOOTS, 2);
                attribute.put(ArmorItem.Type.LEGGINGS, 4);
                attribute.put(ArmorItem.Type.CHESTPLATE, 5);
                attribute.put(ArmorItem.Type.HELMET, 2);
                attribute.put(ArmorItem.Type.BODY, 5);
            }), 8, 0f, 0f, () -> Items.COPPER_INGOT);

    /** Phantom Membrane — upper-mid tier, slow falling. */
    public static final Holder<ArmorMaterial> PHANTOM_ARMOR_MATERIAL = register("phantom", Util.make(new EnumMap<>(ArmorItem.Type.class),
            attribute -> {
                attribute.put(ArmorItem.Type.BOOTS, 2);
                attribute.put(ArmorItem.Type.LEGGINGS, 4);
                attribute.put(ArmorItem.Type.CHESTPLATE, 5);
                attribute.put(ArmorItem.Type.HELMET, 2);
                attribute.put(ArmorItem.Type.BODY, 5);
            }), 12, 0f, 0f, () -> Items.PHANTOM_MEMBRANE);

    /** Magma Cream — upper-mid tier, fire protection. */
    public static final Holder<ArmorMaterial> MAGMA_CREAM_ARMOR_MATERIAL = register("magma_cream", Util.make(new EnumMap<>(ArmorItem.Type.class),
            attribute -> {
                attribute.put(ArmorItem.Type.BOOTS, 2);
                attribute.put(ArmorItem.Type.LEGGINGS, 4);
                attribute.put(ArmorItem.Type.CHESTPLATE, 5);
                attribute.put(ArmorItem.Type.HELMET, 2);
                attribute.put(ArmorItem.Type.BODY, 5);
            }), 8, 0.5f, 0f, () -> Items.MAGMA_CREAM);

    /** Slime — upper-mid tier, bouncy. */
    public static final Holder<ArmorMaterial> SLIME_ARMOR_MATERIAL = register("slime", Util.make(new EnumMap<>(ArmorItem.Type.class),
            attribute -> {
                attribute.put(ArmorItem.Type.BOOTS, 2);
                attribute.put(ArmorItem.Type.LEGGINGS, 3);
                attribute.put(ArmorItem.Type.CHESTPLATE, 4);
                attribute.put(ArmorItem.Type.HELMET, 2);
                attribute.put(ArmorItem.Type.BODY, 4);
            }), 10, 0f, 0.1f, () -> Items.SLIME_BALL);

    /** Blaze Rod — iron-level, fire resistance. */
    public static final Holder<ArmorMaterial> BLAZE_ARMOR_MATERIAL = register("blaze", Util.make(new EnumMap<>(ArmorItem.Type.class),
            attribute -> {
                attribute.put(ArmorItem.Type.BOOTS, 2);
                attribute.put(ArmorItem.Type.LEGGINGS, 5);
                attribute.put(ArmorItem.Type.CHESTPLATE, 6);
                attribute.put(ArmorItem.Type.HELMET, 2);
                attribute.put(ArmorItem.Type.BODY, 6);
            }), 10, 0.5f, 0f, () -> Items.BLAZE_ROD);

    /** Nautilus Shell — iron-level, conduit affinity. */
    public static final Holder<ArmorMaterial> NAUTILUS_ARMOR_MATERIAL = register("nautilus", Util.make(new EnumMap<>(ArmorItem.Type.class),
            attribute -> {
                attribute.put(ArmorItem.Type.BOOTS, 2);
                attribute.put(ArmorItem.Type.LEGGINGS, 5);
                attribute.put(ArmorItem.Type.CHESTPLATE, 6);
                attribute.put(ArmorItem.Type.HELMET, 2);
                attribute.put(ArmorItem.Type.BODY, 6);
            }), 14, 0.5f, 0f, () -> Items.NAUTILUS_SHELL);

    /** Purpur — iron-level, ender resilience. */
    public static final Holder<ArmorMaterial> PURPUR_ARMOR_MATERIAL = register("purpur", Util.make(new EnumMap<>(ArmorItem.Type.class),
            attribute -> {
                attribute.put(ArmorItem.Type.BOOTS, 2);
                attribute.put(ArmorItem.Type.LEGGINGS, 5);
                attribute.put(ArmorItem.Type.CHESTPLATE, 6);
                attribute.put(ArmorItem.Type.HELMET, 2);
                attribute.put(ArmorItem.Type.BODY, 6);
            }), 12, 0.5f, 0f, () -> Items.POPPED_CHORUS_FRUIT);

    /** Ghast Tear — above-iron, regeneration. */
    public static final Holder<ArmorMaterial> GHAST_TEAR_ARMOR_MATERIAL = register("ghast_tear", Util.make(new EnumMap<>(ArmorItem.Type.class),
            attribute -> {
                attribute.put(ArmorItem.Type.BOOTS, 2);
                attribute.put(ArmorItem.Type.LEGGINGS, 5);
                attribute.put(ArmorItem.Type.CHESTPLATE, 6);
                attribute.put(ArmorItem.Type.HELMET, 3);
                attribute.put(ArmorItem.Type.BODY, 6);
            }), 18, 1.0f, 0f, () -> Items.GHAST_TEAR);

    /** Eye of Ender — above-iron, ender sight. */
    public static final Holder<ArmorMaterial> EYE_OF_ENDER_ARMOR_MATERIAL = register("eye_of_ender", Util.make(new EnumMap<>(ArmorItem.Type.class),
            attribute -> {
                attribute.put(ArmorItem.Type.BOOTS, 2);
                attribute.put(ArmorItem.Type.LEGGINGS, 5);
                attribute.put(ArmorItem.Type.CHESTPLATE, 7);
                attribute.put(ArmorItem.Type.HELMET, 3);
                attribute.put(ArmorItem.Type.BODY, 6);
            }), 20, 1.0f, 0.05f, () -> Items.ENDER_EYE);

    /** Shulker Shell — above-iron, levitation shield. */
    public static final Holder<ArmorMaterial> SHULKER_ARMOR_MATERIAL = register("shulker", Util.make(new EnumMap<>(ArmorItem.Type.class),
            attribute -> {
                attribute.put(ArmorItem.Type.BOOTS, 3);
                attribute.put(ArmorItem.Type.LEGGINGS, 6);
                attribute.put(ArmorItem.Type.CHESTPLATE, 7);
                attribute.put(ArmorItem.Type.HELMET, 3);
                attribute.put(ArmorItem.Type.BODY, 7);
            }), 16, 1.5f, 0.1f, () -> Items.SHULKER_SHELL);

    /** Turtle Scute — diamond-adjacent, ocean guardian. */
    public static final Holder<ArmorMaterial> TURTLE_SCUTE_ARMOR_MATERIAL = register("turtle_scute", Util.make(new EnumMap<>(ArmorItem.Type.class),
            attribute -> {
                attribute.put(ArmorItem.Type.BOOTS, 2);
                attribute.put(ArmorItem.Type.LEGGINGS, 5);
                attribute.put(ArmorItem.Type.CHESTPLATE, 7);
                attribute.put(ArmorItem.Type.HELMET, 3);
                attribute.put(ArmorItem.Type.BODY, 7);
            }), 16, 1.5f, 0.05f, () -> Items.TURTLE_SCUTE);

    /** Echo Shard — diamond-adjacent, sculk resonance. */
    public static final Holder<ArmorMaterial> ECHO_SHARD_ARMOR_MATERIAL = register("echo_shard", Util.make(new EnumMap<>(ArmorItem.Type.class),
            attribute -> {
                attribute.put(ArmorItem.Type.BOOTS, 3);
                attribute.put(ArmorItem.Type.LEGGINGS, 6);
                attribute.put(ArmorItem.Type.CHESTPLATE, 7);
                attribute.put(ArmorItem.Type.HELMET, 3);
                attribute.put(ArmorItem.Type.BODY, 7);
            }), 18, 2.0f, 0.05f, () -> Items.ECHO_SHARD);

    /** Dragon's Breath — endgame, draconic aura. */
    public static final Holder<ArmorMaterial> DRAGON_BREATH_ARMOR_MATERIAL = register("dragon_breath", Util.make(new EnumMap<>(ArmorItem.Type.class),
            attribute -> {
                attribute.put(ArmorItem.Type.BOOTS, 3);
                attribute.put(ArmorItem.Type.LEGGINGS, 6);
                attribute.put(ArmorItem.Type.CHESTPLATE, 8);
                attribute.put(ArmorItem.Type.HELMET, 3);
                attribute.put(ArmorItem.Type.BODY, 7);
            }), 20, 2.5f, 0.1f, () -> Items.DRAGON_BREATH);

    /**
     * Ectoplasm — iron-equivalent protection with slight toughness bonus.
     * Spectral armor grants ghost avoidance when worn as a full set.
     */
    public static final Holder<ArmorMaterial> ECTO_ARMOR_MATERIAL = register("ecto", Util.make(new EnumMap<>(ArmorItem.Type.class),
            attribute -> {
                attribute.put(ArmorItem.Type.BOOTS, 2);
                attribute.put(ArmorItem.Type.LEGGINGS, 5);
                attribute.put(ArmorItem.Type.CHESTPLATE, 6);
                attribute.put(ArmorItem.Type.HELMET, 2);
                attribute.put(ArmorItem.Type.BODY, 7);
            }), 16, 0.5f, 0.0f, () -> ModItems.REFINED_ECTOPLASM.get());

    private static Holder<ArmorMaterial> register(String name, EnumMap<ArmorItem.Type, Integer> typeProtection,
                                                  int enchantability, float toughness, float knockbackResistance,
                                                  Supplier<Item> ingredientItem) {
        ResourceLocation location = ResourceLocation.fromNamespaceAndPath(UsefultoolsMod.MOD_ID, name);
        Holder<SoundEvent> equipSound = SoundEvents.ARMOR_EQUIP_GENERIC;
        Supplier<Ingredient> ingredient = () -> Ingredient.of(ingredientItem.get());
        List<ArmorMaterial.Layer> layers = List.of(new ArmorMaterial.Layer(location));

        EnumMap<ArmorItem.Type, Integer> typeMap = new EnumMap<>(ArmorItem.Type.class);
        for (ArmorItem.Type type : ArmorItem.Type.values()) {
            typeMap.put(type, typeProtection.get(type));
        }

        return Registry.registerForHolder(BuiltInRegistries.ARMOR_MATERIAL, location,
                new ArmorMaterial(typeProtection, enchantability, equipSound, ingredient, layers, toughness, knockbackResistance));
    }
}