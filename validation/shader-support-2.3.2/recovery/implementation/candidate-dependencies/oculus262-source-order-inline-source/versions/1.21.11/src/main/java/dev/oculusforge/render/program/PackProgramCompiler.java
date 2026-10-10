package dev.oculusforge.render.program;

import com.mojang.blaze3d.pipeline.CompiledRenderPipeline;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.pipeline.RenderPipeline.Builder;
import com.mojang.blaze3d.pipeline.RenderPipeline.Snippet;
import com.mojang.blaze3d.shaders.ShaderSource;
import com.mojang.blaze3d.shaders.ShaderType;
import com.mojang.blaze3d.shaders.UniformType;
import com.mojang.blaze3d.systems.RenderSystem;
import dev.oculusforge.shaderpack.PackFileSystem;
import dev.oculusforge.shaderpack.GlslPreprocessor;
import dev.oculusforge.shaderpack.ShaderPackDescriptor;
import dev.oculusforge.shaderpack.ShaderPackLimits;
import dev.oculusforge.shaderpack.ShaderSourceLoader;
import dev.oculusforge.shaderpack.VirtualPath;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HexFormat;
import java.util.List;
import java.util.Map;
import net.minecraft.client.renderer.MappableRingBuffer;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;

public final class PackProgramCompiler {
   private static final List<String> FULLSCREEN_STAGES = fullscreenStages();
   private static final Map<String, String> GLSL_ENVIRONMENT = Map.ofEntries(
      Map.entry("MC_VERSION", "12111"),
      Map.entry("IRIS_VERSION", "10809"),
      Map.entry("IS_IRIS", ""),
      Map.entry("MC_OS_WINDOWS", ""),
      Map.entry("MC_GL_VENDOR_AMD", ""),
      Map.entry("MC_GL_RENDERER_RADEON", ""),
      Map.entry("MC_NORMAL_MAP", ""),
      Map.entry("MC_SPECULAR_MAP", ""),
      Map.entry("MC_RENDER_QUALITY", "1.0"),
      Map.entry("MC_SHADOW_QUALITY", "1.0"),
      Map.entry("MC_HAND_DEPTH", "0.125"),
      Map.entry("MAX_COLOR_BUFFERS", "16")
   );
   private final ShaderPackLimits limits;
   private final LegacyFinalShaderAdapter adapter;
   private final LegacyFinalVertexAdapter vertexAdapter;
   private final LegacyUniformStateCapture uniformState;

   public PackProgramCompiler(ShaderPackLimits limits) {
      this(limits, new LegacyUniformStateCapture());
   }

   public PackProgramCompiler(ShaderPackLimits limits, LegacyUniformStateCapture uniformState) {
      this.limits = limits;
      this.adapter = new LegacyFinalShaderAdapter();
      this.vertexAdapter = new LegacyFinalVertexAdapter();
      this.uniformState = uniformState;
   }

   public LegacyUniformStateCapture uniformState() {
      return this.uniformState;
   }

   public PreparedPackPipeline prepare(ShaderPackDescriptor descriptor) throws IOException, ShaderCompatibilityException {
      PreparedPackPipeline var15;
      try (PackFileSystem pack = descriptor.open(this.limits)) {
         List<PreparedFinalProgram> programs = new ArrayList<>();
         String programRoot = selectProgramRoot(pack);
         List<String> stages = "shaders".equals(programRoot) ? FULLSCREEN_STAGES : List.of("final");

         for (String stageName : stages) {
            VirtualPath fragmentPath = VirtualPath.parse(programRoot + "/" + stageName + ".fsh");
            if (pack.exists(fragmentPath)) {
               ShaderSourceLoader.LoadResult fragmentLoaded = new ShaderSourceLoader(pack, this.limits).load(fragmentPath);
               String vertexSource = LegacyFinalVertexAdapter.DEFAULT_SOURCE;
               Map<String, String> vertexOutputs = LegacyFinalVertexAdapter.DEFAULT_OUTPUTS;
               VirtualPath vertexPath = VirtualPath.parse(programRoot + "/" + stageName + ".vsh");
               if ("shaders".equals(programRoot) && pack.exists(vertexPath)) {
                  ShaderSourceLoader.LoadResult vertexLoaded = new ShaderSourceLoader(pack, this.limits).load(vertexPath);
                  LegacyFinalVertexAdapter.AdaptedVertex adaptedVertex = this.vertexAdapter.adapt(vertexLoaded.source());
                  vertexSource = adaptedVertex.source();
                  vertexOutputs = adaptedVertex.outputs();
               }

               String fragmentSource = GlslPreprocessor.preprocess(fragmentLoaded.source(), GLSL_ENVIRONMENT);
               programs.add(new PreparedFinalProgram(descriptor, stageName, vertexSource, this.adapter.adapt(fragmentSource, vertexOutputs)));
            }
         }

         if (programs.isEmpty()) {
            throw new ShaderCompatibilityException("Pack has no compatible deferred, composite, or final fragment program under shaders/world0 or shaders");
         }

         var15 = new PreparedPackPipeline(descriptor, programs);
      }

      return var15;
   }

