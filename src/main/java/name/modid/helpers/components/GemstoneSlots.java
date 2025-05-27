package name.modid.helpers.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.Arrays;

public record GemstoneSlots(Gemstone[] gemstones) {
  public static final Codec<GemstoneSlots> GEMSTONE_SLOTS_CODEC = RecordCodecBuilder.create(builder -> {
    return builder
      .group(Gemstone.GEMSTONE_CODEC.listOf()
        .fieldOf("slots")
        .forGetter(item -> Arrays.asList(item.gemstones)))
      .apply(builder, list -> new GemstoneSlots(list.toArray(new Gemstone[0])));
  });
  
  // Proper equals and hashCode implementations
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    GemstoneSlots that = (GemstoneSlots) o;
    return Arrays.equals(gemstones, that.gemstones);
  }
  
  @Override
  public int hashCode() {
    return Arrays.hashCode(gemstones);
  }
}
