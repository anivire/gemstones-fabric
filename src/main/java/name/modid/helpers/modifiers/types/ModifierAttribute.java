package name.modid.helpers.modifiers.types;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import name.modid.Gemstones;
import name.modid.helpers.modifiers.GemstoneModifier;
import name.modid.helpers.modifiers.GemstoneModifierHelper;
import name.modid.helpers.modifiers.ModifierItemType;
import name.modid.helpers.types.GemstoneRarityType;
import name.modid.helpers.types.GemstoneType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributeModifier.Operation;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ModifierAttribute implements GemstoneModifier {
  protected Operation operation;
  protected ModifierItemType itemType;
  protected ArrayList<Double> modifierValuesList = new ArrayList<Double>();
  protected RegistryEntry<EntityAttribute> attr;
  protected String socketedTooltipString;
  protected String gemstoneTooltipString;
  protected GemstoneType gemstoneType;
  protected GemstoneRarityType rarityType;

  public ModifierAttribute(Operation operation, ArrayList<Double> modifierValuesList, String socketedTooltipString,
      String gemstoneTooltipString, ModifierItemType itemType, RegistryEntry<EntityAttribute> attr,
      GemstoneType gemstoneType) {
    this.operation = operation;
    this.modifierValuesList = new ArrayList<Double>(modifierValuesList);
    this.socketedTooltipString = socketedTooltipString;
    this.gemstoneTooltipString = gemstoneTooltipString;
    this.itemType = itemType;
    this.attr = attr;
    this.gemstoneType = gemstoneType;
  }

  public MutableText getGemstoneTooltipString(GemstoneRarityType gemstoneRarityType) {
    Double value = modifierValuesList.get(gemstoneRarityType.getValue());
    String tooltipKey = String.format("tooltip.gemstones.%s_buff", itemType.toString().toLowerCase());
    MutableText attributeBonus = Text.empty();

    if (this.attr == EntityAttributes.GENERIC_MAX_HEALTH) {
      attributeBonus
          .append(Text.literal("\uE001")
              .styled(style -> style.withFont(Identifier.of("gemstones", "gemstone_sprite_icons"))))
          .formatted(Formatting.WHITE);
    }

    String percent = this.operation == Operation.ADD_VALUE ? "" : "%";
    Double adjustedValue = this.operation == Operation.ADD_VALUE ? value : value * 100;
    String formattedValue = formatValue(adjustedValue) + percent;

    return Text.translatable(tooltipKey).formatted(Formatting.GRAY)
        .append(Text
            .translatable(this.gemstoneTooltipString,
                Text.literal(formattedValue).formatted(Formatting.BLUE).append(attributeBonus))
            .formatted(Formatting.GOLD));
  }

  public String formatValue(double value) {
    BigDecimal bd = BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP).stripTrailingZeros();
    return bd.toPlainString();
  }

  public String getSocketedTooltipString() {
    return this.socketedTooltipString;
  }

  public GemstoneType getGemstoneType() {
    return this.gemstoneType;
  }

  public GemstoneRarityType getRarityType() {
    return this.rarityType;
  }

  @Override
  public void apply(ItemStack itemStack, Item item, Integer slotIndex, GemstoneRarityType gemstoneRarityType) {
    AttributeModifiersComponent itemAttributeModifiers = itemStack.getOrDefault(DataComponentTypes.ATTRIBUTE_MODIFIERS,
        AttributeModifiersComponent.DEFAULT);

    EntityAttributeModifier scaledGemstoneModifier = new EntityAttributeModifier(
        Identifier.of(Gemstones.MOD_ID,
            String.format("%s_gemstone_%s_modifier_slot%s", this.gemstoneType.toString().toLowerCase(),
                this.itemType.toString().toLowerCase(), slotIndex.toString())),
        modifierValuesList.get(gemstoneRarityType.getValue()), this.operation);

    itemAttributeModifiers = itemAttributeModifiers.with(this.attr, scaledGemstoneModifier,
        GemstoneModifierHelper.getAttributeModifierSlot(item));

    itemStack.set(DataComponentTypes.ATTRIBUTE_MODIFIERS, itemAttributeModifiers);
  }
}
