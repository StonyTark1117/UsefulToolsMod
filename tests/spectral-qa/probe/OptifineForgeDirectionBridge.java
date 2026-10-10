package com.stonytark.usefultoolsmod.spectralqa;

import java.io.IOException;
import java.io.InputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.ProtectionDomain;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.jar.JarFile;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodNode;

/** Private exact-Forge donor experiment; leaves the official OptiFine archive unchanged. */
public final class OptifineForgeDirectionBridge {
    private OptifineForgeDirectionBridge() { }

    public static void premain(String argument, Instrumentation instrumentation) throws IOException {
        final String[] paths = argument.split("!", -1);
        if (paths.length != 2) throw new IllegalArgumentException("Expected pinned native donor and evidence path");
        final ClassNode donor = new ClassNode();
        try (JarFile archive = new JarFile(paths[0]);
             InputStream input = archive.getInputStream(archive.getJarEntry("net/minecraft/core/Direction.class"))) {
            new ClassReader(input).accept(donor, 0);
        }
        if (!donor.name.equals("net/minecraft/core/Direction")) throw new IllegalArgumentException("Wrong native donor");
        instrumentation.addTransformer(new ClassFileTransformer() {
            @Override public byte[] transform(ClassLoader loader, String name, Class<?> redefining,
                                               ProtectionDomain domain, byte[] original) {
                if (!name.equals(donor.name)) return null;
                ClassNode target = new ClassNode();
                new ClassReader(original).accept(target, 0);
                for (MethodNode method : target.methods) {
                    if (method.name.equals("getUpdateOrder") && method.desc.equals("()Ljava/util/List;")) return null;
                }
                for (FieldNode field : target.fields) {
                    if (field.name.equals("UPDATE_ORDER")) throw new IllegalStateException("Partial hook restoration requires investigation");
                }
                FieldNode nativeField = null;
                MethodNode nativeGetter = null;
                MethodNode nativeInitializer = null;
                MethodNode targetInitializer = null;
                for (FieldNode field : donor.fields) if (field.name.equals("UPDATE_ORDER")) nativeField = field;
                for (MethodNode method : donor.methods) {
                    if (method.name.equals("getUpdateOrder")) nativeGetter = method;
                    if (method.name.equals("<clinit>")) nativeInitializer = method;
                }
                for (MethodNode method : target.methods) if (method.name.equals("<clinit>")) targetInitializer = method;
                if (nativeField == null || nativeGetter == null || nativeInitializer == null || targetInitializer == null) {
                    throw new IllegalStateException("Pinned native Direction hook donor incomplete");
                }
                target.fields.add(new FieldNode(nativeField.access, nativeField.name, nativeField.desc, nativeField.signature, nativeField.value));
                MethodNode getter = new MethodNode(nativeGetter.access, nativeGetter.name, nativeGetter.desc, nativeGetter.signature, null);
                nativeGetter.accept(getter);
                target.methods.add(getter);
                InsnList initializer = new InsnList();
                Map<LabelNode, LabelNode> labels = new IdentityHashMap<LabelNode, LabelNode>();
                boolean copying = false;
                boolean complete = false;
                for (AbstractInsnNode instruction : nativeInitializer.instructions) {
                    if (instruction instanceof FieldInsnNode) {
                        FieldInsnNode field = (FieldInsnNode) instruction;
                        if (field.getOpcode() == Opcodes.GETSTATIC && field.owner.equals(donor.name + "$Plane")
                                && field.name.equals("HORIZONTAL")) copying = true;
                    }
                    if (copying && instruction.getOpcode() >= 0) initializer.add(instruction.clone(labels));
                    if (copying && instruction instanceof FieldInsnNode) {
                        FieldInsnNode field = (FieldInsnNode) instruction;
                        if (field.getOpcode() == Opcodes.PUTSTATIC && field.owner.equals(donor.name)
                                && field.name.equals("UPDATE_ORDER")) { complete = true; break; }
                    }
                }
                AbstractInsnNode end = targetInitializer.instructions.getLast();
                while (end != null && end.getOpcode() != Opcodes.RETURN) end = end.getPrevious();
                if (!complete || end == null) throw new IllegalStateException("Native hook initializer segment absent");
                int initializerInstructions = initializer.size();
                targetInitializer.instructions.insertBefore(end, initializer);
                ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
                target.accept(writer);
                try {
                    Files.write(Paths.get(paths[1]), ("RESTORED_PINNED_FORGE_DIRECTION_UPDATE_ORDER donor="
                            + paths[0] + " native_initializer_instructions=" + initializerInstructions + "\n").getBytes("UTF-8"));
                } catch (IOException error) { throw new IllegalStateException("Cannot save donor evidence", error); }
                return writer.toByteArray();
            }
        }, false);
    }
}
