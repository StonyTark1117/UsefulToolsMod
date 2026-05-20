package com.stonytark.usefultoolsmod.event;

import com.stonytark.usefultoolsmod.Config;
import com.stonytark.usefultoolsmod.item.ModItems;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/**
 * Damage-amount mutations that fire when an attacker holds a special tool.
 *
 * Wired into CombatHooks.registerDamageModifier from ModEvents.init() — on
 * NeoForge it fires inside a LivingIncomingDamageEvent listener; on Fabric
 * it fires inside a LivingEntity#hurt @ModifyVariable mixin.
 *
 * The actual MobEffect applications (cactus poison, slime knockback, etc.)
 * stay in ModEvents.onLivingHurt — those don't need damage-amount mutation.
 */
public final class DamageModifier {
    private DamageModifier() {}

    public static float compute(LivingEntity target, DamageSource source, float amount) {
        if (!(source.getEntity() instanceof Player attacker)) return amount;
        ItemStack held = attacker.getMainHandItem();
        if (held.isEmpty()) return amount;

        float mod = amount;

        if (Config.sweetBerryEnabled && isTool(held, ModItems.SWEET_BERRY_SWORD, ModItems.SWEET_BERRY_PICKAXE,
                ModItems.SWEET_BERRY_SHOVEL, ModItems.SWEET_BERRY_AXE, ModItems.SWEET_BERRY_HOE)) {
            mod += 1.0f;
        }
        if (Config.glassEnabled && Config.glassEffects && isTool(held, ModItems.GLASS_SWORD, ModItems.GLASS_PICKAXE,
                ModItems.GLASS_SHOVEL, ModItems.GLASS_AXE, ModItems.GLASS_HOE)) {
            mod += 2.0f;
        }
        if (Config.spongeEnabled && Config.spongeEffects && target.isInWater()
                && isTool(held, ModItems.SPONGE_SWORD, ModItems.SPONGE_PICKAXE, ModItems.SPONGE_SHOVEL,
                          ModItems.SPONGE_AXE, ModItems.SPONGE_HOE)) {
            mod += 3.0f;
        }
        if (Config.dripstoneEnabled && Config.dripstoneEffects && isTool(held, ModItems.POINTED_DRIPSTONE_SWORD,
                ModItems.POINTED_DRIPSTONE_PICKAXE, ModItems.POINTED_DRIPSTONE_SHOVEL,
                ModItems.POINTED_DRIPSTONE_AXE, ModItems.POINTED_DRIPSTONE_HOE)) {
            mod *= 1.3f;
            if (attacker.fallDistance > 0 && !attacker.onGround()) mod *= 1.5f;
        }
        if (Config.cactusEnabled && Config.cactusEffects && isTool(held, ModItems.CACTUS_SWORD, ModItems.CACTUS_PICKAXE,
                ModItems.CACTUS_SHOVEL, ModItems.CACTUS_AXE, ModItems.CACTUS_HOE)) {
            mod += 1.0f;
        }
        if (Config.boneEnabled && Config.boneEffects && target instanceof Mob mob && mob.isInvertedHealAndHarm()
                && isTool(held, ModItems.BONE_SWORD, ModItems.BONE_PICKAXE, ModItems.BONE_SHOVEL,
                          ModItems.BONE_AXE, ModItems.BONE_HOE)) {
            mod *= 1.5f;
        }
        if (Config.phantomEnabled && Config.phantomEffects && !attacker.level().isDay()
                && isTool(held, ModItems.PHANTOM_SWORD, ModItems.PHANTOM_PICKAXE, ModItems.PHANTOM_SHOVEL,
                          ModItems.PHANTOM_AXE, ModItems.PHANTOM_HOE)) {
            mod += 2.0f;
        }

        return mod;
    }

    @SafeVarargs
    private static boolean isTool(ItemStack stack,
                                  dev.architectury.registry.registries.RegistrySupplier<net.minecraft.world.item.Item>... candidates) {
        for (var supplier : candidates) {
            if (stack.is(supplier.get())) return true;
        }
        return false;
    }
}
