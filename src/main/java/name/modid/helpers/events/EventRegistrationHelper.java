package name.modid.helpers.events;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import name.modid.helpers.ItemGemstoneHelper;
import name.modid.helpers.attributes.AttributeRegistrationHelper;
import name.modid.helpers.components.Gemstone;
import name.modid.helpers.modifiers.GemstoneModifier;
import name.modid.helpers.modifiers.GemstoneModifierHelper;
import name.modid.helpers.modifiers.types.ModifierOnHitEffect;
import name.modid.helpers.types.GemstoneRarityType;
import name.modid.helpers.types.GemstoneType;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.component.type.AttributeModifiersComponent.Entry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.ActionResult;

public class EventRegistrationHelper {
  public static void initialize() {
    // Register onHit modifiers
    AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
      if (!world.isClient && entity instanceof LivingEntity target) {
        Item item = player.getStackInHand(hand).getItem();
        ItemStack itemStack = player.getStackInHand(hand);

        if (!ItemGemstoneHelper.isGemstonesExists(itemStack)) {
          return ActionResult.PASS;
        }

        Gemstone[] gemstones = ItemGemstoneHelper.getGemstones(itemStack);
        Map<Integer, Map<GemstoneType, GemstoneRarityType>> itemGemstones = new HashMap<>();

        for (int i = 0; i < gemstones.length; i++) {
          Gemstone gem = gemstones[i];

          if (gem.gemstoneType() != null && gem.gemstoneType() != GemstoneType.LOCKED) {
            Map<GemstoneType, GemstoneRarityType> m = new HashMap<>();
            m.put(gem.gemstoneType(), gem.gemstoneRarityType());
            itemGemstones.put(i, m);
          }
        }

        for (Map.Entry<Integer, Map<GemstoneType, GemstoneRarityType>> m : itemGemstones.entrySet()) {
          Integer slotIndex = m.getKey();
          Map<GemstoneType, GemstoneRarityType> i = m.getValue();

          for (Map.Entry<GemstoneType, GemstoneRarityType> e : i.entrySet()) {
            GemstoneType gemstoneType = e.getKey();
            GemstoneRarityType gemstoneRarity = e.getValue();
            GemstoneModifier gemstoneModifier = GemstoneModifierHelper.getGemstoneModifierForItem(gemstoneType, item);

            if (gemstoneModifier != null) {
              if (gemstoneModifier.getClass() == ModifierOnHitEffect.class) {
                gemstoneModifier.apply(itemStack, item, slotIndex, gemstoneRarity, target, world);
              }
            }
          }
        }
      }

      return ActionResult.PASS;
    });

    // CRIT_DAMAGE attribute
    AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
      if (!world.isClient && entity instanceof LivingEntity target) {
        boolean isCritical = !player.isOnGround() && player.fallDistance > 0.0f && !player.isClimbing()
            && !player.isTouchingWater() && !player.hasVehicle();

        if (isCritical) {
          AttributeModifiersComponent itemAttributeModifiers = player.getMainHandStack().getOrDefault(
              DataComponentTypes.ATTRIBUTE_MODIFIERS,
              AttributeModifiersComponent.DEFAULT);

          float bonusCritDamagePercent = 0.0f;
          for (AttributeModifiersComponent.Entry mod : itemAttributeModifiers.modifiers()) {
            if (AttributeRegistrationHelper.CRIT_DAMAGE_ATTRIBUTE == mod.attribute()) {
              bonusCritDamagePercent += (float) mod.modifier().value();
            }
          }

          float baseDamage = (float) player.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
          System.out.println(baseDamage);
          System.out.println(bonusCritDamagePercent);
          float critDamage = baseDamage * (1.5f + bonusCritDamagePercent);

          target.damage(player.getDamageSources().generic(), critDamage);
          return ActionResult.SUCCESS;
        }
      }
      return ActionResult.PASS;
    });
  }
}
