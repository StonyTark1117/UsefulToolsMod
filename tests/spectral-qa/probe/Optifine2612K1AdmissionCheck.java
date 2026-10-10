package com.stonytark.usefultoolsmod.spectralqa;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.util.CheckClassAdapter;

/** Strict offline verification for the private 26.1.2/Forge-64.0.8 K1 admission bridge. */
public final class Optifine2612K1AdmissionCheck {
    private static final String NATIVE_SHA256 =
            "5d6acdaef133c4f002b9d8cf4c1b44e1b0720c039c3f21606f762a226e795601";
    private static final String DONOR_SHA256 =
            "89b6c8051570cf76259041ea13ca4ae5c0147b303c30d85c78d39a3f6305c633";
    private static final String OFFICIAL_SHA256 =
            "f8eb9026e4da2444e18d5601d3dede2bd19cf514d02095ffcdb0e101687c2172";

    private Optifine2612K1AdmissionCheck() { }

    public static void main(String[] arguments) throws Exception {
        if (arguments.length != 3) throw new IllegalArgumentException("native donor official");
        Path nativeJar = Path.of(arguments[0]);
        Path donorJar = Path.of(arguments[1]);
        Path officialJar = Path.of(arguments[2]);
        exactHash(nativeJar, NATIVE_SHA256);
        exactHash(donorJar, DONOR_SHA256);
        exactHash(officialJar, OFFICIAL_SHA256);

        Set<String> nativeEntries = new HashSet<String>();
        Set<String> donorEntries = new HashSet<String>();
        List<String> checked = new ArrayList<String>();
        try (ZipFile nativeArchive = new ZipFile(nativeJar.toFile());
             ZipFile donorArchive = new ZipFile(donorJar.toFile());
             ZipFile officialArchive = new ZipFile(officialJar.toFile())) {
            donorArchive.stream().filter(entry -> selected(entry.getName(), "srg/"))
                    .forEach(entry -> donorEntries.add(entry.getName().substring(4)));
            nativeArchive.stream().filter(entry -> selected(entry.getName(), ""))
                    .forEach(entry -> nativeEntries.add(entry.getName()));
            if (!donorEntries.containsAll(nativeEntries)) {
                Set<String> missing = new HashSet<String>(nativeEntries);
                missing.removeAll(donorEntries);
                throw new IllegalStateException("Selected K1 family lacks native members: " + missing);
            }
            for (String name : donorEntries) {
                byte[] donorBytes = bytes(donorArchive, "srg/" + name);
                structurallyVerify(donorBytes);
                checked.add(name);
            }
            for (String name : nativeEntries) structurallyVerify(bytes(nativeArchive, name));
            ClassNode nativeGl = node(bytes(nativeArchive,
                    "com/mojang/blaze3d/opengl/GlStateManager.class"));
            ClassNode donorGl = node(bytes(donorArchive,
                    "srg/com/mojang/blaze3d/opengl/GlStateManager.class"));
            requireMethod(nativeGl, "init", "(Lorg/lwjgl/opengl/GLCapabilities;)V", false);
            requireMethod(donorGl, "init", "(Lorg/lwjgl/opengl/GLCapabilities;)V", true);

            ClassNode nativeGpuTexture = node(bytes(nativeArchive,
                    "com/mojang/blaze3d/textures/GpuTexture.class"));
            ClassNode donorGpuTexture = node(bytes(donorArchive,
                    "srg/com/mojang/blaze3d/textures/GpuTexture.class"));
            String parentTexture = "(Lnet/minecraft/client/renderer/texture/AbstractTexture;)V";
            requireMethod(nativeGpuTexture, "setParentTexture", parentTexture, false);
            requireMethod(donorGpuTexture, "setParentTexture", parentTexture, true);
            requireMethod(nativeGpuTexture, "getParentTexture",
                    "()Lnet/minecraft/client/renderer/texture/AbstractTexture;", false);
            requireMethod(donorGpuTexture, "getParentTexture",
                    "()Lnet/minecraft/client/renderer/texture/AbstractTexture;", true);
            if (!nativeEntries.contains("com/mojang/blaze3d/textures/GpuTexture$Usage.class")
                    || !donorEntries.contains("com/mojang/blaze3d/textures/GpuTexture$Usage.class")) {
                throw new IllegalStateException("GpuTexture and GpuTexture$Usage must be selected together");
            }

            ClassNode nativeVertexFormat = node(bytes(nativeArchive,
                    "com/mojang/blaze3d/vertex/VertexFormat.class"));
            ClassNode donorVertexFormat = node(bytes(donorArchive,
                    "srg/com/mojang/blaze3d/vertex/VertexFormat.class"));
            requireMethod(nativeVertexFormat, "duplicate",
                    "()Lcom/mojang/blaze3d/vertex/VertexFormat;", false);
            requireMethod(donorVertexFormat, "duplicate",
                    "()Lcom/mojang/blaze3d/vertex/VertexFormat;", true);

            ClassNode nativeGameRenderer = node(bytes(nativeArchive,
                    "net/minecraft/client/renderer/GameRenderer.class"));
            ClassNode donorGameRenderer = node(bytes(donorArchive,
                    "srg/net/minecraft/client/renderer/GameRenderer.class"));
            requireMethod(nativeGameRenderer, "getGuiRenderer",
                    "()Lnet/minecraft/client/gui/render/GuiRenderer;", false);
            requireMethod(donorGameRenderer, "getGuiRenderer",
                    "()Lnet/minecraft/client/gui/render/GuiRenderer;", true);

            ClassNode nativeGuiRenderer = node(bytes(nativeArchive,
                    "net/minecraft/client/gui/render/GuiRenderer.class"));
            ClassNode donorGuiRenderer = node(bytes(donorArchive,
                    "srg/net/minecraft/client/gui/render/GuiRenderer.class"));
            requireMethod(nativeGuiRenderer, "getRenderState",
                    "()Lnet/minecraft/client/renderer/state/gui/GuiRenderState;", false);
            requireMethod(donorGuiRenderer, "getRenderState",
                    "()Lnet/minecraft/client/renderer/state/gui/GuiRenderState;", true);

            ClassNode nativeGpuDevice = node(bytes(nativeArchive,
                    "com/mojang/blaze3d/systems/GpuDevice.class"));
            ClassNode donorGpuDevice = node(bytes(donorArchive,
                    "srg/com/mojang/blaze3d/systems/GpuDevice.class"));
            requireMethod(nativeGpuDevice, "getGlDevice",
                    "()Lcom/mojang/blaze3d/opengl/GlDevice;", false);
            requireMethod(donorGpuDevice, "getGlDevice",
                    "()Lcom/mojang/blaze3d/opengl/GlDevice;", true);
            byte[] bridgedGpuDeviceBytes = Optifine2612K1AdmissionBridge
                    .addGpuDeviceUnstenciledOverloads(bytes(donorArchive,
                            "srg/com/mojang/blaze3d/systems/GpuDevice.class"));
            structurallyVerify(bridgedGpuDeviceBytes);
            ClassNode bridgedGpuDevice = node(bridgedGpuDeviceBytes);
            for (String first : List.of("Ljava/util/function/Supplier;", "Ljava/lang/String;")) {
                String suffix = "ILcom/mojang/blaze3d/textures/TextureFormat;IIII";
                String returnType = "Lcom/mojang/blaze3d/textures/GpuTexture;";
                String originalTexture = "(" + first + suffix + ")" + returnType;
                String withStencil = "(" + first + suffix + "Z)" + returnType;
                requireMethod(nativeGpuDevice, "createTexture", withStencil, true);
                requireMethod(donorGpuDevice, "createTexture", withStencil, false);
                requireMethod(donorGpuDevice, "createTexture", originalTexture, true);
                MethodNode guardedTexture = requireMethod(bridgedGpuDevice,
                        "createTexture", withStencil, true);
                int delegated = 0;
                int rejected = 0;
                for (AbstractInsnNode instruction : guardedTexture.instructions) {
                    if (instruction instanceof MethodInsnNode call
                            && call.getOpcode() == Opcodes.INVOKEVIRTUAL
                            && call.owner.equals(bridgedGpuDevice.name)
                            && call.name.equals("createTexture")
                            && call.desc.equals(originalTexture)) delegated++;
                    if (instruction.getOpcode() == Opcodes.ATHROW) rejected++;
                }
                if (delegated != 1 || rejected != 1) {
                    throw new IllegalStateException("GpuDevice boolean overload must delegate only "
                            + "without stencil and reject true");
                }
            }
            ClassNode nativeGlDevice = node(bytes(nativeArchive,
                    "com/mojang/blaze3d/opengl/GlDevice.class"));
            ClassNode donorGlDevice = node(bytes(donorArchive,
                    "srg/com/mojang/blaze3d/opengl/GlDevice.class"));
            if ((nativeGlDevice.access & Opcodes.ACC_PUBLIC) != 0
                    || (donorGlDevice.access & Opcodes.ACC_PUBLIC) == 0) {
                throw new IllegalStateException("GlDevice must change from package-private to public");
            }
            ClassNode nativeGlCommandEncoder = node(bytes(nativeArchive,
                    "com/mojang/blaze3d/opengl/GlCommandEncoder.class"));
            ClassNode donorGlCommandEncoder = node(bytes(donorArchive,
                    "srg/com/mojang/blaze3d/opengl/GlCommandEncoder.class"));
            if ((nativeGlCommandEncoder.access & Opcodes.ACC_PUBLIC) != 0
                    || (donorGlCommandEncoder.access & Opcodes.ACC_PUBLIC) == 0
                    || !memberInventory(donorGlCommandEncoder).containsAll(
                            memberInventory(nativeGlCommandEncoder))) {
                throw new IllegalStateException("GlCommandEncoder donor must widen access and keep native members");
            }
            requireMethod(nativeGlCommandEncoder, "useVanillaProgram", "()V", false);
            requireMethod(donorGlCommandEncoder, "useVanillaProgram", "()V", true);
            ClassNode outlineEncoderTrial = node(Optifine2612K1AdmissionBridge
                    .trialNativeOutlinePipelines(bytes(donorArchive,
                            "srg/com/mojang/blaze3d/opengl/GlCommandEncoder.class")));
            MethodNode outlineSetup = requireMethod(outlineEncoderTrial, "trySetup",
                    "(Lcom/mojang/blaze3d/opengl/GlRenderPass;Ljava/util/Collection;)Z", true);
            Set<String> outlinePipelineReads = new HashSet<String>();
            int outlinePostChainMatches = 0;
            for (AbstractInsnNode instruction : outlineSetup.instructions) {
                if (instruction instanceof FieldInsnNode field
                        && field.getOpcode() == Opcodes.GETSTATIC
                        && field.owner.equals("net/minecraft/client/renderer/RenderPipelines")
                        && field.name.contains("OUTLINE")) outlinePipelineReads.add(field.name);
                if (instruction instanceof org.objectweb.asm.tree.LdcInsnNode constant
                        && "entity_outline".equals(constant.cst)) outlinePostChainMatches++;
            }
            if (!outlinePipelineReads.containsAll(Set.of(
                    "OUTLINE_CULL", "OUTLINE_NO_CULL", "ENTITY_OUTLINE_BLIT"))
                    || outlinePostChainMatches != 1) {
                throw new IllegalStateException("K1 outline mask trial omits a native pipeline: "
                        + outlinePipelineReads);
            }
            ClassNode nativeRenderTarget = node(bytes(nativeArchive,
                    "com/mojang/blaze3d/pipeline/RenderTarget.class"));
            ClassNode donorRenderTarget = node(bytes(donorArchive,
                    "srg/com/mojang/blaze3d/pipeline/RenderTarget.class"));
            if (!memberInventory(donorRenderTarget).containsAll(memberInventory(nativeRenderTarget))) {
                throw new IllegalStateException("RenderTarget donor omits native members");
            }
            requireMethod(nativeRenderTarget, "hasFramebuffer", "(I)Z", false);
            requireMethod(donorRenderTarget, "hasFramebuffer", "(I)Z", true);
            ClassNode nativeBossOverlay = node(bytes(nativeArchive,
                    "net/minecraft/client/gui/components/BossHealthOverlay.class"));
            ClassNode donorBossOverlay = node(bytes(donorArchive,
                    "srg/net/minecraft/client/gui/components/BossHealthOverlay.class"));
            if (!memberInventory(donorBossOverlay).containsAll(memberInventory(nativeBossOverlay))) {
                throw new IllegalStateException("BossHealthOverlay donor omits native members");
            }
            requireMethod(nativeBossOverlay, "getBossName", "()Ljava/lang/String;", false);
            requireMethod(donorBossOverlay, "getBossName", "()Ljava/lang/String;", true);
            ClassNode nativeCamera = node(bytes(nativeArchive,
                    "net/minecraft/client/Camera.class"));
            ClassNode donorCamera = node(bytes(donorArchive,
                    "srg/net/minecraft/client/Camera.class"));
            MethodNode nativeProjection = requireMethod(nativeCamera,
                    "createProjectionMatrixForCulling", "()Lorg/joml/Matrix4f;", true);
            MethodNode donorProjection = requireMethod(donorCamera,
                    "createProjectionMatrixForCulling", "()Lorg/joml/Matrix4f;", true);
            if ((nativeProjection.access & Opcodes.ACC_PRIVATE) == 0
                    || (donorProjection.access & Opcodes.ACC_PUBLIC) == 0) {
                throw new IllegalStateException("Camera projection method must widen to public");
            }
            ClassNode nativeGlTexture = node(bytes(nativeArchive,
                    "com/mojang/blaze3d/opengl/GlTexture.class"));
            ClassNode donorGlTexture = node(bytes(donorArchive,
                    "srg/com/mojang/blaze3d/opengl/GlTexture.class"));
            if (!memberInventory(donorGlTexture).containsAll(memberInventory(nativeGlTexture))) {
                throw new IllegalStateException("Donor GlTexture omits native members");
            }
            requireMethod(nativeGlTexture, "getMultiTexID",
                    "()Lnet/optifine/shaders/MultiTexID;", false);
            MethodNode donorMultiTex = requireMethod(donorGlTexture, "getMultiTexID",
                    "()Lnet/optifine/shaders/MultiTexID;", true);
            requireField(donorGlTexture, "multiTex", "Lnet/optifine/shaders/MultiTexID;");
            requireCall(donorMultiTex, "net/optifine/shaders/ShadersTex", "getMultiTexID",
                    "(Lcom/mojang/blaze3d/opengl/GlTexture;)Lnet/optifine/shaders/MultiTexID;");
            ClassNode nativeFogRenderer = node(bytes(nativeArchive,
                    "net/minecraft/client/renderer/fog/FogRenderer.class"));
            ClassNode donorFogRenderer = node(bytes(donorArchive,
                    "srg/net/minecraft/client/renderer/fog/FogRenderer.class"));
            if (!memberInventory(donorFogRenderer).containsAll(memberInventory(nativeFogRenderer))) {
                throw new IllegalStateException("Donor FogRenderer omits native members");
            }
            requireMethod(nativeFogRenderer, "setShadersFog",
                    "(Lcom/mojang/blaze3d/buffers/GpuBufferSlice;)V", false);
            requireMethod(donorFogRenderer, "setShadersFog",
                    "(Lcom/mojang/blaze3d/buffers/GpuBufferSlice;)V", true);
            ClassNode nativeLoadingOverlay = node(bytes(nativeArchive,
                    "net/minecraft/client/gui/screens/LoadingOverlay.class"));
            ClassNode donorLoadingOverlay = node(bytes(donorArchive,
                    "srg/net/minecraft/client/gui/screens/LoadingOverlay.class"));
            requireMethod(nativeLoadingOverlay, "isFadeOut", "()Z", false);
            requireMethod(donorLoadingOverlay, "isFadeOut", "()Z", true);
            ClassNode nativeTextureAtlas = node(bytes(nativeArchive,
                    "net/minecraft/client/renderer/texture/TextureAtlas.class"));
            ClassNode donorTextureAtlas = node(bytes(donorArchive,
                    "srg/net/minecraft/client/renderer/texture/TextureAtlas.class"));
            requireMethod(nativeTextureAtlas, "uploadInitialContents",
                    "(Lnet/optifine/shaders/ShadersTextureType;)V", false);
            MethodNode guardedUpload = requireMethod(node(Optifine2612K1AdmissionBridge
                    .guardTextureAtlasEmptyUploads(bytes(donorArchive,
                            "srg/net/minecraft/client/renderer/texture/TextureAtlas.class"))),
                    "uploadInitialContents", "(Lnet/optifine/shaders/ShadersTextureType;)V", true);
            int emptyChecks = 0;
            for (AbstractInsnNode instruction : guardedUpload.instructions) {
                if (instruction instanceof MethodInsnNode call && call.owner.equals("java/util/List")
                        && call.name.equals("isEmpty") && call.desc.equals("()Z")) emptyChecks++;
            }
            if (emptyChecks != 1) {
                throw new IllegalStateException("Expected one zero-sprite atlas upload guard");
            }
            structurallyVerify(Optifine2612K1AdmissionBridge.guardTextureAtlasEmptyUploads(
                    bytes(donorArchive, "srg/net/minecraft/client/renderer/texture/TextureAtlas.class")));
            requireMethod(nativeTextureAtlas, "isAbsoluteLocation",
                    "(Lnet/minecraft/resources/Identifier;)Z", false);
            requireMethod(donorTextureAtlas, "isAbsoluteLocation",
                    "(Lnet/minecraft/resources/Identifier;)Z", true);
            ClassNode nativeTextureAtlasSprite = node(bytes(nativeArchive,
                    "net/minecraft/client/renderer/texture/TextureAtlasSprite.class"));
            ClassNode donorTextureAtlasSprite = node(bytes(donorArchive,
                    "srg/net/minecraft/client/renderer/texture/TextureAtlasSprite.class"));
            requireMethod(nativeTextureAtlasSprite, "getTextureAtlas",
                    "()Lnet/minecraft/client/renderer/texture/TextureAtlas;", false);
            requireMethod(donorTextureAtlasSprite, "getTextureAtlas",
                    "()Lnet/minecraft/client/renderer/texture/TextureAtlas;", true);
            requireMethod(nativeTextureAtlasSprite, "setTextureAtlas",
                    "(Lnet/minecraft/client/renderer/texture/TextureAtlas;)V", false);
            requireMethod(donorTextureAtlasSprite, "setTextureAtlas",
                    "(Lnet/minecraft/client/renderer/texture/TextureAtlas;)V", true);
            String spriteConstructor =
                    "(Lnet/minecraft/resources/Identifier;Lnet/minecraft/client/renderer/texture/SpriteContents;IIIII)V";
            MethodNode nativeSpriteConstructor = requireMethod(nativeTextureAtlasSprite,
                    "<init>", spriteConstructor, true);
            MethodNode donorSpriteConstructor = requireMethod(donorTextureAtlasSprite,
                    "<init>", spriteConstructor, true);
            if ((nativeSpriteConstructor.access & Opcodes.ACC_PUBLIC) == 0
                    || (donorSpriteConstructor.access & Opcodes.ACC_PROTECTED) == 0) {
                throw new IllegalStateException("TextureAtlasSprite constructor must be native-public/donor-protected");
            }
            ClassNode bridgedTextureAtlasSprite = node(
                    Optifine2612K1AdmissionBridge.widenTextureAtlasSpriteConstructor(
                            bytes(donorArchive,
                                    "srg/net/minecraft/client/renderer/texture/TextureAtlasSprite.class")));
            MethodNode bridgedSpriteConstructor = requireMethod(bridgedTextureAtlasSprite,
                    "<init>", spriteConstructor, true);
            if ((bridgedSpriteConstructor.access & Opcodes.ACC_PUBLIC) == 0
                    || (bridgedSpriteConstructor.access
                    & (Opcodes.ACC_PRIVATE | Opcodes.ACC_PROTECTED)) != 0) {
                throw new IllegalStateException("Bridge must restore public TextureAtlasSprite constructor access");
            }
            ClassNode nativeSpriteContents = node(bytes(nativeArchive,
                    "net/minecraft/client/renderer/texture/SpriteContents.class"));
            ClassNode donorSpriteContents = node(bytes(donorArchive,
                    "srg/net/minecraft/client/renderer/texture/SpriteContents.class"));
            for (String field : List.of("width", "height")) {
                FieldNode nativeField = requireField(nativeSpriteContents, field, "I");
                FieldNode donorField = requireField(donorSpriteContents, field, "I");
                if ((nativeField.access & Opcodes.ACC_PRIVATE) == 0
                        || (donorField.access & Opcodes.ACC_PROTECTED) == 0) {
                    throw new IllegalStateException("SpriteContents " + field
                            + " must change from private to protected");
                }
            }
            byte[] bridgedSpriteContentsBytes =
                    Optifine2612K1AdmissionBridge.repairSpriteContents(bytes(donorArchive,
                            "srg/net/minecraft/client/renderer/texture/SpriteContents.class"));
            structurallyVerify(bridgedSpriteContentsBytes);
            ClassNode bridgedSpriteContents = node(bridgedSpriteContentsBytes);
            for (String field : List.of("width", "height")) {
                FieldNode bridgedField = requireField(bridgedSpriteContents, field, "I");
                if ((bridgedField.access & Opcodes.ACC_PROTECTED) == 0
                        || (bridgedField.access & Opcodes.ACC_PRIVATE) != 0) {
                    throw new IllegalStateException("Bridge must restore protected SpriteContents " + field);
                }
            }
            MethodNode repairedMipmap = requireMethod(bridgedSpriteContents,
                    "increaseMipLevel", "(I)V", true);
            requireCall(repairedMipmap, "net/minecraft/client/renderer/texture/SpriteContents",
                    "usefulToolsSafeColorBlender",
                    "(Lnet/minecraft/client/renderer/texture/TextureAtlas;"
                    + "Lnet/optifine/shaders/ShadersTextureType;)"
                    + "Lnet/optifine/texture/IColorBlender;");
            requireMethod(bridgedSpriteContents, "usefulToolsSafeColorBlender",
                    "(Lnet/minecraft/client/renderer/texture/TextureAtlas;"
                    + "Lnet/optifine/shaders/ShadersTextureType;)"
                    + "Lnet/optifine/texture/IColorBlender;", true);
            ClassNode nativeMipmapGenerator = node(bytes(nativeArchive,
                    "net/minecraft/client/renderer/texture/MipmapGenerator.class"));
            ClassNode donorMipmapGenerator = node(bytes(donorArchive,
                    "srg/net/minecraft/client/renderer/texture/MipmapGenerator.class"));
            String colorBlenderMipmap = "(Lnet/minecraft/resources/Identifier;"
                    + "[Lcom/mojang/blaze3d/platform/NativeImage;I"
                    + "Lnet/minecraft/client/renderer/texture/MipmapStrategy;"
                    + "FLcom/mojang/blaze3d/platform/Transparency;"
                    + "Lnet/optifine/texture/IColorBlender;)"
                    + "[Lcom/mojang/blaze3d/platform/NativeImage;";
            requireMethod(nativeMipmapGenerator, "generateMipLevels", colorBlenderMipmap, false);
            requireMethod(donorMipmapGenerator, "generateMipLevels", colorBlenderMipmap, true);
            ClassNode nativeLevelRenderer = node(bytes(nativeArchive,
                    "net/minecraft/client/renderer/LevelRenderer.class"));
            ClassNode donorLevelRenderer = node(bytes(donorArchive,
                    "srg/net/minecraft/client/renderer/LevelRenderer.class"));
            String outlineOwner = "net/minecraft/client/renderer/state/level/BlockOutlineRenderState";
            String outlineFour = "(Lnet/minecraft/core/BlockPos;ZZ"
                    + "Lnet/minecraft/world/phys/shapes/VoxelShape;)V";
            String outlineFive = "(Lnet/minecraft/core/BlockPos;ZZ"
                    + "Lnet/minecraft/world/phys/shapes/VoxelShape;"
                    + "Lnet/minecraft/world/level/block/state/BlockState;)V";
            ClassNode nativeOutline = node(bytes(nativeArchive, outlineOwner + ".class"));
            requireMethod(nativeOutline, "<init>", outlineFour, true);
            requireMethod(nativeOutline, "<init>", outlineFive, false);
            ClassNode repairedLevelRenderer = node(Optifine2612K1AdmissionBridge
                    .repairForge2612BlockOutline(bytes(donorArchive,
                            "srg/net/minecraft/client/renderer/LevelRenderer.class")));
            int unresolvedOutlineCalls = 0;
            int repairedOutlineCalls = 0;
            int unresolvedStateLookups = 0;
            int nativeStateLookups = 0;
            for (MethodNode method : repairedLevelRenderer.methods) {
                for (AbstractInsnNode instruction : method.instructions) {
                    if (!(instruction instanceof MethodInsnNode call)) continue;
                    if (call.owner.equals(outlineOwner) && call.name.equals("<init>")) {
                        if (call.desc.equals(outlineFive)) unresolvedOutlineCalls++;
                        if (call.desc.equals(outlineFour)) repairedOutlineCalls++;
                    }
                    if (call.owner.equals(outlineOwner) && call.name.equals("blockState")) {
                        unresolvedStateLookups++;
                    }
                    if (method.name.equals("renderHitOutline")
                            && call.owner.equals("net/minecraft/client/multiplayer/ClientLevel")
                            && call.name.equals("getBlockState")) nativeStateLookups++;
                }
            }
            if (unresolvedOutlineCalls != 0 || repairedOutlineCalls < 1
                    || unresolvedStateLookups != 0 || nativeStateLookups != 1) {
                throw new IllegalStateException("K1 block-outline calls still mismatch pinned Forge");
            }
            requireMethod(nativeLevelRenderer, "endFrame", "()V", true);
            requireMethod(nativeLevelRenderer, "getFrameCount", "()I", false);
            requireMethod(donorLevelRenderer, "getFrameCount", "()I", true);
            requireMethod(nativeLevelRenderer, "onPlayerPositionSet", "()V", false);
            requireMethod(donorLevelRenderer, "onPlayerPositionSet", "()V", true);
            requireMethod(nativeLevelRenderer, "allChanged", "()V", true);
            requireMethod(nativeLevelRenderer, "getRenderInfosTerrain",
                    "()Ljava/util/List;", false);
            requireMethod(donorLevelRenderer, "getRenderInfosTerrain",
                    "()Ljava/util/List;", true);
            String chunkUpdateDescriptor =
                    "(Lnet/minecraft/client/renderer/chunk/SectionRenderDispatcher$RenderSection;)V";
            requireMethod(nativeLevelRenderer, "onChunkRenderNeedsUpdate",
                    chunkUpdateDescriptor, false);
            requireMethod(donorLevelRenderer, "onChunkRenderNeedsUpdate",
                    chunkUpdateDescriptor, true);
            ClassNode nativeSectionCompiler = node(bytes(nativeArchive,
                    "net/minecraft/client/renderer/chunk/SectionCompiler.class"));
            ClassNode donorSectionCompiler = node(bytes(donorArchive,
                    "srg/net/minecraft/client/renderer/chunk/SectionCompiler.class"));
            String optifineCompile = "(Lnet/minecraft/core/SectionPos;"
                    + "Lnet/optifine/override/ChunkCacheOF;"
                    + "Lcom/mojang/blaze3d/vertex/VertexSorting;"
                    + "Lnet/minecraft/client/renderer/SectionBufferBuilderPack;III)"
                    + "Lnet/minecraft/client/renderer/chunk/SectionCompiler$Results;";
            requireMethod(nativeSectionCompiler, "compile", optifineCompile, false);
            requireMethod(donorSectionCompiler, "compile", optifineCompile, true);
            ClassNode nativeSectionResults = node(bytes(nativeArchive,
                    "net/minecraft/client/renderer/chunk/SectionCompiler$Results.class"));
            ClassNode donorSectionResults = node(bytes(donorArchive,
                    "srg/net/minecraft/client/renderer/chunk/SectionCompiler$Results.class"));
            if (!memberInventory(donorSectionResults).containsAll(
                    memberInventory(nativeSectionResults))) {
                throw new IllegalStateException("K1 SectionCompiler.Results loses pinned members");
            }
            String animatedSprites = "(Lnet/minecraft/client/renderer/chunk/ChunkSectionLayer;"
                    + "Ljava/util/BitSet;)V";
            requireMethod(nativeSectionResults, "setAnimatedSprites", animatedSprites, false);
            requireMethod(donorSectionResults, "setAnimatedSprites", animatedSprites, true);
            ClassNode nativeModelSubmit = node(bytes(nativeArchive,
                    "net/minecraft/client/renderer/SubmitNodeStorage$ModelSubmit.class"));
            ClassNode donorModelSubmit = node(bytes(donorArchive,
                    "srg/net/minecraft/client/renderer/SubmitNodeStorage$ModelSubmit.class"));
            if (!memberInventory(donorModelSubmit).containsAll(memberInventory(nativeModelSubmit))) {
                throw new IllegalStateException("K1 ModelSubmit loses pinned record members");
            }
            requireMethod(nativeModelSubmit, "renderState", "()Ljava/lang/Object;", false);
            requireMethod(donorModelSubmit, "renderState", "()Ljava/lang/Object;", true);
            ClassNode nativeFluidRenderer = node(bytes(nativeArchive,
                    "net/minecraft/client/renderer/block/FluidRenderer.class"));
            ClassNode donorFluidRenderer = node(bytes(donorArchive,
                    "srg/net/minecraft/client/renderer/block/FluidRenderer.class"));
            if (!memberInventory(donorFluidRenderer).containsAll(
                    memberInventory(nativeFluidRenderer))) {
                throw new IllegalStateException("K1 FluidRenderer loses pinned members");
            }
            String fluidModels = "()Lnet/minecraft/client/renderer/block/FluidStateModelSet;";
            requireMethod(nativeFluidRenderer, "getFluidModels", fluidModels, false);
            requireMethod(donorFluidRenderer, "getFluidModels", fluidModels, true);
            ClassNode partialLevelRenderer = node(bytes(donorArchive,
                    "srg/net/minecraft/client/renderer/LevelRenderer.class"));
            if (!partialLevelRenderer.methods.removeIf(method -> method.name.equals(
                    "onPlayerPositionSet") && method.desc.equals("()V"))) {
                throw new IllegalStateException("Cannot construct partial K1 LevelRenderer fixture");
            }
            ClassWriter partialWriter = new ClassWriter(0);
            partialLevelRenderer.accept(partialWriter);
            byte[] mergedLevelRendererBytes = Optifine2612K1AdmissionBridge
                    .mergeLevelRenderer(partialWriter.toByteArray(), bytes(donorArchive,
                            "srg/net/minecraft/client/renderer/LevelRenderer.class"));
            structurallyVerify(mergedLevelRendererBytes);
            ClassNode mergedLevelRenderer = node(mergedLevelRendererBytes);
            if (!memberInventory(mergedLevelRenderer).equals(memberInventory(donorLevelRenderer))) {
                throw new IllegalStateException("Merged LevelRenderer inventory differs from donor");
            }
            System.out.println("verified_level_renderer_fields=" + mergedLevelRenderer.fields.size());
            System.out.println("verified_level_renderer_methods=" + mergedLevelRenderer.methods.size());
            MethodNode mergedPosition = requireMethod(mergedLevelRenderer,
                    "onPlayerPositionSet", "()V", true);
            requireFieldRead(mergedPosition, mergedLevelRenderer.name, "firstWorldLoad", "Z");
            requireFieldWrite(mergedPosition, mergedLevelRenderer.name, "firstWorldLoad", "Z");
            requireVirtualCall(mergedPosition, mergedLevelRenderer.name, "allChanged", "()V");
            byte[] idempotentLevelRenderer = Optifine2612K1AdmissionBridge
                    .mergeLevelRenderer(mergedLevelRendererBytes, bytes(donorArchive,
                            "srg/net/minecraft/client/renderer/LevelRenderer.class"));
            if (!java.util.Arrays.equals(mergedLevelRendererBytes, idempotentLevelRenderer)) {
                throw new IllegalStateException("Merged LevelRenderer is not idempotent");
            }
            int forgeLevelRendererCalls = requireAllLevelRendererCallSitesResolved(
                    nativeArchive, "", mergedLevelRenderer);
            int optifineLevelRendererCalls = requireAllLevelRendererCallSitesResolved(
                    donorArchive, "srg/", mergedLevelRenderer);
            System.out.println("verified_forge_level_renderer_call_sites="
                    + forgeLevelRendererCalls);
            System.out.println("verified_optifine_level_renderer_call_sites="
                    + optifineLevelRendererCalls);
            byte[] bridgedLevelRendererBytes = Optifine2612K1AdmissionBridge
                    .addLevelRendererFrameCount(bytes(nativeArchive,
                            "net/minecraft/client/renderer/LevelRenderer.class"));
            structurallyVerify(bridgedLevelRendererBytes);
            ClassNode bridgedLevelRenderer = node(bridgedLevelRendererBytes);
            requireField(bridgedLevelRenderer, "usefulToolsFrameCount", "I");
            requireField(bridgedLevelRenderer, "firstWorldLoad", "Z");
            requireField(bridgedLevelRenderer, "renderInfosTerrain",
                    "Lit/unimi/dsi/fastutil/objects/ObjectArrayList;");
            requireField(bridgedLevelRenderer, "renderInfosTileEntities", "Ljava/util/List;");
            requireField(bridgedLevelRenderer, "renderDistance", "I");
            requireField(bridgedLevelRenderer, "renderDistanceXZSq", "I");
            MethodNode frameGetter = requireMethod(bridgedLevelRenderer,
                    "getFrameCount", "()I", true);
            requireFieldRead(frameGetter, "net/minecraft/client/renderer/LevelRenderer",
                    "usefulToolsFrameCount", "I");
            MethodNode frameEnd = requireMethod(bridgedLevelRenderer, "endFrame", "()V", true);
            requireFieldWrite(frameEnd, "net/minecraft/client/renderer/LevelRenderer",
                    "usefulToolsFrameCount", "I");
            MethodNode onPosition = requireMethod(bridgedLevelRenderer,
                    "onPlayerPositionSet", "()V", true);
            requireFieldRead(onPosition, bridgedLevelRenderer.name, "firstWorldLoad", "Z");
            requireFieldWrite(onPosition, bridgedLevelRenderer.name, "firstWorldLoad", "Z");
            requireVirtualCall(onPosition, bridgedLevelRenderer.name, "allChanged", "()V");
            MethodNode chunkUpdate = requireMethod(bridgedLevelRenderer,
                    "onChunkRenderNeedsUpdate", chunkUpdateDescriptor, true);
            requireVirtualCall(chunkUpdate,
                    "net/minecraft/client/renderer/chunk/SectionRenderDispatcher$RenderSection",
                    "getSectionMesh", "()Lnet/minecraft/client/renderer/chunk/SectionMesh;");
            requireFieldRead(requireMethod(bridgedLevelRenderer, "getRenderInfos",
                    "()Ljava/util/List;", true), bridgedLevelRenderer.name,
                    "visibleSections", "Lit/unimi/dsi/fastutil/objects/ObjectArrayList;");
            requireFieldRead(requireMethod(bridgedLevelRenderer, "getRenderInfosTerrain",
                    "()Ljava/util/List;", true), bridgedLevelRenderer.name,
                    "renderInfosTerrain", "Lit/unimi/dsi/fastutil/objects/ObjectArrayList;");
            requireFieldRead(requireMethod(bridgedLevelRenderer, "getRenderInfosTileEntities",
                    "()Ljava/util/List;", true), bridgedLevelRenderer.name,
                    "renderInfosTileEntities", "Ljava/util/List;");
            MethodNode rendererRefresh = requireMethod(bridgedLevelRenderer,
                    "allChanged", "()V", true);
            requireFieldWrite(rendererRefresh, bridgedLevelRenderer.name,
                    "renderDistance", "I");
            requireFieldWrite(rendererRefresh, bridgedLevelRenderer.name,
                    "renderDistanceXZSq", "I");
            ClassNode nativeFrustum = node(bytes(nativeArchive,
                    "net/minecraft/client/renderer/culling/Frustum.class"));
            ClassNode donorFrustum = node(bytes(donorArchive,
                    "srg/net/minecraft/client/renderer/culling/Frustum.class"));
            requireMethod(nativeFrustum, "<init>",
                    "(Lorg/joml/Matrix4fc;Lorg/joml/Matrix4f;Z)V", false);
            requireMethod(donorFrustum, "<init>",
                    "(Lorg/joml/Matrix4fc;Lorg/joml/Matrix4f;Z)V", true);
            for (MethodNode nativeMethod : nativeFrustum.methods) {
                requireMethod(donorFrustum, nativeMethod.name, nativeMethod.desc, true);
            }
            ClassNode nativeLevelRendererForDonor = node(bytes(nativeArchive,
                    "net/minecraft/client/renderer/LevelRenderer.class"));
            byte[] fullDonorLevelBytes = bytes(donorArchive,
                    "srg/net/minecraft/client/renderer/LevelRenderer.class");
            structurallyVerify(fullDonorLevelBytes);
            ClassNode fullDonorLevelRenderer = node(fullDonorLevelBytes);
            for (MethodNode nativeMethod : nativeLevelRendererForDonor.methods) {
                if ((nativeMethod.access & (Opcodes.ACC_PUBLIC | Opcodes.ACC_PROTECTED)) == 0) continue;
                MethodNode donorMethod = requireMethod(fullDonorLevelRenderer,
                        nativeMethod.name, nativeMethod.desc, true);
                if ((donorMethod.access & (Opcodes.ACC_PUBLIC | Opcodes.ACC_PROTECTED)) == 0) {
                    throw new IllegalStateException("LevelRenderer donor narrows exposed method "
                            + nativeMethod.name + nativeMethod.desc);
                }
            }
            ClassNode nativeItemFrame = node(bytes(nativeArchive,
                    "net/minecraft/client/renderer/entity/ItemFrameRenderer.class"));
            ClassNode donorItemFrame = node(bytes(donorArchive,
                    "srg/net/minecraft/client/renderer/entity/ItemFrameRenderer.class"));
            if (!memberInventory(donorItemFrame).containsAll(memberInventory(nativeItemFrame))) {
                throw new IllegalStateException("ItemFrameRenderer donor omits native members");
            }
            requireMethod(nativeItemFrame, "updateItemRenderDistance", "()V", false);
            requireMethod(donorItemFrame, "updateItemRenderDistance", "()V", true);
            ClassNode nativeQuadInstance = node(bytes(nativeArchive,
                    "com/mojang/blaze3d/vertex/QuadInstance.class"));
            ClassNode donorQuadInstance = node(bytes(donorArchive,
                    "srg/com/mojang/blaze3d/vertex/QuadInstance.class"));
            if (!memberInventory(donorQuadInstance).containsAll(memberInventory(nativeQuadInstance))) {
                throw new IllegalStateException("QuadInstance donor omits native members");
            }
            requireMethod(nativeQuadInstance, "getMidBlock", "()Lorg/joml/Vector3f;", false);
            requireMethod(donorQuadInstance, "getMidBlock", "()Lorg/joml/Vector3f;", true);
            ClassNode nativeSubmitNodes = node(bytes(nativeArchive,
                    "net/minecraft/client/renderer/SubmitNodeStorage.class"));
            ClassNode donorSubmitNodes = node(bytes(donorArchive,
                    "srg/net/minecraft/client/renderer/SubmitNodeStorage.class"));
            requireMethod(nativeSubmitNodes, "setShadersStorage", "(Z)V", false);
            requireMethod(donorSubmitNodes, "setShadersStorage", "(Z)V", true);
            String forgeBreakingBlock = "(Lcom/mojang/blaze3d/vertex/PoseStack;"
                    + "Lnet/minecraft/client/renderer/block/dispatch/BlockStateModel;JI"
                    + "Lnet/minecraftforge/client/model/data/ModelData;)V";
            requireMethod(nativeSubmitNodes, "submitBreakingBlockModel", forgeBreakingBlock, true);
            requireMethod(donorSubmitNodes, "submitBreakingBlockModel", forgeBreakingBlock, false);
            byte[] bridgedSubmitBytes = Optifine2612K1AdmissionBridge
                    .addSubmitNodeShaderStorage(bytes(nativeArchive,
                            "net/minecraft/client/renderer/SubmitNodeStorage.class"));
            structurallyVerify(bridgedSubmitBytes);
            ClassNode bridgedSubmitNodes = node(bridgedSubmitBytes);
            requireMethod(bridgedSubmitNodes, "submitBreakingBlockModel", forgeBreakingBlock, true);
            MethodNode shaderStorage = requireMethod(bridgedSubmitNodes,
                    "setShadersStorage", "(Z)V", true);
            requireField(bridgedSubmitNodes, "submitsPerOrderMain",
                    "Lit/unimi/dsi/fastutil/ints/Int2ObjectAVLTreeMap;");
            requireField(bridgedSubmitNodes, "submitsPerOrderShaders",
                    "Lit/unimi/dsi/fastutil/ints/Int2ObjectAVLTreeMap;");
            if (shaderStorage.instructions.size() < 8) {
                throw new IllegalStateException("SubmitNodeStorage shader switch is empty");
            }
            ClassNode nativeGuiExtractor = node(bytes(nativeArchive,
                    "net/minecraft/client/gui/GuiGraphicsExtractor.class"));
            ClassNode donorGuiExtractor = node(bytes(donorArchive,
                    "srg/net/minecraft/client/gui/GuiGraphicsExtractor.class"));
            String guiBlit = "(Lnet/minecraft/resources/Identifier;IIIIFFFFI)V";
            requireMethod(nativeGuiExtractor, "blit", guiBlit, false);
            requireMethod(donorGuiExtractor, "blit", guiBlit, true);
            String forgeHover = "(Lnet/minecraft/client/gui/Font;"
                    + "Lnet/minecraft/network/chat/Style;II)V";
            if ((requireMethod(nativeGuiExtractor, "componentHoverEffect", forgeHover, true)
                    .access & Opcodes.ACC_PUBLIC) == 0
                    || (requireMethod(donorGuiExtractor, "componentHoverEffect", forgeHover, true)
                    .access & Opcodes.ACC_PUBLIC) != 0) {
                throw new IllegalStateException("GUI donor would narrow pinned hover API");
            }
            byte[] bridgedGuiBytes = Optifine2612K1AdmissionBridge.addGuiGraphicsExtractorBlit(
                    bytes(nativeArchive, "net/minecraft/client/gui/GuiGraphicsExtractor.class"),
                    bytes(donorArchive, "srg/net/minecraft/client/gui/GuiGraphicsExtractor.class"));
            structurallyVerify(bridgedGuiBytes);
            ClassNode bridgedGui = node(bridgedGuiBytes);
            requireMethod(bridgedGui, "blit", guiBlit, true);
            if ((requireMethod(bridgedGui, "componentHoverEffect", forgeHover, true)
                    .access & Opcodes.ACC_PUBLIC) == 0) {
                throw new IllegalStateException("Bridged GUI lost pinned public hover API");
            }
            byte[] bridgedFrustumBytes = Optifine2612K1AdmissionBridge
                    .addFrustumCameraAliases(bytes(nativeArchive,
                            "net/minecraft/client/renderer/culling/Frustum.class"));
            structurallyVerify(bridgedFrustumBytes);
            ClassNode bridgedFrustum = node(bridgedFrustumBytes);
            for (String axis : List.of("X", "Y", "Z")) {
                String nativeGetter = "getCam" + axis;
                String donorGetter = "getCamera" + axis;
                requireMethod(nativeFrustum, nativeGetter, "()D", true);
                requireMethod(nativeFrustum, donorGetter, "()D", false);
                requireMethod(donorFrustum, donorGetter, "()D", true);
                requireVirtualCall(requireMethod(bridgedFrustum, donorGetter, "()D", true),
                        bridgedFrustum.name, nativeGetter, "()D");
            }
            ClassNode nativeEntityData = node(bytes(nativeArchive,
                    "net/minecraft/network/syncher/SynchedEntityData.class"));
            ClassNode donorEntityData = node(bytes(donorArchive,
                    "srg/net/minecraft/network/syncher/SynchedEntityData.class"));
            byte[] bridgedEntityDataBytes = Optifine2612K1AdmissionBridge
                    .addOptifineEntityDataFields(bytes(nativeArchive,
                            "net/minecraft/network/syncher/SynchedEntityData.class"));
            structurallyVerify(bridgedEntityDataBytes);
            ClassNode bridgedEntityData = node(bridgedEntityDataBytes);
            String[][] randomEntityFields = {
                    {"spawnBiome", "Lnet/minecraft/world/level/biome/Biome;"},
                    {"spawnPosition", "Lnet/minecraft/core/BlockPos;"},
                    {"blockStateOn", "Lnet/minecraft/world/level/block/state/BlockState;"},
                    {"blockStateOnUpdateMs", "J"},
                    {"modelVariables", "Ljava/util/Map;"},
                    {"nbtTag", "Lnet/minecraft/nbt/CompoundTag;"},
                    {"nbtTagUpdateMs", "J"},
            };
            for (String[] field : randomEntityFields) {
                if (nativeEntityData.fields.stream().anyMatch(
                        existing -> existing.name.equals(field[0]))) {
                    throw new IllegalStateException("Pinned entity data already has " + field[0]);
                }
                if ((requireField(donorEntityData, field[0], field[1]).access
                        & Opcodes.ACC_PUBLIC) == 0) {
                    throw new IllegalStateException("K1 field is not public: " + field[0]);
                }
                if ((requireField(bridgedEntityData, field[0], field[1]).access
                        & Opcodes.ACC_PUBLIC) == 0) {
                    throw new IllegalStateException("Bridge field is not public: " + field[0]);
                }
            }
            ClassNode nativeModelBlock = node(bytes(nativeArchive,
                    "net/minecraft/client/renderer/block/ModelBlockRenderer.class"));
            ClassNode donorModelBlock = node(bytes(donorArchive,
                    "srg/net/minecraft/client/renderer/block/ModelBlockRenderer.class"));
            String cacheType = "Lnet/optifine/render/LightCacheOF;";
            requireMethod(nativeModelBlock, "getLightCacheOf", "()" + cacheType, false);
            requireMethod(donorModelBlock, "getLightCacheOf", "()" + cacheType, true);
            requireField(donorModelBlock, "LIGHT_CACHE_OF", cacheType);
            String renderEnvType = "Lnet/optifine/render/RenderEnv;";
            String renderEnvGetter = "(Lnet/minecraft/world/level/block/state/BlockState;"
                    + "Lnet/minecraft/core/BlockPos;)" + renderEnvType;
            requireMethod(nativeModelBlock, "getRenderEnv", renderEnvGetter, false);
            requireMethod(donorModelBlock, "getRenderEnv", renderEnvGetter, true);
            requireField(donorModelBlock, "renderEnv", renderEnvType);
            requireMethod(nativeModelBlock, "setMidBlock", "(FFF)V", false);
            requireMethod(donorModelBlock, "setMidBlock", "(FFF)V", true);
            requireField(nativeModelBlock, "quadInstance",
                    "Lcom/mojang/blaze3d/vertex/QuadInstance;");
            for (String aoMethod : List.of("fixAoLightValue", "updateAoLightValue",
                    "isSeparateAoLightValue")) {
                String descriptor = aoMethod.equals("fixAoLightValue") ? "(F)F"
                        : aoMethod.equals("updateAoLightValue") ? "()V" : "()Z";
                requireMethod(nativeModelBlock, aoMethod, descriptor, false);
                requireMethod(donorModelBlock, aoMethod, descriptor, true);
            }
            for (String getter : List.of("isAmbientOcclusion", "isCull", "getBlockColors")) {
                String descriptor = getter.equals("getBlockColors")
                        ? "()Lnet/minecraft/client/color/block/BlockColors;" : "()Z";
                requireMethod(nativeModelBlock, getter, descriptor, false);
                requireMethod(donorModelBlock, getter, descriptor, true);
            }
            byte[] bridgedModelBlockBytes = Optifine2612K1AdmissionBridge
                    .addModelBlockLightCache(bytes(nativeArchive,
                            "net/minecraft/client/renderer/block/ModelBlockRenderer.class"));
            structurallyVerify(bridgedModelBlockBytes);
            ClassNode bridgedModelBlock = node(bridgedModelBlockBytes);
            requireVirtualCall(requireMethod(bridgedModelBlock,
                    "setMidBlock", "(FFF)V", true),
                    "com/mojang/blaze3d/vertex/QuadInstance", "getMidBlock",
                    "()Lorg/joml/Vector3f;");
            requireField(bridgedModelBlock, "LIGHT_CACHE_OF", cacheType);
            requireFieldInstruction(requireMethod(bridgedModelBlock,
                    "getLightCacheOf", "()" + cacheType, true), bridgedModelBlock.name,
                    "LIGHT_CACHE_OF", cacheType, Opcodes.GETSTATIC);
            requireFieldInstruction(requireMethod(bridgedModelBlock, "<clinit>", "()V", true),
                    bridgedModelBlock.name, "LIGHT_CACHE_OF", cacheType, Opcodes.PUTSTATIC);
            requireField(bridgedModelBlock, "renderEnv", renderEnvType);
            requireField(bridgedModelBlock, "aoLightValueOpaque", "F");
            requireField(bridgedModelBlock, "separateAoLightValue", "Z");
            requireFieldInstruction(requireMethod(bridgedModelBlock,
                    "<clinit>", "()V", true), bridgedModelBlock.name,
                    "aoLightValueOpaque", "F", Opcodes.PUTSTATIC);
            requireFieldInstruction(requireMethod(bridgedModelBlock,
                    "fixAoLightValue", "(F)F", true), bridgedModelBlock.name,
                    "aoLightValueOpaque", "F", Opcodes.GETSTATIC);
            requireFieldInstruction(requireMethod(bridgedModelBlock,
                    "updateAoLightValue", "()V", true), bridgedModelBlock.name,
                    "aoLightValueOpaque", "F", Opcodes.PUTSTATIC);
            requireFieldInstruction(requireMethod(bridgedModelBlock,
                    "isSeparateAoLightValue", "()Z", true), bridgedModelBlock.name,
                    "separateAoLightValue", "Z", Opcodes.GETSTATIC);
            requireFieldRead(requireMethod(bridgedModelBlock,
                    "isAmbientOcclusion", "()Z", true), bridgedModelBlock.name,
                    "ambientOcclusion", "Z");
            requireFieldRead(requireMethod(bridgedModelBlock,
                    "isCull", "()Z", true), bridgedModelBlock.name, "cull", "Z");
            requireFieldRead(requireMethod(bridgedModelBlock,
                    "getBlockColors", "()Lnet/minecraft/client/color/block/BlockColors;", true),
                    bridgedModelBlock.name, "blockColors",
                    "Lnet/minecraft/client/color/block/BlockColors;");
            requireFieldWrite(requireMethod(bridgedModelBlock, "<init>",
                    "(ZZLnet/minecraft/client/color/block/BlockColors;)V", true),
                    bridgedModelBlock.name, "renderEnv", renderEnvType);
            MethodNode renderEnvMethod = requireMethod(bridgedModelBlock,
                    "getRenderEnv", renderEnvGetter, true);
            int envReads = 0;
            for (AbstractInsnNode instruction : renderEnvMethod.instructions) {
                if (instruction instanceof FieldInsnNode field
                        && field.getOpcode() == Opcodes.GETFIELD
                        && field.owner.equals(bridgedModelBlock.name)
                        && field.name.equals("renderEnv")
                        && field.desc.equals(renderEnvType)) envReads++;
            }
            if (envReads != 2) throw new IllegalStateException(
                    "K1 render environment must reset then return the same context: " + envReads);
            requireVirtualCall(renderEnvMethod, "net/optifine/render/RenderEnv", "reset",
                    "(Lnet/minecraft/world/level/block/state/BlockState;"
                    + "Lnet/minecraft/core/BlockPos;)V");
            requireMethod(bridgedModelBlock, "tesselateBlock",
                    "(Lnet/minecraft/client/renderer/block/BlockQuadOutput;FFFLnet/minecraft/client/renderer/block/BlockAndTintGetter;"
                    + "Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;"
                    + "Lnet/minecraft/client/renderer/block/dispatch/BlockStateModel;J"
                    + "Lnet/minecraftforge/client/model/data/ModelData;)V", true);
            ClassNode nativeClientLevel = node(bytes(nativeArchive,
                    "net/minecraft/client/multiplayer/ClientLevel.class"));
            ClassNode donorClientLevel = node(bytes(donorArchive,
                    "srg/net/minecraft/client/multiplayer/ClientLevel.class"));
            String nativeSetBlock = "(Lnet/minecraft/core/BlockPos;"
                    + "Lnet/minecraft/world/level/block/state/BlockState;II)Z";
            requireMethod(nativeClientLevel, "setBlock", nativeSetBlock, true);
            requireMethod(nativeClientLevel, "isPlayerUpdate", "()Z", false);
            requireMethod(donorClientLevel, "isPlayerUpdate", "()Z", true);
            requireMethod(nativeClientLevel, "getSectionStorage",
                    "()Lnet/minecraft/world/level/entity/EntitySectionStorage;", false);
            requireMethod(donorClientLevel, "getSectionStorage",
                    "()Lnet/minecraft/world/level/entity/EntitySectionStorage;", true);
            requireField(nativeClientLevel, "entityStorage",
                    "Lnet/minecraft/world/level/entity/TransientEntitySectionManager;");
            byte[] bridgedClientLevelBytes = Optifine2612K1AdmissionBridge
                    .addClientLevelPlayerUpdate(bytes(nativeArchive,
                            "net/minecraft/client/multiplayer/ClientLevel.class"));
            structurallyVerify(bridgedClientLevelBytes);
            ClassNode bridgedClientLevel = node(bridgedClientLevelBytes);
            requireField(bridgedClientLevel, "usefulToolsPlayerUpdate", "Z");
            MethodNode playerUpdate = requireMethod(bridgedClientLevel,
                    "isPlayerUpdate", "()Z", true);
            requireFieldRead(playerUpdate, bridgedClientLevel.name,
                    "usefulToolsPlayerUpdate", "Z");
            MethodNode sectionStorage = requireMethod(bridgedClientLevel, "getSectionStorage",
                    "()Lnet/minecraft/world/level/entity/EntitySectionStorage;", true);
            requireCall(sectionStorage, "net/optifine/reflect/Reflector", "getFieldValue",
                    "(Ljava/lang/Object;Lnet/optifine/reflect/ReflectorField;)Ljava/lang/Object;");
            MethodNode bridgedSetBlock = requireMethod(bridgedClientLevel,
                    "setBlock", nativeSetBlock, true);
            int playerUpdateWrites = 0;
            for (AbstractInsnNode instruction : bridgedSetBlock.instructions) {
                if (instruction instanceof org.objectweb.asm.tree.FieldInsnNode field
                        && field.getOpcode() == Opcodes.PUTFIELD
                        && field.owner.equals(bridgedClientLevel.name)
                        && field.name.equals("usefulToolsPlayerUpdate")
                        && field.desc.equals("Z")) playerUpdateWrites++;
            }
            if (playerUpdateWrites != 4) throw new IllegalStateException(
                    "ClientLevel.setBlock must set and reset the player-update flag on both paths: "
                    + playerUpdateWrites);
            requireVirtualCall(bridgedSetBlock, "net/optifine/override/PlayerControllerOF",
                    "isActing", "()Z");
            byte[] originalBetterGrass = bytes(officialArchive,
                    "srg/net/optifine/BetterGrass.class");
            ClassNode nativeBetterGrass = node(originalBetterGrass);
            requireField(nativeBetterGrass, "spriteGrass",
                    "Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;");
            requireMethod(nativeBetterGrass, "refreshIcons",
                    "(Lnet/minecraft/client/renderer/texture/TextureAtlas;)V", true);
            byte[] guardedBetterGrassBytes = Optifine2612K1AdmissionBridge
                    .guardBetterGrassRefresh(originalBetterGrass);
            structurallyVerify(guardedBetterGrassBytes);
            MethodNode guardedRefresh = requireMethod(node(guardedBetterGrassBytes),
                    "refreshIcons", "(Lnet/minecraft/client/renderer/texture/TextureAtlas;)V", true);
            List<Integer> firstInstructions = new ArrayList<Integer>();
            for (AbstractInsnNode instruction : guardedRefresh.instructions) {
                if (instruction.getOpcode() >= 0) firstInstructions.add(instruction.getOpcode());
                if (firstInstructions.size() == 3) break;
            }
            if (!firstInstructions.equals(List.of(Opcodes.GETSTATIC, Opcodes.IFNONNULL,
                    Opcodes.RETURN))) {
                throw new IllegalStateException("BetterGrass refresh must return only for null sprite: "
                        + firstInstructions);
            }
            ClassNode nativeDirection = node(bytes(nativeArchive,
                    "net/minecraft/core/Direction.class"));
            ClassNode donorDirection = node(bytes(donorArchive,
                    "srg/net/minecraft/core/Direction.class"));
            String directionArray = "[Lnet/minecraft/core/Direction;";
            if ((requireField(nativeDirection, "VALUES", directionArray).access
                    & Opcodes.ACC_PRIVATE) == 0
                    || (requireField(donorDirection, "VALUES", directionArray).access
                    & Opcodes.ACC_PUBLIC) == 0) {
                throw new IllegalStateException("K1 Direction.VALUES access mismatch");
            }
            byte[] widenedDirection = Optifine2612K1AdmissionBridge
                    .widenDirectionValues(bytes(nativeArchive,
                            "net/minecraft/core/Direction.class"));
            structurallyVerify(widenedDirection);
            if ((requireField(node(widenedDirection), "VALUES", directionArray).access
                    & Opcodes.ACC_PUBLIC) == 0) {
                throw new IllegalStateException("Bridge did not expose K1 Direction.VALUES");
            }
            for (String optifineClass : List.of("net/optifine/render/RenderEnv",
                    "net/optifine/BlockPosM")) {
                ClassNode caller = node(bytes(officialArchive, "srg/" + optifineClass + ".class"));
                int reads = 0;
                for (MethodNode method : caller.methods) {
                    for (AbstractInsnNode instruction : method.instructions) {
                        if (instruction instanceof FieldInsnNode field
                                && field.getOpcode() == Opcodes.GETSTATIC
                                && field.owner.equals(nativeDirection.name)
                                && field.name.equals("VALUES")
                                && field.desc.equals(directionArray)) reads++;
                    }
                }
                if (reads == 0) throw new IllegalStateException(
                        "K1 caller no longer reads Direction.VALUES: " + optifineClass);
            }
            ClassNode nativeBlockState = node(bytes(nativeArchive,
                    "net/minecraft/world/level/block/state/BlockState.class"));
            ClassNode donorBlockState = node(bytes(donorArchive,
                    "srg/net/minecraft/world/level/block/state/BlockState.class"));
            if (!nativeBlockState.interfaces.equals(donorBlockState.interfaces)
                    || !donorBlockState.interfaces.contains(
                            "net/minecraftforge/common/extensions/IForgeBlockState")
                    || !memberInventory(donorBlockState).containsAll(
                            memberInventory(nativeBlockState))) {
                throw new IllegalStateException("K1 BlockState loses pinned Forge members");
            }
            requireMethod(nativeBlockState, "hasEmissive", "()Z", false);
            requireMethod(donorBlockState, "hasEmissive", "()Z", true);
            requireField(donorBlockState, "hasEmissive", "Z");
            ClassNode nativeEntityDispatcher = node(bytes(nativeArchive,
                    "net/minecraft/client/renderer/entity/EntityRenderDispatcher.class"));
            ClassNode donorEntityDispatcher = node(bytes(donorArchive,
                    "srg/net/minecraft/client/renderer/entity/EntityRenderDispatcher.class"));
            String rendererContext =
                    "Lnet/minecraft/client/renderer/entity/EntityRendererProvider$Context;";
            requireMethod(nativeEntityDispatcher, "getContext", "()" + rendererContext, false);
            requireMethod(donorEntityDispatcher, "getContext", "()" + rendererContext, true);
            byte[] bridgedDispatcherBytes = Optifine2612K1AdmissionBridge
                    .addEntityRenderDispatcherContext(bytes(nativeArchive,
                            "net/minecraft/client/renderer/entity/EntityRenderDispatcher.class"));
            structurallyVerify(bridgedDispatcherBytes);
            ClassNode bridgedDispatcher = node(bridgedDispatcherBytes);
            requireField(bridgedDispatcher, "usefulToolsContext", rendererContext);
            requireFieldRead(requireMethod(bridgedDispatcher, "getContext",
                    "()" + rendererContext, true), bridgedDispatcher.name,
                    "usefulToolsContext", rendererContext);
            requireFieldWrite(requireMethod(bridgedDispatcher, "onResourceManagerReload",
                    "(Lnet/minecraft/server/packs/resources/ResourceManager;)V", true),
                    bridgedDispatcher.name, "usefulToolsContext", rendererContext);
            ClassNode nativeModelManager = node(bytes(nativeArchive,
                    "net/minecraft/client/resources/model/ModelManager.class"));
            ClassNode donorModelManager = node(bytes(donorArchive,
                    "srg/net/minecraft/client/resources/model/ModelManager.class"));
            requireMethod(nativeModelManager, "fixMaterial",
                    "(Lnet/minecraft/client/resources/model/sprite/Material;)Lnet/minecraft/client/resources/model/sprite/Material;",
                    false);
            requireMethod(donorModelManager, "fixMaterial",
                    "(Lnet/minecraft/client/resources/model/sprite/Material;)Lnet/minecraft/client/resources/model/sprite/Material;",
                    true);
            ClassNode nativeQuadCollection = node(bytes(nativeArchive,
                    "net/minecraft/client/resources/model/geometry/QuadCollection.class"));
            ClassNode donorQuadCollection = node(bytes(donorArchive,
                    "srg/net/minecraft/client/resources/model/geometry/QuadCollection.class"));
            requireMethod(nativeQuadCollection, "isBuiltinGenerated", "()Z", false);
            requireMethod(donorQuadCollection, "isBuiltinGenerated", "()Z", true);

            ClassNode options = node(bytes(donorArchive, "srg/net/minecraft/client/Options.class"));
            MethodNode constructor = requireMethod(options, "<init>",
                    "(Lnet/minecraft/client/Minecraft;Ljava/io/File;)V", true);
            MethodNode save = requireMethod(options, "save", "()V", true);
            requireCall(constructor, "net/optifine/Config", "initMinecraft",
                    "(Lnet/minecraft/client/Minecraft;)V");
            requireCall(constructor, "net/optifine/Config", "initGameSettings",
                    "(Lnet/minecraft/client/Options;)V");
            requireCall(save, "net/minecraftforge/client/loading/ClientModLoader", "isLoading", "()Z");
        }
        checked.sort(String::compareTo);
        System.out.println("verified_native_sha256=" + NATIVE_SHA256);
        System.out.println("verified_donor_sha256=" + DONOR_SHA256);
        System.out.println("verified_official_k1_sha256=" + OFFICIAL_SHA256);
        System.out.println("verified_selected_classes=" + checked.size());
        for (String name : checked) System.out.println("verified_class=" + name);
    }

