package xyz.anivire.gemstones.helpers;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import xyz.anivire.gemstones.Gemstones;
import xyz.anivire.gemstones.item.gemstone.MalachiteGemstone;
import xyz.anivire.gemstones.item.gemstone.RubyGemstone;
import xyz.anivire.gemstones.item.gemstone.TopazGemstone;

public class ItemRegistrationHelper {
  public static final Item RUBY_GEMSTONE = registerItem("ruby_gemstone", new RubyGemstone(new Item.Settings().rarity(Rarity.EPIC)));
  public static final Item TOPAZ_GEMSTONE = registerItem("topaz_gemstone", new TopazGemstone(new Item.Settings().rarity(Rarity.EPIC)));
  public static final Item MALACHITE_GEMSTONE = registerItem("malachite_gemstone", new MalachiteGemstone(new Item.Settings().rarity(Rarity.EPIC)));

  private static Item registerItem(String nameId, Item item) {
    return Registry.register(Registries.ITEM, Identifier.of(Gemstones.MOD_ID, nameId), item);
  }

  public static void registerModItems() {
    Gemstones.LOGGER.info("Registering mod items for {}", Gemstones.MOD_ID);
    ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
      entries.add(RUBY_GEMSTONE);
      entries.add(TOPAZ_GEMSTONE);
      entries.add(MALACHITE_GEMSTONE);
    });
  }
}
