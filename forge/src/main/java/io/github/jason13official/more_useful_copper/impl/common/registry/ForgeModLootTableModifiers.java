package io.github.jason13official.more_useful_copper.impl.common.registry;

import com.mojang.serialization.Codec;
import io.github.jason13official.more_useful_copper.Constants;
import io.github.jason13official.more_useful_copper.api.common.registry.AddItemModifier;
import java.util.function.Supplier;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ForgeModLootTableModifiers {

  public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIER_SERIALIZERS = DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, Constants.MOD_ID);

  @SuppressWarnings("unused")
  public static final RegistryObject<Codec<? extends IGlobalLootModifier>> ADD_ITEM = registerLootModifierSerializer("add_item", () -> AddItemModifier.CODEC.get());

  public static void register(IEventBus modEventBus) {
    LOOT_MODIFIER_SERIALIZERS.register(modEventBus);
  }

  private static <T extends Codec<? extends IGlobalLootModifier>> RegistryObject<T> registerLootModifierSerializer(String serializerID, Supplier<T> serializerSupplier) {
    return LOOT_MODIFIER_SERIALIZERS.register(serializerID, serializerSupplier);
  }
}
