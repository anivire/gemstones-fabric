package name.modid.mixin;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HeldItemRenderer.class)
public class HeldItemRendererMixin {

  @Inject(method = "renderFirstPersonItem", at = @At("HEAD"), cancellable = false)
  private void syncBowAnimation(AbstractClientPlayerEntity player, float tickDelta, float pitch, Hand hand,
      float swingProgress, ItemStack itemStack, float equipProgress, MatrixStack matrices,
      VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {

    if (itemStack.getItem() instanceof BowItem && player.isUsingItem()) {
      int useTicks = player.getItemUseTimeLeft();
      float adjustedTicks = useTicks * (1.0f + 2.00f);
      float progress = adjustedTicks / 20.0f;
      progress = (progress * progress + progress * 2.0f) / 3.0f;
      progress = Math.min(progress, 1.0f);
    }
  }
}
