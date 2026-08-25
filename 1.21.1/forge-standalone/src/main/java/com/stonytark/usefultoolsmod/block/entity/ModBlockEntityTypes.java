package com.stonytark.usefultoolsmod.block.entity;

import com.stonytark.usefultoolsmod.UsefultoolsMod;
import com.stonytark.usefultoolsmod.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntityTypes {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, UsefultoolsMod.MOD_ID);

    public static final RegistryObject<BlockEntityType<SpectralInfuserBlockEntity>> SPECTRAL_INFUSER =
            BLOCK_ENTITY_TYPES.register("spectral_infuser",
                    () -> BlockEntityType.Builder.of(SpectralInfuserBlockEntity::new,
                            ModBlocks.SPECTRAL_INFUSER.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITY_TYPES.register(eventBus);
    }
}
