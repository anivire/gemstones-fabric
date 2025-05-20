package name.modid.helpers;

public class GemstoneHelper {
    public static String getGemstoneString(GemstoneType gemstoneType) {
        return switch (gemstoneType) {
            case RUBY -> "ruby";
            case SAPPHIRE -> "sapphire";
            case MALACHITE -> "malachite";
            default -> "empty";
        };
    }
}
