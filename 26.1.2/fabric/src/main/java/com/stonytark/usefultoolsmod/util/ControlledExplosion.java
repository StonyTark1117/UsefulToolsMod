package com.stonytark.usefultoolsmod.util;

import com.stonytark.usefultoolsmod.Config;
import com.stonytark.usefultoolsmod.UsefultoolsMod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public final class ControlledExplosion {
    public static final TagKey<Block> IMMUNE = TagKey.create(Registries.BLOCK,
            Identifier.fromNamespaceAndPath(UsefultoolsMod.MOD_ID, "controlled_explosion_immune"));
    private ControlledExplosion() {}
    public static int excavate(ServerLevel level, BlockPos chargePos, Direction inward, Entity owner) {
        int broken = 0; Direction.Axis first = inward.getAxis() == Direction.Axis.Y ? Direction.Axis.X
                : inward.getAxis() == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X;
        Direction.Axis second = inward.getAxis() == Direction.Axis.Y ? Direction.Axis.Z : Direction.Axis.Y;
        for (int depth=1; depth<=4; depth++) { BlockPos center=chargePos.relative(inward,depth);
            for(int a=-1;a<=1;a++) for(int b=-1;b<=1;b++) if(breakSafely(level,offset(center,first,a,second,b),owner)) broken++; }
        damageEntities(level, Vec3.atCenterOf(chargePos.relative(inward,2)),4.5,owner); return broken;
    }
    public static int burst(ServerLevel level, Vec3 center, double radius, Entity owner) {
        int broken=0,r=(int)Math.ceil(radius); BlockPos origin=BlockPos.containing(center);
        for(BlockPos pos:BlockPos.betweenClosed(origin.offset(-r,-r,-r),origin.offset(r,r,r)))
            if(Vec3.atCenterOf(pos).distanceTo(center)<=radius&&breakSafely(level,pos,owner)) broken++;
        damageEntities(level,center,radius,owner); return broken;
    }
    private static BlockPos offset(BlockPos pos,Direction.Axis first,int a,Direction.Axis second,int b){
        int x=pos.getX(),y=pos.getY(),z=pos.getZ(); if(first==Direction.Axis.X)x+=a;else if(first==Direction.Axis.Y)y+=a;else z+=a;
        if(second==Direction.Axis.X)x+=b;else if(second==Direction.Axis.Y)y+=b;else z+=b;return new BlockPos(x,y,z);}
    private static boolean breakSafely(ServerLevel level,BlockPos pos,Entity owner){
        BlockState state=level.getBlockState(pos); if(state.isAir()||state.is(IMMUNE)||state.is(Blocks.BEDROCK))return false;
        if(level.getBlockEntity(pos)!=null||state.getDestroySpeed(level,pos)<0)return false;
        if(Config.controlledDrops)Block.dropResources(state,level,pos,null,owner,ItemStack.EMPTY);
        return level.setBlock(pos,Blocks.AIR.defaultBlockState(),Block.UPDATE_ALL);}
    private static void damageEntities(ServerLevel level,Vec3 center,double radius,Entity owner){
        for(LivingEntity living:level.getEntitiesOfClass(LivingEntity.class,new AABB(center,center).inflate(radius))){
            double distance=living.position().distanceTo(center);if(distance>radius)continue;
            double configuredCap=Math.min(8.0D,Config.controlledEntityDamage);
            float damage=(float)Math.min(configuredCap,configuredCap*(1-distance/radius));
            if(damage>0)living.hurtServer(level,level.damageSources().explosion(null,owner),damage);}}
}
