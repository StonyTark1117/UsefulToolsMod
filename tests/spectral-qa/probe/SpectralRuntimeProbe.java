package com.stonytark.usefultoolsmod.spectralqa;

import java.io.PrintWriter;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.List;

/** Read-only live state observer, compatible with the classic Java 8 runtime. */
public final class SpectralRuntimeProbe {
    private SpectralRuntimeProbe() {}

    public static void premain(final String output, final Instrumentation instrumentation) throws Exception {
        final PrintWriter log = new PrintWriter(output, "UTF-8");
        Thread observer = new Thread(new Runnable() {
            @Override public void run() {
                Class<?> helper = null;
                Method active = null;
                Method apiGetter = null;
                Field directPackLoaded = null;
                Field floor = null;
                Class<?> minecraft = null;
                boolean classicVersionReported = false;
                boolean classic = false;
                while (true) {
                    try {
                        if (helper == null || (classic && (minecraft == null || !classicVersionReported))) {
                            Class<?>[] loaded = instrumentation.getAllLoadedClasses();
                            Class<?> iris = null;
                            for (Class<?> type : loaded) {
                                if (type.getName().equals("cpw.mods.fml.common.FMLCommonHandler")) classic = true;
                                if (type.getName().equals("net.irisshaders.iris.Iris") || type.getName().equals("net.coderbot.iris.Iris")) iris = type;
                                if (Files.exists(Paths.get(output + ".ready")) && (type.getName().equals("shadersmod.client.Shaders") || type.getName().equals("net.optifine.shaders.Shaders"))) {
                                    directPackLoaded = type.getField("shaderPackLoaded");
                                }
                            }
                            for (Class<?> type : loaded) {
                                // Reflecting on modern Minecraft before loader setup can resolve
                                // event types before their transformers are ready. Classic only.
                                if (classic && type.getName().equals("net.minecraft.client.Minecraft")) minecraft = type;
                                if (classic && !classicVersionReported && type.getName().equals("net.minecraftforge.common.ForgeVersion")) {
                                    log.println("SPECTRAL_LOADER forge_version=" + type.getMethod("getVersion").invoke(null));
                                    log.flush(); classicVersionReported = true;
                                }
                                if (type.getName().equals("com.stonytark.usefultoolsmod.entity.client.SpectralShaderSupport")
                                        || type.getName().equals("com.stonytark.usefultoolsmod.classic.client.SpectralShaderSupport")) {
                                    helper = type;
                                    active = type.getDeclaredMethod(type.getName().contains("classic")
                                            ? "isShaderPackLoaded" : "isShaderPackInUse");
                                    active.setAccessible(true);
                                    apiGetter = null;
                                    floor = type.getDeclaredField("WRAITH_LIGHT_FLOOR");
                                    floor.setAccessible(true);
                                }
                            }
                            // Some native Fabric render paths never load the
                            // entity helper. Observe Iris itself for lighting QA.
                            if (helper == null && active == null) for (Class<?> type : loaded) {
                                if (type.getName().equals("net.irisshaders.iris.api.v0.IrisApi")
                                        || type.getName().equals("net.coderbot.iris.api.v0.IrisApi")) {
                                    apiGetter = type.getMethod("getInstance");
                                    active = type.getMethod("isShaderPackInUse");
                                }
                            }
                            if (helper == null && active == null && iris != null && Files.exists(Paths.get(output + ".ready"))) {
                                // World join establishes loader readiness. The
                                // unused entity helper need not initialize Iris.
                                String prefix = iris.getName().substring(0, iris.getName().lastIndexOf('.'));
                                Class<?> api = iris.getClassLoader().loadClass(prefix + ".api.v0.IrisApi");
                                apiGetter = api.getMethod("getInstance");
                                active = api.getMethod("isShaderPackInUse");
                            }
                        }
                        if (minecraft != null) writeClassicMenu(minecraft, output + ".menu");
                        if (Boolean.getBoolean("usefulToolsSpectralMenuProbe") && Files.exists(Paths.get(output + ".ready"))) {
                            for (Class<?> type : instrumentation.getAllLoadedClasses()) {
                                if (type.getName().equals("net.minecraft.client.Minecraft")) {
                                    writeModernMenu(type, output + ".menu-slots"); break;
                                }
                            }
                        }
                        // Observing a merely loaded helper must not initialize its optional
                        // shader classes during mod-loader setup. The harness opens this gate
                        // only after an acknowledged integrated-world join.
                        if (active != null && Files.exists(Paths.get(output + ".ready"))) {
                            log.println("SPECTRAL_PROBE time_ms=" + System.currentTimeMillis()
                                    + " active=" + active.invoke(apiGetter == null ? null : apiGetter.invoke(null))
                                    + " wraith_floor=" + (floor == null ? "unavailable" : String.valueOf(floor.getInt(null))));
                            log.flush();
                        } else if (directPackLoaded != null && Files.exists(Paths.get(output + ".ready"))) {
                            log.println("SPECTRAL_PROBE time_ms=" + System.currentTimeMillis()
                                    + " active=" + directPackLoaded.getBoolean(null) + " wraith_floor=unavailable");
                            log.flush();
                        }
                        Thread.sleep(500);
                    } catch (InterruptedException interrupted) {
                        log.close(); return;
                    } catch (Exception error) {
                        log.println("SPECTRAL_PROBE_ERROR " + error.getClass().getName());
                        log.flush();
                        try { Thread.sleep(1000); } catch (InterruptedException interrupted) { log.close(); return; }
                    }
                }
            }
        }, "spectral-qa-state-observer");
        observer.setDaemon(true);
        observer.start();
        if (Boolean.getBoolean("usefulToolsSpectralCommands")) {
            SpectralCommandController.start(output, instrumentation);
        }
        if (Boolean.getBoolean("usefulToolsSpectralHurtProbe")) {
            SpectralHurtObserver.start(output, instrumentation);
        }
    }

