package io.github.jason13official.more_useful_copper.impl.common;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import io.github.jason13official.more_useful_copper.Constants;
import java.nio.file.Files;
import java.nio.file.Path;

public class ModConfig {

  private static ModConfig INSTANCE = new ModConfig();

  /// Whether charged lightning effects on tools and armor are enabled at all.
  public boolean lightningEffectsEnabled = true;

  public static ModConfig get() {
    return INSTANCE;
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
      return;
    }

    try (CommentedFileConfig config = CommentedFileConfig.builder(file.toFile()).build()) {
      if (Files.exists(file)) {
        config.load();
      }

      ModConfig loaded = new ModConfig();
      loaded.lightningEffectsEnabled = config.getOrElse("lightning_effects_enabled", true);
      INSTANCE = loaded;

      config.setComment("lightning_effects_enabled", " Whether charged lightning effects on tools and armor are enabled at all.");
      config.set("lightning_effects_enabled", INSTANCE.lightningEffectsEnabled);
      config.save();
    } catch (Exception e) {
      Constants.LOG.error("Failed to load More Useful Copper config, using defaults", e);
      INSTANCE = new ModConfig();
    }
  }
}
