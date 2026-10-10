package dev.oculusforge.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class SemanticVersion implements Comparable<SemanticVersion> {
   private static final Pattern PATTERN = Pattern.compile(
      "^(0|[1-9]\\d*)\\.(0|[1-9]\\d*)\\.(0|[1-9]\\d*)(?:-((?:0|[1-9A-Za-z-][0-9A-Za-z-]*)(?:\\.(?:0|[1-9A-Za-z-][0-9A-Za-z-]*))*))?(?:\\+([0-9A-Za-z-]+(?:\\.[0-9A-Za-z-]+)*))?$"
   );
   private final int major;
   private final int minor;
   private final int patch;
   private final List<String> preRelease;
   private final String build;
   private final String text;

   private SemanticVersion(int major, int minor, int patch, List<String> preRelease, String build, String text) {
      this.major = major;
      this.minor = minor;
      this.patch = patch;
      this.preRelease = List.copyOf(preRelease);
      this.build = build;
      this.text = text;
   }

   public static SemanticVersion parse(String value) {
      Matcher matcher = PATTERN.matcher(Objects.requireNonNull(value, "value"));
      if (!matcher.matches()) {
         throw new IllegalArgumentException("Invalid semantic version: " + value);
      } else {
         List<String> preRelease = matcher.group(4) == null ? List.of() : List.of(matcher.group(4).split("\\."));

         for (String identifier : preRelease) {
            if (identifier.length() > 1 && identifier.charAt(0) == '0' && identifier.chars().allMatch(Character::isDigit)) {
               throw new IllegalArgumentException("Numeric pre-release identifier has a leading zero: " + identifier);
            }
         }

         try {
            return new SemanticVersion(
               Integer.parseInt(matcher.group(1)),
               Integer.parseInt(matcher.group(2)),
               Integer.parseInt(matcher.group(3)),
               new ArrayList<>(preRelease),
               matcher.group(5),
               value
            );
         } catch (NumberFormatException var5) {
            throw new IllegalArgumentException("Semantic version component is too large: " + value, var5);
         }
      }
   }

   public int compareTo(SemanticVersion other) {
      int result = Integer.compare(this.major, other.major);
      if (result == 0) {
         result = Integer.compare(this.minor, other.minor);
      }

      if (result == 0) {
         result = Integer.compare(this.patch, other.patch);
      }

      if (result != 0) {
         return result;
      } else if (this.preRelease.isEmpty()) {
         return other.preRelease.isEmpty() ? 0 : 1;
      } else if (other.preRelease.isEmpty()) {
         return -1;
      } else {
         int count = Math.min(this.preRelease.size(), other.preRelease.size());

         for (int index = 0; index < count; index++) {
            String left = this.preRelease.get(index);
            String right = other.preRelease.get(index);
            boolean leftNumeric = left.chars().allMatch(Character::isDigit);
            boolean rightNumeric = right.chars().allMatch(Character::isDigit);
            if (leftNumeric && rightNumeric) {
               result = compareNumericIdentifiers(left, right);
            } else if (leftNumeric != rightNumeric) {
               result = leftNumeric ? -1 : 1;
            } else {
               result = left.compareTo(right);
            }

            if (result != 0) {
               return result;
            }
         }

         return Integer.compare(this.preRelease.size(), other.preRelease.size());
      }
   }

   private static int compareNumericIdentifiers(String left, String right) {
      int result = Integer.compare(left.length(), right.length());
      return result != 0 ? result : left.compareTo(right);
   }

   @Override
   public boolean equals(Object other) {
      if (other instanceof SemanticVersion version && this.compareTo(version) == 0) {
         return true;
      }

      return false;
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.major, this.minor, this.patch, this.preRelease);
   }

   @Override
   public String toString() {
      return this.text;
   }

   public String buildMetadata() {
      return this.build;
   }
}
