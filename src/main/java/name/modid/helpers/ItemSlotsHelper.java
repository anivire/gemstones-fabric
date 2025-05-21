package name.modid.helpers;

import it.unimi.dsi.fastutil.Arrays;
import name.modid.helpers.components.GemstoneSlot;
import name.modid.helpers.components.ItemGemstoneSlots;
import name.modid.helpers.types.GemstoneType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.AxeItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SwordItem;

import java.util.UUID;

public class ItemSlotsHelper {
    public static final int MAX_SLOTS = 5;

    public static boolean isGemsoneSlotsExists(ItemStack itemStack) {
        ItemGemstoneSlots gemstoneSlots = itemStack.get(ComponentsHelper.GEMSTONE_SLOTS);
        return gemstoneSlots != null;
    }

    public static int getTotalGemstoneSlots(ItemStack itemStack) {
        ItemGemstoneSlots gemstoneSlots = itemStack.get(ComponentsHelper.GEMSTONE_SLOTS);
        if (gemstoneSlots == null) return 0;

        return gemstoneSlots.countFreeSlots();
    }

    public static GemstoneSlot getGemstoneSlot(ItemStack itemStack, int index) {
        ItemGemstoneSlots gemstoneSlots = itemStack.get(ComponentsHelper.GEMSTONE_SLOTS);
        if (gemstoneSlots == null) return null;

        return gemstoneSlots.slots()[index];
    }

    public static GemstoneSlot[] getGemstoneSlots(ItemStack itemStack) {
        ItemGemstoneSlots gemstoneSlots = itemStack.get(ComponentsHelper.GEMSTONE_SLOTS);
        if (gemstoneSlots == null) return null;

        return gemstoneSlots.slots();
    }

    public static void setItemSlots(ItemStack itemStack, Item item) {
        if (isItemValid(item) && !isGemsoneSlotsExists(itemStack)) {
            GemstoneSlot[] slots = new GemstoneSlot[MAX_SLOTS];

            for (int i = 0; i < MAX_SLOTS; i++) {
                slots[i] = new GemstoneSlot(GemstoneHelper.getGemstoneString(GemstoneType.LOCKED), UUID.randomUUID(), false);
            }

            ItemGemstoneSlots itemGemstoneSlots = new ItemGemstoneSlots(slots);
            itemStack.set(ComponentsHelper.GEMSTONE_SLOTS, itemGemstoneSlots);
        }
    }

    public static boolean isItemValid(Item item) {
        return item instanceof PickaxeItem
            || item instanceof BowItem
            || item instanceof ArmorItem
            || item instanceof SwordItem
            || item instanceof AxeItem
            || item instanceof ShovelItem
            || item instanceof CrossbowItem;
    }
}