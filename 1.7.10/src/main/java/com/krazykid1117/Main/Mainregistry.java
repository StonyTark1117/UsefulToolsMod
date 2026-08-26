/*
 * Decompiled with CFR 0.152.
 *
 * Could not load the following classes:
 *  cpw.mods.fml.common.FMLCommonHandler
 *  cpw.mods.fml.common.Mod
 *  cpw.mods.fml.common.Mod$EventHandler
 *  cpw.mods.fml.common.Mod$Instance
 *  cpw.mods.fml.common.SidedProxy
 *  cpw.mods.fml.common.event.FMLInitializationEvent
 *  cpw.mods.fml.common.event.FMLPostInitializationEvent
 *  cpw.mods.fml.common.event.FMLPreInitializationEvent
 */
package com.krazykid1117.Main;

import com.krazykid1117.CreativeTabs.MCreativeTabs;
import com.krazykid1117.Main.CraftingManager;
import com.krazykid1117.Main.ServerProxy;
import com.krazykid1117.achievements.MAchievements;
import com.krazykid1117.achievements.OnCraftEvent;
import com.krazykid1117.achievements.OnMineEvent;
import com.krazykid1117.blocks.MBlocks;
import com.krazykid1117.entity.MEntity;
import com.krazykid1117.item.MItems;
import com.krazykid1117.world.MWorld;
import com.stonytark.usefultoolsmod.BuildTags;
import com.stonytark.usefultoolsmod.classic.ClassicGeneratedCatalog;
import com.stonytark.usefultoolsmod.classic.ClassicGuiHandler;
import com.stonytark.usefultoolsmod.classic.ClassicGeneratedRecipes;
import com.stonytark.usefultoolsmod.classic.ClassicGeneratedConfig;
import com.stonytark.usefultoolsmod.classic.ClassicSpectralInfuserBlock;
import cpw.mods.fml.common.network.NetworkRegistry;
import com.stonytark.usefultoolsmod.classic.ClassicCombatEvents;
import com.stonytark.usefultoolsmod.classic.ClassicPlayerEffects;
import com.stonytark.usefultoolsmod.classic.ClassicAchievements;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLMissingMappingsEvent;
import com.stonytark.usefultoolsmod.classic.ClassicMissingMappings;
import com.stonytark.usefultoolsmod.classic.ClassicFeatureRegistry;

@Mod(modid="usefultoolsmod", name="Useful Tools", version=BuildTags.VERSION,
        guiFactory="com.stonytark.usefultoolsmod.classic.client.ClassicGuiFactory")
public class Mainregistry {
    @Mod.Instance
    public static Mainregistry modInstance;
    @SidedProxy(clientSide="com.krazykid1117.Main.ClientProxy", serverSide="com.krazykid1117.Main.ServerProxy")
    public static ServerProxy proxy;

    @Mod.EventHandler
    public static void PreLoad(FMLPreInitializationEvent PreEvent) {
        ClassicGeneratedConfig.load(PreEvent.getSuggestedConfigurationFile());
        MCreativeTabs.initialiseTabs();
        MBlocks.MainRegistry();
        MEntity.mainRegistry();
        MItems.MainRegistry();
        ClassicGeneratedCatalog.register();
        ClassicFeatureRegistry.register();
        ClassicSpectralInfuserBlock.registerTileEntity();
        NetworkRegistry.INSTANCE.registerGuiHandler(modInstance, new ClassicGuiHandler());
        MWorld.MainRegistry();
        CraftingManager.mainRegistry();
        ClassicGeneratedRecipes.register();
        proxy.registerRenderInfo();
        MAchievements.MainRegistry();
        ClassicAchievements.register();
    }

    @Mod.EventHandler
    public static void Load(FMLInitializationEvent Event) {
        FMLCommonHandler.instance().bus().register((Object)new OnMineEvent());
        FMLCommonHandler.instance().bus().register((Object)new OnCraftEvent());
        FMLCommonHandler.instance().bus().register(new ClassicPlayerEffects());
        FMLCommonHandler.instance().bus().register(new ClassicAchievements());
        MinecraftForge.EVENT_BUS.register(new ClassicCombatEvents());
    }

    @Mod.EventHandler
    public static void PostLoad(FMLPostInitializationEvent PostEvent) {
    }

    @Mod.EventHandler
    public static void missingMappings(FMLMissingMappingsEvent event) {
        ClassicMissingMappings.remap(event);
    }
}
