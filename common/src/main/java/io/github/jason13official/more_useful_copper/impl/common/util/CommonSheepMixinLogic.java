package io.github.jason13official.more_useful_copper.impl.common.util;

import io.github.jason13official.more_useful_copper.impl.common.registry.ModItems;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.gameevent.GameEvent;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class CommonSheepMixinLogic {

  public static void injectedMobInteract(Sheep self, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {

    ItemStack itemStack = player.getItemInHand(hand);

    if (!itemStack.is(ModItems.COPPER_SHEARS)) {
      return;
    }

    if (!self.level().isClientSide() && self.readyForShearing()) {
      self.shear(SoundSource.PLAYERS);
      self.gameEvent(GameEvent.SHEAR, player);
      itemStack.hurtAndBreak(1, player, hand == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
      // return InteractionResult.SUCCESS;
      cir.setReturnValue(InteractionResult.SUCCESS);
    } else {
      // return InteractionResult.CONSUME;
      cir.setReturnValue(InteractionResult.CONSUME);
    }
  }
}
