package dev.oculusforge.shaderpack;

import java.io.IOException;

public final class ShaderPackException extends IOException {
   private static final long serialVersionUID = 1L;
   private final ShaderPackException.Code code;
   private final String shaderPath;

   public ShaderPackException(ShaderPackException.Code code, String message) {
      this(code, message, null, null);
   }

   public ShaderPackException(ShaderPackException.Code code, String message, String shaderPath) {
      this(code, message, shaderPath, null);
   }

   public ShaderPackException(ShaderPackException.Code code, String message, String shaderPath, Throwable cause) {
      super(message, cause);
      this.code = code;
      this.shaderPath = shaderPath;
   }

   public ShaderPackException.Code code() {
      return this.code;
   }

   public String shaderPath() {
      return this.shaderPath;
   }

   public static enum Code {
      INVALID_PATH,
      MALFORMED_ARCHIVE,
      ARCHIVE_LIMIT_EXCEEDED,
      FILE_LIMIT_EXCEEDED,
      INVALID_TEXT_ENCODING,
      MISSING_SOURCE,
      INCLUDE_CYCLE,
      INCLUDE_DEPTH_EXCEEDED,
      INCLUDE_COUNT_EXCEEDED,
      EXPANDED_SOURCE_LIMIT_EXCEEDED,
      PREPROCESSING_FAILED;
   }
}
