package io.github.jason13official.more_useful_copper.impl.common.item.tool;

import io.github.jason13official.more_useful_copper.impl.common.ModConfig;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class LightningSwordItem extends SwordItem {

  public LightningSwordItem(Tier tier, int attackDamageModifier, float attackSpeedModifier, Properties properties) {
    super(tier, attackDamageModifier, attackSpeedModifier, properties);
  }

  @Override
  public boolean isFoil(ItemStack stack) {
    return stack.getOrCreateTag().contains("charged") || super.isFoil(stack);
  }

  @Override
  public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {

    if (ModConfig.get().lightningEffectsEnabled && isFoil(stack)) {
      Level level = target.level();

      LightningBolt bolt = EntityType.LIGHTNING_BOLT.create(level);

      if (bolt != null && level instanceof ServerLevel serverLevel) {
        bolt.moveTo(target.position());
        serverLevel.addFreshEntity(bolt);
      }
      stack.getOrCreateTag().remove("charged");
    }

    return super.hurtEnemy(stack, target, attacker);
  }

  @Override
  public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {

    if (isFoil(stack)) {
      tooltipComponents.add(Component.literal("The residual lightning charge attracts more lightning..."));
    }

    super.appendHoverText(stack, level, tooltipComponents, isAdvanced);
  }
}
