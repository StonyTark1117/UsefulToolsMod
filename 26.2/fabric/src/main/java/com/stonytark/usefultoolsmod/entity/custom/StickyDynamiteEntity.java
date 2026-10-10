package com.stonytark.usefultoolsmod.entity.custom;

import com.stonytark.usefultoolsmod.item.ModItems;
import com.stonytark.usefultoolsmod.util.ControlledExplosion;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import java.util.UUID;

public class StickyDynamiteEntity extends ThrowableItemProjectile {
    private static final net.minecraft.network.syncher.EntityDataAccessor<Integer> MODEL_FACE = net.minecraft.network.syncher.SynchedEntityData.defineId(StickyDynamiteEntity.class, net.minecraft.network.syncher.EntityDataSerializers.INT);
    private static final net.minecraft.network.syncher.EntityDataAccessor<Float> MODEL_RADIUS = net.minecraft.network.syncher.SynchedEntityData.defineId(StickyDynamiteEntity.class, net.minecraft.network.syncher.EntityDataSerializers.FLOAT);

    @Override protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder); builder.define(MODEL_FACE, -1); builder.define(MODEL_RADIUS, .7F);
    }

    /** Visual displacement only; collision, attachment and explosion use the original position. */
    public Vec3 getModelOffset() {
        int face = entityData.get(MODEL_FACE);
        if (face < 0) return Vec3.ZERO;
        net.minecraft.core.Direction direction = net.minecraft.core.Direction.from3DDataValue(face);
        double radius = entityData.get(MODEL_RADIUS);
        return new Vec3(direction.getStepX() * radius, direction.getStepY() * radius,
                direction.getStepZ() * radius);
    }

    private int fuse=80;private boolean stuck;private BlockPos stuckPos;private UUID stuckEntity;
    public StickyDynamiteEntity(EntityType<? extends StickyDynamiteEntity> type,Level level){super(type,level);}
    public StickyDynamiteEntity(EntityType<? extends StickyDynamiteEntity> type,Level level,LivingEntity owner){
        super(type,owner,level,new ItemStack(ModItems.STICKY_DYNAMITE));}
    @Override protected void onHit(HitResult result){if(stuck)return;
        if (result instanceof EntityHitResult hit) {
            Entity attached = hit.getEntity();
            stuckEntity = attached.getUUID();
            double dx = getX() - attached.getX(), dz = getZ() - attached.getZ();
            // Use the impacted side of the body; server attachment coordinates stay unchanged.
            entityData.set(MODEL_FACE, Math.abs(dx) > Math.abs(dz) ? (dx > 0 ? 5 : 4) : (dz > 0 ? 3 : 2));
            entityData.set(MODEL_RADIUS, Math.max(.7F, attached.getBbWidth() * .5F + .25F));
        }else if (result instanceof BlockHitResult hit) { stuckPos = hit.getBlockPos(); entityData.set(MODEL_FACE, hit.getDirection().get3DDataValue()); }
        stuck=true;setNoGravity(true);setDeltaMovement(Vec3.ZERO);}
    @Override public void tick(){if(!stuck)super.tick();else if(stuckEntity!=null&&level()instanceof ServerLevel server){Entity attached=server.getEntity(stuckEntity);
            if(attached!=null)setPos(attached.getX(),attached.getY()+attached.getBbHeight()*.5,attached.getZ());}else if(stuckPos!=null)setPos(Vec3.atCenterOf(stuckPos));
        if(level().isClientSide())level().addParticle(ParticleTypes.SMOKE,getX(),getY()+.1,getZ(),0,.01,0);
        else if(--fuse<=0){ControlledExplosion.burst((ServerLevel)level(),position(),4,getOwner());discard();}}
    @Override protected Item getDefaultItem(){return ModItems.STICKY_DYNAMITE;}
    @Override protected void addAdditionalSaveData(ValueOutput out){super.addAdditionalSaveData(out);out.putInt("ModelFace", entityData.get(MODEL_FACE));out.putFloat("ModelRadius", entityData.get(MODEL_RADIUS));out.putInt("Fuse",fuse);out.putBoolean("Stuck",stuck);
        if(stuckPos!=null)out.putLong("StuckPos",stuckPos.asLong());if(stuckEntity!=null)out.putString("StuckEntity",stuckEntity.toString());}
    @Override protected void readAdditionalSaveData(ValueInput in){super.readAdditionalSaveData(in);entityData.set(MODEL_FACE, in.getIntOr("ModelFace", -1));entityData.set(MODEL_RADIUS, in.getFloatOr("ModelRadius", .7F));fuse=in.getIntOr("Fuse",80);stuck=in.getBooleanOr("Stuck",false);
        long pos=in.getLongOr("StuckPos",Long.MIN_VALUE);stuckPos=pos==Long.MIN_VALUE?null:BlockPos.of(pos);String entity=in.getStringOr("StuckEntity","");
        stuckEntity=entity.isEmpty()?null:UUID.fromString(entity);}
}