    private static boolean selected(String entry, String prefix) {
        if (!entry.startsWith(prefix) || !entry.endsWith(".class")) return false;
        String name = entry.substring(prefix.length(), entry.length() - 6);
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
                || name.equals("net/minecraft/resources/Identifier")
                || name.startsWith("net/minecraft/resources/Identifier$")
                || name.equals("net/minecraft/client/Options")
                || name.startsWith("net/minecraft/client/Options$");
    }

    private static ClassNode node(byte[] bytes) {
        ClassNode result = new ClassNode();
        new ClassReader(bytes).accept(result, 0);
        return result;
    }

    private static void structurallyVerify(byte[] bytes) {
        ClassWriter writer = new ClassWriter(0);
        new ClassReader(bytes).accept(new CheckClassAdapter(writer, true), 0);
    }

    private static MethodNode requireMethod(ClassNode type, String name, String descriptor,
                                            boolean expected) {
        MethodNode found = null;
        for (MethodNode method : type.methods) {
            if (method.name.equals(name) && method.desc.equals(descriptor)) found = method;
        }
        if ((found != null) != expected) {
            throw new IllegalStateException(type.name + " method expectation failed: " + name + descriptor);
        }
        return found;
    }

    private static FieldNode requireField(ClassNode type, String name, String descriptor) {
        for (FieldNode field : type.fields) {
            if (field.name.equals(name) && field.desc.equals(descriptor)) return field;
        }
        throw new IllegalStateException(type.name + " missing field " + name + descriptor);
    }

