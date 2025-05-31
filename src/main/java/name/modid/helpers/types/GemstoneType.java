package name.modid.helpers.types;

import com.mojang.serialization.Codec;

public enum GemstoneType {
  EMPTY,
  LOCKED,
  RUBY,
  CELESTINE,
  MALACHITE;
  
  public static final Codec<GemstoneType> CODEC = Codec.STRING.xmap(
    GemstoneType::valueOf,
    GemstoneType::name
  );
}