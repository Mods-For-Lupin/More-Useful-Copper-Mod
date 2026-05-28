package io.github.jason13official.more_useful_copper.impl.common.util;

import io.github.jason13official.more_useful_copper.impl.common.entity.CopperGolem;
import io.github.jason13official.more_useful_copper.impl.common.registry.ModEntities;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CarvedPumpkinBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class CommonCarvedPumpkinBlockMixinLogic {

  private static final Predicate<BlockState> PUMPKINS_PREDICATE = state -> state != null && (state.is(Blocks.CARVED_PUMPKIN) || state.is(Blocks.JACK_O_LANTERN));

  private static final Function<BlockPattern, BlockPattern> PATTERN = CommonCarvedPumpkinBlockMixinLogic::getOrCreateGolemPattern;

  public static void injectedTrySpawnGolem(Level level, BlockPos pos, CallbackInfo ci) {

    // BlockPattern.BlockPatternMatch blocksInPattern = getOrCreateGolemPattern(null).find(level, pos);
    BlockPattern.BlockPatternMatch blocksInPattern = PATTERN.apply(null).find(level, pos);

    if (blocksInPattern == null) {
      return;
    }

    CopperGolem golem = ModEntities.COPPER_GOLEM.create(level, EntitySpawnReason.MOB_SUMMONED);

    if (golem != null) {
      clearBlocksInPatternAndSpawnEntity(level, blocksInPattern, golem, blocksInPattern.getBlock(1, 2, 0).getPos());
    }
  }

  private static BlockPattern getOrCreateGolemPattern(BlockPattern pattern) {

    if (pattern == null) {

      pattern = BlockPatternBuilder.start()
          .aisle("~^~", "###", "~#~")
          .where('^', BlockInWorld.hasState(PUMPKINS_PREDICATE))
          .where('#', BlockInWorld.hasState(BlockStatePredicate.forBlock(Blocks.COPPER_BLOCK)))
          .where('~', (blockInWorld) -> blockInWorld.getState().isAir()).build();
    }

    return pattern;
  }

  private static void clearBlocksInPatternAndSpawnEntity(Level level, BlockPattern.BlockPatternMatch blockPatternMatch, Entity entity, BlockPos blockPos) {

    CarvedPumpkinBlock.clearPatternBlocks(level, blockPatternMatch);

    entity.snapTo((double)blockPos.getX() + 0.5, (double)blockPos.getY() + 0.05, (double)blockPos.getZ() + 0.5, 0.0F, 0.0F);

    level.addFreshEntity(entity);

    for (ServerPlayer serverPlayer : level.getEntitiesOfClass(ServerPlayer.class, entity.getBoundingBox().inflate(5.0))) {
      CriteriaTriggers.SUMMONED_ENTITY.trigger(serverPlayer, entity);
    }

    CarvedPumpkinBlock.updatePatternBlocks(level, blockPatternMatch);
  }
}
