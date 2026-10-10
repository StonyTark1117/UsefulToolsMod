"""Read the maintained loader pin rather than inferring it from passing runs."""
import re
from pathlib import Path
ROOT=Path(__file__).resolve().parents[1]
def pinned_loader(target):
    version,loader=target.rsplit('-',1)
    if target=='1.7.10-forge':
        build=(ROOT/'1.7.10/build.gradle.kts').read_text()
        assert 'retrofuturagradle") version "1.4.9"' in build and 'mcVersion.set("1.7.10")' in build
        return '10.13.4.1614'
    text=(ROOT/version/loader/'gradle.properties').read_text()
    key={'fabric':'loader_version','forge':'forge_version','neoforge':'neo_version'}[loader]
    match=re.search(r'^\s*'+key+r'\s*=\s*([^\n#]+)',text,re.M)
    assert match,('Missing maintained loader pin',target,key)
    return match.group(1).strip()
