package name.modid.helpers.modifiers.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import name.modid.helpers.EffectRegistraionHelper;
import name.modid.helpers.modifiers.GemstoneModifier;
import name.modid.helpers.modifiers.ModifierData;
import name.modid.helpers.modifiers.ModifierItemType;
import name.modid.helpers.modifiers.types.ModifierAttribute;
import name.modid.helpers.modifiers.types.ModifierOnHitEffect;
import name.modid.helpers.types.GemstoneType;
import net.minecraft.entity.attribute.EntityAttributeModifier.Operation;
import net.minecraft.entity.attribute.EntityAttributes;

public record RubyModifierData() implements ModifierData {
  private static final Map<ModifierItemType, GemstoneModifier> MODIFIERS = new HashMap<>();

  static {
    MODIFIERS.put(ModifierItemType.MELEE,
        new ModifierOnHitEffect(new ArrayList<Double>(Arrays.asList(0.1, 0.2, 0.3, 0.4)), 6, 1,
            "tooltip.gemstones.socketed_ruby.melee_bonus", "tooltip.gemstones.item_ruby.melee_bonus",
            ModifierItemType.MELEE, EffectRegistraionHelper.BLEEDING_EFFECT));

    MODIFIERS.put(ModifierItemType.RANGED,
        new ModifierAttribute(Operation.ADD_VALUE, new ArrayList<Double>(Arrays.asList(0.5, 1.0, 1.5, 2.0)),
            "tooltip.gemstones.socketed_ruby.ranged_bonus", "tooltip.gemstones.item_ruby.ranged_bonus",
            ModifierItemType.RANGED, EntityAttributes.ATTACK_DAMAGE, GemstoneType.RUBY));

    MODIFIERS.put(ModifierItemType.TOOLS,
        new ModifierAttribute(Operation.ADD_MULTIPLIED_TOTAL, new ArrayList<Double>(Arrays.asList(0.5, 1.0, 1.5, 2.0)),
            "tooltip.gemstones.socketed_ruby.tools_bonus", "tooltip.gemstones.item_ruby.tools_bonus",
            ModifierItemType.TOOLS, EntityAttributes.BLOCK_BREAK_SPEED, GemstoneType.RUBY));

    MODIFIERS.put(ModifierItemType.ARMOR,
        new ModifierAttribute(Operation.ADD_VALUE, new ArrayList<Double>(Arrays.asList(1.0, 2.0, 3.0, 4.0)),
            "tooltip.gemstones.socketed_ruby.armor_bonus", "tooltip.gemstones.item_ruby.armor_bonus",
            ModifierItemType.ARMOR, EntityAttributes.MAX_HEALTH, GemstoneType.RUBY));
  }

  @Override
  public Map<ModifierItemType, GemstoneModifier> getModifiers() {
    return new HashMap<>(MODIFIERS);
  }
}
