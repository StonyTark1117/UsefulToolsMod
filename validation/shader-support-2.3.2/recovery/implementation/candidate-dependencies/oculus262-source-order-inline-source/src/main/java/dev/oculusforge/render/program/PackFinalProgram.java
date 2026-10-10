package dev.oculusforge.render.program;

import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.buffers.Std140Builder;
import com.mojang.blaze3d.buffers.Std140SizeCalculator;
import com.mojang.blaze3d.buffers.GpuBufferSlice.MappedView;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MappableRingBuffer;
import net.minecraft.client.renderer.state.level.LevelRenderState;
import net.minecraft.world.level.material.FogType;

public final class PackFinalProgram implements AutoCloseable {
   private static final float SECONDS_PER_HOUR = 3600.0F;
   private static final int FRAME_COUNTER_WRAP = 720720;
   private final String packId;
   private final String stageName;
   private final RenderPipeline pipeline;
   private final List<LegacyFinalShaderAdapter.SamplerBinding> samplers;
   private final List<LegacyFinalShaderAdapter.UniformBinding> uniforms;
   private final MappableRingBuffer uniformBuffer;
   private long previousFrameNanos;
   private float lastFrameTime;
   private float frameTimeCounter;
   private float wetness;
   private int frameCounter;
   private double previousCameraX;
   private double previousCameraY;
   private double previousCameraZ;
   private boolean hasPreviousCamera;

   PackFinalProgram(
      String packId,
      String stageName,
      RenderPipeline pipeline,
      List<LegacyFinalShaderAdapter.SamplerBinding> samplers,
      List<LegacyFinalShaderAdapter.UniformBinding> uniforms,
      MappableRingBuffer uniformBuffer
   ) {
      this.packId = packId;
      this.stageName = stageName;
      this.pipeline = pipeline;
      this.samplers = List.copyOf(samplers);
      this.uniforms = List.copyOf(uniforms);
      this.uniformBuffer = uniformBuffer;
   }

   public String packId() {
      return this.packId;
   }

   public RenderPipeline pipeline() {
      return this.pipeline;
   }

   public String stageName() {
      return this.stageName;
   }

   public List<LegacyFinalShaderAdapter.SamplerBinding> samplers() {
      return this.samplers;
   }

   public boolean hasUniforms() {
      return this.uniformBuffer != null;
   }

   public GpuBuffer updateUniforms(LevelRenderState state, int width, int height) {
      if (this.uniformBuffer == null) {
         throw new IllegalStateException("This shader program has no legacy uniform buffer");
      } else {
         long now = System.nanoTime();
         float deltaSeconds = this.previousFrameNanos == 0L ? 0.0F : Math.min((float)(now - this.previousFrameNanos) / 1.0E9F, 1.0F);
         this.previousFrameNanos = now;
         this.lastFrameTime = deltaSeconds;
         this.frameTimeCounter = (this.frameTimeCounter + deltaSeconds) % 3600.0F;
         float rain = state.weatherRenderState.intensity;
         float halfLife = rain > this.wetness ? 600.0F : 200.0F;
         float blend = deltaSeconds <= 0.0F ? 0.0F : 1.0F - (float)Math.pow(0.5, (double)(deltaSeconds * 20.0F / halfLife));
         this.wetness = this.wetness + (rain - this.wetness) * blend;
         MappedView mapped = this.uniformBuffer.currentBuffer().map(false, true);

         try {
            Std140Builder builder = Std140Builder.intoBuffer(mapped.data());

            for (LegacyFinalShaderAdapter.UniformBinding uniform : this.uniforms) {
               this.writeUniform(builder, uniform.semantic(), state, width, height, rain);
            }
         } catch (Throwable var15) {
            if (mapped != null) {
               try {
                  mapped.close();
               } catch (Throwable var14) {
                  var15.addSuppressed(var14);
               }
            }

            throw var15;
         }

         if (mapped != null) {
            mapped.close();
         }

         this.previousCameraX = state.cameraRenderState.pos.x;
         this.previousCameraY = state.cameraRenderState.pos.y;
         this.previousCameraZ = state.cameraRenderState.pos.z;
         this.hasPreviousCamera = true;
         this.frameCounter = (this.frameCounter + 1) % 720720;
         return this.uniformBuffer.currentBuffer();
      }
   }

   public void finishFrame() {
      if (this.uniformBuffer != null) {
         this.uniformBuffer.rotate();
      }
   }

