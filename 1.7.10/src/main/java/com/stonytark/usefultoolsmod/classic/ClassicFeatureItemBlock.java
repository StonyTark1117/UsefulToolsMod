package com.stonytark.usefultoolsmod.classic;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.IIcon;

/** Keeps sparse inventory sprites separate from the opaque legacy block faces. */
public class ClassicFeatureItemBlock extends ItemBlock {
    private final String texture;
    private IIcon icon;

    public ClassicFeatureItemBlock(Block block) {
        super(block);
        String name = block.getUnlocalizedName();
        texture = "usefultoolsmod:" + name.substring(name.indexOf('.') + 1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        icon = register.registerIcon(texture);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int damage) {
        return icon;
    }
}
