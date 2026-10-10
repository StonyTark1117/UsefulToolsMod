"""Bound cancellation of a private client process group without touching shared apps."""
import os
import signal
import subprocess
import time
from pathlib import Path


def cleanup_private_group(group_id, grace_timeout=5):
    """Stop descendants left in the caller's dedicated session after exit."""
    try:
        os.killpg(group_id, signal.SIGTERM)
    except ProcessLookupError:
        return
    deadline = time.monotonic() + grace_timeout
    while time.monotonic() < deadline:
        # Zombies consume no CPU and cannot be killed; their parent reaps them.
        live = False
        for entry in Path('/proc').iterdir():
            if not entry.name.isdigit():
                continue
            try:
                fields = (entry / 'stat').read_text().rsplit(')', 1)[1].split()
                if int(fields[2]) == group_id and fields[0] != 'Z':
                    live = True
                    break
            except (OSError, ValueError, IndexError):
                continue
        if not live:
            return
        time.sleep(.05)
    try:
        os.killpg(group_id, signal.SIGKILL)
    except ProcessLookupError:
        pass


def wait_client(client, cleanup_timeout=30):
    """Wait normally; on interruption let the harness save evidence and clean up.

    The caller must create this subprocess with start_new_session=True. Only
    that private group is terminated if the harness cannot finish cleanup.
    """
    try:
        result = client.wait()
        cleanup_private_group(client.pid)
        return result
    except KeyboardInterrupt:
        previous = {sig: signal.signal(sig, signal.SIG_IGN)
                    for sig in (signal.SIGINT, signal.SIGTERM)}
        try:
            if client.poll() is None:
                client.send_signal(signal.SIGINT)
                try:
                    client.wait(timeout=cleanup_timeout)
                except subprocess.TimeoutExpired:
                    # Children inherit the private session; no global pkill.
                    os.killpg(client.pid, signal.SIGTERM)
                    try:
                        client.wait(timeout=5)
                    except subprocess.TimeoutExpired:
                        pass
                    # Reap any descendants even if the harness exited first.
                    try:
                        os.killpg(client.pid, signal.SIGKILL)
                    except ProcessLookupError:
                        pass
                    client.wait(timeout=5)
        finally:
            cleanup_private_group(client.pid)
            for sig, handler in previous.items():
                signal.signal(sig, handler)
        raise
