package name.modid.items;

import name.modid.helpers.types.GemstoneRarityType;
import name.modid.helpers.types.GemstoneType;

public class RubyGemstoneItem extends GemstoneItem {
  public RubyGemstoneItem(Settings settings) {
    super(settings);
    this.type = GemstoneType.RUBY;
    this.rarityType = GemstoneRarityType.COMMON;
  }

//  @Override
//  public void appendTooltip(ItemStack itemStack, Item.TooltipContext context, List<Text> tooltip, TooltipType type) {
//    tooltip.add(Text.translatable("tooltip.gemstones.ruby_gemstone.tooltip").formatted(Formatting.GRAY));
//  }
}