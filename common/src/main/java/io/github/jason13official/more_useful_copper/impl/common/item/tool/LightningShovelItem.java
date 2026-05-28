package io.github.jason13official.more_useful_copper.impl.common.item.tool;

import java.util.List;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;

public class LightningShovelItem extends ShovelItem {

  public LightningShovelItem(ToolMaterial material, float attackDamage, float attackSpeed, Properties properties) {
    super(material, attackDamage, attackSpeed, properties);
  }

  @Override
  public boolean isFoil(ItemStack stack) {
    CustomData data = stack.get(DataComponents.CUSTOM_DATA);
    return (data != null && data.copyTag().contains("charged")) || super.isFoil(stack);
  }

  @Override
  public void hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
    attacker.igniteForSeconds(4.0F);
    super.hurtEnemy(stack, target, attacker);
  }

  @Override
  public void appendHoverText(ItemStack stack, Item.TooltipContext context, net.minecraft.world.item.component.TooltipDisplay display, java.util.function.Consumer<Component> tooltipComponentsBuilder, TooltipFlag isAdvanced) {
    if (isFoil(stack)) {
      tooltipComponentsBuilder.accept(Component.literal("Imbued with lightning that burns enemies..."));
    }
    super.appendHoverText(stack, context, display, tooltipComponentsBuilder, isAdvanced);
  }
}
