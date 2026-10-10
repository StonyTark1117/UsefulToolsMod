package dev.oculusforge.render.program;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import java.util.Map;

public final class PackShaderPipeline implements AutoCloseable {
   private final String packId;
   private final List<PackFinalProgram> programs;
   private final Map<String, String> gbufferProgramHashes;

   PackShaderPipeline(String packId, List<PackFinalProgram> programs) {
      this(packId, programs, Map.of());
   }

   PackShaderPipeline(String packId, List<PackFinalProgram> programs, Map<String, String> gbufferProgramHashes) {
      this.packId = packId;
      this.programs = List.copyOf(programs);
      this.gbufferProgramHashes = Map.copyOf(gbufferProgramHashes);
   }

   public String packId() {
      return this.packId;
   }

   public List<PackFinalProgram> programs() {
      return this.programs;
   }

   public Map<String, String> gbufferProgramHashes() {
      return this.gbufferProgramHashes;
   }

   @Override
   public void close() {
      if (!this.gbufferProgramHashes.isEmpty()) {
         System.clearProperty("oculus262.gbuffer.active");
         System.clearProperty("oculus262.gbuffer.program.terrain");
         System.clearProperty("oculus262.gbuffer.program.entity");
         RenderSystem.getDevice().clearPipelineCache();
      }
      for (PackFinalProgram program : this.programs) {
         program.close();
      }
   }
}
