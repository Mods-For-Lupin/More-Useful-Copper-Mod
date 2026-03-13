package io.github.jason13official.more_useful_copper.impl.common.registry;

import io.github.jason13official.more_useful_copper.MoreUsefulCopper;
import io.github.jason13official.more_useful_copper.impl.common.entity.CopperStatue.Type;
import io.github.jason13official.more_useful_copper.impl.common.item.CopperBottomBoatItem;
import io.github.jason13official.more_useful_copper.impl.common.item.CopperStatueItem;
import io.github.jason13official.more_useful_copper.impl.common.item.GardenStakeBlockItem;
import io.github.jason13official.more_useful_copper.impl.common.item.LightningBottleItem;
import io.github.jason13official.more_useful_copper.impl.common.item.MoistureCompassItem;
import io.github.jason13official.more_useful_copper.impl.common.item.SprayBottleItem;
import io.github.jason13official.more_useful_copper.impl.common.item.WaxScraperItem;
import java.util.function.BiConsumer;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.StandingAndWallBlockItem;

public class ModItems {

  public static Item WAX_SCRAPER;
  public static Item SPRAY_BOTTLE;
  public static Item COPPER_STATUE_CREEPER;
  public static Item COPPER_STATUE_SKELETON;
  public static Item COPPER_STATUE_SPIDER;
  public static Item COPPER_STATUE_ZOMBIE;
  public static Item COPPER_BOTTOM_BOAT;
  public static Item LIGHTNING_BOTTLE;
  public static Item GARDEN_STAKE;
  public static Item MOISTURE_COMPASS;
  public static Item SPARKSTONE_TORCH;
  public static Item SPARKSTONE_RELAY;

