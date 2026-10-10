package com.stonytark.usefultoolsmod.spectralqa;

import java.lang.instrument.Instrumentation;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/** Read-only client-thread observation of the actual synchronized damage flash. */
public final class SpectralHurtObserver {
    private SpectralHurtObserver() { }

    public static void start(final String output, final Instrumentation instrumentation) {
        final AtomicBoolean pending = new AtomicBoolean();
        Thread observer = new Thread(new Runnable() {
            @Override public void run() {
                while (true) {
                    try {
                        Path request = Paths.get(output + ".hurt-request");
                        if (!Files.exists(Paths.get(output + ".ready")) || !Files.exists(request)
                                || !pending.compareAndSet(false, true)) {
                            Thread.sleep(20); continue;
                        }
                        List<String> lines = Files.readAllLines(request);
                        if (lines.size() != 2 || !lines.get(0).matches("[a-f0-9]{32}")
                                || !lines.get(1).matches("ghost|wraith")) {
                            throw new IllegalArgumentException("Malformed hurt observation request");
                        }
                        final String token = lines.get(0);
                        final String entityName = lines.get(1).equals("ghost") ? "GhostEntity" : "WraithEntity";
                        Class<?> minecraft = null;
                        for (Class<?> type : instrumentation.getAllLoadedClasses()) {
                            if (type.getName().equals("net.minecraft.client.Minecraft")
                                    || type.getName().equals("net.minecraft.class_310")) {
                                minecraft = type; break;
                            }
                        }
                        if (minecraft == null) throw new IllegalStateException("Client not loaded");
                        final Object client = SpectralCommandController.method(minecraft, new Class<?>[0],
                                "getInstance", "m_91087_", "method_1551").invoke(null);
                        SpectralCommandController.method(minecraft, new Class<?>[] {Runnable.class},
                                "execute").invoke(client, new Runnable() {
                            @Override public void run() {
                                try {
                                    Object level = SpectralCommandController.field(client,
                                            "level", "f_91073_", "field_1687");
                                    Iterable<?> entities = (Iterable<?>) SpectralCommandController.method(
                                            level.getClass(), new Class<?>[0], "entitiesForRendering",
                                            "m_104735_", "method_18112").invoke(level);
                                    for (Object entity : entities) {
                                        if (!entity.getClass().getSimpleName().equals(entityName)) continue;
                                        int hurt = ((Number) SpectralCommandController.field(entity,
                                                "hurtTime", "f_20916_", "field_6235")).intValue();
                                        // Leave enough native ticks for input and the next rendered frame.
                                        if (hurt < 5) continue;
                                        write(output, token + "\nHURT\n" + System.currentTimeMillis()
                                                + "\n" + hurt + "\n" + entity.getClass().getName() + "\n");
                                    }
                                } catch (Exception error) {
                                    try { write(output, token + "\nFAILED\n" + error + "\n"); }
                                    catch (Exception ignored) { /* Harness fails closed on missing proof. */ }
                                } finally {
                                    pending.set(false);
                                }
                            }
                        });
                        Thread.sleep(20);
                    } catch (InterruptedException stopped) {
                        return;
                    } catch (Exception error) {
                        pending.set(false);
                        try { Thread.sleep(1000); } catch (InterruptedException stopped) { return; }
                    }
                }
            }
        }, "spectral-qa-native-hurt-observer");
        observer.setDaemon(true);
        observer.start();
    }

    private static void write(String output, String value) throws Exception {
        Path temporary = Paths.get(output + ".hurt-ack.tmp");
        Files.write(temporary, value.getBytes("UTF-8"));
        Files.move(temporary, Paths.get(output + ".hurt-ack"), StandardCopyOption.REPLACE_EXISTING);
    }
}
