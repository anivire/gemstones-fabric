package name.modid.helpers.modifiers.types;

import java.util.ArrayList;

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
  protected String socketedTooltipString;
  protected String gemstoneTooltipString;
  protected GemstoneType gemstoneType;
  protected GemstoneRarityType rarityType;

  public ModifierOnHitEffect(ArrayList<Double> inflitChance, int duration, int amplifier, String socketedTooltipString,
      String gemstoneTooltipString, ModifierItemType itemType, RegistryEntry<StatusEffect> effect) {
    this.inflitChance = inflitChance;
    this.duration = duration;
    this.amplifier = amplifier;
    this.itemType = itemType;
    this.socketedTooltipString = socketedTooltipString;
    this.gemstoneTooltipString = gemstoneTooltipString;
    this.effect = effect;
  }

  public MutableText getGemstoneTooltipString(GemstoneRarityType gemstoneRarityType) {
    Object value = inflitChance.get(gemstoneRarityType.getValue()) * 100;
    String tooltipKey = String.format("tooltip.gemstones.%s_buff", itemType.toString().toLowerCase());

    return Text.translatable(tooltipKey).formatted(Formatting.GRAY)
        .append(Text
            .translatable(this.gemstoneTooltipString,
                Text.literal(String.format("%.0f", value) + "%").formatted(Formatting.BLUE))
            .formatted(Formatting.GOLD));
  }

  public String getSocketedTooltipString() {
    return this.socketedTooltipString;
  }

  public GemstoneType getGemstoneType() {
    return this.gemstoneType;
  }

  public GemstoneRarityType getRarityType() {
    return this.rarityType;
  }

  @Override
  public void apply(ItemStack itemStack, Item item, Integer slotIndex, GemstoneRarityType gemstoneRarityType,
      LivingEntity target, World world) {
    Double procChance = world.getRandom().nextDouble();

    if (procChance < inflitChance.get(gemstoneRarityType.getValue())) {
      target.addStatusEffect(new StatusEffectInstance(effect, duration * 20, amplifier));
    }
  }
}
