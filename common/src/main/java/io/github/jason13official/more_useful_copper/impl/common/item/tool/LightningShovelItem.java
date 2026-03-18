package io.github.jason13official.more_useful_copper.impl.common.item.tool;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.Tier;

public class LightningShovelItem extends ShovelItem {

  public LightningShovelItem(Tier tier, float attackDamageModifier, float attackSpeedModifier, Properties properties) {
    super(tier, attackDamageModifier, attackSpeedModifier, properties);
  }

  @Override
  public boolean isFoil(ItemStack stack) {
    return stack.getOrCreateTag().contains("charged");
  }
}
