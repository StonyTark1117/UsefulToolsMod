package com.stonytark.usefultoolsmod.spectralqa;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/** Private diagnostic for OptiFine classes loaded before its transformation service. */
public final class OptifinePatchBridge {
    private OptifinePatchBridge() { }

    public static void premain(String arguments, Instrumentation instrumentation) throws IOException {
        // Prism/QSettings treats semicolons in instance.cfg as comments.
        String[] paths = arguments.split("!", -1);
        if (paths.length != 2) throw new IllegalArgumentException("Expected patch archive and evidence path");
        final Map<String, byte[]> classes = new HashMap<String, byte[]>();
        try (ZipFile archive = new ZipFile(paths[0])) {
            Enumeration<? extends ZipEntry> entries = archive.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                String name = entry.getName();
                if (!name.startsWith("srg/") || !name.endsWith(".class")) continue;
                String type = name.substring(4, name.length() - 6);
                if (!selected(type)) continue;
                try (InputStream input = archive.getInputStream(entry)) {
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    byte[] buffer = new byte[8192];
                    int count;
                    while ((count = input.read(buffer)) != -1) bytes.write(buffer, 0, count);
                    classes.put(type, bytes.toByteArray());
                }
            }
        }
        if (!classes.containsKey("com/mojang/blaze3d/opengl/GlStateManager")
                || !classes.containsKey("net/minecraft/resources/Identifier")) {
            throw new IllegalArgumentException("Exact OptiFine patch classes absent");
        }
        final FileOutputStream evidence = new FileOutputStream(paths[1]);
        instrumentation.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String name, Class<?> redefining,
                                    ProtectionDomain domain, byte[] original) {
                byte[] replacement = classes.get(name);
                if (replacement == null) return null;
                synchronized (evidence) {
                    try {
                        evidence.write(("OPTIFINE_PATCH_BRIDGE " + name + " bytes="
                                + replacement.length + "\n").getBytes("UTF-8"));
                        evidence.flush();
                    } catch (IOException error) {
                        throw new IllegalStateException("Cannot retain bridge evidence", error);
                    }
                }
                return replacement;
            }
        }, false);
    }

    private static boolean selected(String name) {
        return name.equals("com/mojang/blaze3d/opengl/GlStateManager")
                || name.startsWith("com/mojang/blaze3d/opengl/GlStateManager$")
                || name.equals("net/minecraft/resources/Identifier")
                || name.startsWith("net/minecraft/resources/Identifier$");
    }
}
