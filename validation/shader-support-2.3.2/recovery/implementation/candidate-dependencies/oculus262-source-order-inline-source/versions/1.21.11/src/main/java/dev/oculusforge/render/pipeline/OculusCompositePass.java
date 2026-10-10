package dev.oculusforge.render.pipeline;

import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.framegraph.FramePass;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.resource.RenderTargetDescriptor;
import com.mojang.blaze3d.resource.ResourceHandle;
import com.mojang.blaze3d.systems.CommandEncoder;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.FilterMode;
import com.mojang.blaze3d.textures.GpuSampler;
import com.mojang.blaze3d.textures.GpuTexture;
import com.mojang.blaze3d.textures.GpuTextureView;
import dev.oculusforge.OculusForge;
import dev.oculusforge.render.program.LegacyFinalShaderAdapter;
import dev.oculusforge.render.program.PackFinalProgram;
import dev.oculusforge.render.program.PackShaderPipeline;
import java.util.List;
import java.util.Objects;
import java.util.OptionalInt;
import net.minecraft.client.renderer.LevelTargetBundle;
import net.minecraft.client.renderer.state.LevelRenderState;
import net.minecraft.client.DeltaTracker;
import net.minecraftforge.client.FramePassManager.PassDefinition;

public final class OculusCompositePass implements PassDefinition {
   private final ShaderPipelineManager manager;
   private ResourceHandle<RenderTarget> mainHandle = ResourceHandle.invalid();
   private ResourceHandle<RenderTarget> copyHandle = ResourceHandle.invalid();
   private PackShaderPipeline extractedPipeline;
   private String lastLoggedPack = "";
   private RenderTargetDescriptor copyDescriptor;

   public OculusCompositePass(ShaderPipelineManager manager) {
      this.manager = manager;
   }

   @Override
   public void extracts(LevelTargetBundle bundle, FramePass pass, DeltaTracker deltaTracker) {
      RenderTarget mainTarget = bundle.main.get();
      bundle.main = pass.readsAndWrites(bundle.main);
      this.mainHandle = bundle.main;
      this.extractedPipeline = this.manager.activePipeline();
      if (this.extractedPipeline == null) {
         this.copyHandle = ResourceHandle.invalid();
      } else {
         Objects.requireNonNull(mainTarget.getColorTexture(), "Main color texture");
         this.copyHandle = pass.createsInternal("oculus_final_input", this.descriptorFor(mainTarget));
      }
   }

   public void executes(LevelRenderState state) {
      PackShaderPipeline pipeline = this.extractedPipeline;
      if (pipeline != null) {
         try {
            RenderTarget main = this.mainHandle.get();
            RenderTarget copy = this.copyHandle.get();
            GpuTexture mainColor = Objects.requireNonNull(main.getColorTexture(), "Main color texture");
            GpuTexture copyColor = Objects.requireNonNull(copy.getColorTexture(), "Oculus input color texture");
            GpuTextureView mainColorView = Objects.requireNonNull(main.getColorTextureView(), "Main color texture view");
            GpuTextureView copyColorView = Objects.requireNonNull(copy.getColorTextureView(), "Oculus input color texture view");
            List<PackFinalProgram> programs = pipeline.programs();
            CommandEncoder encoder = RenderSystem.getDevice().createCommandEncoder();

            for (int index = 0; index < programs.size(); index++) {
               boolean outputToCopy = (index & 1) == 0;
               GpuTextureView inputView = outputToCopy ? mainColorView : copyColorView;
               GpuTextureView outputView = outputToCopy ? copyColorView : mainColorView;
               executeProgram(encoder, programs.get(index), state, main, inputView, outputView);
            }

            if ((programs.size() & 1) != 0) {
               encoder.copyTextureToTexture(copyColor, mainColor, 0, 0, 0, 0, 0, main.width, main.height);
            }

            if (!pipeline.packId().equals(this.lastLoggedPack)) {
               this.lastLoggedPack = pipeline.packId();
               OculusForge.LOGGER
                  .info(
                     "Executed shader pack '{}' ({} full-screen stage(s)) in the Forge level frame graph at {}x{}",
                     new Object[]{pipeline.packId(), programs.size(), main.width, main.height}
                  );
            }
         } catch (RuntimeException var11) {
            this.manager.failRuntime(var11);
         }
      }
   }

   private static void executeProgram(
      CommandEncoder encoder,
      PackFinalProgram program,
      LevelRenderState state,
      RenderTarget main,
      GpuTextureView inputColorView,
      GpuTextureView outputColorView
   ) {
      boolean uniformFrameStarted = false;

      try {
         GpuBuffer legacyUniformBuffer = null;
         if (program.hasUniforms()) {
            legacyUniformBuffer = program.updateUniforms(state, main.width, main.height);
            uniformFrameStarted = true;
         }

         RenderPass renderPass = encoder.createRenderPass(
            () -> "Oculus shader-pack " + program.stageName() + " full-screen pass", outputColorView, OptionalInt.empty()
         );

         try {
            renderPass.setPipeline(program.pipeline());
            if (legacyUniformBuffer != null) {
               renderPass.setUniform("OculusLegacyUniforms", legacyUniformBuffer);
            }

            GpuSampler colorSampler = RenderSystem.getSamplerCache().getClampToEdge(FilterMode.LINEAR);
            GpuSampler depthSampler = RenderSystem.getSamplerCache().getClampToEdge(FilterMode.NEAREST);
            List<LegacyFinalShaderAdapter.SamplerBinding> samplers = program.samplers();

            for (int index = 0; index < samplers.size(); index++) {
               LegacyFinalShaderAdapter.SamplerBinding binding = samplers.get(index);
               switch (binding.texture()) {
                  case COLOR:
                     renderPass.bindTexture(binding.name(), inputColorView, colorSampler);
                     break;
                  case DEPTH:
                     renderPass.bindTexture(binding.name(), requireDepthView(main), depthSampler);
                     break;
                  case NOISE_FALLBACK:
                     renderPass.bindTexture(binding.name(), inputColorView, depthSampler);
               }
            }

            renderPass.draw(0, 3);
         } catch (Throwable var20) {
            if (renderPass != null) {
               try {
                  renderPass.close();
               } catch (Throwable var19) {
                  var20.addSuppressed(var19);
               }
            }

            throw var20;
         }

         if (renderPass != null) {
            renderPass.close();
         }
      } finally {
         if (uniformFrameStarted) {
            program.finishFrame();
         }
      }
   }

   private static GpuTextureView requireDepthView(RenderTarget target) {
      if (target.useDepth && target.getDepthTextureView() != null) {
         return target.getDepthTextureView();
      } else {
         throw new IllegalStateException("Shader pack requests depthtex0 but the main target has no depth texture");
      }
   }

   private RenderTargetDescriptor descriptorFor(RenderTarget target) {
      RenderTargetDescriptor descriptor = this.copyDescriptor;
      if (descriptor == null || descriptor.width() != target.width || descriptor.height() != target.height) {
         descriptor = new RenderTargetDescriptor(target.width, target.height, false, 0);
         this.copyDescriptor = descriptor;
      }

      return descriptor;
   }
}
