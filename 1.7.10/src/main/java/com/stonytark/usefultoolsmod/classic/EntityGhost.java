package com.stonytark.usefultoolsmod.classic;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

/** Forge 1.7.10 equivalent of the modern Ghost lifecycle and damage contract. */
public class EntityGhost extends EntityAnimal {
    private static final int MAX_LIFETIME = 5 * 60 * 20;
    private int lifetime;

    public EntityGhost(World world) {
        super(world);
        setSize(0.9F, 1.1F);
        noClip = true;
        isImmuneToFire = true;
        tasks.addTask(1, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0F));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
        getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3D);
        getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(32.0D);
    }

    @Override
    protected boolean isAIEnabled() {
        return true;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        noClip = true;
        // EntityLivingBase applies vanilla gravity during super.onLivingUpdate().
        // Replace its accumulated vertical velocity with a bounded hover wave.
        motionY = Math.sin(ticksExisted * 0.12D) * 0.01D;
        fallDistance = 0.0F;
        extinguish();
        int[] lantern = ClassicSoulLantern.nearest(worldObj, posX, posY, posZ, ClassicSoulLantern.WARD_RADIUS);
        boolean paused = hasCustomNameTag() || lantern != null;
        if (!worldObj.isRemote && lantern != null && ticksExisted % 20 == 0) {
            EntityPlayer player = worldObj.getClosestPlayerToEntity(this, ClassicSoulLantern.WARD_RADIUS);
            if (player != null) player.addStat(ClassicAchievements.stabilizeGhost, 1);
        }
        if (!worldObj.isRemote && !paused && ++lifetime > MAX_LIFETIME) {
            setDead();
            return;
        }
        if (lantern != null) {
            double dx = lantern[0] + .5D - posX, dy = lantern[1] + .7D - posY, dz = lantern[2] + .5D - posZ;
            double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);
            if (distance > 1D) { motionX += dx / distance * .015D; motionY += dy / distance * .01D; motionZ += dz / distance * .015D; }
        }
        if (rand.nextFloat() < 0.04F) {
            motionX += (rand.nextDouble() - 0.5D) * 0.08D;
            motionZ += (rand.nextDouble() - 0.5D) * 0.08D;
        }
        motionX *= 0.92D;
        motionZ *= 0.92D;
    }

    @Override
    public void fall(float distance) {
        // Ghosts have no gravity or fall damage.
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (source == DamageSource.outOfWorld) {
            return super.attackEntityFrom(source, amount);
        }
        Entity attacker = source.getEntity();
        if (attacker instanceof EntityPlayer) {
            ItemStack held = ((EntityPlayer) attacker).getHeldItem();
            if (ClassicInfusion.isInfused(held)) {
                String id = held.getItem().getUnlocalizedName();
                if (!(id.endsWith("_sword") || id.endsWith("_axe"))) {
                    amount = 1.0F;
                }
                return super.attackEntityFrom(source, amount);
            }
        }
        return false;
    }

    @Override
    protected void dropFewItems(boolean recentlyHit, int looting) {
        net.minecraft.item.Item ectoplasm = ClassicGeneratedCatalog.ITEMS.get("ectoplasm");
        int count = isChild() ? rand.nextInt(2) : 1 + rand.nextInt(3);
        if (ectoplasm != null && count > 0) {
            entityDropItem(new ItemStack(ectoplasm, count), 0.0F);
        }
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack != null && stack.getItem() == ClassicGeneratedCatalog.ITEMS.get("ectoplasm");
    }

    @Override
    public EntityAgeable createChild(EntityAgeable mate) {
        return new EntityGhost(worldObj);
    }

    @Override
    public boolean getCanSpawnHere() {
        if (!ClassicGeneratedConfig.booleanValue("ghostEnabled")) return false;
        if (rand.nextDouble() > ClassicGeneratedConfig.doubleValue("ghostSpawnChance")) return false;
        int light = worldObj.getBlockLightValue((int) posX, (int) posY, (int) posZ);
        return light <= 3 || rand.nextInt(3) == 0;
    }

    @Override
    protected String getLivingSound() { return "usefultoolsmod:entity.ghost.ambient"; }

    public void pauseLifetime() { if (!worldObj.isRemote && lifetime > 0) lifetime--; }

    @Override
    protected String getHurtSound() { return "usefultoolsmod:entity.ghost.hurt"; }

    @Override
    protected String getDeathSound() { return "usefultoolsmod:entity.ghost.death"; }

    @Override
    public void writeEntityToNBT(NBTTagCompound tag) {
        super.writeEntityToNBT(tag);
        tag.setInteger("UsefulToolsLifetime", lifetime);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound tag) {
        super.readEntityFromNBT(tag);
        lifetime = tag.getInteger("UsefulToolsLifetime");
    }
}
