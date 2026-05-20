package com.stonytark.usefultoolsmod;

import com.stonytark.usefultoolsmod.block.entity.ModMenuTypes;
import com.stonytark.usefultoolsmod.client.SpectralInfuserScreen;
import com.stonytark.usefultoolsmod.entity.ModEntities;
import com.stonytark.usefultoolsmod.entity.client.GhostModel;
import com.stonytark.usefultoolsmod.entity.client.GhostRenderer;
import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry;
import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import dev.architectury.registry.menu.MenuRegistry;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

/**
 * Client-side initialisation hub.
 *
 * Called by each loader's client entrypoint:
 *   Fabric:    {@code UsefultoolsModFabricClient#onInitializeClient()}
 *   NeoForge:  {@code UsefultoolsModNeoForge}'s @{@code Dist.CLIENT} bootstrap
 */
public final class UsefultoolsModClient {
    private UsefultoolsModClient() {}

    public static void initClient() {
        EntityRendererRegistry.register(ModEntities.GHOST, GhostRenderer::new);
        EntityRendererRegistry.register(ModEntities.GRENADE, ThrownItemRenderer::new);
        EntityModelLayerRegistry.register(GhostModel.LAYER_LOCATION, GhostModel::createBodyLayer);
        MenuRegistry.registerScreenFactory(ModMenuTypes.SPECTRAL_INFUSER_MENU.get(), SpectralInfuserScreen::new);
    }
}
