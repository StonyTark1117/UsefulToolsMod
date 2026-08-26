package com.stonytark.usefultoolsmod.block.custom;

import com.stonytark.usefultoolsmod.Config;
import com.stonytark.usefultoolsmod.block.entity.MiningChargeBlockEntity;
import com.stonytark.usefultoolsmod.block.entity.ModBlockEntityTypes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FlintAndSteelItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class MiningChargeBlock extends BlockWithEntity {
    public static final DirectionProperty FACING = Properties.FACING;
    public static final BooleanProperty LIT = Properties.LIT;
    public MiningChargeBlock(Settings settings) {
        super(settings); setDefaultState(getStateManager().getDefaultState().with(FACING, Direction.DOWN).with(LIT, false));
    }
    @Override protected void appendProperties(StateManager.Builder<Block, BlockState> builder) { builder.add(FACING, LIT); }
    @Override public BlockState getPlacementState(ItemPlacementContext context) {
        return getDefaultState().with(FACING, context.getSide().getOpposite());
    }
    @Override public BlockRenderType getRenderType(BlockState state) { return BlockRenderType.MODEL; }
    @Nullable @Override public BlockEntity createBlockEntity(BlockPos pos, BlockState state) { return new MiningChargeBlockEntity(pos, state); }
    @Nullable @Override public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient ? null : validateTicker(type, ModBlockEntityTypes.MINING_CHARGE, MiningChargeBlockEntity::tick);
    }
    @Override public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player,
                                        Hand hand, BlockHitResult hit) {
        if (!Config.miningChargeEnabled) return ActionResult.FAIL;
        if (player.getStackInHand(hand).getItem() instanceof FlintAndSteelItem) {
            if (!world.isClient && player instanceof ServerPlayerEntity serverPlayer
                    && world.getBlockEntity(pos) instanceof MiningChargeBlockEntity charge) {
                charge.prime(serverPlayer); player.getStackInHand(hand).damage(1, player, p -> p.sendToolBreakStatus(hand));
            }
            return ActionResult.success(world.isClient);
        }
        return ActionResult.PASS;
    }
    @Override public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock,
                                         BlockPos sourcePos, boolean notify) {
        if (!world.isClient && Config.miningChargeEnabled && world.isReceivingRedstonePower(pos)
                && world.getBlockEntity(pos) instanceof MiningChargeBlockEntity charge) charge.prime(null);
    }
}
