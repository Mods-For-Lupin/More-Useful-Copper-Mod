package io.github.jason13official.more_useful_copper.impl.common.item;

import io.github.jason13official.more_useful_copper.impl.common.registry.ModItems;
import java.util.concurrent.atomic.AtomicBoolean;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;

public class LightningArmorItem extends ArmorItem {

  public LightningArmorItem(Holder<ArmorMaterial> material, Type type, Properties properties) {
    super(material, type, properties);
  }

  @Override
  public boolean isFoil(ItemStack stack) {
    CustomData data = stack.get(DataComponents.CUSTOM_DATA);
    return (data != null && data.contains("charged")) || super.isFoil(stack);
  }

  @Override
  public boolean isEnchantable(ItemStack stack) {
    return super.isEnchantable(stack);
  }

  @Override
  public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {

    if (!isFoil(stack)) {
      return;
    }

    if (entity instanceof LivingEntity living) {
      helmet(level, living);
      chestplate(level, living);
      leggings(level, living);
      boots(level, living);
    }
  }

  private void helmet(Level level, LivingEntity living) {
    if (!living.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.COPPER_HELMET)) {
      return;
    }

    living.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 10, 0, false, false));
  }

  private void chestplate(Level level, LivingEntity living) {
    if (!living.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.COPPER_CHESTPLATE)) {
      return;
    }

    living.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 10, 0, false, false));
  }

  private void leggings(Level level, LivingEntity living) {
    if (!living.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.COPPER_LEGGINGS)) {
      return;
    }

    living.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 10, 1, false, false));
  }

  private void boots(Level level, LivingEntity living) {
    if (!living.getItemBySlot(EquipmentSlot.FEET).is(ModItems.COPPER_BOOTS)) {
      return;
    }

    living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 10, 0, false, false));

    if (allArmor(living)) {
      living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 10, 1, false, false));
      living.addEffect(new MobEffectInstance(MobEffects.JUMP, 10, 0, false, false));
      living.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 10, 1, false, false));
    }
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
