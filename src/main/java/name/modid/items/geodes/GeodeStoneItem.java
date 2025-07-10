package name.modid.items.geodes;

import java.util.ArrayList;

import name.modid.helpers.types.GemstoneRarityType;
import name.modid.helpers.types.GemstoneType;

public class GeodeStoneItem extends GeodeItem {
  public GeodeStoneItem(Settings settings, ArrayList<GemstoneRarityType> gemstoneRarities,
      ArrayList<GemstoneType> includedGemstones) {
    super(settings, new ArrayList<>(gemstoneRarities), new ArrayList<>(includedGemstones));
  }
}
