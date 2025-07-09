package name.modid.helpers;

import name.modid.Gemstones;
import name.modid.entities.BleedingEffect;
import name.modid.entities.ExperienceThirstEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public class EffectRegistraionHelper {
  public static final RegistryEntry<StatusEffect> EXP_THIRST_EFFECT = Registry.registerReference(
      Registries.STATUS_EFFECT, Identifier.of(Gemstones.MOD_ID, "exp_thirst"), new ExperienceThirstEffect());

  public static final RegistryEntry<StatusEffect> BLEEDING_EFFECT = Registry.registerReference(Registries.STATUS_EFFECT,
      Identifier.of(Gemstones.MOD_ID, "bleeding"), new BleedingEffect());

  public static void initialize() {
    Gemstones.LOGGER.info("Registering mod effects for {}", Gemstones.MOD_ID);
  }
}