    private static void requireCall(MethodNode method, String owner, String name, String descriptor) {
        int count = 0;
        for (AbstractInsnNode instruction : method.instructions) {
            if (instruction instanceof MethodInsnNode call && call.getOpcode() == Opcodes.INVOKESTATIC
                    && call.owner.equals(owner) && call.name.equals(name) && call.desc.equals(descriptor)) count++;
        }
        if (count != 1) throw new IllegalStateException(method.name + method.desc + " expected one call to "
                + owner + "." + name + descriptor + ", found " + count);
    }

    private static void requireVirtualCall(MethodNode method, String owner, String name,
                                           String descriptor) {
        int count = 0;
        for (AbstractInsnNode instruction : method.instructions) {
            if (instruction instanceof MethodInsnNode call
                    && call.getOpcode() == Opcodes.INVOKEVIRTUAL
                    && call.owner.equals(owner) && call.name.equals(name)
                    && call.desc.equals(descriptor)) count++;
        }
        if (count != 1) throw new IllegalStateException(method.name + method.desc
                + " expected one virtual call to " + owner + "." + name + descriptor
                + ", found " + count);
    }

    private static void requireFieldRead(MethodNode method, String owner, String name,
                                         String descriptor) {
        requireFieldInstruction(method, owner, name, descriptor, Opcodes.GETFIELD);
    }

