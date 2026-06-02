package io.github.jason13official.more_useful_copper.impl.common.registry;

import io.github.jason13official.more_useful_copper.MoreUsefulCopper;
import io.github.jason13official.more_useful_copper.api.common.item.ModArmorMaterials;
import io.github.jason13official.more_useful_copper.api.common.item.ModTiers;
import io.github.jason13official.more_useful_copper.impl.common.entity.CopperStatue;
import io.github.jason13official.more_useful_copper.impl.common.item.CopperBottomBoatItem;
import io.github.jason13official.more_useful_copper.impl.common.item.CopperStatueItem;
import io.github.jason13official.more_useful_copper.impl.common.item.GardenStakeBlockItem;
import io.github.jason13official.more_useful_copper.impl.common.item.LightningArmorItem;
import io.github.jason13official.more_useful_copper.impl.common.item.LightningBottleItem;
import io.github.jason13official.more_useful_copper.impl.common.item.MoistureCompassItem;
import io.github.jason13official.more_useful_copper.impl.common.item.SprayBottleItem;
import io.github.jason13official.more_useful_copper.impl.common.item.WaxScraperItem;
import io.github.jason13official.more_useful_copper.impl.common.item.tool.LightningAxeItem;
import io.github.jason13official.more_useful_copper.impl.common.item.tool.LightningHoeItem;
import io.github.jason13official.more_useful_copper.impl.common.item.tool.LightningPickaxeItem;
import io.github.jason13official.more_useful_copper.impl.common.item.tool.LightningShovelItem;
import io.github.jason13official.more_useful_copper.impl.common.item.tool.LightningSwordItem;
import io.github.jason13official.more_useful_copper.platform.Services;
import java.util.function.BiConsumer;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.item.equipment.ArmorType;

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

  // public static Item COPPER_NUGGET;
  public static Item COPPER_SHEARS;

  // public static Item COPPER_PICKAXE;
  // public static Item COPPER_AXE;
  // public static Item COPPER_HOE;
  // public static Item COPPER_SHOVEL;
  // public static Item COPPER_SWORD;

  // public static Item COPPER_HELMET;
  // public static Item COPPER_CHESTPLATE;
  // public static Item COPPER_LEGGINGS;
  // public static Item COPPER_BOOTS;

  // public static Item COPPER_CHAIN;
  // public static Item COPPER_GOLEM_SPAWN_EGG;
  // public static Item COPPER_HORSE_ARMOR;

  public static Item COPPER_PRESSURE_PLATE;

  public static void register(BiConsumer<Item, Identifier> consumer) {

    registerLegacyItems(consumer);

    WAX_SCRAPER = new WaxScraperItem(new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("wax_scraper"))).durability(64));
    SPRAY_BOTTLE = new SprayBottleItem(new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("spray_bottle"))).durability(64));
    consumer.accept(SPRAY_BOTTLE, MoreUsefulCopper.identifier("spray_bottle"));
    consumer.accept(WAX_SCRAPER, MoreUsefulCopper.identifier("wax_scraper"));

    LIGHTNING_BOTTLE = new LightningBottleItem(new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("lightning_bottle"))).stacksTo(16));
    GARDEN_STAKE = new GardenStakeBlockItem(ModBlocks.GARDEN_STAKE, new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("garden_stake"))));
    consumer.accept(LIGHTNING_BOTTLE, MoreUsefulCopper.identifier("lightning_bottle"));
    consumer.accept(GARDEN_STAKE, MoreUsefulCopper.identifier("garden_stake"));

    MOISTURE_COMPASS = new MoistureCompassItem(new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("moisture_compass"))).stacksTo(1));
    consumer.accept(MOISTURE_COMPASS, MoreUsefulCopper.identifier("moisture_compass"));

    COPPER_BOTTOM_BOAT = new CopperBottomBoatItem(new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("copper_bottom_boat"))).stacksTo(1));
    consumer.accept(COPPER_BOTTOM_BOAT, MoreUsefulCopper.identifier("copper_bottom_boat"));

    COPPER_STATUE_CREEPER = new CopperStatueItem(CopperStatue.Type.CREEPER, new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("copper_statue_creeper"))).stacksTo(16));
    COPPER_STATUE_SKELETON = new CopperStatueItem(CopperStatue.Type.SKELETON, new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("copper_statue_skeleton"))).stacksTo(16));
    COPPER_STATUE_SPIDER = new CopperStatueItem(CopperStatue.Type.SPIDER, new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("copper_statue_spider"))).stacksTo(16));
    COPPER_STATUE_ZOMBIE = new CopperStatueItem(CopperStatue.Type.ZOMBIE, new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("copper_statue_zombie"))).stacksTo(16));
    consumer.accept(COPPER_STATUE_CREEPER, MoreUsefulCopper.identifier("copper_statue_creeper"));
    consumer.accept(COPPER_STATUE_SKELETON, MoreUsefulCopper.identifier("copper_statue_skeleton"));
    consumer.accept(COPPER_STATUE_SPIDER, MoreUsefulCopper.identifier("copper_statue_spider"));
    consumer.accept(COPPER_STATUE_ZOMBIE, MoreUsefulCopper.identifier("copper_statue_zombie"));

    SPARKSTONE_TORCH = new StandingAndWallBlockItem(ModBlocks.SPARKSTONE_TORCH, ModBlocks.SPARKSTONE_WALL_TORCH, Direction.DOWN, new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("sparkstone_torch"))));
    SPARKSTONE_RELAY = new BlockItem(ModBlocks.SPARKSTONE_RELAY, new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("sparkstone_relay"))));
    consumer.accept(SPARKSTONE_TORCH, MoreUsefulCopper.identifier("sparkstone_torch"));
    consumer.accept(SPARKSTONE_RELAY, MoreUsefulCopper.identifier("sparkstone_relay"));

    consumer.accept(new BlockItem(ModBlocks.COPPER_BELL, new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("copper_bell")))), MoreUsefulCopper.identifier("copper_bell"));

    consumer.accept(new BlockItem(ModBlocks.COPPER_BUTTON, new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("copper_button")))), MoreUsefulCopper.identifier("copper_button"));
    consumer.accept(new BlockItem(ModBlocks.EXPOSED_COPPER_BUTTON, new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("exposed_copper_button")))), MoreUsefulCopper.identifier("exposed_copper_button"));
    consumer.accept(new BlockItem(ModBlocks.WEATHERED_COPPER_BUTTON, new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("weathered_copper_button")))), MoreUsefulCopper.identifier("weathered_copper_button"));
    consumer.accept(new BlockItem(ModBlocks.OXIDIZED_COPPER_BUTTON, new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("oxidized_copper_button")))), MoreUsefulCopper.identifier("oxidized_copper_button"));
    consumer.accept(new BlockItem(ModBlocks.WAXED_COPPER_BUTTON, new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("waxed_copper_button")))), MoreUsefulCopper.identifier("waxed_copper_button"));
    consumer.accept(new BlockItem(ModBlocks.WAXED_EXPOSED_COPPER_BUTTON, new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("waxed_exposed_copper_button")))), MoreUsefulCopper.identifier("waxed_exposed_copper_button"));
    consumer.accept(new BlockItem(ModBlocks.WAXED_WEATHERED_COPPER_BUTTON, new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("waxed_weathered_copper_button")))), MoreUsefulCopper.identifier("waxed_weathered_copper_button"));
    consumer.accept(new BlockItem(ModBlocks.WAXED_OXIDIZED_COPPER_BUTTON, new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("waxed_oxidized_copper_button")))), MoreUsefulCopper.identifier("waxed_oxidized_copper_button"));

    consumer.accept(new BlockItem(ModBlocks.COPPER_LEVER, new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("copper_lever")))), MoreUsefulCopper.identifier("copper_lever"));
    consumer.accept(new BlockItem(ModBlocks.EXPOSED_COPPER_LEVER, new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("exposed_copper_lever")))), MoreUsefulCopper.identifier("exposed_copper_lever"));
    consumer.accept(new BlockItem(ModBlocks.WEATHERED_COPPER_LEVER, new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("weathered_copper_lever")))), MoreUsefulCopper.identifier("weathered_copper_lever"));
    consumer.accept(new BlockItem(ModBlocks.OXIDIZED_COPPER_LEVER, new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("oxidized_copper_lever")))), MoreUsefulCopper.identifier("oxidized_copper_lever"));
    consumer.accept(new BlockItem(ModBlocks.WAXED_COPPER_LEVER, new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("waxed_copper_lever")))), MoreUsefulCopper.identifier("waxed_copper_lever"));
    consumer.accept(new BlockItem(ModBlocks.WAXED_EXPOSED_COPPER_LEVER, new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("waxed_exposed_copper_lever")))), MoreUsefulCopper.identifier("waxed_exposed_copper_lever"));
    consumer.accept(new BlockItem(ModBlocks.WAXED_WEATHERED_COPPER_LEVER, new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("waxed_weathered_copper_lever")))), MoreUsefulCopper.identifier("waxed_weathered_copper_lever"));
    consumer.accept(new BlockItem(ModBlocks.WAXED_OXIDIZED_COPPER_LEVER, new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("waxed_oxidized_copper_lever")))), MoreUsefulCopper.identifier("waxed_oxidized_copper_lever"));

    consumer.accept(new BlockItem(ModBlocks.COPPER_COMPARATOR, new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("copper_comparator")))), MoreUsefulCopper.identifier("copper_comparator"));
    consumer.accept(new BlockItem(ModBlocks.EXPOSED_COPPER_COMPARATOR, new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("exposed_copper_comparator")))), MoreUsefulCopper.identifier("exposed_copper_comparator"));
    consumer.accept(new BlockItem(ModBlocks.WEATHERED_COPPER_COMPARATOR, new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("weathered_copper_comparator")))), MoreUsefulCopper.identifier("weathered_copper_comparator"));
    consumer.accept(new BlockItem(ModBlocks.OXIDIZED_COPPER_COMPARATOR, new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("oxidized_copper_comparator")))), MoreUsefulCopper.identifier("oxidized_copper_comparator"));
    consumer.accept(new BlockItem(ModBlocks.WAXED_COPPER_COMPARATOR, new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("waxed_copper_comparator")))), MoreUsefulCopper.identifier("waxed_copper_comparator"));
    consumer.accept(new BlockItem(ModBlocks.WAXED_EXPOSED_COPPER_COMPARATOR, new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("waxed_exposed_copper_comparator")))), MoreUsefulCopper.identifier("waxed_exposed_copper_comparator"));
    consumer.accept(new BlockItem(ModBlocks.WAXED_WEATHERED_COPPER_COMPARATOR, new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("waxed_weathered_copper_comparator")))), MoreUsefulCopper.identifier("waxed_weathered_copper_comparator"));
    consumer.accept(new BlockItem(ModBlocks.WAXED_OXIDIZED_COPPER_COMPARATOR, new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("waxed_oxidized_copper_comparator")))), MoreUsefulCopper.identifier("waxed_oxidized_copper_comparator"));

    consumer.accept(new BlockItem(ModBlocks.COPPER_REDSTONE_DUST, new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("copper_redstone_dust")))), MoreUsefulCopper.identifier("copper_redstone_dust"));
    consumer.accept(new BlockItem(ModBlocks.EXPOSED_COPPER_REDSTONE_DUST, new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("exposed_copper_redstone_dust")))), MoreUsefulCopper.identifier("exposed_copper_redstone_dust"));
    consumer.accept(new BlockItem(ModBlocks.WEATHERED_COPPER_REDSTONE_DUST, new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("weathered_copper_redstone_dust")))), MoreUsefulCopper.identifier("weathered_copper_redstone_dust"));
    consumer.accept(new BlockItem(ModBlocks.OXIDIZED_COPPER_REDSTONE_DUST, new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("oxidized_copper_redstone_dust")))), MoreUsefulCopper.identifier("oxidized_copper_redstone_dust"));
    consumer.accept(new BlockItem(ModBlocks.WAXED_COPPER_REDSTONE_DUST, new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("waxed_copper_redstone_dust")))), MoreUsefulCopper.identifier("waxed_copper_redstone_dust"));
    consumer.accept(new BlockItem(ModBlocks.WAXED_EXPOSED_COPPER_REDSTONE_DUST, new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("waxed_exposed_copper_redstone_dust")))), MoreUsefulCopper.identifier("waxed_exposed_copper_redstone_dust"));
    consumer.accept(new BlockItem(ModBlocks.WAXED_WEATHERED_COPPER_REDSTONE_DUST, new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("waxed_weathered_copper_redstone_dust")))), MoreUsefulCopper.identifier("waxed_weathered_copper_redstone_dust"));
    consumer.accept(new BlockItem(ModBlocks.WAXED_OXIDIZED_COPPER_REDSTONE_DUST, new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("waxed_oxidized_copper_redstone_dust")))), MoreUsefulCopper.identifier("waxed_oxidized_copper_redstone_dust"));

    // Torch items use StandingAndWallBlockItem so one item places both floor and wall variants
    consumer.accept(new StandingAndWallBlockItem(ModBlocks.COPPER_REDSTONE_TORCH, ModBlocks.COPPER_WALL_REDSTONE_TORCH, Direction.DOWN, new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("copper_redstone_torch")))),
        MoreUsefulCopper.identifier("copper_redstone_torch"));
    consumer.accept(new StandingAndWallBlockItem(ModBlocks.EXPOSED_COPPER_REDSTONE_TORCH, ModBlocks.EXPOSED_COPPER_WALL_REDSTONE_TORCH, Direction.DOWN, new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("exposed_copper_redstone_torch")))),
        MoreUsefulCopper.identifier("exposed_copper_redstone_torch"));
    consumer.accept(new StandingAndWallBlockItem(ModBlocks.WEATHERED_COPPER_REDSTONE_TORCH, ModBlocks.WEATHERED_COPPER_WALL_REDSTONE_TORCH, Direction.DOWN, new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("weathered_copper_redstone_torch")))),
        MoreUsefulCopper.identifier("weathered_copper_redstone_torch"));
    consumer.accept(new StandingAndWallBlockItem(ModBlocks.OXIDIZED_COPPER_REDSTONE_TORCH, ModBlocks.OXIDIZED_COPPER_WALL_REDSTONE_TORCH, Direction.DOWN, new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("oxidized_copper_redstone_torch")))),
        MoreUsefulCopper.identifier("oxidized_copper_redstone_torch"));
    consumer.accept(new StandingAndWallBlockItem(ModBlocks.WAXED_COPPER_REDSTONE_TORCH, ModBlocks.WAXED_COPPER_WALL_REDSTONE_TORCH, Direction.DOWN, new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("waxed_copper_redstone_torch")))),
        MoreUsefulCopper.identifier("waxed_copper_redstone_torch"));
    consumer.accept(new StandingAndWallBlockItem(ModBlocks.WAXED_EXPOSED_COPPER_REDSTONE_TORCH, ModBlocks.WAXED_EXPOSED_COPPER_WALL_REDSTONE_TORCH, Direction.DOWN, new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("waxed_exposed_copper_redstone_torch")))),
        MoreUsefulCopper.identifier("waxed_exposed_copper_redstone_torch"));
    consumer.accept(new StandingAndWallBlockItem(ModBlocks.WAXED_WEATHERED_COPPER_REDSTONE_TORCH, ModBlocks.WAXED_WEATHERED_COPPER_WALL_REDSTONE_TORCH, Direction.DOWN, new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("waxed_weathered_copper_redstone_torch")))),
        MoreUsefulCopper.identifier("waxed_weathered_copper_redstone_torch"));
    consumer.accept(new StandingAndWallBlockItem(ModBlocks.WAXED_OXIDIZED_COPPER_REDSTONE_TORCH, ModBlocks.WAXED_OXIDIZED_COPPER_WALL_REDSTONE_TORCH, Direction.DOWN, new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("waxed_oxidized_copper_redstone_torch")))),
        MoreUsefulCopper.identifier("waxed_oxidized_copper_redstone_torch"));

    consumer.accept(new BlockItem(ModBlocks.COPPER_REPEATER, new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("copper_repeater")))), MoreUsefulCopper.identifier("copper_repeater"));
    consumer.accept(new BlockItem(ModBlocks.EXPOSED_COPPER_REPEATER, new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("exposed_copper_repeater")))), MoreUsefulCopper.identifier("exposed_copper_repeater"));
    consumer.accept(new BlockItem(ModBlocks.WEATHERED_COPPER_REPEATER, new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("weathered_copper_repeater")))), MoreUsefulCopper.identifier("weathered_copper_repeater"));
    consumer.accept(new BlockItem(ModBlocks.OXIDIZED_COPPER_REPEATER, new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("oxidized_copper_repeater")))), MoreUsefulCopper.identifier("oxidized_copper_repeater"));
    consumer.accept(new BlockItem(ModBlocks.WAXED_COPPER_REPEATER, new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("waxed_copper_repeater")))), MoreUsefulCopper.identifier("waxed_copper_repeater"));
    consumer.accept(new BlockItem(ModBlocks.WAXED_EXPOSED_COPPER_REPEATER, new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("waxed_exposed_copper_repeater")))), MoreUsefulCopper.identifier("waxed_exposed_copper_repeater"));
    consumer.accept(new BlockItem(ModBlocks.WAXED_WEATHERED_COPPER_REPEATER, new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("waxed_weathered_copper_repeater")))), MoreUsefulCopper.identifier("waxed_weathered_copper_repeater"));
    consumer.accept(new BlockItem(ModBlocks.WAXED_OXIDIZED_COPPER_REPEATER, new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("waxed_oxidized_copper_repeater")))), MoreUsefulCopper.identifier("waxed_oxidized_copper_repeater"));
  }

  private static void registerLegacyItems(BiConsumer<Item, Identifier> consumer) {

    // COPPER_NUGGET = new Item(new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("copper_nugget"))));
    COPPER_SHEARS = new ShearsItem((new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("copper_shears")))).durability(238));

