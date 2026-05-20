package com.stonytark.usefultoolsmod.fabric.mixin;

import com.stonytark.usefultoolsmod.fabric.platform.CombatHooksImpl;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BiPredicate;

/**
 * Cancels {@link Mob#setTarget(LivingEntity)} when {@link CombatHooksImpl#targetPredicate}
 * returns false. Restores the Rotten Flesh undead-neutral, Pumpkin Pie enderman-avoidance,
 * Bone reduced-detection, Phantom-membrane phantom-ignore, etc. behaviours that
 * Architectury has no event for.
 */
@Mixin(Mob.class)
public abstract class MobMixin {

    @Inject(method = "setTarget(Lnet/minecraft/world/entity/LivingEntity;)V", at = @At("HEAD"), cancellable = true)
    private void usefultoolsmod_cancelDisallowedTarget(@Nullable LivingEntity target, CallbackInfo ci) {
        if (target == null) return;
        BiPredicate<Mob, LivingEntity> predicate = CombatHooksImpl.targetPredicate;
        if (predicate != null && !predicate.test((Mob) (Object) this, target)) {
            ci.cancel();
        }
    }
}
