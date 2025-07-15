package name.modid.items.geodes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import name.modid.helpers.ItemRegistrationHelper;
import name.modid.helpers.types.GemstoneRarityType;
import name.modid.helpers.types.GemstoneType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class GeodeItem extends Item {
  private final ArrayList<GemstoneRarityType> gemstoneRarities;
  private final ArrayList<GemstoneType> includedGemstones;
  private final ArrayList<Double> dropChances = new ArrayList<>(Arrays.asList(0.6, 0.3, 0.1));

  public GeodeItem(Settings settings, ArrayList<GemstoneRarityType> gemstoneRarities,
      ArrayList<GemstoneType> includedGemstones) {
    super(settings);
    this.gemstoneRarities = new ArrayList<>(gemstoneRarities);
    this.includedGemstones = new ArrayList<>(includedGemstones);
  }

  public ItemStack getGemstoneStack() {
    if (includedGemstones.isEmpty() || gemstoneRarities.isEmpty()) {
      return ItemStack.EMPTY;
    }

    Random random = new Random();
    GemstoneType selectedType = includedGemstones.get(random.nextInt(includedGemstones.size()));
    GemstoneRarityType selectedRarity = selectRarity(random);

    Item gemstoneItem = getGemstoneByTypeAndRarity(selectedType, selectedRarity);
    return gemstoneItem != null ? new ItemStack(gemstoneItem) : ItemStack.EMPTY;
  }

  private GemstoneRarityType selectRarity(Random random) {
    ArrayList<Double> adjustedChances = new ArrayList<>();
    for (int i = 0; i < gemstoneRarities.size(); i++) {
      adjustedChances.add(i < dropChances.size() ? dropChances.get(i) : 0.0);
    }

    double totalChance = adjustedChances.stream().mapToDouble(Double::doubleValue).sum();
    if (totalChance <= 0) {
      return gemstoneRarities.get(0);
    }

    adjustedChances.replaceAll(chance -> chance / totalChance);
    double roll = random.nextDouble();
    double cumulativeChance = 0.0;

    for (int i = 0; i < gemstoneRarities.size(); i++) {
      cumulativeChance += adjustedChances.get(i);
      if (roll <= cumulativeChance) {
        return gemstoneRarities.get(i);
      }
    }
    return gemstoneRarities.get(0);
  }

  private Item getGemstoneByTypeAndRarity(GemstoneType type, GemstoneRarityType rarity) {
    List<Item> gems = switch (type) {
      case RUBY -> ItemRegistrationHelper.getRubyGemstones();
      case CELESTINE -> ItemRegistrationHelper.getCelestineGemstones();
      case TOPAZ -> ItemRegistrationHelper.getTopazGemstones();
      case SAPPHIRE -> ItemRegistrationHelper.getSapphireGemstones();
      default -> null;
    };

    int index = rarity.getValue();
    if (gems != null && !gems.isEmpty() && index >= 0 && index < gems.size()) {
      return gems.get(index);
    }
    return null;
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
    world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.BLOCK_AMETHYST_CLUSTER_BREAK,
        SoundCategory.PLAYERS, 0.5F, ((world.random.nextFloat() - world.random.nextFloat()) * 0.7F + 1.0F) * 2.0F);

    user.dropItem(gemstoneStack, false);
    geodeStack.decrement(1);
    return ActionResult.SUCCESS;
  }

  @Override
  public void appendTooltip(ItemStack itemStack, TooltipContext context, List<Text> tooltip, TooltipType type) {
    Text[] rarityTexts = new Text[3];
    for (int i = 0; i < 3; i++) {
      if (i < gemstoneRarities.size()) {
        String rarityName = gemstoneRarities.get(i).toString().toLowerCase();
        String transformedRarityName = Character.toUpperCase(rarityName.charAt(0)) + rarityName.substring(1);

        int color = switch (gemstoneRarities.get(i)) {
          case COMMON -> 0xa8a8a8;
          case UNCOMMON -> 0x5454fc;
          case RARE -> 0xfc54fc;
          case LEGENDARY -> 0xffad00;
          default -> 0xa8a8a8;
        };

        rarityTexts[i] = Text.translatable(transformedRarityName)
            .setStyle(Style.EMPTY.withColor(TextColor.fromRgb(color)));
      }
    }
    tooltip.add(Text.translatable("tooltip.gemstones.geode.info", rarityTexts[0], rarityTexts[1], rarityTexts[2])
        .formatted(Formatting.WHITE));
    tooltip.add(Text.literal(""));
    tooltip.add(Text.translatable("tooltip.gemstones.geode.info_open").formatted(Formatting.GRAY));
  }
}