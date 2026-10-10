package com.stonytark.usefultoolsmod.spectralqa;

import java.nio.file.Files;
import java.nio.file.Paths;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

/** Preserve the exact pinned Forge transformation of all eight quad lists. */
public final class OptifineNativeQuadTransform {
    private OptifineNativeQuadTransform() { }

    public static void main(String[] arguments) throws Exception {
        ClassNode publisher = new ClassNode();
        ClassNode nativeType = new ClassNode();
        new ClassReader(Files.readAllBytes(Paths.get(arguments[0]))).accept(publisher, 0);
        new ClassReader(Files.readAllBytes(Paths.get(arguments[1]))).accept(nativeType, 0);
        String owner = "net/minecraft/client/resources/model/geometry/QuadCollection";
        String signature = "(Lnet/minecraftforge/client/model/IQuadTransformer;)L" + owner + ";";
        if (!publisher.name.equals(owner) || !nativeType.name.equals(owner)) {
            throw new IllegalArgumentException("Exact QuadCollection classes required");
        }
        MethodNode nativeTransform = null;
        boolean generatedSetter = false;
        for (MethodNode method : publisher.methods) {
            if (method.name.equals("transform") && method.desc.equals(signature)) {
                throw new IllegalStateException("Publisher already implements native transform; inspect it");
            }
            if (method.name.equals("setBuiltinGenerated") && method.desc.equals("(Z)V")) generatedSetter = true;
        }
        for (MethodNode method : nativeType.methods) {
            if (method.name.equals("transform") && method.desc.equals(signature)) nativeTransform = method;
        }
        if (nativeTransform == null || !generatedSetter) throw new IllegalStateException("Required native/publisher API absent");
        int processes = 0;
        int returns = 0;
        for (AbstractInsnNode instruction : nativeTransform.instructions.toArray()) {
            if (instruction instanceof FieldInsnNode) {
                FieldInsnNode field = (FieldInsnNode) instruction;
                if (field.owner.equals(owner)) {
                    boolean present = false;
                    for (FieldNode candidate : publisher.fields) {
                        if (candidate.name.equals(field.name) && candidate.desc.equals(field.desc)) present = true;
                    }
                    if (!present) throw new IllegalStateException("Native quad field absent: " + field.name);
                }
            }
            if (instruction instanceof MethodInsnNode) {
                MethodInsnNode call = (MethodInsnNode) instruction;
                if (call.owner.equals("net/minecraftforge/client/model/IQuadTransformer")
                        && call.name.equals("process") && call.desc.equals("(Ljava/util/List;)Ljava/util/List;")) processes++;
            }
            if (instruction.getOpcode() == Opcodes.ARETURN) {
                InsnList preserve = new InsnList();
                preserve.add(new InsnNode(Opcodes.DUP));
                preserve.add(new VarInsnNode(Opcodes.ALOAD, 0));
                preserve.add(new FieldInsnNode(Opcodes.GETFIELD, owner, "builtinGenerated", "Z"));
                preserve.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, owner, "setBuiltinGenerated", "(Z)V", false));
                nativeTransform.instructions.insertBefore(instruction, preserve);
                returns++;
            }
        }
        if (processes != 8 || returns != 1) throw new IllegalStateException("Native quad algorithm changed");
        publisher.methods.add(nativeTransform);
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        publisher.accept(writer);
        Files.write(Paths.get(arguments[2]), writer.toByteArray());
    }
}
