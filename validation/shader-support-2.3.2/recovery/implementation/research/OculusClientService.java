// Source: https://raw.githubusercontent.com/Khaliiid0/Oculus-Community-Port/main/src/main/java/dev/oculusforge/client/OculusClientService.java
package dev.oculusforge.client;

import com.mojang.blaze3d.systems.GpuDevice;
import com.mojang.blaze3d.systems.RenderSystem;
import dev.oculusforge.OculusForge;
import dev.oculusforge.config.OculusConfig;
import dev.oculusforge.config.OculusConfigStore;
import dev.oculusforge.render.BackendPolicy;
import dev.oculusforge.render.GraphicsApi;
import dev.oculusforge.render.pipeline.OculusCompositePass;
import dev.oculusforge.render.pipeline.ShaderPipelineManager;
import dev.oculusforge.render.program.PackProgramCompiler;
import dev.oculusforge.render.program.PreparedPackPipeline;
import dev.oculusforge.shaderpack.ShaderPackDescriptor;
import dev.oculusforge.shaderpack.ShaderPackLimits;
import dev.oculusforge.shaderpack.ShaderPackRepository;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.components.toasts.SystemToast.SystemToastId;
import net.minecraft.network.chat.Component;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLPaths;

public final class OculusClientService {
   private static final boolean PIPELINE_AVAILABLE = true;
   private static final SystemToastId BACKEND_WARNING_TOAST = new SystemToastId(10000L);
   private final OculusConfigStore configStore;
   private final ShaderPackRepository repository;
   private final PackProgramCompiler compiler;
   private final ShaderPipelineManager pipelineManager;
   private final OculusCompositePass compositePass;
   private final AtomicReference<ShaderPackRepository.DiscoveryResult> discovery = new AtomicReference<>(
      new ShaderPackRepository.DiscoveryResult(List.of(), List.of())
   );
   private volatile OculusConfig config = OculusConfig.DEFAULT;
   private volatile OculusClientService.ReloadPreparation lastPreparation;
   private volatile PreparedPackPipeline lastKnownGoodPreparation;
   private volatile GraphicsApi graphicsApi = GraphicsApi.UNKNOWN;
   private volatile String backendName = "not initialized";
   private volatile BackendPolicy.Decision decision = BackendPolicy.Decision.DISABLE_UNKNOWN_BACKEND;

   private OculusClientService(OculusConfigStore configStore, ShaderPackRepository repository, PackProgramCompiler compiler) {
      this.configStore = configStore;
      this.repository = repository;
      this.compiler = compiler;
      this.pipelineManager = new ShaderPipelineManager(compiler);
      this.compositePass = new OculusCompositePass(this.pipelineManager);
   }

   public static OculusClientService createDefault() {
      OculusConfigStore configStore = new OculusConfigStore(FMLPaths.CONFIGDIR.get().resolve("oculus.properties"));
      OculusClientService service = new OculusClientService(
         configStore,
         new ShaderPackRepository(FMLPaths.GAMEDIR.get().resolve("shaderpacks"), ShaderPackLimits.DEFAULT),
         new PackProgramCompiler(ShaderPackLimits.DEFAULT)
      );

      try {
         service.config = configStore.load();
      } catch (IOException var3) {
         OculusForge.LOGGER.error("Could not load Oculus configuration from {}", configStore.path(), var3);
      }

      return service;
   }

