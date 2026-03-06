package io.github.jason13official.more_useful_copper.api.common.block;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import io.github.jason13official.more_useful_copper.impl.common.registry.ModBlocks;
import java.util.Optional;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChangeOverTimeBlock;
import net.minecraft.world.level.block.WeatheringCopper.WeatherState;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public interface IOxidizableBlock extends ChangeOverTimeBlock<WeatherState> {

  BiMap<Block, Block> NEXT_BY_BLOCK = HashBiMap.create();
  BiMap<Block, Block> PREVIOUS_BY_BLOCK = HashBiMap.create();

  static void addMapping(Block initialState, Block nextState) {
    NEXT_BY_BLOCK.forcePut(initialState, nextState);
    PREVIOUS_BY_BLOCK.forcePut(nextState, initialState);
  }

  static void init() {
    addMapping(ModBlocks.COPPER_BUTTON, ModBlocks.EXPOSED_COPPER_BUTTON);
    addMapping(ModBlocks.EXPOSED_COPPER_BUTTON, ModBlocks.WEATHERED_COPPER_BUTTON);
    addMapping(ModBlocks.WEATHERED_COPPER_BUTTON, ModBlocks.OXIDIZED_COPPER_BUTTON);
  }

  static Optional<Block> getPrevious(Block block) {

    return Optional.ofNullable(PREVIOUS_BY_BLOCK.get(block));
  }

  static Block getFirst(Block initialBlock) {

    Block reference = initialBlock;

    for (Block found = PREVIOUS_BY_BLOCK.get(initialBlock); found != null; found = PREVIOUS_BY_BLOCK.get(found)) {
      reference = found;
    }

    return reference;
  }

  static Optional<BlockState> getPrevious(BlockState state) {

    return getPrevious(state.getBlock()).map((block) -> block.withPropertiesOf(state));
  }

  static Optional<Block> getNext(Block block) {

    return Optional.ofNullable(NEXT_BY_BLOCK.get(block));
  }

  static BlockState getFirst(BlockState state) {

    return getFirst(state.getBlock()).withPropertiesOf(state);
  }

  default @NotNull Optional<BlockState> getNext(BlockState state) {

    return getNext(state.getBlock()).map((block) -> block.withPropertiesOf(state));
  }

  default float getChanceModifier() {

    return this.getAge() == WeatherState.UNAFFECTED ? 0.75F : 1.0F;
  }
}
