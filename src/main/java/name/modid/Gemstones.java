package name.modid;

import name.modid.entities.EffectRegistraionHelper;
import name.modid.helpers.ItemRegistrationHelper;
import name.modid.helpers.attributes.AttributeRegistrationHelper;
import name.modid.helpers.components.ComponentsHelper;
import name.modid.helpers.events.EventRegistrationHelper;
import name.modid.helpers.particles.ParticlesRegistrationHelper;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Gemstones implements ModInitializer {
  public static final String MOD_ID = "gemstones";
  public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

  @Override
  public void onInitialize() {
    LOGGER.info("Initializing Gemstones");
    AttributeRegistrationHelper.initialize();
    ComponentsHelper.initialize();
    EventRegistrationHelper.initialize();
    ItemRegistrationHelper.initialize();
    EffectRegistraionHelper.initialize();
    ParticlesRegistrationHelper.initialize();
  }
}