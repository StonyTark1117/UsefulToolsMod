package com.stonytark.usefultoolsmod.item;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

import java.util.function.Supplier;

// In Fabric 1.20.1, armor materials are enum-like implementations of the ArmorMaterial interface.
public enum ModArmorMaterials implements ArmorMaterial {
    RGOLD("usefultoolsmod:rgold", 18,
            new int[]{3, 5, 5, 3}, 25,
            SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 2f, 0.1f,
            () -> Ingredient.ofItems(ModItems.RGOLD)),

    OBSIDIAN("usefultoolsmod:obsidian", 45,
            new int[]{6, 7, 9, 6}, 10,
            SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 4f, 0.4f,
            () -> Ingredient.ofItems(ModItems.OBINGOT)),

    EMERALD("usefultoolsmod:emerald", 33,
            new int[]{4, 6, 8, 4}, 30,
            SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 2f, 0.15f,
            () -> Ingredient.ofItems(ModItems.SEM)),

    OVERPOWER("usefultoolsmod:overpower", 100,
            new int[]{15, 15, 15, 15}, 50,
            SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 8f, 1f,
            () -> Ingredient.ofItems(ModItems.OBINGOT)),

    HRED("usefultoolsmod:hred", 20,
            new int[]{2, 4, 4, 3}, 23,
            SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 1.1f, 0.08f,
            () -> Ingredient.ofItems(ModItems.HRED)),

    HGLOW("usefultoolsmod:hglow", 19,
            new int[]{1, 4, 5, 2}, 25,
            SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0f, 0f,
            () -> Ingredient.ofItems(ModItems.HGLOW)),

    RLAPIS("usefultoolsmod:rlapis", 17,
            new int[]{3, 6, 4, 4}, 32,
            SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 1.6f, 0.15f,
            () -> Ingredient.ofItems(ModItems.RLAPIS)),

    COAL("usefultoolsmod:coal", 8,
            new int[]{1, 2, 3, 1}, 8,
            SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0f, 0f,
            () -> Ingredient.ofItems(ModItems.HARDENED_COAL)),

    FNI("usefultoolsmod:fni", 9,
            new int[]{1, 4, 5, 2}, 9,
            SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0f, 0f,
            () -> Ingredient.ofItems(net.minecraft.item.Items.FLINT)),

    CAMETHYST("usefultoolsmod:camethyst", 14,
            new int[]{2, 5, 6, 2}, 14,
            SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0.5f, 0f,
            () -> Ingredient.ofItems(ModItems.CALCIFIED_AMETHYST)),

    ICE("usefultoolsmod:ice", 8,
            new int[]{1, 3, 4, 1}, 8,
            SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0f, 0f,
            () -> Ingredient.ofItems(ModItems.GLACIAL_SHARD)),

    PQUARTZ("usefultoolsmod:pquartz", 15,
            new int[]{2, 5, 6, 2}, 10,
            SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0.5f, 0.05f,
            () -> Ingredient.ofItems(ModItems.POLISHED_QUARTZ)),

    PPRISM("usefultoolsmod:pprism", 14,
            new int[]{2, 5, 6, 2}, 12,
            SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0f, 0f,
            () -> Ingredient.ofItems(ModItems.POLISHED_PRISMARINE)),

    CAKE("usefultoolsmod:cake", 1,
            new int[]{0, 1, 1, 0}, 1,
            SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0f, 0f,
            () -> Ingredient.ofItems(net.minecraft.item.Items.CAKE)),

    BREAD("usefultoolsmod:bread", 2,
            new int[]{0, 1, 2, 0}, 2,
            SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0f, 0f,
            () -> Ingredient.ofItems(net.minecraft.item.Items.BREAD)),

    DRIED_KELP("usefultoolsmod:dried_kelp", 1,
            new int[]{1, 1, 1, 0}, 1,
            SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0f, 0f,
            () -> Ingredient.ofItems(net.minecraft.item.Items.DRIED_KELP)),

    ROTTEN_FLESH("usefultoolsmod:rotten_flesh", 3,
            new int[]{0, 2, 1, 0}, 3,
            SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0f, 0f,
            () -> Ingredient.ofItems(net.minecraft.item.Items.ROTTEN_FLESH)),

    MELON("usefultoolsmod:melon", 4,
            new int[]{1, 2, 3, 1}, 4,
            SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0f, 0f,
            () -> Ingredient.ofItems(net.minecraft.item.Items.MELON_SLICE)),

    SWEET_BERRY("usefultoolsmod:sweet_berry", 5,
            new int[]{1, 2, 2, 1}, 5,
            SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0f, 0f,
            () -> Ingredient.ofItems(net.minecraft.item.Items.SWEET_BERRIES)),

