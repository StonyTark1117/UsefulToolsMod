package com.stonytark.usefultools.lighting.classic;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import java.util.Map;

/** Package-only bootstrap: command-line coremods load before mod JAR classes exist. */
@IFMLLoadingPlugin.MCVersion("1.7.10")
@IFMLLoadingPlugin.SortingIndex(1001)
@IFMLLoadingPlugin.TransformerExclusions({
        "com.stonytark.usefultools.lighting.classic.ClassicLightingQaPlugin",
        "com.stonytark.usefultools.lighting.classic.LightingPlugin",
        "com.stonytark.usefultools.lighting.classic.LightingTransformer",
        "com.stonytark.usefultools.lighting.classic.ClassicQaAudioPlugin"})
public final class ClassicLightingQaPlugin implements IFMLLoadingPlugin {
    private final LightingPlugin production = new LightingPlugin();

    public String[] getASMTransformerClass() {
        String[] nativeTransformers = production.getASMTransformerClass();
        String[] result = java.util.Arrays.copyOf(nativeTransformers, nativeTransformers.length + 1);
        result[nativeTransformers.length] = ClassicQaAudioPlugin.Transformer.class.getName();
        return result;
    }

    public String getModContainerClass() { return production.getModContainerClass(); }
    public String getSetupClass() { return production.getSetupClass(); }
    public void injectData(Map<String, Object> data) { production.injectData(data); }
    public String getAccessTransformerClass() { return production.getAccessTransformerClass(); }
}
