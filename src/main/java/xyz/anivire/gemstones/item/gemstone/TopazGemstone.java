package xyz.anivire.gemstones.item.gemstone;

import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class TopazGemstone extends Gemstone {
  public TopazGemstone(Settings settings) {
    super(settings);
  }

  @Override
  public void appendTooltip(ItemStack itemStack, Item.TooltipContext context, List<Text> tooltip, TooltipType type) {
    tooltip.add(Text.translatable("tooltip.gemstones.topaz_gemstone.tooltip").formatted(Formatting.GRAY));
  }
}
