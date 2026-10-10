package com.stonytark.usefultools.lighting;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/** Keeps pack extensions ahead of the community port's injected GLSL declarations. */
public final class CommunityPortShaderHeaders {
    private static final Pattern VERSION = Pattern.compile("^#[ \\t]*version\\b.*");
    private static final Pattern EXTENSION = Pattern.compile("^#[ \\t]*extension\\b.*");

    private CommunityPortShaderHeaders() {}

    public static String reorderExtensions(String source) {
        String[] lines = source.split("\n", -1);
        List<String> body = new ArrayList<>(lines.length);
        List<String> extensions = new ArrayList<>();
        for (String line : lines) {
            if (EXTENSION.matcher(line.trim()).matches()) extensions.add(line);
            else body.add(line);
        }
        if (extensions.isEmpty()) return source;
        for (int i = 0; i < body.size(); i++) {
            if (VERSION.matcher(body.get(i).trim()).matches()) {
                body.addAll(i + 1, extensions);
                return String.join("\n", body);
            }
        }
        return source;
    }
}