   private void writeUniform(
      Std140Builder builder, LegacyFinalShaderAdapter.UniformSemantic semantic, LevelRenderState state, int width, int height, float rain
   ) {
      switch (semantic) {
         case VIEW_WIDTH:
            builder.putFloat((float)width);
            break;
         case VIEW_HEIGHT:
            builder.putFloat((float)height);
            break;
         case ASPECT_RATIO:
            builder.putFloat(height == 0 ? 1.0F : (float)width / (float)height);
            break;
         case FRAME_TIME:
            builder.putFloat(this.lastFrameTime);
            break;
         case FRAME_TIME_COUNTER:
            builder.putFloat(this.frameTimeCounter);
            break;
         case FRAME_COUNTER:
            builder.putInt(this.frameCounter);
            break;
         case WORLD_TIME:
            builder.putInt((int)Math.floorMod(state.gameTime, 24000L));
            break;
         case WORLD_DAY:
            builder.putInt((int)(state.gameTime / 24000L));
            break;
         case RAIN_STRENGTH:
            builder.putFloat(rain);
            break;
         case WETNESS:
            builder.putFloat(this.wetness);
            break;
         case EYE_ALTITUDE:
            builder.putFloat((float)state.cameraRenderState.pos.y);
            break;
         case NEAR:
            builder.putFloat(0.05F);
            break;
         case FAR:
            builder.putFloat(state.cameraRenderState.depthFar);
            break;
         case SUN_ANGLE:
            float turns = (state.skyRenderState.sunAngle + (float) (Math.PI / 2)) / (float) (Math.PI * 2);
            builder.putFloat(turns - (float)Math.floor((double)turns));
            break;
         case SCREEN_BRIGHTNESS:
            builder.putFloat(Minecraft.getInstance().options.gamma().get().floatValue());
            break;
         case IS_EYE_IN_WATER:
            builder.putInt(eyeMedium(state.cameraRenderState.fogType));
            break;
         case CAMERA_POSITION:
            builder.putVec3((float)state.cameraRenderState.pos.x, (float)state.cameraRenderState.pos.y, (float)state.cameraRenderState.pos.z);
            break;
         case PREVIOUS_CAMERA_POSITION:
            if (!this.hasPreviousCamera) {
               this.previousCameraX = state.cameraRenderState.pos.x;
               this.previousCameraY = state.cameraRenderState.pos.y;
               this.previousCameraZ = state.cameraRenderState.pos.z;
            }

            builder.putVec3((float)this.previousCameraX, (float)this.previousCameraY, (float)this.previousCameraZ);
            break;
         case FOG_COLOR:
            builder.putVec3(state.cameraRenderState.fogData.color.x, state.cameraRenderState.fogData.color.y, state.cameraRenderState.fogData.color.z);
            break;
         case SKY_COLOR:
            int color = state.skyRenderState.skyColor;
            builder.putVec3((float)(color >>> 16 & 0xFF) / 255.0F, (float)(color >>> 8 & 0xFF) / 255.0F, (float)(color & 0xFF) / 255.0F);
            break;
         case NIGHT_VISION:
         case IN_BASALT_DELTAS:
         case IN_CRIMSON_FOREST:
         case IN_NETHER_WASTES:
         case IN_SOUL_VALLEY:
         case IN_WARPED_FOREST:
            builder.putFloat(0.0F);
            break;
         case RAIN_FACTOR:
            builder.putFloat(rain);
            break;
         case CAMERA_POSITION_INT:
            builder.putIVec3(floorToInt(state.cameraRenderState.pos.x), floorToInt(state.cameraRenderState.pos.y), floorToInt(state.cameraRenderState.pos.z));
            break;
         case CAMERA_POSITION_FRACT:
            builder.putVec3(fract(state.cameraRenderState.pos.x), fract(state.cameraRenderState.pos.y), fract(state.cameraRenderState.pos.z));
            break;
         case PREVIOUS_CAMERA_POSITION_FRACT:
            builder.putVec3(fract(this.previousCameraX), fract(this.previousCameraY), fract(this.previousCameraZ));
      }
   }

   private static int floorToInt(double value) {
      return (int)Math.floor(value);
   }

   private static float fract(double value) {
      return (float)(value - Math.floor(value));
   }

   private static int eyeMedium(FogType fogType) {
      return switch (fogType) {
         case WATER -> 1;
         case LAVA -> 2;
         case POWDER_SNOW -> 3;
         case ATMOSPHERIC, NONE -> 0;
         default -> throw new MatchException(null, null);
      };
   }

   static int uniformBufferSize(List<LegacyFinalShaderAdapter.UniformBinding> uniforms) {
      Std140SizeCalculator size = new Std140SizeCalculator();

      for (LegacyFinalShaderAdapter.UniformBinding uniform : uniforms) {
         switch (uniform.semantic().type()) {
            case FLOAT:
               size.putFloat();
               break;
            case INT:
               size.putInt();
               break;
            case VEC3:
               size.putVec3();
               break;
            case IVEC3:
               size.putIVec3();
         }
      }

      return size.align(16).get();
   }

   @Override
   public void close() {
      if (this.uniformBuffer != null) {
         this.uniformBuffer.close();
      }
   }
}
