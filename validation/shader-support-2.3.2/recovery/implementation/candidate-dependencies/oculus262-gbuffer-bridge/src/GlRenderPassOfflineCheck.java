public final class GlRenderPassOfflineCheck {
    private GlRenderPassOfflineCheck() { }

    public static void main(String[] args) throws Exception {
        Class<?> type = Class.forName("com.mojang.blaze3d.opengl.GlRenderPass", false,
            GlRenderPassOfflineCheck.class.getClassLoader());
        String expected = "e2de6aa0f24780fcc8d73c0784d0623773ad5d29dfd5b5c8dd042d2e24cc6ed6";
        String actual = System.getProperty("oculus262.gbuffer.renderBridge", "");
        if (!actual.equals(expected)) {
            throw new IllegalStateException("Render bridge sentinel mismatch: expected=" + expected + " actual=" + actual);
        }
        System.out.println("LOADED " + type.getName() + " render_bridge=" + actual);
    }
}
