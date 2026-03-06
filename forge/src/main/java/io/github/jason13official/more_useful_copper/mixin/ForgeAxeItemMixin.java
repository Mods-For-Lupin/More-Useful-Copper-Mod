package io.github.jason13official.more_useful_copper.mixin;

import io.github.jason13official.more_useful_copper.api.common.block.WaxableRegistry;
import java.util.Optional;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AxeItem.class)
public class ForgeAxeItemMixin {

  @Inject(at = @At("HEAD"), method = "useOn", cancellable = true)
  private void more_useful_copper$useOn(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir) {
    Level level = context.getLevel();
    BlockPos blockPos = context.getClickedPos();
    BlockState blockState = level.getBlockState(blockPos);

    Optional<BlockState> unwaxedState = Optional.ofNullable(WaxableRegistry.WAX_OFF_BY_BLOCK.get(blockState.getBlock()))
        .map(block -> block.withPropertiesOf(blockState));

    if (unwaxedState.isPresent()) {
      Player player = context.getPlayer();
      ItemStack itemStack = context.getItemInHand();

      if (!level.isClientSide) {
        if (player instanceof ServerPlayer) {
          CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, blockPos, itemStack);
        }
        BlockState result = unwaxedState.get();
        level.playSound(null, blockPos, SoundEvents.AXE_WAX_OFF, SoundSource.BLOCKS, 1.0F, 1.0F);
        level.levelEvent(null, LevelEvent.PARTICLES_WAX_OFF, blockPos, 0);
        level.setBlock(blockPos, result, Block.UPDATE_ALL_IMMEDIATE);
        level.gameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Context.of(player, result));
        if (player != null) {
          itemStack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(context.getHand()));
        }
      }

      cir.setReturnValue(InteractionResult.sidedSuccess(level.isClientSide));
    }
  }
}
