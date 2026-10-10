package com.stonytark.usefultools.lighting.mixin;
import net.minecraft.client.multiplayer.ClientChunkCache;
import net.minecraft.world.level.chunk.LevelChunk;
import com.stonytark.usefultools.lighting.ClientLighting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
@Mixin(ClientChunkCache.class)
public abstract class ChunkDataMixin {
    @Inject(method="replaceWithPacketData", at=@At("RETURN"))
    private void usefulToolsLighting$chunk(CallbackInfoReturnable<LevelChunk> cir) { ClientLighting.chunkChanged(cir.getReturnValue()); }
}
