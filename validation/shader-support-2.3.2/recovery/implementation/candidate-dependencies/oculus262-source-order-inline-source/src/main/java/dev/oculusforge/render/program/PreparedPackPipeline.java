package dev.oculusforge.render.program;

import dev.oculusforge.shaderpack.ShaderPackDescriptor;
import java.util.List;
import java.util.Map;

public record PreparedPackPipeline(ShaderPackDescriptor pack, List<PreparedFinalProgram> programs, Map<String, String> gbufferSources) {
   public PreparedPackPipeline(ShaderPackDescriptor pack, List<PreparedFinalProgram> programs) {
      this(pack, programs, Map.of());
   }

   public PreparedPackPipeline(ShaderPackDescriptor pack, List<PreparedFinalProgram> programs, Map<String, String> gbufferSources) {
      programs = List.copyOf(programs);
      gbufferSources = Map.copyOf(gbufferSources);
      if (programs.isEmpty()) {
         throw new IllegalArgumentException("A prepared pack pipeline needs at least one program");
      } else {
         this.pack = pack;
         this.programs = programs;
         this.gbufferSources = gbufferSources;
      }
   }
}
