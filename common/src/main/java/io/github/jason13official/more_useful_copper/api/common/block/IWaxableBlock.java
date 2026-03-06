package io.github.jason13official.more_useful_copper.api.common.block;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import java.util.Optional;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public interface IWaxableBlock {

  BiMap<Block, Block> WAXABLES = HashBiMap.create();
  BiMap<Block, Block> WAX_OFF_BY_BLOCK = HashBiMap.create();

  static void addMapping(Block unwaxed, Block waxed) {
    WAXABLES.forcePut(unwaxed, waxed);
    WAX_OFF_BY_BLOCK.forcePut(waxed, unwaxed);
  }

  static Optional<BlockState> getWaxed(BlockState state) {
    return Optional.ofNullable(WAXABLES.get(state.getBlock())).map((block) -> block.withPropertiesOf(state));
  }
}
