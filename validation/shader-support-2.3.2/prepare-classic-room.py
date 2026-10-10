"""Add a small lit room to a stopped, disposable Minecraft 1.7.10 QA world.

Run with Python and nbtlib: python prepare-classic-room.py /path/to/world
The original region is saved beside it as r.-1.0.mca.before-shader-room.
"""

from io import BytesIO
from pathlib import Path
import argparse
import struct
import zlib

import nbtlib


ROOM_X = range(-24, -15)
ROOM_Y = range(68, 75)
ROOM_Z = range(216, 225)
REGION_NAME = "r.-1.0.mca"


def set_nibble(array, index, value):
    byte_index = index // 2
    previous = int(array[byte_index]) & 0xFF
    updated = ((previous & 0xF0) | value) if index % 2 == 0 else ((previous & 0x0F) | (value << 4))
    array[byte_index] = nbtlib.Byte(updated if updated < 128 else updated - 256)


def update_chunk(blob, chunk_x, chunk_z):
    length = struct.unpack_from(">I", blob)[0]
    if blob[4] != 2:
        raise ValueError("Expected zlib-compressed Anvil chunk")
    root = nbtlib.File.parse(BytesIO(zlib.decompress(blob[5 : 4 + length])))
    level = root["Level"]
    level["HeightMap"] = nbtlib.IntArray(list(level["HeightMap"]))
    sections = {int(section["Y"]): section for section in level["Sections"]}
    for section in sections.values():
        for name in ("Blocks", "Data", "SkyLight", "BlockLight"):
            section[name] = nbtlib.ByteArray(list(section[name]))
    for x in ROOM_X:
        if x // 16 != chunk_x:
            continue
        for z in ROOM_Z:
            if z // 16 != chunk_z:
                continue
            local_x, local_z = x & 15, z & 15
            height_index = local_z * 16 + local_x
            level["HeightMap"][height_index] = nbtlib.Int(max(int(level["HeightMap"][height_index]), 75))
            for y in ROOM_Y:
                section = sections[y // 16]
                index = ((y & 15) << 8) | (local_z << 4) | local_x
                edge = x in (ROOM_X.start, ROOM_X.stop - 1) or z in (ROOM_Z.start, ROOM_Z.stop - 1)
                if y == ROOM_Y.start:
                    block = 5  # plank floor
                elif y == ROOM_Y.stop - 1:
                    block = 89 if (x, z) == (-20, 220) else 98  # glowstone in stone-brick roof
                else:
                    block = 98 if edge else 0
                section["Blocks"][index] = nbtlib.Byte(block)
                set_nibble(section["Data"], index, 0)
                set_nibble(section["SkyLight"], index, 0)
                light = max(0, 14 - abs(x + 20) - abs(y - 74) - abs(z - 220))
                set_nibble(section["BlockLight"], index, light)
    stream = BytesIO()
    root.write(stream)
    compressed = zlib.compress(stream.getvalue())
    return struct.pack(">I", len(compressed) + 1) + b"\x02" + compressed


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("world", type=Path, help="Stopped, disposable 1.7.10 QA world")
    args = parser.parse_args()
    path = args.world / "region" / REGION_NAME
    backup = path.with_name(path.name + ".before-shader-room")
    if backup.exists():
        raise SystemExit(f"Backup already exists; refusing to modify again: {backup}")
    original = path.read_bytes()
    chunks = []
    for index in range(1024):
        offset = int.from_bytes(original[index * 4 : index * 4 + 3], "big") * 4096
        if not offset:
            chunks.append(None)
            continue
        length = struct.unpack_from(">I", original, offset)[0]
        blob = original[offset : offset + 4 + length]
        chunk_x = -32 + index % 32
        chunk_z = index // 32
        if chunk_x in (-2, -1) and chunk_z in (13, 14):
            blob = update_chunk(blob, chunk_x, chunk_z)
        chunks.append(blob)
    result = bytearray(8192)
    result[4096:8192] = original[4096:8192]  # preserve chunk timestamps
    for index, blob in enumerate(chunks):
        if blob is None:
            continue
        sector = len(result) // 4096
        count = (len(blob) + 4095) // 4096
        result[index * 4 : index * 4 + 3] = sector.to_bytes(3, "big")
        result[index * 4 + 3] = count
        result.extend(blob)
        result.extend(b"\x00" * (count * 4096 - len(blob)))
    backup.write_bytes(original)
    path.write_bytes(result)
    print(f"Prepared lit room at x=-24..-16, y=68..74, z=216..224; backup: {backup}")


if __name__ == "__main__":
    main()
