package io.github.jason13official.more_useful_copper.platform;

import io.github.jason13official.more_useful_copper.platform.services.IPlatformHelper;
import java.nio.file.Path;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.CreativeModeTab;
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
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.fml.loading.FMLLoader;

public class NeoForgePlatformHelper implements IPlatformHelper {

  @Override
  public String getPlatformName() {

    return "NeoForge";
  }

  @Override
  public boolean isModLoaded(String modId) {

    return ModList.get().isLoaded(modId);
  }

  @Override
  public boolean isDevelopmentEnvironment() {

    return !FMLEnvironment.isProduction();
  }

  @Override
  public Path getGameDirectory() {

    return FMLLoader.getCurrent().getGameDir();
  }

  @Override
  public boolean isClient() {

    return FMLEnvironment.getDist() == Dist.CLIENT;
  }

  @Override
  public Builder tabBuilder() {

    return CreativeModeTab.builder();
  }

  @Override
  public <T extends BlockEntity> BlockEntityType<T> tileBuilder(BiFunction<BlockPos, BlockState, T> constructor, Block... validBlocks) {

    return new BlockEntityType<>(constructor::apply, Set.of(validBlocks));
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
