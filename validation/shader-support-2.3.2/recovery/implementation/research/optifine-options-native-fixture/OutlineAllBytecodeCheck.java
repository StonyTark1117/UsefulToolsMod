import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.Proxy;
import java.nio.file.Files;
import java.nio.file.Path;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.analysis.Analyzer;
import org.objectweb.asm.tree.analysis.BasicVerifier;
import com.stonytark.usefultoolsmod.spectralqa.OptifineOutlineBridge;

/** Feed each exact publisher class through the actual candidate transformer. */
public final class OutlineAllBytecodeCheck {
    private OutlineAllBytecodeCheck() { }
    public static void main(String[] arguments) throws Exception {
        ClassFileTransformer[] hook = new ClassFileTransformer[1];
        Instrumentation instrumentation = (Instrumentation) Proxy.newProxyInstance(
                OutlineAllBytecodeCheck.class.getClassLoader(), new Class<?>[] {Instrumentation.class},
                (proxy, method, values) -> {
                    if (method.getName().equals("addTransformer")) hook[0] = (ClassFileTransformer) values[0];
                    return null;
                });
        Path root = Path.of(arguments[0]);
        OptifineOutlineBridge.premain(root.resolve("all-transform-events.log").toString(), instrumentation);
        for (String name : new String[] {"ShaderInstance", "LevelRenderer", "RenderStateShard", "Shaders"}) {
            byte[] beforeBytes = Files.readAllBytes(root.resolve(name + ".class"));
            ClassNode before = new ClassNode();
            new ClassReader(beforeBytes).accept(before, 0);
            byte[] afterBytes = hook[0].transform(null, before.name, null, null, beforeBytes);
            if (afterBytes == null) throw new IllegalStateException("No actual transformation: " + name);
            ClassNode after = new ClassNode();
            new ClassReader(afterBytes).accept(after, 0);
            if (before.methods.size() != after.methods.size() || before.fields.size() != after.fields.size()) {
                throw new IllegalStateException("Changed native member count: " + name);
            }
            for (int index = 0; index < before.methods.size(); index++) {
                MethodNode original = before.methods.get(index);
                MethodNode transformed = after.methods.get(index);
                if (!original.name.equals(transformed.name) || !original.desc.equals(transformed.desc)) {
                    throw new IllegalStateException("Changed native method signature: " + name);
                }
                if (transformed.instructions.size() > 0) {
                    new Analyzer<>(new BasicVerifier()).analyze(after.name, transformed);
                }
            }
            Files.write(root.resolve("transformed-" + name + ".class"), afterBytes);
            System.out.println(name + ": actual transform counts accepted; all method stacks verified; native member counts/signatures retained");
        }
    }
}
