package com.stonytark.usefultools.lighting.mixin;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import com.stonytark.usefultools.lighting.ClientLighting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
@Mixin(ClientLevel.class)
public abstract class BlockUpdateMixin {
    @Inject(method = "setBlocksDirty", at = @At("TAIL"))
    private void usefulToolsLighting$changed(BlockPos pos, net.minecraft.world.level.block.state.BlockState oldState, net.minecraft.world.level.block.state.BlockState newState, CallbackInfo ci) { ClientLighting.blockChanged(pos, newState); }
}
