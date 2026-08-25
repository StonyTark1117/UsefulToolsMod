package com.stonytark.usefultoolsmod.item;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

// In 1.20.2, ArmorMaterial is still an interface. Custom armor materials are implemented as enums.
public enum ModArmorMaterials implements ArmorMaterial {

    // name, durabilityMultiplier, protection[boots,leggings,chestplate,helmet],
    // enchantability, equipSound, toughness, knockbackResistance, repairIngredient
    RGOLD("usefultoolsmod:rgold", 18,
            new int[]{3, 5, 5, 3}, 25,
            SoundEvents.ARMOR_EQUIP_GENERIC, 2f, 0.1f,
            () -> Ingredient.of(ModItems.RGOLD.get())),

    OBSIDIAN("usefultoolsmod:obsidian", 45,
            new int[]{6, 7, 9, 6}, 10,
            SoundEvents.ARMOR_EQUIP_GENERIC, 4f, 0.4f,
            () -> Ingredient.of(ModItems.OBINGOT.get())),

    EMERALD("usefultoolsmod:emerald", 33,
            new int[]{4, 6, 8, 4}, 30,
            SoundEvents.ARMOR_EQUIP_GENERIC, 2f, 0.15f,
            () -> Ingredient.of(ModItems.SEM.get())),

    OVERPOWER("usefultoolsmod:overpower", 100,
            new int[]{15, 15, 15, 15}, 50,
            SoundEvents.ARMOR_EQUIP_GENERIC, 8f, 1f,
            () -> Ingredient.of(ModItems.OBINGOT.get())),

    HRED("usefultoolsmod:hred", 20,
            new int[]{2, 4, 4, 3}, 23,
            SoundEvents.ARMOR_EQUIP_GENERIC, 1.1f, 0.08f,
            () -> Ingredient.of(ModItems.HRED.get())),

    HGLOW("usefultoolsmod:hglow", 19,
            new int[]{1, 4, 5, 2}, 25,
            SoundEvents.ARMOR_EQUIP_GENERIC, 0f, 0f,
            () -> Ingredient.of(ModItems.HGLOW.get())),

    RLAPIS("usefultoolsmod:rlapis", 17,
            new int[]{3, 6, 4, 4}, 32,
            SoundEvents.ARMOR_EQUIP_GENERIC, 1.6f, 0.15f,
            () -> Ingredient.of(ModItems.RLAPIS.get())),

    COAL("usefultoolsmod:coal", 8,
            new int[]{1, 2, 3, 1}, 8,
            SoundEvents.ARMOR_EQUIP_GENERIC, 0f, 0f,
            () -> Ingredient.of(ModItems.HARDENED_COAL.get())),

    FNI("usefultoolsmod:fni", 9,
            new int[]{1, 4, 5, 2}, 9,
            SoundEvents.ARMOR_EQUIP_GENERIC, 0f, 0f,
            () -> Ingredient.of(net.minecraft.world.item.Items.FLINT)),

    CAMETHYST("usefultoolsmod:camethyst", 14,
            new int[]{2, 5, 6, 2}, 14,
            SoundEvents.ARMOR_EQUIP_GENERIC, 0.5f, 0f,
            () -> Ingredient.of(ModItems.CALCIFIED_AMETHYST.get())),

    ICE("usefultoolsmod:ice", 8,
            new int[]{1, 3, 4, 1}, 8,
            SoundEvents.ARMOR_EQUIP_GENERIC, 0f, 0f,
            () -> Ingredient.of(ModItems.GLACIAL_SHARD.get())),

    PQUARTZ("usefultoolsmod:pquartz", 15,
            new int[]{2, 5, 6, 2}, 10,
            SoundEvents.ARMOR_EQUIP_GENERIC, 0.5f, 0.05f,
            () -> Ingredient.of(ModItems.POLISHED_QUARTZ.get())),

    PPRISM("usefultoolsmod:pprism", 14,
            new int[]{2, 5, 6, 2}, 12,
            SoundEvents.ARMOR_EQUIP_GENERIC, 0f, 0f,
            () -> Ingredient.of(ModItems.POLISHED_PRISMARINE.get())),

    CAKE("usefultoolsmod:cake", 1,
            new int[]{0, 1, 1, 0}, 1,
            SoundEvents.ARMOR_EQUIP_GENERIC, 0f, 0f,
            () -> Ingredient.of(net.minecraft.world.item.Items.CAKE)),

    BREAD("usefultoolsmod:bread", 2,
            new int[]{0, 1, 2, 0}, 2,
            SoundEvents.ARMOR_EQUIP_GENERIC, 0f, 0f,
            () -> Ingredient.of(net.minecraft.world.item.Items.BREAD)),

