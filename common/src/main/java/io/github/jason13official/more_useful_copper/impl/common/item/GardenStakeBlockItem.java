package io.github.jason13official.more_useful_copper.impl.common.item;

import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

public class GardenStakeBlockItem extends BlockItem {

  public GardenStakeBlockItem(Block block, Properties properties) {
    super(block, properties);
  }


  @Override
  public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
    super.appendHoverText(stack, level, tooltip, flag);
    tooltip.add(Component.literal("After being struck by lightning, this causes nearby crops to grow faster."));
  }
}
