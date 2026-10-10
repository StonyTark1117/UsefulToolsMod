#!/usr/bin/env python3
"""Check that the 26.x providers preserve the custom 2.3.2 device models."""

import argparse
import dataclasses
import importlib.util
from pathlib import Path
import sys

ROOT = Path(__file__).resolve().parent.parent
spec = importlib.util.spec_from_file_location('device_datagen', ROOT / 'scripts/audit-datagen.py')
module = importlib.util.module_from_spec(spec)
sys.modules[spec.name] = module
spec.loader.exec_module(module)


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--target', action='append', choices=[n for n in module.TARGETS if n.startswith('26.')])
    args = parser.parse_args()
    names = args.target or [n for n in module.TARGETS if n.startswith('26.')]
    options = argparse.Namespace(timeout=600, verbose=False,
                                 output_dir=ROOT / 'validation/device-models-2.3.2/datagen')
    original_popen = module.subprocess.Popen

    def popen(command, **kwargs):
        # Client datagen needs model resources, not music/sound objects. Use the
        # installed Minecraft artifacts and omit NeoGradle's unrelated download.
        if isinstance(command, list) and command[0] == './gradlew':
            if kwargs['cwd'].name == 'neoforge':
                version = kwargs['cwd'].parent.name
                revision = '1' if version == '26.1.2' else '2'
                command[1:1] = ['-x', f'neoFormJoined{version}-{revision}DownloadAssets']
            elif kwargs['cwd'].name == 'fabric':
                command[1:1] = ['-x', 'downloadAssets']
        return original_popen(command, **kwargs)

    module.subprocess.Popen = popen
    success = True
    for name in names:
        target = module.TARGETS[name]
        if name.endswith('fabric'):
            target = dataclasses.replace(target, java_home='/home/braydon/.gradle/jdks/eclipse_adoptium-25-amd64-linux.2')
        else:
            target = dataclasses.replace(target, tasks=('runClientData',))
        success = module.audit_target(name, target, options) and success
    return 0 if success else 1


if __name__ == '__main__':
    raise SystemExit(main())
