package io.github.jason13official.more_useful_copper.impl.common.item.tool;

import io.github.jason13official.more_useful_copper.impl.common.registry.ModItems;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;

public class LightningHoeItem extends HoeItem {

  public LightningHoeItem(ToolMaterial material, float attackDamage, float attackSpeed, Properties properties) {
    super(material, attackDamage, attackSpeed, properties);
  }

  @Override
  public boolean isFoil(ItemStack stack) {
    CustomData data = stack.get(DataComponents.CUSTOM_DATA);
    return (data != null && data.copyTag().contains("charged")) || super.isFoil(stack);
  }

  @Override
  public InteractionResult use(Level level, Player player, InteractionHand usedHand) {
    if (level.getRandom().nextFloat() >= 0.5f && player.getHealth() < player.getMaxHealth()) {
      BlockPos pos = player.blockPosition().above();
      level.addParticle(ParticleTypes.HAPPY_VILLAGER, pos.getX(), pos.getY(), pos.getZ(), 0, 0.2, 0);
      player.heal(2.0f);
      player.getCooldowns().addCooldown(player.getItemInHand(usedHand), 40);
    }
    return super.use(level, player, usedHand);
  }

  @Override
  public void appendHoverText(ItemStack stack, Item.TooltipContext context, net.minecraft.world.item.component.TooltipDisplay display, java.util.function.Consumer<Component> tooltipComponentsBuilder, TooltipFlag isAdvanced) {
    if (isFoil(stack)) {
      tooltipComponentsBuilder.accept(Component.literal("Holds a healing effect powered by lightning..."));
    }
    super.appendHoverText(stack, context, display, tooltipComponentsBuilder, isAdvanced);
  }
}
