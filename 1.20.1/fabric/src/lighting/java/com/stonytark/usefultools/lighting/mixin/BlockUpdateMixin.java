package com.stonytark.usefultools.lighting.mixin;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import com.stonytark.usefultools.lighting.ClientLighting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
@Mixin(ClientWorld.class)
public abstract class BlockUpdateMixin {
    @Inject(method = "updateListeners", at = @At("TAIL"))
    private void usefulToolsLighting$changed(BlockPos pos, net.minecraft.block.BlockState oldState, net.minecraft.block.BlockState newState, int flags, CallbackInfo ci) { ClientLighting.blockChanged(pos, newState); }
}
