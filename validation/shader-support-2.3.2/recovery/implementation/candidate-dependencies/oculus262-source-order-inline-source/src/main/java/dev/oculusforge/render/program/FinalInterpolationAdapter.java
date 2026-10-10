package dev.oculusforge.render.program;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Matches interpolation at the fullscreen program interface, without dropping pack qualifiers. */
public final class FinalInterpolationAdapter {
    private static final Pattern INPUT = Pattern.compile("(?m)^[ \t]*(?:(flat|smooth|noperspective)\\s+)?in\\s+(\\w+)\\s+(\\w+)\\s*;");
    private static final Pattern OUTPUT = Pattern.compile("(?m)^[ \t]*(?:(flat|smooth|noperspective)\\s+)?out\\s+(\\w+)\\s+(\\w+)\\s*;");

    private FinalInterpolationAdapter() {}

    public static String match(String vertexSource, String fragmentSource) {
        Map<String, String> qualifiers = new HashMap<>();
        Matcher inputs = INPUT.matcher(fragmentSource);
        while (inputs.find()) {
            qualifiers.put(inputs.group(3), inputs.group(1) == null ? "" : inputs.group(1));
        }
        Matcher outputs = OUTPUT.matcher(vertexSource);
        StringBuilder result = new StringBuilder();
        while (outputs.find()) {
            String qualifier = qualifiers.get(outputs.group(3));
            if (qualifier == null) {
                outputs.appendReplacement(result, Matcher.quoteReplacement(outputs.group()));
            } else {
                outputs.appendReplacement(result, Matcher.quoteReplacement(
                    (qualifier.isEmpty() ? "" : qualifier + " ") + "out " + outputs.group(2) + " " + outputs.group(3) + ";"));
            }
        }
        outputs.appendTail(result);
        return result.toString();
    }
}
