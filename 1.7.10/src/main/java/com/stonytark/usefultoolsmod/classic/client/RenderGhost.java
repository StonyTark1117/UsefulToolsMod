package com.stonytark.usefultoolsmod.classic.client;

import com.stonytark.usefultoolsmod.classic.EntityGhost;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderGhost extends RenderLiving {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation("usefultoolsmod", "textures/entity/ghost/ghost_white.png");

    public RenderGhost() {
        super(new ModelGhost(), 0.6F);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return TEXTURE;
    }
}
