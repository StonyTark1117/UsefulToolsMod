package com.stonytark.usefultoolsmod.sound;

import com.stonytark.usefultoolsmod.UsefultoolsMod;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;

public final class ModSounds {
    private static final SoundEvent GHOST_AMBIENT = sound("entity.ghost.ambient");
    private static final SoundEvent GHOST_HURT = sound("entity.ghost.hurt");
    private static final SoundEvent GHOST_DEATH = sound("entity.ghost.death");
    private static final SoundEvent WRAITH_AMBIENT = sound("entity.wraith.ambient");
    private static final SoundEvent WRAITH_HURT = sound("entity.wraith.hurt");
    private static final SoundEvent WRAITH_DEATH = sound("entity.wraith.death");
    private ModSounds() {}
    private static SoundEvent sound(String path) {
        Identifier id = Identifier.fromNamespaceAndPath(UsefultoolsMod.MOD_ID, path);
        return Registry.register(BuiltInRegistries.SOUND_EVENT, id, SoundEvent.createVariableRangeEvent(id));
    }
    public static void register() {}
    public static SoundEvent ghostAmbient() { return GHOST_AMBIENT; }
    public static SoundEvent ghostHurt() { return GHOST_HURT; }
    public static SoundEvent ghostDeath() { return GHOST_DEATH; }
    public static SoundEvent wraithAmbient() { return WRAITH_AMBIENT; }
    public static SoundEvent wraithHurt() { return WRAITH_HURT; }
    public static SoundEvent wraithDeath() { return WRAITH_DEATH; }
}
