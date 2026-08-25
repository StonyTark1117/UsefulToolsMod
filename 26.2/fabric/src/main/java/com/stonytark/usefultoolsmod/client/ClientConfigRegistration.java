package com.stonytark.usefultoolsmod.client;

/**
 * Fabric config-screen registration shim.
 *
 * <p>On NeoForge the sibling port wires {@code IConfigScreenFactory} via
 * {@code container.registerExtensionPoint(...)}. Fabric has no equivalent extension point;
 * surfacing a per-mod settings screen on Fabric requires <em>Mod Menu</em>, which is not a
 * hard dependency of this mod. When Mod Menu is installed, a downstream entrypoint can
 * point at {@link UsefulToolsConfigScreen#build} to render the built-in vanilla-widget UI; without
 * Mod Menu, users edit {@code config/usefultoolsmod.properties} directly.
 *
 * <p>This file is kept as a no-op placeholder so the call site in {@code UsefultoolsMod}
 * (and any future {@code ClientModInitializer} hook) doesn't need conditional imports.
 */
public final class ClientConfigRegistration {
    private ClientConfigRegistration() {}

    /** No-op because Mod Menu owns the optional Fabric screen route. */
    public static void register() {
        // intentionally empty
    }
}
