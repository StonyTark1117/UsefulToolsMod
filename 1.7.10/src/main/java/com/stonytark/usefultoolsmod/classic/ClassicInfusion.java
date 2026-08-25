package com.stonytark.usefultoolsmod.classic;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public final class ClassicInfusion {
    public static final String TAG = "UsefulToolsEctoplasmInfused";

    private ClassicInfusion() {}

    public static boolean isInfused(ItemStack stack) {
        return stack != null && stack.hasTagCompound() && stack.getTagCompound().getBoolean(TAG);
    }

    public static ItemStack infuse(ItemStack stack) {
        if (stack == null) return null;
        ItemStack result = stack.copy();
        NBTTagCompound tag = result.hasTagCompound() ? result.getTagCompound() : new NBTTagCompound();
        tag.setBoolean(TAG, true);
        result.setTagCompound(tag);
        return result;
    }
}
