package io.github.jason13official.more_useful_copper.api.common.registry;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

// old forge imports
//import net.minecraftforge.common.loot.IGlobalLootModifier;
//import net.minecraftforge.common.loot.LootModifier;
//import net.minecraftforge.registries.ForgeRegistries;

public class AddItemModifier extends LootModifier {

  public static final MapCodec<AddItemModifier> CODEC = RecordCodecBuilder.mapCodec(inst ->
      LootModifier.codecStart(inst).and(
          BuiltInRegistries.ITEM.byNameCodec().fieldOf("item").forGetter(e -> e.item)
      ).apply(inst, AddItemModifier::new)
  );

  private final Item item;

  public AddItemModifier(LootItemCondition[] conditions, int priority, Item item) {
    super(conditions, priority);
    this.item = item;
  }

  @Override
  public MapCodec<? extends IGlobalLootModifier> codec() {
    return CODEC;
  }

  @Override
  protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
    // note commented out version of this method; LootModifier already handled LootItemCondition testing via apply method
    generatedLoot.add(new ItemStack(this.item));
    return generatedLoot;
  }

//  @Override
//  protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
//
//    for (LootItemCondition condition : this.conditions) {
//      if (!condition.test(context)) {
//        return generatedLoot;
//      }
//    }
//
//    generatedLoot.add(new ItemStack(this.item));
//
//    return generatedLoot;
//  }
}

