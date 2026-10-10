"""Build and freeze the private publisher-body diagnostic agent; does not launch clients."""
from pathlib import Path
import subprocess, zipfile, hashlib, shutil, json, sys
root=Path('/tmp/usefultools-shaders-2.3.2')/sys.argv[1]
classes=root/'classes';classes.mkdir(parents=True,exist_ok=True)
jars=[]
for n in ['asm','asm-tree','asm-commons']:
    jars.append(next(p for p in (Path('/home/braydon/.gradle/caches/modules-2/files-2.1/org.ow2.asm')/n/'9.10.1').glob('*/*.jar') if p.name==n+'-9.10.1.jar'))
cp=':'.join(map(str,jars));java='/home/braydon/.gradle/jdks/eclipse_adoptium-25-amd64-linux.2/bin/'
sources=[Path('tests/spectral-qa/probe/OptifineGuiBridge.java'),Path('tests/spectral-qa/probe/RelocateDiagnosticAsm.java')]
subprocess.run([java+'javac','--release','17','-proc:none','-Xlint:all','-Werror','-cp',cp,'-d',str(classes),*map(str,sources)],check=True)
with zipfile.ZipFile(root/'unrelocated.jar','w',zipfile.ZIP_DEFLATED) as z:
    z.writestr('META-INF/MANIFEST.MF','Manifest-Version: 1.0\nPremain-Class: com.stonytark.usefultoolsmod.spectralqa.OptifineGuiBridge\n\n')
    for p in classes.rglob('*.class'):z.write(p,str(p.relative_to(classes)))
    for jar in jars:
        with zipfile.ZipFile(jar) as q:
            for n in q.namelist():
                if n.startswith('org/objectweb/asm/') and n.endswith('.class'):z.writestr(n,q.read(n))
subprocess.run([java+'java','-cp',str(classes)+':'+cp,'com.stonytark.usefultoolsmod.spectralqa.RelocateDiagnosticAsm',str(root/'unrelocated.jar'),str(root/'bridge.jar')],check=True)
h=hashlib.sha256((root/'bridge.jar').read_bytes()).hexdigest()
artifact_root=Path(sys.argv[2]).resolve() if len(sys.argv)>2 else Path('/tmp/usefultools-shaders-2.3.2/qa')
dest=artifact_root/h;dest.mkdir(parents=True,exist_ok=True)
shutil.copy2(root/'bridge.jar',dest/'bridge.jar')
for p in sources:shutil.copy2(p,dest/p.name)
(root/'build.json').write_text(json.dumps({'sha256':h,'sources':{str(p):hashlib.sha256(p.read_bytes()).hexdigest() for p in sources},'libraries':{str(p):hashlib.sha256(p.read_bytes()).hexdigest() for p in jars},'output':str(dest/'bridge.jar')},indent=2)+'\n')
print(h)
