package dev.oculusforge.shaderpack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

final class ShaderPackSecurityTest {
    @TempDir
    Path temporaryDirectory;

    @Test
    void virtualPathsRejectTraversalAndPlatformPaths() {
        for (String path : new String[]{"../secret", "/absolute", "C:/absolute", "shaders\\final.fsh", "shaders//final.fsh"}) {
            ShaderPackException exception = assertThrows(ShaderPackException.class, () -> VirtualPath.parse(path));
            assertEquals(ShaderPackException.Code.INVALID_PATH, exception.code());
        }
    }

    @Test
    void absoluteShaderIncludeIsAnchoredInsideShaders() throws Exception {
        VirtualPath resolved = VirtualPath.resolve(VirtualPath.parse("shaders/world0/final.fsh"), "/lib/common.glsl");
        assertEquals("shaders/lib/common.glsl", resolved.toString());
    }

    @Test
    void directoryEntryBudgetIsEnforced() throws Exception {
        Files.writeString(temporaryDirectory.resolve("one.glsl"), "1");
        Files.writeString(temporaryDirectory.resolve("two.glsl"), "2");

        ShaderPackException exception = assertThrows(
            ShaderPackException.class,
            () -> new DirectoryPackFileSystem(temporaryDirectory, limits(8, 16, 1, 4, 4, 32))
        );

        assertEquals(ShaderPackException.Code.ARCHIVE_LIMIT_EXCEEDED, exception.code());
    }

    @Test
    void directoryTotalByteBudgetIsEnforced() throws Exception {
        Files.writeString(temporaryDirectory.resolve("one.glsl"), "123");
        Files.writeString(temporaryDirectory.resolve("two.glsl"), "456");

        ShaderPackException exception = assertThrows(
            ShaderPackException.class,
            () -> new DirectoryPackFileSystem(temporaryDirectory, limits(8, 5, 4, 4, 4, 32))
        );

        assertEquals(ShaderPackException.Code.ARCHIVE_LIMIT_EXCEEDED, exception.code());
    }

    @Test
    void directoryPerFileBudgetIsEnforcedDuringDiscovery() throws Exception {
        Files.writeString(temporaryDirectory.resolve("large.glsl"), "123");

        ShaderPackException exception = assertThrows(
            ShaderPackException.class,
            () -> new DirectoryPackFileSystem(temporaryDirectory, limits(2, 8, 4, 4, 4, 32))
        );

        assertEquals(ShaderPackException.Code.FILE_LIMIT_EXCEEDED, exception.code());
    }

    @Test
    void cumulativeZipReadBudgetIsEnforced() throws Exception {
        Path archive = createZip(Map.of("shaders/final.fsh", "123456".getBytes()));

        try (ZipPackFileSystem pack = new ZipPackFileSystem(archive, limits(8, 10, 4, 4, 4, 32))) {
            VirtualPath shader = VirtualPath.parse("shaders/final.fsh");
            assertEquals(6, pack.read(shader).length);
            ShaderPackException exception = assertThrows(ShaderPackException.class, () -> pack.read(shader));
            assertEquals(ShaderPackException.Code.ARCHIVE_LIMIT_EXCEEDED, exception.code());
        }
    }

    @Test
    void zipTraversalEntryIsRejected() throws Exception {
        Path archive = createZip(Map.of("../escape.glsl", "x".getBytes()));

        ShaderPackException exception = assertThrows(
            ShaderPackException.class,
            () -> new ZipPackFileSystem(archive, ShaderPackLimits.DEFAULT)
        );

        assertEquals(ShaderPackException.Code.MALFORMED_ARCHIVE, exception.code());
    }

    @Test
    void malformedUtf8IsRejected() throws Exception {
        Files.write(temporaryDirectory.resolve("bad.glsl"), new byte[]{(byte) 0xC3, 0x28});

        try (DirectoryPackFileSystem pack = new DirectoryPackFileSystem(temporaryDirectory, ShaderPackLimits.DEFAULT)) {
            ShaderPackException exception = assertThrows(
                ShaderPackException.class,
                () -> pack.readUtf8(VirtualPath.parse("bad.glsl"))
            );
            assertEquals(ShaderPackException.Code.INVALID_TEXT_ENCODING, exception.code());
        }
    }

    @Test
    void includeCountBudgetIsEnforced() throws Exception {
        Path shaders = Files.createDirectories(temporaryDirectory.resolve("shaders"));
        Files.writeString(shaders.resolve("main.glsl"), "#include \"a.glsl\"\n#include \"b.glsl\"");
        Files.writeString(shaders.resolve("a.glsl"), "a");
        Files.writeString(shaders.resolve("b.glsl"), "b");

        try (DirectoryPackFileSystem pack = new DirectoryPackFileSystem(temporaryDirectory, ShaderPackLimits.DEFAULT)) {
            ShaderPackException exception = assertThrows(
                ShaderPackException.class,
                () -> new ShaderSourceLoader(pack, limits(64, 256, 8, 4, 1, 256))
                    .load(VirtualPath.parse("shaders/main.glsl"))
            );
            assertEquals(ShaderPackException.Code.INCLUDE_COUNT_EXCEEDED, exception.code());
        }
    }

    @Test
    void expandedSourceBudgetIsEnforced() throws Exception {
        Path shaders = Files.createDirectories(temporaryDirectory.resolve("shaders"));
        Files.writeString(shaders.resolve("main.glsl"), "123456789");

        try (DirectoryPackFileSystem pack = new DirectoryPackFileSystem(temporaryDirectory, ShaderPackLimits.DEFAULT)) {
            ShaderPackException exception = assertThrows(
                ShaderPackException.class,
                () -> new ShaderSourceLoader(pack, limits(64, 256, 8, 4, 4, 8))
                    .load(VirtualPath.parse("shaders/main.glsl"))
            );
            assertEquals(ShaderPackException.Code.EXPANDED_SOURCE_LIMIT_EXCEEDED, exception.code());
        }
    }

    private Path createZip(Map<String, byte[]> entries) throws IOException {
        Path archive = temporaryDirectory.resolve("pack.zip");
        try (ZipOutputStream output = new ZipOutputStream(Files.newOutputStream(archive))) {
            for (Map.Entry<String, byte[]> entry : new LinkedHashMap<>(entries).entrySet()) {
                output.putNextEntry(new ZipEntry(entry.getKey()));
                output.write(entry.getValue());
                output.closeEntry();
            }
        }
        return archive;
    }

    private static ShaderPackLimits limits(
        long maxFileBytes,
        long maxArchiveBytes,
        int maxArchiveEntries,
        int maxIncludeDepth,
        int maxIncludeCount,
        long maxExpandedSourceBytes
    ) {
        return new ShaderPackLimits(
            maxFileBytes,
            maxArchiveBytes,
            maxArchiveEntries,
            200.0,
            maxIncludeDepth,
            maxIncludeCount,
            maxExpandedSourceBytes
        );
    }
}
