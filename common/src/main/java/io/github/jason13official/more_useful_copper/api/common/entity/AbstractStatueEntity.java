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
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.vehicle.minecart.AbstractMinecart;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractStatueEntity extends NoInventoryLivingEntity {

  /// Matches only rideable minecarts — used in [pushEntities] to replicate [ArmorStand] push behaviour.
  private static final Predicate<Entity> RIDABLE_MINECARTS = entity -> entity instanceof AbstractMinecart minecart && minecart.isRideable();

  public long lastHit;

  public AbstractStatueEntity(EntityType<? extends AbstractStatueEntity> entityType, Level level) {
    super(entityType, level);
  }

  @Override
  public float maxUpStep() {
    return 0.0F;
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

  /// Pushes any rideable minecarts that overlap this entity's bounding box. Statues don't interact with regular entity push logic, so this is the only push they perform.
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

  /// Handles damage with armor-stand-like behaviour:
  /// - Bypasses-invulnerability and creative players kill immediately.
  /// - Fire/explosion routes through [damagedByFireOrExplosion].
  /// - Only player-attack and arrow damage can break the statue; a second hit within 5 ticks destroys it.
  ///
  /// @return `true` if the damage was accepted (including wobble-only hits)
  @Override
  public boolean hurtServer(ServerLevel serverLevel, DamageSource source, float amount) {

    if (damagePersists(serverLevel, source)) {
      return false;
    }

    if (damagedByFireOrExplosion(serverLevel, source)) {
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
      this.kill(serverLevel);
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
      this.kill(serverLevel);
    }

    return true;
  }

  /// Handles fire and explosion damage types, mirroring vanilla armor-stand logic. Explosions break immediately; fire damage is accumulated or sets the statue on fire.
  ///
  /// @return `true` if this method consumed the damage (caller should return early)
  private boolean damagedByFireOrExplosion(ServerLevel serverLevel, DamageSource source) {
    if (source.is(DamageTypeTags.IS_EXPLOSION)) {
      this.brokenByAnything(serverLevel, source);
      this.kill(serverLevel);
      return true;
    } else if (source.is(DamageTypeTags.IGNITES_ARMOR_STANDS)) {
      if (this.isOnFire()) {
        this.causeDamage(serverLevel, source, 0.15F);
      } else {
        this.igniteForSeconds(5.0F);
      }
      return true;
    } else if (source.is(DamageTypeTags.BURNS_ARMOR_STANDS) && this.getHealth() > 0.5F) {
      this.causeDamage(serverLevel, source, 4.0F);
      return true;
    }
    return false;
  }

  /// Returns `true` if damage should be silently ignored — either because the entity is already dead/client-side, damage bypasses invulnerability and kills it, or it's invulnerable to this source.
  private boolean damagePersists(ServerLevel serverLevel, DamageSource source) {
    if (this.isRemoved()) {
      return true;
    } else if (source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
      this.kill(serverLevel);
      return true;
    } else {
      return this.isInvulnerableTo(serverLevel, source);
    }
  }

  @Override
  public void handleEntityEvent(byte id) {
    if (id == EntityEvent.ARMORSTAND_WOBBLE) {
      if (this.level().isClientSide()) {
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

  private void causeDamage(ServerLevel serverLevel, DamageSource damageSource, float amount) {
    float f = this.getHealth();
    f -= amount;
    if (f <= 0.5F) {
      this.brokenByAnything(serverLevel, damageSource);
      this.kill(serverLevel);
    } else {
      this.setHealth(f);
      this.gameEvent(GameEvent.ENTITY_DAMAGE, damageSource.getEntity());
    }
  }

  /// Drops the appropriate statue item for the concrete variant, preserving any custom name, then delegates to [brokenByAnything] for sound and loot drops.
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
      itemStack.set(DataComponents.CUSTOM_NAME, this.getCustomName());
    }

    Block.popResource(this.level(), this.blockPosition(), itemStack);
    this.brokenByAnything(null, damageSource);
  }

  private void brokenByAnything(ServerLevel serverLevel, DamageSource damageSource) {
    this.playBrokenSound();
    ServerLevel level = serverLevel != null ? serverLevel : (this.level() instanceof ServerLevel sl ? sl : null);
    if (level != null) {
      this.dropAllDeathLoot(level, damageSource);
    }
  }

  private void playBrokenSound() {
    this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ARMOR_STAND_BREAK, this.getSoundSource(), 1.0F, 1.0F);
  }

  @Override
  protected void tickHeadTurn(float yBodyRotT) {
    this.yBodyRotO = this.yRotO;
    this.yBodyRot = this.getYRot();
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
  public void kill(ServerLevel level) {
    this.remove(RemovalReason.KILLED);
    this.gameEvent(GameEvent.ENTITY_DIE);
  }

  @Override
  public boolean ignoreExplosion(Explosion explosion) {
    return this.isInvisible();
  }

  /// Sets or clears a single bit within a packed byte flag field.
  ///
  /// @param oldBit the existing byte
  /// @param offset the bit mask (e.g. `0x01`, `0x02`)
  /// @param value  `true` to set the bit, `false` to clear it
  /// @return the modified byte
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
