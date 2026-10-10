"""Exercise the optional community-port final-pass GLSL interface repair."""
from pathlib import Path
import subprocess
import tempfile
import unittest


ROOT = Path(__file__).resolve().parents[1]
HELPER = ROOT / '26.2/forge/src/lighting/java/com/stonytark/usefultools/lighting/CommunityPortShaderInterfaces.java'


class CommunityPortShaderInterfacesTests(unittest.TestCase):
    def test_interpolation_matches_pack_fragment_without_touching_other_outputs(self):
        fixture = '''import com.stonytark.usefultools.lighting.CommunityPortShaderInterfaces;
public final class Main {
    public static void main(String[] args) {
        String vertex = "#version 330 core\\n"
            + "out vec2 texCoord;\\n"
            + "flat out int materialId;\\n"
            + "out vec3 unrelated;\\n";
        String fragment = "#version 330 core\\n"
            + "noperspective in vec2 texCoord;\\n"
            + "flat in int materialId;\\n";
        String matched = CommunityPortShaderInterfaces.matchInterpolation(vertex, fragment);
        if (!matched.contains("noperspective out vec2 texCoord;")) throw new AssertionError(matched);
        if (!matched.contains("flat out int materialId;")) throw new AssertionError(matched);
        if (!matched.contains("out vec3 unrelated;")) throw new AssertionError(matched);
        if (matched.contains("flat flat") || !matched.equals(CommunityPortShaderInterfaces.matchInterpolation(matched, fragment)))
            throw new AssertionError("interpolation repair must be stable");
    }
}'''
        with tempfile.TemporaryDirectory() as directory:
            root = Path(directory)
            source = root / 'Main.java'
            source.write_text(fixture)
            subprocess.run(['javac', '--release', '17', '-d', str(root), str(HELPER), str(source)],
                           check=True, capture_output=True)
            subprocess.run(['java', '-cp', str(root), 'Main'], check=True, capture_output=True)


if __name__ == '__main__':
    unittest.main()
