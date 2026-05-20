package com.stonytark.usefultoolsmod.item.custom;

import com.stonytark.usefultoolsmod.item.ModArmorMaterials;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;

/**
 * Utility for checking if a player is protected from ghost detection
 * by wearing ectoplasm armor or ectoplasm-infused armor.
 */
public final class EctoplasmArmorHelper {

    private EctoplasmArmorHelper() {}

    /**
     * Returns true if the player is "ghost-invisible" — ghosts will ignore them.
     * Each of the 4 armor slots must be either native Spectral (ecto) armor OR an
     * ectoplasm-infused piece. Mix and match is allowed.
     */
    public static boolean isGhostInvisible(Player player) {
        int count = 0;
        for (ItemStack armorStack : player.getArmorSlots()) {
            if (armorStack.isEmpty()) return false;
            if (!isSpectralOrInfused(armorStack)) return false;
            count++;
        }
        return count == 4;
    }

    public static boolean isSpectralOrInfused(ItemStack stack) {
        if (stack.getItem() instanceof ArmorItem armor
                && ModArmorMaterials.ECTO_ARMOR_MATERIAL.is(armor.getMaterial())) {
            return true;
        }
        return EctoplasmInfusionHelper.isInfused(stack);
    }

    public static boolean hasFullEctoArmorSet(Player player) {
        for (ItemStack armorStack : player.getArmorSlots()) {
            if (armorStack.isEmpty()) return false;
            if (!(armorStack.getItem() instanceof ArmorItem armor)) return false;
            if (!ModArmorMaterials.ECTO_ARMOR_MATERIAL.is(armor.getMaterial())) return false;
        }
        return true;
    }
}
