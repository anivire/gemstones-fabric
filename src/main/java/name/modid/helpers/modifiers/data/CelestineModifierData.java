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

public class CelestineModifierData implements ModifierData {
  private static final Map<ModifierItemType, GemstoneModifier> MODIFIERS = new HashMap<>();

  static {
    MODIFIERS.put(ModifierItemType.MELEE,
        new ModifierAttribute(Operation.ADD_VALUE, new ArrayList<Double>(Arrays.asList(0.5, 1.0, 1.5, 2.0)),
            "tooltip.gemstones.socketed_celestine.melee_bonus", ModifierItemType.MELEE, EntityAttributes.ATTACK_DAMAGE,
            GemstoneType.CELESTINE));

    MODIFIERS.put(ModifierItemType.RANGED,
        new ModifierAttribute(Operation.ADD_VALUE, new ArrayList<Double>(Arrays.asList(0.5, 1.0, 1.5, 2.0)),
            "tooltip.gemstones.socketed_celestine.ranged_bonus", ModifierItemType.RANGED,
            EntityAttributes.ATTACK_DAMAGE, GemstoneType.CELESTINE));

    MODIFIERS.put(ModifierItemType.TOOLS,
        new ModifierAttribute(Operation.ADD_VALUE, new ArrayList<Double>(Arrays.asList(0.5, 1.0, 1.5, 2.0)),
            "tooltip.gemstones.socketed_celestine.tools_bonus", ModifierItemType.TOOLS, EntityAttributes.ATTACK_DAMAGE,
            GemstoneType.CELESTINE));

    MODIFIERS.put(ModifierItemType.ARMOR,
        new ModifierAttribute(Operation.ADD_VALUE, new ArrayList<Double>(Arrays.asList(0.5, 1.0, 1.5, 2.0)),
            "tooltip.gemstones.socketed_celestine.armor_bonus", ModifierItemType.ARMOR, EntityAttributes.ATTACK_DAMAGE,
            GemstoneType.CELESTINE));
  }

  @Override
  public Map<ModifierItemType, GemstoneModifier> getModifiers() { return new HashMap<>(MODIFIERS); }

}
