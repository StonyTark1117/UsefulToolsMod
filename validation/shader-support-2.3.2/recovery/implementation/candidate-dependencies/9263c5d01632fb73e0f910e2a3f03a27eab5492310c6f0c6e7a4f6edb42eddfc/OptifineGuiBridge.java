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
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LdcInsnNode;

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
                } else if (name.equals("net/minecraft/client/renderer/block/FluidRenderer")) {
                    replacement = accessor(original, "fluidModels", "getFluidModels",
                            "Lnet/minecraft/client/renderer/block/FluidStateModelSet;");
                } else if (name.equals("net/minecraft/client/renderer/GameRenderer")) {
                    replacement = nativeGameRenderer(original, classes.get(name));
                } else if (name.equals("net/minecraft/client/gui/render/GuiRenderer")) {
                    replacement = nativeGuiRenderer(original, classes.get(name));
                } else if (name.equals("net/minecraft/client/gui/screens/options/VideoSettingsScreen")
                        && Boolean.getBoolean("usefulToolsSpectralAllowPack262")) {
                    replacement = enablePublisherShadersMenu(classes.get(name));
                } else if (name.equals("net/minecraft/client/gui/GuiGraphicsExtractor")
                        && Boolean.getBoolean("usefulToolsSpectralAllowPack262")) {
                    replacement = addPublisherMethods(original, classes.get(name),
                            "blit(Lnet/minecraft/resources/Identifier;IIIIFFFFI)V");
                } else if (name.equals("com/mojang/blaze3d/systems/GpuDevice")) {
                    replacement = gpuAccessor(original);
                } else if (name.equals("com/mojang/blaze3d/opengl/GlDevice")) {
                    replacement = nativeGlDevice(original, classes.get(name));
                } else if (name.equals("com/mojang/blaze3d/opengl/GlCommandEncoder")) {
                    replacement = Boolean.getBoolean("usefulToolsSpectralPublisherEncoder262")
                            ? classes.get(name) : Boolean.getBoolean("usefulToolsSpectralNativeShaderEncoder262")
                            ? nativeShaderEncoder(original, classes.get(name))
                            : publicGlCommandEncoder(original, classes.get(name));
                } else if (name.equals("com/mojang/blaze3d/opengl/GlStateManager")
                        && Boolean.getBoolean("usefulToolsSpectralForceProgramBind262")) {
                    replacement = forcePublisherProgramBind(classes.get(name));
                } else if (name.equals("com/mojang/blaze3d/pipeline/RenderTarget")) {
                    replacement = addPublisherMethods(original, classes.get(name),
                            "hasFramebuffer(I)Z");
                } else if (name.equals("com/mojang/blaze3d/opengl/GlTextureView")) {
                    replacement = addPublisherMethods(original, classes.get(name),
                            "hasFramebuffer(I)Z");
                } else if (name.equals("com/mojang/blaze3d/opengl/FrameBufferCache")) {
                    replacement = addPublisherMethods(original, classes.get(name),
                            "getFramebuffer(Lcom/mojang/blaze3d/opengl/FrameBufferCache$CacheKey;)I",
                            "hasFramebuffer(Ljava/util/List;I)Z");
                } else if (name.equals("net/minecraft/client/gui/components/BossHealthOverlay")) {
                    replacement = addPublisherMethods(original, classes.get(name),
                            "getBossName()Ljava/lang/String;");
                } else if (name.equals("net/minecraft/client/Camera")) {
                    replacement = addPublisherMethods(exposeNativeMethod(original,
                            "createProjectionMatrixForCulling", "()Lorg/joml/Matrix4f;"),
                            classes.get(name), "isDebugFrustum()Z");
                } else if (name.equals("com/mojang/blaze3d/opengl/GlTexture")) {
                    replacement = nativeGlTexture(original, classes.get(name));
                } else if (name.equals("com/mojang/blaze3d/buffers/GpuBuffer")) {
                    replacement = nativeGpuBuffer(original, classes.get(name));
                } else if (name.equals("com/mojang/blaze3d/buffers/GpuBufferSlice")) {
                    replacement = nativeGpuBufferSlice(original, classes.get(name));
                } else if (name.equals("net/minecraft/client/renderer/fog/FogRenderer")) {
                    replacement = nativeFogRenderer(original, classes.get(name));
                } else if (name.equals("net/minecraft/client/renderer/entity/LivingEntityRenderer")) {
                    replacement = nativeLayerMethods(original, classes.get(name));
                } else if (name.equals("net/minecraft/client/renderer/entity/EntityRenderer")) {
                    replacement = nativeEntityRenderState(original, classes.get(name));
                } else if (name.equals("net/minecraft/client/player/AbstractClientPlayer")) {
                    replacement = nativeCapePlayer(original, classes.get(name));
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
                } else if (name.equals("net/minecraft/client/renderer/LevelRenderer")
                        && Boolean.getBoolean("usefulToolsSpectralFramePass262")) {
                    replacement = forge262FramePass(classes.get(name));
                } else if (name.equals("net/optifine/shaders/Shaders")
                        && Boolean.getBoolean("usefulToolsSpectralAllowPack262")) {
                    replacement = enablePublisherPack262(classes.get(name));
                    if (Boolean.getBoolean("usefulToolsSpectralTraceComposite262"))
                        replacement = tracePublisherComposite(replacement);
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

    private static AbstractInsnNode nextInstruction(AbstractInsnNode instruction) {
        AbstractInsnNode next = instruction.getNext();
        while (next != null && next.getOpcode() < 0) next = next.getNext();
        return next;
    }

    private static AbstractInsnNode previousInstruction(AbstractInsnNode instruction) {
        AbstractInsnNode previous = instruction.getPrevious();
        while (previous != null && previous.getOpcode() < 0) previous = previous.getPrevious();
        return previous;
    }

    /** Remove only K2's unconditional disabled flag; keep normal guard checks. */
    private static byte[] enablePublisherPack262(byte[] publisher) {
        if (publisher == null) throw new IllegalStateException("Exact publisher Shaders donor absent");
        ClassNode type = new ClassNode();
        new ClassReader(publisher).accept(type, 0);
        int changed = 0;
        for (MethodNode method : type.methods) {
            if (!method.name.equals("loadShaderPack") || !method.desc.equals("()V")) continue;
            for (AbstractInsnNode instruction : method.instructions.toArray()) {
                if (!(instruction instanceof FieldInsnNode config) || config.getOpcode() != Opcodes.GETSTATIC
                        || !config.owner.equals(type.name) || !config.name.equals("shadersConfig")) continue;
                AbstractInsnNode store = previousInstruction(instruction);
                AbstractInsnNode value = store == null ? null : previousInstruction(store);
                if (!(store instanceof VarInsnNode local) || store.getOpcode() != Opcodes.ISTORE
                        || local.var != 2 || value == null || value.getOpcode() != Opcodes.ICONST_1) continue;
                AbstractInsnNode retainedGuardStore = previousInstruction(value);
                if (!(retainedGuardStore instanceof VarInsnNode guard)
                        || guard.getOpcode() != Opcodes.ISTORE || guard.var != 2) {
                    throw new IllegalStateException("Pinned Fabulous Graphics guard not adjacent to forced-disable flag");
                }
                method.instructions.remove(value);
                method.instructions.remove(store);
                changed++;
            }
        }
        if (changed != 1) throw new IllegalStateException("Expected one K2 unconditional pack-disable pair; found " + changed);
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        type.accept(writer);
        return writer.toByteArray();
    }

    /** K2 leaves the Shaders button visible but hardcodes an early return in its handler. */
    private static byte[] enablePublisherShadersMenu(byte[] publisher) {
        if (publisher == null) throw new IllegalStateException("Exact publisher Video Settings donor absent");
        ClassNode type = new ClassNode();
        new ClassReader(publisher).accept(type, 0);
        int changed = 0;
        for (MethodNode method : type.methods) {
            if (!method.name.equals("actionPerformed")
                    || !method.desc.equals("(Lnet/optifine/gui/GuiButtonOF;I)V")) continue;
            for (AbstractInsnNode instruction : method.instructions.toArray()) {
                if (!(instruction instanceof FieldInsnNode field)
                        || field.getOpcode() != Opcodes.GETSTATIC
                        || !field.owner.equals("java/lang/Boolean")
                        || !field.name.equals("TRUE")
                        || !field.desc.equals("Ljava/lang/Boolean;")) continue;
                AbstractInsnNode value = nextInstruction(instruction);
                AbstractInsnNode guard = value == null ? null : nextInstruction(value);
                AbstractInsnNode exit = guard == null ? null : nextInstruction(guard);
                if (!(value instanceof MethodInsnNode call)
                        || !call.owner.equals("java/lang/Boolean")
                        || !call.name.equals("booleanValue")
                        || !call.desc.equals("()Z")
                        || guard == null || guard.getOpcode() != Opcodes.IFEQ
                        || exit == null || exit.getOpcode() != Opcodes.RETURN) continue;
                field.name = "FALSE";
                changed++;
            }
        }
        if (changed != 1) throw new IllegalStateException(
                "Expected one K2 Shaders-menu hardcoded return; found " + changed);
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        type.accept(writer);
        return writer.toByteArray();
    }

    /** Retain Forge's device while adding K2's shader-mode emulated GL accessors. */
    private static byte[] nativeGlDevice(byte[] original, byte[] publisher) {
        if (publisher == null) throw new IllegalStateException("Exact publisher GlDevice absent");
        ClassNode target = new ClassNode();
        ClassNode donor = new ClassNode();
        new ClassReader(accessor(original, "encoder", "getGlCommandEncoder",
                "Lcom/mojang/blaze3d/opengl/GlCommandEncoder;")).accept(target, 0);
        new ClassReader(publisher).accept(donor, 0);
        target.access = (target.access & ~(Opcodes.ACC_PRIVATE | Opcodes.ACC_PROTECTED)) | Opcodes.ACC_PUBLIC;
        String dsa = "Lcom/mojang/blaze3d/opengl/DirectStateAccess;";
        String arrays = "Lcom/mojang/blaze3d/opengl/VertexArrayCache;";
        for (String name : new String[] {"directStateAccessEmulated", "vertexArrayCacheEmulated"}) {
            if (target.fields.stream().anyMatch(field -> field.name.equals(name)))
                throw new IllegalStateException("Pinned GlDevice already has " + name);
            FieldNode field = donor.fields.stream().filter(value -> value.name.equals(name))
                    .findFirst().orElseThrow(() -> new IllegalStateException("Publisher GlDevice lacks " + name));
            target.fields.add(new FieldNode(field.access, field.name, field.desc, field.signature, field.value));
        }
        MethodNode constructor = target.methods.stream().filter(method -> method.name.equals("<init>")
                && method.desc.equals("(JLcom/mojang/blaze3d/shaders/ShaderSource;"
                        + "Lcom/mojang/blaze3d/shaders/GpuDebugOptions;)V"))
                .findFirst().orElseThrow(() -> new IllegalStateException("Pinned GlDevice constructor absent"));
        AbstractInsnNode last = constructor.instructions.getLast();
        while (last != null && last.getOpcode() < 0) last = last.getPrevious();
        AbstractInsnNode previous = last == null ? null : previousInstruction(last);
        if (last == null || last.getOpcode() != Opcodes.RETURN
                || !(previous instanceof FieldInsnNode field)
                || field.getOpcode() != Opcodes.PUTFIELD || !field.owner.equals(target.name)
                || !field.name.equals("encoder"))
            throw new IllegalStateException("Pinned GlDevice constructor tail changed");
        var setup = new org.objectweb.asm.tree.InsnList();
        setup.add(new InsnNode(Opcodes.ICONST_0));
        setup.add(new FieldInsnNode(Opcodes.PUTSTATIC, target.name, "USE_GL_ARB_direct_state_access", "Z"));
        setup.add(new VarInsnNode(Opcodes.ALOAD, 0));
        setup.add(new VarInsnNode(Opcodes.ALOAD, 5));
        setup.add(new VarInsnNode(Opcodes.ALOAD, 6));
        setup.add(new VarInsnNode(Opcodes.ALOAD, 8));
        setup.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "com/mojang/blaze3d/opengl/DirectStateAccess",
                "create", "(Lorg/lwjgl/opengl/GLCapabilities;Ljava/util/Set;"
                        + "Lcom/mojang/blaze3d/opengl/GlHeuristics;)" + dsa, false));
        setup.add(new FieldInsnNode(Opcodes.PUTFIELD, target.name, "directStateAccessEmulated", dsa));
        setup.add(new InsnNode(Opcodes.ICONST_1));
        setup.add(new FieldInsnNode(Opcodes.PUTSTATIC, target.name, "USE_GL_ARB_direct_state_access", "Z"));
        setup.add(new InsnNode(Opcodes.ICONST_0));
        setup.add(new FieldInsnNode(Opcodes.PUTSTATIC, target.name, "USE_GL_ARB_vertex_attrib_binding", "Z"));
        setup.add(new VarInsnNode(Opcodes.ALOAD, 0));
        setup.add(new VarInsnNode(Opcodes.ALOAD, 5));
        setup.add(new VarInsnNode(Opcodes.ALOAD, 0));
        setup.add(new FieldInsnNode(Opcodes.GETFIELD, target.name, "debugLabels",
                "Lcom/mojang/blaze3d/opengl/GlDebugLabel;"));
        setup.add(new VarInsnNode(Opcodes.ALOAD, 6));
        setup.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "com/mojang/blaze3d/opengl/VertexArrayCache",
                "create", "(Lorg/lwjgl/opengl/GLCapabilities;"
                        + "Lcom/mojang/blaze3d/opengl/GlDebugLabel;Ljava/util/Set;)" + arrays, false));
        setup.add(new FieldInsnNode(Opcodes.PUTFIELD, target.name, "vertexArrayCacheEmulated", arrays));
        setup.add(new InsnNode(Opcodes.ICONST_1));
        setup.add(new FieldInsnNode(Opcodes.PUTSTATIC, target.name, "USE_GL_ARB_vertex_attrib_binding", "Z"));
        constructor.instructions.insertBefore(last, setup);
        for (String name : new String[] {"directStateAccess", "vertexArrayCache"}) {
            MethodNode nativeMethod = target.methods.stream().filter(method -> method.name.equals(name))
                    .findFirst().orElseThrow(() -> new IllegalStateException("Pinned GlDevice getter absent: " + name));
            MethodNode publisherMethod = donor.methods.stream().filter(method -> method.name.equals(name)
                    && method.desc.equals(nativeMethod.desc))
                    .findFirst().orElseThrow(() -> new IllegalStateException("Publisher GlDevice getter absent: " + name));
            target.methods.remove(nativeMethod);
            target.methods.add(publisherMethod);
        }
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        target.accept(writer);
        return writer.toByteArray();
    }

    private static byte[] tracePublisherComposite(byte[] publisherBytes) {
        ClassNode type = new ClassNode();
        new ClassReader(publisherBytes).accept(type, 0);
        MethodNode renderFinal = null;
        for (MethodNode method : type.methods)
            if (method.name.equals("renderFinal") && method.desc.equals("()V")) renderFinal = method;
        if (renderFinal == null) throw new IllegalStateException("Publisher final composite method absent");
        var before = new org.objectweb.asm.tree.InsnList();
        before.add(new LdcInsnNode("before"));
        before.add(new MethodInsnNode(Opcodes.INVOKESTATIC, type.name,
                "usefulToolsTraceComposite", "(Ljava/lang/String;)V", false));
        renderFinal.instructions.insert(before);
        int returns = 0;
        for (AbstractInsnNode instruction : renderFinal.instructions.toArray()) {
            if (instruction.getOpcode() != Opcodes.RETURN) continue;
            var after = new org.objectweb.asm.tree.InsnList();
            after.add(new LdcInsnNode("after"));
            after.add(new MethodInsnNode(Opcodes.INVOKESTATIC, type.name,
                    "usefulToolsTraceComposite", "(Ljava/lang/String;)V", false));
            renderFinal.instructions.insertBefore(instruction, after);
            returns++;
        }
        if (returns != 1) throw new IllegalStateException("Publisher final composite return changed");
        int finalCheckpoints = 0;
        for (AbstractInsnNode instruction : renderFinal.instructions.toArray()) {
            if (!(instruction instanceof MethodInsnNode call)) continue;
            String label = null;
            if (call.owner.equals(type.name) && call.name.equals("usefulToolsTraceComposite"))
                label = "spectral final entry";
            else if (call.owner.equals(type.name) && call.name.equals("dispatchComputes"))
                label = "spectral final computes";
            else if (call.owner.equals("net/optifine/shaders/GlState") && call.name.equals("unbindFramebuffer"))
                label = "spectral final unbind";
            else if (call.owner.equals("com/mojang/blaze3d/opengl/GlCommandEncoder")
                    && call.name.equals("bindFramebuffer")) label = "spectral final bind main";
            else if (call.owner.equals("org/lwjgl/opengl/GL43")
                    && (call.name.equals("glViewport") || call.name.equals("glClearColor")
                        || call.name.equals("glClear"))) label = "spectral final " + call.name;
            else if (call.owner.equals("com/mojang/blaze3d/opengl/GlStateManager")
                    && (call.name.equals("enableTexture") || call.name.equals("disableAlphaTest")
                        || call.name.equals("_disableBlend") || call.name.equals("_enableDepthTest")
                        || call.name.equals("_depthFunc") || call.name.equals("_depthMask")))
                label = "spectral final " + call.name;
            if (label == null) continue;
            var probe = new org.objectweb.asm.tree.InsnList();
            probe.add(new LdcInsnNode(label));
            probe.add(new MethodInsnNode(Opcodes.INVOKESTATIC, type.name, "checkGLError",
                    "(Ljava/lang/String;)I", false));
            probe.add(new InsnNode(Opcodes.POP));
            renderFinal.instructions.insert(instruction, probe);
            finalCheckpoints++;
        }
        if (finalCheckpoints < 10) throw new IllegalStateException("Publisher final setup anchors changed");
        int compositeDraws = 0;
        for (MethodNode method : type.methods) {
            if (!method.name.equals("renderComposites") || !method.desc.equals(
                    "(Lnet/optifine/shaders/ShadersFramebuffer;[Lnet/optifine/shaders/Program;"
                            + "Z[Lnet/optifine/shaders/ICustomTexture;)V")) continue;
            for (AbstractInsnNode instruction : method.instructions.toArray()) {
                if (!(instruction instanceof MethodInsnNode call) || !call.owner.equals(type.name)
                        || !call.name.equals("drawComposite") || !call.desc.equals("()V")) continue;
                var probe = new org.objectweb.asm.tree.InsnList();
                probe.add(new LdcInsnNode("spectral after composite quad"));
                probe.add(new MethodInsnNode(Opcodes.INVOKESTATIC, type.name, "checkGLError",
                        "(Ljava/lang/String;)I", false));
                probe.add(new InsnNode(Opcodes.POP));
                method.instructions.insert(instruction, probe);
                compositeDraws++;
            }
        }
        if (compositeDraws != 1) throw new IllegalStateException("Publisher composite draw anchor changed");
        MethodNode trace = new MethodNode(Opcodes.ACC_PRIVATE | Opcodes.ACC_STATIC,
                "usefulToolsTraceComposite", "(Ljava/lang/String;)V", null, null);
        var code = trace.instructions;
        code.add(new TypeInsnNode(Opcodes.NEW, "java/lang/StringBuilder"));
        code.add(new InsnNode(Opcodes.DUP));
        code.add(new LdcInsnNode("SPECTRAL_COMPOSITE_TRACE "));
        code.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, "java/lang/StringBuilder", "<init>",
                "(Ljava/lang/String;)V", false));
        code.add(new VarInsnNode(Opcodes.ALOAD, 0));
        code.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append",
                "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false));
        code.add(new LdcInsnNode(" fbo="));
        code.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append",
                "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false));
        code.add(new LdcInsnNode(36006));
        code.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "org/lwjgl/opengl/GL11", "glGetInteger", "(I)I", false));
        code.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append",
                "(I)Ljava/lang/StringBuilder;", false));
        code.add(new LdcInsnNode(" status="));
        code.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append",
                "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false));
        code.add(new LdcInsnNode(36160));
        code.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "org/lwjgl/opengl/GL30", "glCheckFramebufferStatus", "(I)I", false));
        code.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append",
                "(I)Ljava/lang/StringBuilder;", false));
        code.add(new LdcInsnNode(" glProgram="));
        code.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append",
                "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false));
        code.add(new LdcInsnNode(35725));
        code.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "org/lwjgl/opengl/GL11", "glGetInteger", "(I)I", false));
        code.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append",
                "(I)Ljava/lang/StringBuilder;", false));
        code.add(new LdcInsnNode(" activeProgramID="));
        code.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append",
                "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false));
        code.add(new FieldInsnNode(Opcodes.GETSTATIC, type.name, "activeProgramID", "I"));
        code.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append",
                "(I)Ljava/lang/StringBuilder;", false));
        code.add(new LdcInsnNode(" readFbo="));
        code.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append",
                "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false));
        code.add(new LdcInsnNode(36010));
        code.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "org/lwjgl/opengl/GL11", "glGetInteger", "(I)I", false));
        code.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append",
                "(I)Ljava/lang/StringBuilder;", false));
        code.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "toString",
                "()Ljava/lang/String;", false));
        code.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "net/optifine/shaders/SMCLog", "info",
                "(Ljava/lang/String;)V", false));
        code.add(new InsnNode(Opcodes.RETURN));
        type.methods.add(trace);
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        type.accept(writer);
        return writer.toByteArray();
    }

    /** Append the existing DeltaTracker argument required by pinned Forge 65.1.2. */
    private static byte[] forge262FramePass(byte[] publisher) {
        if (publisher == null) throw new IllegalStateException("Exact publisher LevelRenderer absent");
        ClassNode type = new ClassNode();
        new ClassReader(publisher).accept(type, 0);
        int changed = 0;
        for (MethodNode method : type.methods) {
            if (!method.name.equals("render") || !method.desc.startsWith(
                    "(Lcom/mojang/blaze3d/resource/GraphicsResourceAllocator;Lnet/minecraft/client/DeltaTracker;")) continue;
            for (AbstractInsnNode instruction : method.instructions.toArray()) {
                if (!(instruction instanceof FieldInsnNode field) || field.getOpcode() != Opcodes.GETSTATIC
                        || !field.owner.equals("net/optifine/reflect/Reflector")
                        || !field.name.equals("FramePassManager_insertForgePasses")) continue;
                AbstractInsnNode size = nextInstruction(field);
                AbstractInsnNode array = size == null ? null : nextInstruction(size);
                if (size == null || size.getOpcode() != Opcodes.ICONST_3
                        || !(array instanceof TypeInsnNode cast) || cast.getOpcode() != Opcodes.ANEWARRAY
                        || !cast.desc.equals("java/lang/Object")) continue;
                int stores = 0;
                AbstractInsnNode third = null;
                AbstractInsnNode cursor = nextInstruction(array);
                while (cursor != null && !(cursor instanceof MethodInsnNode call
                        && call.owner.equals("net/optifine/reflect/ReflectorMethod")
                        && call.name.equals("call"))) {
                    if (cursor.getOpcode() == Opcodes.AASTORE) {
                        stores++;
                        if (stores == 3) third = cursor;
                    }
                    cursor = nextInstruction(cursor);
                }
                if (cursor == null || stores != 3 || third == null
                        || !(third.getPrevious() instanceof FieldInsnNode state)
                        || !state.owner.equals(type.name) || !state.name.equals("levelRenderState")
                        || !state.desc.equals("Lnet/minecraft/client/renderer/state/level/LevelRenderState;")
                        || !cursor.equals(nextInstruction(third))) {
                    throw new IllegalStateException("Publisher three-argument Forge pass call shape changed");
                }
                method.instructions.set(size, new InsnNode(Opcodes.ICONST_4));
                var append = new org.objectweb.asm.tree.InsnList();
                append.add(new InsnNode(Opcodes.DUP));
                append.add(new InsnNode(Opcodes.ICONST_3));
                append.add(new VarInsnNode(Opcodes.ALOAD, 2));
                append.add(new InsnNode(Opcodes.AASTORE));
                method.instructions.insert(third, append);
                changed++;
            }
        }
        if (changed != 1) throw new IllegalStateException("Expected one publisher Forge pass call; found " + changed);
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        type.accept(writer);
        return writer.toByteArray();
    }

    private static byte[] nativeEmissiveState(byte[] original, byte[] publisher) {
        if (publisher == null) throw new IllegalStateException("Exact publisher block-state donor absent");
        ClassNode target = new ClassNode();
        ClassNode donor = new ClassNode();
        new ClassReader(original).accept(target, 0);
        new ClassReader(publisher).accept(donor, 0);
        for (FieldNode field : target.fields) {
            if (field.name.equals("hasEmissive") || field.name.equals("blockId")
                    || field.name.equals("metadata") || field.name.equals("blockLocation"))
                throw new IllegalStateException("Native OptiFine block-state field already present: " + field.name);
        }
        target.fields.add(new FieldNode(Opcodes.ACC_PRIVATE, "hasEmissive", "Z", null, null));
        target.fields.add(new FieldNode(Opcodes.ACC_PRIVATE, "blockId", "I", null, null));
        target.fields.add(new FieldNode(Opcodes.ACC_PRIVATE, "metadata", "I", null, null));
        target.fields.add(new FieldNode(Opcodes.ACC_PRIVATE, "blockLocation",
                "Lnet/minecraft/resources/Identifier;", null, null));
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
                var ids = new org.objectweb.asm.tree.InsnList();
                for (String field : new String[] { "blockId", "metadata" }) {
                    ids.add(new VarInsnNode(Opcodes.ALOAD, 0));
                    ids.add(new InsnNode(Opcodes.ICONST_M1));
                    ids.add(new FieldInsnNode(Opcodes.PUTFIELD, target.name, field, "I"));
                }
                method.instructions.insertBefore(instruction, ids);
                constructors++;
            }
        }
        if (constructors != 1) throw new IllegalStateException("Exact native state constructor absent");
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        target.accept(writer);
        return addPublisherMethods(writer.toByteArray(), publisher,
                "getBlockId()I", "getMetadata()I",
                "getBlockLocation()Lnet/minecraft/resources/Identifier;");
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

    /** Retain pinned Forge texture lifecycle while adding the publisher's shader IDs. */
    private static byte[] nativeGlTexture(byte[] original, byte[] publisher) {
        if (publisher == null) throw new IllegalStateException("Exact publisher GlTexture donor absent");
        ClassNode target = new ClassNode();
        ClassNode donor = new ClassNode();
        new ClassReader(original).accept(target, 0);
        new ClassReader(publisher).accept(donor, 0);
        int fields = 0, methods = 0;
        for (FieldNode field : donor.fields) {
            if (!field.name.equals("multiTex") && !field.name.equals("sampler")) continue;
            for (FieldNode nativeField : target.fields)
                if (nativeField.name.equals(field.name))
                    throw new IllegalStateException("Native GlTexture field already exists: " + field.name);
            target.fields.add(field);
            fields++;
        }
        for (MethodNode method : donor.methods) {
            if (!(method.name.equals("getMultiTexID") || method.name.equals("getGlTextureId")
                    || method.name.equals("bindTexture") || method.name.equals("deleteGlTexture")
                    || method.name.equals("usageToString") || method.name.equals("applySampler")
                    || method.name.equals("hasFramebuffer") || method.name.equals("toString"))) continue;
            for (MethodNode nativeMethod : target.methods)
                if (nativeMethod.name.equals(method.name) && nativeMethod.desc.equals(method.desc))
                    throw new IllegalStateException("Native GlTexture method already exists: " + method.name);
            target.methods.add(method);
            methods++;
        }
        if (fields != 2 || methods != 8)
            throw new IllegalStateException("Expected exact publisher GlTexture additions, got fields="
                    + fields + " methods=" + methods);
        String textureConstructor = "(ILjava/lang/String;Lcom/mojang/blaze3d/GpuFormat;IIIIILcom/mojang/blaze3d/opengl/FrameBufferCache;Z)V";
        MethodNode nativeConstructor = null, donorConstructor = null;
        for (MethodNode method : target.methods)
            if (method.name.equals("<init>") && method.desc.equals(textureConstructor)) nativeConstructor = method;
        for (MethodNode method : donor.methods)
            if (method.name.equals("<init>") && method.desc.equals(textureConstructor)) donorConstructor = method;
        if (nativeConstructor == null || donorConstructor == null)
            throw new IllegalStateException("Exact native/publisher GlTexture constructors absent");
        AbstractInsnNode nativeKeys = null, donorKeys = null, donorSampler = null;
        for (AbstractInsnNode instruction : nativeConstructor.instructions)
            if (instruction instanceof FieldInsnNode field && field.getOpcode() == Opcodes.PUTFIELD
                    && field.owner.equals(target.name) && field.name.equals("fboKeys")) nativeKeys = instruction;
        for (AbstractInsnNode instruction : donorConstructor.instructions)
            if (instruction instanceof FieldInsnNode field && field.getOpcode() == Opcodes.PUTFIELD
                    && field.owner.equals(donor.name)) {
                if (field.name.equals("fboKeys")) donorKeys = instruction;
                if (field.name.equals("sampler")) donorSampler = instruction;
            }
        if (nativeKeys == null || donorKeys == null || donorSampler == null)
            throw new IllegalStateException("Exact publisher texture sampler initialization absent");
        var samplerInit = new org.objectweb.asm.tree.InsnList();
        Map<LabelNode, LabelNode> samplerLabels = new HashMap<LabelNode, LabelNode>();
        int samplerInstructions = 0;
        for (AbstractInsnNode instruction = donorKeys.getNext(); instruction != donorSampler.getNext();
                instruction = instruction.getNext()) {
            if (instruction == null)
                throw new IllegalStateException("Publisher texture sampler initializer shape changed");
            if (instruction.getOpcode() < 0) continue;
            samplerInit.add(instruction.clone(samplerLabels));
            samplerInstructions++;
        }
        if (samplerInstructions != 11)
            throw new IllegalStateException("Publisher texture sampler initializer length changed");
        nativeConstructor.instructions.insert(nativeKeys, samplerInit);
        int cleanup = 0;
        for (MethodNode method : target.methods) {
            if (!method.name.equals("destroyImmediately") || !method.desc.equals("()V")) continue;
            for (AbstractInsnNode instruction : method.instructions.toArray()) {
                if (!(instruction instanceof MethodInsnNode call)
                        || !call.owner.equals("com/mojang/blaze3d/opengl/GlStateManager")
                        || !call.name.equals("_deleteTexture") || !call.desc.equals("(I)V")) continue;
                AbstractInsnNode end = nextInstruction(instruction);
                if (end == null || end.getOpcode() != Opcodes.RETURN)
                    throw new IllegalStateException("Pinned native texture deletion path changed");
                var release = new org.objectweb.asm.tree.InsnList();
                release.add(new VarInsnNode(Opcodes.ALOAD, 0));
                release.add(new VarInsnNode(Opcodes.ALOAD, 0));
                release.add(new FieldInsnNode(Opcodes.GETFIELD, target.name, "id", "I"));
                release.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "net/optifine/shaders/ShadersTex",
                        "deleteTextures", "(Lcom/mojang/blaze3d/opengl/GlTexture;I)V", false));
                method.instructions.insertBefore(end, release);
                cleanup++;
            }
        }
        if (cleanup != 1) throw new IllegalStateException("Expected one pinned GlTexture cleanup path");
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        target.accept(writer);
        return writer.toByteArray();
    }

    private static byte[] nativeGpuBuffer(byte[] original, byte[] publisher) {
        if (publisher == null) throw new IllegalStateException("Exact publisher GpuBuffer donor absent");
        ClassNode target = new ClassNode(), donor = new ClassNode();
        new ClassReader(original).accept(target, 0);
        new ClassReader(publisher).accept(donor, 0);
        int added = 0;
        for (MethodNode method : donor.methods) {
            if (!(method.name.equals("getGlBuffer") && method.desc.equals("()Lcom/mojang/blaze3d/opengl/GlBuffer;"))
                    && !(method.name.equals("slice") && method.desc.equals("(JJLjava/lang/Object;)Lcom/mojang/blaze3d/buffers/GpuBufferSlice;"))) continue;
            for (MethodNode existing : target.methods)
                if (existing.name.equals(method.name) && existing.desc.equals(method.desc))
                    throw new IllegalStateException("Native GpuBuffer method already exists: " + method.name);
            target.methods.add(method);
            added++;
        }
        if (added != 2) throw new IllegalStateException("Expected two publisher GpuBuffer methods");
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        target.accept(writer);
        return writer.toByteArray();
    }

    private static byte[] nativeGpuBufferSlice(byte[] original, byte[] publisher) {
        if (publisher == null) throw new IllegalStateException("Exact publisher GpuBufferSlice donor absent");
        ClassNode target = new ClassNode(), donor = new ClassNode();
        new ClassReader(original).accept(target, 0);
        new ClassReader(publisher).accept(donor, 0);
        int addedField = 0, addedMethods = 0, initialized = 0;
        for (FieldNode field : donor.fields) if (field.name.equals("mutableData")) {
            if (!field.desc.equals("Lnet/optifine/util/Mutable;")) throw new IllegalStateException("Publisher mutable data type changed");
            target.fields.add(field);addedField++;
        }
        for (MethodNode method : donor.methods) {
            if ((method.name.equals("getData") && method.desc.equals("()Ljava/lang/Object;"))
                    || (method.name.equals("setData") && method.desc.equals("(Ljava/lang/Object;)V"))
                    || (method.name.equals("glBuffer") && method.desc.equals("()Lcom/mojang/blaze3d/opengl/GlBuffer;"))
                    || (method.name.equals("<init>") && method.desc.equals("(Lcom/mojang/blaze3d/buffers/GpuBuffer;JJLnet/optifine/util/Mutable;)V"))) {
                for (MethodNode existing : target.methods)
                    if (existing.name.equals(method.name) && existing.desc.equals(method.desc))
                        throw new IllegalStateException("Native GpuBufferSlice method already exists: " + method.name);
                target.methods.add(method);addedMethods++;
            }
        }
        for (MethodNode method : target.methods) {
            if (!method.name.equals("<init>") || !method.desc.equals("(Lcom/mojang/blaze3d/buffers/GpuBuffer;JJ)V")) continue;
            for (AbstractInsnNode instruction : method.instructions.toArray()) if (instruction.getOpcode() == Opcodes.RETURN) {
                var init = new org.objectweb.asm.tree.InsnList();
                init.add(new VarInsnNode(Opcodes.ALOAD, 0));
                init.add(new TypeInsnNode(Opcodes.NEW, "net/optifine/util/Mutable"));
                init.add(new InsnNode(Opcodes.DUP));
                init.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, "net/optifine/util/Mutable", "<init>", "()V", false));
                init.add(new FieldInsnNode(Opcodes.PUTFIELD, target.name, "mutableData", "Lnet/optifine/util/Mutable;"));
                method.instructions.insertBefore(instruction, init);initialized++;
            }
        }
        if (addedField != 1 || addedMethods != 4 || initialized != 1)
            throw new IllegalStateException("Publisher GpuBufferSlice data graft shape changed");
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        target.accept(writer);
        return writer.toByteArray();
    }

    private static byte[] nativeFogRenderer(byte[] original, byte[] publisher) {
        if (publisher == null) throw new IllegalStateException("Exact publisher FogRenderer donor absent");
        ClassNode target = new ClassNode(), donor = new ClassNode();
        new ClassReader(original).accept(target, 0);
        new ClassReader(publisher).accept(donor, 0);
        int fieldCount = 0, methodCount = 0;
        for (FieldNode field : donor.fields) if (field.name.equals("lastFogParams")) {
            if (!field.desc.equals("Lnet/optifine/render/FogParameters;")) throw new IllegalStateException("Publisher fog metadata type changed");
            target.fields.add(field);fieldCount++;
        }
        for (MethodNode method : donor.methods) {
            if (!(method.name.equals("setShadersFog") && method.desc.equals("(Lcom/mojang/blaze3d/buffers/GpuBufferSlice;)V"))
                    && !method.name.equals("getBuffer") && !method.name.equals("setupFog")) continue;
            boolean replaced = false;
            for (int i = 0; i < target.methods.size(); i++) {
                MethodNode nativeMethod = target.methods.get(i);
                if (nativeMethod.name.equals(method.name) && nativeMethod.desc.equals(method.desc)) {
                    target.methods.set(i, method);replaced = true;break;
                }
            }
            if (!replaced) {
                if (!method.name.equals("setShadersFog")) throw new IllegalStateException("Native fog method absent: " + method.name);
                target.methods.add(method);
            }
            methodCount++;
        }
        if (fieldCount != 1 || methodCount != 3) throw new IllegalStateException("Publisher fog API graft shape changed");
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        target.accept(writer);
        return writer.toByteArray();
    }

    private static byte[] nativeGuiRenderer(byte[] original, byte[] publisher) {
        if (publisher == null) throw new IllegalStateException("Exact publisher GuiRenderer donor absent");
        byte[] withState = accessor(original, "renderState", "getRenderState",
                "Lnet/minecraft/client/renderer/state/gui/GuiRenderState;");
        ClassNode target = new ClassNode(), donor = new ClassNode();
        new ClassReader(withState).accept(target, 0);
        new ClassReader(publisher).accept(donor, 0);
        int fieldCount = 0, methodCount = 0, startCount = 0, endCount = 0;
        for (FieldNode field : donor.fields) if (field.name.equals("renderGui")) {
            if (!field.desc.equals("Z")) throw new IllegalStateException("Publisher GUI state type changed");
            for (FieldNode existing : target.fields)
                if (existing.name.equals(field.name)) throw new IllegalStateException("Native GUI state field already exists");
            target.fields.add(field);fieldCount++;
        }
        for (MethodNode method : donor.methods) if (method.name.equals("isRenderGui")) {
            if (!method.desc.equals("()Z")) throw new IllegalStateException("Publisher GUI state method changed");
            for (MethodNode existing : target.methods)
                if (existing.name.equals(method.name)) throw new IllegalStateException("Native GUI state method already exists");
            target.methods.add(method);methodCount++;
        }
        for (MethodNode method : target.methods) if (method.name.equals("render") && method.desc.equals("()V")) {
            var begin = new org.objectweb.asm.tree.InsnList();
            begin.add(new InsnNode(Opcodes.ICONST_1));
            begin.add(new FieldInsnNode(Opcodes.PUTSTATIC, target.name, "renderGui", "Z"));
            method.instructions.insert(begin);startCount++;
            for (AbstractInsnNode instruction : method.instructions.toArray()) if (instruction.getOpcode() == Opcodes.RETURN) {
                var end = new org.objectweb.asm.tree.InsnList();
                end.add(new InsnNode(Opcodes.ICONST_0));
                end.add(new FieldInsnNode(Opcodes.PUTSTATIC, target.name, "renderGui", "Z"));
                method.instructions.insertBefore(instruction, end);endCount++;
            }
        }
        if (fieldCount != 1 || methodCount != 1 || startCount != 1 || endCount != 1)
            throw new IllegalStateException("Exact pinned GUI render state transition changed");
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        target.accept(writer);
        return writer.toByteArray();
    }

    private static byte[] nativeGameRenderer(byte[] original, byte[] publisher) {
        byte[] withAccessor = accessor(original, "guiRenderer", "getGuiRenderer",
                "Lnet/minecraft/client/gui/render/GuiRenderer;");
        ClassNode type = new ClassNode();
        new ClassReader(withAccessor).accept(type, 0);
        int changed = 0;
        for (FieldNode field : type.fields) if (field.name.equals("hudProjection")) {
            if (!field.desc.equals("Lnet/minecraft/client/renderer/Projection;"))
                throw new IllegalStateException("Pinned HUD projection type changed");
            field.access = (field.access & ~(Opcodes.ACC_PRIVATE | Opcodes.ACC_PROTECTED)) | Opcodes.ACC_PUBLIC;
            changed++;
        }
        if (changed != 1) throw new IllegalStateException("Pinned HUD projection field absent or duplicated");
        if (Boolean.getBoolean("usefulToolsSpectralFramePass262"))
            addNativeShaderFrameLifecycle(type);
        // The premain agent runs outside the Minecraft module class loader.
        // Frame merging must not try to resolve game classes through it.
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS) {
            @Override protected String getCommonSuperClass(String first, String second) {
                return "java/lang/Object";
            }
        };
        type.accept(writer);
        return addPublisherMethods(writer.toByteArray(), publisher, "getRenderDistance()I");
    }

    private static byte[] publicGlCommandEncoder(byte[] original, byte[] publisherBytes) {
        ClassNode type = new ClassNode();
        new ClassReader(original).accept(type, 0);
        if (!type.name.equals("com/mojang/blaze3d/opengl/GlCommandEncoder"))
            throw new IllegalStateException("Pinned OpenGL encoder class changed");
        if (publisherBytes == null) throw new IllegalStateException("Publisher OpenGL encoder absent");
        ClassNode publisher = new ClassNode();
        new ClassReader(publisherBytes).accept(publisher, 0);
        if (!publisher.name.equals(type.name))
            throw new IllegalStateException("Publisher OpenGL encoder class changed");
        for (String signature : new String[] {
                "useVanillaProgram()V",
                "bindFramebuffer(Lcom/mojang/blaze3d/pipeline/RenderTarget;)V" }) {
            for (MethodNode method : type.methods)
                if ((method.name + method.desc).equals(signature))
                    throw new IllegalStateException("Pinned OpenGL encoder already defines " + signature);
            MethodNode selected = null;
            for (MethodNode method : publisher.methods)
                if ((method.name + method.desc).equals(signature)) selected = method;
            if (selected == null || (selected.access & Opcodes.ACC_PUBLIC) == 0)
                throw new IllegalStateException("Publisher OpenGL encoder method changed: " + signature);
            type.methods.add(selected);
        }
        type.access = (type.access & ~(Opcodes.ACC_PRIVATE | Opcodes.ACC_PROTECTED)) | Opcodes.ACC_PUBLIC;
        ClassWriter writer = new ClassWriter(0);
        type.accept(writer);
        return writer.toByteArray();
    }

    /** Keep the pinned encoder API while testing the publisher's world-target and pack-program rules. */
    private static byte[] nativeShaderEncoder(byte[] original, byte[] publisherBytes) {
        ClassNode type = new ClassNode();
        new ClassReader(publicGlCommandEncoder(original, publisherBytes)).accept(type, 0);
        int framebufferBinds = 0;
        int programBinds = 0;
        for (MethodNode method : type.methods) {
            if (method.name.equals("createRenderPass") && method.desc.contains("GpuTextureView")) {
                for (AbstractInsnNode instruction : method.instructions.toArray()) {
                    if (!(instruction instanceof MethodInsnNode call)
                            || !call.owner.equals("com/mojang/blaze3d/opengl/GlStateManager")
                            || !call.name.equals("_glBindFramebuffer") || !call.desc.equals("(II)V")) continue;
                    LabelNode nativeBind = new LabelNode();
                    LabelNode done = new LabelNode();
                    var guard = new org.objectweb.asm.tree.InsnList();
                    guard.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "net/optifine/Config", "isShaders", "()Z", false));
                    guard.add(new JumpInsnNode(Opcodes.IFEQ, nativeBind));
                    guard.add(new FieldInsnNode(Opcodes.GETSTATIC, "net/optifine/shaders/Shaders", "isRenderingWorld", "Z"));
                    guard.add(new JumpInsnNode(Opcodes.IFEQ, nativeBind));
                    guard.add(new InsnNode(Opcodes.POP2));
                    guard.add(new JumpInsnNode(Opcodes.GOTO, done));
                    guard.add(nativeBind);
                    method.instructions.insertBefore(call, guard);
                    method.instructions.insert(call, done);
                    framebufferBinds++;
                }
            }
            if (method.name.equals("trySetup") && method.desc.endsWith(")Z")) {
                for (AbstractInsnNode instruction : method.instructions.toArray()) {
                    if (instruction.getOpcode() != Opcodes.IRETURN
                            || instruction.getPrevious() == null
                            || instruction.getPrevious().getOpcode() != Opcodes.ICONST_1) continue;
                    LabelNode nativeProgram = new LabelNode();
                    var bind = new org.objectweb.asm.tree.InsnList();
                    bind.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "net/optifine/Config", "isShaders", "()Z", false));
                    bind.add(new JumpInsnNode(Opcodes.IFEQ, nativeProgram));
                    bind.add(new FieldInsnNode(Opcodes.GETSTATIC, "net/optifine/shaders/Shaders", "activeProgramID", "I"));
                    bind.add(new JumpInsnNode(Opcodes.IFLE, nativeProgram));
                    bind.add(new FieldInsnNode(Opcodes.GETSTATIC, "net/optifine/shaders/Shaders", "activeProgramID", "I"));
                    bind.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "com/mojang/blaze3d/opengl/GlStateManager", "_glUseProgram", "(I)V", false));
                    bind.add(nativeProgram);
                    method.instructions.insertBefore(instruction.getPrevious(), bind);
                    programBinds++;
                }
            }
        }
        if (framebufferBinds != 1 || programBinds != 1)
            throw new IllegalStateException("Pinned native shader encoder anchors changed: framebuffer="
                    + framebufferBinds + " program=" + programBinds);
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS) {
            @Override protected String getCommonSuperClass(String first, String second) {
                return "java/lang/Object";
            }
        };
        type.accept(writer);
        return writer.toByteArray();
    }

    private static byte[] addPublisherMethods(byte[] original, byte[] publisherBytes, String... signatures) {
        if (publisherBytes == null) throw new IllegalStateException("Publisher OpenGL method owner absent");
        ClassNode target = new ClassNode();
        ClassNode donor = new ClassNode();
        new ClassReader(original).accept(target, 0);
        new ClassReader(publisherBytes).accept(donor, 0);
        if (!target.name.equals(donor.name)) throw new IllegalStateException("Publisher method owner changed");
        for (String signature : signatures) {
            MethodNode selected = null;
            for (MethodNode method : donor.methods)
                if ((method.name + method.desc).equals(signature)) selected = method;
            if (selected == null || (selected.access & Opcodes.ACC_PUBLIC) == 0)
                throw new IllegalStateException("Publisher method changed: " + signature);
            for (MethodNode method : target.methods)
                if ((method.name + method.desc).equals(signature))
                    throw new IllegalStateException("Pinned method already exists: " + signature);
            target.methods.add(selected);
        }
        ClassWriter writer = new ClassWriter(0);
        target.accept(writer);
        return writer.toByteArray();
    }

    private static byte[] forcePublisherProgramBind(byte[] publisherBytes) {
        if (publisherBytes == null) throw new IllegalStateException("Publisher GL state manager absent");
        ClassNode type = new ClassNode();
        new ClassReader(publisherBytes).accept(type, 0);
        int patched = 0;
        for (MethodNode method : type.methods) {
            if (!method.name.equals("_glUseProgram") || !method.desc.equals("(I)V")) continue;
            AbstractInsnNode guard = method.instructions.getFirst();
            while (guard != null && guard.getOpcode() < 0) guard = guard.getNext();
            if (!(guard instanceof MethodInsnNode call) || call.getOpcode() != Opcodes.INVOKESTATIC
                    || !call.owner.equals("com/mojang/blaze3d/systems/RenderSystem")
                    || !call.name.equals("assertOnRenderThread"))
                throw new IllegalStateException("Publisher program-bind thread guard changed");
            var bind = new org.objectweb.asm.tree.InsnList();
            bind.add(new VarInsnNode(Opcodes.ILOAD, 0));
            bind.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "org/lwjgl/opengl/GL33C",
                    "glUseProgram", "(I)V", false));
            method.instructions.insert(guard, bind);
            patched++;
        }
        if (patched != 1) throw new IllegalStateException("Publisher program binder absent");
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        type.accept(writer);
        return writer.toByteArray();
    }

    private static byte[] nativeCapePlayer(byte[] original, byte[] publisherBytes) {
        if (publisherBytes == null) throw new IllegalStateException("Publisher cape player absent");
        ClassNode target = new ClassNode();
        ClassNode donor = new ClassNode();
        new ClassReader(original).accept(target, 0);
        new ClassReader(publisherBytes).accept(donor, 0);
        if (!target.name.equals(donor.name)) throw new IllegalStateException("Publisher cape owner changed");
        String[] fields = {"locationOfCape:Lnet/minecraft/resources/Identifier;",
                "reloadCapeTimeMs:J", "elytraOfCape:Z", "nameClear:Ljava/lang/String;"};
        for (String signature : fields) {
            FieldNode selected = null;
            for (FieldNode field : donor.fields)
                if ((field.name + ":" + field.desc).equals(signature)) selected = field;
            if (selected == null) throw new IllegalStateException("Publisher cape field changed: " + signature);
            for (FieldNode field : target.fields)
                if ((field.name + ":" + field.desc).equals(signature))
                    throw new IllegalStateException("Pinned cape field already exists: " + signature);
            target.fields.add(selected);
        }
        String[] methods = {"getNameClear()Ljava/lang/String;", "getLocationOfCape()Lnet/minecraft/resources/Identifier;",
                "setLocationOfCape(Lnet/minecraft/resources/Identifier;)V", "hasElytraCape()Z",
                "setElytraOfCape(Z)V", "isElytraOfCape()Z", "getReloadCapeTimeMs()J",
                "setReloadCapeTimeMs(J)V", "getLocationCape()Lnet/minecraft/resources/Identifier;",
                "getLocationElytra()Lnet/minecraft/resources/Identifier;",
                "getSkinTextureLocation()Lnet/minecraft/resources/Identifier;"};
        for (String signature : methods) {
            MethodNode selected = null;
            for (MethodNode method : donor.methods)
                if ((method.name + method.desc).equals(signature)) selected = method;
            if (selected == null || (selected.access & Opcodes.ACC_PUBLIC) == 0)
                throw new IllegalStateException("Publisher cape method changed: " + signature);
            for (MethodNode method : target.methods)
                if ((method.name + method.desc).equals(signature))
                    throw new IllegalStateException("Pinned cape method already exists: " + signature);
            target.methods.add(selected);
        }
        String constructor = "(Lnet/minecraft/client/multiplayer/ClientLevel;Lcom/mojang/authlib/GameProfile;)V";
        MethodNode nativeConstructor = null;
        MethodNode donorConstructor = null;
        for (MethodNode method : target.methods)
            if (method.name.equals("<init>") && method.desc.equals(constructor)) nativeConstructor = method;
        for (MethodNode method : donor.methods)
            if (method.name.equals("<init>") && method.desc.equals(constructor)) donorConstructor = method;
        if (nativeConstructor == null || donorConstructor == null)
            throw new IllegalStateException("Exact cape constructors absent");
        // Both constructors have the same native prefix; the publisher suffix initializes
        // the clear name and starts the real OptiFine cape/player-config loading path.
        AbstractInsnNode nativeReturn = null;
        for (AbstractInsnNode instruction : nativeConstructor.instructions)
            if (instruction.getOpcode() == Opcodes.RETURN) nativeReturn = instruction;
        if (nativeReturn == null) throw new IllegalStateException("Pinned cape constructor has no return");
        AbstractInsnNode donorFirst = null;
        for (AbstractInsnNode instruction : donorConstructor.instructions) {
            if (instruction instanceof FieldInsnNode field && field.getOpcode() == Opcodes.PUTFIELD
                    && field.owner.equals(target.name) && field.name.equals("showExtraEars")) {
                donorFirst = instruction.getNext();
                break;
            }
        }
        if (donorFirst == null) throw new IllegalStateException("Publisher cape constructor prefix changed");
        var suffix = new org.objectweb.asm.tree.InsnList();
        Map<LabelNode, LabelNode> labels = new HashMap<LabelNode, LabelNode>();
        for (AbstractInsnNode instruction = donorFirst; instruction != null && instruction.getOpcode() != Opcodes.RETURN;
                instruction = instruction.getNext())
            if (instruction instanceof LabelNode label) labels.put(label, new LabelNode());
        for (AbstractInsnNode instruction = donorFirst; instruction != null && instruction.getOpcode() != Opcodes.RETURN;
                instruction = instruction.getNext()) suffix.add(instruction.clone(labels));
        nativeConstructor.instructions.insertBefore(nativeReturn, suffix);
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        target.accept(writer);
        return writer.toByteArray();
    }

    private static byte[] nativeEntityRenderState(byte[] original, byte[] publisherBytes) {
        if (publisherBytes == null) throw new IllegalStateException("Publisher entity renderer absent");
        ClassNode target = new ClassNode();
        ClassNode donor = new ClassNode();
        new ClassReader(original).accept(target, 0);
        new ClassReader(publisherBytes).accept(donor, 0);
        String descriptor = "(Lnet/minecraft/world/entity/Entity;"
                + "Lnet/minecraft/client/renderer/entity/state/EntityRenderState;F)V";
        MethodNode nativeExtract = null;
        MethodNode publisherExtract = null;
        for (MethodNode method : target.methods)
            if (method.name.equals("extractRenderState") && method.desc.equals(descriptor)) nativeExtract = method;
        for (MethodNode method : donor.methods)
            if (method.name.equals("extractRenderState") && method.desc.equals(descriptor)) publisherExtract = method;
        if (nativeExtract == null || publisherExtract == null)
            throw new IllegalStateException("Exact native/publisher render-state extractor absent");
        boolean publisherStoresEntity = false;
        for (var instruction : publisherExtract.instructions)
            if (instruction instanceof FieldInsnNode field && field.getOpcode() == Opcodes.PUTFIELD
                    && field.owner.equals("net/minecraft/client/renderer/entity/state/EntityRenderState")
                    && field.name.equals("entity")
                    && field.desc.equals("Lnet/minecraft/world/entity/Entity;"))
                publisherStoresEntity = true;
        if (!publisherStoresEntity) throw new IllegalStateException("Publisher entity-state assignment changed");
        int returns = 0;
        for (var instruction : nativeExtract.instructions.toArray()) {
            if (instruction.getOpcode() != Opcodes.RETURN) continue;
            var store = new org.objectweb.asm.tree.InsnList();
            store.add(new VarInsnNode(Opcodes.ALOAD, 2));
            store.add(new VarInsnNode(Opcodes.ALOAD, 1));
            store.add(new FieldInsnNode(Opcodes.PUTFIELD,
                    "net/minecraft/client/renderer/entity/state/EntityRenderState", "entity",
                    "Lnet/minecraft/world/entity/Entity;"));
            nativeExtract.instructions.insertBefore(instruction, store);
            returns++;
        }
        if (returns != 1) throw new IllegalStateException("Pinned entity-state extractor return changed");
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        target.accept(writer);
        return writer.toByteArray();
    }

    private static byte[] exposeNativeMethod(byte[] original, String name, String descriptor) {
        ClassNode type = new ClassNode();
        new ClassReader(original).accept(type, 0);
        MethodNode found = null;
        for (MethodNode method : type.methods)
            if (method.name.equals(name) && method.desc.equals(descriptor)) found = method;
        if (found == null || (found.access & Opcodes.ACC_PRIVATE) == 0)
            throw new IllegalStateException("Pinned native method visibility changed: " + name);
        found.access = (found.access & ~(Opcodes.ACC_PRIVATE | Opcodes.ACC_PROTECTED)) | Opcodes.ACC_PUBLIC;
        ClassWriter writer = new ClassWriter(0);
        type.accept(writer);
        return writer.toByteArray();
    }

    private static void addNativeShaderFrameLifecycle(ClassNode type) {
        MethodNode render = null;
        for (MethodNode method : type.methods)
            if (method.name.equals("renderLevel")
                    && method.desc.equals("(Lnet/minecraft/client/DeltaTracker;)V")) render = method;
        if (render == null) throw new IllegalStateException("Pinned GameRenderer.renderLevel absent");
        var start = new org.objectweb.asm.tree.InsnList();
        LabelNode noFrameInit = new LabelNode();
        start.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "net/optifine/Config", "isShaders", "()Z", false));
        start.add(new JumpInsnNode(Opcodes.IFEQ, noFrameInit));
        start.add(new VarInsnNode(Opcodes.ALOAD, 0));
        start.add(new FieldInsnNode(Opcodes.GETFIELD, type.name, "minecraft", "Lnet/minecraft/client/Minecraft;"));
        start.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "net/optifine/shaders/Shaders", "frameInit",
                "(Lnet/minecraft/client/Minecraft;)V", false));
        start.add(noFrameInit);
        render.instructions.insert(start);
        int begin = 0, composite = 0, end = 0;
        for (AbstractInsnNode instruction : render.instructions.toArray()) {
            if (instruction instanceof MethodInsnNode call && call.name.equals("popPush")
                    && call.owner.equals("net/minecraft/util/profiling/ProfilerFiller")
                    && call.getPrevious() instanceof LdcInsnNode label && "level".equals(label.cst)) {
                var hooks = new org.objectweb.asm.tree.InsnList();
                LabelNode noBegin = new LabelNode();
                hooks.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "net/optifine/Config", "isShaders", "()Z", false));
                hooks.add(new JumpInsnNode(Opcodes.IFEQ, noBegin));
                hooks.add(new VarInsnNode(Opcodes.ALOAD, 0));
                hooks.add(new FieldInsnNode(Opcodes.GETFIELD, type.name, "mainCamera", "Lnet/minecraft/client/Camera;"));
                hooks.add(new VarInsnNode(Opcodes.ALOAD, 9));
                hooks.add(new VarInsnNode(Opcodes.ALOAD, 10));
                hooks.add(new VarInsnNode(Opcodes.ALOAD, 1));
                hooks.add(new VarInsnNode(Opcodes.FLOAD, 2));
                hooks.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "net/optifine/shaders/Shaders", "beginRender",
                        "(Lnet/minecraft/client/Camera;Lorg/joml/Matrix4fc;Lorg/joml/Matrix4f;Lnet/minecraft/client/DeltaTracker;F)V", false));
                hooks.add(new VarInsnNode(Opcodes.FLOAD, 2));
                hooks.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "net/optifine/shaders/Shaders", "beginRenderPass", "(F)V", false));
                hooks.add(noBegin);
                render.instructions.insert(instruction, hooks);begin++;
            }
            if (instruction instanceof LdcInsnNode label && "screenEffects".equals(label.cst)) {
                AbstractInsnNode profiler = nextInstruction(instruction);
                if (!(profiler instanceof MethodInsnNode call)
                        || !call.owner.equals("net/minecraft/util/profiling/ProfilerFiller")
                        || !call.name.equals("popPush")) {
                    throw new IllegalStateException("Pinned screen-effects profiler anchor changed");
                }
                var hooks = new org.objectweb.asm.tree.InsnList();
                LabelNode noComposite = new LabelNode();
                hooks.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "net/optifine/Config", "isShaders", "()Z", false));
                hooks.add(new JumpInsnNode(Opcodes.IFEQ, noComposite));
                hooks.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "com/mojang/blaze3d/systems/RenderSystem",
                        "backupProjectionMatrix", "()V", false));
                hooks.add(new VarInsnNode(Opcodes.ALOAD, 0));
                hooks.add(new FieldInsnNode(Opcodes.GETFIELD, type.name, "levelProjectionMatrixBuffer",
                        "Lnet/minecraft/client/renderer/ProjectionMatrixBuffer;"));
                hooks.add(new VarInsnNode(Opcodes.ALOAD, 10));
                hooks.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL,
                        "net/minecraft/client/renderer/ProjectionMatrixBuffer", "getBuffer",
                        "(Lorg/joml/Matrix4f;)Lcom/mojang/blaze3d/buffers/GpuBufferSlice;", false));
                hooks.add(new FieldInsnNode(Opcodes.GETSTATIC, "com/mojang/blaze3d/ProjectionType",
                        "PERSPECTIVE", "Lcom/mojang/blaze3d/ProjectionType;"));
                hooks.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "com/mojang/blaze3d/systems/RenderSystem",
                        "setProjectionMatrix", "(Lcom/mojang/blaze3d/buffers/GpuBufferSlice;"
                                + "Lcom/mojang/blaze3d/ProjectionType;)V", false));
                hooks.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "net/optifine/shaders/Shaders",
                        "renderCompositeFinal", "()V", false));
                hooks.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "com/mojang/blaze3d/systems/RenderSystem",
                        "restoreProjectionMatrix", "()V", false));
                hooks.add(noComposite);
                render.instructions.insertBefore(instruction, hooks);composite++;
            }
            if (instruction instanceof FieldInsnNode field && field.getOpcode() == Opcodes.GETSTATIC
                    && field.owner.equals("net/minecraft/client/renderer/fog/FogRenderer$FogMode")
                    && field.name.equals("NONE")) {
                var hooks = new org.objectweb.asm.tree.InsnList();
                LabelNode noEnd = new LabelNode();
                hooks.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "net/optifine/Config", "isShaders", "()Z", false));
                hooks.add(new JumpInsnNode(Opcodes.IFEQ, noEnd));
                hooks.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "net/optifine/shaders/Shaders", "endRender", "()V", false));
                hooks.add(noEnd);
                render.instructions.insertBefore(instruction, hooks);end++;
            }
        }
        if (begin != 1 || composite != 1 || end != 1)
            throw new IllegalStateException("Pinned shader frame lifecycle anchors changed");
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
                || name.equals("net/minecraft/world/level/block/state/BlockState")
                || (name.equals("net/optifine/shaders/Shaders")
                    && Boolean.getBoolean("usefulToolsSpectralAllowPack262"))) return true;
        return name.equals("net/minecraft/client/renderer/texture/SpriteContents")
                || name.equals("net/minecraft/client/gui/GuiGraphicsExtractor")
                || name.equals("net/minecraft/client/renderer/state/level/BlockOutlineRenderState")
                || name.equals("net/minecraft/client/gui/screens/options/VideoSettingsScreen")
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
                || name.equals("com/mojang/blaze3d/opengl/GlTexture")
                || name.equals("com/mojang/blaze3d/opengl/GlCommandEncoder")
                || name.equals("com/mojang/blaze3d/opengl/GlDevice")
                || name.equals("com/mojang/blaze3d/pipeline/RenderTarget")
                || name.equals("com/mojang/blaze3d/opengl/GlTextureView")
                || name.equals("com/mojang/blaze3d/opengl/FrameBufferCache")
                || name.equals("net/minecraft/client/gui/components/BossHealthOverlay")
                || name.equals("com/mojang/blaze3d/buffers/GpuBuffer")
                || name.equals("com/mojang/blaze3d/buffers/GpuBufferSlice")
                || name.equals("net/minecraft/client/renderer/fog/FogRenderer")
                || name.equals("net/minecraft/client/gui/render/GuiRenderer")
                || name.equals("net/minecraft/client/renderer/GameRenderer")
                || name.equals("net/minecraft/client/Camera")
                || name.equals("net/minecraft/client/renderer/entity/EntityRenderer")
                || name.equals("net/minecraft/client/player/AbstractClientPlayer")
                || name.startsWith("com/mojang/blaze3d/textures/GpuTexture$")
                || name.equals("net/minecraft/client/Options")
                || name.startsWith("net/minecraft/client/Options$")
                || name.equals("com/mojang/blaze3d/opengl/GlStateManager")
                || name.startsWith("com/mojang/blaze3d/opengl/GlStateManager$")
                || name.equals("net/minecraft/resources/Identifier")
                || name.startsWith("net/minecraft/resources/Identifier$");
    }
}
