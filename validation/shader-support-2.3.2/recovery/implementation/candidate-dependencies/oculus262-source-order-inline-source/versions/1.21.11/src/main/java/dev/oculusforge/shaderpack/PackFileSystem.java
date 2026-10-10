package dev.oculusforge.shaderpack;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.util.Set;

public interface PackFileSystem extends AutoCloseable {
   Set<VirtualPath> files();

   byte[] read(VirtualPath var1) throws IOException;

   default boolean exists(VirtualPath path) {
      return this.files().contains(path);
   }

   default String readUtf8(VirtualPath path) throws IOException {
      try {
         return StandardCharsets.UTF_8
            .newDecoder()
            .onMalformedInput(CodingErrorAction.REPORT)
            .onUnmappableCharacter(CodingErrorAction.REPORT)
            .decode(ByteBuffer.wrap(this.read(path)))
            .toString();
      } catch (CharacterCodingException var3) {
         throw new ShaderPackException(ShaderPackException.Code.INVALID_TEXT_ENCODING, "Shader source is not valid UTF-8: " + path, path.toString(), var3);
      }
   }

   @Override
   void close() throws IOException;
}
