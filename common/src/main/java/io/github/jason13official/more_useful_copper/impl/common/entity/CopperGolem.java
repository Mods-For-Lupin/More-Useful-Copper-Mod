package io.github.jason13official.more_useful_copper.impl.common.entity;

import io.github.jason13official.more_useful_copper.impl.common.entity.ai.goal.DefendPlayerTargetGoal;
import io.github.jason13official.more_useful_copper.impl.common.entity.ai.goal.FollowPlayerGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Crackiness;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.MoveTowardsTargetGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.animal.golem.IronGolem;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;

public class CopperGolem extends AbstractPersistentAngerPathfinderMob {

  private static final int HEAL_AMOUNT = 25;
  private static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(CopperGolem.class, EntityDataSerializers.BYTE);

  private int attackAnimationTick;

  public CopperGolem(EntityType<? extends AbstractPersistentAngerPathfinderMob> entityType, Level level) {
    super(entityType, level);
  }

  @Override
  public float maxUpStep() {
    return 1.0F;
  }

  public static AttributeSupplier.Builder createAttributes() {
    return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 100.0F).add(Attributes.MOVEMENT_SPEED, 0.25F).add(Attributes.KNOCKBACK_RESISTANCE, 1.0F)
        .add(Attributes.ATTACK_DAMAGE, 15.0F);
  }

  @Override
  protected void defineSynchedData(SynchedEntityData.Builder builder) {
    super.defineSynchedData(builder);
    builder.define(DATA_FLAGS_ID, (byte) 0);
  }

  @Override
  protected void addAdditionalSaveData(ValueOutput output) {
    super.addAdditionalSaveData(output);
    output.putBoolean("PlayerCreated", this.isPlayerCreated());
    this.addPersistentAngerSaveData(output);
  }

  @Override
  protected void readAdditionalSaveData(ValueInput input) {
    super.readAdditionalSaveData(input);
    this.setPlayerCreated(input.getBooleanOr("PlayerCreated", false));
    this.readPersistentAngerSaveData(this.level(), input);
  }

  @Override
  protected void registerGoals() {
    this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0F, true));
    this.goalSelector.addGoal(2, new MoveTowardsTargetGoal(this, 0.9, 32.0F));
    this.goalSelector.addGoal(3, new FollowPlayerGoal(this, 0.7));
    this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 0.6));
    this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
    this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));

    this.targetSelector.addGoal(1, new DefendPlayerTargetGoal(this));
    this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
    this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<Player>(this, Player.class, 10, true, false, this::isAngryAt));
    this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Mob.class, 5, false, false, (mob, lvl) -> mob instanceof Enemy && !(mob instanceof Creeper)));
    this.targetSelector.addGoal(4, new ResetUniversalAngerTargetGoal<>(this, false));
  }

  @Override
  public void aiStep() {
    super.aiStep();
    if (this.attackAnimationTick > 0) {
      --this.attackAnimationTick;
    }

    if (!this.level().isClientSide()) {
      this.updatePersistentAnger((ServerLevel) this.level(), true);
    }
  }

  @Override
  protected int decreaseAirSupply(int pAir) {
    return pAir;
  }

  @Override
  protected void doPush(Entity pEntity) {
    if (pEntity instanceof Enemy && !(pEntity instanceof Creeper) && this.getRandom().nextInt(20) == 0) {
      this.setTarget((LivingEntity) pEntity);
    }

    super.doPush(pEntity);
  }

  @Override
  public boolean canSpawnSprintParticle() {
    return this.getDeltaMovement().horizontalDistanceSqr() > (double) 2.5000003E-7F && this.random.nextInt(5) == 0;
  }

  public boolean isPlayerCreated() {
    return (this.entityData.get(DATA_FLAGS_ID) & 1) != 0;
  }

  public void setPlayerCreated(boolean pPlayerCreated) {
    byte flags = this.entityData.get(DATA_FLAGS_ID);
    if (pPlayerCreated) {
      this.entityData.set(DATA_FLAGS_ID, (byte) (flags | 1));
    } else {
      this.entityData.set(DATA_FLAGS_ID, (byte) (flags & -2));
    }
  }

  @Override
  public boolean checkSpawnObstruction(LevelReader pLevel) {
    BlockPos pos = this.blockPosition();
    BlockPos below = pos.below();
    BlockState belowState = pLevel.getBlockState(below);
    if (!belowState.entityCanStandOn(pLevel, below, this)) {
      return false;
    } else {
      for (int yOffset = 1; yOffset < 3; ++yOffset) {
        BlockPos above = pos.above(yOffset);
        BlockState aboveState = pLevel.getBlockState(above);
        if (!NaturalSpawner.isValidEmptySpawnBlock(pLevel, above, aboveState, aboveState.getFluidState(), EntityType.IRON_GOLEM)) {
          return false;
        }
      }

      return NaturalSpawner.isValidEmptySpawnBlock(pLevel, pos, pLevel.getBlockState(pos), Fluids.EMPTY.defaultFluidState(), EntityType.IRON_GOLEM) && pLevel.isUnobstructed(this);
    }
  }

  @Override
  public Vec3 getLeashOffset() {
    return new Vec3(0.0F, 0.875F * this.getEyeHeight(), this.getBbWidth() * 0.4F);
  }

  @Override
  protected SoundEvent getHurtSound(DamageSource pDamageSource) {
    return SoundEvents.IRON_GOLEM_HURT;
  }

  @Override
  protected SoundEvent getDeathSound() {
    return SoundEvents.IRON_GOLEM_DEATH;
  }

  public int getAttackAnimationTick() {
    return this.attackAnimationTick;
  }

  @Override
  public void handleEntityEvent(byte pId) {
    if (pId == EntityEvent.START_ATTACKING) {
      this.attackAnimationTick = 10;
      this.playSound(SoundEvents.IRON_GOLEM_ATTACK, 1.0F, 1.0F);
    } else {
      super.handleEntityEvent(pId);
    }
  }

  public Crackiness.Level getCrackiness() {
    return Crackiness.GOLEM.byFraction(this.getHealth() / this.getMaxHealth());
  }

  @Override
  public boolean hurtServer(ServerLevel level, DamageSource source, float amount) {
    Crackiness.Level crackAmount = this.getCrackiness();
    boolean wasHurt = super.hurtServer(level, source, amount);
    if (wasHurt && this.getCrackiness() != crackAmount) {
      this.playSound(SoundEvents.IRON_GOLEM_DAMAGE, 1.0F, 1.0F);
    }
    return wasHurt;
  }

  @Override
  public boolean doHurtTarget(ServerLevel level, Entity pEntity) {
    this.attackAnimationTick = 10;
    level.broadcastEntityEvent(this, (byte) 4);
    float damage = this.getAttackDamage();
    float weightedDamage = (int) damage > 0 ? damage / 2.0F + (float) this.random.nextInt((int) damage) : damage;
    boolean didHurt = pEntity.hurtServer(level, this.damageSources().mobAttack(this), weightedDamage);
    if (didHurt) {
      double calculatedknockback;
      if (pEntity instanceof LivingEntity living) {
        calculatedknockback = living.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
      } else {
        calculatedknockback = 0.0F;
      }

      double weightedKnockback = Math.max(0.0F, 1.0F - calculatedknockback);
      pEntity.setDeltaMovement(pEntity.getDeltaMovement().add(0.0F, 0.4F * weightedKnockback, 0.0F));
    }

    this.playSound(SoundEvents.IRON_GOLEM_ATTACK, 1.0F, 1.0F);
    return didHurt;
  }

  private float getAttackDamage() {
    return (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE);
  }
}
