package name.modid.helpers.modifiers.types;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import name.modid.Gemstones;
import name.modid.helpers.modifiers.GemstoneModifier;
import name.modid.helpers.types.GemstoneRarityType;
import name.modid.helpers.types.GemstoneType;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.attribute.EntityAttributeModifier.Operation;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public class ModifierMultiplyAttribute implements GemstoneModifier {
  public List<ModifierAttribute> instances = new ArrayList<>();

  public ModifierMultiplyAttribute(List<ModifierAttribute> instances) {
    this.instances = new ArrayList<>(instances);
  }

  public MutableText getTooltipString(GemstoneRarityType gemstoneRarityType, Boolean withCategoryString) {
    String tooltipCategoryType = withCategoryString
        ? String.format("tooltip.gemstones.%s_type", instances.get(0).itemType.toString().toLowerCase())
        : "tooltip.gemstones.without_type";
    List<MutableText> params = new ArrayList<>();

    for (ModifierAttribute modifierInstance : instances) {
      Double value = modifierInstance.modifierValuesList.get(gemstoneRarityType.getValue());
      MutableText attributeBonus = Text.empty();

      if (modifierInstance.attr == EntityAttributes.GENERIC_MAX_HEALTH) {
        attributeBonus
            .append(Text.literal("\uE001")
                .styled(style -> style.withFont(Identifier.of(Gemstones.MOD_ID, "gemstone_sprite_icons"))))
            .formatted(Formatting.WHITE);
      }

      String percent = modifierInstance.operation == Operation.ADD_VALUE ? "" : "%";
      Double adjustedValue = modifierInstance.operation == Operation.ADD_VALUE ? value : value * 100;
      String formattedValue = formatValue(adjustedValue) + percent;

      params.add(Text.literal(formattedValue).formatted(Formatting.BLUE).append(attributeBonus));
    }

    String translationKey = String.format("tooltip.gemstones.%s.%s_bonus",
        instances.get(0).gemstoneType.toString().toLowerCase(), instances.get(0).itemType.toString().toLowerCase());

    MutableText resultTooltip = Text.translatable(translationKey, params.toArray()).formatted(Formatting.GOLD);

    if (withCategoryString) {
      resultTooltip = Text.translatable(tooltipCategoryType).formatted(Formatting.GRAY).append(resultTooltip);
    }

    return resultTooltip;
  }

  private String formatValue(double value) {
    BigDecimal bd = BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP).stripTrailingZeros();
    return bd.toPlainString();
  }

  public GemstoneType getGemstoneType() {
    return null;
  }

  public GemstoneRarityType getRarityType() {
    return null;
  }

  public void setRarityType(GemstoneRarityType rarityType) {}
}
