package com.stonytark.usefultoolsmod.item.custom;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

public final class CoalBurningHelper {
    private static final String TAG_KEY = "coal_burning";

    private CoalBurningHelper() {}

    public static boolean isBurning(ItemStack stack) {
        if (stack.isEmpty()) return false;
        NbtCompound nbt = stack.getNbt();
        if (nbt == null) return false;
        return nbt.getBoolean(TAG_KEY);
    }

    public static void setBurning(ItemStack stack, boolean burning) {
        if (stack.isEmpty()) return;

        if (burning) {
            stack.getOrCreateNbt().putBoolean(TAG_KEY, true);
        } else {
            NbtCompound nbt = stack.getNbt();
            if (nbt != null) {
                nbt.remove(TAG_KEY);
                if (nbt.isEmpty()) {
                    stack.setNbt(null);
                }
            }
        }
    }
}
