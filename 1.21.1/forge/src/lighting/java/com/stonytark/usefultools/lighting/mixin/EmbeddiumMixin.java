package com.stonytark.usefultools.lighting.mixin;
import com.stonytark.usefultools.lighting.ClientLighting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
@Pseudo
@Mixin(targets = "org.embeddedt.embeddium.impl.model.light.data.LightDataAccess", remap = false)
public abstract class EmbeddiumMixin {
    @Inject(method = "compute", at = @At("RETURN"), cancellable = true, remap = false)
    private void usefulToolsLighting$sample(int x, int y, int z, CallbackInfoReturnable<Integer> cir) { cir.setReturnValue(ClientLighting.FIELD.sodium(cir.getReturnValueI(), x, y, z)); }
}
