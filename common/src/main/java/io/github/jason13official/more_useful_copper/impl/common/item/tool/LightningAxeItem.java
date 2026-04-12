package io.github.jason13official.more_useful_copper.impl.common.item.tool;

import io.github.jason13official.more_useful_copper.impl.common.ModConfig;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class LightningAxeItem extends AxeItem {

  public LightningAxeItem(Tier tier, float attackDamageModifier, float attackSpeedModifier, Properties properties) {
    super(tier, attackDamageModifier, attackSpeedModifier, properties);
  }

  @Override
  public boolean isFoil(ItemStack stack) {
    return stack.getOrCreateTag().contains("charged") || super.isFoil(stack);
  }

  @Override
  public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {

    if (ModConfig.get().lightningEffectsEnabled && isFoil(stack)) {
      float g = 2.0f;
      target.knockback(g, Mth.sin(attacker.getYRot() * (float) (Math.PI / 180.0)), -Mth.cos(attacker.getYRot() * (float) (Math.PI / 180.0)));
      stack.getOrCreateTag().remove("charged");
    }

    return super.hurtEnemy(stack, target, attacker);
  }

  @Override
  public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {

    if (isFoil(stack)) {
      tooltipComponents.add(Component.literal("Imbued with knockback from lightning..."));
    }

    super.appendHoverText(stack, level, tooltipComponents, isAdvanced);
  }
}
