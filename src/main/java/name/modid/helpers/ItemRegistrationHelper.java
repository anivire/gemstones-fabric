package name.modid.helpers;

import name.modid.Gemstones;
import name.modid.items.RubyGemstone;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

import java.util.function.Function;

public final class ItemRegistrationHelper {
    private ItemRegistrationHelper() {}

    public static final Item RUBY_GEMSTONE = register(
        "ruby_gemstone",
        RubyGemstone::new,
        new Item.Settings().rarity(Rarity.EPIC)
    );

    public static Item register(String path, Function<Item.Settings, Item> factory, Item.Settings settings) {
        final RegistryKey<Item> registryKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(Gemstones.MOD_ID, path));
        return Items.register(registryKey, factory, settings);
    }

    public static void initialize() {
        Gemstones.LOGGER.info("Registering mod items for {}", Gemstones.MOD_ID);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.add(RUBY_GEMSTONE);
        });
    }
}
