package com.stonytark.usefultoolsmod.block.custom;

import com.stonytark.usefultoolsmod.entity.custom.WraithEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

@SuppressWarnings("deprecation")
public class SoulLanternBlock extends Block {
    public static final int INFLUENCE_RADIUS = 12;
    public SoulLanternBlock(Settings settings) { super(settings); }

    @Override public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        super.onBlockAdded(state, world, pos, oldState, notify);
        if (!world.isClient) world.scheduleBlockTick(pos, this, 10);
    }

    @Override public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!com.stonytark.usefultoolsmod.Config.soulLanternEnabled) {
            world.scheduleBlockTick(pos, this, 10);
            return;
        }
        if (!world.isReceivingRedstonePower(pos)) {
            Vec3d center = Vec3d.ofCenter(pos);
            for (WraithEntity wraith : world.getEntitiesByClass(WraithEntity.class,
                    new Box(pos).expand(INFLUENCE_RADIUS), entity -> true)) {
                Vec3d away = wraith.getPos().subtract(center);
                if (away.lengthSquared() < 0.01D) away = new Vec3d(0, 0.2D, 0);
                wraith.setVelocity(wraith.getVelocity().multiply(0.5D).add(away.normalize().multiply(0.18D)).add(0, 0.04D, 0));
                wraith.setTarget(null);
            }
        }
        world.scheduleBlockTick(pos, this, 10);
    }

    public static boolean isStabilized(Entity entity) {
        if (!com.stonytark.usefultoolsmod.Config.soulLanternEnabled) return false;
        BlockPos origin = entity.getBlockPos(); int r = INFLUENCE_RADIUS;
        for (BlockPos pos : BlockPos.iterate(origin.add(-r, -r, -r), origin.add(r, r, r))) {
            if (entity.getWorld().getBlockState(pos).getBlock() instanceof SoulLanternBlock
                    && !entity.getWorld().isReceivingRedstonePower(pos)) return true;
        }
        return false;
    }
}
