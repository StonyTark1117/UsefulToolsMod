import sys
import unittest
from pathlib import Path

sys.path.insert(0, str(Path(__file__).resolve().parents[1] / 'scripts'))
from lighting_shader_diagnostics import compiler_failures

class ShaderDiagnosticsTest(unittest.TestCase):
    def test_actual_compiler_failures(self):
        failures = ['[19:56:10] ERROR: 0:23: undeclared identifier',
                    '[19:56:10] 0:8(1): error: extension directive',
                    "[GlDevice] Couldn't compile fragment shader oculus:pack/final",
                    '[Shaders] 0(23) : error C0000: syntax error',
                    '[Shaders] 0:23: error: unknown type',
                    '[Shaders] Error compiling fragment shader',
                    '[Shaders] Shader compilation failed',
                    '[Shaders] Invalid program gbuffers_terrain']
        self.assertEqual(failures, compiler_failures('\n'.join(failures)))

    def test_startup_gl_and_option_warnings_are_not_compiler_failures(self):
        log = ('[04Oct2026 19:56:10.269] OpenGL API ERROR: 1 (GL_INVALID_OPERATION)\n'
               '[04Oct2026 19:56:15.002] [Shaders] OpenGL error: 1282, program: none\n'
               '[04Oct2026 19:56:17.002] [OptiFine] Error: Invalid argument type, function: >')
        self.assertEqual([], compiler_failures(log))

if __name__ == '__main__':
    unittest.main()
