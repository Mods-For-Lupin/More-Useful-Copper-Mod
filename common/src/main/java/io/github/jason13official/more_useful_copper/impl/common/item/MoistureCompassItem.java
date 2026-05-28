package io.github.jason13official.more_useful_copper.impl.common.item;

import com.mojang.serialization.DataResult;
import io.github.jason13official.more_useful_copper.Constants;
import java.util.Optional;
import org.jetbrains.annotations.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
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

  public static boolean isMoistureCompass(ItemStack stack) {
    CustomData customData = stack.get(DataComponents.CUSTOM_DATA);
    if (customData == null) return false;
    CompoundTag isMoistureTag = customData.copyTag();
    return isMoistureTag.contains(TAG_MOISTURE_DIMENSION) || isMoistureTag.contains(TAG_MOISTURE_TRACKED);
  }

  private static Optional<ResourceKey<Level>> getMoistureDimension(CompoundTag compoundTag) {
    return Level.RESOURCE_KEY_CODEC.parse(NbtOps.INSTANCE, compoundTag.get(TAG_MOISTURE_DIMENSION)).result();
  }

  @Nullable
  public static GlobalPos getMoisturePosition(ItemStack stack) {
    CustomData customData = stack.get(DataComponents.CUSTOM_DATA);
    if (customData == null) return null;
    CompoundTag tag = customData.copyTag();
    boolean flag = tag.contains(TAG_MOISTURE_POS);
    boolean flag1 = tag.contains(TAG_MOISTURE_DIMENSION);
    if (flag && flag1) {
      Optional<ResourceKey<Level>> optional = getMoistureDimension(tag);
      if (optional.isPresent()) {
        BlockPos pos = BlockPos.of(tag.getLongOr(TAG_MOISTURE_POS, 0L));
        return GlobalPos.of(optional.get(), pos);
      }
    }

    return null;
  }

  @Nullable
  public static GlobalPos getSpawnPosition(Level level) {
    return level.dimension() == Level.OVERWORLD ? GlobalPos.of(level.dimension(), level.getLevelData().getRespawnData().pos()) : null;
  }

  @Override
  public InteractionResult use(Level level, Player player, InteractionHand usedHand) {

    ItemStack stack = player.getItemInHand(usedHand);

    if (isMoistureCompass(stack)) {
      stack.update(DataComponents.CUSTOM_DATA, CustomData.EMPTY, existing -> {
        CompoundTag tag = existing.copyTag();
        tag.remove(TAG_MOISTURE_TRACKED);
        tag.remove(TAG_MOISTURE_POS);
        tag.remove(TAG_MOISTURE_DIMENSION);
        return CustomData.of(tag);
      });

      tagClosestWaterPosition(stack, level, player);
    }

    return super.use(level, player, usedHand);
  }

  public boolean isFoil(ItemStack stack) {
    return isMoistureCompass(stack) || super.isFoil(stack);
  }

  @Override
  public void inventoryTick(ItemStack stack, net.minecraft.server.level.ServerLevel level, Entity entity, @Nullable net.minecraft.world.entity.EquipmentSlot equipSlot) {

    if (!(entity instanceof Player)) {
      return;
    }

    if (level.isClientSide()) {
      return;
    }

    if (isMoistureCompass(stack)) {

      CustomData existingData = stack.get(DataComponents.CUSTOM_DATA);
      CompoundTag compoundtag = existingData != null ? existingData.copyTag() : new CompoundTag();
      if (compoundtag.contains(TAG_MOISTURE_TRACKED) && !compoundtag.getBooleanOr(TAG_MOISTURE_TRACKED, false)) {
        return;
      }

      Optional<ResourceKey<Level>> optional = getMoistureDimension(compoundtag);
      if (optional.isPresent() && optional.get() == level.dimension() && compoundtag.contains(TAG_MOISTURE_POS)) {
        BlockPos maybePos = BlockPos.of(compoundtag.getLongOr(TAG_MOISTURE_POS, 0L));
        if (!level.isInWorldBounds(maybePos)) {
          compoundtag.remove(TAG_MOISTURE_POS);
          stack.set(DataComponents.CUSTOM_DATA, CustomData.of(compoundtag));
        }
      }
    } else {

      // every 2 seconds, find the closest water position
      CustomData currentData = stack.get(DataComponents.CUSTOM_DATA);
      boolean hasMoisturePos = currentData != null && currentData.copyTag().contains(TAG_MOISTURE_POS);
      if (level.getGameTime() % 40 == 0 && !hasMoisturePos) {

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
      CustomData existing = stack.get(DataComponents.CUSTOM_DATA);
      CompoundTag compoundtag = existing != null ? existing.copyTag() : new CompoundTag();
      this.addMoistureTags(level.dimension(), closestWaterPos, compoundtag);
      stack.set(DataComponents.CUSTOM_DATA, CustomData.of(compoundtag));
    }
  }

  public InteractionResult useOn(UseOnContext context) {

    BlockPos blockpos = context.getClickedPos();
    Level level = context.getLevel();

    BlockPlaceContext placeContext = new BlockPlaceContext(context);

    if (!placeContext.getLevel().getBlockState(placeContext.getClickedPos()).is(Blocks.WATER)) {
      return super.useOn(context);
    } else {

      level.playSound(null, blockpos, SoundEvents.LODESTONE_COMPASS_LOCK, SoundSource.PLAYERS, 1.0F, 1.0F);

      Player player = context.getPlayer();
      ItemStack itemstack = context.getItemInHand();

      boolean creativeOneItem = !player.getAbilities().instabuild && itemstack.getCount() == 1;

      if (creativeOneItem) {
        CustomData existing = itemstack.get(DataComponents.CUSTOM_DATA);
        CompoundTag tag = existing != null ? existing.copyTag() : new CompoundTag();
        this.addMoistureTags(level.dimension(), blockpos, tag);
        itemstack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
      } else {
        ItemStack itemstack1 = new ItemStack(Items.COMPASS, 1);

        CustomData existing = itemstack.get(DataComponents.CUSTOM_DATA);
        CompoundTag compoundtag = existing != null ? existing.copyTag() : new CompoundTag();

        this.addMoistureTags(level.dimension(), blockpos, compoundtag);

        itemstack1.set(DataComponents.CUSTOM_DATA, CustomData.of(compoundtag));

        if (!player.getAbilities().instabuild) {
          itemstack.shrink(1);
        }

        if (!player.getInventory().add(itemstack1)) {
          player.drop(itemstack1, false);
        }
      }

      return InteractionResult.SUCCESS;
    }
  }

  private void addMoistureTags(ResourceKey<Level> moistureDimension, BlockPos moisturePos, CompoundTag compoundTag) {

    DataResult<Tag> result = Level.RESOURCE_KEY_CODEC.encodeStart(NbtOps.INSTANCE, moistureDimension);
    result.resultOrPartial(Constants.LOG::error).ifPresent((tag) -> compoundTag.put(TAG_MOISTURE_DIMENSION, tag));

    compoundTag.putBoolean(TAG_MOISTURE_TRACKED, true);

    compoundTag.putLong(TAG_MOISTURE_POS, moisturePos.asLong());
  }

  @Override
  public Component getName(ItemStack stack) {
    return isMoistureCompass(stack) ? Component.translatable("item.more_useful_copper.moisture_compass") : super.getName(stack);
  }
}
