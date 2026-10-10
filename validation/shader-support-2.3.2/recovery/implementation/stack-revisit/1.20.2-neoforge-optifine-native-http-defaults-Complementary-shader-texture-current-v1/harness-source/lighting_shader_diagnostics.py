"""Recognize compiler diagnostics without matching Minecraft log timestamps."""
import re

COMPILER_FAILURE = re.compile(
    r'Error compiling.*shader|Couldn.t compile.*(?:shader|pipeline)|Invalid program|Shader compilation failed|'
    r'ERROR:\s*\d+:\d+|\b\d+\(\d+\)\s*:\s*error\b|\b\d+:\d+(?:\(\d+\))?\s*:\s*error\b',
    re.IGNORECASE,
)

def compiler_failures(log):
    return [line for line in log.splitlines() if COMPILER_FAILURE.search(line)]
