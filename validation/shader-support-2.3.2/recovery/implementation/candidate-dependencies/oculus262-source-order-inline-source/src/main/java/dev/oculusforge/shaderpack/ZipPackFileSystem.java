package dev.oculusforge.shaderpack;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Map.Entry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

public final class ZipPackFileSystem implements PackFileSystem {
   private final ZipFile zip;
   private final ShaderPackLimits limits;
   private final Map<VirtualPath, ZipEntry> entries;
   private final Set<VirtualPath> files;
   private long totalReadBytes;

   public ZipPackFileSystem(Path archive, ShaderPackLimits limits) throws IOException {
      this.limits = limits;

      try {
         this.zip = new ZipFile(archive.toFile());
      } catch (ZipException var11) {
         throw new ShaderPackException(ShaderPackException.Code.MALFORMED_ARCHIVE, "Malformed shader-pack ZIP: " + archive, null, var11);
      }

      try {
         Map<VirtualPath, ZipEntry> raw = this.validateEntries();
         String wrapper = detectWrapper(raw.keySet());
         Map<VirtualPath, ZipEntry> visible = new HashMap<>();

         for (Entry<VirtualPath, ZipEntry> item : raw.entrySet()) {
            String name = item.getKey().toString();
            if (wrapper != null) {
               if (!name.startsWith(wrapper + "/")) {
                  continue;
               }

               name = name.substring(wrapper.length() + 1);
            }

            VirtualPath path = VirtualPath.parse(name);
            if (visible.put(path, item.getValue()) != null) {
               throw malformed("Duplicate normalized ZIP entry: " + path);
            }
         }

         this.entries = Map.copyOf(visible);
         this.files = Collections.unmodifiableSet(new TreeSet<>(visible.keySet()));
      } catch (RuntimeException | IOException var12) {
         try {
            this.zip.close();
         } catch (IOException var10) {
            var12.addSuppressed(var10);
         }

         throw var12;
      }
   }

   private Map<VirtualPath, ZipEntry> validateEntries() throws IOException {
      Map<VirtualPath, ZipEntry> validated = new HashMap<>();
      Enumeration<? extends ZipEntry> enumeration = this.zip.entries();
      long totalUncompressed = 0L;
      int count = 0;

      while (enumeration.hasMoreElements()) {
         ZipEntry entry = enumeration.nextElement();
         if (!entry.isDirectory()) {
            if (++count > this.limits.maxArchiveEntries()) {
               throw limit("ZIP contains more than " + this.limits.maxArchiveEntries() + " files");
            }

            VirtualPath path;
            try {
               path = VirtualPath.parse(entry.getName());
            } catch (ShaderPackException var13) {
               throw malformed("Unsafe ZIP entry name: " + entry.getName(), var13);
            }

            if (validated.put(path, entry) != null) {
               throw malformed("Duplicate normalized ZIP entry: " + path);
            }

            long size = entry.getSize();
            long compressed = entry.getCompressedSize();
            if (size > this.limits.maxFileBytes()) {
               throw new ShaderPackException(
                  ShaderPackException.Code.FILE_LIMIT_EXCEEDED, "ZIP entry exceeds " + this.limits.maxFileBytes() + " bytes: " + path, path.toString()
               );
            }

            if (size >= 0L) {
               try {
                  totalUncompressed = Math.addExact(totalUncompressed, size);
               } catch (ArithmeticException var14) {
                  throw new ShaderPackException(
                     ShaderPackException.Code.ARCHIVE_LIMIT_EXCEEDED, "ZIP declared uncompressed size overflows the supported range", null, var14
                  );
               }

               if (totalUncompressed > this.limits.maxArchiveBytes()) {
                  throw limit("ZIP expands beyond " + this.limits.maxArchiveBytes() + " bytes");
               }
            }

            if (size > 0L && compressed == 0L) {
               throw limit("ZIP entry has an invalid compression ratio: " + path);
            }

            if (size > 0L && compressed > 0L && (double)size / (double)compressed > this.limits.maxCompressionRatio()) {
               throw limit("ZIP entry compression ratio exceeds " + this.limits.maxCompressionRatio() + ": " + path);
            }
         }
      }

      return validated;
   }

