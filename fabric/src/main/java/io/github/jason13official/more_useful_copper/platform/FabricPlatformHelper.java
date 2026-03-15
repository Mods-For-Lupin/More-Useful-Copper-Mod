package io.github.jason13official.more_useful_copper.platform;

import io.github.jason13official.more_useful_copper.platform.services.IPlatformHelper;
import java.nio.file.Path;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.CreativeModeTab.Builder;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.PressurePlateBlock.Sensitivity;
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

    return FabricItemGroup.builder();
  }

  @Override
  public <T extends BlockEntity> BlockEntityType.Builder<T> tileBuilder(BiFunction<BlockPos, BlockState, T> constructor, Block... validBlocks) {

    return BlockEntityType.Builder.of(constructor::apply, validBlocks);
  }

  @Override
  public PickaxeItem createPickaxeItem(Tier tier, float attackDamageMod, float attackSpeedMod, Properties properties) {
    return new PickaxeItem(tier, (int) attackDamageMod, attackSpeedMod, properties);
  }

  @Override
  public AxeItem createAxeItem(Tier tier, float attackDamageMod, float attackSpeedMod, Properties properties) {
    return new AxeItem(tier, attackDamageMod, attackSpeedMod, properties);
  }

  @Override
  public HoeItem createHoeItem(Tier tier, float attackDamageMod, float attackSpeedMod, Properties properties) {
    return new HoeItem(tier, (int) attackDamageMod, attackSpeedMod, properties);
  }

  @Override
  public SpawnEggItem createSpawnEggItem(Supplier<EntityType<? extends Mob>> entityTypeSupplier, int backgroundColor, int highlightColor, Properties properties) {
    return new SpawnEggItem(entityTypeSupplier.get(), backgroundColor, highlightColor, properties);
  }

  @Override
  public WeightedPressurePlateBlock createWeightedPressurePlateBlock(int maxWeight, BlockBehaviour.Properties properties, BlockSetType type) {
    return new WeightedPressurePlateBlock(maxWeight, properties, type);
  }

  @Override
  public PressurePlateBlock createPressurePlateBlock(Sensitivity sensitivity, BlockBehaviour.Properties properties, BlockSetType type) {
    return new PressurePlateBlock(sensitivity, properties, type);
  }
}
