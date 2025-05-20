package name.modid.mixin;

import name.modid.helpers.ItemSlotsHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Shadow
    public abstract Item getItem();

    @Inject(method = "<init>*", at = @At("TAIL"))
    private void init(CallbackInfo ci) {
        ItemStack itemStack = (ItemStack)(Object)this;
        Item item = (Item)(Object)this.getItem();

        // Set initial one slot for tools, weapons and armor
        ItemSlotsHelper.setItemSlots(itemStack, item, 1);
    }

    @Inject(method = "getTooltip", at = @At("RETURN"))
    public void tooltip(Item.TooltipContext context, PlayerEntity player, TooltipType type, CallbackInfoReturnable<List<Text>> cir) {
        List<Text> tooltip = (List<Text>) cir.getReturnValue();
        Item item = this.getItem();
        ItemStack itemStack = (ItemStack)(Object)this;

         if (ItemSlotsHelper.isItemValid(item)) {
             int gemstoneSlots = ItemSlotsHelper.getItemSlots(itemStack);
             tooltip.add(Text.translatable("tooltip.gemstones.gemstone_slots", gemstoneSlots).formatted(Formatting.GOLD));
         }
    }
}