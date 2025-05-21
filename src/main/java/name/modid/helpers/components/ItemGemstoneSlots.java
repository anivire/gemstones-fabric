package name.modid.helpers.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.Arrays;

public record ItemGemstoneSlots(GemstoneSlot[] slots) {
    public static final Codec<ItemGemstoneSlots> GEMSTONE_SLOTS_CODEC = RecordCodecBuilder.create(builder -> {
        return builder.group(
                GemstoneSlot.GEMSTONE_CODEC.listOf().fieldOf("slots").forGetter(item -> Arrays.asList(item.slots))
        ).apply(builder, list -> new ItemGemstoneSlots(list.toArray(new GemstoneSlot[0])));
    });

    public int countFreeSlots() {
        if (slots == null) return 0;

        return (int) Arrays.stream(slots)
            .filter(slot -> !slot.isOccupied())
            .count();
    }

    public void fillSlot(int index, GemstoneSlot newSlot) {
        if (index >= 0 && index < slots.length && !slots[index].isOccupied()) {
            slots[index] = newSlot;
        }
    }
}
