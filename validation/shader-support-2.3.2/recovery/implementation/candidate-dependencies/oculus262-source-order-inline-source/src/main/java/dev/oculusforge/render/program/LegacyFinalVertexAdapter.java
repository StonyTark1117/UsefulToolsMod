package dev.oculusforge.render.program;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class LegacyFinalVertexAdapter {
   private static final Pattern VERSION = Pattern.compile("(?m)^\\s*#\\s*version\\s+\\d+(?:\\s+\\w+)?\\s*(?://.*)?$");
   private static final Pattern MAIN = Pattern.compile("\\bvoid\\s+main\\s*\\(\\s*(?:void\\s*)?\\)");
   private static final Pattern VARYING = Pattern.compile("(?m)^\\s*varying\\s+([A-Za-z_][A-Za-z0-9_]*)\\s+([A-Za-z_][A-Za-z0-9_]*)\\s*;\\s*(?://.*)?$");
   private static final Pattern UNIFORM = Pattern.compile("(?m)^\\s*uniform\\b[^\\n;{]*(?:;|\\{)");
   private static final Pattern ATTRIBUTE = Pattern.compile("(?m)^\\s*attribute\\b");
   private static final Pattern LEGACY_BUILTIN = Pattern.compile("\\bgl_([A-Za-z_][A-Za-z0-9_]*)");
   public static final String DEFAULT_SOURCE = "#version 330 core\nout vec2 texcoord;\nout vec2 texCoord;\nvoid main() {\n    vec2 uv = vec2((gl_VertexID << 1) & 2, gl_VertexID & 2);\n    gl_Position = vec4(uv * 2.0 - 1.0, 0.0, 1.0);\n    texcoord = uv;\n    texCoord = uv;\n}\n";
   public static final Map<String, String> DEFAULT_OUTPUTS = Map.of("texcoord", "vec2", "texCoord", "vec2");

   public LegacyFinalVertexAdapter.AdaptedVertex adapt(String input) throws ShaderCompatibilityException {
      if (input != null && !input.isBlank()) {
         String source = input.replace("\r\n", "\n").replace('\r', '\n');
         if (!source.isEmpty() && source.charAt(0) == '\ufeff') {
            source = source.substring(1);
         }

         String inspected = withoutComments(source);
         if (UNIFORM.matcher(inspected).find()) {
            throw new ShaderCompatibilityException("final.vsh declares uniforms that are not yet representable by the fullscreen vertex bridge");
         } else if (ATTRIBUTE.matcher(inspected).find()) {
            throw new ShaderCompatibilityException("final.vsh declares custom attributes, but the Forge fullscreen pass has no vertex buffer");
         } else {
            LinkedHashMap<String, String> outputs = new LinkedHashMap<>();
            Matcher varying = VARYING.matcher(source);

            while (varying.find()) {
               outputs.putIfAbsent(varying.group(2), varying.group(1));
            }

            source = source.replaceAll("\\bvarying\\b", "out");
            source = source.replaceAll("\\bftransform\\s*\\(\\s*\\)", "oculus_position");
            source = source.replaceAll("\\bgl_MultiTexCoord0\\b", "vec4(oculus_uv, 0.0, 1.0)");
            source = source.replaceAll("\\bgl_Vertex\\b", "oculus_position");
            Matcher builtin = LEGACY_BUILTIN.matcher(withoutComments(source));

            while (builtin.find()) {
               if (!"Position".equals(builtin.group(1))) {
                  throw new ShaderCompatibilityException("Unsupported fixed-function vertex built-in gl_" + builtin.group(1));
               }
            }

            Matcher main = MAIN.matcher(source);
            if (!main.find()) {
               throw new ShaderCompatibilityException("final.vsh has no main function");
            } else {
               source = main.replaceFirst("void oculus_pack_main()");
               String bridgeDeclarations = "vec2 oculus_uv;\nvec4 oculus_position;\n";
               Matcher version = VERSION.matcher(source);
               if (version.find()) {
                  source = version.replaceFirst(Matcher.quoteReplacement("#version 330 core\n" + bridgeDeclarations));
               } else {
                  source = "#version 330 core\n" + bridgeDeclarations + source;
               }

               source = source
                  + "\nvoid main() {\n    vec2 uv = vec2((gl_VertexID << 1) & 2, gl_VertexID & 2);\n    oculus_uv = uv;\n    oculus_position = vec4(uv * 2.0 - 1.0, 0.0, 1.0);\n    oculus_pack_main();\n}\n";
               return new LegacyFinalVertexAdapter.AdaptedVertex(source, outputs);
            }
         }
      } else {
         throw new ShaderCompatibilityException("final.vsh is empty");
      }
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

   public static record AdaptedVertex(String source, Map<String, String> outputs) {
      public AdaptedVertex(String source, Map<String, String> outputs) {
         outputs = Map.copyOf(outputs);
         this.source = source;
         this.outputs = outputs;
      }
   }
}
