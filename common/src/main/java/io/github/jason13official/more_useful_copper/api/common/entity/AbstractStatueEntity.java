package io.github.jason13official.more_useful_copper.api.common.entity;

import io.github.jason13official.more_useful_copper.impl.common.entity.CopperStatue;
import io.github.jason13official.more_useful_copper.impl.common.registry.ModItems;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.entity.vehicle.AbstractMinecart.Type;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractStatueEntity extends NoInventoryLivingEntity {

  /// mimics a similar field in {@link ArmorStand}
  private static final Predicate<Entity> RIDABLE_MINECARTS = entity -> entity instanceof AbstractMinecart && ((AbstractMinecart) entity).getMinecartType() == Type.RIDEABLE;

  public long lastHit;

  public AbstractStatueEntity(EntityType<? extends AbstractStatueEntity> entityType, Level level) {
    super(entityType, level);
    this.setMaxUpStep(0.0f);
  }

  public AbstractStatueEntity(EntityType<? extends AbstractStatueEntity> entityType, Level level, double x, double y, double z) {
    this(entityType, level);
    this.setPos(x, y, z);
  }

  @Override
  public void refreshDimensions() {
    double d = this.getX();
    double e = this.getY();
    double f = this.getZ();
    super.refreshDimensions();
    this.setPos(d, e, f);
  }

  @Override
  public boolean isEffectiveAi() {
    return super.isEffectiveAi() && this.hasPhysics();
  }

  private boolean hasPhysics() {
    return !this.isNoGravity();
  }

  @Override
  public boolean isPushable() {
    return false;
  }

  @Override
  protected void doPush(Entity entity) {
  }

  @Override
  protected void pushEntities() {
    List<Entity> list = this.level().getEntities(this, this.getBoundingBox(), RIDABLE_MINECARTS);

    for (int i = 0; i < list.size(); i++) {
      Entity entity = list.get(i);
      if (this.distanceToSqr(entity) <= 0.2) {
        entity.push(this);
      }
    }
  }

  @Override
  public boolean hurt(DamageSource source, float amount) {

    if (damagePersists(source)) {
      return false;
    }

    if (damagedByFireOrExplosion(source)) {
      return false;
    }

    boolean damageSourceIsArrow = source.getDirectEntity() instanceof AbstractArrow;
    boolean arrowHasPiercing = damageSourceIsArrow && ((AbstractArrow) source.getDirectEntity()).getPierceLevel() > 0;
    boolean playerIsDamageSource = "player".equals(source.getMsgId());

    if (!playerIsDamageSource && !damageSourceIsArrow) {
      return false;
    }

    if (source.getEntity() instanceof Player player && !player.getAbilities().mayBuild) {
      return false;
    }

    if (source.isCreativePlayer()) {
      this.playBrokenSound();
      this.showBreakingParticles();
      this.kill();
      return arrowHasPiercing;
    }

    long l = this.level().getGameTime();
    if (l - this.lastHit > 5L && !damageSourceIsArrow) {
      this.level().broadcastEntityEvent(this, EntityEvent.ARMORSTAND_WOBBLE);
      this.gameEvent(GameEvent.ENTITY_DAMAGE, source.getEntity());
      this.lastHit = l;
    } else {
      this.brokenByPlayer(source);
      this.showBreakingParticles();
      this.kill();
    }

    return true;
  }

  private boolean damagedByFireOrExplosion(DamageSource source) {
    if (source.is(DamageTypeTags.IS_EXPLOSION)) {
      this.brokenByAnything(source);
      this.kill();
      return true;
    } else if (source.is(DamageTypeTags.IGNITES_ARMOR_STANDS)) {
      if (this.isOnFire()) {
        this.causeDamage(source, 0.15F);
      } else {
        this.setSecondsOnFire(5);
      }
      return true;
    } else if (source.is(DamageTypeTags.BURNS_ARMOR_STANDS) && this.getHealth() > 0.5F) {
      this.causeDamage(source, 4.0F);
      return true;
    }
    return false;
  }

  private boolean damagePersists(DamageSource source) {
    if (this.level().isClientSide || this.isRemoved()) {
      return true;
    } else if (source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
      this.kill();
      return true;
    } else
      return this.isInvulnerableTo(source);
  }

  @Override
  public void handleEntityEvent(byte id) {
    if (id == EntityEvent.ARMORSTAND_WOBBLE) {
      if (this.level().isClientSide) {
        this.level().playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.ARMOR_STAND_HIT, this.getSoundSource(), 0.3F, 1.0F, false);
        this.lastHit = this.level().getGameTime();
      }
    } else {
      super.handleEntityEvent(id);
    }
  }

  @Override
  public boolean shouldRenderAtSqrDistance(double distance) {
    double d = this.getBoundingBox().getSize() * 4.0;
    if (Double.isNaN(d) || d == 0.0) {
      d = 4.0;
    }

    d *= 64.0;
    return distance < d * d;
  }

  private void showBreakingParticles() {
    if (this.level() instanceof ServerLevel) {
      ((ServerLevel) this.level()).sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, Blocks.OAK_PLANKS.defaultBlockState()), this.getX(), this.getY(0.6666666666666666), this.getZ(), 10,
          this.getBbWidth() / 4.0F, this.getBbHeight() / 4.0F, this.getBbWidth() / 4.0F, 0.05);
    }
  }

  private void causeDamage(DamageSource damageSource, float amount) {
    float f = this.getHealth();
    f -= amount;
    if (f <= 0.5F) {
      this.brokenByAnything(damageSource);
      this.kill();
    } else {
      this.setHealth(f);
      this.gameEvent(GameEvent.ENTITY_DAMAGE, damageSource.getEntity());
    }
  }

  private void brokenByPlayer(DamageSource damageSource) {

    ItemStack toReturn = ItemStack.EMPTY;

    if (this instanceof CopperStatue copperStatue) {
      CopperStatue.Type type = copperStatue.getVariant();

      toReturn = switch (type) {
        case CREEPER -> new ItemStack(ModItems.COPPER_STATUE_CREEPER);
        case SKELETON -> new ItemStack(ModItems.COPPER_STATUE_SKELETON);
        case SPIDER -> new ItemStack(ModItems.COPPER_STATUE_SPIDER);
        case ZOMBIE -> new ItemStack(ModItems.COPPER_STATUE_ZOMBIE);
      };
    }

    ItemStack itemStack = toReturn;
    if (this.hasCustomName()) {
      itemStack.setHoverName(this.getCustomName());
    }

    Block.popResource(this.level(), this.blockPosition(), itemStack);
    this.brokenByAnything(damageSource);
  }

  private void brokenByAnything(DamageSource damageSource) {
    this.playBrokenSound();
    this.dropAllDeathLoot(damageSource);
  }

  private void playBrokenSound() {
    this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ARMOR_STAND_BREAK, this.getSoundSource(), 1.0F, 1.0F);
  }

  @Override
  protected float tickHeadTurn(float yRot, float animStep) {
    this.yBodyRotO = this.yRotO;
    this.yBodyRot = this.getYRot();
    return 0.0F;
  }

  @Override
  protected float getStandingEyeHeight(Pose pose, EntityDimensions dimensions) {
    return dimensions.height * (this.isBaby() ? 0.5F : 0.9F);
  }

  @Override
  public double getMyRidingOffset() {
    return 0.1F;
  }

  @Override
  public void travel(Vec3 travelVector) {
    if (this.hasPhysics()) {
      super.travel(travelVector);
    }
  }

  @Override
  public void setYBodyRot(float yBodyRot) {
    this.yBodyRotO = this.yRotO = yBodyRot;
    this.yHeadRotO = this.yHeadRot = yBodyRot;
  }

  @Override
  public void setYHeadRot(float yHeadRot) {
    this.yBodyRotO = this.yRotO = yHeadRot;
    this.yHeadRotO = this.yHeadRot = yHeadRot;
  }

  @Override
  public void kill() {
    this.remove(RemovalReason.KILLED);
    this.gameEvent(GameEvent.ENTITY_DIE);
  }

  @Override
  public boolean ignoreExplosion() {
    return this.isInvisible();
  }

  private byte setBit(byte oldBit, int offset, boolean value) {
    if (value) {
      oldBit = (byte) (oldBit | offset);
    } else {
      oldBit = (byte) (oldBit & ~offset);
    }

    return oldBit;
  }

  @Override
  public boolean skipAttackInteraction(Entity entity) {
    return entity instanceof Player && !this.level().mayInteract((Player) entity, this.blockPosition());
  }

  @Override
  public Fallsounds getFallSounds() {
    return new Fallsounds(SoundEvents.ARMOR_STAND_FALL, SoundEvents.ARMOR_STAND_FALL);
  }

  @Nullable
  @Override
  protected SoundEvent getHurtSound(DamageSource damageSource) {
    return SoundEvents.ARMOR_STAND_HIT;
  }

  @Nullable
  @Override
  protected SoundEvent getDeathSound() {
    return SoundEvents.ARMOR_STAND_BREAK;
  }

  @Override
  public void thunderHit(ServerLevel level, LightningBolt lightning) {
  }

  @Override
  public boolean isAffectedByPotions() {
    return false;
  }

  @Override
  public boolean attackable() {
    return false;
  }

  @Override
  public ItemStack getPickResult() {
    return new ItemStack(Items.ARMOR_STAND);
  }

  @Override
  public boolean canBeSeenByAnyone() {
    return !this.isInvisible();
  }
}
