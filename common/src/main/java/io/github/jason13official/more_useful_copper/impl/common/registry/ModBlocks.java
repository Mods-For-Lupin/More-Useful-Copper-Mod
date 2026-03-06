package io.github.jason13official.more_useful_copper.impl.common.registry;

import io.github.jason13official.more_useful_copper.MoreUsefulCopper;
import io.github.jason13official.more_useful_copper.impl.common.block.WaxedButtonBlock;
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

    COPPER_BUTTON = weatheringCopperButton(WeatherState.UNAFFECTED, CopperButtonBlock.UNAFFECTED_PRESSED_TICKS);
    EXPOSED_COPPER_BUTTON = weatheringCopperButton(WeatherState.EXPOSED, CopperButtonBlock.EXPOSED_PRESSED_TICKS);
    WEATHERED_COPPER_BUTTON = weatheringCopperButton(WeatherState.WEATHERED, CopperButtonBlock.WEATHERED_PRESSED_TICKS);
    OXIDIZED_COPPER_BUTTON = weatheringCopperButton(WeatherState.OXIDIZED, CopperButtonBlock.OXIDIZED_PRESSED_TICKS);

    WAXED_COPPER_BUTTON = waxedCopperButton(CopperButtonBlock.UNAFFECTED_PRESSED_TICKS);
    WAXED_EXPOSED_COPPER_BUTTON = waxedCopperButton(CopperButtonBlock.EXPOSED_PRESSED_TICKS);
    WAXED_WEATHERED_COPPER_BUTTON = waxedCopperButton(CopperButtonBlock.WEATHERED_PRESSED_TICKS);
    WAXED_OXIDIZED_COPPER_BUTTON = waxedCopperButton(CopperButtonBlock.OXIDIZED_PRESSED_TICKS);

    consumer.accept(COPPER_BUTTON, MoreUsefulCopper.identifier("copper_button"));
    consumer.accept(EXPOSED_COPPER_BUTTON, MoreUsefulCopper.identifier("exposed_copper_button"));
    consumer.accept(WEATHERED_COPPER_BUTTON, MoreUsefulCopper.identifier("weathered_copper_button"));
    consumer.accept(OXIDIZED_COPPER_BUTTON, MoreUsefulCopper.identifier("oxidized_copper_button"));
    consumer.accept(WAXED_COPPER_BUTTON, MoreUsefulCopper.identifier("waxed_copper_button"));
    consumer.accept(WAXED_EXPOSED_COPPER_BUTTON, MoreUsefulCopper.identifier("waxed_exposed_copper_button"));
    consumer.accept(WAXED_WEATHERED_COPPER_BUTTON, MoreUsefulCopper.identifier("waxed_weathered_copper_button"));
    consumer.accept(WAXED_OXIDIZED_COPPER_BUTTON, MoreUsefulCopper.identifier("waxed_oxidized_copper_button"));
  }

  private static CopperButtonBlock weatheringCopperButton(WeatherState weatherState, int ticksToStayPressed) {
    return new CopperButtonBlock(Properties.of().noCollission().strength(0.5F).pushReaction(PushReaction.DESTROY), weatherState, BlockSetType.STONE, ticksToStayPressed, true);
  }

  private static ButtonBlock waxedCopperButton(int ticksToStayPressed) {
    return new WaxedButtonBlock(Properties.of().noCollission().strength(0.5F).pushReaction(PushReaction.DESTROY), BlockSetType.STONE, ticksToStayPressed, true);
  }
}
