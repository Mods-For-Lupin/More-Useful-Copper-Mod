package io.github.jason13official.more_useful_copper.impl.common.registry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import io.github.jason13official.more_useful_copper.Constants;
import io.github.jason13official.more_useful_copper.api.common.registry.AddItemModifier;
import java.util.function.Supplier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.NeoForgeRegistries.Keys;

// old forge imports
//import net.minecraftforge.common.loot.IGlobalLootModifier;
//import net.minecraftforge.eventbus.api.IEventBus;
//import net.minecraftforge.registries.DeferredRegister;
//import net.minecraftforge.registries.ForgeRegistries;
//import net.minecraftforge.registries.RegistryObject;

public class ForgeModLootTableModifiers {

  public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> LOOT_MODIFIER_SERIALIZERS = DeferredRegister.create(Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, Constants.MOD_ID);

  @SuppressWarnings("unused")
  public static Supplier<MapCodec<AddItemModifier>> ADD_ITEM; // = LOOT_MODIFIER_SERIALIZERS.register("my_loot_modifier", () -> AddItemModifier.CODEC);

  public static void register(IEventBus modEventBus) {

    ADD_ITEM = registerLootModifierSerializer("add_item", () -> AddItemModifier.CODEC);

    LOOT_MODIFIER_SERIALIZERS.register(modEventBus);
  }

  private static <T extends MapCodec<? extends IGlobalLootModifier>> DeferredHolder<MapCodec<? extends IGlobalLootModifier>, T> registerLootModifierSerializer(String id, Supplier<T> serializer) {
    return LOOT_MODIFIER_SERIALIZERS.register(id, serializer);
  }
}
