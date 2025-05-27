package name.modid.mixin.client;

import name.modid.helpers.ItemGemstoneHelper;
import name.modid.helpers.components.Gemstone;
import name.modid.helpers.modifiers.GemstoneModifier;
import name.modid.helpers.types.GemstoneRarityType;
import name.modid.helpers.types.GemstoneType;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
  @Unique
  private Formatting getGemstoneColor(GemstoneType gemType) {
    return switch (gemType) {
      case LOCKED -> Formatting.DARK_GRAY;
      case RUBY -> Formatting.RED;
      default -> Formatting.GRAY;
    };
  }
  
  @Unique
  private String getSlotText(GemstoneType gemType) {
    return switch (gemType) {
      case LOCKED -> "Locked";
      case EMPTY -> "Empty";
      case RUBY -> "Ruby";
      default -> "unknown";
    };
  }
  
  @Inject(method = "getTooltip", at = @At("RETURN"), cancellable = true)
  public void tooltip(Item.TooltipContext context, PlayerEntity player, TooltipType type, CallbackInfoReturnable<List<Text>> cir) {
    List<Text> tooltip = cir.getReturnValue();
    ItemStack itemStack = (ItemStack) (Object) this;
    
    if (ItemGemstoneHelper.isItemValid(itemStack.getItem())
      && ItemGemstoneHelper.isGemstonesExists(itemStack)) {
      Gemstone[] gemstones = ItemGemstoneHelper.getGemstones(itemStack);
      if (gemstones == null || gemstones.length == 0) return;
      
      tooltip.add(1, Text.literal(""));
      
      MutableText slotsText = Text.literal("");
      for (var gemstoneSlot : gemstones) {
        Formatting color = getGemstoneColor(gemstoneSlot.gemstoneType());
        switch (gemstoneSlot.gemstoneType()) {
          case LOCKED, EMPTY -> slotsText.append(
            Text.literal("\uE001").styled(style ->
              style.withFont(Identifier.of("gemstones", "gemstone"))
            )
          );
          case RUBY -> slotsText.append(
            Text.literal("\uE002").styled(style ->
              style.withFont(Identifier.of("gemstones", "gemstone"))
            )
          );
        }
      }
      
      tooltip.add(2, slotsText);
      tooltip.add(3, Text.literal(""));
      tooltip.add(4, Text.literal(""));
      tooltip.add(5, Text.translatable("tooltip.gemstones.gemstone_slots_info_with").formatted(Formatting.GRAY));
      
      int buffIndex = 6;
      for (int i = 0; i < gemstones.length; i++) {
        GemstoneType gemType = gemstones[i].gemstoneType();
        
        if (gemType != GemstoneType.LOCKED && gemType != GemstoneType.EMPTY) {
          EntityAttributeModifier modifier = GemstoneModifier.getModifier(gemType, itemStack.getItem());
          
          if (modifier != null) {
            String bonusType = "";
            String modifierName = modifier.id().getPath();
            double bonusValue = modifier.value();
            
            if (modifierName.contains("attack_damage")) {
              bonusType = "damage";
            } else if (modifierName.contains("armor")) {
              bonusType = "armor";
            } else if (modifierName.contains("mine_speed")) {
              bonusType = "mine_speed";
            }
            
            String translationKey = String.format("tooltip.gemstones.%s_gemstone.%s_bonus_tooltip",
              gemType.toString().toLowerCase(), bonusType);
            
            String formattedValue;
            if (modifier.operation() == EntityAttributeModifier.Operation.ADD_VALUE) {
              formattedValue = String.format("%.0f", bonusValue);
            } else {
              formattedValue = String.format("%.0f", bonusValue * 100) + "%";
            }
            
            tooltip.add(buffIndex++, Text.translatable(translationKey, formattedValue)
              .formatted(getGemstoneColor(gemType)));
          }
        } else {
          tooltip.add(buffIndex++, Text.translatable(
              String.format("tooltip.gemstones.gemstone_slots_%d", i + 1),
              getSlotText(gemstones[i].gemstoneType()))
            .formatted(getGemstoneColor(gemstones[i].gemstoneType())));
        }
      }
      
      tooltip.add(buffIndex++, Text.literal(""));
      
      if (Screen.hasShiftDown()) {
        tooltip.add(buffIndex++, Text.translatable("tooltip.gemstones.gemstone_slots_info_rarities").formatted(Formatting.GRAY));
        for (int i = 0; i < gemstones.length; i++) {
          GemstoneType gemType = gemstones[i].gemstoneType();
          GemstoneRarityType gemRarityType = gemstones[i].gemstoneRarityType();
          tooltip.add(buffIndex++, Text.literal(String.format("  %s %s", gemRarityType.toString(), getSlotText(gemType)))
            .formatted(getGemstoneColor(gemType)));
        }
      } else {
        tooltip.add(buffIndex, Text.translatable("tooltip.gemstones.gemstone_slots_info_rarities_fold").formatted(Formatting.GRAY));
      }
    }
    
    cir.setReturnValue(tooltip);
  }
}