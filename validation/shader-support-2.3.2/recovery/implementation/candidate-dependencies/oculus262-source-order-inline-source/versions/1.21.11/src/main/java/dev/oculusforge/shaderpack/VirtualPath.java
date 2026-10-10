package dev.oculusforge.shaderpack;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;
import java.util.StringJoiner;

public final class VirtualPath implements Comparable<VirtualPath> {
   private final String value;

   private VirtualPath(String value) {
      this.value = value;
   }

   public static VirtualPath parse(String input) throws ShaderPackException {
      return normalize(input, false);
   }

   public static VirtualPath resolve(VirtualPath baseFile, String include) throws ShaderPackException {
      Objects.requireNonNull(baseFile, "baseFile");
      if (include == null || include.isBlank()) {
         throw invalid(include, "Shader path is empty");
      } else if (include.startsWith("/")) {
         return normalize("shaders/" + include.substring(1), true);
      } else {
         int separator = baseFile.value.lastIndexOf(47);
         String baseDirectory = separator < 0 ? "" : baseFile.value.substring(0, separator + 1);
         return normalize(baseDirectory + include, true);
      }
   }

   private static VirtualPath normalize(String input, boolean allowParentSegments) throws ShaderPackException {
      if (input == null || input.isBlank()) {
         throw invalid(input, "Shader path is empty");
      } else if (input.indexOf(0) < 0 && input.indexOf(92) < 0 && !input.startsWith("/") && !input.matches("^[A-Za-z]:.*") && !input.contains(":")) {
         Deque<String> normalized = new ArrayDeque<>();

         for (String segment : input.split("/", -1)) {
            if (!segment.isEmpty() && !".".equals(segment)) {
               if ("..".equals(segment)) {
                  if (!allowParentSegments || normalized.isEmpty()) {
                     throw invalid(input, "Shader path escapes the pack root");
                  }

                  normalized.removeLast();
               } else {
                  if (segment.chars().anyMatch(Character::isISOControl)) {
                     throw invalid(input, "Shader path contains a control character");
                  }

                  normalized.addLast(segment);
               }
            } else if (!allowParentSegments) {
               throw invalid(input, "Shader path contains an empty or dot segment");
            }
         }

         if (normalized.isEmpty()) {
            throw invalid(input, "Shader path resolves to the pack root");
         } else {
            StringJoiner joined = new StringJoiner("/");
            normalized.forEach(joined::add);
            return new VirtualPath(joined.toString());
         }
      } else {
         throw invalid(input, "Shader path must be relative and use forward slashes");
      }
   }

   private static ShaderPackException invalid(String input, String message) {
      return new ShaderPackException(ShaderPackException.Code.INVALID_PATH, message, input);
   }

   public boolean startsWith(String prefix) {
      return this.value.equals(prefix) || this.value.startsWith(prefix + "/");
   }

   public String fileName() {
      int separator = this.value.lastIndexOf(47);
      return separator < 0 ? this.value : this.value.substring(separator + 1);
   }

   public int compareTo(VirtualPath other) {
      return this.value.compareTo(other.value);
   }

   @Override
   public boolean equals(Object other) {
      if (other instanceof VirtualPath path && this.value.equals(path.value)) {
         return true;
      }

      return false;
   }

   @Override
   public int hashCode() {
      return this.value.hashCode();
   }

   @Override
   public String toString() {
      return this.value;
   }
}
