package io.github.jason13official.more_useful_copper.impl.common.block;

import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.state.properties.BlockSetType;

public class BasicButtonBlock extends ButtonBlock {

  public BasicButtonBlock(Properties properties, BlockSetType type, int ticksToStayPressed, boolean arrowsCanPress) {
    super(properties, type, ticksToStayPressed, arrowsCanPress);
  }
}
