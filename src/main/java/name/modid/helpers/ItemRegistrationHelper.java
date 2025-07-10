package name.modid.helpers;

import name.modid.Gemstones;
import name.modid.helpers.types.GemstoneRarityType;
import name.modid.helpers.types.GemstoneType;
import name.modid.items.gemstones.CelestineGemstoneItem;
import name.modid.items.gemstones.RubyGemstoneItem;
import name.modid.items.geodes.GeodeDeepslateItem;
import name.modid.items.geodes.GeodeStoneItem;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public final class ItemRegistrationHelper {
  private ItemRegistrationHelper() {}

  public static final Item GEODE_STONE = register("geode_stone",
      settings -> new GeodeStoneItem(settings, GemstoneRarityType.RARE,
          new ArrayList<>(Arrays.asList(GemstoneType.RUBY, GemstoneType.CELESTINE))),
      new Item.Settings().rarity(Rarity.EPIC).component(DataComponentTypes.MAX_STACK_SIZE, 64));

  public static final Item GEODE_DEEPSLATE = register("geode_deepslate",
      settings -> new GeodeDeepslateItem(settings, GemstoneRarityType.LEGENDARY,
          new ArrayList<>(Arrays.asList(GemstoneType.RUBY, GemstoneType.CELESTINE))),
      new Item.Settings().rarity(Rarity.EPIC).component(DataComponentTypes.MAX_STACK_SIZE, 64));

  private static final List<Item> RUBY_GEMSTONES = new ArrayList<>();
  private static final List<Item> CELESTINE_GEMSTONES = new ArrayList<>();

  public static Item register(String path, Function<Item.Settings, Item> factory, Item.Settings settings) {
    final RegistryKey<Item> registryKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(Gemstones.MOD_ID, path));
    return Registry.register(Registries.ITEM, registryKey, factory.apply(settings.registryKey(registryKey)));
  }

  public static final RegistryKey<ItemGroup> GEMSTONES_ITEM_GROUP_KEY = RegistryKey.of(RegistryKeys.ITEM_GROUP,
      Identifier.of(Gemstones.MOD_ID, "item_group"));

  public static ItemGroup GEMSTONES_ITEM_GROUP;

  public static void initialize() {
    Gemstones.LOGGER.info("Registering mod items for {}", Gemstones.MOD_ID);

    registerGemstones();

    GEMSTONES_ITEM_GROUP = FabricItemGroup.builder().icon(() -> new ItemStack(RUBY_GEMSTONES.get(0)))
        .displayName(Text.translatable("item_group.gemstones")).build();
    Registry.register(Registries.ITEM_GROUP, GEMSTONES_ITEM_GROUP_KEY, GEMSTONES_ITEM_GROUP);

    ItemGroupEvents.modifyEntriesEvent(GEMSTONES_ITEM_GROUP_KEY)
        .register(ItemRegistrationHelper::addGemstonesToItemGroup);

    ItemGroupEvents.modifyEntriesEvent(GEMSTONES_ITEM_GROUP_KEY).register(entries -> {
      entries.add(GEODE_STONE);
      entries.add(GEODE_DEEPSLATE);
    });
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

  public static List<Item> getRubyGemstones() {
    return RUBY_GEMSTONES;
  }

  public static List<Item> getCelestineGemstones() {
    return CELESTINE_GEMSTONES;
  }
}