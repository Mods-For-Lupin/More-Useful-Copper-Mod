package io.github.jason13official.more_useful_copper.impl.common.item;

import java.util.Optional;
import org.jetbrains.annotations.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.LodestoneTracker;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class MoistureCompassItem extends Item {

  public MoistureCompassItem(Properties properties) {
    super(properties);
  }

  public static boolean isMoistureCompass(ItemStack stack) {
    return stack.has(DataComponents.LODESTONE_TRACKER);
  }

  @Nullable
  public static GlobalPos getMoisturePosition(ItemStack stack) {
    LodestoneTracker tracker = stack.get(DataComponents.LODESTONE_TRACKER);
    if (tracker == null) return null;
    return tracker.target().orElse(null);
  }

  @Nullable
  public static GlobalPos getSpawnPosition(Level level) {
    return level.dimension() == Level.OVERWORLD ? GlobalPos.of(level.dimension(), level.getLevelData().getRespawnData().pos()) : null;
  }

  @Override
  public InteractionResult use(Level level, Player player, InteractionHand usedHand) {
    ItemStack stack = player.getItemInHand(usedHand);
    if (isMoistureCompass(stack)) {
      stack.remove(DataComponents.LODESTONE_TRACKER);
      tagClosestWaterPosition(stack, level, player);
    }
    return super.use(level, player, usedHand);
  }

  public boolean isFoil(ItemStack stack) {
    return isMoistureCompass(stack) || super.isFoil(stack);
  }

  @Override
  public void inventoryTick(ItemStack stack, net.minecraft.server.level.ServerLevel level, Entity entity, @Nullable net.minecraft.world.entity.EquipmentSlot equipSlot) {
    if (!(entity instanceof Player)) return;

    if (isMoistureCompass(stack)) {
      LodestoneTracker tracker = stack.get(DataComponents.LODESTONE_TRACKER);
      GlobalPos target = tracker.target().orElse(null);
      if (target != null && target.dimension() == level.dimension()) {
        if (!level.isInWorldBounds(target.pos())) {
          stack.remove(DataComponents.LODESTONE_TRACKER);
        }
      }
    } else {
      if (level.getGameTime() % 40 == 0) {
        tagClosestWaterPosition(stack, level, entity);
      }
    }
  }

  private void tagClosestWaterPosition(ItemStack stack, Level level, Entity entity) {
    AABB box = new AABB(entity.blockPosition()).inflate(64, 64, 64);

    BlockPos closestWaterPos = null;
    double closestDistanceSq = Double.MAX_VALUE;

    for (int x = (int) box.minX; x < box.maxX; x++) {
      for (int y = (int) box.minY; y < box.maxY; y++) {
        for (int z = (int) box.minZ; z < box.maxZ; z++) {
          BlockPos pos = BlockPos.containing(x, y, z);
          BlockState state = level.getBlockState(pos);
          if (state.is(Blocks.WATER)) {
            double distanceSq = entity.blockPosition().distSqr(pos);
            if (distanceSq < closestDistanceSq) {
              closestDistanceSq = distanceSq;
              closestWaterPos = pos;
            }
          }
        }
      }
    }

    if (closestWaterPos != null) {
      stack.set(DataComponents.LODESTONE_TRACKER,
          new LodestoneTracker(Optional.of(GlobalPos.of(level.dimension(), closestWaterPos)), true));
    }
  }

  public InteractionResult useOn(UseOnContext context) {
    BlockPos blockpos = context.getClickedPos();
    Level level = context.getLevel();

    BlockPlaceContext placeContext = new BlockPlaceContext(context);

    if (!placeContext.getLevel().getBlockState(placeContext.getClickedPos()).is(Blocks.WATER)) {
      return super.useOn(context);
    } else {
      level.playSound(null, blockpos, SoundEvents.LODESTONE_COMPASS_LOCK, SoundSource.PLAYERS, 1.0F, 1.0F);

      Player player = context.getPlayer();
      ItemStack itemstack = context.getItemInHand();
      boolean creativeOneItem = !player.getAbilities().instabuild && itemstack.getCount() == 1;

      LodestoneTracker newTracker = new LodestoneTracker(Optional.of(GlobalPos.of(level.dimension(), blockpos)), true);

      if (creativeOneItem) {
        itemstack.set(DataComponents.LODESTONE_TRACKER, newTracker);
      } else {
        ItemStack newCompass = new ItemStack(Items.COMPASS, 1);
        newCompass.set(DataComponents.LODESTONE_TRACKER, newTracker);
        if (!player.getAbilities().instabuild) {
          itemstack.shrink(1);
        }
        if (!player.getInventory().add(newCompass)) {
          player.drop(newCompass, false);
        }
      }
      return InteractionResult.SUCCESS;
    }
  }

  @Override
  public Component getName(ItemStack stack) {
    return isMoistureCompass(stack) ? Component.translatable("item.more_useful_copper.moisture_compass") : super.getName(stack);
  }
}
