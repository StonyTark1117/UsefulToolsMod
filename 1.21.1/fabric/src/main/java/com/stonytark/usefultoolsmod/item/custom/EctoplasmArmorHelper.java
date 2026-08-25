package com.stonytark.usefultoolsmod.item.custom;

import com.stonytark.usefultoolsmod.item.ModArmorMaterials;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;

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
    public static boolean isGhostInvisible(PlayerEntity player) {
        int count = 0;
        for (ItemStack armorStack : player.getArmorItems()) {
            if (armorStack.isEmpty()) return false;
            if (!isSpectralOrInfused(armorStack)) return false;
            count++;
        }
        return count == 4;
    }

    public static boolean isSpectralOrInfused(ItemStack stack) {
        if (stack.getItem() instanceof ArmorItem armor
                && armor.getMaterial() == ModArmorMaterials.ECTO_ARMOR_MATERIAL) {
            return true;
        }
        return EctoplasmInfusionHelper.isInfused(stack);
    }

    public static boolean hasFullEctoArmorSet(PlayerEntity player) {
        for (ItemStack armorStack : player.getArmorItems()) {
            if (armorStack.isEmpty()) return false;
            if (!(armorStack.getItem() instanceof ArmorItem armor)) return false;
            if (armor.getMaterial() != ModArmorMaterials.ECTO_ARMOR_MATERIAL) return false;
        }
        return true;
    }
}
