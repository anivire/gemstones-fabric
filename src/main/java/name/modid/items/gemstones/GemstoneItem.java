package name.modid.items.gemstones;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import name.modid.helpers.GemstoneTooltipHelper;
import name.modid.helpers.modifiers.GemstoneModifier;
import name.modid.helpers.modifiers.GemstoneModifierHelper;
import name.modid.helpers.modifiers.ModifierItemType;
import name.modid.helpers.types.GemstoneRarityType;
import name.modid.helpers.types.GemstoneType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class GemstoneItem extends Item {
  protected GemstoneType gemstoneType;
  protected GemstoneRarityType rarityType;

  public GemstoneItem(Settings settings, GemstoneType gemstoneType, GemstoneRarityType rarityType) {
    super(settings);

    this.gemstoneType = gemstoneType;
    this.rarityType = rarityType;
  }

  public GemstoneType getType() {
    return this.gemstoneType;
  }

  public GemstoneRarityType getRarityType() {
    return this.rarityType;
  }

  @Override
  public void appendTooltip(ItemStack itemStack, TooltipContext context, List<Text> tooltip, TooltipType type) {
    GemstoneItem gemstoneItem = (GemstoneItem) itemStack.getItem();
    GemstoneType gemstoneType = gemstoneItem.getType();
    Map<ModifierItemType, GemstoneModifier> gemstoneModifiers = new LinkedHashMap<>(
        GemstoneModifierHelper.getGemstoneModifiers(gemstoneType, itemStack.getItem()));

    tooltip.add(GemstoneTooltipHelper.getGemstoneRaritySprite(gemstoneItem.getRarityType()));
    tooltip.add(Text.literal(""));

    List<ModifierItemType> modifierOrder = Arrays.asList(ModifierItemType.MELEE, ModifierItemType.RANGED,
        ModifierItemType.TOOLS, ModifierItemType.ARMOR);

    gemstoneModifiers.entrySet().stream()
        .sorted(Comparator.comparingInt(entry -> modifierOrder.indexOf(entry.getKey()))).forEachOrdered(entry -> {
          GemstoneModifier modifier = entry.getValue();
          if (gemstoneType != GemstoneType.LOCKED && gemstoneType != GemstoneType.EMPTY) {
            tooltip.add(modifier.getGemstoneTooltipString(gemstoneItem.getRarityType()));
          }
        });
  }
}