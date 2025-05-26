package name.modid.helpers.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.Arrays;

public record GemstoneSlot(Gemstone[] gemstones) {
  public static final Codec<GemstoneSlot> GEMSTONE_SLOTS_CODEC = RecordCodecBuilder.create(builder -> {
    return builder.group(
      Gemstone.GEMSTONE_CODEC.listOf().fieldOf("slot").forGetter(item -> Arrays.asList(item.gemstones))
    ).apply(builder, list -> new GemstoneSlot(list.toArray(new Gemstone[0])));
  });
}
