package io.github.jason13official.more_useful_copper.platform.services;

import java.nio.file.Path;
import java.util.function.BiFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public interface IPlatformHelper {

  /**
   * Gets the name of the current platform
   *
   * @return The name of the current platform.
   */
  String getPlatformName();

  /**
   * Checks if a mod with the given id is loaded.
   *
   * @param modId The mod to check if it is loaded.
   * @return True if the mod is loaded, false otherwise.
   */
  boolean isModLoaded(String modId);

  /**
   * Check if the game is currently in a development environment.
   *
   * @return True if in a development environment, false otherwise.
   */
  boolean isDevelopmentEnvironment();

  /**
   * Gets the name of the environment type as a string.
   *
   * @return The name of the environment type.
   */
  default String getEnvironmentName() {

    return isDevelopmentEnvironment() ? "development" : "production";
  }

  /**
   * Gets the root directory of the current environment as a path.
   *
   * @return The root directory for the current instance the mod is loaded in.
   */
  Path getGameDirectory();

  /**
   * Gets the config directory of the current environment as a path.
   *
   * @return The config directory for the current instance the mod is loaded in.
   */
  default Path getConfigDirectory() {

    return getGameDirectory().resolve("config");
  }

  boolean isClient();

  CreativeModeTab.Builder tabBuilder();

  /// Mimicking {@link BlockEntityType}'s private `BlockEntitySupplier`
  <T extends BlockEntity> BlockEntityType.Builder<T> tileBuilder(BiFunction<BlockPos, BlockState, T> constructor, Block... validBlocks);
}