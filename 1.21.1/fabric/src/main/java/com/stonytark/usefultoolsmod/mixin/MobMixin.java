package com.stonytark.usefultoolsmod.mixin;

import com.stonytark.usefultoolsmod.Config;
import com.stonytark.usefultoolsmod.event.ModEvents;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.mob.DrownedEntity;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.mob.GuardianEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PhantomEntity;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** Applies armor-based target suppression at MobEntity#setTarget. */
@Mixin(MobEntity.class)
public abstract class MobMixin {
    @Inject(method = "setTarget(Lnet/minecraft/entity/LivingEntity;)V", at = @At("HEAD"), cancellable = true)
    private void usefultoolsmod$allowTarget(LivingEntity newTarget, CallbackInfo ci) {
        if (!(newTarget instanceof PlayerEntity player)) return;
        MobEntity mob = (MobEntity) (Object) this;
        ItemStack head = player.getEquippedStack(EquipmentSlot.HEAD);

        if (Config.rottenFleshEnabled && Config.rottenFleshUndeadNeutral
                && ModEvents.isWearingFullSet(player, ModEvents::isRottenFleshArmor)
                && (mob instanceof ZombieEntity || mob instanceof AbstractSkeletonEntity || mob instanceof PhantomEntity)) {
            ci.cancel(); return;
        }
        if (Config.pumpkinPieEnabled && Config.pumpkinPieEndermanAvoidance
                && ModEvents.isPumpkinPieArmor(head) && mob instanceof EndermanEntity) {
            ci.cancel(); return;
        }
        if (Config.boneEnabled && Config.boneEffects && ModEvents.isBoneArmor(head)
                && (mob instanceof ZombieEntity || mob instanceof AbstractSkeletonEntity)
                && mob.distanceTo(player) > 16) {
            ci.cancel(); return;
        }
        if (Config.phantomEnabled && Config.phantomEffects
                && ModEvents.isWearingFullSet(player, ModEvents::isPhantomArmor) && mob instanceof PhantomEntity) {
            ci.cancel(); return;
        }
        if (Config.nautilusEnabled && Config.nautilusEffects
                && ModEvents.isWearingFullSet(player, ModEvents::isNautilusArmor)
                && (mob instanceof GuardianEntity || mob instanceof DrownedEntity)) {
            ci.cancel(); return;
        }
        if (Config.eyeOfEnderEnabled && Config.eyeOfEnderEffects
                && ModEvents.isWearingFullSet(player, ModEvents::isEyeOfEnderArmor) && mob instanceof EndermanEntity) {
            ci.cancel(); return;
        }
        if (Config.echoShardEnabled && Config.echoShardEffects
                && ModEvents.isWearingFullSet(player, ModEvents::isEchoShardArmor) && mob instanceof WardenEntity) {
            ci.cancel(); return;
        }
        if (Config.turtleScuteEnabled && Config.turtleScuteEffects
                && ModEvents.isWearingFullSet(player, ModEvents::isTurtleScuteArmor) && mob instanceof GuardianEntity) {
            ci.cancel();
        }
    }
}
