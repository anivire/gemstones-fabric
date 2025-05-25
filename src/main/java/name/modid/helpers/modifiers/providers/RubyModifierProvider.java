package name.modid.helpers.modifiers.providers;

import name.modid.Gemstones;
import name.modid.helpers.modifiers.GemstoneModifierData;
import name.modid.helpers.modifiers.GemstoneModifierProvider;
import name.modid.helpers.modifiers.ItemType;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

import java.util.Map;
import java.util.Optional;

public class RubyModifierProvider implements GemstoneModifierProvider {
  private static final Map<ItemType, GemstoneModifierData> MODIFIERS = Map.of(
    ItemType.SWORD, new GemstoneModifierData(
      new EntityAttributeModifier(
        Identifier.of(Gemstones.MOD_ID, "ruby_attack_damage_modifier"),
        1.0,
        EntityAttributeModifier.Operation.ADD_VALUE
      ),
      AttributeModifierSlot.MAINHAND,
      EntityAttributes.ATTACK_DAMAGE
    ),
    ItemType.ARMOR_HEAD, new GemstoneModifierData(
      new EntityAttributeModifier(
        Identifier.of(Gemstones.MOD_ID, "ruby_armor_head_modifier"),
        2.0,
        EntityAttributeModifier.Operation.ADD_VALUE
      ),
      AttributeModifierSlot.HEAD,
      EntityAttributes.ARMOR
    ),
    ItemType.ARMOR_CHEST, new GemstoneModifierData(
      new EntityAttributeModifier(
        Identifier.of(Gemstones.MOD_ID, "ruby_armor_chest_modifier"),
        2.0,
        EntityAttributeModifier.Operation.ADD_VALUE
      ),
      AttributeModifierSlot.CHEST,
      EntityAttributes.ARMOR
    ),
    ItemType.ARMOR_LEGS, new GemstoneModifierData(
      new EntityAttributeModifier(
        Identifier.of(Gemstones.MOD_ID, "ruby_armor_legs_modifier"),
        2.0,
        EntityAttributeModifier.Operation.ADD_VALUE
      ),
      AttributeModifierSlot.LEGS,
      EntityAttributes.ARMOR
    ),
    ItemType.ARMOR_FEET, new GemstoneModifierData(
      new EntityAttributeModifier(
        Identifier.of(Gemstones.MOD_ID, "ruby_armor_feet_modifier"),
        2.0,
        EntityAttributeModifier.Operation.ADD_VALUE
      ),
      AttributeModifierSlot.FEET,
      EntityAttributes.ARMOR
    ),
    ItemType.PICKAXE, new GemstoneModifierData(
      new EntityAttributeModifier(
        Identifier.of(Gemstones.MOD_ID, "ruby_pickaxe_tool_mine_speed_modifier"),
        0.1,
        EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
      ),
      AttributeModifierSlot.MAINHAND,
      EntityAttributes.BLOCK_BREAK_SPEED
    ),
    ItemType.AXE, new GemstoneModifierData(
      new EntityAttributeModifier(
        Identifier.of(Gemstones.MOD_ID, "ruby_axe_tool_mine_speed_modifier"),
        0.1,
        EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
      ),
      AttributeModifierSlot.MAINHAND,
      EntityAttributes.BLOCK_BREAK_SPEED
    ),
    ItemType.SHOVEL, new GemstoneModifierData(
      new EntityAttributeModifier(
        Identifier.of(Gemstones.MOD_ID, "ruby_shovel_tool_mine_speed_modifier"),
        0.1,
        EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
      ),
      AttributeModifierSlot.MAINHAND,
      EntityAttributes.BLOCK_BREAK_SPEED
    )
  );
  
  @Override
  public Optional<GemstoneModifierData> getModifierForItem(Item item) {
    ItemType itemType = ItemType.getItemType(item);
    return Optional.ofNullable(MODIFIERS.get(itemType));
  }
}
