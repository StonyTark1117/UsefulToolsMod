package com.stonytark.usefultoolsmod.fabric.mixin;

import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

/**
 * Exposes {@link FireBlock#getBurnOdds(BlockState)} which is private in vanilla.
 * NeoForge has this public; this accessor brings Fabric to parity.
 */
@Mixin(FireBlock.class)
public interface FireBlockAccessor {
    @Invoker("getBurnOdds")
    int usefultoolsmod_getBurnOdds(BlockState state);
}
