package io.github.jason13official.more_useful_copper.impl.common.registry;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class FabricModLootTableModifiers {

    private static final Identifier DESERT_PYRAMID = Identifier.fromNamespaceAndPath("minecraft", "chests/desert_pyramid");
    private static final Identifier END_CITY_TREASURE = Identifier.fromNamespaceAndPath("minecraft", "chests/end_city_treasure");
    private static final Identifier JUNGLE_TEMPLE = Identifier.fromNamespaceAndPath("minecraft", "chests/jungle_temple");
    private static final Identifier NETHER_BRIDGE = Identifier.fromNamespaceAndPath("minecraft", "chests/nether_bridge");
    private static final Identifier SIMPLE_DUNGEON = Identifier.fromNamespaceAndPath("minecraft", "chests/simple_dungeon");
    private static final Identifier STRONGHOLD_CORRIDOR = Identifier.fromNamespaceAndPath("minecraft", "chests/stronghold_corridor");
    private static final Identifier VILLAGE_WEAPONSMITH = Identifier.fromNamespaceAndPath("minecraft", "chests/village/village_weaponsmith");

    public static void register() {
        LootTableEvents.MODIFY.register(((resourceKey, tableBuilder, source, provider) -> {

            if (!source.isBuiltin()) {
                return;
            }

        }));

//        LootTableEvents.MODIFY.register(((resourceManager, lootManager, id, tableBuilder, source) -> {
//
//            if (DESERT_PYRAMID.equals(id)) {
//                LootPool.Builder poolBuilder = LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
//                    .conditionally(LootItemRandomChanceCondition.randomChance(0.35f).build()) // Drops 35% of the time
//                    .with(LootItem.lootTableItem(ModItems.COPPER_HORSE_ARMOR).build()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 2.0f)).build());
//
//                tableBuilder.pool(poolBuilder.build());
//            }
//            if (END_CITY_TREASURE.equals(id)) {
//                LootPool.Builder poolBuilder = LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
//                    .conditionally(LootItemRandomChanceCondition.randomChance(0.35f).build()) // Drops 35% of the time
//                    .with(LootItem.lootTableItem(ModItems.COPPER_HORSE_ARMOR).build()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 2.0f)).build());
//
//                tableBuilder.pool(poolBuilder.build());
//            }
//            if (JUNGLE_TEMPLE.equals(id)) {
//                LootPool.Builder poolBuilder = LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
//                    .conditionally(LootItemRandomChanceCondition.randomChance(0.35f).build()) // Drops 35% of the time
//                    .with(LootItem.lootTableItem(ModItems.COPPER_HORSE_ARMOR).build()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 2.0f)).build());
//
//                tableBuilder.pool(poolBuilder.build());
//            }
//            if (NETHER_BRIDGE.equals(id)) {
//                LootPool.Builder poolBuilder = LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
//                    .conditionally(LootItemRandomChanceCondition.randomChance(0.35f).build()) // Drops 35% of the time
//                    .with(LootItem.lootTableItem(ModItems.COPPER_HORSE_ARMOR).build()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 2.0f)).build());
//
//                tableBuilder.pool(poolBuilder.build());
//            }
//            if (SIMPLE_DUNGEON.equals(id)) {
//                LootPool.Builder poolBuilder = LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
//                    .conditionally(LootItemRandomChanceCondition.randomChance(0.35f).build()) // Drops 35% of the time
//                    .with(LootItem.lootTableItem(ModItems.COPPER_HORSE_ARMOR).build()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 2.0f)).build());
//
//                tableBuilder.pool(poolBuilder.build());
//            }
//            if (STRONGHOLD_CORRIDOR.equals(id)) {
//                LootPool.Builder poolBuilder = LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
//                    .conditionally(LootItemRandomChanceCondition.randomChance(0.35f).build()) // Drops 35% of the time
//                    .with(LootItem.lootTableItem(ModItems.COPPER_HORSE_ARMOR).build()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 2.0f)).build());
//
//                tableBuilder.pool(poolBuilder.build());
//            }
//            if (VILLAGE_WEAPONSMITH.equals(id)) {
//                LootPool.Builder poolBuilder = LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
//                    .conditionally(LootItemRandomChanceCondition.randomChance(0.35f).build()) // Drops 35% of the time
//                    .with(LootItem.lootTableItem(ModItems.COPPER_HORSE_ARMOR).build()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 2.0f)).build());
//
//                tableBuilder.pool(poolBuilder.build());
//            }
//        }));
    }
}

