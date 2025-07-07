package name.modid.helpers.types;

import com.mojang.serialization.Codec;

public enum GemstoneRarityType {
  NONE(-1), COMMON(0), UNCOMMON(1), RARE(2), LEGENDARY(3), UNUSUAL(4);

  private final int value;

  GemstoneRarityType(int value) { this.value = value; }

  public int getValue() { return value; }

  public static final Codec<GemstoneRarityType> CODEC = Codec.STRING.xmap(GemstoneRarityType::valueOf,
      GemstoneRarityType::name);
}