    DRIED_KELP("usefultoolsmod:dried_kelp", 1,
            new int[]{1, 1, 1, 0}, 1,
            SoundEvents.ARMOR_EQUIP_GENERIC, 0f, 0f,
            () -> Ingredient.of(net.minecraft.world.item.Items.DRIED_KELP)),

    ROTTEN_FLESH("usefultoolsmod:rotten_flesh", 3,
            new int[]{0, 2, 1, 0}, 3,
            SoundEvents.ARMOR_EQUIP_GENERIC, 0f, 0f,
            () -> Ingredient.of(net.minecraft.world.item.Items.ROTTEN_FLESH)),

    MELON("usefultoolsmod:melon", 4,
            new int[]{1, 2, 3, 1}, 4,
            SoundEvents.ARMOR_EQUIP_GENERIC, 0f, 0f,
            () -> Ingredient.of(net.minecraft.world.item.Items.MELON_SLICE)),

    SWEET_BERRY("usefultoolsmod:sweet_berry", 5,
            new int[]{1, 2, 2, 1}, 5,
            SoundEvents.ARMOR_EQUIP_GENERIC, 0f, 0f,
            () -> Ingredient.of(net.minecraft.world.item.Items.SWEET_BERRIES)),

    PUMPKIN_PIE("usefultoolsmod:pumpkin_pie", 7,
            new int[]{1, 2, 2, 2}, 7,
            SoundEvents.ARMOR_EQUIP_GENERIC, 0f, 0f,
            () -> Ingredient.of(net.minecraft.world.item.Items.PUMPKIN_PIE)),

    MUSHROOM("usefultoolsmod:mushroom", 10,
            new int[]{1, 3, 4, 2}, 10,
            SoundEvents.ARMOR_EQUIP_GENERIC, 0f, 0f,
            () -> Ingredient.of(net.minecraft.world.item.Items.RED_MUSHROOM)),

    PUFFERFISH("usefultoolsmod:pufferfish", 8,
            new int[]{1, 3, 3, 1}, 8,
            SoundEvents.ARMOR_EQUIP_GENERIC, 0f, 0f,
            () -> Ingredient.of(net.minecraft.world.item.Items.PUFFERFISH)),

    HONEY("usefultoolsmod:honey", 10,
            new int[]{2, 3, 4, 2}, 10,
            SoundEvents.ARMOR_EQUIP_GENERIC, 0f, 0f,
            () -> Ingredient.of(net.minecraft.world.item.Items.HONEY_BOTTLE)),

    CHORUS_FRUIT("usefultoolsmod:chorus_fruit", 15,
            new int[]{2, 5, 6, 2}, 15,
            SoundEvents.ARMOR_EQUIP_GENERIC, 0.5f, 0f,
            () -> Ingredient.of(net.minecraft.world.item.Items.CHORUS_FRUIT)),

    GOLDEN_APPLE("usefultoolsmod:golden_apple", 22,
            new int[]{2, 5, 6, 3}, 22,
            SoundEvents.ARMOR_EQUIP_GENERIC, 1f, 0f,
            () -> Ingredient.of(net.minecraft.world.item.Items.GOLDEN_APPLE)),

    ECTO("usefultoolsmod:ecto", 16,
            new int[]{2, 5, 6, 2}, 16,
            SoundEvents.ARMOR_EQUIP_GENERIC, 0.5f, 0f,
            () -> Ingredient.of(ModItems.REFINED_ECTOPLASM.get())),

    // ── Vanilla material armor materials ───────────────────────────────────────

    RABBIT_HIDE("usefultoolsmod:rabbit_hide", 5,
            new int[]{1, 2, 3, 1}, 12,
            SoundEvents.ARMOR_EQUIP_GENERIC, 0f, 0f,
            () -> Ingredient.of(net.minecraft.world.item.Items.RABBIT_HIDE)),

    CACTUS("usefultoolsmod:cactus", 7,
            new int[]{1, 2, 3, 1}, 5,
            SoundEvents.ARMOR_EQUIP_GENERIC, 0f, 0f,
            () -> Ingredient.of(net.minecraft.world.item.Items.CACTUS)),

    BONE("usefultoolsmod:bone", 10,
            new int[]{1, 3, 4, 1}, 6,
            SoundEvents.ARMOR_EQUIP_GENERIC, 0f, 0f,
            () -> Ingredient.of(net.minecraft.world.item.Items.BONE)),

    CLAY("usefultoolsmod:clay", 8,
            new int[]{1, 2, 3, 1}, 8,
            SoundEvents.ARMOR_EQUIP_GENERIC, 0f, 0f,
            () -> Ingredient.of(net.minecraft.world.item.Items.CLAY_BALL)),

    BRICK("usefultoolsmod:brick", 12,
            new int[]{1, 4, 5, 2}, 5,
            SoundEvents.ARMOR_EQUIP_GENERIC, 0.5f, 0f,
            () -> Ingredient.of(net.minecraft.world.item.Items.BRICK)),

