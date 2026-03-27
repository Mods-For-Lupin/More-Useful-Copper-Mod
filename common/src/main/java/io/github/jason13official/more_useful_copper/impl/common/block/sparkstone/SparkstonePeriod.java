package io.github.jason13official.more_useful_copper.impl.common.block.sparkstone;

import net.minecraft.util.StringRepresentable;

/// Oscillation period for a [SparkstoneTorchBlock], measured in game ticks.
public enum SparkstonePeriod implements StringRepresentable {

  FAST(8),
  MEDIUM(16),
  SLOW(32),
  VERY_SLOW(64);

  /// Tick count for one full oscillation cycle.
  public final int period;

  SparkstonePeriod(int period) {
    this.period = period;
  }

  @Override
  public String getSerializedName() {
    return name().toLowerCase();
  }

  /// Cycles to the next period: FAST → MEDIUM → SLOW → VERY_SLOW → FAST.
  public SparkstonePeriod next() {
    SparkstonePeriod[] values = values();
    return values[(ordinal() + 1) % values.length];
  }
}
