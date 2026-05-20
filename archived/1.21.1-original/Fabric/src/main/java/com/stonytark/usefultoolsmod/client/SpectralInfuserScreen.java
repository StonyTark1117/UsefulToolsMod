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
    private static final Identifier TEXTURE = Identifier.of(UsefultoolsMod.MOD_ID, "textures/gui/spectral_infuser.png");

    public SpectralInfuserScreen(SpectralInfuserMenu menu, PlayerInventory inv, Text title) {
        super(menu, inv, title);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        context.drawTexture(TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);

        // Progress arrow
        if (handler.isCrafting()) {
            context.drawTexture(TEXTURE, x + 79, y + 35, 176, 0, handler.getScaledProgress(), 16);
        }
    }
}