    private static void requireFieldWrite(MethodNode method, String owner, String name,
                                          String descriptor) {
        requireFieldInstruction(method, owner, name, descriptor, Opcodes.PUTFIELD);
    }

    private static void requireFieldInstruction(MethodNode method, String owner, String name,
                                                String descriptor, int opcode) {
        int count = 0;
        for (AbstractInsnNode instruction : method.instructions) {
            if (instruction instanceof FieldInsnNode field && field.getOpcode() == opcode
                    && field.owner.equals(owner) && field.name.equals(name)
                    && field.desc.equals(descriptor)) count++;
        }
        if (count != 1) throw new IllegalStateException(method.name + method.desc
                + " expected one field instruction for " + owner + "." + name
                + descriptor + ", found " + count);
    }

    private static byte[] bytes(ZipFile archive, String name) throws Exception {
        ZipEntry entry = archive.getEntry(name);
        if (entry == null) throw new IllegalStateException("Missing " + name);
        try (InputStream input = archive.getInputStream(entry)) {
            return input.readAllBytes();
        }
    }

    private static Set<String> memberInventory(ClassNode node) {
        Set<String> result = new HashSet<String>();
        int mask = Opcodes.ACC_PUBLIC | Opcodes.ACC_PRIVATE | Opcodes.ACC_PROTECTED
                | Opcodes.ACC_STATIC | Opcodes.ACC_FINAL | Opcodes.ACC_ABSTRACT
                | Opcodes.ACC_SYNCHRONIZED | Opcodes.ACC_NATIVE | Opcodes.ACC_STRICT;
        for (FieldNode field : node.fields) {
            result.add("F " + field.name + field.desc + " " + (field.access & mask));
        }
        for (MethodNode method : node.methods) {
            result.add("M " + method.name + method.desc + " " + (method.access & mask));
        }
        return result;
    }

