package name.modid.helpers.modifiers.types;

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
import net.minecraft.util.Identifier;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributeModifier.Operation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ModifierAttribute implements GemstoneModifier {
  protected Operation operation;
  protected ModifierItemType itemType;
  protected ArrayList<Double> modifierValuesList = new ArrayList<Double>();
  protected RegistryEntry<EntityAttribute> attr;
  protected String tooltipString;
  protected GemstoneType gemstoneType;
  protected GemstoneRarityType rarityType;

  public ModifierAttribute(Operation operation, ArrayList<Double> modifierValuesList, String tooltipString,
      ModifierItemType itemType, RegistryEntry<EntityAttribute> attr, GemstoneType gemstoneType) {
    this.operation = operation;
    this.modifierValuesList = new ArrayList<Double>(modifierValuesList);
    this.tooltipString = tooltipString;
    this.itemType = itemType;
    this.attr = attr;
    this.gemstoneType = gemstoneType;
  }

  public String getTooltipString() { return this.tooltipString; }

  public GemstoneType getGemstoneType() { return this.gemstoneType; }

  public GemstoneRarityType getRarityType() { return this.rarityType; }

  @Override
  public void applyBonus(ItemStack itemStack, Item item, Integer slotIndex, GemstoneRarityType gemstoneRarityType) {
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
