package name.modid.entities;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.server.world.ServerWorld;

public class BleedingEffect extends StatusEffect {
  public BleedingEffect() {
    super(StatusEffectCategory.HARMFUL, 0xFF0000); // Changed color to red for bleeding effect (0xFF0000)
  }
  
  @Override
  public boolean canApplyUpdateEffect(int duration, int amplifier) {
    // Apply effect every 20 ticks (1 second)
    return duration % 20 == 0;
  }
  
  @Override
  public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
    // Calculate damage: 0.5 hearts (1.0f) + 0.25 hearts per amplifier level
    float damage = 1.0f + (amplifier * 0.5f);
    if (entity.getWorld() instanceof ServerWorld serverWorld) {
      entity.damage(serverWorld.getDamageSources().generic(), damage);
    }
    return true;
  }
}