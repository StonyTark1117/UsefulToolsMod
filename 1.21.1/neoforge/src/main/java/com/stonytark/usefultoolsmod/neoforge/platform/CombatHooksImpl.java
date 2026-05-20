package com.stonytark.usefultoolsmod.neoforge.platform;

import com.stonytark.usefultoolsmod.platform.CombatHooks;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingChangeTargetEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.function.BiPredicate;

/**
 * NeoForge implementations of CombatHooks @ExpectPlatform methods.
 * Damage mutation and target-change use NeoForge events directly.
 * FireBlock#getBurnOdds(BlockState) is exposed (public) by NeoForge.
 */
@EventBusSubscriber(modid = com.stonytark.usefultoolsmod.UsefultoolsMod.MOD_ID)
public final class CombatHooksImpl {
    private CombatHooksImpl() {}

    private static volatile CombatHooks.DamageModifierFn damageModifier;
    private static volatile BiPredicate<Mob, LivingEntity> targetPredicate;

    public static void registerDamageModifier(CombatHooks.DamageModifierFn fn) {
        damageModifier = fn;
    }

    public static void registerTargetPredicate(BiPredicate<Mob, LivingEntity> predicate) {
        targetPredicate = predicate;
    }

    public static int getBurnOdds(BlockState state) {
        FireBlock fire = (FireBlock) Blocks.FIRE;
        return fire.getBurnOdds(state);
    }

    // === Event listeners — bridge the registered fns to NeoForge events ===

    @SubscribeEvent
    public static void onLivingIncomingDamage(LivingIncomingDamageEvent event) {
        CombatHooks.DamageModifierFn fn = damageModifier;
        if (fn == null) return;
        float newAmount = fn.modify(event.getEntity(), event.getSource(), event.getAmount());
        if (newAmount != event.getAmount()) {
            event.setAmount(newAmount);
        }
    }

    @SubscribeEvent
    public static void onLivingChangeTarget(LivingChangeTargetEvent event) {
        BiPredicate<Mob, LivingEntity> predicate = targetPredicate;
        if (predicate == null) return;
        if (!(event.getEntity() instanceof Mob mob)) return;
        LivingEntity target = event.getNewAboutToBeSetTarget();
        if (target == null) return;
        if (!predicate.test(mob, target)) {
            event.setCanceled(true);
        }
    }
}
