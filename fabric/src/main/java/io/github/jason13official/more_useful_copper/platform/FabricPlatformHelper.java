package io.github.jason13official.more_useful_copper.platform;

import io.github.jason13official.more_useful_copper.platform.services.IPlatformHelper;
import java.nio.file.Path;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.CreativeModeTab.Builder;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.WeightedPressurePlateBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;

public class FabricPlatformHelper implements IPlatformHelper {

  @Override
  public String getPlatformName() {

    return "Fabric";
  }

  @Override
  public boolean isModLoaded(String modId) {

    return FabricLoader.getInstance().isModLoaded(modId);
  }

  @Override
  public boolean isDevelopmentEnvironment() {

    return FabricLoader.getInstance().isDevelopmentEnvironment();
  }

  @Override
  public Path getGameDirectory() {

    return FabricLoader.getInstance().getGameDir();
  }

  @Override
  public Path getConfigDirectory() {

    return FabricLoader.getInstance().getConfigDir();
  }

  @Override
  public boolean isClient() {

    return FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT;
  }

  @Override
  public Builder tabBuilder() {

    return FabricCreativeModeTab.builder();
  }

  @Override
  public <T extends BlockEntity> BlockEntityType<T> tileBuilder(BiFunction<BlockPos, BlockState, T> constructor, Block... validBlocks) {

    return FabricBlockEntityTypeBuilder.create(constructor::apply, validBlocks).build();
  }

  @Override
  public SpawnEggItem createSpawnEggItem(Supplier<EntityType<? extends Mob>> entityTypeSupplier, Properties properties) {
    return new SpawnEggItem(properties.spawnEgg(entityTypeSupplier.get()));
  }

  @Override
  public WeightedPressurePlateBlock createWeightedPressurePlateBlock(int maxWeight, BlockBehaviour.Properties properties, BlockSetType type) {
    return new WeightedPressurePlateBlock(maxWeight, type, properties);
  }

  @Override
  public PressurePlateBlock createPressurePlateBlock(BlockSetType type, BlockBehaviour.Properties properties) {
    return new PressurePlateBlock(type, properties);
  }
}
