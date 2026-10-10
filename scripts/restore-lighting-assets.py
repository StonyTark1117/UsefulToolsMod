#!/usr/bin/env python3
"""Restore hash-verified Minecraft asset objects from existing native loader caches."""
from pathlib import Path
import hashlib,os,json
cache=Path.home()/'.gradle/caches';dest=cache/'minecraft/assets/objects';dest.mkdir(parents=True,exist_ok=True)
roots=[cache/'neoformruntime/assets/objects',cache/'forge_gradle/assets/objects',cache/'fabric-loom/assets/objects']
linked=0;invalid=[]
for root in roots:
 if not root.exists():continue
 for path in root.glob('*/*'):
  if not path.is_file() or len(path.name)!=40:continue
  target=dest/path.name[:2]/path.name
  if target.exists():continue
  if hashlib.sha1(path.read_bytes()).hexdigest()!=path.name:invalid.append(str(path));continue
  target.parent.mkdir(parents=True,exist_ok=True)
  try:os.link(path,target);linked+=1
  except FileExistsError:pass
print(json.dumps({'linked_verified_objects':linked,'invalid_cached_objects':invalid,'destination':str(dest)}))
