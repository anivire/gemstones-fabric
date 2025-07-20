package name.modid.helpers.modifiers.types;

import java.util.ArrayList;
import java.util.Map;

import name.modid.helpers.modifiers.GemstoneModifier;
import name.modid.helpers.modifiers.ModifierItemType;
import name.modid.helpers.types.GemstoneRarityType;
import name.modid.helpers.types.GemstoneType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

public class ModifierOnHitEffect implements GemstoneModifier {
  protected ArrayList<Double> inflitChance = new ArrayList<Double>();
  protected ModifierItemType itemType;
  protected int duration;
  protected int amplifier;
  protected RegistryEntry<StatusEffect> effect;
  protected boolean isStacking;
  protected int maxStackCount;
  protected GemstoneType gemstoneType;
  protected GemstoneRarityType rarityType;

  public ModifierOnHitEffect(ArrayList<Double> inflitChance, int duration, int amplifier, String socketedTooltipString,
      String gemstoneTooltipString, ModifierItemType itemType, RegistryEntry<StatusEffect> effect, boolean isStacking,
      int maxStackCount, GemstoneType gemstoneType) {
    this.inflitChance = inflitChance;
    this.duration = duration;
    this.amplifier = amplifier;
    this.itemType = itemType;
    this.effect = effect;
    this.gemstoneType = gemstoneType;
    this.maxStackCount = maxStackCount;
    this.isStacking = isStacking;
  }

  public MutableText getTooltipString(GemstoneRarityType gemstoneRarityType, Boolean withCategoryString) {
    Object value = inflitChance.get(gemstoneRarityType.getValue()) * 100;
    String tooltipCategoryType = withCategoryString
        ? String.format("tooltip.gemstones.%s_type", itemType.toString().toLowerCase())
        : "tooltip.gemstones.without_type";
    MutableText resultTooltip = Text.empty();

    return resultTooltip.append(Text.translatable(tooltipCategoryType).formatted(Formatting.GRAY))
        .append(Text
            .translatable(
                String.format("tooltip.gemstones.%s.%s_bonus", gemstoneType.toString().toLowerCase(),
                    itemType.toString().toLowerCase()),
                Text.literal(String.format("%.0f", value) + "%").formatted(Formatting.BLUE))
            .formatted(Formatting.GOLD));
  }

  public GemstoneType getGemstoneType() {
    return this.gemstoneType;
  }

  public GemstoneRarityType getRarityType() {
    return this.rarityType;
  }

  public void setRarityType(GemstoneRarityType rarityType) {
    this.rarityType = rarityType;
  }

  @Override
  public void apply(ItemStack itemStack, Item item, Integer slotIndex, GemstoneRarityType gemstoneRarityType,
      LivingEntity target, World world) {
    Double procChance = world.getRandom().nextDouble();
    Map<RegistryEntry<StatusEffect>, StatusEffectInstance> effects = target.getActiveStatusEffects();

    if (procChance < inflitChance.get(gemstoneRarityType.getValue())) {
      StatusEffectInstance existingEffect = effects.get(this.effect);
      int newAmplifier = this.amplifier;

      if (existingEffect != null) {
        newAmplifier = Math.min(existingEffect.getAmplifier() + 1, this.maxStackCount - 1);
      }

      target.addStatusEffect(
          new StatusEffectInstance(this.effect, this.duration * 20, this.isStacking ? newAmplifier : this.amplifier));
    }
  }
}
