// Source: https://raw.githubusercontent.com/Khaliiid0/Oculus-Community-Port/main/src/main/java/dev/oculusforge/config/OculusConfig.java
package dev.oculusforge.config;

import java.util.Objects;

public record OculusConfig(boolean shadersEnabled, String selectedPack) {
   public static final OculusConfig DEFAULT = new OculusConfig(false, "");

   public OculusConfig(boolean shadersEnabled, String selectedPack) {
      selectedPack = Objects.requireNonNull(selectedPack, "selectedPack").trim();
      if (selectedPack.indexOf(47) < 0
         && selectedPack.indexOf(92) < 0
         && !selectedPack.contains("..")
         && selectedPack.indexOf(0) < 0
         && selectedPack.length() <= 255) {
         this.shadersEnabled = shadersEnabled;
         this.selectedPack = selectedPack;
      } else {
         throw new IllegalArgumentException("Selected shader pack must be a safe file name");
      }
   }

   public OculusConfig withShadersEnabled(boolean enabled) {
      return new OculusConfig(enabled, this.selectedPack);
   }

   public OculusConfig withSelectedPack(String pack) {
      return new OculusConfig(this.shadersEnabled, pack);
   }
}
