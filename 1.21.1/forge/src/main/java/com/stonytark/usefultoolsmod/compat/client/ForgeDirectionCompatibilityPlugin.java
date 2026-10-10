package com.stonytark.usefultoolsmod.compat.client;

import java.util.List;
import java.util.Set;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

/** Restores the pinned Forge neighbor-update hook removed by OptiFine J1's older class. */
public final class ForgeDirectionCompatibilityPlugin implements IMixinConfigPlugin {
    private static final String OWNER = "net/minecraft/core/Direction";
    private static final String PLANE = OWNER + "$Plane";
    private static final String LIST = "Ljava/util/List;";

    @Override public void onLoad(String mixinPackage) { }
    @Override public String getRefMapperConfig() { return null; }
    @Override public boolean shouldApplyMixin(String targetClassName, String mixinClassName) { return true; }
    @Override public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) { }
    @Override public List<String> getMixins() { return List.of(); }
    @Override public void preApply(String targetClassName, ClassNode targetClass,
                                  String mixinClassName, IMixinInfo mixinInfo) { }

    @Override public void postApply(String targetClassName, ClassNode targetClass,
                                   String mixinClassName, IMixinInfo mixinInfo) {
        restoreUpdateOrder(targetClass);
    }

    static void restoreUpdateOrder(ClassNode target) {
        if (!OWNER.equals(target.name)) throw new IllegalArgumentException("Unexpected compatibility target");
        for (MethodNode method : target.methods) {
            if (method.name.equals("getUpdateOrder") && method.desc.equals("()" + LIST)) return;
        }
        boolean fieldPresent = target.fields.stream().anyMatch(field -> field.name.equals("UPDATE_ORDER"));
        if (!fieldPresent) {
            MethodNode initializer = target.methods.stream().filter(method -> method.name.equals("<clinit>"))
                    .findFirst().orElseThrow(() -> new IllegalStateException("Direction initializer absent"));
            AbstractInsnNode end = initializer.instructions.getLast();
            while (end != null && end.getOpcode() != Opcodes.RETURN) end = end.getPrevious();
            if (end == null) throw new IllegalStateException("Direction initializer return absent");
            target.fields.add(new FieldNode(Opcodes.ACC_PRIVATE | Opcodes.ACC_STATIC | Opcodes.ACC_FINAL,
                    "UPDATE_ORDER", LIST, "Ljava/util/List<L" + OWNER + ";>;", null));
            // Exact Forge 52.1.10 packaged-client order: horizontal, then vertical.
            // Use official runtime names; Gradle's SRG donor is not a runtime API.
            InsnList instructions = new InsnList();
            for (String plane : List.of("HORIZONTAL", "VERTICAL")) {
                instructions.add(new FieldInsnNode(Opcodes.GETSTATIC, PLANE, plane, "L" + PLANE + ";"));
                instructions.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, PLANE, "stream",
                        "()Ljava/util/stream/Stream;", false));
            }
            instructions.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "java/util/stream/Stream", "concat",
                    "(Ljava/util/stream/Stream;Ljava/util/stream/Stream;)Ljava/util/stream/Stream;", true));
            instructions.add(new MethodInsnNode(Opcodes.INVOKEINTERFACE, "java/util/stream/Stream", "toList",
                    "()" + LIST, true));
            instructions.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "java/util/Collections", "unmodifiableList",
                    "(" + LIST + ")" + LIST, false));
            instructions.add(new FieldInsnNode(Opcodes.PUTSTATIC, OWNER, "UPDATE_ORDER", LIST));
            initializer.instructions.insertBefore(end, instructions);
            initializer.maxStack = Math.max(initializer.maxStack, 2);
        }
        MethodNode getter = new MethodNode(Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC | Opcodes.ACC_FINAL,
                "getUpdateOrder", "()" + LIST, "()Ljava/util/List<L" + OWNER + ";>;", null);
        getter.instructions.add(new FieldInsnNode(Opcodes.GETSTATIC, OWNER, "UPDATE_ORDER", LIST));
        getter.instructions.add(new InsnNode(Opcodes.ARETURN));
        getter.maxStack = 1;
        target.methods.add(getter);
        System.getLogger(ForgeDirectionCompatibilityPlugin.class.getName()).log(System.Logger.Level.INFO,
                "Restored Forge 52.1.10 Direction neighbor-update hook after client class replacement");
    }
}
