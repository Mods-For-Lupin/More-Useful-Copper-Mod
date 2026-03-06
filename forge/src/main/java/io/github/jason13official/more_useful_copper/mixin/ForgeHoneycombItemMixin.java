package io.github.jason13official.more_useful_copper.mixin;

import io.github.jason13official.more_useful_copper.api.common.block.WaxableRegistry;
import java.util.Optional;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoneycombItem;
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

@Mixin(HoneycombItem.class)
public class ForgeHoneycombItemMixin {

  @Inject(at = @At("HEAD"), method = "useOn", cancellable = true)
  private void more_useful_copper$useOn(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir) {
    Level level = context.getLevel();
    BlockPos blockPos = context.getClickedPos();
    BlockState clickedState = level.getBlockState(blockPos);

    Optional<BlockState> waxedState = WaxableRegistry.getWaxed(clickedState);

    if (waxedState.isPresent()) {

      InteractionResult result = waxedState.map((blockstate -> {
        Player player = context.getPlayer();
        ItemStack itemStack = context.getItemInHand();
        if (player instanceof ServerPlayer) {
          CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer)player, blockPos, itemStack);
        }

        itemStack.shrink(1);
        level.setBlock(blockPos, blockstate, Block.UPDATE_ALL_IMMEDIATE);
        level.gameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Context.of(player, blockstate));
        level.levelEvent(player, LevelEvent.PARTICLES_AND_SOUND_WAX_ON, blockPos, 0);
        return InteractionResult.sidedSuccess(level.isClientSide);
      })).orElse(InteractionResult.PASS);

      cir.setReturnValue(result); // return

    } // else continue to vanilla logic
  }
}
