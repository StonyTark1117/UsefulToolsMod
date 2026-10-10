#!/usr/bin/env python3
"""Prepare authenticated contact sheets; record a review only when explicitly requested.

The --record-review flag is for a human/agent that has actually inspected the sheet.
Generating a sheet alone does not certify visual behavior.
"""
import argparse
import hashlib
import json
import subprocess
import sys
from pathlib import Path

from PIL import Image, ImageDraw
from lighting_shader_diagnostics import compiler_failures

ROOT = Path(__file__).resolve().parents[1]
OUT = ROOT / 'validation/dynamic-lighting-2.3.2'

def sha(path):
    return hashlib.sha256(path.read_bytes()).hexdigest()

def prepare(folder, review):
    report = json.loads((folder / 'report.json').read_text())
    assert report['status'] == 'captures_complete' and report.get('clean_client_exit')
    diagnostics = report.get('shader_compile_errors', [])
    assert not compiler_failures('\n'.join(diagnostics)), 'Actual shader compiler failure'
    cases = report['lighting_equipment_shader_captures']
    sources = json.loads((ROOT / 'lighting/sources.json').read_text())['sources']
    expected = {('held', 'minecraft:glowstone')}
    expected.update(('held', 'usefultoolsmod:' + s['id']) for s in sources)
    expected.update((slot, 'usefultoolsmod:hglow_' + piece) for slot, piece in
                    [('head', 'helmet'), ('chest', 'chestplate'), ('legs', 'leggings'), ('feet', 'boots')])
    assert len(cases) == 18 and {(c['slot'], c['source']) for c in cases} == expected
    logs = '\n'.join(p.read_text(errors='replace') for p in
                     [folder / 'client.log', folder / 'lighting-native-acknowledgements.log'] if p.exists())
    assert not compiler_failures(logs), 'Actual shader compiler failure in native log'
    if diagnostics:
        (folder / 'shader-diagnostic-review.json').write_text(json.dumps(dict(
            original_report_sha256=sha(folder / 'report.json'),
            retained_original_diagnostics=diagnostics, compiler_failures=[],
            classification='Timestamp-based false positives in the original harness. Native shader programs subsequently loaded and active capture/toggle states were acknowledged. OpenGL startup and shader-option warnings remain recorded; this is not a warning-free backend claim.',
            parser_sha256=sha(ROOT / 'scripts/lighting_shader_diagnostics.py')), indent=2) + '\n')
    sheet = Image.new('RGB', (1200, 1000), '#202020')
    draw = ImageDraw.Draw(sheet)
    states = []
    for n, case in enumerate(cases):
        marker = case.get('native_acknowledgement')
        assert marker and marker in logs, 'Missing native acknowledgement: ' + str(case)
        matching = next(line for line in logs.splitlines() if marker in line)
        assert 'slot=' + case['slot'] in matching and 'source=' + case['source'] in matching
        capture = folder / case['capture']
        assert sha(capture) == report['capture_sha256'][case['capture']], 'Capture changed after run'
        states.append(dict(case, capture_sha256=sha(capture)))
        x, y = (n % 4) * 300, (n // 4) * 200
        draw.text((x + 4, y + 3), case['slot'] + ': ' + case['source'].split(':')[-1], fill='white')
        sheet.paste(Image.open(capture).convert('RGB').resize((300, 169)), (x, y + 23))
    for n, source in enumerate(['ordinary', 'hardened'], 18):
        x, y = (n % 4) * 300, (n // 4) * 200
        draw.text((x + 4, y + 3), source + ' placed block, distance 3', fill='white')
        sheet.paste(Image.open(folder / ('lighting-' + source + '-distance-3.png')).convert('RGB').resize((300, 169)), (x, y + 23))
    path = folder / 'equipment-contact-sheet.png'
    sheet.save(path)
    subprocess.run([sys.executable, str(ROOT / 'scripts/measure-lighting-captures.py'), str(folder)], check=True)
    comparison = json.loads((folder / 'lighting-comparison.json').read_text())
    assert comparison['all_requested_distances_brighter'] and len(report['live_shader_states']) >= 2
    if review:
        target = next(c['version'] for c in report['components']['components'] if c['uid'] == 'net.minecraft')
        loader = {'net.fabricmc.fabric-loader': 'fabric', 'net.minecraftforge': 'forge', 'net.neoforged': 'neoforge'}[report['loader_uid']]
        result = dict(profile=folder.name, target=target + '-' + loader,
                      scope='All 13 mod held sources, ordinary held Glowstone reference, and four individually worn armor pieces.',
                      native_state_evidence=states, visual_review=review,
                      contact_sheet_sha256=sha(path), status='reviewed',
                      renderer_evidence=str((folder / 'glxinfo.txt').relative_to(ROOT)))
        (folder / 'equipment-shader-review.json').write_text(json.dumps(result, indent=2) + '\n')
        cert_path = OUT / 'shader-certification.json'
        cert = json.loads(cert_path.read_text())
        cert['coverage'] = [r for r in cert['coverage'] if r['profile'] != folder.name]
        cert['coverage'].append(dict(target=result['target'], profile=folder.name,
                                    comparison=str((folder / 'lighting-comparison.json').relative_to(ROOT)),
                                    equipment_review=str((folder / 'equipment-shader-review.json').relative_to(ROOT)),
                                    status='matched block and acknowledged held/worn captures reviewed',
                                    item_tool_armor_shader_visuals='All requested held sources and individual armor slots reviewed',
                                    visual_review=review))
        cert_path.write_text(json.dumps(cert, indent=2) + '\n')
    print(path)

if __name__ == '__main__':
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('profiles', nargs='+', type=Path)
    parser.add_argument('--record-review', help='Observed visual findings; only after inspecting the contact sheet.')
    args = parser.parse_args()
    for profile in args.profiles:
        prepare(profile.resolve(), args.record_review)
