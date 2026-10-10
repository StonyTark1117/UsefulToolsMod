package com.stonytark.usefultoolsmod.spectralqa;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.ClassRemapper;
import org.objectweb.asm.commons.Remapper;

/** Build-only relocation: diagnostic agents must not export the loader's ASM package. */
public final class RelocateDiagnosticAsm {
    private RelocateDiagnosticAsm() { }

    public static void main(String[] arguments) throws IOException {
        Remapper remapper = new Remapper(Opcodes.ASM9) {
            @Override public String map(String name) {
                return name.startsWith("org/objectweb/asm/")
                        ? "com/stonytark/usefultoolsmod/spectralqa/privateasm/" + name.substring(18) : name;
            }
        };
        try (JarFile input = new JarFile(arguments[0]);
             JarOutputStream output = new JarOutputStream(Files.newOutputStream(Paths.get(arguments[1])), input.getManifest())) {
            Enumeration<JarEntry> entries = input.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                if (!entry.getName().endsWith(".class")) continue;
                ClassReader reader = new ClassReader(input.getInputStream(entry));
                ClassWriter writer = new ClassWriter(0);
                reader.accept(new ClassRemapper(writer, remapper), 0);
                output.putNextEntry(new JarEntry(remapper.map(reader.getClassName()) + ".class"));
                output.write(writer.toByteArray());
                output.closeEntry();
            }
        }
    }
}
