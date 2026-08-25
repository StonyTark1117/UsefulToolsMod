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
import com.stonytark.usefultoolsmod.gametest.UsefulToolsGameTests;
import com.stonytark.usefultoolsmod.item.ModCreativeModeTabs;
import com.stonytark.usefultoolsmod.item.ModItems;
import com.stonytark.usefultoolsmod.trigger.ModTriggers;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.gametest.GameTest;
import net.minecraftforge.gametest.GameTestDontPrefix;
import net.minecraftforge.gametest.GameTestNamespace;
import net.minecraftforge.gametest.ForgeGameTestHooks;
import net.minecraftforge.registries.RegisterEvent;
import org.slf4j.Logger;

// The value here must match an entry in the META-INF/mods.toml file.
@Mod(UsefultoolsMod.MOD_ID)
@GameTestNamespace(UsefultoolsMod.MOD_ID)
@GameTestDontPrefix
public class UsefultoolsMod {
    public static final String MOD_ID = "usefultoolsmod";
    public static final Logger LOGGER = LogUtils.getLogger();
    private final java.util.Map<Identifier, ForgeGameTestHooks.TestReference> gameTests;

    public UsefultoolsMod(FMLJavaModLoadingContext context) {
        BusGroup modBusGroup = context.getModBusGroup();
        gameTests = ForgeGameTestHooks.gatherTests(getClass(), this);
        RegisterEvent.getBus(modBusGroup).addListener(this::registerTestFunctions);

        // Register the commonSetup method for modloading
        net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent.getBus(modBusGroup).addListener(this::commonSetup);

        // Keep creative tabs above others just in case
        ModCreativeModeTabs.register(modBusGroup);

        ModEntities.register(modBusGroup);
        ModItems.register(modBusGroup);
        ModBlocks.register(modBusGroup);
        ModBlockEntityTypes.register(modBusGroup);
        ModMenuTypes.register(modBusGroup);
        ModTriggers.register(modBusGroup);

        // Register the item to a creative tab. BuildCreativeModeTabContentsEvent fires on
        // the default (game) bus in Forge 26.1.2 — it has a static .BUS field, not getBus().
        // Game-bus listeners go directly on the event's BUS rather than through
        // MinecraftForge.EVENT_BUS.register(this), which is gated by the eventbus 7.x
        // migration helper and rejects classes that have only a single @SubscribeEvent.
        BuildCreativeModeTabContentsEvent.BUS.addListener(this::addCreative);

        // Register our mod's ForgeConfigSpec
        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);

        // Vanilla-only in-game config screen, registered via Forge's
        // ConfigScreenHandler extension point. Client-side only: skip on dedicated
        // server so the client-only Screen class isn't classloaded.
        if (net.minecraftforge.fml.loading.FMLEnvironment.dist
                == net.minecraftforge.api.distmarker.Dist.CLIENT) {
            ClientConfigRegistration.register(context.getContainer());
        }
    }

    private void registerTestFunctions(RegisterEvent event) {
        if (event.getRegistryKey() != Registries.TEST_FUNCTION) return;
        gameTests.forEach((id, test) ->
                event.register(Registries.TEST_FUNCTION, id, test::consumer));
    }

    @GameTest(structure = "gametest_platform", maxTicks = 100)
    public static void registrationsAndRecipeData(GameTestHelper helper) {
        UsefulToolsGameTests.registrationsAndRecipeData(helper);
    }

    @GameTest(structure = "gametest_platform", maxTicks = 100)
    public static void spectralInfuserCreatesBlockEntity(GameTestHelper helper) {
        UsefulToolsGameTests.spectralInfuserCreatesBlockEntity(helper);
    }

    @GameTest(structure = "gametest_platform", maxTicks = 100)
    public static void entitiesInstantiate(GameTestHelper helper) {
        UsefulToolsGameTests.entitiesInstantiate(helper);
    }

    @GameTest(structure = "gametest_platform", maxTicks = 260)
    public static void spectralInfuserProcesses(GameTestHelper helper) {
        UsefulToolsGameTests.spectralInfuserProcesses(helper);
    }

    @GameTest(structure = "gametest_platform", maxTicks = 100)
    public static void ghostAndDamageContracts(GameTestHelper helper) {
        UsefulToolsGameTests.ghostAndDamageContracts(helper);
    }

    @GameTest(structure = "gametest_platform", maxTicks = 100)
    public static void explosivesKillSwitch(GameTestHelper helper) {
        UsefulToolsGameTests.explosivesKillSwitch(helper);
    }

    private void commonSetup(final net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent event) {
        // JER integration disabled until JER publishes a 26.1 build (see compat/jer/**).
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ModItems.RGOLD.get());
            event.accept(ModItems.OBSHARD.get());
            event.accept(ModItems.SEM.get());
            event.accept(ModItems.OBINGOT.get());
            event.accept(ModItems.HRED.get());
            event.accept(ModItems.RLAPIS.get());
        }
        if (event.getTabKey() == CreativeModeTabs.COMBAT) {
            event.accept(ModItems.GRENADE.get());
            event.accept(ModItems.DYNAMITE.get());
        }
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.accept(ModBlocks.RGOLDBLOCK.get());
            event.accept(ModBlocks.HRBLOCK.get());
            event.accept(ModBlocks.SEMBLOCK.get());
            event.accept(ModBlocks.SOBLOCK.get());
            event.accept(ModBlocks.LBLOCK.get());
        }
        if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
            event.accept(ModBlocks.RGOLDORE.get());
            event.accept(ModBlocks.RGOLD_DEEPSLATE_ORE.get());
            event.accept(ModBlocks.RGOLD_END_ORE.get());
            event.accept(ModBlocks.RGOLD_NETHER_ORE.get());
        }
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            EntityRenderers.register(ModEntities.GHOST.get(), GhostRenderer::new);
            event.enqueueWork(() ->
                    MenuScreens.register(ModMenuTypes.SPECTRAL_INFUSER_MENU.get(), SpectralInfuserScreen::new));
        }
    }
}
