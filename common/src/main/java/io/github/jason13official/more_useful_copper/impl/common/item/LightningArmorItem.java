package io.github.jason13official.more_useful_copper.impl.common.item;

import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;

public class LightningArmorItem extends ArmorItem {

  public LightningArmorItem(ArmorMaterial material, Type type, Properties properties) {
    super(material, type, properties);
  }

  @Override
  public boolean isFoil(ItemStack stack) {
    return stack.getOrCreateTag().contains("charged");
  }
}
