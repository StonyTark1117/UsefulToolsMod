package com.stonytark.usefultoolsmod.classic;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;

/** Version-native damage and target bridges; each rule is registered exactly once. */
public class ClassicCombatEvents {
    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent event) {
        if ("thorns".equals(event.source.damageType)) return;
        if (event.source.getEntity() instanceof EntityPlayer) {
            EntityPlayer attacker = (EntityPlayer) event.source.getEntity();
            String id = id(attacker.getHeldItem());
            if (id != null) {
                String family = toolFamily(id);
                if (effectEnabled(family)) {
                    boolean undead = event.entityLiving instanceof EntityZombie || event.entityLiving instanceof EntitySkeleton;
                    event.ammount = computeDamageForFamily(family, event.ammount,
                            event.entityLiving.isInWater(), undead, !attacker.worldObj.isDaytime(),
                            attacker.fallDistance, !attacker.onGround);
                }
                applyToolHit(attacker, event.entityLiving, family);
            }
        }
        if (event.entityLiving instanceof EntityPlayer
                && event.source.getEntity() instanceof EntityLivingBase) {
            applyArmorReaction((EntityPlayer) event.entityLiving,
                    (EntityLivingBase) event.source.getEntity(), event);
        }
    }

    private static void applyToolHit(EntityPlayer attacker, EntityLivingBase target, String family) {
        if (feature("honeyEnabled", "honeySticky") && "honey".equals(family))
            effect(target, Potion.moveSlowdown, 60, 0);
        if (feature("pufferfishEnabled", "pufferfishPoisonAura") && "pufferfish".equals(family))
            effect(target, Potion.poison, 100, 0);
        if (ClassicGeneratedConfig.booleanValue("rottenFleshEnabled") && "rotten_flesh".equals(family))
            effect(target, Potion.hunger, 100, 0);
        if (ClassicGeneratedConfig.booleanValue("mushroomEnabled") && "mushroom".equals(family))
            effect(target, Potion.confusion, 60, 0);
        if (feature("chorusFruitEnabled", "chorusFruitTeleport") && "chorus_fruit".equals(family)
                && attacker.getRNG().nextFloat() < 0.10F) teleportRandomly(target, 3, 8);
        if (feature("netherWartEnabled", "netherWartEffects") && "nether_wart".equals(family))
            effect(target, Potion.wither, 60, 0);
        if (feature("cactusEnabled", "cactusEffects") && "cactus".equals(family))
            effect(target, Potion.poison, 40, 0);
        if (feature("netherBrickEnabled", "netherBrickEffects") && "nether_brick".equals(family))
            target.setFire(4);
        if (feature("magmaCreamEnabled", "magmaCreamEffects") && "magma_cream".equals(family)) {
            target.setFire(5); effect(target, Potion.moveSlowdown, 60, 0);
        }
        if (feature("blazeEnabled", "blazeEffects") && "blaze".equals(family)) target.setFire(6);
        if (feature("fniEnabled", "fniFireEffects") && "fni".equals(family)) target.setFire(8);
        if (feature("ghastTearEnabled", "ghastTearEffects") && "ghast_tear".equals(family)) attacker.heal(2.0F);
        if (feature("eyeOfEnderEnabled", "eyeOfEnderEffects") && "eye_of_ender".equals(family)
                && attacker.getRNG().nextFloat() < 0.15F) effect(target, Potion.blindness, 60, 0);
        if (feature("echoShardEnabled", "echoShardEffects") && "echo_shard".equals(family))
            effect(target, Potion.blindness, 100, 0); // Darkness did not exist in 1.7.10.
        if (feature("dragonBreathEnabled", "dragonBreathEffects") && "dragon_breath".equals(family)) {
            effect(target, Potion.wither, 60, 1); effect(target, Potion.poison, 60, 0);
        }
        if (feature("featherEnabled", "featherEffects") && "feather".equals(family))
            knockAway(attacker, target, 0.3D, 0.8D);
        if (feature("slimeEnabled", "slimeEffects") && "slime".equals(family))
            knockAway(attacker, target, 1.5D, 0.4D);
        if (feature("shulkerEnabled", "shulkerEffects") && "shulker".equals(family)) target.motionY += 1.2D;
        if (feature("purpurEnabled", "purpurEffects") && "purpur".equals(family)
                && attacker.getRNG().nextFloat() < 0.10F) teleportRandomly(target, 5, 10);
    }

    private static void applyArmorReaction(EntityPlayer victim, EntityLivingBase attacker,
                                            LivingHurtEvent event) {
        if (feature("sweetBerryEnabled", "sweetBerryThorns") && fullSet(victim, "sweet_berry"))
            attacker.attackEntityFrom(DamageSource.causeThornsDamage(victim), 1.0F);
        if (feature("honeyEnabled", "honeySticky") && fullSet(victim, "honey"))
            effect(attacker, Potion.moveSlowdown, 60, 1);
        if (feature("chorusFruitEnabled", "chorusFruitTeleport") && fullSet(victim, "chorus_fruit")
                && victim.getRNG().nextFloat() < 0.15F && teleportRandomly(victim, 3, 8)) event.setCanceled(true);

        int cactusPieces = armorPieces(victim, "cactus");
        if (feature("cactusEnabled", "cactusEffects") && cactusPieces > 0)
            attacker.attackEntityFrom(DamageSource.causeThornsDamage(victim), cactusPieces * 0.5F);
        if (feature("netherBrickEnabled", "netherBrickEffects") && fullSet(victim, "nether_brick")) attacker.setFire(2);
        if (feature("magmaCreamEnabled", "magmaCreamEffects") && fullSet(victim, "magma_cream")) {
            attacker.setFire(3); effect(attacker, Potion.moveSlowdown, 60, 0);
        }
        if (feature("slimeEnabled", "slimeEffects") && fullSet(victim, "slime"))
            knockAway(victim, attacker, 2.0D, 0.5D);
        if (feature("blazeEnabled", "blazeEffects") && fullSet(victim, "blaze")) attacker.setFire(4);
        if (feature("fniEnabled", "fniFireEffects") && fullSet(victim, "fni")) attacker.setFire(6);
        if (feature("purpurEnabled", "purpurEffects") && fullSet(victim, "purpur")
                && victim.getRNG().nextFloat() < 0.20F && teleportRandomly(victim, 3, 8)) event.setCanceled(true);
        if (feature("shulkerEnabled", "shulkerEffects") && fullSet(victim, "shulker")) attacker.motionY += 1.2D;
        if (feature("echoShardEnabled", "echoShardEffects") && fullSet(victim, "echo_shard"))
            effect(attacker, Potion.blindness, 60, 0);
        if (feature("dragonBreathEnabled", "dragonBreathEffects") && fullSet(victim, "dragon_breath")) {
            effect(attacker, Potion.wither, 60, 0); attacker.setFire(3);
        }
    }

    private static void effect(EntityLivingBase entity, Potion potion, int duration, int amplifier) {
        entity.addPotionEffect(new PotionEffect(potion.id, duration, amplifier));
    }

    private static boolean feature(String enabled, String behavior) {
        return ClassicGeneratedConfig.booleanValue(enabled) && ClassicGeneratedConfig.booleanValue(behavior);
    }

    private static int armorPieces(EntityPlayer player, String family) {
        int result = 0;
        for (int slot = 0; slot < 4; slot++) if (armor(player, slot, family)) result++;
        return result;
    }

    private static void knockAway(EntityLivingBase source, EntityLivingBase target, double strength, double up) {
        double dx = target.posX - source.posX;
        double dz = target.posZ - source.posZ;
        double length = Math.sqrt(dx * dx + dz * dz);
        if (length < 0.001D) length = 1.0D;
        target.motionX += dx / length * strength;
        target.motionY += up;
        target.motionZ += dz / length * strength;
        target.velocityChanged = true;
    }

    private static boolean teleportRandomly(EntityLivingBase entity, int minDistance, int maxDistance) {
        for (int attempt = 0; attempt < 16; attempt++) {
            double angle = entity.getRNG().nextDouble() * Math.PI * 2.0D;
            double distance = minDistance + entity.getRNG().nextDouble() * (maxDistance - minDistance);
            int x = MathHelper.floor_double(entity.posX + Math.cos(angle) * distance);
            int z = MathHelper.floor_double(entity.posZ + Math.sin(angle) * distance);
            int baseY = MathHelper.floor_double(entity.posY);
            for (int dy = -2; dy <= 2; dy++) {
                int y = baseY + dy;
                if (y < 1 || y > 253) continue;
                if (entity.worldObj.isAirBlock(x, y, z) && entity.worldObj.isAirBlock(x, y + 1, z)
                        && entity.worldObj.getBlock(x, y - 1, z).getMaterial().blocksMovement()) {
                    entity.setPositionAndUpdate(x + 0.5D, y, z + 0.5D);
                    return true;
                }
            }
        }
        return false;
    }

    static float computeDamageForFamily(String family, float amount, boolean targetInWater,
                                        boolean undead, boolean night, float fallDistance, boolean airborne) {
        if ("sweet_berry".equals(family)) return amount + 1.0F;
        if ("glass".equals(family)) return amount + 2.0F;
        if ("sponge".equals(family) && targetInWater) return amount + 3.0F;
        if ("pointed_dripstone".equals(family)) {
            float result = amount * 1.3F;
            return airborne && fallDistance > 0 ? result * 1.5F : result;
        }
        if ("cactus".equals(family)) return amount + 1.0F;
        if ("bone".equals(family) && undead) return amount * 1.5F;
        if ("phantom".equals(family) && night) return amount + 2.0F;
        return amount;
    }

    @SubscribeEvent
    public void onTarget(LivingSetAttackTargetEvent event) {
        if (!(event.target instanceof EntityPlayer) || !(event.entityLiving instanceof EntityLiving)) return;
        EntityPlayer player = (EntityPlayer) event.target;
        EntityLiving mob = (EntityLiving) event.entityLiving;
        boolean cancel = false;
        if (ClassicGeneratedConfig.booleanValue("rottenFleshEnabled")
                && ClassicGeneratedConfig.booleanValue("rottenFleshUndeadNeutral")
                && fullSet(player, "rotten_flesh")
                && (mob instanceof EntityZombie || mob instanceof EntitySkeleton)) cancel = true;
        if (ClassicGeneratedConfig.booleanValue("pumpkinPieEnabled")
                && ClassicGeneratedConfig.booleanValue("pumpkinPieEndermanAvoidance")
                && armor(player, 3, "pumpkin_pie") && mob instanceof EntityEnderman) cancel = true;
        if (ClassicGeneratedConfig.booleanValue("boneEnabled")
                && ClassicGeneratedConfig.booleanValue("boneEffects")
                && armor(player, 3, "bone")
                && (mob instanceof EntityZombie || mob instanceof EntitySkeleton)
                && mob.getDistanceToEntity(player) > 16.0F) cancel = true;
        if (ClassicGeneratedConfig.booleanValue("eyeOfEnderEnabled")
                && ClassicGeneratedConfig.booleanValue("eyeOfEnderEffects")
                && fullSet(player, "eye_of_ender") && mob instanceof EntityEnderman) cancel = true;
        if (ClassicGeneratedConfig.booleanValue("ectoplasmSetEnabled")
                && ClassicGeneratedConfig.booleanValue("ectoplasmGhostAvoidance")
                && fullSet(player, "ecto") && mob instanceof EntityGhost) cancel = true;
        if (cancel) mob.setAttackTarget(null);
    }

    static String toolFamily(String id) {
        for (String suffix : new String[] {"_pickaxe", "_shovel", "_sword", "_axe", "_hoe"})
            if (id.endsWith(suffix)) return id.substring(0, id.length() - suffix.length());
        return id;
    }

    private static boolean effectEnabled(String family) {
        if ("sweet_berry".equals(family)) return ClassicGeneratedConfig.booleanValue("sweetBerryEnabled");
        if ("pointed_dripstone".equals(family)) family = "dripstone";
        String key = family.replaceAll("_([a-z])", "$1").toLowerCase();
        if ("glass".equals(family) || "sponge".equals(family) || "cactus".equals(family)
                || "bone".equals(family) || "phantom".equals(family)) {
            return ClassicGeneratedConfig.booleanValue(key + "Enabled")
                    && ClassicGeneratedConfig.booleanValue(key + "Effects");
        }
        return "dripstone".equals(family)
                && ClassicGeneratedConfig.booleanValue("dripstoneEnabled")
                && ClassicGeneratedConfig.booleanValue("dripstoneEffects");
    }

    static boolean fullSet(EntityPlayer player, String family) {
        return armor(player, 0, family) && armor(player, 1, family)
                && armor(player, 2, family) && armor(player, 3, family);
    }

    static boolean armor(EntityPlayer player, int slot, String family) {
        return (family + new String[] {"_boots", "_leggings", "_chestplate", "_helmet"}[slot])
                .equals(id(player.inventory.armorInventory[slot]));
    }

    static String id(ItemStack stack) {
        if (stack == null) return null;
        GameRegistry.UniqueIdentifier identifier = GameRegistry.findUniqueIdentifierFor(stack.getItem());
        return identifier != null && "usefultoolsmod".equals(identifier.modId) ? identifier.name : null;
    }
}
