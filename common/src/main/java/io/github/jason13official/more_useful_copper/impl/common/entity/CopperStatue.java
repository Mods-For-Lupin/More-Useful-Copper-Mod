package io.github.jason13official.more_useful_copper.impl.common.entity;

import io.github.jason13official.more_useful_copper.api.common.entity.AbstractStatueEntity;
import io.github.jason13official.more_useful_copper.api.common.entity.WeatheringCopperStatueEntity;
import java.util.function.IntFunction;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.Level;

public class CopperStatue extends WeatheringCopperStatueEntity {

  private static final EntityDataAccessor<Integer> DATA_ID_TYPE = SynchedEntityData.defineId(CopperStatue.class, EntityDataSerializers.INT);

  public CopperStatue(EntityType<? extends AbstractStatueEntity> entityType, Level level) {
    super(entityType, level);
  }

  public CopperStatue(EntityType<? extends AbstractStatueEntity> entityType, Level level, double x, double y, double z) {
    super(entityType, level, x, y, z);
  }

  @Override
  protected void defineSynchedData() {
    this.entityData.define(DATA_ID_TYPE, Type.CREEPER.ordinal());
  }

  public void setVariant(CopperStatue.Type variant) {
    this.entityData.set(DATA_ID_TYPE, variant.ordinal());
  }

  public CopperStatue.Type getVariant() {
    return CopperStatue.Type.byId(this.entityData.get(DATA_ID_TYPE));
  }

  public enum Type implements StringRepresentable {
    CREEPER("creeper"), SKELETON("skeleton"), SPIDER("spider"), ZOMBIE("zombie"),
    ;

    @SuppressWarnings("deprecation")
    public static final StringRepresentable.EnumCodec<CopperStatue.Type> CODEC = StringRepresentable.fromEnum(CopperStatue.Type::values);
    private static final IntFunction<CopperStatue.Type> BY_ID = ByIdMap.continuous(Enum::ordinal, values(), ByIdMap.OutOfBoundsStrategy.ZERO);

    private final String name;

    Type(String name) {
      this.name = name;
    }

    public static CopperStatue.Type byId(int id) {
      return BY_ID.apply(id);
    }

    public static CopperStatue.Type byName(String name) {
      return CODEC.byName(name, CREEPER);
    }

    @Override
    public String getSerializedName() {
      return this.name;
    }

    public String getName() {
      return this.name;
    }

    public String toString() {
      return this.name;
    }
  }
}
