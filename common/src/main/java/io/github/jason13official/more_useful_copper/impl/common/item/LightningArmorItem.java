package io.github.jason13official.more_useful_copper.impl.common.item;

import io.github.jason13official.more_useful_copper.impl.common.registry.ModItems;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class LightningArmorItem extends Item {

  public LightningArmorItem(Properties properties) {
    super(properties);
  }

  @Override
  public boolean isFoil(ItemStack stack) {
    CustomData data = stack.get(DataComponents.CUSTOM_DATA);
    return (data != null && data.copyTag().contains("charged")) || super.isFoil(stack);
  }

  @Override
  public void inventoryTick(ItemStack stack, net.minecraft.server.level.ServerLevel level, Entity entity, @Nullable EquipmentSlot equipSlot) {

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

    living.addEffect(new MobEffectInstance(MobEffects.SPEED, 10, 0, false, false));

    if (allArmor(living)) {
      living.addEffect(new MobEffectInstance(MobEffects.SPEED, 10, 1, false, false));
      living.addEffect(new MobEffectInstance(MobEffects.JUMP_BOOST, 10, 0, false, false));
      living.addEffect(new MobEffectInstance(MobEffects.STRENGTH, 10, 1, false, false));
    }
  }

  private boolean allArmor(LivingEntity living) {
    return living.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.COPPER_HELMET)
        && living.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.COPPER_CHESTPLATE)
        && living.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.COPPER_LEGGINGS)
        && living.getItemBySlot(EquipmentSlot.FEET).is(ModItems.COPPER_BOOTS);
  }
}
