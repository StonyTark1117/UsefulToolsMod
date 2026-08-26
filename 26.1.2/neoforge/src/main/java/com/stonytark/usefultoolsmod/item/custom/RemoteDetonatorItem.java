package com.stonytark.usefultoolsmod.item.custom;

import com.stonytark.usefultoolsmod.Config;
import com.stonytark.usefultoolsmod.block.entity.MiningChargeBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class RemoteDetonatorItem extends Item {
    private static final int MAX_LINKS=16;
    public RemoteDetonatorItem(Properties properties){super(properties);}
    @Override public InteractionResult useOn(UseOnContext context){
        if(!Config.explosivesEnabled||!Config.remoteDetonationEnabled)return InteractionResult.FAIL;
        if(!(context.getPlayer()instanceof ServerPlayer player))return InteractionResult.SUCCESS;
        if(!(context.getLevel().getBlockEntity(context.getClickedPos())instanceof MiningChargeBlockEntity charge))return InteractionResult.PASS;
        ItemStack stack=context.getItemInHand();CompoundTag tag=tag(stack);int channel=Math.floorMod(tag.getIntOr("Channel",0),8);
        if(!charge.link(player,channel)){action(player,Component.translatable("message.usefultoolsmod.detonator.unauthorized"));return InteractionResult.FAIL;}
        String dim=player.level().dimension().identifier().toString();BlockPos pos=context.getClickedPos();int count=tag.getIntOr("LinkCount",0);boolean exists=false;
        for(int i=0;i<count;i++)if(tag.getLongOr("Link"+i+"Pos",Long.MIN_VALUE)==pos.asLong()&&tag.getStringOr("Link"+i+"Dim","").equals(dim))exists=true;
        if(!exists&&count<MAX_LINKS){tag.putLong("Link"+count+"Pos",pos.asLong());tag.putString("Link"+count+"Dim",dim);tag.putInt("LinkCount",count+1);save(stack,tag);}
        action(player,Component.translatable("message.usefultoolsmod.detonator.linked",channel+1));return InteractionResult.SUCCESS;}
    @Override public InteractionResult use(Level level,Player player,InteractionHand hand){ItemStack stack=player.getItemInHand(hand);
        if(!Config.explosivesEnabled||!Config.remoteDetonationEnabled)return InteractionResult.FAIL;CompoundTag tag=tag(stack);
        if(player.isShiftKeyDown()){int next=(tag.getIntOr("Channel",0)+1)%8;tag.putInt("Channel",next);save(stack,tag);
            if(player instanceof ServerPlayer server)action(server,Component.translatable("message.usefultoolsmod.detonator.channel",next+1));return InteractionResult.SUCCESS;}
        if(player instanceof ServerPlayer server)detonate(server,stack,tag);return InteractionResult.SUCCESS;}
    private static void detonate(ServerPlayer player,ItemStack stack,CompoundTag old){CompoundTag next=new CompoundTag();int channel=Math.floorMod(old.getIntOr("Channel",0),8);
        next.putInt("Channel",channel);int retained=0,fired=0,count=old.getIntOr("LinkCount",0);String dim=player.level().dimension().identifier().toString();
        for(int i=0;i<count;i++){String linkDim=old.getStringOr("Link"+i+"Dim","");
            BlockPos pos=BlockPos.of(old.getLongOr("Link"+i+"Pos",Long.MIN_VALUE));
            boolean available=dim.equals(linkDim)&&((net.minecraft.server.level.ServerLevel)player.level()).getChunkSource().hasChunk(pos.getX()>>4,pos.getZ()>>4);
            boolean inRange=player.distanceToSqr(pos.getX()+.5,pos.getY()+.5,pos.getZ()+.5)<=Config.remoteRange*Config.remoteRange;
            if(!available||!inRange)continue;
            if(!(player.level().getBlockEntity(pos)instanceof MiningChargeBlockEntity charge))continue;
            if(charge.owner()==null||!player.getUUID().equals(charge.owner()))continue;
            if(charge.channel()==channel&&fired<MAX_LINKS&&charge.detonate(player,channel)){fired++;continue;}
            next.putLong("Link"+retained+"Pos",pos.asLong());next.putString("Link"+retained+"Dim",linkDim);retained++;}
        next.putInt("LinkCount",retained);save(stack,next);
        if(fired>0)com.stonytark.usefultoolsmod.util.ModAdvancements.award(player,"explosives/remote_fire");
        action(player,Component.translatable("message.usefultoolsmod.detonator.fired",fired,channel+1));}
    private static CompoundTag tag(ItemStack stack){return stack.getOrDefault(DataComponents.CUSTOM_DATA,CustomData.EMPTY).copyTag();}
    private static void save(ItemStack stack,CompoundTag tag){stack.set(DataComponents.CUSTOM_DATA,CustomData.of(tag));}
    private static void action(ServerPlayer player,Component text){player.connection.send(new ClientboundSetActionBarTextPacket(text));}
}
