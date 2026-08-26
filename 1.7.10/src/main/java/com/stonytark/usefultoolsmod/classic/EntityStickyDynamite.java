package com.stonytark.usefultoolsmod.classic;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

public class EntityStickyDynamite extends EntityThrowable {
    private int fuse = 80;
    private boolean stuck;
    private int attachedEntity = -1;
    private String attachedUuid = "";

    public EntityStickyDynamite(World world) { super(world); }
    public EntityStickyDynamite(World world, EntityLivingBase thrower) { super(world, thrower); }

    @Override protected float getGravityVelocity() { return stuck ? 0F : .03F; }

    @Override protected void onImpact(MovingObjectPosition hit) {
        if (worldObj.isRemote || stuck) return;
        stuck = true; motionX = motionY = motionZ = 0;
        if (hit.entityHit != null) {
            attachedEntity = hit.entityHit.getEntityId();
            attachedUuid = hit.entityHit.getPersistentID().toString();
        }
        else setPosition(hit.blockX + .5 + Facing.offsetsXForSide[hit.sideHit] * .51,
                hit.blockY + .5 + Facing.offsetsYForSide[hit.sideHit] * .51,
                hit.blockZ + .5 + Facing.offsetsZForSide[hit.sideHit] * .51);
    }

    @Override public void onUpdate() {
        super.onUpdate();
        if (stuck) {
            net.minecraft.entity.Entity attached = null;
            for (Object value : worldObj.loadedEntityList) {
                net.minecraft.entity.Entity candidate = (net.minecraft.entity.Entity)value;
                if (attachedUuid.length() > 0 && attachedUuid.equals(candidate.getPersistentID().toString())) {
                    attached = candidate;
                    attachedEntity = candidate.getEntityId();
                    break;
                }
            }
            if (attached == null && attachedEntity >= 0) attached = worldObj.getEntityByID(attachedEntity);
            if (attached != null) setPosition(attached.posX, attached.posY + attached.height * .5, attached.posZ);
            motionX = motionY = motionZ = 0;
        }
        if (!worldObj.isRemote && --fuse <= 0) {
            setDead(); ClassicControlledExplosion.stickyBurst(worldObj, posX, posY, posZ);
        } else if (fuse % 20 == 0) worldObj.playSoundAtEntity(this, "random.fuse", 1F, 1F);
    }

    @Override public void writeEntityToNBT(NBTTagCompound tag) {
        super.writeEntityToNBT(tag); tag.setInteger("Fuse", fuse); tag.setBoolean("Stuck", stuck);
        tag.setInteger("Attached", attachedEntity); tag.setString("AttachedUuid", attachedUuid);
    }
    @Override public void readEntityFromNBT(NBTTagCompound tag) {
        super.readEntityFromNBT(tag); fuse = tag.getInteger("Fuse"); stuck = tag.getBoolean("Stuck");
        attachedEntity = tag.getInteger("Attached"); attachedUuid = tag.getString("AttachedUuid");
    }
}