   public void initializeOnRenderThread() {
      RenderSystem.assertOnRenderThread();
      GpuDevice device = RenderSystem.tryGetDevice();
      this.backendName = device == null ? "unavailable" : device.getDeviceInfo().backendName();
      this.graphicsApi = GraphicsApi.fromBackendName(this.backendName);
      boolean optiFinePresent = detectOptiFine();
      this.decision = BackendPolicy.evaluate(this.graphicsApi, optiFinePresent, true);
      OculusForge.LOGGER
         .info("Oculus graphics backend: {} ({}); shader pipeline decision: {}", new Object[]{this.backendName, this.graphicsApi, this.decision});
      if (this.config.shadersEnabled() && this.decision != BackendPolicy.Decision.ALLOW_OPENGL_PIPELINE) {
         this.config = this.config.withShadersEnabled(false);
         this.saveConfig();
         OculusForge.LOGGER.warn("Disabled configured shader rendering because the backend policy is {}", this.decision);
      }

      if (this.decision != BackendPolicy.Decision.ALLOW_OPENGL_PIPELINE) {
         this.showSafetyWarning();
      }

      if (this.lastPreparation != null) {
         this.reconcilePipeline(this.lastPreparation);
      }
   }

   private static boolean detectOptiFine() {
      if (!ModList.isLoaded("optifine") && !ModList.isLoaded("optiforge")) {
         try {
            Class.forName("net.optifine.Config", false, OculusClientService.class.getClassLoader());
            return true;
         } catch (ClassNotFoundException var1) {
            return false;
         } catch (LinkageError var2) {
            OculusForge.LOGGER.warn("OptiFine class probe encountered an incompatible class", var2);
            return true;
         }
      } else {
         return true;
      }
   }

   private void showSafetyWarning() {
      Minecraft minecraft = Minecraft.getInstance();

      Component detail = switch (this.decision) {
         case DISABLE_VULKAN_UNSUPPORTED -> Component.translatable("oculus.warning.vulkan");
         case DISABLE_OPTIFINE_CONFLICT -> Component.translatable("oculus.warning.optifine");
         case DISABLE_PIPELINE_NOT_IMPLEMENTED -> Component.translatable("oculus.warning.pipeline_unavailable");
         case DISABLE_UNKNOWN_BACKEND -> Component.translatable("oculus.warning.unknown");
         case ALLOW_OPENGL_PIPELINE -> null;
      };
      if (detail != null) {
         SystemToast.add(minecraft.gui.toastManager(), BACKEND_WARNING_TOAST, Component.translatable("oculus.warning.title"), detail);
      }
   }

   public ShaderPackRepository.DiscoveryResult discover() {
      try {
         return this.repository.discover();
      } catch (IOException var2) {
         OculusForge.LOGGER.error("Could not scan shader-pack directory {}", this.repository.directory(), var2);
         return new ShaderPackRepository.DiscoveryResult(
            List.of(), List.of(new ShaderPackRepository.DiscoveryIssue(this.repository.directory(), var2.getMessage(), null))
         );
      }
   }

   public void applyDiscovery(ShaderPackRepository.DiscoveryResult result) {
      this.discovery.set(result);
      if (!result.issues().isEmpty()) {
         OculusForge.LOGGER.warn("Shader-pack scan found {} valid pack(s) and {} rejected entry/entries", result.packs().size(), result.issues().size());
         result.issues().forEach(issue -> OculusForge.LOGGER.warn("Rejected shader-pack entry {}: {}", issue.path(), issue.message()));
      }
   }

   public OculusClientService.ReloadPreparation prepareReload() {
      ShaderPackRepository.DiscoveryResult result = this.discover();
      OculusConfig snapshot = this.config;
      if (snapshot.shadersEnabled() && !snapshot.selectedPack().isBlank()) {
         ShaderPackDescriptor selected = result.packs().stream().filter(pack -> pack.id().equals(snapshot.selectedPack())).findFirst().orElse(null);
         if (selected == null) {
            return new OculusClientService.ReloadPreparation(result, null, "Selected shader pack '" + snapshot.selectedPack() + "' was not found");
         } else {
            try {
               return new OculusClientService.ReloadPreparation(result, this.compiler.prepare(selected), "");
            } catch (Exception var6) {
               OculusForge.LOGGER.error("Could not prepare shader pack '{}'", selected.displayName(), var6);
               String message = var6.getMessage() == null ? var6.getClass().getSimpleName() : var6.getMessage();
               return new OculusClientService.ReloadPreparation(result, null, message);
            }
         }
      } else {
         return new OculusClientService.ReloadPreparation(result, null, "");
      }
   }

