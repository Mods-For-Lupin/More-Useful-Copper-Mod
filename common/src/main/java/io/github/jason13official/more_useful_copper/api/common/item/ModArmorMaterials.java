package io.github.jason13official.more_useful_copper.api.common.item;

import java.util.Map;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAssets;

public class ModArmorMaterials {

  public static final ArmorMaterial COPPER = new ArmorMaterial(
      7,
      Map.of(
          ArmorType.HELMET, 2,
          ArmorType.CHESTPLATE, 5,
          ArmorType.LEGGINGS, 6,
          ArmorType.BOOTS, 2,
          ArmorType.BODY, 4
      ),
      9,
      SoundEvents.ARMOR_EQUIP_CHAIN,
      0.0F,
      0.0F,
      ItemTags.REPAIRS_COPPER_ARMOR,
      EquipmentAssets.COPPER
  );
}
