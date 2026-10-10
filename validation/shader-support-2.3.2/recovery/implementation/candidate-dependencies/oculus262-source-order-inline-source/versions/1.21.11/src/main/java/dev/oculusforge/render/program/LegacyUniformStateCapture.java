package dev.oculusforge.render.program;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraftforge.client.event.ViewportEvent.RenderFog;
import org.joml.Vector4f;

public final class LegacyUniformStateCapture {
   private float fogRed;
   private float fogGreen;
   private float fogBlue;
   private boolean hasFogColor;

   public void captureFog(RenderFog event) {
      RenderSystem.assertOnRenderThread();
      Vector4f color = event.getColor();
      this.fogRed = color.x;
      this.fogGreen = color.y;
      this.fogBlue = color.z;
      this.hasFogColor = true;
   }

   public void requireFogColor() {
      if (!this.hasFogColor) {
         throw new IllegalStateException("Forge did not provide the current world fog color before the shader pass");
      }
   }

   public float fogRed() {
      return this.fogRed;
   }

   public float fogGreen() {
      return this.fogGreen;
   }

   public float fogBlue() {
      return this.fogBlue;
   }
}
