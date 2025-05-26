package name.modid.mixin;

import name.modid.helpers.ItemGemstoneHelper;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
  @Inject(method = "<init>*", at = @At("TAIL"))
  private void init(CallbackInfo ci) {
    ItemStack itemStack = (ItemStack) (Object) this;
    
    ItemGemstoneHelper.initItemSlots(itemStack, itemStack.getItem());
  }
}