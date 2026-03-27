package io.github.jason13official.more_useful_copper.impl.common.item.tool;

import io.github.jason13official.more_useful_copper.impl.common.registry.ModItems;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class LightningHoeItem extends HoeItem {

  public LightningHoeItem(Tier tier, int attackDamageModifier, float attackSpeedModifier, Properties properties) {
    super(tier, attackDamageModifier, attackSpeedModifier, properties);
  }

  @Override
  public boolean isFoil(ItemStack stack) {
    return stack.getOrCreateTag().contains("charged") || super.isFoil(stack);
  }

  @Override
  public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {

    if (level.getRandom().nextFloat() >= 0.5f && player.getHealth() < player.getMaxHealth()) {
      BlockPos pos = player.blockPosition().above();
      level.addParticle(ParticleTypes.HAPPY_VILLAGER, pos.getX(), pos.getY(), pos.getZ(), 0, 0.2, 0);
      player.heal(2.0f);
      player.getCooldowns().addCooldown(ModItems.COPPER_HOE, 40);
    }

    return super.use(level, player, usedHand);
  }

  @Override
  public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {

    if (isFoil(stack)) {
      tooltipComponents.add(Component.literal("Holds a healing effect powered by lightning..."));
    }

    super.appendHoverText(stack, level, tooltipComponents, isAdvanced);
  }
}
