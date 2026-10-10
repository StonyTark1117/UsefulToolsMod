package com.stonytark.usefultoolsmod.compat.client;

import net.minecraft.core.Direction;
import org.spongepowered.asm.mixin.Mixin;

/** Runs the compatibility plugin after other client replacements have produced Direction. */
@Mixin(Direction.class)
public abstract class ForgeDirectionCompatibilityMixin { }
