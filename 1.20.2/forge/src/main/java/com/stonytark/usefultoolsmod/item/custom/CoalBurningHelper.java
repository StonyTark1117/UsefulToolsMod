package com.stonytark.usefultoolsmod.item.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

/**
 * Utility for reading and writing the coal-burning state stored in an
 * ItemStack's NBT tag.  All coal tool/armor items delegate
 * their isFoil() check here, and ModEvents uses this to manage the
 * burning lifecycle.
 */
public final class CoalBurningHelper {

    private static final String TAG_KEY = "coal_burning";

    private CoalBurningHelper() {}

    public static boolean isBurning(ItemStack stack) {
        if (stack.isEmpty()) return false;
        CompoundTag tag = stack.getTag();
        if (tag == null) return false;
        return tag.getBoolean(TAG_KEY);
    }

    public static void setBurning(ItemStack stack, boolean burning) {
        if (stack.isEmpty()) return;

        if (burning) {
            stack.getOrCreateTag().putBoolean(TAG_KEY, true);
        } else {
            CompoundTag tag = stack.getTag();
            if (tag != null) {
                tag.remove(TAG_KEY);
                if (tag.isEmpty()) {
                    stack.setTag(null);
                }
            }
        }
    }
}
