/*
 * Decompiled with CFR 0.152.
 *
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.projectile.EntityThrowable
 *  net.minecraft.util.MovingObjectPosition
 *  net.minecraft.world.World
 */
package com.krazykid1117.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityGrenade
extends EntityThrowable {
    private float explosionRadius = 5.0f;

    public EntityGrenade(World p_i1776_1_) {
        super(p_i1776_1_);
    }

    public EntityGrenade(World world, EntityLivingBase entity) {
        super(world, entity);
    }

    protected void onImpact(MovingObjectPosition p_70184_1_) {
        for (int i = 0; i < 10; ++i) {
            this.worldObj.spawnParticle("largesmoke", this.posX, this.posY, this.posZ, 0.0, 0.0, 0.0);
        }
        if (!this.worldObj.isRemote) {
            this.setDead();
            if (!this.worldObj.isRemote) {
                this.worldObj.createExplosion((Entity)null, this.posX, this.posY, this.posZ, 5.0f, true);
                this.setDead();
            }
        }
    }
}
