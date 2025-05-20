package name.modid;

import name.modid.helpers.ComponentsHelper;
import name.modid.helpers.ItemRegistrationHelper;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Gemstones implements ModInitializer {
	public static final String MOD_ID = "gemstones";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing Gemstones");
		ComponentsHelper.initialize();
		ItemRegistrationHelper.initialize();
	}
}