package name.modid.helpers.types;

import com.mojang.serialization.Codec;

public enum GemstoneType {
  EMPTY, LOCKED, RUBY, CELESTINE;

  public static final Codec<GemstoneType> CODEC = Codec.STRING.xmap(GemstoneType::valueOf, GemstoneType::name);
}