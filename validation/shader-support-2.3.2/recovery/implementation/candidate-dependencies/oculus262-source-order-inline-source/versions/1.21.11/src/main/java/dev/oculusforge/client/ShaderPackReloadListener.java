package dev.oculusforge.client;

import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

public final class ShaderPackReloadListener extends SimplePreparableReloadListener<OculusClientService.ReloadPreparation> {
   private final OculusClientService service;

   public ShaderPackReloadListener(OculusClientService service) {
      this.service = service;
   }

   protected OculusClientService.ReloadPreparation prepare(ResourceManager manager, ProfilerFiller profiler) {
      profiler.push("oculus_shaderpack_discovery");

      OculusClientService.ReloadPreparation var3;
      try {
         var3 = this.service.prepareReload();
      } finally {
         profiler.pop();
      }

      return var3;
   }

   protected void apply(OculusClientService.ReloadPreparation result, ResourceManager manager, ProfilerFiller profiler) {
      this.service.applyReload(result);
   }
}
