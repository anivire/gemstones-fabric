package name.modid.entities;

import name.modid.helpers.particles.ParticlesRegistrationHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

public class BleedingEffect extends StatusEffect {
  public BleedingEffect() {
    super(StatusEffectCategory.HARMFUL, 0xFF0000);
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
      spawnBleedParticles(entity, amplifier);
    }
    return true;
  }

  private void spawnBleedParticles(LivingEntity entity, int amplifier) {
    World world = entity.getWorld();
    // Adjust Y position to spawn particles around the middle of the mob's hitbox
    double centerY = entity.getY() + entity.getHeight() * 0.5;

    if (world.isClient) {
      // Client-side: spawn particles
      for (int i = 0; i < 5 + amplifier * 2; i++) {
        double offsetX = (world.random.nextDouble() - 0.5) * 0.1;
        double offsetY = (world.random.nextDouble() - 0.5) * 0.1;
        double offsetZ = (world.random.nextDouble() - 0.5) * 0.1;
        double velocityX = (world.random.nextDouble() - 0.5) * 0.15;
        double velocityY = -0.05;
        double velocityZ = (world.random.nextDouble() - 0.5) * 0.15;

        world.addParticle(ParticlesRegistrationHelper.BLEED_PARTICLE, entity.getX() + offsetX, centerY + offsetY,
            entity.getZ() + offsetZ, velocityX, velocityY, velocityZ);
      }
    } else if (world instanceof ServerWorld serverWorld) {
      // Server-side: send particles to clients
      serverWorld.spawnParticles(ParticlesRegistrationHelper.BLEED_PARTICLE, entity.getX(), centerY, entity.getZ(),
          5 + amplifier * 2, 0.1, 0.1, 0.1, 0.15);
    }
  }
}