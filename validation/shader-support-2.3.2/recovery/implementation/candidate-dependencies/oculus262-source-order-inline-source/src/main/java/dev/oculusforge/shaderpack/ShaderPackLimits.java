package dev.oculusforge.shaderpack;

public record ShaderPackLimits(
   long maxFileBytes,
   long maxArchiveBytes,
   int maxArchiveEntries,
   double maxCompressionRatio,
   int maxIncludeDepth,
   int maxIncludeCount,
   long maxExpandedSourceBytes
) {
   public static final ShaderPackLimits DEFAULT = new ShaderPackLimits(8388608L, 268435456L, 4096, 200.0, 32, 512, 16777216L);

   public ShaderPackLimits(
      long maxFileBytes,
      long maxArchiveBytes,
      int maxArchiveEntries,
      double maxCompressionRatio,
      int maxIncludeDepth,
      int maxIncludeCount,
      long maxExpandedSourceBytes
   ) {
      if (maxFileBytes > 0L
         && maxArchiveBytes > 0L
         && maxArchiveEntries > 0
         && !(maxCompressionRatio <= 1.0)
         && maxIncludeDepth > 0
         && maxIncludeCount > 0
         && maxExpandedSourceBytes > 0L) {
         this.maxFileBytes = maxFileBytes;
         this.maxArchiveBytes = maxArchiveBytes;
         this.maxArchiveEntries = maxArchiveEntries;
         this.maxCompressionRatio = maxCompressionRatio;
         this.maxIncludeDepth = maxIncludeDepth;
         this.maxIncludeCount = maxIncludeCount;
         this.maxExpandedSourceBytes = maxExpandedSourceBytes;
      } else {
         throw new IllegalArgumentException("All shader-pack limits must be positive");
      }
   }
}
