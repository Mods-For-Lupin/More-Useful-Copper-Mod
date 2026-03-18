package io.github.jason13official.more_useful_copper.impl.common.util;

import io.github.jason13official.more_useful_copper.impl.common.block.GardenStakeBlock;
import io.github.jason13official.more_useful_copper.impl.common.item.LightningArmorItem;
import io.github.jason13official.more_useful_copper.impl.common.item.tool.LightningAxeItem;
import io.github.jason13official.more_useful_copper.impl.common.item.tool.LightningHoeItem;
import io.github.jason13official.more_useful_copper.impl.common.item.tool.LightningPickaxeItem;
import io.github.jason13official.more_useful_copper.impl.common.item.tool.LightningShovelItem;
import io.github.jason13official.more_useful_copper.impl.common.item.tool.LightningSwordItem;
import io.github.jason13official.more_useful_copper.impl.common.registry.ModBlocks;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import oshi.util.tuples.Pair;

public class CommonLightningBoltMixinLogic {

  /// Mixin injection: called each tick of a [LightningBolt] to light nearby garden stakes. Only acts on tick `life == 2` (the peak-energy tick). Checks the strike block, then the bolt's current
  /// position, then a 10×6×10 AABB around the strike position.
  ///
  /// @param life      remaining lifetime ticks of the bolt
  /// @param strikePos the block position the bolt originally struck
  public static void onLightningTick(LightningBolt self, int life, BlockPos strikePos) {

    if (!(self.level() instanceof ServerLevel level)) {
      return;
    }

    if (life != 2) {
      return;
    }

    affectArmorOnEntities(self, level, strikePos);

    BlockState strikeState = level.getBlockState(strikePos);

    BlockPos insidePos = self.blockPosition();
    BlockState insideState = level.getBlockState(insidePos);

    if (strikeState.is(ModBlocks.GARDEN_STAKE) && !strikeState.getValue(GardenStakeBlock.LIT)) {

      strikeState.setValue(GardenStakeBlock.LIT, true);
      level.setBlock(strikePos, strikeState, 2);

      return;
    } else if (insideState.is(ModBlocks.GARDEN_STAKE) && !insideState.getValue(GardenStakeBlock.LIT)) {

      System.out.println("lightning ticked inside of garden stake");

      var newState = insideState.setValue(GardenStakeBlock.LIT, true);
      // level.setBlock(insidePos, newState, 2);

      level.setBlockAndUpdate(insidePos, newState);
      level.setBlocksDirty(insidePos, insideState, newState);

      return;
    }

    AABB box = new AABB(strikePos).inflate(5, 3, 5);
    List<BlockPos> alreadyChecked = new ArrayList<>();

    for (double x = box.minX; x < box.maxX; x++) {
      for (double z = box.minZ; z < box.maxZ; z++) {
        for (double y = box.minY; y < box.maxY; y++) {

          BlockPos pos = BlockPos.containing(x, y, z);
          BlockState state = level.getBlockState(pos);

          if (!alreadyChecked.contains(pos)) {

            if (state.getBlock() instanceof GardenStakeBlock) {
              state.setValue(GardenStakeBlock.LIT, true);
              level.setBlock(pos, state, 2);
              return;
            }

            alreadyChecked.add(pos);
          }
        }
      }
    }
  }

  /// iterate over armor and main/offhand items
  private static void affectArmorOnEntities(LightningBolt self, ServerLevel level, BlockPos strikePos) {

    List<Entity> struck = level.getEntities(self, new AABB(strikePos).inflate(0.5, 2, 0.5));

    struck.stream().filter(entity -> entity instanceof LivingEntity).forEach(entity -> {
      LivingEntity living = (LivingEntity) entity;

      living.getArmorSlots().forEach(stack -> {

        Item armorItem = stack.getItem();

        if (!(armorItem instanceof LightningArmorItem lightningArmor)) {
          return;
        }

        stack.getOrCreateTag().putBoolean("charged", true);

        living.setItemSlot(lightningArmor.getType().getSlot(), stack);
      });

      if (isValid(living.getMainHandItem())) {
        ItemStack stack = living.getMainHandItem();
        stack.getOrCreateTag().putBoolean("charged", true);
        living.setItemSlot(EquipmentSlot.MAINHAND, stack);
      }

      if (isValid(living.getOffhandItem())) {
        ItemStack stack = living.getOffhandItem();
        stack.getOrCreateTag().putBoolean("charged", true);
        living.setItemSlot(EquipmentSlot.OFFHAND, stack);
      }
    });
  }

  private static boolean isValid(ItemStack stack) {
    Item toolItem = stack.getItem();

    boolean valid = false;

    if (toolItem instanceof LightningSwordItem) {
      valid = true;
    }  else if (toolItem instanceof LightningShovelItem) {
      valid = true;
    }  else if (toolItem instanceof LightningAxeItem) {
      valid = true;
    }  else if (toolItem instanceof LightningPickaxeItem) {
      valid = true;
    }  else if (toolItem instanceof LightningHoeItem) {
      valid = true;
    }

    return valid;
  }
}
