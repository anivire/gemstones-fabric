package name.modid.helpers.modifiers.providers;

import name.modid.Gemstones;
import name.modid.helpers.modifiers.GemstoneModifierData;
import name.modid.helpers.modifiers.GemstoneModifierProvider;
import name.modid.helpers.types.GemstoneType;
import name.modid.helpers.types.ItemType;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

import java.util.Map;
import java.util.Optional;

public class CelestineModifierProvider implements GemstoneModifierProvider {
  private final GemstoneType gemstoneType;
  
  public CelestineModifierProvider(GemstoneType gemstoneType) {
    this.gemstoneType = gemstoneType;
  }
  
  @Override
  public Optional<GemstoneModifierData> getModifierForItem(Item item) {
    ItemType itemType = ItemType.getItemType(item);
    return Optional.ofNullable(getModifiers(gemstoneType).get(itemType));
  }
  
  private static Map<ItemType, GemstoneModifierData> getModifiers(GemstoneType gemstoneType) {
    return Map.of(
      ItemType.SWORD, new GemstoneModifierData(
        new EntityAttributeModifier(
          Identifier.of(Gemstones.MOD_ID, String.format("gemstone_%s_attack_damage_modifier", gemstoneType.name().toLowerCase())),
          1.0,
          EntityAttributeModifier.Operation.ADD_VALUE
        ),
        AttributeModifierSlot.MAINHAND,
        EntityAttributes.ATTACK_DAMAGE
      ),
      ItemType.ARMOR_HEAD, new GemstoneModifierData(
        new EntityAttributeModifier(
          Identifier.of(Gemstones.MOD_ID, String.format("gemstone_%s_armor_head_modifier", gemstoneType.name().toLowerCase())),
          2.0,
          EntityAttributeModifier.Operation.ADD_VALUE
        ),
        AttributeModifierSlot.HEAD,
        EntityAttributes.ARMOR
      ),
      ItemType.ARMOR_CHEST, new GemstoneModifierData(
        new EntityAttributeModifier(
          Identifier.of(Gemstones.MOD_ID, String.format("gemstone_%s_armor_chest_modifier", gemstoneType.name().toLowerCase())),
          2.0,
          EntityAttributeModifier.Operation.ADD_VALUE
        ),
        AttributeModifierSlot.CHEST,
        EntityAttributes.ARMOR
      ),
      ItemType.ARMOR_LEGS, new GemstoneModifierData(
        new EntityAttributeModifier(
          Identifier.of(Gemstones.MOD_ID, String.format("gemstone_%s_armor_legs_modifier", gemstoneType.name().toLowerCase())),
          2.0,
          EntityAttributeModifier.Operation.ADD_VALUE
        ),
        AttributeModifierSlot.LEGS,
        EntityAttributes.ARMOR
      ),
      ItemType.ARMOR_FEET, new GemstoneModifierData(
        new EntityAttributeModifier(
          Identifier.of(Gemstones.MOD_ID, String.format("gemstone_%s_armor_feet_modifier", gemstoneType.name().toLowerCase())),
          2.0,
          EntityAttributeModifier.Operation.ADD_VALUE
        ),
        AttributeModifierSlot.FEET,
        EntityAttributes.ARMOR
      ),
      ItemType.PICKAXE, new GemstoneModifierData(
        new EntityAttributeModifier(
          Identifier.of(Gemstones.MOD_ID, String.format("gemstone_%s_pickaxe_tool_mine_speed_modifier", gemstoneType.name().toLowerCase())),
          0.1,
          EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        ),
        AttributeModifierSlot.MAINHAND,
        EntityAttributes.BLOCK_BREAK_SPEED
      ),
      ItemType.AXE, new GemstoneModifierData(
        new EntityAttributeModifier(
          Identifier.of(Gemstones.MOD_ID, String.format("gemstone_%s_axe_tool_mine_speed_modifier", gemstoneType.name().toLowerCase())),
          0.1,
          EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        ),
        AttributeModifierSlot.MAINHAND,
        EntityAttributes.BLOCK_BREAK_SPEED
      ),
      ItemType.SHOVEL, new GemstoneModifierData(
        new EntityAttributeModifier(
          Identifier.of(Gemstones.MOD_ID, String.format("gemstone_%s_shovel_tool_mine_speed_modifier", gemstoneType.name().toLowerCase())),
          0.1,
          EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        ),
        AttributeModifierSlot.MAINHAND,
        EntityAttributes.BLOCK_BREAK_SPEED
      )
    );
  }
}