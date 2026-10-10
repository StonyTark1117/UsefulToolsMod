package com.stonytark.usefultoolsmod.spectralqa;

import java.lang.instrument.Instrumentation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

/** Optional isolated QA input transport. Native server feedback remains authoritative. */
public final class SpectralCommandController {
    private SpectralCommandController() {}

    static Method method(Class<?> type, Class<?>[] parameters, String... names) throws Exception {
        for (String name : names) {
            try { return type.getMethod(name, parameters); }
            catch (NoSuchMethodException absent) { /* Exact named, SRG or intermediary API. */ }
        }
        throw new NoSuchMethodException(type.getName() + "." + names[0]);
    }

    static Object field(Object object, String... names) throws Exception {
        for (Class<?> type = object.getClass(); type != null; type = type.getSuperclass()) {
            for (String name : names) {
                try {
                    Field value = type.getDeclaredField(name);
                    value.setAccessible(true);
                    return value.get(object);
                } catch (NoSuchFieldException absent) { /* Try exact runtime mapping. */ }
            }
        }
        throw new NoSuchFieldException(names[0]);
    }

    private static void acknowledgement(String output, String value) throws Exception {
        Path temporary = Paths.get(output + ".command-ack.tmp");
        Files.write(temporary, value.getBytes("UTF-8"));
        Files.move(temporary, Paths.get(output + ".command-ack"), StandardCopyOption.REPLACE_EXISTING);
    }

    public static void start(final String output, final Instrumentation instrumentation) {
        Thread controller = new Thread(new Runnable() {
            @Override public void run() {
                String previous = "";
                while (true) {
                    try {
                        Path request = Paths.get(output + ".command");
                        if (!Files.exists(Paths.get(output + ".ready")) || !Files.exists(request)) {
                            Thread.sleep(50); continue;
                        }
                        List<String> lines = Files.readAllLines(request);
                        if (lines.size() != 2 || !lines.get(0).matches("[a-f0-9]{32}")
                                || lines.get(1).isEmpty() || lines.get(1).startsWith("/")) {
                            throw new IllegalArgumentException("Malformed isolated command request");
                        }
                        final String token = lines.get(0);
                        final String command = lines.get(1);
                        if (previous.equals(token)) { Thread.sleep(50); continue; }
                        previous = token;
                        Class<?> minecraft = null;
                        for (Class<?> type : instrumentation.getAllLoadedClasses()) {
                            if (type.getName().equals("net.minecraft.client.Minecraft")
                                    || type.getName().equals("net.minecraft.class_310")) {
                                minecraft = type; break;
                            }
                        }
                        if (minecraft == null) throw new IllegalStateException("Native client not loaded");
                        final Object client = method(minecraft, new Class<?>[0],
                                "getInstance", "m_91087_", "method_1551").invoke(null);
                        method(minecraft, new Class<?>[] {Runnable.class}, "execute").invoke(client, new Runnable() {
                            @Override public void run() {
                                try {
                                    Object player = field(client, "player", "f_91074_", "field_1724");
                                    if (player == null) throw new IllegalStateException("Native player absent");
                                    Object connection = field(player, "connection", "f_108617_", "field_3944");
                                    Method send = method(connection.getClass(), new Class<?>[] {String.class},
                                            "sendCommand", "m_246623_", "method_45730");
                                    // Fabric's boolean sendCommand is a different API; select the
                                    // version-matched void command transport in every mapping.
                                    if (send.getReturnType() != Void.TYPE) {
                                        send = method(connection.getClass(), new Class<?>[] {String.class}, "method_45730");
                                    }
                                    send.invoke(connection, command);
                                    acknowledgement(output, token + "\nSENT\n" + send.toString() + "\n");
                                } catch (Exception error) {
                                    try { acknowledgement(output, token + "\nFAILED\n" + error + "\n"); }
                                    catch (Exception ignored) { /* Harness also detects acknowledgement timeout. */ }
                                }
                            }
                        });
                    } catch (InterruptedException stopped) {
                        return;
                    } catch (Exception error) {
                        try { acknowledgement(output, previous + "\nFAILED\n" + error + "\n"); }
                        catch (Exception ignored) { /* Fail closed at the harness deadline. */ }
                        try { Thread.sleep(1000); } catch (InterruptedException stopped) { return; }
                    }
                }
            }
        }, "spectral-qa-native-command-controller");
        controller.setDaemon(true);
        controller.start();
    }
}
