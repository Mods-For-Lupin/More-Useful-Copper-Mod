package io.github.jason13official.more_useful_copper.api.common.item;

import io.github.jason13official.more_useful_copper.Constants;
import java.util.function.Supplier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public enum ModArmorMaterials implements ArmorMaterial {

  COPPER("copper", 15, new int[]{2, 6, 5, 2}, 9, SoundEvents.ARMOR_EQUIP_CHAIN, 0.0F, 0.0F, () -> Ingredient.of(Items.COPPER_INGOT));

  private static final int[] BASE_DURABILITY = {13, 15, 16, 11};
  private final String name;
  private final int durabilityMultiplier;
  private final int[] protectionAmounts;
  private final int enchantmentValue;
  private final SoundEvent equipSound;
  private final float toughness;
  private final float knockbackResistance;
  private final Supplier<Ingredient> repairIngredient;

  ModArmorMaterials(String name, int durabilityMultiplier, int[] protectionAmounts, int enchantmentValue, SoundEvent equipSound, float toughness, float knockbackResistance,
      Supplier<Ingredient> repairIngredient) {
    this.name = name;
    this.durabilityMultiplier = durabilityMultiplier;
    this.protectionAmounts = protectionAmounts;
    this.enchantmentValue = enchantmentValue;
    this.equipSound = equipSound;
    this.toughness = toughness;
    this.knockbackResistance = knockbackResistance;
    this.repairIngredient = repairIngredient;
  }

  @Override
  public int getDurabilityForType(ArmorItem.Type pType) {
    return BASE_DURABILITY[pType.ordinal()] * this.durabilityMultiplier;
  }

  @Override
  public int getDefenseForType(ArmorItem.Type pType) {
    return this.protectionAmounts[pType.ordinal()];
  }

  @Override
  public int getEnchantmentValue() {
    return enchantmentValue;
  }

  @Override
  public SoundEvent getEquipSound() {
    return this.equipSound;
  }

  @Override
  public Ingredient getRepairIngredient() {
    return this.repairIngredient.get();
  }

  @Override
  public String getName() {
    return Constants.MOD_ID + ":" + this.name;
  }

  @Override
  public float getToughness() {
    return this.toughness;
  }

  @Override
  public float getKnockbackResistance() {
    return this.knockbackResistance;
  }
}
