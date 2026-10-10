package com.stonytark.usefultools.lighting.mixin;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.core.BlockPos;
import com.stonytark.usefultools.lighting.ClientLighting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
@Mixin(LevelRenderer.class)
public abstract class LightmapMixin {
    @Inject(method = "getLightColor(Lnet/minecraft/world/level/BlockAndTintGetter;Lnet/minecraft/core/BlockPos;)I", at = @At("RETURN"), cancellable = true)
    private static void usefulToolsLighting$sample(BlockAndTintGetter view, BlockPos pos, CallbackInfoReturnable<Integer> cir) { cir.setReturnValue(ClientLighting.sample(cir.getReturnValueI(), pos)); }
    @Inject(method = "getLightColor(Lnet/minecraft/world/level/BlockAndTintGetter;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;)I", at = @At("RETURN"), cancellable = true)
    private static void usefulToolsLighting$stateSample(BlockAndTintGetter view, net.minecraft.world.level.block.state.BlockState state, BlockPos pos, CallbackInfoReturnable<Integer> cir) { cir.setReturnValue(ClientLighting.sample(cir.getReturnValueI(), pos)); }
}