    NETHER_BRICK("usefultoolsmod:nether_brick", 12,
            new int[]{1, 4, 5, 2}, 5,
            SoundEvents.ARMOR_EQUIP_GENERIC, 0.5f, 0f,
            () -> Ingredient.of(net.minecraft.world.item.Items.NETHER_BRICK)),

    COPPER("usefultoolsmod:copper", 13,
            new int[]{2, 4, 5, 2}, 8,
            SoundEvents.ARMOR_EQUIP_GENERIC, 0f, 0f,
            () -> Ingredient.of(net.minecraft.world.item.Items.COPPER_INGOT)),

    PHANTOM("usefultoolsmod:phantom", 14,
            new int[]{2, 4, 5, 2}, 12,
            SoundEvents.ARMOR_EQUIP_GENERIC, 0f, 0f,
            () -> Ingredient.of(net.minecraft.world.item.Items.PHANTOM_MEMBRANE)),

    MAGMA_CREAM("usefultoolsmod:magma_cream", 13,
            new int[]{2, 4, 5, 2}, 8,
            SoundEvents.ARMOR_EQUIP_GENERIC, 0.5f, 0f,
            () -> Ingredient.of(net.minecraft.world.item.Items.MAGMA_CREAM)),

    SLIME("usefultoolsmod:slime", 12,
            new int[]{2, 3, 4, 2}, 10,
            SoundEvents.ARMOR_EQUIP_GENERIC, 0f, 0.1f,
            () -> Ingredient.of(net.minecraft.world.item.Items.SLIME_BALL)),

    BLAZE("usefultoolsmod:blaze", 15,
            new int[]{2, 5, 6, 2}, 10,
            SoundEvents.ARMOR_EQUIP_GENERIC, 0.5f, 0f,
            () -> Ingredient.of(net.minecraft.world.item.Items.BLAZE_ROD)),

    NAUTILUS("usefultoolsmod:nautilus", 16,
            new int[]{2, 5, 6, 2}, 14,
            SoundEvents.ARMOR_EQUIP_GENERIC, 0.5f, 0f,
            () -> Ingredient.of(net.minecraft.world.item.Items.NAUTILUS_SHELL)),

    PURPUR("usefultoolsmod:purpur", 15,
            new int[]{2, 5, 6, 2}, 12,
            SoundEvents.ARMOR_EQUIP_GENERIC, 0.5f, 0f,
            () -> Ingredient.of(net.minecraft.world.item.Items.POPPED_CHORUS_FRUIT)),

    GHAST_TEAR("usefultoolsmod:ghast_tear", 20,
            new int[]{2, 5, 6, 3}, 18,
            SoundEvents.ARMOR_EQUIP_GENERIC, 1f, 0f,
            () -> Ingredient.of(net.minecraft.world.item.Items.GHAST_TEAR)),

    EYE_OF_ENDER("usefultoolsmod:eye_of_ender", 22,
            new int[]{2, 5, 7, 3}, 20,
            SoundEvents.ARMOR_EQUIP_GENERIC, 1f, 0.05f,
            () -> Ingredient.of(net.minecraft.world.item.Items.ENDER_EYE)),

    SHULKER("usefultoolsmod:shulker", 25,
            new int[]{3, 6, 7, 3}, 16,
            SoundEvents.ARMOR_EQUIP_GENERIC, 1.5f, 0.1f,
            () -> Ingredient.of(net.minecraft.world.item.Items.SHULKER_SHELL)),

    TURTLE_SCUTE("usefultoolsmod:turtle_scute", 25,
            new int[]{2, 5, 7, 3}, 16,
            SoundEvents.ARMOR_EQUIP_GENERIC, 1.5f, 0.05f,
            () -> Ingredient.of(net.minecraft.world.item.Items.SCUTE)),

    ECHO_SHARD("usefultoolsmod:echo_shard", 30,
            new int[]{3, 6, 7, 3}, 18,
            SoundEvents.ARMOR_EQUIP_GENERIC, 2f, 0.05f,
            () -> Ingredient.of(net.minecraft.world.item.Items.ECHO_SHARD)),

    DRAGON_BREATH("usefultoolsmod:dragon_breath", 35,
            new int[]{3, 6, 8, 3}, 20,
            SoundEvents.ARMOR_EQUIP_GENERIC, 2.5f, 0.1f,
            () -> Ingredient.of(net.minecraft.world.item.Items.DRAGON_BREATH));

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

    @Override
    public int getDurabilityForType(ArmorItem.Type pType) {
        return BASE_DURABILITY[pType.getSlot().getIndex()] * this.durabilityMultiplier;
    }

    @Override
    public int getDefenseForType(ArmorItem.Type pType) {
        return this.protectionAmounts[pType.getSlot().getIndex()];
    }

    @Override
    public int getEnchantmentValue() {
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