    PUMPKIN_PIE("usefultoolsmod:pumpkin_pie", 7,
            new int[]{1, 2, 2, 2}, 7,
            SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0f, 0f,
            () -> Ingredient.ofItems(net.minecraft.item.Items.PUMPKIN_PIE)),

    MUSHROOM("usefultoolsmod:mushroom", 10,
            new int[]{1, 3, 4, 2}, 10,
            SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0f, 0f,
            () -> Ingredient.ofItems(net.minecraft.item.Items.RED_MUSHROOM)),

    PUFFERFISH("usefultoolsmod:pufferfish", 8,
            new int[]{1, 3, 3, 1}, 8,
            SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0f, 0f,
            () -> Ingredient.ofItems(net.minecraft.item.Items.PUFFERFISH)),

    HONEY("usefultoolsmod:honey", 10,
            new int[]{2, 3, 4, 2}, 10,
            SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0f, 0f,
            () -> Ingredient.ofItems(net.minecraft.item.Items.HONEY_BOTTLE)),

    CHORUS_FRUIT("usefultoolsmod:chorus_fruit", 15,
            new int[]{2, 5, 6, 2}, 15,
            SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0.5f, 0f,
            () -> Ingredient.ofItems(net.minecraft.item.Items.CHORUS_FRUIT)),

    GOLDEN_APPLE("usefultoolsmod:golden_apple", 22,
            new int[]{2, 5, 6, 3}, 22,
            SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 1f, 0f,
            () -> Ingredient.ofItems(net.minecraft.item.Items.GOLDEN_APPLE)),

    ECTO("usefultoolsmod:ecto", 16,
            new int[]{2, 5, 6, 2}, 16,
            SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0.5f, 0f,
            () -> Ingredient.ofItems(ModItems.REFINED_ECTOPLASM)),

    // ── Vanilla material armor materials ───────────────────────────────────────

    /** Rabbit Hide — weak leather-equivalent, bunny hop full set. */
    RABBIT_HIDE("usefultoolsmod:rabbit_hide", 5,
            new int[]{1, 2, 3, 1}, 12,
            SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0f, 0f,
            () -> Ingredient.ofItems(net.minecraft.item.Items.RABBIT_HIDE)),

    /** Cactus — prickly weak armor, thorns on hit. */
    CACTUS("usefultoolsmod:cactus", 7,
            new int[]{1, 2, 3, 1}, 5,
            SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0f, 0f,
            () -> Ingredient.ofItems(net.minecraft.item.Items.CACTUS)),

    /** Bone — low-mid tier, undead bane synergy. */
    BONE("usefultoolsmod:bone", 10,
            new int[]{1, 3, 4, 1}, 6,
            SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0f, 0f,
            () -> Ingredient.ofItems(net.minecraft.item.Items.BONE)),

    /** Clay — low-mid tier, enchantable. */
    CLAY("usefultoolsmod:clay", 8,
            new int[]{1, 2, 3, 1}, 8,
            SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0f, 0f,
            () -> Ingredient.ofItems(net.minecraft.item.Items.CLAY_BALL)),

    /** Brick — stone-tier, durable and tough. */
    BRICK("usefultoolsmod:brick", 12,
            new int[]{1, 4, 5, 2}, 5,
            SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0.5f, 0f,
            () -> Ingredient.ofItems(net.minecraft.item.Items.BRICK)),

    /** Nether Brick — stone-tier, fire-themed. */
    NETHER_BRICK("usefultoolsmod:nether_brick", 12,
            new int[]{1, 4, 5, 2}, 5,
            SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0.5f, 0f,
            () -> Ingredient.ofItems(net.minecraft.item.Items.NETHER_BRICK)),

    /** Copper — stone-tier, oxidizes over time. */
    COPPER("usefultoolsmod:copper", 13,
            new int[]{2, 4, 5, 2}, 8,
            SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0f, 0f,
            () -> Ingredient.ofItems(net.minecraft.item.Items.COPPER_INGOT)),

    /** Phantom Membrane — upper-mid tier, slow falling. */
    PHANTOM("usefultoolsmod:phantom", 14,
            new int[]{2, 4, 5, 2}, 12,
            SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0f, 0f,
            () -> Ingredient.ofItems(net.minecraft.item.Items.PHANTOM_MEMBRANE)),

    /** Magma Cream — upper-mid tier, fire protection. */
    MAGMA_CREAM("usefultoolsmod:magma_cream", 13,
            new int[]{2, 4, 5, 2}, 8,
            SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0.5f, 0f,
            () -> Ingredient.ofItems(net.minecraft.item.Items.MAGMA_CREAM)),

