package name.modid.helpers.modifiers.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import name.modid.helpers.modifiers.GemstoneModifier;
import name.modid.helpers.modifiers.ModifierData;
import name.modid.helpers.modifiers.ModifierItemType;
import name.modid.helpers.modifiers.types.ModifierAttribute;
import name.modid.helpers.types.GemstoneType;
import net.minecraft.entity.attribute.EntityAttributeModifier.Operation;
import net.minecraft.entity.attribute.EntityAttributes;

public record SapphireModifierData() implements ModifierData {
  private static final Map<ModifierItemType, GemstoneModifier> MODIFIERS = new HashMap<>();

  static {
    MODIFIERS.put(ModifierItemType.MELEE,
        new ModifierAttribute(Operation.ADD_VALUE, new ArrayList<Double>(Arrays.asList(0.5, 1.0, 1.5, 2.0)),
            ModifierItemType.MELEE, EntityAttributes.GENERIC_ATTACK_DAMAGE, GemstoneType.SAPPHIRE));

    MODIFIERS.put(ModifierItemType.RANGED,
        new ModifierAttribute(Operation.ADD_VALUE, new ArrayList<Double>(Arrays.asList(0.5, 1.0, 1.5, 2.0)),
            ModifierItemType.RANGED, EntityAttributes.GENERIC_ATTACK_DAMAGE, GemstoneType.SAPPHIRE));

    MODIFIERS.put(ModifierItemType.TOOLS,
        new ModifierAttribute(Operation.ADD_MULTIPLIED_TOTAL, new ArrayList<Double>(Arrays.asList(0.5, 1.0, 1.5, 2.0)),
            ModifierItemType.TOOLS, EntityAttributes.PLAYER_BLOCK_BREAK_SPEED, GemstoneType.SAPPHIRE));

    MODIFIERS.put(ModifierItemType.ARMOR,
        new ModifierAttribute(Operation.ADD_VALUE, new ArrayList<Double>(Arrays.asList(1.0, 2.0, 3.0, 4.0)),
            ModifierItemType.ARMOR, EntityAttributes.GENERIC_MAX_HEALTH, GemstoneType.SAPPHIRE));
  }

  @Override
  public Map<ModifierItemType, GemstoneModifier> getModifiers() {
    return new HashMap<>(MODIFIERS);
  }
}
