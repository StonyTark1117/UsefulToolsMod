package dev.oculusforge.shaderpack;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

public final class ShaderPackRepository {
   private final Path shaderPacksDirectory;
   private final ShaderPackLimits limits;

   public ShaderPackRepository(Path shaderPacksDirectory, ShaderPackLimits limits) {
      this.shaderPacksDirectory = shaderPacksDirectory.toAbsolutePath().normalize();
      this.limits = limits;
   }

   public Path directory() {
      return this.shaderPacksDirectory;
   }

   public ShaderPackRepository.DiscoveryResult discover() throws IOException {
      Files.createDirectories(this.shaderPacksDirectory);
      List<ShaderPackDescriptor> packs = new ArrayList<>();
      List<ShaderPackRepository.DiscoveryIssue> issues = new ArrayList<>();

      try (Stream<Path> children = Files.list(this.shaderPacksDirectory)) {
         for (Path child : children.sorted(Comparator.comparing(path -> path.getFileName().toString(), String.CASE_INSENSITIVE_ORDER)).toList()) {
            String name = child.getFileName().toString();

            try {
               if (Files.isDirectory(child, LinkOption.NOFOLLOW_LINKS)) {
                  Path shaderRoot = child.resolve("shaders");
                  if (Files.isDirectory(shaderRoot)) {
                     packs.add(this.descriptor(child, name, ShaderPackDescriptor.Kind.DIRECTORY));
                  } else {
                     issues.add(new ShaderPackRepository.DiscoveryIssue(child, "Directory does not contain a shaders folder", null));
                  }
               } else if (Files.isRegularFile(child, LinkOption.NOFOLLOW_LINKS) && name.toLowerCase(Locale.ROOT).endsWith(".zip")) {
                  try (ZipPackFileSystem archive = new ZipPackFileSystem(child, this.limits)) {
                     if (archive.files().stream().anyMatch(path -> path.startsWith("shaders"))) {
                        packs.add(this.descriptor(child, stripZip(name), ShaderPackDescriptor.Kind.ZIP));
                     } else {
                        issues.add(new ShaderPackRepository.DiscoveryIssue(child, "ZIP does not contain a shaders folder", null));
                     }
                  }
               }
            } catch (ShaderPackException var13) {
               issues.add(new ShaderPackRepository.DiscoveryIssue(child, var13.getMessage(), var13.code()));
            } catch (IOException var14) {
               issues.add(new ShaderPackRepository.DiscoveryIssue(child, var14.getMessage(), ShaderPackException.Code.MALFORMED_ARCHIVE));
            }
         }
      }

      return new ShaderPackRepository.DiscoveryResult(packs, issues);
   }

   private ShaderPackDescriptor descriptor(Path path, String displayName, ShaderPackDescriptor.Kind kind) {
      String id = path.getFileName().toString();
      return new ShaderPackDescriptor(id, displayName, path.toAbsolutePath().normalize(), kind);
   }

   private static String stripZip(String name) {
      return name.substring(0, name.length() - 4);
   }

   public static record DiscoveryIssue(Path path, String message, ShaderPackException.Code code) {
   }

   public static record DiscoveryResult(List<ShaderPackDescriptor> packs, List<ShaderPackRepository.DiscoveryIssue> issues) {
      public DiscoveryResult(List<ShaderPackDescriptor> packs, List<ShaderPackRepository.DiscoveryIssue> issues) {
         packs = List.copyOf(packs);
         issues = List.copyOf(issues);
         this.packs = packs;
         this.issues = issues;
      }
   }
}
