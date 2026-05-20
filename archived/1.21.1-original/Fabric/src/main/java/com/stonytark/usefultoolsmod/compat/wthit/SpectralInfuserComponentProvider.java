package com.stonytark.usefultoolsmod.compat.wthit;

import mcp.mobius.waila.api.IBlockAccessor;
import mcp.mobius.waila.api.IBlockComponentProvider;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.ITooltip;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class SpectralInfuserComponentProvider implements IBlockComponentProvider {

    @Override
    public void appendBody(ITooltip tooltip, IBlockAccessor accessor, IPluginConfig config) {
        NbtCompound data = accessor.getData().raw();
        if (data == null) return;

        int progress = data.getInt("utm_progress");
        int maxProgress = data.getInt("utm_maxProgress");

        if (data.contains("utm_outputName")) {
            String outputName = data.getString("utm_outputName");
            tooltip.addLine(Text.literal("Output: ")
                    .formatted(Formatting.GREEN)
                    .append(Text.literal(outputName).formatted(Formatting.WHITE)));
        } else if (data.contains("utm_inputName")) {
            String inputName = data.getString("utm_inputName");

            if (progress > 0 && maxProgress > 0) {
                int percent = (progress * 100) / maxProgress;
                tooltip.addLine(Text.literal("Infusing: ")
                        .formatted(Formatting.DARK_AQUA)
                        .append(Text.literal(inputName).formatted(Formatting.WHITE)));
                tooltip.addLine(Text.literal("Progress: " + percent + "%")
                        .formatted(Formatting.GRAY));
            } else {
                tooltip.addLine(Text.literal("Input: ")
                        .formatted(Formatting.YELLOW)
                        .append(Text.literal(inputName).formatted(Formatting.WHITE)));
                if (!data.getBoolean("utm_hasFuel")) {
                    tooltip.addLine(Text.literal("Needs Ectoplasm").formatted(Formatting.RED));
                }
            }
        } else {
            tooltip.addLine(Text.literal("Empty").formatted(Formatting.DARK_GRAY));
        }
    }
}
