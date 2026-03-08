package io.github.jason13official.more_useful_copper.impl.common.block;

import io.github.jason13official.more_useful_copper.impl.common.tags.ModItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LeverBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class WaxedLeverBlock extends LeverBlock {

  public WaxedLeverBlock(Properties properties) {
    super(properties);
  }

  @Override
  public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {

    ItemStack stackInHand = player.getItemInHand(hand);

    if (stackInHand.is(ModItemTags.WAX_SCRAPER)) {
      return InteractionResult.PASS;
    }

    return super.use(state, level, pos, player, hand, hit);
  }
}
