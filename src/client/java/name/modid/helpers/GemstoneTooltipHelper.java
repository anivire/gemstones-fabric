package name.modid.helpers;

import name.modid.helpers.types.GemstoneRarityType;
import name.modid.helpers.types.GemstoneType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public class GemstoneTooltipHelper {
  public static Formatting getGemstoneColor(GemstoneType gemType) {
    return switch (gemType) {
    case EMPTY -> Formatting.DARK_GRAY;
    case RUBY -> Formatting.RED;
    case CELESTINE -> Formatting.BLUE;
    case TOPAZ -> Formatting.GOLD;
    case SAPPHIRE -> Formatting.DARK_BLUE;
    default -> Formatting.GRAY;
    };
  }

  public static String getSlotText(GemstoneType gemType) {
    return switch (gemType) {
    case LOCKED -> "Locked";
    case EMPTY -> "Empty";
    case RUBY -> "Ruby";
    case CELESTINE -> "Celestine";
    case SAPPHIRE -> "Sapphire";
    case TOPAZ -> "Topaz";
    default -> "unknown";
    };
  }

  public static Text getGemstoneSprite(GemstoneType gemType) {
    return Text.literal(GemstoneType.getGemstoneLiteral(gemType))
        .styled(style -> style.withFont(Identifier.of("gemstones", "gemstone_sprite_font")));
  }

  public static Text getGemstoneRaritySprite(GemstoneRarityType rarityType) {
    return Text.literal(GemstoneRarityType.getRarityLiteral(rarityType))
        .styled(style -> style.withFont(Identifier.of("gemstones", "rarity_sprite_font")));
  }
}
