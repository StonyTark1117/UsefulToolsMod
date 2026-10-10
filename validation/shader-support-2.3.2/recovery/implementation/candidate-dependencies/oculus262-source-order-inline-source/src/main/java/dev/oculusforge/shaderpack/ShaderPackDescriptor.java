package dev.oculusforge.shaderpack;

import java.io.IOException;
import java.nio.file.Path;

public record ShaderPackDescriptor(String id, String displayName, Path path, ShaderPackDescriptor.Kind kind) {
   public ShaderPackDescriptor(String id, String displayName, Path path, ShaderPackDescriptor.Kind kind) {
      if (id != null && !id.isBlank() && displayName != null && !displayName.isBlank() && path != null && kind != null) {
         this.id = id;
         this.displayName = displayName;
         this.path = path;
         this.kind = kind;
      } else {
         throw new IllegalArgumentException("Shader-pack descriptor fields must be present");
      }
   }

   public PackFileSystem open(ShaderPackLimits limits) throws IOException {
      return switch (this.kind) {
         case DIRECTORY -> new DirectoryPackFileSystem(this.path, limits);
         case ZIP -> new ZipPackFileSystem(this.path, limits);
      };
   }

   public static enum Kind {
      DIRECTORY,
      ZIP;
   }
}
