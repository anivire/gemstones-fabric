package name.modid.items;

import name.modid.helpers.types.GemstoneRarityType;
import name.modid.helpers.types.GemstoneType;

public class CelestineGemstoneItem extends GemstoneItem {
  public CelestineGemstoneItem(Settings settings, GemstoneRarityType rarityType) {
    super(settings, GemstoneType.CELESTINE, rarityType);
  }

  // @Override
  // public void appendTooltip(ItemStack itemStack, Item.TooltipContext context,
  // List<Text> tooltip, TooltipType type) {
  // tooltip.add(Text.translatable("tooltip.gemstones.ruby_gemstone.tooltip").formatted(Formatting.GRAY));
  // }
}