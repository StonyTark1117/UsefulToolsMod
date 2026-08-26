package com.stonytark.usefultoolsmod.sound;

import com.stonytark.usefultoolsmod.UsefultoolsMod;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModSounds {
    private static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, UsefultoolsMod.MOD_ID);
    private static final RegistryObject<SoundEvent> GHOST_AMBIENT = sound("entity.ghost.ambient");
    private static final RegistryObject<SoundEvent> GHOST_HURT = sound("entity.ghost.hurt");
    private static final RegistryObject<SoundEvent> GHOST_DEATH = sound("entity.ghost.death");
    private static final RegistryObject<SoundEvent> WRAITH_AMBIENT = sound("entity.wraith.ambient");
    private static final RegistryObject<SoundEvent> WRAITH_HURT = sound("entity.wraith.hurt");
    private static final RegistryObject<SoundEvent> WRAITH_DEATH = sound("entity.wraith.death");
    private ModSounds() {}
    private static RegistryObject<SoundEvent> sound(String path) { return SOUNDS.register(path,
            () -> SoundEvent.createVariableRangeEvent(Identifier.fromNamespaceAndPath(UsefultoolsMod.MOD_ID, path))); }
    public static void register(BusGroup bus) { SOUNDS.register(bus); }
    public static SoundEvent ghostAmbient() { return GHOST_AMBIENT.get(); }
    public static SoundEvent ghostHurt() { return GHOST_HURT.get(); }
    public static SoundEvent ghostDeath() { return GHOST_DEATH.get(); }
    public static SoundEvent wraithAmbient() { return WRAITH_AMBIENT.get(); }
    public static SoundEvent wraithHurt() { return WRAITH_HURT.get(); }
    public static SoundEvent wraithDeath() { return WRAITH_DEATH.get(); }
}
