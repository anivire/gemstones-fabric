package name.modid.items;

import name.modid.helpers.types.GemstoneRarityType;
import name.modid.helpers.types.GemstoneType;
import net.minecraft.item.Item;

public class GemstoneItem extends Item {
  protected GemstoneType type;
  protected GemstoneRarityType rarityType;
  
  public GemstoneItem(Settings settings) {
    super(settings);
  }
  
  public GemstoneType getType() {
    return this.type;
  }
  
  public GemstoneRarityType getRarityType() {
    return this.rarityType;
  }
}