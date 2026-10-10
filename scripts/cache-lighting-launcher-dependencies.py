#!/usr/bin/env python3
"""Recover missing native launcher artifacts using their published checksums."""
import argparse
import concurrent.futures
import hashlib
import json
import urllib.request
import uuid
from pathlib import Path

parser = argparse.ArgumentParser()
parser.add_argument('metadata', type=Path, nargs='+')
parser.add_argument('--output', type=Path, required=True)
args = parser.parse_args()
cache = Path.home() / '.local/share/PrismLauncher/libraries'
artifacts = {}
for metadata in args.metadata:
    content = json.loads(metadata.read_text())
    for library in content.get('libraries', []) + content.get('mavenFiles', []):
        download = library.get('downloads', {}).get('artifact')
        if not download or not download.get('sha1'):
            continue
        path = download.get('path')
        if not path:
            coordinate, _, extension = library['name'].partition('@')
            group, name, version, *classifier = coordinate.split(':')
            filename = name + '-' + version + ('-' + classifier[0] if classifier else '')
            path = '/'.join([group.replace('.', '/'), name, version, filename + '.' + (extension or 'jar')])
        assert not Path(path).is_absolute() and '..' not in Path(path).parts
        artifacts[path] = download

def recover(entry):
    path, download = entry
    destination = cache / path
    row = dict(path=path, url=download['url'], expected_sha1=download['sha1'])
    temporary = destination.with_name(destination.name + '.lighting-' + uuid.uuid4().hex)
    try:
        if destination.exists():
            assert hashlib.sha1(destination.read_bytes()).hexdigest() == download['sha1'], 'Existing cache checksum differs; preserved for investigation'
            return dict(row, status='existing checksum verified')
        data = urllib.request.urlopen(download['url'], timeout=45).read()
        actual = hashlib.sha1(data).hexdigest()
        assert actual == download['sha1'], 'Downloaded artifact checksum mismatch'
        destination.parent.mkdir(parents=True, exist_ok=True)
        temporary.write_bytes(data)
        if destination.exists():
            assert hashlib.sha1(destination.read_bytes()).hexdigest() == actual
        else:
            temporary.replace(destination)
        return dict(row, status='recovered', sha256=hashlib.sha256(data).hexdigest())
    except Exception as error:
        return dict(row, status='failed', error=str(error))
    finally:
        temporary.unlink(missing_ok=True)

with concurrent.futures.ThreadPoolExecutor(max_workers=4) as executor:
    results = list(executor.map(recover, artifacts.items()))
args.output.parent.mkdir(parents=True, exist_ok=True)
args.output.write_text(json.dumps(results, indent=2) + '\n')
print({status: sum(r['status'] == status for r in results) for status in ['existing checksum verified', 'recovered', 'failed']})
raise SystemExit(any(r['status'] == 'failed' for r in results))
