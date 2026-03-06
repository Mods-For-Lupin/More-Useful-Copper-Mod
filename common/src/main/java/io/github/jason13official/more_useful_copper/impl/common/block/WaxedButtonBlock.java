package io.github.jason13official.more_useful_copper.impl.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.phys.BlockHitResult;

public class WaxedButtonBlock extends ButtonBlock {

  public WaxedButtonBlock(Properties properties, BlockSetType type, int ticksToStayPressed, boolean arrowsCanPress) {
    super(properties, type, ticksToStayPressed, arrowsCanPress);
  }

  @Override
  public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
    if (player.getItemInHand(hand).getItem() instanceof AxeItem) {
      return InteractionResult.PASS;
    }
    return super.use(state, level, pos, player, hand, hit);
  }
}
