package name.modid.helpers.modifiers;

import name.modid.helpers.modifiers.providers.RubyModifierProvider;
import name.modid.helpers.types.GemstoneType;
import net.minecraft.item.Item;

import java.util.EnumMap;
import java.util.Optional;

public class GemstoneModifierRegistry {
  private static final EnumMap<GemstoneType, GemstoneModifierProvider> PROVIDERS = new EnumMap<>(GemstoneType.class);
  
  static {
    PROVIDERS.put(GemstoneType.RUBY, new RubyModifierProvider());
  }
  
  public static Optional<GemstoneModifierData> getModifier(GemstoneType gemType, Item item) {
    GemstoneModifierProvider provider = PROVIDERS.get(gemType);
    if (provider != null) {
      return provider.getModifierForItem(item);
    }
    return Optional.empty();
  }
}
