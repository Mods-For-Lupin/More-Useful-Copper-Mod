package io.github.jason13official.more_useful_copper.impl.common.block;

import io.github.jason13official.more_useful_copper.impl.common.tags.ModItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.WeatheringCopper.WeatherState;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ComparatorMode;
import net.minecraft.world.phys.BlockHitResult;

public class WaxedComparatorBlock extends CopperComparatorBlock {

  public WaxedComparatorBlock(Properties properties) {
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

    ItemStack stackInHand = player.getItemInHand(hand);

    // remove wax
    if (stackInHand.is(ModItemTags.WAX_SCRAPER)) {
      return InteractionResult.PASS;
    }

    // cycle mode
    state = state.cycle(MODE);
    float f = state.getValue(MODE) == ComparatorMode.SUBTRACT ? 0.55F : 0.5F;
    level.playSound(player, pos, SoundEvents.COMPARATOR_CLICK, SoundSource.BLOCKS, 0.3F, f);
    level.setBlock(pos, state, 2);
    this.refreshOutputState(level, pos, state);
    return InteractionResult.sidedSuccess(level.isClientSide);
  }
}
