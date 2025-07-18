package name.modid.helpers.modifiers;

import name.modid.helpers.types.GemstoneRarityType;
import name.modid.helpers.types.GemstoneType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.world.World;

public interface GemstoneModifier {
  default void apply(ItemStack itemStack, Item item, Integer slotIndex, GemstoneRarityType gemstoneRarityType) {
    throw new UnsupportedOperationException("This modifier does not support effect-based apply.");
  };

  default void apply(ItemStack itemStack, Item item, Integer slotIndex, GemstoneRarityType gemstoneRarityType,
      LivingEntity target, World world) {
    throw new UnsupportedOperationException("This modifier does not support attribute-based apply.");
  };

  public MutableText getTooltipString(GemstoneRarityType gemstoneRarityType, Boolean withCategoryString);

  GemstoneType getGemstoneType();

  GemstoneRarityType getRarityType();

  void setRarityType(GemstoneRarityType type);
}
