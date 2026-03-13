package io.github.jason13official.more_useful_copper.impl.common.item;

import io.github.jason13official.more_useful_copper.impl.common.entity.CopperBottomBoat;
import io.github.jason13official.more_useful_copper.impl.common.registry.ModEntities;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraft.world.phys.Vec3;

public class CopperBottomBoatItem extends Item {

  private static final Predicate<Entity> ENTITY_PREDICATE = EntitySelector.NO_SPECTATORS.and(Entity::isPickable);

  public CopperBottomBoatItem(Properties properties) {
    super(properties);
  }

  public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
    ItemStack itemstack = player.getItemInHand(hand);
    HitResult hitresult = getPlayerPOVHitResult(level, player, Fluid.ANY);
    if (hitresult.getType() == Type.MISS) {
      return InteractionResultHolder.pass(itemstack);
    } else {
      Vec3 vec3 = player.getViewVector(1.0F);
      double d0 = 5.0F;
      List<Entity> list = level.getEntities(player, player.getBoundingBox().expandTowards(vec3.scale(d0)).inflate(1.0F), ENTITY_PREDICATE);
      if (!list.isEmpty()) {
        Vec3 vec31 = player.getEyePosition();

        for (Entity entity : list) {
          AABB aabb = entity.getBoundingBox().inflate(entity.getPickRadius());
          if (aabb.contains(vec31)) {
            return InteractionResultHolder.pass(itemstack);
          }
        }
      }

      if (hitresult.getType() == Type.BLOCK) {
        CopperBottomBoat copperBoat = new CopperBottomBoat(level, hitresult.getLocation().x, hitresult.getLocation().y, hitresult.getLocation().z);
        copperBoat.setYRot(player.getYRot());
        if (!level.noCollision(copperBoat, copperBoat.getBoundingBox())) {
          return InteractionResultHolder.fail(itemstack);
        } else {
          if (!level.isClientSide) {
            level.addFreshEntity(copperBoat);
            level.gameEvent(player, GameEvent.ENTITY_PLACE, hitresult.getLocation());
            if (!player.getAbilities().instabuild) {
              itemstack.shrink(1);
            }
          }

          player.awardStat(Stats.ITEM_USED.get(this));
          return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
        }
      } else {
        return InteractionResultHolder.pass(itemstack);
      }
    }
  }
}

