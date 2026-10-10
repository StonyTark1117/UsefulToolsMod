package com.stonytark.usefultools.lighting;
/** Fresh native control bounds supplement pixel-font OCR in acceptance clients only. */
public final class LightingQaMenu {
    private static long last;
    private LightingQaMenu() {}
    public static void capture(net.minecraft.client.MinecraftClient mc) {
        String destination=System.getProperty("usefulToolsLightingQaMenu");
        long now=System.currentTimeMillis();
        if(destination==null||mc.currentScreen==null||now-last<250)return;
        last=now;
        StringBuilder text=new StringBuilder().append(now).append('\n');
        double scale=mc.getWindow().getScaleFactor();
        for(var control:mc.currentScreen.children()) if(control instanceof net.minecraft.client.gui.widget.ClickableWidget button && button.visible && button.active) {
            String label=java.util.Base64.getEncoder().encodeToString(button.getMessage().getString().getBytes(java.nio.charset.StandardCharsets.UTF_8));
            text.append(label).append('\t').append((button.getX()+button.getWidth()*.5)*scale).append('\t').append((button.getY()+button.getHeight()*.5)*scale).append('\n');
        }
        com.stonytark.usefultools.lighting.qa.OptifineLightingQaMenuSnapshot.append(mc.currentScreen,scale,text);
        try {java.nio.file.Files.write(java.nio.file.Paths.get(destination),text.toString().getBytes(java.nio.charset.StandardCharsets.UTF_8));}
        catch(java.io.IOException error) {throw new IllegalStateException("Cannot publish acceptance menu state",error);}
    }
}
