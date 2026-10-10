package dev.oculusforge.render.program;

import dev.oculusforge.shaderpack.ShaderPackDescriptor;
import java.util.List;

public record PreparedPackPipeline(ShaderPackDescriptor pack, List<PreparedFinalProgram> programs) {
   public PreparedPackPipeline(ShaderPackDescriptor pack, List<PreparedFinalProgram> programs) {
      programs = List.copyOf(programs);
      if (programs.isEmpty()) {
         throw new IllegalArgumentException("A prepared pack pipeline needs at least one program");
      } else {
         this.pack = pack;
         this.programs = programs;
      }
   }
}
