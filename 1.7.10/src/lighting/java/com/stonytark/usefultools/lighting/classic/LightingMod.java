package com.stonytark.usefultools.lighting.classic;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
@Mod(modid="usefultools_lighting", name="Useful Tools Lighting", version="2.3.2", acceptableRemoteVersions="*")
public final class LightingMod {
    @Mod.EventHandler
    public void initialize(FMLInitializationEvent event) throws Exception {
        if (event.getSide().isClient()) Class.forName("com.stonytark.usefultools.lighting.classic.ClassicClientLighting").getMethod("initialize").invoke(null);
    }
}
