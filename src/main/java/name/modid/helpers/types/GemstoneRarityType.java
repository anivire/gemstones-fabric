package name.modid.helpers.types;

import com.mojang.serialization.Codec;

public enum GemstoneRarityType {
  NONE,
  COMMON,
  UNCOMMON,
  RARE,
  LEGENDARY,
  UNUSUAL;
  
  public static final Codec<GemstoneRarityType> CODEC = Codec.STRING.xmap(
    GemstoneRarityType::valueOf,
    GemstoneRarityType::name
  );
}
