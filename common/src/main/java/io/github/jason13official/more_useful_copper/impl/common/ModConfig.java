package io.github.jason13official.more_useful_copper.impl.common;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import io.github.jason13official.more_useful_copper.Constants;
import io.github.jason13official.more_useful_copper.platform.Services;
import java.nio.file.Files;
import java.nio.file.Path;

public class ModConfig {

  private static final boolean VANILLA_BACKPORT_LOADED = Services.PLATFORM.isModLoaded("vanillabackport");
  private static ModConfig INSTANCE;

  /// Whether charged lightning effects on tools and armor are enabled at all.
  public boolean lightningEffectsEnabled = true;

  /// Whether items that overlap with Vanilla Backport are automatically disabled when Vanilla Backport is detected.
  public boolean autoDisableVanillaBackportItems = true;

  public static ModConfig get() {
    if (INSTANCE == null) {
      load(Services.PLATFORM.getConfigDirectory());
    }
    return INSTANCE;
  }

  /// Returns true unless Vanilla Backport is loaded and {@link #autoDisableVanillaBackportItems} is enabled.
  public static boolean isVanillaBackportItemEnabled() {
    return !(get().autoDisableVanillaBackportItems && VANILLA_BACKPORT_LOADED);
  }

  /// Loads (or creates) `<configDir>/more_useful_copper-server.toml`, writing defaults on first run.
  ///
  /// @param configDir the platform config directory (e.g. `.minecraft/config`)
  public static void load(Path configDir) {
    Path file = configDir.resolve("more_useful_copper-server.toml");

    try {
      Files.createDirectories(configDir);
    } catch (Exception e) {
      Constants.LOG.error("Failed to create config directory, using defaults", e);
      INSTANCE = new ModConfig();
      return;
    }

    try (CommentedFileConfig config = CommentedFileConfig.builder(file.toFile()).build()) {
      if (Files.exists(file)) {
        config.load();
      }

      ModConfig loaded = new ModConfig();
      loaded.lightningEffectsEnabled = config.getOrElse("lightning_effects_enabled", true);
      loaded.autoDisableVanillaBackportItems = config.getOrElse("auto_disable_vanilla_backport_items", true);
      INSTANCE = loaded;

      config.setComment("lightning_effects_enabled", " Whether charged lightning effects on tools and armor are enabled at all.");
      config.set("lightning_effects_enabled", INSTANCE.lightningEffectsEnabled);
      config.setComment("auto_disable_vanilla_backport_items", " Automatically disable items overlapping with Vanilla Backport (copper tools, armor, nugget, horse armor, chains, and golem) when Vanilla Backport is detected.");
      config.set("auto_disable_vanilla_backport_items", INSTANCE.autoDisableVanillaBackportItems);
      config.save();
    } catch (Exception e) {
      Constants.LOG.error("Failed to load More Useful Copper config, using defaults", e);
      INSTANCE = new ModConfig();
    }
  }
}
