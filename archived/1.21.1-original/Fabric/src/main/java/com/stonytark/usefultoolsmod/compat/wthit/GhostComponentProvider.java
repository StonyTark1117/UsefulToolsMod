package com.stonytark.usefultoolsmod.compat.wthit;

import com.stonytark.usefultoolsmod.entity.custom.GhostEntity;
import com.stonytark.usefultoolsmod.item.custom.EctoplasmArmorHelper;
import com.stonytark.usefultoolsmod.item.custom.EctoplasmInfusionHelper;
import mcp.mobius.waila.api.IEntityAccessor;
import mcp.mobius.waila.api.IEntityComponentProvider;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.ITooltip;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class GhostComponentProvider implements IEntityComponentProvider {

    @Override
    public void appendBody(ITooltip tooltip, IEntityAccessor accessor, IPluginConfig config) {
        if (!(accessor.getEntity() instanceof GhostEntity ghost)) return;
        PlayerEntity player = accessor.getPlayer();

        if (ghost.isBaby()) {
            tooltip.addLine(Text.literal("Baby Ghost").formatted(Formatting.GRAY));
        }

        boolean ghostInvisible = EctoplasmArmorHelper.isGhostInvisible(player);
        if (ghostInvisible) {
            tooltip.addLine(Text.literal("Cannot see you").formatted(Formatting.GREEN));
        } else {
            tooltip.addLine(Text.literal("Can see you").formatted(Formatting.YELLOW));
        }

        ItemStack mainHand = player.getMainHandStack();
        boolean canHurt = EctoplasmInfusionHelper.isInfused(mainHand);
        if (canHurt) {
            tooltip.addLine(Text.literal("Your weapon can damage this ghost").formatted(Formatting.GREEN));
        } else {
            tooltip.addLine(Text.literal("Your weapon cannot damage this ghost").formatted(Formatting.RED));
            tooltip.addLine(Text.literal("Requires ectoplasm-infused weapon").formatted(Formatting.GRAY));
        }
    }
}