//    COPPER_PICKAXE = new LightningPickaxeItem(new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("copper_pickaxe"))).pickaxe(ModTiers.COPPER, 1.0F, -2.8F));
//    COPPER_AXE = new LightningAxeItem(ModTiers.COPPER, 6.0F, -3.0F, new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("copper_axe"))));
//    COPPER_HOE = new LightningHoeItem(ModTiers.COPPER, 0.0F, -3.0F, new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("copper_hoe"))));
//    COPPER_SHOVEL = new LightningShovelItem(ModTiers.COPPER, 1.5F, -3.0F, new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("copper_shovel"))));
//    COPPER_SWORD = new LightningSwordItem(new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("copper_sword"))).sword(ModTiers.COPPER, 3.0F, -2.4F));
//
//    COPPER_HELMET = new LightningArmorItem(new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("copper_helmet"))).humanoidArmor(ModArmorMaterials.COPPER, ArmorType.HELMET));
//    COPPER_CHESTPLATE = new LightningArmorItem(new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("copper_chestplate"))).humanoidArmor(ModArmorMaterials.COPPER, ArmorType.CHESTPLATE));
//    COPPER_LEGGINGS = new LightningArmorItem(new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("copper_leggings"))).humanoidArmor(ModArmorMaterials.COPPER, ArmorType.LEGGINGS));
//    COPPER_BOOTS = new LightningArmorItem(new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("copper_boots"))).humanoidArmor(ModArmorMaterials.COPPER, ArmorType.BOOTS));
//
//    COPPER_CHAIN = new BlockItem(ModBlocks.COPPER_CHAIN, new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("copper_chain"))));
//    COPPER_GOLEM_SPAWN_EGG = Services.PLATFORM.createSpawnEggItem(() -> ModEntities.COPPER_GOLEM, new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("copper_golem_spawn_egg"))));
//    COPPER_HORSE_ARMOR = new Item(new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("copper_horse_armor"))).horseArmor(ModArmorMaterials.COPPER));

    COPPER_PRESSURE_PLATE = new BlockItem(ModBlocks.COPPER_PRESSURE_PLATE, new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("copper_pressure_plate"))));