    private static int requireAllLevelRendererCallSitesResolved(ZipFile archive,
                                                                 String prefix,
                                                                 ClassNode renderer)
            throws Exception {
        Set<String> methods = new HashSet<String>();
        Set<String> fields = new HashSet<String>();
        for (MethodNode method : renderer.methods) methods.add(method.name + method.desc);
        for (FieldNode field : renderer.fields) fields.add(field.name + field.desc);
        List<String> unresolved = new ArrayList<String>();
        int resolved = 0;
        var entries = archive.entries();
        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            if (!entry.getName().startsWith(prefix) || !entry.getName().endsWith(".class")) continue;
            ClassNode caller = node(bytes(archive, entry.getName()));
            if (caller.name.equals(renderer.name)) continue;
            for (MethodNode callerMethod : caller.methods) {
                for (AbstractInsnNode instruction : callerMethod.instructions) {
                    if (instruction instanceof MethodInsnNode call
                            && call.owner.equals(renderer.name)) {
                        if (!methods.contains(call.name + call.desc)) {
                            unresolved.add(caller.name + "." + callerMethod.name + callerMethod.desc
                                    + " -> " + call.name + call.desc);
                        } else resolved++;
                    } else if (instruction instanceof FieldInsnNode field
                            && field.owner.equals(renderer.name)) {
                        if (!fields.contains(field.name + field.desc)) {
                            unresolved.add(caller.name + "." + callerMethod.name + callerMethod.desc
                                    + " -> " + field.name + field.desc);
                        } else resolved++;
                    }
                }
            }
        }
        if (!unresolved.isEmpty()) {
            throw new IllegalStateException("Unresolved LevelRenderer call sites: " + unresolved);
        }
        return resolved;
    }

    private static void exactHash(Path path, String expected) throws Exception {
        String actual = hex(MessageDigest.getInstance("SHA-256").digest(Files.readAllBytes(path)));
        if (!actual.equals(expected)) throw new IllegalStateException(path + " expected " + expected + " got " + actual);
    }

    private static String hex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte value : bytes) result.append(String.format("%02x", value & 0xff));
        return result.toString();
    }
}
