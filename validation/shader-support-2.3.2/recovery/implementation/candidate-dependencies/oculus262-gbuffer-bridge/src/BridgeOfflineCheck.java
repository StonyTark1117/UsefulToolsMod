public final class BridgeOfflineCheck {
    private BridgeOfflineCheck() { }

    public static void main(String[] args) throws Exception {
        for (String name : new String[] {
            "dev.oculusforge.render.program.LegacyFinalShaderAdapter",
            "dev.oculusforge.render.program.PackProgramCompiler",
            "dev.oculusforge.render.program.PreparedPackPipeline",
            "dev.oculusforge.render.program.PackShaderPipeline",
            "com.mojang.blaze3d.opengl.GlRenderPass"
        }) {
            Class.forName(name, false, BridgeOfflineCheck.class.getClassLoader());
            System.out.println("LOADED " + name);
        }
    }
}
