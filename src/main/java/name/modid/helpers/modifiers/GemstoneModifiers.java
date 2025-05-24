package name.modid.helpers.modifiers;

import name.modid.Gemstones;
import name.modid.helpers.types.GemstoneType;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.AxeItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.Item;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SwordItem;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class GemstoneModifiers {
  public enum ItemType {
    SWORD,
    AXE,
    PICKAXE,
    SHOVEL,
    ARMOR,
    BOW,
    CROSSBOW,
    OTHER
  }
  
  public static ItemType getItemType(Item item) {
    if (item instanceof SwordItem) return ItemType.SWORD;
    if (item instanceof AxeItem) return ItemType.AXE;
    if (item instanceof PickaxeItem) return ItemType.PICKAXE;
    if (item instanceof ShovelItem) return ItemType.SHOVEL;
    if (item instanceof ArmorItem) return ItemType.ARMOR;
    if (item instanceof BowItem) return ItemType.BOW;
    if (item instanceof CrossbowItem) return ItemType.CROSSBOW;
    return ItemType.OTHER;
  }
  
  private static final Map<GemstoneType, Map<ItemType, Supplier<EntityAttributeModifier>>> MODIFIERS = new HashMap<>();
  
  static {
    MODIFIERS.put(GemstoneType.RUBY, new HashMap<>());
    MODIFIERS.get(GemstoneType.RUBY).put(ItemType.SWORD, () -> new EntityAttributeModifier(
      Identifier.of(Gemstones.MOD_ID, "ruby_attack_damage_modifier"),
      1.0,
      EntityAttributeModifier.Operation.ADD_VALUE
    ));
    MODIFIERS.get(GemstoneType.RUBY).put(ItemType.ARMOR, () -> new EntityAttributeModifier(
      Identifier.of(Gemstones.MOD_ID, "ruby_armor_modifier"),
      10.0,
      EntityAttributeModifier.Operation.ADD_VALUE
    ));
    MODIFIERS.get(GemstoneType.RUBY).put(ItemType.PICKAXE, () -> new EntityAttributeModifier(
      Identifier.of(Gemstones.MOD_ID, "ruby_pickaxe_tool_mine_speed_modifier"),
      0.1,
      EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
    ));
    MODIFIERS.get(GemstoneType.RUBY).put(ItemType.AXE, () -> new EntityAttributeModifier(
      Identifier.of(Gemstones.MOD_ID, "ruby_axe_tool_mine_speed_modifier"),
      0.1,
      EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
    ));
    MODIFIERS.get(GemstoneType.RUBY).put(ItemType.SHOVEL, () -> new EntityAttributeModifier(
      Identifier.of(Gemstones.MOD_ID, "ruby_shovel_tool_mine_speed_modifier"),
      0.1,
      EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
    ));
  }
  
  public static EntityAttributeModifier getModifier(GemstoneType gemType, Item item) {
    ItemType itemType = getItemType(item);
    if (MODIFIERS.containsKey(gemType) && MODIFIERS.get(gemType).containsKey(itemType)) {
      return MODIFIERS.get(gemType).get(itemType).get();
    }
    return null;
  }
  
  public static RegistryEntry<EntityAttribute> getTargetAttribute(GemstoneType gemType, Item item) {
    switch (gemType) {
      case RUBY:
        if (item instanceof ArmorItem) return EntityAttributes.ARMOR;
        else if (item instanceof SwordItem) return EntityAttributes.ATTACK_DAMAGE;
        else if (item instanceof ShovelItem || item instanceof PickaxeItem || item instanceof AxeItem)
          return EntityAttributes.BLOCK_BREAK_SPEED;
      default:
        return EntityAttributes.ATTACK_DAMAGE;
    }
  }
  
  public static AttributeModifierSlot getTargetGroup(GemstoneType gemType, Item item) {
    switch (gemType) {
      case RUBY:
        if (item instanceof ArmorItem armorItem) {
          //...
        } else if (item instanceof SwordItem
          || item instanceof AxeItem
          || item instanceof PickaxeItem
          || item instanceof ShovelItem
          || item instanceof BowItem
          || item instanceof CrossbowItem)
          return AttributeModifierSlot.MAINHAND;
      default:
        return AttributeModifierSlot.MAINHAND;
    }
  }
}
