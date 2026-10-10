#!/usr/bin/env python3
"""Prepare hash-checked spectral contact sheets without certifying their contents."""
import argparse
import hashlib
import json
from pathlib import Path

from PIL import Image, ImageDraw


def digest(path):
    return hashlib.sha256(path.read_bytes()).hexdigest()


def prepare(directory):
    report_path = directory / 'report.json'
    report = json.loads(report_path.read_text())
    names = [name for name in report.get('captures', [])
             if name.startswith(('ghost-', 'wraith-', 'surfaces-'))]
    verified = {}
    for name in names:
        source = directory / name
        actual = digest(source)
        if actual != report.get('capture_sha256', {}).get(name):
            raise ValueError('Capture changed or lacks recorded hash: ' + name)
        verified[name] = actual
    sheets = []
    for offset in range(0, len(names), 6):
        sheet = Image.new('RGB', (1280, 1140), '#222222')
        draw = ImageDraw.Draw(sheet)
        for index, name in enumerate(names[offset:offset + 6]):
            x, y = index % 2 * 640, index // 2 * 380
            draw.text((x + 5, y + 4), name, fill='white')
            with Image.open(directory / name) as source:
                source = source.convert('RGB')
                source.thumbnail((640, 355))
                sheet.paste(source, (x, y + 24))
        destination = directory / ('review-panel-' + str(offset // 6) + '.jpg')
        sheet.save(destination, quality=95)
        sheets.append({'path': destination.name, 'sha256': digest(destination),
                       'sources': names[offset:offset + 6]})
    (directory / 'review-sheets.json').write_text(json.dumps({
        'report_sha256': digest(report_path), 'source_capture_sha256': verified,
        'sheets': sheets, 'visual_review': 'not performed by this tool'}, indent=2) + '\n')
    print(directory, len(names), 'verified captures;', len(sheets), 'review sheets')


if __name__ == '__main__':
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('directories', nargs='+', type=Path)
    arguments = parser.parse_args()
    for directory in arguments.directories:
        prepare(directory.resolve())
