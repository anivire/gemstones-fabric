package name.modid.helpers.modifiers;

import java.util.HashMap;
import java.util.Map;

public interface GemstonesModifierData {
  Map<GemstoneModifierItemType, GemstoneModifier> MODIFIERS = new HashMap<>();

  Map<GemstoneModifierItemType, GemstoneModifier> getModifiers();
}
