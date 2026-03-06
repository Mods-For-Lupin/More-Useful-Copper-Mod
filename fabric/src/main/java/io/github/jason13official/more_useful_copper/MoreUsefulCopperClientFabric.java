package io.github.jason13official.more_useful_copper;

import net.fabricmc.api.ClientModInitializer;

public class MoreUsefulCopperClientFabric implements ClientModInitializer {

  @Override
  public void onInitializeClient() {

    MoreUsefulCopperClient.init();
  }
}
