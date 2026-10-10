#!/usr/bin/env python3
"""Verify block-update lighting on a real classic client in isolated Xvfb."""
import importlib.util
import json
from pathlib import Path
import subprocess
import sys
import time

ROOT = Path(__file__).resolve().parent.parent
OUT = ROOT / 'validation/classic-lighting-2.3.2/client'
spec = importlib.util.spec_from_file_location('lighting_client_smoke', ROOT / 'scripts/smoke-client.py')
smoke = importlib.util.module_from_spec(spec)
sys.modules[spec.name] = smoke
spec.loader.exec_module(smoke)
original_popen = subprocess.Popen

def popen(command, *args, **kwargs):
    if command and command[0] == './gradlew':
        command = [*command[:1], '-I', str(ROOT / 'tests/classic-lighting/qa.gradle'), *command[1:]]
    return original_popen(command, *args, **kwargs)

subprocess.Popen = popen
smoke.free_display = lambda: next(n for n in range(230, 240) if not Path(f'/tmp/.X11-unix/X{n}').exists())
original_xdo = smoke.xdo

def xdo(display, *parts, **kwargs):
    if parts and parts[0] == 'windowsize':
        parts = (*parts[:-2], '854', '480')
    return original_xdo(display, *parts, **kwargs)

smoke.xdo = xdo
smoke.wait_for_window_replacement = lambda display, previous, timeout: smoke.window_id(display, timeout)
original_quick_click = smoke.quick_click

def quick_click(display, x, y):
    if (x, y) == (206, 443):
        original_quick_click(display, 427, 250)
        time.sleep(0.3)
        original_quick_click(display, 427, 250)
        time.sleep(0.3)
    return original_quick_click(display, x, y)

smoke.quick_click = quick_click

def screenshot(display, path, window, game_dir):
    if path.name.endswith('-world.png'):
        deadline = time.monotonic() + 90
        while time.monotonic() < deadline:
            log_path = game_dir / 'logs/fml-client-latest.log'
            log = log_path.read_text(errors='replace') if log_path.exists() else ''
            (OUT / 'native-client.log').write_text(log)
            if 'CLASSIC_LIGHTING_FAIL' in log or 'CLASSIC_LIGHTING_CLIENT_FAIL' in log:
                raise RuntimeError('classic runtime light checks failed; see client log')
            if 'CLASSIC_LIGHTING_SERVER_PASS' in log and 'CLASSIC_LIGHTING_CLIENT_PASS' in log:
                break
            time.sleep(0.5)
        else:
            raise RuntimeError('classic runtime light checks timed out')
    subprocess.run(['magick', 'import', '-window', window, str(path)],
                   env={**smoke.os.environ, 'DISPLAY': display}, check=True)

smoke.screenshot = screenshot
sys.argv = [sys.argv[0], '1.7.10-forge', '--output-dir', str(OUT), '--timeout', '300']
args = smoke.parse_args()
result = smoke.run_target('1.7.10-forge', smoke.TARGETS['1.7.10-forge'], args)
log = (OUT / '1.7.10-forge.log').read_text()
success = result and 'CLASSIC_LIGHTING_SERVER_PASS' in log and 'CLASSIC_LIGHTING_CLIENT_PASS' in log
(OUT / 'lighting-checks.json').write_text(json.dumps({'success': success, 'server_and_client_pass': success}, indent=2) + '\n')
if __name__ == '__main__':
    raise SystemExit(0 if success else 1)
