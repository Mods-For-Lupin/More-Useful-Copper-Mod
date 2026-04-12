package io.github.jason13official.more_useful_copper.impl.common.item;

import io.github.jason13official.more_useful_copper.impl.common.ModConfig;
import io.github.jason13official.more_useful_copper.impl.common.registry.ModItems;
import java.util.concurrent.atomic.AtomicBoolean;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class LightningArmorItem extends ArmorItem {

  /// Duration (in ticks) for effects applied by a single charge. 200 ticks = 10 seconds.
  private static final int EFFECT_DURATION = 200;

  public LightningArmorItem(ArmorMaterial material, Type type, Properties properties) {
    super(material, type, properties);
  }

  @Override
  public boolean isFoil(ItemStack stack) {
    return stack.getOrCreateTag().contains("charged") || super.isFoil(stack);
  }

  @Override
  public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {

    if (!ModConfig.get().lightningEffectsEnabled) {
      return;
    }

    if (!isFoil(stack)) {
      return;
    }

    if (level.isClientSide()) {
      return;
    }

    if (!(entity instanceof LivingEntity living)) {
      return;
    }

    switch (getType()) {
      case HELMET ->
          living.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, EFFECT_DURATION, 0, false, false));
      case CHESTPLATE ->
          living.addEffect(new MobEffectInstance(MobEffects.REGENERATION, EFFECT_DURATION, 0, false, false));
      case LEGGINGS ->
          living.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, EFFECT_DURATION, 1, false, false));
      case BOOTS -> {
        living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, EFFECT_DURATION, 0, false, false));
        if (allArmor(living)) {
          living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, EFFECT_DURATION, 1, false, false));
          living.addEffect(new MobEffectInstance(MobEffects.JUMP, EFFECT_DURATION, 0, false, false));
          living.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, EFFECT_DURATION, 1, false, false));
        }
      }
      default -> { return; }
    }

    stack.getOrCreateTag().remove("charged");
    living.setItemSlot(getType().getSlot(), stack);
  }

  private boolean allArmor(LivingEntity living) {

    AtomicBoolean foundHelmet = new AtomicBoolean(false);
    AtomicBoolean foundChestplate = new AtomicBoolean(false);
    AtomicBoolean foundLeggings = new AtomicBoolean(false);
    AtomicBoolean foundBoots = new AtomicBoolean(false);

    living.getArmorSlots().forEach(stack -> {
      if (stack.is(ModItems.COPPER_HELMET)) {
        foundHelmet.set(true);
      }
      else if (stack.is(ModItems.COPPER_CHESTPLATE)) {
        foundChestplate.set(true);
      }
      else if (stack.is(ModItems.COPPER_LEGGINGS)) {
        foundLeggings.set(true);
      }
      else if (stack.is(ModItems.COPPER_BOOTS)) {
        foundBoots.set(true);
      }
    });

    return foundHelmet.get() && foundChestplate.get() && foundLeggings.get() && foundBoots.get();
  }
}
