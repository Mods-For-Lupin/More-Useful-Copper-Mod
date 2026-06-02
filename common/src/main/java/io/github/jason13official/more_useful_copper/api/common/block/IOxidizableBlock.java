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

  /// Registers a one-step oxidation transition from `initialState` to `nextState`.
  ///
  /// @param initialState the less-oxidized block
  /// @param nextState    the more-oxidized block
  static void addMapping(Block initialState, Block nextState) {
    NEXT_BY_BLOCK.forcePut(initialState, nextState);
    PREVIOUS_BY_BLOCK.forcePut(nextState, initialState);
  }

  static void init() {
    addMapping(ModBlocks.COPPER_BUTTON, ModBlocks.EXPOSED_COPPER_BUTTON);
    addMapping(ModBlocks.EXPOSED_COPPER_BUTTON, ModBlocks.WEATHERED_COPPER_BUTTON);
    addMapping(ModBlocks.WEATHERED_COPPER_BUTTON, ModBlocks.OXIDIZED_COPPER_BUTTON);

    addMapping(ModBlocks.COPPER_COMPARATOR, ModBlocks.EXPOSED_COPPER_COMPARATOR);
    addMapping(ModBlocks.EXPOSED_COPPER_COMPARATOR, ModBlocks.WEATHERED_COPPER_COMPARATOR);
    addMapping(ModBlocks.WEATHERED_COPPER_COMPARATOR, ModBlocks.OXIDIZED_COPPER_COMPARATOR);

    addMapping(ModBlocks.COPPER_REDSTONE_DUST, ModBlocks.EXPOSED_COPPER_REDSTONE_DUST);
    addMapping(ModBlocks.EXPOSED_COPPER_REDSTONE_DUST, ModBlocks.WEATHERED_COPPER_REDSTONE_DUST);
    addMapping(ModBlocks.WEATHERED_COPPER_REDSTONE_DUST, ModBlocks.OXIDIZED_COPPER_REDSTONE_DUST);

    addMapping(ModBlocks.COPPER_LEVER, ModBlocks.EXPOSED_COPPER_LEVER);
    addMapping(ModBlocks.EXPOSED_COPPER_LEVER, ModBlocks.WEATHERED_COPPER_LEVER);
    addMapping(ModBlocks.WEATHERED_COPPER_LEVER, ModBlocks.OXIDIZED_COPPER_LEVER);

    addMapping(ModBlocks.COPPER_REDSTONE_TORCH, ModBlocks.EXPOSED_COPPER_REDSTONE_TORCH);
    addMapping(ModBlocks.EXPOSED_COPPER_REDSTONE_TORCH, ModBlocks.WEATHERED_COPPER_REDSTONE_TORCH);
    addMapping(ModBlocks.WEATHERED_COPPER_REDSTONE_TORCH, ModBlocks.OXIDIZED_COPPER_REDSTONE_TORCH);

    addMapping(ModBlocks.COPPER_WALL_REDSTONE_TORCH, ModBlocks.EXPOSED_COPPER_WALL_REDSTONE_TORCH);
    addMapping(ModBlocks.EXPOSED_COPPER_WALL_REDSTONE_TORCH, ModBlocks.WEATHERED_COPPER_WALL_REDSTONE_TORCH);
    addMapping(ModBlocks.WEATHERED_COPPER_WALL_REDSTONE_TORCH, ModBlocks.OXIDIZED_COPPER_WALL_REDSTONE_TORCH);

    addMapping(ModBlocks.COPPER_REPEATER, ModBlocks.EXPOSED_COPPER_REPEATER);
    addMapping(ModBlocks.EXPOSED_COPPER_REPEATER, ModBlocks.WEATHERED_COPPER_REPEATER);
    addMapping(ModBlocks.WEATHERED_COPPER_REPEATER, ModBlocks.OXIDIZED_COPPER_REPEATER);

    addMapping(ModBlocks.COPPER_PRESSURE_PLATE, ModBlocks.EXPOSED_COPPER_PRESSURE_PLATE);
    addMapping(ModBlocks.EXPOSED_COPPER_PRESSURE_PLATE, ModBlocks.WEATHERED_COPPER_PRESSURE_PLATE);
    addMapping(ModBlocks.WEATHERED_COPPER_PRESSURE_PLATE, ModBlocks.OXIDIZED_COPPER_PRESSURE_PLATE);

    // addMapping(ModBlocks.COPPER_CHAIN, ModBlocks.EXPOSED_COPPER_CHAIN);
    // addMapping(ModBlocks.EXPOSED_COPPER_CHAIN, ModBlocks.WEATHERED_COPPER_CHAIN);
    // addMapping(ModBlocks.WEATHERED_COPPER_CHAIN, ModBlocks.OXIDIZED_COPPER_CHAIN);
  }

  /// Returns the less-oxidized block one step before `block` in the chain, if any.
  ///
  /// @param block a registered oxidizable block
  /// @return the previous oxidation stage, or empty if already `UNAFFECTED`
  static Optional<Block> getPrevious(Block block) {

    return Optional.ofNullable(PREVIOUS_BY_BLOCK.get(block));
  }

  /// Walks the oxidation chain backwards to find the fully unaffected (first) block.
  ///
  /// @param initialBlock any block in the chain
  /// @return the `UNAFFECTED` block at the start of the chain
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

  /// Returns the more-oxidized block one step after `block` in the chain, if any.
  ///
  /// @param block a registered oxidizable block
  /// @return the next oxidation stage, or empty if already `OXIDIZED`
  static Optional<Block> getNext(Block block) {

    return Optional.ofNullable(NEXT_BY_BLOCK.get(block));
  }

  static BlockState getFirst(BlockState state) {

    return getFirst(state.getBlock()).withPropertiesOf(state);
  }

  default @NotNull Optional<BlockState> getNext(BlockState state) {

    return getNext(state.getBlock()).map((block) -> block.withPropertiesOf(state));
  }

  /// Returns a multiplier applied to the random-tick oxidation chance. `UNAFFECTED` blocks oxidize at 75% the normal rate; all other stages use 100%.
  ///
  /// @return `0.75` for `UNAFFECTED`, `1.0` otherwise
  default float getChanceModifier() {

    return this.getAge() == WeatherState.UNAFFECTED ? 0.75F : 1.0F;
  }
}
