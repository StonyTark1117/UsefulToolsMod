package com.stonytark.usefultoolsmod.item.custom;

import com.stonytark.usefultoolsmod.Config;
import com.stonytark.usefultoolsmod.block.entity.MiningChargeBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class RemoteDetonatorItem extends Item {
    private static final String CHANNEL = "Channel";
    private static final String LINKS = "Links";
    public RemoteDetonatorItem(Properties properties) { super(properties); }

    @Override public InteractionResult useOn(UseOnContext context) {
        if (!Config.explosivesEnabled || !Config.remoteDetonationEnabled) return InteractionResult.FAIL;
        if (!(context.getPlayer() instanceof ServerPlayer player)) return InteractionResult.sidedSuccess(context.getLevel().isClientSide);
        if (!(context.getLevel().getBlockEntity(context.getClickedPos()) instanceof MiningChargeBlockEntity charge)) return InteractionResult.PASS;
        ItemStack stack = context.getItemInHand();
        int channel = channel(stack);
        if (!charge.link(player, channel)) {
            player.displayClientMessage(Component.translatable("message.usefultoolsmod.detonator.unauthorized"), true);
            return InteractionResult.FAIL;
        }
        ListTag links = stack.getOrCreateTag().getList(LINKS, Tag.TAG_COMPOUND);
        String dimension = player.level().dimension().location().toString();
        BlockPos pos = context.getClickedPos();
        boolean exists = links.stream().map(t -> (CompoundTag)t).anyMatch(t -> t.getLong("Pos") == pos.asLong()
                && t.getString("Dimension").equals(dimension));
        if (!exists && links.size() < 16) {
            CompoundTag link = new CompoundTag(); link.putLong("Pos", pos.asLong()); link.putString("Dimension", dimension);
            links.add(link); stack.getOrCreateTag().put(LINKS, links);
        }
        player.displayClientMessage(Component.translatable("message.usefultoolsmod.detonator.linked", channel + 1), true);
        return InteractionResult.SUCCESS;
    }

    @Override public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!Config.explosivesEnabled || !Config.remoteDetonationEnabled) return InteractionResultHolder.fail(stack);
        if (player.isShiftKeyDown()) {
            int next = (channel(stack) + 1) % 8; stack.getOrCreateTag().putInt(CHANNEL, next);
            if (!level.isClientSide) player.displayClientMessage(Component.translatable("message.usefultoolsmod.detonator.channel", next + 1), true);
            return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
        }
        if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) detonate(serverPlayer, stack);
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }

    private static int channel(ItemStack stack) { return Math.floorMod(stack.getOrCreateTag().getInt(CHANNEL), 8); }

    private static void detonate(ServerPlayer player, ItemStack stack) {
        ListTag oldLinks = stack.getOrCreateTag().getList(LINKS, Tag.TAG_COMPOUND);
        ListTag retained = new ListTag(); int fired = 0; int channel = channel(stack);
        String currentDimension = player.level().dimension().location().toString();
        for (Tag raw : oldLinks) {
            CompoundTag link = (CompoundTag)raw;
            if (!currentDimension.equals(link.getString("Dimension"))) continue;
            BlockPos pos = BlockPos.of(link.getLong("Pos"));
            if (!player.serverLevel().getChunkSource().hasChunk(pos.getX() >> 4, pos.getZ() >> 4)
                    || player.distanceToSqr(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D)
                    > (double)Config.remoteRange * Config.remoteRange) continue;
            if (player.level().getBlockEntity(pos) instanceof MiningChargeBlockEntity charge) {
                if (charge.owner() == null || !player.getUUID().equals(charge.owner())) continue;
                if (charge.channel() != channel || fired >= 16) retained.add(link.copy());
                else if (charge.detonate(player, channel)) fired++;
            }
        }
        stack.getOrCreateTag().put(LINKS, retained);
        if (fired > 0) com.stonytark.usefultoolsmod.util.ModAdvancements.award(player, "explosives/remote_fire");
        player.displayClientMessage(Component.translatable("message.usefultoolsmod.detonator.fired", fired, channel + 1), true);
    }
}
