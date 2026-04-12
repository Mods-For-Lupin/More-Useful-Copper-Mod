package io.github.jason13official.more_useful_copper.impl.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.jason13official.more_useful_copper.Constants;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

public class ModConfig {

  private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
  private static ModConfig INSTANCE = new ModConfig();

  /// Whether charged lightning effects on tools and armor are enabled at all.
  public boolean lightningEffectsEnabled = true;

  public static ModConfig get() {
    return INSTANCE;
  }

  public static Gson getGson() {
    return GSON;
  }

  /// Loads the config from `<configDir>/more_useful_copper-server.json`, creating it with defaults if absent.
  ///
  /// @param configDir the platform config directory (e.g. `.minecraft/config`)
  public static void load(Path configDir) {
    Path file = configDir.resolve("more_useful_copper-server.json");

    if (Files.exists(file)) {
      try (Reader reader = Files.newBufferedReader(file)) {
        ModConfig loaded = GSON.fromJson(reader, ModConfig.class);
        if (loaded != null) {
          INSTANCE = loaded;
        }
      } catch (IOException e) {
        Constants.LOG.error("Failed to load More Useful Copper config, using defaults", e);
      }
    }

    save(configDir);
  }

  private static void save(Path configDir) {
    Path file = configDir.resolve("more_useful_copper-server.json");

    try {
      Files.createDirectories(configDir);
      try (Writer writer = Files.newBufferedWriter(file)) {
        GSON.toJson(INSTANCE, writer);
      }
    } catch (IOException e) {
      Constants.LOG.error("Failed to save More Useful Copper config", e);
    }
  }
}
