package com.stonytark.usefultoolsmod.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.BiPredicate;

/**
 * Cross-loader combat-related hooks that Architectury 13.0.8 doesn't expose
 * directly. Each method has a Fabric impl (mixin-driven) and a NeoForge impl
 * (event-driven).
 */
public final class CombatHooks {
    private CombatHooks() {}

    /**
     * Registers a function that mutates outgoing damage amounts.
     *
     * The returned float replaces the damage amount before vanilla calculation.
     * Pass-through is {@code (target, source, amount) -> amount}.
     *
     * Called once from common init. Only the most recent registration is used.
     */
    @ExpectPlatform
    public static void registerDamageModifier(DamageModifierFn fn) {
        throw new AssertionError("ExpectPlatform impl not found for CombatHooks.registerDamageModifier");
    }

    /**
     * Registers a predicate that decides whether a Mob is allowed to target a
     * given LivingEntity. Returning {@code false} cancels the target assignment.
     *
     * Called once from common init.
     */
    @ExpectPlatform
    public static void registerTargetPredicate(BiPredicate<Mob, LivingEntity> predicate) {
        throw new AssertionError("ExpectPlatform impl not found for CombatHooks.registerTargetPredicate");
    }

    /**
     * Returns the vanilla FireBlock burn-odds for a given block state.
     * Result is {@code 0} for non-flammable blocks.
     * NeoForge has this public; Fabric needs an accessor mixin on FireBlock.
     */
    @ExpectPlatform
    public static int getBurnOdds(BlockState state) {
        throw new AssertionError("ExpectPlatform impl not found for CombatHooks.getBurnOdds");
    }

    @FunctionalInterface
    public interface DamageModifierFn {
        float modify(LivingEntity target, DamageSource source, float amount);
    }
}
