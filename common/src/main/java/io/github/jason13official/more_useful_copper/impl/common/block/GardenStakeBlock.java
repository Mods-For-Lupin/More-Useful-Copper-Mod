package io.github.jason13official.more_useful_copper.impl.common.block;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
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
    super(properties);
    this.registerDefaultState(this.stateDefinition.any().setValue(LIT, false));
  }

  @Override
  protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
    builder.add(LIT);
  }

  /// Ensures a tick gets fired soon after being activated
//  @Override
//  public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
//    level.scheduleTick(pos, state.getBlock(), 5);
//    return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
//  }
  @Override
  public boolean isRandomlyTicking(BlockState state) {
    return state.getValue(LIT); // will use GardenStakeBlock#tick logic for random ticking via randomTick side effect
  }

  @Override
  public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
    // super.randomTick(state, level, pos, random); // only calls to tick which we override

    this.tick(state, level, pos, random);
  }

  @Override
  public void tick(BlockState selfState, ServerLevel level, BlockPos selfPos, RandomSource random) {

    AABB box = new AABB(selfPos).inflate(2, 1, 2); // 5x3x5 bounding box centered on garden stake
    List<BlockPos> alreadyChecked = new ArrayList<>();

    iteration:
    for (int x = (int) box.minX; x < (int) box.maxX; x++) {
      for (int z = (int) box.minZ; z < (int) box.maxZ; z++) {
        for (int y = (int) box.minY; y < (int) box.maxY; y++) {

          if (random.nextFloat() >= 0.9) {
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
