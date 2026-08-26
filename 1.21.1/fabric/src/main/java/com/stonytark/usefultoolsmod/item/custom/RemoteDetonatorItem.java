package com.stonytark.usefultoolsmod.item.custom;

import com.stonytark.usefultoolsmod.Config;
import com.stonytark.usefultoolsmod.block.entity.MiningChargeBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RemoteDetonatorItem extends Item {
    private static final String CHANNEL = "Channel";
    private static final String LINKS = "Links";
    public RemoteDetonatorItem(Settings settings) { super(settings); }

    @Override public ActionResult useOnBlock(ItemUsageContext context) {
        if (!Config.explosivesEnabled || !Config.remoteDetonationEnabled) return ActionResult.FAIL;
        if (!(context.getPlayer() instanceof ServerPlayerEntity player)) return ActionResult.success(context.getWorld().isClient);
        if (!(context.getWorld().getBlockEntity(context.getBlockPos()) instanceof MiningChargeBlockEntity charge)) return ActionResult.PASS;
        ItemStack stack = context.getStack(); int channel = channel(stack);
        if (!charge.link(player, channel)) { player.sendMessage(Text.translatable("message.usefultoolsmod.detonator.unauthorized"), true); return ActionResult.FAIL; }
        NbtCompound data = data(stack);
        NbtList links = data.getList(LINKS, NbtElement.COMPOUND_TYPE);
        String dimension = player.getWorld().getRegistryKey().getValue().toString(); BlockPos pos = context.getBlockPos(); boolean exists = false;
        for (NbtElement raw : links) { NbtCompound link = (NbtCompound)raw;
            if (link.getLong("Pos") == pos.asLong() && link.getString("Dimension").equals(dimension)) { exists = true; break; } }
        if (!exists && links.size() < 16) { NbtCompound link = new NbtCompound(); link.putLong("Pos", pos.asLong());
            link.putString("Dimension", dimension); links.add(link); data.put(LINKS, links); setData(stack, data); }
        player.sendMessage(Text.translatable("message.usefultoolsmod.detonator.linked", channel + 1), true); return ActionResult.SUCCESS;
    }

    @Override public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        if (!Config.explosivesEnabled || !Config.remoteDetonationEnabled) return TypedActionResult.fail(stack);
        if (player.isSneaking()) { int next = (channel(stack) + 1) % 8; NbtCompound data = data(stack);
            data.putInt(CHANNEL, next); setData(stack, data);
            if (!world.isClient) player.sendMessage(Text.translatable("message.usefultoolsmod.detonator.channel", next + 1), true);
            return TypedActionResult.success(stack, world.isClient); }
        if (!world.isClient && player instanceof ServerPlayerEntity serverPlayer) detonate(serverPlayer, stack);
        return TypedActionResult.success(stack, world.isClient);
    }

    private static int channel(ItemStack stack) { return Math.floorMod(data(stack).getInt(CHANNEL), 8); }
    private static void detonate(ServerPlayerEntity player, ItemStack stack) {
        NbtCompound data = data(stack); NbtList oldLinks = data.getList(LINKS, NbtElement.COMPOUND_TYPE); NbtList retained = new NbtList();
        int fired = 0, channel = channel(stack); String dimension = player.getWorld().getRegistryKey().getValue().toString();
        for (NbtElement raw : oldLinks) { NbtCompound link = (NbtCompound)raw;
            if (!dimension.equals(link.getString("Dimension"))) continue; BlockPos pos = BlockPos.fromLong(link.getLong("Pos"));
            if (!player.getServerWorld().getChunkManager().isChunkLoaded(pos.getX() >> 4, pos.getZ() >> 4)
                    || player.squaredDistanceTo(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D)
                    > Config.remoteRange * Config.remoteRange) continue;
            if (player.getWorld().getBlockEntity(pos) instanceof MiningChargeBlockEntity charge) {
                if (charge.owner() == null || !player.getUuid().equals(charge.owner())) continue;
                if (charge.channel() != channel || fired >= 16) retained.add(link.copy());
                else if (charge.detonate(player, channel)) fired++;
            }
        }
        data.put(LINKS, retained); setData(stack, data);
        if (fired > 0) com.stonytark.usefultoolsmod.util.ModAdvancements.award(player, "explosives/remote_fire");
        player.sendMessage(Text.translatable("message.usefultoolsmod.detonator.fired", fired, channel + 1), true);
    }

    private static NbtCompound data(ItemStack stack) {
        NbtComponent component = stack.getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT);
        return component == NbtComponent.DEFAULT ? new NbtCompound() : component.copyNbt();
    }

    private static void setData(ItemStack stack, NbtCompound tag) {
        if (tag.isEmpty()) stack.remove(DataComponentTypes.CUSTOM_DATA);
        else stack.set(DataComponentTypes.CUSTOM_DATA, NbtComponent.of(tag));
    }
}
