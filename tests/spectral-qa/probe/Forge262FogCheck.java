package com.stonytark.usefultoolsmod.spectralqa;

import java.nio.file.Path;
import java.util.zip.ZipFile;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.analysis.Analyzer;
import org.objectweb.asm.tree.analysis.BasicVerifier;

/** Verify private K2 fog additions against the exact pinned Forge 65 classes. */
public final class Forge262FogCheck {
    private Forge262FogCheck() { }

    private static byte[] entry(Path jar, String name) throws Exception {
        try (ZipFile archive = new ZipFile(jar.toFile())) {
            var found = archive.getEntry(name);
            if (found == null) throw new IllegalStateException("Exact class absent: " + name);
            try (var input = archive.getInputStream(found)) { return input.readAllBytes(); }
        }
    }

    private static ClassNode read(byte[] bytes) {
        ClassNode node = new ClassNode();
        new ClassReader(bytes).accept(node, 0);
        return node;
    }

    private static MethodNode method(ClassNode type, String name) {
        return type.methods.stream().filter(m -> m.name.equals(name)).findFirst().orElseThrow();
    }

    private static MethodNode method(ClassNode type, String name, String descriptor) {
        return type.methods.stream().filter(m -> m.name.equals(name) && m.desc.equals(descriptor))
                .findFirst().orElseThrow();
    }

    private static boolean calls(MethodNode method, String owner, String name) {
        for (AbstractInsnNode instruction : method.instructions)
            if (instruction instanceof MethodInsnNode call
                    && call.owner.equals(owner) && call.name.equals(name)) return true;
        return false;
    }

    private static boolean calls(MethodNode method, String owner, String name, String descriptor) {
        for (AbstractInsnNode instruction : method.instructions)
            if (instruction instanceof MethodInsnNode call
                    && call.owner.equals(owner) && call.name.equals(name)
                    && call.desc.equals(descriptor)) return true;
        return false;
    }

    private static boolean writes(MethodNode method, String name) {
        for (AbstractInsnNode instruction : method.instructions)
            if (instruction instanceof FieldInsnNode field
                    && field.getOpcode() == org.objectweb.asm.Opcodes.PUTFIELD
                    && field.name.equals(name)) return true;
        return false;
    }

    private static ClassNode graft(String helper, byte[] nativeBytes, byte[] publisherBytes) throws Exception {
        var adapter = OptifineGuiBridge.class.getDeclaredMethod(helper, byte[].class, byte[].class);
        adapter.setAccessible(true);
        ClassNode type = read((byte[]) adapter.invoke(null, nativeBytes, publisherBytes));
        for (MethodNode method : type.methods)
            if (method.instructions.size() > 0)
                new Analyzer<>(new BasicVerifier()).analyze(type.name, method);
        return type;
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 2) throw new IllegalArgumentException("forge-65-client patched-publisher");
        Path nativeJar = Path.of(args[0]), publisherJar = Path.of(args[1]);
        String buffer = "com/mojang/blaze3d/buffers/GpuBuffer";
        String slice = "com/mojang/blaze3d/buffers/GpuBufferSlice";
        String fog = "net/minecraft/client/renderer/fog/FogRenderer";
        ClassNode bufferType = graft("nativeGpuBuffer", entry(nativeJar, buffer + ".class"),
                entry(publisherJar, "srg/" + buffer + ".class"));
        if (!calls(method(bufferType, "slice", "(JJLjava/lang/Object;)Lcom/mojang/blaze3d/buffers/GpuBufferSlice;"),
                slice, "setData"))
            throw new IllegalStateException("Native buffer slice path absent");
        ClassNode sliceType = graft("nativeGpuBufferSlice", entry(nativeJar, slice + ".class"),
                entry(publisherJar, "srg/" + slice + ".class"));
        if (!writes(method(sliceType, "<init>", "(Lcom/mojang/blaze3d/buffers/GpuBuffer;JJ)V"), "mutableData")
                || !calls(method(sliceType, "getData"), "net/optifine/util/Mutable", "get"))
            throw new IllegalStateException("Slice fog metadata storage absent");
        ClassNode fogType = graft("nativeFogRenderer", entry(nativeJar, fog + ".class"),
                entry(publisherJar, "srg/" + fog + ".class"));
        if (!calls(method(fogType, "setShadersFog"), slice, "getData")
                || !calls(method(fogType, "setShadersFog"), "net/optifine/shaders/Shaders", "setFogParameters")
                || !calls(method(fogType, "getBuffer"), buffer, "slice",
                        "(JJLjava/lang/Object;)Lcom/mojang/blaze3d/buffers/GpuBufferSlice;")
                || !writes(method(fogType, "setupFog"), "lastFogParams"))
            throw new IllegalStateException("Shader fog handoff incomplete");
        System.out.println("Verified pinned Forge 65 fog slice metadata and K2 shader handoff; all method stacks valid.");
    }
}
