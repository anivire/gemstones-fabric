package name.modid.helpers;

import name.modid.Gemstones;
import name.modid.helpers.components.Gemstone;
import name.modid.helpers.components.GemstoneSlots;
import name.modid.helpers.modifiers.GemstoneModifier;
import name.modid.helpers.modifiers.GemstoneModifierHelper;
import name.modid.helpers.modifiers.types.ModifierAttribute;
import name.modid.helpers.types.GemstoneRarityType;
import name.modid.helpers.types.GemstoneType;
import name.modid.items.gemstones.GemstoneItem;
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
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ItemGemstoneHelper {
  public static final int MAX_SLOTS = 5;

  public static boolean isItemValid(Item item) {
    return item instanceof PickaxeItem || item instanceof BowItem || item instanceof ArmorItem
        || item instanceof SwordItem || item instanceof AxeItem || item instanceof ShovelItem
        || item instanceof CrossbowItem;
  }

  public static Gemstone[] contains(ItemStack itemStack, GemstoneType gemstoneType) {
    Gemstone[] gemstones = itemStack.get(ComponentsHelper.GEMSTONES) != null
        ? itemStack.get(ComponentsHelper.GEMSTONES).gemstones()
        : new Gemstone[0];

    return Arrays.stream(gemstones).filter(gemstone -> gemstone != null && gemstone.gemstoneType() == gemstoneType)
        .toArray(Gemstone[]::new);
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
    if (gemstoneSlots == null)
      return null;

    Gemstone[] gemstones = gemstoneSlots.gemstones();
    if (gemstones == null)
      return null;

    for (int i = 0; i < gemstones.length; i++) {
      Gemstone gemstone = gemstones[i];
      if (gemstone != null && gemstone.gemstoneType() == GemstoneType.EMPTY) {
        return i;
      }
    }

    return null;
  }

  public static ItemStack setGemstoneByIndex(ItemStack itemStack, int index, GemstoneItem gemstone) {
    GemstoneSlots sourceGemstoneSlots = getGemstonesSlot(itemStack);
    if (sourceGemstoneSlots == null && index < 0 || index >= MAX_SLOTS)
      return null;

    Gemstone[] gemstones = Arrays.copyOf(sourceGemstoneSlots.gemstones(), sourceGemstoneSlots.gemstones().length);

    gemstones[index] = new Gemstone(gemstone.getType(), gemstone.getRarityType());
    itemStack.set(ComponentsHelper.GEMSTONES, new GemstoneSlots(gemstones));
    updateItemSlotBonuses(itemStack, itemStack.getItem());

    return itemStack;
  }

  public static void initItemSlots(ItemStack itemStack, Item item) {
    if (!isItemValid(item))
      return;

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
    if (!isItemValid(item) && !isGemstonesExists(itemStack))
      return;

    Gemstone[] gemstones = getGemstones(itemStack);
    if (gemstones == null)
      return;

    // Get all gemstones from item
    Map<Integer, Map<GemstoneType, GemstoneRarityType>> itemGemstones = new HashMap<>();
    for (int i = 0; i < gemstones.length; i++) {
      Gemstone gem = gemstones[i];

      if (gem.gemstoneType() != null && gem.gemstoneType() != GemstoneType.LOCKED) {
        Map<GemstoneType, GemstoneRarityType> m = new HashMap<>();
        m.put(gem.gemstoneType(), gem.gemstoneRarityType());
        itemGemstones.put(i, m);
      }
    }

    // Collect gemstone modifiers and apply bonuses
    ArrayList<ModifierAttribute> gemstoneModifiers = new ArrayList<>();
    for (Map.Entry<Integer, Map<GemstoneType, GemstoneRarityType>> m : itemGemstones.entrySet()) {
      Map<GemstoneType, GemstoneRarityType> i = m.getValue();

      for (Map.Entry<GemstoneType, GemstoneRarityType> e : i.entrySet()) {
        GemstoneType gemstoneType = e.getKey();
        GemstoneRarityType gemstoneRarity = e.getValue();
        GemstoneModifier gemstoneModifier = GemstoneModifierHelper.getGemstoneModifierForItem(gemstoneType, item);

        if (gemstoneModifier != null) {
          gemstoneModifier.setRarityType(gemstoneRarity);
          if (gemstoneModifier.getClass() == ModifierAttribute.class) {
            gemstoneModifiers.add((ModifierAttribute) (Object) gemstoneModifier);
          }
        }
      }
    }

    applyAttributeModifiers(gemstoneModifiers, item, itemStack);
  }

  public static void applyAttributeModifiers(ArrayList<ModifierAttribute> gemstoneModifiers, Item item,
      ItemStack itemStack) {
    AttributeModifiersComponent itemAttributeModifiers = itemStack.getOrDefault(DataComponentTypes.ATTRIBUTE_MODIFIERS,
        AttributeModifiersComponent.DEFAULT);
    List<AttributeModifiersComponent.Entry> newModifiers = new ArrayList<>();

    // Save all modifiers not equas gemstones namespace
    for (AttributeModifiersComponent.Entry entry : itemAttributeModifiers.modifiers()) {
      if (!entry.modifier().id().getNamespace().equals(Gemstones.MOD_ID)) {
        newModifiers.add(entry);
      }
    }

    // Create new modifiers
    for (ModifierAttribute modifier : gemstoneModifiers) {
      EntityAttributeModifier scaledGemstoneModifier = new EntityAttributeModifier(
          Identifier.of(Gemstones.MOD_ID,
              String.format("%s_gemstone_%s_modifier_slot%s", modifier.gemstoneType.toString().toLowerCase(),
                  modifier.itemType.toString().toLowerCase(), UUID.randomUUID().toString())),
          modifier.modifierValuesList.get(modifier.rarityType.getValue()),
          modifier.operation);

      newModifiers.add(new AttributeModifiersComponent.Entry(
          modifier.attr,
          scaledGemstoneModifier,
          GemstoneModifierHelper.getAttributeModifierSlot(item)));
    }

    itemAttributeModifiers = new AttributeModifiersComponent(newModifiers, itemAttributeModifiers.showInTooltip());
    itemStack.set(DataComponentTypes.ATTRIBUTE_MODIFIERS, itemAttributeModifiers);
  }
}