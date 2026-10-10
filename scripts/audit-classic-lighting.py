#!/usr/bin/env python3
"""Run native 1.7.10 light propagation checks in a disposable server world."""
import importlib.util
import json
from pathlib import Path
import re
import subprocess
import sys

ROOT = Path(__file__).resolve().parent.parent
OUT = ROOT / 'validation/classic-lighting-2.3.2'
spec = importlib.util.spec_from_file_location('lighting_smoke', ROOT / 'scripts/smoke-server.py')
smoke = importlib.util.module_from_spec(spec)
sys.modules[spec.name] = smoke
spec.loader.exec_module(smoke)
original_popen = subprocess.Popen

def popen(command, *args, **kwargs):
    if command and command[0] == './gradlew':
        command = [*command[:1], '-I', str(ROOT / 'tests/classic-lighting/qa.gradle'), *command[1:]]
    return original_popen(command, *args, **kwargs)

subprocess.Popen = popen
smoke.READY_RE = re.compile('CLASSIC_LIGHTING_SERVER_PASS')
smoke.CRASH_RE = re.compile(smoke.CRASH_RE.pattern + '|CLASSIC_LIGHTING_FAIL', re.IGNORECASE)
sys.argv = [sys.argv[0], '1.7.10-forge', '--isolated-game-dir', '--port', '25672',
            '--output-dir', str(OUT), '--startup-timeout', '300']
code = smoke.main()
log = (OUT / '1.7.10-forge.log').read_text()
checks = re.findall(r'CLASSIC_LIGHTING_CHECK ([^\r\n]+)', log)
success = code == 0 and len(checks) == 14 and 'CLASSIC_LIGHTING_FAIL' not in log
(OUT / 'runtime-checks.json').write_text(json.dumps({'success': success, 'checks': checks}, indent=2) + '\n')
if __name__ == '__main__':
    raise SystemExit(0 if success else 1)
