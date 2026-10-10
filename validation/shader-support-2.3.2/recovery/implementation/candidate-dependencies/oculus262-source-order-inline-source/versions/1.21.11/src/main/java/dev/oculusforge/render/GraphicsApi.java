package dev.oculusforge.render;

import java.util.Locale;

public enum GraphicsApi {
   OPENGL,
   VULKAN,
   UNKNOWN;

   public static GraphicsApi fromBackendName(String backendName) {
      if (backendName == null) {
         return UNKNOWN;
      } else {
         String normalized = backendName.toLowerCase(Locale.ROOT);
         if (normalized.contains("vulkan")) {
            return VULKAN;
         } else {
            return !normalized.contains("opengl") && !normalized.contains("open gl") ? UNKNOWN : OPENGL;
         }
      }
   }
}
