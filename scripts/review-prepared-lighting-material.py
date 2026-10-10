#!/usr/bin/env python3
"""Review saved, previously authenticated material comparisons without relaunching clients."""
import argparse
import hashlib
import json
import math
import shutil
import zipfile
from pathlib import Path
from PIL import Image, ImageStat
from lighting_pins import pinned_loader

ROOT = Path(__file__).resolve().parents[1]
OUT = ROOT / 'validation/dynamic-lighting-2.3.2'


def sha(path):
    return hashlib.sha256(path.read_bytes()).hexdigest()


def mean(path, roi):
    image = Image.open(path).convert('RGB')
    assert image.size == (854, 480)
    return sum(v * w for v, w in zip(ImageStat.Stat(image.crop(roi)).mean,
                                    [.2126, .7152, .0722])) / 255


def review(profile, observation):
    folder = OUT / 'packaged' / profile
    prepared = folder / 'material-emission-comparison.json'
    result = json.loads(prepared.read_text())
    assert result['native_profile'] == profile
    assert result['all_required_metrics_passed'] and result['only_13_emission_alphas_changed']
    assert result['status'] == 'material metrics passed; visual review pending'
    # This path requires a saved success from the full ZIP/alpha audit. It cannot
    # prepare a missing comparison or bypass a failed original audit.
    successful = []
    for path in OUT.glob('material-comparison-preparation*.json'):
        rows = json.loads(path.read_text())
        if isinstance(rows, list):
            successful.extend((path, row) for row in rows if isinstance(row, dict)
                              and row.get('native') == profile
                              and row.get('control') == result['zero_alpha_profile']
                              and row.get('exit_code') == 0)
    assert successful, 'No saved successful full input/alpha audit'
    folders = [folder, OUT / 'packaged' / result['zero_alpha_profile']]
    reports = [json.loads((f / 'report.json').read_text()) for f in folders]
    assert [sha(f / 'report.json') for f in folders] == result['report_sha256']
    for f, report, captures in zip(folders, reports, result['capture_sha256']):
        assert report['status'] == 'captures_complete' and report['clean_client_exit']
        assert not report['shader_compile_errors']
        assert report['loader_version'] == pinned_loader(result['target'])
        assert report['shader_pack'] == result['shader_pack'] and report['shader_settings'] == result['settings']
        assert all(command['accepted'] for command in report['commands'])
        assert 'transformed=2' in report['lighting_material_control']['acknowledgement']
        assert len(report['lighting_equipment_shader_captures']) == 18
        assert any(state['active'] for state in report['live_shader_states'])
        assert any(not state['active'] for state in report['live_shader_states'])
        logs = (f / 'lighting-native-acknowledgements.log').read_text()
        for case in report['lighting_equipment_shader_captures']:
            assert case['native_acknowledgement'] in logs
        for capture, expected in captures.items():
            assert sha(f / capture) == expected == report['capture_sha256'][capture]
    assert reports[0]['lighting_material_control']['agent_sha256'] == reports[1]['lighting_material_control']['agent_sha256']
    for report, key in zip(reports, ['native_artifact_sha256', 'zero_alpha_artifact_sha256']):
        artifact = next(v for i, v in enumerate(report['invocation'])
                        if i and report['invocation'][i - 1] == '--artifact')
        assert report['artifacts'][artifact] == result[key]
    for sheet in result['visual_review_sheets']:
        assert sha(ROOT / sheet['path']) == sheet['sha256']
    for row in result['distance_metrics']:
        assert row['roi'] == [250, 355, 600, 420]
        for key, f, source in [('ordinary', folders[0], 'ordinary'),
                               ('native_hardened', folders[0], 'hardened'),
                               ('zero_alpha_hardened', folders[1], 'hardened')]:
            assert math.isclose(mean(f / f'lighting-{source}-distance-{row["distance"]}.png', row['roi']), row[key], abs_tol=1e-14)
    block = result['closeup_face_metrics']
    assert block['roi'] == [374, 229, 480, 316]
    for key, f, source in [('ordinary', folders[0], 'ordinary'),
                           ('native_hardened', folders[0], 'hardened'),
                           ('zero_alpha_hardened', folders[1], 'hardened')]:
        assert math.isclose(mean(f / f'lighting-{source}-distance-3.png', block['roi']), block[key], abs_tol=1e-14)
    assert block['native_hardened'] > block['ordinary'] and block['native_hardened'] > block['zero_alpha_hardened']
    assert len(result['equipment_metrics']) == 15
    for row in result['equipment_metrics']:
        assert row['roi'] == ([365, 265, 428, 346] if row['slot'] == 'held' else [320, 100, 535, 465])
        case = next(c for c in reports[0]['lighting_equipment_shader_captures']
                    if (c['slot'], c['source']) == (row['slot'], row['source']))
        for key, f in zip(['native', 'zero_alpha'], folders):
            assert math.isclose(mean(f / case['capture'], row['roi']), row[key], abs_tol=1e-14)
        assert row['native'] > row['zero_alpha']
    linked = result['required_companion_distance_evidence']
    normal_folder = OUT / 'packaged' / linked['profile']
    normal = json.loads((normal_folder / 'report.json').read_text())
    assert sha(normal_folder / 'report.json') == linked['report_sha256']
    assert normal['status'] == 'captures_complete' and normal['clean_client_exit']
    assert normal['shader_pack'] == result['shader_pack'] and normal['shader_settings'] == result['settings']
    assert normal['loader_version'] == pinned_loader(result['target'])
    certified = next(row for row in json.loads((OUT / 'shader-certification.json').read_text())['coverage']
                     if row['profile'] == linked['profile'])
    equipment_review = ROOT / certified['equipment_review']
    assert sha(equipment_review) == linked['equipment_review_sha256']
    review_data = json.loads(equipment_review.read_text())
    assert review_data['status'] == 'reviewed'
    assert any(path.exists() and sha(path) == review_data['contact_sheet_sha256']
               for path in [normal_folder / 'equipment-contact-sheet.png',
                            normal_folder / 'equipment-review-contact-sheet.png'])
    assert [row['distance'] for row in linked['distance_metrics']] == [10, 15, 20, 29]
    for row in linked['distance_metrics']:
        for source in ['ordinary', 'hardened']:
            name = f'lighting-{source}-distance-{row["distance"]}.png'
            assert sha(normal_folder / name) == normal['capture_sha256'][name]
            assert math.isclose(mean(normal_folder / name, [250, 355, 600, 420]), row[source], abs_tol=1e-14)
        assert row['hardened'] > row['ordinary']
    # A cached ZIP audit cannot certify changed production classes. Link the
    # actual tested QA package and release to the recorded per-class digests,
    # then compare every digest with the current release, including added classes.
    prior = next(row for row in json.loads((OUT / 'production-class-equivalence.json').read_text())
                 if row['target'] == result['target'])
    proofs = json.loads((OUT / 'reviewed-runtime-class-equivalence.json').read_text())
    for name, report_path in [(profile, folder / 'report.json'), (linked['profile'], normal_folder / 'report.json')]:
        proof = next(row for row in proofs if row['profile'] == name)
        assert proof['report_sha256'] == sha(report_path) and not proof['different_production_classes']
        assert proof['observed_package_sha256'] == prior['native_acceptance_sha256']
        assert proof['current_release_sha256'] == prior['release_sha256']
    current = next(row for row in json.loads((OUT / 'artifact-audit.json').read_text())
                   if row['target'] == result['target'])
    release = ROOT / current['companion']
    assert sha(release) == current['companion_sha256']
    with zipfile.ZipFile(release) as jar:
        digests = {name: hashlib.sha256(jar.read(name)).hexdigest()
                   for name in jar.namelist() if name.endswith('.class')}
    assert digests == prior['production_class_sha256'], 'Production classes changed; run current native controls'
    original_hash = sha(prepared)
    archive = OUT / 'prepared-material-audits' / (original_hash + '.json')
    archive.parent.mkdir(exist_ok=True)
    if not archive.exists():shutil.copy2(prepared, archive)
    assert sha(archive) == original_hash
    result.update(status='isolated material emission and held/worn visuals verified', visual_review=observation,
                  saved_audit_revalidation={'prepared_sha256':original_hash,
                    'prepared_archive':str(archive.relative_to(ROOT)),
                    'full_input_audit_records':[{ 'path':str(path.relative_to(ROOT)), 'sha256':sha(path)} for path, row in successful],
                    'current_release_sha256':current['companion_sha256'],
                    'meaning':'The original successful ZIP/13-alpha audit is retained. Reports, captures, measurements, distance controls and current production class digests were rechecked. Removed temporary input bytes were not recreated or represented as present.'})
    prepared.write_text(json.dumps(result, indent=2) + '\n')
    cert = OUT / 'material-emission-certification.json'
    entries = json.loads(cert.read_text())
    entries['coverage'] = [row for row in entries['coverage'] if (row['target'], row['shader']) != (result['target'], result['shader'])]
    entries['coverage'].append({key:result[key] for key in ['target', 'shader', 'native_profile', 'zero_alpha_profile', 'status']}
                              | {'evidence':str(prepared.relative_to(ROOT))})
    cert.write_text(json.dumps(entries, indent=2) + '\n')
    print(result['target'], result['shader'], result['status'])


if __name__ == '__main__':
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('profile')
    parser.add_argument('--record-review', required=True, help='Actual visual findings after inspecting both sheets and close-up')
    args = parser.parse_args()
    review(args.profile, args.record_review)
