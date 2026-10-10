package com.stonytark.usefultoolsmod.spectralqa;

import java.nio.file.Files;
import java.nio.file.Paths;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FrameNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

/** Private packaged OptiFine experiment; never part of a maintained artifact. */
public final class OptifineOptionsFixture {
    private OptifineOptionsFixture() { }

    public static void main(String[] arguments) throws Exception {
        String forgePin = arguments.length > 2 ? arguments[2] : "64.0.8";
        if (!forgePin.equals("64.0.8") && !forgePin.equals("65.1.2")) {
            throw new IllegalArgumentException("Inspect native Options.save before adding a Forge pin");
        }
        ClassNode type = new ClassNode();
        new ClassReader(Files.readAllBytes(Paths.get(arguments[0]))).accept(type, 0);
        int saves = 0;
        int constructors = 0;
        for (MethodNode method : type.methods) {
            if (method.name.equals("save") && method.desc.equals("()V")) {
                // Preserve Forge 64.0.8's native save-during-loading guard.
                LabelNode proceed = new LabelNode();
                InsnList guard = new InsnList();
                guard.add(new MethodInsnNode(Opcodes.INVOKESTATIC,
                        "net/minecraftforge/client/loading/ClientModLoader", "isLoading", "()Z", false));
                guard.add(new JumpInsnNode(Opcodes.IFEQ, proceed));
                guard.add(new InsnNode(Opcodes.RETURN));
                guard.add(proceed);
                guard.add(new FrameNode(Opcodes.F_SAME, 0, null, 0, null));
                // The actual 65.1.2 method has no ClientModLoader loading guard.
                if (forgePin.equals("64.0.8")) method.instructions.insert(guard);
                saves++;
            }
            if (method.name.equals("<init>") && method.desc.equals(
                    "(Lnet/minecraft/client/Minecraft;Ljava/io/File;)V")) {
                boolean initialized = false;
                for (AbstractInsnNode node : method.instructions) {
                    if (node instanceof MethodInsnNode) {
                        MethodInsnNode call = (MethodInsnNode) node;
                        if (call.owner.equals("net/optifine/Config")
                                && call.name.equals("initGameSettings")) initialized = true;
                    }
                }
                if (!initialized) throw new IllegalStateException("Publisher Options lacks initGameSettings");
                AbstractInsnNode superCall = method.instructions.getFirst();
                while (!(superCall instanceof MethodInsnNode)
                        || !((MethodInsnNode) superCall).name.equals("<init>")) {
                    superCall = superCall.getNext();
                }
                InsnList init = new InsnList();
                init.add(new VarInsnNode(Opcodes.ALOAD, 1));
                init.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "net/optifine/Config",
                        "initMinecraft", "(Lnet/minecraft/client/Minecraft;)V", false));
                method.instructions.insert(superCall, init);
                constructors++;
            }
        }
        if (saves != 1 || constructors != 1) throw new IllegalStateException("Exact Options signatures absent");
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        type.accept(writer);
        Files.write(Paths.get(arguments[1]), writer.toByteArray());
    }
}
