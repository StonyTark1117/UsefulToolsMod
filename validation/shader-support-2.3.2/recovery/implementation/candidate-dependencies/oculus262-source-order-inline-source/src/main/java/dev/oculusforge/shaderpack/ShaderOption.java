package dev.oculusforge.shaderpack;

import java.util.List;

public record ShaderOption(String name, String defaultValue, List<String> allowedValues, int line) {
   public ShaderOption(String name, String defaultValue, List<String> allowedValues, int line) {
      allowedValues = List.copyOf(allowedValues);
      this.name = name;
      this.defaultValue = defaultValue;
      this.allowedValues = allowedValues;
      this.line = line;
   }

   public boolean isSwitch() {
      return "true".equals(this.defaultValue) || "false".equals(this.defaultValue);
   }
}
