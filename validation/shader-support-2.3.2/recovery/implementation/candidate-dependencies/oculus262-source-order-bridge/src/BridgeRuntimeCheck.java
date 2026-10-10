import dev.oculusforge.render.program.FinalInterpolationAdapter;
import dev.oculusforge.render.program.LegacyFinalShaderAdapter;

public final class BridgeRuntimeCheck {
    public static void main(String[] arguments) throws Exception {
        String input = "#version 120\n#extension GL_ARB_shader_texture_lod : enable\n"
            + "varying vec2 texCoord;\nvoid main() { gl_FragColor = vec4(texCoord, 0.0, 1.0); }\n";
        String adapted = new LegacyFinalShaderAdapter().adapt(input).source();
        int version = adapted.indexOf("#version");
        int extension = adapted.indexOf("#extension");
        int declaration = adapted.indexOf("out vec4 oculus_FragColor");
        if (!(version >= 0 && version < extension && extension < declaration)) {
            throw new AssertionError("extension ordering was not repaired:\n" + adapted);
        }
        String vertex = "out vec2 texCoord;\n";
        String fragment = "noperspective in vec2 texCoord;\n";
        String matched = FinalInterpolationAdapter.match(vertex, fragment);
        if (!matched.contains("noperspective out vec2 texCoord;")) {
            throw new AssertionError("interpolation was not matched: " + matched);
        }
        Class.forName("dev.oculusforge.render.program.PackProgramCompiler", false,
            BridgeRuntimeCheck.class.getClassLoader());
        System.out.println("BRIDGE_RUNTIME_CHECK pass");
    }
}
