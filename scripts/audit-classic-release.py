#!/usr/bin/env python3
"""Verify the published Useful Tools v1.6 JAR against the migration table.

This audit is deliberately offline: callers supply the independently downloaded
JAR.  It uses javap to prove that each retained field is passed through the
published registerItem/registerBlock methods, then checks the exact mixed-case
unlocalized names recorded in CLASSIC_MIGRATION.md.
"""

from __future__ import annotations

import argparse
import hashlib
from pathlib import Path
import re
import subprocess
import sys
import zipfile


REPO_ROOT = Path(__file__).resolve().parent.parent
EXPECTED_SHA256 = "a1d8127563b1200f4d314f4cc3f560b638a1b78be200f02aae8597b69fc2a815"
ITEM_CLASS = "com.krazykid1117.item.MItems"
BLOCK_CLASS = "com.krazykid1117.blocks.MBlocks"


def parse_args() -> argparse.Namespace:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("jar", type=Path, help="published UsefulTools-v1.6-1.7.10.jar")
    parser.add_argument(
        "--migration-table", type=Path, default=REPO_ROOT / "CLASSIC_MIGRATION.md"
    )
    return parser.parse_args()


def javap(jar: Path, class_name: str) -> str:
    result = subprocess.run(
        ["javap", "-classpath", str(jar), "-c", "-p", class_name],
        check=True,
        stdout=subprocess.PIPE,
        stderr=subprocess.PIPE,
        text=True,
    )
    return result.stdout


def method_body(disassembly: str, name: str) -> str:
    match = re.search(
        rf"  public static void {re.escape(name)}\(\);\n    Code:\n(.*?)(?=\n  (?:public|private|protected)|\Z)",
        disassembly,
        flags=re.DOTALL,
    )
    if not match:
        raise ValueError(f"missing method {name}")
    return match.group(1)


def registered_fields(body: str, registry_method: str) -> list[str]:
    fields: list[str] = []
    pending: str | None = None
    for line in body.splitlines():
        field = re.search(r"// Field ([A-Za-z0-9_$]+):", line)
        if field:
            pending = field.group(1)
        if registry_method in line:
            if pending is None:
                raise ValueError(f"{registry_method} call has no preceding field")
            fields.append(pending)
            pending = None
    return fields


def initialized_names(disassembly: str, fields: set[str]) -> dict[str, str]:
    names: dict[str, str] = {}
    last_name: str | None = None
    for line in disassembly.splitlines():
        string = re.search(r"// String ([^\s]+)$", line)
        if string and not string.group(1).startswith("usefultoolsmod:"):
            last_name = string.group(1)
        field = re.search(r"putstatic\s+#[0-9]+\s+// Field ([A-Za-z0-9_$]+):", line)
        if field and field.group(1) in fields:
            if last_name is None:
                raise ValueError(f"field {field.group(1)} has no preceding unlocalized name")
            names[field.group(1)] = last_name
            last_name = None
    return names


def migration_paths(path: Path, kind: str) -> list[str]:
    pattern = re.compile(
        rf"^\| {re.escape(kind)} \| `usefultoolsmod:(?:item|tile)\.([^`]+)` \|"
    )
    return [match.group(1) for line in path.read_text(encoding="utf-8").splitlines()
            if (match := pattern.match(line))]


def check_equal(label: str, actual: list[str], expected: list[str]) -> None:
    if len(actual) == len(expected) and set(actual) == set(expected):
        return
    missing = [value for value in expected if value not in actual]
    extra = [value for value in actual if value not in expected]
    raise ValueError(
        f"{label} differs: actual={len(actual)} expected={len(expected)} "
        f"missing={missing} extra={extra}"
    )


def main() -> int:
    args = parse_args()
    jar = args.jar.resolve()
    digest = hashlib.sha256(jar.read_bytes()).hexdigest()
    if digest != EXPECTED_SHA256:
        print(f"unexpected SHA-256: {digest}", file=sys.stderr)
        return 2
    with zipfile.ZipFile(jar) as archive:
        entry_count = len(archive.infolist())
    if entry_count != 164:
        print(f"unexpected ZIP entry count: {entry_count}", file=sys.stderr)
        return 2

    item_bytecode = javap(jar, ITEM_CLASS)
    block_bytecode = javap(jar, BLOCK_CLASS)
    item_fields = registered_fields(method_body(item_bytecode, "registerItem"), "registerItem")
    block_fields = (
        registered_fields(method_body(block_bytecode, "registerItem"), "registerBlock")
        + registered_fields(method_body(block_bytecode, "registerItem1"), "registerBlock")
    )
    item_names = initialized_names(item_bytecode, set(item_fields))
    block_names = initialized_names(block_bytecode, set(block_fields))
    published_items = [item_names[field] for field in item_fields]
    published_blocks = [block_names[field] for field in block_fields]
    check_equal("item migration", published_items, migration_paths(args.migration_table, "Item"))
    check_equal("block migration", published_blocks, migration_paths(args.migration_table, "Block"))

    print(f"classic release audit: OK ({len(published_items)} items, {len(published_blocks)} blocks)")
    print(f"SHA-256: {digest}; ZIP entries: {entry_count}")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
