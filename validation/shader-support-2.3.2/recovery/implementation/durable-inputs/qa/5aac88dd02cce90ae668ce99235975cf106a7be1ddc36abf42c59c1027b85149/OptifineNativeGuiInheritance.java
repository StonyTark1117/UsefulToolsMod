package com.stonytark.usefultoolsmod.spectralqa;

import java.nio.file.Files;
import java.nio.file.Path;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

/** Preserve the publisher's real inherited click handler in a private native donor. */
public final class OptifineNativeGuiInheritance {
    private OptifineNativeGuiInheritance() { }

    public static void main(String[] args) throws Exception {
        ClassNode donor = new ClassNode();
        ClassNode publisherParent = new ClassNode();
        new ClassReader(Files.readAllBytes(Path.of(args[0]))).accept(donor, 0);
        new ClassReader(Files.readAllBytes(Path.of(args[1]))).accept(publisherParent, 0);
        if (!donor.name.equals("net/minecraft/client/gui/screens/VideoSettingsScreen")
                || !publisherParent.name.equals("net/optifine/gui/GuiScreenOF")) {
            throw new IllegalArgumentException("Exact video donor and publisher parent required");
        }
        MethodNode inherited = publisherParent.methods.stream()
                .filter(m -> m.name.equals("mouseClicked") && m.desc.equals("(DDI)Z"))
                .findFirst().orElseThrow();
        boolean realAction = false;
        for (var instruction : inherited.instructions) {
            if (instruction instanceof org.objectweb.asm.tree.MethodInsnNode call
                    && call.owner.equals(publisherParent.name)
                    && call.name.equals("actionPerformed")
                    && call.desc.equals("(Lnet/minecraft/client/gui/components/AbstractWidget;)V")) {
                realAction = true;
            }
        }
        if (!realAction) throw new IllegalStateException("Publisher click handler has no real action dispatch");
        MethodNode accidentalOverride = donor.methods.stream()
                .filter(m -> m.name.equals("mouseClicked") && m.desc.equals("(DDI)Z"))
                .findFirst().orElseThrow();
        var opcodes = new java.util.ArrayList<Integer>();
        for (var instruction : accidentalOverride.instructions) {
            if (instruction.getOpcode() >= 0) opcodes.add(instruction.getOpcode());
        }
        if (!opcodes.equals(java.util.List.of(Opcodes.ICONST_0, Opcodes.IRETURN))) {
            throw new IllegalStateException("Refusing to remove a non-default native click implementation");
        }
        donor.methods.remove(accidentalOverride);
        ClassWriter writer = new ClassWriter(0);
        donor.accept(writer);
        Files.write(Path.of(args[2]), writer.toByteArray());
    }
}
