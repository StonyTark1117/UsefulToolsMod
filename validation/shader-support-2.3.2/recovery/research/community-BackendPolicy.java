// Source: https://raw.githubusercontent.com/Khaliiid0/Oculus-Community-Port/main/src/main/java/dev/oculusforge/render/BackendPolicy.java
package dev.oculusforge.render;

public final class BackendPolicy {
   private BackendPolicy() {
   }

   public static BackendPolicy.Decision evaluate(GraphicsApi api, boolean optiFinePresent, boolean pipelineAvailable) {
      if (optiFinePresent) {
         return BackendPolicy.Decision.DISABLE_OPTIFINE_CONFLICT;
      } else if (api == GraphicsApi.VULKAN) {
         return BackendPolicy.Decision.DISABLE_VULKAN_UNSUPPORTED;
      } else if (api != GraphicsApi.OPENGL) {
         return BackendPolicy.Decision.DISABLE_UNKNOWN_BACKEND;
      } else {
         return pipelineAvailable ? BackendPolicy.Decision.ALLOW_OPENGL_PIPELINE : BackendPolicy.Decision.DISABLE_PIPELINE_NOT_IMPLEMENTED;
      }
   }

   public static enum Decision {
      ALLOW_OPENGL_PIPELINE,
      DISABLE_VULKAN_UNSUPPORTED,
      DISABLE_UNKNOWN_BACKEND,
      DISABLE_OPTIFINE_CONFLICT,
      DISABLE_PIPELINE_NOT_IMPLEMENTED;
   }
}
