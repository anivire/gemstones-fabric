package name.modid.helpers.modifiers;

import net.minecraft.item.Item;

import java.util.Optional;

public interface GemstoneModifierProvider {
  Optional<GemstoneModifierData> getModifierForItem(Item item);
}
