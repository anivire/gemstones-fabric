package name.modid.helpers.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Uuids;

import java.util.UUID;

public record GemstoneSlot(String gemstoneType, UUID gemstoneUUID, boolean isOccupied) {
    public static final Codec<GemstoneSlot> GEMSTONE_CODEC = RecordCodecBuilder.create(builder -> {
        return builder.group(
            Codec.STRING.fieldOf("gemstoneType").forGetter(GemstoneSlot::gemstoneType),
            Uuids.CODEC.fieldOf("gemstoneUUID").forGetter(GemstoneSlot::gemstoneUUID),
            Codec.BOOL.fieldOf("isOccupied").forGetter(GemstoneSlot::isOccupied)
        ).apply(builder, GemstoneSlot::new);
    });
}