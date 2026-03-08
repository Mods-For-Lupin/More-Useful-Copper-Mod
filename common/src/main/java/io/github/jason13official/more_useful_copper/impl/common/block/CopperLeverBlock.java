package io.github.jason13official.more_useful_copper.impl.common.block;

import io.github.jason13official.more_useful_copper.api.common.block.IOxidizableBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.LeverBlock;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.WeatheringCopper.WeatherState;
import net.minecraft.world.level.block.state.BlockState;

public class CopperLeverBlock extends LeverBlock implements IOxidizableBlock {

  private final WeatherState weatherState;

  public CopperLeverBlock(Properties properties, WeatherState weatherState) {
    super(properties);
    this.weatherState = weatherState;
  }

  public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
    this.onRandomTick(state, level, pos, random);
  }

  public boolean isRandomlyTicking(BlockState state) {
    return IOxidizableBlock.getNext(state.getBlock()).isPresent();
  }

  public WeatheringCopper.WeatherState getAge() {
    return this.weatherState;
  }
}
