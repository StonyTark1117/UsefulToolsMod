package com.stonytark.usefultools.lighting.mixin;

import com.stonytark.usefultools.lighting.IrisPbrTrackerBridge;
import net.minecraft.client.renderer.texture.AbstractTexture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** Optional bridge for the two trackers present in the published ForgeOculus 1.8.8 jar. */
@Pseudo
@Mixin(targets = "net.irisshaders.iris.texture.TextureTracker", remap = false)
public abstract class IrisPbrTextureMixin {
    @Inject(method = "trackTexture", at = @At("TAIL"), remap = false)
    private void usefulToolsLighting$track(int id, AbstractTexture texture, CallbackInfo ci) {
        IrisPbrTrackerBridge.track(id, texture);
    }

    @Inject(method = "onDeleteTexture", at = @At("TAIL"), remap = false)
    private void usefulToolsLighting$delete(int id, CallbackInfo ci) {
        IrisPbrTrackerBridge.delete(id);
    }
}
