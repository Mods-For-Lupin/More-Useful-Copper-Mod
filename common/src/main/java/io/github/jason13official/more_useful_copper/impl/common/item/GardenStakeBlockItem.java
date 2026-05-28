package io.github.jason13official.more_useful_copper.impl.common.item;

import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class GardenStakeBlockItem extends BlockItem {

  public GardenStakeBlockItem(Block block, Properties properties) {
    super(block, properties);
  }


  @Override
  public void appendHoverText(ItemStack stack, Item.TooltipContext context, net.minecraft.world.item.component.TooltipDisplay display, java.util.function.Consumer<Component> tooltipBuilder, TooltipFlag flag) {
    super.appendHoverText(stack, context, display, tooltipBuilder, flag);
    tooltipBuilder.accept(Component.literal("After being struck by lightning, this causes nearby crops to grow faster."));
  }
}
