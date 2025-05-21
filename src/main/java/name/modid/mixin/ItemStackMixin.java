package name.modid.mixin;

import name.modid.Gemstones;
import name.modid.helpers.ItemSlotsHelper;
import name.modid.helpers.components.GemstoneSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Objects;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Shadow
    public abstract Item getItem();

    @Inject(method = "<init>*", at = @At("TAIL"))
    private void init(CallbackInfo ci) {
        ItemStack itemStack = (ItemStack) (Object) this;
        Item item = (Item) (Object) this.getItem();

        // Set initial one slot for tools, weapons and armor
        ItemSlotsHelper.setItemSlots(itemStack, item);
    }

    @Unique
    private Formatting getSlotColor(String gemstoneType) {
        if (Objects.equals(gemstoneType, "locked")) return Formatting.GRAY;
        else if (Objects.equals(gemstoneType, "empty")) return Formatting.WHITE;
        else return Formatting.WHITE;
    }

    @Unique
    private String getSlotText(String gemstoneType) {
        if (Objects.equals(gemstoneType, "locked")) return "Slot is locked";
        else if (Objects.equals(gemstoneType, "empty")) return "Slot is free";
        else return "...";
    }

    @Inject(method = "getTooltip", at = @At("RETURN"), cancellable = true)
    public void tooltip(Item.TooltipContext context, PlayerEntity player, TooltipType type, CallbackInfoReturnable<List<Text>> cir) {
        List<Text> tooltip = cir.getReturnValue();
        Item item = ((ItemStack) (Object) this).getItem();
        ItemStack itemStack = (ItemStack) (Object) this;

        if (ItemSlotsHelper.isItemValid(item)) {
            GemstoneSlot[] gemstoneSlots = ItemSlotsHelper.getGemstoneSlots(itemStack);

            if (gemstoneSlots != null) {
                MutableText slotsText = Text.literal("");

                for (GemstoneSlot gemstoneSlot : gemstoneSlots) {
                    slotsText.append(Text.literal("\uE001")
                            .styled(style -> style.withFont(Identifier.of(Gemstones.MOD_ID, "gemstone")))
                    );
                }

                // Empty slots for spacing
                tooltip.add(1, Text.literal(""));
                tooltip.add(3, Text.literal(""));
                tooltip.add(4, Text.literal(""));

                tooltip.add(2, slotsText);

                tooltip.add(5, Text.translatable("tooltip.gemstones.gemstone_slots_info_with").formatted(Formatting.GRAY));

                for (int i = 0; i < gemstoneSlots.length; i++) {
                    tooltip.add(6 + i, Text.translatable(String.format("tooltip.gemstones.gemstone_slots_%d", i + 1),
                                    getSlotText(gemstoneSlots[i].gemstoneType()))
                            .formatted(getSlotColor(gemstoneSlots[i].gemstoneType()))
                    );
                }

            }
        }

        // Update the return value
        cir.setReturnValue(tooltip);
    }
}