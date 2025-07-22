package name.modid.entities;

import name.modid.Gemstones;
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

  public static final RegistryEntry<StatusEffect> GUARDIAN_SMITE_EFFECT = Registry.registerReference(
      Registries.STATUS_EFFECT, Identifier.of(Gemstones.MOD_ID, "guardian_smite"), new GuardianSmiteEffect());

  public static final RegistryEntry<StatusEffect> QUICK_SANDS_EFFECT = Registry.registerReference(
      Registries.STATUS_EFFECT, Identifier.of(Gemstones.MOD_ID, "quick_sands"), new QuickSandsEffect());

  public static void initialize() {
    Gemstones.LOGGER.info("Registering mod effects for {}", Gemstones.MOD_ID);
  }
}
