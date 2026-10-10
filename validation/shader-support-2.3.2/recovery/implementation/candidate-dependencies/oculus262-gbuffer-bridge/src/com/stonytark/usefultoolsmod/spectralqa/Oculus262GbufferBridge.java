package com.stonytark.usefultoolsmod.spectralqa;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.ProtectionDomain;
import java.util.HexFormat;
import java.util.Map;

/** Exact-input transformer for the 26.2 community port's BSL gbuffer bridge. */
public final class Oculus262GbufferBridge {
    private static final Map<String, Fingerprints> TARGETS = Map.of(
        "dev/oculusforge/render/program/LegacyFinalShaderAdapter", new Fingerprints(
            "a38310c597c0b03d7e2fec3737ed3f5ceb6db520e388ff11502819ace6fc306d",
            "a53f461cee4134d4d5147d830d17d6259e2ccb6eff1cef326603a9249b81ddba"),
        "dev/oculusforge/render/program/PackProgramCompiler", new Fingerprints(
            "c72fbbf4e6f0d320c14600f36241cd9716e57a5876890812ccfee49fae99ac3a",
            "e2d50346be8421804d60965881d0861018db0c7b3db0522c48b98a4d26f64f09"),
        "dev/oculusforge/render/program/PreparedPackPipeline", new Fingerprints(
            "c8e6ca7f3c7ee1c2a569c2421445d51885c65cb88a35e00017d05e96f7fd829b",
            "f25ab1fb5959d739c7cde745cab0ad3b1dc5bdcf30dcb11ce6cf3a53fec22cc6"),
        "dev/oculusforge/render/program/PackShaderPipeline", new Fingerprints(
            "8d27d0de070039e3ee3f1ba76a057a3d14f76e690a83d6747ec0f93384ef9fa8",
            "45875cdd0a5b91bfa726d0088ab0b712d8c6cb59c289fb8be754a0cf285af5fa"),
        "com/mojang/blaze3d/opengl/GlRenderPass", new Fingerprints(
            "7ad9eed06cc24d3a5c8db45a57d9bb6691cf354b07774036316046cb3acc10fa",
            "e2de6aa0f24780fcc8d73c0784d0623773ad5d29dfd5b5c8dd042d2e24cc6ed6")
    );

    private Oculus262GbufferBridge() { }

    public static void premain(String evidencePath, Instrumentation instrumentation) throws IOException {
        if (evidencePath == null || evidencePath.isBlank()) throw new IllegalArgumentException("Expected an absolute transform-log path");
        Path transformLog = Path.of(evidencePath).toAbsolutePath().normalize();
        Path eventLog = transformLog.resolveSibling("oculus262-gbuffer-events.jsonl");
        String runToken = System.getenv("USEFUL_TOOLS_SPECTRAL_RUN_ID");
        if (runToken == null || !runToken.matches("[0-9a-f]{32}")) {
            throw new IllegalStateException("Expected the harness-provided USEFUL_TOOLS_SPECTRAL_RUN_ID token");
        }
        System.setProperty("oculus262.gbuffer.eventLog", eventLog.toString());
        System.setProperty("oculus262.gbuffer.runToken", runToken);
        final FileOutputStream evidence = new FileOutputStream(transformLog.toFile());
        evidence.write(("OCULUS_262_GBUFFER_BRIDGE ready targets=" + TARGETS.size() + " run_token=" + runToken
            + " event_log=" + eventLog + "\n").getBytes("UTF-8"));
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
                        throw new IllegalStateException("Exact input hash mismatch for " + name + ": expected "
                            + expected.original() + " got " + originalHash);
                    }
                    byte[] replacement = resource("/donor/" + name + ".class");
                    String replacementHash = sha256(replacement);
                    if (!replacementHash.equals(expected.replacement())) {
                        throw new IllegalStateException("Embedded donor hash mismatch for " + name + ": expected "
                            + expected.replacement() + " got " + replacementHash);
                    }
                    synchronized (evidence) {
                        evidence.write(("OCULUS_262_GBUFFER_BRIDGE transformed=" + name + " before=" + originalHash
                            + " after=" + replacementHash + "\n").getBytes("UTF-8"));
                        evidence.flush();
                    }
                    if (name.equals("com/mojang/blaze3d/opengl/GlRenderPass")) {
                        System.setProperty("oculus262.gbuffer.renderBridge", replacementHash);
                    }
                    return replacement;
                } catch (Throwable failure) {
                    try {
                        synchronized (evidence) {
                            evidence.write(("OCULUS_262_GBUFFER_BRIDGE failure=" + name + " reason="
                                + failure.toString().replace('\n', ' ') + "\n").getBytes("UTF-8"));
                            evidence.flush();
                        }
                    } catch (IOException ignored) {
                        // The pipeline-side sentinel below still fails closed.
                    }
                    System.setProperty("oculus262.gbuffer.transformFailure", name);
                    return null;
                }
            }
        }, false);
    }

    private static byte[] resource(String path) throws IOException {
        try (InputStream input = Oculus262GbufferBridge.class.getResourceAsStream(path)) {
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
