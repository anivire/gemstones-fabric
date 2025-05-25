package name.modid.helpers.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.Arrays;

public record ItemGemstoneSlot(GemstoneSlot[] gemstoneSlots) {
  public static final Codec<ItemGemstoneSlot> GEMSTONE_SLOTS_CODEC = RecordCodecBuilder.create(builder -> {
    return builder.group(
      GemstoneSlot.GEMSTONE_CODEC.listOf().fieldOf("slot").forGetter(item -> Arrays.asList(item.gemstoneSlots))
    ).apply(builder, list -> new ItemGemstoneSlot(list.toArray(new GemstoneSlot[0])));
  });
}
