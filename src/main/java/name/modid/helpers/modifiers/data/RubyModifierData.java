package name.modid.helpers.modifiers.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import name.modid.entities.EffectRegistrationHelper;
import name.modid.helpers.modifiers.GemstoneModifier;
import name.modid.helpers.modifiers.GemstonesModifierData;
import name.modid.helpers.modifiers.GemstoneModifierItemType;
import name.modid.helpers.modifiers.types.ModifierAttribute;
import name.modid.helpers.modifiers.types.ModifierOnHitEffect;
import name.modid.helpers.types.GemstoneType;
import net.minecraft.entity.attribute.EntityAttributeModifier.Operation;
import net.minecraft.entity.attribute.EntityAttributes;

public record RubyModifierData() implements GemstonesModifierData {
  private static final Map<GemstoneModifierItemType, GemstoneModifier> MODIFIERS = new HashMap<>();

  static {
    MODIFIERS.put(GemstoneModifierItemType.MELEE,
        new ModifierOnHitEffect(new ArrayList<Double>(Arrays.asList(0.1, 0.2, 0.3, 0.4)), 6, 0,
            GemstoneModifierItemType.MELEE, EffectRegistrationHelper.BLEEDING_EFFECT, true, 5, GemstoneType.RUBY));

    MODIFIERS.put(GemstoneModifierItemType.RANGED,
        new ModifierAttribute(Operation.ADD_VALUE, new ArrayList<Double>(Arrays.asList(0.5, 1.0, 1.5, 2.0)),
            GemstoneModifierItemType.RANGED, EntityAttributes.GENERIC_ATTACK_DAMAGE, GemstoneType.RUBY));

    MODIFIERS.put(GemstoneModifierItemType.TOOLS,
        new ModifierAttribute(Operation.ADD_MULTIPLIED_TOTAL, new ArrayList<Double>(Arrays.asList(0.5, 1.0, 1.5, 2.0)),
            GemstoneModifierItemType.TOOLS, EntityAttributes.PLAYER_BLOCK_BREAK_SPEED, GemstoneType.RUBY));

    MODIFIERS.put(GemstoneModifierItemType.ARMOR,
        new ModifierAttribute(Operation.ADD_VALUE, new ArrayList<Double>(Arrays.asList(1.0, 2.0, 3.0, 4.0)),
            GemstoneModifierItemType.ARMOR, EntityAttributes.GENERIC_MAX_HEALTH, GemstoneType.RUBY));
  }

  @Override
  public Map<GemstoneModifierItemType, GemstoneModifier> getModifiers() {
    return new HashMap<>(MODIFIERS);
  }
}
