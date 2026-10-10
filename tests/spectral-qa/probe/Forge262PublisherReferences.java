package com.stonytark.usefultoolsmod.spectralqa;

import java.nio.file.Path;
import java.util.zip.ZipFile;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/** List exact publisher method references for pinned Forge compatibility review. */
public final class Forge262PublisherReferences {
    private Forge262PublisherReferences() { }

    public static void main(String[] args) throws Exception {
        if (args.length != 1) throw new IllegalArgumentException("patched publisher archive required");
        try (ZipFile archive = new ZipFile(Path.of(args[0]).toFile())) {
            var entries = archive.entries();
            while (entries.hasMoreElements()) {
                var entry = entries.nextElement();
                if (!entry.getName().startsWith("srg/") || !entry.getName().endsWith(".class")) continue;
                try (var stream = archive.getInputStream(entry)) {
                    var reader = new ClassReader(stream.readAllBytes());
                    reader.accept(new ClassVisitor(Opcodes.ASM9) {
                        @Override public MethodVisitor visitMethod(int access, String name, String descriptor,
                                String signature, String[] exceptions) {
                            String caller = reader.getClassName() + "." + name + descriptor;
                            return new MethodVisitor(Opcodes.ASM9) {
                                @Override public void visitMethodInsn(int opcode, String owner,
                                        String method, String desc, boolean isInterface) {
                                    if (owner.equals("com/mojang/blaze3d/opengl/GlDevice")
                                            || owner.equals("com/mojang/blaze3d/opengl/GlCommandEncoder")
                                            || owner.equals("com/mojang/blaze3d/opengl/GlStateManager")
                                            || owner.equals("net/minecraft/client/renderer/GameRenderer")
                                            || owner.equals("net/minecraft/client/gui/render/GuiRenderer")
                                            || owner.equals("net/minecraft/client/renderer/fog/FogRenderer")
                                            || owner.equals("com/mojang/blaze3d/opengl/GlTexture")
                                            || owner.equals("com/mojang/blaze3d/systems/RenderSystem"))
                                        System.out.println(owner + "\t" + method + "\t" + desc + "\t" + caller);
                                }
                            };
                        }
                    }, ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);
                }
            }
        }
    }
}
