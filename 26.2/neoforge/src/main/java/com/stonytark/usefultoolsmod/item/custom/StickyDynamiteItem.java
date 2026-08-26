package com.stonytark.usefultoolsmod.item.custom;
import com.stonytark.usefultoolsmod.Config;
import com.stonytark.usefultoolsmod.entity.ModEntities;
import com.stonytark.usefultoolsmod.entity.custom.StickyDynamiteEntity;
import net.minecraft.world.InteractionHand;import net.minecraft.world.InteractionResult;import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;import net.minecraft.world.item.ItemStack;import net.minecraft.world.level.Level;
public class StickyDynamiteItem extends Item{
    public StickyDynamiteItem(Properties properties){super(properties);}
    @Override public InteractionResult use(Level level,Player player,InteractionHand hand){ItemStack stack=player.getItemInHand(hand);
        if(!Config.explosivesEnabled||!Config.stickyDynamiteEnabled)return InteractionResult.FAIL;
        if(!level.isClientSide()){StickyDynamiteEntity entity=new StickyDynamiteEntity(ModEntities.STICKY_DYNAMITE.get(),level,player);
            entity.shootFromRotation(player,player.getXRot(),player.getYRot(),0,1.1F,.5F);level.addFreshEntity(entity);}
        if(!player.getAbilities().instabuild)stack.shrink(1);return InteractionResult.SUCCESS;}}
