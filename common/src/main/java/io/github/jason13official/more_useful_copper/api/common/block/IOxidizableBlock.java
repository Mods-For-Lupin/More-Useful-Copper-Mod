package io.github.jason13official.more_useful_copper.api.common.block;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import java.util.Optional;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChangeOverTimeBlock;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.WeatheringCopper.WeatherState;
import net.minecraft.world.level.block.state.BlockState;

public interface IOxidizableBlock extends ChangeOverTimeBlock<WeatherState> {

  BiMap<Block, Block> NEXT_BY_BLOCK = HashBiMap.create();
  BiMap<Block, Block> PREVIOUS_BY_BLOCK = HashBiMap.create();

  static void addMapping(Block initialState, Block nextState) {
    NEXT_BY_BLOCK.forcePut(initialState, nextState);
    PREVIOUS_BY_BLOCK.forcePut(nextState, initialState);
  }

  static Optional<Block> getPrevious(Block block) {
    return Optional.ofNullable((Block) ((BiMap) PREVIOUS_BY_BLOCK).get(block));
  }

  static Block getFirst(Block p_block) {
    Block block = p_block;

    for (Block block1 = (Block) ((BiMap) PREVIOUS_BY_BLOCK).get(p_block); block1 != null; block1 = PREVIOUS_BY_BLOCK.get(block1)) {
      block = block1;
    }

    return block;
  }

  static Optional<BlockState> getPrevious(BlockState state) {
    return getPrevious(state.getBlock()).map((block) -> block.withPropertiesOf(state));
  }

  static Optional<Block> getNext(Block block) {
    return Optional.ofNullable((Block) ((BiMap) NEXT_BY_BLOCK).get(block));
  }

  static BlockState getFirst(BlockState state) {
    return getFirst(state.getBlock()).withPropertiesOf(state);
  }

  default Optional<BlockState> getNext(BlockState state) {
    return getNext(state.getBlock()).map((block) -> block.withPropertiesOf(state));
  }

  default float getChanceModifier() {
    return this.getAge() == WeatheringCopper.WeatherState.UNAFFECTED ? 0.75F : 1.0F;
  }
}
