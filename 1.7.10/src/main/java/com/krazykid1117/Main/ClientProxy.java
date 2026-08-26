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
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderSnowball;

public class ClientProxy
extends ServerProxy {
    @Override
    public void registerRenderInfo() {
        RenderingRegistry.registerEntityRenderingHandler(EntityGrenade.class, (Render)new RenderSnowball(MItems.Grenade));
        RenderingRegistry.registerEntityRenderingHandler(EntityGhost.class, new RenderGhost());
        RenderingRegistry.registerEntityRenderingHandler(EntityWraith.class, new RenderWraith());
        RenderingRegistry.registerEntityRenderingHandler(EntityStickyDynamite.class,
                (Render)new RenderSnowball(ClassicFeatureRegistry.stickyDynamite));
    }

    public int addArmor(String armor) {
        return RenderingRegistry.addNewArmourRendererPrefix((String)armor);
    }
}
