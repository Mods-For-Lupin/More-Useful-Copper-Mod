package io.github.jason13official.more_useful_copper.api.common.block;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import io.github.jason13official.more_useful_copper.impl.common.registry.ModBlocks;
import java.util.Optional;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class WaxableRegistry {

  public static BiMap<Block, Block> WAXABLES = HashBiMap.create();
  public static BiMap<Block, Block> WAX_OFF_BY_BLOCK = HashBiMap.create();

  public static void addMapping(Block unwaxed, Block waxed) {
    WAXABLES.forcePut(unwaxed, waxed);
    WAX_OFF_BY_BLOCK.forcePut(waxed, unwaxed);
  }

  public static Optional<BlockState> getWaxed(BlockState state) {
    return Optional.ofNullable(WAXABLES.get(state.getBlock())).map((block) -> block.withPropertiesOf(state));
  }

  public static void init() {
    addMapping(ModBlocks.COPPER_BUTTON, ModBlocks.WAXED_COPPER_BUTTON);
    addMapping(ModBlocks.EXPOSED_COPPER_BUTTON, ModBlocks.WAXED_EXPOSED_COPPER_BUTTON);
    addMapping(ModBlocks.WEATHERED_COPPER_BUTTON, ModBlocks.WAXED_WEATHERED_COPPER_BUTTON);
    addMapping(ModBlocks.OXIDIZED_COPPER_BUTTON, ModBlocks.WAXED_OXIDIZED_COPPER_BUTTON);
  }
}
