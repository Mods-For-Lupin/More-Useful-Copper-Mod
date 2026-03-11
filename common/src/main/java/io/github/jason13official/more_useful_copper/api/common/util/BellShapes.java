package io.github.jason13official.more_useful_copper.api.common.util;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BellShapes {

  public static final VoxelShape NORTH_SOUTH_FLOOR_SHAPE = Block.box(0.0F, 0.0F, 4.0F, 16.0F, 16.0F, 12.0F);
  public static final VoxelShape EAST_WEST_FLOOR_SHAPE = Block.box(4.0F, 0.0F, 0.0F, 12.0F, 16.0F, 16.0F);
  public static final VoxelShape BELL_TOP_SHAPE = Block.box(5.0F, 6.0F, 5.0F, 11.0F, 13.0F, 11.0F);
  public static final VoxelShape BELL_BOTTOM_SHAPE = Block.box(4.0F, 4.0F, 4.0F, 12.0F, 6.0F, 12.0F);
  public static final VoxelShape BELL_SHAPE = Shapes.or(BELL_BOTTOM_SHAPE, BELL_TOP_SHAPE);
  public static final VoxelShape NORTH_SOUTH_BETWEEN = Shapes.or(BELL_SHAPE, Block.box(7.0F, 13.0F, 0.0F, 9.0F, 15.0F, 16.0F));
  public static final VoxelShape EAST_WEST_BETWEEN = Shapes.or(BELL_SHAPE, Block.box(0.0F, 13.0F, 7.0F, 16.0F, 15.0F, 9.0F));
  public static final VoxelShape TO_WEST = Shapes.or(BELL_SHAPE, Block.box(0.0F, 13.0F, 7.0F, 13.0F, 15.0F, 9.0F));
  public static final VoxelShape TO_EAST = Shapes.or(BELL_SHAPE, Block.box(3.0F, 13.0F, 7.0F, 16.0F, 15.0F, 9.0F));
  public static final VoxelShape TO_NORTH = Shapes.or(BELL_SHAPE, Block.box(7.0F, 13.0F, 0.0F, 9.0F, 15.0F, 13.0F));
  public static final VoxelShape TO_SOUTH = Shapes.or(BELL_SHAPE, Block.box(7.0F, 13.0F, 3.0F, 9.0F, 15.0F, 16.0F));
  public static final VoxelShape CEILING_SHAPE = Shapes.or(BELL_SHAPE, Block.box(7.0F, 13.0F, 7.0F, 9.0F, 16.0F, 9.0F));
}
