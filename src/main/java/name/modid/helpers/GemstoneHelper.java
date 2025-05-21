package name.modid.helpers;

import name.modid.helpers.types.GemstoneType;

public class GemstoneHelper {
    public static String getGemstoneString(GemstoneType gemstoneType) {
        return switch (gemstoneType) {
            case RUBY -> "ruby";
            case SAPPHIRE -> "sapphire";
            case MALACHITE -> "malachite";
            case LOCKED -> "locked";
            case EMPTY -> "empty";
        };
    }
}
