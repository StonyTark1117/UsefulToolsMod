package com.stonytark.usefultools.lighting.mixin;
import net.minecraft.client.world.ClientChunkManager;
import net.minecraft.world.chunk.WorldChunk;
import com.stonytark.usefultools.lighting.ClientLighting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
@Mixin(ClientChunkManager.class)
public abstract class ChunkDataMixin {
    @Inject(method="loadChunkFromPacket", at=@At("RETURN"))
    private void usefulToolsLighting$chunk(CallbackInfoReturnable<WorldChunk> cir) { ClientLighting.chunkChanged(cir.getReturnValue()); }
}
