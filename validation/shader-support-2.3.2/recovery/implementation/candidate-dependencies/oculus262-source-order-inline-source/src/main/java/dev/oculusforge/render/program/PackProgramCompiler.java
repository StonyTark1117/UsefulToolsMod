package dev.oculusforge.render.program;

import com.mojang.blaze3d.PrimitiveTopology;
import com.mojang.blaze3d.pipeline.BindGroupLayout;
import com.mojang.blaze3d.pipeline.ColorTargetState;
import com.mojang.blaze3d.pipeline.CompiledRenderPipeline;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.pipeline.BindGroupLayout.Builder;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HexFormat;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.client.renderer.MappableRingBuffer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.ShaderManager;
import net.minecraft.resources.Identifier;

public final class PackProgramCompiler {
   private static final List<String> FULLSCREEN_STAGES = fullscreenStages();
   private static final Map<String, String> GLSL_ENVIRONMENT = Map.ofEntries(
      Map.entry("MC_VERSION", "260200"),
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
   private static final Identifier TERRAIN_SHADER = Identifier.withDefaultNamespace("core/terrain");
   private static final Identifier ENTITY_SHADER = Identifier.withDefaultNamespace("core/entity");

   public PackProgramCompiler(ShaderPackLimits limits) {
      this.limits = limits;
      this.adapter = new LegacyFinalShaderAdapter();
      this.vertexAdapter = new LegacyFinalVertexAdapter();
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
               LegacyFinalShaderAdapter.AdaptedShader fragment = this.adapter.adapt(fragmentSource, vertexOutputs);
               vertexSource = matchFinalInterpolation(vertexSource, fragment.source());
               programs.add(new PreparedFinalProgram(descriptor, stageName, vertexSource, fragment));
            }
         }

         if (programs.isEmpty()) {
            throw new ShaderCompatibilityException("Pack has no compatible deferred, composite, or final fragment program under shaders/world0 or shaders");
         }

