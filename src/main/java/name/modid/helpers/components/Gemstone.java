package name.modid.helpers.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import name.modid.helpers.types.GemstoneRarityType;
import name.modid.helpers.types.GemstoneType;

public record Gemstone(GemstoneType gemstoneType, GemstoneRarityType gemstoneRarityType) {
  public static final Codec<Gemstone> GEMSTONE_CODEC = RecordCodecBuilder.create(builder -> {
    return builder.group(
      GemstoneType.CODEC.fieldOf("gemstoneType").forGetter(Gemstone::gemstoneType),
      GemstoneRarityType.CODEC.fieldOf("gemstoneRarityType").forGetter(Gemstone::gemstoneRarityType)
    ).apply(builder, Gemstone::new);
  });
}