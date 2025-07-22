package name.modid.helpers.modifiers;

import name.modid.helpers.modifiers.data.CelestineModifierData;
import name.modid.helpers.modifiers.data.RubyModifierData;
import name.modid.helpers.modifiers.data.SapphireModifierData;
import name.modid.helpers.modifiers.data.TopazModifierData;
import name.modid.helpers.modifiers.data.ZirconModifierData;
import name.modid.helpers.types.GemstoneType;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.AxeItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.Item;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SwordItem;

import java.util.HashMap;
import java.util.Map;

public class GemstoneModifierHelper {
  private static final Map<GemstoneType, GemstonesModifierData> MODIFIER_REGISTRY = new HashMap<>();

  static {
    MODIFIER_REGISTRY.put(GemstoneType.RUBY, new RubyModifierData());
    MODIFIER_REGISTRY.put(GemstoneType.CELESTINE, new CelestineModifierData());
    MODIFIER_REGISTRY.put(GemstoneType.TOPAZ, new TopazModifierData());
    MODIFIER_REGISTRY.put(GemstoneType.SAPPHIRE, new SapphireModifierData());
    MODIFIER_REGISTRY.put(GemstoneType.ZIRCON, new ZirconModifierData());
  }

  public static Map<GemstoneModifierItemType, GemstoneModifier> getGemstoneModifiers(GemstoneType gemstoneType,
      Item item) {
    if (gemstoneType == GemstoneType.EMPTY || gemstoneType == GemstoneType.LOCKED)
      return null;

    GemstonesModifierData modifiersData = MODIFIER_REGISTRY.get(gemstoneType);

    Map<GemstoneModifierItemType, GemstoneModifier> modifiers = modifiersData.getModifiers();

    return modifiers;
  }

  public static GemstoneModifier getGemstoneModifierForItem(GemstoneType gemstoneType, Item item) {
    if (gemstoneType == GemstoneType.EMPTY || gemstoneType == GemstoneType.LOCKED)
      return null;

    GemstonesModifierData modifiersData = MODIFIER_REGISTRY.get(gemstoneType);

    Map<GemstoneModifierItemType, GemstoneModifier> modifiers = modifiersData.getModifiers();
    GemstoneModifier modifier = modifiers.get(getModifieritemSlot(item));

    return modifier;
  }

  public static AttributeModifierSlot getAttributeModifierSlot(Item item) {
    if (item instanceof ArmorItem armorItem) {
      return switch (armorItem.getSlotType()) {
      case HEAD -> AttributeModifierSlot.HEAD;
      case CHEST -> AttributeModifierSlot.CHEST;
      case LEGS -> AttributeModifierSlot.LEGS;
      case FEET -> AttributeModifierSlot.FEET;
      default -> AttributeModifierSlot.CHEST;
      };
    }

    return AttributeModifierSlot.MAINHAND;
  }

  public static GemstoneModifierItemType getModifieritemSlot(Item item) {
    if (item instanceof SwordItem) {
      return GemstoneModifierItemType.MELEE;
    }

    if (item instanceof BowItem || item instanceof CrossbowItem) {
      return GemstoneModifierItemType.RANGED;
    }

    if (item instanceof AxeItem || item instanceof PickaxeItem || item instanceof ShovelItem) {
      return GemstoneModifierItemType.TOOLS;
    }

    if (item instanceof ArmorItem) {
      return GemstoneModifierItemType.ARMOR;
    }

    return GemstoneModifierItemType.TOOLS;
  }
}