package name.modid.helpers;

import org.spongepowered.asm.mixin.Unique;

import name.modid.helpers.types.GemstoneRarityType;
import name.modid.helpers.types.GemstoneType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public class GemstoneTooltipHelper {
  @Unique
  public static Formatting getGemstoneColor(GemstoneType gemType) {
    return switch (gemType) {
    case EMPTY -> Formatting.DARK_GRAY;
    case RUBY -> Formatting.RED;
    case CELESTINE -> Formatting.BLUE;
    default -> Formatting.GRAY;
    };
  }

  @Unique
  public static String getSlotText(GemstoneType gemType) {
    return switch (gemType) {
    case LOCKED -> "Locked";
    case EMPTY -> "Empty";
    case RUBY -> "Ruby";
    case CELESTINE -> "Celestine";
    default -> "unknown";
    };
  }

  @Unique
  public static Text getGemstoneSprite(GemstoneType gemType) {
    return switch (gemType) {
    case EMPTY -> Text.literal("\uE001")
        .styled(style -> style.withFont(Identifier.of("gemstones", "gemstone_sprite_font")));
    case RUBY -> Text.literal("\uE002")
        .styled(style -> style.withFont(Identifier.of("gemstones", "gemstone_sprite_font")));
    case CELESTINE -> Text.literal("\uE003")
        .styled(style -> style.withFont(Identifier.of("gemstones", "gemstone_sprite_font")));
    default -> Text.literal("");
    };
  }

  @Unique
  public static Text getGemstoneRaritySprite(GemstoneRarityType rarityType) {
    return switch (rarityType) {
    case COMMON -> Text.literal("\uE001")
        .styled(style -> style.withFont(Identifier.of("gemstones", "rarity_sprite_font")));
    case UNCOMMON -> Text.literal("\uE002")
        .styled(style -> style.withFont(Identifier.of("gemstones", "rarity_sprite_font")));
    case RARE -> Text.literal("\uE003")
        .styled(style -> style.withFont(Identifier.of("gemstones", "rarity_sprite_font")));
    case LEGENDARY -> Text.literal("\uE004")
        .styled(style -> style.withFont(Identifier.of("gemstones", "rarity_sprite_font")));
    case UNUSUAL -> Text.literal("\uE005")
        .styled(style -> style.withFont(Identifier.of("gemstones", "rarity_sprite_font")));
    default -> Text.literal("");
    };
  }

  @Unique
  public static String getGemstoneModifier(String gemstoneModifierText) {
    if (gemstoneModifierText.contains("attack_damage")) {
      return "damage";
    } else if (gemstoneModifierText.contains("armor")) {
      return "armor";
    } else if (gemstoneModifierText.contains("mine_speed")) {
      return "mine_speed";
    } else {
      return "unknown_modifier";
    }
  }
}
