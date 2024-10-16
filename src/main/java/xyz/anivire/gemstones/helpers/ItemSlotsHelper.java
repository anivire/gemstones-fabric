package xyz.anivire.gemstones.helpers;

import java.util.UUID;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolItem;
import xyz.anivire.gemstones.helpers.components.GemstoneSlot;
import xyz.anivire.gemstones.helpers.components.ItemGemstoneSlots;
import xyz.anivire.gemstones.item.gemstone.GemstoneType;

public class ItemSlotsHelper {
  public static boolean isGemsoneSlotsExists(ItemStack itemStack) {
    ItemGemstoneSlots gemstoneSlots = itemStack.get(ComponentsHelper.GEMSTONE_SLOTS);   
    if (gemstoneSlots == null) return false;
    else return true;
  }

  public static int getItemSlots(ItemStack itemStack) {
    ItemGemstoneSlots gemstoneSlots = itemStack.get(ComponentsHelper.GEMSTONE_SLOTS);
    if (gemstoneSlots == null) return 0;

    int freeSlots = gemstoneSlots.countFreeSlots();
    return freeSlots;
  }

  public static void setItemSlots(ItemStack itemStack, Item item, int gemstoneSlots) {
    if (isItemValid(item) && !isGemsoneSlotsExists(itemStack)) {
      GemstoneSlot[] slots = new GemstoneSlot[gemstoneSlots];
      for (int i = 0; i < gemstoneSlots; i++) {
        slots[i] = new GemstoneSlot(GemstoneHelper.getGemstoneString(GemstoneType.EMPTY), UUID.randomUUID(), false);
      }

      ItemGemstoneSlots itemGemstoneSlots = new ItemGemstoneSlots(slots);
			itemStack.set(ComponentsHelper.GEMSTONE_SLOTS, itemGemstoneSlots);
		}
  }

  public static boolean isItemValid(Item item) {
    if (item instanceof ToolItem || item instanceof BowItem || item instanceof ArmorItem || item instanceof SwordItem) {
			return true;
		} else {
      return false;
    }
  }
}
