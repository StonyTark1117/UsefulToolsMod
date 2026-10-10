package com.stonytark.usefultoolsmod.spectralqa;

import java.nio.file.Files;
import java.nio.file.Paths;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

/** Private compatibility experiment for the publisher's obsolete interface. */
public final class OptifineVertexFixture {
    private OptifineVertexFixture() { }

    public static void main(String[] arguments) throws Exception {
        ClassNode type = new ClassNode();
        new ClassReader(Files.readAllBytes(Paths.get(arguments[0]))).accept(type, 0);
        if (!type.name.equals("com/mojang/blaze3d/vertex/VertexFormat")
                || !type.interfaces.remove("net/minecraftforge/client/extensions/IForgeVertexFormat")) {
            throw new IllegalStateException("Exact publisher interface absent");
        }
        // Forge 64.0.8 supplies these concrete hooks without an interface.
        boolean mapping = false;
        boolean offset = false;
        for (MethodNode method : type.methods) {
            if (method.name.equals("getElementMapping")
                    && method.desc.equals("()Lcom/google/common/collect/ImmutableMap;")) mapping = true;
            if (method.name.equals("getOffset") && method.desc.equals("(I)I")) offset = true;
        }
        if (!mapping || !offset) throw new IllegalStateException("Native Forge vertex hooks absent");
        ClassWriter writer = new ClassWriter(0);
        type.accept(writer);
        Files.write(Paths.get(arguments[1]), writer.toByteArray());
    }
}
