#!/usr/bin/env python3
"""Prepare a private, explicitly modified BSL 8.4.01.1 classic test fixture.

The publisher archive remains untouched. This fixes its missing semicolon in
the MC_VERSION < 11605 branch; it does not certify the unmodified release.
"""
import argparse
import hashlib
import json
from pathlib import Path
import zipfile

ORIGINAL = '1dc06e6e161da44540ae21fe3f7d9e94dba85e7efa8e4215ac6c1bd9f12a76f2'
ENTRY = 'shaders/program/gbuffers_skybasic.glsl'


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('publisher_archive', type=Path)
    parser.add_argument('output', type=Path)
    args = parser.parse_args()
    digest = lambda p: hashlib.sha256(p.read_bytes()).hexdigest()
    if digest(args.publisher_archive) != ORIGINAL:
        raise SystemExit('Publisher archive hash mismatch')
    if args.output.exists() or args.output.resolve() == args.publisher_archive.resolve():
        raise SystemExit('Output must be a new private archive')
    with zipfile.ZipFile(args.publisher_archive) as source:
        old = source.read(ENTRY)
        needle = b'alpha = 0.0\r\n'
        if old.count(needle) != 1:
            raise SystemExit('Expected classic-only syntax error was not found')
        new = old.replace(needle, b'alpha = 0.0;\r\n')
        args.output.parent.mkdir(parents=True, exist_ok=True)
        with zipfile.ZipFile(args.output, 'w') as target:
            for info in source.infolist():
                target.writestr(info, new if info.filename == ENTRY else source.read(info))
    record = {'publisher': 'https://modrinth.com/shader/bsl-shaders/version/9NNQ6mPZ',
              'publisher_archive_sha256': ORIGINAL, 'modified_archive': str(args.output.resolve()),
              'modified_archive_sha256': digest(args.output), 'changed_entry': ENTRY,
              'original_entry_sha256': hashlib.sha256(old).hexdigest(),
              'modified_entry_sha256': hashlib.sha256(new).hexdigest(),
              'change': 'Add one missing semicolon in the pre-1.16.5 vertex branch.',
              'status': 'Private modified fixture prepared; runtime not verified',
              'unmodified_release': 'Remains failed; retain its compiler evidence.'}
    args.output.with_suffix('.provenance.json').write_text(json.dumps(record, indent=2)+'\n')
    print(json.dumps(record, indent=2))


if __name__ == '__main__':
    main()
