package com.stonytark.usefultoolsmod.spectralqa;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

/** Restore the pinned Forge key-modifier persistence and loading broadcast guard. */
public final class OptifineNativeOptionsHooks {
    private OptifineNativeOptionsHooks() { }

    public static void main(String[] arguments) throws Exception {
        ClassNode publisher = new ClassNode();
        ClassNode nativeType = new ClassNode();
        new ClassReader(Files.readAllBytes(Paths.get(arguments[0]))).accept(publisher, 0);
        new ClassReader(Files.readAllBytes(Paths.get(arguments[1]))).accept(nativeType, 0);
        if (!publisher.name.equals("net/minecraft/client/Options")
                || !publisher.name.equals(nativeType.name)) {
            throw new IllegalArgumentException("Exact native Options classes required");
        }
        Set<String> signatures = new HashSet<>();
        signatures.add("broadcastOptions()V");
        signatures.add("processOptionsKeysOnly(Lnet/minecraft/client/Options$FieldAccess;)V");
        for (int index = 0; index < publisher.methods.size(); index++) {
            MethodNode current = publisher.methods.get(index);
            String signature = current.name + current.desc;
            if (!signatures.contains(signature)) continue;
            // Do not silently remove a publisher integration from an unfamiliar version.
            for (AbstractInsnNode instruction : current.instructions) {
                String owner = instruction instanceof MethodInsnNode
                        ? ((MethodInsnNode) instruction).owner
                        : instruction instanceof FieldInsnNode
                        ? ((FieldInsnNode) instruction).owner : "";
                // The inspected publisher key loop uses only its optional Forge
                // reflection adapters. The exact native loop persists the same
                // keyMappings array, including publisher-added keys, with direct
                // pinned KeyModifier calls. No shader/options logic lives here.
                boolean keyReflection = current.name.equals("processOptionsKeysOnly")
                        && (owner.equals("net/optifine/reflect/Reflector")
                        || owner.equals("net/optifine/reflect/ReflectorMethod")
                        || owner.equals("net/optifine/reflect/ReflectorField"));
                boolean broadcastReflection = current.name.equals("broadcastOptions")
                        && (owner.equals("net/optifine/reflect/Reflector")
                        || owner.equals("net/optifine/reflect/ReflectorMethod"));
                if (owner.startsWith("net/optifine/") && !keyReflection && !broadcastReflection) {
                    throw new IllegalStateException("Publisher logic requires a merge: " + signature);
                }
                if (broadcastReflection && instruction instanceof FieldInsnNode
                        && !((FieldInsnNode) instruction).name.equals("ClientModLoader_isLoading")) {
                    throw new IllegalStateException("Uninspected publisher broadcast adapter");
                }
                if (keyReflection && instruction instanceof FieldInsnNode) {
                    String field = ((FieldInsnNode) instruction).name;
                    if (!field.equals("ForgeKeyBinding_getKeyModifier")
                            && !field.equals("KeyModifier_NONE")
                            && !field.equals("KeyModifier_valueFromString")
                            && !field.equals("ForgeKeyBinding_setKeyModifierAndCode")) {
                        throw new IllegalStateException("Uninspected publisher key adapter: " + field);
                    }
                }
            }
            MethodNode replacement = null;
            for (MethodNode candidate : nativeType.methods) {
                if ((candidate.name + candidate.desc).equals(signature)) replacement = candidate;
            }
            if (replacement == null) throw new IllegalStateException("Native signature missing: " + signature);
            publisher.methods.set(index, replacement);
            signatures.remove(signature);
        }
        if (!signatures.isEmpty()) throw new IllegalStateException("Publisher signatures missing: " + signatures);
        ClassWriter writer = new ClassWriter(0);
        publisher.accept(writer);
        Files.write(Paths.get(arguments[2]), writer.toByteArray());
    }
}
