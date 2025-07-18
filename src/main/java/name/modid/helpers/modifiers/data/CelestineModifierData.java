package name.modid.helpers.modifiers.data;

import name.modid.helpers.attributes.AttributeRegistrationHelper;
import name.modid.helpers.modifiers.GemstoneModifier;
import name.modid.helpers.modifiers.ModifierData;
import name.modid.helpers.modifiers.ModifierItemType;
import name.modid.helpers.modifiers.types.ModifierAttribute;
import name.modid.helpers.types.GemstoneType;
import net.minecraft.entity.attribute.EntityAttributeModifier.Operation;
import net.minecraft.entity.attribute.EntityAttributes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CelestineModifierData implements ModifierData {
  private static final Map<ModifierItemType, GemstoneModifier> MODIFIERS = new HashMap<>();

  static {
    MODIFIERS.put(ModifierItemType.MELEE,
        new ModifierAttribute(Operation.ADD_MULTIPLIED_BASE,
            new ArrayList<Double>(Arrays.asList(0.05, 0.08, 0.14, 0.20)),
            ModifierItemType.MELEE, EntityAttributes.GENERIC_ATTACK_SPEED, GemstoneType.CELESTINE));

    MODIFIERS.put(ModifierItemType.RANGED,
        new ModifierAttribute(Operation.ADD_MULTIPLIED_BASE, new ArrayList<Double>(Arrays.asList(0.5, 1.2, 1.5, 2.2)),
            ModifierItemType.RANGED, AttributeRegistrationHelper.DRAW_SPEED_ATTRIBUTE, GemstoneType.CELESTINE));

    MODIFIERS.put(ModifierItemType.TOOLS,
        new ModifierAttribute(Operation.ADD_MULTIPLIED_BASE,
            new ArrayList<Double>(Arrays.asList(0.08, 0.10, 0.15, 0.25)),
            ModifierItemType.TOOLS, EntityAttributes.PLAYER_BLOCK_BREAK_SPEED, GemstoneType.CELESTINE));

    MODIFIERS.put(ModifierItemType.ARMOR,
        new ModifierAttribute(Operation.ADD_MULTIPLIED_BASE,
            new ArrayList<Double>(Arrays.asList(0.05, 0.08, 0.12, 0.18)),
            ModifierItemType.ARMOR, EntityAttributes.GENERIC_MOVEMENT_SPEED, GemstoneType.CELESTINE));
  }

  @Override
  public Map<ModifierItemType, GemstoneModifier> getModifiers() {
    return new HashMap<>(MODIFIERS);
  }

}
