package name.modid.helpers;

import name.modid.helpers.components.GemstoneSlot;
import name.modid.helpers.components.ItemGemstoneSlot;
import name.modid.helpers.modifiers.GemstoneModifier;
import name.modid.helpers.types.GemstoneRarityType;
import name.modid.helpers.types.GemstoneType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.AxeItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SwordItem;

import java.util.HashMap;
import java.util.Map;

public class ItemGemstoneSlotsHelper {
  public static final int MAX_SLOTS = 5;
  
  public static boolean isGemstoneSlotsExists(ItemStack itemStack) {
    ItemGemstoneSlot gemstoneSlots = itemStack.get(ComponentsHelper.GEMSTONES);
    return gemstoneSlots != null;
  }
  
  public static GemstoneSlot getGemstoneSlot(ItemStack itemStack, int index) {
    ItemGemstoneSlot gemstoneSlots = itemStack.get(ComponentsHelper.GEMSTONES);
    if (gemstoneSlots == null) return null;
    
    return gemstoneSlots.gemstoneSlots()[index];
  }
  
  public static GemstoneSlot[] getGemstoneSlots(ItemStack itemStack) {
    ItemGemstoneSlot gemstoneSlots = itemStack.get(ComponentsHelper.GEMSTONES);
    if (gemstoneSlots == null) return null;
    
    return gemstoneSlots.gemstoneSlots();
  }
  
  public static void initItemSlots(ItemStack itemStack, Item item) {
    if (isItemValid(item)) {
      itemStack.set(DataComponentTypes.MAX_STACK_SIZE, 1);
      
      ItemGemstoneSlot currentSlots = itemStack.get(ComponentsHelper.GEMSTONES);
      if (currentSlots == null || currentSlots.gemstoneSlots().length != MAX_SLOTS) {
        GemstoneSlot[] gemstoneSlots = new GemstoneSlot[MAX_SLOTS];
        
        for (int i = 0; i < MAX_SLOTS; i++) {
//          UUID uuid = UUID.randomUUID();
          if (i == 0) gemstoneSlots[i] = new GemstoneSlot(GemstoneType.RUBY, GemstoneRarityType.COMMON);
          else gemstoneSlots[i] = new GemstoneSlot(GemstoneType.LOCKED, GemstoneRarityType.NONE);
        }
        
        itemStack.set(ComponentsHelper.GEMSTONES, new ItemGemstoneSlot(gemstoneSlots));
        updateItemSlotBonuses(itemStack, item);
      }
    }
  }
  
  public static void updateItemSlotBonuses(ItemStack itemStack, Item item) {
    if (!isItemValid(item) && !isGemstoneSlotsExists(itemStack)) return;
    
    GemstoneSlot[] gemstoneSlots = getGemstoneSlots(itemStack);
    if (gemstoneSlots == null) return;
    
    Map<GemstoneType, Integer> itemGems = new HashMap<>();
    for (GemstoneSlot slot : gemstoneSlots) {
      if (slot.gemstoneType() != null && slot.gemstoneType() != GemstoneType.LOCKED) {
        itemGems.merge(slot.gemstoneType(), 1, Integer::sum);
      }
    }
    
    AttributeModifiersComponent gemstoneModifiers = itemStack.getOrDefault(
      DataComponentTypes.ATTRIBUTE_MODIFIERS,
      AttributeModifiersComponent.DEFAULT
    );
    
    for (Map.Entry<GemstoneType, Integer> entry : itemGems.entrySet()) {
      GemstoneType gemType = entry.getKey();
      int count = entry.getValue();
      
      EntityAttributeModifier baseModifier = GemstoneModifier.getModifier(gemType, item);
      if (baseModifier != null) {
        EntityAttributeModifier scaledModifier = new EntityAttributeModifier(
          baseModifier.id(),
          baseModifier.value() * count,
          baseModifier.operation()
        );
        
        gemstoneModifiers = gemstoneModifiers.with(
          GemstoneModifier.getTargetAttribute(gemType, item),
          scaledModifier,
          GemstoneModifier.getTargetGroup(gemType, item)
        );
      }
    }
    
    itemStack.set(DataComponentTypes.ATTRIBUTE_MODIFIERS, gemstoneModifiers);
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