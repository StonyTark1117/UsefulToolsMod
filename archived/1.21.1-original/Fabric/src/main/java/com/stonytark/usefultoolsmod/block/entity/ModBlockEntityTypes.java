package com.stonytark.usefultoolsmod.block.entity;

import com.stonytark.usefultoolsmod.UsefultoolsMod;
import com.stonytark.usefultoolsmod.block.ModBlocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntityTypes {
    public static final BlockEntityType<SpectralInfuserBlockEntity> SPECTRAL_INFUSER_BE =
            Registry.register(Registries.BLOCK_ENTITY_TYPE,
                    Identifier.of(UsefultoolsMod.MOD_ID, "spectral_infuser"),
                    BlockEntityType.Builder.create(SpectralInfuserBlockEntity::new,
                            ModBlocks.SPECTRAL_INFUSER).build());

    public static void register() {}
}
