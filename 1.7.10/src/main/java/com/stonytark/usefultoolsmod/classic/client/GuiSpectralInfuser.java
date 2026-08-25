package com.stonytark.usefultoolsmod.classic.client;

import com.stonytark.usefultoolsmod.classic.ContainerSpectralInfuser;
import com.stonytark.usefultoolsmod.classic.TileSpectralInfuser;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiSpectralInfuser extends GuiContainer {
    private final ContainerSpectralInfuser infuser;

    public GuiSpectralInfuser(InventoryPlayer player, TileSpectralInfuser tile) {
        super(new ContainerSpectralInfuser(player, tile));
        infuser = (ContainerSpectralInfuser) inventorySlots;
        xSize = 176;
        ySize = 166;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        fontRendererObj.drawString("Spectral Infuser", 8, 6, 0x404040);
        fontRendererObj.drawString("Item", 39, 21, 0x404040);
        fontRendererObj.drawString("Ectoplasm", 67, 21, 0x404040);
        fontRendererObj.drawString("Result", 126, 21, 0x404040);
        fontRendererObj.drawString("Inventory", 8, 72, 0x404040);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partial, int mouseX, int mouseY) {
        int left = (width - xSize) / 2;
        int top = (height - ySize) / 2;
        drawRect(left - 1, top - 1, left + xSize + 1, top + ySize + 1, 0xFF202020);
        drawRect(left, top, left + xSize, top + ySize, 0xFFC6C6C6);
        slot(left, top, 44, 35);
        slot(left, top, 80, 35);
        slot(left, top, 134, 35);
        drawRect(left + 101, top + 39, left + 127, top + 43, 0xFF404040);
        drawRect(left + 102, top + 40, left + 102 + infuser.scaledProgress(24), top + 42, 0xFF4FCFE8);
        for (int row = 0; row < 3; row++) for (int column = 0; column < 9; column++)
            slot(left, top, 8 + column * 18, 84 + row * 18);
        for (int column = 0; column < 9; column++) slot(left, top, 8 + column * 18, 142);
    }

    private void slot(int left, int top, int x, int y) {
        drawRect(left + x - 1, top + y - 1, left + x + 17, top + y + 17, 0xFF555555);
        drawRect(left + x, top + y, left + x + 16, top + y + 16, 0xFFEEEEEE);
    }
}
