package name.modid.items.geodes;

import java.util.ArrayList;

import name.modid.helpers.types.GemstoneRarityType;
import name.modid.helpers.types.GemstoneType;

public class GeodeStoneItem extends GeodeItem {
  public GeodeStoneItem(Settings settings, GemstoneRarityType rarityCap, ArrayList<GemstoneType> includedGemstones) {
    super(settings, rarityCap, new ArrayList<GemstoneType>(includedGemstones));
  }
}