   private static String detectWrapper(Set<VirtualPath> paths) {
      boolean rootShaders = paths.stream().anyMatch(pathx -> pathx.startsWith("shaders"));
      if (rootShaders) {
         return null;
      } else {
         String candidate = null;

         for (VirtualPath path : paths) {
            String value = path.toString();
            int separator = value.indexOf(47);
            if (separator <= 0) {
               return null;
            }

            String first = value.substring(0, separator);
            if (candidate == null) {
               candidate = first;
            } else if (!candidate.equals(first)) {
               return null;
            }
         }

         if (candidate == null) {
            return null;
         } else {
            String prefix = candidate + "/shaders";
            return paths.stream().anyMatch(pathx -> pathx.startsWith(prefix)) ? candidate : null;
         }
      }
   }

   @Override
   public Set<VirtualPath> files() {
      return this.files;
   }

   @Override
   public byte[] read(VirtualPath path) throws IOException {
      ZipEntry entry = this.entries.get(path);
      if (entry == null) {
         throw new ShaderPackException(ShaderPackException.Code.MISSING_SOURCE, "Shader source does not exist: " + path, path.toString());
      } else {
         try {
            byte[] var9;
            try (
               InputStream input = this.zip.getInputStream(entry);
               ByteArrayOutputStream output = new ByteArrayOutputStream(initialCapacity(entry));
            ) {
               byte[] buffer = new byte[16384];
               long total = 0L;

               int read;
               while ((read = input.read(buffer)) >= 0) {
                  total += (long)read;
                  if (total > this.limits.maxFileBytes()) {
                     throw new ShaderPackException(
                        ShaderPackException.Code.FILE_LIMIT_EXCEEDED,
                        "ZIP entry exceeds " + this.limits.maxFileBytes() + " bytes while reading: " + path,
                        path.toString()
                     );
                  }

                  output.write(buffer, 0, read);
               }

               var9 = output.toByteArray();
            }

            this.reserveReadBudget(var9.length, path);
            return var9;
         } catch (ZipException var14) {
            throw new ShaderPackException(ShaderPackException.Code.MALFORMED_ARCHIVE, "Cannot read ZIP entry: " + path, path.toString(), var14);
         }
      }
   }

   private static int initialCapacity(ZipEntry entry) {
      long size = entry.getSize();
      return size > 0L && size <= 2147483647L ? (int)size : 8192;
   }

   private synchronized void reserveReadBudget(long bytes, VirtualPath path) throws ShaderPackException {
      try {
         this.totalReadBytes = Math.addExact(this.totalReadBytes, bytes);
      } catch (ArithmeticException exception) {
         throw new ShaderPackException(
            ShaderPackException.Code.ARCHIVE_LIMIT_EXCEEDED, "Cumulative ZIP reads overflow the supported range", path.toString(), exception
         );
      }
      if (this.totalReadBytes > this.limits.maxArchiveBytes()) {
         throw new ShaderPackException(
            ShaderPackException.Code.ARCHIVE_LIMIT_EXCEEDED,
            "Cumulative ZIP reads exceed " + this.limits.maxArchiveBytes() + " bytes",
            path.toString()
         );
      }
   }

   private static ShaderPackException malformed(String message) {
      return new ShaderPackException(ShaderPackException.Code.MALFORMED_ARCHIVE, message);
   }

   private static ShaderPackException malformed(String message, Throwable cause) {
      return new ShaderPackException(ShaderPackException.Code.MALFORMED_ARCHIVE, message, null, cause);
   }

   private static ShaderPackException limit(String message) {
      return new ShaderPackException(ShaderPackException.Code.ARCHIVE_LIMIT_EXCEEDED, message);
   }

   @Override
   public void close() throws IOException {
      this.zip.close();
   }
}
