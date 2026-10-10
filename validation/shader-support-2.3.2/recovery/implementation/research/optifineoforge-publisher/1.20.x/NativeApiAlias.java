import java.nio.file.*;
import java.io.*;
import java.util.*;
import java.util.zip.*;
import org.objectweb.asm.*;
import org.objectweb.asm.commons.*;

public final class NativeApiAlias {
    public static void main(String[] args) throws Exception {
        Properties aliases = new Properties();
        try (InputStream in = Files.newInputStream(Path.of(args[0]))) { aliases.load(in); }
        Remapper remapper = new Remapper(Opcodes.ASM9) {
            @Override public String map(String name) { return aliases.getProperty(name, name); }
            @Override public Object mapValue(Object value) {
                if (value instanceof String string) {
                    String mapped = aliases.getProperty(string.replace('.', '/'));
                    if (mapped != null) return string.contains(".") ? mapped.replace('/', '.') : mapped;
                }
                return super.mapValue(value);
            }
        };
        int changed = 0;
        try (ZipFile source = new ZipFile(args[1]); ZipOutputStream target = new ZipOutputStream(Files.newOutputStream(Path.of(args[2])))) {
            for (ZipEntry entry : Collections.list(source.entries())) {
                if (entry.isDirectory()) continue;
                byte[] data;
                try (InputStream input = source.getInputStream(entry)) { data = input.readAllBytes(); }
                if (entry.getName().endsWith(".class")) {
                    ClassWriter writer = new ClassWriter(0);
                    new ClassReader(data).accept(new ClassRemapper(writer, remapper), 0);
                    byte[] mapped = writer.toByteArray();
                    if (!Arrays.equals(data, mapped)) changed++;
                    data = mapped;
                }
                ZipEntry output = new ZipEntry(entry.getName()); output.setTime(0);
                target.putNextEntry(output); target.write(data); target.closeEntry();
            }
        }
        System.out.println("Native API alias class files processed=" + changed + "; actual type aliases=" + aliases.size());
    }
}
