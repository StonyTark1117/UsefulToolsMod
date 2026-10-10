package shadersmod.client;

import java.util.Arrays;
import java.util.List;

/** The SRG field names and hit-test signature come from the pinned 1.7.10 game. */
public final class GuiShaders {
    private final Slot shaderList = new Slot();
    public void scroll(int pixels) { shaderList.field_148169_q = pixels; }
    public void rejectHits() { shaderList.rejectHits = true; }

    private static class Bounds {
        protected int field_148152_e = 10, field_148155_a = 120;
        protected int field_148153_b = 20, field_148154_c = 82;
        protected int field_148149_f = 20, field_148160_j;
        protected float field_148169_q;
    }

    private static final class Slot extends Bounds {
        private final List<String> shaderslist = Arrays.asList("OFF", "(internal)",
                "BSL_v8.4.02.2.zip", "different.zip");
        private boolean rejectHits;
        public int func_148124_c(int x, int y) {
            if (rejectHits || x < field_148152_e || x > field_148152_e + field_148155_a
                    || y <= field_148153_b || y >= field_148154_c) return -1;
            return (y - field_148153_b - field_148160_j + (int) field_148169_q - 4) / field_148149_f;
        }
    }
}
