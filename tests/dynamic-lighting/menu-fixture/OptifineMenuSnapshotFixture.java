import com.stonytark.usefultools.lighting.qa.OptifineLightingQaMenuSnapshot;
import net.optifine.shaders.gui.GuiShaders;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public final class OptifineMenuSnapshotFixture {
    private OptifineMenuSnapshotFixture() {}
    public static void main(String[] args) {
        GuiShaders gui = new GuiShaders();
        StringBuilder text = new StringBuilder();
        OptifineLightingQaMenuSnapshot.append(gui, 2, text);
        String label = Base64.getEncoder().encodeToString("BSL_v10.1.2.zip".getBytes(StandardCharsets.UTF_8));
        if (!text.toString().contains(label + "\t140.0\t148.0\n")) throw new AssertionError(text);
        if (text.toString().split("\n").length != 3) throw new AssertionError("Clipped row was published");
        gui.scroll(40);
        text.setLength(0); OptifineLightingQaMenuSnapshot.append(gui, 2, text);
        if (!text.toString().contains(label + "\t140.0\t68.0\n")) throw new AssertionError(text);
        if (text.toString().split("\n").length != 2) throw new AssertionError("Scrolled-off rows were published");
        gui.rejectHits();
        text.setLength(0); OptifineLightingQaMenuSnapshot.append(gui, 2, text);
        if (text.length() != 0) throw new AssertionError("Native hit rejection ignored");
        gui.hide();
        text.setLength(0); OptifineLightingQaMenuSnapshot.append(gui, 2, text);
        if (text.length() != 0) throw new AssertionError("Invisible list was published");
        OptifineLightingQaMenuSnapshot.append(new Object(), 2, text);
        if (text.length() != 0) throw new AssertionError("Unrelated menu was changed");
        shadersmod.client.GuiShaders classic = new shadersmod.client.GuiShaders();
        String classicLabel = Base64.getEncoder().encodeToString("BSL_v8.4.02.2.zip".getBytes(StandardCharsets.UTF_8));
        OptifineLightingQaMenuSnapshot.append(classic, 3, 4, text);
        if (!text.toString().contains(classicLabel + "\t210.0\t296.0\n")) throw new AssertionError(text);
        if (text.toString().split("\n").length != 3) throw new AssertionError("Classic clipped row was published");
        classic.scroll(40);
        text.setLength(0); OptifineLightingQaMenuSnapshot.append(classic, 3, 4, text);
        if (!text.toString().contains(classicLabel + "\t210.0\t136.0\n")) throw new AssertionError(text);
        if (text.toString().split("\n").length != 2) throw new AssertionError("Classic scrolled-off rows were published");
        classic.rejectHits();
        text.setLength(0); OptifineLightingQaMenuSnapshot.append(classic, 3, 4, text);
        if (text.length() != 0) throw new AssertionError("Classic native hit rejection ignored");
        System.out.println("Modern and classic SRG native labels, scroll/clipping, hit rejection and unrelated menus passed");
    }
}
