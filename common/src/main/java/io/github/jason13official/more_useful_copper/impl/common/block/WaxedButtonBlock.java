package io.github.jason13official.more_useful_copper.impl.common.block;

import io.github.jason13official.more_useful_copper.impl.common.tags.ModItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.WeatheringCopper.WeatherState;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.phys.BlockHitResult;

public class WaxedButtonBlock extends CopperButtonBlock {

  public WaxedButtonBlock(Properties properties, BlockSetType type, int ticksToStayPressed, boolean arrowsCanPress) {
    super(properties, WeatherState.UNAFFECTED, type, ticksToStayPressed, arrowsCanPress);
  }

  @Override
  public boolean isRandomlyTicking(BlockState state) {
    return false;
  }

  @Override
  protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
    if (stack.is(ModItemTags.WAX_SCRAPER)) {
      return InteractionResult.TRY_WITH_EMPTY_HAND;
    }
    return super.useItemOn(stack, state, level, pos, player, hand, hit);
  }
}
