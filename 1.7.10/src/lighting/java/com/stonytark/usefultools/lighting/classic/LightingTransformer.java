package com.stonytark.usefultools.lighting.classic;
import cpw.mods.fml.relauncher.FMLLaunchHandler;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

/** Instrument renderer brightness returns only; vanilla light arrays and world queries survive unchanged. */
public final class LightingTransformer implements IClassTransformer {
    private static final String HOOK = "com/stonytark/usefultools/lighting/classic/ClassicClientLighting";
    private static boolean referencesBlockBrightness(byte[] bytes) {
        // Overrides such as BlockLiquid bypass Block's implementation. Limit
        // full ASM parsing to classes which mention the native render method.
        String constants = new String(bytes, java.nio.charset.StandardCharsets.ISO_8859_1);
        return constants.contains("getMixedBrightnessForBlock") || constants.contains("func_149677_c");
    }
    public byte[] transform(String name, String transformedName, byte[] bytes) {
        if (bytes == null || !FMLLaunchHandler.side().isClient()) return bytes;
        boolean block = transformedName.equals("net.minecraft.block.Block");
        boolean entity = transformedName.equals("net.minecraft.entity.Entity");
        boolean update = transformedName.equals("net.minecraft.client.multiplayer.WorldClient");
        boolean chunk = transformedName.equals("net.minecraft.world.chunk.Chunk");
        if (!block && !entity && !update && !chunk && !referencesBlockBrightness(bytes)) return bytes;
        ClassNode node = new ClassNode(); new ClassReader(bytes).accept(node, 0);
        boolean changed = false;
        for (Object value : node.methods) {
            MethodNode method = (MethodNode)value;
            boolean sampleBlock = (method.access & Opcodes.ACC_STATIC) == 0
                && method.desc.equals("(Lnet/minecraft/world/IBlockAccess;III)I")
                && (method.name.equals("getMixedBrightnessForBlock") || method.name.equals("func_149677_c"));
            boolean sampleEntity = entity && (method.name.equals("getBrightnessForRender") || method.name.equals("func_70070_b"));
            boolean changedBlock = update && method.name.equals("func_147492_c");
            boolean changedChunk = chunk && (method.name.equals("fillChunk") || method.name.equals("func_76607_a"));
            if (!sampleBlock && !sampleEntity && !changedBlock && !changedChunk) continue;
            for (AbstractInsnNode insn = method.instructions.getFirst(); insn != null; insn = insn.getNext()) {
                if (insn.getOpcode() != Opcodes.IRETURN && insn.getOpcode() != Opcodes.RETURN) continue;
                InsnList inject = new InsnList();
                if (sampleBlock) {
                    inject.add(new VarInsnNode(Opcodes.ILOAD, 2)); inject.add(new VarInsnNode(Opcodes.ILOAD, 3)); inject.add(new VarInsnNode(Opcodes.ILOAD, 4));
                    inject.add(new MethodInsnNode(Opcodes.INVOKESTATIC, HOOK, "sample", "(IIII)I", false));
                } else if (sampleEntity || changedChunk) {
                    inject.add(new VarInsnNode(Opcodes.ALOAD, 0));
                    inject.add(new MethodInsnNode(Opcodes.INVOKESTATIC, HOOK, sampleEntity ? "sampleEntity" : "chunkChanged", sampleEntity ? "(ILjava/lang/Object;)I" : "(Ljava/lang/Object;)V", false));
                } else {
                    inject.add(new VarInsnNode(Opcodes.ILOAD, 1)); inject.add(new VarInsnNode(Opcodes.ILOAD, 2)); inject.add(new VarInsnNode(Opcodes.ILOAD, 3));
                    inject.add(new MethodInsnNode(Opcodes.INVOKESTATIC, HOOK, "blockChanged", "(III)V", false));
                }
                method.instructions.insertBefore(insn, inject); changed = true;
            }
        }
        if (!changed) return bytes;
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS); node.accept(writer); return writer.toByteArray();
    }
}
