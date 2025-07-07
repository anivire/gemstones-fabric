package name.modid.helpers.modifiers;

import java.util.HashMap;
import java.util.Map;

public interface ModifierData {
  Map<ModifierItemType, GemstoneModifier> MODIFIERS = new HashMap<>();

  Map<ModifierItemType, GemstoneModifier> getModifiers();
}
