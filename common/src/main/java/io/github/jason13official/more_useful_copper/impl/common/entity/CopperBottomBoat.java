package io.github.jason13official.more_useful_copper.impl.common.entity;

import io.github.jason13official.more_useful_copper.impl.common.registry.ModEntities;
import io.github.jason13official.more_useful_copper.impl.common.registry.ModItems;
import io.github.jason13official.more_useful_copper.impl.common.tags.ModItemTags;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.boat.Boat;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;

public class CopperBottomBoat extends Boat {

  public static final EntityDataAccessor<Boolean> WAXED = SynchedEntityData.defineId(CopperBottomBoat.class, EntityDataSerializers.BOOLEAN);
  public static final EntityDataAccessor<Integer> OXIDIZATION_LEVEL = SynchedEntityData.defineId(CopperBottomBoat.class, EntityDataSerializers.INT);

  public CopperBottomBoat(EntityType<? extends CopperBottomBoat> entityType, Level level) {
    super(entityType, level, () -> ModItems.COPPER_BOTTOM_BOAT);
  }

  public CopperBottomBoat(Level level, double x, double y, double z) {
    this(ModEntities.COPPER_BOTTOM_BOAT, level);
    this.setPos(x, y, z);
    this.xo = x;
    this.yo = y;
    this.zo = z;
  }

  @Override
  protected void defineSynchedData(SynchedEntityData.Builder builder) {
    super.defineSynchedData(builder);
    builder.define(OXIDIZATION_LEVEL, 0);
    builder.define(WAXED, false);
  }

  @Override
  public void tick() {
    super.tick();
    if (!this.level().isClientSide() && this.level().getRandom().nextFloat() <= 0.001F && !this.isWaxed()) {
      this.oxidize();
    }
  }

  @Override
  public InteractionResult interact(Player player, InteractionHand hand, Vec3 location) {
    ItemStack stack = player.getItemInHand(hand);

    if (player.isSecondaryUseActive()) {
      if (!this.level().isClientSide()) {
        if (stack.is(Items.HONEYCOMB) && !this.isWaxed()) {
          this.wax();
          stack.shrink(1);
          player.setItemInHand(hand, stack);
          return InteractionResult.CONSUME;
        }

        if (stack.is(ModItemTags.WAX_SCRAPER) && this.isWaxed()) {
          this.setWaxed(false);
          stack.hurtAndBreak(1, player, hand == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
          return InteractionResult.CONSUME;
        }

        if (stack.is(ModItemTags.MANUAL_OXIDIZER) && !this.isWaxed() && this.getOxidizationLevel() < 3) {
          this.oxidize();
          return InteractionResult.CONSUME;
        }
      }
      return InteractionResult.PASS;
    }

    return super.interact(player, hand, location);
  }

  @Override
  protected void addAdditionalSaveData(ValueOutput output) {
    super.addAdditionalSaveData(output);
    output.putInt("oxidization", this.getOxidizationLevel());
    output.putBoolean("waxed", this.isWaxed());
  }

  @Override
  protected void readAdditionalSaveData(ValueInput input) {
    super.readAdditionalSaveData(input);
    this.setOxidizationLevel(input.getIntOr("oxidization", 0));
    this.setWaxed(input.getBooleanOr("waxed", false));
  }

  public boolean isWaxed() {
    return this.getEntityData().get(WAXED);
  }

  public void setWaxed(boolean waxed) {
    this.getEntityData().set(WAXED, waxed);
  }

  public int getOxidizationLevel() {
    return this.getEntityData().get(OXIDIZATION_LEVEL);
  }

  public void setOxidizationLevel(int level) {
    this.getEntityData().set(OXIDIZATION_LEVEL, level);
  }

  public void wax() {
    this.setWaxed(true);
  }

  public void oxidize() {
    int current = this.getOxidizationLevel();
    if (current < 3) {
      setOxidizationLevel(current + 1);
    }
  }
}
