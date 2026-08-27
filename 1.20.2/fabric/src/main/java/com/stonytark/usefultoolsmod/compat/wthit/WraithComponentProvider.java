package com.stonytark.usefultoolsmod.compat.wthit;

import com.stonytark.usefultoolsmod.block.custom.SoulLanternBlock;
import com.stonytark.usefultoolsmod.entity.custom.WraithEntity;
import com.stonytark.usefultoolsmod.item.custom.EctoplasmInfusionHelper;
import mcp.mobius.waila.api.IEntityAccessor;
import mcp.mobius.waila.api.IEntityComponentProvider;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.ITooltip;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class WraithComponentProvider implements IEntityComponentProvider {
    @Override
    public void appendBody(ITooltip tooltip, IEntityAccessor accessor, IPluginConfig config) {
        if (!(accessor.getEntity() instanceof WraithEntity wraith)) return;
        PlayerEntity player = accessor.getPlayer();

        tooltip.addLine(Text.translatable("wthit.usefultoolsmod.wraith.identity")
                .formatted(Formatting.DARK_PURPLE));
        if (SoulLanternBlock.isStabilized(wraith)) {
            tooltip.addLine(Text.translatable("wthit.usefultoolsmod.wraith.warded")
                    .formatted(Formatting.GREEN));
        } else {
            tooltip.addLine(Text.translatable("wthit.usefultoolsmod.wraith.hunting")
                    .formatted(Formatting.RED));
        }

        ItemStack weapon = player.getMainHandStack();
        if (EctoplasmInfusionHelper.isInfused(weapon)) {
            tooltip.addLine(Text.translatable("wthit.usefultoolsmod.wraith.weapon_effective")
                    .formatted(Formatting.GREEN));
        } else {
            tooltip.addLine(Text.translatable("wthit.usefultoolsmod.wraith.weapon_immune")
                    .formatted(Formatting.RED));
            tooltip.addLine(Text.translatable("wthit.usefultoolsmod.wraith.weapon_required")
                    .formatted(Formatting.GRAY));
        }
    }
}
