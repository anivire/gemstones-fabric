package name.modid.helpers;

import name.modid.Gemstones;
import name.modid.helpers.components.GemstoneSlot;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ComponentsHelper {
  public static final ComponentType<GemstoneSlot> GEMSTONES = Registry.register(
    Registries.DATA_COMPONENT_TYPE,
    Identifier.of(Gemstones.MOD_ID, "gemstones"),
    ComponentType.<GemstoneSlot>builder().codec(GemstoneSlot.GEMSTONE_SLOTS_CODEC).build()
  );
  
  public static void initialize() {
    Gemstones.LOGGER.info("Registering {} components", Gemstones.MOD_ID);
  }
}
