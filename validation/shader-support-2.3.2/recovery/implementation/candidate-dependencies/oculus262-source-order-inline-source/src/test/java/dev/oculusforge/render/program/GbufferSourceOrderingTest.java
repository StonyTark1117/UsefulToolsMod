package dev.oculusforge.render.program;

import com.mojang.blaze3d.shaders.ShaderType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GbufferSourceOrderingTest {
    private static final String HASH = "a".repeat(64);
    private static final String RENDER_BRIDGE_HASH = "e2de6aa0f24780fcc8d73c0784d0623773ad5d29dfd5b5c8dd042d2e24cc6ed6";

    @Test
    void markerFollowsVersionInVertexShader() throws Exception {
        String source = "#version 150\nin vec3 Position;\nvoid main() { gl_Position = vec4(Position, 1.0); }\n";
        String adapted = PackProgramCompiler.adaptGbuffer("terrain", ShaderType.VERTEX, source, HASH);

        assertEquals("#version 150", adapted.lines().findFirst().orElseThrow());
        assertTrue(adapted.startsWith("#version 150\n// Oculus BSL terrain gbuffer source " + HASH + "\n"));
    }

    @Test
    void markerAndColorRoundTripFollowVersionInFragmentShader() throws Exception {
        String source = "#version 150\nout vec4 fragColor;\nvoid main() {\n"
            + "    vec4 color = vec4(1.0);\n"
            + "    fragColor = apply_fog(color, 0.0);\n}\n";
        String adapted = PackProgramCompiler.adaptGbuffer("entity", ShaderType.FRAGMENT, source, HASH);

        assertEquals("#version 150", adapted.lines().findFirst().orElseThrow());
        assertTrue(adapted.indexOf("// Oculus BSL entity gbuffer source") < adapted.indexOf("out vec4 fragColor"));
        assertTrue(adapted.indexOf("linear-light albedo round trip") < adapted.indexOf("fragColor = apply_fog"));
    }

    @Test
    void missingLeadingVersionFailsClosed() {
        assertThrows(ShaderCompatibilityException.class,
            () -> PackProgramCompiler.adaptGbuffer("terrain", ShaderType.VERTEX, "// comment\n#version 150\n", HASH));
    }

    @Test
    void exactRenderProofBridgeIsRequired() {
        String oldBridge = System.getProperty("oculus262.gbuffer.renderBridge");
        String oldFailure = System.getProperty("oculus262.gbuffer.transformFailure");
        try {
            System.clearProperty("oculus262.gbuffer.renderBridge");
            System.clearProperty("oculus262.gbuffer.transformFailure");
            assertThrows(ShaderCompatibilityException.class, PackProgramCompiler::requireRenderProofBridge);

            System.setProperty("oculus262.gbuffer.renderBridge", "b".repeat(64));
            assertThrows(ShaderCompatibilityException.class, PackProgramCompiler::requireRenderProofBridge);

            System.setProperty("oculus262.gbuffer.renderBridge", RENDER_BRIDGE_HASH);
            assertDoesNotThrow(PackProgramCompiler::requireRenderProofBridge);
        } finally {
            restoreProperty("oculus262.gbuffer.renderBridge", oldBridge);
            restoreProperty("oculus262.gbuffer.transformFailure", oldFailure);
        }
    }

    private static void restoreProperty(String name, String value) {
        if (value == null) System.clearProperty(name);
        else System.setProperty(name, value);
    }
}
