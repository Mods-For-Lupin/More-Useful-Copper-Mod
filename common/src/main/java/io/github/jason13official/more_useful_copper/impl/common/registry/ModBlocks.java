package io.github.jason13official.more_useful_copper.impl.common.registry;

import io.github.jason13official.more_useful_copper.MoreUsefulCopper;
import io.github.jason13official.more_useful_copper.impl.common.block.BasicButtonBlock;
import io.github.jason13official.more_useful_copper.impl.common.block.CopperButtonBlock;
import java.util.function.BiConsumer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.WeatheringCopper.WeatherState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.material.PushReaction;

public class ModBlocks {

  public static Block COPPER_BUTTON;
  public static Block EXPOSED_COPPER_BUTTON;
  public static Block WEATHERED_COPPER_BUTTON;
  public static Block OXIDIZED_COPPER_BUTTON;

  public static Block WAXED_COPPER_BUTTON;
  public static Block WAXED_EXPOSED_COPPER_BUTTON;
  public static Block WAXED_WEATHERED_COPPER_BUTTON;
  public static Block WAXED_OXIDIZED_COPPER_BUTTON;

  public static void register(BiConsumer<Block, ResourceLocation> consumer) {

    COPPER_BUTTON = weatheringCopperButton(WeatherState.UNAFFECTED);
    EXPOSED_COPPER_BUTTON = weatheringCopperButton(WeatherState.EXPOSED);
    WEATHERED_COPPER_BUTTON = weatheringCopperButton(WeatherState.WEATHERED);
    OXIDIZED_COPPER_BUTTON = weatheringCopperButton(WeatherState.OXIDIZED);

    WAXED_COPPER_BUTTON = waxedCopperButton();
    WAXED_EXPOSED_COPPER_BUTTON = waxedCopperButton();
    WAXED_WEATHERED_COPPER_BUTTON = waxedCopperButton();
    WAXED_OXIDIZED_COPPER_BUTTON = waxedCopperButton();

    consumer.accept(COPPER_BUTTON, MoreUsefulCopper.identifier("copper_button"));
    consumer.accept(EXPOSED_COPPER_BUTTON, MoreUsefulCopper.identifier("exposed_copper_button"));
    consumer.accept(WEATHERED_COPPER_BUTTON, MoreUsefulCopper.identifier("weathered_copper_button"));
    consumer.accept(OXIDIZED_COPPER_BUTTON, MoreUsefulCopper.identifier("oxidized_copper_button"));
    consumer.accept(WAXED_COPPER_BUTTON, MoreUsefulCopper.identifier("waxed_copper_button"));
    consumer.accept(WAXED_EXPOSED_COPPER_BUTTON, MoreUsefulCopper.identifier("waxed_exposed_copper_button"));
    consumer.accept(WAXED_WEATHERED_COPPER_BUTTON, MoreUsefulCopper.identifier("waxed_weathered_copper_button"));
    consumer.accept(WAXED_OXIDIZED_COPPER_BUTTON, MoreUsefulCopper.identifier("waxed_oxidized_copper_button"));
  }

  private static CopperButtonBlock weatheringCopperButton(WeatherState weatherState) {
    return new CopperButtonBlock(Properties.of().noCollission().strength(0.5F).pushReaction(PushReaction.DESTROY), weatherState, BlockSetType.STONE, 30, true);
  }

  private static ButtonBlock waxedCopperButton() {
    return new BasicButtonBlock(Properties.of().noCollission().strength(0.5F).pushReaction(PushReaction.DESTROY), BlockSetType.STONE, 30, true);
  }
}
