package com.stonytark.usefultoolsmod.event;

import com.stonytark.usefultoolsmod.Config;
import com.stonytark.usefultoolsmod.item.ModArmorMaterials;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;

/**
 * Decides whether a Mob is allowed to target a given LivingEntity, based on
 * what armor the target player is wearing.
 *
 * Wired into CombatHooks.registerTargetPredicate from ModEvents.init() — on
 * NeoForge it fires inside a LivingChangeTargetEvent listener; on Fabric it
 * fires inside a Mob#setTarget @Inject mixin.
 */
public final class TargetPredicate {
    private TargetPredicate() {}

    /** Returns {@code true} if the targeting is allowed, {@code false} to cancel. */
    public static boolean allow(Mob mob, LivingEntity target) {
        if (!(target instanceof Player player)) return true;

        // Rotten Flesh full set — undead mobs ignore player
        if (Config.rottenFleshEnabled && Config.rottenFleshUndeadNeutral
                && hasFullSet(player, ModArmorMaterials.ROTTEN_FLESH_ARMOR_MATERIAL)) {
            if (mob instanceof Zombie || mob instanceof AbstractSkeleton || mob instanceof Phantom) {
                return false;
            }
        }
        // Pumpkin Pie helmet — endermen ignore player
        if (Config.pumpkinPieEnabled && Config.pumpkinPieEndermanAvoidance
                && isMaterial(player.getItemBySlot(EquipmentSlot.HEAD),
                        ModArmorMaterials.PUMPKIN_PIE_ARMOR_MATERIAL)
                && mob instanceof EnderMan) {
            return false;
        }
        // Bone helmet — undead reduced detection (16+ blocks)
        if (Config.boneEnabled && Config.boneEffects
                && isMaterial(player.getItemBySlot(EquipmentSlot.HEAD), ModArmorMaterials.BONE_ARMOR_MATERIAL)
                && (mob instanceof Zombie || mob instanceof AbstractSkeleton)
                && mob.distanceTo(player) > 16) {
            return false;
        }
        // Phantom membrane full set — phantoms ignore
        if (Config.phantomEnabled && Config.phantomEffects
                && hasFullSet(player, ModArmorMaterials.PHANTOM_ARMOR_MATERIAL)
                && mob instanceof Phantom) {
            return false;
        }
        // Nautilus full set — aquatic mobs ignore
        if (Config.nautilusEnabled && Config.nautilusEffects
                && hasFullSet(player, ModArmorMaterials.NAUTILUS_ARMOR_MATERIAL)
                && (mob instanceof Guardian || mob instanceof Drowned)) {
            return false;
        }
        // Eye of Ender full set — endermen neutral
        if (Config.eyeOfEnderEnabled && Config.eyeOfEnderEffects
                && hasFullSet(player, ModArmorMaterials.EYE_OF_ENDER_ARMOR_MATERIAL)
                && mob instanceof EnderMan) {
            return false;
        }
        // Echo Shard full set — warden neutral
        if (Config.echoShardEnabled && Config.echoShardEffects
                && hasFullSet(player, ModArmorMaterials.ECHO_SHARD_ARMOR_MATERIAL)
                && mob instanceof Warden) {
            return false;
        }
        // Turtle Scute full set — guardians ignore
        if (Config.turtleScuteEnabled && Config.turtleScuteEffects
                && hasFullSet(player, ModArmorMaterials.TURTLE_SCUTE_ARMOR_MATERIAL)
                && mob instanceof Guardian) {
            return false;
        }
        return true;
    }

    private static boolean isMaterial(ItemStack stack,
                                      net.minecraft.core.Holder<net.minecraft.world.item.ArmorMaterial> material) {
        return !stack.isEmpty() && stack.getItem() instanceof ArmorItem armor && material.is(armor.getMaterial());
    }

    private static boolean hasFullSet(Player player,
                                      net.minecraft.core.Holder<net.minecraft.world.item.ArmorMaterial> material) {
        return isMaterial(player.getItemBySlot(EquipmentSlot.HEAD), material)
            && isMaterial(player.getItemBySlot(EquipmentSlot.CHEST), material)
            && isMaterial(player.getItemBySlot(EquipmentSlot.LEGS), material)
            && isMaterial(player.getItemBySlot(EquipmentSlot.FEET), material);
    }
}
