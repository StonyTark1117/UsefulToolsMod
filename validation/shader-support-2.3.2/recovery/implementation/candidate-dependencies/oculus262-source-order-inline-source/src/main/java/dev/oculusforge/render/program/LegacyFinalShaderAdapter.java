package dev.oculusforge.render.program;

import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class LegacyFinalShaderAdapter {
   private static final Pattern VERSION = Pattern.compile("(?m)^\\s*#\\s*version\\s+\\d+(?:\\s+\\w+)?\\s*$");
   private static final Pattern SAMPLER = Pattern.compile("(?m)^\\s*uniform\\s+(sampler2D|sampler2DShadow)\\s+([A-Za-z_][A-Za-z0-9_]*)\\s*;\\s*(?://.*)?$");
   private static final Pattern UNIFORM = Pattern.compile("(?m)^\\s*uniform\\s+([A-Za-z_][A-Za-z0-9_]*)\\s+([A-Za-z_][A-Za-z0-9_]*)\\s*;\\s*(?://.*)?$");
   private static final Pattern DEFAULTED_UNIFORM = Pattern.compile(
      "(?m)^\\s*uniform\\s+(bool|float|int|vec[234]|ivec[234])\\s+([A-Za-z_][A-Za-z0-9_]*)\\s*=\\s*([^;\\n]+);"
   );
   private static final Pattern UNIFORM_LIST = Pattern.compile(
      "(?m)^\\s*uniform\\s+([A-Za-z_][A-Za-z0-9_]*)\\s+([A-Za-z_][A-Za-z0-9_]*(?:\\s*,\\s*[A-Za-z_][A-Za-z0-9_]*)+)\\s*;\\s*(?://.*)?$"
   );
   private static final Pattern FRAG_DATA = Pattern.compile("\\bgl_FragData\\s*\\[\\s*(\\d+)\\s*]");
   private static final Pattern LEGACY_VARYING = Pattern.compile("\\bvarying\\b");
   private static final Pattern VARYING_DECLARATION = Pattern.compile(
      "(?m)^\\s*varying\\s+([A-Za-z_][A-Za-z0-9_]*)\\s+([A-Za-z_][A-Za-z0-9_]*)\\s*;\\s*(?://.*)?$"
   );
   private static final Pattern ANY_UNIFORM_DECLARATION = Pattern.compile("(?m)^\\s*(?:layout\\s*\\([^\\n)]*\\)\\s*)?uniform\\b[^\\n;{]*(?:;|\\{)");
   private static final Pattern LEGACY_ATTRIBUTE = Pattern.compile("\\battribute\\b");
   private static final Pattern TEXTURE_2D_LOD = Pattern.compile("\\btexture2DLod\\b");
   private static final Pattern TEXTURE_2D = Pattern.compile("\\btexture2D\\b");
   private static final Pattern FRAG_COLOR = Pattern.compile("\\bgl_FragColor\\b");
   private static final Set<String> COLOR_SAMPLERS = Set.of(
      "gcolor", "gdepth", "gnormal", "composite", "insampler", "gaux1", "gaux2", "gaux3", "gaux4", "shadowcolor0", "shadowcolor1"
   );
   private static final Set<String> DEPTH_SAMPLERS = Set.of("gdepthtex", "watershadow");
   private static final Map<String, LegacyFinalShaderAdapter.UniformSemantic> UNIFORM_SEMANTICS = Map.ofEntries(
      Map.entry("viewWidth", LegacyFinalShaderAdapter.UniformSemantic.VIEW_WIDTH),
      Map.entry("viewHeight", LegacyFinalShaderAdapter.UniformSemantic.VIEW_HEIGHT),
      Map.entry("aspectRatio", LegacyFinalShaderAdapter.UniformSemantic.ASPECT_RATIO),
      Map.entry("frameTime", LegacyFinalShaderAdapter.UniformSemantic.FRAME_TIME),
      Map.entry("frameTimeCounter", LegacyFinalShaderAdapter.UniformSemantic.FRAME_TIME_COUNTER),
      Map.entry("frameCounter", LegacyFinalShaderAdapter.UniformSemantic.FRAME_COUNTER),
      Map.entry("worldTime", LegacyFinalShaderAdapter.UniformSemantic.WORLD_TIME),
      Map.entry("worldDay", LegacyFinalShaderAdapter.UniformSemantic.WORLD_DAY),
      Map.entry("rainStrength", LegacyFinalShaderAdapter.UniformSemantic.RAIN_STRENGTH),
      Map.entry("wetness", LegacyFinalShaderAdapter.UniformSemantic.WETNESS),
      Map.entry("eyeAltitude", LegacyFinalShaderAdapter.UniformSemantic.EYE_ALTITUDE),
      Map.entry("near", LegacyFinalShaderAdapter.UniformSemantic.NEAR),
      Map.entry("far", LegacyFinalShaderAdapter.UniformSemantic.FAR),
      Map.entry("sunAngle", LegacyFinalShaderAdapter.UniformSemantic.SUN_ANGLE),
      Map.entry("screenBrightness", LegacyFinalShaderAdapter.UniformSemantic.SCREEN_BRIGHTNESS),
      Map.entry("isEyeInWater", LegacyFinalShaderAdapter.UniformSemantic.IS_EYE_IN_WATER),
      Map.entry("cameraPosition", LegacyFinalShaderAdapter.UniformSemantic.CAMERA_POSITION),
      Map.entry("previousCameraPosition", LegacyFinalShaderAdapter.UniformSemantic.PREVIOUS_CAMERA_POSITION),
      Map.entry("fogColor", LegacyFinalShaderAdapter.UniformSemantic.FOG_COLOR),
      Map.entry("skyColor", LegacyFinalShaderAdapter.UniformSemantic.SKY_COLOR),
      Map.entry("nightVision", LegacyFinalShaderAdapter.UniformSemantic.NIGHT_VISION),
      Map.entry("cameraPositionInt", LegacyFinalShaderAdapter.UniformSemantic.CAMERA_POSITION_INT),
      Map.entry("cameraPositionFract", LegacyFinalShaderAdapter.UniformSemantic.CAMERA_POSITION_FRACT),
      Map.entry("previousCameraPositionFract", LegacyFinalShaderAdapter.UniformSemantic.PREVIOUS_CAMERA_POSITION_FRACT),
      Map.entry("rainFactor", LegacyFinalShaderAdapter.UniformSemantic.RAIN_FACTOR),
      Map.entry("inBasaltDeltas", LegacyFinalShaderAdapter.UniformSemantic.IN_BASALT_DELTAS),
      Map.entry("inCrimsonForest", LegacyFinalShaderAdapter.UniformSemantic.IN_CRIMSON_FOREST),
      Map.entry("inNetherWastes", LegacyFinalShaderAdapter.UniformSemantic.IN_NETHER_WASTES),
      Map.entry("inSoulValley", LegacyFinalShaderAdapter.UniformSemantic.IN_SOUL_VALLEY),
      Map.entry("inWarpedForest", LegacyFinalShaderAdapter.UniformSemantic.IN_WARPED_FOREST)
   );

   public LegacyFinalShaderAdapter.AdaptedShader adapt(String input) throws ShaderCompatibilityException {
      return this.adapt(input, LegacyFinalVertexAdapter.DEFAULT_OUTPUTS);
   }

   public LegacyFinalShaderAdapter.AdaptedShader adapt(String input, Map<String, String> vertexOutputs) throws ShaderCompatibilityException {
      if (input != null && !input.isBlank()) {
         String source = input.replace("\r\n", "\n").replace('\r', '\n');
         if (!source.isEmpty() && source.charAt(0) == '\ufeff') {
            source = source.substring(1);
         }

         if (LEGACY_ATTRIBUTE.matcher(withoutComments(source)).find()) {
            throw new ShaderCompatibilityException("Fragment shader uses the vertex-only 'attribute' qualifier");
         } else {
            validateVaryings(source, vertexOutputs);
            source = normalizeUniformLists(source);
            source = DEFAULTED_UNIFORM.matcher(source).replaceAll("const $1 $2 = $3;");
            source = stripUnusedUniformDeclarations(source);
            List<LegacyFinalShaderAdapter.SamplerBinding> samplers = parseSamplers(source);
            List<LegacyFinalShaderAdapter.UniformBinding> uniforms = parseUniforms(source);
            source = removeLooseUniforms(source, uniforms);
            validateNoUnmappedUniforms(source, samplers);
            Matcher fragData = FRAG_DATA.matcher(withoutComments(source));

            while (fragData.find()) {
               if (!"0".equals(fragData.group(1))) {
                  throw new ShaderCompatibilityException("final.fsh writes gl_FragData[" + fragData.group(1) + "], but the final pass has one color target");
               }
            }

            boolean needsLegacyOutput = FRAG_COLOR.matcher(source).find() || FRAG_DATA.matcher(source).find();
            source = LEGACY_VARYING.matcher(source).replaceAll("in");
            source = adaptDepthSampling(source, samplers);
            source = TEXTURE_2D_LOD.matcher(source).replaceAll("textureLod");
            source = TEXTURE_2D.matcher(source).replaceAll("texture");
            source = FRAG_DATA.matcher(source).replaceAll("oculus_FragColor");
            source = FRAG_COLOR.matcher(source).replaceAll("oculus_FragColor");
            boolean samplesDepth = samplers.stream().anyMatch(binding -> binding.texture() == LegacyFinalShaderAdapter.TextureSemantic.DEPTH);
            String declarations = declarations(uniforms, needsLegacyOutput, samplesDepth);
            Matcher extensions = Pattern.compile("(?m)^[ \t]*#[ \t]*extension[^\\n]*$").matcher(source);
            StringBuilder extensionHeader = new StringBuilder();
            while (extensions.find()) extensionHeader.append(extensions.group()).append('\n');
            source = extensions.replaceAll("");
            declarations = extensionHeader + declarations;
            Matcher version = VERSION.matcher(source);
            if (version.find()) {
               source = version.replaceFirst(Matcher.quoteReplacement("#version 330 core\n" + declarations));
            } else {
               source = "#version 330 core\n" + declarations + source;
            }

            return new LegacyFinalShaderAdapter.AdaptedShader(source, samplers, uniforms);
         }
      } else {
         throw new ShaderCompatibilityException("final.fsh is empty");
      }
   }

   private static String normalizeUniformLists(String source) {
      Matcher matcher = UNIFORM_LIST.matcher(source);
      StringBuilder result = new StringBuilder(source.length());
      while (matcher.find()) {
         StringBuilder replacement = new StringBuilder();
         for (String name : matcher.group(2).split("\\s*,\\s*")) {
            replacement.append("uniform ").append(matcher.group(1)).append(' ').append(name).append(";\n");
         }
         matcher.appendReplacement(result, Matcher.quoteReplacement(replacement.toString()));
      }
      matcher.appendTail(result);
      return result.toString();
   }

   private static String stripUnusedUniformDeclarations(String source) {
      Matcher declarations = UNIFORM.matcher(source);
      String codeOnly = declarations.replaceAll("");
      Matcher matcher = UNIFORM.matcher(source);
      StringBuilder result = new StringBuilder(source.length());
      while (matcher.find()) {
         String name = matcher.group(2);
         if (!Pattern.compile("\\b" + Pattern.quote(name) + "\\b").matcher(withoutComments(codeOnly)).find()) {
            matcher.appendReplacement(result, "");
         }
      }
      matcher.appendTail(result);
      return result.toString();
   }

   private static List<LegacyFinalShaderAdapter.SamplerBinding> parseSamplers(String source) throws ShaderCompatibilityException {
      LinkedHashMap<String, LegacyFinalShaderAdapter.SamplerBinding> samplers = new LinkedHashMap<>();
      List<String> unsupported = new ArrayList<>();
      Matcher matcher = SAMPLER.matcher(source);

      while (matcher.find()) {
         String type = matcher.group(1);
         String name = matcher.group(2);
         if (!"sampler2D".equals(type)) {
            throw new ShaderCompatibilityException("Unsupported final-pass sampler type " + type + " for " + name);
         }

         String key = name.toLowerCase(Locale.ROOT);
         LegacyFinalShaderAdapter.TextureSemantic semantic = textureSemantic(key);
         if (semantic == null) {
            unsupported.add(name);
            continue;
         }

         samplers.putIfAbsent(name, new LegacyFinalShaderAdapter.SamplerBinding(name, semantic));
      }

      if (!unsupported.isEmpty()) {
         throw new ShaderCompatibilityException("Unsupported final-pass texture uniforms " + unsupported + "; no safe Forge render target mapping is available");
      }
      return List.copyOf(samplers.values());
   }

   private static LegacyFinalShaderAdapter.TextureSemantic textureSemantic(String name) {
      if (COLOR_SAMPLERS.contains(name) || name.matches("colortex(?:[0-9]|1[0-5])")) {
         return LegacyFinalShaderAdapter.TextureSemantic.COLOR;
      }
      if (DEPTH_SAMPLERS.contains(name) || name.matches("(?:depthtex[0-2]|shadowtex[01])")) {
         return LegacyFinalShaderAdapter.TextureSemantic.DEPTH;
      }
      return "noisetex".equals(name) ? LegacyFinalShaderAdapter.TextureSemantic.NOISE_FALLBACK : null;
   }

   private static List<LegacyFinalShaderAdapter.UniformBinding> parseUniforms(String source) throws ShaderCompatibilityException {
      LinkedHashMap<String, LegacyFinalShaderAdapter.UniformBinding> uniforms = new LinkedHashMap<>();
      List<String> unsupported = new ArrayList<>();
      Matcher matcher = UNIFORM.matcher(source);

      while (matcher.find()) {
         String type = matcher.group(1);
         String name = matcher.group(2);
         if (!type.startsWith("sampler")) {
            LegacyFinalShaderAdapter.UniformSemantic semantic = UNIFORM_SEMANTICS.get(name);
            if (semantic == null) {
               unsupported.add(type + " " + name);
               continue;
            }

            if (!semantic.type().glsl().equals(type)) {
               throw new ShaderCompatibilityException("Uniform '" + name + "' must be declared as " + semantic.type().glsl() + ", not " + type);
            }

            uniforms.putIfAbsent(name, new LegacyFinalShaderAdapter.UniformBinding(name, semantic));
         }
      }

      if (!unsupported.isEmpty()) {
         throw new ShaderCompatibilityException("Unsupported final-pass uniforms " + unsupported + "; Oculus will not substitute incorrect defaults");
      }
      return List.copyOf(uniforms.values());
   }

   private static void validateVaryings(String source, Map<String, String> vertexOutputs) throws ShaderCompatibilityException {
      Matcher matcher = VARYING_DECLARATION.matcher(source);

      while (matcher.find()) {
         String type = matcher.group(1);
         String name = matcher.group(2);
         String vertexType = vertexOutputs.get(name);
         if (!type.equals(vertexType)) {
            throw new ShaderCompatibilityException("Fragment varying '" + name + "' (" + type + ") has no matching final.vsh output");
         }
      }
   }

   private static void validateNoUnmappedUniforms(String source, List<LegacyFinalShaderAdapter.SamplerBinding> samplers) throws ShaderCompatibilityException {
      String withoutSamplerDeclarations = source;

      for (LegacyFinalShaderAdapter.SamplerBinding sampler : samplers) {
         withoutSamplerDeclarations = withoutSamplerDeclarations.replaceAll(
            "(?m)^\\s*uniform\\s+sampler2D\\s+" + Pattern.quote(sampler.name()) + "\\s*;\\s*(?://.*)?$", ""
         );
      }

      Matcher remaining = ANY_UNIFORM_DECLARATION.matcher(withoutComments(withoutSamplerDeclarations));
      if (remaining.find()) {
         throw new ShaderCompatibilityException("Unsupported uniform declaration in final.fsh: " + remaining.group().trim());
      }
   }

   private static String removeLooseUniforms(String source, List<LegacyFinalShaderAdapter.UniformBinding> uniforms) {
      if (uniforms.isEmpty()) {
         return source;
      } else {
         Set<String> names = uniforms.stream().map(LegacyFinalShaderAdapter.UniformBinding::name).collect(Collectors.toSet());
         Matcher matcher = UNIFORM.matcher(source);
         StringBuilder result = new StringBuilder(source.length());

         while (matcher.find()) {
            if (names.contains(matcher.group(2))) {
               matcher.appendReplacement(result, "");
            }
         }

         matcher.appendTail(result);
         return result.toString();
      }
   }

   private static String adaptDepthSampling(String source, List<LegacyFinalShaderAdapter.SamplerBinding> samplers) {
      String adapted = source;

      for (LegacyFinalShaderAdapter.SamplerBinding sampler : samplers) {
         if (sampler.texture() == LegacyFinalShaderAdapter.TextureSemantic.DEPTH) {
            String name = Pattern.quote(sampler.name());
            adapted = adapted.replaceAll("\\btexture2DLod\\s*\\(\\s*" + name + "\\s*,", "oculus_textureDepthLod(" + sampler.name() + ",");
            adapted = adapted.replaceAll("\\btexture2D\\s*\\(\\s*" + name + "\\s*,", "oculus_textureDepth(" + sampler.name() + ",");
            adapted = adapted.replaceAll("\\btextureLod\\s*\\(\\s*" + name + "\\s*,", "oculus_textureDepthLod(" + sampler.name() + ",");
            adapted = adapted.replaceAll("\\btexture\\s*\\(\\s*" + name + "\\s*,", "oculus_textureDepth(" + sampler.name() + ",");
         }
      }

      return adapted;
   }

   private static String declarations(List<LegacyFinalShaderAdapter.UniformBinding> uniforms, boolean needsLegacyOutput, boolean samplesDepth) {
      StringBuilder result = new StringBuilder();
      if (needsLegacyOutput) {
         result.append("out vec4 oculus_FragColor;\n");
      }

      if (samplesDepth) {
         result.append(
            "vec4 oculus_reverseDepth(vec4 sampleValue) {\n    sampleValue.r = 1.0 - sampleValue.r;\n    return sampleValue;\n}\nvec4 oculus_textureDepth(sampler2D image, vec2 uv) {\n    return oculus_reverseDepth(texture(image, uv));\n}\nvec4 oculus_textureDepth(sampler2D image, vec2 uv, float bias) {\n    return oculus_reverseDepth(texture(image, uv, bias));\n}\nvec4 oculus_textureDepthLod(sampler2D image, vec2 uv, float lod) {\n    return oculus_reverseDepth(textureLod(image, uv, lod));\n}\n"
         );
      }

      if (!uniforms.isEmpty()) {
         result.append("layout(std140) uniform OculusLegacyUniforms {\n");

         for (LegacyFinalShaderAdapter.UniformBinding uniform : uniforms) {
            result.append("    ").append(uniform.semantic().type().glsl()).append(' ').append(uniform.name()).append(";\n");
         }

         result.append("};\n");
      }

      return result.toString();
   }

   private static String withoutComments(String source) {
      StringBuilder output = new StringBuilder(source.length());
      boolean line = false;
      boolean block = false;

      for (int index = 0; index < source.length(); index++) {
         char current = source.charAt(index);
         char next = index + 1 < source.length() ? source.charAt(index + 1) : 0;
         if (line) {
            output.append(current == '\n' ? '\n' : ' ');
            if (current == '\n') {
               line = false;
            }
         } else if (block) {
            output.append(current == '\n' ? '\n' : ' ');
            if (current == '*' && next == '/') {
               output.append(' ');
               index++;
               block = false;
            }
         } else if (current == '/' && next == '/') {
            output.append("  ");
            index++;
            line = true;
         } else if (current == '/' && next == '*') {
            output.append("  ");
            index++;
            block = true;
         } else {
            output.append(current);
         }
      }

      return output.toString();
   }

   public static record AdaptedShader(
      String source, List<LegacyFinalShaderAdapter.SamplerBinding> samplers, List<LegacyFinalShaderAdapter.UniformBinding> uniforms
   ) {
      public AdaptedShader(String source, List<LegacyFinalShaderAdapter.SamplerBinding> samplers, List<LegacyFinalShaderAdapter.UniformBinding> uniforms) {
         samplers = List.copyOf(samplers);
         uniforms = List.copyOf(uniforms);
         this.source = source;
         this.samplers = samplers;
         this.uniforms = uniforms;
      }
   }

   public static record SamplerBinding(String name, LegacyFinalShaderAdapter.TextureSemantic texture) {
   }

   public static enum TextureSemantic {
      COLOR,
      DEPTH,
      NOISE_FALLBACK;
   }

   public static record UniformBinding(String name, LegacyFinalShaderAdapter.UniformSemantic semantic) {
   }

   public static enum UniformSemantic {
      VIEW_WIDTH(LegacyFinalShaderAdapter.UniformType.FLOAT),
      VIEW_HEIGHT(LegacyFinalShaderAdapter.UniformType.FLOAT),
      ASPECT_RATIO(LegacyFinalShaderAdapter.UniformType.FLOAT),
      FRAME_TIME(LegacyFinalShaderAdapter.UniformType.FLOAT),
      FRAME_TIME_COUNTER(LegacyFinalShaderAdapter.UniformType.FLOAT),
      FRAME_COUNTER(LegacyFinalShaderAdapter.UniformType.INT),
      WORLD_TIME(LegacyFinalShaderAdapter.UniformType.INT),
      WORLD_DAY(LegacyFinalShaderAdapter.UniformType.INT),
      RAIN_STRENGTH(LegacyFinalShaderAdapter.UniformType.FLOAT),
      WETNESS(LegacyFinalShaderAdapter.UniformType.FLOAT),
      EYE_ALTITUDE(LegacyFinalShaderAdapter.UniformType.FLOAT),
      NEAR(LegacyFinalShaderAdapter.UniformType.FLOAT),
      FAR(LegacyFinalShaderAdapter.UniformType.FLOAT),
      SUN_ANGLE(LegacyFinalShaderAdapter.UniformType.FLOAT),
      SCREEN_BRIGHTNESS(LegacyFinalShaderAdapter.UniformType.FLOAT),
      IS_EYE_IN_WATER(LegacyFinalShaderAdapter.UniformType.INT),
      CAMERA_POSITION(LegacyFinalShaderAdapter.UniformType.VEC3),
      PREVIOUS_CAMERA_POSITION(LegacyFinalShaderAdapter.UniformType.VEC3),
      FOG_COLOR(LegacyFinalShaderAdapter.UniformType.VEC3),
      SKY_COLOR(LegacyFinalShaderAdapter.UniformType.VEC3),
      NIGHT_VISION(LegacyFinalShaderAdapter.UniformType.FLOAT),
      CAMERA_POSITION_INT(LegacyFinalShaderAdapter.UniformType.IVEC3),
      CAMERA_POSITION_FRACT(LegacyFinalShaderAdapter.UniformType.VEC3),
      PREVIOUS_CAMERA_POSITION_FRACT(LegacyFinalShaderAdapter.UniformType.VEC3),
      RAIN_FACTOR(LegacyFinalShaderAdapter.UniformType.FLOAT),
      IN_BASALT_DELTAS(LegacyFinalShaderAdapter.UniformType.FLOAT),
      IN_CRIMSON_FOREST(LegacyFinalShaderAdapter.UniformType.FLOAT),
      IN_NETHER_WASTES(LegacyFinalShaderAdapter.UniformType.FLOAT),
      IN_SOUL_VALLEY(LegacyFinalShaderAdapter.UniformType.FLOAT),
      IN_WARPED_FOREST(LegacyFinalShaderAdapter.UniformType.FLOAT);

      private final LegacyFinalShaderAdapter.UniformType type;

      private UniformSemantic(LegacyFinalShaderAdapter.UniformType type) {
         this.type = type;
      }

      public LegacyFinalShaderAdapter.UniformType type() {
         return this.type;
      }
   }

   public static enum UniformType {
      FLOAT("float"),
      INT("int"),
      VEC3("vec3"),
      IVEC3("ivec3");

      private final String glsl;

      private UniformType(String glsl) {
         this.glsl = glsl;
      }

      public String glsl() {
         return this.glsl;
      }
   }
}
