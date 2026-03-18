package io.github.jason13official.more_useful_copper.impl.common.item.tool;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;

public class LightningAxeItem extends AxeItem {

  public LightningAxeItem(Tier tier, float attackDamageModifier, float attackSpeedModifier, Properties properties) {
    super(tier, attackDamageModifier, attackSpeedModifier, properties);
  }

  @Override
  public boolean isFoil(ItemStack stack) {
    return stack.getOrCreateTag().contains("charged");
  }

  @Override
  public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {

    float g = 2.0f;

    if (isFoil(stack)) {
      target.knockback(g, Mth.sin(attacker.getYRot() * (float) (Math.PI / 180.0)), -Mth.cos(attacker.getYRot() * (float) (Math.PI / 180.0)));
    }

    return super.hurtEnemy(stack, target, attacker);
  }
}
