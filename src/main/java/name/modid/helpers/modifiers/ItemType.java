package name.modid.helpers.modifiers;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.EquippableComponent;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.AxeItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.Item;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SwordItem;

public enum ItemType {
  SWORD,
  AXE,
  PICKAXE,
  SHOVEL,
  ARMOR_HEAD,
  ARMOR_CHEST,
  ARMOR_LEGS,
  ARMOR_FEET,
  BOW,
  CROSSBOW,
  OTHER;
  
  public static ItemType getItemType(Item item) {
    if (item instanceof SwordItem) return SWORD;
    if (item instanceof AxeItem) return AXE;
    if (item instanceof PickaxeItem) return PICKAXE;
    if (item instanceof ShovelItem) return SHOVEL;
    if (item instanceof BowItem) return BOW;
    if (item instanceof CrossbowItem) return CROSSBOW;
    if (item instanceof ArmorItem armorItem) {
      EquippableComponent e = armorItem.getComponents().get(DataComponentTypes.EQUIPPABLE);
      return switch (e.slot()) {
        case HEAD -> ARMOR_HEAD;
        case CHEST -> ARMOR_CHEST;
        case LEGS -> ARMOR_LEGS;
        case FEET -> ARMOR_FEET;
        default -> OTHER;
      };
    }
    return OTHER;
  }
}
