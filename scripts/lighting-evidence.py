#!/usr/bin/env python3
"""Identify both native test bytecode and its source tree without claiming a pass."""
import hashlib,subprocess
from pathlib import Path
ROOT=Path(__file__).resolve().parents[1]
def identity(target):
 root=ROOT/'1.7.10' if target=='1.7.10-forge' else ROOT.joinpath(*target.rsplit('-',1))
 files=set()
 for folder in [root/'src/main',root/'src/lighting',ROOT/'lighting',ROOT/'tests/dynamic-lighting/targets'/target]:
  if folder.exists():files.update(p for p in folder.rglob('*') if p.is_file())
 for name in ['build.gradle','build.gradle.kts','gradle.properties']:
  if (root/name).exists():files.add(root/name)
 bytecode=[]
 for folder in [root/'build/classes',root/'build/resources',root/'build/sourceSets',root/'build/sourcesSets']:
  if folder.exists():bytecode.extend(p for p in folder.rglob('*') if p.is_file())
 def hashes(paths):return {str(p.relative_to(ROOT)):hashlib.sha256(p.read_bytes()).hexdigest() for p in sorted(paths)}
 sources=hashes(files);compiled=hashes(bytecode)
 return {'revision':subprocess.check_output(['git','rev-parse','HEAD'],cwd=ROOT,text=True).strip(),
  'source_files':sources,'source_manifest_sha256':hashlib.sha256(str(sorted(sources.items())).encode()).hexdigest(),
  'compiled_files':compiled,'compiled_manifest_sha256':hashlib.sha256(str(sorted(compiled.items())).encode()).hexdigest(),
  'artifacts':hashes((root/'build/libs').glob('*.jar'))}
