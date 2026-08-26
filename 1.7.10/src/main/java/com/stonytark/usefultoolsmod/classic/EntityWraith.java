package com.stonytark.usefultoolsmod.classic;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityWraith extends EntityMob {
    private int lungeCooldown;

    public EntityWraith(World world) {
        super(world); setSize(1.2F, 2.25F); noClip = true; isImmuneToFire = true;
        tasks.addTask(1, new EntityAIAttackOnCollide(this, 1.15D, true));
        tasks.addTask(5, new EntityAIWander(this, .9D));
        targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
        targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityGhost.class, 0, true));
    }

    @Override protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30D);
        getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(6D);
        getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(.32D);
        getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(32D);
    }

    @Override public void onLivingUpdate() {
        super.onLivingUpdate(); noClip = true; fallDistance = 0; extinguish(); motionY *= .6D;
        if (!worldObj.isRemote && ticksExisted % 20 == 0) {
            EntityPlayer player = worldObj.getClosestPlayerToEntity(this, 16D);
            if (player != null) player.addStat(ClassicAchievements.encounterWraith, 1);
        }
        if (lungeCooldown > 0) lungeCooldown--;
        if (!worldObj.isRemote && ClassicSoulLantern.activeWithin(worldObj, posX, posY, posZ, 12)) {
            setAttackTarget(null); motionY += .08D; motionX *= 1.1D; motionZ *= 1.1D;
        } else if (!worldObj.isRemote && getAttackTarget() != null && lungeCooldown == 0) {
            Entity target = getAttackTarget(); double dx = target.posX - posX, dy = target.posY + 1 - posY, dz = target.posZ - posZ;
            double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);
            if (distance > 3 && distance < 12 && canEntityBeSeen(target)) {
                motionX = dx / distance * .85D; motionY = dy / distance * .85D; motionZ = dz / distance * .85D; lungeCooldown = 40;
            }
        }
    }

    @Override public boolean attackEntityAsMob(Entity target) {
        boolean hit = super.attackEntityAsMob(target);
        if (hit && target instanceof net.minecraft.entity.EntityLivingBase)
            ((net.minecraft.entity.EntityLivingBase)target).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 60, 0));
        return hit;
    }

    @Override public boolean attackEntityFrom(DamageSource source, float amount) {
        if (source == DamageSource.outOfWorld || source.isExplosion()) return super.attackEntityFrom(source, amount);
        Entity attacker = source.getEntity();
        if (attacker instanceof EntityPlayer && ClassicInfusion.isInfused(((EntityPlayer)attacker).getHeldItem()))
            return super.attackEntityFrom(source, amount);
        return false;
    }

    @Override protected void dropFewItems(boolean recentlyHit, int looting) {
        entityDropItem(new ItemStack(ClassicFeatureRegistry.condensedEctoplasm, 1 + (looting > 0 && rand.nextInt(4) < looting ? 1 : 0)), 0);
        entityDropItem(new ItemStack(ClassicGeneratedCatalog.ITEMS.get("ectoplasm"), 1 + rand.nextInt(3)), 0);
    }

    @Override public boolean getCanSpawnHere() {
        return ClassicGeneratedConfig.booleanValue("ghostEnabled") && ClassicGeneratedConfig.booleanValue("wraithEnabled")
                && rand.nextDouble() <= ClassicGeneratedConfig.doubleValue("wraithSpawnChance")
                && worldObj.getBlockLightValue((int)posX, (int)posY, (int)posZ) <= 3
                && !ClassicSoulLantern.activeWithin(worldObj, posX, posY, posZ, 24) && super.getCanSpawnHere();
    }
    @Override protected String getLivingSound() { return "usefultoolsmod:entity.wraith.ambient"; }
    @Override protected String getHurtSound() { return "usefultoolsmod:entity.wraith.hurt"; }
    @Override protected String getDeathSound() { return "usefultoolsmod:entity.wraith.death"; }
}
