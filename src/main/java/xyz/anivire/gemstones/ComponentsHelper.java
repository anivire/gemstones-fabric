package xyz.anivire.gemstones;

import java.util.function.UnaryOperator;

import com.mojang.serialization.Codec;

import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ComponentsHelper {
  public static final ComponentType<Integer> GEMSTONE_SLOTS = register("gemstone_slots", builder -> builder.codec(Codec.INT));

  private static <T>ComponentType<T> register(String name, UnaryOperator<ComponentType.Builder<T>> buidlOperator) {
    return Registry.register(
      Registries.DATA_COMPONENT_TYPE, 
      Identifier.of(Gemstones.MOD_ID, name), 
      buidlOperator.apply(ComponentType.builder()).build()
    );
  }

  protected static void initialize() {
		Gemstones.LOGGER.info("Registering {} components", Gemstones.MOD_ID);
	}
}
