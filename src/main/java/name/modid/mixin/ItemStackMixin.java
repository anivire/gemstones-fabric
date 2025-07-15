package name.modid.mixin;

import name.modid.helpers.ItemGemstoneHelper;
import net.minecraft.component.ComponentChanges;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

  @Inject(method = "<init>(Lnet/minecraft/registry/entry/RegistryEntry;ILnet/minecraft/component/ComponentChanges;)V", at = @At("TAIL"))
  private void onConstruct(RegistryEntry item, int count, ComponentChanges changes, CallbackInfo ci) {
    ItemStack itemStack = (ItemStack) (Object) this;
    ItemGemstoneHelper.initItemSlots(itemStack, (Item) item);
  }
}
