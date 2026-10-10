package com.stonytark.usefultoolsmod.spectralqa;

import java.nio.file.Path;
import java.util.zip.ZipFile;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.analysis.Analyzer;
import org.objectweb.asm.tree.analysis.BasicVerifier;

/** Verify K2's GUI render-state hook on exact Forge 65 without replacing native rendering. */
public final class Forge262GuiCheck {
    private Forge262GuiCheck() { }

    private static byte[] entry(Path jar, String name) throws Exception {
        try (ZipFile archive = new ZipFile(jar.toFile())) {
            var found = archive.getEntry(name);
            if (found == null) throw new IllegalStateException("Exact class absent: " + name);
            try (var input = archive.getInputStream(found)) { return input.readAllBytes(); }
        }
    }

    private static ClassNode read(byte[] bytes) {
        ClassNode node = new ClassNode();new ClassReader(bytes).accept(node, 0);return node;
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 2) throw new IllegalArgumentException("forge-65-client patched-publisher");
        var selected = OptifineGuiBridge.class.getDeclaredMethod("selected", String.class);
        selected.setAccessible(true);
        if (!Boolean.TRUE.equals(selected.invoke(null, "com/mojang/blaze3d/opengl/GlDevice")))
            throw new IllegalStateException("Publisher GlDevice donor is not selected for the bridge");
        if (!Boolean.TRUE.equals(selected.invoke(null, "net/minecraft/client/gui/render/GuiRenderer")))
            throw new IllegalStateException("GUI renderer absent from premain patch selection");
        if (!Boolean.TRUE.equals(selected.invoke(null, "com/mojang/blaze3d/opengl/GlCommandEncoder")))
            throw new IllegalStateException("Publisher encoder absent from premain patch selection");
        var accessor = OptifineGuiBridge.class.getDeclaredMethod("accessor", byte[].class,
                String.class, String.class, String.class);
        accessor.setAccessible(true);
        ClassNode glDevice = read((byte[]) accessor.invoke(null,
                entry(Path.of(args[0]), "com/mojang/blaze3d/opengl/GlDevice.class"),
                "encoder", "getGlCommandEncoder", "Lcom/mojang/blaze3d/opengl/GlCommandEncoder;"));
        MethodNode encoderGetter = glDevice.methods.stream().filter(m -> m.name.equals("getGlCommandEncoder")
                && m.desc.equals("()Lcom/mojang/blaze3d/opengl/GlCommandEncoder;"))
                .findFirst().orElseThrow();
        if ((encoderGetter.access & Opcodes.ACC_PUBLIC) == 0)
            throw new IllegalStateException("Pinned OpenGL encoder accessor is not public");
        var deviceAdapter = OptifineGuiBridge.class.getDeclaredMethod("nativeGlDevice",
                byte[].class, byte[].class);
        deviceAdapter.setAccessible(true);
        ClassNode adaptedDevice = read((byte[]) deviceAdapter.invoke(null,
                entry(Path.of(args[0]), "com/mojang/blaze3d/opengl/GlDevice.class"),
                entry(Path.of(args[1]), "srg/com/mojang/blaze3d/opengl/GlDevice.class")));
        for (String name : new String[] {"directStateAccessEmulated", "vertexArrayCacheEmulated"})
            if (adaptedDevice.fields.stream().noneMatch(field -> field.name.equals(name)))
                throw new IllegalStateException("Shader-mode OpenGL device field absent: " + name);
        for (MethodNode method : adaptedDevice.methods)
            if (method.name.equals("<init>") || method.name.equals("directStateAccess")
                    || method.name.equals("vertexArrayCache"))
                new Analyzer<>(new BasicVerifier()).analyze(adaptedDevice.name, method);
        String encoderName = "com/mojang/blaze3d/opengl/GlCommandEncoder.class";
        ClassNode nativeEncoder = read(entry(Path.of(args[0]), encoderName));
        ClassNode publisherEncoder = read(entry(Path.of(args[1]), "srg/" + encoderName));
        if ((nativeEncoder.access & Opcodes.ACC_PUBLIC) != 0
                || (publisherEncoder.access & Opcodes.ACC_PUBLIC) == 0)
            throw new IllegalStateException("Exact native/publisher encoder visibility changed");
        for (MethodNode method : publisherEncoder.methods)
            if (method.instructions.size() > 0)
                new Analyzer<>(new BasicVerifier()).analyze(publisherEncoder.name, method);
        String textureName = "com/mojang/blaze3d/opengl/GlTexture.class";
        var textureAdapter = OptifineGuiBridge.class.getDeclaredMethod("nativeGlTexture",
                byte[].class, byte[].class);
        textureAdapter.setAccessible(true);
        ClassNode adaptedTexture = read((byte[]) textureAdapter.invoke(null,
                entry(Path.of(args[0]), textureName), entry(Path.of(args[1]), "srg/" + textureName)));
        boolean samplerInitialized = false;
        for (MethodNode method : adaptedTexture.methods) {
            if (method.name.equals("<init>"))
                for (AbstractInsnNode instruction : method.instructions)
                    if (instruction instanceof FieldInsnNode field && field.getOpcode() == Opcodes.PUTFIELD
                            && field.owner.equals(adaptedTexture.name) && field.name.equals("sampler"))
                        samplerInitialized = true;
            if (method.instructions.size() > 0)
                new Analyzer<>(new BasicVerifier()).analyze(adaptedTexture.name, method);
        }
        if (!samplerInitialized) throw new IllegalStateException("Publisher texture sampler is uninitialized");
        var forcedProgramBinder = OptifineGuiBridge.class.getDeclaredMethod("forcePublisherProgramBind", byte[].class);
        forcedProgramBinder.setAccessible(true);
        String stateManagerName = "com/mojang/blaze3d/opengl/GlStateManager.class";
        ClassNode forcedStateManager = read((byte[]) forcedProgramBinder.invoke(null,
                entry(Path.of(args[1]), "srg/" + stateManagerName)));
        MethodNode forcedBind = forcedStateManager.methods.stream()
                .filter(m -> m.name.equals("_glUseProgram") && m.desc.equals("(I)V"))
                .findFirst().orElseThrow();
        int directBinds = 0;
        for (AbstractInsnNode instruction : forcedBind.instructions)
            if (instruction instanceof org.objectweb.asm.tree.MethodInsnNode call
                    && call.owner.equals("org/lwjgl/opengl/GL33C") && call.name.equals("glUseProgram"))
                directBinds++;
        if (directBinds != 2) throw new IllegalStateException("Forced program bind was not inserted exactly once");
        for (MethodNode method : forcedStateManager.methods)
            if (method.instructions.size() > 0)
                new Analyzer<>(new BasicVerifier()).analyze(forcedStateManager.name, method);
        String shadersName = "net/optifine/shaders/Shaders.class";
        var packAdapter = OptifineGuiBridge.class.getDeclaredMethod("enablePublisherPack262", byte[].class);
        packAdapter.setAccessible(true);
        var compositeTrace = OptifineGuiBridge.class.getDeclaredMethod("tracePublisherComposite", byte[].class);
        compositeTrace.setAccessible(true);
        byte[] enabledShaders = (byte[]) packAdapter.invoke(null, entry(Path.of(args[1]), "srg/" + shadersName));
        ClassNode tracedShaders = read((byte[]) compositeTrace.invoke(null, enabledShaders));
        for (MethodNode method : tracedShaders.methods)
            if (method.name.equals("renderFinal") || method.name.equals("renderComposites")
                    || method.name.equals("usefulToolsTraceComposite"))
                new Analyzer<>(new BasicVerifier()).analyze(tracedShaders.name, method);
        var encoderAdapter = OptifineGuiBridge.class.getDeclaredMethod("publicGlCommandEncoder",
                byte[].class, byte[].class);
        encoderAdapter.setAccessible(true);
        ClassNode adaptedEncoder = read((byte[]) encoderAdapter.invoke(null,
                entry(Path.of(args[0]), encoderName), entry(Path.of(args[1]), "srg/" + encoderName)));
        if ((adaptedEncoder.access & Opcodes.ACC_PUBLIC) == 0)
            throw new IllegalStateException("Pinned OpenGL encoder was not exposed");
        if (adaptedEncoder.methods.size() != nativeEncoder.methods.size() + 2)
            throw new IllegalStateException("Unexpected native encoder method changes");
        MethodNode vanillaProgram = adaptedEncoder.methods.stream().filter(m -> m.name.equals("useVanillaProgram")
                && m.desc.equals("()V")).findFirst().orElseThrow();
        boolean bindsNativeProgram = false;
        for (AbstractInsnNode instruction : vanillaProgram.instructions)
            if (instruction instanceof org.objectweb.asm.tree.MethodInsnNode call
                    && call.owner.equals("com/mojang/blaze3d/opengl/GlStateManager")
                    && call.name.equals("_glUseProgram") && call.desc.equals("(I)V"))
                bindsNativeProgram = true;
        if (!bindsNativeProgram) throw new IllegalStateException("Publisher vanilla-program binding absent");
        MethodNode bindFramebuffer = adaptedEncoder.methods.stream().filter(m -> m.name.equals("bindFramebuffer")
                && m.desc.equals("(Lcom/mojang/blaze3d/pipeline/RenderTarget;)V"))
                .findFirst().orElseThrow();
        boolean getsNativeFbo = false;
        boolean bindsNativeFbo = false;
        for (AbstractInsnNode instruction : bindFramebuffer.instructions)
            if (instruction instanceof org.objectweb.asm.tree.MethodInsnNode call) {
                if (call.owner.equals("com/mojang/blaze3d/opengl/FrameBufferCache")
                        && call.name.equals("getFbo")) getsNativeFbo = true;
                if (call.owner.equals("com/mojang/blaze3d/opengl/GlStateManager")
                        && call.name.equals("_glBindFramebuffer") && call.desc.equals("(II)V"))
                    bindsNativeFbo = true;
            }
        if (!getsNativeFbo || !bindsNativeFbo)
            throw new IllegalStateException("Publisher framebuffer binding changed");
        for (MethodNode method : adaptedEncoder.methods)
            if (method.instructions.size() > 0)
                new Analyzer<>(new BasicVerifier()).analyze(adaptedEncoder.name, method);
        var copyMethods = OptifineGuiBridge.class.getDeclaredMethod("addPublisherMethods",
                byte[].class, byte[].class, String[].class);
        copyMethods.setAccessible(true);
        String[][] publisherMethodOwners = {
                { "com/mojang/blaze3d/pipeline/RenderTarget", "hasFramebuffer(I)Z" },
                { "com/mojang/blaze3d/opengl/GlTextureView", "hasFramebuffer(I)Z" },
                { "com/mojang/blaze3d/opengl/FrameBufferCache",
                        "getFramebuffer(Lcom/mojang/blaze3d/opengl/FrameBufferCache$CacheKey;)I",
                        "hasFramebuffer(Ljava/util/List;I)Z" },
                { "net/minecraft/client/gui/components/BossHealthOverlay",
                        "getBossName()Ljava/lang/String;" }
        };
        for (String[] owner : publisherMethodOwners) {
            if (!Boolean.TRUE.equals(selected.invoke(null, owner[0])))
                throw new IllegalStateException("Publisher method owner absent from premain: " + owner[0]);
            String className = owner[0] + ".class";
            byte[] nativeBytes = entry(Path.of(args[0]), className);
            byte[] publisherBytes = entry(Path.of(args[1]), "srg/" + className);
            String[] signatures = java.util.Arrays.copyOfRange(owner, 1, owner.length);
            ClassNode original = read(nativeBytes);
            ClassNode adapted = read((byte[]) copyMethods.invoke(null,
                    nativeBytes, publisherBytes, signatures));
            if (adapted.methods.size() != original.methods.size() + signatures.length)
                throw new IllegalStateException("Unexpected publisher method changes: " + owner[0]);
            for (String signature : signatures)
                if (adapted.methods.stream().noneMatch(m -> (m.name + m.desc).equals(signature)))
                    throw new IllegalStateException("Publisher method absent: " + signature);
            for (MethodNode method : adapted.methods)
                if (method.instructions.size() > 0)
                    new Analyzer<>(new BasicVerifier()).analyze(adapted.name, method);
        }
        String stateClass = "net/minecraft/world/level/block/state/BlockState.class";
        var stateAdapter = OptifineGuiBridge.class.getDeclaredMethod("nativeEmissiveState",
                byte[].class, byte[].class);
        stateAdapter.setAccessible(true);
        ClassNode nativeState = read(entry(Path.of(args[0]), stateClass));
        ClassNode adaptedState = read((byte[]) stateAdapter.invoke(null,
                entry(Path.of(args[0]), stateClass), entry(Path.of(args[1]), "srg/" + stateClass)));
        if (adaptedState.fields.size() != nativeState.fields.size() + 4
                || adaptedState.methods.size() != nativeState.methods.size() + 4)
            throw new IllegalStateException("Unexpected native block-state member changes: fields "
                    + nativeState.fields.size() + " -> " + adaptedState.fields.size()
                    + ", methods " + nativeState.methods.size() + " -> " + adaptedState.methods.size());
        for (String fieldName : new String[] { "blockId", "metadata" }) {
            boolean initializesMinusOne = false;
            MethodNode constructor = adaptedState.methods.stream().filter(m -> m.name.equals("<init>"))
                    .findFirst().orElseThrow();
            for (AbstractInsnNode instruction : constructor.instructions)
                if (instruction instanceof FieldInsnNode field && field.getOpcode() == Opcodes.PUTFIELD
                        && field.owner.equals(adaptedState.name) && field.name.equals(fieldName)
                        && instruction.getPrevious() != null
                        && instruction.getPrevious().getOpcode() == Opcodes.ICONST_M1)
                    initializesMinusOne = true;
            if (!initializesMinusOne) throw new IllegalStateException("Block-state ID cache not initialized: " + fieldName);
        }
        for (String signature : new String[] { "getBlockId()I", "getMetadata()I",
                "getBlockLocation()Lnet/minecraft/resources/Identifier;" })
            if (adaptedState.methods.stream().noneMatch(m -> (m.name + m.desc).equals(signature)))
                throw new IllegalStateException("Publisher block-state getter absent: " + signature);
        for (MethodNode method : adaptedState.methods)
            if (method.instructions.size() > 0)
                new Analyzer<>(new BasicVerifier()).analyze(adaptedState.name, method);
        String entityRendererClass = "net/minecraft/client/renderer/entity/EntityRenderer.class";
        String entityStateClass = "net/minecraft/client/renderer/entity/state/EntityRenderState.class";
        ClassNode nativeEntityState = read(entry(Path.of(args[0]), entityStateClass));
        ClassNode publisherEntityState = read(entry(Path.of(args[1]), "srg/" + entityStateClass));
        if (nativeEntityState.fields.stream().anyMatch(f -> f.name.equals("entity"))
                || publisherEntityState.fields.stream().noneMatch(f -> f.name.equals("entity")
                    && f.desc.equals("Lnet/minecraft/world/entity/Entity;")))
            throw new IllegalStateException("Exact native/publisher entity-state field changed");
        if (!Boolean.TRUE.equals(selected.invoke(null, "net/minecraft/client/renderer/entity/EntityRenderer")))
            throw new IllegalStateException("Publisher entity renderer absent from premain patch selection");
        var entityAdapter = OptifineGuiBridge.class.getDeclaredMethod("nativeEntityRenderState",
                byte[].class, byte[].class);
        entityAdapter.setAccessible(true);
        ClassNode adaptedEntityRenderer = read((byte[]) entityAdapter.invoke(null,
                entry(Path.of(args[0]), entityRendererClass),
                entry(Path.of(args[1]), "srg/" + entityRendererClass)));
        MethodNode entityExtract = adaptedEntityRenderer.methods.stream()
                .filter(m -> m.name.equals("extractRenderState")
                    && m.desc.equals("(Lnet/minecraft/world/entity/Entity;"
                        + "Lnet/minecraft/client/renderer/entity/state/EntityRenderState;F)V"))
                .findFirst().orElseThrow();
        int entityWrites = 0;
        for (AbstractInsnNode instruction : entityExtract.instructions)
            if (instruction instanceof FieldInsnNode field && field.getOpcode() == Opcodes.PUTFIELD
                    && field.owner.equals("net/minecraft/client/renderer/entity/state/EntityRenderState")
                    && field.name.equals("entity")) entityWrites++;
        if (entityWrites != 1) throw new IllegalStateException("Expected one native entity-state assignment");
        for (MethodNode method : adaptedEntityRenderer.methods)
            if (method.instructions.size() > 0)
                new Analyzer<>(new BasicVerifier()).analyze(adaptedEntityRenderer.name, method);
        String capeClass = "net/minecraft/client/player/AbstractClientPlayer.class";
        if (!Boolean.TRUE.equals(selected.invoke(null, "net/minecraft/client/player/AbstractClientPlayer")))
            throw new IllegalStateException("Cape player absent from premain patch selection");
        var capeAdapter = OptifineGuiBridge.class.getDeclaredMethod("nativeCapePlayer",
                byte[].class, byte[].class);
        capeAdapter.setAccessible(true);
        ClassNode nativeCape = read(entry(Path.of(args[0]), capeClass));
        ClassNode adaptedCape = read((byte[]) capeAdapter.invoke(null,
                entry(Path.of(args[0]), capeClass), entry(Path.of(args[1]), "srg/" + capeClass)));
        if (adaptedCape.fields.size() != nativeCape.fields.size() + 4
                || adaptedCape.methods.size() != nativeCape.methods.size() + 11)
            throw new IllegalStateException("Unexpected native cape player members");
        MethodNode capeConstructor = adaptedCape.methods.stream().filter(m -> m.name.equals("<init>"))
                .findFirst().orElseThrow();
        boolean startsCapeDownload = false;
        for (AbstractInsnNode instruction : capeConstructor.instructions)
            if (instruction instanceof org.objectweb.asm.tree.MethodInsnNode call
                    && call.owner.equals("net/optifine/player/CapeUtils")
                    && call.name.equals("downloadCape")) startsCapeDownload = true;
        if (!startsCapeDownload) throw new IllegalStateException("Publisher cape download initialization absent");
        for (MethodNode method : adaptedCape.methods)
            if (method.instructions.size() > 0)
                new Analyzer<>(new BasicVerifier()).analyze(adaptedCape.name, method);
        String cameraClass = "net/minecraft/client/Camera.class";
        ClassNode nativeCamera = read(entry(Path.of(args[0]), cameraClass));
        ClassNode publisherCamera = read(entry(Path.of(args[1]), "srg/" + cameraClass));
        String cullingMethod = "createProjectionMatrixForCulling";
        String cullingDescriptor = "()Lorg/joml/Matrix4f;";
        MethodNode nativeCulling = nativeCamera.methods.stream().filter(m -> m.name.equals(cullingMethod)
                && m.desc.equals(cullingDescriptor)).findFirst().orElseThrow();
        MethodNode publisherCulling = publisherCamera.methods.stream().filter(m -> m.name.equals(cullingMethod)
                && m.desc.equals(cullingDescriptor)).findFirst().orElseThrow();
        if ((nativeCulling.access & Opcodes.ACC_PRIVATE) == 0
                || (publisherCulling.access & Opcodes.ACC_PUBLIC) == 0)
            throw new IllegalStateException("Exact native/publisher camera method visibility changed");
        var cameraAdapter = OptifineGuiBridge.class.getDeclaredMethod("exposeNativeMethod",
                byte[].class, String.class, String.class);
        cameraAdapter.setAccessible(true);
        if (!Boolean.TRUE.equals(selected.invoke(null, "net/minecraft/client/Camera")))
            throw new IllegalStateException("Publisher camera absent from premain patch selection");
        byte[] exposedCamera = (byte[]) cameraAdapter.invoke(null,
                entry(Path.of(args[0]), cameraClass), cullingMethod, cullingDescriptor);
        ClassNode adaptedCamera = read((byte[]) copyMethods.invoke(null, exposedCamera,
                entry(Path.of(args[1]), "srg/" + cameraClass),
                new String[] { "isDebugFrustum()Z" }));
        if (adaptedCamera.methods.size() != nativeCamera.methods.size() + 1
                || adaptedCamera.methods.stream().noneMatch(m -> m.name.equals(cullingMethod)
                    && m.desc.equals(cullingDescriptor) && (m.access & Opcodes.ACC_PUBLIC) != 0))
            throw new IllegalStateException("Pinned camera method was not exposed");
        MethodNode debugFrustum = adaptedCamera.methods.stream().filter(m -> m.name.equals("isDebugFrustum")
                && m.desc.equals("()Z")).findFirst().orElseThrow();
        boolean readsCapturedFrustum = false;
        for (AbstractInsnNode instruction : debugFrustum.instructions)
            if (instruction instanceof FieldInsnNode field
                    && field.owner.equals("net/minecraft/client/Camera")
                    && field.name.equals("capturedFrustum")) readsCapturedFrustum = true;
        if (!readsCapturedFrustum) throw new IllegalStateException("Publisher debug-frustum getter changed");
        for (MethodNode method : adaptedCamera.methods)
            if (method.instructions.size() > 0)
                new Analyzer<>(new BasicVerifier()).analyze(adaptedCamera.name, method);
        for (MethodNode method : glDevice.methods)
            if (method.instructions.size() > 0)
                new Analyzer<>(new BasicVerifier()).analyze(glDevice.name, method);
        if (!Boolean.TRUE.equals(selected.invoke(null, "net/minecraft/client/renderer/GameRenderer")))
            throw new IllegalStateException("Publisher game renderer absent from premain patch selection");
        var gameAdapter = OptifineGuiBridge.class.getDeclaredMethod("nativeGameRenderer",
                byte[].class, byte[].class);
        gameAdapter.setAccessible(true);
        System.setProperty("usefulToolsSpectralFramePass262", "true");
        ClassNode game = read((byte[]) gameAdapter.invoke(null,
                entry(Path.of(args[0]), "net/minecraft/client/renderer/GameRenderer.class"),
                entry(Path.of(args[1]), "srg/net/minecraft/client/renderer/GameRenderer.class")));
        MethodNode renderDistance = game.methods.stream().filter(m -> m.name.equals("getRenderDistance")
                && m.desc.equals("()I")).findFirst().orElseThrow();
        boolean usesEffectiveDistance = false;
        for (AbstractInsnNode instruction : renderDistance.instructions)
            if (instruction instanceof org.objectweb.asm.tree.MethodInsnNode call
                    && call.owner.equals("net/minecraft/client/Options")
                    && call.name.equals("getEffectiveRenderDistance") && call.desc.equals("()I"))
                usesEffectiveDistance = true;
        if (!usesEffectiveDistance)
            throw new IllegalStateException("Publisher render-distance getter changed");
        var projection = game.fields.stream().filter(f -> f.name.equals("hudProjection")
                && f.desc.equals("Lnet/minecraft/client/renderer/Projection;"))
                .findFirst().orElseThrow();
        if ((projection.access & Opcodes.ACC_PUBLIC) == 0)
            throw new IllegalStateException("OptiFine projection access bridge missing");
        MethodNode renderLevel = game.methods.stream().filter(m -> m.name.equals("renderLevel")
                && m.desc.equals("(Lnet/minecraft/client/DeltaTracker;)V")).findFirst().orElseThrow();
        for (String hook : new String[] { "frameInit", "beginRender", "beginRenderPass",
                "renderCompositeFinal", "endRender" }) {
            int calls = 0;
            for (AbstractInsnNode instruction : renderLevel.instructions)
                if (instruction instanceof org.objectweb.asm.tree.MethodInsnNode call
                        && call.owner.equals("net/optifine/shaders/Shaders") && call.name.equals(hook)) calls++;
            if (calls != 1) throw new IllegalStateException("Expected one native shader lifecycle hook: " + hook);
        }
        int frameInit = -1, beginRender = -1, beginPass = -1, levelRender = -1;
        int composite = -1, screenEffects = -1, endRender = -1;
        int backupProjection = -1, setProjection = -1, restoreProjection = -1;
        int offset = 0;
        for (AbstractInsnNode instruction : renderLevel.instructions) {
            if (instruction instanceof org.objectweb.asm.tree.MethodInsnNode call) {
                if (call.owner.equals("net/optifine/shaders/Shaders")) {
                    if (call.name.equals("frameInit")) frameInit = offset;
                    if (call.name.equals("beginRender")) beginRender = offset;
                    if (call.name.equals("beginRenderPass")) beginPass = offset;
                    if (call.name.equals("renderCompositeFinal")) composite = offset;
                    if (call.name.equals("endRender")) endRender = offset;
                }
                if (call.owner.equals("net/minecraft/client/renderer/LevelRenderer")
                        && call.name.equals("render")) levelRender = offset;
                if (call.owner.equals("com/mojang/blaze3d/systems/RenderSystem")) {
                    if (call.name.equals("backupProjectionMatrix")) backupProjection = offset;
                    if (call.name.equals("setProjectionMatrix")) setProjection = offset;
                    if (call.name.equals("restoreProjectionMatrix")) restoreProjection = offset;
                }
            }
            if (instruction instanceof org.objectweb.asm.tree.LdcInsnNode label
                    && "screenEffects".equals(label.cst)) screenEffects = offset;
            offset++;
        }
        if (!(frameInit >= 0 && frameInit < beginRender && beginRender < beginPass
                && beginPass < levelRender && levelRender < backupProjection
                && backupProjection < setProjection && setProjection < composite
                && composite < restoreProjection && restoreProjection < screenEffects
                && screenEffects < endRender))
            throw new IllegalStateException("Shader lifecycle hooks do not bracket native world rendering");
        for (MethodNode method : game.methods)
            if (method.instructions.size() > 0)
                new Analyzer<>(new BasicVerifier()).analyze(game.name, method);
        String name = "net/minecraft/client/gui/render/GuiRenderer.class";
        byte[] nativeBytes = entry(Path.of(args[0]), name);
        byte[] publisherBytes = entry(Path.of(args[1]), "srg/" + name);
        var adapter = OptifineGuiBridge.class.getDeclaredMethod("nativeGuiRenderer", byte[].class, byte[].class);
        adapter.setAccessible(true);
        ClassNode before = read(nativeBytes);
        ClassNode after = read((byte[]) adapter.invoke(null, nativeBytes, publisherBytes));
        if (after.fields.size() != before.fields.size() + 1
                || after.methods.size() != before.methods.size() + 2)
            throw new IllegalStateException("Unexpected native GUI member changes");
        MethodNode renderer = after.methods.stream().filter(m -> m.name.equals("render") && m.desc.equals("()V"))
                .findFirst().orElseThrow();
        int writes = 0;
        for (AbstractInsnNode instruction : renderer.instructions)
            if (instruction instanceof FieldInsnNode field && field.getOpcode() == Opcodes.PUTSTATIC
                    && field.owner.equals(after.name) && field.name.equals("renderGui")) writes++;
        if (writes != 2) throw new IllegalStateException("GUI render-state enter/exit hooks missing");
        MethodNode getter = after.methods.stream().filter(m -> m.name.equals("isRenderGui")
                && m.desc.equals("()Z")).findFirst().orElseThrow();
        boolean reads = false;
        for (AbstractInsnNode instruction : getter.instructions)
            if (instruction instanceof FieldInsnNode field && field.getOpcode() == Opcodes.GETSTATIC
                    && field.owner.equals(after.name) && field.name.equals("renderGui")) reads = true;
        if (!reads) throw new IllegalStateException("Publisher GUI render-state getter does not read state");
        for (MethodNode method : after.methods)
            if (method.instructions.size() > 0)
                new Analyzer<>(new BasicVerifier()).analyze(after.name, method);
        System.out.println("Verified K2 GUI enter/exit and state accessor on pinned Forge 65; all method stacks valid.");
    }
}
