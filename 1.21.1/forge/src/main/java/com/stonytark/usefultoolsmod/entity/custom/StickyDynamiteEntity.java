package com.stonytark.usefultoolsmod.entity.custom;

import com.stonytark.usefultoolsmod.Config;
import com.stonytark.usefultoolsmod.item.ModItems;
import com.stonytark.usefultoolsmod.util.ControlledExplosion;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.UUID;

public class StickyDynamiteEntity extends ThrowableItemProjectile {
    private int fuse = 80;
    private boolean stuck;
    private BlockPos stuckPos;
    private UUID stuckEntity;

    public StickyDynamiteEntity(EntityType<? extends StickyDynamiteEntity> type, Level level) { super(type, level); }
    public StickyDynamiteEntity(EntityType<? extends StickyDynamiteEntity> type, Level level, LivingEntity owner) {
        super(type, owner, level);
    }

    @Override protected void onHit(HitResult result) {
        if (stuck) return;
        if (result instanceof EntityHitResult entityHit) stuckEntity = entityHit.getEntity().getUUID();
        else if (result instanceof BlockHitResult blockHit) stuckPos = blockHit.getBlockPos();
        stuck = true;
        setNoGravity(true);
        setDeltaMovement(Vec3.ZERO);
    }

    @Override public void tick() {
        if (!stuck) super.tick();
        else {
            if (stuckEntity != null && level() instanceof ServerLevel server) {
                Entity attached = server.getEntity(stuckEntity);
                if (attached != null) setPos(attached.getX(), attached.getY() + attached.getBbHeight() * 0.5D, attached.getZ());
            } else if (stuckPos != null) setPos(Vec3.atCenterOf(stuckPos));
        }
        if (level().isClientSide) {
            level().addParticle(ParticleTypes.SMOKE, getX(), getY() + 0.1D, getZ(), 0, 0.01D, 0);
        } else if (--fuse <= 0) {
            ControlledExplosion.burst((ServerLevel)level(), position(), 4.0D, getOwner());
            discard();
        }
    }

    @Override protected Item getDefaultItem() { return ModItems.STICKY_DYNAMITE.get(); }
    @Override public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag); tag.putInt("Fuse", fuse); tag.putBoolean("Stuck", stuck);
        if (stuckPos != null) tag.putLong("StuckPos", stuckPos.asLong());
        if (stuckEntity != null) tag.putUUID("StuckEntity", stuckEntity);
    }
    @Override public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag); fuse = tag.getInt("Fuse"); stuck = tag.getBoolean("Stuck");
        stuckPos = tag.contains("StuckPos") ? BlockPos.of(tag.getLong("StuckPos")) : null;
        stuckEntity = tag.hasUUID("StuckEntity") ? tag.getUUID("StuckEntity") : null;
    }
}
