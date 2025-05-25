package name.modid.helpers.modifiers;

import name.modid.helpers.types.GemstoneType;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.Item;
import net.minecraft.registry.entry.RegistryEntry;

public class GemstoneModifier {
  public static EntityAttributeModifier getModifier(GemstoneType gemType, Item item) {
    return GemstoneModifierRegistry.getModifier(gemType, item)
      .map(GemstoneModifierData::modifier)
      .orElse(null);
  }
  
  public static RegistryEntry<EntityAttribute> getTargetAttribute(GemstoneType gemType, Item item) {
    return GemstoneModifierRegistry.getModifier(gemType, item)
      .map(GemstoneModifierData::attribute)
      .orElse(null);
  }
  
  public static AttributeModifierSlot getTargetGroup(GemstoneType gemType, Item item) {
    return GemstoneModifierRegistry.getModifier(gemType, item)
      .map(GemstoneModifierData::slot)
      .orElse(AttributeModifierSlot.MAINHAND);
  }
}
