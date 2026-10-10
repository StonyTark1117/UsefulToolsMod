package com.stonytark.usefultools.lighting.classic;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import java.util.Map;
/** Forge 10.13.4.1614 native coremod. No client classes are loaded by this entry point. */
@IFMLLoadingPlugin.MCVersion("1.7.10")
@IFMLLoadingPlugin.SortingIndex(1001)
@IFMLLoadingPlugin.TransformerExclusions({"com.stonytark.usefultools.lighting.classic.LightingPlugin", "com.stonytark.usefultools.lighting.classic.LightingTransformer"})
public final class LightingPlugin implements IFMLLoadingPlugin {
    public String[] getASMTransformerClass() { return new String[] {"com.stonytark.usefultools.lighting.classic.LightingTransformer"}; }
    public String getModContainerClass() { return null; }
    public String getSetupClass() { return null; }
    public void injectData(Map<String, Object> data) {}
    public String getAccessTransformerClass() { return null; }
}
