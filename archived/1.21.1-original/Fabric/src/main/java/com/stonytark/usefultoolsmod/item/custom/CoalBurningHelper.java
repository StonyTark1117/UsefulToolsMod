package com.stonytark.usefultoolsmod.item.custom;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.item.ItemStack;

public final class CoalBurningHelper {
    private static final String TAG_KEY = "coal_burning";

    private CoalBurningHelper() {}

    public static boolean isBurning(ItemStack stack) {
        if (stack.isEmpty()) return false;
        NbtComponent data = stack.getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT);
        if (data == NbtComponent.DEFAULT) return false;
        NbtCompound tag = data.getNbt();
        return tag.getBoolean(TAG_KEY);
    }

    public static void setBurning(ItemStack stack, boolean burning) {
        if (stack.isEmpty()) return;

        NbtComponent existing = stack.getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT);
        NbtCompound tag = new NbtCompound();
        if (existing != NbtComponent.DEFAULT) {
            tag = existing.getNbt().copy();
        }

        if (burning) {
            tag.putBoolean(TAG_KEY, true);
            stack.set(DataComponentTypes.CUSTOM_DATA, NbtComponent.of(tag));
        } else {
            tag.remove(TAG_KEY);
            if (tag.isEmpty()) {
                stack.remove(DataComponentTypes.CUSTOM_DATA);
            } else {
                stack.set(DataComponentTypes.CUSTOM_DATA, NbtComponent.of(tag));
            }
        }
    }
}
