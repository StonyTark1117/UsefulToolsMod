package com.stonytark.usefultoolsmod.spectralqa;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.zip.ZipFile;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/** Offline direct-declaration audit; inherited methods still require separate inspection. */
public final class Forge262ShaderReferenceDiff {
    private Forge262ShaderReferenceDiff() { }

    private static Set<String> declared(ZipFile archive, String entry) throws Exception {
        var member = archive.getEntry(entry);
        if (member == null) return null;
        Set<String> methods = new HashSet<>();
        try (var stream = archive.getInputStream(member)) {
            new ClassReader(stream.readAllBytes()).accept(new ClassVisitor(Opcodes.ASM9) {
                @Override public MethodVisitor visitMethod(int access, String name, String descriptor,
                        String signature, String[] exceptions) {
                    methods.add(name + descriptor);
                    return null;
                }
            }, ClassReader.SKIP_CODE | ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);
        }
        return methods;
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 2) throw new IllegalArgumentException("pinned-client.jar publisher-patched.jar");
        try (ZipFile nativeArchive = new ZipFile(Path.of(args[0]).toFile());
                ZipFile publisher = new ZipFile(Path.of(args[1]).toFile())) {
            Map<String, Set<String>> nativeMethods = new HashMap<>();
            Map<String, Set<String>> publisherMethods = new HashMap<>();
            Set<String> missing = new TreeSet<>();
            var entries = publisher.entries();
            while (entries.hasMoreElements()) {
                var entry = entries.nextElement();
                String path = entry.getName();
                if (!path.startsWith("srg/net/optifine/shaders/") || !path.endsWith(".class")) continue;
                try (var stream = publisher.getInputStream(entry)) {
                    var reader = new ClassReader(stream.readAllBytes());
                    reader.accept(new ClassVisitor(Opcodes.ASM9) {
                        @Override public MethodVisitor visitMethod(int access, String name, String descriptor,
                                String signature, String[] exceptions) {
                            String caller = reader.getClassName() + "." + name + descriptor;
                            return new MethodVisitor(Opcodes.ASM9) {
                                @Override public void visitMethodInsn(int opcode, String owner,
                                        String method, String desc, boolean isInterface) {
                                    if (!owner.startsWith("net/minecraft/")
                                            && !owner.startsWith("com/mojang/blaze3d/")) return;
                                    try {
                                        Set<String> methods = nativeMethods.get(owner);
                                        if (methods == null && !nativeMethods.containsKey(owner)) {
                                            methods = declared(nativeArchive, owner + ".class");
                                            nativeMethods.put(owner, methods);
                                        }
                                        if (methods == null || methods.contains(method + desc)) return;
                                        Set<String> donor = publisherMethods.get(owner);
                                        if (donor == null && !publisherMethods.containsKey(owner)) {
                                            donor = declared(publisher, "srg/" + owner + ".class");
                                            publisherMethods.put(owner, donor);
                                        }
                                        missing.add(owner + "\t" + method + "\t" + desc + "\t"
                                                + (donor != null && donor.contains(method + desc)) + "\t" + caller);
                                    } catch (Exception error) {
                                        throw new IllegalStateException("Cannot inspect " + owner, error);
                                    }
                                }
                            };
                        }
                    }, ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);
                }
            }
            for (String line : missing) System.out.println(line);
        }
    }
}
