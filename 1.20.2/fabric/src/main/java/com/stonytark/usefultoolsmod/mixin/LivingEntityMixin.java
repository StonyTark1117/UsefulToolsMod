package com.stonytark.usefultoolsmod.mixin;

import com.stonytark.usefultoolsmod.event.DamageModifier;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

/** Mutates the original damage amount without issuing a second damage event. */
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @ModifyVariable(
            method = "damage(Lnet/minecraft/entity/damage/DamageSource;F)Z",
            at = @At("HEAD"),
            argsOnly = true,
            ordinal = 0
    )
    private float usefultoolsmod$modifyDamage(float amount, DamageSource source) {
        return DamageModifier.compute((LivingEntity) (Object) this, source, amount);
    }
}
