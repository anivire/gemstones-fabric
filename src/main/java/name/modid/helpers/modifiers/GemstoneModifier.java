package name.modid.helpers.modifiers;

import name.modid.helpers.types.GemstoneRarityType;
import name.modid.helpers.types.GemstoneType;
import net.minecraft.text.MutableText;

public interface GemstoneModifier {
  public MutableText getTooltipString(GemstoneRarityType gemstoneRarityType, Boolean withCategoryString);

  public GemstoneType getGemstoneType();

  public GemstoneRarityType getRarityType();

  public void setRarityType(GemstoneRarityType type);
}
