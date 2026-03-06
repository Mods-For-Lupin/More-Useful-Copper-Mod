package io.github.jason13official.more_useful_copper.api.common.util;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ButtonShapes {

  public static final VoxelShape CEILING_AABB_X = Block.box(6.0F, 14.0F, 5.0F, 10.0F, 16.0F, 11.0F);
  public static final VoxelShape CEILING_AABB_Z = Block.box(5.0F, 14.0F, 6.0F, 11.0F, 16.0F, 10.0F);
  public static final VoxelShape FLOOR_AABB_X = Block.box(6.0F, 0.0F, 5.0F, 10.0F, 2.0F, 11.0F);
  public static final VoxelShape FLOOR_AABB_Z = Block.box(5.0F, 0.0F, 6.0F, 11.0F, 2.0F, 10.0F);
  public static final VoxelShape NORTH_AABB = Block.box(5.0F, 6.0F, 14.0F, 11.0F, 10.0F, 16.0F);
  public static final VoxelShape SOUTH_AABB = Block.box(5.0F, 6.0F, 0.0F, 11.0F, 10.0F, 2.0F);
  public static final VoxelShape WEST_AABB = Block.box(14.0F, 6.0F, 5.0F, 16.0F, 10.0F, 11.0F);
  public static final VoxelShape EAST_AABB = Block.box(0.0F, 6.0F, 5.0F, 2.0F, 10.0F, 11.0F);
  public static final VoxelShape PRESSED_CEILING_AABB_X = Block.box(6.0F, 15.0F, 5.0F, 10.0F, 16.0F, 11.0F);
  public static final VoxelShape PRESSED_CEILING_AABB_Z = Block.box(5.0F, 15.0F, 6.0F, 11.0F, 16.0F, 10.0F);
  public static final VoxelShape PRESSED_FLOOR_AABB_X = Block.box(6.0F, 0.0F, 5.0F, 10.0F, 1.0F, 11.0F);
  public static final VoxelShape PRESSED_FLOOR_AABB_Z = Block.box(5.0F, 0.0F, 6.0F, 11.0F, 1.0F, 10.0F);
  public static final VoxelShape PRESSED_NORTH_AABB = Block.box(5.0F, 6.0F, 15.0F, 11.0F, 10.0F, 16.0F);
  public static final VoxelShape PRESSED_SOUTH_AABB = Block.box(5.0F, 6.0F, 0.0F, 11.0F, 10.0F, 1.0F);
  public static final VoxelShape PRESSED_WEST_AABB = Block.box(15.0F, 6.0F, 5.0F, 16.0F, 10.0F, 11.0F);
  public static final VoxelShape PRESSED_EAST_AABB = Block.box(0.0F, 6.0F, 5.0F, 1.0F, 10.0F, 11.0F);
}
