#!/usr/bin/env python3
"""Generate task-specific 3D devices for all maintained Useful Tools clients."""

from __future__ import annotations

import argparse
import importlib.util
import json
from pathlib import Path
import struct
import zlib

ROOT = Path(__file__).resolve().parent.parent
VERSIONS = ('1.20.1', '1.20.2', '1.21.1', '26.1.2', '26.2')
LOADERS = ('forge', 'fabric', 'neoforge')
ITEMS = ('sticky_dynamite', 'remote_detonator', 'spectral_resonator',
         'mining_charge', 'ectoplasm_lantern')
FACES = ('down', 'up', 'north', 'south', 'west', 'east')
COLORS = ((170, 38, 29), (91, 159, 58), (70, 62, 47), (240, 125, 30),
          (38, 43, 47), (141, 153, 159), (32, 187, 193), (172, 125, 56),
          (205, 43, 35), (25, 65, 62), (147, 236, 225), (70, 80, 84),
          (213, 222, 224), (69, 78, 81), (47, 52, 37), (226, 201, 146))


def load_generator(filename: str):
    spec = importlib.util.spec_from_file_location(filename, ROOT / 'scripts' / filename)
    assert spec and spec.loader
    module = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(module)
    return module


explosives = load_generator('sync-2.3.2-explosive-models.py')
blocks = load_generator('sync-2.3.1-block-assets.py')


def uv(material: int) -> list[float]:
    x, y = material % 4 * 4, material // 4 * 4
    return [x + .25, y + .25, x + 3.75, y + 3.75]


def cube(start, end, material):
    return {'from': start, 'to': end,
            'faces': {face: {'texture': '#material', 'uv': uv(material)} for face in FACES}}


def geometry(name: str) -> list[dict]:
    if name == 'sticky_dynamite':
        parts = explosives.geometry('dynamite')
        # Adhesive straps and pads distinguish it from the paper-bound instant explosive.
        mapping = {0: 0, 1: 1, 2: 2, 3: 3}
        for part in parts:
            old_uv = part['faces']['up']['uv']
            material = next(i for i in mapping if old_uv == explosives.uv(i))
            for face in part['faces'].values():
                face['uv'] = uv(mapping[material])
        parts += [cube([5, 5.2, 4.35], [10.5, 8.5, 4.8], 1),
                  cube([5, 5.2, 12.7], [10.5, 8.5, 13.15], 1)]
        return parts
    if name == 'remote_detonator':
        return [cube([4.5, 2, 5.5], [11.5, 11.5, 9.5], 4),
                cube([4.3, 2, 5.3], [11.7, 3, 9.7], 5),
                cube([4.3, 10.7, 5.3], [11.7, 11.7, 9.7], 5),
                cube([5.2, 7.2, 5.1], [10.8, 10.3, 5.5], 11),
                cube([5.7, 7.7, 4.95], [10.3, 9.8, 5.1], 9),
                cube([6.1, 8.4, 4.8], [6.6, 9.2, 4.95], 10),
                cube([7.3, 8.4, 4.8], [7.8, 9.2, 4.95], 10),
                cube([8.5, 8.4, 4.8], [9, 9.2, 4.95], 10),
                cube([6.3, 3.8, 5.1], [9.7, 6.2, 5.5], 5),
                cube([6.7, 4.2, 4.6], [9.3, 5.8, 5.1], 8),
                cube([5.2, 6.4, 5.05], [6.1, 6.9, 5.5], 1),
                cube([9.4, 11.7, 7], [10.2, 15, 7.8], 5),
                cube([9.2, 14.6, 6.8], [10.4, 15.4, 8], 4),
                cube([4.2, 4, 6.5], [4.5, 9.5, 8.5], 11)]
    if name == 'spectral_resonator':
        parts = [cube([6, 1.5, 6], [10, 6.5, 10], 4),
                 cube([5.5, 5.5, 5.5], [10.5, 7, 10.5], 7),
                 cube([4.5, 7, 4.5], [11.5, 8, 11.5], 5),
                 cube([4.5, 13.5, 4.5], [11.5, 14.5, 11.5], 7),
                 cube([6, 8.4, 6], [10, 12.8, 10], 6),
                 cube([6.7, 12.8, 6.7], [9.3, 13.5, 9.3], 10),
                 cube([7.1, 14.5, 7.1], [8.9, 15.5, 8.9], 6),
                 cube([6.2, 3.2, 5.5], [9.8, 5, 6], 7),
                 cube([6.7, 3.6, 5.2], [9.3, 4.6, 5.5], 9),
                 cube([7.7, 3.7, 5.05], [8.3, 4.5, 5.2], 10),
                 cube([10, 3.5, 7], [11.2, 4.7, 9], 7)]
        for x in (4.5, 10.7):
            for z in (4.5, 10.7):
                parts.append(cube([x, 8, z], [x + .8, 13.5, z + .8], 7))
        return parts
    # Classic has no JSON block models. Match the modern silhouette using opaque
    # materials; retain its native bounded placed blocks and collision behavior.
    if name == 'mining_charge':
        return [cube([3, 0, 3], [13, 2, 13], 12),
                cube([4, 2, 4], [12, 5, 12], 4),
                cube([4, 3, 3.75], [12, 4, 12.25], 5),
                cube([5, 5, 5], [11, 5.75, 11], 13),
                cube([6.5, 5.76, 6.5], [9.5, 5.77, 9.5], 8)]
    if name == 'ectoplasm_lantern':
        parts = [cube([4, 0, 4], [12, 2, 12], 4),
                 cube([4, 14, 4], [12, 16, 12], 4),
                 cube([5, 3, 5], [11, 13, 11], 6),
                 cube([6.5, 4, 4.9], [9.5, 11, 5], 10),
                 cube([6.5, 4, 11], [9.5, 11, 11.1], 10)]
        for x in (4, 11):
            for z in (4, 11):
                parts.append(cube([x, 2, z], [x + 1, 14, z + 1], 4))
        return parts
    raise ValueError(name)


