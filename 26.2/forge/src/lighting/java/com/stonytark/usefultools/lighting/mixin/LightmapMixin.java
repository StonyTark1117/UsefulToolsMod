package com.stonytark.usefultools.lighting.mixin;
import net.minecraft.util.LightCoordsUtil;
import net.minecraft.world.level.BlockAndLightGetter;
import net.minecraft.core.BlockPos;
import com.stonytark.usefultools.lighting.ClientLighting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
@Mixin(LightCoordsUtil.class)
public abstract class LightmapMixin {
    @Inject(method = "getLightCoords(Lnet/minecraft/world/level/BlockAndLightGetter;Lnet/minecraft/core/BlockPos;)I", at = @At("RETURN"), cancellable = true)
    private static void usefulToolsLighting$sample(BlockAndLightGetter view, BlockPos pos, CallbackInfoReturnable<Integer> cir) { cir.setReturnValue(ClientLighting.sample(cir.getReturnValueI(), pos)); }
    @Inject(method = "getLightCoords(Lnet/minecraft/util/LightCoordsUtil$BrightnessGetter;Lnet/minecraft/world/level/BlockAndLightGetter;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;)I", at = @At("RETURN"), cancellable = true)
    private static void usefulToolsLighting$stateSample(LightCoordsUtil.BrightnessGetter brightness, BlockAndLightGetter view, net.minecraft.world.level.block.state.BlockState state, BlockPos pos, CallbackInfoReturnable<Integer> cir) { cir.setReturnValue(ClientLighting.sample(cir.getReturnValueI(), pos)); }
}
