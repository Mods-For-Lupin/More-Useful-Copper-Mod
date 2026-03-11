package io.github.jason13official.more_useful_copper.api.common.entity;

import java.util.List;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public abstract class NoInventoryLivingEntity extends LivingEntity {

  List<ItemStack> EMPTY_ARMOR_SLOTS = List.of(ItemStack.EMPTY);

  public NoInventoryLivingEntity(EntityType<? extends NoInventoryLivingEntity> entityType, Level level) {
    super(entityType, level);
  }

  @Override
  public Iterable<ItemStack> getArmorSlots() {
    return EMPTY_ARMOR_SLOTS;
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
