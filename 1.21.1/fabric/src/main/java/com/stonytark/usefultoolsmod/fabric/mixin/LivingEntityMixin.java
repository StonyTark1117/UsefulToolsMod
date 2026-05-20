package com.stonytark.usefultoolsmod.fabric.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.stonytark.usefultoolsmod.fabric.platform.CombatHooksImpl;
import com.stonytark.usefultoolsmod.platform.CombatHooks;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

/**
 * Mutates outgoing damage amount via {@link CombatHooksImpl#damageModifier}.
 * Restores the Sweet Berry +1 / Glass +2 / Dripstone ×1.3 etc. effects that
 * Architectury's LIVING_HURT can't express (it can only cancel, not mutate).
 */
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @ModifyVariable(
            method = "hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z",
            at = @At("HEAD"),
            argsOnly = true
    )
    private float usefultoolsmod_modifyDamage(float amount, @Local(argsOnly = true) DamageSource source) {
        CombatHooks.DamageModifierFn fn = CombatHooksImpl.damageModifier;
        if (fn == null) return amount;
        return fn.modify((LivingEntity) (Object) this, source, amount);
    }
}
