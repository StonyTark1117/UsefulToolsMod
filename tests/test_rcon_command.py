from __future__ import annotations

import importlib.util
from pathlib import Path
import struct
import unittest


ROOT = Path(__file__).resolve().parents[1]
SCRIPT = ROOT / "scripts/rcon-command.py"
SPEC = importlib.util.spec_from_file_location("useful_tools_rcon", SCRIPT)
assert SPEC is not None and SPEC.loader is not None
RCON = importlib.util.module_from_spec(SPEC)
SPEC.loader.exec_module(RCON)


class RconCommandTests(unittest.TestCase):
    def test_packet_matches_minecraft_rcon_framing(self) -> None:
        encoded = RCON.packet(7, 2, "list")
        self.assertEqual(len(encoded) - 4, struct.unpack("<i", encoded[:4])[0])
        self.assertEqual((7, 2), struct.unpack("<ii", encoded[4:12]))
        self.assertEqual(b"list\0\0", encoded[12:])


if __name__ == "__main__":
    unittest.main()
