package name.modid.helpers;

import name.modid.Gemstones;
import name.modid.helpers.types.GemstoneRarityType;
import name.modid.items.CelestineGemstoneItem;
import name.modid.items.RubyGemstoneItem;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public final class ItemRegistrationHelper {
  private ItemRegistrationHelper() {}

  private static final List<Item> RUBY_GEMSTONES = new ArrayList<>();
  private static final List<Item> CELESTINE_GEMSTONES = new ArrayList<>();

  public static Item register(String path, Function<Item.Settings, Item> factory, Item.Settings settings) {
    final RegistryKey<Item> registryKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(Gemstones.MOD_ID, path));
    return Items.register(registryKey, factory, settings);
  }

  public static void initialize() {
    Gemstones.LOGGER.info("Registering mod items for {}", Gemstones.MOD_ID);
    registerGemstones();
    ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS)
        .register(ItemRegistrationHelper::addGemstonesToItemGroup);
  }

  private static void registerGemstones() {
    List<GemstoneRarityType> rarities = Arrays.asList(GemstoneRarityType.COMMON, GemstoneRarityType.UNCOMMON,
        GemstoneRarityType.RARE, GemstoneRarityType.LEGENDARY);
    for (GemstoneRarityType rarity : rarities) {
      String rarityName = rarity.toString().toLowerCase();

      // RUBY
      Item rubyGemstone = register("ruby_gemstone_" + rarityName, settings -> new RubyGemstoneItem(settings, rarity),
          new Item.Settings().rarity(Rarity.EPIC).component(DataComponentTypes.MAX_STACK_SIZE, 1));
      RUBY_GEMSTONES.add(rubyGemstone);

      // CELESTINE
      Item celestineGemstone = register("celestine_gemstone_" + rarityName,
          settings -> new CelestineGemstoneItem(settings, rarity),
          new Item.Settings().rarity(Rarity.EPIC).component(DataComponentTypes.MAX_STACK_SIZE, 1));
      CELESTINE_GEMSTONES.add(celestineGemstone);
    }
  }

  private static void addGemstonesToItemGroup(FabricItemGroupEntries entries) {
    RUBY_GEMSTONES.forEach(entries::add);
    CELESTINE_GEMSTONES.forEach(entries::add);
  }
}