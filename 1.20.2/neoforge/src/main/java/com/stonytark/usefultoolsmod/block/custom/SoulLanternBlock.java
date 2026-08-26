package com.stonytark.usefultoolsmod.block.custom;

import com.stonytark.usefultoolsmod.entity.custom.WraithEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

/** A spectral safe point: ghosts persist near it and wraiths are pushed away. */
@SuppressWarnings("deprecation")
public class SoulLanternBlock extends Block {
    public static final int INFLUENCE_RADIUS = 12;

    public SoulLanternBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void onPlace(BlockState state, net.minecraft.world.level.Level level, BlockPos pos,
                        BlockState oldState, boolean moved) {
        super.onPlace(state, level, pos, oldState, moved);
        if (!level.isClientSide) level.scheduleTick(pos, this, 10);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!com.stonytark.usefultoolsmod.Config.soulLanternEnabled) {
            level.scheduleTick(pos, this, 10);
            return;
        }
        if (level.hasNeighborSignal(pos)) {
            level.scheduleTick(pos, this, 10);
            return;
        }
        Vec3 center = Vec3.atCenterOf(pos);
        for (WraithEntity wraith : level.getEntitiesOfClass(WraithEntity.class,
                new AABB(pos).inflate(INFLUENCE_RADIUS))) {
            Vec3 away = wraith.position().subtract(center);
            if (away.lengthSqr() < 0.01D) away = new Vec3(0, 0.2D, 0);
            wraith.setDeltaMovement(wraith.getDeltaMovement().scale(0.5D)
                    .add(away.normalize().scale(0.18D)).add(0, 0.04D, 0));
            wraith.setTarget(null);
        }
        level.scheduleTick(pos, this, 10);
    }

    public static boolean isStabilized(Entity entity) {
        if (!com.stonytark.usefultoolsmod.Config.soulLanternEnabled) return false;
        BlockPos origin = entity.blockPosition();
        int r = INFLUENCE_RADIUS;
        for (BlockPos pos : BlockPos.betweenClosed(origin.offset(-r, -r, -r), origin.offset(r, r, r))) {
            if (entity.level().getBlockState(pos).getBlock() instanceof SoulLanternBlock
                    && !entity.level().hasNeighborSignal(pos)) return true;
        }
        return false;
    }
}
