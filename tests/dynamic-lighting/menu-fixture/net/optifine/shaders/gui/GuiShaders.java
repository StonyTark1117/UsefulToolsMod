package net.optifine.shaders.gui;

import java.util.Arrays;
import java.util.List;

/** Fixture mirrors only the exact pinned SlotGui signatures used by the reader. */
public final class GuiShaders {
    private final Slot shaderList = new Slot();
    public void scroll(int pixels) { shaderList.yo = pixels; }
    public void hide() { shaderList.visible = false; }
    public void rejectHits() { shaderList.rejectHits = true; }

    private static class Bounds {
        protected boolean visible = true;
        protected int x0 = 10, width = 120, y0 = 20, y1 = 82;
        protected int itemHeight = 20, headerHeight;
        protected double yo;
    }

    private static final class Slot extends Bounds {
        private final List<String> shaderslist = Arrays.asList("OFF", "(internal)",
                "BSL_v10.1.2.zip", "different.zip");
        private boolean rejectHits;
        public int getItemAtPosition(double x, double y) {
            if (rejectHits || x < x0 || x > x0 + width || y <= y0 || y >= y1) return -1;
            return ((int) Math.floor(y - y0) - headerHeight + (int) yo - 4) / itemHeight;
        }
    }
}
