package io.github.jason13official.more_useful_copper.impl.common.block.entity;

import io.github.jason13official.more_useful_copper.impl.common.registry.ModTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class CopperComparatorBlockEntity extends BlockEntity {

  private int output;

  public CopperComparatorBlockEntity(BlockPos pos, BlockState state) {
    super(ModTiles.COPPER_COMPARATOR, pos, state);
  }

  @Override
  protected void saveAdditional(CompoundTag tag) {
    super.saveAdditional(tag);
    tag.putInt("OutputSignal", this.output);
  }

  @Override
  public void load(CompoundTag tag) {
    super.load(tag);
    this.output = tag.getInt("OutputSignal");
  }

  public int getOutputSignal() {
    return this.output;
  }

  public void setOutputSignal(int output) {
    this.output = output;
  }
}
