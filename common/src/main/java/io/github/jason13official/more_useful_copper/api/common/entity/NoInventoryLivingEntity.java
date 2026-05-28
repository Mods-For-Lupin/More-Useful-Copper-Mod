package io.github.jason13official.more_useful_copper.api.common.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public abstract class NoInventoryLivingEntity extends LivingEntity {

  public NoInventoryLivingEntity(EntityType<? extends NoInventoryLivingEntity> entityType, Level level) {
    super(entityType, level);
  }

  @Override
  public ItemStack getItemBySlot(EquipmentSlot equipmentSlot) {
    return ItemStack.EMPTY;
  }

  @Override
  public void setItemSlot(EquipmentSlot equipmentSlot, ItemStack itemStack) {
    // no-op
  }

  @Override
  public HumanoidArm getMainArm() {
    return HumanoidArm.RIGHT;
  }
}
