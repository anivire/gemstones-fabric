package name.modid.helpers.modifiers.types;

import name.modid.helpers.modifiers.GemstoneModifier;
import name.modid.helpers.types.GemstoneRarityType;
import name.modid.helpers.types.GemstoneType;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ModifierOnHitEffect implements GemstoneModifier {
  protected Double inflitChance;
  protected Integer duration;
  protected Integer amplifier;
  protected StatusEffect effect;
  protected String tooltipString;
  protected GemstoneType gemstoneType;
  protected GemstoneRarityType rarityType;

  public ModifierOnHitEffect(Double inflitChance, Integer duration, Integer amplifier, StatusEffect effect) {
    this.inflitChance = inflitChance;
    this.duration = duration;
    this.amplifier = amplifier;
    this.effect = effect;
  }

  public String getTooltipString() { return this.tooltipString; }

  public GemstoneType getGemstoneType() { return this.gemstoneType; }

  public GemstoneRarityType getRarityType() { return this.rarityType; }

  @Override
  public void applyBonus(ItemStack itemStack, Item item, Integer slotIndex, GemstoneRarityType gemstoneRarityType) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'applyBonus'");
  }
}
