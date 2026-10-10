package dev.oculusforge.tools;

import dev.oculusforge.render.program.PackProgramCompiler;
import dev.oculusforge.render.program.PreparedFinalProgram;
import dev.oculusforge.render.program.PreparedPackPipeline;
import dev.oculusforge.shaderpack.ShaderPackDescriptor;
import dev.oculusforge.shaderpack.ShaderPackLimits;

import java.nio.file.Files;
import java.nio.file.Path;

public final class ShaderPackAuditMain {
    private ShaderPackAuditMain() {
    }

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            throw new IllegalArgumentException("Pass one or more shader-pack ZIP or directory paths");
        }

        for (String argument : args) {
            Path path = Path.of(argument).toAbsolutePath().normalize();
            if (!Files.exists(path)) {
                throw new IllegalArgumentException("Shader pack does not exist: " + path);
            }

            ShaderPackDescriptor.Kind kind = Files.isDirectory(path)
                ? ShaderPackDescriptor.Kind.DIRECTORY
                : ShaderPackDescriptor.Kind.ZIP;
            ShaderPackDescriptor descriptor = new ShaderPackDescriptor(
                path.getFileName().toString(),
                path.getFileName().toString(),
                path,
                kind
            );
            PreparedPackPipeline pipeline = new PackProgramCompiler(ShaderPackLimits.DEFAULT).prepare(descriptor);
            System.out.printf("PACK %s: %d prepared program(s)%n", path, pipeline.programs().size());
            for (PreparedFinalProgram program : pipeline.programs()) {
                System.out.printf(
                    "  %s: vertex=%d chars, fragment=%d chars, samplers=%s, uniforms=%s%n",
                    program.stageName(),
                    program.vertexSource().length(),
                    program.fragment().source().length(),
                    program.fragment().samplers(),
                    program.fragment().uniforms()
                );
            }
        }
    }
}
