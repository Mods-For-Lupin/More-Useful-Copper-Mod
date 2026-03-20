package io.github.jason13official.more_useful_copper.impl.common.registry;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class FabricModLootTableModifiers {

    private static final ResourceLocation DESERT_PYRAMID = new ResourceLocation("minecraft", "chests/desert_pyramid");
    private static final ResourceLocation END_CITY_TREASURE = new ResourceLocation("minecraft", "chests/end_city_treasure");
    private static final ResourceLocation JUNGLE_TEMPLE = new ResourceLocation("minecraft", "chests/jungle_temple");
    private static final ResourceLocation NETHER_BRIDGE = new ResourceLocation("minecraft", "chests/nether_bridge");
    private static final ResourceLocation SIMPLE_DUNGEON = new ResourceLocation("minecraft", "chests/simple_dungeon");
    private static final ResourceLocation STRONGHOLD_CORRIDOR = new ResourceLocation("minecraft", "chests/stronghold_corridor");
    private static final ResourceLocation VILLAGE_WEAPONSMITH = new ResourceLocation("minecraft", "chests/village/village_weaponsmith");

    public static void register() {
        LootTableEvents.MODIFY.register(((resourceManager, lootManager, id, tableBuilder, source) -> {

            if(DESERT_PYRAMID.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .conditionally(LootItemRandomChanceCondition.randomChance(0.35f).build()) // Drops 35% of the time
                        .with(LootItem.lootTableItem(ModItems.COPPER_HORSE_ARMOR).build())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 2.0f)).build());

                tableBuilder.pool(poolBuilder.build());
            }
            if(END_CITY_TREASURE.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .conditionally(LootItemRandomChanceCondition.randomChance(0.35f).build()) // Drops 35% of the time
                        .with(LootItem.lootTableItem(ModItems.COPPER_HORSE_ARMOR).build())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 2.0f)).build());

                tableBuilder.pool(poolBuilder.build());
            }
            if(JUNGLE_TEMPLE.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .conditionally(LootItemRandomChanceCondition.randomChance(0.35f).build()) // Drops 35% of the time
                        .with(LootItem.lootTableItem(ModItems.COPPER_HORSE_ARMOR).build())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 2.0f)).build());

                tableBuilder.pool(poolBuilder.build());
            }
            if(NETHER_BRIDGE.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .conditionally(LootItemRandomChanceCondition.randomChance(0.35f).build()) // Drops 35% of the time
                        .with(LootItem.lootTableItem(ModItems.COPPER_HORSE_ARMOR).build())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 2.0f)).build());

                tableBuilder.pool(poolBuilder.build());
            }
            if(SIMPLE_DUNGEON.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .conditionally(LootItemRandomChanceCondition.randomChance(0.35f).build()) // Drops 35% of the time
                        .with(LootItem.lootTableItem(ModItems.COPPER_HORSE_ARMOR).build())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 2.0f)).build());

                tableBuilder.pool(poolBuilder.build());
            }
            if(STRONGHOLD_CORRIDOR.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .conditionally(LootItemRandomChanceCondition.randomChance(0.35f).build()) // Drops 35% of the time
                        .with(LootItem.lootTableItem(ModItems.COPPER_HORSE_ARMOR).build())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 2.0f)).build());

                tableBuilder.pool(poolBuilder.build());
            }
            if(VILLAGE_WEAPONSMITH.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .conditionally(LootItemRandomChanceCondition.randomChance(0.35f).build()) // Drops 35% of the time
                        .with(LootItem.lootTableItem(ModItems.COPPER_HORSE_ARMOR).build())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 2.0f)).build());

                tableBuilder.pool(poolBuilder.build());
            }
        }));
    }
}

