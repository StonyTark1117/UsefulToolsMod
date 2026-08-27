package com.stonytark.usefultoolsmod.block.entity;

import com.stonytark.usefultoolsmod.Config;
import com.stonytark.usefultoolsmod.block.custom.MiningChargeBlock;
import com.stonytark.usefultoolsmod.util.ControlledExplosion;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

import java.util.UUID;

public class MiningChargeBlockEntity extends BlockEntity {
    private int fuse=-1,channel;private UUID owner;
    public MiningChargeBlockEntity(BlockPos pos,BlockState state){super(ModBlockEntityTypes.MINING_CHARGE.get(),pos,state);}
    public void prime(ServerPlayer player){primeOwner(player==null?null:player.getUUID());}
    public void primeOwner(UUID playerId){if(fuse>=0)return;if(playerId!=null)owner=playerId;fuse=80;
        if(level!=null)level.setBlock(worldPosition,getBlockState().setValue(MiningChargeBlock.LIT,true),3);setChanged();}
    public boolean link(ServerPlayer player,int selected){return linkOwner(player.getUUID(),selected);}
    public boolean linkOwner(UUID playerId,int selected){if(owner!=null&&!owner.equals(playerId))return false;
        owner=playerId;channel=selected;setChanged();return true;}
    public boolean detonate(ServerPlayer player,int selected){return detonateOwner(player.getUUID(),selected);}
    public boolean detonateOwner(UUID playerId,int selected){if(owner==null||!owner.equals(playerId)||channel!=selected)return false;
        fuse=1;setChanged();return true;}
    public UUID owner(){return owner;}
    public int channel(){return channel;}
    public static void tick(Level level,BlockPos pos,BlockState state,MiningChargeBlockEntity charge){
        if(charge.fuse<0){if(Config.miningChargeEnabled&&level.hasNeighborSignal(pos))charge.prime(null);return;}
        if(--charge.fuse>0){charge.setChanged();return;}ServerLevel server=(ServerLevel)level;
        ServerPlayer owner=charge.owner==null?null:server.getServer().getPlayerList().getPlayer(charge.owner);
        level.setBlock(pos,Blocks.AIR.defaultBlockState(),3);ControlledExplosion.excavate(server,pos,state.getValue(MiningChargeBlock.FACING),owner);
        if(owner!=null)com.stonytark.usefultoolsmod.util.ModAdvancements.award(owner,"explosives/precision_excavation");}
    @Override protected void saveAdditional(ValueOutput out){super.saveAdditional(out);out.putInt("Fuse",fuse);out.putInt("Channel",channel);
        if(owner!=null)out.putString("Owner",owner.toString());}
    @Override protected void loadAdditional(ValueInput in){super.loadAdditional(in);fuse=in.getIntOr("Fuse",-1);channel=in.getIntOr("Channel",0);
        String value=in.getStringOr("Owner","");owner=value.isEmpty()?null:UUID.fromString(value);}
}
