package name.modid.items.geodes;

import java.util.ArrayList;
import java.util.Random;

import name.modid.helpers.ItemRegistrationHelper;
import name.modid.helpers.types.GemstoneRarityType;
import name.modid.helpers.types.GemstoneType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class GeodeItem extends Item {
  protected GemstoneRarityType rarityCap;
  protected ArrayList<GemstoneType> includedGemstones;

  public GeodeItem(Settings settings, GemstoneRarityType rarityCap, ArrayList<GemstoneType> includedGemstones) {
    super(settings);
    this.includedGemstones = new ArrayList<>(includedGemstones);
    this.rarityCap = rarityCap;
  }

  public ItemStack getGemstoneStack() {
    if (includedGemstones.isEmpty() || rarityCap == GemstoneRarityType.NONE) {
      return ItemStack.EMPTY;
    }

    Random random = new Random();
    GemstoneType selectedType = includedGemstones.get(random.nextInt(includedGemstones.size()));
    ArrayList<GemstoneRarityType> validRarities = new ArrayList<>();

    for (GemstoneRarityType rarity : GemstoneRarityType.values()) {
      if (rarity != GemstoneRarityType.NONE && rarity != GemstoneRarityType.UNUSUAL
          && rarity.getValue() <= rarityCap.getValue()) {
        validRarities.add(rarity);
      }
    }

    if (validRarities.isEmpty()) {
      return ItemStack.EMPTY;
    }

    GemstoneRarityType selectedRarity = validRarities.get(random.nextInt(validRarities.size()));

    Item gemstoneItem = null;
    if (selectedType == GemstoneType.RUBY) {
      int index = selectedRarity.getValue();
      if (index >= 0 && index < ItemRegistrationHelper.getRubyGemstones().size()) {
        gemstoneItem = ItemRegistrationHelper.getRubyGemstones().get(index);
      }
    } else if (selectedType == GemstoneType.CELESTINE) {
      int index = selectedRarity.getValue();
      if (index >= 0 && index < ItemRegistrationHelper.getCelestineGemstones().size()) {
        gemstoneItem = ItemRegistrationHelper.getCelestineGemstones().get(index);
      }
    }

    if (gemstoneItem == null) {
      return ItemStack.EMPTY;
    }

    return new ItemStack(gemstoneItem);
  }

  @Override
  public ActionResult use(World world, PlayerEntity user, Hand hand) {
    if (world.isClient) {
      return ActionResult.PASS;
    }

    ItemStack gemstoneStack = getGemstoneStack();
    if (gemstoneStack.isEmpty()) {
      return ActionResult.FAIL;
    }

    ItemStack geodeStack = user.getStackInHand(hand);
    user.getInventory().offerOrDrop(gemstoneStack);
    geodeStack.decrement(1);

    return ActionResult.SUCCESS;
  }
}