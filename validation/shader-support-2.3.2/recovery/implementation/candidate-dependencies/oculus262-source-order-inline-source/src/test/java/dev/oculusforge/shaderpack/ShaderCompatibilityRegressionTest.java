package dev.oculusforge.shaderpack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.oculusforge.render.program.LegacyFinalShaderAdapter;
import dev.oculusforge.render.program.FinalInterpolationAdapter;
import dev.oculusforge.render.program.PackProgramCompiler;
import dev.oculusforge.render.program.PreparedPackPipeline;
import dev.oculusforge.render.program.ShaderCompatibilityException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

final class ShaderCompatibilityRegressionTest {
    @TempDir
    Path temporaryDirectory;

    @Test
    void packExtensionsRemainAheadOfInjectedDeclarations() throws Exception {
        String source = "#version 120\n#extension GL_ARB_shader_texture_lod : enable\nvarying vec2 texcoord;\nvoid main() { gl_FragColor = vec4(texcoord, 0.0, 1.0); }\n";
        String adapted = new LegacyFinalShaderAdapter().adapt(source).source();
        assertTrue(adapted.indexOf("#version") < adapted.indexOf("#extension"));
        assertTrue(adapted.indexOf("#extension") < adapted.indexOf("out vec4 oculus_FragColor"));
        assertEquals(1, adapted.split("#extension", -1).length - 1);
    }

    @Test
    void fullscreenInterpolationMatchesActualFragmentInputs() {
        String vertex = "out vec2 texCoord;\nflat out vec2 ordinary;\nout vec2 unused;\n";
        String fragment = "noperspective in vec2 texCoord;\nin vec2 ordinary;\n";
        String matched = FinalInterpolationAdapter.match(vertex, fragment);
        assertTrue(matched.contains("noperspective out vec2 texCoord;"));
        assertTrue(matched.contains("\nout vec2 ordinary;"));
        assertFalse(matched.contains("smooth out vec2 ordinary;"));
        assertTrue(FinalInterpolationAdapter.match(vertex, "smooth in vec2 ordinary;").contains("smooth out vec2 ordinary;"));
        assertTrue(matched.contains("out vec2 unused;"));
        assertEquals(matched, FinalInterpolationAdapter.match(matched, fragment));
        assertTrue(FinalInterpolationAdapter.match(vertex, "flat in vec2 texCoord;").contains("flat out vec2 texCoord;"));
    }

    @Test
    void preprocessingKeepsOnlyTheActiveShaderBranch() throws Exception {
        String source = """
            #version 120
            #ifdef FRAGMENT_SHADER
            uniform float viewWidth;
            #else
            uniform mat4 unsupportedVertexMatrix;
            #endif
            """;

        String result = GlslPreprocessor.preprocess(source, Map.of("FRAGMENT_SHADER", ""));

        assertTrue(result.lines().findFirst().orElseThrow().matches("#version\\s+120"));
        assertTrue(result.contains("viewWidth"));
        assertFalse(result.contains("unsupportedVertexMatrix"));
    }

    @Test
    void reservedPreprocessorMarkersAreRejected() {
        ShaderPackException exception = assertThrows(
            ShaderPackException.class,
            () -> GlslPreprocessor.preprocess("#warning OCULUS_JCPP_GLSL_VERSION 120", Map.of())
        );
        assertEquals(ShaderPackException.Code.PREPROCESSING_FAILED, exception.code());
    }

    @Test
    void adapterHandlesLegacyAttachmentAliasesAndUniformLists() throws Exception {
        String source = """
            #version 120
            varying vec2 texcoord;
            uniform sampler2D colortex3;
            uniform sampler2D noisetex;
            uniform sampler2D normals;
            uniform float viewWidth, viewHeight;
            uniform bool heavyFog = false;
            void main() {
                vec4 color = texture2D(colortex3, texcoord);
                color += texture2D(noisetex, texcoord) * 0.0;
                if (heavyFog) color.rgb *= 0.5;
                gl_FragColor = color + vec4((viewWidth + viewHeight) * 0.0);
            }
            """;

        LegacyFinalShaderAdapter.AdaptedShader result = new LegacyFinalShaderAdapter().adapt(source);

        assertEquals(2, result.samplers().size());
        assertEquals(2, result.uniforms().size());
        assertTrue(result.source().contains("const bool heavyFog = false;"));
        assertFalse(result.source().contains("uniform sampler2D normals"));
    }

