package xyz.anivire.gemstones;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Gemstones implements ModInitializer {
	public static final String MOD_ID = "gemstones";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ComponentsHelper.initialize();
		ItemRegistrationHelper.registerModItems();
	}
}