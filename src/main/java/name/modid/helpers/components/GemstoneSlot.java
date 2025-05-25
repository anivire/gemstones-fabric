package name.modid.helpers.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import name.modid.helpers.types.GemstoneRarityType;
import name.modid.helpers.types.GemstoneType;

// UUID gemstoneUUID,
// Uuids.CODEC.fieldOf("gemstoneUUID").forGetter(GemstoneSlot::gemstoneUUID),

public record GemstoneSlot(GemstoneType gemstoneType, GemstoneRarityType gemstoneRarityType) {
  public static final Codec<GemstoneSlot> GEMSTONE_CODEC = RecordCodecBuilder.create(builder -> {
    return builder.group(
      GemstoneType.CODEC.fieldOf("gemstoneType").forGetter(GemstoneSlot::gemstoneType),
      GemstoneRarityType.CODEC.fieldOf("gemstoneRarityType").forGetter(GemstoneSlot::gemstoneRarityType)
    ).apply(builder, GemstoneSlot::new);
  });
}