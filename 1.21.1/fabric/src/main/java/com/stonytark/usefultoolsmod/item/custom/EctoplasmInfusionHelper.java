package com.stonytark.usefultoolsmod.item.custom;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public final class EctoplasmInfusionHelper {
    private static final String TAG_KEY = "ectoplasm_infused";
    private static final String LEGACY_TAG_KEY = "ecto_infused";

    private EctoplasmInfusionHelper() {}

    public static boolean isInfused(ItemStack stack) {
        if (stack.isEmpty()) return false;
        // Ecto tools are inherently infused -- no tag check needed
        if (isEctoItem(stack.getItem())) return true;
        NbtComponent data = stack.getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT);
        if (data == NbtComponent.DEFAULT) return false;
        NbtCompound tag = data.copyNbt();
        return tag.getBoolean(TAG_KEY) || tag.getBoolean(LEGACY_TAG_KEY);
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

        NbtComponent existing = stack.getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT);
        NbtCompound tag = new NbtCompound();
        if (existing != NbtComponent.DEFAULT) {
            tag = existing.copyNbt();
        }

        if (infused) {
            tag.putBoolean(TAG_KEY, true);
            tag.remove(LEGACY_TAG_KEY);
            stack.set(DataComponentTypes.CUSTOM_DATA, NbtComponent.of(tag));
        } else {
            tag.remove(TAG_KEY);
            tag.remove(LEGACY_TAG_KEY);
            if (tag.isEmpty()) {
                stack.remove(DataComponentTypes.CUSTOM_DATA);
            } else {
                stack.set(DataComponentTypes.CUSTOM_DATA, NbtComponent.of(tag));
            }
        }
    }
}
