#!/usr/bin/env python3
"""Fail when the generated catalog is stale, incomplete, or internally inconsistent."""

from __future__ import annotations

import json
import sys
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
CATALOG = ROOT / "catalog/useful_tools_catalog.json"


EXPECTED_COUNTS = {
    "items": 638,
    "blocks": 23,
    "entities": 4,
    "config": 126,
    "recipes": 688,
    "advancements": 854,
}


def validation_errors(data: dict[str, object]) -> list[str]:
    errors: list[str] = []
    content = data["content"]

    for key in ("items", "blocks", "entities"):
        values = content[key]
        count = EXPECTED_COUNTS[key]
        if len(values) != count:
            errors.append(f"expected {count} {key}, found {len(values)}")
        if len(values) != len(set(values)):
            errors.append(f"duplicate {key} IDs")

    if content["block_items"] != content["blocks"]:
        errors.append("every block must have a matching block item")

    if len(data["config"]) != EXPECTED_COUNTS["config"]:
        errors.append(f"expected {EXPECTED_COUNTS['config']} config options, found {len(data['config'])}")
    for key in ("recipes", "advancements"):
        values = content[key]
        if not values:
            errors.append(f"canonical {key[:-1]} set is empty")
        count = EXPECTED_COUNTS[key]
        if len(values) != count:
            errors.append(f"expected {count} {key} across all namespaces, found {len(values)}")
        if len(values) != len(set(values)):
            errors.append(f"duplicate {key} IDs")
    if "minecraft:ice" not in content["recipes"]:
        errors.append("missing the intentional minecraft:ice snowball recipe")
    return errors


def main() -> None:
    from generate_catalog import build_catalog
    from generate_registration_tables import adoption_errors, generated_drift

    catalog = json.loads(CATALOG.read_text(encoding="utf-8"))
    errors = validation_errors(catalog)
    imported = build_catalog()
    if imported != catalog:
        errors.append("canonical 1.21.1 implementation differs from the catalog manifest")
    for path in generated_drift(catalog):
        errors.append(f"stale generated registration table: {path}")
    errors.extend(adoption_errors(catalog))
    if errors:
        for message in errors:
            print(f"catalog audit: {message}", file=sys.stderr)
        raise SystemExit(1)
    print("catalog audit: OK (manifest authoritative; 15 generated registration tables current)")


if __name__ == "__main__":
    main()
