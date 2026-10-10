#!/usr/bin/env python3
"""Private ForgeOculus 1.8.8 diagnostic: use its native PBR tracker consistently."""
import argparse,hashlib,json,struct,zipfile
from pathlib import Path
p=argparse.ArgumentParser(description=__doc__);p.add_argument('source',type=Path);p.add_argument('output',type=Path);a=p.parse_args()
old=b'net/irisshaders/iris/texture/TextureTracker';new=b'net/irisshaders/iris/pbr/TextureTracker'
def rewrite(data):
 assert data[:4]==b'\xca\xfe\xba\xbe'
 count=struct.unpack('>H',data[8:10])[0];out=bytearray(data[:10]);offset=10;index=1;changed=0
 lengths={3:4,4:4,5:8,6:8,7:2,8:2,9:4,10:4,11:4,12:4,15:3,16:2,17:4,18:4,19:2,20:2}
 while index<count:
  tag=data[offset];offset+=1;out.append(tag)
  if tag==1:
   n=struct.unpack('>H',data[offset:offset+2])[0];offset+=2;value=data[offset:offset+n];offset+=n
   if old in value:changed+=1;value=value.replace(old,new)
   out.extend(struct.pack('>H',len(value)));out.extend(value)
  else:
   n=lengths[tag];out.extend(data[offset:offset+n]);offset+=n
  index+=2 if tag in (5,6) else 1
 out.extend(data[offset:]);return bytes(out),changed
changes=[];a.output.parent.mkdir(parents=True,exist_ok=True)
with zipfile.ZipFile(a.source) as source,zipfile.ZipFile(a.output,'w',zipfile.ZIP_DEFLATED) as output:
 for info in source.infolist():
  data=source.read(info.filename)
  if info.filename.endswith('.class') and info.filename!=old.decode()+'.class':
   revised,count=rewrite(data)
   if count:changes.append(dict(path=info.filename,constant_pool_references=count));data=revised
  output.writestr(info,data)
assert changes and any(row['path'].endswith('MixinAbstractTexture.class') for row in changes)
sha=lambda p:hashlib.sha256(p.read_bytes()).hexdigest()
result=dict(source=str(a.source.resolve()),source_sha256=sha(a.source),artifact=str(a.output.resolve()),artifact_sha256=sha(a.output),changed_classes=changes,method='Replace only constant-pool tracker owner/descriptors outside the unused old tracker class. No methods, world lighting, shader code, culling, metadata, or resources changed.',source_locations=['https://github.com/peaksuperior885/ForgeOculus/blob/Forge-1.21.1/src/main/java/net/irisshaders/iris/mixin/texture/MixinAbstractTexture.java','https://github.com/peaksuperior885/ForgeOculus/blob/Forge-1.21.1/src/main/java/net/irisshaders/iris/pbr/texture/PBRTextureManager.java'])
a.output.with_suffix('.json').write_text(json.dumps(result,indent=2)+'\n');print(json.dumps(result,indent=2))
