package com.stonytark.usefultoolsmod;

import com.mojang.logging.LogUtils;
import com.stonytark.usefultoolsmod.block.ModBlocks;
import com.stonytark.usefultoolsmod.block.entity.ModBlockEntityTypes;
import com.stonytark.usefultoolsmod.block.entity.ModMenuTypes;
import com.stonytark.usefultoolsmod.block.entity.SpectralInfuserMenu;
import com.stonytark.usefultoolsmod.client.SpectralInfuserScreen;
import com.stonytark.usefultoolsmod.entity.ModEntities;
import com.stonytark.usefultoolsmod.entity.client.GhostRenderer;
import com.stonytark.usefultoolsmod.item.ModCreativeModeTabs;
import com.stonytark.usefultoolsmod.item.ModItems;
import com.stonytark.usefultoolsmod.trigger.ModTriggers;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(UsefultoolsMod.MOD_ID)
public class UsefultoolsMod
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "usefultoolsmod";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public UsefultoolsMod(FMLJavaModLoadingContext context)
    {
        IEventBus modEventBus = context.getModEventBus();

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
        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        context.registerConfig(ModConfig.Type.SERVER, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ModItems.ECTOPLASM);
            event.accept(ModItems.RGOLD);
            event.accept(ModItems.RAW_RGOLD);
            event.accept(ModItems.OBSHARD);
            event.accept(ModItems.SEM);
            event.accept(ModItems.OBINGOT);
            event.accept(ModItems.HRED);
            event.accept(ModItems.RLAPIS);
        }
        if(event.getTabKey() == CreativeModeTabs.COMBAT) {
            event.accept(ModItems.GRENADE);
            event.accept(ModItems.DYNAMITE);
        }
        if(event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.accept(ModBlocks.RGOLDBLOCK);
            event.accept(ModBlocks.HRBLOCK);
            event.accept(ModBlocks.SEMBLOCK);
            event.accept(ModBlocks.SOBLOCK);
            event.accept(ModBlocks.LBLOCK);
        }
        if(event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            event.accept(ModBlocks.SPECTRAL_INFUSER);
        }
        if(event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
            event.accept(ModBlocks.RGOLDORE);
            event.accept(ModBlocks.RGOLD_DEEPSLATE_ORE);
            event.accept(ModBlocks.RGOLD_END_ORE);
            event.accept(ModBlocks.RGOLD_NETHER_ORE);
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            EntityRenderers.register(ModEntities.GHOST.get(), GhostRenderer::new);
            event.enqueueWork(() ->
                    MenuScreens.register(ModMenuTypes.SPECTRAL_INFUSER_MENU.get(), SpectralInfuserScreen::new));
        }
    }
}
