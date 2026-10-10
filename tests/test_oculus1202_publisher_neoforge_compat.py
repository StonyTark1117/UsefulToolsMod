import importlib.util
import tempfile
import unittest
from pathlib import Path
from zipfile import ZipFile

ROOT = Path(__file__).resolve().parents[1]
SCRIPT = ROOT / "scripts/build-oculus1202-publisher-neoforge-compat.py"
SPEC = importlib.util.spec_from_file_location("oculus_compat", SCRIPT)
MODULE = importlib.util.module_from_spec(SPEC)
SPEC.loader.exec_module(MODULE)


class PublisherCompatTest(unittest.TestCase):
    def test_deterministic_identity_and_provenance(self):
        publisher = Path.home() / ".cache/useful-tools-spectral-recovery/matrix-inputs" / MODULE.PUBLISHER_SHA / "oculus-mc1.20.2-1.8.0.jar"
        native = ROOT / "validation/shader-support-2.3.2/recovery/implementation/candidate-dependencies" / MODULE.NATIVE_SHA / "oculus-mc1.20.2-1.8.0-neoforge-qa2.jar"
        neoforge = Path.home() / ".gradle/caches/modules-2/files-2.1/net.neoforged/neoforge/20.2.93/2178eb0db26807ae60b0fefa97c281f992601152/neoforge-20.2.93-universal.jar"
        fml = Path.home() / ".gradle/caches/modules-2/files-2.1/net.neoforged.fancymodloader/loader/1.0.16/47bd8344813542922e606b034623172a8c271da6/loader-1.0.16.jar"
        with tempfile.TemporaryDirectory() as first, tempfile.TemporaryDirectory() as second:
            one, two = Path(first) / "compat.jar", Path(second) / "compat.jar"
            result_one = MODULE.build(publisher, native, neoforge, fml, one)
            result_two = MODULE.build(publisher, native, neoforge, fml, two)
            self.assertEqual(result_one["sha256"], result_two["sha256"])
            with ZipFile(one) as archive:
                metadata = archive.read("META-INF/mods.toml")
                provenance = archive.read(MODULE.PROVENANCE_NAME)
                contents = [archive.read(name) for name in archive.namelist() if not name.endswith("/")]
            self.assertIn(b'version="1.8.0"', metadata)
            self.assertNotIn(b"neoforge-qa2", metadata)
            self.assertIn(MODULE.PUBLISHER_SHA.encode(), provenance)
            self.assertFalse(any(b"net/minecraftforge" in data for data in contents))


if __name__ == "__main__":
    unittest.main()
