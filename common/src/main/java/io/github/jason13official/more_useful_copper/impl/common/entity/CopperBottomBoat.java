package io.github.jason13official.more_useful_copper.impl.common.entity;

import com.google.common.collect.Lists;
import com.google.common.collect.UnmodifiableIterator;
import io.github.jason13official.more_useful_copper.impl.common.registry.ModEntities;
import io.github.jason13official.more_useful_copper.impl.common.registry.ModItems;
import io.github.jason13official.more_useful_copper.impl.common.tags.ModItemTags;
import java.util.List;
import java.util.function.IntFunction;
import net.minecraft.BlockUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ServerboundPaddleBoatPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.ByIdMap.OutOfBoundsStrategy;
import net.minecraft.util.Mth;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WaterlilyBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class CopperBottomBoat extends Boat {

  public static final int PADDLE_LEFT = 0;
  public static final int PADDLE_RIGHT = 1;
  public static final double PADDLE_SOUND_TIME = (float) Math.PI / 4F;
  public static final int BUBBLE_TIME = 60;
  public static final EntityDataAccessor<Boolean> WAXED = SynchedEntityData.defineId(CopperBottomBoat.class, EntityDataSerializers.BOOLEAN);
  public static final EntityDataAccessor<Integer> OXIDIZATION_LEVEL = SynchedEntityData.defineId(CopperBottomBoat.class, EntityDataSerializers.INT);
  private static final EntityDataAccessor<Integer> DATA_ID_HURT = SynchedEntityData.defineId(CopperBottomBoat.class, EntityDataSerializers.INT);
  private static final EntityDataAccessor<Integer> DATA_ID_HURTDIR = SynchedEntityData.defineId(CopperBottomBoat.class, EntityDataSerializers.INT);
  private static final EntityDataAccessor<Float> DATA_ID_DAMAGE = SynchedEntityData.defineId(CopperBottomBoat.class, EntityDataSerializers.FLOAT);
  private static final EntityDataAccessor<Integer> DATA_ID_TYPE = SynchedEntityData.defineId(CopperBottomBoat.class, EntityDataSerializers.INT);
  private static final EntityDataAccessor<Boolean> DATA_ID_PADDLE_LEFT = SynchedEntityData.defineId(CopperBottomBoat.class, EntityDataSerializers.BOOLEAN);
  private static final EntityDataAccessor<Boolean> DATA_ID_PADDLE_RIGHT = SynchedEntityData.defineId(CopperBottomBoat.class, EntityDataSerializers.BOOLEAN);
  private static final EntityDataAccessor<Integer> DATA_ID_BUBBLE_TIME = SynchedEntityData.defineId(CopperBottomBoat.class, EntityDataSerializers.INT);
  private static final int TIME_TO_EJECT = 60;
  private static final float PADDLE_SPEED = ((float) Math.PI / 8F);

  private final float[] paddlePositions;
  private float invFriction;
  private float outOfControlTicks;
  private float deltaRotation;
  private int lerpSteps;
  private double lerpX;
  private double lerpY;
  private double lerpZ;
  private double lerpYRot;
  private double lerpXRot;
  private boolean inputLeft;
  private boolean inputRight;
  private boolean inputUp;
  private boolean inputDown;
  private double waterLevel;
  private float landFriction;
  private Status status;
  private Status oldStatus;
  private double lastYd;
  private boolean isAboveBubbleColumn;
  private boolean bubbleColumnDirectionIsDown;
  private float bubbleMultiplier;
  private float bubbleAngle;
  private float bubbleAngleO;

  public CopperBottomBoat(EntityType<? extends CopperBottomBoat> entityType, Level level) {
    super(entityType, level);
    this.paddlePositions = new float[2];
    this.blocksBuilding = true;
  }

  public CopperBottomBoat(Level level, double x, double y, double z) {
    this(ModEntities.COPPER_BOTTOM_BOAT, level);
    this.setPos(x, y, z);
    this.xo = x;
    this.yo = y;
    this.zo = z;
  }

  /// Returns `true` if `entity` should be able to collide with `vehicle`, excluding same-vehicle passengers.
  public static boolean canVehicleCollide(Entity vehicle, Entity entity) {
    return (entity.canBeCollidedWith() || entity.isPushable()) && !vehicle.isPassengerOfSameVehicle(entity);
  }

  protected float getEyeHeight(Pose pose, EntityDimensions size) {
    return size.height();
  }

  protected MovementEmission getMovementEmission() {
    return MovementEmission.EVENTS;
  }

  @Override
  protected void defineSynchedData(SynchedEntityData.Builder builder) {
    builder.define(DATA_ID_HURT, 0);
    builder.define(DATA_ID_HURTDIR, 1);
    builder.define(DATA_ID_DAMAGE, 0.0F);
    builder.define(DATA_ID_TYPE, Type.OAK.ordinal());
    builder.define(DATA_ID_PADDLE_LEFT, false);
    builder.define(DATA_ID_PADDLE_RIGHT, false);
    builder.define(DATA_ID_BUBBLE_TIME, 0);
    builder.define(OXIDIZATION_LEVEL, 0);
    builder.define(WAXED, false);
  }

  public boolean canCollideWith(Entity entity) {
    return canVehicleCollide(this, entity);
  }

  public boolean canBeCollidedWith() {
    return true;
  }

  public boolean isPushable() {
    return true;
  }

  public Vec3 getRelativePortalPosition(Direction.Axis axis, BlockUtil.FoundRectangle portal) {
    return LivingEntity.resetForwardDirectionOfRelativePortalPosition(super.getRelativePortalPosition(axis, portal));
  }

  public double getPassengersRidingOffset() {
    return -0.1;
  }

  public boolean hurt(DamageSource source, float amount) {
    if (this.isInvulnerableTo(source)) {
      return false;
    } else if (!this.level().isClientSide && !this.isRemoved()) {
      this.setHurtDir(-this.getHurtDir());
      this.setHurtTime(10);
      this.setDamage(this.getDamage() + amount * 10.0F);
      this.markHurt();
      this.gameEvent(GameEvent.ENTITY_DAMAGE, source.getEntity());
      boolean flag = source.getEntity() instanceof Player && ((Player) source.getEntity()).getAbilities().instabuild;
      if (flag || this.getDamage() > 40.0F) {
        if (!flag && this.level().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
          this.destroy(source);
        }

        this.discard();
      }

      return true;
    } else {
      return true;
    }
  }

  protected void destroy(DamageSource damageSource) {
    this.spawnAtLocation(this.getDropItem());
  }

  public void onAboveBubbleCol(boolean downwards) {
    if (!this.level().isClientSide) {
      this.isAboveBubbleColumn = true;
      this.bubbleColumnDirectionIsDown = downwards;
      if (this.getBubbleTime() == 0) {
        this.setBubbleTime(60);
      }
    }

    this.level().addParticle(ParticleTypes.SPLASH, this.getX() + (double) this.random.nextFloat(), this.getY() + 0.7, this.getZ() + (double) this.random.nextFloat(), 0.0F, 0.0F, 0.0F);
    if (this.random.nextInt(20) == 0) {
      this.level().playLocalSound(this.getX(), this.getY(), this.getZ(), this.getSwimSplashSound(), this.getSoundSource(), 1.0F, 0.8F + 0.4F * this.random.nextFloat(), false);
      this.gameEvent(GameEvent.SPLASH, this.getControllingPassenger());
    }

  }

  public void push(Entity entity) {
    if (entity instanceof CopperBottomBoat) {
      if (entity.getBoundingBox().minY < this.getBoundingBox().maxY) {
        super.push(entity);
      }
    } else if (entity.getBoundingBox().minY <= this.getBoundingBox().minY) {
      super.push(entity);
    }

  }

  public Item getDropItem() {

    return ModItems.COPPER_BOTTOM_BOAT;

//    Item item;
//    switch (this.getVariant().ordinal()) {
//      case 1 -> item = Items.SPRUCE_CopperBottomBoat;
//      case 2 -> item = Items.BIRCH_CopperBottomBoat;
//      case 3 -> item = Items.JUNGLE_CopperBottomBoat;
//      case 4 -> item = Items.ACACIA_CopperBottomBoat;
//      case 5 -> item = Items.CHERRY_CopperBottomBoat;
//      case 6 -> item = Items.DARK_OAK_CopperBottomBoat;
//      case 7 -> item = Items.MANGROVE_CopperBottomBoat;
//      case 8 -> item = Items.BAMBOO_RAFT;
//      default -> item = Items.OAK_CopperBottomBoat;
//    }
//
//    return item;
  }

  public void animateHurt(float yaw) {
    this.setHurtDir(-this.getHurtDir());
    this.setHurtTime(10);
    this.setDamage(this.getDamage() * 11.0F);
  }

  public boolean isPickable() {
    return !this.isRemoved();
  }

  public void lerpTo(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
    this.lerpX = x;
    this.lerpY = y;
    this.lerpZ = z;
    this.lerpYRot = yaw;
    this.lerpXRot = pitch;
    this.lerpSteps = 10;
  }

  public Direction getMotionDirection() {
    return this.getDirection().getClockWise();
  }

  public void tick() {
    this.oldStatus = this.status;
    this.status = this.getStatus();
    if (this.status != Status.UNDER_WATER && this.status != Status.UNDER_FLOWING_WATER) {
      this.outOfControlTicks = 0.0F;
    } else {
      ++this.outOfControlTicks;
    }

    if (!this.level().isClientSide && this.outOfControlTicks >= 60.0F) {
      this.ejectPassengers();
    }

    if (!this.level().isClientSide()) {
      if (this.level().getRandom().nextFloat() <= 0.001) {
        if (!this.isWaxed()) {
          this.oxidize();
        }
      }
    }

    if (this.getHurtTime() > 0) {
      this.setHurtTime(this.getHurtTime() - 1);
    }

    if (this.getDamage() > 0.0F) {
      this.setDamage(this.getDamage() - 1.0F);
    }

    // super.tick();
    this.tickLerp();
    if (this.isControlledByLocalInstance()) {
      if (!(this.getFirstPassenger() instanceof Player)) {
        this.setPaddleState(false, false);
      }

      this.floatCopperBottomBoat();
      if (this.level().isClientSide) {
        this.controlCopperBottomBoat();
        this.level().sendPacketToServer(new ServerboundPaddleBoatPacket(this.getPaddleState(0), this.getPaddleState(1)));
      }

      double speedReduction = this.getOxidizationLevel() * 0.25D;

      if (this.getOxidizationLevel() == 3) {
      }
      speedReduction *= 2;

      this.move(MoverType.SELF, this.getDeltaMovement().multiply(2.0D - speedReduction, 2.0D - speedReduction, 2.0D - speedReduction));
    } else {
      this.setDeltaMovement(Vec3.ZERO);
    }

    this.tickBubbleColumn();

    for (int i = 0; i <= 1; ++i) {
      if (this.getPaddleState(i)) {
        if (!this.isSilent() && (double) (this.paddlePositions[i] % ((float) Math.PI * 2F)) <= (double) ((float) Math.PI / 4F)
            && (double) ((this.paddlePositions[i] + ((float) Math.PI / 8F)) % ((float) Math.PI * 2F)) >= (double) ((float) Math.PI / 4F)) {
          SoundEvent soundevent = this.getPaddleSound();
          if (soundevent != null) {
            Vec3 vec3 = this.getViewVector(1.0F);
            double d0 = i == 1 ? -vec3.z : vec3.z;
            double d1 = i == 1 ? vec3.x : -vec3.x;
            this.level().playSound(null, this.getX() + d0, this.getY(), this.getZ() + d1, soundevent, this.getSoundSource(), 1.0F, 0.8F + 0.4F * this.random.nextFloat());
          }
        }

        float[] var10000 = this.paddlePositions;
        var10000[i] += ((float) Math.PI / 8F);
      } else {
        this.paddlePositions[i] = 0.0F;
      }
    }

    this.checkInsideBlocks();
    List<Entity> list = this.level().getEntities(this, this.getBoundingBox().inflate(0.2F, -0.01F, 0.2F), EntitySelector.pushableBy(this));
    if (!list.isEmpty()) {
      boolean flag = !this.level().isClientSide && !(this.getControllingPassenger() instanceof Player);

      for (int j = 0; j < list.size(); ++j) {
        Entity entity = list.get(j);
        if (!entity.hasPassenger(this)) {
          if (flag && this.getPassengers().size() < this.getMaxPassengers() && !entity.isPassenger() && this.hasEnoughSpaceFor(entity) && entity instanceof LivingEntity
              && !(entity instanceof WaterAnimal) && !(entity instanceof Player)) {
            entity.startRiding(this);
          } else {
            this.push(entity);
          }
        }
      }
    }

  }

  private void tickBubbleColumn() {
    if (this.level().isClientSide) {
      int i = this.getBubbleTime();
      if (i > 0) {
        this.bubbleMultiplier += 0.05F;
      } else {
        this.bubbleMultiplier -= 0.1F;
      }

      this.bubbleMultiplier = Mth.clamp(this.bubbleMultiplier, 0.0F, 1.0F);
      this.bubbleAngleO = this.bubbleAngle;
      this.bubbleAngle = 10.0F * (float) Math.sin(0.5F * (float) this.level().getGameTime()) * this.bubbleMultiplier;
    } else {
      if (!this.isAboveBubbleColumn) {
        this.setBubbleTime(0);
      }

      int k = this.getBubbleTime();
      if (k > 0) {
        --k;
        this.setBubbleTime(k);
        int j = 60 - k - 1;
        if (j > 0 && k == 0) {
          this.setBubbleTime(0);
          Vec3 vec3 = this.getDeltaMovement();
          if (this.bubbleColumnDirectionIsDown) {
            this.setDeltaMovement(vec3.add(0.0F, -0.7, 0.0F));
            this.ejectPassengers();
          } else {
            this.setDeltaMovement(vec3.x, this.hasPassenger((p_150274_) -> p_150274_ instanceof Player) ? 2.7 : 0.6, vec3.z);
          }
        }

        this.isAboveBubbleColumn = false;
      }
    }

  }

  @Nullable
  protected SoundEvent getPaddleSound() {
    switch (this.getStatus().ordinal()) {
      case 0:
      case 1:
      case 2:
        return SoundEvents.BOAT_PADDLE_WATER;
      case 3:
        return SoundEvents.BOAT_PADDLE_LAND;
      case 4:
      default:
        return null;
    }
  }

  private void tickLerp() {
    if (this.isControlledByLocalInstance()) {
      this.lerpSteps = 0;
      this.syncPacketPositionCodec(this.getX(), this.getY(), this.getZ());
    }

    if (this.lerpSteps > 0) {
      double d0 = this.getX() + (this.lerpX - this.getX()) / (double) this.lerpSteps;
      double d1 = this.getY() + (this.lerpY - this.getY()) / (double) this.lerpSteps;
      double d2 = this.getZ() + (this.lerpZ - this.getZ()) / (double) this.lerpSteps;
      double d3 = Mth.wrapDegrees(this.lerpYRot - (double) this.getYRot());
      this.setYRot(this.getYRot() + (float) d3 / (float) this.lerpSteps);
      this.setXRot(this.getXRot() + (float) (this.lerpXRot - (double) this.getXRot()) / (float) this.lerpSteps);
      --this.lerpSteps;
      this.setPos(d0, d1, d2);
      this.setRot(this.getYRot(), this.getXRot());
    }

  }

  public void setPaddleState(boolean left, boolean right) {
    this.entityData.set(DATA_ID_PADDLE_LEFT, left);
    this.entityData.set(DATA_ID_PADDLE_RIGHT, right);
  }

  /// Returns the interpolated paddle rotation angle for the given side and partial tick, used by the renderer.
  ///
  /// @param side      `0` = left, `1` = right
  /// @param limbSwing partial tick interpolation factor
  /// @return angle in radians, or `0` if the paddle is not actively rowing
  public float getRowingTime(int side, float limbSwing) {
    return this.getPaddleState(side) ? Mth.clampedLerp(this.paddlePositions[side] - ((float) Math.PI / 8F), this.paddlePositions[side], limbSwing) : 0.0F;
  }

  /// Determines the boat's current movement medium in priority order: fully submerged → in water → on land → in air.
  ///
  /// @return the current [Status]
  private Status getStatus() {
    Status CopperBottomBoat$status = this.isUnderwater();
    if (CopperBottomBoat$status != null) {
      this.waterLevel = this.getBoundingBox().maxY;
      return CopperBottomBoat$status;
    } else if (this.checkInWater()) {
      return Status.IN_WATER;
    } else {
      float f = this.getGroundFriction();
      if (f > 0.0F) {
        this.landFriction = f;
        return Status.ON_LAND;
      } else {
        return Status.IN_AIR;
      }
    }
  }

  public float getWaterLevelAbove() {
    AABB aabb = this.getBoundingBox();
    int i = Mth.floor(aabb.minX);
    int j = Mth.ceil(aabb.maxX);
    int k = Mth.floor(aabb.maxY);
    int l = Mth.ceil(aabb.maxY - this.lastYd);
    int i1 = Mth.floor(aabb.minZ);
    int j1 = Mth.ceil(aabb.maxZ);
    BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

    label39:
    for (int k1 = k; k1 < l; ++k1) {
      float f = 0.0F;

      for (int l1 = i; l1 < j; ++l1) {
        for (int i2 = i1; i2 < j1; ++i2) {
          blockpos$mutableblockpos.set(l1, k1, i2);
          FluidState fluidstate = this.level().getFluidState(blockpos$mutableblockpos);
          if (fluidstate.is(FluidTags.WATER)) {
            f = Math.max(f, fluidstate.getHeight(this.level(), blockpos$mutableblockpos));
          }

          if (f >= 1.0F) {
            continue label39;
          }
        }
      }

      if (f < 1.0F) {
        return (float) blockpos$mutableblockpos.getY() + f;
      }
    }

    return (float) (l + 1);
  }

  public float getGroundFriction() {
    AABB aabb = this.getBoundingBox();
    AABB aabb1 = new AABB(aabb.minX, aabb.minY - 0.001, aabb.minZ, aabb.maxX, aabb.minY, aabb.maxZ);
    int i = Mth.floor(aabb1.minX) - 1;
    int j = Mth.ceil(aabb1.maxX) + 1;
    int k = Mth.floor(aabb1.minY) - 1;
    int l = Mth.ceil(aabb1.maxY) + 1;
    int i1 = Mth.floor(aabb1.minZ) - 1;
    int j1 = Mth.ceil(aabb1.maxZ) + 1;
    VoxelShape voxelshape = Shapes.create(aabb1);
    float f = 0.0F;
    int k1 = 0;
    BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

    for (int l1 = i; l1 < j; ++l1) {
      for (int i2 = i1; i2 < j1; ++i2) {
        int j2 = (l1 != i && l1 != j - 1 ? 0 : 1) + (i2 != i1 && i2 != j1 - 1 ? 0 : 1);
        if (j2 != 2) {
          for (int k2 = k; k2 < l; ++k2) {
            if (j2 <= 0 || k2 != k && k2 != l - 1) {
              blockpos$mutableblockpos.set(l1, k2, i2);
              BlockState blockstate = this.level().getBlockState(blockpos$mutableblockpos);
              if (!(blockstate.getBlock() instanceof WaterlilyBlock) && Shapes.joinIsNotEmpty(blockstate.getCollisionShape(this.level(), blockpos$mutableblockpos).move(l1, k2, i2), voxelshape,
                  BooleanOp.AND)) {
                f += blockstate.getBlock().getFriction();
                ++k1;
              }
            }
          }
        }
      }
    }

    return f / (float) k1;
  }

  private boolean checkInWater() {
    AABB aabb = this.getBoundingBox();
    int i = Mth.floor(aabb.minX);
    int j = Mth.ceil(aabb.maxX);
    int k = Mth.floor(aabb.minY);
    int l = Mth.ceil(aabb.minY + 0.001);
    int i1 = Mth.floor(aabb.minZ);
    int j1 = Mth.ceil(aabb.maxZ);
    boolean flag = false;
    this.waterLevel = -Double.MAX_VALUE;
    BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

    for (int k1 = i; k1 < j; ++k1) {
      for (int l1 = k; l1 < l; ++l1) {
        for (int i2 = i1; i2 < j1; ++i2) {
          blockpos$mutableblockpos.set(k1, l1, i2);
          FluidState fluidstate = this.level().getFluidState(blockpos$mutableblockpos);
          if (fluidstate.is(FluidTags.WATER)) {
            float f = (float) l1 + fluidstate.getHeight(this.level(), blockpos$mutableblockpos);
            this.waterLevel = Math.max(f, this.waterLevel);
            flag |= aabb.minY < (double) f;
          }
        }
      }
    }

    return flag;
  }

  @Nullable
  private Status isUnderwater() {
    AABB aabb = this.getBoundingBox();
    double d0 = aabb.maxY + 0.001;
    int i = Mth.floor(aabb.minX);
    int j = Mth.ceil(aabb.maxX);
    int k = Mth.floor(aabb.maxY);
    int l = Mth.ceil(d0);
    int i1 = Mth.floor(aabb.minZ);
    int j1 = Mth.ceil(aabb.maxZ);
    boolean flag = false;
    BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

    for (int k1 = i; k1 < j; ++k1) {
      for (int l1 = k; l1 < l; ++l1) {
        for (int i2 = i1; i2 < j1; ++i2) {
          blockpos$mutableblockpos.set(k1, l1, i2);
          FluidState fluidstate = this.level().getFluidState(blockpos$mutableblockpos);
          if (fluidstate.is(FluidTags.WATER) && d0 < (double) ((float) blockpos$mutableblockpos.getY() + fluidstate.getHeight(this.level(), blockpos$mutableblockpos))) {
            if (!fluidstate.isSource()) {
              return Status.UNDER_FLOWING_WATER;
            }

            flag = true;
          }
        }
      }
    }

    return flag ? Status.UNDER_WATER : null;
  }

  /// Applies buoyancy, gravity, and friction each tick based on the current [Status]. Also snaps the boat to the water surface when transitioning from air to water.
  private void floatCopperBottomBoat() {
    double d0 = -0.04F;
    double d1 = this.isNoGravity() ? (double) 0.0F : (double) -0.04F;
    double d2 = 0.0F;
    this.invFriction = 0.05F;
    if (this.oldStatus == Status.IN_AIR && this.status != Status.IN_AIR && this.status != Status.ON_LAND) {
      this.waterLevel = this.getY(1.0F);
      this.setPos(this.getX(), (double) (this.getWaterLevelAbove() - this.getBbHeight()) + 0.101, this.getZ());
      this.setDeltaMovement(this.getDeltaMovement().multiply(1.0F, 0.0F, 1.0F));
      this.lastYd = 0.0F;
      this.status = Status.IN_WATER;
    } else {
      if (this.status == Status.IN_WATER) {
        d2 = (this.waterLevel - this.getY()) / (double) this.getBbHeight();
        this.invFriction = 0.9F;
      } else if (this.status == Status.UNDER_FLOWING_WATER) {
        d1 = -7.0E-4;
        this.invFriction = 0.9F;
      } else if (this.status == Status.UNDER_WATER) {
        d2 = 0.01F;
        this.invFriction = 0.45F;
      } else if (this.status == Status.IN_AIR) {
        this.invFriction = 0.9F;
      } else if (this.status == Status.ON_LAND) {
        this.invFriction = this.landFriction;
        if (this.getControllingPassenger() instanceof Player) {
          this.landFriction /= 2.0F;
        }
      }

      Vec3 vec3 = this.getDeltaMovement();
      this.setDeltaMovement(vec3.x * (double) this.invFriction, vec3.y + d1, vec3.z * (double) this.invFriction);
      this.deltaRotation *= this.invFriction;
      if (d2 > (double) 0.0F) {
        Vec3 vec31 = this.getDeltaMovement();
        this.setDeltaMovement(vec31.x, (vec31.y + d2 * 0.06153846016296973) * (double) 0.75F, vec31.z);
      }
    }

  }

  /// Translates player input (left/right/forward/back) into rotation and thrust each tick. Oxidation level reduces the movement multiplier by 0.25 per stage (doubled at level 3).
  private void controlCopperBottomBoat() {
    if (this.isVehicle()) {
      float force = 0.0F;

      if (this.inputLeft) {
        --this.deltaRotation;
      }
      if (this.inputRight) {
        ++this.deltaRotation;
      }

      if (this.inputRight != this.inputLeft && !this.inputUp && !this.inputDown) {
        force += 0.005F;
      }

      this.setYRot(this.getYRot() + this.deltaRotation);
      if (this.inputUp) {
        force += 0.04F;
      }

      if (this.inputDown) {
        force -= 0.005F;
      }

      this.setDeltaMovement(this.getDeltaMovement().add(Mth.sin(-this.getYRot() * ((float) Math.PI / 180F)) * force, 0.0F, Mth.cos(this.getYRot() * ((float) Math.PI / 180F)) * force));
      this.setPaddleState(this.inputRight && !this.inputLeft || this.inputUp, this.inputLeft && !this.inputRight || this.inputUp);
    }

  }

  protected float getSinglePassengerXOffset() {
    return 0.0F;
  }

  public boolean hasEnoughSpaceFor(Entity entity) {
    return entity.getBbWidth() < this.getBbWidth();
  }

  protected void positionRider(Entity passenger, MoveFunction callback) {
    if (this.hasPassenger(passenger)) {
      float f = this.getSinglePassengerXOffset();
      float f1 = (float) (this.isRemoved() ? (double) 0.01F : this.getPassengersRidingOffset());
      if (this.getPassengers().size() > 1) {
        int i = this.getPassengers().indexOf(passenger);
        if (i == 0) {
          f = 0.2F;
        } else {
          f = -0.6F;
        }

        if (passenger instanceof Animal) {
          f += 0.2F;
        }
      }

      Vec3 vec3 = (new Vec3(f, 0.0F, 0.0F)).yRot(-this.getYRot() * ((float) Math.PI / 180F) - ((float) Math.PI / 2F));
      callback.accept(passenger, this.getX() + vec3.x, this.getY() + (double) f1, this.getZ() + vec3.z);
      passenger.setYRot(passenger.getYRot() + this.deltaRotation);
      passenger.setYHeadRot(passenger.getYHeadRot() + this.deltaRotation);
      this.clampRotation(passenger);
      if (passenger instanceof Animal && this.getPassengers().size() == this.getMaxPassengers()) {
        int j = passenger.getId() % 2 == 0 ? 90 : 270;
        passenger.setYBodyRot(((Animal) passenger).yBodyRot + (float) j);
        passenger.setYHeadRot(passenger.getYHeadRot() + (float) j);
      }
    }

  }

  public Vec3 getDismountLocationForPassenger(LivingEntity livingEntity) {
    Vec3 vec3 = getCollisionHorizontalEscapeVector(this.getBbWidth() * Mth.SQRT_OF_TWO, livingEntity.getBbWidth(), livingEntity.getYRot());
    double d0 = this.getX() + vec3.x;
    double d1 = this.getZ() + vec3.z;
    BlockPos blockpos = BlockPos.containing(d0, this.getBoundingBox().maxY, d1);
    BlockPos blockpos1 = blockpos.below();
    if (!this.level().isWaterAt(blockpos1)) {
      List<Vec3> list = Lists.newArrayList();
      double d2 = this.level().getBlockFloorHeight(blockpos);
      if (DismountHelper.isBlockFloorValid(d2)) {
        list.add(new Vec3(d0, (double) blockpos.getY() + d2, d1));
      }

      double d3 = this.level().getBlockFloorHeight(blockpos1);
      if (DismountHelper.isBlockFloorValid(d3)) {
        list.add(new Vec3(d0, (double) blockpos1.getY() + d3, d1));
      }

      UnmodifiableIterator var14 = livingEntity.getDismountPoses().iterator();

      while (var14.hasNext()) {
        Pose pose = (Pose) var14.next();

        for (Vec3 vec31 : list) {
          if (DismountHelper.canDismountTo(this.level(), vec31, livingEntity, pose)) {
            livingEntity.setPose(pose);
            return vec31;
          }
        }
      }
    }

    return super.getDismountLocationForPassenger(livingEntity);
  }

  protected void clampRotation(Entity entityToUpdate) {
    entityToUpdate.setYBodyRot(this.getYRot());
    float f = Mth.wrapDegrees(entityToUpdate.getYRot() - this.getYRot());
    float f1 = Mth.clamp(f, -105.0F, 105.0F);
    entityToUpdate.yRotO += f1 - f;
    entityToUpdate.setYRot(entityToUpdate.getYRot() + f1 - f);
    entityToUpdate.setYHeadRot(entityToUpdate.getYRot());
  }

  public void onPassengerTurned(Entity entityToUpdate) {
    this.clampRotation(entityToUpdate);
  }

  protected void addAdditionalSaveData(CompoundTag compound) {

    compound.putInt("oxidization", this.getOxidizationLevel());
    compound.putBoolean("waxed", this.isWaxed());
  }

  protected void readAdditionalSaveData(CompoundTag compound) {

    this.setOxidizationLevel(compound.getInt("oxidization"));
    this.setWaxed(compound.getBoolean("waxed"));
  }

  public boolean isWaxed() {
    return this.getEntityData().get(WAXED);
  }

  public void setWaxed(boolean waxed) {
    this.getEntityData().set(WAXED, waxed);
  }

  public int getOxidizationLevel() {
    return this.getEntityData().get(OXIDIZATION_LEVEL);
  }

  public void setOxidizationLevel(int level) {
    this.getEntityData().set(OXIDIZATION_LEVEL, level);
  }

  public void wax() {
    this.setWaxed(true);
  }

  public void oxidize() {
    int currentLevel = this.getOxidizationLevel();

    if (currentLevel < 3) {
      setOxidizationLevel(currentLevel + 1);
    }
  }

  public InteractionResult interact(Player player, InteractionHand hand) {

    if (player.isSecondaryUseActive()) {
      ItemStack stack = player.getItemInHand(hand);

      if (!this.level().isClientSide()) {
        if (stack.is(Items.HONEYCOMB) && !this.isWaxed()) {
          this.wax();
          stack.shrink(1);
          player.setItemInHand(hand, stack);
          return InteractionResult.CONSUME;
        }

        if (stack.is(ModItemTags.WAX_SCRAPER) && this.isWaxed()) {
          this.setWaxed(false);
          stack.hurtAndBreak(1, player, hand == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
          return InteractionResult.CONSUME;
        }

        if (stack.is(ModItemTags.MANUAL_OXIDIZER) && !this.isWaxed() && this.getOxidizationLevel() < 3) {
          this.oxidize();
          return InteractionResult.CONSUME;
        }
      }

      return InteractionResult.PASS;
    } else if (this.outOfControlTicks < 60.0F) {
      if (!this.level().isClientSide) {
        return player.startRiding(this) ? InteractionResult.CONSUME : InteractionResult.PASS;
      } else {
        return InteractionResult.SUCCESS;
      }
    } else {
      return InteractionResult.PASS;
    }
  }

  protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {
    this.lastYd = this.getDeltaMovement().y;
    if (!this.isPassenger()) {
      if (onGround) {
        if (this.fallDistance > 3.0F) {
          if (this.status != Status.ON_LAND) {
            this.resetFallDistance();
            return;
          }

          this.causeFallDamage(this.fallDistance, 1.0F, this.damageSources().fall());
          if (!this.level().isClientSide && !this.isRemoved()) {
            this.kill();
            if (this.level().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {

              for (int j = 0; j < 2; ++j) {
                this.spawnAtLocation(Items.STICK);
              }
            }
          }
        }

        this.resetFallDistance();
      } else if (!this.level().getFluidState(this.blockPosition().below()).is(FluidTags.WATER) && y < (double) 0.0F) {
        this.fallDistance -= (float) y;
      }
    }

  }

  public boolean getPaddleState(int side) {
    return this.entityData.get(side == 0 ? DATA_ID_PADDLE_LEFT : DATA_ID_PADDLE_RIGHT) && this.getControllingPassenger() != null;
  }

  public float getDamage() {
    return this.entityData.get(DATA_ID_DAMAGE);
  }

  public void setDamage(float damageTaken) {
    this.entityData.set(DATA_ID_DAMAGE, damageTaken);
  }

  public int getHurtTime() {
    return this.entityData.get(DATA_ID_HURT);
  }

  public void setHurtTime(int hurtTime) {
    this.entityData.set(DATA_ID_HURT, hurtTime);
  }

  private int getBubbleTime() {
    return this.entityData.get(DATA_ID_BUBBLE_TIME);
  }

  private void setBubbleTime(int bubbleTime) {
    this.entityData.set(DATA_ID_BUBBLE_TIME, bubbleTime);
  }

  public float getBubbleAngle(float partialTicks) {
    return Mth.lerp(partialTicks, this.bubbleAngleO, this.bubbleAngle);
  }

  public int getHurtDir() {
    return this.entityData.get(DATA_ID_HURTDIR);
  }

  public void setHurtDir(int hurtDirection) {
    this.entityData.set(DATA_ID_HURTDIR, hurtDirection);
  }

//  public CopperBottomBoat.Type getVariant() {
//    return CopperBottomBoat.Type.byId(this.entityData.get(DATA_ID_TYPE));
//  }

  public void setVariant(Type variant) {
    this.entityData.set(DATA_ID_TYPE, variant.ordinal());
  }

  protected boolean canAddPassenger(Entity passenger) {
    return this.getPassengers().size() < this.getMaxPassengers() && !this.isEyeInFluid(FluidTags.WATER);
  }

  protected int getMaxPassengers() {
    return 2;
  }

  @Nullable
  public LivingEntity getControllingPassenger() {
    Entity entity = this.getFirstPassenger();
    LivingEntity livingentity1;
    if (entity instanceof LivingEntity livingentity) {
      livingentity1 = livingentity;
    } else {
      livingentity1 = null;
    }

    return livingentity1;
  }


  public void setInput(boolean inputLeft, boolean inputRight, boolean inputUp, boolean inputDown) {
    this.inputLeft = inputLeft;
    this.inputRight = inputRight;
    this.inputUp = inputUp;
    this.inputDown = inputDown;
  }

  public boolean isUnderWater() {
    return this.status == Status.UNDER_WATER || this.status == Status.UNDER_FLOWING_WATER;
  }

  public ItemStack getPickResult() {
    return new ItemStack(this.getDropItem());
  }

  public enum Status {
    IN_WATER, UNDER_WATER, UNDER_FLOWING_WATER, ON_LAND, IN_AIR
  }

  public enum Type implements StringRepresentable {
    OAK(Blocks.OAK_PLANKS, "oak"), SPRUCE(Blocks.SPRUCE_PLANKS, "spruce"), BIRCH(Blocks.BIRCH_PLANKS, "birch"), JUNGLE(Blocks.JUNGLE_PLANKS, "jungle"), ACACIA(Blocks.ACACIA_PLANKS, "acacia"), CHERRY(
        Blocks.CHERRY_PLANKS, "cherry"), DARK_OAK(Blocks.DARK_OAK_PLANKS, "dark_oak"), MANGROVE(Blocks.MANGROVE_PLANKS, "mangrove"), BAMBOO(Blocks.BAMBOO_PLANKS, "bamboo");

    public static final EnumCodec<Type> CODEC = StringRepresentable.fromEnum(Type::values);
    private static final IntFunction<Type> BY_ID = ByIdMap.continuous(Enum::ordinal, values(), OutOfBoundsStrategy.ZERO);
    private final String name;
    private final Block planks;

    Type(Block planks, String name) {
      this.name = name;
      this.planks = planks;
    }

    public static Type byId(int id) {
      return BY_ID.apply(id);
    }

    public static Type byName(String name) {
      return CODEC.byName(name, OAK);
    }

    public String getSerializedName() {
      return this.name;
    }

    public String getName() {
      return this.name;
    }

    public Block getPlanks() {
      return this.planks;
    }

    public String toString() {
      return this.name;
    }
  }
}
