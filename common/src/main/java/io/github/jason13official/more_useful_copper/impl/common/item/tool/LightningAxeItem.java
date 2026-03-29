package io.github.jason13official.more_useful_copper.impl.common.item.tool;

import java.util.List;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;

public class LightningAxeItem extends AxeItem {

  public LightningAxeItem(Tier tier, float attackDamageModifier, float attackSpeedModifier, Properties properties) {
    super(tier, properties.attributes(AxeItem.createAttributes(tier, attackDamageModifier, attackSpeedModifier)));
  }

  @Override
  public boolean isFoil(ItemStack stack) {
    CustomData data = stack.get(DataComponents.CUSTOM_DATA);
    return (data != null && data.contains("charged")) || super.isFoil(stack);
  }

  @Override
  public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
    float g = 2.0f;
    if (isFoil(stack)) {
      target.knockback(g, Mth.sin(attacker.getYRot() * (float) (Math.PI / 180.0)), -Mth.cos(attacker.getYRot() * (float) (Math.PI / 180.0)));
    }
    return super.hurtEnemy(stack, target, attacker);
  }

  @Override
  public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
    if (isFoil(stack)) {
      tooltipComponents.add(Component.literal("Imbued with knockback from lightning..."));
    }
    super.appendHoverText(stack, context, tooltipComponents, isAdvanced);
  }
}
