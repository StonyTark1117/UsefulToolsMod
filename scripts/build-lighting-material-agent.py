#!/usr/bin/env python3
"""Build a Java 8 QA-only agent, with its ASM relocated away from game dependencies."""
import argparse, hashlib, json, struct, subprocess, tempfile, zipfile
from pathlib import Path
ROOT=Path(__file__).resolve().parents[1]
p=argparse.ArgumentParser(description=__doc__)
p.add_argument('--asm',type=Path,required=True)
p.add_argument('--javac',type=Path,default=Path('/usr/bin/javac'))
p.add_argument('--output',type=Path,default=ROOT/'tests/dynamic-lighting/material-agent/build/material-control-agent.jar')
a=p.parse_args()
old=b'org/objectweb/asm';new=b'com/stonytark/lightingqa/asm'
def relocate(data):
    assert data[:4]==b'\xca\xfe\xba\xbe'
    count=struct.unpack('>H',data[8:10])[0];out=bytearray(data[:10]);offset=10;index=1
    lengths={3:4,4:4,5:8,6:8,7:2,8:2,9:4,10:4,11:4,12:4,15:3,16:2,17:4,18:4,19:2,20:2}
    while index<count:
        tag=data[offset];offset+=1;out.append(tag)
        if tag==1:
            n=struct.unpack('>H',data[offset:offset+2])[0];offset+=2
            value=data[offset:offset+n].replace(old,new);offset+=n
            out.extend(struct.pack('>H',len(value)));out.extend(value)
        else:
            n=lengths[tag];out.extend(data[offset:offset+n]);offset+=n
        index+=2 if tag in (5,6) else 1
    out.extend(data[offset:]);return bytes(out)
source=ROOT/'tests/dynamic-lighting/material-agent/MaterialControlAgent.java'
with tempfile.TemporaryDirectory(prefix='useful-tools-lighting-material-agent-') as directory:
    classes=Path(directory)/'classes';classes.mkdir()
    subprocess.run([str(a.javac),'--release','8','-cp',str(a.asm),'-d',str(classes),str(source)],check=True)
    a.output.parent.mkdir(parents=True,exist_ok=True)
    with zipfile.ZipFile(a.output,'w',zipfile.ZIP_DEFLATED) as jar:
        jar.writestr('META-INF/MANIFEST.MF','Manifest-Version: 1.0\nPremain-Class: com.stonytark.lightingqa.MaterialControlAgent\n\n')
        for cls in sorted(classes.rglob('*.class')):jar.writestr(str(cls.relative_to(classes)),relocate(cls.read_bytes()))
        with zipfile.ZipFile(a.asm) as asm:
            for name in sorted(asm.namelist()):
                if name.startswith('org/objectweb/asm/') and name.endswith('.class'):
                    jar.writestr(name.replace(old.decode(),new.decode()),relocate(asm.read(name)))
record=dict(artifact=str(a.output.resolve()),sha256=hashlib.sha256(a.output.read_bytes()).hexdigest(),source_sha256=hashlib.sha256(source.read_bytes()).hexdigest(),asm_sha256=hashlib.sha256(a.asm.read_bytes()).hexdigest(),java_release=8,production_artifact=False)
a.output.with_suffix('.json').write_text(json.dumps(record,indent=2)+'\n');print(json.dumps(record,indent=2))
