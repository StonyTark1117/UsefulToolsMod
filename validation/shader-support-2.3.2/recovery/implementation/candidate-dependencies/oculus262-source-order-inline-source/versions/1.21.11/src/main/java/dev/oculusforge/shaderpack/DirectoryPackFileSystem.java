package dev.oculusforge.shaderpack;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;

public final class DirectoryPackFileSystem implements PackFileSystem {
   private final Path root;
   private final ShaderPackLimits limits;
   private final Set<VirtualPath> files;

   public DirectoryPackFileSystem(Path root, ShaderPackLimits limits) throws IOException {
      this.root = root.toRealPath();
      this.limits = limits;
      if (!Files.isDirectory(this.root, LinkOption.NOFOLLOW_LINKS)) {
         throw new ShaderPackException(ShaderPackException.Code.INVALID_PATH, "Shader pack is not a directory: " + root);
      } else {
         TreeSet<VirtualPath> discovered = new TreeSet<>();
         long totalBytes = 0L;
         int fileCount = 0;

         try (Stream<Path> stream = Files.walk(this.root)) {
            for (var paths = stream.iterator(); paths.hasNext(); ) {
               Path path = paths.next();
               if (path.equals(this.root)) {
                  continue;
               }
               if (Files.isSymbolicLink(path)) {
                  throw new ShaderPackException(ShaderPackException.Code.INVALID_PATH, "Symbolic links are not allowed in shader packs: " + path);
               }
               if (!Files.isRegularFile(path, LinkOption.NOFOLLOW_LINKS)) {
                  continue;
               }
               if (++fileCount > this.limits.maxArchiveEntries()) {
                  throw new ShaderPackException(
                     ShaderPackException.Code.ARCHIVE_LIMIT_EXCEEDED,
                     "Shader-pack directory contains more than " + this.limits.maxArchiveEntries() + " files"
                  );
               }

               Path real = path.toRealPath();
               if (!real.startsWith(this.root)) {
                  throw new ShaderPackException(ShaderPackException.Code.INVALID_PATH, "Symbolic link escapes shader-pack root: " + path);
               }

               long size = Files.size(real);
               if (size > this.limits.maxFileBytes()) {
                  throw new ShaderPackException(
                     ShaderPackException.Code.FILE_LIMIT_EXCEEDED,
                     "Shader source exceeds " + this.limits.maxFileBytes() + " bytes: " + path
                  );
               }
               try {
                  totalBytes = Math.addExact(totalBytes, size);
               } catch (ArithmeticException exception) {
                  throw new ShaderPackException(
                     ShaderPackException.Code.ARCHIVE_LIMIT_EXCEEDED, "Shader-pack directory size overflows the supported range", null, exception
                  );
               }
               if (totalBytes > this.limits.maxArchiveBytes()) {
                  throw new ShaderPackException(
                     ShaderPackException.Code.ARCHIVE_LIMIT_EXCEEDED,
                     "Shader-pack directory exceeds " + this.limits.maxArchiveBytes() + " bytes"
                  );
               }

               String relative = this.root.relativize(real).toString().replace('\\', '/');
               discovered.add(VirtualPath.parse(relative));
            }
         }

         this.files = Collections.unmodifiableSet(discovered);
      }
   }

   @Override
   public Set<VirtualPath> files() {
      return this.files;
   }

   @Override
   public byte[] read(VirtualPath path) throws IOException {
      if (!this.files.contains(path)) {
         throw new ShaderPackException(ShaderPackException.Code.MISSING_SOURCE, "Shader source does not exist: " + path, path.toString());
      } else {
         Path candidate = this.root.resolve(path.toString().replace('/', this.root.getFileSystem().getSeparator().charAt(0))).normalize();
         Path real = candidate.toRealPath();
         if (!real.startsWith(this.root)) {
            throw new ShaderPackException(ShaderPackException.Code.INVALID_PATH, "Shader source escapes shader-pack root: " + path, path.toString());
         } else {
            long size = Files.size(real);
            if (size > this.limits.maxFileBytes()) {
               throw new ShaderPackException(
                  ShaderPackException.Code.FILE_LIMIT_EXCEEDED, "Shader source exceeds " + this.limits.maxFileBytes() + " bytes: " + path, path.toString()
               );
            } else {
               return Files.readAllBytes(real);
            }
         }
      }
   }

   @Override
   public void close() {
   }
}
