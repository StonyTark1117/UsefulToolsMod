package com.stonytark.usefultoolsmod.spectralqa;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.ProtectionDomain;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.VarInsnNode;
import org.objectweb.asm.tree.InsnList;

/**
 * Private, version-locked admission diagnostic for OptiFine K1 pre2 on Forge 64.0.8.
 * This is deliberately not a general OptiFine compatibility layer.
 */
public final class Optifine2612K1AdmissionBridge {
    private static final String DONOR_SHA256 =
            "89b6c8051570cf76259041ea13ca4ae5c0147b303c30d85c78d39a3f6305c633";

    private Optifine2612K1AdmissionBridge() { }

    public static void premain(String arguments, Instrumentation instrumentation) throws Exception {
        String[] paths = arguments.split("!", -1);
        if (paths.length != 2 || paths[0].isEmpty() || paths[1].isEmpty()) {
            throw new IllegalArgumentException("Expected exact donor archive and evidence path");
        }
        Path donor = Paths.get(paths[0]).toAbsolutePath().normalize();
        Path evidencePath = Paths.get(paths[1]).toAbsolutePath().normalize();
        if (!sha256(Files.readAllBytes(donor)).equals(DONOR_SHA256)) {
            throw new IllegalArgumentException("Refusing non-K1 donor archive: " + donor);
        }
        Path parent = evidencePath.getParent();
        if (parent == null || !Files.isDirectory(parent)) {
            throw new IllegalArgumentException("Evidence parent must already exist: " + evidencePath);
        }

        final Map<String, byte[]> replacements = new HashMap<String, byte[]>();
        final byte[][] levelRendererDonor = new byte[1][];
        try (ZipFile archive = new ZipFile(donor.toFile())) {
            Enumeration<? extends ZipEntry> entries = archive.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                String name = entry.getName();
                if (!name.startsWith("srg/") || !name.endsWith(".class")) continue;
                String type = name.substring(4, name.length() - 6);
                if (type.equals("net/minecraft/client/renderer/LevelRenderer")) {
                    try (InputStream input = archive.getInputStream(entry)) {
                        levelRendererDonor[0] = repairForge2612BlockOutline(readAll(input));
                        if (Boolean.getBoolean("usefultools.k1.outlineMaskTrial")) {
                            levelRendererDonor[0] = trialForge2612EntityOutline(levelRendererDonor[0]);
                        }
                    }
                    continue;
                }
                if (!selected(type)) continue;
                try (InputStream input = archive.getInputStream(entry)) {
                    byte[] replacement = readAll(input);
                    if (type.equals("net/minecraft/client/renderer/texture/TextureAtlasSprite")) {
                        replacement = widenTextureAtlasSpriteConstructor(replacement);
                    } else if (type.equals("net/minecraft/client/renderer/texture/SpriteContents")) {
                        replacement = repairSpriteContents(replacement);
                    } else if (type.equals("com/mojang/blaze3d/systems/GpuDevice")) {
                        replacement = addGpuDeviceUnstenciledOverloads(replacement);
                    } else if (type.equals("net/minecraft/client/renderer/texture/TextureAtlas")) {
                        replacement = guardTextureAtlasEmptyUploads(replacement);
                    } else if (type.equals("com/mojang/blaze3d/opengl/GlCommandEncoder")
                            && Boolean.getBoolean("usefultools.k1.outlineMaskTrial")) {
                        replacement = trialNativeOutlinePipelines(replacement);
                    }
                    replacements.put(type, replacement);
                }
            }
        }
        require(replacements, "com/mojang/blaze3d/opengl/GlStateManager");
        require(replacements, "com/mojang/blaze3d/textures/GpuTexture");
        require(replacements, "com/mojang/blaze3d/textures/GpuTexture$Usage");
        require(replacements, "com/mojang/blaze3d/vertex/VertexFormat");
        require(replacements, "net/minecraft/client/renderer/GameRenderer");
        require(replacements, "net/minecraft/client/gui/render/GuiRenderer");
        require(replacements, "com/mojang/blaze3d/systems/GpuDevice");
        require(replacements, "com/mojang/blaze3d/opengl/GlDevice");
        require(replacements, "com/mojang/blaze3d/opengl/GlCommandEncoder");
        require(replacements, "com/mojang/blaze3d/pipeline/RenderTarget");
        require(replacements, "net/minecraft/client/gui/components/BossHealthOverlay");
        require(replacements, "net/minecraft/client/Camera");
        require(replacements, "net/minecraft/client/renderer/culling/Frustum");
        require(replacements, "net/minecraft/client/renderer/entity/ItemFrameRenderer");
        require(replacements, "com/mojang/blaze3d/vertex/QuadInstance");
        require(replacements, "net/minecraft/client/gui/GuiGraphicsExtractor");
        require(replacements, "com/mojang/blaze3d/opengl/GlTexture");
        require(replacements, "net/minecraft/client/renderer/fog/FogRenderer");
        require(replacements, "net/minecraft/client/gui/screens/LoadingOverlay");
        require(replacements, "net/minecraft/client/renderer/texture/TextureAtlas");
        require(replacements, "net/minecraft/client/renderer/texture/TextureAtlasSprite");
        require(replacements, "net/minecraft/client/renderer/texture/SpriteContents");
        require(replacements, "net/minecraft/client/renderer/texture/MipmapGenerator");
        require(replacements, "net/minecraft/client/resources/model/ModelManager");
        require(replacements, "net/minecraft/client/resources/model/geometry/QuadCollection");
        require(replacements, "net/minecraft/client/renderer/chunk/RenderSectionRegion");
        require(replacements, "net/minecraft/client/renderer/chunk/SectionCompiler");
        require(replacements, "net/minecraft/client/renderer/chunk/SectionCompiler$Results");
        require(replacements, "net/minecraft/world/level/block/state/BlockState");
        require(replacements, "net/minecraft/client/renderer/SubmitNodeStorage$ModelSubmit");
        require(replacements, "net/minecraft/client/renderer/block/FluidRenderer");
        require(replacements, "net/minecraft/resources/Identifier");
        require(replacements, "net/minecraft/client/Options");
        if (levelRendererDonor[0] == null) {
            throw new IllegalArgumentException("K1 donor lacks LevelRenderer");
        }

