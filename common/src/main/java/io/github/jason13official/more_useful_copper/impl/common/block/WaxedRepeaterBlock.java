package io.github.jason13official.more_useful_copper.impl.common.block;

import io.github.jason13official.more_useful_copper.impl.common.tags.ModItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.WeatheringCopper.WeatherState;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class WaxedRepeaterBlock extends CopperRepeaterBlock {

  public WaxedRepeaterBlock(Properties properties) {
    super(properties, WeatherState.UNAFFECTED);
  }

  @Override
  public boolean isRandomlyTicking(BlockState state) {
    return false;
  }

  @Override
  public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
    if (!player.getAbilities().mayBuild) {
      return InteractionResult.PASS;
    }

    if (player.getItemInHand(hand).is(ModItemTags.WAX_SCRAPER)) {
      return InteractionResult.PASS;
    }

    level.setBlock(pos, state.cycle(DELAY), 3);
    return InteractionResult.sidedSuccess(level.isClientSide);
  }
}
