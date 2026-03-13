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

  /// Gets the name of the current platform.
  ///
  /// @return the name of the current platform
  String getPlatformName();

  /// Checks if a mod with the given id is loaded.
  ///
  /// @param modId the mod id to check
  /// @return `true` if the mod is loaded, `false` otherwise
  boolean isModLoaded(String modId);

  /// Checks if the game is currently in a development environment.
  ///
  /// @return `true` if in a development environment, `false` otherwise
  boolean isDevelopmentEnvironment();

  /// Gets the name of the environment type as a string.
  ///
  /// @return `"development"` or `"production"`
  default String getEnvironmentName() {

    return isDevelopmentEnvironment() ? "development" : "production";
  }

  /// Gets the root directory of the current environment.
  ///
  /// @return the game directory path for the current instance
  Path getGameDirectory();

  /// Gets the config directory of the current environment.
  ///
  /// @return the config directory path for the current instance
  default Path getConfigDirectory() {

    return getGameDirectory().resolve("config");
  }

  boolean isClient();

  CreativeModeTab.Builder tabBuilder();

  /// Creates a [BlockEntityType.Builder] mimicking `BlockEntityType`'s private `BlockEntitySupplier`.
  ///
  /// @param <T>         the block entity type
  /// @param constructor factory function taking position and state
  /// @param validBlocks blocks this entity type is valid for
  /// @return a new [BlockEntityType.Builder]
  <T extends BlockEntity> BlockEntityType.Builder<T> tileBuilder(BiFunction<BlockPos, BlockState, T> constructor, Block... validBlocks);
}
