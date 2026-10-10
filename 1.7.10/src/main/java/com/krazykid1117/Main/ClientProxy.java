/*
 * Decompiled with CFR 0.152.
 *
 * Could not load the following classes:
 *  cpw.mods.fml.client.registry.RenderingRegistry
 *  net.minecraft.client.renderer.entity.Render
 *  net.minecraft.client.renderer.entity.RenderSnowball
 */
package com.krazykid1117.Main;

import com.krazykid1117.Main.ServerProxy;
import com.krazykid1117.entity.EntityGrenade;
import com.krazykid1117.item.MItems;
import cpw.mods.fml.client.registry.RenderingRegistry;
import com.stonytark.usefultoolsmod.classic.EntityGhost;
import com.stonytark.usefultoolsmod.classic.client.RenderGhost;
import com.stonytark.usefultoolsmod.classic.EntityWraith;
import com.stonytark.usefultoolsmod.classic.EntityStickyDynamite;
import com.stonytark.usefultoolsmod.classic.ClassicFeatureRegistry;
import com.stonytark.usefultoolsmod.classic.client.RenderWraith;
import com.stonytark.usefultoolsmod.classic.ClassicGeneratedCatalog;
import com.stonytark.usefultoolsmod.classic.client.ExplosiveItemRenderer;
import com.stonytark.usefultoolsmod.classic.client.RenderGrenade;
import com.stonytark.usefultoolsmod.classic.client.DeviceItemRenderer;
import com.stonytark.usefultoolsmod.classic.client.RenderStickyDynamite;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy
extends ServerProxy {
    @Override
    public void registerRenderInfo() {
        RenderingRegistry.registerEntityRenderingHandler(EntityGrenade.class, new RenderGrenade());
        // Both the old mixed-case items and the canonical catalog remain usable.
        MinecraftForgeClient.registerItemRenderer(MItems.Dynamite, new ExplosiveItemRenderer(true));
        MinecraftForgeClient.registerItemRenderer(MItems.Grenade, new ExplosiveItemRenderer(false));
        MinecraftForgeClient.registerItemRenderer(ClassicGeneratedCatalog.ITEMS.get("dynamite"), new ExplosiveItemRenderer(true));
        MinecraftForgeClient.registerItemRenderer(ClassicGeneratedCatalog.ITEMS.get("grenade"), new ExplosiveItemRenderer(false));
        for (String name : new String[] {"sticky_dynamite", "remote_detonator", "spectral_resonator"}) {
            MinecraftForgeClient.registerItemRenderer(ClassicGeneratedCatalog.ITEMS.get(name), new DeviceItemRenderer(name));
        }
        for (String name : new String[] {"mining_charge", "ectoplasm_lantern", "soul_lantern"}) {
            MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ClassicGeneratedCatalog.BLOCKS.get(name)), new DeviceItemRenderer(name));
        }
        RenderingRegistry.registerEntityRenderingHandler(EntityGhost.class, new RenderGhost());
        RenderingRegistry.registerEntityRenderingHandler(EntityWraith.class, new RenderWraith());
        RenderingRegistry.registerEntityRenderingHandler(EntityStickyDynamite.class,
                new RenderStickyDynamite());
    }

    public int addArmor(String armor) {
        return RenderingRegistry.addNewArmourRendererPrefix((String)armor);
    }
}
