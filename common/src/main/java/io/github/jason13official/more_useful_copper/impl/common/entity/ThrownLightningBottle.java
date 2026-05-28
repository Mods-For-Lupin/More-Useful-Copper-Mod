package io.github.jason13official.more_useful_copper.impl.common.entity;

import io.github.jason13official.more_useful_copper.impl.common.registry.ModEntities;
import io.github.jason13official.more_useful_copper.impl.common.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

public class ThrownLightningBottle extends ThrowableItemProjectile {

  public ThrownLightningBottle(EntityType<? extends ThrownLightningBottle> entityType, Level level) {
    super(entityType, level);
  }

  public ThrownLightningBottle(Level level, LivingEntity shooter) {
    super(ModEntities.LIGHTNING_BOTTLE, shooter, level, new net.minecraft.world.item.ItemStack(ModItems.LIGHTNING_BOTTLE));
  }

  public ThrownLightningBottle(Level level, double x, double y, double z) {
    super(ModEntities.LIGHTNING_BOTTLE, x, y, z, level, new net.minecraft.world.item.ItemStack(ModItems.LIGHTNING_BOTTLE));
  }

  public void handleEntityEvent(byte id) {
    if (id == EntityEvent.DEATH) {
      double d0 = 0.08;

      for (int i = 0; i < 8; ++i) {
        this.level().addParticle(new ItemParticleOption(ParticleTypes.ITEM, this.getItem().getItem()), this.getX(), this.getY(), this.getZ(), ((double) this.random.nextFloat() - (double) 0.5F) * d0,
            ((double) this.random.nextFloat() - (double) 0.5F) * d0, ((double) this.random.nextFloat() - (double) 0.5F) * d0);
      }
    }

  }

  protected void onHit(HitResult result) {
    super.onHit(result);
    if (this.level() instanceof ServerLevel level) {
      EntityType.LIGHTNING_BOLT.spawn(level, BlockPos.containing(result.getLocation()), EntitySpawnReason.EVENT);
      this.level().broadcastEntityEvent(this, EntityEvent.DEATH);
      this.discard();
    }

  }

  protected Item getDefaultItem() {
    return ModItems.LIGHTNING_BOTTLE;
  }
}

