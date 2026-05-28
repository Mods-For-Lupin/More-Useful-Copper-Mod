package io.github.jason13official.more_useful_copper.impl.common.item;

import io.github.jason13official.more_useful_copper.api.common.block.IOxidizableBlock;
import java.util.Optional;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

public class SprayBottleItem extends Item {

  public SprayBottleItem(Properties properties) {
    super(properties);
  }

  @Override
  public InteractionResult useOn(UseOnContext context) {
    Level level = context.getLevel();
    BlockPos blockPos = context.getClickedPos();
    BlockState blockState = level.getBlockState(blockPos);

    // check our registry first
    Optional<BlockState> nextState = IOxidizableBlock.getNext(blockState.getBlock())
        .map(block -> block.withPropertiesOf(blockState));

    // fall back to vanilla weathering copper registry
    if (nextState.isEmpty()) {
      nextState = WeatheringCopper.getNext(blockState.getBlock())
          .map(block -> block.withPropertiesOf(blockState));
    }

    if (nextState.isEmpty()) {
      return InteractionResult.PASS;
    }

    Player player = context.getPlayer();
    ItemStack itemStack = context.getItemInHand();

    if (!level.isClientSide()) {
      if (player instanceof ServerPlayer) {
        CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, blockPos, itemStack);
      }
      BlockState result = nextState.get();
      level.playSound(null, blockPos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
      level.levelEvent(null, LevelEvent.PARTICLES_SCRAPE, blockPos, 0);
      level.setBlock(blockPos, result, Block.UPDATE_ALL_IMMEDIATE);
      level.gameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Context.of(player, result));
      itemStack.hurtAndBreak(1, player, context.getHand() == net.minecraft.world.InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
    }

    return InteractionResult.SUCCESS;
  }
}
