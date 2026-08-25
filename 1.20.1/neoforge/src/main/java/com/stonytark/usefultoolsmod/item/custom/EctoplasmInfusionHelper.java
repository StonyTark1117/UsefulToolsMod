package com.stonytark.usefultoolsmod.item.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

/**
 * Utility for reading and writing the ectoplasm-infused state stored in an
 * ItemStack's NBT tag.  Infused weapons can damage ghosts.
 */
public final class EctoplasmInfusionHelper {

    private static final String TAG_KEY = "ectoplasm_infused";

    private EctoplasmInfusionHelper() {}

    public static boolean isInfused(ItemStack stack) {
        if (stack.isEmpty()) return false;
        // Ecto tools are inherently infused -- no tag check needed
        if (isEctoItem(stack.getItem())) return true;
        CompoundTag tag = stack.getTag();
        if (tag == null) return false;
        return tag.getBoolean(TAG_KEY);
    }

    /** Returns true if the item is an ecto tool class (always considered infused). */
    public static boolean isEctoItem(net.minecraft.world.item.Item item) {
        return item instanceof EctoSwordItem
            || item instanceof EctoPickaxeItem
            || item instanceof EctoAxeItem
            || item instanceof EctoShovelItem
            || item instanceof EctoHoeItem;
    }

    public static void setInfused(ItemStack stack, boolean infused) {
        if (stack.isEmpty()) return;

        if (infused) {
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
