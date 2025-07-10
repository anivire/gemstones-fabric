package name.modid.items.geodes;

import java.util.ArrayList;

import name.modid.helpers.types.GemstoneRarityType;
import name.modid.helpers.types.GemstoneType;

public class GeodeDeepslateItem extends GeodeItem {
  public GeodeDeepslateItem(Settings settings, GemstoneRarityType rarityCap,
      ArrayList<GemstoneType> includedGemstones) {
    super(settings, rarityCap, new ArrayList<GemstoneType>(includedGemstones));
  }
}
