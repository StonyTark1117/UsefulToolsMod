package com.stonytark.usefultoolsmod;

import com.mojang.logging.LogUtils;
import com.stonytark.usefultoolsmod.block.ModBlocks;
import com.stonytark.usefultoolsmod.block.entity.ModBlockEntityTypes;
import com.stonytark.usefultoolsmod.block.entity.ModMenuTypes;
import com.stonytark.usefultoolsmod.block.entity.SpectralInfuserMenu;
import com.stonytark.usefultoolsmod.client.ClientConfigRegistration;
import com.stonytark.usefultoolsmod.client.SpectralInfuserScreen;
import com.stonytark.usefultoolsmod.entity.ModEntities;
import com.stonytark.usefultoolsmod.entity.client.GhostRenderer;
import com.stonytark.usefultoolsmod.item.ModCreativeModeTabs;
import com.stonytark.usefultoolsmod.item.ModItems;
import com.stonytark.usefultoolsmod.trigger.ModTriggers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(UsefultoolsMod.MOD_ID)
public class UsefultoolsMod
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "usefultoolsmod";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public UsefultoolsMod(IEventBus modEventBus, ModContainer container)
    {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        //Keep creative tabs above others just in case
        ModCreativeModeTabs.register(modEventBus);

        ModEntities.register(modEventBus);
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModBlockEntityTypes.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        ModTriggers.register(modEventBus);

        // Register ourselves for server and other game events we are interested in
        NeoForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ModConfigSpec so that NeoForge can create and load the config file for us
        container.registerConfig(ModConfig.Type.COMMON, Config.SPEC);

        // Cloth Config–backed in-game config screen (no-op when cloth_config isn't loaded)
        // 1.21.5+: FMLEnvironment.dist field replaced by FMLEnvironment.getDist() method.
        if (FMLEnvironment.getDist() == Dist.CLIENT) {
            ClientConfigRegistration.register(container);
        }
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        event.enqueueWork(() -> {
            // Just Enough Resources: register our ore worldgen distributions
            // so they show up in JER's WorldGen tab. JER's @JERPlugin scanner
            // is broken on NeoForge, so we register directly against
            // JERAPI.getInstance() during common setup. The isLoaded gate
            // keeps UsefulToolsJerPlugin (and its JER imports) unloaded
            // when JER isn't installed.
            // NOTE: temporarily disabled while JER doesn't have a 26.1
            // build pinned in gradle.properties; re-enable once jer_version
            // points at a real artifact and `compat/jer` is removed from
            // the source-set exclude list in build.gradle.
            // if (net.neoforged.fml.ModList.get().isLoaded("jeresources")) {
            //     com.stonytark.usefultoolsmod.compat.jer.UsefulToolsJerPlugin.register();
            // }
        });
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ModItems.RGOLD.get());
            event.accept(ModItems.OBSHARD.get());
            event.accept(ModItems.SEM.get());
            event.accept(ModItems.OBINGOT.get());
            event.accept(ModItems.HRED.get());
            event.accept(ModItems.RLAPIS.get());
        }
        if(event.getTabKey() == CreativeModeTabs.COMBAT) {
            event.accept(ModItems.GRENADE.get());
            event.accept(ModItems.DYNAMITE.get());
        }
        if(event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.accept(ModBlocks.RGOLDBLOCK.get());
            event.accept(ModBlocks.HRBLOCK.get());
            event.accept(ModBlocks.SEMBLOCK.get());
            event.accept(ModBlocks.SOBLOCK.get());
            event.accept(ModBlocks.LBLOCK.get());
        }
        if(event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
            event.accept(ModBlocks.RGOLDORE.get());
            event.accept(ModBlocks.RGOLD_DEEPSLATE_ORE.get());
            event.accept(ModBlocks.RGOLD_END_ORE.get());
            event.accept(ModBlocks.RGOLD_NETHER_ORE.get());
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MOD_ID, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            EntityRenderers.register(ModEntities.GHOST.get(), GhostRenderer::new);
        }

        @SubscribeEvent
        public static void onRegisterMenuScreens(RegisterMenuScreensEvent event)
        {
            event.register(ModMenuTypes.SPECTRAL_INFUSER_MENU.get(), SpectralInfuserScreen::new);
        }
    }
}