         var15 = new PreparedPackPipeline(descriptor, programs, this.prepareGbuffers(pack, programRoot));
      }

      return var15;
   }

   private static String matchFinalInterpolation(String vertexSource, String fragmentSource) {
      Pattern input = Pattern.compile("(?m)^[ \\t]*(?:(flat|smooth|noperspective)\\s+)?in\\s+(\\w+)\\s+(\\w+)\\s*;");
      Pattern output = Pattern.compile("(?m)^[ \\t]*(?:(flat|smooth|noperspective)\\s+)?out\\s+(\\w+)\\s+(\\w+)\\s*;");
      Map<String, String> qualifiers = new HashMap<>();
      Matcher inputs = input.matcher(fragmentSource);
      while (inputs.find()) {
         qualifiers.put(inputs.group(3), inputs.group(1) == null ? "" : inputs.group(1));
      }
      Matcher outputs = output.matcher(vertexSource);
      StringBuilder result = new StringBuilder();
      while (outputs.find()) {
         String qualifier = qualifiers.get(outputs.group(3));
         if (qualifier == null) {
            outputs.appendReplacement(result, Matcher.quoteReplacement(outputs.group()));
         } else {
            outputs.appendReplacement(result, Matcher.quoteReplacement(
               (qualifier.isEmpty() ? "" : qualifier + " ") + "out " + outputs.group(2) + " " + outputs.group(3) + ";"));
         }
      }
      outputs.appendTail(result);
      return result.toString();
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
      Map<String, String> gbufferHashes = Map.of();

      try {
         gbufferHashes = this.installGbufferPipelines(prepared);
         for (PreparedFinalProgram program : prepared.programs()) {
            compiledPrograms.add(this.compileProgram(program));
         }

         return new PackShaderPipeline(prepared.pack().id(), compiledPrograms, gbufferHashes);
      } catch (RuntimeException | ShaderCompatibilityException var5) {
         compiledPrograms.forEach(PackFinalProgram::close);
         if (!gbufferHashes.isEmpty()) {
            System.clearProperty("oculus262.gbuffer.active");
            System.clearProperty("oculus262.gbuffer.program.terrain");
            System.clearProperty("oculus262.gbuffer.program.entity");
            RenderSystem.getDevice().clearPipelineCache();
         }
         throw var5;
      }
   }

   private Map<String, String> prepareGbuffers(PackFileSystem pack, String programRoot) throws IOException, ShaderCompatibilityException {
      Map<String, String> sources = new HashMap<>();
      ShaderSourceLoader loader = new ShaderSourceLoader(pack, this.limits);
      boolean foundAny = false;
      for (String category : List.of("terrain", "entity")) {
         String packName = category.equals("entity") ? "gbuffers_entities" : "gbuffers_terrain";
         VirtualPath vertexPath = VirtualPath.parse(programRoot + "/" + packName + ".vsh");
         VirtualPath fragmentPath = VirtualPath.parse(programRoot + "/" + packName + ".fsh");
         boolean hasVertex = pack.exists(vertexPath);
         boolean hasFragment = pack.exists(fragmentPath);
         if (!hasVertex && !hasFragment && !foundAny) continue;
         if (!hasVertex || !hasFragment) {
            throw new ShaderCompatibilityException("Pack is missing required " + packName + " vertex or fragment source");
         }
         foundAny = true;
         String vertex = GlslPreprocessor.preprocess(loader.load(vertexPath).source(), GLSL_ENVIRONMENT);
         String fragment = GlslPreprocessor.preprocess(loader.load(fragmentPath).source(), GLSL_ENVIRONMENT);
         sources.put(category + ".pack.vertex", vertex);
         sources.put(category + ".pack.fragment", fragment);
         sources.put(category + ".pack.vertex.sha256", fingerprintFull(vertex));
         sources.put(category + ".pack.fragment.sha256", fingerprintFull(fragment));
      }
      if (foundAny && sources.size() != 8) {
         throw new ShaderCompatibilityException("Pack must provide both terrain and entity gbuffer source pairs");
      }
      return Map.copyOf(sources);
   }

   private Map<String, String> installGbufferPipelines(PreparedPackPipeline prepared) throws ShaderCompatibilityException {
      if (prepared.gbufferSources().isEmpty()) return Map.of();
      requireRenderProofBridge();
      ShaderManager manager = Minecraft.getInstance().getShaderManager();
      var device = RenderSystem.getDevice();
      Map<String, String> programHashes = new HashMap<>();
      Map<String, String> vertices = new HashMap<>();
      Map<String, String> fragments = new HashMap<>();
      for (String category : List.of("terrain", "entity")) {
         Identifier shader = category.equals("terrain") ? TERRAIN_SHADER : ENTITY_SHADER;
         String nativeVertex = manager.getShader(shader, ShaderType.VERTEX);
         String nativeFragment = manager.getShader(shader, ShaderType.FRAGMENT);
         if (nativeVertex == null || nativeFragment == null) {
            throw new ShaderCompatibilityException("Minecraft did not expose the native " + category + " shader sources");
         }
         String vertex = adaptGbuffer(category, ShaderType.VERTEX, nativeVertex,
            prepared.gbufferSources().get(category + ".pack.vertex.sha256"));
         String fragment = adaptGbuffer(category, ShaderType.FRAGMENT, nativeFragment,
            prepared.gbufferSources().get(category + ".pack.fragment.sha256"));
         vertices.put(category, vertex);
         fragments.put(category, fragment);
         String programHash = fingerprintFull(category + "\u0000" + vertex + "\u0000" + fragment);
         programHashes.put(category, programHash);
         writeProof("prepared", category, "", false, programHash, 0,
            prepared.gbufferSources().get(category + ".pack.vertex.sha256"),
            prepared.gbufferSources().get(category + ".pack.fragment.sha256"),
            fingerprintFull(vertex), fingerprintFull(fragment));
      }

      device.clearPipelineCache();
      for (RenderPipeline pipeline : RenderPipelines.getStaticPipelines()) {
         String category = pipelineCategory(pipeline);
         if (category == null) continue;
         Identifier target = category.equals("terrain") ? TERRAIN_SHADER : ENTITY_SHADER;
         ShaderSource source = (id, type) -> {
            if (id.equals(target)) return type == ShaderType.VERTEX ? vertices.get(category) : fragments.get(category);
            return manager.getShader(id, type);
         };
         CompiledRenderPipeline compiled = device.precompilePipeline(pipeline, source);
         String hash = programHashes.get(category);
         writeProof("compiled", category, pipeline.getLocation().toString(), compiled.isValid(), hash, 0,
            "", "", "", "");
         if (!compiled.isValid()) {
            device.clearPipelineCache();
            throw new ShaderCompatibilityException("OpenGL rejected the BSL-compatible " + category + " gbuffer pipeline " + pipeline.getLocation());
         }
      }
      System.setProperty("oculus262.gbuffer.active", "true");
      for (Map.Entry<String, String> entry : programHashes.entrySet()) {
         System.setProperty("oculus262.gbuffer.program." + entry.getKey(), entry.getValue());
      }
      return Map.copyOf(programHashes);
   }

   static void requireRenderProofBridge() throws ShaderCompatibilityException {
      try {
         Class.forName("com.mojang.blaze3d.opengl.GlRenderPass", false, PackProgramCompiler.class.getClassLoader());
      } catch (ClassNotFoundException exception) {
         throw new ShaderCompatibilityException("Exact OpenGL render-pass proof class is unavailable");
      }
      String expected = "e2de6aa0f24780fcc8d73c0784d0623773ad5d29dfd5b5c8dd042d2e24cc6ed6";
      String actual = System.getProperty("oculus262.gbuffer.renderBridge", "");
      if (!actual.equals(expected)) {
         String failure = System.getProperty("oculus262.gbuffer.transformFailure", "none");
         throw new ShaderCompatibilityException("Exact OpenGL render-pass proof bridge was not installed; expected="
            + expected + " actual=" + actual + " transformFailure=" + failure);
      }
   }

   private static String pipelineCategory(RenderPipeline pipeline) {
      String location = pipeline.getLocation().toString();
      if (pipeline.getVertexShader().equals(TERRAIN_SHADER) && location.matches("minecraft:pipeline/(solid|cutout|translucent)_terrain")) return "terrain";
      if (pipeline.getVertexShader().equals(ENTITY_SHADER) && location.matches("minecraft:pipeline/(entity|armor)_[a-z0-9_]+")) return "entity";
      return null;
   }

   static String adaptGbuffer(String category, ShaderType type, String nativeSource, String packSourceHash)
      throws ShaderCompatibilityException {
      String marker = "// Oculus BSL " + category + " gbuffer source " + packSourceHash + "\n";
      int versionEnd = nativeSource.indexOf('\n');
      if (!nativeSource.startsWith("#version ") || versionEnd < 0) {
         throw new ShaderCompatibilityException("Native " + category + " " + type.getName() + " shader no longer begins with #version");
      }
      String markedSource = nativeSource.substring(0, versionEnd + 1) + marker + nativeSource.substring(versionEnd + 1);
      if (type == ShaderType.VERTEX) return markedSource;
      String assignment = "    fragColor = apply_fog(color,";
      if (!markedSource.contains(assignment)) {
         throw new ShaderCompatibilityException("Native " + category + " fragment source no longer has the expected final color assignment");
      }
      String bslColor = "    // BSL-compatible linear-light albedo round trip; the native bindings supply texture, lightmap and fog.\n"
         + "    color.rgb = sqrt(max(pow(max(color.rgb, vec3(0.0)), vec3(2.2)), vec3(0.0)));\n";
      return markedSource.replace(assignment, bslColor + assignment);
   }

   private static void writeProof(String kind, String category, String pipeline, boolean valid, String programHash, int drawCount,
                                  String packVertexHash, String packFragmentHash, String adaptedVertexHash, String adaptedFragmentHash) {
      String path = System.getProperty("oculus262.gbuffer.eventLog", "");
      String token = System.getProperty("oculus262.gbuffer.runToken", "");
      if (path.isBlank() || token.isBlank()) return;
      StringBuilder event = new StringBuilder(384).append('{')
         .append("\"kind\":\"").append(kind).append("\",\"category\":\"").append(category)
         .append("\",\"run_token\":\"").append(token).append('"');
      if (!pipeline.isBlank()) event.append(",\"pipeline\":\"").append(pipeline).append('"');
      if (kind.equals("compiled")) event.append(",\"valid\":").append(valid);
      if (!programHash.isBlank()) event.append(",\"program_sha256\":\"").append(programHash).append('"');
      if (kind.equals("rendered")) event.append(",\"draw_count\":").append(drawCount);
      if (kind.equals("prepared")) event.append(",\"pack_vertex_sha256\":\"").append(packVertexHash)
         .append("\",\"pack_fragment_sha256\":\"").append(packFragmentHash)
         .append("\",\"adapted_vertex_sha256\":\"").append(adaptedVertexHash)
         .append("\",\"adapted_fragment_sha256\":\"").append(adaptedFragmentHash).append('"');
      event.append("}\n");
      try {
         Files.writeString(Path.of(path), event, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
      } catch (IOException exception) {
         throw new IllegalStateException("Cannot retain Oculus gbuffer proof", exception);
      }
   }

   private PackFinalProgram compileProgram(PreparedFinalProgram prepared) throws ShaderCompatibilityException {
      String fingerprint = fingerprint(prepared.stageName() + "\u0000" + prepared.vertexSource() + "\u0000" + prepared.fragment().source());
      String root = "pack/" + fingerprint + "/" + prepared.stageName();
      Identifier vertexId = Identifier.fromNamespaceAndPath("oculus", root + "_vertex");
      Identifier fragmentId = Identifier.fromNamespaceAndPath("oculus", root + "_fragment");
      Identifier pipelineId = Identifier.fromNamespaceAndPath("oculus", root);
      Builder bindings = BindGroupLayout.builder();
      prepared.fragment().samplers().forEach(sampler -> bindings.withSampler(sampler.name()));
      boolean hasUniforms = !prepared.fragment().uniforms().isEmpty();
      if (hasUniforms) {
         bindings.withUniform("OculusLegacyUniforms", UniformType.UNIFORM_BUFFER);
      }

      RenderPipeline pipeline = RenderPipeline.builder(new Snippet[0])
         .withLocation(pipelineId)
         .withVertexShader(vertexId)
         .withFragmentShader(fragmentId)
         .withBindGroupLayout(bindings.build())
         .withColorTargetState(ColorTargetState.DEFAULT)
         .withPrimitiveTopology(PrimitiveTopology.TRIANGLES)
         .withCull(false)
         .build();
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
            prepared.pack().id(), prepared.stageName(), pipeline, prepared.fragment().samplers(), prepared.fragment().uniforms(), uniformBuffer
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

   private static String fingerprintFull(String source) throws ShaderCompatibilityException {
      try {
         return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(source.getBytes(StandardCharsets.UTF_8)));
      } catch (NoSuchAlgorithmException exception) {
         throw new ShaderCompatibilityException("This Java runtime does not provide SHA-256");
      }
   }
}