   private static String selectProgramRoot(PackFileSystem pack) throws IOException {
      for (String root : List.of("shaders/world0", "shaders")) {
         for (String stageName : FULLSCREEN_STAGES) {
            if (pack.exists(VirtualPath.parse(root + "/" + stageName + ".fsh"))) {
               return root;
            }
         }
      }

      return "shaders";
   }

   public PackShaderPipeline compile(PreparedPackPipeline prepared) throws ShaderCompatibilityException {
      RenderSystem.assertOnRenderThread();
      List<PackFinalProgram> compiledPrograms = new ArrayList<>();

      try {
         for (PreparedFinalProgram program : prepared.programs()) {
            compiledPrograms.add(this.compileProgram(program));
         }

         return new PackShaderPipeline(prepared.pack().id(), compiledPrograms);
      } catch (RuntimeException | ShaderCompatibilityException var5) {
         compiledPrograms.forEach(PackFinalProgram::close);
         throw var5;
      }
   }

   private PackFinalProgram compileProgram(PreparedFinalProgram prepared) throws ShaderCompatibilityException {
      String fingerprint = fingerprint(prepared.stageName() + "\u0000" + prepared.vertexSource() + "\u0000" + prepared.fragment().source());
      String root = "pack/" + fingerprint + "/" + prepared.stageName();
      Identifier vertexId = Identifier.fromNamespaceAndPath("oculus", root + "_vertex");
      Identifier fragmentId = Identifier.fromNamespaceAndPath("oculus", root + "_fragment");
      Identifier pipelineId = Identifier.fromNamespaceAndPath("oculus", root);
      Builder pipelineBuilder = RenderPipeline.builder(new Snippet[]{RenderPipelines.POST_PROCESSING_SNIPPET});
      prepared.fragment().samplers().forEach(sampler -> pipelineBuilder.withSampler(sampler.name()));
      boolean hasUniforms = !prepared.fragment().uniforms().isEmpty();
      if (hasUniforms) {
         pipelineBuilder.withUniform("OculusLegacyUniforms", UniformType.UNIFORM_BUFFER);
      }

      RenderPipeline pipeline = pipelineBuilder.withLocation(pipelineId).withVertexShader(vertexId).withFragmentShader(fragmentId).withCull(false).build();
      ShaderSource source = (id, type) -> shaderSource(id, type, vertexId, fragmentId, prepared.vertexSource(), prepared.fragment().source());
      CompiledRenderPipeline compiled = RenderSystem.getDevice().precompilePipeline(pipeline, source);
      if (!compiled.isValid()) {
         throw new ShaderCompatibilityException(
            "OpenGL rejected shaders/"
               + prepared.stageName()
               + ".fsh from '"
               + prepared.pack().displayName()
               + "' at stage '"
               + prepared.stageName()
               + "'. The compiler log is in latest.log."
         );
      } else {
         MappableRingBuffer uniformBuffer = hasUniforms
            ? new MappableRingBuffer(
               () -> "Oculus " + prepared.pack().displayName() + " legacy uniforms", 130, PackFinalProgram.uniformBufferSize(prepared.fragment().uniforms())
            )
            : null;
         return new PackFinalProgram(
            prepared.pack().id(),
            prepared.stageName(),
            pipeline,
            prepared.fragment().samplers(),
            prepared.fragment().uniforms(),
            uniformBuffer,
            this.uniformState
         );
      }
   }

   private static List<String> fullscreenStages() {
      List<String> stages = new ArrayList<>(33);
      stages.add("deferred");

      for (int index = 1; index <= 15; index++) {
         stages.add("deferred" + index);
      }

      stages.add("composite");

      for (int index = 1; index <= 15; index++) {
         stages.add("composite" + index);
      }

      stages.add("final");
      return List.copyOf(stages);
   }

   private static String shaderSource(
      Identifier requested, ShaderType type, Identifier vertexId, Identifier fragmentId, String vertexSource, String fragmentSource
   ) {
      if (type == ShaderType.VERTEX && requested.equals(vertexId)) {
         return vertexSource;
      } else {
         return type == ShaderType.FRAGMENT && requested.equals(fragmentId) ? fragmentSource : null;
      }
   }

   private static String fingerprint(String source) throws ShaderCompatibilityException {
      try {
         byte[] digest = MessageDigest.getInstance("SHA-256").digest(source.getBytes(StandardCharsets.UTF_8));
         return HexFormat.of().formatHex(digest, 0, 12);
      } catch (NoSuchAlgorithmException var2) {
         throw new ShaderCompatibilityException("This Java runtime does not provide SHA-256");
      }
   }
}
