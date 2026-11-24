package io.github.jason13official.more_useful_copper.mixin;

import java.util.function.Consumer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LootTable.class)
public abstract class FabricLootTableMixin {

  @ModifyVariable(method = "getRandomItemsRaw(Lnet/minecraft/world/level/storage/loot/LootContext;Ljava/util/function/Consumer;)V", at = @At("HEAD"), argsOnly = true)
  private Consumer<ItemStack> more_useful_copper$filterDisabledItems(Consumer<ItemStack> output, LootContext context) {
    return stack -> {
      if (stack.getItem().isEnabled(context.getLevel().enabledFeatures()) || !"more_useful_copper".equals(net.minecraft.core.registries.BuiltInRegistries.ITEM.getKey(stack.getItem()).getNamespace())) {
        output.accept(stack);
      }
    };
  }
}
