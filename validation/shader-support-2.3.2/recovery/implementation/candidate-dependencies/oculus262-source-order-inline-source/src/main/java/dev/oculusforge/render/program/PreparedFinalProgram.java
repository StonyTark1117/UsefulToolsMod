package dev.oculusforge.render.program;

import dev.oculusforge.shaderpack.ShaderPackDescriptor;

public record PreparedFinalProgram(ShaderPackDescriptor pack, String stageName, String vertexSource, LegacyFinalShaderAdapter.AdaptedShader fragment) {
}
