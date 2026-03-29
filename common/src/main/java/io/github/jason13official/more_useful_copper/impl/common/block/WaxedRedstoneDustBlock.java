package io.github.jason13official.more_useful_copper.impl.common.block;

import io.github.jason13official.more_useful_copper.impl.common.tags.ModItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.WeatheringCopper.WeatherState;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class WaxedRedstoneDustBlock extends CopperRedstoneDustBlock {

  public WaxedRedstoneDustBlock(Properties props, WeatherState weatherState) {
    super(props, weatherState);
  }

  @Override
  public boolean isRandomlyTicking(BlockState state) {
    return false;
  }

  @Override
  protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
    if (stack.is(ModItemTags.WAX_SCRAPER)) {
      return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }
    return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
  }

  @Override
  protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
    if (!player.getAbilities().mayBuild) {
      return InteractionResult.PASS;
    }
    if (isCross(state) || isDot(state)) {
      BlockState newState = isCross(state) ? this.defaultBlockState() : this.crossState;
      newState = newState.setValue(POWER, state.getValue(POWER)).setValue(WATERLOGGED, state.getValue(WATERLOGGED));
      newState = this.getConnectionState(level, newState, pos);
      if (newState != state) {
        level.setBlock(pos, newState, 3);
        this.updatesOnShapeChange(level, pos, state, newState);
        return InteractionResult.SUCCESS;
      }
    }
    return InteractionResult.PASS;
  }
}
