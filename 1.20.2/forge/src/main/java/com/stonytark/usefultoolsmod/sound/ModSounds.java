package com.stonytark.usefultoolsmod.sound;

import com.stonytark.usefultoolsmod.UsefultoolsMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModSounds {
    private static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, UsefultoolsMod.MOD_ID);
    public static final RegistryObject<SoundEvent> GHOST_AMBIENT = sound("entity.ghost.ambient");
    public static final RegistryObject<SoundEvent> GHOST_HURT = sound("entity.ghost.hurt");
    public static final RegistryObject<SoundEvent> GHOST_DEATH = sound("entity.ghost.death");
    public static final RegistryObject<SoundEvent> WRAITH_AMBIENT = sound("entity.wraith.ambient");
    public static final RegistryObject<SoundEvent> WRAITH_HURT = sound("entity.wraith.hurt");
    public static final RegistryObject<SoundEvent> WRAITH_DEATH = sound("entity.wraith.death");
    private ModSounds() {}
    private static RegistryObject<SoundEvent> sound(String id) {
        return SOUNDS.register(id, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(UsefultoolsMod.MOD_ID, id)));
    }
    public static void register(IEventBus bus) { SOUNDS.register(bus); }
}