  public static void register(BiConsumer<Item, ResourceLocation> consumer) {
    WAX_SCRAPER = new WaxScraperItem(new Properties().durability(64));
    SPRAY_BOTTLE = new SprayBottleItem(new Properties().durability(64));
    consumer.accept(SPRAY_BOTTLE, MoreUsefulCopper.identifier("spray_bottle"));
    consumer.accept(WAX_SCRAPER, MoreUsefulCopper.identifier("wax_scraper"));

    LIGHTNING_BOTTLE = new LightningBottleItem(new Properties().stacksTo(16));
    GARDEN_STAKE = new GardenStakeBlockItem(ModBlocks.GARDEN_STAKE, new Properties());
    consumer.accept(LIGHTNING_BOTTLE, MoreUsefulCopper.identifier("lightning_bottle"));
    consumer.accept(GARDEN_STAKE, MoreUsefulCopper.identifier("garden_stake"));

    MOISTURE_COMPASS = new MoistureCompassItem(new Properties().stacksTo(1));
    consumer.accept(MOISTURE_COMPASS, MoreUsefulCopper.identifier("moisture_compass"));

    COPPER_BOTTOM_BOAT = new CopperBottomBoatItem(new Properties().stacksTo(1));
    consumer.accept(COPPER_BOTTOM_BOAT, MoreUsefulCopper.identifier("copper_bottom_boat"));

    COPPER_STATUE_CREEPER = new CopperStatueItem(Type.CREEPER, new Properties().stacksTo(16));
    COPPER_STATUE_SKELETON = new CopperStatueItem(Type.SKELETON, new Properties().stacksTo(16));
    COPPER_STATUE_SPIDER = new CopperStatueItem(Type.SPIDER, new Properties().stacksTo(16));
    COPPER_STATUE_ZOMBIE = new CopperStatueItem(Type.ZOMBIE, new Properties().stacksTo(16));
    consumer.accept(COPPER_STATUE_CREEPER, MoreUsefulCopper.identifier("copper_statue_creeper"));
    consumer.accept(COPPER_STATUE_SKELETON, MoreUsefulCopper.identifier("copper_statue_skeleton"));
    consumer.accept(COPPER_STATUE_SPIDER, MoreUsefulCopper.identifier("copper_statue_spider"));
    consumer.accept(COPPER_STATUE_ZOMBIE, MoreUsefulCopper.identifier("copper_statue_zombie"));

    SPARKSTONE_TORCH = new StandingAndWallBlockItem(ModBlocks.SPARKSTONE_TORCH, ModBlocks.SPARKSTONE_WALL_TORCH, new Properties(), Direction.DOWN);
    SPARKSTONE_RELAY = new BlockItem(ModBlocks.SPARKSTONE_RELAY, new Properties());
    consumer.accept(SPARKSTONE_TORCH, MoreUsefulCopper.identifier("sparkstone_torch"));
    consumer.accept(SPARKSTONE_RELAY, MoreUsefulCopper.identifier("sparkstone_relay"));

    consumer.accept(new BlockItem(ModBlocks.COPPER_BELL, new Properties()), MoreUsefulCopper.identifier("copper_bell"));

    consumer.accept(new BlockItem(ModBlocks.COPPER_BUTTON, new Properties()), MoreUsefulCopper.identifier("copper_button"));
    consumer.accept(new BlockItem(ModBlocks.EXPOSED_COPPER_BUTTON, new Properties()), MoreUsefulCopper.identifier("exposed_copper_button"));
    consumer.accept(new BlockItem(ModBlocks.WEATHERED_COPPER_BUTTON, new Properties()), MoreUsefulCopper.identifier("weathered_copper_button"));
    consumer.accept(new BlockItem(ModBlocks.OXIDIZED_COPPER_BUTTON, new Properties()), MoreUsefulCopper.identifier("oxidized_copper_button"));
    consumer.accept(new BlockItem(ModBlocks.WAXED_COPPER_BUTTON, new Properties()), MoreUsefulCopper.identifier("waxed_copper_button"));
    consumer.accept(new BlockItem(ModBlocks.WAXED_EXPOSED_COPPER_BUTTON, new Properties()), MoreUsefulCopper.identifier("waxed_exposed_copper_button"));
    consumer.accept(new BlockItem(ModBlocks.WAXED_WEATHERED_COPPER_BUTTON, new Properties()), MoreUsefulCopper.identifier("waxed_weathered_copper_button"));
    consumer.accept(new BlockItem(ModBlocks.WAXED_OXIDIZED_COPPER_BUTTON, new Properties()), MoreUsefulCopper.identifier("waxed_oxidized_copper_button"));

    consumer.accept(new BlockItem(ModBlocks.COPPER_LEVER, new Properties()), MoreUsefulCopper.identifier("copper_lever"));
    consumer.accept(new BlockItem(ModBlocks.EXPOSED_COPPER_LEVER, new Properties()), MoreUsefulCopper.identifier("exposed_copper_lever"));
    consumer.accept(new BlockItem(ModBlocks.WEATHERED_COPPER_LEVER, new Properties()), MoreUsefulCopper.identifier("weathered_copper_lever"));
    consumer.accept(new BlockItem(ModBlocks.OXIDIZED_COPPER_LEVER, new Properties()), MoreUsefulCopper.identifier("oxidized_copper_lever"));
    consumer.accept(new BlockItem(ModBlocks.WAXED_COPPER_LEVER, new Properties()), MoreUsefulCopper.identifier("waxed_copper_lever"));
    consumer.accept(new BlockItem(ModBlocks.WAXED_EXPOSED_COPPER_LEVER, new Properties()), MoreUsefulCopper.identifier("waxed_exposed_copper_lever"));
    consumer.accept(new BlockItem(ModBlocks.WAXED_WEATHERED_COPPER_LEVER, new Properties()), MoreUsefulCopper.identifier("waxed_weathered_copper_lever"));
    consumer.accept(new BlockItem(ModBlocks.WAXED_OXIDIZED_COPPER_LEVER, new Properties()), MoreUsefulCopper.identifier("waxed_oxidized_copper_lever"));

    consumer.accept(new BlockItem(ModBlocks.COPPER_COMPARATOR, new Properties()), MoreUsefulCopper.identifier("copper_comparator"));
    consumer.accept(new BlockItem(ModBlocks.EXPOSED_COPPER_COMPARATOR, new Properties()), MoreUsefulCopper.identifier("exposed_copper_comparator"));
    consumer.accept(new BlockItem(ModBlocks.WEATHERED_COPPER_COMPARATOR, new Properties()), MoreUsefulCopper.identifier("weathered_copper_comparator"));
    consumer.accept(new BlockItem(ModBlocks.OXIDIZED_COPPER_COMPARATOR, new Properties()), MoreUsefulCopper.identifier("oxidized_copper_comparator"));
    consumer.accept(new BlockItem(ModBlocks.WAXED_COPPER_COMPARATOR, new Properties()), MoreUsefulCopper.identifier("waxed_copper_comparator"));
    consumer.accept(new BlockItem(ModBlocks.WAXED_EXPOSED_COPPER_COMPARATOR, new Properties()), MoreUsefulCopper.identifier("waxed_exposed_copper_comparator"));
    consumer.accept(new BlockItem(ModBlocks.WAXED_WEATHERED_COPPER_COMPARATOR, new Properties()), MoreUsefulCopper.identifier("waxed_weathered_copper_comparator"));
    consumer.accept(new BlockItem(ModBlocks.WAXED_OXIDIZED_COPPER_COMPARATOR, new Properties()), MoreUsefulCopper.identifier("waxed_oxidized_copper_comparator"));

    consumer.accept(new BlockItem(ModBlocks.COPPER_REDSTONE_DUST, new Properties()), MoreUsefulCopper.identifier("copper_redstone_dust"));
    consumer.accept(new BlockItem(ModBlocks.EXPOSED_COPPER_REDSTONE_DUST, new Properties()), MoreUsefulCopper.identifier("exposed_copper_redstone_dust"));
    consumer.accept(new BlockItem(ModBlocks.WEATHERED_COPPER_REDSTONE_DUST, new Properties()), MoreUsefulCopper.identifier("weathered_copper_redstone_dust"));
    consumer.accept(new BlockItem(ModBlocks.OXIDIZED_COPPER_REDSTONE_DUST, new Properties()), MoreUsefulCopper.identifier("oxidized_copper_redstone_dust"));
    consumer.accept(new BlockItem(ModBlocks.WAXED_COPPER_REDSTONE_DUST, new Properties()), MoreUsefulCopper.identifier("waxed_copper_redstone_dust"));
    consumer.accept(new BlockItem(ModBlocks.WAXED_EXPOSED_COPPER_REDSTONE_DUST, new Properties()), MoreUsefulCopper.identifier("waxed_exposed_copper_redstone_dust"));
    consumer.accept(new BlockItem(ModBlocks.WAXED_WEATHERED_COPPER_REDSTONE_DUST, new Properties()), MoreUsefulCopper.identifier("waxed_weathered_copper_redstone_dust"));
    consumer.accept(new BlockItem(ModBlocks.WAXED_OXIDIZED_COPPER_REDSTONE_DUST, new Properties()), MoreUsefulCopper.identifier("waxed_oxidized_copper_redstone_dust"));

    // Torch items use StandingAndWallBlockItem so one item places both floor and wall variants
    consumer.accept(new StandingAndWallBlockItem(ModBlocks.COPPER_REDSTONE_TORCH, ModBlocks.COPPER_WALL_REDSTONE_TORCH, new Properties(), Direction.DOWN), MoreUsefulCopper.identifier("copper_redstone_torch"));
    consumer.accept(new StandingAndWallBlockItem(ModBlocks.EXPOSED_COPPER_REDSTONE_TORCH, ModBlocks.EXPOSED_COPPER_WALL_REDSTONE_TORCH, new Properties(), Direction.DOWN), MoreUsefulCopper.identifier("exposed_copper_redstone_torch"));
    consumer.accept(new StandingAndWallBlockItem(ModBlocks.WEATHERED_COPPER_REDSTONE_TORCH, ModBlocks.WEATHERED_COPPER_WALL_REDSTONE_TORCH, new Properties(), Direction.DOWN), MoreUsefulCopper.identifier("weathered_copper_redstone_torch"));
    consumer.accept(new StandingAndWallBlockItem(ModBlocks.OXIDIZED_COPPER_REDSTONE_TORCH, ModBlocks.OXIDIZED_COPPER_WALL_REDSTONE_TORCH, new Properties(), Direction.DOWN), MoreUsefulCopper.identifier("oxidized_copper_redstone_torch"));
    consumer.accept(new StandingAndWallBlockItem(ModBlocks.WAXED_COPPER_REDSTONE_TORCH, ModBlocks.WAXED_COPPER_WALL_REDSTONE_TORCH, new Properties(), Direction.DOWN), MoreUsefulCopper.identifier("waxed_copper_redstone_torch"));
    consumer.accept(new StandingAndWallBlockItem(ModBlocks.WAXED_EXPOSED_COPPER_REDSTONE_TORCH, ModBlocks.WAXED_EXPOSED_COPPER_WALL_REDSTONE_TORCH, new Properties(), Direction.DOWN), MoreUsefulCopper.identifier("waxed_exposed_copper_redstone_torch"));
    consumer.accept(new StandingAndWallBlockItem(ModBlocks.WAXED_WEATHERED_COPPER_REDSTONE_TORCH, ModBlocks.WAXED_WEATHERED_COPPER_WALL_REDSTONE_TORCH, new Properties(), Direction.DOWN), MoreUsefulCopper.identifier("waxed_weathered_copper_redstone_torch"));
    consumer.accept(new StandingAndWallBlockItem(ModBlocks.WAXED_OXIDIZED_COPPER_REDSTONE_TORCH, ModBlocks.WAXED_OXIDIZED_COPPER_WALL_REDSTONE_TORCH, new Properties(), Direction.DOWN), MoreUsefulCopper.identifier("waxed_oxidized_copper_redstone_torch"));

    consumer.accept(new BlockItem(ModBlocks.COPPER_REPEATER, new Properties()), MoreUsefulCopper.identifier("copper_repeater"));
    consumer.accept(new BlockItem(ModBlocks.EXPOSED_COPPER_REPEATER, new Properties()), MoreUsefulCopper.identifier("exposed_copper_repeater"));
    consumer.accept(new BlockItem(ModBlocks.WEATHERED_COPPER_REPEATER, new Properties()), MoreUsefulCopper.identifier("weathered_copper_repeater"));
    consumer.accept(new BlockItem(ModBlocks.OXIDIZED_COPPER_REPEATER, new Properties()), MoreUsefulCopper.identifier("oxidized_copper_repeater"));
    consumer.accept(new BlockItem(ModBlocks.WAXED_COPPER_REPEATER, new Properties()), MoreUsefulCopper.identifier("waxed_copper_repeater"));
    consumer.accept(new BlockItem(ModBlocks.WAXED_EXPOSED_COPPER_REPEATER, new Properties()), MoreUsefulCopper.identifier("waxed_exposed_copper_repeater"));
    consumer.accept(new BlockItem(ModBlocks.WAXED_WEATHERED_COPPER_REPEATER, new Properties()), MoreUsefulCopper.identifier("waxed_weathered_copper_repeater"));
    consumer.accept(new BlockItem(ModBlocks.WAXED_OXIDIZED_COPPER_REPEATER, new Properties()), MoreUsefulCopper.identifier("waxed_oxidized_copper_repeater"));
  }
}