def model(name: str) -> dict:
    if name == 'mining_charge':
        return blocks.ITEM_MODELS[name]
    if name in ('ectoplasm_lantern', 'soul_lantern'):
        return blocks.ITEM_MODELS['soul_lantern']
    result = explosives.model('dynamite')
    result['credit'] = 'Useful Tools 2.3.2 task-specific device geometry'
    result['textures'] = {'particle': f'usefultoolsmod:item/{name}',
                          'material': 'usefultoolsmod:item/device_materials'}
    result['elements'] = geometry(name)
    if name in ('remote_detonator', 'spectral_resonator'):
        result['display']['firstperson_righthand']['rotation'] = [0, 155, 15]
        result['display']['firstperson_lefthand']['rotation'] = [0, -155, -15]
    return result


def texture() -> bytes:
    def chunk(kind, data):
        return struct.pack('>I', len(data)) + kind + data + struct.pack('>I', zlib.crc32(kind + data))
    rows = bytearray()
    for y in range(16):
        rows.append(0)
        for x in range(16):
            shade = (-7, 0, 4, 0)[(x + y * 3) % 4]
            rows.extend((*[max(0, min(255, c + shade)) for c in COLORS[y // 4 * 4 + x // 4]], 255))
    return (b'\x89PNG\r\n\x1a\n' + chunk(b'IHDR', struct.pack('>IIBBBBB', 16, 16, 8, 6, 0, 0, 0))
            + chunk(b'IDAT', zlib.compress(rows)) + chunk(b'IEND', b''))


def classic_geometry() -> bytes:
    lines = ['package com.stonytark.usefultoolsmod.classic.client;', '',
             '/** Generated by scripts/sync-2.3.2-device-models.py. */',
             'final class DeviceGeometry {', '    private DeviceGeometry() {}']
    for name in ITEMS:
        lines.append(f'    static final float[][] {name.upper()} = {{')
        for part in geometry(name):
            values = part['from'] + part['to'] + part['faces']['up']['uv']
            lines.append('        {' + ', '.join(f'{v:g}F' for v in values) + '},')
        lines.append('    };')
    lines.append('}')
    return ('\n'.join(lines) + '\n').encode()


def outputs() -> dict[Path, bytes]:
    result = {}
    for version in VERSIONS:
        for loader in LOADERS:
            assets = ROOT / version / loader / 'src/main/resources/assets/usefultoolsmod'
            result[assets / 'textures/item/device_materials.png'] = texture()
            for name in (*ITEMS, 'soul_lantern'):
                result[assets / f'models/item/{name}.json'] = (json.dumps(model(name), indent=2) + '\n').encode()
    classic = ROOT / '1.7.10/src/main'
    result[classic / 'resources/assets/usefultoolsmod/textures/items/device_materials.png'] = texture()
    result[classic / 'java/com/stonytark/usefultoolsmod/classic/client/DeviceGeometry.java'] = classic_geometry()
    return result


def main() -> int:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--check', action='store_true')
    args = parser.parse_args()
    stale = []
    for path, data in outputs().items():
        if args.check:
            if not path.is_file() or path.read_bytes() != data:
                stale.append(str(path.relative_to(ROOT)))
        else:
            path.parent.mkdir(parents=True, exist_ok=True)
            path.write_bytes(data)
    if stale:
        print('\n'.join(stale))
        return 1
    print('Device models: all 16 targets current')
    return 0


if __name__ == '__main__':
    raise SystemExit(main())
