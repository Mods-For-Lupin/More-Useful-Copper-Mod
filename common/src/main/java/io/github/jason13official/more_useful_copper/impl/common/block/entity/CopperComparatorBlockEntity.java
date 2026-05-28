package io.github.jason13official.more_useful_copper.impl.common.block.entity;

import io.github.jason13official.more_useful_copper.impl.common.registry.ModTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class CopperComparatorBlockEntity extends BlockEntity {

  private int output;

  public CopperComparatorBlockEntity(BlockPos pos, BlockState state) {
    super(ModTiles.COPPER_COMPARATOR, pos, state);
  }

  @Override
  protected void saveAdditional(ValueOutput output) {
    super.saveAdditional(output);
    output.putInt("OutputSignal", this.output);
  }

  @Override
  protected void loadAdditional(ValueInput input) {
    super.loadAdditional(input);
    this.output = input.getIntOr("OutputSignal", 0);
  }

  public int getOutputSignal() {
    return this.output;
  }

  public void setOutputSignal(int output) {
    this.output = output;
  }
}
