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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class CopperStatue extends WeatheringCopperStatueEntity {

  private static final EntityDataAccessor<Integer> DATA_ID_TYPE = SynchedEntityData.defineId(CopperStatue.class, EntityDataSerializers.INT);

  public CopperStatue(EntityType<? extends AbstractStatueEntity> entityType, Level level) {
    super(entityType, level);
  }

  public CopperStatue(EntityType<? extends AbstractStatueEntity> entityType, Level level, double x, double y, double z) {
    super(entityType, level, x, y, z);
  }

  @Override
  protected void defineSynchedData(SynchedEntityData.Builder builder) {
    super.defineSynchedData(builder);
    builder.define(DATA_ID_TYPE, Type.CREEPER.ordinal());
  }

  public Type getVariant() {
    return Type.byId(this.entityData.get(DATA_ID_TYPE));
  }

  public void setVariant(Type variant) {
    this.entityData.set(DATA_ID_TYPE, variant.ordinal());
  }

  @Override
  protected void addAdditionalSaveData(ValueOutput output) {
    super.addAdditionalSaveData(output);

    output.putInt("copperStatueTypeOrdinal", this.getVariant().ordinal());
  }

  @Override
  protected void readAdditionalSaveData(ValueInput input) {
    super.readAdditionalSaveData(input);

    this.setVariant(Type.byId(input.getIntOr("copperStatueTypeOrdinal", 0)));
  }

  public enum Type implements StringRepresentable {
    CREEPER("creeper"), SKELETON("skeleton"), SPIDER("spider"), ZOMBIE("zombie"),
    ;

    @SuppressWarnings("deprecation")
    public static final EnumCodec<Type> CODEC = StringRepresentable.fromEnum(Type::values);
    private static final IntFunction<Type> BY_ID = ByIdMap.continuous(Enum::ordinal, values(), ByIdMap.OutOfBoundsStrategy.ZERO);

    private final String name;

    Type(String name) {
      this.name = name;
    }

    public static Type byId(int id) {
      return BY_ID.apply(id);
    }

    public static Type byName(String name) {
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
