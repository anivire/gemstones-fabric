package name.modid.mixin.client;

import name.modid.helpers.GemstoneTooltipHelper;
import name.modid.helpers.ItemGemstoneHelper;
import name.modid.helpers.components.Gemstone;
import name.modid.helpers.modifiers.GemstoneModifier;
import name.modid.helpers.modifiers.GemstoneModifierHelper;
import name.modid.helpers.modifiers.ModifierItemType;
import name.modid.helpers.types.GemstoneRarityType;
import name.modid.helpers.types.GemstoneType;
import name.modid.items.gemstones.GemstoneItem;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

  @Inject(method = "getTooltip", at = @At("RETURN"), cancellable = true)
  public void tooltip(Item.TooltipContext context, PlayerEntity player, TooltipType type,
      CallbackInfoReturnable<List<Text>> cir) {
    List<Text> tooltip = cir.getReturnValue();
    ItemStack itemStack = (ItemStack) (Object) this;

    if (ItemGemstoneHelper.isItemValid(itemStack.getItem()) && ItemGemstoneHelper.isGemstonesExists(itemStack)) {
      Gemstone[] gemstones = ItemGemstoneHelper.getGemstones(itemStack);
      if (gemstones == null || gemstones.length == 0)
        return;

      tooltip.add(1, Text.literal(""));

      MutableText slotsText = Text.literal("");
      for (var gemstoneSlot : gemstones) {
        slotsText.append(GemstoneTooltipHelper.getGemstoneSprite(gemstoneSlot.gemstoneType()));
      }

      tooltip.add(2, slotsText);
      tooltip.add(3, Text.literal(""));
      tooltip.add(4, Text.literal(""));
      tooltip.add(5, Text.translatable("tooltip.gemstones.gemstone_slots_info_with").formatted(Formatting.GRAY));

      int buffIndex = 6;
      for (int i = 0; i < gemstones.length; i++) {
        GemstoneType gemType = gemstones[i].gemstoneType();

        if (gemType != GemstoneType.LOCKED && gemType != GemstoneType.EMPTY) {
          GemstoneModifier modifier = GemstoneModifierHelper.getGemstoneModifierForItem(gemType, itemStack.getItem());
          tooltip.add(buffIndex++, Text.translatable(modifier.getSocketedTooltipString())
              .formatted(GemstoneTooltipHelper.getGemstoneColor(gemType)));
        } else {
          tooltip.add(buffIndex++,
              Text.translatable(String.format("tooltip.gemstones.gemstone_slots.%d", i + 1),
                  GemstoneTooltipHelper.getSlotText(gemstones[i].gemstoneType()))
                  .formatted(GemstoneTooltipHelper.getGemstoneColor(gemstones[i].gemstoneType())));
        }
      }

      tooltip.add(buffIndex++, Text.literal(""));

      if (Screen.hasShiftDown()) {
        tooltip.add(buffIndex++,
            Text.translatable("tooltip.gemstones.gemstone_slots_info_rarities").formatted(Formatting.GRAY));
        for (int i = 0; i < gemstones.length; i++) {
          GemstoneType gemType = gemstones[i].gemstoneType();
          GemstoneRarityType gemRarityType = gemstones[i].gemstoneRarityType();
          tooltip.add(buffIndex++,
              Text.literal(String.format("%s %s", gemRarityType.toString(), GemstoneTooltipHelper.getSlotText(gemType)))
                  .formatted(GemstoneTooltipHelper.getGemstoneColor(gemType)));
        }
      } else {
        tooltip.add(buffIndex,
            Text.translatable("tooltip.gemstones.gemstone_slots_info_rarities_fold").formatted(Formatting.GRAY));
      }

    } else if (itemStack.getItem() instanceof GemstoneItem) {

    }

    cir.setReturnValue(tooltip);
  }
}