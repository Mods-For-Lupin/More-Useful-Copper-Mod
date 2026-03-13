package io.github.jason13official.more_useful_copper.impl.common.item;

import com.mojang.serialization.DataResult;
import io.github.jason13official.more_useful_copper.Constants;
import io.github.jason13official.more_useful_copper.MoreUsefulCopper;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class MoistureCompassItem extends Item {

  public static final String TAG_MOISTURE_POS = "MoisturePos";
  public static final String TAG_MOISTURE_DIMENSION = "MoistureDimension";
  public static final String TAG_MOISTURE_TRACKED = "MoistureTracked";

  public MoistureCompassItem(Properties properties) {
    super(properties);
  }

  @Override
  public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {

    ItemStack stack = player.getItemInHand(usedHand);

    CompoundTag tag = stack.getTag();

    if (tag != null && isMoistureCompass(stack)) {
      tag.remove(TAG_MOISTURE_TRACKED);
      tag.remove(TAG_MOISTURE_POS);
      tag.remove(TAG_MOISTURE_DIMENSION);

      tagClosestWaterPosition(stack, level, player);
    }

    return super.use(level, player, usedHand);
  }

  public static boolean isMoistureCompass(ItemStack stack) {
    CompoundTag compoundtag = stack.getTag();
    return compoundtag != null && (compoundtag.contains(TAG_MOISTURE_DIMENSION) || compoundtag.contains(TAG_MOISTURE_TRACKED));
  }

  private static Optional<ResourceKey<Level>> getMoistureDimension(CompoundTag compoundTag) {
    return Level.RESOURCE_KEY_CODEC.parse(NbtOps.INSTANCE, compoundTag.get(TAG_MOISTURE_DIMENSION)).result();
  }

  @Nullable
  public static GlobalPos getMoisturePosition(CompoundTag tag) {
    boolean flag = tag.contains(TAG_MOISTURE_POS);
    boolean flag1 = tag.contains(TAG_MOISTURE_DIMENSION);
    if (flag && flag1) {
      Optional<ResourceKey<Level>> optional = getMoistureDimension(tag);
      if (optional.isPresent()) {
        BlockPos blockpos = NbtUtils.readBlockPos(tag.getCompound(TAG_MOISTURE_POS));
        return GlobalPos.of(optional.get(), blockpos);
      }
    }

    return null;
  }

  @Nullable
  public static GlobalPos getSpawnPosition(Level level) {
    return level.dimensionType().natural() ? GlobalPos.of(level.dimension(), level.getSharedSpawnPos()) : null;
  }

  public boolean isFoil(ItemStack stack) {
    return isMoistureCompass(stack) || super.isFoil(stack);
  }

  @Override
  public void inventoryTick(ItemStack stack, Level level, Entity entity, int itemSlot, boolean isSelected) {

    if (!(entity instanceof Player)) {
      return;
    }

    if (level.isClientSide()) {
      return;
    }

    if (isMoistureCompass(stack)) {

      CompoundTag compoundtag = stack.getOrCreateTag();
      if (compoundtag.contains(TAG_MOISTURE_TRACKED) && !compoundtag.getBoolean(TAG_MOISTURE_TRACKED)) {
        return;
      }

      Optional<ResourceKey<Level>> optional = getMoistureDimension(compoundtag);
      if (optional.isPresent() && optional.get() == level.dimension() && compoundtag.contains(TAG_MOISTURE_POS)) {
        BlockPos blockpos = NbtUtils.readBlockPos(compoundtag.getCompound(TAG_MOISTURE_POS));
        if (!level.isInWorldBounds(blockpos)) {
          compoundtag.remove(TAG_MOISTURE_POS);
        }
      }
    }
    else {

      // every 2 seconds, find the closest water position
      if (level.getGameTime() % 40 == 0 && !stack.getOrCreateTag().contains(TAG_MOISTURE_POS)) {

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
      CompoundTag compoundtag = stack.hasTag() ? stack.getTag().copy() : new CompoundTag();
      this.addMoistureTags(level.dimension(), closestWaterPos, compoundtag);
      stack.setTag(compoundtag);
    }
  }

  public InteractionResult useOn(UseOnContext context) {

    BlockPos blockpos = context.getClickedPos();
    Level level = context.getLevel();

    BlockPlaceContext placeContext = new BlockPlaceContext(context);

    // if (!level.getBlockState(blockpos).is(Blocks.WATER)) {
    if (!placeContext.getLevel().getBlockState(placeContext.getClickedPos()).is(Blocks.WATER)) {
      return super.useOn(context);
    } else {

      level.playSound(null, blockpos, SoundEvents.LODESTONE_COMPASS_LOCK, SoundSource.PLAYERS, 1.0F, 1.0F);

      Player player = context.getPlayer();
      ItemStack itemstack = context.getItemInHand();

      boolean creativeOneItem = !player.getAbilities().instabuild && itemstack.getCount() == 1;

      if (creativeOneItem) {
        this.addMoistureTags(level.dimension(), blockpos, itemstack.getOrCreateTag());
      } else {
        ItemStack itemstack1 = new ItemStack(Items.COMPASS, 1);

        CompoundTag compoundtag = itemstack.hasTag() ? itemstack.getTag().copy() : new CompoundTag();

        this.addMoistureTags(level.dimension(), blockpos, compoundtag);

        itemstack1.setTag(compoundtag);

        if (!player.getAbilities().instabuild) {
          itemstack.shrink(1);
        }

        if (!player.getInventory().add(itemstack1)) {
          player.drop(itemstack1, false);
        }
      }

      return InteractionResult.sidedSuccess(level.isClientSide);
    }
  }

  private void addMoistureTags(ResourceKey<Level> moistureDimension, BlockPos moisturePos, CompoundTag compoundTag) {


    DataResult<Tag> result = Level.RESOURCE_KEY_CODEC.encodeStart(NbtOps.INSTANCE, moistureDimension);
    result.resultOrPartial(Constants.LOG::error).ifPresent((tag) -> compoundTag.put(TAG_MOISTURE_DIMENSION, tag));

    compoundTag.putBoolean(TAG_MOISTURE_TRACKED, true);

    compoundTag.put(TAG_MOISTURE_POS, NbtUtils.writeBlockPos(moisturePos));
  }

  public String getDescriptionId(ItemStack stack) {
    return isMoistureCompass(stack) ? "item.more_useful_copper.moisture_compass" : super.getDescriptionId(stack);
  }
}
