package com.stonytark.usefultoolsmod.sound;

import com.stonytark.usefultoolsmod.UsefultoolsMod;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModSounds {
    private static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, UsefultoolsMod.MOD_ID);
    public static final DeferredHolder<SoundEvent, SoundEvent> GHOST_AMBIENT = sound("entity.ghost.ambient");
    public static final DeferredHolder<SoundEvent, SoundEvent> GHOST_HURT = sound("entity.ghost.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> GHOST_DEATH = sound("entity.ghost.death");
    public static final DeferredHolder<SoundEvent, SoundEvent> WRAITH_AMBIENT = sound("entity.wraith.ambient");
    public static final DeferredHolder<SoundEvent, SoundEvent> WRAITH_HURT = sound("entity.wraith.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> WRAITH_DEATH = sound("entity.wraith.death");
    private ModSounds() {}
    private static DeferredHolder<SoundEvent, SoundEvent> sound(String path) {
        return SOUNDS.register(path, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(UsefultoolsMod.MOD_ID, path)));
    }
    public static void register(IEventBus bus) { SOUNDS.register(bus); }
}
