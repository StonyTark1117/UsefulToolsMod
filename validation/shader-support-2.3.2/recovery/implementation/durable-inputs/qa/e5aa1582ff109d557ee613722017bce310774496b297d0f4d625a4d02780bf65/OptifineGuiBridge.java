package com.stonytark.usefultoolsmod.spectralqa;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.TypeInsnNode;

/** Private diagnostic for OptiFine classes loaded before its transformation service. */
public final class OptifineGuiBridge {
    private OptifineGuiBridge() { }

    public static void premain(String arguments, Instrumentation instrumentation) throws IOException {
        // Prism/QSettings treats semicolons in instance.cfg as comments.
        String[] paths = arguments.split("!", -1);
        if (paths.length != 2) throw new IllegalArgumentException("Expected patch archive and evidence path");
        final Map<String, byte[]> classes = new HashMap<String, byte[]>();
        try (ZipFile archive = new ZipFile(paths[0])) {
            Enumeration<? extends ZipEntry> entries = archive.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                String name = entry.getName();
                if (!name.startsWith("srg/") || !name.endsWith(".class")) continue;
                String type = name.substring(4, name.length() - 6);
                if (!selected(type)) continue;
                try (InputStream input = archive.getInputStream(entry)) {
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    byte[] buffer = new byte[8192];
                    int count;
                    while ((count = input.read(buffer)) != -1) bytes.write(buffer, 0, count);
                    classes.put(type, bytes.toByteArray());
                }
            }
        }
        if (!classes.containsKey("com/mojang/blaze3d/opengl/GlStateManager")
                || !classes.containsKey("net/minecraft/resources/Identifier")) {
            throw new IllegalArgumentException("Exact OptiFine patch classes absent");
        }
        final FileOutputStream evidence = new FileOutputStream(paths[1]);
        instrumentation.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String name, Class<?> redefining,
                                    ProtectionDomain domain, byte[] original) {
                byte[] replacement = classes.get(name);
                if (name.equals("net/minecraft/core/Direction")) {
                    replacement = exposeNativeDirections(original);
                } else if (name.equals("net/minecraft/client/renderer/GameRenderer")) {
                    replacement = accessor(original, "guiRenderer", "getGuiRenderer",
                            "Lnet/minecraft/client/gui/render/GuiRenderer;");
                } else if (name.equals("net/minecraft/client/gui/render/GuiRenderer")) {
                    replacement = accessor(original, "renderState", "getRenderState",
                            "Lnet/minecraft/client/renderer/state/gui/GuiRenderState;");
                } else if (name.equals("com/mojang/blaze3d/systems/GpuDevice")) {
                    replacement = gpuAccessor(original);
                } else if (name.equals("com/mojang/blaze3d/opengl/GlDevice")) {
                    ClassNode type = new ClassNode();
                    new ClassReader(original).accept(type, 0);
                    type.access = (type.access & ~(Opcodes.ACC_PRIVATE | Opcodes.ACC_PROTECTED)) | Opcodes.ACC_PUBLIC;
                    ClassWriter writer = new ClassWriter(0);
                    type.accept(writer);
                    replacement = writer.toByteArray();
                } else if (name.equals("net/minecraft/client/renderer/entity/LivingEntityRenderer")) {
                    replacement = nativeLayerMethods(original, classes.get(name));
                } else if (name.equals("net/minecraft/client/renderer/texture/TextureAtlasSprite")) {
                    replacement = nativeSpriteConstructor(classes.get(name));
                } else if (name.equals("net/minecraft/client/renderer/texture/TextureAtlas")) {
                    replacement = atlasHelpers(original, classes.get(name));
                } else if (name.equals("net/minecraft/client/renderer/texture/SpriteLoader")) {
                    replacement = nativeSpriteAtlasBinding(original);
                } else if (name.equals("net/minecraft/client/renderer/texture/MipmapGenerator")) {
                    replacement = nativeMipmapBlender(original, classes.get(name));
                } else if (name.equals("net/minecraft/world/level/block/state/BlockState")) {
                    replacement = nativeEmissiveState(original, classes.get(name));
                }
                if (replacement == null) return null;
                synchronized (evidence) {
                    try {
                        evidence.write(("OPTIFINE_PATCH_BRIDGE " + name + " bytes="
                                + replacement.length + "\n").getBytes("UTF-8"));
                        evidence.flush();
                    } catch (IOException error) {
                        throw new IllegalStateException("Cannot retain bridge evidence", error);
                    }
                }
                return replacement;
            }
        }, false);
    }

    private static byte[] nativeEmissiveState(byte[] original, byte[] publisher) {
        if (publisher == null) throw new IllegalStateException("Exact publisher block-state donor absent");
        ClassNode target = new ClassNode();
        ClassNode donor = new ClassNode();
        new ClassReader(original).accept(target, 0);
        new ClassReader(publisher).accept(donor, 0);
        for (FieldNode field : target.fields) {
            if (field.name.equals("hasEmissive")) throw new IllegalStateException("Native emissive field already present");
        }
        target.fields.add(new FieldNode(Opcodes.ACC_PRIVATE, "hasEmissive", "Z", null, null));
        String constructor = "(Lnet/minecraft/world/level/block/Block;[Lnet/minecraft/world/level/block/state/properties/Property;[Ljava/lang/Comparable;)V";
        MethodNode init = null;
        int getters = 0;
        for (MethodNode method : donor.methods) {
            if (method.name.equals("hasEmissive") && method.desc.equals("()Z")) {
                MethodNode copy = new MethodNode(method.access, method.name, method.desc, method.signature, null);
                method.accept(copy);
                target.methods.add(copy);
                getters++;
            }
            if (method.name.equals("<init>") && method.desc.equals(constructor)) {
                init = new MethodNode(method.access, method.name, method.desc, method.signature, null);
                method.accept(init);
            }
        }
        if (getters != 1 || init == null) throw new IllegalStateException("Exact publisher emissive getter/initialization absent");
        // Keep the actual publisher AIR/Reflector guards and predicate identity
        // comparison. Native Block exposes properties() publicly; retain its
        // constructor and Forge interfaces rather than replacing the class.
        while (init.instructions.size() > 0) {
            var instruction = init.instructions.getFirst();
            if (instruction instanceof FieldInsnNode field && field.getOpcode() == Opcodes.GETSTATIC
                    && field.owner.equals("net/minecraft/world/level/block/Blocks") && field.name.equals("AIR")) break;
            init.instructions.remove(instruction);
        }
        int writes = 0;
        int properties = 0;
        for (var instruction : init.instructions.toArray()) {
            if (instruction instanceof FieldInsnNode field && field.owner.equals(target.name)) {
                if (!field.name.equals("hasEmissive") || !field.desc.equals("Z")) {
                    throw new IllegalStateException("Unrelated publisher state field in emissive initialization");
                }
                if (field.getOpcode() == Opcodes.PUTFIELD) writes++;
            }
            if (instruction instanceof FieldInsnNode field && field.getOpcode() == Opcodes.GETFIELD
                    && field.owner.equals("net/minecraft/world/level/block/Block") && field.name.equals("properties")) {
                init.instructions.set(field, new org.objectweb.asm.tree.MethodInsnNode(Opcodes.INVOKEVIRTUAL,
                        field.owner, "properties", "()" + field.desc, false));
                properties++;
            }
        }
        var terminal = init.instructions.getLast();
        while (terminal != null && terminal.getOpcode() < 0) terminal = terminal.getPrevious();
        if (writes != 1 || properties != 2 || terminal == null || terminal.getOpcode() != Opcodes.RETURN) {
            throw new IllegalStateException("Exact publisher emissive predicate comparison absent: writes="
                    + writes + "; properties=" + properties);
        }
        init.instructions.remove(terminal);
        int constructors = 0;
        for (MethodNode method : target.methods) {
            if (!method.name.equals("<init>") || !method.desc.equals(constructor)) continue;
            for (var instruction : method.instructions.toArray()) {
                if (instruction.getOpcode() != Opcodes.RETURN) continue;
                method.instructions.insertBefore(instruction, init.instructions);
                constructors++;
            }
        }
        if (constructors != 1) throw new IllegalStateException("Exact native state constructor absent");
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        target.accept(writer);
        return writer.toByteArray();
    }

    private static byte[] nativeMipmapBlender(byte[] original, byte[] publisher) {
        if (publisher == null) throw new IllegalStateException("Exact publisher mipmap donor absent");
        ClassNode target = new ClassNode();
        ClassNode donor = new ClassNode();
        new ClassReader(original).accept(target, 0);
        new ClassReader(publisher).accept(donor, 0);
        String descriptor = "(Lnet/minecraft/resources/Identifier;[Lcom/mojang/blaze3d/platform/NativeImage;ILnet/minecraft/client/renderer/texture/MipmapStrategy;FLcom/mojang/blaze3d/platform/Transparency;Lnet/optifine/texture/IColorBlender;)[Lcom/mojang/blaze3d/platform/NativeImage;";
        MethodNode overload = null;
        for (MethodNode method : donor.methods) {
            if (method.name.equals("generateMipLevels") && method.desc.equals(descriptor)) overload = method;
        }
        if (overload == null) throw new IllegalStateException("Exact publisher color-blender overload absent");
        for (MethodNode method : target.methods) {
            if (method.name.equals(overload.name) && method.desc.equals(overload.desc)) {
                throw new IllegalStateException("Native color-blender overload already present");
            }
        }
        for (org.objectweb.asm.tree.AbstractInsnNode instruction : overload.instructions) {
            if (instruction instanceof org.objectweb.asm.tree.MethodInsnNode call && call.owner.equals(target.name)) {
                boolean found = false;
                for (MethodNode method : target.methods) {
                    if (method.name.equals(call.name) && method.desc.equals(call.desc)) found = true;
                }
                if (!found) throw new IllegalStateException("Publisher mipmap helper absent from native class: " + call.name);
            }
            if (instruction instanceof FieldInsnNode field && field.owner.equals(target.name)) {
                boolean found = false;
                for (FieldNode member : target.fields) {
                    if (member.name.equals(field.name) && member.desc.equals(field.desc)) found = true;
                }
                if (!found) throw new IllegalStateException("Publisher mipmap field absent from native class: " + field.name);
            }
        }
        MethodNode copy = new MethodNode(overload.access, overload.name, overload.desc, overload.signature, null);
        overload.accept(copy);
        target.methods.add(copy);
        ClassWriter writer = new ClassWriter(0);
        target.accept(writer);
        return writer.toByteArray();
    }

    private static byte[] nativeSpriteAtlasBinding(byte[] original) {
        ClassNode type = new ClassNode();
        new ClassReader(original).accept(type, 0);
        String atlas = "Lnet/minecraft/client/renderer/texture/TextureAtlas;";
        String member = "spectralqa$owningAtlas";
        for (FieldNode field : type.fields) {
            if (field.name.equals(member)) throw new IllegalStateException("Native atlas binding already present");
        }
        type.fields.add(new FieldNode(Opcodes.ACC_PRIVATE, member, atlas, null, null));
        int factories = 0;
        int sprites = 0;
        for (MethodNode method : type.methods) {
            if (method.name.equals("create") && method.desc.equals(
                    "(" + atlas + ")Lnet/minecraft/client/renderer/texture/SpriteLoader;")) {
                for (org.objectweb.asm.tree.AbstractInsnNode instruction : method.instructions.toArray()) {
                    if (instruction.getOpcode() != Opcodes.ARETURN) continue;
                    org.objectweb.asm.tree.InsnList bind = new org.objectweb.asm.tree.InsnList();
                    bind.add(new InsnNode(Opcodes.DUP));
                    bind.add(new VarInsnNode(Opcodes.ALOAD, 0));
                    bind.add(new FieldInsnNode(Opcodes.PUTFIELD, type.name, member, atlas));
                    method.instructions.insertBefore(instruction, bind);
                    factories++;
                }
            }
            for (org.objectweb.asm.tree.AbstractInsnNode instruction : method.instructions.toArray()) {
                if (!(instruction instanceof org.objectweb.asm.tree.MethodInsnNode call)
                        || !call.owner.equals("net/minecraftforge/client/ForgeHooksClient")
                        || !call.name.equals("loadTextureAtlasSprite")
                        || !call.desc.equals("(Lnet/minecraft/resources/Identifier;Lnet/minecraft/client/renderer/texture/SpriteContents;IIIII)Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;")) continue;
                if ((method.access & Opcodes.ACC_STATIC) != 0) {
                    throw new IllegalStateException("Native sprite factory has no loader receiver");
                }
                org.objectweb.asm.tree.InsnList bind = new org.objectweb.asm.tree.InsnList();
                bind.add(new InsnNode(Opcodes.DUP));
                bind.add(new VarInsnNode(Opcodes.ALOAD, 0));
                bind.add(new FieldInsnNode(Opcodes.GETFIELD, type.name, member, atlas));
                bind.add(new org.objectweb.asm.tree.MethodInsnNode(Opcodes.INVOKEVIRTUAL,
                        "net/minecraft/client/renderer/texture/TextureAtlasSprite", "setTextureAtlas",
                        "(" + atlas + ")V", false));
                method.instructions.insert(call, bind);
                sprites++;
            }
        }
        if (factories != 1 || sprites != 1) {
            throw new IllegalStateException("Exact native loader/Forge sprite factory pair absent");
        }
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        type.accept(writer);
        return writer.toByteArray();
    }

    private static byte[] nativeSpriteConstructor(byte[] patched) {
        if (patched == null) throw new IllegalStateException("Exact publisher sprite donor absent");
        ClassNode type = new ClassNode();
        new ClassReader(patched).accept(type, 0);
        int changed = 0;
        for (MethodNode method : type.methods) {
            if (method.name.equals("<init>") && method.desc.equals(
                    "(Lnet/minecraft/resources/Identifier;Lnet/minecraft/client/renderer/texture/SpriteContents;IIIII)V")) {
                method.access = (method.access & ~(Opcodes.ACC_PRIVATE | Opcodes.ACC_PROTECTED)) | Opcodes.ACC_PUBLIC;
                changed++;
            }
        }
        if (changed != 1) throw new IllegalStateException("Exact native public sprite constructor absent");
        int dimensions = 0;
        for (MethodNode method : type.methods) {
            for (org.objectweb.asm.tree.AbstractInsnNode instruction : method.instructions.toArray()) {
                if (instruction instanceof FieldInsnNode field && field.getOpcode() == Opcodes.GETFIELD
                        && field.owner.equals("net/minecraft/client/renderer/texture/SpriteContents")
                        && field.desc.equals("I") && (field.name.equals("width") || field.name.equals("height"))) {
                    method.instructions.set(field, new org.objectweb.asm.tree.MethodInsnNode(
                            Opcodes.INVOKEVIRTUAL, field.owner, field.name, "()I", false));
                    dimensions++;
                }
            }
        }
        if (dimensions == 0) throw new IllegalStateException("Exact native sprite dimension accesses absent");
        ClassWriter writer = new ClassWriter(0);
        type.accept(writer);
        return writer.toByteArray();
    }

    private static byte[] atlasHelpers(byte[] original, byte[] patched) {
        if (patched == null) throw new IllegalStateException("Exact publisher atlas donor absent");
        ClassNode target = new ClassNode();
        ClassNode donor = new ClassNode();
        new ClassReader(original).accept(target, 0);
        new ClassReader(patched).accept(donor, 0);
        int copied = 0;
        for (MethodNode method : donor.methods) {
            boolean pathHelper = method.name.equals("isAbsoluteLocation")
                    || method.name.equals("isAbsoluteLocationPath");
            boolean formatHelper = method.name.equals("getTextureFormat")
                    || method.name.equals("getShadersColorBlender")
                    || method.name.equals("isTextureBlend")
                    || method.name.equals("isNormalBlend")
                    || method.name.equals("isSpecularBlend");
            if (!pathHelper && !formatHelper) continue;
            boolean present = false;
            for (MethodNode nativeMethod : target.methods) {
                if (nativeMethod.name.equals(method.name) && nativeMethod.desc.equals(method.desc)) present = true;
            }
            if (!present) {
                MethodNode helper = new MethodNode(method.access, method.name, method.desc, method.signature, null);
                method.accept(helper);
                // The native atlas keeps its own upload lifecycle. The
                // publisher snapshots this global format in preStitch, which
                // the native atlas does not execute. Read the actual current
                // TextureProperties format rather than adding an uninitialized
                // publisher field or returning a placeholder blender.
                for (org.objectweb.asm.tree.AbstractInsnNode instruction : helper.instructions.toArray()) {
                    if (instruction instanceof FieldInsnNode field
                            && field.getOpcode() == Opcodes.GETFIELD
                            && field.owner.equals(target.name) && field.name.equals("textureFormat")
                            && field.desc.equals("Lnet/optifine/shaders/ITextureFormat;")) {
                        helper.instructions.insertBefore(field, new InsnNode(Opcodes.POP));
                        helper.instructions.set(field, new org.objectweb.asm.tree.MethodInsnNode(
                                Opcodes.INVOKESTATIC, "net/optifine/TextureProperties", "getTextureFormat",
                                "()Lnet/optifine/shaders/ITextureFormat;", false));
                    }
                }
                target.methods.add(helper);
            }
            copied++;
        }
        if (copied != 7) throw new IllegalStateException("Exact publisher atlas path/format helper set absent");
        ClassWriter writer = new ClassWriter(0);
        target.accept(writer);
        return writer.toByteArray();
    }

    private static byte[] gpuAccessor(byte[] original) {
        ClassNode type = new ClassNode();
        new ClassReader(original).accept(type, 0);
        String backend = "Lcom/mojang/blaze3d/systems/GpuDeviceBackend;";
        boolean field = false;
        for (FieldNode member : type.fields) {
            if (member.name.equals("backend") && member.desc.equals(backend)) field = true;
        }
        if (!field) throw new IllegalStateException("Exact native GPU backend field absent");
        for (MethodNode member : type.methods) {
            if (member.name.equals("getGlDevice")) throw new IllegalStateException("GPU accessor already present");
        }
        MethodNode getter = new MethodNode(Opcodes.ACC_PUBLIC, "getGlDevice",
                "()Lcom/mojang/blaze3d/opengl/GlDevice;", null, null);
        getter.instructions.add(new VarInsnNode(Opcodes.ALOAD, 0));
        getter.instructions.add(new FieldInsnNode(Opcodes.GETFIELD, type.name, "backend", backend));
        getter.instructions.add(new TypeInsnNode(Opcodes.CHECKCAST, "com/mojang/blaze3d/opengl/GlDevice"));
        getter.instructions.add(new InsnNode(Opcodes.ARETURN));
        getter.maxStack = 1;
        getter.maxLocals = 1;
        type.methods.add(getter);
        ClassWriter writer = new ClassWriter(0);
        type.accept(writer);
        return writer.toByteArray();
    }

    private static byte[] accessor(byte[] original, String fieldName, String getterName, String descriptor) {
        ClassNode type = new ClassNode();
        new ClassReader(original).accept(type, 0);
        boolean field = false;
        for (FieldNode member : type.fields) {
            if (member.name.equals(fieldName) && member.desc.equals(descriptor)) field = true;
        }
        if (!field) throw new IllegalStateException("Exact native GUI renderer field absent");
        for (MethodNode member : type.methods) {
            if (member.name.equals(getterName)) throw new IllegalStateException("Accessor already present");
        }
        MethodNode getter = new MethodNode(Opcodes.ACC_PUBLIC, getterName, "()" + descriptor, null, null);
        getter.instructions.add(new VarInsnNode(Opcodes.ALOAD, 0));
        getter.instructions.add(new FieldInsnNode(Opcodes.GETFIELD, type.name, fieldName, descriptor));
        getter.instructions.add(new InsnNode(Opcodes.ARETURN));
        getter.maxStack = 1;
        getter.maxLocals = 1;
        type.methods.add(getter);
        ClassWriter writer = new ClassWriter(0);
        type.accept(writer);
        return writer.toByteArray();
    }

    private static byte[] exposeNativeDirections(byte[] original) {
        ClassNode type = new ClassNode();
        new ClassReader(original).accept(type, 0);
        boolean changed = false;
        for (FieldNode field : type.fields) {
            if (field.name.equals("VALUES") && field.desc.equals("[Lnet/minecraft/core/Direction;")
                    && (field.access & Opcodes.ACC_STATIC) != 0) {
                field.access = (field.access & ~(Opcodes.ACC_PRIVATE | Opcodes.ACC_PROTECTED)) | Opcodes.ACC_PUBLIC;
                changed = true;
            }
        }
        if (!changed) return null;
        ClassWriter writer = new ClassWriter(0);
        type.accept(writer);
        return writer.toByteArray();
    }

    private static byte[] nativeLayerMethods(byte[] original, byte[] publisher) {
        ClassNode target = new ClassNode();
        ClassNode donor = new ClassNode();
        new ClassReader(original).accept(target, 0);
        new ClassReader(publisher).accept(donor, 0);
        for (MethodNode method : donor.methods) {
            if (!(method.name.equals("removeLayers") || method.name.equals("replaceLayer")
                    || method.name.equals("getLayerIndex") || method.name.equals("getLayer"))) continue;
            boolean present = false;
            for (MethodNode existing : target.methods) {
                if (existing.name.equals(method.name) && existing.desc.equals(method.desc)) present = true;
            }
            if (!present) target.methods.add(method);
        }
        ClassWriter writer = new ClassWriter(0);
        target.accept(writer);
        return writer.toByteArray();
    }

    private static boolean selected(String name) {
        if (name.equals("net/minecraft/client/renderer/texture/MipmapGenerator")
                || name.equals("net/minecraft/world/level/block/state/BlockState")) return true;
        return name.equals("net/minecraft/client/renderer/texture/SpriteContents")
                || name.startsWith("net/minecraft/client/renderer/texture/SpriteContents$")
                || name.equals("net/minecraft/client/renderer/texture/TextureAtlasSprite")
                || name.startsWith("net/minecraft/client/renderer/texture/TextureAtlasSprite$")
                || name.equals("net/minecraft/client/renderer/chunk/SectionCompiler")
                || name.startsWith("net/minecraft/client/renderer/chunk/SectionCompiler$")
                || name.equals("net/minecraft/client/renderer/chunk/RenderSectionRegion")
                || name.startsWith("net/minecraft/client/renderer/chunk/RenderSectionRegion$")
                || name.equals("net/minecraft/client/renderer/entity/ItemFrameRenderer")
                || name.startsWith("net/minecraft/client/renderer/entity/ItemFrameRenderer$")
                || name.equals("net/minecraft/client/renderer/culling/Frustum")
                || name.equals("net/minecraft/client/renderer/extract/LevelExtractor")
                || name.startsWith("net/minecraft/client/renderer/extract/LevelExtractor$")
                || name.equals("net/minecraft/network/syncher/SynchedEntityData")
                || name.startsWith("net/minecraft/network/syncher/SynchedEntityData$")
                || name.equals("net/minecraft/client/multiplayer/ClientLevel")
                || name.startsWith("net/minecraft/client/multiplayer/ClientLevel$")
                || name.equals("net/minecraft/client/renderer/block/ModelBlockRenderer")
                || name.startsWith("net/minecraft/client/renderer/block/ModelBlockRenderer$")
                || name.equals("net/minecraft/client/renderer/LevelRenderer")
                || name.startsWith("net/minecraft/client/renderer/LevelRenderer$")
                || name.equals("net/minecraft/client/renderer/entity/LivingEntityRenderer")
                || name.equals("net/minecraft/client/renderer/entity/EntityRenderDispatcher")
                || name.startsWith("net/minecraft/client/renderer/entity/EntityRenderDispatcher$")
                || name.equals("net/minecraft/client/resources/model/geometry/QuadCollection")
                || name.startsWith("net/minecraft/client/resources/model/geometry/QuadCollection$")
                || name.equals("net/minecraft/client/resources/model/ModelManager")
                || name.startsWith("net/minecraft/client/resources/model/ModelManager$")
                || name.equals("net/minecraft/client/renderer/texture/TextureAtlas")
                || name.equals("com/mojang/blaze3d/vertex/VertexFormat")
                || name.startsWith("com/mojang/blaze3d/vertex/VertexFormat$")
                || name.equals("com/mojang/blaze3d/textures/GpuTexture")
                || name.startsWith("com/mojang/blaze3d/textures/GpuTexture$")
                || name.equals("net/minecraft/client/Options")
                || name.startsWith("net/minecraft/client/Options$")
                || name.equals("com/mojang/blaze3d/opengl/GlStateManager")
                || name.startsWith("com/mojang/blaze3d/opengl/GlStateManager$")
                || name.equals("net/minecraft/resources/Identifier")
                || name.startsWith("net/minecraft/resources/Identifier$");
    }
}
