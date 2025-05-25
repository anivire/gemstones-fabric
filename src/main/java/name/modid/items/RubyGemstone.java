package name.modid.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class RubyGemstone extends Gemstone {
  public RubyGemstone(Settings settings) {
    super(settings);
  }
  
  @Override
  public void appendTooltip(ItemStack itemStack, Item.TooltipContext context, List<Text> tooltip, TooltipType type) {
    tooltip.add(Text.translatable("tooltip.gemstones.ruby_gemstone.tooltip").formatted(Formatting.GRAY));
  }
}