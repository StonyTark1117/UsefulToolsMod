#!/usr/bin/env python3
"""Derive Forge Mixin selectors from that target's resolved MCP-to-SRG mapping, not a hand-written API table."""
import json,sys,re
from pathlib import Path
mapping,source,output=map(Path,sys.argv[1:4])
sources=[source,*map(Path,sys.argv[4:])]
classes={};owner=None
for line in mapping.read_text().splitlines():
 if line.startswith('tsrg'): continue
 if line and not line.startswith('\t'):
  a=line.split();owner=a[0];classes[owner]={}
 elif line.startswith('\t') and not line.startswith('\t\t'):
  a=line.split()
  if len(a)==3 and a[1].startswith('('): classes[owner][a[0]+a[1]]=a[2]
result={}
for path in (path for folder in sources for path in folder.rglob('*Mixin.java')):
 text=path.read_text()
 target=re.search(r'@Mixin\((\w+)\.class\)',text)
 if not target:continue
 short=target[1]
 fullname=re.search(r'import ([\w.]+\.'+short+');',text)[1].replace('.','/')
 methods=classes[fullname];selectors={}
 for selector in re.findall(r'@Inject\(method\s*=\s*"([^"]+)"',text):
  name=selector.split('(')[0]
  matches=[(key,val) for key,val in methods.items() if key.split('(')[0]==name and ('(' not in selector or key==selector)]
  if len(matches)!=1:raise RuntimeError(f'{path}: ambiguous or missing selector {selector}: {matches}')
  key,val=matches[0];desc=key[len(name):]
  selectors[selector]='L'+fullname+';'+val+desc
 result['com/stonytark/usefultools/lighting/mixin/'+path.stem]=selectors
output.parent.mkdir(parents=True,exist_ok=True)
output.write_text(json.dumps({'mappings':result,'data':{'searge':result}},indent=2)+'\n')
