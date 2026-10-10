package com.stonytark.usefultools.lighting.mixin;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.world.BlockRenderView;
import net.minecraft.util.math.BlockPos;
import com.stonytark.usefultools.lighting.ClientLighting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
@Mixin(WorldRenderer.class)
public abstract class LightmapMixin {
    @Inject(method = "getLightmapCoordinates(Lnet/minecraft/world/BlockRenderView;Lnet/minecraft/util/math/BlockPos;)I", at = @At("RETURN"), cancellable = true)
    private static void usefulToolsLighting$sample(BlockRenderView view, BlockPos pos, CallbackInfoReturnable<Integer> cir) { cir.setReturnValue(ClientLighting.sample(cir.getReturnValueI(), pos)); }
    @Inject(method = "getLightmapCoordinates(Lnet/minecraft/world/BlockRenderView;Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/BlockPos;)I", at = @At("RETURN"), cancellable = true)
    private static void usefulToolsLighting$stateSample(BlockRenderView view, net.minecraft.block.BlockState state, BlockPos pos, CallbackInfoReturnable<Integer> cir) { cir.setReturnValue(ClientLighting.sample(cir.getReturnValueI(), pos)); }
}