    @Test
    void unknownUsedSamplerStillFailsClosed() {
        String source = """
            #version 120
            varying vec2 texcoord;
            uniform sampler2D remoteTexture;
            void main() { gl_FragColor = texture2D(remoteTexture, texcoord); }
            """;

        assertThrows(ShaderCompatibilityException.class, () -> new LegacyFinalShaderAdapter().adapt(source));
    }

    @Test
    void worldZeroProgramIsPreferredAndPrepared() throws Exception {
        Path world = Files.createDirectories(temporaryDirectory.resolve("shaders/world0"));
        Files.writeString(world.resolve("final.fsh"), """
            #version 120
            #define FRAGMENT_SHADER
            #ifdef FRAGMENT_SHADER
            varying vec2 texcoord;
            uniform sampler2D colortex1;
            void main() { gl_FragColor = texture2D(colortex1, texcoord); }
            #endif
            """);
        Files.writeString(temporaryDirectory.resolve("shaders/final.fsh"), "invalid root fallback");
        ShaderPackDescriptor descriptor = new ShaderPackDescriptor(
            "test", "Test pack", temporaryDirectory, ShaderPackDescriptor.Kind.DIRECTORY
        );

        PreparedPackPipeline result = new PackProgramCompiler(ShaderPackLimits.DEFAULT).prepare(descriptor);

        assertEquals(1, result.programs().size());
        assertEquals("final", result.programs().getFirst().stageName());
        assertEquals("colortex1", result.programs().getFirst().fragment().samplers().getFirst().name());
        assertTrue(result.gbufferSources().isEmpty());
    }

    @Test
    void terrainAndEntityGbufferPairsArePreparedAndHashBound() throws Exception {
        Path world = Files.createDirectories(temporaryDirectory.resolve("shaders/world0"));
        Files.writeString(world.resolve("final.fsh"), "#version 120\nvarying vec2 texcoord;\nvoid main(){gl_FragColor=vec4(texcoord,0.0,1.0);}\n");
        for (String name : new String[] {"gbuffers_terrain", "gbuffers_entities"}) {
            Files.writeString(world.resolve(name + ".vsh"), "#version 120\nvoid main(){gl_Position=gl_Vertex;}\n");
            Files.writeString(world.resolve(name + ".fsh"), "#version 120\nvoid main(){gl_FragColor=vec4(1.0);}\n");
        }
        ShaderPackDescriptor descriptor = new ShaderPackDescriptor(
            "test", "Test pack", temporaryDirectory, ShaderPackDescriptor.Kind.DIRECTORY
        );
        PreparedPackPipeline result = new PackProgramCompiler(ShaderPackLimits.DEFAULT).prepare(descriptor);
        assertEquals(8, result.gbufferSources().size());
        for (String category : new String[] {"terrain", "entity"}) {
            assertTrue(result.gbufferSources().get(category + ".pack.vertex.sha256").matches("[0-9a-f]{64}"));
            assertTrue(result.gbufferSources().get(category + ".pack.fragment.sha256").matches("[0-9a-f]{64}"));
        }
    }

    @Test
    void partialGbufferPairsFailClosed() throws Exception {
        Path world = Files.createDirectories(temporaryDirectory.resolve("shaders/world0"));
        Files.writeString(world.resolve("final.fsh"), "#version 120\nvarying vec2 texcoord;\nvoid main(){gl_FragColor=vec4(texcoord,0.0,1.0);}\n");
        Files.writeString(world.resolve("gbuffers_terrain.vsh"), "#version 120\nvoid main(){gl_Position=gl_Vertex;}\n");
        ShaderPackDescriptor descriptor = new ShaderPackDescriptor(
            "test", "Test pack", temporaryDirectory, ShaderPackDescriptor.Kind.DIRECTORY
        );
        assertThrows(ShaderCompatibilityException.class,
            () -> new PackProgramCompiler(ShaderPackLimits.DEFAULT).prepare(descriptor));
    }

    @Test
    void includeCyclesAreRejected() throws Exception {
        Path shaders = Files.createDirectories(temporaryDirectory.resolve("shaders"));
        Files.writeString(shaders.resolve("a.glsl"), "#include \"b.glsl\"");
        Files.writeString(shaders.resolve("b.glsl"), "#include \"a.glsl\"");

        try (PackFileSystem pack = new DirectoryPackFileSystem(temporaryDirectory, ShaderPackLimits.DEFAULT)) {
            ShaderPackException exception = assertThrows(
                ShaderPackException.class,
                () -> new ShaderSourceLoader(pack, ShaderPackLimits.DEFAULT).load(VirtualPath.parse("shaders/a.glsl"))
            );
            assertEquals(ShaderPackException.Code.INCLUDE_CYCLE, exception.code());
        }
    }
}
