package com.stonytark.usefultools.lighting;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Matches final-pass vertex interpolation to the active pack's fragment inputs. */
public final class CommunityPortShaderInterfaces {
    private static final Pattern INPUT = Pattern.compile(
        "(?m)^[ \\t]*(?:(flat|smooth|noperspective)\\s+)?in\\s+([A-Za-z_][A-Za-z_0-9]*)\\s+([A-Za-z_][A-Za-z_0-9]*)\\s*;"
    );
    private static final Pattern OUTPUT = Pattern.compile(
        "(?m)^[ \\t]*(?:(flat|smooth|noperspective)\\s+)?out\\s+([A-Za-z_][A-Za-z_0-9]*)\\s+([A-Za-z_][A-Za-z_0-9]*)\\s*;"
    );

    private CommunityPortShaderInterfaces() {}

    public static String matchInterpolation(String vertexSource, String fragmentSource) {
        Map<String, String> inputs = new HashMap<>();
        Matcher fragmentInputs = INPUT.matcher(fragmentSource);
        while (fragmentInputs.find()) {
            inputs.put(fragmentInputs.group(3), fragmentInputs.group(1) == null ? "" : fragmentInputs.group(1));
        }

        Matcher vertexOutputs = OUTPUT.matcher(vertexSource);
        StringBuilder result = new StringBuilder(vertexSource.length());
        while (vertexOutputs.find()) {
            String qualifier = inputs.get(vertexOutputs.group(3));
            if (qualifier == null) {
                vertexOutputs.appendReplacement(result, Matcher.quoteReplacement(vertexOutputs.group()));
            } else {
                String declaration = (qualifier.isEmpty() ? "" : qualifier + " ")
                    + "out " + vertexOutputs.group(2) + " " + vertexOutputs.group(3) + ";";
                vertexOutputs.appendReplacement(result, Matcher.quoteReplacement(declaration));
            }
        }
        vertexOutputs.appendTail(result);
        return result.toString();
    }
}
