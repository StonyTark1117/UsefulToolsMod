package com.stonytark.usefultools.lighting.mixin;
import net.minecraft.client.Minecraft;
import com.stonytark.usefultools.lighting.ClientLighting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
@Mixin(Minecraft.class)
public abstract class ClientTickMixin {
    @Inject(method = "tick", at = @At("TAIL"))
    private void usefulToolsLighting$tick(CallbackInfo ci) { ClientLighting.tick(); }
}
