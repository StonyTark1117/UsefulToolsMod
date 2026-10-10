"""Recovery must authenticate exact bytes without modifying historical reports."""
import ast
import hashlib
from pathlib import Path
import tempfile
import unittest


class MaterialInputRecoveryTests(unittest.TestCase):
    def setUp(self):
        source = Path(__file__).resolve().parents[1] / 'scripts/audit-lighting-material-controls.py'
        module = ast.parse(source.read_text())
        functions = [node for node in module.body if isinstance(node, ast.FunctionDef)
                     and node.name in ('sha', 'base', 'preserved_base')]
        self.scope = dict(Path=Path, hashlib=hashlib, recovered_inputs={})
        exec(compile(ast.Module(body=functions, type_ignores=[]), str(source), 'exec'), self.scope)
        self.temporary = tempfile.TemporaryDirectory()
        self.addCleanup(self.temporary.cleanup)
        self.root = Path(self.temporary.name)
        self.original = self.root / 'removed-native.jar'
        self.saved = self.root / 'recovered-native.jar'
        self.saved.write_bytes(b'actual historical native artifact')
        self.expected = hashlib.sha256(self.saved.read_bytes()).hexdigest()
        self.report = dict(invocation=['harness', '--artifact', str(self.original)],
                           artifacts={str(self.original): self.expected})
        self.scope['recovered_inputs'][self.expected] = dict(path=str(self.saved), sha256=self.expected)

    def test_exact_recovery_leaves_original_report_unchanged(self):
        import copy
        previous = copy.deepcopy(self.report)
        self.assertEqual(self.saved, self.scope['preserved_base'](self.report))
        self.assertEqual(previous, self.report)
        self.assertFalse(self.original.exists())

    def test_tampered_recovered_bytes_are_rejected(self):
        self.saved.write_bytes(b'rebuilt but different artifact')
        with self.assertRaises(AssertionError):
            self.scope['preserved_base'](self.report)

    def test_wrong_manifest_identity_is_rejected(self):
        self.scope['recovered_inputs'][self.expected]['sha256'] = '0' * 64
        with self.assertRaises(AssertionError):
            self.scope['preserved_base'](self.report)

    def test_changed_existing_original_cannot_be_silently_replaced(self):
        self.original.write_bytes(b'changed current artifact')
        with self.assertRaises(AssertionError):
            self.scope['preserved_base'](self.report)
