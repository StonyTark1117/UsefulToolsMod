package com.stonytark.usefultoolsmod.item.custom;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

public final class EctoplasmInfusionHelper {
    private static final String TAG_KEY = "ectoplasm_infused";

    private EctoplasmInfusionHelper() {}

    public static boolean isInfused(ItemStack stack) {
        if (stack.isEmpty()) return false;
        // Ecto tools are inherently infused — no tag check needed
        if (isEctoItem(stack.getItem())) return true;
        NbtCompound nbt = stack.getNbt();
        if (nbt == null) return false;
        return nbt.getBoolean(TAG_KEY);
    }

    /** Returns true if the item is an ecto tool class (always considered infused). */
    public static boolean isEctoItem(Item item) {
        return item instanceof EctoSwordItem
            || item instanceof EctoPickaxeItem
            || item instanceof EctoAxeItem
            || item instanceof EctoShovelItem
            || item instanceof EctoHoeItem;
    }

    public static void setInfused(ItemStack stack, boolean infused) {
        if (stack.isEmpty()) return;

        if (infused) {
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
