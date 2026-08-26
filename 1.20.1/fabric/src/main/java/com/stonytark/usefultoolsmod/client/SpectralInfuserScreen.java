package com.stonytark.usefultoolsmod.client;

import com.stonytark.usefultoolsmod.UsefultoolsMod;
import com.stonytark.usefultoolsmod.block.entity.SpectralInfuserMenu;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class SpectralInfuserScreen extends HandledScreen<SpectralInfuserMenu> {
    private static final Identifier TEXTURE =
            new Identifier(UsefultoolsMod.MOD_ID, "textures/gui/spectral_infuser.png");

    public SpectralInfuserScreen(SpectralInfuserMenu menu, PlayerInventory inv, Text title) {
        super(menu, inv, title);
    }

    @Override
    protected void drawBackground(DrawContext context, float partialTick, int mouseX, int mouseY) {
        int x = (this.width - this.backgroundWidth) / 2;
        int y = (this.height - this.backgroundHeight) / 2;
        context.drawTexture(TEXTURE, x, y, 0, 0, this.backgroundWidth, this.backgroundHeight);

        // Progress arrow
        if (this.handler.isCrafting()) {
            context.drawTexture(TEXTURE, x + 79, y + 35, 176, 0,
                    this.handler.getScaledProgress(), 16);
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(context);
        super.render(context, mouseX, mouseY, partialTick);
        this.drawMouseoverTooltip(context, mouseX, mouseY);
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        super.drawForeground(context, mouseX, mouseY);
        if (handler.getFuelUses() > 0) context.drawText(textRenderer,
                Text.translatable("gui.usefultoolsmod.infuser_fuel_uses", handler.getFuelUses()),
                76, 58, 0x72E6E1, false);
    }
}
