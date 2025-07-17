package name.modid.mixin;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import name.modid.helpers.attributes.AttributeRegistrationHelper;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
  // Modify crit damage if item has CRIT_DAMAGE_ATTRIBUTE
  @ModifyConstant(method = "attack", constant = @Constant(floatValue = 1.5F))
  private float modifyCriticalDamage(float f, Entity target) {
    PlayerEntity player = (PlayerEntity) (Object) this;
    float bonusCritDamagePercent = 1.5f;
    AttributeModifiersComponent itemAttributeModifiers = player.getMainHandStack()
        .getOrDefault(DataComponentTypes.ATTRIBUTE_MODIFIERS, AttributeModifiersComponent.DEFAULT);

    for (AttributeModifiersComponent.Entry mod : itemAttributeModifiers.modifiers()) {
      if (AttributeRegistrationHelper.CRIT_DAMAGE_ATTRIBUTE.equals(mod.attribute())) {
        bonusCritDamagePercent += (float) mod.modifier().value();
      }
    }

    return bonusCritDamagePercent;
  }
}