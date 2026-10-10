package dev.oculusforge;

import com.mojang.logging.LogUtils;
import dev.oculusforge.client.OculusClientService;
import dev.oculusforge.client.ShaderPackReloadListener;
import dev.oculusforge.client.gui.ShaderPackScreen;
import net.minecraft.resources.Identifier;
import net.minecraftforge.client.event.AddFramePassEvent;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.client.event.ViewportEvent.RenderFog;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.GameShuttingDownEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod("oculus")
public final class OculusForge {
   public static final String MOD_ID = "oculus";
   public static final Logger LOGGER = LogUtils.getLogger();
   private final OculusClientService clientService = OculusClientService.createDefault();

   public OculusForge(FMLJavaModLoadingContext context) {
      FMLClientSetupEvent.getBus(context.getModBusGroup()).addListener(this::onClientSetup);
      RegisterClientReloadListenersEvent.BUS.addListener(this::registerReloadListener);
      AddFramePassEvent.BUS
         .addListener(event -> event.addPass(Identifier.fromNamespaceAndPath("oculus", "shaderpack_final"), this.clientService.compositePass()));
      RenderFog.BUS.addListener((byte)-128, this.clientService.uniformState()::captureFog);
      GameShuttingDownEvent.BUS.addListener(event -> this.clientService.close());
      MinecraftForge.registerConfigScreen(parent -> new ShaderPackScreen(parent, this.clientService));
   }

   private void onClientSetup(FMLClientSetupEvent event) {
      event.enqueueWork(this.clientService::initializeOnRenderThread);
   }

   private void registerReloadListener(RegisterClientReloadListenersEvent event) {
      event.registerReloadListener(new ShaderPackReloadListener(this.clientService));
   }
}
