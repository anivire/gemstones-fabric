package name.modid.mixin.client;

import name.modid.helpers.ItemGemstoneHelper;
import name.modid.helpers.components.Gemstone;
import name.modid.helpers.modifiers.GemstoneModifier;
import name.modid.helpers.types.GemstoneRarityType;
import name.modid.helpers.types.GemstoneType;
import name.modid.items.GemstoneItem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.MutableText;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
  @Unique
  private Formatting getGemstoneColor(GemstoneType gemType) {
    return switch (gemType) {
      case EMPTY -> Formatting.DARK_GRAY;
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
  
  @Unique
  private Text getGemstoneSprite(GemstoneType gemType) {
    return switch (gemType) {
      case EMPTY -> Text.literal("\uE001").styled(style ->
        style.withFont(Identifier.of("gemstones", "gemstone_sprite_font"))
      );
      case RUBY -> Text.literal("\uE002").styled(style ->
        style.withFont(Identifier.of("gemstones", "gemstone_sprite_font"))
      );
      case CELESTINE -> Text.literal("\uE003").styled(style ->
        style.withFont(Identifier.of("gemstones", "gemstone_sprite_font"))
      );
      default -> Text.literal("");
    };
  }
  
  @Unique
  private Text getGemstoneRaritySprite(GemstoneRarityType rarityType) {
    return switch (rarityType) {
      case COMMON -> Text.literal("\uE001").styled(style ->
        style.withFont(Identifier.of("gemstones", "rarity_sprite_font"))
      );
      case UNCOMMON -> Text.literal("\uE002").styled(style ->
        style.withFont(Identifier.of("gemstones", "rarity_sprite_font"))
      );
      case RARE -> Text.literal("\uE003").styled(style ->
        style.withFont(Identifier.of("gemstones", "rarity_sprite_font"))
      );
      case LEGENDARY -> Text.literal("\uE004").styled(style ->
        style.withFont(Identifier.of("gemstones", "rarity_sprite_font"))
      );
      case UNUSUAL -> Text.literal("\uE005").styled(style ->
        style.withFont(Identifier.of("gemstones", "rarity_sprite_font"))
      );
      default -> Text.literal("");
    };
  }
  
  @Unique
  private String getGemstoneModifier(String gemstoneModifierText) {
    if (gemstoneModifierText.contains("attack_damage")) {
      return "damage";
    } else if (gemstoneModifierText.contains("armor")) {
      return "armor";
    } else if (gemstoneModifierText.contains("mine_speed")) {
      return "mine_speed";
    } else {
      return "unknown_modifier";
    }
  }
  
  @Unique
  private List<Text> getGemstoneItemBonuses(GemstoneType gemType) {
    List<Text> bonuses = new ArrayList<>();
    String gemKey = gemType.name().toLowerCase();
    
    bonuses.addAll(createWrappedTooltip(
      "tooltip.gemstones.melee_buff",
      "tooltip.gemstones." + gemKey + "_item.melee_buff",
      Formatting.GRAY,
      Formatting.DARK_GREEN
    ));
    bonuses.addAll(createWrappedTooltip(
      "tooltip.gemstones.ranged_buff",
      "tooltip.gemstones." + gemKey + "_item.ranged_buff",
      Formatting.GRAY,
      Formatting.DARK_GREEN
    ));
    bonuses.addAll(createWrappedTooltip(
      "tooltip.gemstones.tools_buff",
      "tooltip.gemstones." + gemKey + "_item.tools_buff",
      Formatting.GRAY,
      Formatting.DARK_GREEN
    ));
    bonuses.addAll(createWrappedTooltip(
      "tooltip.gemstones.armor_buff",
      "tooltip.gemstones." + gemKey + "_item.armor_buff",
      Formatting.GRAY,
      Formatting.DARK_GREEN
    ));
    
    return bonuses;
  }
  
  @Unique
  private List<Text> createWrappedTooltip(
    String baseKey,
    String valueKey,
    Formatting baseColor,
    Formatting valueColor) {
    List<Text> lines = new ArrayList<>();
    MutableText baseText = Text.translatable(baseKey).formatted(baseColor);
    MutableText valueText = Text.translatable(valueKey).formatted(valueColor);
    TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
    int maxWidth = 150;
    
    List<Text> baseLines = wrapText(baseText, textRenderer, maxWidth);
    List<Text> valueLines = wrapText(valueText, textRenderer, maxWidth);
    
    if (!baseLines.isEmpty() && !valueLines.isEmpty()) {
      Text lastBase = baseLines.remove(baseLines.size() - 1);
      Text firstValue = valueLines.remove(0);
      baseLines.add(lastBase.copy().append(firstValue));
    }
    
    lines.addAll(baseLines);
    lines.addAll(valueLines);
    
    return lines;
  }
  
  @Unique
  private List<Text> wrapText(Text text, TextRenderer renderer, int maxWidth) {
    List<Text> lines = new ArrayList<>();
    StringVisitable visitable = StringVisitable.styled(text.getString(), text.getStyle());
    List<StringVisitable> wrapped = renderer.getTextHandler()
      .wrapLines(visitable, maxWidth, Style.EMPTY);
    for (StringVisitable line : wrapped) {
      lines.add(Text.literal(line.getString()).setStyle(text.getStyle()));
    }
    return lines;
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
        slotsText.append(getGemstoneSprite(gemstoneSlot.gemstoneType()));
      }
      
      tooltip.add(2, slotsText);
      tooltip.add(3, Text.literal(""));
      tooltip.add(4, Text.literal(""));
      tooltip.add(5, Text.translatable("tooltip.gemstones.gemstone_slots_info_with").formatted(Formatting.GRAY));
      
      int buffIndex = 6;
      for (int i = 0; i < gemstones.length; i++) {
        GemstoneType gemType = gemstones[i].gemstoneType();
        
        if (gemType != GemstoneType.LOCKED
          && gemType != GemstoneType.EMPTY) {
          EntityAttributeModifier modifier = GemstoneModifier.getModifier(gemType, itemStack.getItem());
          
          if (modifier != null) {
            String formattedValue;
            String translationKey = String.format("tooltip.gemstones.%s_gemstone.%s_bonus_tooltip",
              gemType.toString().toLowerCase(), getGemstoneModifier(modifier.id().getPath()));
            
            if (modifier.operation() == EntityAttributeModifier.Operation.ADD_VALUE) {
              formattedValue = String.format("%.0f", modifier.value());
            } else {
              formattedValue = String.format("%.0f", modifier.value() * 100) + "%";
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
          tooltip.add(buffIndex++, Text.literal(String.format("%s %s", gemRarityType.toString(), getSlotText(gemType)))
            .formatted(getGemstoneColor(gemType)));
        }
      } else {
        tooltip.add(buffIndex, Text.translatable("tooltip.gemstones.gemstone_slots_info_rarities_fold").formatted(Formatting.GRAY));
      }
    } else if (itemStack.getItem() instanceof GemstoneItem) {
      GemstoneItem i = (GemstoneItem) itemStack.getItem();
      List<Text> b = getGemstoneItemBonuses(i.getType());
      tooltip.addLast(getGemstoneRaritySprite(i.getRarityType()));
      tooltip.addLast(Text.literal(""));
      tooltip.addAll(b);
    }
    
    cir.setReturnValue(tooltip);
  }
}