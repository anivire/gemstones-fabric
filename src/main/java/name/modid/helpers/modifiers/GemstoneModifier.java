package name.modid.helpers.modifiers;

import name.modid.helpers.types.GemstoneRarityType;
import name.modid.helpers.types.GemstoneType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;

public interface GemstoneModifier {
  void applyBonus(ItemStack itemStack, Item item, Integer slotIndex, GemstoneRarityType gemstoneRarityType);

  public MutableText getGemstoneTooltipString(GemstoneRarityType gemstoneRarityType);

  public String getSocketedTooltipString();

  GemstoneType getGemstoneType();

  GemstoneRarityType getRarityType();
}
