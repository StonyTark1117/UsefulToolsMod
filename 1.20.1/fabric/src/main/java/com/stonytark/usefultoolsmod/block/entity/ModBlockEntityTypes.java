package com.stonytark.usefultoolsmod.block.entity;

import com.stonytark.usefultoolsmod.UsefultoolsMod;
import com.stonytark.usefultoolsmod.block.ModBlocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntityTypes {

    public static final BlockEntityType<SpectralInfuserBlockEntity> SPECTRAL_INFUSER =
            Registry.register(Registries.BLOCK_ENTITY_TYPE,
                    new Identifier(UsefultoolsMod.MOD_ID, "spectral_infuser"),
                    BlockEntityType.Builder.create(SpectralInfuserBlockEntity::new,
                            ModBlocks.SPECTRAL_INFUSER).build(null));

    public static void register() {
        // Registration happens via the static initializer
    }
}
