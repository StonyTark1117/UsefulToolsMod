import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.TreeMap;
import java.util.zip.ZipFile;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.analysis.Analyzer;
import org.objectweb.asm.tree.analysis.BasicVerifier;
import com.stonytark.usefultoolsmod.spectralqa.OptifineGuiBridge;

/** Inspect actual transformed methods, excluding annotation-only references. */
public final class NativeBridgeHookCheck {
    private NativeBridgeHookCheck() { }

    private static Map<String, Integer> calls(byte[] bytes) {
        ClassNode type = new ClassNode();
        new ClassReader(bytes).accept(type, 0);
        Map<String, Integer> result = new TreeMap<>();
        for (MethodNode method : type.methods) {
            for (AbstractInsnNode instruction : method.instructions) {
                if (instruction instanceof MethodInsnNode) {
                    MethodInsnNode call = (MethodInsnNode) instruction;
                    if (call.owner.startsWith("net/minecraftforge/")) {
                        String key = method.name + method.desc + " -> "
                                + call.owner + "." + call.name + call.desc;
                        result.merge(key, 1, Integer::sum);
                    }
                }
            }
        }
        return result;
    }

    private static String adapters(byte[] bytes, String signature, String calledMethod) {
        ClassNode type = new ClassNode();
        new ClassReader(bytes).accept(type, 0);
        java.util.Set<String> result = new java.util.TreeSet<>();
        for (MethodNode method : type.methods) {
            if (!(method.name + method.desc).equals(signature)) continue;
            for (AbstractInsnNode instruction : method.instructions) {
                if (instruction instanceof org.objectweb.asm.tree.FieldInsnNode) {
                    var field = (org.objectweb.asm.tree.FieldInsnNode) instruction;
                    if (field.owner.equals("net/optifine/reflect/Reflector")
                            && field.name.endsWith("_" + calledMethod)) result.add(field.name);
                }
            }
        }
        return String.join(",", result);
    }

    /** Compare actual native bodies so an added compatibility overload cannot
     * silently replace native upload, texture processing or Forge callbacks. */
    private static byte[] methodBytes(String owner, MethodNode method) {
        ClassNode isolated = new ClassNode();
        isolated.version = org.objectweb.asm.Opcodes.V17;
        isolated.access = org.objectweb.asm.Opcodes.ACC_PUBLIC;
        isolated.name = owner;
        isolated.superName = "java/lang/Object";
        isolated.methods.add(method);
        org.objectweb.asm.ClassWriter writer = new org.objectweb.asm.ClassWriter(0);
        isolated.accept(writer);
        return writer.toByteArray();
    }

    private static void preservedNativeMethods(byte[] original, ClassNode output) {
        ClassNode nativeType = new ClassNode();
        new ClassReader(original).accept(nativeType, 0);
        int compared = 0;
        int intentionallyBound = 0;
        for (MethodNode before : nativeType.methods) {
            boolean spriteBinding = nativeType.name.equals("net/minecraft/world/level/block/state/BlockState")
                    && before.name.equals("<init>") && before.desc.equals("(Lnet/minecraft/world/level/block/Block;[Lnet/minecraft/world/level/block/state/properties/Property;[Ljava/lang/Comparable;)V");
            if (nativeType.name.endsWith("/SpriteLoader")) {
                spriteBinding = before.name.equals("create") && before.desc.equals(
                        "(Lnet/minecraft/client/renderer/texture/TextureAtlas;)Lnet/minecraft/client/renderer/texture/SpriteLoader;");
                for (AbstractInsnNode instruction : before.instructions) {
                    if (instruction instanceof MethodInsnNode call
                            && call.owner.equals("net/minecraftforge/client/ForgeHooksClient")
                            && call.name.equals("loadTextureAtlasSprite")) spriteBinding = true;
                }
            }
            MethodNode after = null;
            for (MethodNode candidate : output.methods) {
                if (candidate.name.equals(before.name) && candidate.desc.equals(before.desc)) {
                    if (after != null) throw new IllegalStateException("Duplicated native method: " + before.name);
                    after = candidate;
                }
            }
            if (after == null) throw new IllegalStateException("Deleted native method: " + before.name);
            if (spriteBinding) {
                intentionallyBound++;
                continue;
            }
            if (!java.util.Arrays.equals(methodBytes(nativeType.name, before), methodBytes(output.name, after))) {
                throw new IllegalStateException("Altered unrelated native method: " + nativeType.name + "." + before.name + before.desc);
            }
            compared++;
        }
        if (nativeType.name.endsWith("/SpriteLoader") && intentionallyBound != 2) {
            throw new IllegalStateException("Expected exactly two native atlas-binding methods");
        }
        if (nativeType.name.equals("net/minecraft/world/level/block/state/BlockState") && intentionallyBound != 1) {
            throw new IllegalStateException("Expected exactly one native emissive initialization method");
        }
        System.err.println("Preserved native methods: " + nativeType.name + "=" + compared
                + "; intentional compatibility methods=" + intentionallyBound);
    }

    public static void main(String[] arguments) throws Exception {
        ClassFileTransformer[] hook = new ClassFileTransformer[1];
        Instrumentation instrumentation = (Instrumentation) Proxy.newProxyInstance(
                NativeBridgeHookCheck.class.getClassLoader(),
                new Class<?>[] {Instrumentation.class}, (proxy, method, values) -> {
                    if (method.getName().equals("addTransformer")) {
                        hook[0] = (ClassFileTransformer) values[0];
                    }
                    return null;
                });
        OptifineGuiBridge.premain(arguments[1] + "!" + arguments[2], instrumentation);
        try (ZipFile nativeJar = new ZipFile(arguments[0])) {
            var entries = nativeJar.entries();
            while (entries.hasMoreElements()) {
                var entry = entries.nextElement();
                if (!entry.getName().endsWith(".class")) continue;
                String name = entry.getName().substring(0, entry.getName().length() - 6);
                byte[] original;
                try (var input = nativeJar.getInputStream(entry)) {
                    original = input.readAllBytes();
                }
                byte[] transformed = hook[0].transform(null, name, null, null, original);
                if (transformed == null) continue;
                if (name.equals("net/minecraft/client/renderer/texture/SpriteLoader")
                        || name.equals("net/minecraft/client/renderer/texture/TextureAtlas")
                        || name.equals("net/minecraft/client/renderer/texture/MipmapGenerator")
                        || name.equals("net/minecraft/world/level/block/state/BlockState")
                        || name.equals("net/minecraft/client/renderer/block/FluidRenderer")) {
                    ClassNode output = new ClassNode();
                    new ClassReader(transformed).accept(output, 0);
                    preservedNativeMethods(original, output);
                    for (MethodNode method : output.methods) {
                        if (method.instructions.size() > 0) {
                            new Analyzer<>(new BasicVerifier()).analyze(output.name, method);
                        }
                    }
                }
                Map<String, Integer> before = calls(original);
                Map<String, Integer> after = calls(transformed);
                for (var call : before.entrySet()) {
                    int retained = after.getOrDefault(call.getKey(), 0);
                    if (retained < call.getValue()) {
                        String[] parts = call.getKey().split(" -> ", 2);
                        String invoked = parts[1].substring(parts[1].lastIndexOf('.') + 1);
                        String methodName = invoked.substring(0, invoked.indexOf('('));
                        System.out.println(name + "\t" + call.getKey() + "\t"
                                + call.getValue() + "\t" + retained + "\t"
                                + adapters(transformed, parts[0], methodName));
                    }
                }
            }
        }
    }
}
