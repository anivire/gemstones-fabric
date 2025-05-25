package name.modid.helpers.modifiers;

import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.registry.entry.RegistryEntry;

public record GemstoneModifierData(
  EntityAttributeModifier modifier,
  AttributeModifierSlot slot,
  RegistryEntry<EntityAttribute> attribute
) {
}