package com.stonytark.usefultoolsmod.spectralqa;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.security.MessageDigest;
import java.security.ProtectionDomain;
import java.util.HexFormat;
import java.util.Map;

/**
 * Exact-input runtime bridge for the publisher Oculus 26.2 beta.  The
 * publisher jar remains untouched: two source preparation classes are replaced
 * only while they are defined, after their byte hashes have been verified.
 */
public final class Oculus262SourceBridge {
    private static final Map<String, Fingerprints> TARGETS = Map.of(
        "dev/oculusforge/render/program/LegacyFinalShaderAdapter",
        new Fingerprints(
            "a38310c597c0b03d7e2fec3737ed3f5ceb6db520e388ff11502819ace6fc306d",
            "a53f461cee4134d4d5147d830d17d6259e2ccb6eff1cef326603a9249b81ddba"),
        "dev/oculusforge/render/program/PackProgramCompiler",
        new Fingerprints(
            "c72fbbf4e6f0d320c14600f36241cd9716e57a5876890812ccfee49fae99ac3a",
            "62ff9d9e264eef086380e946cc3bce57f6a4cd903c9be861206e80edcb50724b")
    );

    private Oculus262SourceBridge() { }

    public static void premain(String evidencePath, Instrumentation instrumentation) throws IOException {
        if (evidencePath == null || evidencePath.isBlank()) {
            throw new IllegalArgumentException("Expected an absolute evidence-log path");
        }
        final FileOutputStream evidence = new FileOutputStream(evidencePath);
        evidence.write(("OCULUS_262_SOURCE_BRIDGE ready targets=" + TARGETS.size() + "\n").getBytes("UTF-8"));
        evidence.flush();
        instrumentation.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String name, Class<?> redefining,
                                    ProtectionDomain domain, byte[] original) {
                Fingerprints expected = TARGETS.get(name);
                if (expected == null) return null;
                try {
                    String originalHash = sha256(original);
                    if (!originalHash.equals(expected.original())) {
                        throw new IllegalStateException("Publisher class hash mismatch for " + name
                            + ": expected " + expected.original() + " got " + originalHash);
                    }
                    byte[] replacement = resource("/donor/" + name + ".class");
                    String replacementHash = sha256(replacement);
                    if (!replacementHash.equals(expected.replacement())) {
                        throw new IllegalStateException("Embedded donor hash mismatch for " + name
                            + ": expected " + expected.replacement() + " got " + replacementHash);
                    }
                    synchronized (evidence) {
                        evidence.write(("OCULUS_262_SOURCE_BRIDGE transformed=" + name
                            + " before=" + originalHash + " after=" + replacementHash + "\n")
                            .getBytes("UTF-8"));
                        evidence.flush();
                    }
                    return replacement;
                } catch (IOException error) {
                    throw new IllegalStateException("Cannot retain Oculus bridge evidence", error);
                }
            }
        }, false);
    }

    private static byte[] resource(String path) throws IOException {
        try (InputStream input = Oculus262SourceBridge.class.getResourceAsStream(path)) {
            if (input == null) throw new IOException("Missing bridge resource " + path);
            return input.readAllBytes();
        }
    }

    private static String sha256(byte[] bytes) {
        try {
            return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(bytes));
        } catch (java.security.NoSuchAlgorithmException impossible) {
            throw new AssertionError(impossible);
        }
    }

    private record Fingerprints(String original, String replacement) { }
}
