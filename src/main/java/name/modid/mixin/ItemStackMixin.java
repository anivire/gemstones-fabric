package name.modid.mixin;

import name.modid.Gemstones;
import name.modid.helpers.ItemGemstoneSlotsHelper;
import name.modid.helpers.components.GemstoneSlot;
import name.modid.helpers.modifiers.GemstoneModifier;
import name.modid.helpers.types.GemstoneType;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
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
  
  @Shadow
  @Final
  private static Logger LOGGER;
  
  @Inject(method = "<init>*", at = @At("TAIL"))
  private void init(CallbackInfo ci) {
    ItemStack itemStack = (ItemStack) (Object) this;
    
    ItemGemstoneSlotsHelper.initItemSlots(itemStack, itemStack.getItem());
  }
  
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
    if (gemType == GemstoneType.LOCKED) return "Locked";
    if (gemType == GemstoneType.EMPTY) return "Empty";
    return gemType.toString();
  }
  
  @Inject(method = "getTooltip", at = @At("RETURN"), cancellable = true)
  public void tooltip(Item.TooltipContext context, PlayerEntity player, TooltipType type, CallbackInfoReturnable<List<Text>> cir) {
    List<Text> tooltip = cir.getReturnValue();
    Item item = ((ItemStack) (Object) this).getItem();
    ItemStack itemStack = (ItemStack) (Object) this;
    
    
    if (ItemGemstoneSlotsHelper.isItemValid(item)) {
      GemstoneSlot[] gemstoneSlots = ItemGemstoneSlotsHelper.getGemstoneSlots(itemStack);
      
      if (gemstoneSlots != null) {
//        AttributeModifiersComponent modifiers = itemStack.get(DataComponentTypes.ATTRIBUTE_MODIFIERS);
        
        MutableText slotsText = Text.literal("");
        
        for (var gemstoneSlot : gemstoneSlots) {
          if (Objects.equals(gemstoneSlot.gemstoneType(), GemstoneType.LOCKED) || Objects.equals(gemstoneSlot.gemstoneType(), GemstoneType.EMPTY)) {
            slotsText.append(Text.literal("\uE001").styled(style -> style.withFont(Identifier.of(Gemstones.MOD_ID, "gemstone"))));
          } else if (Objects.equals(gemstoneSlot.gemstoneType(), GemstoneType.RUBY)) {
            slotsText.append(Text.literal("\uE002").styled(style -> style.withFont(Identifier.of(Gemstones.MOD_ID, "gemstone"))));
          }
        }
        
        tooltip.add(1, Text.literal(""));
        tooltip.add(3, Text.literal(""));
        tooltip.add(4, Text.literal(""));
        
        tooltip.add(2, slotsText);
        
        tooltip.add(5, Text.translatable("tooltip.gemstones.gemstone_slots_info_with").formatted(Formatting.GRAY));
        
        for (int i = 0; i < gemstoneSlots.length; i++) {
          GemstoneType gemType = gemstoneSlots[i].gemstoneType();
          
          if (gemType != GemstoneType.LOCKED && gemType != GemstoneType.EMPTY) {
            EntityAttributeModifier modifier = GemstoneModifier.getModifier(gemType, item);
            
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
              
              String translationKey = String.format("tooltip.gemstones.%s_gemstone.%s_bonus_tooltip", gemType.toString().toLowerCase(), bonusType);
              
              String formattedValue;
              if (modifier.operation() == EntityAttributeModifier.Operation.ADD_VALUE) {
                formattedValue = String.format("%.0f", bonusValue);
              } else {
                formattedValue = String.format("%.0f", bonusValue * 100) + "%";
              }
              
              tooltip.add(6 + i, Text.translatable(translationKey, formattedValue).formatted(getGemstoneColor(gemType)));
            }
          } else {
            tooltip.add(6 + i, Text.translatable(String.format("tooltip.gemstones.gemstone_slots_%d", i + 1), getSlotText(gemstoneSlots[i].gemstoneType())).formatted(getGemstoneColor(gemstoneSlots[i].gemstoneType())));
          }
        }
      }
    }
    
    // Update the return value
    cir.setReturnValue(tooltip);
  }
}