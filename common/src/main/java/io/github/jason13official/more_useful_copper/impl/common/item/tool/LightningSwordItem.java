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
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;

public class LightningSwordItem extends Item {

  public LightningSwordItem(Properties properties) {
    super(properties);
  }

  @Override
  public boolean isFoil(ItemStack stack) {
    CustomData data = stack.get(DataComponents.CUSTOM_DATA);
    return (data != null && data.copyTag().contains("charged")) || super.isFoil(stack);
  }

  @Override
  public void hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
    Level level = target.level();
    if (level instanceof ServerLevel serverLevel) {
      LightningBolt bolt = EntityType.LIGHTNING_BOLT.create(serverLevel, net.minecraft.world.entity.EntitySpawnReason.EVENT);
      if (bolt != null) {
        bolt.setPos(target.position());
        serverLevel.addFreshEntity(bolt);
      }
    }
    super.hurtEnemy(stack, target, attacker);
  }

  @Override
  public void appendHoverText(ItemStack stack, Item.TooltipContext context, net.minecraft.world.item.component.TooltipDisplay display, java.util.function.Consumer<Component> tooltipComponentsBuilder, TooltipFlag isAdvanced) {
    if (isFoil(stack)) {
      tooltipComponentsBuilder.accept(Component.literal("The residual lightning charge attracts more lightning..."));
    }
    super.appendHoverText(stack, context, display, tooltipComponentsBuilder, isAdvanced);
  }
}