    private static Object field(Object instance, String named, String srg) throws Exception {
        Class<?> type = instance.getClass();
        while (type != null) {
            for (String name : new String[] {named, srg}) {
                try {
                    Field result = type.getDeclaredField(name);
                    result.setAccessible(true);
                    return result.get(instance);
                } catch (NoSuchFieldException absent) { /* Try the actual runtime mapping. */ }
            }
            type = type.getSuperclass();
        }
        throw new NoSuchFieldException(named);
    }

    /** Inspect populated client slots on the native client thread after world entry. */
    private static void writeModernMenu(Class<?> minecraft, final String path) throws Exception {
        final Object client = minecraft.getMethod("getInstance").invoke(null);
        minecraft.getMethod("execute", Runnable.class).invoke(client, new Runnable() {
            @Override public void run() {
                try {
                    Object player = field(client, "player", "player");
                    if (player == null) return;
                    Object menu = field(player, "containerMenu", "containerMenu");
                    if (!menu.getClass().getName().endsWith("SpectralInfuserMenu")) return;
                    StringBuilder text = new StringBuilder().append(System.currentTimeMillis()).append(' ')
                            .append(menu.getClass().getName()).append('\n');
                    List<?> slots = (List<?>) field(menu, "slots", "slots");
                    for (int index = 0; index < slots.size(); index++) {
                        Object slot = slots.get(index);
                        Method getter = slot.getClass().getMethod("getItem");
                        getter.setAccessible(true);
                        text.append(index).append(' ').append(getter.invoke(slot)).append('\n');
                    }
                    Files.write(Paths.get(path), text.toString().getBytes("UTF-8"));
                    final Object server = client.getClass().getMethod("getSingleplayerServer").invoke(client);
                    if (server != null) server.getClass().getMethod("execute", Runnable.class).invoke(server, new Runnable() {
                        @Override public void run() {
                            try {
                                Object playerList = server.getClass().getMethod("getPlayerList").invoke(server);
                                Method playersGetter = playerList.getClass().getMethod("getPlayers");
                                playersGetter.setAccessible(true);
                                StringBuilder state = new StringBuilder().append(System.currentTimeMillis()).append('\n');
                                for (Object serverPlayer : (List<?>) playersGetter.invoke(playerList)) {
                                    Object serverMenu = field(serverPlayer, "containerMenu", "containerMenu");
                                    state.append(serverMenu.getClass().getName()).append('\n');
                                    List<?> serverSlots = (List<?>) field(serverMenu, "slots", "slots");
                                    for (int i = 36; i < serverSlots.size(); i++) {
                                        Object slot = serverSlots.get(i);
                                        Method getItem = slot.getClass().getMethod("getItem");
                                        getItem.setAccessible(true);
                                        state.append(i).append(' ').append(getItem.invoke(slot)).append('\n');
                                    }
                                }
                                Files.write(Paths.get(path + ".server"), state.toString().getBytes("UTF-8"));
                            } catch (Exception error) {
                                try { Files.write(Paths.get(path + ".server"), ("SERVER_MENU_PROBE_ERROR " + error + "\n").getBytes("UTF-8")); }
                                catch (Exception ignored) { /* Diagnostic output is optional. */ }
                            }
                        }
                    });
                } catch (Exception error) {
                    try { Files.write(Paths.get(path), ("MENU_PROBE_ERROR " + error + "\n").getBytes("UTF-8")); }
                    catch (Exception ignored) { /* Main state observer retains launch errors. */ }
                }
            }
        });
    }