   public void applyReload(OculusClientService.ReloadPreparation preparation) {
      RenderSystem.assertOnRenderThread();
      this.applyDiscovery(preparation.discovery());
      this.lastPreparation = preparation;
      this.reconcilePipeline(preparation);
   }

   private void reconcilePipeline(OculusClientService.ReloadPreparation preparation) {
      RenderSystem.assertOnRenderThread();
      if (!this.config.shadersEnabled() || this.decision != BackendPolicy.Decision.ALLOW_OPENGL_PIPELINE) {
         this.pipelineManager.deactivate();
      } else if (preparation == null) {
         this.restoreOrReject("Shader-pack resources have not been prepared yet");
      } else if (!preparation.error().isBlank()) {
         this.restoreOrReject(preparation.error());
      } else if (preparation.program() != null && preparation.program().pack().id().equals(this.config.selectedPack())) {
         if (this.pipelineManager.activate(preparation.program())) {
            this.lastKnownGoodPreparation = preparation.program();
         } else {
            this.restoreOrReject(this.pipelineManager.lastError());
         }
      } else {
         this.restoreOrReject("Prepared shader pack does not match the current selection");
      }
   }

   private void restoreOrReject(String message) {
      PreparedPackPipeline fallback = this.lastKnownGoodPreparation;
      if (fallback == null) {
         this.pipelineManager.reject(message);
      } else {
         this.pipelineManager.restore(fallback, message);
      }
   }

   public CompletableFuture<ShaderPackRepository.DiscoveryResult> refreshAsync() {
      return CompletableFuture.supplyAsync(this::discover).thenApply(result -> {
         this.applyDiscovery(result);
         return result;
      });
   }

   public CompletableFuture<Void> applySelection(String packId, boolean enabled) {
      RenderSystem.assertOnRenderThread();
      boolean effectiveEnabled = enabled && !packId.isBlank() && this.shaderPipelineAvailable();
      this.config = this.config.withSelectedPack(packId).withShadersEnabled(effectiveEnabled);
      this.saveConfig();
      if (!effectiveEnabled) {
         this.pipelineManager.deactivate();
      }
      this.lastPreparation = null;
      return Minecraft.getInstance().reloadResourcePacks();
   }

   private void saveConfig() {
      try {
         this.configStore.save(this.config);
      } catch (IOException var2) {
         OculusForge.LOGGER.error("Could not save Oculus configuration to {}", this.configStore.path(), var2);
      }
   }

   public OculusConfig config() {
      return this.config;
   }

   public ShaderPackRepository.DiscoveryResult discovery() {
      return this.discovery.get();
   }

   public GraphicsApi graphicsApi() {
      return this.graphicsApi;
   }

   public String backendName() {
      return this.backendName;
   }

   public BackendPolicy.Decision decision() {
      return this.decision;
   }

   public boolean shaderPipelineAvailable() {
      return this.decision == BackendPolicy.Decision.ALLOW_OPENGL_PIPELINE;
   }

   public boolean shadersActive() {
      return this.config.shadersEnabled() && this.pipelineManager.state() == ShaderPipelineManager.State.READY;
   }

   public String activePackId() {
      return this.pipelineManager.activePackId();
   }

   public ShaderPipelineManager.State pipelineState() {
      return this.pipelineManager.state();
   }

   public String pipelineError() {
      return this.pipelineManager.lastError();
   }

   public OculusCompositePass compositePass() {
      return this.compositePass;
   }

   public void close() {
      RenderSystem.assertOnRenderThread();
      this.pipelineManager.close();
   }

   public static record ReloadPreparation(ShaderPackRepository.DiscoveryResult discovery, PreparedPackPipeline program, String error) {
   }
}
