package name.modid.helpers;

import java.util.ArrayList;
import java.util.List;

import org.spongepowered.asm.mixin.Unique;

import name.modid.helpers.types.GemstoneRarityType;
import name.modid.helpers.types.GemstoneType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.text.MutableText;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public class GemstoneTooltipHelper {
  @Unique
  public static Formatting getGemstoneColor(GemstoneType gemType) {
    return switch (gemType) {
    case EMPTY -> Formatting.DARK_GRAY;
    case RUBY -> Formatting.RED;
    default -> Formatting.GRAY;
    };
  }

  @Unique
  public static String getSlotText(GemstoneType gemType) {
    return switch (gemType) {
    case LOCKED -> "Locked";
    case EMPTY -> "Empty";
    case RUBY -> "Ruby";
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

  @Unique
  public static List<Text> getGemstoneItemBonuses(GemstoneType gemType) {
    List<Text> bonuses = new ArrayList<>();
    String gemKey = gemType.name().toLowerCase();

    bonuses.addAll(createWrappedTooltip("tooltip.gemstones.melee_buff",
        "tooltip.gemstones." + gemKey + "_item.melee_buff", Formatting.GRAY, Formatting.DARK_GREEN));
    bonuses.addAll(createWrappedTooltip("tooltip.gemstones.ranged_buff",
        "tooltip.gemstones." + gemKey + "_item.ranged_buff", Formatting.GRAY, Formatting.DARK_GREEN));
    bonuses.addAll(createWrappedTooltip("tooltip.gemstones.tools_buff",
        "tooltip.gemstones." + gemKey + "_item.tools_buff", Formatting.GRAY, Formatting.DARK_GREEN));
    bonuses.addAll(createWrappedTooltip("tooltip.gemstones.armor_buff",
        "tooltip.gemstones." + gemKey + "_item.armor_buff", Formatting.GRAY, Formatting.DARK_GREEN));

    return bonuses;
  }

  @Unique
  public static List<Text> createWrappedTooltip(String baseKey, String valueKey, Formatting baseColor,
      Formatting valueColor) {
    List<Text> lines = new ArrayList<>();
    MutableText baseText = Text.translatable(baseKey).formatted(baseColor);
    MutableText valueText = Text.translatable(valueKey).formatted(valueColor);
    TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
    int maxWidth = 150;

    List<Text> baseLines = wrapText(baseText, textRenderer, maxWidth);
    List<Text> valueLines = wrapText(valueText, textRenderer, maxWidth);

    if (!baseLines.isEmpty() && !valueLines.isEmpty()) {
      Text lastBase = baseLines.remove(baseLines.size() - 1);
      Text firstValue = valueLines.remove(0);
      baseLines.add(lastBase.copy().append(firstValue));
    }

    lines.addAll(baseLines);
    lines.addAll(valueLines);

    return lines;
  }

  @Unique
  public static List<Text> wrapText(Text text, TextRenderer renderer, int maxWidth) {
    List<Text> lines = new ArrayList<>();
    StringVisitable visitable = StringVisitable.styled(text.getString(), text.getStyle());
    List<StringVisitable> wrapped = renderer.getTextHandler().wrapLines(visitable, maxWidth, Style.EMPTY);
    for (StringVisitable line : wrapped) {
      lines.add(Text.literal(line.getString()).setStyle(text.getStyle()));
    }
    return lines;
  }
}
