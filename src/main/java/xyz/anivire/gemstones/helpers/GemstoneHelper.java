package xyz.anivire.gemstones.helpers;

import xyz.anivire.gemstones.item.gemstone.GemstoneType;

public class GemstoneHelper {
  public static String getGemstoneString(GemstoneType gemstoneType) {
    switch (gemstoneType) {
      case RUBY:
        return "ruby";
      case SAPPHIRE:
        return "sapphire";
      case MALACHITE:
        return "malachite";
      case EMPTY:
      default:
        return "empty";
    }
  }
}