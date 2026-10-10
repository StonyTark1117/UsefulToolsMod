package dev.oculusforge.shaderpack;

import java.util.Map;

public final class ShaderMacroExpander {
   private static final int MAX_PASSES = 32;

   private ShaderMacroExpander() {
   }

   public static String expand(String source, Map<String, String> macros) {
      String current = source;

      for (int pass = 0; pass < 32; pass++) {
         String next = expandOnce(current, macros);
         if (next.equals(current)) {
            return next;
         }

         current = next;
      }

      throw new IllegalArgumentException("Macro expansion did not converge within 32 passes");
   }

   private static String expandOnce(String source, Map<String, String> macros) {
      StringBuilder output = new StringBuilder(source.length());
      int index = 0;
      boolean lineComment = false;
      boolean blockComment = false;
      char quote = 0;

      while (index < source.length()) {
         char current = source.charAt(index);
         char next = index + 1 < source.length() ? source.charAt(index + 1) : 0;
         if (lineComment) {
            output.append(current);
            index++;
            if (current == '\n') {
               lineComment = false;
            }
         } else if (blockComment) {
            output.append(current);
            index++;
            if (current == '*' && next == '/') {
               output.append(next);
               index++;
               blockComment = false;
            }
         } else if (quote != 0) {
            output.append(current);
            if (current == '\\' && ++index < source.length()) {
               output.append(source.charAt(index++));
            } else if (current == quote) {
               quote = 0;
            }
         } else if (current == '/' && next == '/') {
            output.append("//");
            index += 2;
            lineComment = true;
         } else if (current == '/' && next == '*') {
            output.append("/*");
            index += 2;
            blockComment = true;
         } else if (current != '"' && current != '\'') {
            if (!Character.isLetter(current) && current != '_') {
               output.append(current);
               index++;
            } else {
               int end = index + 1;

               while (true) {
                  if (end < source.length()) {
                     char token = source.charAt(end);
                     if (Character.isLetterOrDigit(token) || token == '_') {
                        end++;
                        continue;
                     }
                  }

                  String identifier = source.substring(index, end);
                  output.append(macros.getOrDefault(identifier, identifier));
                  index = end;
                  break;
               }
            }
         } else {
            quote = current;
            output.append(current);
            index++;
         }
      }

      return output.toString();
   }
}
