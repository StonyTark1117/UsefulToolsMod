package com.stonytark.usefultoolsmod.block.entity;

import com.stonytark.usefultoolsmod.UsefultoolsMod;
import com.stonytark.usefultoolsmod.block.ModBlocks;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ModBlockEntityTypes {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
            DeferredRegister.create(UsefultoolsMod.MOD_ID, Registries.BLOCK_ENTITY_TYPE);

    public static final RegistrySupplier<BlockEntityType<SpectralInfuserBlockEntity>> SPECTRAL_INFUSER =
            BLOCK_ENTITY_TYPES.register("spectral_infuser",
                    () -> BlockEntityType.Builder.of(SpectralInfuserBlockEntity::new,
                            ModBlocks.SPECTRAL_INFUSER.get()).build(null));

    public static void register() {
        BLOCK_ENTITY_TYPES.register();
    }
}
