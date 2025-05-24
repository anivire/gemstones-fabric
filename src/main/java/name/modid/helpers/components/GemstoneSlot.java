package name.modid.helpers.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import name.modid.helpers.types.GemstoneType;
import net.minecraft.util.Uuids;

import java.util.UUID;

public record GemstoneSlot(UUID gemstoneUUID, GemstoneType gemstoneType) {
  public static final Codec<GemstoneSlot> GEMSTONE_CODEC = RecordCodecBuilder.create(builder -> {
    return builder.group(
      Uuids.CODEC.fieldOf("gemstoneUUID").forGetter(GemstoneSlot::gemstoneUUID),
      GemstoneType.CODEC.fieldOf("gemstoneType").forGetter(GemstoneSlot::gemstoneType)
    ).apply(builder, GemstoneSlot::new);
  });
}