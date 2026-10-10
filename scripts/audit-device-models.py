#!/usr/bin/env python3
"""Verify 2.3.2 geometry, opaque UVs, and the assets packaged by all targets."""

from __future__ import annotations

import argparse
import importlib.util
import json
import os
from pathlib import Path
import re
import subprocess
import sys
import zipfile
import zlib

ROOT = Path(__file__).resolve().parent.parent
spec = importlib.util.spec_from_file_location('explosive_assets', ROOT / 'scripts/sync-2.3.2-device-models.py')
assert spec and spec.loader
assets = importlib.util.module_from_spec(spec)
spec.loader.exec_module(assets)
OUT = ROOT / 'validation/device-models-2.3.2'


def method_body(source: str, declaration: str) -> str:
    start = source.index('{', source.index(declaration))
    depth = 1
    end = start + 1
    while depth:
        depth += (source[end] == '{') - (source[end] == '}')
        end += 1
    return re.sub(r'\s+', '', source[start:end])


def main() -> int:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--assemble', action='store_true', help='assemble all 16 targets before the JAR checks')
    parser.add_argument('--sources-only', action='store_true')
    parser.add_argument('--offline', action='store_true', help='use cached Gradle dependencies')
    parser.add_argument('--target', action='append', help='limit assembly/JAR checks to these targets')
    args = parser.parse_args()
    OUT.mkdir(parents=True, exist_ok=True)
    errors = []
    expected = assets.outputs()
    for path, data in expected.items():
        if not path.is_file() or path.read_bytes() != data:
            errors.append('stale asset: ' + str(path.relative_to(ROOT)))

    # Verify the contract that matters for these new solid models: every face is
    # present, volumes and UVs are bounded, and every sampled texel is opaque.
    png = assets.texture()
    compressed = bytearray()
    offset = 8
    while offset < len(png):
        length = int.from_bytes(png[offset:offset + 4], 'big')
        if png[offset + 4:offset + 8] == b'IDAT':
            compressed.extend(png[offset + 8:offset + 8 + length])
        offset += length + 12
    pixels = zlib.decompress(compressed)
    assert len(pixels) == 16 * 65 and all(pixels[y * 65] == 0 for y in range(16))
    for name in assets.ITEMS:
        if name in ('mining_charge', 'ectoplasm_lantern'):
            model = assets.model(name)
            assert model['parent'] == f'usefultoolsmod:block/{name}'
            assert all(s > 0 for s in model['display']['firstperson_lefthand']['scale'])
            assert all(s > 0 for s in model['display']['firstperson_righthand']['scale'])
            continue
        model = assets.model(name)
        assert 'parent' not in model and len(model['elements']) >= 10
        for part in model['elements']:
            assert all(0 <= a < b <= 16 for a, b in zip(part['from'], part['to']))
            assert set(part['faces']) == set(assets.FACES)
            for face in part['faces'].values():
                assert face['texture'] == '#material'
                u0, v0, u1, v1 = face['uv']
                assert 0 <= u0 < u1 <= 16 and 0 <= v0 < v1 <= 16
                for y in range(int(v0), int(v1) + 1):
                    for x in range(int(u0), int(u1) + 1):
                        assert pixels[y * 65 + 1 + x * 4 + 3] == 255
        for side in ('right', 'left'):
            assert all(s > 0 for s in model['display'][f'firstperson_{side}hand']['scale'])
            assert all(s > 0 for s in model['display'][f'thirdperson_{side}hand']['scale'])
        assert model['display']['ground']['rotation'][1] == 30

    for name in ('mining_charge', 'ectoplasm_lantern'):
        for part in assets.geometry(name):
            assert set(part['faces']) == set(assets.FACES)
            assert all(0 <= a < b <= 16 for a, b in zip(part['from'], part['to']))
    classic_entity = ROOT / '1.7.10/src/main/java/com/stonytark/usefultoolsmod/classic/EntityStickyDynamite.java'
    old_classic = subprocess.check_output(['git', 'show', 'HEAD:' + str(classic_entity.relative_to(ROOT))], cwd=ROOT, text=True)
    assert method_body(classic_entity.read_text(), 'void onUpdate()') == method_body(old_classic, 'void onUpdate()')
    for version in assets.VERSIONS:
        for loader in assets.LOADERS:
            root = ROOT / version / loader
            source = root / 'src/main/resources/assets/usefultoolsmod'
            entity = root / 'src/main/java/com/stonytark/usefultoolsmod/entity/custom/StickyDynamiteEntity.java'
            old_entity = subprocess.check_output(['git', 'show', 'HEAD:' + str(entity.relative_to(ROOT))], cwd=ROOT, text=True)
            assert method_body(entity.read_text(), 'void tick()') == method_body(old_entity, 'void tick()'), f'{root}: sticky fuse/attachment/explosion tick changed'
            assert 'ModelFace' in entity.read_text() and 'getModelOffset()' in entity.read_text()
            for name in ('mining_charge', 'ectoplasm_lantern'):
                parent = json.loads((source / f'models/block/{name}.json').read_text())
                assert parent.get('elements'), f'{version}-{loader}: empty block parent {name}'
            if version.startswith('26.'):
                generated = root / ('src/main/generated' if loader == 'fabric' else 'src/generated/resources/client') / 'assets/usefultoolsmod'
                for name in ('sticky_dynamite', 'remote_detonator', 'spectral_resonator'):
                    assert not (generated / f'models/item/{name}.json').exists(), f'duplicate flat geometry {root}/{name}'

    targets = [('1.7.10-forge', ROOT / '1.7.10', '/usr/lib/jvm/java-21-openjdk')]
    for version in assets.VERSIONS:
        for loader in assets.LOADERS:
            project = ROOT / version / loader
            if version.startswith('26.'):
                java = ('/home/braydon/.gradle/jdks/eclipse_adoptium-25-amd64-linux.2'
                        if loader == 'fabric' else '/usr/lib/jvm/java-21-openjdk')
            else:
                java = '/usr/lib/jvm/java-17-openjdk' if version.startswith('1.20') and loader != 'fabric' else '/usr/lib/jvm/java-21-openjdk'
            targets.append((f'{version}-{loader}', project, java))

    reports = []
    if args.target:
        unknown = set(args.target) - {name for name, _, _ in targets}
        if unknown:
            parser.error('unknown targets: ' + ', '.join(sorted(unknown)))
    for name, project, java in targets:
        if args.target and name not in args.target:
            continue
        if args.assemble:
            print('Assembling ' + name, flush=True)
            log = OUT / (name + '-assemble.log')
            env = {**os.environ, 'JAVA_HOME': java}
            command = ['./gradlew', '--no-daemon', '--max-workers=1', 'assemble']
            if args.offline:
                command.insert(1, '--offline')
            with log.open('w') as output:
                process = subprocess.run(command, cwd=project, env=env,
                                         stdout=output, stderr=subprocess.STDOUT, timeout=900)
            if process.returncode:
                errors.append('assemble failed: ' + name)
                print('FAILED ' + name + '; see ' + str(log), flush=True)
        if args.sources_only:
            continue
        jars = sorted((project / 'build/libs').glob('usefultoolsmod-*.jar'))
        jars = [p for p in jars if not any(s in p.name for s in ('-sources', '-dev', '-javadoc'))]
        if len(jars) != 1:
            errors.append('expected one base binary JAR: ' + name)
            continue
        jar = jars[0]
        if not zipfile.is_zipfile(jar):
            errors.append('invalid/interrupted binary JAR: ' + name)
            continue
        checked = []
        with zipfile.ZipFile(jar) as archive:
            source_jar = jar.with_name(jar.stem + '-sources.jar')
            # Loom remaps Yarn references in the shipped sources. Verify the
            # original source input from devlibs and the shipped model hook.
            original_sources = project / 'build/devlibs' / source_jar.name if name.endswith('-fabric') and name.startswith(('1.20', '1.21')) else source_jar
            keys = ('com/stonytark/usefultoolsmod/classic/EntityStickyDynamite.java',) if name == '1.7.10-forge' else (
                'com/stonytark/usefultoolsmod/entity/custom/StickyDynamiteEntity.java',
                'com/stonytark/usefultoolsmod/entity/client/StickyDynamiteRenderer.java')
            with zipfile.ZipFile(original_sources) as sources, zipfile.ZipFile(source_jar) as shipped_sources:
                for key in keys:
                    if sources.read(key) != (project / 'src/main/java' / key).read_bytes():
                        errors.append(name + ': stale packaged renderer/entity source ' + key)
                    assert b'getModelOffset' in shipped_sources.read(key)
            if name != '1.7.10-forge':
                renderer = 'com/stonytark/usefultoolsmod/entity/client/StickyDynamiteRenderer.class'
                if renderer not in archive.namelist():
                    errors.append(name + ': missing surface-offset renderer')
            for source, content in expected.items():
                if project not in source.parents or '/resources/' not in str(source):
                    continue
                resource = str(source).split('/resources/', 1)[1]
                if resource not in archive.namelist() or archive.read(resource) != content:
                    errors.append(f'{name}: missing/stale packaged {resource}')
                checked.append(resource)
            if name == '1.7.10-forge':
                for cls in ('DeviceGeometry', 'DeviceItemRenderer', 'RenderStickyDynamite', 'ExplosiveItemRenderer'):
                    key = f'com/stonytark/usefultoolsmod/classic/client/{cls}.class'
                    if key not in archive.namelist():
                        errors.append('missing classic renderer: ' + cls)
                    else:
                        compiled = project / 'build/classes/java/main' / key
                        # The release JAR is reobfuscated by RFG, so its class
                        # bytes legitimately differ from the development output.
                        # Verify the dev JAR's inputs and the shipped sources.
                        dev_jar = jar.with_name(jar.stem + '-dev.jar')
                        sources_jar = jar.with_name(jar.stem + '-sources.jar')
                        with zipfile.ZipFile(dev_jar) as dev, zipfile.ZipFile(sources_jar) as sources:
                            if not compiled.is_file() or dev.read(key) != compiled.read_bytes():
                                errors.append('stale packaged classic renderer: ' + cls)
                            source_key = key.replace('.class', '.java')
                            if sources.read(source_key) != (project / 'src/main/java' / source_key).read_bytes():
                                errors.append('stale classic renderer source: ' + cls)
                        assert archive.read(key)[:4] == b'\xca\xfe\xba\xbe'
            elif name.startswith('26.'):
                for item in (*assets.ITEMS, 'soul_lantern'):
                    dispatcher = json.loads(archive.read(f'assets/usefultoolsmod/items/{item}.json'))
                    assert dispatcher['model']['model'] == f'usefultoolsmod:item/{item}'
        reports.append({'target': name, 'jar': str(jar.relative_to(ROOT)), 'checked_resources': checked})
    scope = 'sources-only' if args.sources_only else 'selected' if args.target else 'all'
    report = {'success': not errors, 'scope': scope, 'geometry_and_opaque_uvs_verified': True,
              'sticky_gameplay_tick_unchanged': True,
              'targets': reports, 'errors': errors}
    report_name = 'asset-audit.json' if scope == 'all' else scope + '-asset-audit.json'
    (OUT / report_name).write_text(json.dumps(report, indent=2) + '\n')
    for error in errors:
        print(error, file=sys.stderr)
    print(f'Device model audit: {"PASS" if not errors else "FAIL"} ({len(reports)} packaged targets)', flush=True)
    return 0 if not errors else 1


if __name__ == '__main__':
    raise SystemExit(main())
