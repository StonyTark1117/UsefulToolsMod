package com.stonytark.usefultoolsmod.client;

import com.stonytark.usefultoolsmod.entity.ModEntities;
import com.stonytark.usefultoolsmod.entity.client.GhostModel;
import com.stonytark.usefultoolsmod.entity.client.GhostRenderer;
import com.stonytark.usefultoolsmod.entity.client.WraithModel;
import com.stonytark.usefultoolsmod.entity.client.WraithRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

public final class ModEntityRenderers {
    private ModEntityRenderers() {}

    public static void register() {
        // Entities
        EntityRenderers.register(ModEntities.GRENADE, ThrownItemRenderer::new);
        EntityRenderers.register(ModEntities.GHOST, GhostRenderer::new);
        EntityRenderers.register(ModEntities.WRAITH, WraithRenderer::new);
        EntityRenderers.register(ModEntities.STICKY_DYNAMITE, ThrownItemRenderer::new);

        // Model layers
        ModelLayerRegistry.registerModelLayer(GhostModel.LAYER_LOCATION, GhostModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(WraithModel.LAYER_LOCATION, WraithModel::createBodyLayer);
    }
}