    /** Read native classic controls; never changes GUI state or performs input. */
    private static void writeClassicMenu(Class<?> minecraft, String path) throws Exception {
        Method getter;
        try { getter = minecraft.getDeclaredMethod("func_71410_x"); }
        catch (NoSuchMethodException modern) { return; }
        Object client = getter.invoke(null);
        Object screen = field(client, "currentScreen", "field_71462_r");
        StringBuilder text = new StringBuilder().append(System.currentTimeMillis()).append('\n');
        if (screen != null) {
            double scaleX = ((Number) field(client, "displayWidth", "field_71443_c")).doubleValue()
                    / ((Number) field(screen, "width", "field_146294_l")).doubleValue();
            double scaleY = ((Number) field(client, "displayHeight", "field_71440_d")).doubleValue()
                    / ((Number) field(screen, "height", "field_146295_m")).doubleValue();
            for (Object button : (List<?>) field(screen, "buttonList", "field_146292_n")) {
                if (!Boolean.TRUE.equals(field(button, "visible", "field_146125_m"))
                        || !Boolean.TRUE.equals(field(button, "enabled", "field_146124_l"))) continue;
                String label = String.valueOf(field(button, "displayString", "field_146126_j"));
                double x = ((Number) field(button, "xPosition", "field_146128_h")).doubleValue()
                        + ((Number) field(button, "width", "field_146120_f")).doubleValue() / 2;
                double y = ((Number) field(button, "yPosition", "field_146129_i")).doubleValue()
                        + ((Number) field(button, "height", "field_146121_g")).doubleValue() / 2;
                text.append(Base64.getEncoder().encodeToString(label.getBytes("UTF-8")))
                        .append('\t').append(x * scaleX).append('\t').append(y * scaleY).append('\n');
            }
            try {
                Class<?> snapshot = minecraft.getClassLoader().loadClass(
                        "com.stonytark.usefultools.lighting.qa.OptifineLightingQaMenuSnapshot");
                snapshot.getMethod("append", Object.class, double.class, double.class, StringBuilder.class)
                        .invoke(null, screen, scaleX, scaleY, text);
            } catch (ClassNotFoundException absentLightingFixture) {
                // Ordinary spectral profiles do not install the lighting QA package.
            }
        }
        Files.write(Paths.get(path + ".tmp"), text.toString().getBytes("UTF-8"));
        Files.move(Paths.get(path + ".tmp"), Paths.get(path), StandardCopyOption.REPLACE_EXISTING);
    }
}
