package io.github.jason13official.more_useful_copper.impl.common.util;

import io.github.jason13official.more_useful_copper.impl.common.block.GardenStakeBlock;
import io.github.jason13official.more_useful_copper.impl.common.registry.ModBlocks;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class CommonLightningBoltMixinLogic {

  /// Mixin injection: called each tick of a [LightningBolt] to light nearby garden stakes.
  /// Only acts on tick `life == 2` (the peak-energy tick). Checks the strike block, then the
  /// bolt's current position, then a 10×6×10 AABB around the strike position.
  ///
  /// @param life      remaining lifetime ticks of the bolt
  /// @param strikePos the block position the bolt originally struck
  public static void onLightningTick(LightningBolt self, int life, BlockPos strikePos) {

    if (!(self.level() instanceof ServerLevel level)) {
      return;
    }

    if (life != 2) {
      return;
    }

    BlockState strikeState = level.getBlockState(strikePos);

    BlockPos insidePos = self.blockPosition();
    BlockState insideState = level.getBlockState(insidePos);

    if (strikeState.is(ModBlocks.GARDEN_STAKE) && !strikeState.getValue(GardenStakeBlock.LIT)) {

      strikeState.setValue(GardenStakeBlock.LIT, true);
      level.setBlock(strikePos, strikeState, 2);

      return;
    } else if (insideState.is(ModBlocks.GARDEN_STAKE) && !insideState.getValue(GardenStakeBlock.LIT)) {

      System.out.println("lightning ticked inside of garden stake");

      var newState = insideState.setValue(GardenStakeBlock.LIT, true);
      // level.setBlock(insidePos, newState, 2);

      level.setBlockAndUpdate(insidePos, newState);
      level.setBlocksDirty(insidePos, insideState, newState);

      return;
    }

    AABB box = new AABB(strikePos).inflate(5, 3, 5);
    List<BlockPos> alreadyChecked = new ArrayList<>();

    for (double x = box.minX; x < box.maxX; x++) {
      for (double z = box.minZ; z < box.maxZ; z++) {
        for (double y = box.minY; y < box.maxY; y++) {

          BlockPos pos = BlockPos.containing(x, y, z);
          BlockState state = level.getBlockState(pos);

          if (!alreadyChecked.contains(pos)) {

            if (state.getBlock() instanceof GardenStakeBlock) {
              state.setValue(GardenStakeBlock.LIT, true);
              level.setBlock(pos, state, 2);
              return;
            }

            alreadyChecked.add(pos);
          }
        }
      }
    }
  }
}
