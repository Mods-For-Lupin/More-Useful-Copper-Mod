package io.github.jason13official.more_useful_copper.impl.common.util;

import io.github.jason13official.more_useful_copper.impl.common.registry.ModItems;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class CommonLightningRodBlockMixinLogic {

  private static final Supplier<ItemStack> FILLED_LIGHTNING_BOTTLE = () -> new ItemStack(ModItems.LIGHTNING_BOTTLE);

  /// Mixin injection: called when a lightning rod is struck.
  /// Delegates to [createFilledLightningBottle] on the server side only.
  ///
  /// @param pos position of the lightning rod block
  public static void onLightningStrike(Level abstractLevel, BlockPos pos) {

    if (!(abstractLevel instanceof ServerLevel level)) {
      return;
    }

    createFilledLightningBottle(level, pos);
  }

  /// Converts one glass bottle in the container directly below the rod into a lightning bottle.
  /// If the matching stack has count > 1, the stack is shrunk and the filled bottle is placed in
  /// an empty slot; if no empty slot exists it is given to the nearest player within 64 blocks.
  ///
  /// @param pos position of the lightning rod (container is checked at `pos.below()`)
  private static void createFilledLightningBottle(ServerLevel level, BlockPos pos) {


    BlockEntity tile = level.getBlockEntity(pos.below());

    if (!(tile instanceof Container container)) {
      return;
    }

    int size = container.getContainerSize();

    if (container.hasAnyMatching(stack -> stack.is(Items.GLASS_BOTTLE))) {
      for (int index = 0; index < size; index++) {
        ItemStack stack = container.getItem(index);

        if (!stack.is(Items.GLASS_BOTTLE)) {
          continue; // check next slot
        }

        if (stack.getCount() == 1) {
          container.setItem(index, FILLED_LIGHTNING_BOTTLE.get());
          break; // only create one item
        } else {

          // shrink empty bottle stack
          stack.shrink(1);
          container.setItem(index, stack);

          // if there is an empty slot
          if (container.hasAnyMatching(check -> check == ItemStack.EMPTY)) {
            for (int emptyIndex = 0; emptyIndex < size; emptyIndex++) {
              if (container.getItem(emptyIndex).isEmpty()) {
                container.setItem(emptyIndex, FILLED_LIGHTNING_BOTTLE.get());
                break; // only create one item
              }
            }
          } else {

            var player = level.getNearestPlayer(pos.getX(), pos.getY(), pos.getZ(), 64.0D, true);

            if (player != null) {
              player.addItem(FILLED_LIGHTNING_BOTTLE.get());
            }

            break; // only create one item
          }
        }

      }
    }
  }
}