        final FileOutputStream evidence = new FileOutputStream(evidencePath.toFile());
        evidence.write(("OPTIFINE_2612_K1_ADMISSION donor_sha256=" + DONOR_SHA256 + "\n")
                .getBytes(StandardCharsets.UTF_8));
        boolean gpuPreloaded = false;
        for (Class<?> loaded : instrumentation.getAllLoadedClasses()) {
            if (!loaded.getName().equals("com.mojang.blaze3d.systems.GpuDevice")) continue;
            gpuPreloaded = true;
            evidence.write(("PRELOADED com/mojang/blaze3d/systems/GpuDevice loader="
                    + String.valueOf(loaded.getClassLoader()) + " modifiable="
                    + instrumentation.isModifiableClass(loaded) + "\n")
                    .getBytes(StandardCharsets.UTF_8));
        }
        if (!gpuPreloaded) evidence.write("PRELOADED com/mojang/blaze3d/systems/GpuDevice false\n"
                .getBytes(StandardCharsets.UTF_8));
        evidence.flush();
        instrumentation.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String name, Class<?> redefining,
                                    ProtectionDomain domain, byte[] original) {
                byte[] replacement = name.equals("net/minecraft/client/renderer/LevelRenderer")
                        ? (Boolean.getBoolean("usefultools.k1.donorLevelRenderer")
                                ? levelRendererDonor[0] : addLevelRendererFrameCount(original))
                        : name.equals("net/minecraft/network/syncher/SynchedEntityData")
                        ? addOptifineEntityDataFields(original)
                        : name.equals("net/minecraft/client/renderer/block/ModelBlockRenderer")
                        ? addModelBlockLightCache(original)
                        : name.equals("net/minecraft/core/Direction")
                        ? widenDirectionValues(original)
                        : name.equals("net/minecraft/client/multiplayer/ClientLevel")
                        ? addClientLevelPlayerUpdate(original)
                        : name.equals("net/minecraft/client/renderer/SubmitNodeStorage")
                        ? addSubmitNodeShaderStorage(original)
                        : name.equals("net/minecraft/client/gui/GuiGraphicsExtractor")
                        ? addGuiGraphicsExtractorBlit(original, replacements.get(name))
                        : name.equals("net/minecraft/client/renderer/entity/EntityRenderDispatcher")
                        ? addEntityRenderDispatcherContext(original)
                        : name.equals("net/optifine/BetterGrass")
                        ? guardBetterGrassRefresh(original) : replacements.get(name);
                if (replacement == null) return null;
                synchronized (evidence) {
                    try {
                        evidence.write(("REPLACE " + name + " original_sha256=" + sha256(original)
                                + " donor_sha256=" + sha256(replacement) + "\n")
                                .getBytes(StandardCharsets.UTF_8));
                        evidence.flush();
                    } catch (IOException error) {
                        throw new IllegalStateException("Cannot retain K1 bridge evidence", error);
                    }
                }
                return replacement;
            }
        }, false);
    }

    private static boolean selected(String name) {
        return name.equals("com/mojang/blaze3d/opengl/GlStateManager")
                || name.startsWith("com/mojang/blaze3d/opengl/GlStateManager$")
                || name.equals("com/mojang/blaze3d/textures/GpuTexture")
                || name.equals("com/mojang/blaze3d/textures/GpuTexture$Usage")
                || name.equals("com/mojang/blaze3d/vertex/VertexFormat")
                || name.startsWith("com/mojang/blaze3d/vertex/VertexFormat$")
                || name.equals("net/minecraft/client/renderer/GameRenderer")
                || name.startsWith("net/minecraft/client/renderer/GameRenderer$")
                || name.equals("net/minecraft/client/gui/render/GuiRenderer")
                || name.startsWith("net/minecraft/client/gui/render/GuiRenderer$")
                || name.equals("com/mojang/blaze3d/systems/GpuDevice")
                || name.equals("com/mojang/blaze3d/opengl/GlDevice")
                || name.startsWith("com/mojang/blaze3d/opengl/GlDevice$")
                || name.equals("com/mojang/blaze3d/opengl/GlCommandEncoder")
                || name.equals("com/mojang/blaze3d/pipeline/RenderTarget")
                || name.equals("net/minecraft/client/gui/components/BossHealthOverlay")
                || name.equals("net/minecraft/client/Camera")
                || name.equals("net/minecraft/client/renderer/culling/Frustum")
                || name.equals("net/minecraft/client/renderer/entity/ItemFrameRenderer")
                || name.equals("com/mojang/blaze3d/vertex/QuadInstance")
                || name.equals("net/minecraft/client/gui/GuiGraphicsExtractor")
                || name.equals("com/mojang/blaze3d/opengl/GlTexture")
                || name.equals("net/minecraft/client/renderer/fog/FogRenderer")
                || name.equals("net/minecraft/client/gui/screens/LoadingOverlay")
                || name.startsWith("net/minecraft/client/gui/screens/LoadingOverlay$")
                || name.equals("net/minecraft/client/renderer/texture/TextureAtlas")
                || name.equals("net/minecraft/client/renderer/texture/TextureAtlasSprite")
                || name.equals("net/minecraft/client/renderer/texture/SpriteContents")
                || name.equals("net/minecraft/client/renderer/texture/MipmapGenerator")
                || name.startsWith("net/minecraft/client/renderer/texture/SpriteContents$")
                || name.equals("net/minecraft/client/resources/model/ModelManager")
                || name.startsWith("net/minecraft/client/resources/model/ModelManager$")
                || name.equals("net/minecraft/client/resources/model/geometry/QuadCollection")
                || name.startsWith("net/minecraft/client/resources/model/geometry/QuadCollection$")
                || name.equals("net/minecraft/client/renderer/chunk/RenderSectionRegion")
                || name.equals("net/minecraft/client/renderer/chunk/SectionCompiler")
                || name.startsWith("net/minecraft/client/renderer/chunk/SectionCompiler$")
                || name.equals("net/minecraft/world/level/block/state/BlockState")
                || name.equals("net/minecraft/client/renderer/SubmitNodeStorage$ModelSubmit")
                || name.equals("net/minecraft/client/renderer/block/FluidRenderer")
                || name.equals("net/minecraft/resources/Identifier")
                || name.startsWith("net/minecraft/resources/Identifier$")
                || name.equals("net/minecraft/client/Options")
                || name.startsWith("net/minecraft/client/Options$");
    }

    private static void require(Map<String, byte[]> classes, String name) {
        if (!classes.containsKey(name)) throw new IllegalArgumentException("K1 donor lacks " + name);
    }

    private static byte[] readAll(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[8192];
        int count;
        while ((count = input.read(buffer)) != -1) output.write(buffer, 0, count);
        return output.toByteArray();
    }

    static byte[] widenTextureAtlasSpriteConstructor(byte[] donor) {
        ClassReader reader = new ClassReader(donor);
        ClassWriter writer = new ClassWriter(reader, 0);
        int[] matched = new int[1];
        reader.accept(new ClassVisitor(Opcodes.ASM9, writer) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor,
                                             String signature, String[] exceptions) {
                if (name.equals("<init>") && descriptor.equals(
                        "(Lnet/minecraft/resources/Identifier;Lnet/minecraft/client/renderer/texture/SpriteContents;IIIII)V")) {
                    if ((access & Opcodes.ACC_PROTECTED) == 0 || (access & Opcodes.ACC_PUBLIC) != 0) {
                        throw new IllegalArgumentException("Unexpected K1 TextureAtlasSprite constructor access");
                    }
                    access = (access & ~(Opcodes.ACC_PRIVATE | Opcodes.ACC_PROTECTED))
                            | Opcodes.ACC_PUBLIC;
                    matched[0]++;
                }
                return super.visitMethod(access, name, descriptor, signature, exceptions);
            }
        }, 0);
        if (matched[0] != 1) {
            throw new IllegalArgumentException("Expected one K1 TextureAtlasSprite constructor, found "
                    + matched[0]);
        }
        return writer.toByteArray();
    }

    static byte[] guardTextureAtlasEmptyUploads(byte[] donor) {
        ClassReader reader = new ClassReader(donor);
        ClassWriter writer = new ClassWriter(reader, 0);
        int[] matched = new int[1];
        reader.accept(new ClassVisitor(Opcodes.ASM9, writer) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor,
                                             String signature, String[] exceptions) {
                MethodVisitor delegate = super.visitMethod(access, name, descriptor,
                        signature, exceptions);
                if (!name.equals("uploadInitialContents") || !descriptor.equals(
                        "(Lnet/optifine/shaders/ShadersTextureType;)V")) return delegate;
                return new MethodVisitor(Opcodes.ASM9, delegate) {
                    @Override
                    public void visitTypeInsn(int opcode, String type) {
                        if (opcode == Opcodes.NEW && type.equals("java/util/ArrayList")) {
                            if (++matched[0] != 1) {
                                throw new IllegalArgumentException("Unexpected K1 atlas upload allocation");
                            }
                            Label nonempty = new Label();
                            super.visitVarInsn(Opcodes.ALOAD, 6);
                            super.visitMethodInsn(Opcodes.INVOKEINTERFACE, "java/util/List",
                                    "isEmpty", "()Z", true);
                            super.visitJumpInsn(Opcodes.IFEQ, nonempty);
                            super.visitInsn(Opcodes.RETURN);
                            super.visitLabel(nonempty);
                            super.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
                        }
                        super.visitTypeInsn(opcode, type);
                    }
                };
            }
        }, 0);
        if (matched[0] != 1) {
            throw new IllegalArgumentException("Expected one K1 atlas upload allocation, found "
                    + matched[0]);
        }
        return writer.toByteArray();
    }

    static byte[] repairSpriteContents(byte[] donor) {
        ClassReader reader = new ClassReader(donor);
        ClassWriter writer = new ClassWriter(reader, 0);
        int[] matched = new int[2];
        int[] blenderCalls = new int[1];
        String sprite = "net/minecraft/client/renderer/texture/SpriteContents";
        String atlas = "net/minecraft/client/renderer/texture/TextureAtlas";
        String shadersType = "net/optifine/shaders/ShadersTextureType";
        String blender = "net/optifine/texture/IColorBlender";
        String originalDescriptor = "(L" + shadersType + ";)L" + blender + ";";
        String helperDescriptor = "(L" + atlas + ";L" + shadersType + ";)L" + blender + ";";
        reader.accept(new ClassVisitor(Opcodes.ASM9, writer) {
            @Override
            public FieldVisitor visitField(int access, String name, String descriptor,
                                           String signature, Object value) {
                int index = name.equals("width") ? 0 : name.equals("height") ? 1 : -1;
                if (index >= 0 && descriptor.equals("I")) {
                    if ((access & (Opcodes.ACC_PRIVATE | Opcodes.ACC_PROTECTED)) == 0) {
                        throw new IllegalArgumentException("Unexpected K1 SpriteContents "
                                + name + " access");
                    }
                    access = (access & ~Opcodes.ACC_PRIVATE) | Opcodes.ACC_PROTECTED;
                    matched[index]++;
                }
                return super.visitField(access, name, descriptor, signature, value);
            }
            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor,
                                             String signature, String[] exceptions) {
                if (name.equals("usefulToolsSafeColorBlender")) {
                    throw new IllegalArgumentException("K1 SpriteContents helper name already present");
                }
                MethodVisitor delegate = super.visitMethod(access, name, descriptor,
                        signature, exceptions);
                if (!name.equals("increaseMipLevel") || !descriptor.equals("(I)V")) return delegate;
                return new MethodVisitor(Opcodes.ASM9, delegate) {
                    @Override
                    public void visitMethodInsn(int opcode, String owner, String method,
                                                String callDescriptor, boolean isInterface) {
                        if (opcode == Opcodes.INVOKEVIRTUAL && owner.equals(atlas)
                                && method.equals("getShadersColorBlender")
                                && callDescriptor.equals(originalDescriptor)) {
                            super.visitMethodInsn(Opcodes.INVOKESTATIC, sprite,
                                    "usefulToolsSafeColorBlender", helperDescriptor, false);
                            blenderCalls[0]++;
                        } else {
                            super.visitMethodInsn(opcode, owner, method, callDescriptor, isInterface);
                        }
                    }
                };
            }
            @Override
            public void visitEnd() {
                MethodVisitor method = super.visitMethod(Opcodes.ACC_PRIVATE | Opcodes.ACC_STATIC
                        | Opcodes.ACC_SYNTHETIC, "usefulToolsSafeColorBlender", helperDescriptor,
                        null, null);
                Label nonnull = new Label();
                method.visitCode();
                method.visitVarInsn(Opcodes.ALOAD, 0);
                method.visitJumpInsn(Opcodes.IFNONNULL, nonnull);
                method.visitInsn(Opcodes.ACONST_NULL);
                method.visitInsn(Opcodes.ARETURN);
                method.visitLabel(nonnull);
                method.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
                method.visitVarInsn(Opcodes.ALOAD, 0);
                method.visitVarInsn(Opcodes.ALOAD, 1);
                method.visitMethodInsn(Opcodes.INVOKEVIRTUAL, atlas,
                        "getShadersColorBlender", originalDescriptor, false);
                method.visitInsn(Opcodes.ARETURN);
                method.visitMaxs(2, 2);
                method.visitEnd();
                super.visitEnd();
            }
        }, 0);
        if (matched[0] != 1 || matched[1] != 1 || blenderCalls[0] != 1) {
            throw new IllegalArgumentException("Expected K1 SpriteContents width/height and blender call once: "
                    + matched[0] + "/" + matched[1] + "/" + blenderCalls[0]);
        }
        return writer.toByteArray();
    }

    static byte[] addLevelRendererFrameCount(byte[] original) {
        ClassReader reader = new ClassReader(original);
        ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_MAXS);
        int[] endFrames = new int[1];
        int[] constructors = new int[1];
        int[] refreshes = new int[1];
        int[] levels = new int[1];
        String renderer = "net/minecraft/client/renderer/LevelRenderer";
        reader.accept(new ClassVisitor(Opcodes.ASM9, writer) {
            @Override
            public org.objectweb.asm.FieldVisitor visitField(int access, String name,
                                                               String descriptor, String signature,
                                                               Object value) {
                if (name.equals("level") && descriptor.equals(
                        "Lnet/minecraft/client/multiplayer/ClientLevel;")) {
                    levels[0]++;
                    access = (access & ~Opcodes.ACC_PRIVATE) | Opcodes.ACC_PROTECTED;
                }
                if (name.equals("renderInfosTerrain") || name.equals("renderInfosTileEntities")
                        || name.equals("renderDistance") || name.equals("renderDistanceXZSq")) {
                    throw new IllegalArgumentException("K1 LevelRenderer donor field already present: " + name);
                }
                return super.visitField(access, name, descriptor, signature, value);
            }

            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor,
                                             String signature, String[] exceptions) {
                if (name.equals("getFrameCount") || name.equals("onPlayerPositionSet")
                        || name.equals("getRenderInfos") || name.equals("getRenderInfosTerrain")
                        || name.equals("getRenderInfosTileEntities")
                        || name.equals("onChunkRenderNeedsUpdate")) {
                    throw new IllegalArgumentException("K1 LevelRenderer helper already present: " + name);
                }
                MethodVisitor delegate = super.visitMethod(access, name, descriptor,
                        signature, exceptions);
                if (name.equals("<init>")) {
                    constructors[0]++;
                    return new MethodVisitor(Opcodes.ASM9, delegate) {
                        @Override
                        public void visitInsn(int opcode) {
                            if (opcode == Opcodes.RETURN) {
                                super.visitVarInsn(Opcodes.ALOAD, 0);
                                super.visitTypeInsn(Opcodes.NEW,
                                        "it/unimi/dsi/fastutil/objects/ObjectArrayList");
                                super.visitInsn(Opcodes.DUP);
                                super.visitMethodInsn(Opcodes.INVOKESPECIAL,
                                        "it/unimi/dsi/fastutil/objects/ObjectArrayList",
                                        "<init>", "()V", false);
                                super.visitFieldInsn(Opcodes.PUTFIELD, renderer,
                                        "renderInfosTerrain",
                                        "Lit/unimi/dsi/fastutil/objects/ObjectArrayList;");
                                super.visitVarInsn(Opcodes.ALOAD, 0);
                                super.visitTypeInsn(Opcodes.NEW, "java/util/ArrayList");
                                super.visitInsn(Opcodes.DUP);
                                super.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/util/ArrayList",
                                        "<init>", "()V", false);
                                super.visitFieldInsn(Opcodes.PUTFIELD, renderer,
                                        "renderInfosTileEntities", "Ljava/util/List;");
                            }
                            super.visitInsn(opcode);
                        }
                    };
                }
                if (name.equals("allChanged") && descriptor.equals("()V")) {
                    refreshes[0]++;
                    return new MethodVisitor(Opcodes.ASM9, delegate) {
                        @Override
                        public void visitCode() {
                            super.visitCode();
                            super.visitVarInsn(Opcodes.ALOAD, 0);
                            super.visitVarInsn(Opcodes.ALOAD, 0);
                            super.visitFieldInsn(Opcodes.GETFIELD, renderer, "minecraft",
                                    "Lnet/minecraft/client/Minecraft;");
                            super.visitFieldInsn(Opcodes.GETFIELD,
                                    "net/minecraft/client/Minecraft", "options",
                                    "Lnet/minecraft/client/Options;");
                            super.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                                    "net/minecraft/client/Options", "getEffectiveRenderDistance",
                                    "()I", false);
                            super.visitIntInsn(Opcodes.BIPUSH, 16);
                            super.visitInsn(Opcodes.IMUL);
                            super.visitFieldInsn(Opcodes.PUTFIELD, renderer, "renderDistance", "I");
                            super.visitVarInsn(Opcodes.ALOAD, 0);
                            super.visitVarInsn(Opcodes.ALOAD, 0);
                            super.visitFieldInsn(Opcodes.GETFIELD, renderer, "minecraft",
                                    "Lnet/minecraft/client/Minecraft;");
                            super.visitFieldInsn(Opcodes.GETFIELD,
                                    "net/minecraft/client/Minecraft", "options",
                                    "Lnet/minecraft/client/Options;");
                            super.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                                    "net/minecraft/client/Options", "getEffectiveRenderDistance",
                                    "()I", false);
                            super.visitInsn(Opcodes.ICONST_1);
                            super.visitInsn(Opcodes.IADD);
                            super.visitIntInsn(Opcodes.BIPUSH, 16);
                            super.visitInsn(Opcodes.IMUL);
                            super.visitInsn(Opcodes.DUP);
                            super.visitInsn(Opcodes.IMUL);
                            super.visitFieldInsn(Opcodes.PUTFIELD, renderer,
                                    "renderDistanceXZSq", "I");
                        }
                    };
                }
                if (!name.equals("endFrame") || !descriptor.equals("()V")) return delegate;
                endFrames[0]++;
                return new MethodVisitor(Opcodes.ASM9, delegate) {
                    @Override
                    public void visitCode() {
                        super.visitCode();
                        super.visitVarInsn(Opcodes.ALOAD, 0);
                        super.visitInsn(Opcodes.DUP);
                        super.visitFieldInsn(Opcodes.GETFIELD, renderer, "usefulToolsFrameCount", "I");
                        super.visitInsn(Opcodes.ICONST_1);
                        super.visitInsn(Opcodes.IADD);
                        super.visitFieldInsn(Opcodes.PUTFIELD, renderer, "usefulToolsFrameCount", "I");
                    }
                };
            }
            @Override
            public void visitEnd() {
                super.visitField(Opcodes.ACC_PRIVATE, "usefulToolsFrameCount", "I",
                        null, null).visitEnd();
                // The K1 donor initializes this flag to false. Retain its conditional
                // reload behavior if an integration later marks the first load.
                super.visitField(Opcodes.ACC_PRIVATE, "firstWorldLoad", "Z",
                        null, null).visitEnd();
                super.visitField(Opcodes.ACC_PRIVATE, "renderInfosTerrain",
                        "Lit/unimi/dsi/fastutil/objects/ObjectArrayList;", null, null).visitEnd();
                super.visitField(Opcodes.ACC_PRIVATE, "renderInfosTileEntities",
                        "Ljava/util/List;", null, null).visitEnd();
                super.visitField(Opcodes.ACC_PROTECTED, "renderDistance", "I",
                        null, null).visitEnd();
                super.visitField(Opcodes.ACC_PROTECTED, "renderDistanceXZSq", "I",
                        null, null).visitEnd();
                MethodVisitor method = super.visitMethod(Opcodes.ACC_PUBLIC, "getFrameCount", "()I",
                        null, null);
                method.visitCode();
                method.visitVarInsn(Opcodes.ALOAD, 0);
                method.visitFieldInsn(Opcodes.GETFIELD, renderer, "usefulToolsFrameCount", "I");
                method.visitInsn(Opcodes.IRETURN);
                method.visitMaxs(1, 1);
                method.visitEnd();
                MethodVisitor position = super.visitMethod(Opcodes.ACC_PUBLIC,
                        "onPlayerPositionSet", "()V", null, null);
                position.visitCode();
                position.visitVarInsn(Opcodes.ALOAD, 0);
                position.visitFieldInsn(Opcodes.GETFIELD, renderer, "firstWorldLoad", "Z");
                org.objectweb.asm.Label done = new org.objectweb.asm.Label();
                position.visitJumpInsn(Opcodes.IFEQ, done);
                position.visitVarInsn(Opcodes.ALOAD, 0);
                position.visitMethodInsn(Opcodes.INVOKEVIRTUAL, renderer, "allChanged", "()V", false);
                position.visitVarInsn(Opcodes.ALOAD, 0);
                position.visitInsn(Opcodes.ICONST_0);
                position.visitFieldInsn(Opcodes.PUTFIELD, renderer, "firstWorldLoad", "Z");
                position.visitLabel(done);
                position.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
                position.visitInsn(Opcodes.RETURN);
                position.visitMaxs(2, 1);
                position.visitEnd();
                MethodVisitor chunkUpdate = super.visitMethod(Opcodes.ACC_PUBLIC,
                        "onChunkRenderNeedsUpdate",
                        "(Lnet/minecraft/client/renderer/chunk/SectionRenderDispatcher$RenderSection;)V",
                        null, null);
                chunkUpdate.visitCode();
                chunkUpdate.visitVarInsn(Opcodes.ALOAD, 1);
                chunkUpdate.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                        "net/minecraft/client/renderer/chunk/SectionRenderDispatcher$RenderSection",
                        "getSectionMesh", "()Lnet/minecraft/client/renderer/chunk/SectionMesh;", false);
                chunkUpdate.visitMethodInsn(Opcodes.INVOKEINTERFACE,
                        "net/minecraft/client/renderer/chunk/SectionMesh",
                        "hasTerrainBlockEntities", "()Z", true);
                chunkUpdate.visitInsn(Opcodes.POP);
                chunkUpdate.visitInsn(Opcodes.RETURN);
                chunkUpdate.visitMaxs(1, 2);
                chunkUpdate.visitEnd();
                addListGetter("getRenderInfos", "visibleSections",
                        "Lit/unimi/dsi/fastutil/objects/ObjectArrayList;");
                addListGetter("getRenderInfosTerrain", "renderInfosTerrain",
                        "Lit/unimi/dsi/fastutil/objects/ObjectArrayList;");
                addListGetter("getRenderInfosTileEntities", "renderInfosTileEntities",
                        "Ljava/util/List;");
                super.visitEnd();
            }

            private void addListGetter(String name, String field, String fieldDescriptor) {
                MethodVisitor getter = super.visitMethod(Opcodes.ACC_PUBLIC, name,
                        "()Ljava/util/List;", null, null);
                getter.visitCode();
                getter.visitVarInsn(Opcodes.ALOAD, 0);
                getter.visitFieldInsn(Opcodes.GETFIELD, renderer, field, fieldDescriptor);
                getter.visitInsn(Opcodes.ARETURN);
                getter.visitMaxs(1, 1);
                getter.visitEnd();
            }
        }, 0);
        if (endFrames[0] != 1 || constructors[0] != 1 || refreshes[0] != 1
                || levels[0] != 1) {
            throw new IllegalArgumentException("Expected K1 LevelRenderer endFrame/ctor/allChanged/level once: "
                    + endFrames[0] + "/" + constructors[0] + "/" + refreshes[0]
                    + "/" + levels[0]);
        }
        return writer.toByteArray();
    }

    /** K1's extracted renderer targets an outline constructor absent from pinned Forge 64.0.8. */
    static byte[] repairForge2612BlockOutline(byte[] donor) {
        ClassNode type = new ClassNode(Opcodes.ASM9);
        new ClassReader(donor).accept(type, 0);
        if (!type.name.equals("net/minecraft/client/renderer/LevelRenderer")) {
            throw new IllegalArgumentException("Unexpected K1 outline donor class");
        }
        String owner = "net/minecraft/client/renderer/state/level/BlockOutlineRenderState";
        String four = "(Lnet/minecraft/core/BlockPos;ZZLnet/minecraft/world/phys/shapes/VoxelShape;)V";
        String five = "(Lnet/minecraft/core/BlockPos;ZZLnet/minecraft/world/phys/shapes/VoxelShape;"
                + "Lnet/minecraft/world/level/block/state/BlockState;)V";
        int changed = 0;
        int stateLookups = 0;
        for (MethodNode method : type.methods) {
            if (!(method.name.equals("extractBlockOutline") || method.name.equals("renderHitOutline"))) continue;
            for (AbstractInsnNode instruction : method.instructions.toArray()) {
                if (!(instruction instanceof MethodInsnNode call) || !call.owner.equals(owner)) continue;
                if (method.name.equals("extractBlockOutline")
                        && call.getOpcode() == Opcodes.INVOKESPECIAL
                        && call.name.equals("<init>") && call.desc.equals(five)) {
                    // Forge's four-argument overload preserves the rendered shape. The extra
                    // BlockState belongs to K1's absent constructor and is not consumed here.
                    method.instructions.insertBefore(call, new InsnNode(Opcodes.POP));
                    call.desc = four;
                    changed++;
                } else if (method.name.equals("renderHitOutline")
                        && call.getOpcode() == Opcodes.INVOKEVIRTUAL
                        && call.name.equals("blockState")
                        && call.desc.equals("()Lnet/minecraft/world/level/block/state/BlockState;")) {
                    // K1's custom-model guard still needs the target BlockState. Obtain it
                    // from the native client level at the outline position.
                    call.name = "pos";
                    call.desc = "()Lnet/minecraft/core/BlockPos;";
                    InsnList lookup = new InsnList();
                    lookup.add(new VarInsnNode(Opcodes.ALOAD, 0));
                    lookup.add(new FieldInsnNode(Opcodes.GETFIELD,
                            type.name, "level", "Lnet/minecraft/client/multiplayer/ClientLevel;"));
                    lookup.add(new InsnNode(Opcodes.SWAP));
                    lookup.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL,
                            "net/minecraft/client/multiplayer/ClientLevel", "getBlockState",
                            "(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;", false));
                    method.instructions.insert(call, lookup);
                    stateLookups++;
                }
            }
        }
        if (changed != 1 || stateLookups != 1) {
            throw new IllegalArgumentException("Expected one K1 block-outline constructor and state lookup, found "
                    + changed + "/" + stateLookups);
        }
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        type.accept(writer);
        return writer.toByteArray();
    }

    /** Private trial: admit entity outlines under an active pack outside its shadow pass. */
    static byte[] trialForge2612EntityOutline(byte[] donor) {
        ClassNode type = new ClassNode(Opcodes.ASM9);
        new ClassReader(donor).accept(type, 0);
        int changed = 0;
        for (MethodNode method : type.methods) {
            if (!method.name.equals("shouldShowEntityOutlines") || !method.desc.equals("()Z")) continue;
            for (AbstractInsnNode instruction : method.instructions.toArray()) {
                if (!(instruction instanceof MethodInsnNode call)
                        || call.getOpcode() != Opcodes.INVOKESTATIC
                        || !call.owner.equals("net/optifine/Config")
                        || !call.name.equals("isShaders") || !call.desc.equals("()Z")) continue;
                method.instructions.set(call, new FieldInsnNode(Opcodes.GETSTATIC,
                        "net/optifine/shaders/Shaders", "isShadowPass", "Z"));
                changed++;
            }
        }
        if (changed != 1) throw new IllegalArgumentException("K1 entity-outline guard count: " + changed);
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        type.accept(writer);
        return writer.toByteArray();
    }

    /** Private trial: keep native GPU programs for both mask draw variants and the blit. */
    static byte[] trialNativeOutlinePipelines(byte[] donor) {
        ClassNode type = new ClassNode(Opcodes.ASM9);
        new ClassReader(donor).accept(type, 0);
        if (!type.name.equals("com/mojang/blaze3d/opengl/GlCommandEncoder")) {
            throw new IllegalArgumentException("Unexpected K1 encoder donor class");
        }
        int changed = 0;
        for (MethodNode method : type.methods) {
            if (!method.name.equals("trySetup")) continue;
            for (AbstractInsnNode instruction : method.instructions.toArray()) {
                if (!(instruction instanceof MethodInsnNode call)
                        || call.getOpcode() != Opcodes.INVOKESTATIC
                        || !call.owner.equals("net/optifine/Config")
                        || !call.name.equals("isShaders") || !call.desc.equals("()Z")) continue;
                AbstractInsnNode next = instruction.getNext();
                while (next != null && next.getOpcode() < 0) next = next.getNext();
                if (next == null || next.getOpcode() != Opcodes.IFEQ) continue;
                AbstractInsnNode after = next.getNext();
                while (after != null && after.getOpcode() < 0) after = after.getNext();
                if (!(after instanceof FieldInsnNode field)
                        || !field.owner.equals("net/optifine/shaders/Shaders")
                        || !field.name.equals("activeProgramID")) continue;
                InsnList guard = new InsnList();
                // trySetup evaluates this guard for ordinary draws too. Read
                // the pipeline once instead of repeating the pass field and
                // info() accessor for every outline comparison.
                int pipelineLocal = method.maxLocals++;
                guard.add(new VarInsnNode(Opcodes.ALOAD, 1));
                guard.add(new FieldInsnNode(Opcodes.GETFIELD,
                        "com/mojang/blaze3d/opengl/GlRenderPass", "pipeline",
                        "Lcom/mojang/blaze3d/opengl/GlRenderPipeline;"));
                guard.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL,
                        "com/mojang/blaze3d/opengl/GlRenderPipeline", "info",
                        "()Lcom/mojang/blaze3d/pipeline/RenderPipeline;", false));
                guard.add(new VarInsnNode(Opcodes.ASTORE, pipelineLocal));
                String[] outlinePipelines = {"OUTLINE_CULL", "OUTLINE_NO_CULL", "ENTITY_OUTLINE_BLIT"};
                for (int index = 0; index < outlinePipelines.length; index++) {
                    guard.add(new VarInsnNode(Opcodes.ALOAD, pipelineLocal));
                    guard.add(new FieldInsnNode(Opcodes.GETSTATIC,
                            "net/minecraft/client/renderer/RenderPipelines", outlinePipelines[index],
                            "Lcom/mojang/blaze3d/pipeline/RenderPipeline;"));
                    guard.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL,
                            "java/lang/Object", "equals", "(Ljava/lang/Object;)Z", false));
                    if (index != 0) guard.add(new InsnNode(Opcodes.IOR));
                }
                guard.add(new VarInsnNode(Opcodes.ALOAD, pipelineLocal));
                guard.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL,
                        "com/mojang/blaze3d/pipeline/RenderPipeline", "getLocation",
                        "()Lnet/minecraft/resources/Identifier;", false));
                guard.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL,
                        "net/minecraft/resources/Identifier", "getPath", "()Ljava/lang/String;", false));
                guard.add(new org.objectweb.asm.tree.LdcInsnNode("entity_outline"));
                guard.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL,
                        "java/lang/String", "startsWith", "(Ljava/lang/String;)Z", false));
                guard.add(new InsnNode(Opcodes.IOR));
                guard.add(new InsnNode(Opcodes.ICONST_1));
                guard.add(new InsnNode(Opcodes.IXOR));
                guard.add(new InsnNode(Opcodes.IAND));
                method.instructions.insert(call, guard);
                changed++;
            }
        }
        if (changed != 1) throw new IllegalArgumentException("K1 native outline program guard count: " + changed);
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        type.accept(writer);
        return writer.toByteArray();
    }

    /**
     * Restore the one Forge runtime hook erased by K1 while preserving every
     * OptiFine-patched method body. The partially patched input must have the
     * exact donor member inventory minus only onPlayerPositionSet()V.
     */
    static byte[] mergeLevelRenderer(byte[] original, byte[] donor) {
        ClassNode input = new ClassNode(Opcodes.ASM9);
        new ClassReader(original).accept(input, 0);
        ClassNode expected = new ClassNode(Opcodes.ASM9);
        new ClassReader(donor).accept(expected, 0);
        if (!input.name.equals(expected.name) || !input.superName.equals(expected.superName)
                || !input.interfaces.equals(expected.interfaces)) {
            throw new IllegalArgumentException("K1 LevelRenderer class identity changed");
        }
        Map<String, Integer> expectedFields = fieldInventory(expected);
        Map<String, Integer> actualFields = fieldInventory(input);
        if (!actualFields.equals(expectedFields)) {
            throw new IllegalArgumentException("K1 LevelRenderer field inventory changed");
        }
        Map<String, Integer> expectedMethods = methodInventory(expected);
        Map<String, Integer> actualMethods = methodInventory(input);
        String hook = "onPlayerPositionSet()V";
        if (actualMethods.equals(expectedMethods)) return original;
        Map<String, Integer> withoutHook = new HashMap<String, Integer>(expectedMethods);
        Integer hookAccess = withoutHook.remove(hook);
        if (hookAccess == null || !actualMethods.equals(withoutHook)) {
            throw new IllegalArgumentException("K1 LevelRenderer method inventory changed outside " + hook);
        }
        MethodNode donorHook = null;
        for (MethodNode method : expected.methods) {
            if ((method.name + method.desc).equals(hook)) donorHook = method;
        }
        if (donorHook == null || (donorHook.access & Opcodes.ACC_PUBLIC) == 0) {
            throw new IllegalArgumentException("K1 donor LevelRenderer hook missing or non-public");
        }
        input.methods.add(donorHook);
        if (!methodInventory(input).equals(expectedMethods)) {
            throw new IllegalArgumentException("Merged K1 LevelRenderer member inventory is incomplete");
        }
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        input.accept(writer);
        return writer.toByteArray();
    }

    private static Map<String, Integer> fieldInventory(ClassNode node) {
        Map<String, Integer> result = new HashMap<String, Integer>();
        for (FieldNode field : node.fields) {
            result.put(field.name + ":" + field.desc, memberAccess(field.access));
        }
        return result;
    }

    private static Map<String, Integer> methodInventory(ClassNode node) {
        Map<String, Integer> result = new HashMap<String, Integer>();
        for (MethodNode method : node.methods) {
            result.put(method.name + method.desc, memberAccess(method.access));
        }
        return result;
    }

    private static int memberAccess(int access) {
        return access & (Opcodes.ACC_PUBLIC | Opcodes.ACC_PRIVATE | Opcodes.ACC_PROTECTED
                | Opcodes.ACC_STATIC | Opcodes.ACC_FINAL | Opcodes.ACC_ABSTRACT
                | Opcodes.ACC_SYNCHRONIZED | Opcodes.ACC_NATIVE | Opcodes.ACC_STRICT);
    }

    /** Restore K1's camera getter names without replacing Forge's frustum geometry. */
    static byte[] addFrustumCameraAliases(byte[] original) {
        ClassNode node = new ClassNode(Opcodes.ASM9);
        new ClassReader(original).accept(node, 0);
        String owner = "net/minecraft/client/renderer/culling/Frustum";
        if (!node.name.equals(owner)) throw new IllegalArgumentException("Unexpected Frustum identity");
        int aliases = 0;
        for (String axis : new String[] {"X", "Y", "Z"}) {
            String nativeName = "getCam" + axis;
            String optifineName = "getCamera" + axis;
            boolean nativeFound = false;
            boolean aliasFound = false;
            for (MethodNode method : node.methods) {
                if (method.name.equals(nativeName) && method.desc.equals("()D")
                        && (method.access & Opcodes.ACC_PUBLIC) != 0) nativeFound = true;
                if (method.name.equals(optifineName) && method.desc.equals("()D")) aliasFound = true;
            }
            if (!nativeFound) throw new IllegalArgumentException("Pinned Frustum lacks " + nativeName);
            if (aliasFound) throw new IllegalArgumentException("Frustum already has " + optifineName);
            MethodNode alias = new MethodNode(Opcodes.ASM9, Opcodes.ACC_PUBLIC,
                    optifineName, "()D", null, null);
            alias.visitCode();
            alias.visitVarInsn(Opcodes.ALOAD, 0);
            alias.visitMethodInsn(Opcodes.INVOKEVIRTUAL, owner, nativeName, "()D", false);
            alias.visitInsn(Opcodes.DRETURN);
            alias.visitMaxs(2, 1);
            alias.visitEnd();
            node.methods.add(alias);
            aliases++;
        }
        if (aliases != 3) throw new IllegalStateException("Expected all three Frustum aliases");
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        node.accept(writer);
        return writer.toByteArray();
    }

    /** K1 stores random-entity metadata alongside vanilla synchronized data. */
    static byte[] addOptifineEntityDataFields(byte[] original) {
        ClassNode node = new ClassNode(Opcodes.ASM9);
        new ClassReader(original).accept(node, 0);
        if (!node.name.equals("net/minecraft/network/syncher/SynchedEntityData")) {
            throw new IllegalArgumentException("Unexpected SynchedEntityData identity");
        }
        String[][] fields = {
                {"spawnBiome", "Lnet/minecraft/world/level/biome/Biome;"},
                {"spawnPosition", "Lnet/minecraft/core/BlockPos;"},
                {"blockStateOn", "Lnet/minecraft/world/level/block/state/BlockState;"},
                {"blockStateOnUpdateMs", "J"},
                {"modelVariables", "Ljava/util/Map;"},
                {"nbtTag", "Lnet/minecraft/nbt/CompoundTag;"},
                {"nbtTagUpdateMs", "J"},
        };
        for (String[] field : fields) {
            for (FieldNode existing : node.fields) {
                if (existing.name.equals(field[0])) {
                    throw new IllegalArgumentException("K1 field already present: " + field[0]);
                }
            }
            node.fields.add(new FieldNode(Opcodes.ACC_PUBLIC, field[0], field[1], null, null));
        }
        ClassWriter writer = new ClassWriter(0);
        node.accept(writer);
        return writer.toByteArray();
    }

    /** Preserve Forge's model-data renderer while restoring K1's light/cache context. */
    static byte[] addModelBlockLightCache(byte[] original) {
        String owner = "net/minecraft/client/renderer/block/ModelBlockRenderer";
        String cache = "Lnet/optifine/render/LightCacheOF;";
        String environment = "Lnet/optifine/render/RenderEnv;";
        ClassReader reader = new ClassReader(original);
        ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_MAXS);
        int[] clinit = new int[1];
        int[] returns = new int[1];
        int[] constructors = new int[1];
        int[] constructorReturns = new int[1];
        reader.accept(new ClassVisitor(Opcodes.ASM9, writer) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor,
                                             String signature, String[] exceptions) {
                if (name.equals("getLightCacheOf") || name.equals("getRenderEnv")
                        || name.equals("setMidBlock")
                        || name.equals("fixAoLightValue")
                        || name.equals("updateAoLightValue")
                        || name.equals("isSeparateAoLightValue")
                        || name.equals("isAmbientOcclusion")
                        || name.equals("isCull")
                        || name.equals("getBlockColors")
                        || name.equals("usefulToolsLightCacheOf")) {
                    throw new IllegalArgumentException("K1 model-block helper already present: " + name);
                }
                MethodVisitor delegate = super.visitMethod(access, name, descriptor,
                        signature, exceptions);
                if (name.equals("<init>") && descriptor.equals(
                        "(ZZLnet/minecraft/client/color/block/BlockColors;)V")) {
                    constructors[0]++;
                    return new MethodVisitor(Opcodes.ASM9, delegate) {
                        @Override
                        public void visitInsn(int opcode) {
                            if (opcode == Opcodes.RETURN) {
                                constructorReturns[0]++;
                                super.visitVarInsn(Opcodes.ALOAD, 0);
                                super.visitTypeInsn(Opcodes.NEW, "net/optifine/render/RenderEnv");
                                super.visitInsn(Opcodes.DUP);
                                super.visitFieldInsn(Opcodes.GETSTATIC,
                                        "net/minecraft/world/level/block/Blocks", "AIR",
                                        "Lnet/minecraft/world/level/block/Block;");
                                super.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                                        "net/minecraft/world/level/block/Block", "defaultBlockState",
                                        "()Lnet/minecraft/world/level/block/state/BlockState;", false);
                                super.visitFieldInsn(Opcodes.GETSTATIC,
                                        "net/minecraft/core/BlockPos", "ZERO",
                                        "Lnet/minecraft/core/BlockPos;");
                                super.visitMethodInsn(Opcodes.INVOKESPECIAL,
                                        "net/optifine/render/RenderEnv", "<init>",
                                        "(Lnet/minecraft/world/level/block/state/BlockState;"
                                        + "Lnet/minecraft/core/BlockPos;)V", false);
                                super.visitFieldInsn(Opcodes.PUTFIELD, owner, "renderEnv", environment);
                            }
                            super.visitInsn(opcode);
                        }
                    };
                }
                if (!name.equals("<clinit>") || !descriptor.equals("()V")) return delegate;
                clinit[0]++;
                return new MethodVisitor(Opcodes.ASM9, delegate) {
                    @Override
                    public void visitInsn(int opcode) {
                        if (opcode == Opcodes.RETURN) {
                            returns[0]++;
                            super.visitTypeInsn(Opcodes.NEW, "net/optifine/render/LightCacheOF");
                            super.visitInsn(Opcodes.DUP);
                            super.visitMethodInsn(Opcodes.INVOKESPECIAL,
                                    "net/optifine/render/LightCacheOF", "<init>", "()V", false);
                            super.visitFieldInsn(Opcodes.PUTSTATIC, owner,
                                    "LIGHT_CACHE_OF", cache);
                            super.visitLdcInsn(Float.valueOf(0.2f));
                            super.visitFieldInsn(Opcodes.PUTSTATIC, owner,
                                    "aoLightValueOpaque", "F");
                        }
                        super.visitInsn(opcode);
                    }
                };
            }

            @Override
            public void visitEnd() {
                super.visitField(Opcodes.ACC_PRIVATE | Opcodes.ACC_STATIC | Opcodes.ACC_FINAL,
                        "LIGHT_CACHE_OF", cache, null, null).visitEnd();
                super.visitField(Opcodes.ACC_PRIVATE, "renderEnv", environment, null, null)
                        .visitEnd();
                super.visitField(Opcodes.ACC_PRIVATE | Opcodes.ACC_STATIC,
                        "aoLightValueOpaque", "F", null, null).visitEnd();
                super.visitField(Opcodes.ACC_PRIVATE | Opcodes.ACC_STATIC,
                        "separateAoLightValue", "Z", null, null).visitEnd();
                MethodVisitor getter = super.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC,
                        "getLightCacheOf", "()" + cache, null, null);
                getter.visitCode();
                getter.visitFieldInsn(Opcodes.GETSTATIC, owner, "LIGHT_CACHE_OF", cache);
                getter.visitInsn(Opcodes.ARETURN);
                getter.visitMaxs(1, 0);
                getter.visitEnd();
                MethodVisitor renderEnv = super.visitMethod(Opcodes.ACC_PUBLIC,
                        "getRenderEnv", "(Lnet/minecraft/world/level/block/state/BlockState;"
                        + "Lnet/minecraft/core/BlockPos;)" + environment, null, null);
                renderEnv.visitCode();
                renderEnv.visitVarInsn(Opcodes.ALOAD, 0);
                renderEnv.visitFieldInsn(Opcodes.GETFIELD, owner, "renderEnv", environment);
                renderEnv.visitVarInsn(Opcodes.ALOAD, 1);
                renderEnv.visitVarInsn(Opcodes.ALOAD, 2);
                renderEnv.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                        "net/optifine/render/RenderEnv", "reset",
                        "(Lnet/minecraft/world/level/block/state/BlockState;"
                        + "Lnet/minecraft/core/BlockPos;)V", false);
                renderEnv.visitVarInsn(Opcodes.ALOAD, 0);
                renderEnv.visitFieldInsn(Opcodes.GETFIELD, owner, "renderEnv", environment);
                renderEnv.visitInsn(Opcodes.ARETURN);
                renderEnv.visitMaxs(3, 3);
                renderEnv.visitEnd();
                MethodVisitor ao = super.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC,
                        "fixAoLightValue", "(F)F", null, null);
                ao.visitCode();
                ao.visitVarInsn(Opcodes.FLOAD, 0);
                ao.visitLdcInsn(Float.valueOf(0.2f));
                ao.visitInsn(Opcodes.FCMPL);
                Label unchanged = new Label();
                ao.visitJumpInsn(Opcodes.IFNE, unchanged);
                ao.visitFieldInsn(Opcodes.GETSTATIC, owner, "aoLightValueOpaque", "F");
                ao.visitInsn(Opcodes.FRETURN);
                ao.visitLabel(unchanged);
                ao.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
                ao.visitVarInsn(Opcodes.FLOAD, 0);
                ao.visitInsn(Opcodes.FRETURN);
                ao.visitMaxs(2, 1);
                ao.visitEnd();
                MethodVisitor updateAo = super.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC,
                        "updateAoLightValue", "()V", null, null);
                updateAo.visitCode();
                updateAo.visitInsn(Opcodes.FCONST_1);
                updateAo.visitMethodInsn(Opcodes.INVOKESTATIC, "net/optifine/Config",
                        "getAmbientOcclusionLevel", "()F", false);
                updateAo.visitLdcInsn(Float.valueOf(0.8f));
                updateAo.visitInsn(Opcodes.FMUL);
                updateAo.visitInsn(Opcodes.FSUB);
                updateAo.visitFieldInsn(Opcodes.PUTSTATIC, owner, "aoLightValueOpaque", "F");
                updateAo.visitMethodInsn(Opcodes.INVOKESTATIC, "net/optifine/Config",
                        "isShaders", "()Z", false);
                Label notSeparate = new Label();
                Label done = new Label();
                updateAo.visitJumpInsn(Opcodes.IFEQ, notSeparate);
                updateAo.visitMethodInsn(Opcodes.INVOKESTATIC, "net/optifine/shaders/Shaders",
                        "isSeparateAo", "()Z", false);
                updateAo.visitJumpInsn(Opcodes.IFEQ, notSeparate);
                updateAo.visitInsn(Opcodes.ICONST_1);
                updateAo.visitJumpInsn(Opcodes.GOTO, done);
                updateAo.visitLabel(notSeparate);
                updateAo.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
                updateAo.visitInsn(Opcodes.ICONST_0);
                updateAo.visitLabel(done);
                updateAo.visitFrame(Opcodes.F_SAME1, 0, null, 1,
                        new Object[] {Opcodes.INTEGER});
                updateAo.visitFieldInsn(Opcodes.PUTSTATIC, owner,
                        "separateAoLightValue", "Z");
                updateAo.visitInsn(Opcodes.RETURN);
                updateAo.visitMaxs(3, 0);
                updateAo.visitEnd();
                MethodVisitor separateAo = super.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC,
                        "isSeparateAoLightValue", "()Z", null, null);
                separateAo.visitCode();
                separateAo.visitFieldInsn(Opcodes.GETSTATIC, owner,
                        "separateAoLightValue", "Z");
                separateAo.visitInsn(Opcodes.IRETURN);
                separateAo.visitMaxs(1, 0);
                separateAo.visitEnd();
                addSimpleGetter("isAmbientOcclusion", "ambientOcclusion", "Z",
                        Opcodes.IRETURN);
                addSimpleGetter("isCull", "cull", "Z", Opcodes.IRETURN);
                addSimpleGetter("getBlockColors", "blockColors",
                        "Lnet/minecraft/client/color/block/BlockColors;", Opcodes.ARETURN);
                MethodVisitor midBlock = super.visitMethod(Opcodes.ACC_PUBLIC,
                        "setMidBlock", "(FFF)V", null, null);
                midBlock.visitCode();
                midBlock.visitVarInsn(Opcodes.ALOAD, 0);
                midBlock.visitFieldInsn(Opcodes.GETFIELD, owner, "quadInstance",
                        "Lcom/mojang/blaze3d/vertex/QuadInstance;");
                midBlock.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                        "com/mojang/blaze3d/vertex/QuadInstance", "getMidBlock",
                        "()Lorg/joml/Vector3f;", false);
                midBlock.visitVarInsn(Opcodes.FLOAD, 1);
                midBlock.visitVarInsn(Opcodes.FLOAD, 2);
                midBlock.visitVarInsn(Opcodes.FLOAD, 3);
                midBlock.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "org/joml/Vector3f", "set",
                        "(FFF)Lorg/joml/Vector3f;", false);
                midBlock.visitInsn(Opcodes.POP);
                midBlock.visitInsn(Opcodes.RETURN);
                midBlock.visitMaxs(4, 4);
                midBlock.visitEnd();
                super.visitEnd();
            }

            private void addSimpleGetter(String methodName, String fieldName,
                                         String descriptor, int returnOpcode) {
                MethodVisitor getter = super.visitMethod(Opcodes.ACC_PUBLIC, methodName,
                        "()" + descriptor, null, null);
                getter.visitCode();
                getter.visitVarInsn(Opcodes.ALOAD, 0);
                getter.visitFieldInsn(Opcodes.GETFIELD, owner, fieldName, descriptor);
                getter.visitInsn(returnOpcode);
                getter.visitMaxs(1, 1);
                getter.visitEnd();
            }
        }, 0);
        if (clinit[0] != 1 || returns[0] != 1
                || constructors[0] != 1 || constructorReturns[0] != 1) {
            throw new IllegalArgumentException("Expected one pinned ModelBlockRenderer initializer/constructor: "
                    + clinit[0] + "/" + returns[0] + "/"
                    + constructors[0] + "/" + constructorReturns[0]);
        }
        return writer.toByteArray();
    }

    /** Match K1's public array access without allocating a clone per block lookup. */
    static byte[] widenDirectionValues(byte[] original) {
        ClassReader reader = new ClassReader(original);
        ClassWriter writer = new ClassWriter(reader, 0);
        int[] matched = new int[1];
        reader.accept(new ClassVisitor(Opcodes.ASM9, writer) {
            @Override
            public FieldVisitor visitField(int access, String name, String descriptor,
                                           String signature, Object value) {
                if (name.equals("VALUES")
                        && descriptor.equals("[Lnet/minecraft/core/Direction;")) {
                    if ((access & Opcodes.ACC_PRIVATE) == 0
                            || (access & Opcodes.ACC_STATIC) == 0
                            || (access & Opcodes.ACC_FINAL) == 0) {
                        throw new IllegalArgumentException("Unexpected pinned Direction.VALUES access");
                    }
                    access = (access & ~Opcodes.ACC_PRIVATE) | Opcodes.ACC_PUBLIC;
                    matched[0]++;
                }
                return super.visitField(access, name, descriptor, signature, value);
            }
        }, 0);
        if (matched[0] != 1) throw new IllegalArgumentException(
                "Expected one pinned Direction.VALUES field, found " + matched[0]);
        return writer.toByteArray();
    }

    static byte[] addClientLevelPlayerUpdate(byte[] original) {
        ClassReader reader = new ClassReader(original);
        ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_MAXS);
        String level = "net/minecraft/client/multiplayer/ClientLevel";
        String controller = "net/optifine/override/PlayerControllerOF";
        int[] setBlocks = new int[1];
        reader.accept(new ClassVisitor(Opcodes.ASM9, writer) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor,
                                             String signature, String[] exceptions) {
                if (name.equals("isPlayerUpdate") || name.equals("getSectionStorage")
                        || name.equals("usefulToolsPlayerUpdate")) {
                    throw new IllegalArgumentException("K1 ClientLevel helper already present: " + name);
                }
                MethodVisitor delegate = super.visitMethod(access, name, descriptor,
                        signature, exceptions);
                if (!name.equals("setBlock") || !descriptor.equals(
                        "(Lnet/minecraft/core/BlockPos;"
                        + "Lnet/minecraft/world/level/block/state/BlockState;II)Z")) return delegate;
                setBlocks[0]++;
                return new MethodVisitor(Opcodes.ASM9, delegate) {
                    @Override
                    public void visitCode() {
                        super.visitCode();
                        super.visitVarInsn(Opcodes.ALOAD, 0);
                        super.visitFieldInsn(Opcodes.GETFIELD, level, "minecraft",
                                "Lnet/minecraft/client/Minecraft;");
                        super.visitFieldInsn(Opcodes.GETFIELD, "net/minecraft/client/Minecraft",
                                "gameMode", "Lnet/minecraft/client/multiplayer/MultiPlayerGameMode;");
                        super.visitTypeInsn(Opcodes.INSTANCEOF, controller);
                        org.objectweb.asm.Label noController = new org.objectweb.asm.Label();
                        org.objectweb.asm.Label done = new org.objectweb.asm.Label();
                        super.visitJumpInsn(Opcodes.IFEQ, noController);
                        super.visitVarInsn(Opcodes.ALOAD, 0);
                        super.visitVarInsn(Opcodes.ALOAD, 0);
                        super.visitFieldInsn(Opcodes.GETFIELD, level, "minecraft",
                                "Lnet/minecraft/client/Minecraft;");
                        super.visitFieldInsn(Opcodes.GETFIELD, "net/minecraft/client/Minecraft",
                                "gameMode", "Lnet/minecraft/client/multiplayer/MultiPlayerGameMode;");
                        super.visitTypeInsn(Opcodes.CHECKCAST, controller);
                        super.visitMethodInsn(Opcodes.INVOKEVIRTUAL, controller, "isActing", "()Z", false);
                        super.visitFieldInsn(Opcodes.PUTFIELD, level,
                                "usefulToolsPlayerUpdate", "Z");
                        super.visitJumpInsn(Opcodes.GOTO, done);
                        super.visitLabel(noController);
                        super.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
                        super.visitVarInsn(Opcodes.ALOAD, 0);
                        super.visitInsn(Opcodes.ICONST_0);
                        super.visitFieldInsn(Opcodes.PUTFIELD, level,
                                "usefulToolsPlayerUpdate", "Z");
                        super.visitLabel(done);
                        super.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
                    }

                    @Override
                    public void visitInsn(int opcode) {
                        if (opcode == Opcodes.IRETURN) {
                            super.visitVarInsn(Opcodes.ALOAD, 0);
                            super.visitInsn(Opcodes.ICONST_0);
                            super.visitFieldInsn(Opcodes.PUTFIELD, level,
                                    "usefulToolsPlayerUpdate", "Z");
                        }
                        super.visitInsn(opcode);
                    }
                };
            }

            @Override
            public void visitEnd() {
                super.visitField(Opcodes.ACC_PRIVATE, "usefulToolsPlayerUpdate", "Z",
                        null, null).visitEnd();
                MethodVisitor getter = super.visitMethod(Opcodes.ACC_PUBLIC,
                        "isPlayerUpdate", "()Z", null, null);
                getter.visitCode();
                getter.visitVarInsn(Opcodes.ALOAD, 0);
                getter.visitFieldInsn(Opcodes.GETFIELD, level, "usefulToolsPlayerUpdate", "Z");
                getter.visitInsn(Opcodes.IRETURN);
                getter.visitMaxs(1, 1);
                getter.visitEnd();
                MethodVisitor storage = super.visitMethod(Opcodes.ACC_PUBLIC,
                        "getSectionStorage",
                        "()Lnet/minecraft/world/level/entity/EntitySectionStorage;", null, null);
                storage.visitCode();
                storage.visitVarInsn(Opcodes.ALOAD, 0);
                storage.visitFieldInsn(Opcodes.GETFIELD, level, "entityStorage",
                        "Lnet/minecraft/world/level/entity/TransientEntitySectionManager;");
                storage.visitFieldInsn(Opcodes.GETSTATIC, "net/optifine/reflect/Reflector",
                        "TransientEntitySectionManager_sectionStorage",
                        "Lnet/optifine/reflect/ReflectorField;");
                storage.visitMethodInsn(Opcodes.INVOKESTATIC, "net/optifine/reflect/Reflector",
                        "getFieldValue", "(Ljava/lang/Object;Lnet/optifine/reflect/ReflectorField;)"
                        + "Ljava/lang/Object;", false);
                storage.visitTypeInsn(Opcodes.CHECKCAST,
                        "net/minecraft/world/level/entity/EntitySectionStorage");
                storage.visitInsn(Opcodes.ARETURN);
                storage.visitMaxs(2, 1);
                storage.visitEnd();
                super.visitEnd();
            }
        }, 0);
        if (setBlocks[0] != 1) throw new IllegalArgumentException(
                "Expected one pinned ClientLevel.setBlock(4), found " + setBlocks[0]);
        return writer.toByteArray();
    }

    static byte[] addSubmitNodeShaderStorage(byte[] original) {
        ClassReader reader = new ClassReader(original);
        ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_MAXS);
        String owner = "net/minecraft/client/renderer/SubmitNodeStorage";
        String map = "Lit/unimi/dsi/fastutil/ints/Int2ObjectAVLTreeMap;";
        int[] constructors = new int[1];
        int[] maps = new int[1];
        reader.accept(new ClassVisitor(Opcodes.ASM9, writer) {
            @Override
            public FieldVisitor visitField(int access, String name, String descriptor,
                                           String signature, Object value) {
                if (name.equals("submitsPerOrder") && descriptor.equals(map)) {
                    if ((access & Opcodes.ACC_FINAL) == 0) {
                        throw new IllegalArgumentException("Pinned submit map is not final");
                    }
                    maps[0]++;
                    access &= ~Opcodes.ACC_FINAL;
                }
                if (name.equals("submitsPerOrderMain") || name.equals("submitsPerOrderShaders")) {
                    throw new IllegalArgumentException("K1 submit queue field already present: " + name);
                }
                return super.visitField(access, name, descriptor, signature, value);
            }

            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor,
                                             String signature, String[] exceptions) {
                if (name.equals("setShadersStorage")) {
                    throw new IllegalArgumentException("K1 submit queue hook already present");
                }
                MethodVisitor delegate = super.visitMethod(access, name, descriptor,
                        signature, exceptions);
                if (!name.equals("<init>")) return delegate;
                constructors[0]++;
                return new MethodVisitor(Opcodes.ASM9, delegate) {
                    @Override
                    public void visitInsn(int opcode) {
                        if (opcode == Opcodes.RETURN) {
                            super.visitVarInsn(Opcodes.ALOAD, 0);
                            super.visitVarInsn(Opcodes.ALOAD, 0);
                            super.visitFieldInsn(Opcodes.GETFIELD, owner, "submitsPerOrder", map);
                            super.visitFieldInsn(Opcodes.PUTFIELD, owner,
                                    "submitsPerOrderMain", map);
                            super.visitVarInsn(Opcodes.ALOAD, 0);
                            super.visitTypeInsn(Opcodes.NEW,
                                    "it/unimi/dsi/fastutil/ints/Int2ObjectAVLTreeMap");
                            super.visitInsn(Opcodes.DUP);
                            super.visitMethodInsn(Opcodes.INVOKESPECIAL,
                                    "it/unimi/dsi/fastutil/ints/Int2ObjectAVLTreeMap",
                                    "<init>", "()V", false);
                            super.visitFieldInsn(Opcodes.PUTFIELD, owner,
                                    "submitsPerOrderShaders", map);
                        }
                        super.visitInsn(opcode);
                    }
                };
            }

            @Override
            public void visitEnd() {
                super.visitField(Opcodes.ACC_PRIVATE | Opcodes.ACC_FINAL,
                        "submitsPerOrderMain", map, null, null).visitEnd();
                super.visitField(Opcodes.ACC_PRIVATE | Opcodes.ACC_FINAL,
                        "submitsPerOrderShaders", map, null, null).visitEnd();
                MethodVisitor switcher = super.visitMethod(Opcodes.ACC_PUBLIC,
                        "setShadersStorage", "(Z)V", null, null);
                Label main = new Label();
                Label done = new Label();
                switcher.visitCode();
                switcher.visitVarInsn(Opcodes.ILOAD, 1);
                switcher.visitJumpInsn(Opcodes.IFEQ, main);
                switcher.visitVarInsn(Opcodes.ALOAD, 0);
                switcher.visitVarInsn(Opcodes.ALOAD, 0);
                switcher.visitFieldInsn(Opcodes.GETFIELD, owner,
                        "submitsPerOrderShaders", map);
                switcher.visitFieldInsn(Opcodes.PUTFIELD, owner, "submitsPerOrder", map);
                switcher.visitJumpInsn(Opcodes.GOTO, done);
                switcher.visitLabel(main);
                switcher.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
                switcher.visitVarInsn(Opcodes.ALOAD, 0);
                switcher.visitVarInsn(Opcodes.ALOAD, 0);
                switcher.visitFieldInsn(Opcodes.GETFIELD, owner,
                        "submitsPerOrderMain", map);
                switcher.visitFieldInsn(Opcodes.PUTFIELD, owner, "submitsPerOrder", map);
                switcher.visitLabel(done);
                switcher.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
                switcher.visitInsn(Opcodes.RETURN);
                switcher.visitMaxs(2, 2);
                switcher.visitEnd();
                super.visitEnd();
            }
        }, 0);
        if (constructors[0] != 1 || maps[0] != 1) {
            throw new IllegalArgumentException("Expected one pinned submit constructor and map: "
                    + constructors[0] + "/" + maps[0]);
        }
        return writer.toByteArray();
    }

    static byte[] addGuiGraphicsExtractorBlit(byte[] original, byte[] donor) {
        String descriptor = "(Lnet/minecraft/resources/Identifier;IIIIFFFFI)V";
        ClassNode nativeNode = new ClassNode();
        new ClassReader(original).accept(nativeNode, 0);
        ClassNode donorNode = new ClassNode();
        new ClassReader(donor).accept(donorNode, 0);
        if (!nativeNode.name.equals(donorNode.name)) {
            throw new IllegalArgumentException("K1 GUI extractor donor identity mismatch");
        }
        MethodNode addition = null;
        boolean innerBlit = false;
        for (MethodNode method : nativeNode.methods) {
            if (method.name.equals("blit") && method.desc.equals(descriptor)) {
                throw new IllegalArgumentException("K1 GUI blit already present");
            }
            if (method.name.equals("innerBlit") && method.desc.equals(
                    "(Lcom/mojang/blaze3d/pipeline/RenderPipeline;"
                    + "Lnet/minecraft/resources/Identifier;IIIIFFFFI)V")) innerBlit = true;
        }
        for (MethodNode method : donorNode.methods) {
            if (method.name.equals("blit") && method.desc.equals(descriptor)) addition = method;
        }
        if (addition == null || !innerBlit) {
            throw new IllegalArgumentException("K1 GUI blit donor or native helper absent");
        }
        nativeNode.methods.add(addition);
        ClassWriter writer = new ClassWriter(0);
        nativeNode.accept(writer);
        return writer.toByteArray();
    }

    static byte[] guardBetterGrassRefresh(byte[] original) {
        ClassReader reader = new ClassReader(original);
        ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_MAXS);
        int[] matched = new int[1];
        String betterGrass = "net/optifine/BetterGrass";
        reader.accept(new ClassVisitor(Opcodes.ASM9, writer) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor,
                                             String signature, String[] exceptions) {
                MethodVisitor delegate = super.visitMethod(access, name, descriptor,
                        signature, exceptions);
                if (!name.equals("refreshIcons") || !descriptor.equals(
                        "(Lnet/minecraft/client/renderer/texture/TextureAtlas;)V")) return delegate;
                matched[0]++;
                return new MethodVisitor(Opcodes.ASM9, delegate) {
                    @Override
                    public void visitCode() {
                        super.visitCode();
                        Label initialized = new Label();
                        super.visitFieldInsn(Opcodes.GETSTATIC, betterGrass, "spriteGrass",
                                "Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;");
                        super.visitJumpInsn(Opcodes.IFNONNULL, initialized);
                        super.visitInsn(Opcodes.RETURN);
                        super.visitLabel(initialized);
                        super.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
                    }
                };
            }
        }, 0);
        if (matched[0] != 1) {
            throw new IllegalArgumentException("Expected one K1 BetterGrass.refreshIcons, found "
                    + matched[0]);
        }
        return writer.toByteArray();
    }

    static byte[] addEntityRenderDispatcherContext(byte[] original) {
        ClassReader reader = new ClassReader(original);
        ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_MAXS);
        int[] stores = new int[1];
        String dispatcher = "net/minecraft/client/renderer/entity/EntityRenderDispatcher";
        String context = "Lnet/minecraft/client/renderer/entity/EntityRendererProvider$Context;";
        reader.accept(new ClassVisitor(Opcodes.ASM9, writer) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor,
                                             String signature, String[] exceptions) {
                if (name.equals("getContext")) {
                    throw new IllegalArgumentException("K1 EntityRenderDispatcher.getContext already present");
                }
                MethodVisitor delegate = super.visitMethod(access, name, descriptor,
                        signature, exceptions);
                if (!name.equals("onResourceManagerReload") || !descriptor.equals(
                        "(Lnet/minecraft/server/packs/resources/ResourceManager;)V")) return delegate;
                return new MethodVisitor(Opcodes.ASM9, delegate) {
                    @Override
                    public void visitVarInsn(int opcode, int variable) {
                        super.visitVarInsn(opcode, variable);
                        if (opcode == Opcodes.ASTORE && variable == 2) {
                            super.visitVarInsn(Opcodes.ALOAD, 0);
                            super.visitVarInsn(Opcodes.ALOAD, 2);
                            super.visitFieldInsn(Opcodes.PUTFIELD, dispatcher,
                                    "usefulToolsContext", context);
                            stores[0]++;
                        }
                    }
                };
            }
            @Override
            public void visitEnd() {
                super.visitField(Opcodes.ACC_PRIVATE, "usefulToolsContext", context,
                        null, null).visitEnd();
                MethodVisitor method = super.visitMethod(Opcodes.ACC_PUBLIC,
                        "getContext", "()" + context, null, null);
                method.visitCode();
                method.visitVarInsn(Opcodes.ALOAD, 0);
                method.visitFieldInsn(Opcodes.GETFIELD, dispatcher,
                        "usefulToolsContext", context);
                method.visitInsn(Opcodes.ARETURN);
                method.visitMaxs(1, 1);
                method.visitEnd();
                super.visitEnd();
            }
        }, 0);
        if (stores[0] != 1) {
            throw new IllegalArgumentException("Expected one K1 EntityRenderDispatcher context local, found "
                    + stores[0]);
        }
        return writer.toByteArray();
    }

    static byte[] addGpuDeviceUnstenciledOverloads(byte[] donor) {
        ClassReader reader = new ClassReader(donor);
        ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_MAXS);
        String gpu = "com/mojang/blaze3d/systems/GpuDevice";
        String texture = "Lcom/mojang/blaze3d/textures/GpuTexture;";
        String args = "ILcom/mojang/blaze3d/textures/TextureFormat;IIII";
        int[] existing = new int[1];
        reader.accept(new ClassVisitor(Opcodes.ASM9, writer) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor,
                                             String signature, String[] exceptions) {
                if (name.equals("createTexture") && descriptor.endsWith("Z)" + texture)) {
                    existing[0]++;
                }
                return super.visitMethod(access, name, descriptor, signature, exceptions);
            }
            @Override
            public void visitEnd() {
                for (String first : new String[] {"Ljava/util/function/Supplier;", "Ljava/lang/String;"}) {
                    String withoutStencil = "(" + first + args + ")" + texture;
                    String withStencil = "(" + first + args + "Z)" + texture;
                    MethodVisitor method = super.visitMethod(Opcodes.ACC_PUBLIC, "createTexture",
                            withStencil, null, null);
                    Label noStencil = new Label();
                    method.visitCode();
                    method.visitVarInsn(Opcodes.ILOAD, 8);
                    method.visitJumpInsn(Opcodes.IFEQ, noStencil);
                    method.visitTypeInsn(Opcodes.NEW, "java/lang/UnsupportedOperationException");
                    method.visitInsn(Opcodes.DUP);
                    method.visitLdcInsn("K1 donor GpuDevice cannot create stencil textures");
                    method.visitMethodInsn(Opcodes.INVOKESPECIAL,
                            "java/lang/UnsupportedOperationException", "<init>",
                            "(Ljava/lang/String;)V", false);
                    method.visitInsn(Opcodes.ATHROW);
                    method.visitLabel(noStencil);
                    method.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
                    method.visitVarInsn(Opcodes.ALOAD, 0);
                    method.visitVarInsn(Opcodes.ALOAD, 1);
                    method.visitVarInsn(Opcodes.ILOAD, 2);
                    method.visitVarInsn(Opcodes.ALOAD, 3);
                    for (int slot = 4; slot <= 7; slot++) method.visitVarInsn(Opcodes.ILOAD, slot);
                    method.visitMethodInsn(Opcodes.INVOKEVIRTUAL, gpu, "createTexture",
                            withoutStencil, false);
                    method.visitInsn(Opcodes.ARETURN);
                    method.visitMaxs(8, 9);
                    method.visitEnd();
                }
                super.visitEnd();
            }
        }, 0);
        if (existing[0] != 0) {
            throw new IllegalArgumentException("K1 donor GpuDevice already has stencil overloads");
        }
        return writer.toByteArray();
    }

    private static String sha256(byte[] bytes) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            StringBuilder result = new StringBuilder();
            for (byte value : digest.digest(bytes)) result.append(String.format("%02x", value & 0xff));
            return result.toString();
        } catch (java.security.NoSuchAlgorithmException error) {
            throw new AssertionError(error);
        }
    }
}
