package com.stonytark.usefultoolsmod.compat.wthit;

import com.stonytark.usefultoolsmod.block.custom.SoulLanternWard;
import com.stonytark.usefultoolsmod.entity.custom.WraithEntity;
import com.stonytark.usefultoolsmod.item.custom.EctoplasmInfusionHelper;
import mcp.mobius.waila.api.IEntityAccessor;
import mcp.mobius.waila.api.IEntityComponentProvider;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.ITooltip;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class WraithComponentProvider implements IEntityComponentProvider {
    @Override
    public void appendBody(ITooltip tooltip, IEntityAccessor accessor, IPluginConfig config) {
        if (!(accessor.getEntity() instanceof WraithEntity wraith)) return;
        Player player = accessor.getPlayer();

        tooltip.addLine(Component.translatable("wthit.usefultoolsmod.wraith.identity")
                .withStyle(ChatFormatting.DARK_PURPLE));
        if (SoulLanternWard.activeWithin(wraith, SoulLanternWard.RADIUS)) {
            tooltip.addLine(Component.translatable("wthit.usefultoolsmod.wraith.warded")
                    .withStyle(ChatFormatting.GREEN));
        } else {
            tooltip.addLine(Component.translatable("wthit.usefultoolsmod.wraith.hunting")
                    .withStyle(ChatFormatting.RED));
        }

        ItemStack weapon = player.getMainHandItem();
        if (EctoplasmInfusionHelper.isInfused(weapon)) {
            tooltip.addLine(Component.translatable("wthit.usefultoolsmod.wraith.weapon_effective")
                    .withStyle(ChatFormatting.GREEN));
        } else {
            tooltip.addLine(Component.translatable("wthit.usefultoolsmod.wraith.weapon_immune")
                    .withStyle(ChatFormatting.RED));
            tooltip.addLine(Component.translatable("wthit.usefultoolsmod.wraith.weapon_required")
                    .withStyle(ChatFormatting.GRAY));
        }
    }
}
