package com.stonytark.lightingqa;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.ProtectionDomain;
import java.util.HashMap;
import java.util.Map;
import org.objectweb.asm.*;

/** Private QA control: bypass only the two renderer light samples after normal acceptance. */
public final class MaterialControlAgent {
    private static final String TARGET = "com/stonytark/usefultools/lighting/LightField";
    private static final String PROPERTY = "usefulToolsLightingMaterialOnly.active";
    private static volatile int transformed;
    public static void premain(String argument, final Instrumentation instrumentation) {
        final String[] arguments = argument.split("[,;]", -1);
        final Path flag = Paths.get(arguments[0]);
        final boolean optifine = (arguments.length > 1 && "optifine".equals(arguments[1]))
                || flag.getFileName().toString().contains("-optifine.");
        System.setProperty(PROPERTY, "false");
        instrumentation.addTransformer(new ClassFileTransformer() {
            public byte[] transform(final ClassLoader loader, String name, Class<?> redefining,
                                    ProtectionDomain domain, byte[] bytes) {
                if (!TARGET.equals(name)) return null;
                final int[] count = {0};
                ClassReader reader = new ClassReader(bytes);
                ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS) {
                    protected String getCommonSuperClass(String first, String second) {
                        try {
                            Class<?> a = Class.forName(first.replace('/', '.'), false, loader);
                            Class<?> b = Class.forName(second.replace('/', '.'), false, loader);
                            if (a.isAssignableFrom(b)) return first;
                            if (b.isAssignableFrom(a)) return second;
                            if (a.isInterface() || b.isInterface()) return "java/lang/Object";
                            do { a = a.getSuperclass(); } while (!a.isAssignableFrom(b));
                            return a.getName().replace('.', '/');
                        } catch (ClassNotFoundException error) { throw new IllegalStateException(error); }
                    }
                };
                reader.accept(new ClassVisitor(Opcodes.ASM9, writer) {
                    public MethodVisitor visitMethod(int access, String name, String descriptor,
                                                     String signature, String[] exceptions) {
                        MethodVisitor method = super.visitMethod(access, name, descriptor, signature, exceptions);
                        if (!("packed".equals(name) && "(IDDD)I".equals(descriptor)) &&
                            !("sodium".equals(name) && "(IIII)I".equals(descriptor))) return method;
                        count[0]++;
                        return new MethodVisitor(Opcodes.ASM9, method) {
                            public void visitCode() {
                                super.visitCode();
                                super.visitLdcInsn(PROPERTY);
                                super.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Boolean", "getBoolean", "(Ljava/lang/String;)Z", false);
                                Label normal = new Label();
                                super.visitJumpInsn(Opcodes.IFEQ, normal);
                                super.visitVarInsn(Opcodes.ILOAD, 1);
                                super.visitInsn(Opcodes.IRETURN);
                                super.visitLabel(normal);
                            }
                        };
                    }
                }, ClassReader.EXPAND_FRAMES);
                if (count[0] != 2) throw new IllegalStateException("Expected both native renderer sample methods");
                transformed = count[0];
                return writer.toByteArray();
            }
        });
        Thread control = new Thread(new Runnable() {
            public void run() {
                try {
                    Class<?> field = null;
                    while (field == null || !Files.isRegularFile(flag)) {
                        for (Class<?> candidate : instrumentation.getAllLoadedClasses())
                            if (candidate.getName().equals(TARGET.replace('/', '.'))) field = candidate;
                        Thread.sleep(100);
                    }
                    if (transformed != 2) throw new IllegalStateException("Renderer transformation missing");
                    if (optifine) {
                        Class<?> config = null;
                        for (Class<?> candidate : instrumentation.getAllLoadedClasses())
                            if (candidate.getName().equals("Config") || candidate.getName().equals("net.optifine.Config")) config = candidate;
                        if (config == null || !Boolean.FALSE.equals(config.getMethod("isDynamicLights").invoke(null)))
                            throw new IllegalStateException("OptiFine dynamic lighting was not disabled");
                    }
                    Object instance = field.getConstructor().newInstance();
                    Class<?> source = Class.forName(TARGET.replace('/', '.') + "$Source", true, field.getClassLoader());
                    Object light = source.getConstructor(double.class, double.class, double.class, int.class, int.class)
                        .newInstance(0.0, 0.0, 0.0, 15, 30);
                    Map<String, Object> sources = new HashMap<String, Object>(); sources.put("control", light);
                    field.getMethod("update", Map.class).invoke(instance, sources);
                    Method packed = field.getMethod("packed", int.class, double.class, double.class, double.class);
                    Method sodium = field.getMethod("sodium", int.class, int.class, int.class, int.class);
                    int original = 0x12340002;
                    System.setProperty(PROPERTY, "false");
                    if (((Integer)packed.invoke(instance, original, 0.0, 0.0, 0.0)) != ((original & ~255) | 240))
                        throw new IllegalStateException("Normal packed control did not raise block light");
                    if (((Integer)sodium.invoke(instance, original, 0, 0, 0)) != ((original & ~15) | 15))
                        throw new IllegalStateException("Normal Sodium control did not raise block light");
                    System.setProperty(PROPERTY, "true");
                    if (((Integer)packed.invoke(instance, original, 0.0, 0.0, 0.0)) != original ||
                        ((Integer)sodium.invoke(instance, original, 0, 0, 0)) != original)
                        throw new IllegalStateException("Material control changed original light or metadata");
                    if (((Double)field.getMethod("light", double.class, double.class, double.class)
                        .invoke(instance, 0.0, 0.0, 0.0)) != 15.0)
                        throw new IllegalStateException("Source policy changed under material control");
                    System.setProperty(PROPERTY, "false");
                    if (((Integer)packed.invoke(instance, original, 0.0, 0.0, 0.0)) != ((original & ~255) | 240) ||
                        ((Integer)sodium.invoke(instance, original, 0, 0, 0)) != ((original & ~15) | 15))
                        throw new IllegalStateException("Normal renderer branch was not restored");
                    System.setProperty(PROPERTY, "true");
                    Path ack = Paths.get(flag.toString() + ".ack");
                    Path temporary = Paths.get(flag.toString() + ".ack.tmp");
                    Files.write(temporary, ("USEFUL_TOOLS_LIGHTING_MATERIAL_CONTROL_PASS packed=same sodium=same source=15 normal=restored transformed=2 optifine=" + optifine + "\n").getBytes(StandardCharsets.UTF_8));
                    Files.move(temporary, ack, StandardCopyOption.REPLACE_EXISTING);
                } catch (Throwable error) {
                    try {
                        Files.write(Paths.get(flag.toString() + ".fail"), error.toString().getBytes(StandardCharsets.UTF_8));
                    } catch (Exception ignored) { error.printStackTrace(); }
                }
            }
        }, "Useful Tools private material control");
        control.setDaemon(true); control.start();
    }
}
