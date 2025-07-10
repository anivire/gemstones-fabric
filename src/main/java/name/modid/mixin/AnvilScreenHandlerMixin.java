package name.modid.mixin;

import name.modid.helpers.ItemGemstoneHelper;
import name.modid.items.gemstones.GemstoneItem;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.Property;
import net.minecraft.text.Text;
import net.minecraft.util.StringHelper;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(AnvilScreenHandler.class)
public abstract class AnvilScreenHandlerMixin {
  @Shadow
  @Final
  @Mutable
  private Property levelCost;

  @Shadow
  @Final
  private static Logger LOGGER;

  @Shadow
  private @Nullable String newItemName;

  @Inject(method = "updateResult", at = @At("RETURN"), cancellable = true)
  private void onUpdateResult(CallbackInfo ci) {
    AnvilScreenHandler handler = (AnvilScreenHandler) (Object) this;
    ItemStack left = handler.getSlot(0).getStack();
    ItemStack right = handler.getSlot(1).getStack();

    if (!left.isEmpty() && !right.isEmpty() && ItemGemstoneHelper.isItemValid(left.getItem())
        && right.getItem() instanceof GemstoneItem) {
      Integer emptySlotIndex = ItemGemstoneHelper.getGemstoneFirstEmptyIndex(left);

      if (emptySlotIndex != null) {
        ItemStack resultStack = left.copy();

        if (this.newItemName != null && !StringHelper.isBlank(this.newItemName)) {
          if (!this.newItemName.equals(left.getName().getString())) {
            resultStack.set(DataComponentTypes.CUSTOM_NAME, Text.literal(this.newItemName));
          }
        } else if (left.contains(DataComponentTypes.CUSTOM_NAME)) {
          resultStack.remove(DataComponentTypes.CUSTOM_NAME);
        }

        GemstoneItem gemstoneItem = (GemstoneItem) right.getItem();
        // Gemstone newGemstone = new Gemstone(gemstoneItem.getType(),
        // gemstoneItem.getRarityType());

        ItemStack modifiedStack = ItemGemstoneHelper.setGemstoneByIndex(resultStack, emptySlotIndex, gemstoneItem);

        if (modifiedStack != null) {
          handler.getSlot(2).setStack(modifiedStack);
          this.levelCost.set(1);
          ci.cancel();
        }
      }
    }
  }

  @Inject(method = "getLevelCost", at = @At("HEAD"), cancellable = true)
  private void onGetLevelCost(CallbackInfoReturnable<Integer> cir) {
    AnvilScreenHandler handler = (AnvilScreenHandler) (Object) this;
    ItemStack left = handler.getSlot(0).getStack();
    ItemStack right = handler.getSlot(1).getStack();

    if (!left.isEmpty() && !right.isEmpty() && ItemGemstoneHelper.isItemValid(left.getItem()) &&
    // TODO: add exp level scaling or set to flat number
        Objects.equals(right.getItem().getClass().getSuperclass().getSimpleName(), "GemstoneItem")) {
      cir.setReturnValue(1);
    }
  }

  @Inject(method = "canTakeOutput", at = @At("HEAD"), cancellable = true)
  protected void canTakeOutputMixin(PlayerEntity player, boolean present, CallbackInfoReturnable<Boolean> info) {
    AnvilScreenHandler handler = (AnvilScreenHandler) (Object) this;
    ItemStack left = handler.getSlot(0).getStack();
    ItemStack right = handler.getSlot(1).getStack();

    if (!left.isEmpty() && !right.isEmpty() && ItemGemstoneHelper.isItemValid(left.getItem())
        && Objects.equals(right.getItem().getClass().getSuperclass().getSimpleName(), "GemstoneItem")) {
      info.setReturnValue(true);
    }
  }
}