// Source: https://raw.githubusercontent.com/Khaliiid0/Oculus-Community-Port/main/src/main/java/dev/oculusforge/render/pipeline/ShaderPipelineManager.java
package dev.oculusforge.render.pipeline;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.oculusforge.OculusForge;
import dev.oculusforge.render.program.PackProgramCompiler;
import dev.oculusforge.render.program.PackShaderPipeline;
import dev.oculusforge.render.program.PreparedPackPipeline;
import dev.oculusforge.render.program.ShaderCompatibilityException;

public final class ShaderPipelineManager implements AutoCloseable {
   private final PackProgramCompiler compiler;
   private volatile PackShaderPipeline active;
   private volatile ShaderPipelineManager.State state = ShaderPipelineManager.State.DISABLED;
   private volatile String lastError = "";

   public ShaderPipelineManager(PackProgramCompiler compiler) {
      this.compiler = compiler;
   }

   public PackShaderPipeline activePipeline() {
      return this.active;
   }

   public ShaderPipelineManager.State state() {
      return this.state;
   }

   public String lastError() {
      return this.lastError;
   }

   public String activePackId() {
      PackShaderPipeline pipeline = this.active;
      return pipeline == null ? "" : pipeline.packId();
   }

   public boolean activate(PreparedPackPipeline prepared) {
      RenderSystem.assertOnRenderThread();

      try {
         PackShaderPipeline replacement = this.compiler.compile(prepared);
         PackShaderPipeline previous = this.active;
         this.active = replacement;
         this.state = ShaderPipelineManager.State.READY;
         this.lastError = "";
         closeSuperseded(previous);
         OculusForge.LOGGER
            .info("Activated shader pack '{}' with {} Forge frame-graph full-screen stage(s)", prepared.pack().displayName(), prepared.programs().size());
         return true;
      } catch (RuntimeException | ShaderCompatibilityException var3) {
         this.keepPreviousOrFail("Could not activate shader pack '" + prepared.pack().displayName() + "'", var3);
         return false;
      }
   }

   public boolean restore(PreparedPackPipeline prepared, String replacementError) {
      RenderSystem.assertOnRenderThread();

      try {
         PackShaderPipeline replacement = this.compiler.compile(prepared);
         PackShaderPipeline previous = this.active;
         this.active = replacement;
         this.state = ShaderPipelineManager.State.READY;
         this.lastError = replacementError;
         closeSuperseded(previous);
         OculusForge.LOGGER.warn("Recompiled and restored last-known-good shader pack '{}' after replacement failure: {}", prepared.pack().displayName(), replacementError);
         return true;
      } catch (RuntimeException | ShaderCompatibilityException exception) {
         PackShaderPipeline previous = this.active;
         this.active = null;
         closeSuperseded(previous);
         String detail = exception.getMessage() == null ? exception.getClass().getSimpleName() : exception.getMessage();
         this.state = ShaderPipelineManager.State.ERROR;
         this.lastError = replacementError + "; last-known-good restore also failed: " + detail;
         OculusForge.LOGGER.error("{}; vanilla rendering has been restored", this.lastError, exception);
         return false;
      }
   }

   public void deactivate() {
      RenderSystem.assertOnRenderThread();
      PackShaderPipeline previous = this.active;
      this.active = null;
      closeSuperseded(previous);

      this.state = ShaderPipelineManager.State.DISABLED;
      this.lastError = "";
   }

   public void reject(String message) {
      RenderSystem.assertOnRenderThread();
      PackShaderPipeline previous = this.active;
      this.active = null;
      closeSuperseded(previous);
      this.lastError = message;
      this.state = ShaderPipelineManager.State.ERROR;
      OculusForge.LOGGER.error("Shader pack was not activated: {}; vanilla rendering remains active", message);
   }

   public void failRuntime(RuntimeException exception) {
      RenderSystem.assertOnRenderThread();
      String message = exception.getMessage() == null ? exception.getClass().getSimpleName() : exception.getMessage();
      PackShaderPipeline previous = this.active;
      this.active = null;
      closeSuperseded(previous);

      this.state = ShaderPipelineManager.State.ERROR;
      this.lastError = "The shader pass failed at runtime: " + message;
      OculusForge.LOGGER.error("{} Vanilla rendering has been restored.", this.lastError, exception);
   }

   private void keepPreviousOrFail(String context, Exception exception) {
      String detail = exception.getMessage() == null ? exception.getClass().getSimpleName() : exception.getMessage();
      this.lastError = context + ": " + detail;
      if (this.active == null) {
         this.state = ShaderPipelineManager.State.ERROR;
         OculusForge.LOGGER.error("{}; vanilla rendering remains active", this.lastError, exception);
      } else {
         this.state = ShaderPipelineManager.State.READY;
         OculusForge.LOGGER.error("{}; keeping '{}' active", new Object[]{this.lastError, this.active.packId(), exception});
      }
   }

   private static void closeSuperseded(PackShaderPipeline pipeline) {
      if (pipeline != null) {
         try {
            pipeline.close();
         } catch (RuntimeException exception) {
            OculusForge.LOGGER.warn("Could not fully release a superseded shader pipeline", exception);
         }
      }
   }

   @Override
   public void close() {
      RenderSystem.assertOnRenderThread();
      this.deactivate();
   }

   public static enum State {
      DISABLED,
      READY,
      ERROR;
   }
}
