package io.github.jason13official.more_useful_copper.impl.common.block;

import io.github.jason13official.more_useful_copper.Constants;
import io.github.jason13official.more_useful_copper.platform.Services;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.AABB;

public class GardenStakeBlock extends Block {

  public static final BooleanProperty LIT = BlockStateProperties.LIT;

  public GardenStakeBlock(Properties properties) {
    super(properties.randomTicks());
    this.registerDefaultState(this.stateDefinition.any().setValue(LIT, false));
  }

  @Override
  protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
    builder.add(LIT);
  }

  @Override
  public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
    level.scheduleTick(pos, state.getBlock(), 5);
    return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
  }

  @Override
  public void tick(BlockState selfState, ServerLevel level, BlockPos selfPos, RandomSource random) {

    if (Services.PLATFORM.isDevelopmentEnvironment()) {
      Constants.LOG.info("GardenStake#tick");
    }

    if (!selfState.getValue(LIT)) {
      return;
    }

    AABB box = new AABB(selfPos).inflate(5, 3, 5);
    List<BlockPos> alreadyChecked = new ArrayList<>();

    iteration:
    for (double x = box.minX; x < box.maxX; x++) {
      for (double z = box.minZ; z < box.maxZ; z++) {
        for (double y = box.minY; y < box.maxY; y++) {
          
          if (random.nextFloat() >= 0.1) {
            continue iteration;
          }

          BlockPos pos = BlockPos.containing(x, y, z);
          BlockState state = level.getBlockState(pos);

          if (!alreadyChecked.contains(pos) && state.getBlock() instanceof BonemealableBlock block) {

            // Blocks.WHEAT -> CropBlock#performBonemeal -> CropBlock#growCrops
            block.performBonemeal(level, random, pos, state);

            alreadyChecked.add(pos);
          }
        }
      }
    }

    // level.scheduleTick(selfPos, selfState.getBlock(), 5);
  }

  @Override
  public boolean isRandomlyTicking(BlockState state) {
    return state.getValue(LIT); // will use tick logic for random ticking via randomTick side effect
  }

//  @Override
//  public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
//    super.randomTick(state, level, pos, random);
//  }

  @Override
  public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {

    if (!state.getValue(LIT)) {
      return;
    }

    if (random.nextFloat() > 0.25f) {
      return;
    }

    for (int particleCount = 0; particleCount < 2; ++particleCount) {
      int xModifier = random.nextInt(2) * 2 - 1;
      int zModifier = random.nextInt(2) * 2 - 1;
      double x = (double) pos.getX() + (double) 0.5F + (double) 0.25F * (double) xModifier;
      double y = (float) pos.getY() + random.nextFloat();
      double z = (double) pos.getZ() + (double) 0.5F + (double) 0.25F * (double) zModifier;
      double xSpeed = random.nextFloat() * (float) xModifier;
      double ySpeed = ((double) random.nextFloat() - (double) 0.5F) * (double) 0.125F;
      double zSpeed = random.nextFloat() * (float) zModifier;
      level.addParticle(ParticleTypes.HAPPY_VILLAGER, x, y, z, xSpeed, ySpeed, zSpeed);
    }

  }
}
