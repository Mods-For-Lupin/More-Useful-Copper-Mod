package io.github.jason13official.more_useful_copper.api.common.item;

import io.github.jason13official.more_useful_copper.Constants;
import java.util.EnumMap;
import java.util.List;
import net.minecraft.Util;
import java.util.function.Supplier;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public class ModArmorMaterials {

  public static final Holder<ArmorMaterial> COPPER = register(
    "copper",
    Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
      map.put(ArmorItem.Type.BOOTS, 2);
      map.put(ArmorItem.Type.LEGGINGS, 6);
      map.put(ArmorItem.Type.CHESTPLATE, 5);
      map.put(ArmorItem.Type.HELMET, 2);
    }),
    9,
    SoundEvents.ARMOR_EQUIP_CHAIN,
    0.0F,
    0.0F,
    () -> Ingredient.of(Items.COPPER_INGOT)
  );

  private static Holder<ArmorMaterial> register(
    String name,
    EnumMap<ArmorItem.Type, Integer> defense,
    int enchantmentValue,
    Holder<SoundEvent> equipSound,
    float toughness,
    float knockbackResistance,
    Supplier<Ingredient> repairIngredient
  ) {
    EnumMap<ArmorItem.Type, Integer> fullMap = new EnumMap<>(ArmorItem.Type.class);
    for (ArmorItem.Type type : ArmorItem.Type.values()) {
      fullMap.put(type, defense.getOrDefault(type, 0));
    }
    return Registry.registerForHolder(
      BuiltInRegistries.ARMOR_MATERIAL,
      Identifier.fromNamespaceAndPath(Constants.MOD_ID, name),
      new ArmorMaterial(fullMap, enchantmentValue, equipSound, repairIngredient,
        List.of(new ArmorMaterial.Layer(Identifier.fromNamespaceAndPath(Constants.MOD_ID, name))),
        toughness, knockbackResistance)
    );
  }
}
