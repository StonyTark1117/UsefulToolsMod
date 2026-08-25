package com.stonytark.usefultoolsmod.compat.jade;

import com.stonytark.usefultoolsmod.UsefultoolsMod;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public class SpectralInfuserComponentProvider implements IBlockComponentProvider {

    @Override
    public ResourceLocation getUid() {
        return new ResourceLocation(UsefultoolsMod.MOD_ID, "spectral_infuser");
    }

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        CompoundTag data = accessor.getServerData();
        if (data == null || data.isEmpty()) return;

        int progress = data.getInt("utm_progress");
        int maxProgress = data.getInt("utm_maxProgress");

        if (data.contains("utm_outputName")) {
            String outputName = data.getString("utm_outputName");
            tooltip.add(Component.literal("Output: ")
                    .withStyle(ChatFormatting.GREEN)
                    .append(Component.literal(outputName).withStyle(ChatFormatting.WHITE)));
        } else if (data.contains("utm_inputName")) {
            String inputName = data.getString("utm_inputName");

            if (progress > 0 && maxProgress > 0) {
                int percent = (progress * 100) / maxProgress;
                tooltip.add(Component.literal("Infusing: ")
                        .withStyle(ChatFormatting.DARK_AQUA)
                        .append(Component.literal(inputName).withStyle(ChatFormatting.WHITE)));
                tooltip.add(Component.literal("Progress: " + percent + "%")
                        .withStyle(ChatFormatting.GRAY));
            } else {
                tooltip.add(Component.literal("Input: ")
                        .withStyle(ChatFormatting.YELLOW)
                        .append(Component.literal(inputName).withStyle(ChatFormatting.WHITE)));
                if (!data.getBoolean("utm_hasFuel")) {
                    tooltip.add(Component.literal("Needs Ectoplasm")
                            .withStyle(ChatFormatting.RED));
                }
            }
        } else {
            tooltip.add(Component.literal("Empty")
                    .withStyle(ChatFormatting.DARK_GRAY));
        }
    }
}
