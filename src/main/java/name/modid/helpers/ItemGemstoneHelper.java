package name.modid.helpers;

import name.modid.helpers.components.Gemstone;
import name.modid.helpers.components.GemstoneSlots;
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

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ItemGemstoneHelper {
  public static final int MAX_SLOTS = 5;
  
  public static boolean isItemValid(Item item) {
    return item instanceof PickaxeItem
      || item instanceof BowItem
      || item instanceof ArmorItem
      || item instanceof SwordItem
      || item instanceof AxeItem
      || item instanceof ShovelItem
      || item instanceof CrossbowItem;
  }
  
  public static boolean isGemstonesExists(ItemStack itemStack) {
    return itemStack.get(ComponentsHelper.GEMSTONES) != null;
  }
  
  public static Gemstone[] getGemstones(ItemStack itemStack) {
    return itemStack.get(ComponentsHelper.GEMSTONES).gemstones();
  }
  
  public static GemstoneSlots getGemstonesSlot(ItemStack itemStack) {
    return itemStack.get(ComponentsHelper.GEMSTONES);
  }
  
  public static Gemstone getGemstoneByIndex(ItemStack itemStack, int index) {
    return itemStack.get(ComponentsHelper.GEMSTONES).gemstones()[index];
  }
  
  public static Integer getGemstoneFirstEmptyIndex(ItemStack itemStack) {
    GemstoneSlots gemstoneSlots = getGemstonesSlot(itemStack);
    if (gemstoneSlots == null) return null;
    
    Gemstone[] gemstones = gemstoneSlots.gemstones();
    if (gemstones == null) return null;
    
    for (int i = 0; i < gemstones.length; i++) {
      Gemstone gemstone = gemstones[i];
      if (gemstone != null && gemstone.gemstoneType() == GemstoneType.EMPTY) {
        return i;
      }
    }
    
    return null;
  }
  
  public static ItemStack setGemstoneByIndex(ItemStack itemStack, int index, Gemstone gemstone) {
    GemstoneSlots sourceSlots = getGemstonesSlot(itemStack);
    if (sourceSlots == null && index < 0 || index >= MAX_SLOTS) return null;
    
    Gemstone[] newGemstones = Arrays.copyOf(sourceSlots.gemstones(), sourceSlots.gemstones().length);
    
    newGemstones[index] = gemstone;
    itemStack.set(ComponentsHelper.GEMSTONES, new GemstoneSlots(newGemstones));
    updateItemSlotBonuses(itemStack, itemStack.getItem());
    
    return itemStack;
  }
  
  public static void initItemSlots(ItemStack itemStack, Item item) {
    if (!isItemValid(item)) return;
    
    // Only initialize on server side to prevent duplication
    if (itemStack.getHolder() != null && itemStack.getHolder().getWorld().isClient) return;
    
    GemstoneSlots currentSlots = itemStack.get(ComponentsHelper.GEMSTONES);
    if (currentSlots == null || currentSlots.gemstones().length != MAX_SLOTS) {
      Gemstone[] gemstones = new Gemstone[MAX_SLOTS];
      
      for (int i = 0; i < MAX_SLOTS; i++) {
        gemstones[i] = new Gemstone(GemstoneType.EMPTY, GemstoneRarityType.NONE);
      }
      
      itemStack.set(ComponentsHelper.GEMSTONES, new GemstoneSlots(gemstones));
      updateItemSlotBonuses(itemStack, item);
    }
  }
  
  public static void updateItemSlotBonuses(ItemStack itemStack, Item item) {
    if (!isItemValid(item) && !isGemstonesExists(itemStack)) return;
    
    Gemstone[] gemstones = getGemstones(itemStack);
    if (gemstones == null) return;
    
    Map<GemstoneType, Integer> itemGems = new HashMap<>();
    for (Gemstone slot : gemstones) {
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
}