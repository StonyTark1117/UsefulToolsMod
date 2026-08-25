package com.stonytark.usefultoolsmod.neoforge.client;

import com.stonytark.usefultoolsmod.block.entity.ModMenuTypes;
import com.stonytark.usefultoolsmod.client.SpectralInfuserScreen;
import com.stonytark.usefultoolsmod.entity.ModEntities;
import com.stonytark.usefultoolsmod.entity.client.GhostModel;
import com.stonytark.usefultoolsmod.entity.client.GhostRenderer;
import com.stonytark.usefultoolsmod.event.ModEvents;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

/** Registers client objects only after NeoForge has applied deferred registries. */
public final class NeoForgeClientRegistration {
    private NeoForgeClientRegistration() {}

    public static void register(IEventBus modBus) {
        modBus.addListener(NeoForgeClientRegistration::registerRenderers);
        modBus.addListener(NeoForgeClientRegistration::registerLayers);
        modBus.addListener(NeoForgeClientRegistration::registerScreens);
        modBus.addListener(NeoForgeClientRegistration::clientSetup);
    }

    private static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.GHOST.get(), GhostRenderer::new);
        event.registerEntityRenderer(ModEntities.GRENADE.get(), ThrownItemRenderer::new);
    }

    private static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(GhostModel.LAYER_LOCATION, GhostModel::createBodyLayer);
    }

    private static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(ModMenuTypes.SPECTRAL_INFUSER_MENU.get(), SpectralInfuserScreen::new);
    }

    private static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(ModEvents::initClient);
    }
}
