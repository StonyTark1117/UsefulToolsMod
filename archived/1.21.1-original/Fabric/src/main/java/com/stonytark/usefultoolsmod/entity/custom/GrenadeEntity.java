package com.stonytark.usefultoolsmod.entity.custom;

import com.stonytark.usefultoolsmod.item.ModItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class GrenadeEntity extends ThrownItemEntity {
    private static final float EXPLOSION_RADIUS = 10.0F;

    public GrenadeEntity(EntityType<? extends GrenadeEntity> type, World world) {
        super(type, world);
    }

    public GrenadeEntity(EntityType<? extends GrenadeEntity> type, World world, LivingEntity thrower) {
        super(type, thrower, world);
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.getWorld().isClient) {
            this.getWorld().createExplosion(this, this.getX(), this.getY(), this.getZ(),
                    EXPLOSION_RADIUS, World.ExplosionSourceType.TNT);
            this.discard();
        } else {
            for (int i = 0; i < 10; i++) {
                this.getWorld().addParticle(ParticleTypes.SMOKE,
                        this.getX(), this.getY(), this.getZ(), 0.0D, 0.1D, 0.0D);
            }
        }
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.GRENADE;
    }
}
