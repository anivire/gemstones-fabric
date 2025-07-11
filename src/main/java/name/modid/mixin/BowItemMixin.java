package name.modid.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BowItem.class)
public class BowItemMixin {
  private static final float PULL_SPEED = 2.0f;

  @Inject(method = "getMaxUseTime", at = @At("RETURN"), cancellable = true)
  private void modifyMaxUseTime(ItemStack stack, LivingEntity user, CallbackInfoReturnable<Integer> cir) {
    int original = cir.getReturnValue();
    cir.setReturnValue((int) (original / PULL_SPEED));
  }
}