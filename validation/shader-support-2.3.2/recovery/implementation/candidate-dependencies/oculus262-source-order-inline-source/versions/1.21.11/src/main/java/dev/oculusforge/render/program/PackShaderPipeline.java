package dev.oculusforge.render.program;

import java.util.List;

public final class PackShaderPipeline implements AutoCloseable {
   private final String packId;
   private final List<PackFinalProgram> programs;

   PackShaderPipeline(String packId, List<PackFinalProgram> programs) {
      this.packId = packId;
      this.programs = List.copyOf(programs);
   }

   public String packId() {
      return this.packId;
   }

   public List<PackFinalProgram> programs() {
      return this.programs;
   }

   @Override
   public void close() {
      for (PackFinalProgram program : this.programs) {
         program.close();
      }
   }
}
