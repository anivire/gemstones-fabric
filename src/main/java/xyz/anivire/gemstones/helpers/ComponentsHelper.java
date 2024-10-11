package xyz.anivire.gemstones.helpers;

import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import xyz.anivire.gemstones.Gemstones;
import xyz.anivire.gemstones.helpers.components.GemstoneSlot;
import xyz.anivire.gemstones.helpers.components.ItemGemstoneSlots;

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
