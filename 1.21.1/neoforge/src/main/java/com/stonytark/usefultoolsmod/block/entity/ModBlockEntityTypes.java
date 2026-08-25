package com.stonytark.usefultoolsmod.block.entity;


import net.minecraft.core.registries.BuiltInRegistries;
import com.stonytark.usefultoolsmod.UsefultoolsMod;
import com.stonytark.usefultoolsmod.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ModBlockEntityTypes {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, UsefultoolsMod.MOD_ID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SpectralInfuserBlockEntity>> SPECTRAL_INFUSER =
            BLOCK_ENTITY_TYPES.register("spectral_infuser",
                    () -> BlockEntityType.Builder.of(SpectralInfuserBlockEntity::new,
                            ModBlocks.SPECTRAL_INFUSER.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITY_TYPES.register(eventBus);
    }
}
