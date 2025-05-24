package name.modid.helpers;

import name.modid.Gemstones;
import name.modid.helpers.components.GemstoneSlot;
import name.modid.helpers.components.ItemGemstoneSlots;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ComponentsHelper {
  public static final ComponentType<GemstoneSlot> GEMSTONE_SLOT = Registry.register(
    Registries.DATA_COMPONENT_TYPE,
    Identifier.of(Gemstones.MOD_ID, "slot"),
    ComponentType.<GemstoneSlot>builder().codec(GemstoneSlot.GEMSTONE_CODEC).build()
  );
  
  public static final ComponentType<ItemGemstoneSlots> GEMSTONE_SLOTS = Registry.register(
    Registries.DATA_COMPONENT_TYPE,
    Identifier.of(Gemstones.MOD_ID, "gemstone_slots"),
    ComponentType.<ItemGemstoneSlots>builder().codec(ItemGemstoneSlots.GEMSTONE_SLOTS_CODEC).build()
  );
  
  public static void initialize() {
    Gemstones.LOGGER.info("Registering {} components", Gemstones.MOD_ID);
  }
}
