package xyz.anivire.gemstones.mixin;

import net.minecraft.component.Component;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import xyz.anivire.gemstones.ComponentsHelper;

import java.util.List;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
	@Shadow public abstract Item getItem();

	protected ItemStackMixin() {
		super();
	}

	@SuppressWarnings("rawtypes")
	@Inject(method = "getComponents", at = @At("HEAD"))
	public void getComponents(CallbackInfoReturnable<List<Component>> cir) {
		Item item = this.getItem();
		ItemStack itemStack = (ItemStack)(Object)this;
		if (item instanceof ToolItem || item instanceof BowItem || item instanceof ArmorItem || item instanceof SwordItem) {
			itemStack.set(ComponentsHelper.GEMSTONE_SLOTS, 1);
		}
	}

	@Inject(method = "getTooltip", at = @At("RETURN"))
	public void tooltip(Item.TooltipContext context, @Nullable PlayerEntity player, TooltipType type, CallbackInfoReturnable<List<Text>> cir) {
		List<Text> tooltip = (List<Text>) cir.getReturnValue();
		Item item = this.getItem();
		ItemStack itemStack = (ItemStack)(Object)this;
		if (item instanceof ToolItem || item instanceof BowItem || item instanceof ArmorItem || item instanceof SwordItem) {
			Integer gemstoneSlots = itemStack.get(ComponentsHelper.GEMSTONE_SLOTS);
			tooltip.add(Text.translatable("tooltip.gemstones.gemstone_slots", gemstoneSlots == null ? 0 : gemstoneSlots).formatted(Formatting.GOLD));
		}
	}
}