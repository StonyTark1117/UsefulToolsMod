package com.stonytark.usefultoolsmod.fabric.platform;

import com.stonytark.usefultoolsmod.platform.CombatHooks;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.BiPredicate;

/**
 * Fabric implementations of CombatHooks @ExpectPlatform methods.
 *
 * The two callback-style helpers store the registered fn/predicate in static
 * fields; the matching mixins (LivingEntityMixin, MobMixin) read these fields
 * inside the hot path of hurt()/setTarget(). The accessor-style getBurnOdds()
 * calls the accessor mixin directly.
 */
public final class CombatHooksImpl {
    private CombatHooksImpl() {}

    public static volatile CombatHooks.DamageModifierFn damageModifier;
    public static volatile BiPredicate<Mob, LivingEntity> targetPredicate;

    public static void registerDamageModifier(CombatHooks.DamageModifierFn fn) {
        damageModifier = fn;
    }

    public static void registerTargetPredicate(BiPredicate<Mob, LivingEntity> predicate) {
        targetPredicate = predicate;
    }

    public static int getBurnOdds(BlockState state) {
        FireBlock fire = (FireBlock) Blocks.FIRE;
        return ((com.stonytark.usefultoolsmod.fabric.mixin.FireBlockAccessor) fire).usefultoolsmod_getBurnOdds(state);
    }
}
