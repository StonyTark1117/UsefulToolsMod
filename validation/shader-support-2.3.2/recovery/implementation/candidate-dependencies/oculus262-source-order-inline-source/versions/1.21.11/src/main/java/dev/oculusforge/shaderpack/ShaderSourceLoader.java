package dev.oculusforge.shaderpack;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ShaderSourceLoader {
   private static final Pattern INCLUDE = Pattern.compile("^\\s*#\\s*include\\s*[\"<]([^\">]+)[\">]\\s*(?://.*)?$");
   private final PackFileSystem pack;
   private final ShaderPackLimits limits;
   private final Map<VirtualPath, Integer> sourceIds = new LinkedHashMap<>();
   private final Deque<VirtualPath> stack = new ArrayDeque<>();
   private int includeCount;
   private long expandedBytes;

   public ShaderSourceLoader(PackFileSystem pack, ShaderPackLimits limits) {
      this.pack = pack;
      this.limits = limits;
   }

   public ShaderSourceLoader.LoadResult load(VirtualPath entryPoint) throws IOException {
      this.sourceIds.clear();
      this.stack.clear();
      this.includeCount = 0;
      this.expandedBytes = 0L;
      if (!this.pack.exists(entryPoint)) {
         throw new ShaderPackException(ShaderPackException.Code.MISSING_SOURCE, "Shader source does not exist: " + entryPoint, entryPoint.toString());
      } else {
         StringBuilder output = new StringBuilder();
         this.append(entryPoint, output, 0);
         Map<Integer, VirtualPath> inverted = new LinkedHashMap<>();
         this.sourceIds.forEach((path, id) -> inverted.put(id, path));
         return new ShaderSourceLoader.LoadResult(output.toString(), inverted);
      }
   }

   private void append(VirtualPath path, StringBuilder output, int depth) throws IOException {
      if (depth > this.limits.maxIncludeDepth()) {
         throw new ShaderPackException(
            ShaderPackException.Code.INCLUDE_DEPTH_EXCEEDED, "Shader include depth exceeds " + this.limits.maxIncludeDepth() + ": " + path, path.toString()
         );
      } else if (this.stack.contains(path)) {
         String chain = this.stack.stream().map(VirtualPath::toString).reduce((left, right) -> left + " -> " + right).orElse("");
         throw new ShaderPackException(ShaderPackException.Code.INCLUDE_CYCLE, "Shader include cycle: " + chain + " -> " + path, path.toString());
      } else {
         this.stack.addLast(path);
         int sourceId = this.sourceIds.computeIfAbsent(path, ignored -> this.sourceIds.size());
         String source = this.pack.readUtf8(path);
         String[] lines = source.split("\\R", -1);

         for (int index = 0; index < lines.length; index++) {
            String line = lines[index];
            Matcher matcher = INCLUDE.matcher(line);
            if (!matcher.matches()) {
               this.appendBounded(output, line);
               if (index < lines.length - 1) {
                  this.appendBounded(output, "\n");
               }
            } else {
               this.includeCount++;
               if (this.includeCount > this.limits.maxIncludeCount()) {
                  throw new ShaderPackException(
                     ShaderPackException.Code.INCLUDE_COUNT_EXCEEDED, "Shader includes more than " + this.limits.maxIncludeCount() + " files", path.toString()
                  );
               }

               VirtualPath included = VirtualPath.resolve(path, matcher.group(1).trim());
               if (!this.pack.exists(included)) {
                  throw new ShaderPackException(
                     ShaderPackException.Code.MISSING_SOURCE,
                     "Missing include " + matcher.group(1) + " referenced by " + path + ":" + (index + 1),
                     included.toString()
                  );
               }

               int includedId = this.sourceIds.computeIfAbsent(included, ignored -> this.sourceIds.size());
               this.appendBounded(output, "#line 1 " + includedId + "\n");
               this.append(included, output, depth + 1);
               this.appendBounded(output, "\n#line " + (index + 2) + " " + sourceId);
               if (index < lines.length - 1) {
                  this.appendBounded(output, "\n");
               }
            }
         }

         this.stack.removeLast();
      }
   }

   private void appendBounded(StringBuilder output, String text) throws ShaderPackException {
      this.expandedBytes = this.expandedBytes + (long)text.getBytes(StandardCharsets.UTF_8).length;
      if (this.expandedBytes > this.limits.maxExpandedSourceBytes()) {
         throw new ShaderPackException(
            ShaderPackException.Code.EXPANDED_SOURCE_LIMIT_EXCEEDED, "Expanded shader source exceeds " + this.limits.maxExpandedSourceBytes() + " bytes"
         );
      } else {
         output.append(text);
      }
   }

   public static record LoadResult(String source, Map<Integer, VirtualPath> sourceMap) {
      public LoadResult(String source, Map<Integer, VirtualPath> sourceMap) {
         sourceMap = Map.copyOf(sourceMap);
         this.source = source;
         this.sourceMap = sourceMap;
      }
   }
}
