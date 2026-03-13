package io.github.jason13official.more_useful_copper.api.client.rendering;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;

public final class BuiltinItemRendererRegistryImpl implements BuiltinItemRendererRegistry {

  private static final Map<Item, DynamicItemRenderer> RENDERERS = new HashMap<>();

  public BuiltinItemRendererRegistryImpl() {
  }

  @Override
  public void register(ItemLike item, DynamicItemRenderer renderer) {
    Objects.requireNonNull(item, "item is null");
    Objects.requireNonNull(item.asItem(), "item is null");
    Objects.requireNonNull(renderer, "renderer is null");

    if (RENDERERS.putIfAbsent(item.asItem(), renderer) != null) {
      throw new IllegalArgumentException("Item " + BuiltInRegistries.ITEM.getKey(item.asItem()) + " already has a builtin renderer!");
    }
  }

  @Override
  public DynamicItemRenderer get(ItemLike item) {
    Objects.requireNonNull(item.asItem(), "item is null");

    return RENDERERS.get(item.asItem());
  }
}

