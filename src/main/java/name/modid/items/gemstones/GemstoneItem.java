package name.modid.items.gemstones;

import name.modid.helpers.types.GemstoneRarityType;
import name.modid.helpers.types.GemstoneType;
import net.minecraft.item.Item;

public class GemstoneItem extends Item {
  protected GemstoneType gemstoneType;
  protected GemstoneRarityType rarityType;

  public GemstoneItem(Settings settings, GemstoneType gemstoneType, GemstoneRarityType rarityType) {
    super(settings);

    this.gemstoneType = gemstoneType;
    this.rarityType = rarityType;
  }

  public GemstoneType getType() {
    return this.gemstoneType;
  }

  public GemstoneRarityType getRarityType() {
    return this.rarityType;
  }
}