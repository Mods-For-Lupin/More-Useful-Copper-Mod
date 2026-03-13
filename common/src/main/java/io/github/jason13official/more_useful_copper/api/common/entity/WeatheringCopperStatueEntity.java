package io.github.jason13official.more_useful_copper.api.common.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public abstract class WeatheringCopperStatueEntity extends AbstractStatueEntity {

  public static final EntityDataAccessor<Boolean> WAXED = SynchedEntityData.defineId(WeatheringCopperStatueEntity.class, EntityDataSerializers.BOOLEAN);
  public static final EntityDataAccessor<Integer> OXIDIZATION_LEVEL = SynchedEntityData.defineId(WeatheringCopperStatueEntity.class, EntityDataSerializers.INT);

  public WeatheringCopperStatueEntity(EntityType<? extends AbstractStatueEntity> entityType, Level level) {
    super(entityType, level);
  }

  public WeatheringCopperStatueEntity(EntityType<? extends AbstractStatueEntity> entityType, Level level, double x, double y, double z) {
    super(entityType, level, x, y, z);
  }

  @Override
  protected void defineSynchedData() {
    super.defineSynchedData();

    this.entityData.define(OXIDIZATION_LEVEL, 0);
    this.entityData.define(WAXED, false);
  }

  @Override
  public void addAdditionalSaveData(CompoundTag compound) {
    super.addAdditionalSaveData(compound);

    compound.putInt("oxidization", this.getOxidizationLevel());
    compound.putBoolean("waxed", this.isWaxed());
  }

  @Override
  public void readAdditionalSaveData(CompoundTag compound) {
    super.readAdditionalSaveData(compound);

    this.setOxidizationLevel(compound.getInt("oxidization"));
    this.setWaxed(compound.getBoolean("waxed"));
  }

  public boolean isWaxed() {
    return this.getEntityData().get(WAXED);
  }

  public int getOxidizationLevel() {
    return this.getEntityData().get(OXIDIZATION_LEVEL);
  }

  public void setWaxed(boolean waxed) {
    this.getEntityData().set(WAXED, waxed);
  }

  public void wax() {
    this.setWaxed(true);
  }

  public void setOxidizationLevel(int level) {
    this.getEntityData().set(OXIDIZATION_LEVEL, level);
  }

  /// Advances oxidation by one stage (0 = unaffected → 3 = oxidized). No-ops if already at max.
  public void oxidize() {
    int currentLevel = this.getOxidizationLevel();

    if (currentLevel < 3) {
      setOxidizationLevel(currentLevel + 1);
    }
  }

  /// Ticks oxidation on the server: 0.05% chance per tick to advance one oxidation stage,
  /// unless the statue is waxed.
  @Override
  protected void serverAiStep() {
    ServerLevel level = (ServerLevel) this.level();

    if (level.getRandom().nextFloat() <= 0.0005) {
      if (!this.isWaxed()) {
        this.oxidize();
      }
    }
  }

  /// Applies wax when a player right-clicks with a honeycomb, consuming one item from the stack.
  @Override
  public InteractionResult interact(Player player, InteractionHand hand) {

    ItemStack stack = player.getItemInHand(hand);

    if (!this.level().isClientSide() && stack.is(Items.HONEYCOMB)) {

      this.wax();

      stack.shrink(1);
      player.setItemInHand(hand, stack);
    }

    // return super.interact(player, hand);
    return InteractionResult.PASS;
  }
}
