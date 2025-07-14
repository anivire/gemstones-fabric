package name.modid.mixin;

import net.minecraft.item.BowItem;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BowItem.class)
public class BowItemMixin {
  private static final float BASE_PULL_TIME = 20.0f;
  private static final float SPEED_INCREASE_PERCENT = 4.00f;

  @Inject(method = "getPullProgress", at = @At("RETURN"), cancellable = true)
  private static void getPullProgress(int useTicks, CallbackInfoReturnable<Float> cir) {
    float adjustedTicks = useTicks * (1.0f + SPEED_INCREASE_PERCENT);
    float progress = adjustedTicks / BASE_PULL_TIME;
    progress = (progress * progress + progress * 2.0f) / 3.0f;
    if (progress > 1.0f) {
      progress = 1.0f;
    }
    cir.setReturnValue(progress);
  }
}