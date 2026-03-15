package io.github.jason13official.more_useful_copper.platform.services;

import java.nio.file.Path;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WeightedPressurePlateBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;

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

  /// helper for private constructor
  PickaxeItem createPickaxeItem(Tier tier, float attackDamageMod, float attackSpeedMod, Properties properties);

  /// helper for private constructor
  AxeItem createAxeItem(Tier tier, float attackDamageMod, float attackSpeedMod, Properties properties);

  /// helper for private constructor
  HoeItem createHoeItem(Tier tier, float attackDamageMod, float attackSpeedMod, Properties properties);

  SpawnEggItem createSpawnEggItem(Supplier<EntityType<? extends Mob>> entityTypeSupplier, int backgroundColor, int highlightColor, Properties properties);

  WeightedPressurePlateBlock createWeightedPressurePlateBlock(int maxWeight, BlockBehaviour.Properties properties, BlockSetType type);
}
