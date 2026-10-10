package com.stonytark.lightingqa.backend;

import java.nio.file.Files;
import java.nio.file.Paths;
import org.objectweb.asm.*;

/** Replace the two removed extension calls while retaining native frustum culling. */
public final class RepairEmbeddiumBounds {
    public static void main(String[] args) throws Exception {
        final String entity = "net/minecraft/world/level/block/entity/BlockEntity";
        final String dispatcher = "net/minecraft/client/renderer/blockentity/BlockEntityRenderDispatcher";
        final String box = "net/minecraft/world/phys/AABB";
        final String helper = "com/stonytark/lightingqa/backend/NativeBlockEntityBounds";
        final int[] replaced = {0};
        ClassReader reader = new ClassReader(Files.readAllBytes(Paths.get(args[0])));
        ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_MAXS);
        reader.accept(new ClassVisitor(Opcodes.ASM9, writer) {
            public MethodVisitor visitMethod(int access, String name, String desc, String sig, String[] exceptions) {
                MethodVisitor next = super.visitMethod(access, name, desc, sig, exceptions);
                return new MethodVisitor(Opcodes.ASM9, next) {
                    public void visitMethodInsn(int opcode, String owner, String method, String descriptor, boolean iface) {
                        if (owner.equals(entity) && method.equals("getRenderBoundingBox")) {
                            int slot = (access & Opcodes.ACC_STATIC) == 0 ? 1 : 0;
                            int found = -1;
                            for (Type argument : Type.getArgumentTypes(desc)) {
                                if (argument.getDescriptor().equals("L" + dispatcher + ";")) found = slot;
                                slot += argument.getSize();
                            }
                            if (found != 12 || !descriptor.equals("()L" + box + ";")) throw new AssertionError("Unexpected native bounds call " + name + desc);
                            super.visitVarInsn(Opcodes.ALOAD, found);
                            super.visitInsn(Opcodes.SWAP);
                            super.visitMethodInsn(Opcodes.INVOKESTATIC, helper, "get", "(L" + dispatcher + ";L" + entity + ";)L" + box + ";", false);
                            replaced[0]++;
                        } else super.visitMethodInsn(opcode, owner, method, descriptor, iface);
                    }
                };
            }
        }, 0);
        if (replaced[0] != 2) throw new AssertionError("Expected both local/global bounds calls, got " + replaced[0]);
        Files.write(Paths.get(args[1]), writer.toByteArray());
        System.out.println("Repaired both native renderer bounds calls; retained frustum predicates");
    }
}
