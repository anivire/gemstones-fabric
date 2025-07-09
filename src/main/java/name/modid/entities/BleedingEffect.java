package name.modid.entities;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.server.world.ServerWorld;

public class BleedingEffect extends StatusEffect {
  public BleedingEffect() {
    super(StatusEffectCategory.HARMFUL, 0x00000000);
  }

  @Override
  public boolean canApplyUpdateEffect(int duration, int amplifier) {
    // Every 20 tiks = 1 second
    return duration % 20 == 0;
  }

  @Override
  public boolean applyUpdateEffect(ServerWorld world, LivingEntity entity, int amplifier) {
    // Calculate damage: 0.5 hearts (1.0f damage) + 0.25 hearts per amplifier level
    float damage = 1.0f + (amplifier * 0.5f);
    entity.damage(world, entity.getWorld().getDamageSources().generic(), damage);
    return super.applyUpdateEffect(world, entity, amplifier);
  }
}