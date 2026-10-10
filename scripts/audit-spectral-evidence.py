#!/usr/bin/env python3
"""Check one exact configuration's saved evidence against explicit requirements."""
import argparse
import hashlib
import json
from pathlib import Path


def audit(directory, expected):
    report_path = directory / 'report.json'
    if not report_path.is_file():
        return ['missing report']
    report = json.loads(report_path.read_text())
    gaps = []
    for field in ('loader_uid', 'loader_version', 'mode'):
        if report.get(field) != expected[field]:
            gaps.append('wrong ' + field)
    if report.get('status') != 'captures_complete' or not report.get('clean_client_exit'):
        gaps.append('run or shutdown incomplete')
    if report.get('framebuffer') != '1280x720x24' or not report.get('wayland_unset') or report.get('audio_backend') != 'ALSOFT_DRIVERS=null':
        gaps.append('isolation missing')
    if report.get('visual_verdict') != 'pass':
        gaps.append('visual review incomplete')
    if report.get('shader_compile_errors'):
        gaps.append('shader compilation errors remain')
    if not report.get('commands') or any(not command.get('accepted') for command in report.get('commands', [])):
        gaps.append('rejected scene command')
    weather = report.get('weather_control')
    if weather:
        for name, expected_hash in weather.get('files_sha256', {}).items():
            source = directory / 'weather-control' / name
            if not source.is_file() or hashlib.sha256(source.read_bytes()).hexdigest() != expected_hash:
                gaps.append('native weather fixture missing or changed: ' + name)
        checked = {row.get('entity') for row in report.get('native_weather_checks', [])
                   if row.get('raining') is True and row.get('acknowledged') is True}
        for entity in ('ghost', 'wraith'):
            if entity + '-rain.png' in expected['captures'] and entity not in checked:
                gaps.append('native rain state not acknowledged: ' + entity)
    for artifact, digest in expected['artifacts'].items():
        if report.get('artifacts', {}).get(artifact) != digest:
            gaps.append('artifact hash mismatch: ' + artifact)
    if set(report.get('artifacts', {})) != set(expected['artifacts']):
        gaps.append('unexpected or missing runtime artifact')
    if expected['mode'] == 'active':
        if report.get('shader_pack') != expected['shader_pack'] or not report.get('shader_activation_logged'):
            gaps.append('pack activation or hash mismatch')
    states = report.get('live_shader_states', [])
    if not states or any(state['active'] != (expected['mode'] == 'active') for state in states) and not expected.get('toggles'):
        gaps.append('live shader state missing or wrong')
    if expected.get('toggles') and {state['active'] for state in states} != {True, False}:
        gaps.append('toggle states incomplete')
    for name in expected['captures']:
        path = directory / name
        recorded = report.get('capture_sha256', {}).get(name)
        if name not in report.get('captures', []) or not path.is_file():
            gaps.append('missing fresh capture: ' + name)
        elif hashlib.sha256(path.read_bytes()).hexdigest() != recorded:
            gaps.append('capture hash mismatch: ' + name)
        if expected.get('per_capture_review'):
            review=report.get('visual_reviews',{}).get(name,{})
            if review.get('verdict')!='pass' or review.get('capture_sha256')!=recorded or not review.get('note'):
                gaps.append('capture visual review missing or stale: '+name)
        if expected.get('full_framebuffer'):
            frame = directory / (Path(name).stem + '-framebuffer.png')
            if not frame.is_file() or hashlib.sha256(frame.read_bytes()).hexdigest() != report.get('framebuffer_sha256', {}).get(frame.name):
                gaps.append('full framebuffer missing or changed: ' + name)
    for relative, digest in expected.get('source_sha256', {}).items():
        if report.get('source_sha256', {}).get(relative) != digest:
            gaps.append('source provenance mismatch: ' + relative)
    return gaps


if __name__ == '__main__':
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('directory', type=Path)
    parser.add_argument('requirements', type=Path)
    args = parser.parse_args()
    gaps = audit(args.directory, json.loads(args.requirements.read_text()))
    print(json.dumps({'complete': not gaps, 'gaps': gaps}, indent=2))
    raise SystemExit(bool(gaps))
