package xyz.anivire.gemstones.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
	@Shadow public abstract Item getItem();
	@Unique private int gemstoneSlots = 1;

	protected ItemStackMixin() {
			super();
	}

	@Unique
	public void setGemstoneSlots(int slot) {
			this.gemstoneSlots = slot;
	}

	@Unique
	public int getGemstoneSlot() {
			return this.gemstoneSlots;
	}

	@Inject(method = "encode(Lnet/minecraft/registry/RegistryWrapper$WrapperLookup;Lnet/minecraft/nbt/NbtElement;)Lnet/minecraft/nbt/NbtElement;", at = @At("RETURN"))
	public void encode(RegistryWrapper.WrapperLookup registries, NbtElement prefix, CallbackInfoReturnable<NbtElement> cir) {
			NbtCompound nbt = (NbtCompound) cir.getReturnValue();
			Item item = this.getItem();
			if (item instanceof ToolItem || item instanceof BowItem || item instanceof ArmorItem || item instanceof SwordItem) {
					nbt.putInt("gemstone_slots", this.gemstoneSlots);
			}
	}

	@Inject(method = "getTooltip", at = @At("RETURN"))
	public void tooltip(Item.TooltipContext context, @Nullable PlayerEntity player, TooltipType type, CallbackInfoReturnable<List<Text>> cir) {
			List<Text> tooltip = (List<Text>) cir.getReturnValue();
			Item item = this.getItem();
			if (item instanceof ToolItem || item instanceof BowItem || item instanceof ArmorItem || item instanceof SwordItem) {
					tooltip.add(Text.translatable("tooltip.gemstones.gemstone_slots", this.gemstoneSlots).formatted(Formatting.GOLD));
			}
	}
}