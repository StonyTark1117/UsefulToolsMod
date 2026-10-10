import com.stonytark.usefultools.lighting.LightField;
import com.stonytark.usefultools.lighting.SourcePolicy;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
public final class LightFieldTest {
    private static void close(double got, double expected) { if (Math.abs(got - expected) > 1e-9) throw new AssertionError(got + " != " + expected); }
    public static void main(String[] args) throws Exception {
        LightField field = new LightField(); Map<String, LightField.Source> sources = new HashMap<String, LightField.Source>();
        sources.put("worn", new LightField.Source(.5, .5, .5, 15, 30)); field.update(sources);
        int[] distances = {0, 10, 15, 20, 29, 30, 31}; double[] expected = {15, 10, 7.5, 5, .5, 0, 0};
        for (int i = 0; i < distances.length; i++) close(field.light(.5 + distances[i], .5, .5), expected[i]);
        sources.put("hand", new LightField.Source(.5, .5, .5, 12, 12)); field.update(sources); close(field.light(.5, .5, .5), 15);
        sources.put("second armor", new LightField.Source(.5, .5, .5, 15, 30)); field.update(sources); close(field.light(20.5, .5, .5), 5);
        int original = 0xD70021; int packed = field.packed(original, 20.5, .5, .5);
        if ((packed & ~255) != (original & ~255) || (packed & 255) != 80) throw new AssertionError("Packed skylight was altered");
        if (field.sodium(0xABCDE001, 20, 0, 0) != 0xABCDE005) throw new AssertionError("Sodium cache flags altered");
        field.drainDirty(); field.update(sources); if (!field.drainDirty().isEmpty()) throw new AssertionError("Stationary source rebuilt sections");
        sources.remove("worn"); sources.remove("second armor"); field.update(sources); close(field.light(20.5, .5, .5), 0);
        if (field.drainDirty().isEmpty()) throw new AssertionError("Removing light failed to invalidate former sections");
        sources.put("moving", new LightField.Source(-31.5, -63.5, -47.5, 15, 30)); field.update(sources); close(field.light(-31.5, -63.5, -47.5), 15);
        for (int x : new int[]{-1875000,-1,0,1875000}) for (int y : new int[]{-64,-1,0,64}) for (int z : new int[]{-1875000,-1,0,1875000}) {
            long key = LightField.section(x,y,z); if (LightField.sectionX(key)!=x || LightField.sectionY(key)!=y || LightField.sectionZ(key)!=z) throw new AssertionError("Signed section bounds");
        }
        AtomicReference<Throwable> failure = new AtomicReference<Throwable>();
        Thread renderWorker = new Thread(() -> { try { for (int i=0;i<100000;i++) { double value=field.light(.5,.5,.5); if (value<0 || value>15) throw new AssertionError("Torn render snapshot"); } } catch(Throwable error) { failure.set(error); } });
        renderWorker.start(); for(int i=0;i<100;i++) { field.clear(); field.update(sources); } renderWorker.join(); if(failure.get()!=null) throw new AssertionError(failure.get());
        field.clear(); close(field.light(-31.5,-63.5,-47.5),0);
        for (String armor : new String[]{"helmet","chestplate","leggings","boots"}) if(SourcePolicy.level("usefultoolsmod:hglow_"+armor,true)!=15) throw new AssertionError("Armor slot missing");
        if (SourcePolicy.level("usefultoolsmod:ectoplasm_lantern",true)!=0 || SourcePolicy.level("usefultoolsmod:spectral_infuser",false)!=0 || SourcePolicy.level("othermod:hglow",false)!=0) throw new AssertionError("Unrequested source");
        if(SourcePolicy.radius("minecraft:glowstone",false)!=15 || SourcePolicy.radius("usefultoolsmod:hglow",false)!=30) throw new AssertionError("Reference radii");
        System.out.println("PASS: linear radius, max composition, removal, negative sections, snapshot concurrency, source policy, sky/cache preservation");
    }
}
