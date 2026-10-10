"""Bounded archive recovery: write only a complete recorded SHA-256 match.

Compressed payloads, ordering and other metadata remain unchanged. A failed
timestamp experiment cannot certify a replacement artifact or any capture.
"""
from datetime import datetime, timedelta
import argparse
import hashlib
import json
from pathlib import Path
import struct
import time
import zipfile

root = Path('validation/shader-support-2.3.2/recovery/implementation')
parser = argparse.ArgumentParser(description=__doc__)
parser.add_argument('--target')
parser.add_argument('--source', type=Path)
parser.add_argument('--inventory', type=Path, default=root / 'build/matrix-floor13-artifacts.json')
parser.add_argument('--start', default='2026-10-04T16:00:00')
parser.add_argument('--window-seconds', type=int, default=960)
parser.add_argument('--max-seconds', type=int, default=40)
parser.add_argument('--output', type=Path, default=root / 'research/exact-archive-timestamp-recovery.json')
args = parser.parse_args()
if args.source and not args.target:
    parser.error('--source requires an exact target')
if not 1 <= args.max_seconds <= 60:
    parser.error('Recovery experiments must remain bounded to at most 60 seconds')
records = json.loads(args.inventory.read_text())['artifacts']
deadline = time.monotonic() + args.max_seconds
results = []
for record in records:
    if not args.target and record['target'] not in ('1.20.1-forge', '1.20.1-neoforge', '1.20.2-forge', '1.20.2-neoforge'):
        continue
    if args.target and record['target'] != args.target:
        continue
    source = args.source or Path(record['artifact'])
    data = bytearray(source.read_bytes())
    with zipfile.ZipFile(source) as archive:
        entries = archive.infolist()
        central = archive.start_dir
    offsets = []
    for entry in entries:
        if data[central:central + 4] != b'PK\x01\x02':
            raise ValueError('Central directory layout differs')
        name, extra, comment = struct.unpack_from('<HHH', data, central + 28)
        if entry.date_time[0] != 1980:
            offsets.append((entry.header_offset + 10, central + 12, datetime(*entry.date_time)))
        central += 46 + name + extra + comment
    newest = max(stamp for _, _, stamp in offsets)
    attempts = 0
    recovered = None
    for mode in ('relative', 'uniform'):
        for seconds in range(0, args.window_seconds, 2):
            if time.monotonic() >= deadline:
                break
            timestamp = datetime.fromisoformat(args.start) + timedelta(seconds=seconds)
            for local, directory, old in offsets:
                date = timestamp + (old - newest if mode == 'relative' else timedelta())
                dos_time = (date.hour << 11) | (date.minute << 5) | (date.second // 2)
                dos_date = ((date.year - 1980) << 9) | (date.month << 5) | date.day
                packed = struct.pack('<HH', dos_time, dos_date)
                data[local:local + 4] = packed
                data[directory:directory + 4] = packed
            attempts += 1
            if hashlib.sha256(data).hexdigest() == record['artifact_sha256']:
                destination = (Path('/home/braydon/.cache/useful-tools-spectral-recovery/restored-inputs')
                               / record['artifact_sha256'] / source.name)
                destination.parent.mkdir(parents=True, exist_ok=True)
                destination.write_bytes(data)
                recovered = dict(path=str(destination), mode=mode, timestamp=str(timestamp))
                break
        if recovered or time.monotonic() >= deadline:
            break
    results.append(dict(target=record['target'], source=str(source),
                        source_sha256=hashlib.sha256(source.read_bytes()).hexdigest(),
                        expected_sha256=record['artifact_sha256'], attempts=attempts, recovered=recovered))
    print(record['target'], attempts, recovered, flush=True)
    if time.monotonic() >= deadline:
        break
args.output.write_text(json.dumps(dict(
    scope='Reconstruction experiment; only a full original SHA-256 match is accepted. '
          'No missing artifact requirement relaxed.', results=results), indent=2) + '\n')
