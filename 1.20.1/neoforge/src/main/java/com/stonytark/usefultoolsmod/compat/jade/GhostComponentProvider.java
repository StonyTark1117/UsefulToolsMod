package com.stonytark.usefultoolsmod.compat.jade;

import com.stonytark.usefultoolsmod.UsefultoolsMod;
import com.stonytark.usefultoolsmod.entity.custom.GhostEntity;
import com.stonytark.usefultoolsmod.item.custom.EctoplasmArmorHelper;
import com.stonytark.usefultoolsmod.item.custom.EctoplasmInfusionHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import snownee.jade.api.EntityAccessor;
import snownee.jade.api.IEntityComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public class GhostComponentProvider implements IEntityComponentProvider {

    @Override
    public ResourceLocation getUid() {
        return new ResourceLocation(UsefultoolsMod.MOD_ID, "ghost_info");
    }

    @Override
    public void appendTooltip(ITooltip tooltip, EntityAccessor accessor, IPluginConfig config) {
        if (!(accessor.getEntity() instanceof GhostEntity ghost)) return;
        Player player = accessor.getPlayer();

        if (ghost.isBaby()) {
            tooltip.add(Component.literal("Baby Ghost")
                    .withStyle(ChatFormatting.GRAY));
        }

        // Can the ghost see this player?
        boolean ghostInvisible = EctoplasmArmorHelper.isGhostInvisible(player);
        if (ghostInvisible) {
            tooltip.add(Component.literal("Cannot see you")
                    .withStyle(ChatFormatting.GREEN));
        } else {
            tooltip.add(Component.literal("Can see you")
                    .withStyle(ChatFormatting.YELLOW));
        }

        // Can the player hurt this ghost?
        ItemStack mainHand = player.getMainHandItem();
        boolean canHurt = EctoplasmInfusionHelper.isInfused(mainHand);
        if (canHurt) {
            tooltip.add(Component.literal("Your weapon can damage this ghost")
                    .withStyle(ChatFormatting.GREEN));
        } else {
            tooltip.add(Component.literal("Your weapon cannot damage this ghost")
                    .withStyle(ChatFormatting.RED));
            tooltip.add(Component.literal("Requires ectoplasm-infused weapon")
                    .withStyle(ChatFormatting.GRAY));
        }
    }
}
