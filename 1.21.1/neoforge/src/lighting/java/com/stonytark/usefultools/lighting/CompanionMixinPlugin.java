package com.stonytark.usefultools.lighting;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
/** Allows an external, unpackaged acceptance fixture to attach to native client ticks. */
public final class CompanionMixinPlugin implements IMixinConfigPlugin {
    public void onLoad(String mixinPackage) {}
    public String getRefMapperConfig() { return null; }
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) { return true; }
    public void acceptTargets(Set<String> mine, Set<String> other) {}
    public List<String> getMixins() { return Boolean.getBoolean("usefulToolsLightingQa") ? Collections.singletonList("LightingQaMixin") : Collections.emptyList(); }
    public void preApply(String target, ClassNode node, String name, IMixinInfo info) {}
    public void postApply(String target, ClassNode node, String name, IMixinInfo info) {}
}