//    consumer.accept(COPPER_NUGGET, MoreUsefulCopper.identifier("copper_nugget"));
    consumer.accept(COPPER_SHEARS, MoreUsefulCopper.identifier("copper_shears"));
//    consumer.accept(COPPER_PICKAXE, MoreUsefulCopper.identifier("copper_pickaxe"));
//    consumer.accept(COPPER_AXE, MoreUsefulCopper.identifier("copper_axe"));
//    consumer.accept(COPPER_HOE, MoreUsefulCopper.identifier("copper_hoe"));
//    consumer.accept(COPPER_SHOVEL, MoreUsefulCopper.identifier("copper_shovel"));
//    consumer.accept(COPPER_SWORD, MoreUsefulCopper.identifier("copper_sword"));
//    consumer.accept(COPPER_HELMET, MoreUsefulCopper.identifier("copper_helmet"));
//    consumer.accept(COPPER_CHESTPLATE, MoreUsefulCopper.identifier("copper_chestplate"));
//    consumer.accept(COPPER_LEGGINGS, MoreUsefulCopper.identifier("copper_leggings"));
//    consumer.accept(COPPER_BOOTS, MoreUsefulCopper.identifier("copper_boots"));
//    consumer.accept(COPPER_CHAIN, MoreUsefulCopper.identifier("copper_chain"));
//    consumer.accept(COPPER_GOLEM_SPAWN_EGG, MoreUsefulCopper.identifier("copper_golem_spawn_egg"));
//    consumer.accept(COPPER_HORSE_ARMOR, MoreUsefulCopper.identifier("copper_horse_armor"));
    consumer.accept(COPPER_PRESSURE_PLATE, MoreUsefulCopper.identifier("copper_pressure_plate"));
    consumer.accept(new BlockItem(ModBlocks.EXPOSED_COPPER_PRESSURE_PLATE, new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("exposed_copper_pressure_plate")))), MoreUsefulCopper.identifier("exposed_copper_pressure_plate"));
    consumer.accept(new BlockItem(ModBlocks.WEATHERED_COPPER_PRESSURE_PLATE, new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("weathered_copper_pressure_plate")))), MoreUsefulCopper.identifier("weathered_copper_pressure_plate"));
    consumer.accept(new BlockItem(ModBlocks.OXIDIZED_COPPER_PRESSURE_PLATE, new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("oxidized_copper_pressure_plate")))), MoreUsefulCopper.identifier("oxidized_copper_pressure_plate"));
    consumer.accept(new BlockItem(ModBlocks.WAXED_COPPER_PRESSURE_PLATE, new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("waxed_copper_pressure_plate")))), MoreUsefulCopper.identifier("waxed_copper_pressure_plate"));
    consumer.accept(new BlockItem(ModBlocks.WAXED_EXPOSED_COPPER_PRESSURE_PLATE, new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("waxed_exposed_copper_pressure_plate")))), MoreUsefulCopper.identifier("waxed_exposed_copper_pressure_plate"));
    consumer.accept(new BlockItem(ModBlocks.WAXED_WEATHERED_COPPER_PRESSURE_PLATE, new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("waxed_weathered_copper_pressure_plate")))), MoreUsefulCopper.identifier("waxed_weathered_copper_pressure_plate"));
    consumer.accept(new BlockItem(ModBlocks.WAXED_OXIDIZED_COPPER_PRESSURE_PLATE, new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("waxed_oxidized_copper_pressure_plate")))), MoreUsefulCopper.identifier("waxed_oxidized_copper_pressure_plate"));
