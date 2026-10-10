package com.stonytark.usefultools.lighting.mixin;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import com.stonytark.usefultools.lighting.ClientLighting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
@Mixin(EntityRenderer.class)
public abstract class EntityLightMixin {
    @Inject(method = "getLight", at = @At("RETURN"), cancellable = true)
    private void usefulToolsLighting$entity(Entity entity, float partialTicks, CallbackInfoReturnable<Integer> cir) { cir.setReturnValue(ClientLighting.FIELD.packed(cir.getReturnValueI(), entity.getX(), entity.getY() + entity.getStandingEyeHeight(), entity.getZ())); }
}
