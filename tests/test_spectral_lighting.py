"""Run the production optional-integration helpers against mutable API fixtures."""
from pathlib import Path
import subprocess
import tempfile
import unittest

ROOT = Path(__file__).resolve().parents[1]
PACKAGE = 'com.stonytark.usefultoolsmod.entity.client'


class SpectralLightingTests(unittest.TestCase):
    def test_modern_live_state_and_independent_light(self):
        helpers = [ROOT / version / loader / 'src/main/java' / Path(PACKAGE.replace('.', '/'))
                   / 'SpectralShaderSupport.java'
                   for version in ('1.20.1', '1.20.2', '1.21.1', '26.1.2', '26.2')
                   for loader in ('forge', 'fabric', 'neoforge')]
        # All copies must implement the same policy; loader hooks are compiled by the matrix.
        sources = {path.read_text() for path in helpers}
        self.assertEqual(1, len(sources))
        fixtures = {
            'net/irisshaders/iris/api/v0/IrisApi.java': '''package net.irisshaders.iris.api.v0;
public final class IrisApi {
 public static boolean enabled; public static boolean broken;
 private static final IrisApi INSTANCE = new IrisApi();
 public static IrisApi getInstance() { return INSTANCE; }
 public boolean isShaderPackInUse() { if (broken) throw new IllegalStateException(); return enabled; }
}''',
            'net/optifine/shaders/Shaders.java': '''package net.optifine.shaders;
public final class Shaders { public static boolean shaderPackLoaded; }''',
            'dev/oculusforge/client/OculusClientService.java': '''package dev.oculusforge.client;
public final class OculusClientService {
 public boolean active; public boolean shadersActive() { return active; }
}''',
            'dev/oculusforge/OculusForge.java': '''package dev.oculusforge;
public final class OculusForge {
 private final dev.oculusforge.client.OculusClientService clientService = new dev.oculusforge.client.OculusClientService();
 public void activate(boolean active) { clientService.active = active; }
}''',
            'net/minecraftforge/fml/ModList.java': '''package net.minecraftforge.fml;
public final class ModList {
 public static Object mod;
 public static java.util.Optional<Object> getModObjectById(String id) { return java.util.Optional.ofNullable(mod); }
}''',
            'Main.java': '''package com.stonytark.usefultoolsmod.entity.client;
public final class Main {
 private static void check(boolean condition) { if (!condition) throw new AssertionError(); }
 public static void main(String[] args) {
  check(SpectralShaderSupport.blockLightLevel(2) == 2);
  check(SpectralShaderSupport.wraithBlockLightLevel(2) == 2);
  check(SpectralShaderSupport.shouldUseVanillaEmissive(true, false, false));
  net.irisshaders.iris.api.v0.IrisApi.enabled = true;
  int expected = Integer.parseInt(args[0]);
  check(SpectralShaderSupport.blockLightLevel(2) == 10);
  check(SpectralShaderSupport.wraithBlockLightLevel(2) == expected);
  check(SpectralShaderSupport.wraithBlockLightLevel(15) == 15);
  check(!SpectralShaderSupport.shouldUseVanillaEmissive(true, false, false));
  net.irisshaders.iris.api.v0.IrisApi.broken = true;
  check(!SpectralShaderSupport.isShaderPackInUse());
  net.optifine.shaders.Shaders.shaderPackLoaded = true;
  check(SpectralShaderSupport.isShaderPackInUse());
  net.optifine.shaders.Shaders.shaderPackLoaded = false;
  dev.oculusforge.OculusForge mod = new dev.oculusforge.OculusForge();
  net.minecraftforge.fml.ModList.mod = mod;
  mod.activate(true); check(SpectralShaderSupport.isShaderPackInUse());
  mod.activate(false); check(!SpectralShaderSupport.isShaderPackInUse());
  check(!SpectralShaderSupport.shouldUseVanillaEmissive(false, false, false));
  check(!SpectralShaderSupport.shouldUseVanillaEmissive(true, true, false));
  check(!SpectralShaderSupport.shouldUseVanillaEmissive(true, false, true));
 }
}''',
        }
        with tempfile.TemporaryDirectory() as directory:
            root = Path(directory)
            helper = root / 'SpectralShaderSupport.java'
            helper.write_text(next(iter(sources)))
            paths = [helper]
            for name, source in fixtures.items():
                path = root / name; path.parent.mkdir(parents=True, exist_ok=True)
                path.write_text(source); paths.append(path)
            subprocess.run(['javac', '--release', '17', '-d', str(root), *map(str, paths)], check=True,
                           capture_output=True)
            for floor, expected in ((10,10), (11,11), (12,12), (13,13), (14,14), (15,15), (0,10), (99,15)):
                subprocess.run(['java', '-Dusefultools.wraithShaderLight='+str(floor), '-cp', str(root),
                                PACKAGE+'.Main', str(expected)], check=True, capture_output=True)
            subprocess.run(['java', '-cp', str(root), PACKAGE+'.Main', '13'],
                           check=True, capture_output=True)
            # A fresh classloader/JVM with every optional API absent must load successfully.
            for name in fixtures:
                if name == 'Main.java': continue
                (root / name.replace('.java', '.class')).unlink()
            helper_main = root / 'Main.java'
            helper_main.write_text('''package com.stonytark.usefultoolsmod.entity.client;
public final class Main { public static void main(String[] args) {
 if (SpectralShaderSupport.isShaderPackInUse() || SpectralShaderSupport.wraithBlockLightLevel(2) != 2)
  throw new AssertionError();
}}''')
            subprocess.run(['javac', '-cp', str(root), '-d', str(root), str(helper_main)], check=True, capture_output=True)
            subprocess.run(['java', '-cp', str(root), PACKAGE+'.Main'], check=True, capture_output=True)

    def test_classic_preserves_sky_light_and_disabled_brightness(self):
        helper = ROOT / '1.7.10/src/main/java/com/stonytark/usefultoolsmod/classic/client/SpectralShaderSupport.java'
        with tempfile.TemporaryDirectory() as directory:
            root = Path(directory)
            (root / 'SpectralShaderSupport.java').write_text(helper.read_text())
            (root / 'Shaders.java').write_text('package shadersmod.client; public class Shaders { public static boolean shaderPackLoaded; }')
            (root / 'Main.java').write_text('''import com.stonytark.usefultoolsmod.classic.client.SpectralShaderSupport;
public class Main { public static void main(String[] args) {
 int original = (7 << 20) | (2 << 4);
 if (SpectralShaderSupport.wraithPackedLight(original) != original) throw new AssertionError();
 shadersmod.client.Shaders.shaderPackLoaded = true;
 if (SpectralShaderSupport.wraithPackedLight(original) != ((7 << 20) | (14 << 4))) throw new AssertionError();
 if (SpectralShaderSupport.packedLight(original) != ((7 << 20) | (10 << 4))) throw new AssertionError();
 shadersmod.client.Shaders.shaderPackLoaded = false;
 if (SpectralShaderSupport.wraithPackedLight(original) != original) throw new AssertionError();
}}''')
            subprocess.run(['javac', '--release', '8', '-d', str(root), *map(str, root.glob('*.java'))], check=True, capture_output=True)
            subprocess.run(['java', '-Dusefultools.wraithShaderLight=14', '-cp', str(root), 'Main'], check=True, capture_output=True)

    def test_classic_shader_arms_preserve_native_model_on_toggle(self):
        source = ROOT / '1.7.10/src/main/java/com/stonytark/usefultoolsmod/classic/client'
        with tempfile.TemporaryDirectory() as directory:
            root = Path(directory)
            for name in ('ModelWraith.java', 'SpectralShaderSupport.java'):
                (root / name).write_text((source / name).read_text())
            fixtures = {
                'Entity.java': 'package net.minecraft.entity; public class Entity {}',
                'Shaders.java': 'package shadersmod.client; public class Shaders { public static boolean shaderPackLoaded; }',
                'ModelBase.java': '''package net.minecraft.client.model;
public class ModelBase { public int textureWidth, textureHeight;
 public void render(net.minecraft.entity.Entity e,float l,float a,float t,float y,float p,float s) {} }''',
                'ModelRenderer.java': '''package net.minecraft.client.model;
public class ModelRenderer { public static int rendered; public boolean mirror;
 public float rotateAngleX,rotateAngleY,rotateAngleZ;
 public ModelRenderer(ModelBase b,int u,int v) {}
 public ModelRenderer setTextureOffset(int u,int v) { return this; }
 public void addBox(float x,float y,float z,int w,int h,int d) {}
 public void setRotationPoint(float x,float y,float z) {}
 public void render(float scale) { rendered++; } }''',
                'Main.java': '''import com.stonytark.usefultoolsmod.classic.client.ModelWraith;
import net.minecraft.client.model.ModelRenderer;
public class Main { public static void main(String[] args) {
 ModelWraith model=new ModelWraith();
 for (int i=0;i<3;i++) {
  shadersmod.client.Shaders.shaderPackLoaded=i==1;
  ModelRenderer.rendered=0; model.render(null,0,0,20,0,0,.0625F);
  if (ModelRenderer.rendered!=(i==1?5:3)) throw new AssertionError("native silhouette or shader arms changed");
 }
}}''',
                'NoShaders.java': '''import com.stonytark.usefultoolsmod.classic.client.ModelWraith;
import com.stonytark.usefultoolsmod.classic.client.SpectralShaderSupport;
import net.minecraft.client.model.ModelRenderer;
public class NoShaders { public static void main(String[] args) {
 int nativeLight=(7<<20)|(2<<4);
 if (SpectralShaderSupport.isShaderPackInUse() || SpectralShaderSupport.wraithPackedLight(nativeLight)!=nativeLight)
  throw new AssertionError("optional shader absence changed native lighting");
 new ModelWraith().render(null,0,0,20,0,0,.0625F);
 if (ModelRenderer.rendered!=3) throw new AssertionError("optional shader absence changed native model");
}}''',
            }
            for name, text in fixtures.items():
                (root / name).write_text(text)
            subprocess.run(['javac', '--release', '8', '-d', str(root),
                            *map(str, root.glob('*.java'))], check=True, capture_output=True)
            subprocess.run(['java', '-cp', str(root), 'Main'], check=True, capture_output=True)
            (root / 'shadersmod/client/Shaders.class').unlink()
            subprocess.run(['java', '-cp', str(root), 'NoShaders'], check=True, capture_output=True)


if __name__ == '__main__':
    unittest.main()
