package com.stonytark.usefultoolsmod.sound;

import com.stonytark.usefultoolsmod.UsefultoolsMod;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public final class ModSounds {
    public static final SoundEvent GHOST_AMBIENT = sound("entity.ghost.ambient");
    public static final SoundEvent GHOST_HURT = sound("entity.ghost.hurt");
    public static final SoundEvent GHOST_DEATH = sound("entity.ghost.death");
    public static final SoundEvent WRAITH_AMBIENT = sound("entity.wraith.ambient");
    public static final SoundEvent WRAITH_HURT = sound("entity.wraith.hurt");
    public static final SoundEvent WRAITH_DEATH = sound("entity.wraith.death");
    private ModSounds() {}
    private static SoundEvent sound(String path) {
        Identifier id = Identifier.of(UsefultoolsMod.MOD_ID, path);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }
    public static void register() {}
}