//    consumer.accept(new BlockItem(ModBlocks.EXPOSED_COPPER_CHAIN, new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("exposed_copper_chain")))), MoreUsefulCopper.identifier("exposed_copper_chain"));
//    consumer.accept(new BlockItem(ModBlocks.WEATHERED_COPPER_CHAIN, new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("weathered_copper_chain")))), MoreUsefulCopper.identifier("weathered_copper_chain"));
//    consumer.accept(new BlockItem(ModBlocks.OXIDIZED_COPPER_CHAIN, new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("oxidized_copper_chain")))), MoreUsefulCopper.identifier("oxidized_copper_chain"));
//    consumer.accept(new BlockItem(ModBlocks.WAXED_COPPER_CHAIN, new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("waxed_copper_chain")))), MoreUsefulCopper.identifier("waxed_copper_chain"));
//    consumer.accept(new BlockItem(ModBlocks.WAXED_EXPOSED_COPPER_CHAIN, new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("waxed_exposed_copper_chain")))), MoreUsefulCopper.identifier("waxed_exposed_copper_chain"));
//    consumer.accept(new BlockItem(ModBlocks.WAXED_WEATHERED_COPPER_CHAIN, new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("waxed_weathered_copper_chain")))), MoreUsefulCopper.identifier("waxed_weathered_copper_chain"));
//    consumer.accept(new BlockItem(ModBlocks.WAXED_OXIDIZED_COPPER_CHAIN, new Properties().setId(ResourceKey.create(Registries.ITEM, MoreUsefulCopper.identifier("waxed_oxidized_copper_chain")))), MoreUsefulCopper.identifier("waxed_oxidized_copper_chain"));
  }
}
