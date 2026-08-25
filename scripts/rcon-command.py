#!/usr/bin/env python3
"""Send one or more commands to a local Minecraft RCON endpoint."""

from __future__ import annotations

import argparse
import socket
import struct


def packet(request_id: int, packet_type: int, payload: str) -> bytes:
    body = struct.pack("<ii", request_id, packet_type) + payload.encode() + b"\0\0"
    return struct.pack("<i", len(body)) + body


def receive(connection: socket.socket) -> tuple[int, int, str]:
    size_data = connection.recv(4)
    if len(size_data) != 4:
        raise RuntimeError("RCON connection closed before a packet was received")
    size = struct.unpack("<i", size_data)[0]
    body = bytearray()
    while len(body) < size:
        chunk = connection.recv(size - len(body))
        if not chunk:
            raise RuntimeError("RCON connection closed during a packet")
        body.extend(chunk)
    request_id, packet_type = struct.unpack("<ii", body[:8])
    return request_id, packet_type, body[8:-2].decode(errors="replace")


def main() -> int:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--host", default="127.0.0.1")
    parser.add_argument("--port", type=int, required=True)
    parser.add_argument("--password", required=True)
    parser.add_argument("command", nargs="+")
    args = parser.parse_args()

    with socket.create_connection((args.host, args.port), timeout=10) as connection:
        connection.sendall(packet(1, 3, args.password))
        auth_id, _, _ = receive(connection)
        if auth_id == -1:
            raise RuntimeError("RCON authentication failed")
        for index, command in enumerate(args.command, start=2):
            connection.sendall(packet(index, 2, command))
            response_id, _, response = receive(connection)
            if response_id != index:
                raise RuntimeError(f"unexpected RCON response id {response_id}")
            print(f"> {command}\n{response}")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
