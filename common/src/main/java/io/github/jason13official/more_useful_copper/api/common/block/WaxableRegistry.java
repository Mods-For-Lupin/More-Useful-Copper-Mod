package io.github.jason13official.more_useful_copper.api.common.block;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import io.github.jason13official.more_useful_copper.impl.common.registry.ModBlocks;
import java.util.Optional;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoneycombItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;

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

  /**
   * Attempts to apply wax to a block using a honeycomb item.
   * Returns a non-null {@link InteractionResult} if the item was a honeycomb (hit or miss),
   * or {@code null} if the item was not a honeycomb (caller should continue handling).
   */
  @Nullable
  public static InteractionResult tryWaxing(BlockState state, Level level, BlockPos pos, Player player, ItemStack itemStack) {
    if (itemStack.getItem() instanceof HoneycombItem) {
      Optional<BlockState> waxedState = getWaxed(state);
      if (waxedState.isPresent()) {
        if (!level.isClientSide) {
          BlockState blockstate = waxedState.get();
          if (player instanceof ServerPlayer sp) {
            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(sp, pos, itemStack);
          }
          itemStack.shrink(1);
          level.setBlock(pos, blockstate, Block.UPDATE_ALL_IMMEDIATE);
          level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, blockstate));
          level.levelEvent(null, LevelEvent.PARTICLES_AND_SOUND_WAX_ON, pos, 0);
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
      }
      return InteractionResult.PASS;
    }
    return null;
  }

  public static void init() {
    addMapping(ModBlocks.COPPER_BUTTON, ModBlocks.WAXED_COPPER_BUTTON);
    addMapping(ModBlocks.EXPOSED_COPPER_BUTTON, ModBlocks.WAXED_EXPOSED_COPPER_BUTTON);
    addMapping(ModBlocks.WEATHERED_COPPER_BUTTON, ModBlocks.WAXED_WEATHERED_COPPER_BUTTON);
    addMapping(ModBlocks.OXIDIZED_COPPER_BUTTON, ModBlocks.WAXED_OXIDIZED_COPPER_BUTTON);

    addMapping(ModBlocks.COPPER_COMPARATOR, ModBlocks.WAXED_COPPER_COMPARATOR);
    addMapping(ModBlocks.EXPOSED_COPPER_COMPARATOR, ModBlocks.WAXED_EXPOSED_COPPER_COMPARATOR);
    addMapping(ModBlocks.WEATHERED_COPPER_COMPARATOR, ModBlocks.WAXED_WEATHERED_COPPER_COMPARATOR);
    addMapping(ModBlocks.OXIDIZED_COPPER_COMPARATOR, ModBlocks.WAXED_OXIDIZED_COPPER_COMPARATOR);

    addMapping(ModBlocks.COPPER_REDSTONE_DUST, ModBlocks.WAXED_COPPER_REDSTONE_DUST);
    addMapping(ModBlocks.EXPOSED_COPPER_REDSTONE_DUST, ModBlocks.WAXED_EXPOSED_COPPER_REDSTONE_DUST);
    addMapping(ModBlocks.WEATHERED_COPPER_REDSTONE_DUST, ModBlocks.WAXED_WEATHERED_COPPER_REDSTONE_DUST);
    addMapping(ModBlocks.OXIDIZED_COPPER_REDSTONE_DUST, ModBlocks.WAXED_OXIDIZED_COPPER_REDSTONE_DUST);

    addMapping(ModBlocks.COPPER_LEVER, ModBlocks.WAXED_COPPER_LEVER);
    addMapping(ModBlocks.EXPOSED_COPPER_LEVER, ModBlocks.WAXED_EXPOSED_COPPER_LEVER);
    addMapping(ModBlocks.WEATHERED_COPPER_LEVER, ModBlocks.WAXED_WEATHERED_COPPER_LEVER);
    addMapping(ModBlocks.OXIDIZED_COPPER_LEVER, ModBlocks.WAXED_OXIDIZED_COPPER_LEVER);

    addMapping(ModBlocks.COPPER_REDSTONE_TORCH, ModBlocks.WAXED_COPPER_REDSTONE_TORCH);
    addMapping(ModBlocks.EXPOSED_COPPER_REDSTONE_TORCH, ModBlocks.WAXED_EXPOSED_COPPER_REDSTONE_TORCH);
    addMapping(ModBlocks.WEATHERED_COPPER_REDSTONE_TORCH, ModBlocks.WAXED_WEATHERED_COPPER_REDSTONE_TORCH);
    addMapping(ModBlocks.OXIDIZED_COPPER_REDSTONE_TORCH, ModBlocks.WAXED_OXIDIZED_COPPER_REDSTONE_TORCH);

    addMapping(ModBlocks.COPPER_WALL_REDSTONE_TORCH, ModBlocks.WAXED_COPPER_WALL_REDSTONE_TORCH);
    addMapping(ModBlocks.EXPOSED_COPPER_WALL_REDSTONE_TORCH, ModBlocks.WAXED_EXPOSED_COPPER_WALL_REDSTONE_TORCH);
    addMapping(ModBlocks.WEATHERED_COPPER_WALL_REDSTONE_TORCH, ModBlocks.WAXED_WEATHERED_COPPER_WALL_REDSTONE_TORCH);
    addMapping(ModBlocks.OXIDIZED_COPPER_WALL_REDSTONE_TORCH, ModBlocks.WAXED_OXIDIZED_COPPER_WALL_REDSTONE_TORCH);

    addMapping(ModBlocks.COPPER_REPEATER, ModBlocks.WAXED_COPPER_REPEATER);
    addMapping(ModBlocks.EXPOSED_COPPER_REPEATER, ModBlocks.WAXED_EXPOSED_COPPER_REPEATER);
    addMapping(ModBlocks.WEATHERED_COPPER_REPEATER, ModBlocks.WAXED_WEATHERED_COPPER_REPEATER);
    addMapping(ModBlocks.OXIDIZED_COPPER_REPEATER, ModBlocks.WAXED_OXIDIZED_COPPER_REPEATER);
  }
}
