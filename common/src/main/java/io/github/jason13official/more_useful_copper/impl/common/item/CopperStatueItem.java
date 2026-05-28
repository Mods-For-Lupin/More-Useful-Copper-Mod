package io.github.jason13official.more_useful_copper.impl.common.item;

import io.github.jason13official.more_useful_copper.impl.common.entity.CopperStatue;
import io.github.jason13official.more_useful_copper.impl.common.registry.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class CopperStatueItem extends Item {

  public final CopperStatue.Type type;

  public CopperStatueItem(CopperStatue.Type type, Properties properties) {
    super(properties);

    this.type = type;
  }

  @Override
  public InteractionResult useOn(UseOnContext context) {
    Direction direction = context.getClickedFace();

    if (direction == Direction.DOWN) {
      return InteractionResult.FAIL;
    }

    Level level = context.getLevel();
    BlockPlaceContext blockPlaceContext = new BlockPlaceContext(context);
    BlockPos blockPos = blockPlaceContext.getClickedPos();
    ItemStack itemStack = context.getItemInHand();
    Vec3 vec3 = Vec3.atBottomCenterOf(blockPos);
    AABB aABB = ModEntities.COPPER_STATUE.getDimensions().makeBoundingBox(vec3.x(), vec3.y(), vec3.z());

    if (level.noCollision(null, aABB) && level.getEntities(null, aABB).isEmpty()) {
      return trySpawningStatue(context, level, itemStack, blockPos);
    }

    return InteractionResult.FAIL;
  }

  private @NotNull InteractionResult trySpawningStatue(UseOnContext context, Level level, ItemStack itemStack, BlockPos blockPos) {
    if (level instanceof ServerLevel serverLevel) {
      CopperStatue statue = ModEntities.COPPER_STATUE.create(serverLevel, EntityType.createDefaultStackConfig(serverLevel, itemStack, context.getPlayer()), blockPos, EntitySpawnReason.SPAWN_ITEM_USE, true, true);

      if (statue != null) {
        statue.setVariant(this.type);
      } else {
        return InteractionResult.FAIL;
      }

      float f = (float) Mth.floor((Mth.wrapDegrees(context.getRotation() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
      statue.snapTo(statue.getX(), statue.getY(), statue.getZ(), f, 0.0F);
      serverLevel.addFreshEntityWithPassengers(statue);
      level.playSound(null, statue.getX(), statue.getY(), statue.getZ(), SoundEvents.ARMOR_STAND_PLACE, SoundSource.BLOCKS, 0.75F, 0.8F);
      statue.gameEvent(GameEvent.ENTITY_PLACE, context.getPlayer());
    }

    itemStack.shrink(1);
    return InteractionResult.SUCCESS;
  }
}
