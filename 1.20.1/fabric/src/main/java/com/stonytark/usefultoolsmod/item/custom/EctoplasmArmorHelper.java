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
     * Condition: full set of ecto armor material, OR all 4 armor slots filled with ectoplasm-infused armor.
     */
    public static boolean isGhostInvisible(PlayerEntity player) {
        // Check for full ecto armor set (native ecto material)
        if (hasFullEctoArmorSet(player)) return true;

        // Check if ALL 4 armor slots are filled AND infused (any armor type, including modded)
        int slotCount = 0;
        for (ItemStack armorStack : player.getArmorItems()) {
            if (armorStack.isEmpty() || !EctoplasmInfusionHelper.isInfused(armorStack)) {
                return false;
            }
            slotCount++;
        }
        return slotCount == 4;
    }

    public static boolean hasFullEctoArmorSet(PlayerEntity player) {
        for (ItemStack armorStack : player.getArmorItems()) {
            if (armorStack.isEmpty()) return false;
            if (!(armorStack.getItem() instanceof ArmorItem armor)) return false;
            if (armor.getMaterial() != ModArmorMaterials.ECTO) return false;
        }
        return true;
    }
}