    /** Slime — upper-mid tier, bouncy. */
    SLIME("usefultoolsmod:slime", 12,
            new int[]{2, 3, 4, 2}, 10,
            SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0f, 0.1f,
            () -> Ingredient.ofItems(net.minecraft.item.Items.SLIME_BALL)),

    /** Blaze Rod — iron-level, fire resistance. */
    BLAZE("usefultoolsmod:blaze", 15,
            new int[]{2, 5, 6, 2}, 10,
            SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0.5f, 0f,
            () -> Ingredient.ofItems(net.minecraft.item.Items.BLAZE_ROD)),

    /** Nautilus Shell — iron-level, conduit affinity. */
    NAUTILUS("usefultoolsmod:nautilus", 16,
            new int[]{2, 5, 6, 2}, 14,
            SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0.5f, 0f,
            () -> Ingredient.ofItems(net.minecraft.item.Items.NAUTILUS_SHELL)),

    /** Purpur — iron-level, ender resilience. */
    PURPUR("usefultoolsmod:purpur", 15,
            new int[]{2, 5, 6, 2}, 12,
            SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0.5f, 0f,
            () -> Ingredient.ofItems(net.minecraft.item.Items.POPPED_CHORUS_FRUIT)),

    /** Ghast Tear — above-iron, regeneration. */
    GHAST_TEAR("usefultoolsmod:ghast_tear", 20,
            new int[]{2, 5, 6, 3}, 18,
            SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 1.0f, 0f,
            () -> Ingredient.ofItems(net.minecraft.item.Items.GHAST_TEAR)),

    /** Eye of Ender — above-iron, ender sight. */
    EYE_OF_ENDER("usefultoolsmod:eye_of_ender", 22,
            new int[]{2, 5, 7, 3}, 20,
            SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 1.0f, 0.05f,
            () -> Ingredient.ofItems(net.minecraft.item.Items.ENDER_EYE)),

    /** Shulker Shell — above-iron, levitation shield. */
    SHULKER("usefultoolsmod:shulker", 25,
            new int[]{3, 6, 7, 3}, 16,
            SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 1.5f, 0.1f,
            () -> Ingredient.ofItems(net.minecraft.item.Items.SHULKER_SHELL)),

    /** Turtle Scute — diamond-adjacent, ocean guardian. */
    TURTLE_SCUTE("usefultoolsmod:turtle_scute", 25,
            new int[]{2, 5, 7, 3}, 16,
            SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 1.5f, 0.05f,
            () -> Ingredient.ofItems(net.minecraft.item.Items.SCUTE)),

    /** Echo Shard — diamond-adjacent, sculk resonance. */
    ECHO_SHARD("usefultoolsmod:echo_shard", 30,
            new int[]{3, 6, 7, 3}, 18,
            SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 2.0f, 0.05f,
            () -> Ingredient.ofItems(net.minecraft.item.Items.ECHO_SHARD)),

    /** Dragon's Breath — endgame, draconic aura. */
    DRAGON_BREATH("usefultoolsmod:dragon_breath", 35,
            new int[]{3, 6, 8, 3}, 20,
            SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 2.5f, 0.1f,
            () -> Ingredient.ofItems(net.minecraft.item.Items.DRAGON_BREATH));

    // Base durability values (same as vanilla: boots, leggings, chestplate, helmet)
    private static final int[] BASE_DURABILITY = {13, 15, 16, 11};

    private final String name;
    private final int durabilityMultiplier;
    private final int[] protectionAmounts; // boots, leggings, chestplate, helmet
    private final int enchantability;
    private final SoundEvent equipSound;
    private final float toughness;
    private final float knockbackResistance;
    private final Supplier<Ingredient> repairIngredient;

    ModArmorMaterials(String name, int durabilityMultiplier,
                      int[] protectionAmounts, int enchantability,
                      SoundEvent equipSound, float toughness,
                      float knockbackResistance, Supplier<Ingredient> repairIngredient) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.protectionAmounts = protectionAmounts;
        this.enchantability = enchantability;
        this.equipSound = equipSound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredient = repairIngredient;
    }

    public static void register() {
        // Registration happens automatically for enum, but we can add this for clarity
    }

    @Override
    public int getDurability(ArmorItem.Type type) {
        return BASE_DURABILITY[type.getEquipmentSlot().getEntitySlotId()] * this.durabilityMultiplier;
    }

    @Override
    public int getProtection(ArmorItem.Type type) {
        return this.protectionAmounts[type.getEquipmentSlot().getEntitySlotId()];
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override
    public SoundEvent getEquipSound() {
        return this.equipSound;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}
