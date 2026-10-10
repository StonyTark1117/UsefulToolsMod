package dev.oculusforge.shaderpack;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ShaderOptionParser {
   private static final Pattern DEFINE = Pattern.compile("^\\s*(//\\s*)?#\\s*define\\s+([A-Za-z_][A-Za-z0-9_]*)(?:\\s+([^\\s/]+))?\\s*(?://\\s*(.*))?$");
   private static final Pattern VALUES = Pattern.compile("\\[([^]]+)]");

   private ShaderOptionParser() {
   }

   public static Map<String, ShaderOption> parse(String source) {
      Map<String, ShaderOption> options = new LinkedHashMap<>();
      String[] lines = source.split("\\R", -1);

      for (int index = 0; index < lines.length; index++) {
         Matcher define = DEFINE.matcher(lines[index]);
         if (define.matches()) {
            boolean commented = define.group(1) != null;
            String name = define.group(2);
            String rawValue = define.group(3);
            String comment = define.group(4);
            Matcher valuesMatcher = VALUES.matcher(comment == null ? "" : comment);
            List<String> allowed = new ArrayList<>();
            if (valuesMatcher.find()) {
               for (String value : valuesMatcher.group(1).trim().split("\\s+")) {
                  if (!value.isBlank()) {
                     allowed.add(value);
                  }
               }
            }

            if ((rawValue != null || commented || !allowed.isEmpty() || comment != null && !comment.isBlank()) && (rawValue == null || !allowed.isEmpty())) {
               String defaultValue = rawValue == null ? Boolean.toString(!commented) : rawValue;
               if (!allowed.isEmpty() && !allowed.contains(defaultValue)) {
                  allowed.add(0, defaultValue);
               }

               if (rawValue == null && allowed.isEmpty()) {
                  allowed = List.of("false", "true");
               }

               options.putIfAbsent(name, new ShaderOption(name, defaultValue, allowed, index + 1));
            }
         }
      }

      return Map.copyOf(options);
   }
}
