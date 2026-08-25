package com.stonytark.usefultoolsmod.event;

import com.stonytark.usefultoolsmod.Config;
import com.stonytark.usefultoolsmod.item.ModItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/** Applies the offensive tool damage transformation once to the original hit. */
public final class DamageModifier {
    private DamageModifier() {}

    public static float compute(LivingEntity target, DamageSource source, float amount) {
        if (!(source.getAttacker() instanceof PlayerEntity attacker)) return amount;
        ItemStack held = attacker.getMainHandStack();
        if (held.isEmpty()) return amount;

        float modified = amount;
        if (Config.sweetBerryEnabled && isTool(held, ModItems.SWEET_BERRY_SWORD, ModItems.SWEET_BERRY_PICKAXE,
                ModItems.SWEET_BERRY_SHOVEL, ModItems.SWEET_BERRY_AXE, ModItems.SWEET_BERRY_HOE)) {
            modified += 1.0f;
        }
        if (Config.glassEnabled && Config.glassEffects && isTool(held, ModItems.GLASS_SWORD, ModItems.GLASS_PICKAXE,
                ModItems.GLASS_SHOVEL, ModItems.GLASS_AXE, ModItems.GLASS_HOE)) {
            modified += 2.0f;
        }
        if (Config.spongeEnabled && Config.spongeEffects && target.isTouchingWater()
                && isTool(held, ModItems.SPONGE_SWORD, ModItems.SPONGE_PICKAXE, ModItems.SPONGE_SHOVEL,
                          ModItems.SPONGE_AXE, ModItems.SPONGE_HOE)) {
            modified += 3.0f;
        }
        if (Config.pointedDripstoneEnabled && Config.pointedDripstoneEffects
                && isTool(held, ModItems.POINTED_DRIPSTONE_SWORD, ModItems.POINTED_DRIPSTONE_PICKAXE,
                          ModItems.POINTED_DRIPSTONE_SHOVEL, ModItems.POINTED_DRIPSTONE_AXE,
                          ModItems.POINTED_DRIPSTONE_HOE)) {
            modified *= 1.3f;
            if (attacker.fallDistance > 0 && !attacker.isOnGround()) modified *= 1.5f;
        }
        if (Config.cactusEnabled && Config.cactusEffects
                && isTool(held, ModItems.CACTUS_SWORD, ModItems.CACTUS_PICKAXE, ModItems.CACTUS_SHOVEL,
                          ModItems.CACTUS_AXE, ModItems.CACTUS_HOE)) {
            modified += 1.0f;
        }
        if (Config.boneEnabled && Config.boneEffects && target instanceof MobEntity mob && mob.isUndead()
                && isTool(held, ModItems.BONE_SWORD, ModItems.BONE_PICKAXE, ModItems.BONE_SHOVEL,
                          ModItems.BONE_AXE, ModItems.BONE_HOE)) {
            modified *= 1.5f;
        }
        if (Config.phantomEnabled && Config.phantomEffects && !attacker.getWorld().isDay()
                && isTool(held, ModItems.PHANTOM_SWORD, ModItems.PHANTOM_PICKAXE, ModItems.PHANTOM_SHOVEL,
                          ModItems.PHANTOM_AXE, ModItems.PHANTOM_HOE)) {
            modified += 2.0f;
        }
        return modified;
    }

    private static boolean isTool(ItemStack stack, Item... candidates) {
        for (Item candidate : candidates) {
            if (stack.isOf(candidate)) return true;
        }
        return false;
    }
}
