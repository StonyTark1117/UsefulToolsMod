package com.stonytark.usefultoolsmod.block.custom;

import com.mojang.serialization.MapCodec;
import com.stonytark.usefultoolsmod.Config;
import com.stonytark.usefultoolsmod.block.entity.MiningChargeBlockEntity;
import com.stonytark.usefultoolsmod.block.entity.ModBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.FlintAndSteelItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class MiningChargeBlock extends BaseEntityBlock {
    public static final EnumProperty<Direction> FACING=BlockStateProperties.FACING;
    public static final BooleanProperty LIT=BlockStateProperties.LIT;
    public static final MapCodec<MiningChargeBlock> CODEC=simpleCodec(MiningChargeBlock::new);
    public MiningChargeBlock(Properties properties){super(properties);registerDefaultState(stateDefinition.any().setValue(FACING,Direction.DOWN).setValue(LIT,false));}
    @Override protected MapCodec<? extends BaseEntityBlock> codec(){return CODEC;}
    @Override protected void createBlockStateDefinition(StateDefinition.Builder<Block,BlockState> builder){builder.add(FACING,LIT);}
    @Override public BlockState getStateForPlacement(BlockPlaceContext context){return defaultBlockState().setValue(FACING,context.getClickedFace().getOpposite());}
    @Override protected RenderShape getRenderShape(BlockState state){return RenderShape.MODEL;}
    @Nullable @Override public BlockEntity newBlockEntity(BlockPos pos,BlockState state){return new MiningChargeBlockEntity(pos,state);}
    @Nullable @Override public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level,BlockState state,BlockEntityType<T> type){
        return level.isClientSide()?null:createTickerHelper(type,ModBlockEntityTypes.MINING_CHARGE.get(),MiningChargeBlockEntity::tick);}
    @Override protected InteractionResult useItemOn(ItemStack stack,BlockState state,Level level,BlockPos pos,Player player,
                                                    InteractionHand hand,BlockHitResult hit){
        if(!Config.miningChargeEnabled)return InteractionResult.FAIL;
        if(stack.getItem() instanceof FlintAndSteelItem){if(!level.isClientSide()&&player instanceof ServerPlayer server
                &&level.getBlockEntity(pos)instanceof MiningChargeBlockEntity charge){charge.prime(server);
            stack.hurtAndBreak(1,player,hand==InteractionHand.MAIN_HAND?EquipmentSlot.MAINHAND:EquipmentSlot.OFFHAND);}return InteractionResult.SUCCESS;}
        return InteractionResult.PASS;}
}
