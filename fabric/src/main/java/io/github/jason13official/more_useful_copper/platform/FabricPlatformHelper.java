package io.github.jason13official.more_useful_copper.platform;

import io.github.jason13official.more_useful_copper.platform.services.IPlatformHelper;
import java.nio.file.Path;
import java.util.function.BiFunction;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.CreativeModeTab.Builder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

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

    return FabricItemGroup.builder();
  }

  @Override
  public <T extends BlockEntity> BlockEntityType.Builder<T> tileBuilder(BiFunction<BlockPos, BlockState, T> constructor, Block... validBlocks) {

    return BlockEntityType.Builder.of(constructor::apply, validBlocks);
  }
}
