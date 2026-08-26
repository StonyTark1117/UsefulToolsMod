package com.stonytark.usefultoolsmod.sound;

import com.stonytark.usefultoolsmod.UsefultoolsMod;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModSounds {
    private static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, UsefultoolsMod.MOD_ID);
    private static final DeferredHolder<SoundEvent, SoundEvent> GHOST_AMBIENT = sound("entity.ghost.ambient");
    private static final DeferredHolder<SoundEvent, SoundEvent> GHOST_HURT = sound("entity.ghost.hurt");
    private static final DeferredHolder<SoundEvent, SoundEvent> GHOST_DEATH = sound("entity.ghost.death");
    private static final DeferredHolder<SoundEvent, SoundEvent> WRAITH_AMBIENT = sound("entity.wraith.ambient");
    private static final DeferredHolder<SoundEvent, SoundEvent> WRAITH_HURT = sound("entity.wraith.hurt");
    private static final DeferredHolder<SoundEvent, SoundEvent> WRAITH_DEATH = sound("entity.wraith.death");
    private ModSounds() {}
    private static DeferredHolder<SoundEvent, SoundEvent> sound(String path) { return SOUNDS.register(path,
            () -> SoundEvent.createVariableRangeEvent(Identifier.fromNamespaceAndPath(UsefultoolsMod.MOD_ID, path))); }
    public static void register(IEventBus bus) { SOUNDS.register(bus); }
    public static SoundEvent ghostAmbient() { return GHOST_AMBIENT.get(); }
    public static SoundEvent ghostHurt() { return GHOST_HURT.get(); }
    public static SoundEvent ghostDeath() { return GHOST_DEATH.get(); }
    public static SoundEvent wraithAmbient() { return WRAITH_AMBIENT.get(); }
    public static SoundEvent wraithHurt() { return WRAITH_HURT.get(); }
    public static SoundEvent wraithDeath() { return WRAITH_DEATH.get(); }
}
