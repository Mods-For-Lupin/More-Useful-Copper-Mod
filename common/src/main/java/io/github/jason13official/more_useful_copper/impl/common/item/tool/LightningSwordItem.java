package io.github.jason13official.more_useful_copper.impl.common.item.tool;

import java.util.List;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;

public class LightningSwordItem extends SwordItem {

  public LightningSwordItem(Tier tier, int attackDamageModifier, float attackSpeedModifier, Properties properties) {
    super(tier, properties.attributes(SwordItem.createAttributes(tier, attackDamageModifier, attackSpeedModifier)));
  }

  @Override
  public boolean isFoil(ItemStack stack) {
    CustomData data = stack.get(DataComponents.CUSTOM_DATA);
    return (data != null && data.contains("charged")) || super.isFoil(stack);
  }

  @Override
  public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
    Level level = target.level();
    LightningBolt bolt = EntityType.LIGHTNING_BOLT.create(level);
    if (bolt != null && level instanceof ServerLevel serverLevel) {
      bolt.moveTo(target.position());
      serverLevel.addFreshEntity(bolt);
    }
    return super.hurtEnemy(stack, target, attacker);
  }

  @Override
  public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
    if (isFoil(stack)) {
      tooltipComponents.add(Component.literal("The residual lightning charge attracts more lightning..."));
    }
    super.appendHoverText(stack, context, tooltipComponents, isAdvanced);
  }
}
