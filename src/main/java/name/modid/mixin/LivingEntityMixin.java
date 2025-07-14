package name.modid.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
  @Inject(method = "getItemUseTimeLeft", at = @At("RETURN"), cancellable = true)
  private void onGetItemUseTimeLeft(CallbackInfoReturnable<Integer> cir) {
    LivingEntity self = (LivingEntity) (Object) this;
    ItemStack activeItem = self.getActiveItem();

    if (activeItem.getItem() == Items.BOW) {
      int original = cir.getReturnValueI();
      cir.setReturnValue(original);
    }
  }
}